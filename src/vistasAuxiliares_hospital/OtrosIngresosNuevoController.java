/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.FormaPago;
import clases_hospital.OtrosIngresos;
import clases_hospital.TipoTarjeta;
import clases_hospital_DAO.FormaPagoDAO;
import clases_hospital_DAO.ProveedorDAO;
import clases_hospital_DAO.TipoTarjetaDAO;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class OtrosIngresosNuevoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    ObservableList<OtrosIngresos> otrosIngresos = FXCollections.observableArrayList();
    int idProveedor, idFormaPago, idTipoTarjeta;
    @FXML
    private JFXButton btnPrincipal;
    @FXML
    private TableView<OtrosIngresos> tabla;
    @FXML
    private TableColumn<OtrosIngresos, String> colMotivo;
    @FXML
    private TableColumn<OtrosIngresos, Double> colCantidad;
    @FXML
    private TableColumn<OtrosIngresos, Double> colImporte;
    @FXML
    private Label lblSubTotal;
    @FXML
    private Label lblIVA;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private TextArea txaObcerbaciones;
    @FXML
    private ComboBox<FormaPago> cmbFormaPagos;
    @FXML
    private Label lblTarjeta;
    @FXML
    private ComboBox<TipoTarjeta> cmbTarjetas;
    @FXML
    private TextField txfProveedor;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfMotivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarCombos();
            lambda();
            tabla.setItems(otrosIngresos);
            colMotivo.setCellValueFactory(new PropertyValueFactory("motivo"));
            colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
            colImporte.setCellValueFactory(new PropertyValueFactory("monto"));
        } catch (SQLException ex) {
            Logger.getLogger(OtrosIngresosNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionIngresar(ActionEvent event) {

 

    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();;
        stage.close();
    }

    @FXML
    private void agregarATabla(ActionEvent event) {
        OtrosIngresos otroIngreso = new OtrosIngresos();
        otroIngreso.setIdProveedor(idProveedor);
        otroIngreso.setCantidad(1);
        otroIngreso.setMonto(0);
        otroIngreso.setMotivo(txfMotivo.getText());
        otroIngreso.setObservaciones(txaObcerbaciones.getText());
        otrosIngresos.add(otroIngreso);
    }

    private void lambda() {
        colMotivo.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        colMotivo.setOnEditCommit(event -> {
            OtrosIngresos otroIngreso = event.getTableView().getItems().get(event.getTablePosition().getRow());
            otroIngreso.setMotivo(event.getNewValue());
            tabla.refresh();
        });
        colMotivo.setEditable(true);

        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCantidad.setOnEditCommit(event -> {
            OtrosIngresos otroIngreso = event.getTableView().getItems().get(event.getTablePosition().getRow());
            otroIngreso.setCantidad(event.getNewValue());
            tabla.refresh();
        });
        colCantidad.setEditable(true);

        colImporte.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colImporte.setOnEditCommit(event -> {
            OtrosIngresos otroIngreso = event.getTableView().getItems().get(event.getTablePosition().getRow());
            otroIngreso.setMonto(event.getNewValue());
            tabla.refresh();
        });
        colImporte.setEditable(true);
    }

    private void llenarCombos() throws SQLException {
        con = conexion.conectar2();

        AutoCompletionBinding<Proveedor> proveedorBusqueda = TextFields.bindAutoCompletion(txfProveedor, new ProveedorDAO(con).obtenerTodos());
        proveedorBusqueda.setPrefWidth(750);
        proveedorBusqueda.setOnAutoCompleted(e -> {
            idProveedor = e.getCompletion().getId();
        });

        cmbFormaPagos.setItems(FXCollections.observableArrayList(new FormaPagoDAO(con).obtenerTodos()));
        cmbFormaPagos.setOnAction(e -> {
            FormaPago formapago = cmbFormaPagos.getValue();
            if (formapago.getId() == 3 || formapago.getId() == 4) {
                lblTarjeta.setVisible(true);
                cmbTarjetas.setVisible(true);
                cmbTarjetas.setPromptText("SELECCIONAR");
            } else {
                lblTarjeta.setVisible(false);
                cmbTarjetas.setVisible(false);
                idTipoTarjeta = 0;
            }
            idFormaPago = formapago.getId();
        });

        cmbTarjetas.getItems().addAll(new TipoTarjetaDAO(con).selectTipoTarjetas());
        cmbTarjetas.setOnAction(event -> {
            idTipoTarjeta = cmbTarjetas.getValue().getIdTipoTarjeta();
        });

        con.close();
    }

}
