/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.CompraInsumoDetalle;

import clases_hospital.Insumo;
import clases_hospital.Inventario;
import clases_hospital.InventarioDetalle;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.CostosDAO;
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
public class ConciliacionInventarioController implements Initializable {

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

    @FXML
    private void accionSallir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionAgregarInsumoTabla(ActionEvent event) {
        MovimientoDetalle movimientoDetalle = new MovimientoDetalle();
        double movimiento_actual = Double.parseDouble(txfcantidad.getText());

        cantidad_actual += (int) movimiento_actual;
        if (cantidad_actual <= compraInsumoDetalle.getCantidad_faltante()) {
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaSeleccionada = dtpcaducidad.getValue();

            movimientoDetalle.setId_insumo(idInsumo);
            System.out.println("id_insumo " + idInsumo);
            if (fechaSeleccionada != null && !fechaSeleccionada.isAfter(fechaActual)) {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("INSUMO CADUCADO. COMUNIQUESE CON EL PROVEEDOR.");
                alertaError.showAndWait();
            } else {
                movimientoDetalle.setCaducidad(Date.valueOf(dtpcaducidad.getValue()));
                if (rdbLote.isSelected()) {
                    movimientoDetalle.setLote_insumo(txfLote.getText());
                    movimientoDetalle.setId_inventario_detalle(0);
                    // System.out.println("id_inv_det" + movimientoDetalle.getId_inventario_detalle());
                } else {
                    movimientoDetalle.setLote_insumo(cmbLotes.getValue().getLote());
                    // System.out.println("id_inv_det" + cmbLotes.getValue().getId_inventario_detalle());
                    movimientoDetalle.setId_inventario_detalle(cmbLotes.getValue().getId_inventario_detalle());
                    //   System.out.println("id_inv_det" + movimientoDetalle.getId_inventario_detalle());
                }

                movimientoDetalle.setMovimineto(Double.parseDouble(txfcantidad.getText()));
                movimientoDetalle.setUsuario_modificacion(userSystem);
                movimientoDetalle.setNombre(txfInsumo.getText());
                movimientoDetalle.setExiste_lote(existelote);

                movimientoDetalles.add(movimientoDetalle);

                //.setCellValueFactory(new PropertyValueFactory("nombre"));
                cel_lote.setCellValueFactory(new PropertyValueFactory("lote_insumo"));
                cel_caducidad.setCellValueFactory(new PropertyValueFactory("caducidad"));
                cel_cantidad.setCellValueFactory(new PropertyValueFactory("movimineto"));

                tabla.setItems(movimientoDetalles);

            }
        } else {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("Favor de poner una cantidad menor a la faltante.");
            alertaError.showAndWait();
        }
        actualizarLabelCantidad();

        dtpcaducidad.setValue(null);
        cmbLotes.setValue(null);
        txfLote.setText("");
        txfcantidad.setText("");
        rdbLote.setSelected(false);

    }

    private void llenarDatos() throws SQLException {
        con = conexion.conectar2();
        idInsumo = compraInsumoDetalle.getId_insumo();
        txfInsumo.setText(compraInsumoDetalle.getNombreInsumo());

        System.out.println("" + idInsumo);
        inventarioDetalleDAO = new InventarioDetalleDAO(con);
        listalotes = inventarioDetalleDAO.traerlotes(idInsumo);
        lotesOB.addAll(listalotes);

        cmbLotes.setItems(lotesOB);
        if (!cmbLotes.getItems().isEmpty()) {

        } else {
            rdbLote.setSelected(true);
            txfLote.setDisable(false);
            cmbLotes.setDisable(true);
            existelote = 0;

        }
        lblcantidad.setText(" 0");
        lblcantidad2.setText("" + compraInsumoDetalle.getCantidad_faltante());

    }

    @FXML
    private void accionnuevolote(ActionEvent event) {
        if (rdbLote.isSelected()) {
            txfLote.setDisable(false);
            cmbLotes.setDisable(true);
            existelote = 0;
            dtpcaducidad.setValue(null);
            dtpcaducidad.setDisable(false);
        } else {
            txfLote.setDisable(true);
            cmbLotes.setDisable(false);
            dtpcaducidad.setDisable(true);
            existelote = 1;
        }
    }

