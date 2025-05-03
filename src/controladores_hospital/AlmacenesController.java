/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Almacen;
import clases_hospital_DAO.AlmacenDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vistasAuxiliares_hospital.AlmacenesVistaAuxiliarController;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AlmacenesController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<Almacen> almacenes = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    AlmacenDAO alamacendao;

    @FXML
    private Button btnEditar;
    @FXML
    private Button btnVisualizar;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableColumn<?, ?> colAlmacen;
    @FXML
    private TableColumn<?, ?> colPiso;
    @FXML
    private TableView<Almacen> tabla;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(AlmacenesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
    }

    @FXML
    private void accionEditar(ActionEvent event) {
    }

    @FXML
    private void accionVisualizar(ActionEvent event) throws IOException, SQLException {
        Almacen almacen = this.tabla.getSelectionModel().getSelectedItem();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AlmacenesVistaAuxiliar.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        AlmacenesVistaAuxiliarController avc = fxml.getController();
        //Mandamos a llamar el metodo Cita moficiar de la vista CITAQUIROFANONUEVO
        avc.recibirDatos(almacen);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    private void llenarTabla() throws SQLException {
        almacenes.clear();
        alamacendao = new AlmacenDAO(conexion.conectar2());
        almacenes.addAll(alamacendao.getAllConPiso());

        colAlmacen.setCellValueFactory(new PropertyValueFactory("almacen"));
        colPiso.setCellValueFactory(new PropertyValueFactory("piso"));
        
        tabla.setItems(almacenes);
    }

}
