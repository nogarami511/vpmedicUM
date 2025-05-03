/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.ConfiguracionPaquete;
import clases_hospital.Insumo;
import clases_hospital_DAO.ConfiguracionPaqueteDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vistasAuxiliares_hospital.CambiarInsumoConfiguracionPaqueteController;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class ConfiguracionPaqueteMedicoController implements Initializable {

    @FXML
    private TableView<ConfiguracionPaquete> tabla;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<ConfiguracionPaquete> configuracion = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con;
    double sumaPaqueteBase, sumaConfiguracionPaquete, diferencia;
    int idFolio, idPaquete;
    boolean configurado = false;
    @FXML
    private TableColumn insumoBaseConfiguracionPaquete;
    @FXML
    private TableColumn insumoVarianteConfiguracionPaquete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        ConfiguracionPaqueteDAO configuracionPaqueteDAO = new ConfiguracionPaqueteDAO(con);

        try {
            this.tabla.getItems().clear();
            configuracion = configuracionPaqueteDAO.traerArmadoPorFolio(idFolio);//devuelve un observableList
            if (configuracion.isEmpty()) {
              
                configurado = false;
            } else {
               
                configurado = true;
            }
            tabla.setItems(configuracion);
            insumoBaseConfiguracionPaquete.setCellValueFactory(new PropertyValueFactory("nombre_insumo"));
            insumoVarianteConfiguracionPaquete.setCellValueFactory(new PropertyValueFactory("precio_insumo"));//precio insumo paquete
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
    }

    @FXML
    private void cambiarInsumo(ActionEvent event) throws IOException, SQLException {

        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CambiarInsumoConfiguracionPaquete.fxml"));
        Parent root = loader.load();
        CambiarInsumoConfiguracionPaqueteController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
//        ConfiguracionPaquete configuracionPaquete = tabla.getSelectionModel().getSelectedItem();

        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(idFolio, idPaquete, diferencia, configurado);

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("CAMBIO DE INSUMO BASE POR VARIANTE");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

        llenarTabla();
    }

    public void setObjeto(int idFolio, int idPaquete) {
        try {
            this.idFolio = idFolio;
            this.idPaquete = idPaquete;
            llenarTabla();
            //traerSumatorias();
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionPaqueteMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
