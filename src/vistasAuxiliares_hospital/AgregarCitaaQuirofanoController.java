/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.*;
import clases_hospital_DAO.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author noga
 */
public class AgregarCitaaQuirofanoController implements Initializable {

    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);

    Conexion conexion = new Conexion();
    Connection con;

    ObservableList<Medico> listamedico = FXCollections.observableArrayList();
    ObservableList<Quirofano> listaquirofano = FXCollections.observableArrayList();

    AgendaQuirofano agendaQuirofano;

    MedicoDAO medicoDAO;
    QuirofanoDAO quirofanoDAO;
    AgendaQuirofanoDAO agendaQuirofanoDAO;

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCanelar;
    @FXML
    private Button btnEditar;
    @FXML
    private TextField txfPaciente;
    @FXML
    private ComboBox<Quirofano> cmbQuirofano;
    @FXML
    private ComboBox<Medico> cmbMedico;
    @FXML
    private TextField txtObservacion;
    @FXML
    private DatePicker dtpFecha;
    @FXML
    private TextField txfMin;
    @FXML
    private TextField txfhora;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //todo

            llenarvalores();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarCitaaQuirofanoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionagregar(ActionEvent event) throws SQLException {
        agendaQuirofano = new AgendaQuirofano();
        agendaQuirofanoDAO = new AgendaQuirofanoDAO(con);
        boolean validar = validardatos();

        if (validar == true) {
            agendaQuirofano.setId_quirofano(cmbQuirofano.getValue().getId());
            agendaQuirofano.setId_medico(cmbMedico.getValue().getId_medico());
            agendaQuirofano.setNombre_paciente(txfPaciente.getText());
            agendaQuirofano.setObservaciones(txtObservacion.getText());
            agendaQuirofano.setFechaagenda(formatofecha());
            agendaQuirofano.setHora_agenda(txfhora.getText() + ":" + txfMin.getText());
            agendaQuirofano.setId_usuario_modificacion(VPMedicaPlaza.userSystem);

            agendaQuirofanoDAO.agregarcitaagenda(agendaQuirofano);
            alertaConfirmacion.setTitle("PRECEDIMIENTO REALIZADO!");
            alertaConfirmacion.setHeaderText("PRECEDIMIENTO");
            alertaConfirmacion.setContentText("PRECEDIMIENTO REALIZADO CON EXITO");
            alertaConfirmacion.showAndWait();

            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void accioneditar(ActionEvent event) throws SQLException {
        
        agendaQuirofanoDAO = new AgendaQuirofanoDAO(con);
        boolean validar = validardatos();

        if (validar == true) {
            agendaQuirofano.setId_quirofano(cmbQuirofano.getValue().getId());
            agendaQuirofano.setId_medico(cmbMedico.getValue().getId_medico());
            agendaQuirofano.setNombre_paciente(txfPaciente.getText());
            agendaQuirofano.setObservaciones(txtObservacion.getText());
            agendaQuirofano.setFechaagenda(formatofecha());
            agendaQuirofano.setHora_agenda(txfhora.getText() + ":" + txfMin.getText());
            agendaQuirofano.setId_usuario_modificacion(VPMedicaPlaza.userSystem);
            System.out.println(""+ agendaQuirofano.getId_agenda_quirofano());

            agendaQuirofanoDAO.editarrcitaagenda(agendaQuirofano);

            alertaConfirmacion.setTitle("PRECEDIMIENTO REALIZADO!");
            alertaConfirmacion.setHeaderText("EDITADO");
            alertaConfirmacion.setContentText("PRECEDIMIENTO REALIZADO CON EXITO");
            alertaConfirmacion.showAndWait();
            Stage stage = (Stage) btnEditar.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCanelar.getScene().getWindow();
        stage.close();
    }

    public void setObjeto(AgendaQuirofano agendaQuirofano) throws SQLException {
        agendaQuirofanoDAO = new AgendaQuirofanoDAO(con);
       
        this.agendaQuirofano = agendaQuirofanoDAO.rellernarparaeditar(agendaQuirofano);
         llenarvalores();
        
        
        System.out.println(""+ this.agendaQuirofano.getId_agenda_quirofano());

        btnEditar.setVisible(true);
        btnAgregar.setVisible(false);

        llenarDatosparaEditar();
    }

    private void llenarDatosparaEditar() {
        java.sql.Date fechaSQL = agendaQuirofano.getFechaagenda();
        LocalDate fechaLocal = fechaSQL.toLocalDate();
        String horaCompleta = agendaQuirofano.getHora_agenda();
        String[] partesHora = horaCompleta.split(":");

        // Establecer los valores de los ComboBox utilizando el ID del quirofano y del médico
    for (Quirofano quirofano : cmbQuirofano.getItems()) {
        if (quirofano.getId() == agendaQuirofano.getId_quirofano()) {
            cmbQuirofano.getSelectionModel().select(quirofano);
            break;
        }
    }

    // Buscar el Medico en el modelo del ComboBox por su id
    for (Medico medico : cmbMedico.getItems()) {
        if (medico.getId_medico() == agendaQuirofano.getId_medico()) {
            cmbMedico.getSelectionModel().select(medico);
            break;
        }
    }

        txfPaciente.setText(agendaQuirofano.getNombre_paciente());
        txtObservacion.setText(agendaQuirofano.getObservaciones());
        dtpFecha.setValue(fechaLocal);
        txfhora.setText(partesHora[0]);
        txfMin.setText(partesHora[1]);
    }

    private void llenarvalores() throws SQLException {
        con = conexion.conectar2();
        medicoDAO = new MedicoDAO(con);
        quirofanoDAO = new QuirofanoDAO(con);

        listamedico.addAll(medicoDAO.getAll());
        listaquirofano.addAll(quirofanoDAO.obtenerTodos());
        cmbMedico.setItems(listamedico);
        cmbQuirofano.setItems(listaquirofano);

    }

    private boolean validardatos() {
        boolean bo = false;

        if (txfPaciente.getText() != null && cmbQuirofano.getValue() != null && cmbMedico.getValue() != null
                && txtObservacion.getText() != null && dtpFecha.getValue() != null && txfMin.getText() != null && txfhora.getText() != null) {
            bo = true;

        } else {

            alertaError.setTitle("ERROR!");
            alertaError.setHeaderText("DATOS");
            alertaError.setContentText("Por favor, complete todos los campos.");
            alertaError.showAndWait();
        }

        return bo;
    }

    private Date formatofecha() {
        LocalDate fecha = dtpFecha.getValue();

        // Obtener la hora y los minutos de los TextField
        int hora = Integer.parseInt(txfhora.getText());
        int minuto = Integer.parseInt(txfMin.getText());

        // Crear un objeto LocalTime con la hora y los minutos
        LocalTime horaMinuto = LocalTime.of(hora, minuto);

        // Combinar la fecha y la hora en un LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(fecha, horaMinuto);

        // Convertir LocalDateTime a Date
        Date fechaHoraDate = convertirLocalDateTimeADate(dateTime);
        return fechaHoraDate;
    }

    private static java.sql.Date convertirLocalDateTimeADate(LocalDateTime localDateTime) {
        return java.sql.Date.valueOf(localDateTime.toLocalDate());
    }

}
