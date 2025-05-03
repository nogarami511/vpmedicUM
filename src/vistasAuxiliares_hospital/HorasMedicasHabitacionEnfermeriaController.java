/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Consumo;
import clases_hospital.Folio;
import clases_hospital.TipoHospitalizaciones;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.TipoHospitalizacionDAO;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class HorasMedicasHabitacionEnfermeriaController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);

    ObservableList<TipoHospitalizaciones> tipoHospitalizaciones = FXCollections.observableArrayList();
    ObservableList<Consumo> consumos = FXCollections.observableArrayList();

    FoliosDAO foliosdao;
    TipoHospitalizacionDAO tipoHabitacionDAO;

    Folio folioGuardado;
    Folio folioHab;
    TipoHospitalizaciones tipoHabitaciones;

    @FXML
    private Label lblNombrePaciente;
    @FXML
    private Label lblDiasHoras;
    @FXML
    private Label lblCuenta;
    @FXML
    private Label lblFechaIngreso;
    @FXML
    private ComboBox<TipoHospitalizaciones> cmbTipoHospitalizacion;
    @FXML
    private TextField txfCantidad;
    @FXML
    private RadioButton rdbHoras;
    @FXML
    private RadioButton rdbDias;
    @FXML
    private TableView<?> TABLA;
    @FXML
    private TableColumn<?, ?> colConcepto;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TableColumn<?, ?> colPrecioUnitario;
    @FXML
    private TableColumn<?, ?> colTotal;
    @FXML
    private RadioButton rdbPaquete;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        if (folioGuardado.getId_paquete() > 0) {
        }
        colConcepto.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("telefono"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("nombreMedico"));
        colTotal.setStyle("-fx-alignment: CENTER;");
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void optenerDatos(Folio folio) {
        folioGuardado = folio;
        optenerDatosHabitacion(folio.getId());
        llenarCmbox();
    }

    private void optenerDatosHabitacion(int id_folio) {
        con = conexion.conectar2();
        foliosdao = new FoliosDAO(con);

        try {
            folioHab = foliosdao.optenerInfoHabFolio(id_folio);
            lblNombrePaciente.setText(folioHab.getNombre_paciente());
            lblFechaIngreso.setText(folioHab.getFecha_Ingreso_String());
            lblDiasHoras.setText(folioHab.getDias() + " Dias con " + folioHab.getHoras() + " Horas");
            rdbPaquete.setSelected(folioHab.isPaquete());
        } catch (SQLException ex) {
            Logger.getLogger(HorasMedicasHabitacionEnfermeriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarCmbox() {
        con = conexion.conectar2();
        tipoHabitacionDAO = new TipoHospitalizacionDAO(con);

        tipoHospitalizaciones.addAll(tipoHabitacionDAO.getAllTiposHabitacion());
        cmbTipoHospitalizacion.setItems(tipoHospitalizaciones);
        cmbTipoHospitalizacion.valueProperty().addListener((obs, oldVal, newVal) -> {
            tipoHabitaciones = newVal;
            System.out.println("Objeto seleccionado: " + tipoHabitaciones);
        });
    }
    
    private void agregarDatosTabla(){
        for(int i = 0; i < 3; i++){
            Consumo consumo = new Consumo();
            consumo.setTipo(tipoHabitaciones.getNombre_tipo_hozpitalizacion()); 
        }
    }
}
