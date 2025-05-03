package controladores_hospital;

import clase.Conexion;
import clases_hospital.CitaQuirofano;
import clases_hospital.CuentaPaciente2;
import clases_hospital.Folio;
import clases_hospital.NumerosALetras;
import clases_hospital_DAO.ActualizacionFolioDAO;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.ConsumoPacienteMezclasDAO;
import clases_hospital_DAO.CuentaPacienteDAO2;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.PagosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import reportes.ReporteC;
import vistasAuxiliares_hospital.CambioDEPaqueteController;

public class CuentaPaciente2Controller implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    ObservableList<CuentaPaciente2> pacientesHospitalizados;

    Alert alertError = new Alert(Alert.AlertType.ERROR);
    Alert alertinfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaOpciones = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaWARNING = new Alert(Alert.AlertType.WARNING);

    CitaQuirofano citaQuirofano;
    Folio folio;

    IndicaspDAO indicaspDAO;

    PagosDAO pagodao;
    FoliosDAO foliodao;
    ActualizacionFolioDAO actualizarfoliodao;
    CitaQuirofanoDAO citaQuirofanodao;
    ConsumoPacienteMezclasDAO consumoPacienteMezclasDAO;

    double abonoTotal;
    double adeudo;
    double totaconiva;
    int id_CitaQuirofano;

    @FXML
    private Button btnVercuenta;
    @FXML
    private TableView<CuentaPaciente2> tabla;
    @FXML
    private TableColumn clmPaciente;
    @FXML
    private TableColumn clmHabitacion;
    @FXML
    private Button btnCambioPaquete;
    @FXML
    private TableColumn<CuentaPaciente2, String> colCorteParcial;
    @FXML
    private TableColumn<CuentaPaciente2, String> colHemodinamia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
            clmPaciente.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
            clmHabitacion.setCellValueFactory(new PropertyValueFactory("tipohabitacion"));
            corteParcial();
        } catch (SQLException ex) {
            Logger.getLogger(CuentaPaciente2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void descartarConsumo(ActionEvent event) throws IOException {
        CuentaPaciente2 cuentaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas_hospital/EliminarConsumoPaciente.fxml"));
        Parent root = loader.load();
        EliminarConsumoPacienteController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
//        ConfiguracionPaquete configuracionPaquete = tabla.getSelectionModel().getSelectedItem();
        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(cuentaSeleccionada.getIdFolio());

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setMaximized(true);
        destinoStage.setTitle("CONSUMOS DE PACIENTE");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();
    }

    @FXML
    private void verCuentaPaciente(ActionEvent event) {
        try {
            con = conexion.conectar2();
            indicaspDAO = new IndicaspDAO(con);

            int size = indicaspDAO.indicasPSize(tabla.getSelectionModel().getSelectedItem().getIdFolio());
            int insumosValidados = indicaspDAO.indicasPValidados(tabla.getSelectionModel().getSelectedItem().getIdFolio());
            if (insumosValidados == size) {
                //formato cuenta detalle
                actualizarFolio();
                reporteDetallado();
            } else {
                alertaWARNING.setHeaderText(null);
                alertaWARNING.setTitle("ALERTA");
                alertaWARNING.setContentText("HACEN FALTA VERIFICAR INUSMOS, COMUNICARSE A FARMACIA PARA MAS INFORMACION\nEXTENCION: 104");
                alertaWARNING.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CuentaPaciente2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        CuentaPacienteDAO2 cuentaPacienteDAO2 = new CuentaPacienteDAO2(con);
        pacientesHospitalizados = FXCollections.observableArrayList(cuentaPacienteDAO2.obtenerPacientesHospitalizadosConHemodinamia());
        tabla.setItems(pacientesHospitalizados);
        con.close();
    }

    private void actualizarFolio() {
        con = conexion.conectar2();
        try {
            pagodao = new PagosDAO(con);
            foliodao = new FoliosDAO(con);
            actualizarfoliodao = new ActualizacionFolioDAO(con);
            citaQuirofanodao = new CitaQuirofanoDAO(con);

            CuentaPaciente2 cuentpaciente = tabla.getSelectionModel().getSelectedItem();

            citaQuirofano = citaQuirofanodao.obteCitaQuirofanoPorIdFolio(cuentpaciente.getIdFolio());
            folio = foliodao.obtenerFolioPorId(cuentpaciente.getIdFolio());

            if (citaQuirofano == null) {
                id_CitaQuirofano = 0;
            } else {
                id_CitaQuirofano = citaQuirofano.getIdQuirofano();
            }

            double sumatoriatotal = actualizarfoliodao.calculateTotalSubtotal(actualizarfoliodao.getReportItems(cuentpaciente.getIdFolio()));

            abonoTotal = pagodao.optenerTotalPagosPaciente(cuentpaciente.getIdFolio());
            adeudo = (sumatoriatotal * 1.16) - abonoTotal;
            totaconiva = sumatoriatotal * 1.16;

            folio.setMontoHastaElMomento(sumatoriatotal);
            //double saldoacubir = totaconiva - abonoTotal;
            //folio.setSaldoACubrir(saldoacubir);
            foliodao.actualizarFolio(folio);

        } catch (SQLException ex) {
            Logger.getLogger(CuentaPaciente2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarFolioParcial(CuentaPaciente2 cuentpaciente) {
        con = conexion.conectar2();
        try {
            pagodao = new PagosDAO(con);
            foliodao = new FoliosDAO(con);
            actualizarfoliodao = new ActualizacionFolioDAO(con);
            citaQuirofanodao = new CitaQuirofanoDAO(con);

            citaQuirofano = citaQuirofanodao.obteCitaQuirofanoPorIdFolio(cuentpaciente.getIdFolio());
            folio = foliodao.obtenerFolioPorId(cuentpaciente.getIdFolio());

            if (citaQuirofano == null) {
                id_CitaQuirofano = 0;
            } else {
                id_CitaQuirofano = citaQuirofano.getIdQuirofano();
            }

            double sumatoriatotal = actualizarfoliodao.calculateTotalSubtotal(actualizarfoliodao.getReportItemsParcial(folio.getId_paquete(), cuentpaciente.getIdFolio(), id_CitaQuirofano, folio.getId_habitacion(), folio.getNumero_habitacion()));

            abonoTotal = pagodao.optenerTotalPagosPaciente(cuentpaciente.getIdFolio());
            adeudo = (sumatoriatotal * 1.16) - abonoTotal;
            totaconiva = sumatoriatotal * 1.16;

            folio.setMontoHastaElMomento(sumatoriatotal);
            //double saldoacubir = totaconiva - abonoTotal;
            //folio.setSaldoACubrir(saldoacubir);
            foliodao.actualizarFolio(folio);

        } catch (SQLException ex) {
            Logger.getLogger(CuentaPaciente2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reporteDetallado() {

        consumoPacienteMezclasDAO = new ConsumoPacienteMezclasDAO(conexion.conectar2());
        NumerosALetras numeroLetras = new NumerosALetras(totaconiva);
        String numeroALetra = numeroLetras.getCantidadString();

        int idQuirofano;
        if (citaQuirofano == null) {
            idQuirofano = 0;
        } else {
            idQuirofano = citaQuirofano.getIdQuirofano();
        }

        if (folio.getId_estatus_hospitalizacion() < 2) {
            //SIGNIFICA QUE ESTA EN EL HOSPITAL Y REFRESCA SU HORA DE SALIDA DE LA HABITACION CON UN NOW()

            foliodao.actualizarHoraSalida(folio.getId(), folio.getId_habitacion(), folio.getNumero_habitacion());
            ReporteC reporte = new ReporteC("Rpt_CorteDetalleCuenta");
            reporte.generarCorteDetalles1(folio.getId(),
                    folio.getId_paquete(),
                    numeroALetra.toUpperCase(),
                    idQuirofano,
                    folio.getId_habitacion(),
                    folio.getNumero_habitacion()
            );
        } else {
            //AGARRA LA ULTIMA FECHA DE SALIDA
            ReporteC reporte = new ReporteC("Rpt_CorteDetalleCuenta");
            reporte.generarCorteDetalles1(folio.getId(),
                    folio.getId_paquete(),
                    numeroALetra.toUpperCase(),
                    idQuirofano,
                    folio.getId_habitacion(),
                    folio.getNumero_habitacion()
            );
        }
    }

    @FXML
    private void irCambioPaquete(ActionEvent event) throws IOException, SQLException {

        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CambioDEPaquete.fxml"));
        Parent root = loader.load();
        CambioDEPaqueteController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        CuentaPaciente2 cuentpaciente = tabla.getSelectionModel().getSelectedItem();

        // Pasar el objeto a la vista de destino
        destinoController.recibirDatos(cuentpaciente.getIdFolio(), cuentpaciente.getNombrePaciente(), cuentpaciente.getIdPaciente(), cuentpaciente.getIdPaquete());

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("CAMBIO DE PAQUETE MEDICO");
        destinoStage.setScene(new Scene(root));
        destinoStage.setMaximized(false);
        destinoStage.setResizable(false);
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

    }

    private void corteParcial() {
        //generarColumnaComplemento(foliodao);
        Callback<TableColumn<CuentaPaciente2, String>, TableCell<CuentaPaciente2, String>> corteParcial = (TableColumn<CuentaPaciente2, String> param) -> {
            final TableCell<CuentaPaciente2, String> cell = new TableCell<CuentaPaciente2, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        CuentaPaciente2 cuentpaciente = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaOpciones.setHeaderText(null);
                                alertaOpciones.setTitle("Confirmación");
                                alertaOpciones.setContentText("¿Estas seguro de ver el ingrso de: " + cuentpaciente.getNombrePaciente() + " ?");
                                Optional<ButtonType> action = alertaOpciones.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    actualizarFolioParcial(cuentpaciente);
                                    verConsumoParcial(folio);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(entrada);
                    }
                }
            };
            return cell;
        };

        colCorteParcial.setCellFactory(corteParcial);

        Callback<TableColumn<CuentaPaciente2, String>, TableCell<CuentaPaciente2, String>> hemodinamia = (TableColumn<CuentaPaciente2, String> param) -> {
            final TableCell<CuentaPaciente2, String> cell = new TableCell<CuentaPaciente2, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnHemo = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        CuentaPaciente2 cuentpaciente = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnHemo.setOnAction(event -> {

                            try {
                                alertaOpciones.setHeaderText(null);
                                alertaOpciones.setTitle("Confirmación");
                                alertaOpciones.setContentText("¿Estas seguro de ver el ingrso de: " + cuentpaciente.getNombrePaciente() + " ?");
                                Optional<ButtonType> action = alertaOpciones.showAndWait();
                                if (action.get() == ButtonType.OK) {

                                    int cuentaPacienteInt = cuentpaciente.getIdFolio();
                                    cuentpaciente.setIdFolio(cuentaPacienteInt + 1);
                                    actualizarFolioParcial(cuentpaciente);
                                    verConsumoParcial(folio);
                                    cuentpaciente.setIdFolio(cuentaPacienteInt);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnHemo);
                        setText(null);
                        btnHemo.setGraphic(entrada);
                        if (cuentpaciente.isHemodinamia()) {
                            btnHemo.setDisable(false);
                        } else {
                            btnHemo.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colHemodinamia.setCellFactory(hemodinamia);

    }

    private void verConsumoParcial(Folio folio) {

        try {
            NumerosALetras numeroLetras = new NumerosALetras(totaconiva);
            String numeroALetra = numeroLetras.getCantidadString();
            
            con = conexion.conectar2();
            citaQuirofanodao = new  CitaQuirofanoDAO(con);
            System.out.println(id_CitaQuirofano);
            int idquiro = citaQuirofanodao.obtenerIdQuirofanoPorId(id_CitaQuirofano);
            
            ReporteC reporteParcial = new ReporteC("Rpt_CorteDetalleCuenta_1");
            System.out.println("{ID FOLIO: " + folio.getId() + " ID PAQUETE: " + folio.getId_paquete() + " ID TIPO HABITACION: " + folio.getId_habitacion() + " QUIROFANO: " + idquiro + " NUMERO HABITACION: " + folio.getNumero_habitacion() + "}");
            reporteParcial.generarCorteDetalles1CorteParcial(folio.getId(), folio.getId_paquete(), numeroALetra, idquiro, folio.getId_habitacion(), folio.getNumero_habitacion());
        } catch (SQLException ex) {
            Logger.getLogger(CuentaPaciente2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
