/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Medico;
import clases_hospital_DAO.MedicoDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vistasAuxiliares_hospital.AgregarMedicoController;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class MedicosController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Medico> medicos = FXCollections.observableArrayList();

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnSalir;
    @FXML
    private TableColumn nombreMedico;
    @FXML
    private TableColumn telefonoMedico;
    @FXML
    private TableColumn correoMedico;
    @FXML
    private TableColumn cedulaMedico;
    @FXML
    private TableColumn especialidadMedico;
    @FXML
    private TableView<Medico> tabla;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(MedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarMedico.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("PACIENTE NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void editar(ActionEvent event) throws IOException, SQLException {
        Medico medicoSelected = this.tabla.getSelectionModel().getSelectedItem();

        if (medicoSelected == null) {
            alertaError.setTitle("ERROR!");
            alertaError.setHeaderText("NO HA SELECCIONADO UN MEDICO");
            alertaError.setContentText("PARA EDITA0R UN MEDICO\n\n(1) SELECIONE EL MEDICO QUE DESEA EDITAR\n(2) PRECIONE EL BOTON \"EDITAR\"");
            alertaError.showAndWait();
        } else {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarMedico.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);
         
            AgregarMedicoController agregarM = fxml.getController();
            Medico medico = new Medico(medicoSelected.getId_medico(),
                    medicoSelected.getNombre(),
                    medicoSelected.getTitulo(),
                    medicoSelected.getCedula(),
                    medicoSelected.getCedula_especialidad(),
                    medicoSelected.getEspecialidad(),
                    medicoSelected.getCertificado(),
                    medicoSelected.getTelefono(),
                    medicoSelected.getRfc(),
                    medicoSelected.getDireccion(),
                    medicoSelected.getCorreo(),
                    medicoSelected.getFecha_nacimiento(),
                    medicoSelected.getLugar_nacimiento(),
                    medicoSelected.getCurp(),
                    medicoSelected.getId_tipo(),
                    medicoSelected.getId_estatus());
            agregarM.recibirDatos(medico, true);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setTitle("PACIENTE NUEVO");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
            llenarTabla();
        }

    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        try {
            con = conexion.conectar2();
            Medico medicoSelected = tabla.getSelectionModel().getSelectedItem();
            Connection connection = null;
            alertaConfirmacion.setTitle("CONFIRMACION");
            alertaConfirmacion.setHeaderText("ELIMINACION DE MEDICO");
            alertaConfirmacion.setContentText("¿Está seguro de eliminar a "+medicoSelected.getNombre()+"?");
            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
            if (action.get() == ButtonType.OK) {
                MedicoDAO medicoDAO = new MedicoDAO(con);
                medicoDAO.desactivarMedico(medicoSelected.getId_medico());
                alertaSuccess.setTitle("EXITO!");
                alertaSuccess.setHeaderText("PROCEDIMIENTO REALIZADO DE MANERA CORECTA");
                alertaSuccess.setContentText("MEDICO ELIMINADO DE MANERA CORRECTA");
                alertaSuccess.showAndWait();
                llenarTabla();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close(); // Cierra la conexión
            }
        }

    }

    public void llenarTabla() throws SQLException {
        this.tabla.getItems().clear();
        Connection connection = null;
        connection = conexion.conectar2();
        nombreMedico.setCellValueFactory(new PropertyValueFactory("nombre"));
        telefonoMedico.setCellValueFactory(new PropertyValueFactory("telefono"));
        correoMedico.setCellValueFactory(new PropertyValueFactory("correo"));
        cedulaMedico.setCellValueFactory(new PropertyValueFactory("cedula"));
        especialidadMedico.setCellValueFactory(new PropertyValueFactory("especialidad"));

        ObservableList<Medico> medicos = FXCollections.observableArrayList();
        Medico medico;

        String sql = "SELECT m.id_medico, m.nombre, m.titulo, m.cedula, m.cedula_especialidad, m.especialidad, m.certificado, m.telefono, m.rfc, m.direccion, m.correo, m.fecha_nacimiento, m.lugar_nacimiento, m.curp, m.id_tipo_medico, m.id_estatus FROM medicos m WHERE m.id_estatus = 1;";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                medico = new Medico(rs.getInt(1),//idmedico
                        rs.getString(2),//nombre
                        rs.getString(3),//titulo
                        rs.getString(4),//cedula
                        rs.getString(5),//cedula especialidad
                        rs.getString(6),// especialidad
                        rs.getString(7),// certificado
                        rs.getString(8),// telefono
                        rs.getString(9),//rfc
                        rs.getString(10),//direccion
                        rs.getString(11),//correo
                        rs.getDate(12),// fecha de nacimiento
                        rs.getString(13),//lugar de nacimiento
                        rs.getString(14),//curp
                        rs.getInt(15),//id tipo medico
                        rs.getInt(16));//id estatus
                medicos.add(medico);
                tabla.getItems().add(medico);
            }

        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close(); // Cierra la conexión
            }
        }
    }

}
