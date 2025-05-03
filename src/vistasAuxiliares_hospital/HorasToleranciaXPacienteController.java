/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class HorasToleranciaXPacienteController implements Initializable {

    @FXML
    private TextField horasTolerancia;
    Alert alertinfo = new Alert(Alert.AlertType.INFORMATION);
    Conexion conexion = new Conexion();
    int idPaciente, idFolio;
    String nombre;
    @FXML
    private Button btnAceptar;
    @FXML
    private Text txtNombrePaciente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void recibirDatos(int idFolio, String nombrePaciente, int idPaciente) {
        this.idFolio = idFolio;
        this.nombre = nombrePaciente;
        txtNombrePaciente.setText(nombrePaciente);
        this.idPaciente = idPaciente;
        horasTolerancia.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, c
                -> c.getControlNewText().matches("\\d*") ? c : null));
    }

    @FXML
    private void aceptar(ActionEvent event) throws SQLException {
        try (Connection con = conexion.conectar2()) {
            String query = "UPDATE folios SET horasTolerancia = ? WHERE id = ?";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                int horas = Integer.parseInt(horasTolerancia.getText());
                statement.setInt(1, horas);
                statement.setInt(2, idFolio);
                statement.executeUpdate();
                // Opcional: Realizar alguna acción después de la actualización
                con.close();
                alertinfo.setTitle("EXITO");
                alertinfo.setHeaderText("PROCEDIMIENTO EJECUTADO CON EXITO");
                alertinfo.setContentText("HORAS DE TOLERANCIA INGRESADAS AL PACIENTE");
                alertinfo.showAndWait();
                Stage stage = (Stage) btnAceptar.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException e) {
            // Manejar la excepción si el valor ingresado no es un número válido
        } catch (SQLException e) {
            // Manejar la excepción si ocurre algún error con la base de datos
        }

    }
}
