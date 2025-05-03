/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.CuentaxPagarPago;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class CuentasxPagarPagosController implements Initializable {

    @FXML
    private Button btnEliminarAbono;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<CuentaxPagarPago> tabla;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void eliminarAbono(ActionEvent event) {
        
    }

    @FXML
    private void salir(ActionEvent event) {
        
    }
    
}
