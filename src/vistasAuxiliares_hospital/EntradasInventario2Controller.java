/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.CompraInsumoDetalle;
import clases_hospital.Costo;
import clases_hospital.Insumo;
import clases_hospital.Inventario;
import clases_hospital.InventarioDetalle;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.EntradasDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventarioDetalleDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import clases_hospital_DAO.ProveedorDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.Reporte;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class EntradasInventario2Controller implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<MovimientoDetalle> movimientoDetalles = FXCollections.observableArrayList();
    ObservableList<InventarioDetalle> lotesOB = FXCollections.observableArrayList();
    MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Connection conProveedor = conexion.conectar2();
    private int idInsumo, idProveedor;
    private String nombreInsu;
    private double costo_total, subtotal, costoCaja, impuesto_insumo, impuestoSumatoria, costoCantidadUnitario, totalExistencia;
    CostosDAO costodao;
    InventariosDAO inventariodao;
    List<InventarioDetalle> listalotes;
    InventarioDetalleDAO inventarioDetalleDAO;
    InsumosDAO insumodao;
    ProveedorDAO proveedordao;
    EntradasDAO entradadao;
    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfInsumo;
    @FXML
    private TextField txfCaja;
    @FXML
    private DatePicker dtpCaducidad;
    @FXML
    private TextField txfFactura;
    @FXML
    private TextField txfProveedor;
    @FXML
    private TextField txfLote;
    @FXML
    private Label lblInventarioActual;
    @FXML
    private TableView<MovimientoDetalle> tabla;
    @FXML
    private TableColumn colInsumo;
    @FXML
    private TableColumn colLote;
    @FXML
    private TableColumn colFechaCaducidad;
    @FXML
    private TableColumn colInventarioAnteriror;
    @FXML
    private TableColumn colEntrada;
    @FXML
    private TableColumn colInventarioActual;
    @FXML
    private TableColumn colCostoCompra;
    @FXML
    private Button btnIngresarInsumos;
    @FXML
    private Button btnAgregarInusmo;
    @FXML
    private Label lblCostoCompraTotal;
    @FXML
    private RadioButton rdbSI;
    @FXML
    private RadioButton rdbNo;
    @FXML
    private TextField txfDescuento;
    @FXML
    private ComboBox<InventarioDetalle> cmbLotes;
    @FXML
    private RadioButton rdbLote;
    private int existelote = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        txfDescuento.setDisable(true);
        desactivarCampos();
        llenarTxfInsumo();
        lambda();
        tabla.setEditable(true);
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        int id;
        int sumatoriaInsumos = 0;
        boolean error = false;
        String errorS = "";
        String errorDetalle = "";
        int errorcode = 0;
        movimientodetalledao = new MovimientoDetalleDAO(conexion.conectar2());
        movimientopadredao = new MovimientoPadreDAO(conexion.conectar2());

        movimientoInventarioP.setTipo_mov(1);
        movimientoInventarioP.setId_origen(1);
        movimientoInventarioP.setId_destino(1);
        movimientoInventarioP.setId_proveedor(idProveedor);
        movimientoInventarioP.setFolio_mov(txfFactura.getText());
        movimientoInventarioP.setSubtotal(subtotal);
        movimientoInventarioP.setDescuento(Double.parseDouble(txfDescuento.getText()));
        movimientoInventarioP.setImporte_impuesto(impuestoSumatoria);
        movimientoInventarioP.setTotal(costo_total);
        movimientoInventarioP.setEstatus_movimiento(1);
        movimientoInventarioP.setObservaciones("");
        movimientoInventarioP.setUsuario_registro(userSystem);

        try {
            id = movimientopadredao.agregarMovimientoInventarioPINT(movimientoInventarioP);

            for (int i = 0; i < movimientoDetalles.size(); i++) {

                movimientoDetalles.get(i).setId_insumo_mov_padre(id);

                movimientodetalledao.CrearmovimientoconLote(movimientoDetalles.get(i));
                sumatoriaInsumos += movimientoDetalles.get(i).getMovimineto();
            }

        } catch (SQLException ex) {
            alertaError.setHeaderText("ENTRADA ERRONEA");
            alertaError.setContentText(ex.getMessage());
            alertaError.showAndWait();
            error = true;
            errorS = ex.getMessage();
            errorcode = ex.getErrorCode();
            ex.printStackTrace();
            id = 0;
        }

        if (error) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("CODIGO DE ERROR: " + errorcode);
            alertaError.setContentText(errorS);
            alertaError.showAndWait();
        } else {
            alertaSuccess.setHeaderText("INSUMO AGREGADO");
            alertaSuccess.setContentText("SE HAN AGREGADO: " + sumatoriaInsumos + " INSUMOS AL INVENTARIO");
            alertaSuccess.showAndWait();

//            Reporte reporte = new Reporte("ReporteEntradas4");
//            reporte.generarReporte(id);
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionActivarCampos(ActionEvent event) {

        if (txfFactura.getText().equals("") && txfProveedor.getText().equals("")) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("POR FAVOR, INGRESE UNA FACTURA Y UN PROVEEDOR PARA CONTINUAR.");
            alertaError.showAndWait();
        } else {
            rdbLote.setDisable(false);
            cmbLotes.setDisable(false);
            btnAgregar.setDisable(false);
            txfInsumo.setDisable(false);
            txfCaja.setDisable(false);
            dtpCaducidad.setDisable(false);
            lblInventarioActual.setDisable(false);
            txfLote.setDisable(true);
            tabla.setDisable(false);
            btnAgregarInusmo.setDisable(false);
            btnIngresarInsumos.setDisable(true);
            txfFactura.setDisable(true);
            txfProveedor.setDisable(true);
            rdbSI.setDisable(true);
            rdbNo.setDisable(true);
            txfDescuento.setDisable(true);

        }
    }

    @FXML
    private void accionAgregarInsumoTabla(ActionEvent event) {
        DecimalFormat df = new DecimalFormat("#.##");
        MovimientoDetalle movimientoDetalle = new MovimientoDetalle();
        costodao = new CostosDAO(conexion.conectar2());
        double cantidadentrada = (costoCantidadUnitario * Double.parseDouble(txfCaja.getText()));/*???*/
        double cajascant = Double.parseDouble(txfCaja.getText());
        double movimientodetallecoto = costoCaja * cajascant;
        double totalMuestra;
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaSeleccionada = dtpCaducidad.getValue();

        movimientoDetalle.setId_insumo(idInsumo);
        if (fechaSeleccionada != null && !fechaSeleccionada.isAfter(fechaActual)) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("INSUMO CADUCADO. COMUNIQUESE CON EL PROVEEDOR.");
            alertaError.showAndWait();
        } else {
            movimientoDetalle.setCaducidad(Date.valueOf(dtpCaducidad.getValue()));
        } 

        if (rdbLote.isSelected()) {
            movimientoDetalle.setLote_insumo(txfLote.getText());
        } else {
            movimientoDetalle.setLote_insumo(cmbLotes.getValue().getLote());
        }

        movimientoDetalle.setInventario_inicial(totalExistencia);
        movimientoDetalle.setMovimineto(cantidadentrada);
        movimientoDetalle.setInventario_final(totalExistencia + cantidadentrada);
        movimientoDetalle.setCosto(movimientodetallecoto);
        movimientoDetalle.setUsuario_modificacion(userSystem);
        movimientoDetalle.setNombre(nombreInsu);
        movimientoDetalle.setExiste_lote(existelote);

        movimientoDetalles.add(movimientoDetalle);

        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
        colLote.setCellValueFactory(new PropertyValueFactory("lote_insumo"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory("caducidad"));
        colInventarioAnteriror.setCellValueFactory(new PropertyValueFactory("inventario_inicial"));
        colEntrada.setCellValueFactory(new PropertyValueFactory("movimineto"));
        colInventarioActual.setCellValueFactory(new PropertyValueFactory("inventario_final"));
        colCostoCompra.setCellValueFactory(new PropertyValueFactory("costo"));

        tabla.setItems(movimientoDetalles);

        subtotal += Double.parseDouble(df.format(movimientodetallecoto));
        impuestoSumatoria += Double.parseDouble(df.format((movimientodetallecoto * impuesto_insumo)));
        costo_total += Double.parseDouble(df.format((movimientodetallecoto + (movimientodetallecoto * impuesto_insumo))
                - Double.parseDouble(txfDescuento.getText()))); // 
        if (costo_total < 0) {
            totalMuestra = 0;
        } else {
            totalMuestra = costo_total;
        }
        cmbLotes.getItems().clear();
        idInsumo = 0;
        txfInsumo.setText("");
        dtpCaducidad.setValue(null);
        txfLote.setText("");
        txfCaja.setText("");
        cantidadentrada = 0;
        lblInventarioActual.setText("");
        rdbLote.setSelected(false);

        lblCostoCompraTotal.setText("DESCUENTO: " + txfDescuento.getText() + " IMPUESTOS: $" + impuestoSumatoria + " COSTO TOTAL: $" + totalMuestra);

    }

    @FXML
    private void accionSi(ActionEvent event) {
        rdbNo.setSelected(false);
        txfDescuento.setDisable(false);
    }

    @FXML
    private void accionNo(ActionEvent event) {
        rdbSI.setSelected(false);
        txfDescuento.setDisable(true);
        txfDescuento.setText("0");
    }

    private void desactivarCampos() {
        btnAgregar.setDisable(true);
        txfInsumo.setDisable(true);
        txfCaja.setDisable(true);
        dtpCaducidad.setDisable(true);
        lblInventarioActual.setDisable(true);
        cmbLotes.setDisable(true);
        txfLote.setDisable(true);
        tabla.setDisable(true);
        btnAgregarInusmo.setDisable(true);
        rdbLote.setDisable(true);
    }
      private void lambda() {
        tabla.setRowFactory(tableView -> {
            TableRow<MovimientoDetalle> row = new TableRow<>();
            ContextMenu cxmConfiguracion = new ContextMenu();
            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
            descartarConsumo.setOnAction(event -> {
                //con = conexion.conectar2();
                MovimientoDetalle compraInsumoDetalle = row.getItem();
                movimientoDetalles.remove(compraInsumoDetalle);
                

            });
            cxmConfiguracion.getItems().add(descartarConsumo);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(cxmConfiguracion)
            );

            return row;
        });
    }

    private void llenarTxfInsumo() {
        insumodao = new InsumosDAO(conexion.conectar2());
        proveedordao = new ProveedorDAO(conexion.conectar2());

        try {
            AutoCompletionBinding<Insumo> insumos = TextFields.bindAutoCompletion(txfInsumo, insumodao.obtenerTodosInsumos());
            insumos.setOnAutoCompleted(e -> {
                cmbLotes.getItems().clear();
                costoCantidadUnitario = 0;
                Insumo insumoSelect = e.getCompletion();
                idInsumo = insumoSelect.getId();
                nombreInsu = insumoSelect.getNombre();
                impuesto_insumo = (insumoSelect.getIva() / 100.0);

                try {
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

                    costodao = new CostosDAO(conexion.conectar2());
                    inventariodao = new InventariosDAO(conexion.conectar2());

                    Costo costo = costodao.obtenerPorIdInsumo(idInsumo);
                    costoCantidadUnitario = costo.getCantidadUnitariaxCaja();
                    costoCaja = costo.getCostoCompraCaja();

                    Inventario inventario = inventariodao.obtenerPorIdInsumo(idInsumo);
                    totalExistencia = inventario.getTotalExistencia();
                    lblInventarioActual.setText("" + totalExistencia);

                } catch (SQLException ex) {
                    Logger.getLogger(EntradasInventarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            AutoCompletionBinding<Proveedor> proveedor = TextFields.bindAutoCompletion(txfProveedor, proveedordao.obtenerTodos());

            proveedor.setOnAutoCompleted(e -> {
                Proveedor proveeSelect = e.getCompletion();
                idProveedor = proveeSelect.getId();
            });
        } catch (SQLException ex) {
            Logger.getLogger(EntradasInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionnuevolote(ActionEvent event) {
        if (rdbLote.isSelected()) {
            txfLote.setDisable(false);
            cmbLotes.setDisable(true);
            existelote = 0;
            dtpCaducidad.setValue(null);
        } else {
            txfLote.setDisable(true);
            cmbLotes.setDisable(false);
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
                dtpCaducidad.setValue(fechaLocalDate);
            } catch (DateTimeParseException e) {
                // Maneja la excepción si la conversión falla
                System.err.println("Error al convertir la fecha: " + e.getMessage());
            }
        } else {
            // Maneja el caso en que el valor seleccionado es nulo
            System.err.println("El valor seleccionado en cmbLotes es nulo.");
        }
    }

}
