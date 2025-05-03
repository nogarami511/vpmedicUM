/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.ComisionPorPago;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import clases_hospital.Pagos;
import clases_hospital_DAO.ComisionPorPagoDAO;
import clases_hospital_DAO.PagosDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class ComisionesPorPagoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    ObservableList<ComisionPorPago> comisionPorPagos = FXCollections.observableArrayList();
    @FXML
    private TableView<ComisionPorPago> tabla;
    @FXML
    private TableColumn nombrePaciente;
    @FXML
    private TableColumn formaPago;
    @FXML
    private TableColumn totalPago;
    @FXML
    private TableColumn comision;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ComisionesPorPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        ComisionPorPagoDAO comisionPorPagoDAO = new ComisionPorPagoDAO(con);
        comisionPorPagos = comisionPorPagoDAO.obtenerPorElMesActual();
        tabla.setItems(comisionPorPagos);
        nombrePaciente.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
        formaPago.setCellValueFactory(new PropertyValueFactory("tipoPagoString"));
        totalPago.setCellValueFactory(new PropertyValueFactory("totalPagoFormateado"));
        comision.setCellValueFactory(new PropertyValueFactory("comisionFormateado"));
        con.close();
    }

    @FXML
    private void btnGenerarReporte(ActionEvent event) {
       
    }

}
