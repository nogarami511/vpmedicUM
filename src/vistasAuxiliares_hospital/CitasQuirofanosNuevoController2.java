/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.CitaQuirofano;
import clases_hospital.Folio;
import clases_hospital.Medico;
import clases_hospital.PaqueteMedico;
import clases_hospital.Procedimiento;
import clases_hospital.Quirofano;
import clases_hospital.ServicioAdicional;
import clases_hospital.TipoHabitacion;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.HabitacionDAO;
import clases_hospital_DAO.MedicoDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.ProcedimientoDAO;
import clases_hospital_DAO.QuirofanoDAO;
import clases_hospital_DAO.ServiciosAdicionalesDAO;
import clases_hospital_DAO.TipoHabitacionDAO;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CitasQuirofanosNuevoController2 implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Procedimiento> procedimientos = FXCollections.observableArrayList();
    ObservableList<ServicioAdicional> servicioAdicionales = FXCollections.observableArrayList();
    ObservableList<TipoHabitacion> tipodehabitaciones = FXCollections.observableArrayList();
    ObservableList<Quirofano> quirofanos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Connection conVPMP = conexion.conectar2();
    private String quirofanoConsulta;
    private int idQuirofanoConsulta = 0;
    private String idsProcedimineto = "";
    private String idServiciosAdicionales = "";
    private int idHabitacion;
    private String folioPaciente = "";
    private String paquete = "";
    private String nombremedicoString = "";
    private boolean seleccionarHora = true;

    private int idMedico, idProcedimiento, idPaciente, idServicioAdicional, idFolio, idCitaQuirofano, idPaquete;

    MedicoDAO medicodao;
    ProcedimientoDAO procedimientodao;
    PacientesDAO pacientesdao;
    ServiciosAdicionalesDAO serviciosAdicionalesdao;
    CitaQuirofanoDAO citaquirofanodao;
    FoliosDAO folidao;
    PaqueteMedicoDAO paquetemedicodao;
    QuirofanoDAO quirofanodao;
    HabitacionDAO habitaciondao;
    TipoHabitacionDAO tipohabitaciondao;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnEditar;
    @FXML
    private TextField pacienteCitasQ;
    @FXML
    private TextField contactoCitasQ;
    @FXML
    private DatePicker fechaCitasQ;
    @FXML
    private ComboBox cbmHoraInicio;
    @FXML
    private ComboBox cbmMinutosInicio;
    @FXML
    private ComboBox cbmHoraFin;
    @FXML
    private ComboBox cbmMinutosFin;
    @FXML
    private TextField cirugiaCitasQ;
    @FXML
    private TextField medicoCitasQ;
    @FXML
    private ComboBox<Quirofano> quirofanoCitasQ;
    @FXML
    private TextField serviciosCitaQ;
    @FXML
    private Text lblDuracionHoras;
    @FXML
    private Text lblDuracionMinutos;
    @FXML
    private Button btnServiciosAdicionales;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
