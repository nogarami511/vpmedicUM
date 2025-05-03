/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.ComandaDetalle;
import clases_hospital_DAO.ComandaDetalleDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class VisualizarComandaController implements Initializable {

    Connection con;
    Conexion conexion = new Conexion();
    int idComanda = 0;
    ObservableList<ComandaDetalle> comandasDetalles;
    @FXML
    private TableView<ComandaDetalle> tabla;
    @FXML
    private TableColumn<?, ?> nombreComida;
    @FXML
    private TableColumn<?, ?> costoComida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nombreComida.setCellValueFactory(new PropertyValueFactory("nombreComida"));
        costoComida.setCellValueFactory(new PropertyValueFactory("total"));
    }

    @FXML
    private void cancelarAlimento(ActionEvent event) {

    }

    public void setObjeto(int idComanda) throws SQLException {
        this.idComanda = idComanda;
        llenarTabla();
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        ComandaDetalleDAO comandaDetalleDAO = new ComandaDetalleDAO(con);
        comandasDetalles = FXCollections.observableArrayList(comandaDetalleDAO.obtenerComandaDetallePorID(idComanda));
        tabla.setItems(comandasDetalles);
        con.close();
    }
}
