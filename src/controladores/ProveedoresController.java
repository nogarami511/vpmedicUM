/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clase.Proveedor;
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
import vistasAuxiliares.ProveedorNuevoController;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class ProveedoresController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Proveedor proveedor;
    ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnEliminar;
    //private Button btnSalir;
    @FXML
    private TableView<Proveedor> tabla;
    @FXML
    private TableColumn nombreComercialProveedor;

    @FXML
    private TableColumn direccionProveedor;
    @FXML
    private TableColumn telefonoProveedor;
    @FXML
    private TableColumn razonSocialProveedor;
    @FXML
    private Button btnEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
            nombreComercialProveedor.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
            razonSocialProveedor.setCellValueFactory(new PropertyValueFactory("razonSocial"));
            direccionProveedor.setCellValueFactory(new PropertyValueFactory("direccion"));
            telefonoProveedor.setCellValueFactory(new PropertyValueFactory("telefono"));
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/ProveedorNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("PROVEEDOR NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        proveedor = this.tabla.getSelectionModel().getSelectedItem();
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("¿Estas seguro de eliminar a : " + proveedor.getRazonSocial() + " ?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
            CallableStatement stmt = null; //objeto para llamar al procedimiento
            String sql = "{call eliminarProveedor (?)}";
            stmt = con.prepareCall(sql);
        
            stmt.setInt(1, proveedor.getId());
            stmt.execute();
            alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
            alertaSuccess.setContentText("Proveedor fuera del sistema");
            alertaSuccess.showAndWait();
            llenarTabla();
        }
    }

    private void llenarTabla() throws SQLException {
        this.tabla.getItems().clear();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from proveedores");

        try {
            while (rs.next()) {
                proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setNombreComercial(rs.getString(2));
                proveedor.setRazonSocial(rs.getString(3));
                proveedor.setDireccion(rs.getString(4));
                proveedor.setTelefono(rs.getString(5));
                proveedor.setRfc(rs.getString(6));
                proveedores.add(proveedor);
            }

            tabla.setItems(proveedores);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void editar(ActionEvent event) throws IOException, SQLException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/ProveedorNuevo.fxml"));
        Parent root = loader.load();
        ProveedorNuevoController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        Proveedor proveedor = tabla.getSelectionModel().getSelectedItem();

        //imprimir toString Objeto
        

        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(proveedor);

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("EDITAR DATOS PROVEEDOR");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

        llenarTabla();
    }
}
