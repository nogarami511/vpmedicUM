/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.KardexPaquetesP;
import clases_hospital_DAO.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author olver
 */


public class VerModificacionesPaquetesPController implements Initializable {
    
    KardexPaquetesPDAO kdxPPDAO;
    KardexPaquetesP kdxPP ;
    
    Connection con;
    Conexion conexion = new Conexion();
    @FXML
    private Label lblNombrePaquete;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnSalir1;
    @FXML
    private Label lblUsuarioModificacion;
    @FXML
    private Label lblFechaModificacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = conexion.conectar2();
    }    
    public void SetObjeto(long id_paquete){
        kdxPPDAO = new KardexPaquetesPDAO(con);
        kdxPP = new KardexPaquetesP();
        
        kdxPP = kdxPPDAO.ultima_modificacion(id_paquete);
        lblNombrePaquete.setText(kdxPP.getNombre());
        lblUsuarioModificacion.setText(kdxPP.getUsuario_modificacion());
        lblFechaModificacion.setText(kdxPP.getFecha_modificacion());
        
        
        
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
}
