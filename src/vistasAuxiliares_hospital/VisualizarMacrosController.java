/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.*;
import clases_hospital_DAO.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class VisualizarMacrosController implements Initializable {
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    
    ObservableList<Insumo> insumosenMacro = FXCollections.observableArrayList();
    ObservableList<TipoInsumoMedico> tipoInsumoMedico = FXCollections.observableArrayList();
    
      Conexion conexion = new Conexion();
      Connection con;
    
      InsumosDAO insumoDAO;
      TipoInsumoMedicoDAO tipoInsumoDAO;
      
      
    @FXML
    private Button btnCancelar;
    @FXML
    private ComboBox<TipoInsumoMedico> cmbMacros;
    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private TableColumn<?, ?> colMacro;
    @FXML
    private TableColumn<?, ?> colInsumo;
      
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenardatos();
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarMacrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void macroseleccionado(ActionEvent event) throws SQLException {
        int macroselect = 0;
        if(cmbMacros.getValue()!= null){
            macroselect = cmbMacros.getSelectionModel().getSelectedItem().getIdTipoInsumoMedico();
            llenartabla(macroselect);
            
        }
    }
        // LLENAR COMBO BOX CON TIPOS DE MACROS
        private void llenardatos() throws SQLException{
        con = conexion.conectar2();
        tipoInsumoDAO = new TipoInsumoMedicoDAO(con);
        
        tipoInsumoMedico.addAll(tipoInsumoDAO.getAll());
        cmbMacros.setItems(tipoInsumoMedico);
        con.close();
    }
    
    // LLENAR TABLA SEGUN MACRO SELECCIONADO
    private void llenartabla(int id_macro) throws SQLException{
        tabla.getItems().clear();
        con = conexion.conectar2();
        insumoDAO = new InsumosDAO(con);
        insumosenMacro.addAll(insumoDAO.InsumosporIdMacro(id_macro));
        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
        colMacro.setCellValueFactory(new PropertyValueFactory("nombreMacro"));
        tabla.setItems(insumosenMacro);
        con.close();
        
    }
    
}
