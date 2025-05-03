/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.GenerarReabastos;
import clases_hospital.Insumo;
import clases_hospital.PedidosReabastosProveedor;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class VisualizarPedidoReabastosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<PedidosReabastosProveedor> pedidosReabastosProveedores = FXCollections.observableArrayList();
    ObservableList<GenerarReabastos> generarReabastos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection convp = conexion.conectar2();
    Connection con = conexion.conectar2();
    private int id_provedor;
    private String calve_producto;
    private String listaid;
    private String listaCosto = "";
    private double costo_inventario;
    private double sum_costo_proveedor;
    private int id_pedido;
    private boolean primeravez = true;
    ArrayList<String> cosots_provedorLista = new ArrayList<>();

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnCancelarPedido;
    @FXML
    private Button btnCovertirAPedido;
    @FXML
    private Label lblSubtotal;
    @FXML
    private Label lblIva;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private Label lblSBT;
    @FXML
    private Label lblivastatic;
    @FXML
    private Label lbltp;
    @FXML
    private TableView<PedidosReabastosProveedor> tabla;
    @FXML
    private TableColumn colClave;
    @FXML
    private TableColumn colImporteInventario;
    @FXML
    private TableColumn<PedidosReabastosProveedor, Double> colImporteProveedor;
    @FXML
    private TextField txfProveedor;
    @FXML
    private Label lblClavePEdido;
    @FXML
    private TableView<GenerarReabastos> tablaInsumo;
    @FXML
    private TableColumn colInsumo;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colCstoInven;
    @FXML
    private TableColumn<GenerarReabastos, Double> colCostoProveedor;
    @FXML
    private Label lblEstatusPedido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabla.setEditable(true);
        tablaInsumo.setEditable(true);
        llenarBuscadorProveedor();
        visibles(false);
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionCancelarPedido(ActionEvent event) throws SQLException {
        String sql = "{call atctualizarestatuspedidos_reabastos_proveedor (?,?,?)}";
        PreparedStatement stmt = con.prepareCall(sql);
        stmt.setInt(1, pedidosReabastosProveedores.get(0).getId_pedidos_reabastos_proveedor());
        stmt.setInt(2, 1);
        stmt.setInt(3, VPMedicaPlaza.userSystem);
        stmt.execute();
    }

    @FXML
    private void accionConvertirAPedido(ActionEvent event) throws SQLException {
        String sql = "{call atctualizarestatuspedidos_reabastos_proveedor (?,?,?)}";
        PreparedStatement stmt = con.prepareCall(sql);
        stmt.setInt(1, pedidosReabastosProveedores.get(0).getId_pedidos_reabastos_proveedor());
        stmt.setInt(2, 2);
        stmt.setInt(3, VPMedicaPlaza.userSystem);
        stmt.execute();
        Stage stage = (Stage) btnCovertirAPedido.getScene().getWindow();
        stage.close();
    }

    private void llenarBuscadorProveedor() {
        ArrayList<Proveedor> arrayProveedor = new ArrayList<>();
        try {
            convp = conexion.conectar2();

            Statement stmt = convp.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombreComercial FROM proveedores");
            while (rs.next()) {
                arrayProveedor.add(new Proveedor(rs.getInt(1), rs.getString(2)));
            }
            TextFields.bindAutoCompletion(txfProveedor, arrayProveedor);
            AutoCompletionBinding<Proveedor> proveedores = TextFields.bindAutoCompletion(txfProveedor, arrayProveedor);

            proveedores.setOnAutoCompleted(event -> {
                Proveedor selectProveedor = event.getCompletion();
                id_provedor = selectProveedor.getId();
                selectPedidos_Reabastos_Proveedor();
            });

            convp.close();
        } catch (SQLException e) {
        }
    }

    public void calvePedido(String clave) {
        this.calve_producto = clave;
        lblClavePEdido.setText(clave);
    }

    public void listaInsumos(String listaId, String listaidParcial) {
        this.listaid = listaId + "," + listaidParcial;
        listaCosto = "0";
    }

    private void llenarTabla() {
        this.tabla.getItems().clear();
        this.generarReabastos.clear();
        DecimalFormat df = new DecimalFormat("0.00");
        String sql = "SELECT * FROM pedidos_reabastos_proveedor WHERE clave_pedido = ? AND id_proveedor = ? AND id_estatus = 1";
        PedidosReabastosProveedor pedidosreabastosproveedor;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, calve_producto);
            stmt.setInt(2, id_provedor);
           
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pedidosreabastosproveedor = new PedidosReabastosProveedor();
                pedidosreabastosproveedor.setId_pedidos_reabastos_proveedor(rs.getInt(1));
                id_pedido = rs.getInt(1);
                pedidosreabastosproveedor.setClave_pedido(rs.getString(2));
                pedidosreabastosproveedor.setId_proveedor(rs.getInt(3));
                pedidosreabastosproveedor.setLista_profucto_pedir(rs.getString(4));
                pedidosreabastosproveedor.setImporte_inventario(Double.parseDouble(df.format(rs.getDouble(5))));
                costo_inventario = Double.parseDouble(df.format(rs.getDouble(5)));
                if (rs.getDouble(6) != 0) {
                    pedidosreabastosproveedor.setImporte_proveedor(rs.getDouble(6));
                }
                pedidosreabastosproveedor.setImporte_proveedor(rs.getDouble(6));
                pedidosreabastosproveedor.setId_estatus(rs.getInt(9));
                pedidosreabastosproveedor.setLista_costos(rs.getString(11));
                if (rs.getString(11) == null) {
                    listaCosto = "";
                } else {
                    listaCosto = rs.getString(11);
                }

                pedidosReabastosProveedores.add(pedidosreabastosproveedor);
            }

            colClave.setCellValueFactory(new PropertyValueFactory("clave_pedido"));
            colImporteInventario.setCellValueFactory(new PropertyValueFactory("importe_inventario"));
            colImporteProveedor.setCellValueFactory(new PropertyValueFactory("importe_proveedor"));

            colImporteProveedor.setCellValueFactory(new PropertyValueFactory("importe_proveedor"));
            colImporteProveedor.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colImporteProveedor.setOnEditCommit(event -> {
                PedidosReabastosProveedor pedprovreab = event.getRowValue();
                pedprovreab.setImporte_proveedor(event.getNewValue());
                costo_inventario = pedprovreab.getImporte_proveedor();

                actualizarImporte(pedprovreab.getId_pedidos_reabastos_proveedor(), pedprovreab.getImporte_proveedor());

                calcularLBLTotales();

                tabla.refresh();
            });

            colImporteProveedor.setEditable(true);
            tabla.setItems(pedidosReabastosProveedores);

            visibles(true);

        } catch (SQLException ex) {
            Logger.getLogger(VisualizarPedidoReabastosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenarTablaInsumo() {
        this.tablaInsumo.getItems().clear();
        this.generarReabastos.clear();

        //OJO AQUI POR MOTIVO DE NO GENERAR UNA TABLA LA CUAL SERIA ESPACIO EXTRA DESPERDICIADO EN LA BASE DE DATOS, SE TUVO QUE AGREGAR EL COSOTO PROVEEDOR
        //EN LA TABLA 'pedidos_reabastos_proveedor'.
        String sql = "SELECT g.id, g.id_insumo, i.nombre, i.marca, i.presentacion, i.medida, g.paquetes, g.cajas_pedir, g.unidades_pedir, g.costo_paquete_pedir, g.id_estatus, g.pedir, i.cantidad_caja, i.cantidad_unidad, i.costo FROM generarreabastos g INNER JOIN insumos i ON g.id_insumo = i.id WHERE g.id = ? AND g.id_estatus = 2;";
        PreparedStatement stmt = null;
        ResultSet rs;
        GenerarReabastos generarReabasto;
        String[] arreglolista = listaid.split(",");
        for (String id : arreglolista) {
            try {
              

                stmt = con.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(id));
                rs = stmt.executeQuery();

                if (rs.next()) {
                    generarReabasto = new GenerarReabastos();
                    generarReabasto.setId_reabastos(rs.getInt(1));
                    generarReabasto.setId_reabastos(rs.getInt(1));
                    generarReabasto.setId_insumo(rs.getInt(2));
                    generarReabasto.setNombre(rs.getString(3));
                    generarReabasto.setMarca(rs.getString(4));
                    generarReabasto.setPresentacion(rs.getString(5));
                    generarReabasto.setMedida(rs.getString(6));
                    generarReabasto.setPaquetes(rs.getInt(7));
                    generarReabasto.setCajas(rs.getInt(8));
                    generarReabasto.setFalta(rs.getInt(9));
                    generarReabasto.setCosto(rs.getDouble(10));
                    generarReabasto.setId_estatus_generar_reporte(rs.getInt(11));
                    generarReabasto.setGenerar(rs.getBoolean(12));
                    generarReabasto.setCantidad_cajas_unidad_paquete(rs.getInt(13));
                    generarReabasto.setCantidad_unidadades_paquete(rs.getInt(14));
                    generarReabasto.setCosto_unidad_paquete(rs.getDouble(15));
                    if (listaCosto.equals("")) {
                        generarReabasto.setCosto_provedor(0.00);
                        primeravez = false;
                    } else {
                        String[] splitComa = listaCosto.split(",");
                        for (String primeradivicion : splitComa) {
                            String[] splitDosPuntos = primeradivicion.split(":");
                            if (Integer.parseInt(splitDosPuntos[0]) == rs.getInt(1)) {
                                generarReabasto.setCosto_provedor(Double.parseDouble(splitDosPuntos[1]));
                            }
                        }
                    }
                    generarReabastos.add(generarReabasto);
                }

            } catch (SQLException ex) {
                Logger.getLogger(VisualizarPedidoReabastosController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("paquetes"));
        colCstoInven.setCellValueFactory(new PropertyValueFactory("costo"));
        colCostoProveedor.setCellValueFactory(new PropertyValueFactory("costo_provedor"));

        colCostoProveedor.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCostoProveedor.setOnEditCommit(event -> {

            GenerarReabastos genreab = event.getRowValue();
            genreab.setCosto_provedor(event.getNewValue());
            boolean noexiste = true;
            if (cosots_provedorLista.isEmpty()) {
                cosots_provedorLista.add(genreab.getId_reabastos() + ":" + genreab.getCosto_provedor());
            } else {
                //Agregar lo que falata aqui
                for (int i = 0; i < cosots_provedorLista.size(); i++) {
                    String[] split = cosots_provedorLista.get(i).split(":");
                    if (Integer.parseInt(split[0]) == genreab.getId_reabastos()) {
                        cosots_provedorLista.set(i, genreab.getId_reabastos() + ":" + genreab.getCosto_provedor());
                        noexiste = false;
                    }
                }
                if (noexiste) {
                    cosots_provedorLista.add(genreab.getId_reabastos() + ":" + genreab.getCosto_provedor());
                }
            }
            listaCosto = "";
            for (int i = 0; i < cosots_provedorLista.size(); i++) {
                listaCosto = listaCosto + cosots_provedorLista.get(i) + ",";
            }

            actualizarListaCosotosPaquetes(listaCosto);

            sum_costo_proveedor = 0.0;
            tablaInsumo.getItems().forEach((item) -> {
                sum_costo_proveedor += (item.getCosto_provedor() * item.getPaquetes());
            });
            pedidosReabastosProveedores.get(0).setCosto_proveedor(sum_costo_proveedor);

            actualizarImporte(pedidosReabastosProveedores.get(0).getId_pedidos_reabastos_proveedor(), sum_costo_proveedor);//

            llenarTablaInsumo();
        });

        colCostoProveedor.setEditable(true);
        tablaInsumo.setItems(generarReabastos);

    }

    private void calcularLBLTotales() {
        DecimalFormat df = new DecimalFormat("0.00");

        double calculoIVA = costo_inventario - (costo_inventario / 1.16);
        double subtotal = costo_inventario - calculoIVA;

        lblSubtotal.setText(df.format(subtotal));
        lblIva.setText(df.format(calculoIVA));
        lblTotalPagar.setText("" + costo_inventario);
    }

    private void selectPedidos_Reabastos_Proveedor() {
        String sql = "SELECT * FROM pedidos_reabastos_proveedor WHERE clave_pedido = ? AND id_proveedor = ? AND id_estatus = 1";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, calve_producto);
            stmt.setInt(2, id_provedor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                llenarTabla();
                llenarTablaInsumo();
            } else {
                insertarTabla();
                llenarTabla();
                llenarTablaInsumo();
            }
            calcularLBLTotales();
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarPedidoReabastosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void insertarTabla() {
        try {
            costoInventario();
            PreparedStatement stmt = con.prepareCall("{call ingresarpedidos_reabastos_proveedor(?,?,?,?,?,?)}");
            stmt.setString(1, calve_producto);
            stmt.setInt(2, id_provedor);
            stmt.setString(3, listaid);
            stmt.setDouble(4, costo_inventario);
            stmt.setDouble(5, 0.00);
            stmt.setInt(6, VPMedicaPlaza.userSystem);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarPedidoReabastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void costoInventario() {
        try {
            String sql = "SELECT SUM(g.costo_paquete_pedir) AS costo FROM generarreabastos g WHERE g.clave_pedido = ?;";
            PreparedStatement stmt = con.prepareCall(sql);
            stmt.setString(1, calve_producto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                costo_inventario = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarPedidoReabastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarImporte(int id, double importe) {
        try {
            PreparedStatement stmt = con.prepareCall("{call actualizarprecioproveedor(?,?,?)}");
            stmt.setInt(1, id);
            stmt.setDouble(2, importe);
            stmt.setInt(3, VPMedicaPlaza.userSystem);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarPedidoReabastosController.class.getName()).log(Level.SEVERE, null, ex);
        }

        llenarTabla();
    }

    private void actualizarListaCosotosPaquetes(String lista) {
        String sql = "{call actualizarListaCosotosPaquetes(?,?,?)}";
        try {
            PreparedStatement stmt = con.prepareCall(sql);
            stmt.setInt(1, pedidosReabastosProveedores.get(0).getId_pedidos_reabastos_proveedor());
            stmt.setString(2, lista);
            stmt.setInt(3, VPMedicaPlaza.userSystem);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarPedidoReabastosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void visibles(boolean bl) {
        lblSubtotal.setVisible(bl);
        lblIva.setVisible(bl);
        lblTotalPagar.setVisible(bl);
        lblSBT.setVisible(bl);
        lblivastatic.setVisible(bl);
        lbltp.setVisible(bl);
    }

}
