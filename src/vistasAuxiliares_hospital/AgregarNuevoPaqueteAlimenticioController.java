/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class AgregarNuevoPaqueteAlimenticioController implements Initializable {

    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    @FXML
    private TextField nombrePaquete;
    @FXML
    private TextField descripcionPaquete;
    @FXML
    private TextField precioPaquete;
    @FXML
    private TextField claveSATPaquete;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnActualizar;
    int idPaqueteAlimento = 0;// para el update

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ingresar(ActionEvent event) {
        con = conexion.conectar2();
        CallableStatement stmt = null;
        String sqlIngresarInsumoPaquete = "{call ingresarPaqueteAlimenticio(?,?,?,?,?)}";
        try {
            stmt = con.prepareCall(sqlIngresarInsumoPaquete);
            stmt.setString(1, nombrePaquete.getText());
            stmt.setString(2, descripcionPaquete.getText());
            stmt.setDouble(3, Double.parseDouble(precioPaquete.getText()));
            stmt.setInt(4, vpmedicaplaza.VPMedicaPlaza.userSystem);
            stmt.setString(5, claveSATPaquete.getText());
            stmt.execute();
            alertaSuccess.setHeaderText("Nuevo Paquete Ingresado al Sistema");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarNuevoPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void recibirDatos(int id) {
        this.idPaqueteAlimento = id;
        con = conexion.conectar2();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from paquetesalimentos where id = '" + id + "'");
            //PaqueteAlimento paqueteAlimento;
            while (rs.next()) {
                this.nombrePaquete.setText(rs.getString(2));
                this.descripcionPaquete.setText(rs.getString(3));
                this.precioPaquete.setText(String.valueOf(rs.getDouble(4)));
                this.claveSATPaquete.setText(rs.getString(7));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        btnIngresar.setVisible(false);
        btnActualizar.setVisible(true);
    }

    @FXML
    private void actualizar(ActionEvent event) {
        try {
            CallableStatement stmt = null;
            String sql = "{call ActualizarPaqueteAlimenticio (?,?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, nombrePaquete.getText());
            stmt.setString(2, descripcionPaquete.getText());
            stmt.setDouble(3, Double.parseDouble(precioPaquete.getText()));
            stmt.setInt(4, VPMedicaPlaza.userSystem);
            stmt.setString(5, claveSATPaquete.getText());
            stmt.setInt(6, idPaqueteAlimento);
           

            stmt.execute();
            stmt.close();
            alertaSuccess.setHeaderText("Paquete Alimenticio Actualizado");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
