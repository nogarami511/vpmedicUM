/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Meses;
import clases_hospital.OtrosIngresos;
import clases_hospital_DAO.OtrosIngresosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class OtrosIngresosController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    ObservableList<OtrosIngresos> otrosIngresos;
    @FXML
    private TextField txfRazonSocial;
    @FXML
    private TableView<OtrosIngresos> tabla;
    @FXML
    private ComboBox<Meses> mesSeleccion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mesSeleccion.setItems(new Meses().mesesAnio());
      
        //llenarTabla();
//        costoAnterior.setCellValueFactory(new PropertyValueFactory("costoAnterior"));
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        OtrosIngresosDAO otrosIngresosDAO = new OtrosIngresosDAO(con);
        otrosIngresos = FXCollections.observableArrayList(otrosIngresosDAO.obtenerPorMesActual());
        tabla.setItems(otrosIngresos);
        con.close();
    }

    public void llenarTablaMesSeleccionado() throws SQLException {
        con = conexion.conectar2();
        OtrosIngresosDAO otrosIngresosDAO = new OtrosIngresosDAO(con);
        otrosIngresos = FXCollections.observableArrayList(otrosIngresosDAO.obtenerPorMes(mesSeleccion.getValue().getNumeroMes()));
        tabla.setItems(otrosIngresos);
        con.close();
    }

    @FXML
    private void ingresar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/OtrosIngresosNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("AGREGAR OTRO INGRESO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void visualizar(ActionEvent event) {
        //REPORTE DE INGRESO
    }

    @FXML
    private void cancelar(ActionEvent event) {

    }

}
