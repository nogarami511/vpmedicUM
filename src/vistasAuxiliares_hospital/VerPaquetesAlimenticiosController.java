/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.InsumoAlimenticio;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class VerPaquetesAlimenticiosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    int idPquete;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<InsumoAlimenticio> insumosAlimenticios = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    @FXML
    private TableView<InsumoAlimenticio> tabla;
    @FXML
    private TableColumn nombreInsumoAlimenticio;
    @FXML
    private TableColumn cantidadInsumoAlimenticio;
    @FXML
    private TableColumn medidaInsumoAlimenticio;
    @FXML
    private TextField nombrePaquete;
    @FXML
    private TextField idPaquete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
    }

    public void llenarTabla() {
        try {
            this.tabla.getItems().clear();
            Statement stmt = con.createStatement();
            
            String query = "SELECT a.id, a.id_insumoAlimento, i.nombre, a.cantidad, pin.presentacion, p.nombre\n"
                    + "  FROM armadopaquetealimento a\n"
                    + "  INNER JOIN insumos i ON a.id_insumoAlimento = i.id\n"
                    + "  INNER JOIN presentaciones_insumos pin ON i.id_presentacion = pin.id\n"
                    + "  INNER JOIN paquetesalimentos p ON p.id = a.id_paquete"
                    + "  WHERE a.id_paquete = '" + idPquete + "' ";
            ResultSet rs = stmt.executeQuery(query);
            InsumoAlimenticio insumoAlimenticio;
            while (rs.next()) {
                insumoAlimenticio = new InsumoAlimenticio();
                insumoAlimenticio.setId(rs.getInt(2));
                insumoAlimenticio.setNombre(rs.getString(3));
                insumoAlimenticio.setCantidad(rs.getDouble(4));
                insumoAlimenticio.setMedida(rs.getString(5));
                insumosAlimenticios.add(insumoAlimenticio);
            }
            nombreInsumoAlimenticio.setCellValueFactory(new PropertyValueFactory("nombre"));
            cantidadInsumoAlimenticio.setCellValueFactory(new PropertyValueFactory("cantidad"));
            medidaInsumoAlimenticio.setCellValueFactory(new PropertyValueFactory("medida"));
            tabla.setItems(insumosAlimenticios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recibirDatos(int idPaquete, String nombrePaquete) {
        this.idPaquete.setText(String.valueOf(idPaquete));//1
        this.nombrePaquete.setText(nombrePaquete);//QUESADILLA
        idPquete = idPaquete;
        llenarTabla();
    }

    @FXML
    private void ingresarInsumoAlimenticio(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarInsumoPaqueteMedico.fxml"));
            Parent root = loader.load();
            AgregarInsumoPaqueteMedicoController controller = loader.getController();
            controller.recibirDatos(nombrePaquete.getText(), idPaquete.getText(), 2);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setTitle("INSUMO NUEVO A PAQUETE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
            llenarTabla();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarInsumoAlimenticio(ActionEvent event) throws SQLException {
        int idInsumoAEliminar = 0, idPaqueteAEliminar = 0;
        con = conexion.conectar2();
        Statement stmt = con.createStatement();
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("ELMINAR DEL PAQUETE");
        alertaConfirmacion.setContentText("¿DESEA ELIMINAR EL INSUMO DEL PAQUETE?");
        InsumoAlimenticio insumoAlimenticioPaquete = this.tabla.getSelectionModel().getSelectedItem();
    
        ResultSet rs = stmt.executeQuery("SELECT pa.id_insumoAlimento FROM armadopaquetealimento pa WHERE pa.id_insumoAlimento = '" + insumoAlimenticioPaquete.getId() + "' AND pa.id_paquete = '" + idPaquete.getText() + "' ");
        while (rs.next()) {
            idInsumoAEliminar = rs.getInt(1);
            
        }

        Optional<ButtonType> alertaConfirmacion2 = alertaConfirmacion.showAndWait();
        if (alertaConfirmacion2.get() == ButtonType.OK) {
            try {
                CallableStatement callstmt = null;
                String sql = "{call eliminarInsumoPaqueteAlimenticio (?,?)}";
                callstmt = con.prepareCall(sql);
                callstmt.setInt(1, idInsumoAEliminar);
             
                callstmt.setInt(2, Integer.parseInt(idPaquete.getText()));
               
                
                callstmt.execute();
                callstmt.close();
                alertaSuccess.setHeaderText("Paquete Alimenticio fuera del Sistema");
                alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
                alertaSuccess.showAndWait();
                llenarTabla();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
