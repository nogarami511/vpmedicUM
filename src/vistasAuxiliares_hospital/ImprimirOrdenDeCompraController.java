/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.ReabastoPadre;
import clases_hospital_DAO.ReabastoPadreDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ImprimirOrdenDeCompraController implements Initializable {
    
    Conexion conexion = new Conexion();
    ReabastoPadreDAO reabastopadredao;
    ObservableList<ReabastoPadre> generarreabastosinsumos = FXCollections.observableArrayList();
    private ReabastoPadre rpg = new ReabastoPadre();
    
    @FXML
    private Button btnImprimir;
    @FXML
    private ComboBox<ReabastoPadre> cmbOrdenes;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarCMB();
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirOrdenDeCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void accionImprimir(ActionEvent event) {
    
    }
    
    private void llenarCMB() throws SQLException{
        reabastopadredao = new ReabastoPadreDAO(conexion.conectar2());
        generarreabastosinsumos.addAll(reabastopadredao.getPedidos());
        cmbOrdenes.setItems(generarreabastosinsumos);
        cmbOrdenes.setOnAction(e -> {
            rpg = cmbOrdenes.getValue();
        });
    }
    
}
