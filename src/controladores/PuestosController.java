/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clase.Puesto;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class PuestosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Puesto> puestos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    
    @FXML
    private TableView<Puesto> tabla;
    @FXML
    private TableColumn nombrePuesto;
    @FXML
    private TableColumn salarioPuesto;
    @FXML
    private TableColumn nPlazasPuesto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(PuestosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        this.tabla.getItems().clear();
        Puesto puesto;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from puestos");

        try {
            while (rs.next()) {
                puesto = new Puesto();
                puesto.setId(rs.getInt(1));
                puesto.setNombre(rs.getString(2));
                puesto.setSalario(rs.getDouble(3));
                puesto.setPlazas(rs.getInt(4));
                puestos.add(puesto);
            }
        } catch (Exception e) {
        }
        nombrePuesto.setCellValueFactory(new PropertyValueFactory("nombre"));
        salarioPuesto.setCellValueFactory(new PropertyValueFactory("salarioFormat"));
        nPlazasPuesto.setCellValueFactory(new PropertyValueFactory("plazas"));
        tabla.setItems(puestos);
        con.close();
    }

    @FXML
    private void agregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/PuestoNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("PUESTO NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        Puesto puesto = this.tabla.getSelectionModel().getSelectedItem();
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("¿Estas seguro de el puesto : " + puesto.getNombre() + " ?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
            CallableStatement stmt = null; //objeto para llamar al procedimiento
            String sql = "{call eliminarPuesto (?)}";
            stmt = con.prepareCall(sql);
            stmt.setInt(1, puesto.getId());
            stmt.execute();
            alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
            alertaSuccess.setContentText("Puesto fuera del sistema");
            alertaSuccess.showAndWait();
            llenarTabla();
        }
        con.close();
    }



}
