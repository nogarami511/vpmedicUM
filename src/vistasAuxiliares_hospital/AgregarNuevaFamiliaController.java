/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.FamiliaInsumos;
import clases_hospital_DAO.FamiliasInsumosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AgregarNuevaFamiliaController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    FamiliasInsumosDAO familiasInsumosDAO;
    FamiliaInsumos familiaInsumo;

    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txfNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionAceptar(ActionEvent event) {
        if (txfNombre.getText().equals("")) {
            alertaError.setTitle("LLENAR TODOS LOS CAMPOS");
            alertaError.setHeaderText("LLENE TODOS LOS CAMPOS PARA CONTINUAR");
            alertaError.showAndWait();
        } else {
            try {
                con = conexion.conectar2();
                familiasInsumosDAO = new FamiliasInsumosDAO(con);
                familiaInsumo = new FamiliaInsumos();

               
                familiaInsumo.setNombreFamiliaInsumo(txfNombre.getText().toUpperCase());
                familiaInsumo.setIdUsuarioCreacion(VPMedicaPlaza.userSystem);
                familiaInsumo.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

             

                familiasInsumosDAO.insertarFamiliaInsumos(familiaInsumo);
                
                alertaSuccess.setTitle("PROCESO REALIZADO EXISTOSAMENTE");
                alertaSuccess.setHeaderText("FAMILIA INGRESADA CORRECTAMENTE");
                alertaSuccess.showAndWait();

                Stage stage = (Stage) btnAceptar.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgregarNuevaFamiliaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void accionEditar(ActionEvent event) {
        if (txfNombre.getText().equals("")) {
            alertaError.setTitle("LLENAR TODOS LOS CAMPOS");
            alertaError.setHeaderText("LLENE TODOS LOS CAMPOS PARA CONTINUAR");
            alertaError.showAndWait();
        } else {
            try {
                con = conexion.conectar2();
                familiasInsumosDAO = new FamiliasInsumosDAO(con);

             
                familiaInsumo.setNombreFamiliaInsumo(txfNombre.getText().toUpperCase());
                familiaInsumo.setIdUsuarioCreacion(VPMedicaPlaza.userSystem);
                familiaInsumo.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

                familiasInsumosDAO.actualizarFamiliaInsumos(familiaInsumo);
                
                alertaSuccess.setTitle("PROCESO REALIZADO EXISTOSAMENTE");
                alertaSuccess.setHeaderText("FAMILIA EDITADA CORRECTAMENTE");
                alertaSuccess.showAndWait();

                Stage stage = (Stage) btnEditar.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgregarNuevaFamiliaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void setObjeto(FamiliaInsumos familiaInsumos){
        this.familiaInsumo = familiaInsumos;
        
        btnEditar.setVisible(true);
        btnAceptar.setVisible(false);
        
        llenarDatos();
    }
    
    private void llenarDatos(){
        txfNombre.setText(familiaInsumo.getNombreFamiliaInsumo());
    }

}
