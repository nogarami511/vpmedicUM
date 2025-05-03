/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.IngresoEfectivo;
import clases_hospital_DAO.FondoEfectivoFijoDAO;
import clases_hospital_DAO.IngresosEfectivoDAO;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class IngresoEfectivoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaSucces = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaWarring = new Alert(Alert.AlertType.WARNING);

    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnIngresar;
    @FXML
    private TextField txfConcepto;
    @FXML
    private TextField txfCantidadAingresar;

    IngresosEfectivoDAO ingresosEfectivoDAO;
    FondoEfectivoFijoDAO fondoEfectivoFijoDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        limitarCampoCantidadAnumeros();
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionIngresar(ActionEvent event) {
        if (txfCantidadAingresar.getText().equals("") || txfConcepto.getText().equals("")) {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("LLENAR TODOS LOS CAMPOS PARA PODER CONTINUAR");
            alertaError.showAndWait();
        } else {
            ingresoEfectivo();
            alertaSucces.setTitle("CONFIRMACION");
            alertaSucces.setHeaderText("PROCESO EJECUTADO CORRECTAMENTE");
            alertaSucces.showAndWait();
            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            stage.close();
        }
    }

    private void ingresoEfectivo() {
        try {
            double cantidad = Double.parseDouble(txfCantidadAingresar.getText());
            IngresoEfectivo ingreso = new IngresoEfectivo();
            ingreso.setConcepto(txfConcepto.getText());
            ingreso.setFolio("E-" + encrypt(txfConcepto.getText()));
            ingreso.setFormaPago(1);
            ingreso.setImporte(cantidad);
            ingreso.setUsuarioCreacion(1);
            con = conexion.conectar2();
            ingresosEfectivoDAO = new IngresosEfectivoDAO(con);
            fondoEfectivoFijoDAO = new FondoEfectivoFijoDAO(con);

            ingresosEfectivoDAO.insertIngresoEfectivo(ingreso);

            double cantidadFondo = fondoEfectivoFijoDAO.leerFondoEfectivoFijoPorId(1).getMonto();
            double sumaFondoFijo = cantidadFondo + cantidad;

            fondoEfectivoFijoDAO.actualizarFondoEfectivoFijo(1, sumaFondoFijo);

        } catch (SQLException ex) {
            Logger.getLogger(IngresoEfectivoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void limitarCampoCantidadAnumeros() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (isValidNumeric(newText)) {
                return change;
            } else {
                return null; // Rechazar el cambio si no es válido
            }
        };

        // Crea un TextFormatter con el filtro
        TextFormatter<String> formatter = new TextFormatter<>(filter);

        // Aplica el TextFormatter al TextField
        txfCantidadAingresar.setTextFormatter(formatter);

        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().isEmpty()) {
                change.setText("0");
                change.setCaretPosition(1); // Coloca el cursor al final
            }
            return change;
        });

        txfCantidadAingresar.setTextFormatter(textFormatter);
    }

    private boolean isValidNumeric(String text) {
        // Utiliza una expresión regular para validar números con un punto decimal
        return Pattern.matches("^\\d*\\.?\\d*$", text);
    }

    private static String encrypt(String nombre) {
        try {
            // Obtener una instancia de MessageDigest con el algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Calcular el hash MD5 de la cadena de entrada
            byte[] messageDigest = md.digest(nombre.getBytes());

            // Convertir los primeros 5 bytes del hash en una representación hexadecimal
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);

            // Rellenar con ceros si es necesario para tener una longitud de 5 caracteres
            while (hashText.length() < 5) {
                hashText = "0" + hashText;
            }

            return hashText.substring(0, 5);
        } catch (NoSuchAlgorithmException e) {
            // Manejo de excepciones si ocurre un error al obtener la instancia del algoritmo MD5
            e.printStackTrace();
            return null;
        }
    }

}
