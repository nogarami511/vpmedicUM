/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Consumo;
import clases_hospital.Folio;
import clases_hospital.Medico;
import clases_hospital.PaqueteMedico;
import clases_hospital.TipoHabitacion;
import clases_hospital.TipoSangre;
import clases_hospital_DAO.ConsumoQuirofanoDAO;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.MedicoDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.TipoSangreDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class PacienteNuevo2Controller implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    ObservableList<Medico> medicos = FXCollections.observableArrayList();
    ObservableList<TipoSangre> tipoSangres = FXCollections.observableArrayList();
    ObservableList<TipoHabitacion> habitaciones = FXCollections.observableArrayList();
    ObservableList<Paciente> pacientes;
    int idPacienteRecibido;
    Paciente paciente;
    int idMedicoSeleccion = 9;//medico de guardia
    int idTipoSangre = 1;
    int idPaquete = 1;
    ToggleGroup toggleGroup = new ToggleGroup();
    ToggleGroup toggleGroup2 = new ToggleGroup();
    boolean esPaquete = false, esCuentaabierta = true, huboConsulta = false;
    boolean entraHospital = false, entraUrgencia = false;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnEditar;
    @FXML
    private TextField txfnombreIngresado;
    @FXML
    private ComboBox<Medico> cmbMedico;
    @FXML
    private TextField txftelefonoIngresado;
    @FXML
    private TextField txfpacienteNuevoDireccion;
    @FXML
    private TextField txfpacienteNuevoCurp;
    @FXML
    private TextField txfpacienteNuevoLugarNacimiento;
    @FXML
    private DatePicker dtppacienteNuevoFechaNacimiento;
    @FXML
    private TextField txfpacienteNuevoEdad;
    @FXML
    private ChoiceBox<TipoSangre> pacienteNuevoTipoSangre;
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
    private RadioButton rdbHospital;
    @FXML
    private RadioButton rdbUrgenciasentrada;
    @FXML
    private RadioButton rdbConsulta;
    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn<?, ?> nomnrePacienteTabla;
    @FXML
    private TableColumn<?, ?> estatusPacienteTabla;
    @FXML
    private TextField txfApellidoMaterno;
    @FXML
    private TextField txfApellidoPaterno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarCBX();
            rdbPaquete.setToggleGroup(toggleGroup);
            rdbAbierta.setToggleGroup(toggleGroup);
            rdbUrgenciasentrada.setToggleGroup(toggleGroup2);
            rdbHospital.setToggleGroup(toggleGroup2);
            rdbConsulta.setDisable(false);
            desbloquearCampos();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteNuevo2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        /*INGRESO AL PACIENTE EN CATALOGO*/
        Paciente paciente = new Paciente();
        paciente.setNombre(txfApellidoPaterno.getText() + " " + txfApellidoMaterno.getText() + " " + txfnombreIngresado.getText().toUpperCase());
        paciente.setTelefono(txftelefonoIngresado.getText());
        paciente.setIdMedico(idMedicoSeleccion);
        PacientesDAO pacientesDAO = new PacientesDAO(con);
        int idPacienteGenerado = pacientesDAO.insertarPacienteDatosEsenciales(paciente);
     
        /*GENERO EL FOLIO*/
        int folioGenerado = generarFolio(idPacienteGenerado);
       
        /*ACTUALIZO CON EL IDFOLIO CREADO AL PACIENTE*/
      
        pacientesDAO.actualizarFolioPaciente(idPacienteGenerado, folioGenerado);

        if (rdbConsulta.isSelected()) {
            /*
            agregar a consumo de paciente la consulta de valoracion
            621	CONSULTA DE VALORACION	431.034483
             */
            CostosDAO costosDAO = new CostosDAO(con);
            double costoConsulta = costosDAO.obtenerCosto(621);
            Consumo consumo = new Consumo("CONSULTA DE VALORACION",// tipo
                    1,// cantidad
                    costoConsulta,//costo unitario multiplicado por la cantidad (monto total)
                    "",// folio
                    idPacienteGenerado,//id paciente
                    folioGenerado,// id folio
                    621);// id producto
            consumo.setPrecioUnitario(costoConsulta);
            consumo.setId_tipo_consumo(1);
            ConsumoQuirofanoDAO consumoQuirofanoDAO = new ConsumoQuirofanoDAO(con);
            consumoQuirofanoDAO.incertarConsumo(consumo);
        }

        alertaSuccess.setHeaderText("PACIENTE INGRESADO");
        alertaSuccess.setContentText("PROCEDIMIENTO EJECUTADO CON EXITO");
        alertaSuccess.showAndWait();
        con.close();
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionEditar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        PacientesDAO pacientesDAO = new PacientesDAO(con);
        paciente.setNombre(txfApellidoPaterno.getText()+" "+txfApellidoMaterno.getText()+" "+ txfnombreIngresado.getText().toUpperCase());
        paciente.setTelefono(txftelefonoIngresado.getText());
        paciente.setIdMedico(idMedicoSeleccion);
        paciente.setCurp(txfpacienteNuevoCurp.getText());
        paciente.setDomicilio(txfpacienteNuevoDireccion.getText());
        paciente.setProcedencia(txfpacienteNuevoLugarNacimiento.getText());
        paciente.setFechaNacimiento(java.sql.Date.valueOf(dtppacienteNuevoFechaNacimiento.getValue()));
        paciente.setEdad(Integer.parseInt(txfpacienteNuevoEdad.getText()));
        paciente.setIdTipoSangre(idTipoSangre);
        paciente.setOcupacion(txfpacienteNuevoOcupacion.getText());
        paciente.setSexo(txfpacienteNuevoSexo.getText());
        paciente.setReligion(txfpacienteNuevoReligion.getText());
        pacientesDAO.actualizarPaciente2(paciente);

        alertaSuccess.setHeaderText("PACIENTE EDITADO");
        alertaSuccess.setContentText("PROCEDIMIENTO EJECUTADO CON EXITO");
        alertaSuccess.showAndWait();
        con.close();
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionPaquete(ActionEvent event) {
        //ingreso por paquete
        txfPaquete.setDisable(false);
        esPaquete = true;
        esCuentaabierta = false;

        rdbHospital.setDisable(true);

        rdbUrgenciasentrada.setDisable(true);
    }

    @FXML
    private void accionCuentaAbierta(ActionEvent event) {
        //ingreso cuenta abierta
        txfPaquete.setDisable(true);
        esPaquete = false;
        esCuentaabierta = true;
        rdbHospital.setDisable(false);
        rdbUrgenciasentrada.setDisable(false);
    }

    @FXML
    private void accionHospital(ActionEvent event) {
        entraUrgencia = false;
        entraHospital = true;
    }

    @FXML
    private void accionUrgenciasEntrada(ActionEvent event) {
        entraUrgencia = true;
        entraHospital = false;
    }

    @FXML
    private void accionConsulta(ActionEvent event) {
        
    }

    private void calcuarEdadPaciente() {
        dtppacienteNuevoFechaNacimiento.setOnAction(event -> {
            LocalDate selectedDate = dtppacienteNuevoFechaNacimiento.getValue();
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(selectedDate, currentDate);
            int edad = period.getYears();
            txfpacienteNuevoEdad.setText("" + edad);
        });
    }

    public void llenarCBX() throws SQLException {
        con = conexion.conectar2();
        MedicoDAO medicodao = new MedicoDAO(con);
        TipoSangreDAO tipoSangreDAO = new TipoSangreDAO(con);
        PaqueteMedicoDAO paqueteMedicoDAO = new PaqueteMedicoDAO(con);
        try {
            medicos.addAll(medicodao.traerMedicosConDatosEsenciales());
            cmbMedico.setItems(medicos);
            cmbMedico.setOnAction(e -> {
                Medico medico = (Medico) cmbMedico.getValue();
                idMedicoSeleccion = medico.getId_medico();
            });

        } catch (SQLException ex) {
            Logger.getLogger(PacienteNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tipoSangres.addAll(tipoSangreDAO.getAll());
            pacienteNuevoTipoSangre.setItems(tipoSangres);
            pacienteNuevoTipoSangre.setOnAction(e -> {
                TipoSangre tps = (TipoSangre) pacienteNuevoTipoSangre.getValue();
                idTipoSangre = tps.getId_tipo_sangre();
            });
        } catch (SQLException ex) {
            Logger.getLogger(PacienteNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AutoCompletionBinding<PaqueteMedico> paquete = TextFields.bindAutoCompletion(txfPaquete, paqueteMedicoDAO.obtenerTodos());
        paquete.setPrefWidth(600);
        paquete.setOnAutoCompleted(event -> {
            PaqueteMedico paqueteMedicoSeleccion = event.getCompletion();
            idPaquete = paqueteMedicoSeleccion.getId();
        });
        con.close();
    }
    @FXML
    private void ingresar2(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        int id_foliogen = 0;
        /*INGRESO AL PACIENTE EN CATALOGO*/
        Paciente paciente = new Paciente();
        paciente.setNombre(txfApellidoPaterno.getText() + " " + txfApellidoMaterno.getText() + " " + txfnombreIngresado.getText().toUpperCase());
        paciente.setTelefono(txftelefonoIngresado.getText());
        paciente.setIdMedico(idMedicoSeleccion);
        PacientesDAO pacientesDAO = new PacientesDAO(con);
        int idPacienteGenerado = pacientesDAO.insertarPacienteDatosEsenciales(paciente);
      
        /*GENERO EL FOLIO*/
        id_foliogen = generarFolio2(idPacienteGenerado);
     
        /*ACTUALIZO CON EL IDFOLIO CREADO AL PACIENTE*/
      
     //   pacientesDAO.actualizarFolioPaciente(idPacienteGenerado, folioGenerado);

        if (rdbConsulta.isSelected()) {
            /*
            agregar a consumo de paciente la consulta de valoracion
            621	CONSULTA DE VALORACION	431.034483
             */
            CostosDAO costosDAO = new CostosDAO(con);
            double costoConsulta = costosDAO.obtenerCosto(621);
            Consumo consumo = new Consumo("CONSULTA DE VALORACION",// tipo
                    1,// cantidad
                    costoConsulta,//costo unitario multiplicado por la cantidad (monto total)
                    "",// folio
                    idPacienteGenerado,//id paciente
                    id_foliogen,// id folio
                    621);// id producto
            consumo.setPrecioUnitario(costoConsulta);
            consumo.setId_tipo_consumo(1);
            ConsumoQuirofanoDAO consumoQuirofanoDAO = new ConsumoQuirofanoDAO(con);
            consumoQuirofanoDAO.incertarConsumo(consumo);
        }

        alertaSuccess.setHeaderText("PACIENTE INGRESADO");
        alertaSuccess.setContentText("PROCEDIMIENTO EJECUTADO CON EXITO");
        alertaSuccess.showAndWait();
        con.close();
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
    }
           private int generarFolio2(int id_paciente) throws SQLException {
               int id_foliogen =0;
        FoliosDAO foliodao = new FoliosDAO(conexion.conectar2());
        Folio folio = new Folio();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);
        String sfolio = "" + id_paciente + formattedDateTime;

        folio.setFolio(sfolio);
        folio.setIdPaciente(id_paciente);
        folio.setIdUsuarioModificacion(userSystem);
        if (entraUrgencia) {
            folio.setUrgencias(true);
            folio.setNumero_habitacion(102);
            folio.setId_habitacion(11);
        } else {
            folio.setUrgencias(false);
        }
        if (esPaquete) {
            folio.setId_paquete(idPaquete);
            folio.setCosoto_deposito(0);
        } else {
            folio.setId_paquete(1);
            folio.setCosoto_deposito(10000);
        }
       

       id_foliogen = foliodao.crearfolios(folio);
        return id_foliogen;
    }
    private int generarFolio(int id_paciente) throws SQLException {
        FoliosDAO foliodao = new FoliosDAO(conexion.conectar2());
        Folio folio = new Folio();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);
        String sfolio = "" + id_paciente + formattedDateTime;

        folio.setFolio(sfolio);
        folio.setIdPaciente(id_paciente);
        folio.setMontoHastaElMomento(0);
        folio.setTotalDeAbono(0);
        folio.setSaldoACubrir(0);
        folio.setIdUsuarioModificacion(userSystem);
        folio.setIdEstatus(0);
        if (entraUrgencia) {
            folio.setUrgencias(true);
        } else {
            folio.setUrgencias(false);
        }
        if (esPaquete) {
            folio.setId_paquete(idPaquete);
            folio.setCosoto_deposito(0);
        } else {
            folio.setId_paquete(1);
            folio.setCosoto_deposito(10000);
        }
        folio.setId_estatus_pago_deposito(0);
       // folio.setId_habitacion(idTipoHabitacion);
        int idfoliogenerado = foliodao.insertarIntFolioConUrgencias(folio);
        return idfoliogenerado;
    }

    public void setObjeto(int idPaciente) throws SQLException {
        idPacienteRecibido = idPaciente;
        habilitar_deshabilitarBotones();
        llenar();
        calcuarEdadPaciente();
        rdbConsulta.setDisable(true);
        tabla.setVisible(false);
    }

    public void setObjetoIngresarNuevo(ObservableList<Paciente> pacientes) throws SQLException {
        this.pacientes = pacientes;
        tabla.setItems(this.pacientes);
        nomnrePacienteTabla.setCellValueFactory(new PropertyValueFactory("nombre"));
        estatusPacienteTabla.setCellValueFactory(new PropertyValueFactory("nombreMedico"));
        txfApellidoPaterno.setOnKeyReleased(e -> filtrarLista(txfApellidoPaterno.getText()));
        txfApellidoMaterno.setOnKeyReleased(e -> filtrarLista(txfApellidoPaterno.getText()+" "+txfApellidoMaterno.getText()));
        txfnombreIngresado.setOnKeyReleased(e -> filtrarLista(txfApellidoPaterno.getText()+" "+txfApellidoMaterno.getText()+" "+txfnombreIngresado.getText()));
        nomnrePacienteTabla.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
    }

    public void habilitar_deshabilitarBotones() {
        btnIngresar.setVisible(false);
        btnEditar.setVisible(true);

        txfnombreIngresado.setDisable(false);
        txftelefonoIngresado.setDisable(false);
        txfpacienteNuevoDireccion.setDisable(false);
        txfpacienteNuevoCurp.setDisable(false);
        txfpacienteNuevoLugarNacimiento.setDisable(false);
        txfpacienteNuevoEdad.setDisable(false);
        txfpacienteNuevoOcupacion.setDisable(false);
        txfpacienteNuevoSexo.setDisable(false);
        txfpacienteNuevoReligion.setDisable(false);
        cmbMedico.setDisable(false);
        dtppacienteNuevoFechaNacimiento.setDisable(false);
        pacienteNuevoTipoSangre.setDisable(false);

        txfPaquete.setDisable(true);
        rdbAbierta.setDisable(true);
        rdbPaquete.setDisable(true);
    }

    public void llenar() throws SQLException {
        con = conexion.conectar2();
        PacientesDAO pacientesDAO = new PacientesDAO(con);
        paciente = pacientesDAO.obtenerPacientePorId(idPacienteRecibido);
        
       
        String[] partesNombre = paciente.getNombre().split(" ");
        txfApellidoPaterno.setText(partesNombre[0]);
        txfApellidoMaterno.setText(partesNombre[1]);
        txfnombreIngresado.setText(partesNombre.length > 2 ? partesNombre[2] : "");
        //cmbMedico
        txftelefonoIngresado.setText(paciente.getTelefono());
        txfpacienteNuevoDireccion.setText(paciente.getDomicilio());
        txfpacienteNuevoCurp.setText(paciente.getCurp());
        txfpacienteNuevoLugarNacimiento.setText(paciente.getProcedencia());
        dtppacienteNuevoFechaNacimiento.setValue(paciente.getFechaNacimiento().toLocalDate());
        txfpacienteNuevoEdad.setText(String.valueOf(paciente.getEdad()));
        //pacienteNuevoTipoSangre
        txfpacienteNuevoOcupacion.setText(paciente.getOcupacion());
        txfpacienteNuevoSexo.setText(paciente.getSexo());
        txfpacienteNuevoReligion.setText(paciente.getReligion());
        //txfPaquete;
        //txfProcedimiento;
        //cmbTipoHabitacion;
        con.close();
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

    private void desbloquearCampos() {
        // Configurar eventos
        txfApellidoPaterno.textProperty().addListener((observable, oldValue, newValue) -> {
            // Habilitar el segundo campo cuando el primero se llena
            if (!newValue.isEmpty()) {
                txfApellidoMaterno.setDisable(false);
            } else {
                // Deshabilitar el segundo campo si el primero se vacía
                txfApellidoMaterno.setDisable(true);
                // Deshabilitar el tercer campo si el primero se vacía
                txfnombreIngresado.setDisable(true);
            }
            actualizarNombreField();
        });

        txfApellidoMaterno.textProperty().addListener((observable, oldValue, newValue) -> {
            // Habilitar el tercer campo cuando el segundo se llena
            if (!newValue.isEmpty()) {
                txfnombreIngresado.setDisable(false);
            } else {
                // Deshabilitar el tercer campo si el segundo se vacía
                txfnombreIngresado.setDisable(true);
            }
            actualizarNombreField();
        });

    }

    // Método para actualizar el campo de nombre
    private void actualizarNombreField() {
        // Habilitar el tercer campo solo cuando ambos apellidos están llenos
        if (!txfApellidoPaterno.getText().isEmpty() && !txfApellidoMaterno.getText().isEmpty()) {
            txfnombreIngresado.setDisable(false);
        } else {
            // Deshabilitar el tercer campo si alguno de los apellidos se vacía
            txfnombreIngresado.setDisable(true);
        }
    }
}
