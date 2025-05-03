/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.CitaQuirofano;
import clases_hospital.Consumo;
import clases_hospital.Costo;
import clases_hospital.Folio;
import clases_hospital.Medico;
import clases_hospital.PaqueteMedico;
import clases_hospital.Procedimiento;
import clases_hospital.TipoHabitacion;
import clases_hospital.TipoProcedimiento;
import clases_hospital.TipoSangre;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.HabitacionDAO;
import clases_hospital_DAO.MedicoDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.ProcedimientoDAO;
import clases_hospital_DAO.TipoHabitacionDAO;
import clases_hospital_DAO.TipoSangreDAO;
import clases_hospital_DAO.TipoprocedimientoDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vistasAuxiliares_hospital.CitasQuirofanosNuevoController;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class PacienteNuevoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    int idPaciente, idTipoSangre, idMedico, idPaquete, idTipoHabitacion, idTipoCirugia, idProcedimiento, idfoliogenerado, idFolioEditar;
    ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
    ObservableList<Medico> medicos = FXCollections.observableArrayList();
    ObservableList<TipoSangre> tipoSangres = FXCollections.observableArrayList();
    ObservableList<TipoHabitacion> habitaciones = FXCollections.observableArrayList();
    String[] esDonador = {"SI", "NO"};
    private String nombreMedico = "", tipoSangre = "";

    PacientesDAO pacientedao;
    MedicoDAO medicodao;
    TipoSangreDAO tiposangredao;
    FoliosDAO foliodao;
    PaqueteMedicoDAO paquetemedicodao;
    ProcedimientoDAO procedimientodao;
    HabitacionDAO habitaciondao;
    ConsumosDAO consumodao;
    TipoprocedimientoDAO tipoprocedimientodao;
    TipoHabitacionDAO tipohabitaciondao;
    CostosDAO costodao;

    String sfolio;

    private boolean urgencias = false;
    private boolean consultaugenm = false;

    ToggleGroup toglegroupUrgenHospital = new ToggleGroup();
    ToggleGroup toglegroupPaqueAbier = new ToggleGroup();
    ToggleGroup toglegroupHospInter = new ToggleGroup();

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnSalir;
    @FXML
    private ChoiceBox<TipoSangre> pacienteNuevoTipoSangre;
    @FXML
    private Button btnEditar;
    @FXML
    private ComboBox<Medico> cmbMedico;
    @FXML
    private TextField txfnombreIngresado;
    @FXML
    private TextField txfpacienteNuevoDireccion;
    @FXML
    private TextField txfpacienteNuevoCurp;
    @FXML
    private TextField txftelefonoIngresado;
    @FXML
    private TextField txfpacienteNuevoLugarNacimiento;
    @FXML
    private DatePicker dtppacienteNuevoFechaNacimiento;
    @FXML
    private TextField txfpacienteNuevoEdad;
    @FXML
    private TextField txfpacienteNuevoOcupacion;
    @FXML
    private TextField txfpacienteNuevoSexo;
    @FXML
    private TextField txfpacienteNuevoReligion;
    @FXML
    private RadioButton rdbPaquete;
    @FXML
    private TextField txfPaquete;
    @FXML
    private RadioButton rdbAbierta;
    @FXML
    private RadioButton rdbIntervencion;
    @FXML
    private RadioButton rdbHospitalizacion;
    @FXML
    private TextField txfProcedimiento;
    @FXML
    private ComboBox<TipoHabitacion> cmbTipoHabitacion;
    @FXML
    private Button btnIntegrar;
    @FXML
    private RadioButton rdbHospital;
    @FXML
    private RadioButton rdbUrgencias;
    @FXML
    private RadioButton rdbUrgenciasentrada;
    @FXML
    private RadioButton rdbConsulta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            calcuarEdadPAciente();
            llenarcmb();
            llenarCmbTipoHabitacion();
            desactivarCampos();
            desactivarCamposGenerales();
            seleccionToggleGroup();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) throws IOException {

        if (txfnombreIngresado.getText().equals("") || nombreMedico.equals("")) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("LLENE TODOS LOS CAMPOS PARA CONTINUAR");
            alertaError.showAndWait();
        } else {
            try {
                
                btnIngresar.setDisable(true);
              
                Paciente paciente = new Paciente();

                paciente.setCurp("POR INGRESAR");
                paciente.setNombre(txfnombreIngresado.getText().toUpperCase());
                paciente.setProcedencia("POR INGRESAR");
                paciente.setOcupacion("POR INGRESAR");
                paciente.setSexo("POR INGRESAR");
                paciente.setEdad(1);
                paciente.setReligion("POR INGRESAR");
                paciente.setDomicilio("POR INGRESAR");
                paciente.setIdTipoSangre(1);
                paciente.setTelefono(txftelefonoIngresado.getText());
                paciente.setIdMedico(idMedico);
                paciente.setUsuarioModificacion(userSystem);

                pacientedao = new PacientesDAO(conexion.conectar2());
                int idpaciente_generardo = pacientedao.insertarIntPaciente(paciente);
                idfoliogenerado = generarFolio(idpaciente_generardo);
                paciente.setIdPaciente(idpaciente_generardo);
                paciente.setIdfolio(idfoliogenerado);
                pacientedao.actualizarPacienteSinFechaNacimiento(paciente);

                if (urgencias) {
                    if (consultaugenm) {
                        ingresarConsumo(paciente);
                    }
                } else {
                    if (rdbHospitalizacion.isSelected()) {
                    } else {
                        ingresarConsumo(paciente);
                        ingresoQuirofano(paciente);
                    }
                }

                alertaSuccess.setHeaderText("NUEVO PACIENTE INGRESADO");
                alertaSuccess.setContentText("PACIENTE INGRESADO CON EXITO");
                alertaSuccess.showAndWait();

                Stage stage = (Stage) btnIngresar.getScene().getWindow();
                stage.close();

            } catch (SQLException ex) {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("ERROR AL INGRESAR LOS DATOS");
                alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
                alertaError.showAndWait();
                System.err.println(ex);
            }
        }

    }

    @FXML
    private void salir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnSalir.getScene().getWindow();
        stage_actual.close();
    }

    @FXML
    private void actionEditar(ActionEvent event) throws SQLException {
        if (txfnombreIngresado.getText().equals("") || txfpacienteNuevoCurp.getText().equals("") || txfpacienteNuevoLugarNacimiento.getText().equals("") || txfpacienteNuevoOcupacion.getText().equals("") || txfpacienteNuevoSexo.getText().equals("") || txfpacienteNuevoEdad.getText().equals("") || txfpacienteNuevoReligion.getText().equals("") || txfpacienteNuevoDireccion.getText().equals("") || txftelefonoIngresado.getText().equals("") || tipoSangre.equals("") || nombreMedico.equals("")) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("LLENE TODOS LOS CAMPOS PARA CONTINUAR");
            alertaError.showAndWait();
        } else {
            try {
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(idPaciente);
                paciente.setNombre(txfnombreIngresado.getText().toUpperCase());
                paciente.setCurp(txfpacienteNuevoCurp.getText().toUpperCase());
                paciente.setProcedencia(txfpacienteNuevoLugarNacimiento.getText().toUpperCase());
                paciente.setOcupacion(txfpacienteNuevoOcupacion.getText().toUpperCase());
                paciente.setSexo(txfpacienteNuevoSexo.getText().toUpperCase());
                paciente.setFechaNacimiento(java.sql.Date.valueOf(dtppacienteNuevoFechaNacimiento.getValue()));
                paciente.setEdad(Integer.parseInt(txfpacienteNuevoEdad.getText()));
                paciente.setReligion(txfpacienteNuevoReligion.getText());
                paciente.setDomicilio(txfpacienteNuevoDireccion.getText());
                paciente.setTelefono(txftelefonoIngresado.getText());
                paciente.setIdTipoSangre(idTipoSangre);
                paciente.setIdMedico(idMedico);
                paciente.setUsuarioModificacion(userSystem);
                paciente.setIdfolio(idFolioEditar);

             

                pacientedao = new PacientesDAO(conexion.conectar2());
                pacientedao.actualizarPaciente(paciente);

                alertaSuccess.setHeaderText("Paciente Modificado");
                alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
                alertaSuccess.showAndWait();

                Stage stage = (Stage) btnIngresar.getScene().getWindow();
                stage.close();

            } catch (SQLException ex) {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("ERROR AL INGRESAR LOS DATOS");
                alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
                alertaError.showAndWait();
                System.err.println(ex);
            }
        }
    }

    @FXML
    private void accionPaquete(ActionEvent event) throws SQLException {
        txfPaquete.setDisable(false);
        rdbIntervencion.setDisable(true);
        rdbHospitalizacion.setDisable(true);
        txfProcedimiento.setDisable(true);
        cmbTipoHabitacion.setDisable(true);

        rdbHospital.setDisable(true);
        rdbUrgencias.setDisable(true);

        rdbHospitalizacion.setSelected(false);
        rdbIntervencion.setSelected(false);

        rdbHospital.setSelected(false);
        rdbUrgencias.setSelected(false);

        urgencias = false;

        seleccionarPaquete();
    }

    @FXML
    private void accionCuentaAbierta(ActionEvent event) {
        txfPaquete.setDisable(true);
        rdbIntervencion.setDisable(false);
        rdbHospitalizacion.setDisable(false);

        idPaquete = 1;
    }

    @FXML
    private void accionIntervencion(ActionEvent event) throws SQLException {
        txfProcedimiento.setDisable(false);
        cmbTipoHabitacion.setDisable(false);

        seleccionarProcedimiento();
        //llenarCmbTipoHabitacion();

        rdbConsulta.setDisable(true);
        rdbUrgencias.setDisable(true);
        rdbHospital.setDisable(true);

        rdbConsulta.setSelected(false);
        rdbUrgencias.setSelected(false);
        rdbHospital.setSelected(false);
    }

    @FXML
    private void accionHospitalizacion(ActionEvent event) throws SQLException {
        txfProcedimiento.setDisable(true);
        cmbTipoHabitacion.setDisable(false);

        rdbConsulta.setDisable(true);
        rdbUrgencias.setDisable(true);
        rdbHospital.setDisable(true);

        rdbConsulta.setSelected(false);
        rdbUrgencias.setSelected(false);
        rdbHospital.setSelected(false);

        idTipoCirugia = 0;
        //llenarCmbTipoHabitacion();
    }

    @FXML
    private void integrar(ActionEvent event) {
        if (txfnombreIngresado.getText().equals("") || txfpacienteNuevoCurp.getText().equals("") || txfpacienteNuevoLugarNacimiento.getText().equals("") || txfpacienteNuevoOcupacion.getText().equals("") || txfpacienteNuevoSexo.getText().equals("") || txfpacienteNuevoEdad.getText().equals("") || txfpacienteNuevoReligion.getText().equals("") || txfpacienteNuevoDireccion.getText().equals("") || txftelefonoIngresado.getText().equals("") || tipoSangre.equals("") || nombreMedico.equals("")) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("LLENE TODOS LOS CAMPOS PARA CONTINUAR");
            alertaError.showAndWait();
        } else {
            try {
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(idPaciente);
                paciente.setNombre(txfnombreIngresado.getText().toUpperCase());
                paciente.setCurp(txfpacienteNuevoCurp.getText().toUpperCase());
                paciente.setProcedencia(txfpacienteNuevoLugarNacimiento.getText().toUpperCase());
                paciente.setOcupacion(txfpacienteNuevoOcupacion.getText().toUpperCase());
                paciente.setSexo(txfpacienteNuevoSexo.getText().toUpperCase());
                paciente.setFechaNacimiento(java.sql.Date.valueOf(dtppacienteNuevoFechaNacimiento.getValue()));
                paciente.setEdad(Integer.parseInt(txfpacienteNuevoEdad.getText()));
                paciente.setReligion(txfpacienteNuevoReligion.getText());
                paciente.setDomicilio(txfpacienteNuevoDireccion.getText());
                paciente.setTelefono(txftelefonoIngresado.getText());
                paciente.setIdTipoSangre(idTipoSangre);
                paciente.setIdMedico(idMedico);
                paciente.setIdfolio(idFolioEditar);
                paciente.setUsuarioModificacion(userSystem);

           

                pacientedao = new PacientesDAO(conexion.conectar2());
                pacientedao.actualizarPaciente(paciente);

                alertaSuccess.setHeaderText("Paciente Modificado");
                alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
                alertaSuccess.showAndWait();

                Stage stage = (Stage) btnIngresar.getScene().getWindow();
                stage.close();

            } catch (SQLException ex) {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("ERROR AL INGRESAR LOS DATOS");
                alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
                alertaError.showAndWait();
                System.err.println(ex);
            }
        }
    }

    @FXML
    private void accionHospital(ActionEvent event) throws SQLException {
        urgencias = false;

        txfProcedimiento.setDisable(false);
        cmbTipoHabitacion.setDisable(false);
        rdbHospital.setDisable(false);
        rdbUrgencias.setDisable(false);
        rdbConsulta.setDisable(false);

        seleccionarProcedimiento();
        //llenarCmbTipoHabitacion();
    }

    @FXML
    private void accionUrgencias(ActionEvent event) throws SQLException {
        urgencias = true;
        txfProcedimiento.setDisable(false);
        cmbTipoHabitacion.setDisable(false);

        seleccionarProcedimiento();
        //llenarCmbTipoHabitacion();
    }

    @FXML
    private void accionUrgenciasEntrada(ActionEvent event) {
        urgencias = true;
        consultaugenm = false;
        rdbConsulta.setDisable(false);
        rdbUrgencias.setDisable(false);
        rdbHospital.setDisable(false);

        txfProcedimiento.setDisable(true);
        cmbTipoHabitacion.setDisable(true);

    }

    @FXML
    private void accionConsulta(ActionEvent event) {
        consultaugenm = true;
        txfProcedimiento.setDisable(true);
        cmbTipoHabitacion.setDisable(true);

        txfProcedimiento.setText("");
    }

    public void obtenerDatos(Paciente paciente) throws SQLException {
        btnEditar.setVisible(true);
        btnIngresar.setVisible(false);
        btnIntegrar.setVisible(false);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaNacimiento = sdf.format(paciente.getFechaNacimiento());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fnaciiento = LocalDate.parse(fechaNacimiento, dtf);
        tiposangredao = new TipoSangreDAO(conexion.conectar2());
        medicodao = new MedicoDAO(conexion.conectar2());

        idFolioEditar = paciente.getIdfolio();
        idPaciente = paciente.getIdPaciente();
        txfnombreIngresado.setText(paciente.getNombre());
        txfpacienteNuevoCurp.setText(paciente.getCurp());
        txfpacienteNuevoLugarNacimiento.setText(paciente.getProcedencia());
        txfpacienteNuevoOcupacion.setText(paciente.getOcupacion());
        txfpacienteNuevoSexo.setText(paciente.getSexo());
        dtppacienteNuevoFechaNacimiento.setValue(fnaciiento);
        txfpacienteNuevoEdad.setText("" + paciente.getEdad());
        txfpacienteNuevoReligion.setText(paciente.getReligion());
        txfpacienteNuevoDireccion.setText(paciente.getDomicilio());
        txftelefonoIngresado.setText(paciente.getTelefono());
        idTipoSangre = paciente.getIdTipoSangre();
        idMedico = paciente.getIdMedico();
        nombreMedico = tiposangredao.getById(idTipoSangre).getTipo_sangre();
        tipoSangre = medicodao.getById(idMedico).getNombre();
        pacienteNuevoTipoSangre.setValue(tiposangredao.getById(idTipoSangre));
        cmbMedico.setValue(medicodao.getById(idMedico));
    }

    public void modo(int modo) {
        if (modo == 2) {
            btnIngresar.setVisible(false);
            btnEditar.setVisible(true);
            btnIntegrar.setVisible(false);
        } else {
         
        }
    }

    private void calcuarEdadPAciente() {
        txfpacienteNuevoEdad.setDisable(true);
        dtppacienteNuevoFechaNacimiento.setOnAction(event -> {
            LocalDate selectedDate = dtppacienteNuevoFechaNacimiento.getValue();
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(selectedDate, currentDate);
            int edad = period.getYears();
            txfpacienteNuevoEdad.setText("" + edad);
        });
    }

    private void llenarcmb() {
        medicodao = new MedicoDAO(conexion.conectar2());
        tiposangredao = new TipoSangreDAO(conexion.conectar2());
        try {
            medicos.addAll(medicodao.getAll());

            cmbMedico.setItems(medicos);
            cmbMedico.setOnAction(e -> {
                Medico medico = (Medico) cmbMedico.getValue();
                idMedico = medico.getId_medico();
               
                nombreMedico = medico.getNombre();
            });

        } catch (SQLException ex) {
            Logger.getLogger(PacienteNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            tipoSangres.addAll(tiposangredao.getAll());
            pacienteNuevoTipoSangre.setItems(tipoSangres);
            pacienteNuevoTipoSangre.setOnAction(e -> {
                TipoSangre tps = (TipoSangre) pacienteNuevoTipoSangre.getValue();
                idTipoSangre = tps.getId_tipo_sangre();
          
                tipoSangre = tps.getTipo_sangre();
            });
        } catch (SQLException ex) {
            Logger.getLogger(PacienteNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void desactivarCampos() {
        txfPaquete.setDisable(true);
        rdbIntervencion.setDisable(true);
        rdbHospitalizacion.setDisable(true);
        txfProcedimiento.setDisable(true);
        cmbTipoHabitacion.setDisable(true);
    }

    private void seleccionarPaquete() throws SQLException {
        paquetemedicodao = new PaqueteMedicoDAO(conexion.conectar2());
        AutoCompletionBinding<PaqueteMedico> paquete = TextFields.bindAutoCompletion(txfPaquete, paquetemedicodao.obtenerTodos());
        paquete.setPrefWidth(600);
        paquete.setOnAutoCompleted(event -> {
            PaqueteMedico pacmedSelecciionado = event.getCompletion();
            idPaquete = pacmedSelecciionado.getId();
            idTipoCirugia = pacmedSelecciionado.getId_tipo_procedimiento();
            idTipoHabitacion = pacmedSelecciionado.getId_tipo_habitacion();
        });

    }

    private void seleccionarProcedimiento() {
        procedimientodao = new ProcedimientoDAO(con);
        AutoCompletionBinding<Procedimiento> procedimiento = TextFields.bindAutoCompletion(txfProcedimiento, procedimientodao.getAllProcedimientos());
        procedimiento.setOnAutoCompleted(event -> {
        
            Procedimiento procedimientoselect = event.getCompletion();
            idProcedimiento = procedimientoselect.getId();
            idTipoCirugia = procedimientoselect.getId_especialidad();
        });
    }

    private void llenarCmbTipoHabitacion() throws SQLException {
        tipohabitaciondao = new TipoHabitacionDAO(conexion.conectar2());
        habitaciones.addAll(tipohabitaciondao.getAllTiposHabitacion());
        cmbTipoHabitacion.setItems(habitaciones);
        cmbTipoHabitacion.setOnAction(e -> {
            TipoHabitacion habit = cmbTipoHabitacion.getValue();
            idTipoHabitacion = habit.getIdTipo();
        });
    }

    private int generarFolio(int id_paciente) throws SQLException {
        foliodao = new FoliosDAO(conexion.conectar2());
        Folio folio = new Folio();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);
        sfolio = "" + id_paciente + formattedDateTime;

        folio.setFolio(sfolio);
        folio.setIdPaciente(id_paciente);
        folio.setMontoHastaElMomento(0);
        folio.setTotalDeAbono(0);
        folio.setSaldoACubrir(0);
        folio.setIdUsuarioModificacion(userSystem);
        folio.setIdEstatus(0);
        if (urgencias) {
            folio.setId_estatus_folio(1);
        } else {
            folio.setId_estatus_folio(1);
        }
        folio.setId_paquete(idPaquete);
        folio.setCosoto_deposito(0);
        folio.setId_estatus_pago_deposito(0);
        folio.setId_habitacion(idTipoHabitacion);
        folio.setUrgencias(urgencias);

        int idfoliogenerado = foliodao.insertarIntFolioConUrgencias(folio);
        return idfoliogenerado;
    }

    private void ingresoQuirofano(Paciente paciente) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CitasQuirofanosNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        CitasQuirofanosNuevoController cqnc = fxml.getController();

        if (idPaquete == 1) {
            cqnc.ingresoPacienteCuentaAbierta(paciente, idProcedimiento, idTipoCirugia, idTipoHabitacion);
        } else {
            cqnc.ingresoPaciente(paciente);
        }

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void ingresarConsumo(Paciente paciente) throws SQLException {
        con = conexion.conectar2();
        double cosoto;
        int tipoConsumo;
        int id_prodcuto;
        String tipo;

        if (idPaquete == 1) {
            if (urgencias) {
                costodao = new CostosDAO(conexion.conectar2());
                Costo costoobj = costodao.obtenerPorId(621);
                cosoto = costoobj.getPrecioVentaUnitaria();
                tipoConsumo = 2;
                tipo = "CONSULTA DE VALORACION";
                id_prodcuto = 621;
            } else {
                tipoprocedimientodao = new TipoprocedimientoDAO(con);
                TipoProcedimiento tipopro = tipoprocedimientodao.leer(idTipoCirugia);
                cosoto = tipopro.getCosto();// ESTO ES LO QUE HAY QUE ARREGLAR.
                tipoConsumo = 1;
                tipo = "QUIROFANO";
                id_prodcuto = 0;
            }
        } else {
            paquetemedicodao = new PaqueteMedicoDAO(con);
            PaqueteMedico paqueteMedico = paquetemedicodao.leer(idPaquete);
            cosoto = paqueteMedico.getPrecio();
            tipoConsumo = 4;
            tipo = paqueteMedico.getNombre();
            id_prodcuto = paqueteMedico.getId();//ID del paquete medico
        }

        Consumo consumo = new Consumo();

        consumo.setTipo(tipo);
        consumo.setCantidad(1);
        consumo.setMonto(cosoto / 1.16);
        consumo.setFolio(sfolio);
        consumo.setId_pasiente(paciente.getIdPaciente());
        consumo.setId_PaqueteAlimento(0);
        consumo.setId_tipo_consumo(tipoConsumo);
        consumo.setId_folio(idfoliogenerado);
        consumo.setId_producto_venta(id_prodcuto);
        consumo.setPrecioUnitario(cosoto / 1.16);
        consumodao = new ConsumosDAO(con);
        consumodao.insertarConsumo(consumo);

        foliodao = new FoliosDAO(con);
        Folio folio = foliodao.obtenerFolioPorId(idfoliogenerado);

        folio.setCosoto_deposito(cosoto);

        foliodao.actualizarFolio(folio);
    }

    private void desactivarCamposGenerales() {
        txfpacienteNuevoDireccion.setDisable(true);
        txfpacienteNuevoCurp.setDisable(true);
        txfpacienteNuevoLugarNacimiento.setDisable(true);
        dtppacienteNuevoFechaNacimiento.setDisable(true);
        txfpacienteNuevoEdad.setDisable(true);
        txfpacienteNuevoOcupacion.setDisable(true);
        txfpacienteNuevoSexo.setDisable(true);
        txfpacienteNuevoReligion.setDisable(true);
        pacienteNuevoTipoSangre.setDisable(true);
    }

    private void ActivarDesactivarCampos() {
        txfpacienteNuevoDireccion.setDisable(false);
        txfpacienteNuevoCurp.setDisable(false);
        txfpacienteNuevoLugarNacimiento.setDisable(false);
        dtppacienteNuevoFechaNacimiento.setDisable(false);
        txfpacienteNuevoEdad.setDisable(false);
        txfpacienteNuevoOcupacion.setDisable(false);
        txfpacienteNuevoSexo.setDisable(false);
        txfpacienteNuevoReligion.setDisable(false);
        pacienteNuevoTipoSangre.setDisable(false);

        rdbPaquete.setDisable(true);
        rdbAbierta.setDisable(true);
    }

    public void integrarInformacion(Paciente paciente) throws SQLException {
        btnIngresar.setVisible(false);
        btnEditar.setVisible(false);
        btnIntegrar.setVisible(true);
        ActivarDesactivarCampos();

        medicodao = new MedicoDAO(conexion.conectar2());

        idFolioEditar = paciente.getIdfolio();
        idPaciente = paciente.getIdPaciente();
        txfnombreIngresado.setText(paciente.getNombre());
        idMedico = paciente.getIdMedico();
        nombreMedico = medicodao.getById(idMedico).getNombre();
        cmbMedico.setValue(medicodao.getById(idMedico));
        txftelefonoIngresado.setText(paciente.getTelefono());

        CitaQuirofanoDAO citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());
        procedimientodao = new ProcedimientoDAO(conexion.conectar2());
        paquetemedicodao = new PaqueteMedicoDAO(conexion.conectar2());
        tipohabitaciondao = new TipoHabitacionDAO(conexion.conectar2());
        foliodao = new FoliosDAO(conexion.conectar2());
        Folio folio = foliodao.obtenerFolioPorId(paciente.getIdfolio());
       

        if (folio.getId_paquete() == 0) {
            rdbAbierta.setSelected(true);
            if (citaquirofanodao.obteCitaQuirofanoPorIdFolio(folio.getId()) == null) {
                rdbHospitalizacion.setSelected(true);
                //POR AGREGAR
            } else {
                CitaQuirofano citaquiro = citaquirofanodao.obteCitaQuirofanoPorIdFolio(folio.getId());
                Procedimiento procedimiento = procedimientodao.obtenerProcedimientoPorId(Integer.parseInt(citaquiro.getCirugia()));
                rdbIntervencion.setSelected(true);
                idProcedimiento = procedimiento.getId();
                txfProcedimiento.setText(procedimiento.getNombre());
            }
            idTipoHabitacion = folio.getId_habitacion();
            cmbTipoHabitacion.setValue(tipohabitaciondao.read(idTipoHabitacion));
        } else {
            PaqueteMedico paquetemed = paquetemedicodao.leer(1);//folio.getId_paquete());
            rdbPaquete.setSelected(true);
            idPaquete = folio.getId_paquete();
            txfPaquete.setText(paquetemed.getNombre());

        }
    }

    private void seleccionToggleGroup() {
        rdbHospital.setToggleGroup(toglegroupUrgenHospital);
        rdbUrgencias.setToggleGroup(toglegroupUrgenHospital);
        rdbConsulta.setToggleGroup(toglegroupUrgenHospital);

        toglegroupUrgenHospital.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // No hay botones seleccionados, puedes manejarlo aquí si es necesario
            } else {
                // Desseleccionamos los botones que no estén seleccionados
                if (oldValue != null && !oldValue.equals(newValue)) {
                    oldValue.setSelected(false);
                }
            }
        });

        rdbPaquete.setToggleGroup(toglegroupPaqueAbier);
        rdbAbierta.setToggleGroup(toglegroupPaqueAbier);

        toglegroupUrgenHospital.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // No hay botones seleccionados, puedes manejarlo aquí si es necesario
            } else {
                // Desseleccionamos los botones que no estén seleccionados
                if (oldValue != null && !oldValue.equals(newValue)) {
                    oldValue.setSelected(false);
                }
            }
        });

        rdbHospitalizacion.setToggleGroup(toglegroupHospInter);
        rdbIntervencion.setToggleGroup(toglegroupHospInter);
        rdbUrgenciasentrada.setToggleGroup(toglegroupHospInter);

        toglegroupHospInter.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
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

}
