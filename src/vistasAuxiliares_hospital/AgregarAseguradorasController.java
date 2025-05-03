/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Aseguradora;
import clases_hospital_DAO.AseguradorasCostosDAO;
import controladores_hospital.AseguradoraDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AgregarAseguradorasController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Aseguradora> insumosaseguradorascostos = FXCollections.observableArrayList();
    private final ObservableList<Aseguradora> data = FXCollections.observableArrayList(
            new Aseguradora("", 0.0, false, false, false)
    );

    AseguradoraDAO aseguradoraDAO;
    AseguradorasCostosDAO aseguradoracostosDAO;

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Aseguradora> tabla;
    @FXML
    private TableColumn<Aseguradora, String> colAseguradora;
    @FXML
    private TableColumn<Aseguradora, Double> colFactor;
    @FXML
    private TableColumn<Aseguradora, Boolean> colMaterialCuracion;
    @FXML
    private TableColumn<Aseguradora, Boolean> colMedicamentos;
    @FXML
    private TableColumn<Aseguradora, Boolean> colEquipoMedico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        alertaConfirmacion.setTitle("CONFIRMACION");
        alertaConfirmacion.setHeaderText("¿LOS DATOS INGRESADOS SON CORRECTOS?");
        Optional<ButtonType> result = alertaConfirmacion.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            con = conexion.conectar2();
            aseguradoraDAO = new AseguradoraDAO(con);
            aseguradoracostosDAO = new AseguradorasCostosDAO(con);

            tabla.getItems().get(0).setUsuarioModificacionAseguradora(VPMedicaPlaza.userSystem);
            try {
                int id_aseguradora = aseguradoraDAO.addAseguradora(tabla.getItems().get(0));
                aseguradoracostosDAO.insertarCostosAseguradoraInsumos(id_aseguradora);

                alertaSuccess.setTitle("EXITO");
                alertaSuccess.setHeaderText("LA ASEGURADORA SE HA AGREGADO EXITOSAMENTE");
                alertaSuccess.showAndWait();

                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgregarAseguradorasController.class.getName()).log(Level.SEVERE, null, ex);
                alertaError.setTitle("ERROR");
                alertaError.setHeaderText("AH OCURIDO UN ERROR INESPEREADO");
                alertaError.setHeaderText("#" + ex.getErrorCode());
                alertaError.setContentText("ERROR: " + ex.getMessage());
                alertaError.showAndWait();
            }
        }

    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void llenarTabla() {
        colAseguradora.setCellValueFactory(new PropertyValueFactory<>("nombreAseguradora"));
        colFactor.setCellValueFactory(new PropertyValueFactory<>("factorAseguradora"));
        colMaterialCuracion.setCellValueFactory(new PropertyValueFactory<>("materialCuracionAseguradora"));
        colMedicamentos.setCellValueFactory(new PropertyValueFactory<>("medicamentosAseguradora"));
        colEquipoMedico.setCellValueFactory(new PropertyValueFactory<>("equipoMedicoAseguradora"));

        colAseguradora.setCellFactory(TextFieldTableCell.forTableColumn());
        colFactor.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colMaterialCuracion.setCellFactory(column -> {
        CheckBoxTableCell<Aseguradora, Boolean> cell = new CheckBoxTableCell<>(index -> {
            BooleanProperty active = new SimpleBooleanProperty(tabla.getItems().get(index).isMaterialCuracionAseguradora());
            active.addListener((obs, wasSelected, isNowSelected) -> {
                tabla.getItems().get(index).setMaterialCuracionAseguradora(isNowSelected);
            });
            return active;
        });
        return cell;
    });
    colMedicamentos.setCellFactory(column -> {
        CheckBoxTableCell<Aseguradora, Boolean> cell = new CheckBoxTableCell<>(index -> {
            BooleanProperty active = new SimpleBooleanProperty(tabla.getItems().get(index).isMedicamentosAseguradora());
            active.addListener((obs, wasSelected, isNowSelected) -> {
                tabla.getItems().get(index).setMedicamentosAseguradora(isNowSelected);
            });
            return active;
        });
        return cell;
    });
    colEquipoMedico.setCellFactory(column -> {
        CheckBoxTableCell<Aseguradora, Boolean> cell = new CheckBoxTableCell<>(index -> {
            BooleanProperty active = new SimpleBooleanProperty(tabla.getItems().get(index).isEquipoMedicoAseguradora());
            active.addListener((obs, wasSelected, isNowSelected) -> {
                tabla.getItems().get(index).setEquipoMedicoAseguradora(isNowSelected);
            });
            return active;
        });
        return cell;
    });

        colAseguradora.setOnEditCommit(event -> {
            Aseguradora aseguradora = event.getRowValue();
            aseguradora.setNombreAseguradora(event.getNewValue());
        });
        colFactor.setOnEditCommit(event -> {
            Aseguradora aseguradora = event.getRowValue();
            aseguradora.setFactorAseguradora(event.getNewValue());
        });

        tabla.setItems(data);
        tabla.setEditable(true);
    }

}
