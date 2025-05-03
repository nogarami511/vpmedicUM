/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class IngresarConsumoPacientePisoUrgenciasController implements Initializable {

    @FXML
    private Label lblNombrePaciente;
    @FXML
    private Label lblAlmacen;
    @FXML
    private Button btnAplicar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<?> tabla;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private TableColumn<?, ?> colAplicar;
    @FXML
    private TableColumn<?, ?> colEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
