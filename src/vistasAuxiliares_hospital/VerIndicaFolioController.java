/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.IndicaDetalle;
import clases_hospital.Indicasp;
import clases_hospital_DAO.IndicaDetalleDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import reportes.ReporteC;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class VerIndicaFolioController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    ObservableList<IndicaDetalle> indicasdetalles = FXCollections.observableArrayList();

    IndicaDetalleDAO indicaDetalleDAO;

    Indicasp indicaDetalleGuardada;
    
    Paciente pasiienteGuardado;

    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<IndicaDetalle> tabla;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private TableColumn<?, ?> colCantidadEntregada;
    @FXML
    private TableColumn<?, ?> colCantidadSuministrada;
    @FXML
    private TableColumn<?, ?> colCantidadDevuelta;
    @FXML
    private Button btnValidar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void setObjeto(Indicasp indicaDetalle, Paciente paciente) {
        indicaDetalleGuardada = indicaDetalle;
        this.pasiienteGuardado = paciente;
        llenarTabla();
    }

    @FXML
    private void accIonImprimirreporte(ActionEvent event) {
        ReporteC reporte = new ReporteC("reporteIndicaConsumo");
        reporte.generarReporteIndicaDevlucion(indicaDetalleGuardada.getIdFolio(), indicaDetalleGuardada.getIdIndicasp());
    }

    private void llenarTabla() {
        try {
            indicaDetalleDAO = new IndicaDetalleDAO(conexion.conectar2());
            indicasdetalles.addAll(indicaDetalleDAO.getAllIndicaDetallesbyFolioR(indicaDetalleGuardada.getIdIndicasp()));

            colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
            colCantidadEntregada.setCellValueFactory(new PropertyValueFactory("cantidadEntregada"));
            colCantidadSuministrada.setCellValueFactory(new PropertyValueFactory("cantidadSuministrada"));
            colCantidadDevuelta.setCellValueFactory(new PropertyValueFactory("cantidadDevolucion"));

            tabla.setItems(indicasdetalles);
        } catch (SQLException ex) {
            Logger.getLogger(VerIndicaFolioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionValidar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ValidarHojaConsumo.fxml"));
        Parent root = loader.load();
        ValidarHojaConsumoController destinoController = loader.getController();

        // Pasar el objeto a la vista de destino
        destinoController.setObjetos(indicaDetalleGuardada.getIdIndicasp(), pasiienteGuardado);
        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("VALIDAR CONSUMO");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);
        destinoStage.initStyle(StageStyle.UNDECORATED);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();
    }

}
