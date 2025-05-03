/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.SolicitudPago;
import clases_hospital.ConfirmacionAutorizacion;
import clases_hospital_DAO.ConfirmacionAutorizacionDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class AutorizacionPagosController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    ObservableList<ConfirmacionAutorizacion> confirmacionAutorizaciones;

    @FXML
    private TableView<ConfirmacionAutorizacion> tablaPrincipalAutorizacion;
    @FXML
    private TableView<SolicitudPago> tablaVisualizarSolicitudes;
    @FXML
    private TableColumn<?, ?> solicitudAutorizacion;
    @FXML
    private TableColumn<?, ?> fechaAutorizacion;
    @FXML
    private TableColumn<?, ?> montoTotalAutorizacion;
    @FXML
    private TableColumn<?, ?> formaPagoAutorizacion;
    @FXML
    private TableColumn<?, ?> solicitanteAutorizacion;
    @FXML
    private TableColumn<?, ?> estatusAutorizacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void llenarTablaConfirmacionAutorizacion() {
        //tablaPrincipalAutorizacion
        try {
            con = conexion.conectar2();
            ConfirmacionAutorizacionDAO confirmacionAutorizacionDAO = new ConfirmacionAutorizacionDAO(con);
            // Obtener todos los pacientes desde la base de datos
            List<ConfirmacionAutorizacion> confirmacionesAutorizaciones = confirmacionAutorizacionDAO.obtenerTodos();

            // Crear una lista observable para los datos de la tabla
            confirmacionAutorizaciones = FXCollections.observableArrayList(confirmacionesAutorizaciones);

            // Asignar las propiedades de los pacientes a las columnas de la tabla
            solicitudAutorizacion.setCellValueFactory(new PropertyValueFactory("id_confirmacionAutorizacion"));
            fechaAutorizacion.setCellValueFactory(new PropertyValueFactory(""));
            montoTotalAutorizacion.setCellValueFactory(new PropertyValueFactory("montoTotalAAutorizar"));
            formaPagoAutorizacion.setCellValueFactory(new PropertyValueFactory("formaPagoString"));
            solicitanteAutorizacion.setCellValueFactory(new PropertyValueFactory(""));
            estatusAutorizacion.setCellValueFactory(new PropertyValueFactory("estatus"));

            tablaPrincipalAutorizacion.setItems(confirmacionAutorizaciones);
            con.close();
            generarBotones();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void llenarTablaSolicitudPagos() throws SQLException {
        con = conexion.conectar2();
        
        con.close();
    }

    public void generarBotones() {

    }
}
