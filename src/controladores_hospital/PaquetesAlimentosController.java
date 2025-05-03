/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import clases_hospital.PaqueteAlimento;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vistasAuxiliares_hospital.AgregarNuevoPaqueteAlimenticioController;
import vistasAuxiliares_hospital.VerPaquetesAlimenticiosController;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class PaquetesAlimentosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<PaqueteAlimento> paquetes = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    @FXML
    private TableView<PaqueteAlimento> tabla;
    @FXML
    private TableColumn nombrePaquete;
    @FXML
    private TableColumn descripcionPaquete;
    @FXML
    private TableColumn precioPaquete;
    @FXML
    private TextField txfNombreAlimento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
        txfNombreAlimento.setOnKeyReleased(e -> filtrarLista(txfNombreAlimento.getText()));
    }

    @FXML
    private void irAgregar(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarNuevoPaqueteAlimenticio.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("PAQUETE NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void irEditar(ActionEvent event) throws IOException {
        PaqueteAlimento paquete = tabla.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarNuevoPaqueteAlimenticio.fxml"));
        Parent root = loader.load();
        AgregarNuevoPaqueteAlimenticioController controller = loader.getController();
        controller.recibirDatos(paquete.getId());

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        //stage.setMaximized(true);
        stage.setTitle("INSUMOS DE PAQUETE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        //txfbuscador.clear();
        stage.showAndWait();
        this.tabla.getItems().clear();
        llenarTabla();

    }

    @FXML
    private void irEliminar(ActionEvent event) throws SQLException {
        PaqueteAlimento paqueteAlimento = new PaqueteAlimento();
        paqueteAlimento = this.tabla.getSelectionModel().getSelectedItem();
        con = conexion.conectar2();
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("ELMINAR PAQUETE");
        alertaConfirmacion.setContentText("¿DESEA ELIMINAR EL PAQUETE?");
        Optional<ButtonType> alertaConfirmacion2 = alertaConfirmacion.showAndWait();
        if (alertaConfirmacion2.get() == ButtonType.OK) {
            try {
                CallableStatement stmt = null;
                String sql = "{call eliminarPaqueteAlimenticio (?)}";
                stmt = con.prepareCall(sql);
                stmt.setInt(1, paqueteAlimento.getId());
                stmt.execute();
                stmt.close();
                alertaSuccess.setHeaderText("Paquete Alimenticio fuera del Sistema");
                alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
                alertaSuccess.showAndWait();
                llenarTabla();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void irVisualizar(ActionEvent event) throws IOException {
        PaqueteAlimento paquete = tabla.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VerPaquetesAlimenticios.fxml"));
        Parent root = loader.load();
        VerPaquetesAlimenticiosController controller = loader.getController();
        controller.recibirDatos(paquete.getId(), paquete.getNombre());

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setMaximized(true);
        stage.setTitle("INSUMOS DE PAQUETE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        this.tabla.getItems().clear();
        llenarTabla();
        //txfbuscador.clear();
        stage.showAndWait();

    }

    public void llenarTabla() {
        con = conexion.conectar2();
        try {
            this.tabla.getItems().clear();
            paquetes.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from paquetesalimentos");
            PaqueteAlimento paquete;
            while (rs.next()) {
                paquete = new PaqueteAlimento();
                paquete.setId(rs.getInt(1));
                paquete.setNombre(rs.getString(2));
                paquete.setDescripcion(rs.getString(3));
                paquete.setPrecio(rs.getDouble(4));
                paquetes.add(paquete);
            }
            nombrePaquete.setCellValueFactory(new PropertyValueFactory("nombre"));
            descripcionPaquete.setCellValueFactory(new PropertyValueFactory("descripcion"));
            precioPaquete.setCellValueFactory(new PropertyValueFactory("precioFormateado"));
            tabla.setItems(paquetes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(paquetes);
        } else {
            ObservableList<PaqueteAlimento> listaFiltrada = FXCollections.observableArrayList();
            for (PaqueteAlimento paqueteAlimento : paquetes) {
                if (paqueteAlimento.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(paqueteAlimento);
                }
            }
            tabla.setItems(listaFiltrada);
        }
    }
}
