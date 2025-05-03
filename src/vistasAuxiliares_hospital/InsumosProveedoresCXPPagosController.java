/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.ComprasInternas;
import clases_hospital.CuentasPorPagar;
import clases_hospital.CuentasPorPagarAbonoDetalle;
import clases_hospital.EstudioMedico;
import clases_hospital.FondoEfectivo;
import clases_hospital.FondoEfectivoFijo;
import clases_hospital.Laboratorio;
import clases_hospital.NumerosALetras;
import clases_hospital.PagoCuentaPorPagar;
import clases_hospital_DAO.ComprasInternasDAO;
import clases_hospital_DAO.CuentasPorPagarAbonoDetallesDAO;
import clases_hospital_DAO.CuentasPorPagarDAO;
import clases_hospital_DAO.EstudiosMedicosDAO;
import clases_hospital_DAO.FondoEfectivoDAO;
import clases_hospital_DAO.FondoEfectivoFijoDAO;
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
public class InsumosProveedoresCXPPagosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<PagoCuentaPorPagar> pagoscuentasporpagar = FXCollections.observableArrayList();

    Conexion conexion = new Conexion();
    CuentasPorPagarAbonoDetalle cuentasPorPagar = new CuentasPorPagarAbonoDetalle();

    PagosCuentasPorPagarDAO pagoscuentasporpagardao;
    CuentasPorPagarDAO cuentasPorPagarDAO;
    CuentasPorPagarAbonoDetallesDAO cuentasPorPagarAbonoDetallesDAO;
    FondoEfectivoDAO fondoefectivodao;
    FondoEfectivoFijoDAO fondoefectivofijodao;

    int filas;

    @FXML
    private Label lblFolioCompra;
    @FXML
    private Label lblFolioPedido;
    @FXML
    private Label lblRazonSocial;
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
        cuentasPorPagarAbonoDetallesDAO = new CuentasPorPagarAbonoDetallesDAO(con);
        PagoCuentaPorPagar pagocuentaporpagar = new PagoCuentaPorPagar();
        FondoEfectivoFijo fondoefectivofijo;

        if (filas == 0) {
            pagocuentaporpagar.setNumPago(1);
        } else {
            pagocuentaporpagar.setNumPago((filas + 1));
        }
     

        java.sql.Date sqlDate = java.sql.Date.valueOf(dtpFechaPago.getValue());

        pagocuentaporpagar.setIdComprasInternasP(cuentasPorPagar.getIdCuentaPorPagarAbonoDetalle());
        pagocuentaporpagar.setFechaPago(sqlDate);
        pagocuentaporpagar.setId_forma_pago(cuentasPorPagar.getIdFormaPago());
        pagocuentaporpagar.setImportePago(Double.parseDouble(txfMontoPago.getText()));
        pagocuentaporpagar.setUsuarioModificacion(VPMedicaPlaza.userSystem);

        pagoscuentasporpagardao.insertarPago(pagocuentaporpagar);

        if (cuentasPorPagar.getIdFormaPago() == 1) {
            fondoefectivofijo = fondoefectivofijodao.leerFondoEfectivoFijoPorId(1);

            FondoEfectivo fondoefectivo = new FondoEfectivo();

            fondoefectivo.setTipoOperacion("EGRESO");
            fondoefectivo.setImporte(Double.parseDouble(txfMontoPago.getText()));
            fondoefectivo.setSaldo(fondoefectivofijo.getMonto());
            fondoefectivo.setConcepto(txaObservaciones.getText());

            fondoefectivodao.insertarFondoEfectivo(fondoefectivo);

            double nuevo_saldo = Double.parseDouble(df.format(fondoefectivofijo.getMonto() - Double.parseDouble(txfMontoPago.getText())));

            fondoefectivofijodao.actualizarFondoEfectivoFijo(1, nuevo_saldo);
        }

        double saldoci = Double.parseDouble(df.format((cuentasPorPagar.getSaldoAbono()- Double.parseDouble(txfMontoPago.getText()))));
        System.out.println("SALDO" + cuentasPorPagar.getSaldoAbono() + " ABONO" + Double.parseDouble(txfMontoPago.getText()));
        double montopagado = Double.parseDouble(df.format((cuentasPorPagar.getAbonoAbono()+ Double.parseDouble(txfMontoPago.getText()))));
        cuentasPorPagar.setAbonoAbono(montopagado);
        cuentasPorPagar.setSaldoAbono(saldoci);
        
        System.out.println(saldoci);
        if (saldoci <= 0.0) {
            cuentasPorPagar.setEstatus_pago(true);
        }
        cuentasPorPagar.setUsuarioModificacion(VPMedicaPlaza.userSystem);

        cuentasPorPagarAbonoDetallesDAO.update(cuentasPorPagar);
        
        cuentasPorPagarDAO = new CuentasPorPagarDAO(con);
        CuentasPorPagar cpp = cuentasPorPagarDAO.getById(cuentasPorPagar.getIdCuentaPorPagar());
        double saldo = cpp.getSaldo() - Double.parseDouble(txfMontoPago.getText());
        double abono = Double.parseDouble(txfMontoPago.getText()) + cpp.getAbono();
        
        cpp.setSaldo(saldo);
        cpp.setAbono(abono);
        
        cuentasPorPagarDAO.update(cpp);

        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("Pago generado correctamente");
        alertaConfirmacion.showAndWait();

        Stage stage = (Stage) btnRealizarPagos.getScene().getWindow();
        stage.close();
    }

    public void recibirDatosPago(CuentasPorPagarAbonoDetalle estudioMedico) {
        DecimalFormat df = new DecimalFormat("0.00");
        cuentasPorPagar = estudioMedico;
        Proveedor laboratorio = estudioMedico.getProvedor();
        lblFolioCompra.setText(""+estudioMedico.getIdCuentaPorPagarAbonoDetalle());
        lblFolioPedido.setText(""+estudioMedico.getIdCuentaPorPagar());
        lblRazonSocial.setText(laboratorio.getRazonSocial());
        lblRfc.setText(laboratorio.getRfc());

        if (cuentasPorPagar.isEstatus_pago()) {
            dtpFechaPago.setDisable(true);
            txaObservaciones.setDisable(true);
            txfMontoPago.setDisable(true);
            btnRealizarPagos.setDisable(true);
        }

        lblFormaPAgo.setText(estudioMedico.getFormapagotipo());
        dtpFechaCompra.setValue(estudioMedico.getFechaModificacion().toLocalDate());
        lblMontoCompra.setText(cuentasPorPagar.getTotalAbonarMX());
        lblTotalAbono.setText(cuentasPorPagar.getAbonoAbonoMX());
        lblSaldo.setText(cuentasPorPagar.getSaldoAbonoMX());

        pagoscuentasporpagardao = new PagosCuentasPorPagarDAO(conexion.conectar2());
        try {
            filas = pagoscuentasporpagardao.obtenerCantidadFilasPorIdOrdenCompra(cuentasPorPagar.getIdAutorizacionPagos());

            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(CXPCompraInternaVisualizarPagosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        pagoscuentasporpagardao = new PagosCuentasPorPagarDAO(conexion.conectar2());
        pagoscuentasporpagar.addAll(pagoscuentasporpagardao.obtenerTodosLosPagosPorIdOrdenCompra(cuentasPorPagar.getIdCuentaPorPagar()));

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
                        PagoCuentaPorPagar pagoCuentaPorPagar = getTableView().getItems().get(getIndex());
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
                                    NumerosALetras num = new NumerosALetras(pagoCuentaPorPagar.getImportePago());
                                    ReporteC reportec = new ReporteC("");
                                    reportec.generarReporteImpresionPagosOrdenesComraEstudios(num.getCantidadString(), pagoCuentaPorPagar.getIdComprasInternasP(), pagoCuentaPorPagar.getIdPagosCuentasPorPagar());
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

}
