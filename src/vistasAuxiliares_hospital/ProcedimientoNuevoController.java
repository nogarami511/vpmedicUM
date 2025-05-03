/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Especialidad;
import clases_hospital.Procedimiento;
import clases_hospital_DAO.EspeciaidadesDAO;
import clases_hospital_DAO.ProcedimientoDAO;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ProcedimientoNuevoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Especialidad> especialidades = FXCollections.observableArrayList();
    ProcedimientoDAO procedimientodao;
    EspeciaidadesDAO especilaidesdao;

    @FXML
    private TextField nombreIngresar;
    private TextField especialidadIngresar;
    @FXML
    private Button btnIngresar;
    @FXML
    private ChoiceBox cmbTipoProcedimiento;
    @FXML
    private Button btnSalir;
    @FXML
    private ChoiceBox<Especialidad> especialidadProcedimiento;

    private int idTipo = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarCMB();
            llenarProcedimiento();
        } catch (SQLException ex) {
            Logger.getLogger(ProcedimientoNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) {
        con = conexion.conectar2();
        procedimientodao = new ProcedimientoDAO(con);
        Procedimiento procedimiento = new Procedimiento();
        String nombre = nombreIngresar.getText();
        String tipo_procedimiento = cmbTipoProcedimiento.getSelectionModel().getSelectedItem().toString();
//        String especialidad = especialidadIngresar.getText();

        procedimiento.setNombre(nombre);
        procedimiento.setId_especialidad(idTipo);
        procedimiento.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);   
        procedimiento.setTipo_procedimiento(tipo_procedimiento);
        switch (tipo_procedimiento) {
                case "A":
                    procedimiento.setId_tipo_procedimiento(1);
                    break;
                case "AA":
                    procedimiento.setId_tipo_procedimiento(2);
                    break;
                case "AAA":
                    procedimiento.setId_tipo_procedimiento(3);
                    break;
            }
        

        try {
            
            procedimientodao.create(procedimiento);
            
            alertaSuccess.setHeaderText("NUEVO PROCEDIMIENTO AGREGADO");
            alertaSuccess.setContentText("PROCEDIMIENTO EJECUTADO CON EXITO");
            alertaSuccess.showAndWait();
            // get a handle to the stage
            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (Exception e) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("ERROR AL INGRESAR LOS DATOS ");
            alertaError.showAndWait();
            e.printStackTrace();
        }
    }

    private void llenarCMB() {
        cmbTipoProcedimiento.getItems().add("A");
        cmbTipoProcedimiento.getItems().add("AA");
        cmbTipoProcedimiento.getItems().add("AAA");
    }

    private void llenarProcedimiento() throws SQLException {
        especilaidesdao = new EspeciaidadesDAO(conexion.conectar2());
        especialidades.addAll(especilaidesdao.obtenerTodos());
        especialidadProcedimiento.setItems(especialidades);
        especialidadProcedimiento.setOnAction(e -> {
            Especialidad espeselect = especialidadProcedimiento.getValue();
            idTipo = espeselect.getId();
        });
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnSalir.getScene().getWindow();
        stage_actual.close();
    }

}
