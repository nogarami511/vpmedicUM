/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Censos;
import clases_hospital_DAO.CensosDAO;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CensoFXMLController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<Censos> censos = FXCollections.observableArrayList();

    Conexion conexion = new Conexion();

    Censos censo = new Censos();
    CensosDAO censosDAO;

    @FXML
    private DatePicker dtpFecha;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnImprimir;
    @FXML
    private TableView<Censos> tabla;
    @FXML
    private TableColumn<?, ?> colHabitacion;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colEdad;
    @FXML
    private TableColumn<?, ?> colSexo;
    @FXML
    private TableColumn<?, ?> colMedico;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> coldiagnostico;
    @FXML
    private TableColumn<?, ?> colPlan;
    @FXML
    private TableColumn<?, ?> colAgregar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void llenarTabla() throws SQLException {
        censos.clear();
        censosDAO = new CensosDAO(conexion.conectar2());
        censos.addAll(censosDAO.ejecutarProcedimiento(""+dtpFecha.getValue(), VPMedicaPlaza.userSystem));

        colHabitacion.setCellValueFactory(new PropertyValueFactory("numeroHabitacion"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
        colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        colSexo.setCellValueFactory(new PropertyValueFactory("nombreMedico"));
        colMedico.setCellValueFactory(new PropertyValueFactory("fechaIngreso"));
        colFecha.setCellValueFactory(new PropertyValueFactory("diasInternado"));
        coldiagnostico.setCellValueFactory(new PropertyValueFactory("diagnosticoCenso"));
        colPlan.setCellValueFactory(new PropertyValueFactory("planCenso"));
//        colAgregar.setCellValueFactory(new PropertyValueFactory("piso"));

        tabla.setItems(censos);
    }

    @FXML
    private void accionBuscar(ActionEvent event) {
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(CensoFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionImpirmir(ActionEvent event) {
    }

}
