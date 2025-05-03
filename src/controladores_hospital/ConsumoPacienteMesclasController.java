/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Folio;
import clases_hospital_DAO.ConsumoQuirofanoDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vistasAuxiliares_hospital.mezclasController;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class ConsumoPacienteMesclasController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertError = new Alert(Alert.AlertType.ERROR);
    Alert alertinfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaOpciones = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private TextField txfbuscador;
    @FXML
    private TableView<Folio> tabla;
    @FXML
    private TableColumn<Folio, String> clmPaciente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ConsumoPacienteMesclasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void verCuentaPaciente(MouseEvent event) throws SQLException, IOException {
        Folio folioSeleccionado = tabla.getSelectionModel().getSelectedItem();
        
        if (folioSeleccionado == null) {
            alertError.setTitle("ERROR");
            alertError.setHeaderText("ELEMENTO NO SELECCIONADO");
            alertError.setContentText("PRIMERO TIENE QUE SELECCIONAR UN ELEMENTO DE LA TABLA PARA PODER VISUALIZARLO");
            alertError.showAndWait();
            return;
        }

        alertaOpciones.setTitle("VISUALIZAR");
        alertaOpciones.setHeaderText(null);
        alertaOpciones.setContentText("¿DESEA AGREGAR INSUMOS LA CUENTA?\nPACIENTE: " + folioSeleccionado.getNombre());

        Optional<ButtonType> option = alertaOpciones.showAndWait();

        if (option.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/mezclas.fxml"));
            Parent root = loader.load();
            
            mezclasController controller = loader.getController();
          
            Folio folio = new Folio(folioSeleccionado.getIdPaciente(), folioSeleccionado.getId(), folioSeleccionado.getFolio(), folioSeleccionado.getNombre());
            controller.recibirDatos(folio);
            

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(true);
            stage.setTitle("CONSUMO QUIROFANO");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            this.tabla.getItems().clear();
            llenarTabla();
            txfbuscador.clear();
            stage.showAndWait();
        } else {
            this.tabla.getItems().clear();
            llenarTabla();
        }
    }

    private void llenarTabla() throws SQLException {
        Connection connection = null;

        connection = conexion.conectar2();
        ConsumoQuirofanoDAO consumoQuirofanoDAO = new ConsumoQuirofanoDAO(connection);
        ObservableList<Folio> folios = consumoQuirofanoDAO.llenarTabla();

        clmPaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
        tabla.setItems(folios);
    }

}
