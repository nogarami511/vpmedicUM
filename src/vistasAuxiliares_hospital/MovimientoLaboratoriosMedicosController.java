/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Laboratorio;
import clases_hospital_DAO.LaboratoriosDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author alfar
 */
public class MovimientoLaboratoriosMedicosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Conexion conexion = new Conexion();
    Laboratorio laboratorioeditar;

    boolean agregar = true;

    LaboratoriosDAO laboratoriosdao;

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfNombreComercial;
    @FXML
    private TextField txfRazonSocial;
    @FXML
    private TextField txfDireccion;
    @FXML
    private TextField txfRFC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (agregar) {
            btnAgregar.setVisible(true);
        }
    }

    @FXML
    private void accionAgregar(ActionEvent event) throws SQLException {
        if (txfNombreComercial.getText().equals("") || txfRazonSocial.getText().equals("") || txfDireccion.getText().equals("") || txfRFC.getText().equals("")) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("LLENE TODOS LOS CAMPOS PARA CONTINUAR");
            alertaError.showAndWait();
        } else {
            Laboratorio laboratorio = new Laboratorio();

            laboratorio.setNombreComercial(txfNombreComercial.getText());
            laboratorio.setRazonSocial(txfRazonSocial.getText());
            laboratorio.setDireccion(txfDireccion.getText());
            laboratorio.setRfc(txfRFC.getText());

            laboratoriosdao = new LaboratoriosDAO(conexion.conectar2());
            laboratoriosdao.insertarLaboratorio(laboratorio);

            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("Confirmación");
            alertaConfirmacion.setContentText("LABORATORIO AGREGADO CORRECTAMENTE");
            alertaConfirmacion.showAndWait();

            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void accionEditar(ActionEvent event) throws SQLException {
        if (txfNombreComercial.getText().equals("") || txfRazonSocial.getText().equals("") || txfDireccion.getText().equals("") || txfRFC.getText().equals("")) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("LLENE TODOS LOS CAMPOS PARA CONTINUAR");
            alertaError.showAndWait();

        } else {
            laboratorioeditar.setNombreComercial(txfNombreComercial.getText());
            laboratorioeditar.setRazonSocial(txfRazonSocial.getText());
            laboratorioeditar.setDireccion(txfDireccion.getText());
            laboratorioeditar.setRfc(txfRFC.getText());

            laboratoriosdao = new LaboratoriosDAO(conexion.conectar2());
            laboratoriosdao.actualizarLaboratorio(laboratorioeditar);

            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("Confirmación");
            alertaConfirmacion.setContentText("LABORATORIO EDITADO CORRECTAMENTE");
            alertaConfirmacion.showAndWait();

            Stage stage = (Stage) btnEditar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void recibirDatos(int id_laboratorio) {
        try {
            btnEditar.setVisible(true);
            agregar = false;
            laboratoriosdao = new LaboratoriosDAO(conexion.conectar2());
            laboratorioeditar = laboratoriosdao.obtenerLaboratorioPorId(id_laboratorio);

            txfNombreComercial.setText(laboratorioeditar.getNombreComercial());
            txfRazonSocial.setText(laboratorioeditar.getRazonSocial());
            txfDireccion.setText(laboratorioeditar.getDireccion());
            txfRFC.setText(laboratorioeditar.getRfc());
        } catch (SQLException ex) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("HA OCURRIDO UN ERROR, FAVOR DE CONTACTAR AL DEPARTAMENTO DE SISTEMAS");
            alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
            alertaError.showAndWait();
        }
    }

}
