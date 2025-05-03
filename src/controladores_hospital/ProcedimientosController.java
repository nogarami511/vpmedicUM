/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Procedimiento;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ProcedimientosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Procedimiento> procedimientos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    @FXML
    private TableView<Procedimiento> tabla;
    @FXML
    private TableColumn nombreProcedimiento;
    @FXML
    private TableColumn especialidadProcedimiento;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableColumn tipoDeCirugiaProcedimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(VPMedicaPlaza.userNameSystem.equals("MEZCLAS")){
            
        }
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ProcedimientosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        this.tabla.getItems().clear();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT p.nombre AS nombreCirugia , e.nombre AS nombreEspecialidad, t.nombre AS tipo FROM procedimiento p JOIN especialidades e ON p.id_especialidad = e.id JOIN tipoprocedimiento t ON t.categoria = p.tipo_procedimiento ORDER BY e.id, p.tipo_procedimiento;");
        Procedimiento procedimiento;
        try {
            while (rs.next()) {
                procedimiento = new Procedimiento();
                procedimiento.setNombre(rs.getString(1).toUpperCase());
                procedimiento.setNombreEspecialidad(rs.getString(2).toUpperCase());
                procedimiento.setTipo_procedimiento(rs.getString(3).toUpperCase());
                procedimientos.add(procedimiento);
            }

            nombreProcedimiento.setCellValueFactory(new PropertyValueFactory("nombre"));
            tipoDeCirugiaProcedimiento.setCellValueFactory(new PropertyValueFactory("tipo_procedimiento"));
            especialidadProcedimiento.setCellValueFactory(new PropertyValueFactory("nombreEspecialidad"));
            tabla.setItems(procedimientos);
            con.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void insertar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ProcedimientoNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("PROCEDIMIENTO NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

}
