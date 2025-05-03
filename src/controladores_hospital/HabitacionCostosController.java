/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.CostoHabitacion;
import clases_hospital.CostoHabitacionDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class HabitacionCostosController implements Initializable {
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<CostoHabitacion> tabla;
    @FXML
    private TableColumn<CostoHabitacion, String> clmHabitacion;
    @FXML
    private TableColumn<CostoHabitacion, String> clmCostoTipo;
    @FXML
    private TableColumn<CostoHabitacion, Double> clmCosto;
    @FXML
    private TableColumn<CostoHabitacion, Integer> clmHoras;
    @FXML
    private TableColumn<CostoHabitacion, Integer> clmMinutos;
    @FXML
    private TableColumn<CostoHabitacion, Integer> clmHorasTolerancia;
    @FXML
    private TableColumn<CostoHabitacion, Integer> clmMinutosTolerancia;
    @FXML
    private Button btnEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(HabitacionCostosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void actionBtnAgregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas_hospital/CostoHabitacion.fxml"));
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
    private void actionBtnEditar(ActionEvent event) throws IOException, SQLException {
        CostoHabitacion costo = this.tabla.getSelectionModel().getSelectedItem();
     FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas_hospital/CostoHabitacion.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);

        CostoHabitacionController costoH = fxml.getController();
        costoH.recibirDatos(costo.getTipoHabitacion(),costo.getPrecio(), costo.getNombre(), costo.getHoras(), costo.getMinutos(), costo.getHorasTolerancia(), costo.getMinutosTolerancia(),true, costo.getId(),costo.getIdHabitacion());
        
        
        
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
    
    private void llenarTabla() throws SQLException{
        Connection connection = null;
        connection = conexion.conectar2();
        CostoHabitacionDAO costoHabitacionDAO = new CostoHabitacionDAO(connection);
        
        ObservableList<CostoHabitacion> costos = costoHabitacionDAO.llenarTabla();
        
        clmHabitacion.setCellValueFactory(new PropertyValueFactory("tipoHabitacion"));
        clmCostoTipo.setCellValueFactory(new PropertyValueFactory("nombre"));
        clmCosto.setCellValueFactory(new PropertyValueFactory("precio"));
        clmHoras.setCellValueFactory(new PropertyValueFactory("horas"));
        clmMinutos.setCellValueFactory(new PropertyValueFactory("minutos"));
        clmHorasTolerancia.setCellValueFactory(new PropertyValueFactory("horasTolerancia"));
        clmMinutosTolerancia.setCellValueFactory(new PropertyValueFactory("minutosTolerancia"));
        
        tabla.setItems(costos);
    }
}
