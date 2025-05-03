/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.CompraInsumoDetalle;
import clases_hospital.Folio;

import clases_hospital.Insumo;
import clases_hospital.Inventario;
import clases_hospital.InventarioDetalle;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventarioDetalleDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ConciliacionInventario2Controller implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfInsumo;
    private TextField txflote;
    @FXML
    private DatePicker dtpcaducidad;
    @FXML
    private TableColumn<?, ?> cel_lote;
    @FXML
    private TableColumn<?, ?> cel_caducidad;
    @FXML
    private TableColumn<?, ?> cel_cantidad;
    @FXML
    private TextField txfcantidad;
    @FXML
    private TableView<MovimientoDetalle> tabla;
    @FXML
    private Label lblcantidad;

    ObservableList<InventarioDetalle> OBinve_det = FXCollections.observableArrayList();
    ObservableList<InventarioDetalle> lotesOB = FXCollections.observableArrayList();
    ObservableList<MovimientoDetalle> movimientoDetalles = FXCollections.observableArrayList();
    List<InventarioDetalle> listalotes;

    Conexion conexion = new Conexion();
    Connection con;

    int id_inventario, idInsumo, existelote = 0, cantidad_actual = 0;

    Inventario inventario;
    Insumo insumo;
    MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();

    InsumosDAO insumosDAO;
    InventariosDAO inventariosDAO;
    InventarioDetalleDAO inventarioDetalleDAO;
    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;
    CompraInsumoDetalle compraInsumoDetalle;

    @FXML
    private Label lblcantidad2;
    @FXML
    private ComboBox<InventarioDetalle> cmbLotes;
    @FXML
    private TextField txfLote;
    @FXML
    private RadioButton rdbLote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btnGenerar.setDisable(false);
       
    }
    
        

}
