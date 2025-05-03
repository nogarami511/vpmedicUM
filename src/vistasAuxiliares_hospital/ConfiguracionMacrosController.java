/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Insumo;
import clases_hospital.TipoInsumoMedico;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.TipoInsumoMedicoDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ConfiguracionMacrosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Insumo> insumos = FXCollections.observableArrayList(); // Este va aser otro xdd

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    TipoInsumoMedicoDAO tipoinsumomedicoDAO;
    InsumosDAO insumosDAO;

    TipoInsumoMedico tipoInsumoMedico;
    Insumo insumo;

    @FXML
    private ComboBox<TipoInsumoMedico> cmbMacros;
    @FXML
    private TextField txfInsumos;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private TableColumn<?, ?> colTabla;
    @FXML
    private TableColumn<?, ?> colMacro;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenearBuscador();
        llenarCmb();
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        llenarTabla();
    }

    @FXML
    private void accionGuardar(ActionEvent event) {
        alertaConfirmacion.setTitle("CONFIRMACION");
        alertaConfirmacion.setHeaderText("¿ESTA SEGURO DE GUARDAR LOS CAMBIOS?");
        Optional<ButtonType> result = alertaConfirmacion.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            actualizarDatos();
        }
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        alertaConfirmacion.setTitle("CONFIRMACION");
        alertaConfirmacion.setHeaderText("¿ESTA SEGURO DE SALIR SIN GUARDAR LOS CAMBIOS?");
        Optional<ButtonType> result = alertaConfirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        }
    }

    private void llenarCmb() {
        con = conexion.conectar2();
        tipoinsumomedicoDAO = new TipoInsumoMedicoDAO(con);
        cmbMacros.getItems().clear();
        loadAseguradoras();

        cmbMacros.setOnAction(event -> {
            TipoInsumoMedico selectedTipoInsumoMacro = cmbMacros.getSelectionModel().getSelectedItem();
            if (selectedTipoInsumoMacro != null) {
                handleAseguradoraSelection(selectedTipoInsumoMacro);
            }
            cmbMacros.setDisable(true);
        });
    }

    private void loadAseguradoras() {
        try {
            List<TipoInsumoMedico> aseguradorasList = tipoinsumomedicoDAO.getAll();
            ObservableList<TipoInsumoMedico> observableAseguradorasList = FXCollections.observableArrayList(aseguradorasList);
            cmbMacros.setItems(observableAseguradorasList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleAseguradoraSelection(TipoInsumoMedico tipoInsumoMedico) {
        this.tipoInsumoMedico = tipoInsumoMedico;
        System.out.println("Aseguradora seleccionada: " + tipoInsumoMedico.getNombreTipoInsumoMedico());
        //llenarTabla(aseguradora.getIdAseguradora());
    }

    private void llenearBuscador() {
        try {
            con = conexion.conectar2();
            insumosDAO = new InsumosDAO(conexion.conectar2());

            AutoCompletionBinding<Insumo> pacientes = TextFields.bindAutoCompletion(txfInsumos, insumosDAO.obtenerTodosInsumos());
            pacientes.setMinWidth(500);

            pacientes.setOnAutoCompleted(event -> {
                insumo = event.getCompletion();
                insumo.setIdTipoInsumoMedicoMacro(tipoInsumoMedico.getIdTipoInsumoMedico());
                insumo.setNombreMacro(tipoInsumoMedico.getNombreTipoInsumoMedico());
            });
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionMacrosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenarTabla() {

        insumos.add(insumo);

        colTabla.setCellValueFactory(new PropertyValueFactory("nombre"));
        colMacro.setCellValueFactory(new PropertyValueFactory("nombreMacro"));

        tabla.setItems(insumos);
    }

    private void actualizarDatos() {
        try {
            con = conexion.conectar2();
            insumosDAO = new InsumosDAO(con);
            
            insumosDAO.actualizarInsumosTipoInusmo(insumo);

            alertaConfirmacion.setTitle("EXITO");
            alertaConfirmacion.setHeaderText("PROCESO FINALIZADO CORRECTAMENTE");
            alertaConfirmacion.showAndWait();

            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionMacrosController.class.getName()).log(Level.SEVERE, null, ex);

            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("HA OCRRIDO UN ERROR. CODIGO: " + ex.getErrorCode());
            alertaError.setContentText(ex.getMessage());
            alertaError.showAndWait();
        }
    }

}