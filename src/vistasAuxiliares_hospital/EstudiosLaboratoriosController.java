/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.EstudioLaboratorio;
import clases_hospital.Insumo;
import clases_hospital.Laboratorio;
import clases_hospital_DAO.EstudiosLaboratoriosDAO;
import clases_hospital_DAO.InsumosDAO;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class EstudiosLaboratoriosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<EstudioLaboratorio> estudioslabLaboratorios = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    private Insumo insumo = new Insumo();

    private String nombreEstudio;
    private int id_insumo;
    private double precio;

    private int id_lab;

    InsumosDAO insumodao;
    EstudiosLaboratoriosDAO estudioslaboratoriodao;

    @FXML
    private Label lblLaboratorio;
    @FXML
    private Button btnSalir;
    @FXML
    private AnchorPane apAgregar;
    @FXML
    private TextField txfEstudios;
    @FXML
    private Button btnAgregarTabla;
    @FXML
    private TableView<EstudioLaboratorio> tablaAgregarEstudios;
    @FXML
    private TableColumn<?, ?> colAgregarEstudio;
    @FXML
    private TableColumn<?, ?> colAgregarCosto;
    @FXML
    private Button btnAgregarEstudios;
    @FXML
    private AnchorPane apVer;
    @FXML
    private TableView<EstudioLaboratorio> tablaVer;
    @FXML
    private TableColumn<?, ?> colVerEstudio;
    @FXML
    private TableColumn<?, ?> colVerCosto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionAgregarTabla(ActionEvent event) {
        if (insumo != null) {
            EstudioLaboratorio estudiolaboratorio = new EstudioLaboratorio(id_insumo, id_lab, VPMedicaPlaza.userSystem, 1, precio, nombreEstudio);
            estudioslabLaboratorios.add(estudiolaboratorio);

            colAgregarEstudio.setCellValueFactory(new PropertyValueFactory("nombreEstudio"));
            colAgregarCosto.setCellValueFactory(new PropertyValueFactory("costoSinIVA"));

            tablaAgregarEstudios.setItems(estudioslabLaboratorios);
        }
    }

    @FXML
    private void accionAgregarEstudios(ActionEvent event) {
        estudioslaboratoriodao = new EstudiosLaboratoriosDAO(conexion.conectar2());
        try {
            for (EstudioLaboratorio estudiolaboratorio : estudioslabLaboratorios) {
                estudioslaboratoriodao.insertarEstudioLaboratorio(estudiolaboratorio);
            }
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("Confirmación");
            alertaConfirmacion.setContentText("ESTUDIOS VINCULADOS CON LABORATORIO");
            alertaConfirmacion.showAndWait();

            Stage stage = (Stage) btnAgregarEstudios.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("HA OCURRIDO UN ERROR, FAVOR DE CONTACTAR AL DEPARTAMENTO DE SISTEMAS");
            alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
            alertaError.showAndWait();
        }
    }

    public void agregarEstudios(Laboratorio laboratorio) {
        apAgregar.setVisible(true);
        lblLaboratorio.setText(laboratorio.getNombreComercial());
        id_lab = laboratorio.getIdLaboratorio();
        try {
            llenarBuscadorEstudios();
        } catch (SQLException ex) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("HA OCURRIDO UN ERROR, FAVOR DE CONTACTAR AL DEPARTAMENTO DE SISTEMAS");
            alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
            alertaError.showAndWait();
        }
    }

    private void llenarBuscadorEstudios() throws SQLException {
        insumodao = new InsumosDAO(conexion.conectar2());
        AutoCompletionBinding<Insumo> insumos = TextFields.bindAutoCompletion(txfEstudios, insumodao.optenerDatosEstudios());

        insumos.setOnAutoCompleted(event -> {
            insumo = event.getCompletion();
            id_insumo = insumo.getId();
            nombreEstudio = insumo.getNombre();
            precio = insumo.getPrecio_venta_unitaria();
        });
    }

    public void visualizarEstudios(Laboratorio laboratorio) {
        try {
            apVer.setVisible(true);
            estudioslaboratoriodao = new EstudiosLaboratoriosDAO(conexion.conectar2());
            estudioslabLaboratorios.addAll(estudioslaboratoriodao.obtenerTodosLosEstudiosLaboratoriosPorIdLaboratorio(laboratorio.getIdLaboratorio()));

            colVerEstudio.setCellValueFactory(new PropertyValueFactory("nombreEstudio"));
            colVerCosto.setCellValueFactory(new PropertyValueFactory("costoSinIVA"));

            tablaVer.setItems(estudioslabLaboratorios);
        } catch (SQLException ex) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("HA OCURRIDO UN ERROR, FAVOR DE CONTACTAR AL DEPARTAMENTO DE SISTEMAS");
            alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
            alertaError.showAndWait();
        }
    }

}
