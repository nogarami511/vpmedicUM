/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.Conexion;
import clase.TipoUsuario;
import clase.Usuario;
import clases_hospital_DAO.TipoUsuarioDAO;
import clases_hospital_DAO.UsuarioDAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class UsuarioNuevoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Conexion conexion = new Conexion();
    Connection con;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfUsuarioNombre;
    @FXML
    private PasswordField txfPassword;
    @FXML
    private ChoiceBox<TipoUsuario> cbxPerfil;

    ObservableList<TipoUsuario> tiposUsuarios;
    private String nombrePerfilUsuario = "";
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Usuario usuarioRecibido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            llenarComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        UsuarioDAO usuarioDAO = new UsuarioDAO(con);
        Usuario usuario = new Usuario();
        usuario.setNombre(txfUsuarioNombre.getText().toUpperCase());
        String contraEncriptada = DigestUtils.md5Hex(txfPassword.getText());
        usuario.setContra(contraEncriptada);
        usuario.setCargo(nombrePerfilUsuario);
        usuarioDAO.crear(usuario);
        alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
        alertaSuccess.setContentText("Usuario ingresado al sistema");
        alertaSuccess.showAndWait();
        con.close();
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void editar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        UsuarioDAO usuarioDAO = new UsuarioDAO(con);
        usuarioRecibido.setNombre(txfUsuarioNombre.getText());
        String contraEncriptada = DigestUtils.md5Hex(txfPassword.getText());
        usuarioRecibido.setContra(contraEncriptada);
        usuarioRecibido.setCargo(nombrePerfilUsuario);
        usuarioDAO.actualizar(usuarioRecibido);
        alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
        alertaSuccess.setContentText("Usuario actualizado en sistema");
        alertaSuccess.showAndWait();
        con.close();
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void llenarComboBox() throws SQLException {
        con = conexion.conectar2();
        TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO(con);
        tiposUsuarios = FXCollections.observableArrayList(tipoUsuarioDAO.obtenerTodos());
        cbxPerfil.setItems(tiposUsuarios);

        cbxPerfil.setOnAction(e -> {
            TipoUsuario tipoUsuario = cbxPerfil.getValue();
            if (cbxPerfil.getValue() != null) {
                nombrePerfilUsuario = tipoUsuario.getNombre();
           
            }
        });

        con.close();
    }

    public void setObjeto(Usuario usuario) {
        usuarioRecibido = usuario;
        btnAgregar.setVisible(false);
        btnEditar.setVisible(true);
        txfUsuarioNombre.setText(usuarioRecibido.getNombre());
    }
}
