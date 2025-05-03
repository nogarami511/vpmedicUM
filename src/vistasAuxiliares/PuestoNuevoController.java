/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class PuestoNuevoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private TextField puestoIngresado;
    @FXML
    private TextField salarioIngresado;
    @FXML
    private TextField nPlazasIngresado;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ingresar(ActionEvent event) {
        String nombre = puestoIngresado.getText(), salario = salarioIngresado.getText(), nPlazas = nPlazasIngresado.getText();
        try {
            CallableStatement stmt = null; //objeto para llamar al procedimiento
            String sql = "{call ingresarPuesto (?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, nombre);
            stmt.setDouble(2, Double.valueOf(salario));
            stmt.setInt(3, Integer.valueOf(nPlazas));
            stmt.execute();
            alertaSuccess.setHeaderText("Nuevo Puesto Agregado");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            // get a handle to the stage
            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (Exception e) {
            alertaSuccess.setHeaderText("Error");
            alertaSuccess.setContentText("Error... ");
            alertaSuccess.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {

    }

    @FXML
    private void restriccionCaracteres(KeyEvent event) { //On Key Typed
        if (event.getTarget() == salarioIngresado) {
            if (!Character.isDigit(event.getCharacter().charAt(0)) && event.getCharacter().charAt(0) != '.') {
                event.consume();
            }
            if (event.getCharacter().charAt(0) == '.' && salarioIngresado.getText().contains(".")) {
                event.consume();
            }
        }
    }
}