    @FXML
    private void accioncmbcaducidad(ActionEvent event) {
        // Obtén el valor seleccionado en cmbLotes
        InventarioDetalle itemSeleccionado = cmbLotes.getValue();

        // Verifica si el valor seleccionado no es nulo
        if (itemSeleccionado != null) {
            // Obtén la fecha del valor seleccionado en cmbLotes
            Date fechaCaducidad = itemSeleccionado.getCaducidad();

            // Crea un formateador de fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            // Formatea la fecha como una cadena
            String fechaFormateada = dateFormat.format(fechaCaducidad);

            try {
                // Convierte la cadena formateada a LocalDate
                LocalDate fechaLocalDate = LocalDate.parse(fechaFormateada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                // Establece el valor del DatePicker
                dtpcaducidad.setValue(fechaLocalDate);
            } catch (DateTimeParseException e) {
                // Maneja la excepción si la conversión falla
                System.err.println("Error al convertir la fecha: " + e.getMessage());
            }
        } else {
            // Maneja el caso en que el valor seleccionado es nulo
            System.err.println("El valor seleccionado en cmbLotes es nulo.");
        }
    }

    @FXML
    private void accionConciliar(ActionEvent event) {
        MovimientoDetalleDAO movimientodetalledao = new MovimientoDetalleDAO(conexion.conectar2());
        MovimientoPadreDAO movimientopadredao = new MovimientoPadreDAO(conexion.conectar2());
        InventarioDetalleDAO inventarioDetalleDAO = new InventarioDetalleDAO(conexion.conectar2());
        int id_mov_p;

        MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP(); // Supongamos que esta clase existe
        movimientoInventarioP.setTipo_mov(1);
        movimientoInventarioP.setId_origen(1);
        movimientoInventarioP.setId_destino(1);
        movimientoInventarioP.setId_proveedor(0);
        movimientoInventarioP.setFolio_mov("");
        movimientoInventarioP.setSubtotal(0);
        movimientoInventarioP.setDescuento(0);
        movimientoInventarioP.setImporte_impuesto(0);
        movimientoInventarioP.setTotal(0);
        movimientoInventarioP.setEstatus_movimiento(1);
        movimientoInventarioP.setObservaciones("");
        movimientoInventarioP.setUsuario_registro(userSystem);

        alertaSuccess.setTitle("AGREGANDO LOTES");
        alertaSuccess.setHeaderText("PROCESO ejecutandose");

        alertaError.setHeaderText("ALGO SALIÓ MAL");
        alertaError.setContentText("ALGO SALIÓ MAL, FAVOR DE REINTENTAR MÁS TARDE");

        try {
            id_mov_p = movimientopadredao.agregarMovimientoInventarioPINT(movimientoInventarioP);
            //System.out.println("id _ mov padre" + id_mov_p);

            Platform.runLater(() -> alertaSuccess.show());

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i < movimientoDetalles.size(); i++) {
                        movimientoDetalles.get(i).setId_insumo_mov_padre(id_mov_p);
                        inventarioDetalleDAO.EntradasInventarioDetConCompras(movimientoDetalles.get(i), compraInsumoDetalle.getId_compras_insumo_detalle(), userSystem);
                        System.out.println(i + "El proceso se ejecutó correctamente");

                        final int currentProcessed = i + 1; // Incrementamos para obtener la cantidad actual procesada
                        Platform.runLater(() -> {
                            alertaSuccess.setContentText(currentProcessed + " de " + movimientoDetalles.size() + " procesados");
                        });
                    }
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                alertaSuccess.setContentText("El proceso se ejecutó correctamente");
                alertaSuccess.close();
                alertaError.close();
                alertaSuccess.showAndWait();
                Stage stage = (Stage) btnGenerar.getScene().getWindow();
                stage.close();
            });

            task.setOnFailed(e -> {
                alertaError.showAndWait();
            });

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        } catch (SQLException ex) {
            Logger.getLogger(ConciliacionInventarioController.class.getName()).log(Level.SEVERE, null, ex);
            alertaError.showAndWait();
        }
    }

    public void setObjeto(CompraInsumoDetalle compraInsumoDetalle) throws SQLException {
        this.compraInsumoDetalle = compraInsumoDetalle;
        llenarDatos();
    }

    private void actualizarLabelCantidad() {
        cantidad_actual = 0;

        for (int i = 0; i < movimientoDetalles.size(); i++) {
            cantidad_actual += movimientoDetalles.get(i).getMovimineto();
        }
        lblcantidad.setText(" " + cantidad_actual);
    }

}
