/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Folio;
import clases_hospital_DAO.ConsumoQuirofanoDAO;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vistasAuxiliares.AgregarConsumoPacienteController;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class FoliosController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertError = new Alert(Alert.AlertType.ERROR);
    Alert alertinfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaOpciones = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaWARNING = new Alert(Alert.AlertType.WARNING);

    ObservableList<Folio> folios = FXCollections.observableArrayList();
    @FXML
    private TableView<Folio> tabla;
    @FXML
    private TableColumn<Folio, String> clmPaciente;
    @FXML
    private TableColumn<?, ?> colNumeroHabitacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
            // buscadorDePaciente();
        } catch (SQLException ex) {
            Logger.getLogger(FoliosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        Connection connection = null;
        connection = conexion.conectar2();
        ConsumoQuirofanoDAO consumoQuirofanoDAO = new ConsumoQuirofanoDAO(connection);
        ObservableList<Folio> folios = consumoQuirofanoDAO.llenarTabla();

        clmPaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
        colNumeroHabitacion.setCellValueFactory(new PropertyValueFactory("numero_habitacion"));
        tabla.setItems(folios);
    }

    @FXML
    private void agregarConsumo(ActionEvent event) throws IOException, SQLException {
        Folio folioSeleccionado = tabla.getSelectionModel().getSelectedItem();

        alertaOpciones.setTitle("VISUALIZAR");
        alertaOpciones.setHeaderText(null);
        alertaOpciones.setContentText("¿DESEA VER LA CUENTA?\nPACIENTE: " + folioSeleccionado.getNombre());

        Optional<ButtonType> option = alertaOpciones.showAndWait();

        if (option.get() == ButtonType.OK) {
        if (folioSeleccionado.getNumero_habitacion() == 0) {
            alertaWARNING.setHeaderText("PACIENTE SIN PULSERA");
            alertaWARNING.setContentText("SE DEBE ASIGNAR HABITACIÓN AL PACIENTE"
                    + "PARA QUE PUEDAN GENERAR LA PULSERAS");
            alertaWARNING.showAndWait();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/AgregarConsumoPaciente.fxml"));
            Parent root = loader.load();

            AgregarConsumoPacienteController controller = loader.getController();
            Folio folio = new Folio(folioSeleccionado.getIdPaciente(), folioSeleccionado.getId(), folioSeleccionado.getFolio(), folioSeleccionado.getNombre(), folioSeleccionado.getId_paquete());
            controller.recibirDatos(folio);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMaximized(false);
            stage.setTitle("CONSUMO PACIENTE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            this.tabla.getItems().clear();
            llenarTabla();
            stage.showAndWait();
        }
    }

}
