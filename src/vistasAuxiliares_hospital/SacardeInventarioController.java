/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.*;
import clases_hospital_DAO.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class SacardeInventarioController implements Initializable {

    // DAO
    InsumosDAO insumosDAO;
    TipoMovimientoAlmacenDAO tipoMovimientoAlmacenDAO;
    InventarioDetalleDAO inventarioDetalleDAO;
    MovimientoPadreDAO movimientoPDAO;

    // CLASES NORMALES
    MovimientoDetalle productoSaliDetalle;
    MovimientoInventarioP movimientoP;

    // LISTAS OBSERVABLES
    ObservableList<InventarioDetalle> listalotesOB = FXCollections.observableArrayList();
    ObservableList<Insumo> listaInsumosOB = FXCollections.observableArrayList();
    ObservableList<MovimientoDetalle> listaRetirarOB = FXCollections.observableArrayList();
    ObservableList<TipoMovimientoAlmacen> tipoMovimientoOB = FXCollections.observableArrayList();

    //ALERTAS
    
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);

    // CONEXION
    Conexion conection = new Conexion();
    Connection con;

    // OBJETOS DE VISTA
    @FXML
    private TextField txfInsumo;
    @FXML
    private TextField txfCantidad;
    @FXML
    private Button btnAgregarTabla;
    @FXML
    private ComboBox<TipoMovimientoAlmacen> cmbTípoSalida;
    @FXML
    private ComboBox<InventarioDetalle> cmbLote;
    @FXML
    private Label lblLote;
    @FXML
    private Label lblNombrePaciente;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<MovimientoDetalle> tabla;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private TableColumn<?, ?> colTipoMovimiento;
    @FXML
    private TableColumn<?, ?> colLoteCad;
    @FXML
    private TableColumn<?, ?> colCantidad;

    // VARIABLES GLOBALES
    double cantidad = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            checarConexion();
            llenarDatos();
            llenarBuscadorInsumo();
        } catch (SQLException ex) {
            Logger.getLogger(SacardeInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarDatos() throws SQLException {
        insumosDAO = new InsumosDAO(con);
        tipoMovimientoAlmacenDAO = new TipoMovimientoAlmacenDAO(con);

        listaInsumosOB.addAll(insumosDAO.obtenerTodosInsumos());
        tipoMovimientoOB.addAll(tipoMovimientoAlmacenDAO.seleccionarTipoMovimientoNegativos());

        cmbTípoSalida.setItems(tipoMovimientoOB);

    }

    private void llenarBuscadorInsumo() {


            AutoCompletionBinding<Insumo> nombres = TextFields.bindAutoCompletion(txfInsumo, listaInsumosOB);
            nombres.setPrefWidth(1000);
            nombres.setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<Insumo> event) -> {
                
                try {
                    checarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(SacardeInventarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                txfInsumo.setDisable(true);
                txfCantidad.setDisable(false);
                cmbLote.setDisable(false);
                cmbTípoSalida.setDisable(false);
                btnAgregarTabla.setDisable(false);
                
                productoSaliDetalle = new MovimientoDetalle();

                productoSaliDetalle.setId_insumo(event.getCompletion().getId());
                productoSaliDetalle.setNombre(event.getCompletion().getNombre());

                inventarioDetalleDAO = new InventarioDetalleDAO(con);

                try {
                    listalotesOB.addAll(inventarioDetalleDAO.traerlotesconCaducados(event.getCompletion().getId()));
                    cmbLote.setItems(listalotesOB);
                } catch (SQLException ex) {
                    alertaError.setTitle("ERROR");
                    alertaError.setHeaderText("ALGO SALIO MAL");
                    alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\\n" + ex.getMessage());
                    alertaError.showAndWait();
                    Logger.getLogger(SacardeInventarioController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
    
    }

    private void llenartabla() {
        
        listaRetirarOB.add(productoSaliDetalle);

        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTipoMovimiento.setCellValueFactory(new PropertyValueFactory("tipo_movimientoString"));
        colLoteCad.setCellValueFactory(new PropertyValueFactory("lote_insumo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("movimineto"));
        tabla.setItems(listaRetirarOB);

    }
        private void lambda() {
        tabla.setRowFactory(tableView -> {
            TableRow<MovimientoDetalle> row = new TableRow<>();
            ContextMenu cxmConfiguracion = new ContextMenu();
            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
            descartarConsumo.setOnAction(event -> {
                MovimientoDetalle movimientoDetalle = row.getItem();
                listaRetirarOB.remove(movimientoDetalle);

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

    private void checarConexion() throws SQLException {

        if (con == null || con.isClosed()) {
            con = conection.conectar2();
        }

    }
    
    private void  limpiarcampos(){
        cmbLote.getItems().clear();
        txfCantidad.setText("");
        txfInsumo.setText("");     
        cmbLote.setValue(null);
        cmbTípoSalida.setValue(null);
        cantidad = 0;
        lblLote.setText("0.0");
        
    }
        
    private int CrearMovimientoP() throws SQLException{
        checarConexion();
        movimientoPDAO = new MovimientoPadreDAO(con);
        movimientoP = new MovimientoInventarioP();
        int mov = 0;
        movimientoP.setTipo_mov(15);
        movimientoP.setId_origen(1);
        movimientoP.setId_destino(0);
        movimientoP.setId_proveedor(userSystem);
        movimientoP.setFolio_mov("0");
        movimientoP.setSubtotal(0);
        movimientoP.setDescuento(0.0);
        movimientoP.setImporte_impuesto(0);
        movimientoP.setTotal(0);
        movimientoP.setEstatus_movimiento(1);
        movimientoP.setObservaciones("");
        movimientoP.setUsuario_registro(userSystem);
        
         mov = movimientoPDAO.agregarMovimientoInventarioPINT(movimientoP);
         return  mov;
    }

    @FXML
    private void accionSeleccionarLote(ActionEvent event) {
        if (cmbLote.getValue() != null) {
            productoSaliDetalle.setLote_insumo(cmbLote.getValue().toString());
            productoSaliDetalle.setId_inventario_detalle(cmbLote.getValue().getId_inventario_detalle());
            lblLote.setText("" + cmbLote.getValue().getCantidad());
            cantidad = cmbLote.getValue().getCantidad();
        }
    }

    @FXML
    private void accionTipoSalida(ActionEvent event) {
        if (cmbTípoSalida != null && cmbTípoSalida.getValue() != null) {
            productoSaliDetalle.setId_tipo_mov_almacen(cmbTípoSalida.getValue().getId_movimiento());
            productoSaliDetalle.setTipo_movimientoString(cmbTípoSalida.getValue().getTipo_movimiento());
        }
    }

    @FXML
    private void accionAgregarTabla(ActionEvent event) {

        String textoCantidad = txfCantidad.getText();

        try {
            double cantidadtxf = Double.parseDouble(textoCantidad);
            
            if(cantidad >= cantidadtxf){
                productoSaliDetalle.setMovimineto(cantidadtxf);
                 llenartabla();
                txfInsumo.setDisable(false);
                txfCantidad.setDisable(true);
                cmbLote.setDisable(true);
                cmbTípoSalida.setDisable(true);
                btnAgregarTabla.setDisable(true);
                limpiarcampos();
                
            }else{
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("LA CANTIDAD NO ES ACEPTABLE");
            alertaError.setContentText("LA CANTIDAD QUE INGRESO ES MAYOR A LA QUE HAY EN INVENTARIO");
            alertaError.showAndWait();
            }
            
        } catch (NumberFormatException e) {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("LA CANTIDAD NO ES UN VALOR ACEPTABLE");
            alertaError.showAndWait();
        }
        
    }


    @FXML
    private void accionGenerar(ActionEvent event) throws SQLException {
       int mov_p = CrearMovimientoP();
       checarConexion();
       inventarioDetalleDAO = new InventarioDetalleDAO(con);
       
       
       
         // Crear una tarea para el proceso largo
        Task<Void> tarea = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Realizar el proceso en un bucle
                for (int i = 0; i < listaRetirarOB.size(); i++) {
                    // Actualizar la ventana de alerta
                    final int procesoActual = i+1;
                    Platform.runLater(() -> {
                        alertaInfo.setContentText("proceso " + procesoActual + " de " + listaRetirarOB.size());
                    });

                    // Realizar la operación en la base de datos
                    
                listaRetirarOB.get(i).setId_insumo_mov_padre(mov_p);
                inventarioDetalleDAO.RetirarInsumosInventario(listaRetirarOB.get(i));
                
                
                }
                return null;
            }
        };

        // Manejar el evento cuando la tarea se completa
        tarea.setOnSucceeded(e -> {
            // Cerrar la ventana de alerta
            alertaInfo.close();

            // Mostrar una nueva ventana de alerta para indicar que el proceso ha terminado
            Stage stage = (Stage) btnGenerar.getScene().getWindow();
            stage.close();
            alertaConfirmacion.setTitle("INSUMOS RETIRADOS");
            alertaConfirmacion.setHeaderText("EL PROCESO TERMINO CORRECTAMENTE");
            alertaConfirmacion.setContentText("COMPRA REALIZADO CON EXITO");
            alertaConfirmacion.showAndWait();
        });

        // Ejecutar la tarea en un hilo separado
        Thread thread = new Thread(tarea);
        thread.start();

        // Mostrar la ventana de alerta inicial
        alertaInfo.setTitle("RETIRANDO INSUMOS");
        alertaInfo.setHeaderText("ESTA VENTANA SE CERRARA SOLA");
        alertaInfo.show();
       
       
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

}
