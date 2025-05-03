/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.CuentaPaciente;
import clases_hospital_DAO.CuentaPacienteDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vistasAuxiliares_hospital.VisualizarFolioController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class CuentaPacienteController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertError = new Alert(Alert.AlertType.ERROR);
    Alert alertinfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaOpciones = new Alert(Alert.AlertType.CONFIRMATION);
    private TextField txfbuscador;
    @FXML
    private TableView<CuentaPaciente> tabla;
    @FXML
    private TableColumn<CuentaPaciente, String> clmFolio;
    @FXML
    private TableColumn<CuentaPaciente, String> clmPaciente;
    @FXML
    private TableColumn<CuentaPaciente, Date> clmFecha;
    @FXML
    private TableColumn<CuentaPaciente, String> clmHabitacion;
    @FXML
    private TableColumn<CuentaPaciente, Double> clmConsumoActual;
    @FXML
    private TableColumn<CuentaPaciente, Double> clmSaldoACubrir;
    @FXML
    private TableColumn<CuentaPaciente, Double> clmAbonos;
    @FXML
    private Button btnVercuenta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            buscadorDePaciente();
            llenarTabla();
            if (VPMedicaPlaza.userNameSystem.equals("ENFERMERIA") || VPMedicaPlaza.userNameSystem.equals("COCINA")) {
                btnVercuenta.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CuentaPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscadorDePaciente() throws SQLException {
//        Connection connection = null;
//
//        try {
//            connection = conexion.conectar2();
//
//            ConsumoQuirofanoDAO consumoQuirofanoDAO = new ConsumoQuirofanoDAO(connection);
//            ArrayList<Folio> nombresPacientes = consumoQuirofanoDAO.buscadorPacientes();
//            ObservableList<Folio> nombreTabla = FXCollections.observableArrayList();
//            TextFields.bindAutoCompletion(txfbuscador, nombresPacientes).setPrefWidth(500);
//
//        } catch (Exception e) {
//        } finally {
//            if (connection != null) {
//                connection.close(); // Cierra la conexión
//            }
//        }
    }

    private void llenarTabla() throws SQLException {
        Connection connection = null;

        try {
            connection = conexion.conectar2();
            CuentaPacienteDAO cuentaPacienteDAO = new CuentaPacienteDAO(connection);
            ObservableList<CuentaPaciente> cuentaPacientes = cuentaPacienteDAO.llenarTabla();
            clmFolio.setCellValueFactory(new PropertyValueFactory("folio"));
            clmPaciente.setCellValueFactory(new PropertyValueFactory("nombre_Paciente"));
            clmFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
            clmHabitacion.setCellValueFactory(new PropertyValueFactory("tipo_habitacion"));
            clmConsumoActual.setCellValueFactory(new PropertyValueFactory("montoHastaElMomento"));
            clmSaldoACubrir.setCellValueFactory(new PropertyValueFactory("saldoACubrir"));
            clmAbonos.setCellValueFactory(new PropertyValueFactory("totalDeAbono"));
            tabla.setItems(cuentaPacientes);
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @FXML
    private void verCuentaPaciente(ActionEvent event) throws SQLException, IOException {
        CuentaPaciente cuentaSeleccionada = tabla.getSelectionModel().getSelectedItem();
//        if (cuentaSeleccionada == null) {
//            alertError.setTitle("ERROR");
//            alertError.setHeaderText("ELEMENTO NO SELECCIONADO");
//            alertError.setContentText("PRIMERO TIENE QUE SELECCIONAR UN ELEMENTO DE LA TABLA PARA PODER VISUALIZARLO");
//            alertError.showAndWait();
//            return;
//        }

        // alertaOpciones.setTitle("VISUALIZAR");
//        alertaOpciones.setHeaderText(null);
//        alertaOpciones.setContentText("¿DESEA VISUALIZAR LA CUENTA?\nDEL PACIENTE: " + cuentaSeleccionada.getNombre_Paciente());
//
//        Optional<ButtonType> option = alertaOpciones.showAndWait();
        // if (option.get() == ButtonType.OK) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VisualizarFolio.fxml"));
        Parent root = loader.load();

     
        VisualizarFolioController controller = loader.getController();
        controller.recibirDatos(cuentaSeleccionada.getId_paciente(), cuentaSeleccionada.getId_folio(), cuentaSeleccionada.getNombre_Paciente(), cuentaSeleccionada.getFolio(), cuentaSeleccionada.getMontoHastaElMomento(), cuentaSeleccionada.getIdPaquete());
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        //stage.initStyle(StageStyle.UNDECORATED);
//            stage.setMaximized(true);
        stage.setTitle("CUENTA DEL PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);

//        this.tabla.getItems().clear();
//        llenarTabla();
//        txfbuscador.clear();
        stage.showAndWait();
    }
//    else {
//            this.tabla.getItems().clear();
//            llenarTabla();
//        }

    @FXML
    private void descartarConsumo(ActionEvent event) throws IOException {
        CuentaPaciente cuentaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas_hospital/EliminarConsumoPaciente.fxml"));
        Parent root = loader.load();
        EliminarConsumoPacienteController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
//        ConfiguracionPaquete configuracionPaquete = tabla.getSelectionModel().getSelectedItem();

        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(cuentaSeleccionada.getId_folio());

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setMaximized(true);
        destinoStage.setTitle("CONSUMOS DE PACIENTE");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();
    }

}
