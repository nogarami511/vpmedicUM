/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.ComprasInternas;
import clases_hospital.ConfirmacionAutorizacion;
import clases_hospital.FormaPago;
import clases_hospital.NumerosALetras;
import clases_hospital_DAO.ComprasInternasDAO;
import clases_hospital_DAO.ConfirmacionAutorizacionDAO;
import clases_hospital_DAO.FormaPagoDAO;
import clases_hospital_DAO.ProveedorDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ComprasInternasController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<ComprasInternas> comprasinternas = FXCollections.observableArrayList();
    ObservableList<FormaPago> formaspagos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    ConfirmacionAutorizacionDAO confirmacionautorizacionDAO;
    ComprasInternasDAO comprasinternasDAO;
    FormaPagoDAO formaspagoDAO;
    ProveedorDAO proveedordao;

    Proveedor provedor;

    FormaPago formapago;

    @FXML
    private TextField txfBuscarRazonSocial;
    @FXML
    private Button btnBuscarRazonSocial;
    @FXML
    private ComboBox<FormaPago> cmbFomraPago;
    @FXML
    private Label lblTotalSolicitado;
    @FXML
    private Button btnSolicitudPagoCxP;
    @FXML
    private TableView<ComprasInternas> tabla;
    @FXML
    private TableColumn<?, ?> colNumero;
    @FXML
    private TableColumn<?, ?> colRubro;
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
    private TableColumn<ComprasInternas, Double> colMontoSolicitado;
    @FXML
    private TableColumn<?, ?> colMontoAutorizado;
    @FXML
    private TableColumn colSolicitar;
    @FXML
    private Button btnSolicitar;
    @FXML
    private TableColumn colImprimir;
    @FXML
    private Button btnGenerarCompra;

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
            llenarCmb();
            lleanrbuscador();
            btnBuscarRazonSocial.setDisable(true);
        } catch (SQLException ex) {
            Logger.getLogger(ComprasInternasController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    private void accionBuscarRazonSocial(ActionEvent event) throws SQLException {
        llenarTablaBucador();
    }

    @FXML
    private void accionSolicitudPagoCxP(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ConfirmacionDeAutorizacionCuentasxPagar.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.setTitle("ConfirmacionDeAutorizacionCuentasxPagar");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void accionSolicitar(ActionEvent event) {
        ConfirmacionAutorizacion confirmacionAutorizacion = new ConfirmacionAutorizacion();
        DecimalFormat df = new DecimalFormat("0.00");
        LocalDate localDate = LocalDate.now();
        Date sqlDate = Date.valueOf(localDate);

        if (formapago == null || lblTotalSolicitado.getText().equals("$0.00")) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("SELECIONE UNA FORMA DE PAGO, Y/O ORDENES DE COMPRA PARA CONTINUAR");
            alertaError.showAndWait();
        } else {
            int contar = 0;
            for (int i = 0; i < comprasinternas.size(); i++) {
                if (comprasinternas.get(i).isSolicitar_compra() && comprasinternas.get(i).getId_confirmacion_autorizacion() == 1) {
                    contar++;
                }
            }
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("Confirmación");
            alertaConfirmacion.setContentText("¿Esta seguro que quiere solicitar el pago de: " + contar + " ordenes de compra?");
            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
            if (action.get() == ButtonType.OK) {
                confirmacionAutorizacion.setFecha_pedio(sqlDate);
                confirmacionAutorizacion.setId_formaPago(formapago.getId());
             
                confirmacionAutorizacion.setMontoTotalAAutorizar(Double.parseDouble(df.format(actualizarLabel())));
                confirmacionAutorizacion.setEstatus(1);
                confirmacionAutorizacion.setUsuario_solicitante(VPMedicaPlaza.userSystem);

                Connection con = conexion.conectar2();
                confirmacionautorizacionDAO = new ConfirmacionAutorizacionDAO(con);
                comprasinternasDAO = new ComprasInternasDAO(con);

                int id_confirmacionAutorizacion;
                try {
                    id_confirmacionAutorizacion = confirmacionautorizacionDAO.crearYRegresarIdConfirmacion(confirmacionAutorizacion);
                    if (id_confirmacionAutorizacion > 0) {
                        for (int i = 0; i < comprasinternas.size(); i++) {
                            if (comprasinternas.get(i).isSolicitar_compra() && comprasinternas.get(i).getId_confirmacion_autorizacion() == 1) {
                                ComprasInternas coprainterna = comprasinternas.get(i);
                                coprainterna.setId_confirmacion_autorizacion(id_confirmacionAutorizacion);
                                comprasinternasDAO.actualizarDatosCompletos(coprainterna);
                            }
                        }

                        alertaConfirmacion.setHeaderText(null);
                        alertaConfirmacion.setTitle("Confirmación");
                        alertaConfirmacion.setContentText("SOLICITUD DE PAGO GENERADA");
                        alertaConfirmacion.showAndWait();

                        llenarTabla();
                    } else {
                        alertaError.setHeaderText(null);
                        alertaError.setTitle("ERROR");
                        alertaError.setContentText("CONTACTE A SISTEMAS");
                        alertaError.showAndWait();
                    }
                } catch (SQLException ex) {

                    alertaError.setHeaderText(null);
                    alertaError.setTitle("ERROR");
                    alertaError.setContentText("CONTACTE A SISTEMAS, CODIGO DE ERROR: " + ex.getErrorCode());
                    alertaError.setContentText(ex.getMessage());
                    alertaError.showAndWait();
                    Logger.getLogger(ComprasInternasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void accionSeleccionarDato(ActionEvent event) {
        formapago = cmbFomraPago.getValue();
       
    }

    private void llenarTabla() throws SQLException {
        comprasinternas.clear();
        tabla.getItems().clear();
        comprasinternasDAO = new ComprasInternasDAO(conexion.conectar2());
        comprasinternas.addAll(comprasinternasDAO.obtenerTodosConNombreRubroYrazonSocial());

        colNumero.setCellValueFactory(new PropertyValueFactory("idComprasInternasp"));
        colRubro.setCellValueFactory(new PropertyValueFactory("nombre_rubro"));
        colFolio.setCellValueFactory(new PropertyValueFactory("folioPedido"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory("razonSocial"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fechaPedido"));
        colMonto.setCellValueFactory(new PropertyValueFactory("totalFormateado"));
        colMontoPagado.setCellValueFactory(new PropertyValueFactory("montoPagadoFormateado"));
        colSaldoSAldado.setCellValueFactory(new PropertyValueFactory("saldoFormateado"));
        colMontoSolicitado.setCellValueFactory(new PropertyValueFactory("montoSolicitadoFormateado"));
        colMontoAutorizado.setCellValueFactory(new PropertyValueFactory("montoAutorizadoFormateado"));
        generarRadioButton();
        generarColumnaImprimir();

        tabla.setItems(comprasinternas);
    }

    private void generarRadioButton() {
        DecimalFormat df = new DecimalFormat("0.00");

        Callback<TableColumn<ComprasInternas, String>, TableCell<ComprasInternas, String>> solicitar = (TableColumn<ComprasInternas, String> param) -> {
            final TableCell<ComprasInternas, String> cell = new TableCell<ComprasInternas, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton rdbCompra = new RadioButton();
                        ComprasInternas cmpi = getTableView().getItems().get(getIndex());
                        rdbCompra.setSelected(cmpi.isSolicitar_compra());
                        if (cmpi.getId_confirmacion_autorizacion() > 1) {
                            rdbCompra.setDisable(true);
                        }
                        rdbCompra.setOnAction(event -> {
                            if (cmpi.isSolicitar_compra()) {
                                cmpi.setSolicitar_compra(false);
                            } else {
                                cmpi.setSolicitar_compra(true);
                            }
                            lblTotalSolicitado.setText("$" + df.format(actualizarLabel()));
                          
                        });
                        setGraphic(rdbCompra);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colSolicitar.setCellFactory(solicitar);
    }

    private void llenarCmb() throws SQLException {
        formaspagoDAO = new FormaPagoDAO(conexion.conectar2());
        formaspagos.addAll(formaspagoDAO.obtenerPrimerosCuatro());
        cmbFomraPago.setItems(formaspagos);
    }

    private double actualizarLabel() {
        double sumaTotal = 0;
        for (int i = 0; i < comprasinternas.size(); i++) {
            if (comprasinternas.get(i).isSolicitar_compra() && comprasinternas.get(i).getId_confirmacion_autorizacion() == 1) {
                sumaTotal += comprasinternas.get(i).getTotal();
            }
        }
        return sumaTotal;
    }

    private void generarColumnaImprimir() {
        Callback<TableColumn<ComprasInternas, String>, TableCell<ComprasInternas, String>> imprimir = (TableColumn<ComprasInternas, String> param) -> {
            final TableCell<ComprasInternas, String> cell = new TableCell<ComprasInternas, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        ComprasInternas compraInterna = getTableView().getItems().get(getIndex());
                        ImageView impr = new ImageView("/img/icons/icons8-imprimir-30.png");
                        impr.setFitHeight(20);
                        impr.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir orden de copra: " + compraInterna.getIdComprasInternasp());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    NumerosALetras nal = new NumerosALetras(Double.parseDouble(df.format((compraInterna.getMontoSolicitado() - compraInterna.getComision()))));
                                    ReporteC reporte = new ReporteC("");
                                    reporte.generarReporteCompraInterna(compraInterna.getIdComprasInternasp(), nal.getCantidadString());
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
    }

    private void lleanrbuscador() throws SQLException {
        proveedordao = new ProveedorDAO(conexion.conectar2());
        AutoCompletionBinding<Proveedor> proveedorselecionado = TextFields.bindAutoCompletion(txfBuscarRazonSocial, proveedordao.obtenerTodos());
        proveedorselecionado.setOnAutoCompleted(event -> {
            provedor = event.getCompletion();
            btnBuscarRazonSocial.setDisable(false);
        });
    }

    private void llenarTablaBucador() throws SQLException {
        comprasinternas.clear();
        tabla.getItems().clear();
        comprasinternasDAO = new ComprasInternasDAO(conexion.conectar2());
        comprasinternas.addAll(comprasinternasDAO.obtenerConNombreRubroYrazonSocialPorIdLista(provedor.getId()));

        colNumero.setCellValueFactory(new PropertyValueFactory("idComprasInternasp"));
        colRubro.setCellValueFactory(new PropertyValueFactory("nombre_rubro"));
        colFolio.setCellValueFactory(new PropertyValueFactory("folioPedido"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory("razonSocial"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fechaPedido"));
        colMonto.setCellValueFactory(new PropertyValueFactory("total"));
        colMontoPagado.setCellValueFactory(new PropertyValueFactory("monto_pagado"));
        colSaldoSAldado.setCellValueFactory(new PropertyValueFactory("saldo_saldo"));
        colMontoSolicitado.setCellValueFactory(new PropertyValueFactory("montoSolicitado"));
        colMontoAutorizado.setCellValueFactory(new PropertyValueFactory("montoAutorizado"));
        generarRadioButton();
        generarColumnaImprimir();

        tabla.setItems(comprasinternas);
    }

    @FXML
    private void accionGenerarCompra(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ComprasInternas.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }
    
    private void actualizarDatosCompraInterna() {
        colMontoSolicitado.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colMontoSolicitado.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            ComprasInternas comprareabasto = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
                comprareabasto.setMontoSolicitado(event.getNewValue());
                tabla.refresh();
        });
    }
}
