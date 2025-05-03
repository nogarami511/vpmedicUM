/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class DashBoard_NominaController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private TitledPane tpContaduria;
    @FXML
    private AnchorPane anchorPanel;
           Conexion connection = new Conexion();
    Connection con = connection.conectar2();
    @FXML
    private Button btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imagenFondo();
        if (VPMedicaPlaza.userNameSystem.equals("SISTEMAS")) {
            tpContaduria.setVisible(true);
        }
    }
    
     @FXML
    private void accionCerrarSesion(ActionEvent event) throws SQLException {
        List<String> listanombres = new ArrayList<>();
        UsuarioDAO usuariodao = new UsuarioDAO(con);
        ConsumosDAO consumodao = new ConsumosDAO(con);
        listanombres = usuariodao.IndicasFaltantes(VPMedicaPlaza.userArea);
        

        if (listanombres.isEmpty()) {
           
            usuariodao.EstadoSesion(VPMedicaPlaza.userSystem, false);
            
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        } else {

            StringBuilder contentText = new StringBuilder("Lista de nombres:\n");
            for (String nombre : listanombres) {
                contentText.append("- ").append(nombre).append("\n");
            }

            // Crear el cuadro de diálogo de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(" Tienes Indicas pendientes \n¿Quieres continuar?");
            alert.setContentText(contentText.toString());

            // Obtener la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();

            // Verificar la respuesta del usuario
            if (result.isPresent() && result.get() == ButtonType.OK) {
                
                // Hacer algo si el usuario seleccionó "Sí"
                consumodao.actualizarIndicasporarea(VPMedicaPlaza.userArea);
                usuariodao.EstadoSesion(VPMedicaPlaza.userSystem, false);
                Stage stage = (Stage) btnSalir.getScene().getWindow();
                stage.close();

            } else {
                
                // Hacer algo si el usuario seleccionó "No" o cerró el cuadro de diálogo
            }
        }

    }


    @FXML
    private void irColaboradores(ActionEvent event) throws IOException {
        loadPage("Colaboradores");
    }

    @FXML
    private void irPuestos(ActionEvent event) throws IOException {
        loadPage("Puestos");
    }

    @FXML
    private void irNomina(ActionEvent event) throws IOException {
        loadPageHospital("NominaInterna");
    }

    private void imagenFondo() {
        try {
            Image image = new Image("/img/dash.png");
            BackgroundImage backgroundImage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true));
            Background background = new Background(backgroundImage);
            anchorPanel.setBackground(background);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/" + page + ".fxml"));
        bp.setCenter(root);
    }

    private void loadPageHospital(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas_hospital/" + page + ".fxml"));
        bp.setCenter(root);
    }

    @FXML
    private void irAutorizarPagarNomina(ActionEvent event) throws IOException {
        loadPageHospital("AutorizaryPagarNominaTabla");
    }

}
