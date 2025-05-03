/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Movimiento;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import javafx.util.converter.IntegerStringConverter;
//import reportes.ReporteEntradasInsumos;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class MovimientosInventarioController implements Initializable {
    
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Movimiento> movimientos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Connection conProveedor = conexion.conectar2();
    private int id_proveedor;

    @FXML
    private Label lblUltimoCambio;
    @FXML
    private TableView<Movimiento> tabla;
    @FXML
    private TableColumn colInsumo;
    @FXML
    private TableColumn colProveedor;
    @FXML
    private TableColumn<Movimiento, String> colLote;
    @FXML
    private TableColumn<Movimiento, Integer> colPaquete;
    @FXML
    private TableColumn colInventarioInicial;
    @FXML
    private TableColumn colEntrada;
    @FXML
    private TableColumn<Movimiento, Integer>  conInventarioFinal;
    @FXML
    private TableColumn<Movimiento, String> colCaducidad;
    @FXML
    private TableColumn<Movimiento, Double> colImporte;
    @FXML
    private TextField txfBuscarOrden;
    @FXML
    private Button btnBuscarOrden;
    @FXML
    private Label lblTipoMovimiento;
    @FXML
    private Button btnImprimir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void accionBuscarOrden(ActionEvent event) throws SQLException {
        if(txfBuscarOrden.getText().equals("")){
            System.err.println("RECUERDA AGREGAR EL PP REY P");
        }else{
            llenarTabla(txfBuscarOrden.getText()); 
        }
    }
    

    @FXML
    private void accionImprimir(ActionEvent event) {
//        ReporteEntradasInsumos rei = new ReporteEntradasInsumos("valeIngreso");
//        rei.generarReporte(txfBuscarOrden.getText(), datosProveedor(), sumatoriaEntrada());
    }
    
    private void llenarTabla(String ordenCompra) throws SQLException{
        String sql = "SELECT e.*, p.nombreComercial, i.nombre FROM entradas e INNER JOIN insumos i ON e.id_insumo = i.id JOIN ghregioc_vpmedica.proveedores p ON e.id_proveedor = p.id WHERE e.factura = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, ordenCompra);
        ResultSet rs = ps.executeQuery();
        Movimiento movimiento;
        while(rs.next()){
            movimiento = new Movimiento();
            movimiento.setId(rs.getInt(1));
            movimiento.setId_insumo(rs.getInt(2));
            movimiento.setId_proveedor(rs.getInt(3));
            id_proveedor = rs.getInt(3);
            movimiento.setCosto(rs.getInt(4));
            movimiento.setFactura(rs.getString(5));
            movimiento.setLote(rs.getString(6));
            movimiento.setFecha_movimiento(rs.getDate(7));
            movimiento.setCantidad_paquete(rs.getInt(8));
            movimiento.setCantidad_caja(rs.getInt(9));
            movimiento.setCantidad_unidad(rs.getInt(10));
            movimiento.setInventario_inicial(rs.getInt(11));
            movimiento.setMovimiento(rs.getInt(12));
            movimiento.setInventario_final(rs.getInt(13));
            movimiento.setCaducidad(rs.getDate(14));
            movimiento.setPrecio_unitario(rs.getDouble(15));
            movimiento.setPorcentaje_utilidad(rs.getDouble(16));
            movimiento.setNombre_proveedor(rs.getString(19));
            movimiento.setNombre_insumo(rs.getString(20));
            movimientos.add(movimiento);
        }
        
        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre_insumo"));
        colProveedor.setCellValueFactory(new PropertyValueFactory("nombre_proveedor"));
 /**
   * ====================================================================================
   *                               COLUMNA EDITABLE LOTE
   * ====================================================================================
   */
        colLote.setCellValueFactory(new PropertyValueFactory("lote"));
        colLote.setCellFactory(TextFieldTableCell.forTableColumn());
            colLote.setOnEditCommit(event -> {
                Movimiento movEdit = event.getTableView().getItems().get(event.getTablePosition().getRow());
                movEdit.setLote(event.getNewValue());
                callActualizarMovimiento(movEdit.getId(), movEdit.getLote(), movEdit.getCantidad_paquete(), movEdit.getInventario_final(), "" + movEdit.getCaducidad(), movEdit.getMovimiento());
                tabla.refresh();
            });
 /**
   * ====================================================================================
   *                               COLUMNA EDITABLE PAQUETE
   * ====================================================================================
   */        
        colPaquete.setCellValueFactory(new PropertyValueFactory("cantidad_paquete"));
        colPaquete.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colPaquete.setOnEditCommit(event -> {
                Movimiento movEdit = event.getTableView().getItems().get(event.getTablePosition().getRow());
                movEdit.setCantidad_paquete(event.getNewValue());
                movEdit.setMovimiento( movEdit.getCantidad_paquete() * movEdit.getCantidad_caja()* movEdit.getCantidad_unidad() );
                movEdit.setInventario_final( movEdit.getInventario_inicial() + movEdit.getMovimiento());
                callActualizarMovimiento(movEdit.getId(), movEdit.getLote(), movEdit.getCantidad_paquete(), movEdit.getInventario_final(), "" + movEdit.getCaducidad(), movEdit.getMovimiento());
                tabla.refresh();
            });
//   * ==================================================================================== 
        colInventarioInicial.setCellValueFactory(new PropertyValueFactory("inventario_inicial"));
        colEntrada.setCellValueFactory(new PropertyValueFactory("movimiento"));
 /**
   * ====================================================================================
   *                           COLUMNA EDITABLE INVENTARIO FINAL
   * ====================================================================================
   */
        conInventarioFinal.setCellValueFactory(new PropertyValueFactory("inventario_final"));
        conInventarioFinal.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            conInventarioFinal.setOnEditCommit(event -> {
                Movimiento movEdit = event.getTableView().getItems().get(event.getTablePosition().getRow());
                movEdit.setInventario_final(event.getNewValue());
                movEdit.setMovimiento(movEdit.getInventario_final() - movEdit.getInventario_inicial());
                callActualizarMovimiento(movEdit.getId(), movEdit.getLote(), movEdit.getCantidad_paquete(), movEdit.getInventario_final(), "" + movEdit.getCaducidad(), movEdit.getMovimiento());
                tabla.refresh();
            });
 /**
   * ====================================================================================
   *                               COLUMNA EDITABLE CADUCIDAD
   * ====================================================================================
   */
        colCaducidad.setCellValueFactory(new PropertyValueFactory("formatterCaducidad"));
        colImporte.setCellValueFactory(new PropertyValueFactory("costo"));
        
        
        
        colLote.setEditable(true);
        colEntrada.setEditable(true);
        conInventarioFinal.setEditable(true);
        colCaducidad.setEditable(true);
        tabla.setEditable(true);
        tabla.setItems(movimientos);
        
    }
    
    private void callActualizarMovimiento(int id, String lote, int paquete, int invenfinal, String caducidad, int entrada) {
        try {
            String sql = "{call actualizarMovimeineto (?,?,?,?,?,?,?)}";
            PreparedStatement ps = con.prepareCall(sql);
            ps.setInt(1, id);
            ps.setString(2, lote);
            ps.setInt(3, paquete);
            ps.setInt(4, entrada);
            ps.setInt(5, invenfinal);
            ps.setString(6, caducidad);
            ps.setInt(7, VPMedicaPlaza.userSystem);
            ps.execute();
            
            LocalTime horaActual = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaFormateada = horaActual.format(formatter);
            lblUltimoCambio.setText("Ultima actualizacion: " + horaFormateada);
            
        } catch (SQLException ex) {
            Logger.getLogger(MovimientosInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private String datosProveedor(){
        String respuesta = "";
        try {
            PreparedStatement pstmt = conProveedor.prepareStatement("SELECT * FROM proveedores WHERE id = ?");
            pstmt.setInt(1, this.id_proveedor);
            ResultSet prs = pstmt.executeQuery();
            if(prs.next()){
                respuesta = prs.getString(2)+","+ prs.getString(3)+","+prs.getString(6);
            }
            
        } catch (SQLException ex) {
        }
        return respuesta;
    }
    
    private int sumatoriaEntrada(){
        int sumaEntrada = 0;
        for(int i = 0; i < movimientos.size(); i++){
            sumaEntrada += movimientos.get(i).getMovimiento();
        }
        return sumaEntrada;
    }
    
}
