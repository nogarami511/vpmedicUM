/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Usuario;
import clases_hospital_DAO.UsuarioDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ActividadUsuariosController implements Initializable {

    @FXML
    private BorderPane bpPagos;
    @FXML
    private TableView<Usuario> tabla;
    @FXML
    private TableColumn<?, ?> colUsuario;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colHora;
    @FXML
    private TableColumn<?, ?> colMontoAlMomento;
    @FXML
    private TableColumn<?, ?> colHorasAcumuladas;
    
    ObservableList<Usuario> listaUusarios = FXCollections.observableArrayList();

    Conexion connection = new Conexion();
    Connection con = connection.conectar2();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ActividadUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public void llenarTabla() throws SQLException{
        UsuarioDAO usuariodao = new UsuarioDAO(con);
        listaUusarios.clear();
        listaUusarios.addAll(usuariodao.ActividadUsuarios());
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("Hora"));
        colHorasAcumuladas.setCellValueFactory(new PropertyValueFactory<>("Horas_acumuladas"));
        tabla.setItems(listaUusarios);
    }
    
}
