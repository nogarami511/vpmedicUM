/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.FormaPago;
import clases_hospital_DAO.FormaPagoDAO;
import clases_hospital_DAO.PagosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class CambiarFormaController implements Initializable {

    ObservableList<FormaPago> formasPagos;
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Conexion conexion = new Conexion();
    Connection con;
    int idFormaPagoSeleccion = 0, idPago = 0;
    String tipoPago = "";
    @FXML
    private Button btnSalir;
    @FXML
    private ComboBox<FormaPago> formasPagosCBX;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarChoiseBox();
        } catch (SQLException ex) {
            Logger.getLogger(CambiarFormaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();;
        stage.close();
    }

    public void llenarChoiseBox() throws SQLException {
        con = conexion.conectar2();
        FormaPagoDAO formaPagoDAO = new FormaPagoDAO(con);
        formasPagos = FXCollections.observableArrayList(formaPagoDAO.obtenerTodasFormasPagos());
        formasPagosCBX.setItems(formasPagos);
        formasPagosCBX.setOnAction(e -> {
            idFormaPagoSeleccion = formasPagosCBX.getValue().getId();
            tipoPago = formasPagosCBX.getValue().getTipo();
            
        });
    }

    public void setObjeto(int idPago) {
        this.idPago = idPago;
     
    }

    @FXML
    private void hacerCambioFormaPago(ActionEvent event) throws SQLException {
        PagosDAO pagosDAO = new PagosDAO(con);
        pagosDAO.actualizarFormaPago(idPago, idFormaPagoSeleccion,tipoPago);
        alertaConfirmacion.setHeaderText("PROCEDIMENTO EJECUTADO CON EXITO");
        alertaConfirmacion.setContentText("EL CAMBIO DE FORMA DE PAGO HA SIDO EFECTUADO");
        alertaConfirmacion.showAndWait();
        salir(event);
    }
}
