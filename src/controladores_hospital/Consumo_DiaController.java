/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.*;
import java.sql.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Consumo_DiaController implements Initializable {
    Conexion conexion = new Conexion();
    Connection con;
    
    ObservableList<Inventario> OBinventario = FXCollections.observableArrayList();

    @FXML
    private DatePicker dtpDia;
    @FXML
    private Button BUSCAR;
    @FXML
    private TableView<Inventario> tabla;
    @FXML
    private TableColumn<?, ?> col_Nombre;
    @FXML
    private TableColumn<?, ?> col_cantidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = conexion.conectar2();
    }    

    @FXML
    private void accionbuscar(ActionEvent event) {
        try(CallableStatement stm = con.prepareCall("{CALL CONSUMO_DEL_DIA(?)}")){
            stm.setDate(1, Date.valueOf(dtpDia.getValue()));
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                System.out.println("1");
                Inventario inv = new Inventario();
                inv.setNombre(rs.getString("tipo"));
                inv.setTotalExistencia(rs.getDouble("cantidad_total"));
               OBinventario.add(inv);
            }
             
                llenarTabla();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void llenarTabla(){
        tabla.getItems().clear();
        
        col_Nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        col_cantidad.setCellValueFactory(new PropertyValueFactory("totalExistencia"));
        tabla.setItems(OBinventario);
        
        
        
    }
    
}
