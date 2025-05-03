/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.EstudioMedico;
import clases_hospital.FormaPago;
import clases_hospital.NumerosALetras;
import clases_hospital.SolicitudPagoEstudioMedico;
import clases_hospital_DAO.EstudiosMedicosDAO;
import clases_hospital_DAO.FormaPagoDAO;
import clases_hospital_DAO.SolicitudPagosEstudiosMedicosDAO;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class OrdenesEstudiosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<EstudioMedico> estudiosmedicos = FXCollections.observableArrayList();
    ObservableList<FormaPago> formaspagos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    SolicitudPagosEstudiosMedicosDAO solicitudpagoestudiomedicodao;
    EstudiosMedicosDAO estudiomedicodao;
    FormaPagoDAO formaspagoDAO;

    FormaPago formapago;

    @FXML
    private Button btnBuscarRazonSocial;
    @FXML
    private ComboBox<FormaPago> cmbFomraPago;
    @FXML
    private Label lblTotalSolicitado;
    @FXML
    private TableView<EstudioMedico> tabla;
    @FXML
    private TableColumn<?, ?> colNumero;
    @FXML
    private TableColumn<?, ?> colEstudio;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<?, ?> colLaboratorio;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colMonto;
    @FXML
    private TableColumn<?, ?> colMontoPagado;
    @FXML
    private TableColumn<?, ?> colSaldoSAldado;
    @FXML
    private TableColumn colSolicitar;
    @FXML
    private Button btnSolicitar;
    @FXML
    private TableColumn colImprimir;
    @FXML
    private Button btnGenerarCompra;
    @FXML
    private TextField txfBuscarLaboratorios;
    @FXML
    private Button btnSolicitudPagoEstudio;

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
        } catch (SQLException ex) {
            Logger.getLogger(ComprasInternasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionBuscarRazonSocial(ActionEvent event) {
    }

    @FXML
    private void accionSolicitudPagoEstudio(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ConfirmacionDeSolicitudDePago.fxml"));
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
        SolicitudPagoEstudioMedico solicitudpafoestudiomedico = new SolicitudPagoEstudioMedico();
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
            for (int i = 0; i < estudiosmedicos.size(); i++) {
                if (estudiosmedicos.get(i).isSolicitar() && estudiosmedicos.get(i).getId_solicitud() == 0) {
                    contar++;
                }
            }
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("Confirmación");
            alertaConfirmacion.setContentText("¿Esta seguro que quiere solicitar el pago de: " + contar + " ordenes de compra?");
            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
            if (action.get() == ButtonType.OK) {
                solicitudpafoestudiomedico.setFechaGenerada(sqlDate);
                solicitudpafoestudiomedico.setFormaPago(formapago.getId());
                solicitudpafoestudiomedico.setMontototal(Double.parseDouble(df.format(actualizarLabel())));
                solicitudpafoestudiomedico.setEstatusSolicitud(1);
                solicitudpafoestudiomedico.setSolicitante(VPMedicaPlaza.userSystem);
                solicitudpafoestudiomedico.setUsuarioAutorizacion(0);
                solicitudpafoestudiomedico.setUsuarioModificacion(VPMedicaPlaza.userSystem);
                solicitudpafoestudiomedico.setFechaAutorizacion(sqlDate);

                Connection con = conexion.conectar2();
                solicitudpagoestudiomedicodao = new SolicitudPagosEstudiosMedicosDAO(con);
                estudiomedicodao = new EstudiosMedicosDAO(con);

                int id_solicitud;
                try {
                    id_solicitud = solicitudpagoestudiomedicodao.insertarSolicitudPagoEstudioMedicoYRegresarInt(solicitudpafoestudiomedico);
                    for (int i = 0; i < estudiosmedicos.size(); i++) {
                        if (estudiosmedicos.get(i).isSolicitar() && estudiosmedicos.get(i).getId_solicitud() == 0) {
                            EstudioMedico estudio = estudiosmedicos.get(i);
                            
                            estudio.setId_solicitud(id_solicitud);
                            estudiomedicodao.actualizarEstudioMedicoAbonos(estudio);
                        }
                    }

                    alertaConfirmacion.setHeaderText(null);
                    alertaConfirmacion.setTitle("Confirmación");
                    alertaConfirmacion.setContentText("SOLICITUD DE PAGO GENERADA");
                    alertaConfirmacion.showAndWait();
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
        estudiosmedicos.clear();
        tabla.getItems().clear();
        estudiomedicodao = new EstudiosMedicosDAO(conexion.conectar2());
        estudiosmedicos.addAll(estudiomedicodao.obtenerTodosLosEstudiosMedicosConNombrelaboratoroListosParaSeleccionar());

        colNumero.setCellValueFactory(new PropertyValueFactory("id"));
        colEstudio.setCellValueFactory(new PropertyValueFactory("nombreEstudio"));
        colFolio.setCellValueFactory(new PropertyValueFactory("idFolio"));
        colLaboratorio.setCellValueFactory(new PropertyValueFactory("nombre_comercial_laboratorio"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fechaPedido"));
        colMonto.setCellValueFactory(new PropertyValueFactory("precio_con_iva"));
        colMontoPagado.setCellValueFactory(new PropertyValueFactory("monto_abonado"));
        colSaldoSAldado.setCellValueFactory(new PropertyValueFactory("saldo_saldar"));
        generarRadioButton();
        generarColumnaImprimir();

        tabla.setItems(estudiosmedicos);
    }

    private void generarRadioButton() {
        DecimalFormat df = new DecimalFormat("0.00");

        Callback<TableColumn<EstudioMedico, String>, TableCell<EstudioMedico, String>> solicitar = (TableColumn<EstudioMedico, String> param) -> {
            final TableCell<EstudioMedico, String> cell = new TableCell<EstudioMedico, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton rdbCompra = new RadioButton();
                        EstudioMedico em = getTableView().getItems().get(getIndex());
                        rdbCompra.setSelected(em.isSolicitar());
                        if (em.getId_solicitud() > 0) {
                            rdbCompra.setDisable(true);
                        }
                        rdbCompra.setOnAction(event -> {
                            if (em.isSolicitar()) {
                                em.setSolicitar(false);
                            } else {
                                em.setSolicitar(true);
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
        for (int i = 0; i < estudiosmedicos.size(); i++) {
            if (estudiosmedicos.get(i).isSolicitar()&& estudiosmedicos.get(i).getId_solicitud() == 0) {
                sumaTotal += estudiosmedicos.get(i).getPrecio_con_iva();
            }
        }
        return sumaTotal;
    }

    private void generarColumnaImprimir() {
        Callback<TableColumn<EstudioMedico, String>, TableCell<EstudioMedico, String>> imprimir = (TableColumn<EstudioMedico, String> param) -> {
            final TableCell<EstudioMedico, String> cell = new TableCell<EstudioMedico, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        EstudioMedico estudioMedico = getTableView().getItems().get(getIndex());
                        ImageView impr = new ImageView("/img/icons/icons8-imprimir-30.png");
                        impr.setFitHeight(20);
                        impr.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir orden de copra: " + estudioMedico.getId());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                   
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    NumerosALetras nal = new NumerosALetras(Double.parseDouble(df.format(estudioMedico.getPrecio_con_iva())));
                                   
                                    ReporteC reporte = new ReporteC("");
                                    reporte.generarReporteImpresionreEstudioMedico(estudioMedico.getId(), nal.getCantidadString());
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

}
