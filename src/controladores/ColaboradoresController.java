/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Cliente;
import clase.Colaborador;
import clase.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ColaboradoresController implements Initializable {

    @FXML
    private TextField nombreIngresado;
    @FXML
    private TextField nssIngresado;
    @FXML
    private TextField rfcIngresado;
    @FXML
    private DatePicker fNacimiento;
    @FXML
    private DatePicker fIngreso;
    @FXML
    private ChoiceBox<String> puestoIngresado;
    @FXML
    private Button btnIngresar;

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private TableView<Colaborador> tabla;
    @FXML
    private TableColumn idColaborador;
    @FXML
    private TableColumn nombreColaborador;
    @FXML
    private TableColumn nssColaborador;
    @FXML
    private TableColumn fNacimientoColaborador;
    @FXML
    private TableColumn fIngresoColaborador;
    @FXML
    private TableColumn puestoColaborador;
    @FXML
    private TableColumn rfcColaborador;

    ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Traemos todos los puestos existentes
        puestosExistentes();
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ColaboradoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();// agrgar a la base de datos hospital
        String nombre = nombreIngresado.getText(), nss = nssIngresado.getText(), rfc = rfcIngresado.getText(), puesto = puestoIngresado.getValue();
        Date fechaIngreso = Date.valueOf(fIngreso.getValue()), fechaNacimiento = Date.valueOf(fNacimiento.getValue());//FECHA!!!!!!!!!!!!!!!!!!!!!!!!
        try {

            if (verificarVacantes(puesto)) {
                CallableStatement stmt = null; //objeto para llamar al procedimiento
                String sql = "{call ingresarColaboradores (?,?,?,?,?,?)}";
                stmt = con.prepareCall(sql);
                stmt.setString(1, nombre);
                stmt.setDate(2, fechaNacimiento);
                stmt.setString(3, nss);
                stmt.setString(4, rfc);
                stmt.setDate(5, fechaIngreso);
                stmt.setString(6, puesto);
                stmt.execute();
                alertaSuccess.setHeaderText("Alta a colaborador realizada");
                alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
                alertaSuccess.showAndWait();
                //refrescar tabla
                this.tabla.refresh();
                llenarTabla();
            } else {
                alertaSuccess.setHeaderText("Sin vacantes");
                alertaSuccess.setContentText("Las plazas para " + puesto + ", ya están ocupados");
                alertaSuccess.showAndWait();
            }

        } catch (Exception e) {
            alertaError.setHeaderText("ERROR EN CONEXION A BASE DE DATOS");
            alertaError.setContentText("Verifique que tenga conexión a Internet\n"
                    + "Caso contrario comuniquese a Sistemas Ext. 30011");
            alertaError.showAndWait();
        }
        con.close();
    }


    private boolean verificarVacantes(String puesto) throws SQLException {
        con = conexion.conectar2();
        boolean validado = false;
        int nPlazas = 0, contadorPuestosOcupados = 0;
        String aux = "";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT plazas from puestos WHERE nombre ='" + puesto + "' ");
            while (rs.next()) {
                nPlazas = rs.getInt(1);
            }

            rs = stmt.executeQuery("SELECT nombre FROM colaboradores WHERE puesto = '" + puesto + "'");
            while (rs.next()) {
                aux = rs.getString(1);//nombre del colaborador nuevo
                contadorPuestosOcupados++;
            }

            if (contadorPuestosOcupados < nPlazas) {
                validado = true;
            }
            return validado;
        } catch (Exception e) {
            alertaError.setHeaderText("ERROR EN CONEXION A BASE DE DATOS");
            alertaError.setContentText("Verifique que tenga conexión a Internet\n"
                    + "Caso contrario comuniquese a Sistemas Ext. 30011");
            alertaError.showAndWait();
        }
        con.close();
        return validado;
    }

    private void puestosExistentes() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select nombre from puestos");
            while (rs.next()) {
                puestoIngresado.getItems().add(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }

    private void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        this.tabla.getItems().clear();
        Colaborador colaborador;
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from colaboradores");

        try {
            while (rs.next()) {
                colaborador = new Colaborador();
                colaborador.setId(String.valueOf(rs.getInt(1)));
                colaborador.setNombre(rs.getString(2));
                colaborador.setNss(rs.getString(4));
                colaborador.setRfc(rs.getString(5));
                colaborador.setNacimiento(format.format(rs.getDate(3)));
                colaborador.setIngreso(format.format(rs.getDate(6)));
                colaborador.setPuesto(rs.getString(7));
                colaboradores.add(colaborador);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        idColaborador.setCellValueFactory(new PropertyValueFactory("id"));
        nombreColaborador.setCellValueFactory(new PropertyValueFactory("nombre"));
        nssColaborador.setCellValueFactory(new PropertyValueFactory("nss"));
        rfcColaborador.setCellValueFactory(new PropertyValueFactory("rfc"));
        fNacimientoColaborador.setCellValueFactory(new PropertyValueFactory("nacimiento"));
        fIngresoColaborador.setCellValueFactory(new PropertyValueFactory("ingreso"));
        puestoColaborador.setCellValueFactory(new PropertyValueFactory("puesto"));
        tabla.setItems(colaboradores);
        con.close();
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        Colaborador colaborador = this.tabla.getSelectionModel().getSelectedItem();
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("¿Estas seguro de eliminar a: " + colaborador.getNombre()+ " ?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
            CallableStatement stmt = null; //objeto para llamar al procedimiento
            String sql = "{call eliminarColaboradores (?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, colaborador.getNombre());
            stmt.execute();
            alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
            alertaSuccess.setContentText("Colaborador fuera del sistema");
            alertaSuccess.showAndWait();
            llenarTabla();
        }
        con.close();
    }
}
