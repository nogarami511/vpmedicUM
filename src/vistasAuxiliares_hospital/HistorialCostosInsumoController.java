/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.HistorialCostosInsumo;
import clases_hospital_DAO.HistorialCostosInsumoDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class HistorialCostosInsumoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    int idInsumo = 0;
    ObservableList<HistorialCostosInsumo> historial;

    @FXML
    private TableView<HistorialCostosInsumo> tabla;
    @FXML
    private TableColumn<?, ?> nombreInsumo;
    @FXML
    private TableColumn<?, ?> costoAnterior;
    @FXML
    private TableColumn<?, ?> costoModificado;
    @FXML
    private TableColumn<?, ?> fechaModificacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        HistorialCostosInsumoDAO historialCostoInsumo = new HistorialCostosInsumoDAO(con);
        historial = FXCollections.observableArrayList(historialCostoInsumo.traerPorInsumo(idInsumo));
        tabla.setItems(historial);
        con.close();
    }

    public void setObjeto(int idInsumo) throws SQLException {
        this.idInsumo = idInsumo;
        llenarTabla();
        nombreInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
        costoAnterior.setCellValueFactory(new PropertyValueFactory("costoAnterior"));
        costoModificado.setCellValueFactory(new PropertyValueFactory("costoActual"));
        fechaModificacion.setCellValueFactory(new PropertyValueFactory("fechaMod"));
    }
}
