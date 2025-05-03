/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.Conexion;
import clase.Ministracion;
import clase.Rubro;
import clases_hospital_DAO.MinistracionDAO;
import clases_hospital_DAO.RubrosDAO;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class RubroNuevoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Ministracion> ministraciones = FXCollections.observableArrayList();
    int idrubro;

    RubrosDAO rubrosdao;
    MinistracionDAO ministraciondao;

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField nombreIngresado;
    @FXML
    private TextField montoIngresado;
    @FXML
    private TextArea observacionesIngresado;
    @FXML
    private ChoiceBox<Ministracion> ministracionIngresado;
    @FXML
    private Button btnEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            rubrosExistentes();
        } catch (SQLException ex) {
            Logger.getLogger(RubroNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) {
        rubrosdao = new RubrosDAO(conexion.conectar2());
        String nombre = nombreIngresado.getText(),
               observaciones = observacionesIngresado.getText();
        double monto = Double.parseDouble(montoIngresado.getText());
        int ministracion = ministracionIngresado.getValue().getId();
        Rubro rubro = new Rubro(nombre, monto, observaciones, ministracion);

        try {
            rubrosdao.insertar(rubro);

            alertaSuccess.setHeaderText("Nuevo Rubro Agregado");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            // get a handle to the stage
            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (SQLException ex) {
            alertaSuccess.setHeaderText("Error");
            alertaSuccess.setContentText("Error... \n" + ex);
            alertaSuccess.showAndWait();
        }

    }

    @FXML
    private void salir(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void rubrosExistentes() throws SQLException {
        ministraciondao = new MinistracionDAO(conexion.conectar2());
        ministraciones.addAll(ministraciondao.obtenerTodos());

        ministracionIngresado.setItems(ministraciones);
    }

    @FXML
    private void accionEditar(ActionEvent event) {
        rubrosdao = new RubrosDAO(conexion.conectar2());
        String nombre = nombreIngresado.getText(),
               observaciones = observacionesIngresado.getText();
        double monto = Double.parseDouble(montoIngresado.getText());
        int ministracion = ministracionIngresado.getValue().getId();
        Rubro rubro = new Rubro();
        rubro.setNombre(nombre);
        rubro.setMonto(monto);
        rubro.setObservaciones(observaciones);
        rubro.setMinistracion(ministracion);
        rubro.setId(idrubro);
        try {
            rubrosdao.actualizar(rubro);

            alertaSuccess.setHeaderText("Nuevo Rubro Agregado");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            // get a handle to the stage
            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            // do what you have to do
            stage.close();
        } catch (SQLException ex) {
            alertaSuccess.setHeaderText("Error");
            alertaSuccess.setContentText("Error... \n" + ex);
            alertaSuccess.showAndWait();
        }
    }

    public void llenarDatos(Rubro rubro) {
        idrubro = rubro.getId();
        nombreIngresado.setText(rubro.getNombre());
        montoIngresado.setText(""+rubro.getMonto());
        ministracionIngresado.setValue(ministraciones.get(rubro.getMinistracion()));
        
        btnEditar.setVisible(true);
        btnIngresar.setVisible(false);
    }
}
