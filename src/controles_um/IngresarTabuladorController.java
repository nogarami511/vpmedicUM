/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles_um;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ComboBox<?> cmbTipo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ingresarTabulacion(ActionEvent event) {
        
        String nombre = txtNombre.getText();
        String nota = txtAreaNota.getText();
        int id = Integer.parseInt(txtID.getText());;
        
        
    }

    
    @FXML
private void cerrarVentana(ActionEvent event) {
    // Obtener el botón que disparó el evento y cerrar su ventana
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
}

    
}
