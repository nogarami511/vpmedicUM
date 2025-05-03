/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital_DAO.ProveedorDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class ProveedorNuevoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Proveedor proveedorRecibido = new Proveedor();
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField nombreComercialIngresado;
    @FXML
    private TextField razonSocialIngresado;
    @FXML
    private TextField direccionIngresado;
    @FXML
    private TextField telefonoIngresado;
    @FXML
    private Button btnActualizar;
    @FXML
    private TextField rfcIngresado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ingresar(ActionEvent event) throws SQLException, IOException {
        con = conexion.conectar2();
        Proveedor proveedor = new Proveedor();
        ProveedorDAO provedorDAO = new ProveedorDAO(con);
        proveedor.setNombreComercial(nombreComercialIngresado.getText());
        proveedor.setRazonSocial(razonSocialIngresado.getText());
        proveedor.setDireccion(direccionIngresado.getText());
        proveedor.setTelefono(telefonoIngresado.getText());
        proveedor.setRfc(rfcIngresado.getText());
        provedorDAO.insertar(proveedor);
        con.close();
        alertaSuccess.setHeaderText("PROCEDIMIENTO EXITOSO");
        alertaSuccess.setContentText("PROVEEDOR INGRESADO A SISTEMA");
        alertaSuccess.showAndWait();
        salir(event);
    }

    @FXML
    private void salir(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void actualizar(ActionEvent event) throws SQLException, IOException {
        con = conexion.conectar2();

        ProveedorDAO provedorDAO = new ProveedorDAO(con);
        proveedorRecibido.setNombreComercial(nombreComercialIngresado.getText());
        proveedorRecibido.setRazonSocial(razonSocialIngresado.getText());
        proveedorRecibido.setDireccion(direccionIngresado.getText());
        proveedorRecibido.setTelefono(telefonoIngresado.getText());
        proveedorRecibido.setRfc(rfcIngresado.getText());
        
        provedorDAO.actualizar(proveedorRecibido);
        con.close();
        alertaSuccess.setHeaderText("PROCEDIMIENTO EXITOSO");
        alertaSuccess.setContentText("PROVEEDOR EDITADO");
        alertaSuccess.showAndWait();
        salir(event);
    }

    public void setObjeto(Proveedor proveedor) {
        btnIngresar.setVisible(false);
        btnActualizar.setVisible(true);
        proveedorRecibido = proveedor;
        nombreComercialIngresado.setText(proveedorRecibido.getNombreComercial());
        razonSocialIngresado.setText(proveedorRecibido.getRazonSocial());
        direccionIngresado.setText(proveedorRecibido.getDireccion());
        telefonoIngresado.setText(proveedorRecibido.getTelefono());
        rfcIngresado.setText(proveedorRecibido.getRfc());
    }
}
