/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clase.Paciente;
import clase.TipoVistaPago;
import clase.UM.PacienteUM;
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

   
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaWarning = new Alert(Alert.AlertType.WARNING);
    @FXML
    private TextField txfBuscarPaciente;
    @FXML
    private ComboBox<?> cbxfiltro;
    @FXML
    private TableView<PacienteUM> tabla;
    @FXML
    private TableColumn<?, ?> nombrePaciente;
    @FXML
    private TableColumn<?, ?> telefonoPaciente;
    @FXML
    private TableColumn<?, ?> nombreMedico;

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }

    @FXML
    private void filtrar(ActionEvent event) {
    }

    @FXML
    private void agregarPacienteNuevo(ActionEvent event) {
    }

 
   

}
