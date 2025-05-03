/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.CitaQuirofano;
import clases_hospital.InventarioDetalle;
import clases_hospital_DAO.InventarioDetalleDAO;
import java.awt.Color;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class VerLotesInsumosController implements Initializable {

    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    Conexion conexion = new Conexion();
    Connection con;
    ObservableList<InventarioDetalle> listaInventa = FXCollections.observableArrayList();
    ;
    
    InventarioDetalleDAO inventarioDetalleDAO;

    @FXML
    private Label lblNombre;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<InventarioDetalle> tabla;
    @FXML
    private TableColumn<?, ?> colLote;
    @FXML
    private TableColumn<?, ?> colCaducidad;
    @FXML
    private TableColumn<?, ?> colEstatus;
    @FXML
    private TableColumn<InventarioDetalle, Integer> colCantidad;
    @FXML
    private TableColumn<?, ?> colFecha_mod;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = conexion.conectar2();
        llenarTabla();
        pintarTabla();
        tabla.setEditable(true);

    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();

    }

    public void recibeIdInsumi(int id_insumo, String nombre) {
        inventarioDetalleDAO = new InventarioDetalleDAO(con);
        try {
            lblNombre.setText(nombre);
            listaInventa.addAll(inventarioDetalleDAO.visualizarlotesconestado(id_insumo));
        } catch (SQLException ex) {
            Logger.getLogger(VerLotesInsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void llenarTabla() {
        tabla.getItems().clear();

        colLote.setCellValueFactory(new PropertyValueFactory("lote"));
        colCaducidad.setCellValueFactory(new PropertyValueFactory("caducidad"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estado"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colFecha_mod.setCellValueFactory(new PropertyValueFactory("fecha_modificacion"));
        editartabla();
        tabla.setItems(listaInventa);
    }
    //Metodo para pintar tabla

 private void pintarTabla() {
    tabla.setRowFactory(tv -> new TableRow<InventarioDetalle>() {
        @Override
        public void updateItem(InventarioDetalle item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null) {
                setStyle("");
              
            } else if ("ACTIVO".equals(item.getEstado())) {
                setStyle("-fx-background-color: #c7f5f5; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
            } else if ("INACTIVO".equals(item.getEstado())) {
                setStyle("-fx-background-color:  #E74C3C; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//cancelado
            } else {
                setStyle(" ");
            }
        }
    });
}
 
   private void editartabla() {
       
    

    // Habilitar la edición en la columna "Cantidad"
    colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


    // Manejar el evento cuando se confirma la edición de la cantidad
    colCantidad.setOnEditCommit(event -> {
        String respuesta;
        // Obtener el valor editado
        int nuevoValor =   event.getNewValue();
        //selecciona la linea 
        int indiceFila = event.getTablePosition().getRow();
        // actualiza los valores en esa linea
        int id_inv_det = listaInventa.get(indiceFila).getId_inventario_detalle();
        
        respuesta = inventarioDetalleDAO.ActualizarCantidadPorIdInventadioDetalle(id_inv_det, nuevoValor);
         
         listaInventa.get(indiceFila).setCantidad(nuevoValor);
        alertaConfirmacion.setTitle("ACTUALIZADO");
        alertaConfirmacion.setHeaderText(respuesta);
        alertaConfirmacion.setContentText("");
        alertaConfirmacion.showAndWait();
        
        
        // Recalcular el importe (si es necesario)

        // Actualizar la tabla
   
        tabla.refresh();
    });
       // Permitir la edición de la columnas 
    colCantidad.setEditable(true);

    }

}
