/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clase.Usuario;
import clases_hospital_DAO.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vistasAuxiliares.UsuarioNuevoController;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class UsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> tabla;
    @FXML
    private TableColumn nombreUsuario;
    @FXML
    private TableColumn cuentaUsuario;

    ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaConfirmacion = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        this.tabla.getItems().clear();
        Usuario usuario;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from usuarios");
        try {
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setContra(rs.getString(3));
                usuario.setCargo(rs.getString(4));
                usuarios.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        nombreUsuario.setCellValueFactory(new PropertyValueFactory("nombre"));
        cuentaUsuario.setCellValueFactory(new PropertyValueFactory("cargo"));
        nombreUsuario.setStyle("-fx-alignment: CENTER;");
        cuentaUsuario.setStyle("-fx-alignment: CENTER;");
        tabla.setItems(usuarios);
        con.close();
    }

    @FXML
    private void editar(ActionEvent event) throws IOException, SQLException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/UsuarioNuevo.fxml"));
        Parent root = loader.load();
        UsuarioNuevoController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        Usuario usuarioSeleccionado = tabla.getSelectionModel().getSelectedItem();
        
        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(usuarioSeleccionado);

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("EDITAR USUARIO");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

        llenarTabla();
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        Usuario usuario = this.tabla.getSelectionModel().getSelectedItem();
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("¿Estas seguro de eliminar a : " + usuario.getNombre() + "?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(con);
            usuarioDAO.eliminar(usuario.getId());
            alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
            alertaSuccess.setContentText("Usuario fuera del sistema");
            alertaSuccess.showAndWait();
            llenarTabla();
        }
        con.close();
    }

    @FXML
    private void ingresar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/UsuarioNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("USUARIO NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

}
