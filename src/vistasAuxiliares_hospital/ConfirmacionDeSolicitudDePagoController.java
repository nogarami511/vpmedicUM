/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.ComprasInternas;
import clases_hospital.ConfirmacionAutorizacion;
import clases_hospital.EstudioMedico;
import clases_hospital.NumerosALetras;
import clases_hospital.SolicitudPagoEstudioMedico;
import clases_hospital_DAO.ComprasInternasDAO;
import clases_hospital_DAO.ConfirmacionAutorizacionDAO;
import clases_hospital_DAO.EstudiosMedicosDAO;
import clases_hospital_DAO.SolicitudPagosEstudiosMedicosDAO;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class ConfirmacionDeSolicitudDePagoController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<EstudioMedico> estudiosmedicos = FXCollections.observableArrayList();
    ObservableList<SolicitudPagoEstudioMedico> solicitudespagosestudiosmeicos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    SolicitudPagosEstudiosMedicosDAO solicitudestudiosmedicosdao;
    EstudiosMedicosDAO estudiomedicodao;

    ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private Button btnSalir;
    @FXML
    private TableView<SolicitudPagoEstudioMedico> tablaConfirmacion;
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
    private TableView<EstudioMedico> talbaSolicitud;
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
    private RadioButton rdbSolicitudesEstudios;
    @FXML
    private RadioButton rdbAutorizadasEstudios;

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
    private void accionSolicitudesEstudios(ActionEvent event) throws SQLException {
        llenarTablaSolicitados();
    }

    @FXML
    private void accionAutorizadasEstudios(ActionEvent event) throws SQLException {
        lleanarTablaAutorizados();
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();;
        stage.close();
    }

    private void seleccionToggleGroup() {
       
        rdbSolicitudesEstudios.setToggleGroup(toggleGroup);
        rdbAutorizadasEstudios.setToggleGroup(toggleGroup);
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
        solicitudespagosestudiosmeicos.clear();
        tablaConfirmacion.getItems().clear();
        solicitudestudiosmedicosdao = new SolicitudPagosEstudiosMedicosDAO(conexion.conectar2());
        solicitudespagosestudiosmeicos.addAll(solicitudestudiosmedicosdao.obtenerTodasLasSolicitudesConInformacionPorAutorizar());

        colConfirmacionNum.setCellValueFactory(new PropertyValueFactory("idSolicitudPagosEstudiosMedicos"));
        colConfirmacionFecha.setCellValueFactory(new PropertyValueFactory("fechaGenerada"));
        colConfirmacionMontoTotal.setCellValueFactory(new PropertyValueFactory("montototal"));
        colConfirmacionFormaPago.setCellValueFactory(new PropertyValueFactory("formapagString"));
        colConfirmacionSolicitante.setCellValueFactory(new PropertyValueFactory("usuarioModificacion"));
        colConfirmacionEstatus.setCellValueFactory(new PropertyValueFactory("estatusString"));
        
        generarBotones();

        tablaConfirmacion.setItems(solicitudespagosestudiosmeicos);
    }

    private void lleanarTablaAutorizados() throws SQLException {
        solicitudespagosestudiosmeicos.clear();
        tablaConfirmacion.getItems().clear();
        solicitudestudiosmedicosdao = new SolicitudPagosEstudiosMedicosDAO(conexion.conectar2());
        solicitudespagosestudiosmeicos.addAll(solicitudestudiosmedicosdao.obtenerTodasLasSolicitudesConInformacionAutorisadas());

        colConfirmacionNum.setCellValueFactory(new PropertyValueFactory("idSolicitudPagosEstudiosMedicos"));
        colConfirmacionFecha.setCellValueFactory(new PropertyValueFactory("fechaGenerada"));
        colConfirmacionMontoTotal.setCellValueFactory(new PropertyValueFactory("montototal"));
        colConfirmacionFormaPago.setCellValueFactory(new PropertyValueFactory("formapagString"));
        colConfirmacionSolicitante.setCellValueFactory(new PropertyValueFactory("usuarioModificacion"));
        colConfirmacionEstatus.setCellValueFactory(new PropertyValueFactory("estatusString"));
        
        generarBotones();

        tablaConfirmacion.setItems(solicitudespagosestudiosmeicos);
    }

    private void llenarTablaSolicitud(int id_confirmacion_autorizacion) throws SQLException {
        estudiosmedicos.clear();
        talbaSolicitud.getItems().clear();
        estudiomedicodao = new EstudiosMedicosDAO(conexion.conectar2());
        estudiosmedicos.addAll(estudiomedicodao.obtenerTodosLosEstudiosMedicosConNombrelaboratoro(id_confirmacion_autorizacion));
        
        colSolicitudNumOrden.setCellValueFactory(new PropertyValueFactory("id"));
        colSolicitudRubro.setCellValueFactory(new PropertyValueFactory("nombreEstudio"));
        colSolicitudFolio.setCellValueFactory(new PropertyValueFactory("idFolio"));
        colSolicitudRazonSocial.setCellValueFactory(new PropertyValueFactory("nombre_comercial_laboratorio"));
        colSolicitudFecha.setCellValueFactory(new PropertyValueFactory("fechaPedido"));
        colSolicitudMonto.setCellValueFactory(new PropertyValueFactory("precio_con_iva"));
        colSolicitudMontopagado.setCellValueFactory(new PropertyValueFactory("monto_abonado"));
        colSolicitudSaldo.setCellValueFactory(new PropertyValueFactory("saldo_saldar"));
        
        talbaSolicitud.setItems(estudiosmedicos);
    }

    private void generarBotones() {
        Callback<TableColumn<SolicitudPagoEstudioMedico, String>, TableCell<SolicitudPagoEstudioMedico, String>> visualizar = (TableColumn<SolicitudPagoEstudioMedico, String> param) -> {
            final TableCell<SolicitudPagoEstudioMedico, String> cell = new TableCell<SolicitudPagoEstudioMedico, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        SolicitudPagoEstudioMedico solicitudpagoestudiomedico = getTableView().getItems().get(getIndex());
                        ImageView ver = new ImageView("/img/icons/icons8-ver-archivo-48.png");
                        ver.setFitHeight(20);
                        ver.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("VISUALIZAR COMPRA: " + solicitudpagoestudiomedico.getIdSolicitudPagosEstudiosMedicos());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    llenarTablaSolicitud(solicitudpagoestudiomedico.getIdSolicitudPagosEstudiosMedicos());
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
        
        Callback<TableColumn<SolicitudPagoEstudioMedico, String>, TableCell<SolicitudPagoEstudioMedico, String>> imprimir = (TableColumn<SolicitudPagoEstudioMedico, String> param) -> {
            final TableCell<SolicitudPagoEstudioMedico, String> cell = new TableCell<SolicitudPagoEstudioMedico, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        SolicitudPagoEstudioMedico solictudpagoestudiomedico = getTableView().getItems().get(getIndex());
                        ImageView impr = new ImageView("/img/icons/icons8-imprimir-30.png");
                        impr.setFitHeight(20);
                        impr.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir solicitud de copra: " + solictudpagoestudiomedico.getIdSolicitudPagosEstudiosMedicos());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    NumerosALetras num = new NumerosALetras(Double.parseDouble(df.format(solictudpagoestudiomedico.getMontototal())));
                                    //llenarTablaSolicitud(confirmacionautorizacion.getId_confirmacionAutorizacion());
                                    ReporteC reportec = new ReporteC("");
                                    reportec.generarReporteImpresionSolicitud(solictudpagoestudiomedico.getIdSolicitudPagosEstudiosMedicos(), num.getCantidadString());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                               
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(impr);
                        if(solictudpagoestudiomedico.getEstatusSolicitud()> 1){
                            btnEntrada.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colConfirmacionImprimir.setCellFactory(imprimir);
        
        Callback<TableColumn<SolicitudPagoEstudioMedico, String>, TableCell<SolicitudPagoEstudioMedico, String>> autorizar = (TableColumn<SolicitudPagoEstudioMedico, String> param) -> {
            final TableCell<SolicitudPagoEstudioMedico, String> cell = new TableCell<SolicitudPagoEstudioMedico, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        SolicitudPagoEstudioMedico solictudpagoestudiomedico = getTableView().getItems().get(getIndex());
                        ImageView autto = new ImageView("/img/icons/icons8-marca-de-verificación-50.png");
                        autto.setFitHeight(20);
                        autto.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Autorizar solicitud de compra: " + solictudpagoestudiomedico.getIdSolicitudPagosEstudiosMedicos()+"?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    solictudpagoestudiomedico.setEstatusSolicitud(2);
                                    actualizarEstatus(solictudpagoestudiomedico);
                                    llenarTablaSolicitud(solictudpagoestudiomedico.getIdSolicitudPagosEstudiosMedicos());
                                    tablaConfirmacion.refresh();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(autto);
                        if(solictudpagoestudiomedico.getEstatusSolicitud()> 1){
                            btnEntrada.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colConfirmacionAutorizar.setCellFactory(autorizar);
        
         Callback<TableColumn<SolicitudPagoEstudioMedico, String>, TableCell<SolicitudPagoEstudioMedico, String>> cancelar = (TableColumn<SolicitudPagoEstudioMedico, String> param) -> {
            final TableCell<SolicitudPagoEstudioMedico, String> cell = new TableCell<SolicitudPagoEstudioMedico, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        SolicitudPagoEstudioMedico solicitudpafoEstudioMedico = getTableView().getItems().get(getIndex());
                        ImageView cance = new ImageView("/img/icons/icons8-cancelar-2-50.png");
                        cance.setFitHeight(20);
                        cance.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Cancelar solicitud de compra: " + solicitudpafoEstudioMedico.getIdSolicitudPagosEstudiosMedicos()+"?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    solicitudpafoEstudioMedico.setEstatusSolicitud(0);
                                    actualizarEstatus(solicitudpafoEstudioMedico);
                                    tablaConfirmacion.refresh();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                               
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(cance);
                        if(solicitudpafoEstudioMedico.getEstatusSolicitud()> 1){
                            btnEntrada.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colConfirmacionCancelar.setCellFactory(cancelar);
        
        Callback<TableColumn<SolicitudPagoEstudioMedico, String>, TableCell<SolicitudPagoEstudioMedico, String>> imprimirAuto = (TableColumn<SolicitudPagoEstudioMedico, String> param) -> {
            final TableCell<SolicitudPagoEstudioMedico, String> cell = new TableCell<SolicitudPagoEstudioMedico, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        SolicitudPagoEstudioMedico solicitudestatusmedico = getTableView().getItems().get(getIndex());
                        ImageView impr = new ImageView("/img/icons/icons8-imprimir-30.png");
                        impr.setFitHeight(20);
                        impr.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir autorizacion de copra: " + solicitudestatusmedico.getIdSolicitudPagosEstudiosMedicos());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    NumerosALetras num = new NumerosALetras(Double.parseDouble(df.format(solicitudestatusmedico.getMontototal())));
                                    ReporteC reportec = new ReporteC("");
                                    reportec.generarReporteImpresionAutorizacion(solicitudestatusmedico.getIdSolicitudPagosEstudiosMedicos(), num.getCantidadString());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                               
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(impr);
                        if(solicitudestatusmedico.getEstatusSolicitud()< 2){
                            btnEntrada.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colConfirmacionImpAuto.setCellFactory(imprimirAuto);
    }
    
    private void actualizarEstatus(SolicitudPagoEstudioMedico solicitudpagoestidopmedioco) throws SQLException{
        solicitudestudiosmedicosdao = new SolicitudPagosEstudiosMedicosDAO(conexion.conectar2());
        solicitudestudiosmedicosdao.actualizarSolicitudPagoEstudioMedico(solicitudpagoestidopmedioco);
    }

}
