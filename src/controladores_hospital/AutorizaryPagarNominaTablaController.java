/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.AutorizarPagoNomina;
import clases_hospital.FondoEfectivo;
import clases_hospital_DAO.FondoEfectivoDAO;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import reportes.Reporte;
//import reportes.Reporte;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AutorizaryPagarNominaTablaController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    ObservableList<AutorizarPagoNomina> autorizarPagsoNominas = FXCollections.observableArrayList();
    private String mes;
    private int year;

    @FXML
    private TableView<AutorizarPagoNomina> tabla;
    @FXML
    private TableColumn colClave;
    @FXML
    private TableColumn colTipoNomina;
    @FXML
    private TableColumn colPerioridad;
    @FXML
    private TableColumn colEjercicioFiscal;
    @FXML
    private TableColumn colFechaInicial;
    @FXML
    private TableColumn colFechaFinal;
    @FXML
    private TableColumn colFechaCalculo;
    @FXML
    private TableColumn colFechaAutorizado;
    @FXML
    private TableColumn colFechaPago;
    @FXML
    private TableColumn colTotal;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colAutorizar;
    @FXML
    private TableColumn colPagar;
    @FXML
    private TableColumn colDesautorizar;
    @FXML
    private TableColumn colImprimimirReporte;
    @FXML
    private TableColumn colImprimirrecibos;
    @FXML
    private ComboBox cmbMes;
    @FXML
    private ComboBox cmbYear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarcmb();
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(AutorizaryPagarNominaTablaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        this.tabla.getItems().clear();
        this.autorizarPagsoNominas.clear();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM autorizarypagosnominas a WHERE MONTH(a.fechacaluclo) = ? AND a.ejercisiofiscal = ?");
        stmt.setString(1, mes);
        stmt.setInt(2, year);
        ResultSet rs = stmt.executeQuery();

        AutorizarPagoNomina autorizarNominaPago;

//        System.out.print("MIRAME MIRAME MIRAME --" + rs.next() + "-- MIRAME MIRAME MIRAME");
        try {
            while (rs.next()) {
                
                autorizarNominaPago = new AutorizarPagoNomina();
                autorizarNominaPago.setId(rs.getInt(1));
                autorizarNominaPago.setClavenomina(rs.getString(2));
                autorizarNominaPago.setTipo_nomina(rs.getString(3));
                autorizarNominaPago.setPerioridad(rs.getString(4));
                autorizarNominaPago.setEjercisiofiscal(rs.getInt(5));
                autorizarNominaPago.setFechainicio(rs.getDate(6));
                autorizarNominaPago.setFechafin(rs.getDate(7));
                autorizarNominaPago.setFechacaluclo(rs.getDate(8));
                autorizarNominaPago.setFechaautorizado(rs.getDate(9));
                autorizarNominaPago.setFechapago(rs.getDate(10));
                autorizarNominaPago.setTotal(rs.getDouble(11));
                autorizarNominaPago.setEstatus(rs.getInt(12));
                autorizarNominaPago.setStringStatus(sEstatus(rs.getInt(12)));
         
                autorizarPagsoNominas.add(autorizarNominaPago);
            }

//            private int id, usuario_modifiicacion, , estatus;
//    private String , , ;
//    private Date  fechainicio, , , , ;
//    private double total;
            colClave.setCellValueFactory(new PropertyValueFactory("clavenomina"));
            colTipoNomina.setCellValueFactory(new PropertyValueFactory("tipo_nomina"));
            colPerioridad.setCellValueFactory(new PropertyValueFactory("perioridad"));
            colEjercicioFiscal.setCellValueFactory(new PropertyValueFactory("ejercisiofiscal"));
            colFechaInicial.setCellValueFactory(new PropertyValueFactory("formatterFechaInicio"));
            colFechaFinal.setCellValueFactory(new PropertyValueFactory("formatterFechaFin"));
            colFechaCalculo.setCellValueFactory(new PropertyValueFactory("formatterFechacaluclo"));
            colFechaAutorizado.setCellValueFactory(new PropertyValueFactory("formatterFechaautorizado"));
            colFechaPago.setCellValueFactory(new PropertyValueFactory("formatterFechapago"));
            colTotal.setCellValueFactory(new PropertyValueFactory("total"));
            colEstatus.setCellValueFactory(new PropertyValueFactory("stringStatus"));

            generarBotonesTabla();

            tabla.setItems(autorizarPagsoNominas);
            centrarTextoTabla();
            pintarTabla();
            con.close();
        } catch (Exception e) {
        }
    }

    private void llenarcmb() {
        cmbMes.getItems().addAll(
                "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO",
                "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"
        );
        Calendar calendario = Calendar.getInstance();
        int mesActual = calendario.get(Calendar.MONTH) + 1;
        
        String stringMesSeleccionado = "";
        switch (mesActual) {
            case 1:
                stringMesSeleccionado = "ENERO";
                break;
            case 2:
                stringMesSeleccionado = "FEBRERO";
                break;
            case 3:
                stringMesSeleccionado = "MARZO";
                break;
            case 4:
                stringMesSeleccionado = "ABRIL";
                break;
            case 5:
                stringMesSeleccionado = "MAYO";
                break;
            case 6:
                stringMesSeleccionado = "JUNIO";
                break;
            case 7:
                stringMesSeleccionado = "JULIO";
                break;
            case 8:
                stringMesSeleccionado = "AGOSTO";
                break;
            case 9:
                stringMesSeleccionado = "SEPTIEMBRE";
                break;
            case 10:
                stringMesSeleccionado = "OCTUBRE";
                break;
            case 11:
                stringMesSeleccionado = "NOVIEMBRE";
                break;
            case 12:
                stringMesSeleccionado = "DICIEMBRE";
                break;
        }
        cmbMes.setValue(stringMesSeleccionado);
        mes = "" + mesActual;

        year = Year.now().getValue();
       
        for (int i = Year.now().getValue(); i >= 2023; i--) {
            cmbYear.getItems().add(i);
        }
        cmbYear.setValue(Year.now().getValue());

        cmbMes.setOnAction(e -> {
            try {
                int valorcmb = cmbMes.getSelectionModel().getSelectedIndex() + 1;
                if (cmbMes.getSelectionModel().getSelectedIndex() <= 9) {
                    mes = "0" + valorcmb;
                } else {
                    mes = "" + valorcmb;
                }
                System.out.print("HOLA MUNDO CON MES CON VALOR: " + mes + " Y  AÑO CON VALOR: " + year);
                llenarTabla();
            } catch (SQLException ex) {
                Logger.getLogger(AutorizaryPagarNominaTablaController.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        });

        cmbYear.setOnAction(e -> {
            year = Integer.parseInt(cmbMes.getSelectionModel().getSelectedItem().toString());
            try {
                llenarTabla();
            } catch (SQLException ex) {
                Logger.getLogger(AutorizaryPagarNominaTablaController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        });

    }

    private void generarBotonesTabla() {
        /**
         * GENERAR EL BOTON AUTORIZAR NOMINA EN LA TABLA
         */
        Callback<TableColumn<AutorizarPagoNomina, String>, TableCell<AutorizarPagoNomina, String>> autorizar = (TableColumn<AutorizarPagoNomina, String> param) -> {
            final TableCell<AutorizarPagoNomina, String> cell = new TableCell<AutorizarPagoNomina, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnAutorizar = new Button("");
                        AutorizarPagoNomina autpagnom = getTableView().getItems().get(getIndex());
                        ImageView check = new ImageView("/img/icons/icons8-marca-de-verificación-50.png");
                        check.setFitHeight(20);
                        check.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnAutorizar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de AUTORIZAR la nomina con clave: " + autpagnom.getClavenomina() + "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    cambiarEstus(autpagnom.getClavenomina(), 1);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnAutorizar);
                        setText(null);
                        btnAutorizar.setGraphic(check);
                        switch (autpagnom.getEstatus()) {
                            case 0:
                                btnAutorizar.setDisable(false);
                                break;
                            case 1:
                                btnAutorizar.setDisable(true);
                                break;
                            case 2:
                                btnAutorizar.setDisable(true);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colAutorizar.setCellFactory(autorizar);

        /**
         * GENERAR EL BOTON DE PAGO NOMINA EN LA TABLA
         */
        Callback<TableColumn<AutorizarPagoNomina, String>, TableCell<AutorizarPagoNomina, String>> pagar = (TableColumn<AutorizarPagoNomina, String> param) -> {
            final TableCell<AutorizarPagoNomina, String> cell = new TableCell<AutorizarPagoNomina, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnPago = new Button("");
                        AutorizarPagoNomina autpagnom = getTableView().getItems().get(getIndex());

                        ImageView pago = new ImageView("/img/icons/icons8-paga-48.png");
                        pago.setFitHeight(20);
                        pago.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON PAGO
                         */
                        btnPago.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estás seguro de PAGAR la nómina con clave: " + autpagnom.getClavenomina() + " de un valor de: $" + autpagnom.getTotal() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    cambiarEstus(autpagnom.getClavenomina(), 2);
                                    insertFondoEfectivo(autpagnom.getTotal(), autpagnom.getClavenomina());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnPago);
                        setText(null);
                        btnPago.setGraphic(pago);
                        switch (autpagnom.getEstatus()) {
                            case 0:
                                btnPago.setDisable(true);
                                break;
                            case 1:
                                btnPago.setDisable(false);
                                break;
                            case 2:
                                btnPago.setDisable(true);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colPagar.setCellFactory(pagar);

        /**
         * GENERAR EL BOTON DE IMPRIMIR NOMINA
         */
        Callback<TableColumn<AutorizarPagoNomina, String>, TableCell<AutorizarPagoNomina, String>> imprimirNomina = (TableColumn<AutorizarPagoNomina, String> param) -> {
            final TableCell<AutorizarPagoNomina, String> cell = new TableCell<AutorizarPagoNomina, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnImprimirNomina = new Button("");
                        AutorizarPagoNomina imprimirNomina = getTableView().getItems().get(getIndex());

                        ImageView imprimirnomina = new ImageView("/img/icons/icons8-imprimir-30.png");
                        imprimirnomina.setFitHeight(20);
                        imprimirnomina.setFitWidth(20);

                        btnImprimirNomina.setOnAction(event -> {
                            try {
                                Reporte reporte = new Reporte("ReportNomina");
                                reporte.generarReporte(imprimirNomina.getClavenomina());

                            } catch (Exception e) {
                                e.printStackTrace();
                              
                            }
                        });
                        setGraphic(btnImprimirNomina);
                        setText(null);
                        btnImprimirNomina.setGraphic(imprimirnomina);
                    }
                }
            };
            return cell;
        };

        colImprimimirReporte.setCellFactory(imprimirNomina);

        /**
         * GENERAR EL BOTON DE DESAUTORIZAR NOMINA EN LA TABLA
         */
        Callback<TableColumn<AutorizarPagoNomina, String>, TableCell<AutorizarPagoNomina, String>> desautorizar = (TableColumn<AutorizarPagoNomina, String> param) -> {
            final TableCell<AutorizarPagoNomina, String> cell = new TableCell<AutorizarPagoNomina, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnDesautorizar = new Button("");
                        AutorizarPagoNomina autpagnom = getTableView().getItems().get(getIndex());
                        ImageView cancelar = new ImageView("/img/icons/icons8-cancelar-30.png");
                        cancelar.setFitHeight(20);
                        cancelar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON PAGO
                         */
                        btnDesautorizar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de  ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    cambiarEstus(autpagnom.getClavenomina(), 0);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                              
                            }
                        });
                        setGraphic(btnDesautorizar);
                        setText(null);
                        btnDesautorizar.setGraphic(cancelar);
                        switch (autpagnom.getEstatus()) {
                            case 0:
                                btnDesautorizar.setDisable(true);
                                break;
                            case 1:
                                btnDesautorizar.setDisable(false);
                                break;
                            case 2:
                                btnDesautorizar.setDisable(false);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colDesautorizar.setCellFactory(desautorizar);

        /**
         * GENERAR EL BOTON DE IMPRIMIR NOMINA
         */
        Callback<TableColumn<AutorizarPagoNomina, String>, TableCell<AutorizarPagoNomina, String>> imprimirRecibos = (TableColumn<AutorizarPagoNomina, String> param) -> {
            final TableCell<AutorizarPagoNomina, String> cell = new TableCell<AutorizarPagoNomina, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnImprimirRecibosNomina = new Button("");
                        AutorizarPagoNomina imprimirNominaRecibos = getTableView().getItems().get(getIndex());

                        ImageView imprimirnomina = new ImageView("/img/icons/icons8-imprimir-30.png");
                        imprimirnomina.setFitHeight(20);
                        imprimirnomina.setFitWidth(20);

                        btnImprimirRecibosNomina.setOnAction(event -> {
                            try {
                                Reporte reporte = new Reporte("");
                                reporte.generarRecibosNomina(imprimirNominaRecibos.getClavenomina());

                            } catch (Exception e) {
                                e.printStackTrace();
                               
                            }
                        });
                        setGraphic(btnImprimirRecibosNomina);
                        setText(null);
                        btnImprimirRecibosNomina.setGraphic(imprimirnomina);
                    }
                }
            };
            return cell;
        };

        colImprimirrecibos.setCellFactory(imprimirRecibos);
    }

    private String sEstatus(int estatus) throws SQLException {
        con = conexion.conectar2();
        PreparedStatement stmt = con.prepareStatement("SELECT nombre FROM estatusnominas WHERE id = ?");
        stmt.setInt(1, estatus);
        ResultSet rs = stmt.executeQuery();
        String sestatus = "";

        if (rs.next()) {
            sestatus = rs.getString(1);
        }
        return sestatus;
    }

    private void cambiarEstus(String clave, int estatus) {
        con = conexion.conectar2();
        CallableStatement stmt;
        String callsql = "{call actualizarestatusautorizarypagarnomina (?,?,?)}";

        try {
            stmt = con.prepareCall(callsql);
            stmt.setString(1, clave);
            stmt.setInt(2, estatus);
            stmt.setInt(3, userSystem);
            stmt.execute();
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("Confirmación");
            alertaConfirmacion.setContentText("NOMINA " + sEstatus(estatus) + " CORRECTAMENTE");
            alertaConfirmacion.showAndWait();
            llenarTabla();
        } catch (Exception e) {
        }
    }

    private void pintarTabla() {
        tabla.setRowFactory(tv -> new TableRow<AutorizarPagoNomina>() {
            @Override
            public void updateItem(AutorizarPagoNomina item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getEstatus() == 0) {
                    setStyle("-fx-background-color: #fbdc32; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//SIN AUTORIZAR
                } else if (item.getEstatus() == 1) {
                    setStyle("-fx-background-color: #2ECC71; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//AUTORIZADO
                } else if (item.getEstatus() == 2) {
                    setStyle("-fx-background-color: #E74C3C; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//PAGOADO
                } else {
                    setStyle(" ");
                }

            }
        });
    }

    private void centrarTextoTabla() {
        colClave.setStyle("-fx-alignment: CENTER;");
        colTipoNomina.setStyle("-fx-alignment: CENTER;");
        colPerioridad.setStyle("-fx-alignment: CENTER;");
        colEjercicioFiscal.setStyle("-fx-alignment: CENTER;");
        colFechaInicial.setStyle("-fx-alignment: CENTER;");
        colFechaFinal.setStyle("-fx-alignment: CENTER;");
        colFechaCalculo.setStyle("-fx-alignment: CENTER;");
        colFechaAutorizado.setStyle("-fx-alignment: CENTER;");
        colFechaPago.setStyle("-fx-alignment: CENTER;");
        colTotal.setStyle("-fx-alignment: CENTER;");
        colEstatus.setStyle("-fx-alignment: CENTER;");
        colAutorizar.setStyle("-fx-alignment: CENTER;");
        colPagar.setStyle("-fx-alignment: CENTER;");
        colDesautorizar.setStyle("-fx-alignment: CENTER;");
        colImprimimirReporte.setStyle("-fx-alignment: CENTER;");
        colImprimirrecibos.setStyle("-fx-alignment: CENTER;");
    }

    public void insertFondoEfectivo(double totalpagar, String claveNomina) throws SQLException {
        FondoEfectivoDAO fondoEfectivoDAO = new FondoEfectivoDAO(con);
        FondoEfectivo paraTraerElFEFijo = new FondoEfectivo();
        /*
        int idFolioEfectivo, 
        Timestamp fecha, 
        String tipoOperacion, 
        double importe, 
        double saldo,   
        String concepto  
        int idCliente
         */
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        
        double monto = paraTraerElFEFijo.getTotalFondoEfectivoFijo() - totalpagar;
        FondoEfectivo fondoEfectivo2 = new FondoEfectivo(0, timestamp, "SALIDA", totalpagar, monto, "PAGO NOMINA INTERNA '" + claveNomina + "' ", 0);

        fondoEfectivoDAO.insertarFondoEfectivo(fondoEfectivo2);
    }

}
