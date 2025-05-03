/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.*;
import clases_hospital_DAO.CompraInsumoPDAO;
import clases_hospital_DAO.ComprasInsumosDetalleDAO;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventarioDetalleDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.ProveedorDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class PedidosComprasController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;

    ComprasInsumosDetalleDAO compraInsDetDAO;
    CompraInsumoPDAO comprasInsumosPdao;
    InsumosDAO insumosDAO;
    ProveedorDAO proveedorDAO;

    ComprasInsumosP compraP;

    Insumo producto;

    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    private final ArrayList<RadioButton> radioButtons = new ArrayList<>();

    ObservableList<Insumo> OBInsumsPedido = FXCollections.observableArrayList();
    ObservableList<ComprasInsumosP> OBcomInsumosPs = FXCollections.observableArrayList();
    ObservableList<Insumo> OBProductos = FXCollections.observableArrayList();
    ObservableList<Proveedor> OBProveedors = FXCollections.observableArrayList();

    int id_compraP = 0;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblDescuento;
    @FXML
    private Label lblSubTotal;
    @FXML
    private Label lblIVA;
    @FXML
    private TextField txfComision;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblTotalComision;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private TableColumn<?, ?> colCodigo;
    @FXML
    private TableColumn<?, ?> colProducto;
    @FXML
    private TableColumn<Insumo, Double> colCantidad;
    @FXML
    private TableColumn<Insumo, Double> colPrecioUnitario;
    @FXML
    private TableColumn<Insumo, Double> colDescuento;
    @FXML
    private TableColumn<?, ?> colImporte;
    @FXML
    private TextField txfProducto;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txfRazonSocial;
    @FXML
    private TextField txfFolioCompra;

    private int id_porveedor;
    @FXML
    private TableColumn<Insumo, Double> colCantidadxCaja;
    @FXML
    private TableColumn<Insumo, Double> colPrecioxCaja;
    @FXML
    private TableColumn<?, ?> colCantidaduni;
    @FXML
    private TableColumn<Insumo, String> coliva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = conexion.conectar2();
        insumosDAO = new InsumosDAO(con);
        compraInsDetDAO = new ComprasInsumosDetalleDAO(con);
        proveedorDAO = new ProveedorDAO(con);

        lambda();
        tabla.setEditable(true);

        //      OBProductos.addAll(insumosDAO.InsumosConPrecios());
        try {
            OBProductos.addAll(insumosDAO.InsumosConPrecios());
            OBProveedors.addAll(proveedorDAO.obtenerTodos());
            llenarTxfInsumo();
        } catch (SQLException ex) {
            Logger.getLogger(PedidosComprasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setObjeto(int id_CompraInsumonP, String nombreProeedor) {
      System.out.println("entra a la vista");
    id_compraP = id_CompraInsumonP;     
    txfRazonSocial.setText(nombreProeedor);
    OBInsumsPedido.addAll(compraInsDetDAO.traerInsumosCompra(id_compraP));
    llenartabla();
    txfRazonSocial.setDisable(true);
    //txfProducto.setVisible(false);
   // btnAgregar.setVisible(false);
    

    }

    public void llenartabla() {
        //      tabla.getItems().clear();
        colCodigo.setCellValueFactory(new PropertyValueFactory("id"));
        colProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colCantidadxCaja.setCellValueFactory(new PropertyValueFactory("cantidad_unitariaxcaja"));
        colPrecioxCaja.setCellValueFactory(new PropertyValueFactory("costo_compra_caja"));
        colCantidaduni.setCellValueFactory(new PropertyValueFactory("cantidadd_unitarias"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("costo_compra_unitaria"));
        colDescuento.setCellValueFactory(new PropertyValueFactory("descuento"));
        colImporte.setCellValueFactory(new PropertyValueFactory("importeFormato"));
        editartabla();
        Radiobutton();
        tabla.setItems(OBInsumsPedido);
        actualizarCuenta();
    }

    private void editartabla() {

        DecimalFormat df = new DecimalFormat("0.00");

        // Habilitar la edición en la columna "Cantidad"
        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrecioxCaja.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colDescuento.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCantidadxCaja.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        // CANTIDAD
        colCantidad.setOnEditCommit(event -> {
            // Obtener el valor editado
            Double nuevoValor = event.getNewValue();
            //selecciona la linea 
            int indiceFila = event.getTablePosition().getRow();
            // actualiza los valores en esa linea
            double nuevoImporte, descuento;
            nuevoImporte = nuevoValor * OBInsumsPedido.get(indiceFila).getCosto_compra_caja();
            descuento = OBInsumsPedido.get(indiceFila).getDescuento();

            if (OBInsumsPedido.get(indiceFila).getIva() != 0.0) {
                nuevoImporte = nuevoImporte - descuento;
                OBInsumsPedido.get(indiceFila).setIva(Agregariva(nuevoImporte));
                nuevoImporte += Agregariva(nuevoImporte);
            } else {
                nuevoImporte = nuevoImporte - descuento;
            }

            // Actualizar el valor en la ObservableList
            OBInsumsPedido.get(indiceFila).setCantidad(nuevoValor);
            OBInsumsPedido.get(indiceFila).setImporte(nuevoImporte);
            OBInsumsPedido.get(indiceFila).setCantidadd_unitarias(nuevoValor * OBInsumsPedido.get(indiceFila).getCantidad_unitariaxcaja());

            // Recalcular el importe (si es necesario)
            // OBInsumsPedido.get(indiceFila).setImporte(nuevoValor * OBInsumsPedido.get(indiceFila).getPrecioUnitario());
            // Actualizar la tabla
            actualizarCuenta();
            tabla.refresh();
        });
        // CANTIDAD x caja
        colCantidadxCaja.setOnEditCommit(event -> {
            // Obtener el valor editado
            Double nuevoValor = event.getNewValue();
            //selecciona la linea 
            int indiceFila = event.getTablePosition().getRow();
            // actualiza los valores en esa linea
            double nuevoImporte, descuento, costounitario;
            nuevoImporte = OBInsumsPedido.get(indiceFila).getCantidad() * OBInsumsPedido.get(indiceFila).getCosto_compra_caja();
            descuento = OBInsumsPedido.get(indiceFila).getDescuento();

            if (OBInsumsPedido.get(indiceFila).getIva() != 0.0) {
                nuevoImporte = nuevoImporte - descuento;
                OBInsumsPedido.get(indiceFila).setIva(Agregariva(nuevoImporte));
                nuevoImporte += Agregariva(nuevoImporte);
            } else {
                nuevoImporte = nuevoImporte - descuento;
            }
            costounitario = OBInsumsPedido.get(indiceFila).getCosto_compra_caja() / nuevoValor;

            // Actualizar el valor en la ObservableList
            OBInsumsPedido.get(indiceFila).setCantidad_unitariaxcaja(nuevoValor);
            OBInsumsPedido.get(indiceFila).setImporte(nuevoImporte);
            OBInsumsPedido.get(indiceFila).setCantidadd_unitarias(nuevoValor * OBInsumsPedido.get(indiceFila).getCantidad());
            OBInsumsPedido.get(indiceFila).setCosto_compra_unitaria(costounitario);
            

            // Recalcular el importe (si es necesario)
            // OBInsumsPedido.get(indiceFila).setImporte(nuevoValor * OBInsumsPedido.get(indiceFila).getPrecioUnitario());
            // Actualizar la tabla
            actualizarCuenta();
            tabla.refresh();
        });

        // PRECIO UNITARIO
        colPrecioxCaja.setOnEditCommit(event -> {
            // Obtener el valor editado
            Double nuevoValor = event.getNewValue();
            //selecciona la linea 
            int indiceFila = event.getTablePosition().getRow();
            // actualiza los valores en esa linea
            double nuevoImporte, descuento, costounitario;
            nuevoImporte = nuevoValor * OBInsumsPedido.get(indiceFila).getCantidad();
            descuento = OBInsumsPedido.get(indiceFila).getDescuento();
            if (OBInsumsPedido.get(indiceFila).getIva() != 0.0) {
                nuevoImporte = nuevoImporte - descuento;
                OBInsumsPedido.get(indiceFila).setIva(Agregariva(nuevoImporte));
                nuevoImporte += Agregariva(nuevoImporte);
            } else {
                nuevoImporte = nuevoImporte - descuento;
            }
            costounitario = nuevoValor / OBInsumsPedido.get(indiceFila).getCantidad_unitariaxcaja();

            // Actualizar el valor en la ObservableList
            OBInsumsPedido.get(indiceFila).setCosto_compra_caja(nuevoValor);
            OBInsumsPedido.get(indiceFila).setImporte(nuevoImporte);
            OBInsumsPedido.get(indiceFila).setCosto_compra_unitaria(costounitario);

            // Recalcular el importe (si es necesario)
            // OBInsumsPedido.get(indiceFila).setImporte(nuevoValor * OBInsumsPedido.get(indiceFila).getPrecioUnitario());
//         Actualizar la tabla
            actualizarCuenta();
            tabla.refresh();
        });

        // DESCUENTO
        colDescuento.setOnEditCommit(event -> {
            // Obtener el valor editado
            Double nuevoValor = event.getNewValue();
            //selecciona la linea 
            int indiceFila = event.getTablePosition().getRow();
            // actualiza los valores en esa linea
            double nuevoImporte, cantidad;
            cantidad = OBInsumsPedido.get(indiceFila).getCantidad();
            nuevoImporte = cantidad * OBInsumsPedido.get(indiceFila).getCosto_compra_caja();

            if (OBInsumsPedido.get(indiceFila).getIva() != 0.0) {
                nuevoImporte = nuevoImporte - nuevoValor;
                OBInsumsPedido.get(indiceFila).setIva(Agregariva(nuevoImporte));
                nuevoImporte += Agregariva(nuevoImporte);
            } else {
                nuevoImporte = nuevoImporte - nuevoValor;
            }

            // Actualizar el valor en la ObservableList
            OBInsumsPedido.get(indiceFila).setDescuento(nuevoValor);
            OBInsumsPedido.get(indiceFila).setImporte(nuevoImporte);

            // Recalcular el importe (si es necesario)
            // OBInsumsPedido.get(indiceFila).setImporte(nuevoValor * OBInsumsPedido.get(indiceFila).getPrecioUnitario());
            // Actualizar la tabla
            actualizarCuenta();
            tabla.refresh();
        });

        // Permitir la edición de la columnas 
        colCantidadxCaja.setEditable(true);
        colCantidad.setEditable(true);
        colPrecioUnitario.setEditable(true);
        colDescuento.setEditable(true);
    }

    private void Radiobutton() {
        Callback<TableColumn<Insumo, String>, TableCell<Insumo, String>> confirmar = (TableColumn<Insumo, String> param) -> {
            final TableCell<Insumo, String> cell = new TableCell<Insumo, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton AgregarIva = new RadioButton();
                        Insumo Pedido = getTableView().getItems().get(getIndex());
                        if (Pedido.getIva() != 0.0) {

                            AgregarIva.setSelected(true);
                        } else {
                            AgregarIva.setSelected(false);
                        }
                        AgregarIva.setOnAction(event -> {
                            //Insumo Pedido = getTableView().getItems().get(getIndex());
                            int indiceFila = getTableRow().getIndex();
                            if (AgregarIva.isSelected()) {

                                double con_iva = Pedido.getImporte() * 1.16;
                                double iva = con_iva - Pedido.getImporte();
                              //  System.out.println("" + con_iva + "     " + Pedido.getImporte());
                                OBInsumsPedido.get(indiceFila).setIva(iva);
                                OBInsumsPedido.get(indiceFila).setImporte(con_iva);
                                //System.out.println("seleccionado" + OBInsumsPedido.get(indiceFila).getIva());
                                actualizarCuenta();

                            } else {

                                double sin_iva = Pedido.getImporte() / 1.16;
                                double iva = Pedido.getImporte() - sin_iva;
                                OBInsumsPedido.get(indiceFila).setIva(0.0);
                                OBInsumsPedido.get(indiceFila).setImporte(sin_iva);
                                // System.out.println("seleccionado" + OBInsumsPedido.get(indiceFila).getIva());
                                actualizarCuenta();
                            }
                            actualizarCuenta();
                            tabla.refresh();

                        });
                        setGraphic(AgregarIva);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        coliva.setCellFactory(confirmar);
    }

    private double Agregariva(double importe) {
        double con_iva = importe * 1.16;
        double iva = con_iva - importe;

        return iva;
    }

    private void actualizarCuenta() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        double total = 0, descuento = 0, iva = 0, comision = 0;
        String comisionString = txfComision.getText();
        String comisionSinComas = comisionString.replace("$", "").replaceAll(",", "");

        comision = Double.parseDouble(comisionSinComas);

        for (int i = 0; i < OBInsumsPedido.size(); i++) {
            total += OBInsumsPedido.get(i).getImporte();
            iva += OBInsumsPedido.get(i).getIva();
            descuento += OBInsumsPedido.get(i).getDescuento();

        }
        comisionString = df.format(comision);
        // iva = total - ( total / 1.16);
        double subtotal = total - iva ;
        double totalpagarsC = total - comision;
        String totalsString = df.format(total);
        String descuentoString = df.format(descuento);
        String ivaString = df.format(iva);
        String SunTotalString = df.format(subtotal);
        String totalPagarScomision = df.format(totalpagarsC);

        iva = total - (total / 1.16);
        lblDescuento.setText("$ " + descuentoString);
        lblSubTotal.setText("$ " + SunTotalString);
        lblIVA.setText("$" + ivaString);
        lblTotal.setText("$ " + totalsString);
        txfComision.setText("$ " + comisionString);
        lblTotalPagar.setText("$ " + totalsString);
        lblTotalComision.setText("$" + totalPagarScomision);

    }

    private void llenarComprasInsumosP() {
        compraP = new ComprasInsumosP();

        String subtotalsString = lblSubTotal.getText().replace("$", "").replaceAll(",", "");
        String descuentosString = lblDescuento.getText().replace("$", "").replaceAll(",", "");
        String ivaString = lblIVA.getText().replace("$", "").replaceAll(",", "");
        String totalString = lblTotal.getText().replace("$", "").replaceAll(",", "");
        String comisionString = txfComision.getText().replace("$", "").replaceAll(",", "");
        String totalpagarString = lblTotalPagar.getText().replace("$", "").replaceAll(",", "");
        String totalpagarScomisionString = lblTotalComision.getText().replace("$", "").replaceAll(",", "");

        compraP.setId_compra_insumosp(id_compraP);
        compraP.setId_proveedor(id_porveedor);
        compraP.setFolioCompra(txfFolioCompra.getText());
        compraP.setSubtotal(Double.parseDouble(subtotalsString));
        compraP.setDescuento(Double.parseDouble(descuentosString));
        compraP.setIvaDouble(Double.parseDouble(ivaString));
        compraP.setTotalCompra(Double.parseDouble(totalpagarString));
        compraP.setTotalSinComision(Double.parseDouble(totalpagarScomisionString));
        compraP.setTotalSinComision(Double.parseDouble(totalpagarString));
        compraP.setImporteComision(Double.parseDouble(comisionString));

    }

    private void lambda() {
        tabla.setRowFactory(tableView -> {
            TableRow<Insumo> row = new TableRow<>();
            ContextMenu cxmConfiguracion = new ContextMenu();
            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
            descartarConsumo.setOnAction(event -> {
                //con = conexion.conectar2();
                Insumo insumo = row.getItem();
                OBInsumsPedido.remove(insumo);
                //actualizarCuenta();

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

    private void llenarTxfInsumo() throws SQLException {
        producto = new Insumo();

        AutoCompletionBinding<Insumo> insumos = TextFields.bindAutoCompletion(txfProducto, OBProductos);
        insumos.setPrefWidth(txfProducto.getPrefWidth());
        insumos.setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<Insumo> e) -> {

            producto = e.getCompletion();
            producto.setCantidad(1.0);
            producto.setCantidadd_unitarias(producto.getCantidad_unitariaxcaja());
            producto.setDescuento(0.0);
            producto.setImporte(producto.getCosto_compra_caja());
            producto.setIva(0.0);
            producto.setId_comprainsdet(0);

        });

        AutoCompletionBinding<Proveedor> proveedor = TextFields.bindAutoCompletion(txfRazonSocial, OBProveedors);
        proveedor.setPrefWidth(txfRazonSocial.getPrefWidth());
        proveedor.setOnAutoCompleted(eX -> {
            id_porveedor = eX.getCompletion().getId();
            txfRazonSocial.setDisable(true);

        });
    }

    @FXML
    private void accionGenerar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        ReporteC reporte = new ReporteC("ReporteComprasInsumosDetalles");
        comprasInsumosPdao = new CompraInsumoPDAO(con);
        compraInsDetDAO = new ComprasInsumosDetalleDAO(con);

        llenarComprasInsumosP();
        id_compraP = comprasInsumosPdao.ActualizarEstatusComprasInsumosP(compraP);

        // Crear una tarea para el proceso largo
        Task<Void> tarea = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Realizar el proceso en un bucle
                for (int i = 0; i < OBInsumsPedido.size(); i++) {
                    // Actualizar la ventana de alerta
                    final int procesoActual = i;
                    Platform.runLater(() -> {
                        alertaInfo.setContentText("proceso " + procesoActual + " de " + OBInsumsPedido.size());
                    });

                    // Realizar la operación en la base de datos
                OBInsumsPedido.get(i).setId_comprainsumoP(id_compraP);
                compraInsDetDAO.actualizaroIngresarCompraInsumoDET(OBInsumsPedido.get(i));
                
                }
                return null;
            }
        };

        // Manejar el evento cuando la tarea se completa
        tarea.setOnSucceeded(e -> {
            // Cerrar la ventana de alerta
            alertaInfo.close();
            reporte.ReporteComprasInsumosDetalles("" + id_compraP);

            // Mostrar una nueva ventana de alerta para indicar que el proceso ha terminado
            Stage stage = (Stage) btnGenerar.getScene().getWindow();
            stage.close();
            alertaConfirmacion.setTitle("COMPRA REALIZADA");
            alertaConfirmacion.setHeaderText("EL PROCESO TERMINO CORRECTAMENTE");
            alertaConfirmacion.setContentText("COMPRA REALIZADO CON EXITO");
            alertaConfirmacion.showAndWait();
        });

        // Ejecutar la tarea en un hilo separado
        Thread thread = new Thread(tarea);
        thread.start();

        // Mostrar la ventana de alerta inicial
        alertaInfo.setTitle("EN PROCESO");
        alertaInfo.setHeaderText("ESTA VENTANA SE CERRARA SOLA");
        alertaInfo.show();
    }

    @FXML
    private void accionSallir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionComision(ActionEvent event) {
        actualizarCuenta();
    }

    @FXML
    private void accionAgregar(ActionEvent event) throws SQLException {
        if (producto.getId() != 0) {
            OBInsumsPedido.add(producto);
            llenartabla();
            txfProducto.clear();
            producto = new Insumo();
           // llenarTxfInsumo();
        }
    }

}
