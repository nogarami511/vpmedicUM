/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clase.Rubro;
import clases_hospital_DAO.RubrosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vistasAuxiliares.RubroNuevoController;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class RubrosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Rubro rubro;
    ObservableList<Rubro> rubros = FXCollections.observableArrayList();

    RubrosDAO rubrosdao;

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private TableView<Rubro> tabla;
    @FXML
    private TableColumn nombreRubro;
    @FXML
    private TableColumn montoRubro;
    @FXML
    private TableColumn observacionesRubro;
    @FXML
    private TableColumn ministracionRubro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(RubrosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/RubroNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("RUBRO NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        rubro = this.tabla.getSelectionModel().getSelectedItem();
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("¿Estas seguro de el puesto : " + rubro.getNombre() + " ?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {

            rubrosdao = new RubrosDAO(conexion.conectar2());
            rubrosdao.eliminar(rubro.getId());

            llenarTabla();
        }
    }

    @FXML
    private void editar(ActionEvent event) throws IOException, SQLException {
        Rubro rubroSeleccionado = this.tabla.getSelectionModel().getSelectedItem();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/RubroNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        
        RubroNuevoController rnc = fxml.getController();
        rnc.llenarDatos(rubroSeleccionado);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("RUBRO NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    private void salir(ActionEvent event) throws IOException {
        
    }

    private void llenarTabla() throws SQLException {
        this.tabla.getItems().clear();
        rubros.clear();

        rubrosdao = new RubrosDAO(conexion.conectar2());
        rubros.addAll(rubrosdao.obtenerTodosConNombreMinistracion());

        nombreRubro.setCellValueFactory(new PropertyValueFactory("nombre"));
        montoRubro.setCellValueFactory(new PropertyValueFactory("montoFormateado"));
        observacionesRubro.setCellValueFactory(new PropertyValueFactory("observaciones"));
        ministracionRubro.setCellValueFactory(new PropertyValueFactory("nombreMinis"));
        tabla.setItems(rubros);

    }
}
