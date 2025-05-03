/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.ConsumoPacienteMezclas;
import clases_hospital.Folio;
import clases_hospital_DAO.ConsumoPacienteMezclasDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class mezclasController implements Initializable {

    
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    int idPaciente, idFolio,idPaquete;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnRerporte;
    @FXML
    private Text txtNombre;
    @FXML
    private TableView<ConsumoPacienteMezclas> tablaConsumo;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, String> clmTipoConsumo;
    @FXML
    private Text txtFolio;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Integer> clmConsumido;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Double> clmExedente;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Integer> clmDevolucion;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Integer> clmEntregado;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Double> clmPaquete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actionAgregarConsumo(ActionEvent event) {
    }

    @FXML
    private void actionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void reporte(ActionEvent event) {
    }

    public void recibirDatos(Folio folio) throws SQLException {
        txtNombre.setText(folio.getNombre());
        txtFolio.setText(folio.getFolio());
        
        this.idPaciente = folio.getIdPaciente();
        this.idFolio = folio.getId();
        this.idPaquete = folio.getId_paquete();
        llenarTabla();
    }

    public void llenarTabla() throws SQLException {
        Connection connection = null;

        try {
            connection = conexion.conectar2();
            ConsumoPacienteMezclasDAO consumoPacienteMezclasDAO = new ConsumoPacienteMezclasDAO(connection);
            ObservableList<ConsumoPacienteMezclas> consumoP = consumoPacienteMezclasDAO.llenarTablaVisualizarFolioController(idFolio, idPaquete);
            
            clmTipoConsumo.setCellValueFactory(new PropertyValueFactory("insumo"));
            clmEntregado.setCellValueFactory(new PropertyValueFactory("cantidadEntregada"));
            clmConsumido.setCellValueFactory(new PropertyValueFactory("consumo"));
            clmPaquete.setCellValueFactory(new PropertyValueFactory("cantidadPaquete"));
            clmExedente.setCellValueFactory(new PropertyValueFactory("exedenteMezcla"));
            clmDevolucion.setCellValueFactory(new PropertyValueFactory("devolucion"));
            
            
            tablaConsumo.setItems(consumoP);
        } catch (Exception e) {

        } finally {

        }

    }

}
