/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Almacen;
import clases_hospital_DAO.AlmacenDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class SeleccionarAlmacenController implements Initializable {
    
    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    ObservableList<Almacen> almacenes = FXCollections.observableArrayList();
    
    Almacen almacen;
    
    AlmacenDAO almacendao;

    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<Almacen> cmbAlmacen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionAceptar(ActionEvent event) {
        Stage stage = (Stage) btnAceptar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionSeleccionAlmacen(ActionEvent event) {
        almacen = cmbAlmacen.getValue();
    }
    
    private void llenarCmbAlmacen() throws SQLException{
        almacendao = new AlmacenDAO(conexion.conectar2());
        almacenes.addAll(almacendao.getAllConPiso());
        cmbAlmacen.setItems(almacenes);
    }
    
}
