/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.UM.PacienteUM;
import clase.UM.TipoTabulacion;
import clase.UM_DAO.PacienteUmDAO;
import clase.UM_DAO.TipoTabulacionDAO;
import java.net.URL;
import java.sql.SQLException;
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
import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class PacienteNuevo2Controller implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    ObservableList<TipoTabulacion> tipos = FXCollections.observableArrayList();

    PacienteUM paciente;

    PacienteUmDAO daoPaciente;
    TipoTabulacionDAO daoTipo;

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnEditar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCurp;
    @FXML
    private DatePicker dtpFechaNacimiento;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtSexo;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtId;
    @FXML
    private ComboBox<TipoTabulacion> cmbTipoTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            paciente = new PacienteUM();
            llenarCbx();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteNuevo2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setObjeto(PacienteUM pacente) {

    }

    private void llenarCbx() throws SQLException {
        daoTipo = new TipoTabulacionDAO();
        tipos.addAll(daoTipo.ejecutarProcedimiento("listar", new TipoTabulacion()));

        cmbTipoTab.setItems(tipos);
    }

    @FXML
    private void agregar(ActionEvent event) throws SQLException {
        paciente = new PacienteUM();
        daoPaciente = new PacienteUmDAO();
        
        paciente.getTipoTab().setIdTipoTabulacion(cmbTipoTab.getSelectionModel().getSelectedItem().getIdTipoTabulacion());
        paciente.setCurp(txtCurp.getText());
        paciente.setNombrePaciente(txtNombre.getText());
        paciente.setApellidoPaterno(txtApellidoPaterno.getText());
        paciente.setApellidoMaterno(txtApellidoMaterno.getText());
        paciente.setSexoPaciente(txtSexo.getText());
        paciente.setFechaNacimientoPaciente(java.sql.Date.valueOf(dtpFechaNacimiento.getValue()));
        LocalDate fechaNac = paciente.getFechaNacimientoPaciente().toLocalDate(); // si es java.sql.Date
        int edadCalculada = calcularEdad(fechaNac);
        paciente.setEdad(edadCalculada);

        paciente.getUsuarioCreacion().setIdUsuario(VPMedicaPlaza.userSystem);
        
        
        daoPaciente.ejecutarProcedimientoPaciente("agregar", paciente);
        
        alertaSuccess.setTitle("PACIENTE INGRESADO");
        alertaSuccess.setHeaderText("PACIENTE INGRESADO CORRECTAMENTE");
        alertaSuccess.setContentText("EL PACIENTE SE AGREGO A LA BASE DE DATOS");
        alertaSuccess.showAndWait();
        btnIngresar.setDisable(true);
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
        
    }

    public int calcularEdad(LocalDate fechaNacimiento) {
        LocalDate hoy = LocalDate.now();
        return Period.between(fechaNacimiento, hoy).getYears();
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionEditar(ActionEvent event) throws SQLException {
        
    }

    @FXML
    private void dtpFechNacEdad(ActionEvent event) {
        LocalDate fechaNac = dtpFechaNacimiento.getValue(); 
        txtEdad.setText("" + calcularEdad(fechaNac));
    }

}
