/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.ComprasInternas;
import clases_hospital.FondoEfectivo;
import clases_hospital.FondoEfectivoFijo;
import clases_hospital.Fondocuentabanco;
import clases_hospital.FormaPago;
import clases_hospital.NumerosALetras;
import clases_hospital.PagoCuentaPorPagar;
import clases_hospital_DAO.ComprasInternasDAO;
import clases_hospital_DAO.FondoBancoDAO;
import clases_hospital_DAO.FondoEfectivoDAO;
import clases_hospital_DAO.FondoEfectivoFijoDAO;
import clases_hospital_DAO.FondocuentabancoDAO;
import clases_hospital_DAO.FormaPagoDAO;
import clases_hospital_DAO.PagosCuentasPorPagarDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CXPCompraInternaVisualizarPagosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<PagoCuentaPorPagar> pagoscuentasporpagar = FXCollections.observableArrayList();
    ObservableList<FormaPago> formasPagos = FXCollections.observableArrayList();

    Conexion conexion = new Conexion();
    Connection con;
    ComprasInternas ci = new ComprasInternas();
    FormaPago formaPago;

    PagosCuentasPorPagarDAO pagoscuentasporpagardao;
    ComprasInternasDAO comprasinternasdao;
    FondoEfectivoDAO fondoefectivodao;
    FondoEfectivoFijoDAO fondoefectivofijodao;
    FormaPagoDAO formaPagoDAO;
    FondocuentabancoDAO  fondocuentabancoDAO;

    int filas;

    @FXML
    private Label lblFolioCompra;
    @FXML
    private Label lblFolioPedido;
    @FXML
    private Label lblRazonSocial;
    @FXML
    private Label lblNombreComercial;
    @FXML
    private Label lblRfc;
    @FXML
    private DatePicker dtpFechaCompra;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<PagoCuentaPorPagar> tabla;
    @FXML
    private TableColumn colImprimir;
    @FXML
    private TableColumn colActualziar;
    @FXML
    private TableColumn<?, ?> colCancelar;
    @FXML
    private TableColumn colNumPago;
    @FXML
    private TableColumn colFechaPago;
    @FXML
    private TableColumn colFormaPago;
    @FXML
    private TableColumn colImportePago;
    @FXML
    private DatePicker dtpFechaPago;
    @FXML
    private Label lblFormaPAgo;
    @FXML
    private TextField txfMontoPago;
    @FXML
    private TextArea txaObservaciones;
    @FXML
    private Button btnRealizarPagos;
    @FXML
    private Label lblMontoCompra;
    @FXML
    private Label lblTotalAbono;
    @FXML
    private Label lblSaldo;
    @FXML
    private Label lblAutorizado;
    @FXML
    private ComboBox<FormaPago> cmbFormaPago;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionRealizarPago(ActionEvent event) throws SQLException {
        Connection con = conexion.conectar2();
        DecimalFormat df = new DecimalFormat("0.00");
        pagoscuentasporpagardao = new PagosCuentasPorPagarDAO(con);
        fondoefectivofijodao = new FondoEfectivoFijoDAO(con);
        fondoefectivodao = new FondoEfectivoDAO(con);
        comprasinternasdao = new ComprasInternasDAO(con);
        PagoCuentaPorPagar pagocuentaporpagar = new PagoCuentaPorPagar();
        FondoEfectivoFijo fondoefectivofijo;

        if (filas == 0) {
            pagocuentaporpagar.setNumPago(1);
        } else {
            pagocuentaporpagar.setNumPago((filas + 1));
        }
    

        java.sql.Date sqlDate = java.sql.Date.valueOf(dtpFechaPago.getValue());

        pagocuentaporpagar.setIdComprasInternasP(ci.getIdComprasInternasp());
        pagocuentaporpagar.setFechaPago(sqlDate);
        pagocuentaporpagar.setId_forma_pago(formaPago.getId());
        pagocuentaporpagar.setImportePago(Double.parseDouble(txfMontoPago.getText()));
        pagocuentaporpagar.setUsuarioModificacion(VPMedicaPlaza.userSystem);

        pagoscuentasporpagardao.insertarPago(pagocuentaporpagar);

        if (ci.getId_formaPago() == 1) {
            fondoefectivofijo = fondoefectivofijodao.leerFondoEfectivoFijoPorId(1);

            FondoEfectivo fondoefectivo = new FondoEfectivo();

            fondoefectivo.setTipoOperacion("EGRESO");
            fondoefectivo.setImporte(Double.parseDouble(txfMontoPago.getText()));
            fondoefectivo.setSaldo(fondoefectivofijo.getMonto());
            fondoefectivo.setConcepto(txaObservaciones.getText());

            fondoefectivodao.insertarFondoEfectivo(fondoefectivo);

            double nuevo_saldo = Double.parseDouble(df.format(fondoefectivofijo.getMonto() - Double.parseDouble(txfMontoPago.getText())));

            fondoefectivofijodao.actualizarFondoEfectivoFijo(1, nuevo_saldo);
        }else if(ci.getId_formaPago() == 2 || ci.getId_formaPago() == 3 || ci.getId_formaPago() == 4 ){
            fondocuentabancoDAO =  new  FondocuentabancoDAO(con);
            double fondocuentabanco = fondocuentabancoDAO.fondocuentabancofijo();
            Fondocuentabanco fondobanco = new Fondocuentabanco();
            
            fondobanco.setTipoOperacion("EGRESO");
            fondobanco.setImporte(Double.parseDouble(txfMontoPago.getText()));
            fondobanco.setSaldo(fondocuentabanco);
          //  System.out.println(""+ txaObservaciones);
            fondobanco.setConcepto(lblNombreComercial.getText());
            fondobanco.setIdCliente(0);
            
            fondocuentabancoDAO.crearFondoCuentaBanco(fondobanco);
           
            double nuevo_saldo = fondocuentabanco - fondobanco.getImporte();
            
            
            fondocuentabancoDAO.actualizarfindobancofijo(nuevo_saldo);
        }

        double saldoci = Double.parseDouble(df.format((ci.getSaldo_saldo() - Double.parseDouble(txfMontoPago.getText()))));
        double montopagado = Double.parseDouble(df.format((ci.getMonto_pagado() + Double.parseDouble(txfMontoPago.getText()))));
        ci.setMonto_pagado(montopagado);
        ci.setSaldo_saldo(saldoci);

        if (saldoci == 0.0) {
          
            ci.setId_estatus_pagos_compras(1);
        }
        ci.setUsuarioModificacion(VPMedicaPlaza.userSystem);

        comprasinternasdao.actualizarDatosCompletosEstatusCompra(ci);

        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("Pago generado correctamente");
        alertaConfirmacion.showAndWait();

        Stage stage = (Stage) btnRealizarPagos.getScene().getWindow();
        stage.close();
    }

    public void recibirDatosPago(ComprasInternas comprasInternas) {
        DecimalFormat df = new DecimalFormat("0.00");
        ci = comprasInternas;
        Proveedor provedor = comprasInternas.getProveedor();
        lblFolioCompra.setText(comprasInternas.getFolioPedido());
        lblFolioPedido.setText(comprasInternas.getFolioPedido());
        lblNombreComercial.setText(provedor.getNombreComercial());
        lblRazonSocial.setText(provedor.getRazonSocial());
        lblRfc.setText(provedor.getRfc());

        if (ci.getId_estatus_pagos_compras() == 1) {
            dtpFechaPago.setDisable(true);
            txaObservaciones.setDisable(true);
            txfMontoPago.setDisable(true);
            btnRealizarPagos.setDisable(true);
        }

        lblFormaPAgo.setText(comprasInternas.getForma_pago_nombre());
        dtpFechaCompra.setValue(comprasInternas.getFechaPedido().toLocalDate());
        lblMontoCompra.setText("$" + df.format(comprasInternas.getTotal()));
        lblTotalAbono.setText("$" + df.format(comprasInternas.getMonto_pagado()));
        lblSaldo.setText("$" + df.format(comprasInternas.getSaldo_saldo()));
        lblAutorizado.setText("$" + df.format(comprasInternas.getMontoAutorizado()));
        
        con = conexion.conectar2();
        pagoscuentasporpagardao = new PagosCuentasPorPagarDAO(con);
        formaPagoDAO = new FormaPagoDAO(con);
        try {
            filas = pagoscuentasporpagardao.obtenerCantidadFilasPorIdOrdenCompra(ci.getIdComprasInternasp());
            
            formaPago = formaPagoDAO.leer(comprasInternas.getId_formaPago());
            formasPagos.addAll(formaPagoDAO.obtenerPrimerosCuatro());
            cmbFormaPago.setValue(formaPago);
            cmbFormaPago.setItems(formasPagos);

            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(CXPCompraInternaVisualizarPagosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        pagoscuentasporpagardao = new PagosCuentasPorPagarDAO(conexion.conectar2());
        pagoscuentasporpagar.addAll(pagoscuentasporpagardao.obtenerTodosLosPagosPorIdOrdenCompra(ci.getIdComprasInternasp()));

        colNumPago.setCellValueFactory(new PropertyValueFactory("numPago"));
        colFechaPago.setCellValueFactory(new PropertyValueFactory("fechaPago"));
        colFormaPago.setCellValueFactory(new PropertyValueFactory("fptipoformapagonombre"));
        colImportePago.setCellValueFactory(new PropertyValueFactory("importePago"));
        generarBotones();

        tabla.setItems(pagoscuentasporpagar);

    }

    private void generarBotones() {
        Callback<TableColumn<PagoCuentaPorPagar, String>, TableCell<PagoCuentaPorPagar, String>> imprimir = (TableColumn<PagoCuentaPorPagar, String> param) -> {
            final TableCell<PagoCuentaPorPagar, String> cell = new TableCell<PagoCuentaPorPagar, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        PagoCuentaPorPagar confirmacionautorizacion = getTableView().getItems().get(getIndex());
                        ImageView impr = new ImageView("/img/icons/icons8-imprimir-30.png");
                        impr.setFitHeight(20);
                        impr.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir solicitud de copra:");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    NumerosALetras num = new NumerosALetras(confirmacionautorizacion.getImportePago());
                                    ReporteC reportec = new ReporteC("");
                                    reportec.generarReporteImpresionPagosOrdenesComra(num.getCantidadString(), confirmacionautorizacion.getIdComprasInternasP(), confirmacionautorizacion.getIdPagosCuentasPorPagar());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                             
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(impr);
                    }
                }
            };
            return cell;
        };

        colImprimir.setCellFactory(imprimir);

        Callback<TableColumn<PagoCuentaPorPagar, String>, TableCell<PagoCuentaPorPagar, String>> actualizar = (TableColumn<PagoCuentaPorPagar, String> param) -> {
            final TableCell<PagoCuentaPorPagar, String> cell = new TableCell<PagoCuentaPorPagar, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        PagoCuentaPorPagar confirmacionautorizacion = getTableView().getItems().get(getIndex());
                        ImageView impr = new ImageView("/img/icons/icons8-actualizar-30.png");
                        impr.setFitHeight(20);
                        impr.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir solicitud de copra:");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    txaObservaciones.setDisable(false);
                                    txfMontoPago.setDisable(false);
                                    btnRealizarPagos.setDisable(false);
                                    
                                    txfMontoPago.setText(""+confirmacionautorizacion.getImportePago());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                               
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(impr);
                    }
                }
            };
            return cell;
        };

        colActualziar.setCellFactory(actualizar);
    }

    @FXML
    private void accionFormaDePago(ActionEvent event) {
        formaPago = cmbFormaPago.getValue();
    }

}
