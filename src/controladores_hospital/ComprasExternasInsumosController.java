/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.ReabastoPadre;
import clases_hospital_DAO.ComprasInternasDAO;
import clases_hospital_DAO.GenerarReabastoInsumoDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import clases_hospital_DAO.ReabastoPadreDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ComprasExternasInsumosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<ReabastoPadre> reabastospadpadres = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    
    ReabastoPadreDAO reabastopadrdao;
    
    private int id_reabastoP;

    ReabastoPadreDAO reabastopadredao;
    GenerarReabastoInsumoDAO generarreabastoinsumodao;

    MovimientoPadreDAO movimientopadredao;

    @FXML
    private TextField txfBuscarRazonSocial;
    @FXML
    private Button btnBuscarRazonSocial;
    @FXML
    private ComboBox<?> cmbFomraPago;
    @FXML
    private Label lblTotalSolicitado;
    @FXML
    private Button btnSolicitudPagoCxP;
    @FXML
    private Button btnSolicitar;
    @FXML
    private Button btnGenerarCompra;
    @FXML
    private TableView<ReabastoPadre> tabla;
    @FXML
    private TableColumn<?, ?> colNumero;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<?, ?> colRazonSocial;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colMonto;
    @FXML
    private TableColumn<?, ?> colMontoPagado;
    @FXML
    private TableColumn<?, ?> colSaldoSAldado;
    @FXML
    private TableColumn<?, ?> colMontoSolicitado;
    @FXML
    private TableColumn<?, ?> colMontoAutorizado;
    @FXML
    private TableColumn<ReabastoPadre, String> colSolicitar;
    @FXML
    private TableColumn<?, ?> colImprimir;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ComprasExternasInsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionBuscarRazonSocial(ActionEvent event) {
    }

    @FXML
    private void accionSeleccionarDato(ActionEvent event) {
    }

    @FXML
    private void accionSolicitudPagoCxP(ActionEvent event) {
    }

    @FXML
    private void accionSolicitar(ActionEvent event) {
    }

    @FXML
    private void accionGenerarCompra(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ComprasExternasInsumos.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
//        llenarTabla();
    }

    private void llenarTabla() throws SQLException {
        reabastospadpadres.clear();
        tabla.getItems().clear();
        reabastopadrdao = new ReabastoPadreDAO(conexion.conectar2());
        reabastospadpadres.addAll(reabastopadrdao.obtenerConNombreRubroYrazonSocialPorIdLista());
        
        colNumero.setCellValueFactory(new PropertyValueFactory("idRabastosPadre"));
        colFolio.setCellValueFactory(new PropertyValueFactory("folioReabasto"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory("nombre"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fechaGenerado"));
        colMonto.setCellValueFactory(new PropertyValueFactory("costoTotalInicial"));
        colMontoPagado.setCellValueFactory(new PropertyValueFactory("monto_pagado"));
        colSaldoSAldado.setCellValueFactory(new PropertyValueFactory("saldo_saldar"));
        colMontoSolicitado.setCellValueFactory(new PropertyValueFactory("montoSolicitado"));
        colMontoAutorizado.setCellValueFactory(new PropertyValueFactory("montoAutorizado"));
//        generarRadioButton();
        
        tabla.setItems(reabastospadpadres);
    }
    
//    private void generarRadioButton() {
//        DecimalFormat df = new DecimalFormat("0.00");
//
//        Callback<TableColumn<ReabastoPadre, String>, TableCell<ReabastoPadre, String>> solicitar = (TableColumn<ReabastoPadre, String> param) -> {
//            final TableCell<ReabastoPadre, String> cell = new TableCell<ReabastoPadre, String>() {
//                @Override
//                public void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (empty) {
//                        setGraphic(null);
//                        setText(null);
//                    } else {
//                        final RadioButton rdbCompra = new RadioButton();
//                        ReabastoPadre rp = getTableView().getItems().get(getIndex());
//                        rdbCompra.setSelected(rp.isPedir());
//                        if (cmpi.getId_confirmacion_autorizacion() > 1) {
//                            rdbCompra.setDisable(true);
//                        }
//                        rdbCompra.setOnAction(event -> {
//                            if (cmpi.isSolicitar_compra()) {
//                                cmpi.setSolicitar_compra(false);
//                            } else {
//                                cmpi.setSolicitar_compra(true);
//                            }
//                            lblTotalSolicitado.setText("$" + df.format(actualizarLabel()));

//                        });
//                        setGraphic(rdbCompra);
//                        setText(null);
//                    }
//                }
//            };
//            return cell;
//        };
//        colSolicitar.setCellFactory(solicitar);
//    }

}
