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
import clases_hospital_DAO.FoliosDAO;
import clases_hospital.HabitacionesPaciente;
import clases_hospital.TipoHabitacion;
import clases_hospital_DAO.HabitacionDAO;
import clases_hospital_DAO.TipoHabitacionDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class VincularPacienteCuartoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    //HabitacionesPaciente habitacionPaciente;
    ObservableList<AsignacionHabitacion> asighab = FXCollections.observableArrayList();
    Alert alertaSuccess = new Alert(Alert.AlertType.CONFIRMATION);

    TipoHabitacion tipoHabitacionSeleccion;
    Habitacion habitacionSeleccion;
    TipoHabitacion tipoHabitacionUPGSeleccion;
    Habitacion habitacionUPGSeleccion;
    ObservableList<TipoHabitacion> tiposhabitaciones;
    ObservableList<Habitacion> habitaciones;
    ObservableList<Habitacion> habitacionesFiltradas;

    int idFolio = 0;
    int idPaciente = 0;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField tfNombrePaciente;
    @FXML
    private ComboBox<TipoHabitacion> tipoHabitacionCBX;
    @FXML
    private ComboBox<Habitacion> habitacionCBX;
    @FXML
    private ComboBox<TipoHabitacion> tipoHabitacionCBXUPG;
    @FXML
    private ComboBox<Habitacion> habitacionCBXUPG;

    /**
     * Initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void agregar(ActionEvent event) throws SQLException, IOException {
        con = conexion.conectar2();
        FoliosDAO folioDAO = new FoliosDAO(con);
        Folio folio = folioDAO.obtenerFolio(idFolio);

        HabitacionDAO habitacionDAO = new HabitacionDAO(con);
        habitacionDAO.habitacionOcupada(habitacionSeleccion.getId());
   
        // if (folioDAO.verificarConsumoHabirtacion(folio.getId()) == false) {//si no tiene nada
        try {
            //ACTUALIZAMOS LA HABITACION EN DONDE ESTÁ CON NOW() EN SALIDA
            folioDAO.actualizarHoraSalida(folio.getId(), folio.getId_habitacion(), folio.getNumero_habitacion());
            CallableStatement stmt = null;
            String sql = "{call ingresoPacienteHabitacion2 (?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setInt(1, idPaciente);
            stmt.setString(2, tfNombrePaciente.getText());
            stmt.setInt(3, idFolio);
            stmt.setInt(4, tipoHabitacionSeleccion.getIdTipo());
            stmt.setInt(5, habitacionSeleccion.getNumeroHabitacion());
            stmt.setInt(6, 24);
            stmt.setInt(7, 0);
            stmt.setDouble(8, 1681.03);//tarifa Normal en caso de que se pase
            stmt.setDouble(9, 1681.03);//costo descuento (tarifa) y descontar el porcentaje que ingresen
            stmt.setString(10, "SIN DESCUENTO");//motivoDescuento.getText()
            stmt.setString(11, "N/A");
            stmt.setInt(12, 1);
            stmt.setInt(13, habitacionSeleccion.getId());
            stmt.execute();
//            String sql2 = "{call actualizarHabitacionPaciente (?,?)}";
//            stmt = con.prepareCall(sql2);
//            stmt.setInt(1, idPaciente);
//            stmt.setInt(2, habitacionSeleccion.getId());//idHabitacion
//            stmt.execute();
//            String sql3 = "{call actualizarNumeroHabitacionFolio (?,?)}";
//            stmt = con.prepareCall(sql3);
//            stmt.setInt(1, idFolio);
//            stmt.setInt(2, habitacionSeleccion.getNumeroHabitacion());//numero Habitacion
//            stmt.execute();

            alertaSuccess.setHeaderText("Paciente ingresado a la habitacion");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
        con.close();
    
    }

    public void recibirDatos(String nombrePaciente, int idFolio, int idPaciente) throws SQLException {
        //this.habitacionPaciente = habitacionPaciente;
        tfNombrePaciente.setText(nombrePaciente);
        this.idFolio = idFolio;
        this.idPaciente = idPaciente;
        traerHabitaciones();
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void llenarChiseBox() throws SQLException {
        tipoHabitacionCBX.setItems(tiposhabitaciones);
        habitacionCBX.setItems(habitaciones);
        tipoHabitacionCBX.setOnAction(e -> {
            tipoHabitacionSeleccion = tipoHabitacionCBX.getValue();
            habitacionesFiltradas = habitaciones.filtered(
                    habitacion -> habitacion.getIdTipo() == tipoHabitacionSeleccion.getIdTipo() && habitacion.getEstatus() == 1
            );
            
                 habitacionCBX.setItems(habitacionesFiltradas);

            
            
           
        });
        habitacionesFiltradas = habitaciones.filtered(
                habitacion -> habitacion.getIdTipo() == 2 && habitacion.getEstatus() == 1
        );
        
        if(habitacionesFiltradas.isEmpty()){
          habitacionCBX.setDisable(true);
          tipoHabitacionCBXUPG.setDisable(false);
          habitacionCBXUPG.setDisable(false);
        }
        else{
            habitacionCBX.setItems(habitacionesFiltradas);
            habitacionCBX.setOnAction(e -> {
            habitacionSeleccion = habitacionCBX.getValue();
        });
        }
        
    }
    public void llenarChiseBoxUPGRADE() throws SQLException {
        tipoHabitacionCBXUPG.setItems(tiposhabitaciones);
        habitacionCBXUPG.setItems(habitaciones);
        tipoHabitacionCBX.setOnAction(e -> {
            tipoHabitacionSeleccion = tipoHabitacionCBX.getValue();
            habitacionesFiltradas = habitaciones.filtered(
                    habitacion -> habitacion.getIdTipo() != tipoHabitacionSeleccion.getIdTipo() && habitacion.getEstatus() == 1
            );
            habitacionCBX.setItems(habitacionesFiltradas);
        });
        habitacionesFiltradas = habitaciones.filtered(
                habitacion -> habitacion.getIdTipo() == 2 && habitacion.getEstatus() == 1
        );
        habitacionCBX.setItems(habitacionesFiltradas);
        habitacionCBX.setOnAction(e -> {
            habitacionSeleccion = habitacionCBX.getValue();
        });
    }

    private void traerHabitaciones() throws SQLException {
        con = conexion.conectar2();
        HabitacionDAO habitacionDAO = new HabitacionDAO(con);
        TipoHabitacionDAO tipoHabitacionDAO = new TipoHabitacionDAO(con);
        habitaciones = FXCollections.observableArrayList(habitacionDAO.obtenerHabitaciones());
        tiposhabitaciones = FXCollections.observableArrayList(tipoHabitacionDAO.getAllTiposHabitacion());
        llenarChiseBox();
        con.close();
    }

}
