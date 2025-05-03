/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Medico;
import clases_hospital_DAO.MedicoDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class AgregarMedicoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    boolean modoEditar = false;
    Medico medicoEditable;

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfNombre;
    @FXML
    private TextField txfDireccion;
    @FXML
    private DatePicker dtpNacimiento;
    @FXML
    private TextField txfLugarNa;
    @FXML
    private TextField txfCorreo;
    @FXML
    private TextField txfTelefono;
    @FXML
    private TextField txfTitulo;
    @FXML
    private TextField txfCedula;
    @FXML
    private TextField txfCedulaEspecialidad;
    @FXML
    private TextField txfEspecialidad;
    @FXML
    private TextField txfCertificado;
    @FXML
    private TextField txfRfc;
    @FXML
    private TextField txfCurp;
    @FXML
    private RadioButton rbtnMedicoInterno;
    @FXML
    private ToggleGroup rbtnTipoDeMedico;
    @FXML
    private RadioButton rbtnExterno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        numeroDeTelefonoRestriccion();
    }

    @FXML
    private void actionBtnAgregar(ActionEvent event) throws SQLException {

        if (modoEditar) {
            if (validarDatos()) {
                actualizarMedico();
            } else {
                alertaError.setTitle("ERROR!");
                alertaError.setHeaderText("SIN PRODUCTOS");
                alertaError.setContentText("POR FAVOR SELECCIONE PRIMERO UN PRODUCTO");
                alertaError.showAndWait();
            }
        } else {
            if (validarDatos()) {
                generarMedico();
            } else {
                alertaError.setTitle("ERROR!");
                alertaError.setHeaderText("SIN PRODUCTOS");
                alertaError.setContentText("POR FAVOR SELECCIONE PRIMERO UN PRODUCTO");
                alertaError.showAndWait();
            }
        }

    }

    @FXML
    private void actionBtnSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private boolean validarDatos() {
        if (txfNombre.getText().isEmpty()
                || txfDireccion.getText().isEmpty()
                || txfLugarNa.getText().isEmpty()
                || txfCorreo.getText().isEmpty()
                || txfTelefono.getText().isEmpty()
                || txfTitulo.getText().isEmpty()
                || txfCedula.getText().isEmpty()
                || txfCedulaEspecialidad.getText().isEmpty()
                || txfCertificado.getText().isEmpty()
                || txfCertificado.getText().isEmpty()
                || txfRfc.getText().isEmpty()
                || txfCurp.getText().isEmpty()
                || dtpNacimiento.getValue() == null) {
            pintarDatosFaltantes();
            return false;
        }
        return true;
    }

    private void pintarDatosFaltantes() {

        if (txfNombre.getText().isEmpty()) {
            txfNombre.setStyle("-fx-border-color: red;");
        } else {
            txfNombre.setStyle(null); // Eliminar el estilo de borde
        }

        if (txfDireccion.getText().isEmpty()) {
            txfDireccion.setStyle("-fx-border-color: red;");
        } else {
            txfDireccion.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfLugarNa.getText().isEmpty()) {
            txfLugarNa.setStyle("-fx-border-color: red;");
        } else {
            txfLugarNa.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfCorreo.getText().isEmpty()) {
            txfCorreo.setStyle("-fx-border-color: red;");
        } else {
            txfCorreo.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfTelefono.getText().isEmpty()) {
            txfTelefono.setStyle("-fx-border-color: red;");
        } else {
            txfTelefono.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfTitulo.getText().isEmpty()) {
            txfTitulo.setStyle("-fx-border-color: red;");
        } else {
            txfTitulo.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfCedula.getText().isEmpty()) {
            txfCedula.setStyle("-fx-border-color: red;");
        } else {
            txfCedula.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfCedulaEspecialidad.getText().isEmpty()) {
            txfCedulaEspecialidad.setStyle("-fx-border-color: red;");
        } else {
            txfCedulaEspecialidad.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfEspecialidad.getText().isEmpty()) {
            txfEspecialidad.setStyle("-fx-border-color: red;");
        } else {
            txfEspecialidad.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfCertificado.getText().isEmpty()) {
            txfCertificado.setStyle("-fx-border-color: red;");
        } else {
            txfCertificado.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfRfc.getText().isEmpty()) {
            txfRfc.setStyle("-fx-border-color: red;");
        } else {
            txfRfc.setStyle(null); // Eliminar el estilo de borde
        }
        if (txfCurp.getText().isEmpty()) {
            txfCurp.setStyle("-fx-border-color: red;");
        } else {
            txfCurp.setStyle(null); // Eliminar el estilo de borde
        }
        if (dtpNacimiento.getValue() == null) {
            dtpNacimiento.setStyle("-fx-border-color: red;");
        } else {
            dtpNacimiento.setStyle(null);
        }
    }

    private void generarMedico() throws SQLException {
        Connection connection = null;

        try {
            connection = conexion.conectar2();
            Medico medico;
            String nombre = txfNombre.getText().toUpperCase();
            String titulo = txfTitulo.getText().toUpperCase();
            String cedula = txfCedula.getText().toUpperCase();
            String cedula_especialidad = txfCedulaEspecialidad.getText().toUpperCase();
            String especialidad = txfEspecialidad.getText().toUpperCase();
            String certificado = txfCertificado.getText().toUpperCase();
            String telefono = txfTelefono.getText();
            String rfc = txfRfc.getText().toUpperCase();
            String direccion = txfDireccion.getText().toUpperCase();
            String correo = txfCorreo.getText().toUpperCase();
            LocalDate fecha_nacimiento = dtpNacimiento.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(fecha_nacimiento);
            String lugar_nacimiento = txfLugarNa.getText().toUpperCase();
            String curp = txfCurp.getText().toUpperCase();
            int tipoMed = tipoMedico();// id_tipo = 1(externo), 2(interno)
            if (tipoMed != 0) {
                medico = new Medico(nombre, titulo, cedula, cedula_especialidad, especialidad, certificado, telefono, rfc, direccion, correo, sqlDate, lugar_nacimiento, curp, 1, tipoMed);
                MedicoDAO medicoDAO = new MedicoDAO(connection);
                medicoDAO.insertarMedico(medico);
                alertaInfo.setTitle("EXITO!");
                alertaInfo.setHeaderText("PROCEDIMIENTO REALIZADO DE MANERA CORECTA");
                alertaInfo.setContentText("MEDICO INGRESADO DE MANERA CORRECTA");
                alertaInfo.showAndWait();
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            } else {
                alertaError.setTitle("ERROR!");
                alertaError.setHeaderText(null);
                alertaError.setContentText("POR FAVOR VERIFIQUE SI EL MEDICO ES\nINTERNO\nEXTERNO");
                alertaError.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close(); // Cierra la conexión
            }
        }

    }

    // id_tipo = 1(interno), 2(externo)
    private int tipoMedico() {
        int tipo = 0;
        if (rbtnExterno.isSelected()) {
            tipo = 2;
        } else if (rbtnMedicoInterno.isSelected()) {
            tipo = 1;
        } else {
            tipo = 0;
        }

        return tipo;
    }

    private void numeroDeTelefonoRestriccion() {
        txfTelefono.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
    }

    public void recibirDatos(Medico medico, boolean modo) {
        modoEditar = modo;
        medicoEditable = medico;
        btnAgregar.setText("EDITAR");
        txfNombre.setText(medico.getNombre());
        txfDireccion.setText(medico.getDireccion());
        txfLugarNa.setText(medico.getLugar_nacimiento());
        Date fecha = medico.getFecha_nacimiento();
        LocalDate fechaN = fecha.toLocalDate();
        dtpNacimiento.setValue(fechaN);
        txfTelefono.setText(medico.getTelefono());
        txfCorreo.setText(medico.getCorreo());
        txfTitulo.setText(medico.getTitulo());
        txfCedula.setText(medico.getCedula());
        txfCedulaEspecialidad.setText(medico.getCedula_especialidad());
        txfEspecialidad.setText(medico.getEspecialidad());
        txfCertificado.setText(medico.getCertificado());
        txfRfc.setText(medico.getRfc());
        txfCurp.setText(medico.getCurp());
        int tipo = medico.getId_tipo();

        if (tipo == 2) {// id_tipo = 1(externo), 2(interno)
            rbtnExterno.setSelected(true);
        } else if (tipo == 1) {
            rbtnMedicoInterno.setSelected(true);
        }

    }

    private void actualizarMedico() throws SQLException {
        Connection connection = null;

        try {
            connection = conexion.conectar2();
            int tipoMed = tipoMedico();
          
            LocalDate fecha_nacimiento = dtpNacimiento.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(fecha_nacimiento);
            Medico medico = new Medico(
                    medicoEditable.getId_medico(),
                    txfNombre.getText(),
                    txfTitulo.getText(),
                    txfCedula.getText(),
                    txfCedulaEspecialidad.getText(),
                    txfEspecialidad.getText(),
                    txfCertificado.getText(),
                    txfTelefono.getText(),
                    txfRfc.getText(),
                    txfDireccion.getText(),
                    txfCorreo.getText(),
                    sqlDate,
                    txfLugarNa.getText(),
                    txfCurp.getText(),
                    1,
                    tipoMed);
            MedicoDAO medicoDAO = new MedicoDAO(connection);
            medicoDAO.update(medico);
            alertaInfo.setTitle("EXITO!");
            alertaInfo.setHeaderText("PROCEDIMIENTO REALIZADO DE MANERA CORECTA");
            alertaInfo.setContentText("MEDICO ACTUALIZADO DE MANERA CORRECTA");
            alertaInfo.showAndWait();
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
        } finally {
            if (connection != null) {

                connection.close(); // Cierra la conexión
            }
        }
    }
}
