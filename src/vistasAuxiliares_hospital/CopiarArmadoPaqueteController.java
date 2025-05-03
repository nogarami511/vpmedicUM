/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.PaqueteMedico;
import clases_hospital_DAO.PaqueteMedicoDAO;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class CopiarArmadoPaqueteController implements Initializable {

    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    PaqueteMedico paqueteMedicoOrigenSeleccion, paqueteMedicoDestinoSeleccion;
    ObservableList<PaqueteMedico> paquetesMedicos;
    Conexion conexion = new Conexion();
    Connection con;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfPaqueteOrigen;
    @FXML
    private TextField txfPaqueteDestino;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            autocompletado();
        } catch (SQLException ex) {
            Logger.getLogger(CopiarArmadoPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void copiarArmado(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        alertaConfirmacion.setTitle("CONFIRMACION");
        alertaConfirmacion.setHeaderText("¡ADVERTENCIA!");
        alertaConfirmacion.setContentText("AL COPIAR ESTE ARMADO DE PAQUETE,"
                + "\nSE ELIMINARÁ EL ARMADO EXSITENTE DE: " + paqueteMedicoDestinoSeleccion.getNombre());
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {

            String procedimiento = "{call CopiarArmadoPaquete(?,?,?)}";
            CallableStatement callableStatement = con.prepareCall(procedimiento);
            callableStatement.setInt(1, paqueteMedicoOrigenSeleccion.getId());
            callableStatement.setInt(2, paqueteMedicoDestinoSeleccion.getId());
            callableStatement.setInt(3, VPMedicaPlaza.userSystem);
            callableStatement.execute();

            alertaConfirmacion.setHeaderText("COPIA DE ARMADO EXITOSO");
            alertaConfirmacion.setContentText("PROCEDIMIENTO EJECUTADO CON EXITO");
            alertaConfirmacion.showAndWait();
            salir(event);
        }
    }

    public void autocompletado() throws SQLException {
        con = conexion.conectar2();
        PaqueteMedicoDAO paquetesMedicosDAO = new PaqueteMedicoDAO(con);
        paquetesMedicos = FXCollections.observableArrayList(paquetesMedicosDAO.obtenerTodos());

        AutoCompletionBinding<PaqueteMedico> paqueteMedicoOrigen = TextFields.bindAutoCompletion(txfPaqueteOrigen, paquetesMedicos);
        paqueteMedicoOrigen.setPrefWidth(1000);
        paqueteMedicoOrigen.setOnAutoCompleted(event -> {
            paqueteMedicoOrigenSeleccion = event.getCompletion();
        });

        AutoCompletionBinding<PaqueteMedico> paqueteMedicoDestino = TextFields.bindAutoCompletion(txfPaqueteDestino, paquetesMedicos);
        paqueteMedicoDestino.setPrefWidth(1000);
        paqueteMedicoDestino.setOnAutoCompleted(event -> {
            paqueteMedicoDestinoSeleccion = event.getCompletion();
        });

        con.close();
    }
}
