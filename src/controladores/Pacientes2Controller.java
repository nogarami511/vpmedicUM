/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clase.Paciente;
import clase.TipoVistaPago;
import clases_hospital.AsignacionHabitacion;
import clases_hospital.CitaQuirofano;
import clases_hospital.Consumo;
import clases_hospital.ConsumoHabitacion;
import clases_hospital.CostoHabitacion;
import clases_hospital.CostoHabitacionDAO;
import clases_hospital.Folio;
import clases_hospital.Habitacion;
import clases_hospital.NumerosALetras;
import clases_hospital.ReporteCierre;
import clases_hospital_DAO.ActualizacionFolioDAO;
import clases_hospital_DAO.AsignacionHabitacionDAO;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.ConsumoHabitacionDAO;
import clases_hospital_DAO.ConsumoPacienteMezclasDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.HabitacionDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.PagosDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.PaquetesMedicosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import vistasAuxiliares.PacienteNuevo2Controller;
import vistasAuxiliares_hospital.HorasMedicasHabitacionEnfermeriaController;
import vistasAuxiliares_hospital.ModuloPagosFXMLController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class Pacientes2Controller implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
    ObservableList<TipoVistaPago> tipoVistaPagos = FXCollections.observableArrayList();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaWarning = new Alert(Alert.AlertType.WARNING);

    FoliosDAO foliosDAO;
    PaqueteMedicoDAO paqueteMedicoDAO;
    ConsumosDAO consumosDAO;
    PagosDAO pagodao;
    ActualizacionFolioDAO actualizarfoliodao;
    CitaQuirofanoDAO citaQuirofanodao;
    ConsumoPacienteMezclasDAO consumoPacienteMezclasDAO;
    PaquetesMedicosDAO paquetesMedicosDAO;
    IndicaspDAO indicaspDAO;

    CitaQuirofano citaQuirofano;
    Folio folio;

    double abonoTotal;
    double adeudo;
    double totaconiva;
    int id_CitaQuirofano, filtro = 1;

    @FXML
    private TextField txfBuscarPaciente;
    @FXML
    private TableColumn nombrePaciente;
    @FXML
    private TableColumn telefonoPaciente;
    @FXML
    private TableColumn nombreMedico;
    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn colEntrada;
    @FXML
    private TableColumn colAlta;
    @FXML
    private TableColumn colImprimir;
    @FXML
    private TableColumn colServicios;
    @FXML
    private ComboBox<TipoVistaPago> cbxfiltro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla(filtro);
            llenarcbx();
            nombrePaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
            telefonoPaciente.setCellValueFactory(new PropertyValueFactory("telefono"));
            nombreMedico.setCellValueFactory(new PropertyValueFactory("nombreMedico"));
            nombrePaciente.setStyle("-fx-alignment: CENTER;");
            telefonoPaciente.setStyle("-fx-alignment: CENTER;");
            nombreMedico.setStyle("-fx-alignment: CENTER;");
            colEntrada.setStyle("-fx-alignment: CENTER;");
            colAlta.setStyle("-fx-alignment: CENTER;");
            colImprimir.setStyle("-fx-alignment: CENTER;");
            colServicios.setStyle("-fx-alignment: CENTER;");
            generarBotones();
            txfBuscarPaciente.setOnKeyReleased(e -> filtrarLista(txfBuscarPaciente.getText()));
        } catch (SQLException ex) {
            Logger.getLogger(Pacientes2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarTabla(int filtro) throws SQLException {
        if (VPMedicaPlaza.userNameSystem.equals("ENFERMERIA") || VPMedicaPlaza.userNameSystem.equals("MEDICO") ) {
            colAlta.setVisible(false);
        }
        pacientes.clear();
        con = conexion.conectar2();
        PacientesDAO pacientesDAO = new PacientesDAO(con);
        pacientes.addAll(pacientesDAO.LlenarTabloporFiltrodePacientes(filtro));
        tabla.setItems(pacientes);
        //  con.close();
    }

    public void llenarcbx() {
        List<TipoVistaPago> filtroList = new ArrayList<>();
        filtroList.add(new TipoVistaPago(1, "HABITACIÓN"));
        filtroList.add(new TipoVistaPago(2, "APARTADO"));
        filtroList.add(new TipoVistaPago(3, "GENERAL"));

        tipoVistaPagos.addAll(filtroList);
        cbxfiltro.setItems(tipoVistaPagos);
    }

    @FXML
    private void agregarPacienteNuevo(ActionEvent event) throws IOException, SQLException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/PacienteNuevo2.fxml"));
        Parent root = loader.load();
        PacienteNuevo2Controller destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        Paciente paciente = tabla.getSelectionModel().getSelectedItem();

        // Pasar el objeto a la vista de destino
        destinoController.setObjetoIngresarNuevo(pacientes);

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("PACIENTE NUEVO");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);
        destinoStage.setResizable(false);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

        llenarTabla(filtro);
    }

    @FXML
    private void reingreso(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/ReIngreso.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("PACIENTE NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla(filtro);
//        // Cargar la vista de destino
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/ReIngreso.fxml"));
//        Parent root = loader.load();
//       
//
//        // Obtener el objeto de la vista de origen
//      
//
//        // Pasar el objeto a la vista de destino
//       
//
//        // Crear un nuevo Stage para la vista de destino
//        Stage destinoStage = new Stage();
//        destinoStage.setTitle("PACIENTE NUEVO");
//        destinoStage.setScene(new Scene(root));
//        destinoStage.initModality(Modality.APPLICATION_MODAL);
//        destinoStage.setResizable(false);
//
//        // Mostrar el nuevo Stage de forma modal
//        destinoStage.showAndWait();

    }

    @FXML
    private void editarPaciente(ActionEvent event) throws IOException, SQLException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/PacienteNuevo2.fxml"));
        Parent root = loader.load();
        PacienteNuevo2Controller destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        Paciente paciente = tabla.getSelectionModel().getSelectedItem();

        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(paciente.getIdPaciente());

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("EDITAR PACIENTE");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

        llenarTabla(filtro);
    }

    @FXML
    private void cancelar(ActionEvent event) {

    }

    private void generarBotones() {
        FoliosDAO foliodao = new FoliosDAO(conexion.conectar2());

        //generarColumnaComplemento(foliodao);
        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> entrada = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
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
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de confirmar el ingrso de: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    Folio folio = foliodao.obtenerFolioPorId(paciente.getIdfolio());
                                    folio.setId_estatus_hospitalizacion(1);
                                    folio.setId_estatus_folio(1);
                                    alertaConfirmacion.setHeaderText(null);
                                    alertaConfirmacion.setTitle("Confirmación");
                                    alertaConfirmacion.setContentText("Se ha confirmar el ingrso de: " + paciente.getNombre());
                                    alertaConfirmacion.showAndWait();
                                    foliodao.actualizarFolioFechaIngreso(folio);
                                    llenarTabla(filtro);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(entrada);
                        switch (paciente.getId_estatus_hospitalizacion()) {
                            case 0:
                                btnEntrada.setDisable(false);
                                break;
                            case 1:
                                btnEntrada.setDisable(true);
                                break;
                            case 2:
                                btnEntrada.setDisable(true);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colEntrada.setCellFactory(entrada);

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> alta = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnSalida = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView altapaciente = new ImageView("/img/icons/icons8-salida-50.png");
                        altapaciente.setFitHeight(20);
                        altapaciente.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnSalida.setOnAction(event -> {

                            try {
                                Folio folio = foliodao.obtenerFolioPorId(paciente.getIdfolio());

                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de confirmar el Alta de: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {

                                    con = conexion.conectar2();
                                    indicaspDAO = new IndicaspDAO(con);

                                    int size = indicaspDAO.indicasPSize(folio.getId());
                                    int insumosValidados = indicaspDAO.indicasPValidados(folio.getId());
                                    if (insumosValidados == size) {
                                        //formato cuenta detalle
                                        Connection connection = null;
                                        connection = conexion.conectar2();
                                        LocalDate fechaLocal = LocalDate.now();
                                        Date fecha_ingreso = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                        java.sql.Date fechaSQL_ingreso = new java.sql.Date(fecha_ingreso.getTime());
                                        folio.setId_estatus_hospitalizacion(2);//2 alta
                                        folio.setId_estatus_folio(0);//0 inactivo, 1 activo

                                        PagosDAO pagosDAO = new PagosDAO(conexion.conectar2());
                                        compararPaquete(paciente);
                                        foliodao.ALTAPACIENTE(folio.getId(), VPMedicaPlaza.userSystem);
//                                    ReporteCierre rptCierre = pagosDAO.datosReporteCierre(folio.getId());
//                                    reporte(rptCierre, folio, paciente.getNombre());
                                        actualizarFolio(paciente);
                                        reporteDetallado(paciente);

                                        foliodao.actualizarFolio(folio);
                                        llenarTabla(filtro);

                                        connection.close();
                                    } else {
                                        alertaError.setHeaderText(null);
                                        alertaError.setTitle("ALERTA");
                                        alertaError.setContentText("HACEN FALTA VERIFICAR INUSMOS, COMUNICARSE A FARMACIA PARA MAS INFORMACION\nEXTENCION: 104");
                                        alertaError.showAndWait();
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        setGraphic(btnSalida);
                        setText(null);
                        btnSalida.setGraphic(altapaciente);
                        switch (paciente.getId_estatus_hospitalizacion()) {
                            case 0:
                                btnSalida.setDisable(true);
                                break;
                            case 1:
                                btnSalida.setDisable(false);
                                break;
                            case 2:
                                btnSalida.setDisable(true);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colAlta.setCellFactory(alta);

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> imprimir = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnReingreso = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView editar = new ImageView("/img/icons/icons8-imprimir-30.png");
                        editar.setFitHeight(20);
                        editar.setFitWidth(20);

                        btnReingreso.setOnAction(event -> {

                            try {
                                FoliosDAO foliodao = new FoliosDAO(conexion.conectar2());
                                Folio folio = foliodao.obtenerFolioPorId(paciente.getIdfolio());

//                                alertaConfirmacion.setHeaderText(null);
//                                alertaConfirmacion.setTitle("Confirmación");
//                                alertaConfirmacion.setContentText("¿IMPRIMIR INFORMACION DE : " + paciente.getNombre() + " ?");
//                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
//                                if (action.get() == ButtonType.OK) {
                                if (folio.getNumero_habitacion() != 0) {
                                    imprimirPulcera(paciente.getIdPaciente());
                                } else {
                                    alertaWarning.setHeaderText("PACIENTE SIN HABITACION");
                                    alertaWarning.setContentText("SE LE DEBE ASIGNAR HABITACION AL PACIENTE\n"
                                            + "PARA PODER IMPRIMIR LA PULSERA");
                                    alertaWarning.showAndWait();
                                }
                                //}
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });

                        setGraphic(btnReingreso);
                        setText(null);

                        setGraphic(btnReingreso);
                        setText(null);
                        btnReingreso.setGraphic(editar);
                        switch (paciente.getId_estatus_hospitalizacion()) {
                            case 0:
                                btnReingreso.setDisable(true);
                                break;
                            case 1:
                                btnReingreso.setDisable(false);
                                break;
                            case 2:
                                btnReingreso.setDisable(true);
                                break;
                        }

                    }
                }
            };
            return cell;
        };

        colImprimir.setCellFactory(imprimir);

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> servicios = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnReingreso = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView servicios = new ImageView("/img/icons/icons8-imprimir-30.png");
                        servicios.setFitHeight(20);
                        servicios.setFitWidth(20);

                        btnReingreso.setOnAction(event -> {

                            try {

                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿AGREGAR SERCICIOS A LA CUENTA DE: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    FoliosDAO foliodao = new FoliosDAO(conexion.conectar2());
                                    Folio folio = foliodao.obtenerFolioPorId(paciente.getIdfolio());
                                    
                                    llamarModuloServiciosNuevo(folio);
                                    
                                }
                                //}
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });

                        setGraphic(btnReingreso);
                        setText(null);

                        setGraphic(btnReingreso);
                        setText(null);
                        btnReingreso.setGraphic(servicios);
                        switch (paciente.getId_estatus_hospitalizacion()) {
                            case 0:
                                btnReingreso.setDisable(true);
                                break;
                            case 1:
                                btnReingreso.setDisable(false);
                                break;
                            case 2:
                                btnReingreso.setDisable(true);
                                break;
                        }

                    }
                }
            };
            return cell;
        };

        colServicios.setCellFactory(servicios);

    }

    private void llamarModuloPagos(Folio folio) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ModuloPagosFXML.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);
            ModuloPagosFXMLController generarPago = fxml.getController();

            generarPago.llenarTablaCaja(folio);
            Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);
            stage.setTitle("PACIENTE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
            llenarTabla(filtro);
        } catch (IOException ex) {
            Logger.getLogger(PacientesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PacientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void llamarModuloServiciosNuevo(Folio folio) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/HorasMedicasHabitacionEnfermeria.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);
            HorasMedicasHabitacionEnfermeriaController generarPago = fxml.getController();

            generarPago.optenerDatos(folio);
            Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);
            stage.setTitle("PACIENTE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
            llenarTabla(filtro);
        } catch (IOException ex) {
            Logger.getLogger(PacientesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PacientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reporte(ReporteCierre rptCierre, Folio folio, String nombre) {
        NumerosALetras numeroLetras = new NumerosALetras(rptCierre.getMontoHaastaElMomento());
        String numeroALetra = numeroLetras.getCantidadString();

        ReporteC reporte = new ReporteC("Rpt_CorteCierreCuenta");
        reporte.generarCierre(folio.getId(),
                rptCierre.getFolio(),
                rptCierre.getFecha(),
                rptCierre.getHora(),
                rptCierre.getPaciente(),
                rptCierre.getMedico(),
                rptCierre.getHabitacion(),
                rptCierre.getTipoPaquete(),
                rptCierre.getDepocito(),
                rptCierre.getMontoHaastaElMomento(),
                rptCierre.getSaldoACubrir(),
                rptCierre.getFormaDePago(),
                rptCierre.getConcepto(),
                rptCierre.getIdPaquete(),
                numeroALetra);

        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("Se ha confirmar el Alta de: " + nombre);
        alertaConfirmacion.showAndWait();
    }

    private void imprimirPulcera(int id) {
        ReporteC repc = new ReporteC("Rpt_Pulcera_paciente");
        repc.generarPulcera(id);
    }

    private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(pacientes);
        } else {
            ObservableList<Paciente> listaFiltrada = FXCollections.observableArrayList();
            for (Paciente paciente : pacientes) {
                if (paciente.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(paciente);
                }
            }
            tabla.setItems(listaFiltrada);
        }
    }

    private void actualizarFolio(Paciente paciente) {
        con = conexion.conectar2();
        try {
            pagodao = new PagosDAO(con);
            foliosDAO = new FoliosDAO(con);
            actualizarfoliodao = new ActualizacionFolioDAO(con);
            citaQuirofanodao = new CitaQuirofanoDAO(con);

            citaQuirofano = citaQuirofanodao.obteCitaQuirofanoPorIdFolio(paciente.getIdfolio());
            folio = foliosDAO.obtenerFolioPorId(paciente.getIdfolio());

            if (citaQuirofano == null) {
                id_CitaQuirofano = 0;
            } else {
                id_CitaQuirofano = citaQuirofano.getIdQuirofano();
            }
            double sumatoriatotal = actualizarfoliodao.calculateTotalSubtotal(actualizarfoliodao.getReportItems(paciente.getIdfolio()));
            System.out.println("LA SUMATORIA ES: " + sumatoriatotal);
            abonoTotal = pagodao.optenerTotalPagosPaciente(paciente.getIdfolio());
            adeudo = (sumatoriatotal * 1.16) - abonoTotal;
            totaconiva = sumatoriatotal * 1.16;

            folio.setMontoHastaElMomento(sumatoriatotal);
            //double saldoacubir = totaconiva - abonoTotal;
            //folio.setSaldoACubrir(saldoacubir);
            foliosDAO.actualizarFolio(folio);

        } catch (SQLException ex) {
            Logger.getLogger(Pacientes2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reporteDetallado(Paciente paciente) {
        con = conexion.conectar2();
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

            foliosDAO.actualizarHoraSalida(folio.getId(), folio.getId_habitacion(), folio.getNumero_habitacion());
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
            ReporteC reporte = new ReporteC("Rpt_CorteDetalleCuenta_1_Alta");
            reporte.generarCorteDetalles1Alta(folio.getId(),
                    folio.getId_paquete(),
                    numeroALetra.toUpperCase(),
                    idQuirofano,
                    folio.getId_habitacion(),
                    folio.getNumero_habitacion()
            );
        }
    }

    private void compararPaquete(Paciente paciente) {
        con = conexion.conectar2();
        foliosDAO = new FoliosDAO(con);
        consumosDAO = new ConsumosDAO(con);
        paqueteMedicoDAO = new PaqueteMedicoDAO(con);
        paquetesMedicosDAO = new PaquetesMedicosDAO(con);

        // Folio folio;
        List<Consumo> consumos = new ArrayList<>();

        try {
            folio = foliosDAO.obtenerFolio(paciente.getIdfolio());
            {
                double precioCostoPaquete = paquetesMedicosDAO.precioCostoPaquete(folio.getId_paquete());
                // System.out.println("precioCostoPaquete :"+ precioCostoPaquete);
//                Double sumatoriaPaquete = consumosDAO.sumatoriaTotalPaquete(paciente.getIdfolio());
                Double precioPaquete = paqueteMedicoDAO.leer(folio.getId_paquete()).getPrecio();
                //System.out.println("preciopaquete"+ precioPaquete);
                consumos.addAll(consumosDAO.obtenerTodosConsumosPaquetePorFolio(paciente.getIdfolio()));
                if (precioCostoPaquete >= precioPaquete) {
                    System.out.println("entro al primer if");
                    List<Consumo> consumoLista = new ArrayList<>();
                    double canitad;
                    for (int i = 0; i < consumos.size(); i++) {
                        System.out.println("-1");
                        canitad = consumos.get(i).getCantidad();
                        for (int j = 0; j < canitad; j++) {

                            Consumo consumoAux = consumos.get(i);
                            consumoAux.setCantidad(1);
                            consumoAux.setMonto(consumos.get(i).getPrecioUnitario());
                            consumoLista.add(consumoAux);

                        }
                    }
                    // System.out.println("antes de el if a while - "+ consumoLista.toString());
                    if (!consumoLista.isEmpty()) {

                        int i = 0;
                        while (precioCostoPaquete > precioPaquete) {
                            if ((consumoLista.get(i).getPrecioUnitarioPaquete() + precioPaquete) <= precioCostoPaquete) {
                                //  System.out.println("entrando al while de cambio a paquete");
                                precioCostoPaquete += consumoLista.get(i).getPrecioUnitarioPaquete();
                                consumoLista.get(i).setPaquete(true);
                                consumosDAO.actualizarConsumoPorCambioPaquetePaquete(consumoLista.get(i));
                            }
                            if (consumoLista.size() == (i + 1)) {
                                precioCostoPaquete = precioPaquete;
                            }
                            i++;
                        }
                    }

                }

            }
//            consumo = consumosDAO.obtenerTodosConsumosPorFolio(paciente.getIdfolio());

        } catch (SQLException e) {
        }
    }

    private void compararPaquete2(Paciente paciente) {
        con = conexion.conectar2();
        foliosDAO = new FoliosDAO(con);
        consumosDAO = new ConsumosDAO(con);
        paqueteMedicoDAO = new PaqueteMedicoDAO(con);
        paquetesMedicosDAO = new PaquetesMedicosDAO(con);

        // Folio folio;
        List<Consumo> consumos = new ArrayList<>();

        try {
            folio = foliosDAO.obtenerFolio(paciente.getIdfolio());

            double precioCostoPaquete = paquetesMedicosDAO.precioCostoPaquete(folio.getId_paquete());
//              Double sumatoriaPaquete = consumosDAO.sumatoriaTotalPaquete(paciente.getIdfolio());
            Double precioPaquete = paqueteMedicoDAO.leer(folio.getId_paquete()).getPrecio();
            consumos.addAll(consumosDAO.obtenerTodosConsumosPaquetePorFolio(paciente.getIdfolio()));
            if (precioCostoPaquete <= precioPaquete) {
                List<Consumo> consumoLista = new ArrayList<>();
                double canitad;
                for (int i = 0; i < consumos.size(); i++) {
                    canitad = consumos.get(i).getCantidad();
                    for (int j = 0; j < canitad; j++) {

                        Consumo consumoAux = consumos.get(i);
                        consumoAux.setCantidad(1);
                        consumoAux.setMonto(consumos.get(i).getPrecioUnitario());
                        consumoLista.add(consumoAux);

                    }
                }

                if (!consumoLista.isEmpty()) {

                    int i = 0;
                    // redundancia en consumoLista dado que varias tienen el mismo id, eso puede hacer que varie que es lo que toma
                    // ademas esto no considera el costo de habitacion
                    while (precioCostoPaquete < precioPaquete) {
                        if ((consumoLista.get(i).getPrecioUnitarioPaquete() + precioCostoPaquete) <= precioPaquete) {
                            precioCostoPaquete += consumoLista.get(i).getPrecioUnitarioPaquete();
                            consumoLista.get(i).setPaquete(true);
                            consumosDAO.actualizarConsumoPorCambioPaquetePaquete(consumoLista.get(i));
                        }
                        if (consumoLista.size() == (i + 1)) {
                            precioCostoPaquete = precioPaquete;
                        }
                        i++;
                    }
                }

            }

//            consumo = consumosDAO.obtenerTodosConsumosPorFolio(paciente.getIdfolio());
        } catch (SQLException e) {
        }
    }

    @FXML
    private void filtrar(ActionEvent event) throws SQLException {
        llenarTabla(cbxfiltro.getValue().getId_tipo());
    }

}
