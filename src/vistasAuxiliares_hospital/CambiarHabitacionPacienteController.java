/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.AsignacionHabitacion;
import clases_hospital.Folio;
import clases_hospital.Habitacion;
import clases_hospital.TipoHabitacion;
import clases_hospital_DAO.ConsumoHabitacionDAO;
import clases_hospital.ConsumoHabitacion;
import clases_hospital.CostoHabitacion;
import clases_hospital_DAO.CostoHabitacionDAO;
import clases_hospital_DAO.AsignacionHabitacionDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.HabitacionDAO;
import clases_hospital_DAO.TipoHabitacionDAO;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CambiarHabitacionPacienteController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<TipoHabitacion> tiposhabitaciones;
    ObservableList<Habitacion> habitaciones;
    ObservableList<Habitacion> habitacionesFiltradas;
    Conexion conexion = new Conexion();
    Folio folio;
    ConsumoHabitacionDAO consumohabitaciondao;
    HabitacionDAO habitaciondao;
    FoliosDAO foliodao;
    AsignacionHabitacionDAO asignacionHabitaciondao;
    private boolean eshabitacion = false;
    private boolean esUpgrade = false;
    //private int idTipoHabitacionPorCambioDeHabitacion, numeroHabitacion;
    TipoHabitacion tipoHabitacionSeleccion;
    Habitacion habitacionSeleccion;
    HabitacionDAO habitacionDAO;
    Habitacion h;
    Connection con;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCambiodeHabitacion;
    @FXML
    private Button btnUpgrade;
    @FXML
    private ComboBox<TipoHabitacion> cmbTipoHabitacion;
    @FXML
    private ComboBox<Habitacion> cmbHabitacionCambio;
    @FXML
    private ComboBox<Habitacion> cmbHabitacion;
    @FXML
    private Button btnConfirmar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            traerHabitaciones();
            btnConfirmar.setDisable(false);
        } catch (SQLException ex) {
            Logger.getLogger(CambiarHabitacionPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionAceptar(ActionEvent event) throws SQLException {
        /*consumos_ habitacion -> almacena las habitaciones en las que ha estado el paciente
            Folio el idTipoHabitacion y el numeroHabitacion
            asignacion_habitacion -> quien está en la habitacion
            habitacion -> modificar el estatus a 2 cuando está ocupada
         */

        hacerElDesmadreDelCambioHabitacion();

        alertaSuccess.setHeaderText("CAMBIO DE HABITACION EXITOSA");
        alertaSuccess.setContentText("SE MOVIÓ AL PACIENTE DE HABITACIÓN");
        alertaSuccess.showAndWait();
        accionSalir(event);

    }

    @FXML
    private void accionCambiodeHabitacion(ActionEvent event) {
        esUpgrade = false;
        eshabitacion = true;
        cmbTipoHabitacion.setDisable(false);
        cmbHabitacionCambio.setDisable(false);
        cmbHabitacion.setDisable(true);
    }

    @FXML
    private void accionUpgrade(ActionEvent event) {
        esUpgrade = true;
        eshabitacion = false;
        cmbTipoHabitacion.setDisable(true);
        cmbHabitacionCambio.setDisable(true);
        cmbHabitacion.setDisable(false);
    }

    @FXML
    private void accionConfirmar(ActionEvent event) {
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("CONFIRMACION");
        alertaConfirmacion.setContentText("¿LOS DATOS INGRESADOS SON CORRECTOS?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
            btnAceptar.setDisable(false);
        } else {
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("CONFIRMACION");
            alertaConfirmacion.setContentText("INGRESE CORRECTAMENTE LOS DATOS PARA CONTINUAR");
            alertaConfirmacion.showAndWait();
        }
    }

    public void obtenerInformacion(Folio folio) {
        this.folio = folio;

    }

    private void llenarComboBox() {
        cmbTipoHabitacion.setItems(tiposhabitaciones);
        cmbHabitacion.setItems(habitaciones);
        cmbTipoHabitacion.setOnAction(e -> {
            tipoHabitacionSeleccion = cmbTipoHabitacion.getValue();
            habitacionesFiltradas = habitaciones.filtered(
                    habitacion -> habitacion.getIdTipo() == tipoHabitacionSeleccion.getIdTipo() && habitacion.getEstatus() == 1
            );
            cmbHabitacionCambio.setItems(habitacionesFiltradas);
        });
        habitacionesFiltradas = habitaciones.filtered(
                habitacion -> habitacion.getIdTipo() == 2 && habitacion.getEstatus() == 1
        );
        cmbHabitacion.setItems(habitacionesFiltradas);
        cmbHabitacion.setOnAction(e -> {
            habitacionSeleccion = cmbHabitacion.getValue();
        });
        cmbHabitacionCambio.setOnAction(e -> {
            habitacionSeleccion = cmbHabitacionCambio.getValue();
        });
    }

    private void traerHabitaciones() throws SQLException {
        con = conexion.conectar2();
        habitacionDAO = new HabitacionDAO(con);
        TipoHabitacionDAO tipoHabitacionDAO = new TipoHabitacionDAO(con);
        habitaciones = FXCollections.observableArrayList(habitacionDAO.obtenerHabitaciones());
        tiposhabitaciones = FXCollections.observableArrayList(tipoHabitacionDAO.getAllTiposHabitacion());
        llenarComboBox();
        con.close();
    }

    private void hacerElDesmadreDelCambioHabitacion() throws SQLException {
        con = conexion.conectar2();
        habitacionDAO = new HabitacionDAO(con);
        folio.setId_habitacion(cmbHabitacionCambio.getSelectionModel().getSelectedItem().getId());
        folio.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

        habitacionDAO.CambiarHabitacion(folio);
        con.close();
    }

}
