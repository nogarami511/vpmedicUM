/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Insumo;
import clases_hospital_DAO.InsumosDAO;
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
 * @author alfar
 */
public class InsumosFamiliaController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    ObservableList<Insumo> insumos = FXCollections.observableArrayList();

    int idFamilia;

    InsumosDAO insumosDAO;

    Insumo insumo;

    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfInsumo;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private TableColumn<Insumo, String> colTabla;
    @FXML
    private TableColumn<Insumo, String> colClave;
    @FXML
    private TableColumn<Insumo, String> colMontoUnitario;
    @FXML
    private TableColumn<Insumo, String> colMontoPaquete;
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
    private void accionAceptar(ActionEvent event) {
        con = conexion.conectar2();
        insumosDAO = new InsumosDAO(con);

        if (tabla.getItems().isEmpty()) {
            alertaError.setTitle("LLENAR TODOS LOS CAMPOS");
            alertaError.setHeaderText("LLENE TODOS LOS CAMPOS PARA CONTINUAR");
            alertaError.showAndWait();
        } else {
            try {
                for (int i = 0; i < insumos.size(); i++) {

                    insumosDAO.actualizarFamiliaInaumos(insumos.get(i));
                }

                alertaConfirmacion.setTitle("FAMILIA MODIFICACA CON EXITO");
                alertaConfirmacion.showAndWait();
                
                Stage stage = (Stage) btnAceptar.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                Logger.getLogger(InsumosFamiliaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        if (txfInsumo.getText().equals("")) {
        } else {
            insumos.add(insumo);

            colClave.setCellValueFactory(new PropertyValueFactory("clave"));
            colTabla.setCellValueFactory(new PropertyValueFactory("nombre"));
            colMontoUnitario.setCellValueFactory(new PropertyValueFactory("precio_venta_unitaria"));
            colMontoPaquete.setCellValueFactory(new PropertyValueFactory("precioVentaUnitariaPaquete"));
          

            tabla.setItems(insumos);

            txfInsumo.setText("");
        }
    }

    public void setIdfamilia(int idFamilia) {
        this.idFamilia = idFamilia;

        txfInsumo.setDisable(false);
        btnAgregar.setDisable(false);

        btnAceptar.setVisible(true);
        btnCancelar.setVisible(true);

        btnSalir.setVisible(false);

        try {
            buscardorInsumo();
        } catch (SQLException ex) {
            Logger.getLogger(InsumosFamiliaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setObejtos(int idFamilia) {
        this.idFamilia = idFamilia;

        llenarTabla();
    }

    private void llenarTabla() {
        con = conexion.conectar2();
        insumosDAO = new InsumosDAO(con);

        try {
            insumos.addAll(insumosDAO.optenerInsumosPorfamilia(idFamilia));

            colClave.setCellValueFactory(new PropertyValueFactory("clave"));
            colTabla.setCellValueFactory(new PropertyValueFactory("nombre"));
            colMontoUnitario.setCellValueFactory(new PropertyValueFactory("precio_venta_unitaria"));
            colMontoPaquete.setCellValueFactory(new PropertyValueFactory("precioVentaUnitariaPaquete"));

            tabla.setItems(insumos);

        } catch (SQLException ex) {
            Logger.getLogger(InsumosFamiliaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscardorInsumo() throws SQLException {
        con = conexion.conectar2();
        insumosDAO = new InsumosDAO(con);

        AutoCompletionBinding<Insumo> insumobuscar = TextFields.bindAutoCompletion(txfInsumo, insumosDAO.optenerInsumosSinfamilia());
        insumobuscar.setOnAutoCompleted(e -> {
            insumo = e.getCompletion();
            insumo.setIdFamiliaInsumo(idFamilia);
        });
    }

}
