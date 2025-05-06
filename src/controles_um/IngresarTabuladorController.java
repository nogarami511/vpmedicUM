/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles_um;

import clase.UM.TipoTabulacion;
import clase.UM_DAO.TabuladorCatalogoDAO;
import clase.UM_DAO.TipoTabulacionDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class IngresarTabuladorController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea txtAreaNota;
    @FXML
    private TextField txtID;
    @FXML
    private ComboBox<TipoTabulacion> cmbTipoTabulacion;
    
    private final TipoTabulacionDAO tipoTabDAO = new TipoTabulacionDAO();
    private final TabuladorCatalogoDAO tabuladorDAO = new TabuladorCatalogoDAO();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTipo();
    }

    public void cargarTipo (){
        try {
            TipoTabulacion tipoTab = new TipoTabulacion();
            tipoTab.setIdTipoTabulacion(0);
            tipoTab.setTipo(null);
            tipoTab.setEstatus(true);

            List<TipoTabulacion> listaTipos = tipoTabDAO.ejecutarProcedimiento("listar", tipoTab);
            cmbTipoTabulacion.setItems(FXCollections.observableArrayList(listaTipos));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarDatos(){
        String nombre = txtNombre.getText();
        String nota = txtAreaNota.getText();
        TipoTabulacion seleccionado = cmbTipoTabulacion.getSelectionModel().getSelectedItem();
        
        //Validación
        if (nombre.isEmpty() || nota.isEmpty() || seleccionado == null){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("¡CAMPOS INCOMPLETOS!");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, completa todos los campos antes de ingresar.");
            alerta.initModality(Modality.APPLICATION_MODAL);
            alerta.showAndWait();
            return;
        }
        
        int idTipoTabulacion = seleccionado.getIdTipoTabulacion();
        
        try {
            tabuladorDAO.agregarTabulacion(idTipoTabulacion, nombre, nota, idTipoTabulacion);
            Alert alertaExito = new Alert(Alert.AlertType.INFORMATION);
            alertaExito.setTitle("Éxito");
            alertaExito.setHeaderText(null);
            alertaExito.setContentText("Tabulación guardada exitosamente.");
            alertaExito.showAndWait();
            
            txtNombre.clear();
            txtAreaNota.clear();
            cmbTipoTabulacion.getSelectionModel().clearSelection();
            
        } catch (Exception e) {
            e.printStackTrace();
            
            Alert alertaError = new Alert(Alert.AlertType.ERROR);
            alertaError.setTitle("Error");
            alertaError.setHeaderText("No se pudo guarda");
            alertaError.setContentText("Ocurrio un error al guardar la tabulación.");
            alertaError.showAndWait();
        }
        
        
    }

    @FXML
    private void ingresarTabulacion(ActionEvent event) {
        guardarDatos();
         Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    
    @FXML
    private void cerrarVentana(ActionEvent event) {
        // Obtener el botón que disparó el evento y cerrar su ventana
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }    
}
