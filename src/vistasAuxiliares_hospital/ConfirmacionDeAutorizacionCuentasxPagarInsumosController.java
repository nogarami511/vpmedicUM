/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.ComprasInternas;
import clases_hospital.ConfirmacionAutorizacion;
import clases_hospital.CuentasPorPagar;
import clases_hospital.CuentasPorPagarAbonoDetalle;
import clases_hospital.Fondocuentabanco;
import clases_hospital.NumerosALetras;
import clases_hospital_DAO.ComprasInternasDAO;
import clases_hospital_DAO.ConfirmacionAutorizacionDAO;
import clases_hospital_DAO.CuentasPorPagarAbonoDetallesDAO;
import clases_hospital_DAO.CuentasPorPagarDAO;
import clases_hospital_DAO.FondocuentabancoDAO;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import reportes.ReporteC;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ConfirmacionDeAutorizacionCuentasxPagarInsumosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<CuentasPorPagarAbonoDetalle> cuentasPorPagarAbonoDetalles = FXCollections.observableArrayList();
    ObservableList<ConfirmacionAutorizacion> confirmacionautorizaciones = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    ConfirmacionAutorizacionDAO confirmacionautorizacionDAO;
    CuentasPorPagarAbonoDetallesDAO cuentasPorPagarAbonoDetallesDAO;
    FondocuentabancoDAO fondocuentabaconDAO;

    String startDateString;
    String endDateString;

    ToggleGroup toggleGroup = new ToggleGroup();
    int idCA;

    @FXML
    private RadioButton rdbSolicitudes;
    @FXML
    private RadioButton rdbAutorizadas;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<ConfirmacionAutorizacion> tablaConfirmacion;
    @FXML
    private TableColumn<?, ?> colConfirmacionNum;
    @FXML
    private TableColumn<?, ?> colConfirmacionFecha;
    @FXML
    private TableColumn<?, ?> colConfirmacionMontoTotal;
    @FXML
    private TableColumn<?, ?> colConfirmacionFormaPago;
    @FXML
    private TableColumn<?, ?> colConfirmacionSolicitante;
    @FXML
    private TableColumn<?, ?> colConfirmacionEstatus;
    @FXML
    private TableColumn colConfirmacionVer;
    @FXML
    private TableColumn colConfirmacionImprimir;
    @FXML
    private TableColumn colConfirmacionAutorizar;
    @FXML
    private TableColumn colConfirmacionCancelar;
    @FXML
    private TableColumn colConfirmacionImpAuto;
    @FXML
    private TableView<CuentasPorPagarAbonoDetalle> talbaSolicitud;
    @FXML
    private TableColumn<?, ?> colSolicitudNumOrden;
    @FXML
    private TableColumn<?, ?> colSolicitudRubro;
    @FXML
    private TableColumn<?, ?> colSolicitudFolio;
    @FXML
    private TableColumn<?, ?> colSolicitudRazonSocial;
    @FXML
    private TableColumn<?, ?> colSolicitudFecha;
    @FXML
    private TableColumn<?, ?> colSolicitudMonto;
    @FXML
    private TableColumn<?, ?> colSolicitudMontopagado;
    @FXML
    private TableColumn<?, ?> colSolicitudSaldo;
    @FXML
    private TableColumn<CuentasPorPagarAbonoDetalle, Double> colSolicitudAutorizado;
    @FXML
    private TableColumn colSolicitudGuardar;
    @FXML
    private DatePicker dtpFechaInicio;
    @FXML
    private DatePicker dtpFechaFin;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        talbaSolicitud.setEditable(true);
        seleccionToggleGroup();
    }

    @FXML
    private void accionSolicitudes(ActionEvent event) throws SQLException {
        if (dtpFechaInicio.getValue() != null || dtpFechaFin.getValue() != null) {
            LocalDate startDate = dtpFechaInicio.getValue();
            LocalDate endDate = dtpFechaFin.getValue();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startDateString = startDate.format(dateFormatter);
            endDateString = endDate.format(dateFormatter);

            llenarTablaSolicitados();
        } else {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("FECHAS NO RECONOCIDAS");
            alertaError.setContentText("FAVOR DE LLENR TODOS LOS CAMPOS");
            alertaError.showAndWait();
        }
    }

    @FXML
    private void accionAutorizadas(ActionEvent event) throws SQLException {
        if (dtpFechaInicio.getValue() != null || dtpFechaFin.getValue() != null) {
            LocalDate startDate = dtpFechaInicio.getValue();
            LocalDate endDate = dtpFechaFin.getValue();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startDateString = startDate.format(dateFormatter);
            endDateString = endDate.format(dateFormatter);

            lleanarTablaAutorizados();
        } else {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("FECHAS NO RECONOCIDAS");
            alertaError.setContentText("FAVOR DE LLENR TODOS LOS CAMPOS");
            alertaError.showAndWait();
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();;
        stage.close();
    }

    @FXML
    private void accionFechaInicio(ActionEvent event) {
        dtpFechaFin.setDisable(false);
    }

    @FXML
    private void accionFechaFin(ActionEvent event) {
        rdbSolicitudes.setDisable(false);
        rdbAutorizadas.setDisable(false);
    }

    private void seleccionToggleGroup() {
        rdbSolicitudes.setToggleGroup(toggleGroup);
        rdbAutorizadas.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // No hay botones seleccionados, puedes manejarlo aquí si es necesario
            } else {
                // Desseleccionamos los botones que no estén seleccionados
                if (oldValue != null && !oldValue.equals(newValue)) {
                    oldValue.setSelected(false);
                }
            }
        });
    }

    private void llenarTablaSolicitados() throws SQLException {
        confirmacionautorizaciones.clear();
        tablaConfirmacion.getItems().clear();
        confirmacionautorizacionDAO = new ConfirmacionAutorizacionDAO(conexion.conectar2());
        confirmacionautorizaciones.addAll(confirmacionautorizacionDAO.obtenerTodosConInfirmacionAutorizacionConInformacionPorAutorizar(startDateString, endDateString));

        colConfirmacionNum.setCellValueFactory(new PropertyValueFactory("idComprasmenosuno"));
        colConfirmacionFecha.setCellValueFactory(new PropertyValueFactory("fecha_pedio"));
        colConfirmacionMontoTotal.setCellValueFactory(new PropertyValueFactory("montoTotalAAutorizar"));
        colConfirmacionFormaPago.setCellValueFactory(new PropertyValueFactory("forma_pago"));
        colConfirmacionSolicitante.setCellValueFactory(new PropertyValueFactory("usuario_solicitante"));
        colConfirmacionEstatus.setCellValueFactory(new PropertyValueFactory("nombre_estatus"));

        generarBotones();

        tablaConfirmacion.setItems(confirmacionautorizaciones);
    }

    private void lleanarTablaAutorizados() throws SQLException {
        confirmacionautorizaciones.clear();
        tablaConfirmacion.getItems().clear();
        confirmacionautorizacionDAO = new ConfirmacionAutorizacionDAO(conexion.conectar2());
        confirmacionautorizaciones.addAll(confirmacionautorizacionDAO.obtenerTodosConInfirmacionAutorizacionConInformacionAutorizados(startDateString, endDateString));

        colConfirmacionNum.setCellValueFactory(new PropertyValueFactory("idComprasmenosuno"));
        colConfirmacionFecha.setCellValueFactory(new PropertyValueFactory("fecha_pedio"));
        colConfirmacionMontoTotal.setCellValueFactory(new PropertyValueFactory("montoTotalAAutorizar"));
        colConfirmacionFormaPago.setCellValueFactory(new PropertyValueFactory("forma_pago"));
        colConfirmacionSolicitante.setCellValueFactory(new PropertyValueFactory("usuario_solicitante"));
        colConfirmacionEstatus.setCellValueFactory(new PropertyValueFactory("nombre_estatus"));

        generarBotones();

        tablaConfirmacion.setItems(confirmacionautorizaciones);
    }

    private void llenarTablaSolicitud(int id_confirmacion_autorizacion) throws SQLException {
        cuentasPorPagarAbonoDetalles.clear();
        talbaSolicitud.getItems().clear();
        cuentasPorPagarAbonoDetallesDAO = new CuentasPorPagarAbonoDetallesDAO(conexion.conectar2());
        cuentasPorPagarAbonoDetalles.addAll(cuentasPorPagarAbonoDetallesDAO.getByIdAutorizacionConfirmacion(id_confirmacion_autorizacion));

        colSolicitudNumOrden.setCellValueFactory(new PropertyValueFactory("idCuentaPorPagarAbonoDetalle"));
        colSolicitudRubro.setCellValueFactory(new PropertyValueFactory("nombreRubro"));
        colSolicitudFolio.setCellValueFactory(new PropertyValueFactory("idCuentaPorPagar"));
        colSolicitudRazonSocial.setCellValueFactory(new PropertyValueFactory("razonSocial"));
        colSolicitudFecha.setCellValueFactory(new PropertyValueFactory("fechaPago"));
        colSolicitudMonto.setCellValueFactory(new PropertyValueFactory("totalAbonarMX"));
        colSolicitudMontopagado.setCellValueFactory(new PropertyValueFactory("abonoAbonoMX"));
        colSolicitudSaldo.setCellValueFactory(new PropertyValueFactory("saldoAbonoMX"));
        colSolicitudAutorizado.setCellValueFactory(new PropertyValueFactory("totalAbonar"));

        actualizarDatosCompraInterna();

        generarBotonGuardar();

        talbaSolicitud.setItems(cuentasPorPagarAbonoDetalles);
    }

    private void generarBotones() {
        Callback<TableColumn<ConfirmacionAutorizacion, String>, TableCell<ConfirmacionAutorizacion, String>> visualizar = (TableColumn<ConfirmacionAutorizacion, String> param) -> {
            final TableCell<ConfirmacionAutorizacion, String> cell = new TableCell<ConfirmacionAutorizacion, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        ConfirmacionAutorizacion confirmacionautorizacion = getTableView().getItems().get(getIndex());
                        ImageView ver = new ImageView("/img/icons/icons8-ver-archivo-48.png");
                        ver.setFitHeight(20);
                        ver.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("VISUALIZAR COMPRA: " + confirmacionautorizacion.getId_confirmacionAutorizacion());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    idCA = confirmacionautorizacion.getId_confirmacionAutorizacion();
                                    llenarTablaSolicitud(confirmacionautorizacion.getId_confirmacionAutorizacion());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(ver);
                    }
                }
            };
            return cell;
        };

        colConfirmacionVer.setCellFactory(visualizar);

        Callback<TableColumn<ConfirmacionAutorizacion, String>, TableCell<ConfirmacionAutorizacion, String>> imprimir = (TableColumn<ConfirmacionAutorizacion, String> param) -> {
            final TableCell<ConfirmacionAutorizacion, String> cell = new TableCell<ConfirmacionAutorizacion, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        ConfirmacionAutorizacion confirmacionautorizacion = getTableView().getItems().get(getIndex());
                        ImageView impr = new ImageView("/img/icons/icons8-imprimir-30.png");
                        impr.setFitHeight(20);
                        impr.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir solicitud de copra: " + confirmacionautorizacion.getId_confirmacionAutorizacion());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    NumerosALetras num = new NumerosALetras(Double.parseDouble(df.format(confirmacionautorizacion.getMontoTotalAAutorizar())));
                                    //llenarTablaSolicitud(confirmacionautorizacion.getId_confirmacionAutorizacion());
                                    ReporteC reportec = new ReporteC("");
                                    reportec.generarReporteImpresionAutorizacionINSUprove(confirmacionautorizacion.getId_confirmacionAutorizacion(), num.getCantidadString());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(impr);
                        if (confirmacionautorizacion.getEstatus() > 1) {
                            btnEntrada.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colConfirmacionImprimir.setCellFactory(imprimir);

        Callback<TableColumn<ConfirmacionAutorizacion, String>, TableCell<ConfirmacionAutorizacion, String>> autorizar = (TableColumn<ConfirmacionAutorizacion, String> param) -> {
            final TableCell<ConfirmacionAutorizacion, String> cell = new TableCell<ConfirmacionAutorizacion, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        ConfirmacionAutorizacion confirmacionautorizacion = getTableView().getItems().get(getIndex());
                        ImageView autto = new ImageView("/img/icons/icons8-marca-de-verificación-50.png");
                        autto.setFitHeight(20);
                        autto.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Autorizar solicitud de compra: " + confirmacionautorizacion.getId_confirmacionAutorizacion() + "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {

                                    cuentasPorPagarAbonoDetallesDAO = new CuentasPorPagarAbonoDetallesDAO(conexion.conectar2());
                                    CuentasPorPagarAbonoDetalle comprasInternas = cuentasPorPagarAbonoDetallesDAO.obtenerPorIdConfirmacionAutorizacion(confirmacionautorizacion.getIdComprasmenosuno());
                                    System.out.println("EL ID DE CONFIRMACION AUTORIZACION ES: " + confirmacionautorizacion.getIdComprasmenosuno());

                                    confirmacionautorizacion.setEstatus(2);
                                    actualizarEstatus(confirmacionautorizacion);
                                    llenarTablaSolicitud(confirmacionautorizacion.getId_confirmacionAutorizacion());
                                    tablaConfirmacion.refresh();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(autto);
                        if (confirmacionautorizacion.getEstatus() > 1) {
                            btnEntrada.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colConfirmacionAutorizar.setCellFactory(autorizar);

        Callback<TableColumn<ConfirmacionAutorizacion, String>, TableCell<ConfirmacionAutorizacion, String>> cancelar = (TableColumn<ConfirmacionAutorizacion, String> param) -> {
            final TableCell<ConfirmacionAutorizacion, String> cell = new TableCell<ConfirmacionAutorizacion, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        ConfirmacionAutorizacion confirmacionautorizacion = getTableView().getItems().get(getIndex());
                        ImageView cance = new ImageView("/img/icons/icons8-cancelar-2-50.png");
                        cance.setFitHeight(20);
                        cance.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Cancelar solicitud de compra: " + confirmacionautorizacion.getId_confirmacionAutorizacion() + "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    confirmacionautorizacion.setEstatus(0);
                                    actualizarEstatus(confirmacionautorizacion);
                                    tablaConfirmacion.refresh();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(cance);
                        if (confirmacionautorizacion.getEstatus() > 1) {
                            btnEntrada.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colConfirmacionCancelar.setCellFactory(cancelar);

        Callback<TableColumn<ConfirmacionAutorizacion, String>, TableCell<ConfirmacionAutorizacion, String>> imprimirAuto = (TableColumn<ConfirmacionAutorizacion, String> param) -> {
            final TableCell<ConfirmacionAutorizacion, String> cell = new TableCell<ConfirmacionAutorizacion, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        ConfirmacionAutorizacion confirmacionautorizacion = getTableView().getItems().get(getIndex());
                        ImageView impr = new ImageView("/img/icons/icons8-imprimir-30.png");
                        impr.setFitHeight(20);
                        impr.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir autorizacion de copra: " + confirmacionautorizacion.getId_confirmacionAutorizacion());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    NumerosALetras num = new NumerosALetras(Double.parseDouble(df.format(confirmacionautorizacion.getMontoTotalAAutorizar())));
                                    ReporteC reportec = new ReporteC("");
                                    reportec.generarReporteImpresionAutorizacion(confirmacionautorizacion.getId_confirmacionAutorizacion(), num.getCantidadString());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(impr);
                        if (confirmacionautorizacion.getEstatus() < 2) {
                            btnEntrada.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colConfirmacionImpAuto.setCellFactory(imprimirAuto);
    }

    private void generarBotonGuardar() {
        Callback<TableColumn<CuentasPorPagarAbonoDetalle, String>, TableCell<CuentasPorPagarAbonoDetalle, String>> guardar = (TableColumn<CuentasPorPagarAbonoDetalle, String> param) -> {
            final TableCell<CuentasPorPagarAbonoDetalle, String> cell = new TableCell<CuentasPorPagarAbonoDetalle, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        CuentasPorPagarAbonoDetalle cuentasPorPagar = getTableView().getItems().get(getIndex());
                        ImageView guardar = new ImageView("/img/icons/icons8-insertar-50.png");
                        guardar.setFitHeight(20);
                        guardar.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Esta seguro de guardar los cambios");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
//                                    //llenarTablaSolicitud(confirmacionautorizacion.getId_confirmacionAutorizacion());
//                                    if (cuentasPorPagar.getSaldo() < cuentasPorPagar.getTotal()) {
//                                        cuentasPorPagar.setId_estatus_pagos(2);
//                                        cuentasPorPagar.setSaldo((cuentasPorPagar.getTotal() - cuentasPorPagar.getSaldo()));
//                                        cuentasPorPagar.setImporteAutorizado(cuentasPorPagar.getSaldo());
//                                    } else {
//                                        cuentasPorPagar.setId_estatus_pagos(1);
//                                    }
//                                    cuentasPorPagarDAO.update(cuentasPorPagar);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(guardar);
                    }
                }
            };
            return cell;
        };

        colSolicitudGuardar.setCellFactory(guardar);
    }

    private void actualizarDatosCompraInterna() {
        colSolicitudAutorizado.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colSolicitudAutorizado.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            CuentasPorPagarAbonoDetalle comprareabasto = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
//                comprareabasto.set(event.getNewValue());
//                comprareabasto.setSaldo(event.getNewValue());
//                cuentasPorPagarDAO.update(comprareabasto);
//                sumatoriaNuevaCantidad();
//                talbaSolicitud.refresh();
        });

        colSolicitudAutorizado.setEditable(true);
    }

//    private void sumatoriaNuevaCantidad() {
//        try {
//            double sumatoria = 0;
//            for (int i = 0; i < cuentasPorPagars.size(); i++) {
//                sumatoria += cuentasPorPagars.get(i).getSaldo();
//            }
//
//            confirmacionautorizacionDAO = new ConfirmacionAutorizacionDAO(conexion.conectar2());
//            ConfirmacionAutorizacion confAuto = confirmacionautorizacionDAO.leer(idCA);
//
//            double resta = confAuto.getMontoTotalAAutorizar() - sumatoria;
//            confAuto.setMontoTotalAAutorizar(sumatoria);
//
//            confirmacionautorizacionDAO.actualizar(confAuto);
//
//            ConfirmacionAutorizacion confAutoNVO = confAuto;
//            confAutoNVO.setMontoTotalAAutorizar(resta);
//            confirmacionautorizacionDAO.crear(confAutoNVO);
//
//            if (rdbSolicitudes.isSelected()) {
//                llenarTablaSolicitados();
//            } else {
//                lleanarTablaAutorizados();
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ConfirmacionDeAutorizacionCuentasxPagarInsumosController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private void actualizarEstatus(ConfirmacionAutorizacion confirmacionautorizacion) throws SQLException {
        confirmacionautorizacionDAO = new ConfirmacionAutorizacionDAO(conexion.conectar2());
        confirmacionautorizacionDAO.actualizar(confirmacionautorizacion);
    }

}
