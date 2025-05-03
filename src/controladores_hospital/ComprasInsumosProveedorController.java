/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.ComprasInsumosP;
import clases_hospital_DAO.*;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vistasAuxiliares_hospital.PedidosComprasController;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ComprasInsumosProveedorController implements Initializable {
    
    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    
    ObservableList<Proveedor> OBProveedoresList = FXCollections.observableArrayList();
    ObservableList<ComprasInsumosP> OBComprasInsumosP = FXCollections.observableArrayList();
    
    ProveedorDAO provedorDAO;
    CompraInsumoPDAO compraInsumoPDAO;
    
    int id_proveedor;

    @FXML
    private Button btnCambiarpProveedor;
    @FXML
    private Button btnConvertirCompra;
    @FXML
    private Button btnCrearPedido;
    @FXML
    private TextField txfProveedores;
    @FXML
    private TableColumn<?, ?> colPedido;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colFechaGenerado;
    @FXML
    private TableColumn<?, ?> colEstatus;
    @FXML
    private TableView<ComprasInsumosP> tabla;
    @FXML
    private TableColumn<?, ?> colEstatusRecibido;
    @FXML
    private TableColumn<?, ?> colEstatusPago;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = conexion.conectar2();
        provedorDAO = new ProveedorDAO(con);
        try {
            llenardatos();
        } catch (SQLException ex) {
            Logger.getLogger(ComprasInsumosProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    

    @FXML
    private void accionCambiarProveedor(ActionEvent event) {
        txfProveedores.setText(null);
        llenarAutoProveedores();
    }

    @FXML
    private void accionConvertirCompra(ActionEvent event) throws IOException {
        System.out.println(tabla.getSelectionModel().getSelectedItem().getEstatString());
        if("COMPRA".equals(tabla.getSelectionModel().getSelectedItem().getEstatString())){
            alertaError.setTitle("ALGO SALIO MAL");
               alertaError.setHeaderText("OCURRIO ALGO INESPERADO");
               alertaError.setContentText("ESTE NO ES UN PEDIDO, ES UNA COMPRA " );
               alertaError.showAndWait();
        }else{
            System.out.println("entra en el else");
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/PedidosCompras.fxml"));
        Parent root = fxml.load();
        
        PedidosComprasController destinoController = fxml.getController();
        destinoController.setObjeto(tabla.getSelectionModel().getSelectedItem().getId_compra_insumosp(), txfProveedores.getText());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
      //  stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.showAndWait();
        }
        
        
    }

    @FXML
    private void accionCrearPedido(ActionEvent event) throws IOException {
         FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/PedidosCompras.fxml"));
        Parent root = fxml.load();
        
//        PedidosComprasController destinoController = fxml.getController();
//        destinoController.setObjeto(tabla.getSelectionModel().getSelectedItem().getId_compra_insumosp());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
       // stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    private void  llenardatos() throws SQLException{
        
        OBProveedoresList.addAll(provedorDAO.obtenerTodos());
        llenarAutoProveedores();
        
    }
    
    private void  llenarAutoProveedores(){
        
        txfProveedores.setDisable(false);
        
         AutoCompletionBinding<Proveedor> nombres = TextFields.bindAutoCompletion(txfProveedores, OBProveedoresList);
            nombres.setPrefWidth(1000);
            nombres.setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<Proveedor> event) -> {
                Proveedor insumoSeleccionado = event.getCompletion();
                    id_proveedor = insumoSeleccionado.getId();
                   
                    txfProveedores.setDisable(true);
            try {
                llenarTabla();
            } catch (SQLException ex) {
                Logger.getLogger(ComprasInsumosProveedorController.class.getName()).log(Level.SEVERE, null, ex);
            }
                  
            });
    }
    
    private void llenarTabla() throws SQLException{
        con = conexion.conectar2();
        tabla.getItems().clear();
        compraInsumoPDAO = new CompraInsumoPDAO(con);
       
        OBComprasInsumosP.addAll(compraInsumoPDAO.listaComprasPorIdProveedor(id_proveedor));
         colPedido.setCellValueFactory(new PropertyValueFactory("id_compra_insumosp"));
         colNombre.setCellValueFactory(new PropertyValueFactory("observacion"));
         colFechaGenerado.setCellValueFactory(new PropertyValueFactory("fecha_creacion"));
         colEstatus.setCellValueFactory(new PropertyValueFactory("estatString"));
         colEstatusPago.setCellValueFactory(new PropertyValueFactory("estatPagoString"));
         colEstatusRecibido.setCellValueFactory(new PropertyValueFactory("estatRecibidoString"));
         tabla.setItems(OBComprasInsumosP);
        
    }
    
}
