/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.GenerarReabastoInsumo;
import clases_hospital.ReabastoPadre;
import clases_hospital_DAO.GenerarReabastoInsumoDAO;
import clases_hospital_DAO.ProveedorDAO;
import clases_hospital_DAO.ReabastoPadreDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class GenerarCompra2Controller implements Initializable {
    
    Conexion conexion = new Conexion();
    Connection con;

    
     Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    
    ObservableList<Proveedor> OBprovedores = FXCollections.observableArrayList();
    ObservableList<GenerarReabastoInsumo> OBReabastoInsumos = FXCollections.observableArrayList();
    ObservableList<ReabastoPadre> OBreabastoP = FXCollections.observableArrayList();
     private final ArrayList<RadioButton> radioButtons = new ArrayList<>();

    GenerarReabastoInsumoDAO generarReabastoinsumodao;
    ProveedorDAO provedordao;
    ReabastoPadreDAO reabastopadredao;
    
    
    
    
     double total;
     int idProveedor = 0;
     
     
     
    @FXML
    private ComboBox<ReabastoPadre> cmbPedidoReabasto;
    @FXML
    private Button btnSeleccionar;
    @FXML
    private Button btnDeseleccionar;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblToltal;
    @FXML
    private TableView<GenerarReabastoInsumo> tabla;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private TableColumn<?, ?> colPresentacion;
    private TableColumn<GenerarReabastoInsumo, Proveedor> colProveedor;
    @FXML
    private TableColumn<?, ?> colPiezasxPresentacion;
    @FXML
    private TableColumn<GenerarReabastoInsumo, Double> colUnidad;
    @FXML
    private TableColumn<?, ?> colCostoUnitario;
    @FXML
    private TableColumn<?, ?> colCostoHastaElMomento;
    @FXML
    private TableColumn colPedido;
    @FXML
    private ComboBox<Proveedor> cmbProveedor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = conexion.conectar2();
        try {
            // TODO
            llenarcmbproveedores();
        } catch (SQLException ex) {
            Logger.getLogger(GenerarCompra2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabla.setEditable(true);
    }
     @FXML
    private void accionSeleccionaReabasto(ActionEvent event) {
         if (cmbPedidoReabasto.getItems().isEmpty()) {
             System.out.println("no selecciono nada");
         }else{
             llenarTabla(cmbPedidoReabasto.getSelectionModel().getSelectedItem().getIdRabastosPadre());
             total = cmbPedidoReabasto.getSelectionModel().getSelectedItem().getCostoTotalInicial();
             lblToltal.setText("$ "+ total);
         }
    }

    @FXML
    private void accionSeleccionar(ActionEvent event) {
          for (int i = 0; i < OBReabastoInsumos.size(); i++) {
//            radioButtons.get(i).setSelected(true);
            double costo = OBReabastoInsumos.get(i).getTotalUnidadesFaltantes()* OBReabastoInsumos.get(i).getCostoUnitarioFinal();
            OBReabastoInsumos.get(i).setPedir(true);
            OBReabastoInsumos.get(i).setCosto_total_inicial(costo);
          
            actualizarLabel();
          
        }
        tabla.refresh();

        btnSeleccionar.setDisable(true);
        btnDeseleccionar.setDisable(false);
    }

    @FXML
    private void accionDeseleccionar(ActionEvent event) {
                for (int i = 0; i < OBReabastoInsumos.size(); i++) {
//            radioButtons.get(i).setSelected(false);
            OBReabastoInsumos.get(i).setPedir(false);
            actualizarLabel();
         
        }
        tabla.refresh();
        btnSeleccionar.setDisable(false);
        btnDeseleccionar.setDisable(true);
    }

    @FXML
    private void accionGenerar(ActionEvent event) throws SQLException {
        btnDeseleccionar.setDisable(false);
        generarReabastoinsumodao = new GenerarReabastoInsumoDAO(con);
        
        if(idProveedor != 0){
            
            alertaSuccess.setTitle("REALIZANDO PEDIDO");
            
            alertaSuccess.setContentText("ESTA VENTARA SE CERRARA SOLA");
            alertaSuccess.show();
            for( int i =0 ; i < OBReabastoInsumos.size() ; i++){
                
                alertaSuccess.setHeaderText("REALIZANDO PEDIDO" + i + " de " + OBReabastoInsumos.size());
           if(OBReabastoInsumos.get(i).isPedir()){
               
               OBReabastoInsumos.get(i).setId_proveedor(idProveedor);
               generarReabastoinsumodao.ActualizarInsumoporIdGenerarRI(OBReabastoInsumos.get(i));
              // System.out.println("iteracion" + i);
           }
       }
            reabastopadredao.CambiarStatusReabastoP(cmbPedidoReabasto.getValue().getIdRabastosPadre());
            reabastopadredao.GenerarPedidopoProveedor(cmbPedidoReabasto.getValue().getIdRabastosPadre(), idProveedor);
            alertaSuccess.close();
            alertaSuccess.setHeaderText("PEDIDO CORRECTAMENTE COLOCADO");
            alertaSuccess.setTitle("PEDIDO REALIZADO CON EXITO");
            alertaSuccess.setContentText("PEDIDO REALIZADO CON EXITO");
            alertaSuccess.showAndWait();
       Stage stage = (Stage) btnGenerar.getScene().getWindow();
        stage.close();
        }else
        {
            alertaError.setHeaderText("ERROR DE PROVEEDOR");
            alertaError.setTitle("NO SELECCIONO PROVEEDOR");
            alertaError.setContentText("FAVOR DE SELECCIONAR UN PROVEEDOR.");
            alertaError.showAndWait();
        }
        
       
        
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    //LLENAR DATOS NECESARIOSe
    private void llenarcmbproveedores() throws SQLException {        
        provedordao = new ProveedorDAO(con);
        reabastopadredao = new ReabastoPadreDAO(con);
        OBprovedores.addAll(provedordao.obtenerTodos());
        OBreabastoP.addAll(reabastopadredao.ultimos2ReabastosCreados());
        System.out.println(""+OBreabastoP.toString());
        cmbPedidoReabasto.setItems(OBreabastoP);
        cmbProveedor.setItems(OBprovedores);

    }
    
    private void llenarTabla(int id_reabastoP){
        tabla.getItems().clear();
        generarReabastoinsumodao = new GenerarReabastoInsumoDAO(con);
        
        OBReabastoInsumos.addAll(generarReabastoinsumodao.ListaReabasto(id_reabastoP));
        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        //PROVEEDOR
        editarTablaConsumo();
//        colProveedor.setCellValueFactory(new PropertyValueFactory("nombrePoveedor"));
        colUnidad.setCellValueFactory(new PropertyValueFactory("totalUnidadesFaltantes"));
        colCostoHastaElMomento.setCellValueFactory(new PropertyValueFactory("costo_total_inicial"));
        colPiezasxPresentacion.setCellValueFactory(new PropertyValueFactory("cantidad_unitariaxcaja"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory("costoUnitarioInicial"));
        
        DecimalFormat df = new DecimalFormat("0.00");
       
        
         Callback<TableColumn<GenerarReabastoInsumo, String>, TableCell<GenerarReabastoInsumo, String>> confirmar = (TableColumn<GenerarReabastoInsumo, String> param) -> {
            final TableCell<GenerarReabastoInsumo, String> cell = new TableCell<GenerarReabastoInsumo, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton rdbReabastecer = new RadioButton();
                        radioButtons.add(rdbReabastecer);
                        GenerarReabastoInsumo grSlt = getTableView().getItems().get(getIndex());
                        rdbReabastecer.setSelected(grSlt.isPedir());
                        rdbReabastecer.setOnAction(event -> {
                            if (grSlt.isPedir()) {
                                double act = actualizarLabel();
                                lblToltal.setText("$" + df.format(act - (grSlt.getCostoUnitarioInicial() * grSlt.getTotalUnidadesFaltantes())));
                                grSlt.setPedir(false);

                            } else {
                                grSlt.setPedir(true);
                                lblToltal.setText("$" + df.format(actualizarLabel()));
                            }

                         
                        });
                        setGraphic(rdbReabastecer);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        colPedido.setCellFactory(confirmar);

        
        
        
        tabla.setItems(OBReabastoInsumos);
        
    }
        private void editarTablaConsumo() {
       
        DecimalFormat df = new DecimalFormat("0.00");

        colUnidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colUnidad.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            GenerarReabastoInsumo genreainsuSelect = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            double anteriorPedidoUnitario = genreainsuSelect.getTotalUnidadesFaltantes();

            genreainsuSelect.setTotalUnidadesFaltantes(event.getNewValue());

            double nuevoPedididoUnitario = genreainsuSelect.getTotalUnidadesFaltantes();
            double costototalfina = Double.parseDouble(df.format((nuevoPedididoUnitario * genreainsuSelect.getCostoUnitarioInicial())));

            if (nuevoPedididoUnitario < anteriorPedidoUnitario) {
                genreainsuSelect.setIdEstatusReabasto(3);
            }
            lblToltal.setText("$" + df.format(actualizarLabel()));
            genreainsuSelect.setCosto_total_inicial(costototalfina); 

//            actializarRegistro(genreainsuSelect);
//            
//            actualizarLabel();
//            tabla.refresh();
        });
        
      
        
        colUnidad.setEditable(true);
    }
    
    
    

    private double actualizarLabel() {
        double totaldevolver = 0;
        for (int i = 0; i < OBReabastoInsumos.size(); i++) {
            if (OBReabastoInsumos.get(i).isPedir()) {
                totaldevolver = totaldevolver + (OBReabastoInsumos.get(i).getCostoUnitarioInicial()* OBReabastoInsumos.get(i).getTotalUnidadesFaltantes());
             
            } 
        }
        lblToltal.setText("$ "+ totaldevolver);
        return totaldevolver;
    }

    @FXML
    private void accionSeleccionaProveedor(ActionEvent event) {
        
        if(cmbProveedor.getValue() != null){
            cmbProveedor.setDisable(true);
            idProveedor =  cmbProveedor.getValue().getId();
            System.out.println("id_proveedor"+ idProveedor);
        }
        
    }

   
}
