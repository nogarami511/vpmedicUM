/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Habitacion;
import clases_hospital_DAO.HabitacionDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vistasAuxiliares_hospital.AgregarHabitacionController;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class HabitacionesController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Habitacion> tabla;
    @FXML
    private TableColumn<Habitacion, String> clmHabitacion;
    @FXML
    private TableColumn<Habitacion, Integer> clmNumero;
    @FXML
    private TableColumn<Habitacion, Integer> clmPiso;
    @FXML
    private TableColumn<Habitacion, Integer> clmPrioridad;
    @FXML
    private TableColumn<Habitacion, Integer> clmEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
            pintarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(HabitacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
private void actionBtnEditar(ActionEvent event) throws IOException, SQLException {
    Habitacion habitacion = this.tabla.getSelectionModel().getSelectedItem();

    if (habitacion == null) {
        alertaError.setTitle("ERROR!");
        alertaError.setHeaderText("NO HA SELECCIONADO UNA HABITACION");
        alertaError.setContentText("PARA EDITAR LA HABITACION\n\n(1) SELECIONE LA HABITACION QUE DESEA EDITAR\n(2) PRECIONE EL BOTON \"EDITAR\"");
        alertaError.showAndWait();
    } else {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarHabitacion.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);

        AgregarHabitacionController agregarH = fxml.getController();

        agregarH.modo(true);
        agregarH.recibirIdHabitacion(habitacion.getId());
        System.out.println("" + habitacion.getTipo() + " - " + habitacion.getNumeroHabitacion() + " - " + habitacion.getPiso() + " - " + habitacion.getEstatus() + " - " + habitacion.getObservaciones() + " - " + habitacion.getPrioridad());
        agregarH.recibirDatos(habitacion.getTipo(), habitacion.getNumeroHabitacion(), habitacion.getPiso(), habitacion.getEstatus(), habitacion.getObservaciones(), habitacion.getPrioridad());

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
    private void aactionBtnAgregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarHabitacion.fxml"));
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
    private void actionBtnCancelar(ActionEvent event) {
    }

    private void llenarTabla() throws SQLException {
        System.out.println("llenado tabla...");
        Connection connection = null;

        connection = conexion.conectar2();
        HabitacionDAO habitacionDAO = new HabitacionDAO(connection);
        ObservableList<Habitacion> habitaciones = habitacionDAO.llenarTabla();

        clmHabitacion.setCellValueFactory(new PropertyValueFactory("tipo"));
        clmNumero.setCellValueFactory(new PropertyValueFactory("numeroHabitacion"));
        clmPiso.setCellValueFactory(new PropertyValueFactory("piso"));
        clmPrioridad.setCellValueFactory(new PropertyValueFactory("prioridad"));
        clmEstatus.setCellValueFactory(new PropertyValueFactory("observaciones"));

        tabla.setItems(habitaciones);

    }

    private void pintarTabla() {
        tabla.setRowFactory(tv -> new TableRow<Habitacion>() {
            @Override
            protected void updateItem(Habitacion item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.getEstatus() == 2) {
                        setStyle("-fx-background-color: #e63946; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
                    } 
                }
            }
        });
    }
}