//            quirofanoCitasQ.setDisable(true);
//            btnAgregar.setDisable(Strue);
            llenarChoiseBox();
            desactivarFechasPasadas();
        } catch (SQLException ex) {
            Logger.getLogger(CitasQuirofanosNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregar(ActionEvent event) {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        String duaracion = lblDuracionHoras.getText();
        //Inicializamos la variable hora y la seteamos en 0
        int hora;
        //Inicializamos la variable medico y le pasamos el valor que se encuentra en el txt quirofano
        String quirofano = quirofanoCitasQ.getSelectionModel().getSelectedItem().toString();
        for (int i = 0; i < servicioAdicionales.size(); i++) {
            if (i == 0) {
                idServiciosAdicionales = "" + servicioAdicionales.get(i).getId();
            } else {
                idServiciosAdicionales = idServiciosAdicionales + "," + servicioAdicionales.get(i).getId();
            }
        }
        //For para optener el Id de los procedimientos
        for (int i = 0; i < procedimientos.size(); i++) {
            if (i == 0) {
                idsProcedimineto = "" + procedimientos.get(i).getId();
            } else {
                idsProcedimineto = idsProcedimineto + "," + procedimientos.get(i).getId();
            }

        }
        //Inicializamos la variable medico mediante la concatenacion de los valores en el cmb inicio y fin.
        String hInicio = cbmHoraInicio.getSelectionModel().getSelectedItem().toString() + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
        //Se pregunta si el valor de cmb Quirofano es igual a "SANTA RITA"
        if (idQuirofanoConsulta == 1) {
            //En caso de ser true, se coloca la hora de fin a "1 HORA POSTRIOR AL INICO"
            hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 1;
        } else {
            //En caso de ser false, se coloca la hora de fin a "3 HORA POSTRIOR AL INICO"
            hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 3;
        }
        //se setea el valor hora fin el cual sera igual al valor de la variable hora
        String hFin = "";
        //Se pregunta si el valor hora es una unidad o una decena
        if (hora <= 9) {
            //En caso de ser true se agrega un 0 al inicio y se concatena con el cmb de minutos de inico
            hFin = "0" + hora + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
        } else {
            //En caso de ser false se concatena con el cmb de minutos de inico
            hFin = "" + hora + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
        }
        //Inizializamos la variable fechacita al cual le pasaremos el valor del date picker fechaCitasQ
        Date fechaCita = Date.valueOf(fechaCitasQ.getValue());

        try {
            citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());
            CitaQuirofano citaquirofano = new CitaQuirofano();

            java.util.Date utilDateI = formato.parse(hInicio);
            java.util.Date utilDateF = formato.parse(hFin);
            Time horaITime = new Time(utilDateI.getTime());
            Time horaFTime = new Time(utilDateF.getTime());

            citaquirofano.setIdQuirofano(idQuirofanoConsulta);  // ----> hay que revisar esto       
            citaquirofano.setIdMedico(idMedico);
            citaquirofano.setIdPaciente(idPaciente);
            citaquirofano.setIdTipoHabitacion(idHabitacion);    // ----> hayq que revisar esto      
            citaquirofano.setIdServiciosAdicionales("");
            citaquirofano.setCirugia(""+idPaquete);
            citaquirofano.setContacto(contactoCitasQ.getText());
            citaquirofano.setHoraInicio(horaITime);
            citaquirofano.setHoraFin(horaFTime);
            citaquirofano.setDuracionHora(hora);               // -----> hay que revisar esto.      
            citaquirofano.setDuracionMinutos(hora);            // -----> hay que revisar esto.      
            citaquirofano.setFechaCirugia(fechaCita);
            citaquirofano.setId_folios(idFolio);
            citaquirofano.setIdUsuarioModificacion(userSystem);
            citaquirofano.setIdEstatusAgenda(1);
            citaquirofano.setIdEstatusPanelInformacionQuirofano(1);
            citaquirofano.setFecha_ingreso_quirofano(fechaCita);
            citaquirofano.setHora_ingreso_quirofano(horaITime);
            citaquirofano.setHora_salida_quirofano(horaFTime);

            citaquirofanodao.insertarCita(citaquirofano);
//
//            folidao = new FoliosDAO(conexion.conectar2());
//            Folio folio = folidao.obtenerFolioPorId(idFolio);
//
//            int comparaciondefechas = fechaCita.compareTo(fechaHabitacionApartada);
//            if (comparaciondefechas < 0) {// Fecha 1 anterior a fehca 2
//                folio.setFecha_ingreso(fechaHabitacionApartada);
//            } else if (comparaciondefechas > 1) {// Fecha 1 es posterior a fecha 2
//                folio.setFecha_ingreso(fechaCita);
//            } else {// Fecha 1 fecha 2 son iguales
//                folio.setFecha_ingreso(fechaCita);
//            }
//
//            folidao.actualizarFolio(folio);

            alertaSuccess.setHeaderText("Cita Agendada");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            con.close();
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            alertaError.setHeaderText("Error");
            alertaError.setContentText("Error: al ingresar los datos");
            alertaError.showAndWait();

            e.printStackTrace(System.out);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
    }

    @FXML
    private void editar(ActionEvent event) {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        String duaracion = lblDuracionHoras.getText();
        //Inicializamos la variable hora y la seteamos en 0
        int hora;
        //Inicializamos la variable medico y le pasamos el valor que se encuentra en el txt quirofano
        String quirofano = quirofanoCitasQ.getSelectionModel().getSelectedItem().toString();
        for (int i = 0; i < servicioAdicionales.size(); i++) {
            if (i == 0) {
                idServiciosAdicionales = "" + servicioAdicionales.get(i).getId();
            } else {
                idServiciosAdicionales = idServiciosAdicionales + "," + servicioAdicionales.get(i).getId();
            }
        }
        //For para optener el Id de los procedimientos
        for (int i = 0; i < procedimientos.size(); i++) {
            if (i == 0) {
                idsProcedimineto = "" + procedimientos.get(i).getId();
            } else {
                idsProcedimineto = idsProcedimineto + "," + procedimientos.get(i).getId();
            }

        }
        //Inicializamos la variable medico mediante la concatenacion de los valores en el cmb inicio y fin.
        String hInicio = cbmHoraInicio.getSelectionModel().getSelectedItem().toString() + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
        //Se pregunta si el valor de cmb Quirofano es igual a "SANTA RITA"
        if (idQuirofanoConsulta == 1) {
            //En caso de ser true, se coloca la hora de fin a "1 HORA POSTRIOR AL INICO"
            hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 1;
        } else {
            //En caso de ser false, se coloca la hora de fin a "3 HORA POSTRIOR AL INICO"
            hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 3;
        }
        //se setea el valor hora fin el cual sera igual al valor de la variable hora
        String hFin = "";
        //Se pregunta si el valor hora es una unidad o una decena
        if (hora <= 9) {
            //En caso de ser true se agrega un 0 al inicio y se concatena con el cmb de minutos de inico
            hFin = "0" + hora + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
        } else {
            //En caso de ser false se concatena con el cmb de minutos de inico
            hFin = "" + hora + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
        }
        //Inizializamos la variable fechacita al cual le pasaremos el valor del date picker fechaCitasQ
        Date fechaCita = Date.valueOf(fechaCitasQ.getValue());

        try {
            citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());
            CitaQuirofano citaquirofano = new CitaQuirofano();

            java.util.Date utilDateI = formato.parse(hInicio);
            java.util.Date utilDateF = formato.parse(hFin);
            Time horaITime = new Time(utilDateI.getTime());
            Time horaFTime = new Time(utilDateF.getTime());

            citaquirofano.setIdQuirofano(idQuirofanoConsulta);  // ----> hay que revisar esto       
            citaquirofano.setIdMedico(idMedico);
            citaquirofano.setIdPaciente(idPaciente);
            citaquirofano.setIdTipoHabitacion(idHabitacion);    // ----> hayq que revisar esto      
            citaquirofano.setIdServiciosAdicionales("");
            citaquirofano.setCirugia(""+idPaquete);
            citaquirofano.setContacto(contactoCitasQ.getText());
            citaquirofano.setHoraInicio(horaITime);
            citaquirofano.setHoraFin(horaFTime);
            citaquirofano.setDuracionHora(hora);               // -----> hay que revisar esto.      
            citaquirofano.setDuracionMinutos(hora);            // -----> hay que revisar esto.      
            citaquirofano.setFechaCirugia(fechaCita);
            citaquirofano.setId_folios(idFolio);
            citaquirofano.setIdUsuarioModificacion(userSystem);
            citaquirofano.setIdEstatusAgenda(1);
            citaquirofano.setIdEstatusPanelInformacionQuirofano(1);
            citaquirofano.setFecha_ingreso_quirofano(fechaCita);
            citaquirofano.setHora_ingreso_quirofano(horaITime);
            citaquirofano.setHora_salida_quirofano(horaFTime);

            citaquirofanodao.actualizarCita(citaquirofano);

            alertaSuccess.setHeaderText("Cita Agendada");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            con.close();
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            alertaError.setHeaderText("Error");
            alertaError.setContentText("Error: al ingresar los datos");
            alertaError.showAndWait();

            e.printStackTrace(System.out);
        }
    }

    @FXML
    private void serviciosAdicionales(ActionEvent event) {
    }

    private void llenarChoiseBox() throws SQLException {
        con = conexion.conectar2();
        cbmHoraFin.setDisable(true);
        cbmMinutosFin.setDisable(true);
        cbmMinutosInicio.setDisable(true);

        medicodao = new MedicoDAO(con);
        AutoCompletionBinding<Medico> medico = TextFields.bindAutoCompletion(medicoCitasQ, medicodao.getAll());
        medico.setPrefWidth(800);
        medico.setOnAutoCompleted(event -> {
            Medico medicoselect = event.getCompletion();
            idMedico = medicoselect.getId_medico();
        });

        procedimientodao = new ProcedimientoDAO(con);
        AutoCompletionBinding<Procedimiento> procedimiento = TextFields.bindAutoCompletion(cirugiaCitasQ, procedimientodao.getAllProcedimientos());
        procedimiento.setPrefWidth(800);
        procedimiento.setOnAutoCompleted(event -> {
            Procedimiento procedimientoselect = event.getCompletion();
            idProcedimiento = procedimientoselect.getId();
        });

        pacientesdao = new PacientesDAO(con);
        AutoCompletionBinding<Paciente> paciente = TextFields.bindAutoCompletion(pacienteCitasQ, pacientesdao.obtenerTodosPacientesQuirto());
        paciente.setPrefWidth(800);
        paciente.setOnAutoCompleted(event -> {

            try {
                citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());
                folidao = new FoliosDAO(conexion.conectar2());
                paquetemedicodao = new PaqueteMedicoDAO(conexion.conectar2());
                medicodao = new MedicoDAO(conexion.conectar2());

                Paciente pacienteselect = event.getCompletion();
                if (citaquirofanodao.existeCita(pacienteselect.getIdPaciente())) {
                    alertaError.setHeaderText("¡ALERTA!");
                    alertaError.setContentText("ESTE PACIENTE YA TIENE UNA CITA REGISTRADA.\nSI DESEA CAMBIAR SU CITA, POR FAVOR, MODIFIQUE SU REGISTRO.");
                    alertaError.showAndWait();

                    idPaciente = 0;
                    pacienteCitasQ.setText("");
                } else {
                    idPaciente = pacienteselect.getIdPaciente();
                    contactoCitasQ.setText(pacienteselect.getTelefono());
                    idFolio = pacienteselect.getIdfolio();
                    idMedico = pacienteselect.getIdMedico();
                    Medico medicopac = medicodao.leerById(idMedico);
                    nombremedicoString = medicopac.getNombre();
                    Folio foliopac = folidao.obtenerFolio(idFolio);
                    idPaquete = foliopac.getId_paquete();
                    PaqueteMedico paquetemedpac = paquetemedicodao.leer(idPaquete);
                    paquete = paquetemedpac.getNombre();
                    medicoCitasQ.setText(nombremedicoString);
                    cirugiaCitasQ.setText(paquete);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CitasQuirofanosNuevoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        serviciosAdicionalesdao = new ServiciosAdicionalesDAO(con);
        AutoCompletionBinding<ServicioAdicional> servicioadicional = TextFields.bindAutoCompletion(serviciosCitaQ, serviciosAdicionalesdao.getAllServicios());
        servicioadicional.setPrefWidth(800);
        servicioadicional.setOnAutoCompleted(event -> {
            ServicioAdicional servicioAdicionalselect = event.getCompletion();
            idServicioAdicional = servicioAdicionalselect.getId();
        });

        quirofanodao = new QuirofanoDAO(conexion.conectar2());
        quirofanos.addAll(quirofanodao.obtenerTodos());
        quirofanoCitasQ.getItems().addAll(quirofanos);
        quirofanoCitasQ.setOnAction(e -> {
            idQuirofanoConsulta = quirofanoCitasQ.getValue().getId();
        });

        String hh;
        String mm;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hh = "0" + i;
                cbmHoraInicio.getItems().add(hh);
                cbmHoraFin.getItems().add(hh);
            } else {
                cbmHoraInicio.getItems().add(00 + i);
                cbmHoraFin.getItems().add(00 + i);
            }
        }
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                mm = "0" + i;
                cbmMinutosInicio.getItems().add(mm);
                cbmMinutosFin.getItems().add(mm);
            } else {
                cbmMinutosInicio.getItems().add(00 + i);
                cbmMinutosFin.getItems().add(00 + i);
            }
        }

        cbmHoraInicio.setOnAction(e -> {
            if (cbmMinutosInicio.isDisable()) {
                cbmMinutosInicio.setDisable(false);
                horaFin();
            } else {
                horaFin();
            }
        });
        cbmMinutosInicio.setOnAction(e -> {

            cbmHoraFin.setDisable(false);
            cbmMinutosFin.setDisable(false);
            lblDuracionMinutos.setText(cbmMinutosInicio.getSelectionModel().getSelectedItem().toString());
            cbmMinutosFin.setValue(cbmMinutosInicio.getSelectionModel().getSelectedItem().toString());

        });

        cbmHoraFin.setOnAction(e -> {
            seleccionarHora = false;
            int horafin = Integer.parseInt(cbmHoraFin.getSelectionModel().getSelectedItem().toString()) - Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString());
            if (horafin <= 9) {
                lblDuracionHoras.setText("0" + horafin);
            } else {
                lblDuracionHoras.setText("" + horafin);
            }
        });

        cbmMinutosFin.setOnAction(e -> {
            int minuotsfin = Integer.parseInt(cbmMinutosFin.getSelectionModel().getSelectedItem().toString()) - Integer.parseInt(cbmMinutosInicio.getSelectionModel().getSelectedItem().toString());
            if (minuotsfin <= 9) {
                lblDuracionMinutos.setText("0" + minuotsfin);
            } else {
                lblDuracionMinutos.setText("" + minuotsfin);
            }
            
        });

    }
    
    public void horaFin() {
        boolean seleccionarHoraCopia = seleccionarHora;
        int hora;

        if (cbmHoraInicio.getSelectionModel().getSelectedItem() == null) {
            // Manejar la situación en la que no hay ninguna hora de inicio seleccionada
            // Puedes lanzar una excepción, mostrar un mensaje de error, etc.
            return;
        }

        if (quirofanoCitasQ.getItems().isEmpty()) {
            lblDuracionHoras.setText("01");
            hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 1;
            if (hora <= 9) {
                cbmHoraFin.setValue("0" + hora);
            } else {
                if (hora > 23) {
                    cbmHoraFin.setValue("00");
                }
                cbmHoraFin.setValue("" + hora);
            }
        } else {
//            String quirofano = quirofanoCitasQ.getSelectionModel().getSelectedItem().toString();

            int cantidad;
            if (idQuirofanoConsulta == 1) {
                //En caso de ser true, se coloca la hora de fin a "1 HORA POSTRIOR AL INICO"
                hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 1;
                cantidad = 1;
                lblDuracionHoras.setText("01");
            } else {
                //En caso de ser false, se coloca la hora de fin a "3 HORA POSTRIOR AL INICO"
                hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 3;
                cantidad = 3;
                lblDuracionHoras.setText("0" + 3);
            }
            if (hora <= 9) {
                cbmHoraFin.setValue("0" + (Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + cantidad));
            } else {
                if (hora > 20 && cantidad == 3) {
                    switch (hora) {
                        case 21:
                            cbmHoraFin.setValue("00");
                            break;
                        case 22:
                            cbmHoraFin.setValue("01");
                            break;
                        case 23:
                            cbmHoraFin.setValue("02");
                            break;
                    }
                } else if (hora > 23 && cantidad == 1) {
                    cbmHoraFin.setValue("00");
                } else {
                    cbmHoraFin.setValue("" + (Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + cantidad));
                }
            }
        }
        seleccionarHora = seleccionarHoraCopia;
    }
    
    private void desactivarFechasPasadas() {
//        fechaCitasQ.setDayCellFactory(picker -> new DateCell() {
//            @Override
//            public void updateItem(LocalDate date, boolean empty) {
//                super.updateItem(date, empty);
//                LocalDate today = LocalDate.now();
//                setDisable(empty || date.compareTo(today) < 0);
//            }
//        });
    }
    
    public void recuperarDatos(CitaQuirofano citaquirofano) throws SQLException {
        btnEditar.setVisible(true);
        btnAgregar.setVisible(false);
        pacientesdao = new PacientesDAO(conexion.conectar2());
        medicodao = new MedicoDAO(conexion.conectar2());
        tipohabitaciondao = new TipoHabitacionDAO(conexion.conectar2());
        quirofanodao = new QuirofanoDAO(conexion.conectar2());
        Paciente pacienteEditar = pacientesdao.obtenerPacientePorId(citaquirofano.getIdPaciente());
        Medico medicoEditar = medicodao.leerById(citaquirofano.getIdMedico());
        TipoHabitacion tipohabitacion = tipohabitaciondao.read(citaquirofano.getIdTipoHabitacion());
        Quirofano quirofano = quirofanodao.leer(citaquirofano.getIdQuirofano());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTimeHinicio = citaquirofano.getHoraInicio().toLocalTime();
        LocalTime localTimeHfin = citaquirofano.getHoraFin().toLocalTime();

        String horaStringInicio = localTimeHinicio.format(formatter);
        String horaStringFin = localTimeHfin.format(formatter);

        btnAgregar.setVisible(false);
        this.idCitaQuirofano = citaquirofano.getId();

        String[] horaInico = horaStringInicio.split(":");
        cbmHoraInicio.setValue(horaInico[0]);
        cbmMinutosInicio.setValue(horaInico[1]);

        String[] horaFin = horaStringFin.split(":");
        cbmHoraFin.setValue(horaFin[0]);
        cbmMinutosFin.setValue(horaFin[1]);

        idPaciente = citaquirofano.getIdPaciente();
        pacienteCitasQ.setText(pacienteEditar.getNombre());
        contactoCitasQ.setText(citaquirofano.getContacto());
        fechaCitasQ.setValue(citaquirofano.getFechaCirugia().toLocalDate());

        idMedico = citaquirofano.getIdMedico();
        medicoCitasQ.setText(medicoEditar.getNombre());

        idHabitacion = citaquirofano.getIdTipoHabitacion();
        idQuirofanoConsulta = citaquirofano.getIdQuirofano();
        quirofanoCitasQ.setValue(quirofano);

    }
    

}
