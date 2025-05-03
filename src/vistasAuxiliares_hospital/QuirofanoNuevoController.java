/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Quirofano;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
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
public class QuirofanoNuevoController implements Initializable {
    
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private Button btnInsertar;
    @FXML
    private TextField nombreEditar;
    @FXML
    private TextField costoEditar;
    @FXML
    private TextField descripcionEditar;
    @FXML
    private Button btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void insertar(ActionEvent event) {
        String nombre = nombreEditar.getText(), costo = costoEditar.getText(), descripcion = descripcionEditar.getText();
//        VPMedicaPlaza.userSystem
        try {
            CallableStatement stmt = null;
            String sql = "{call IngresarQuirofano (?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, nombre);
            stmt.setInt(2, Integer.parseInt(costo));
            stmt.setString(3, descripcion);
            stmt.setInt(4, VPMedicaPlaza.userSystem);
            stmt.execute();
            alertaSuccess.setHeaderText("Nuevo Quirofano Agregado");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            // get a handle to the stage
            Stage stage = (Stage) btnInsertar.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (Exception e) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("ERROR AL INGRESAR LOS DATOS ");
            
            alertaError.showAndWait();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnSalir.getScene().getWindow();
        stage_actual.close();
    }
    
    
}
