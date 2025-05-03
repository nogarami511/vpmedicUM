/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.HabitacionesPaciente;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vistasAuxiliares_hospital.VincularPacienteCuartoController;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class HabitacionesPacienteController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<HabitacionesPaciente> habitacionesPacientes = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    @FXML
    private TableView<HabitacionesPaciente> tabla;
    @FXML
    private TableColumn folioHabitacionesPaciente;
    @FXML
    private TableColumn nombreHabitacionesPaciente;
    @FXML
    private TableColumn habitacionHabitacionesPaciente;
    @FXML
    private TableColumn fechaHabitacionPaciente;
    @FXML
    private TableColumn estatusHospitalizacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
    }

    @FXML
    private void irVisualizar(ActionEvent event) throws IOException, SQLException {
        HabitacionesPaciente habitacionPaciente = tabla.getSelectionModel().getSelectedItem();
        if (habitacionPaciente.getIdEstatusHospitalizacion() == 0) {
            alertaError.setHeaderText("PACIENTE SIN INGRESO");
            alertaError.setContentText("NO SE HA REGISTRADO LA ENTRADA DEL PACIENTE");
            alertaError.showAndWait();
        } else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VincularPacienteCuarto.fxml"));
            Parent root = loader.load();
            VincularPacienteCuartoController controller = loader.getController();
            controller.recibirDatos(habitacionPaciente.getNombrePaciente(), habitacionPaciente.getIdFolio(), habitacionPaciente.getIdPaciente());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("ASIGNAR HABITACION");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);

            stage.showAndWait();
            llenarTabla();
        }
    }

    public void llenarTabla() {
        con = conexion.conectar2();
        try {
            this.tabla.getItems().clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT f.id, f.folio ,f.id_habitacion, f.fechaCreacion, p.nombre_paciente, f.id_estatus_hospitalizacion, eh.nombre, p.id_paciente \n"
                    + "                                        FROM folios f\n"
                    + "                                        -- INNER JOIN tipoHabitacion h ON h.id_tipo = f.id_habitacion\n"
                    + "                                        INNER JOIN pacientes p ON p.id_paciente = f.id_paciente\n"
                    + "                                        INNER JOIN estatus_hospitalizacion eh ON eh.id = f.id_estatus_hospitalizacion\n"
                    + "                                        WHERE f.id_estatus_hospitalizacion = 1 AND f.numero_habitacion = 0 AND NOT (f.folio LIKE 'H-%') ;");
            HabitacionesPaciente habitacionPaciente;
            while (rs.next()) {
                habitacionPaciente = new HabitacionesPaciente();
                habitacionPaciente.setIdFolio(rs.getInt(1));
                habitacionPaciente.setFolio(rs.getString(2));
                habitacionPaciente.setIdTipoHabitacion(rs.getInt(3));
                habitacionPaciente.setFechaApartado(rs.getDate(4));
                //habitacionPaciente.setTipoHabitacion(rs.getString(4));
                habitacionPaciente.setNombrePaciente(rs.getString(5));
                habitacionPaciente.setIdEstatusHospitalizacion(rs.getInt(6));
                habitacionPaciente.setEstatusHospitalizacion(rs.getString(7));
                habitacionPaciente.setIdPaciente(rs.getInt(8));
                habitacionesPacientes.add(habitacionPaciente);
            }
            folioHabitacionesPaciente.setCellValueFactory(new PropertyValueFactory("folio"));
            nombreHabitacionesPaciente.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
            habitacionHabitacionesPaciente.setCellValueFactory(new PropertyValueFactory("tipoHabitacion"));
            fechaHabitacionPaciente.setCellValueFactory(new PropertyValueFactory("fechaApartado"));
            estatusHospitalizacion.setCellValueFactory(new PropertyValueFactory("estatusHospitalizacion"));
            tabla.setItems(habitacionesPacientes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
