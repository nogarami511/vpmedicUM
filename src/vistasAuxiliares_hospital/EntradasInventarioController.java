/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.*;

import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.*;

import clases_hospital_DAO.MovimientoPadreDAO;
import clases_hospital_DAO.ProveedorDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.Reporte;
import reportes.ReporteC;
//import reportes.GenerarReportes;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class EntradasInventarioController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    
    
    
    
    
    ObservableList<MovimientoDetalle> movimientoDetalles = FXCollections.observableArrayList();
    ObservableList<ComprasInsumosP> OBCompraInsumoP = FXCollections.observableArrayList();
    ObservableList<CompraInsumoDetalle> OBCompraInsumoDetalles = FXCollections.observableArrayList();
    
    
    
    
    
    
    MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();
    
    
    
    
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Connection conProveedor = conexion.conectar2();
    private int idInsumo;
    
    
      
    

    ProveedorDAO proveedordao;
    MovimientoPadreDAO movimientopadredao;
    CompraInsumoPDAO compraInsumoPDAO;
    ComprasInsumosDetalleDAO comprasInsumosDetalleDAO;
    
    
    
    
    
    private int idCompraInsumoP, idProveedor;
    private String nombreInsu;
    private double costo_total, subtotal, costoCaja, impuesto_insumo, impuestoSumatoria, costoCantidadUnitario, totalExistencia;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblCostoCompraTotal;
    @FXML
    private TextField txfFolioCompra;
    @FXML
    private TextField txfProveedor;
    @FXML
    private Button btnIngresarInsumos;
    @FXML
    private TableView<CompraInsumoDetalle> tabla;
    @FXML
    private TableColumn<?, ?> colInsumo;
    private TableColumn<?, ?> colEntrada;
    @FXML
    private TableColumn<?, ?> colCantidadFaltante;
    @FXML
    private TableColumn<?, ?> colcantidad;
    @FXML
    private TableColumn<?, ?> colCantidadRecibida;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = conexion.conectar2();
        try {
            LlenartxfProveedor();
        } catch (SQLException ex) {
            Logger.getLogger(EntradasInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        generarReporte();
        Stage stage = (Stage) btnAgregar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionSalir(ActionEvent event) {
          Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

@FXML
private void accionAgregarLotes(ActionEvent event) throws IOException, SQLException {
    
    CompraInsumoDetalle selectCompraInsumoDetalle = tabla.getSelectionModel().getSelectedItem();
    
    if (selectCompraInsumoDetalle != null) {
        System.out.println("id_insumo vista_entrada inv  "+selectCompraInsumoDetalle.getId_insumo());
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ConciliacionInventario.fxml"));
        Parent root = fxmlLoader.load();
        
        ConciliacionInventarioController controller = fxmlLoader.getController();
        controller.setObjeto(selectCompraInsumoDetalle);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("Conciliar Inventario");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
        compraInsumoPDAO.CambiarEstatusdeRecibido(idCompraInsumoP);
        
    } else {
        alertaError.setTitle("SELECCIONE UN INSUMO PARA AGREGAR LOTES");
        alertaError.setHeaderText("SELECCIONE UN INSUMO PARA AGREGAR LOTES");
        alertaError.setContentText("Favor de seleccionar un insumo de la tabla para poder agregar lotes");
        alertaError.showAndWait();
    }
}

    @FXML
    private void accionCargarPedido(ActionEvent event) {
        comprasInsumosDetalleDAO = new ComprasInsumosDetalleDAO(con);
        llenarTabla();
        
    }
    
    
    private void LlenartxfProveedor() throws SQLException{
            proveedordao = new ProveedorDAO(con);
            compraInsumoPDAO = new CompraInsumoPDAO(con);
         

            AutoCompletionBinding<Proveedor> proveedor = TextFields.bindAutoCompletion(txfProveedor, proveedordao.obtenerTodos());

            proveedor.setOnAutoCompleted(e -> {
                
                Proveedor proveeSelect = e.getCompletion();
                idProveedor = proveeSelect.getId();
                txfProveedor.setDisable(true);
                txfFolioCompra.setDisable(false);
                try {
                    OBCompraInsumoP.clear();
                    OBCompraInsumoP.addAll(compraInsumoPDAO.listaComprasPorIdProveedorSoloFolio(idProveedor));
                    LlenartxfFolio();
                } catch (SQLException ex) {
                    Logger.getLogger(EntradasInventarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
  
    }
        private void LlenartxfFolio() throws SQLException{
         
                
            AutoCompletionBinding<ComprasInsumosP> compraP = TextFields.bindAutoCompletion(txfFolioCompra, OBCompraInsumoP);

            compraP.setOnAutoCompleted(ex -> {
                
               idCompraInsumoP = ex.getCompletion().getId_compra_insumosp();
                System.out.println(""+ idCompraInsumoP);
                txfFolioCompra.setDisable(true);
            });
  
    }
        
        private void llenarTabla(){          
            tabla.getItems().clear();
            OBCompraInsumoDetalles.addAll(comprasInsumosDetalleDAO.traerCompraInsumoDetallesporCantidad(idCompraInsumoP));
            
            colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
            colcantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
            colCantidadRecibida.setCellValueFactory(new PropertyValueFactory("cantidad_recibida"));
            colCantidadFaltante.setCellValueFactory(new PropertyValueFactory("cantidad_faltante"));
            
            tabla.setItems(OBCompraInsumoDetalles);
        }
        
        private void  generarReporte(){
            ReporteC reporte =  new ReporteC("InsumosRecibidosPorLote");
            System.out.println("" + idCompraInsumoP);
            reporte.ReporteRecepciondeCompras(""+idCompraInsumoP);
            
        }

}