/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Pagos;
import clases_hospital_DAO.PagosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class PagosEnTransitoController implements Initializable {

    ObservableList<Pagos> pagosEnTransito;
    Conexion conexion = new Conexion();
    Connection con;

    @FXML
    private TableView<Pagos> tabla;
    @FXML
    private TableColumn<?, ?> nombrePaciente;
    @FXML
    private TableColumn<?, ?> monto;
    @FXML
    private TableColumn<?, ?> fechaCobro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
            nombrePaciente.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
            monto.setCellValueFactory(new PropertyValueFactory("totalPagoFormateado"));
            fechaCobro.setCellValueFactory(new PropertyValueFactory("fecha_pago"));
        } catch (SQLException ex) {
            Logger.getLogger(PagosEnTransitoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        PagosDAO pagosDAO = new PagosDAO(con);
        pagosEnTransito = FXCollections.observableArrayList(pagosDAO.obtenerPagosEnTransito());
        tabla.setItems(pagosEnTransito);
        con.close();
    }

    @FXML
    private void cambiarFormaPago(ActionEvent event) throws IOException, SQLException {
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

}
