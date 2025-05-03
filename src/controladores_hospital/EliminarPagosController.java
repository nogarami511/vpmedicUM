/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Pagos;
import clases_hospital_DAO.PagosDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import vistasAuxiliares_hospital.CambiarFormaController;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class EliminarPagosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Pagos> pagos;
    Conexion conexion = new Conexion();
    Connection con;
    int idFolio;
    @FXML
    private TableView<Pagos> tabla;
    @FXML
    private Button btnCancelarPago;
    @FXML
    private TableColumn<?, ?> pagoTotal;
    @FXML
    private TableColumn<?, ?> fechaCobro;
    @FXML
    private JFXButton btnNombrePaciente;
    @FXML
    private TableColumn<?, ?> formaPago;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cancelarPago(ActionEvent event) throws IOException, SQLException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CambiarFormaPago.fxml"));
        Parent root = loader.load();
        CambiarFormaController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        Pagos pago = tabla.getSelectionModel().getSelectedItem();

        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(pago.getId_pago());

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setResizable(false);
        destinoStage.setTitle("HISTORIAL DE PAGOS");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();
        llenarTabla();
    }

    public void setObjeto(int idFolio, String nombreP) throws SQLException {
        this.idFolio = idFolio;
        btnNombrePaciente.setText(nombreP);
        llenarTabla();
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        PagosDAO pagosDAO = new PagosDAO(con);
        pagos = pagosDAO.obtenerPagosXIDPaciente(idFolio);
        formaPago.setCellValueFactory(new PropertyValueFactory("formaPago"));
        pagoTotal.setCellValueFactory(new PropertyValueFactory("total_pago"));
        fechaCobro.setCellValueFactory(new PropertyValueFactory("fecha_pago"));
        tabla.setItems(pagos);
        con.close();
    }

}
