/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ServicioAdicionalNuevoController implements Initializable {
    
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField nombreSA;
    @FXML
    private TextField costoSA;
    @FXML
    private TextField propietarioSA;
    @FXML
    private TextField marcaSA;
    @FXML
    private TextField modeloSA;
    @FXML
    private TextField descripcionSA;
    @FXML
    private TextField tipoServicioAdicional;
    @FXML
    private TextField existenciaServicioAdicional;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void insertar(ActionEvent event) {
        String nombre = nombreSA.getText(), costo = costoSA.getText(), propietario = propietarioSA.getText(), marca = marcaSA.getText(), modelo = modeloSA.getText(), descripcion = descripcionSA.getText(), tipo = tipoServicioAdicional.getText(), existencia = existenciaServicioAdicional.getText();
        try {
            CallableStatement stmt = null;
            String sql = "{call InsertarServicioAdic (?,?,?,?,?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, nombre);
            stmt.setDouble(2, Double.parseDouble(costo));
            stmt.setString(3, propietario);
            stmt.setString(4, marca);
            stmt.setString(5, modelo);
            stmt.setString(6, descripcion);
            stmt.setInt(7, VPMedicaPlaza.userSystem);
            stmt.setString(8, tipo);
            stmt.setString(9, existencia);
            
            stmt.execute();
            alertaSuccess.setHeaderText("Servicio Adicional Agregado");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            // get a handle to the stage
            Stage stage = (Stage) btnInsertar.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (Exception e) {
            alertaError.setHeaderText("Error");
            alertaError.setContentText("ERROR AL INGRESAR LOS DATOS");
            alertaError.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnSalir.getScene().getWindow();
        stage_actual.close();
    }
    
}
