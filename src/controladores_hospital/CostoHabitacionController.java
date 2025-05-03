/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.CostoHabitacion;
import clases_hospital.CostoHabitacionDAO;
import clases_hospital.Habitacion;
import clases_hospital_DAO.HabitacionDAO;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class CostoHabitacionController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Connection conUsuarios = conexion.conectar2();
    boolean modo = false;
    int idCosto;
    int idHabitacion;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txfNombreCosto;
    @FXML
    private TextField txfCosto;
    @FXML
    private ChoiceBox<String> cbxHabitacion;
    @FXML
    private TableView<CostoHabitacion> tabla;
    @FXML
    private TableColumn<CostoHabitacion, String> clmTipo;
    @FXML
    private TableColumn<CostoHabitacion, String> clmTipoCosto;
    @FXML
    private TableColumn<CostoHabitacion, Double> clmCosto;
    @FXML
    private JFXButton txtTitulo;
    @FXML
    private Button btnEnviarATabla;
    @FXML
    private TextField txfHoras;
    @FXML
    private TextField txfHorasTolerancia;
    @FXML
    private TextField txfMinutosTolerancia;
    @FXML
    private TextField txfMinutos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        costoHabitacion();
        try {
            llenarHabitaciones();
        } catch (SQLException ex) {
            Logger.getLogger(CostoHabitacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionBtnAgregar(ActionEvent event) throws SQLException {

        if (modo) {
            insertCostoHabitacion();
        } else {
            if (tabla.getItems().isEmpty()) {
                alertaError.setTitle("ERROR!");
                alertaError.setHeaderText("TABLA SIN DATOS");
                alertaError.setContentText("NO SE AN ENCONTRADO DATOS EN LA TABLA PARA AGREGAR");
                alertaError.showAndWait();
            } else {
                insertCostoHabitacion();
            }
        }
    }

    @FXML
    private void actionBtnSalir(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionBtnEnviarATabla(ActionEvent event) throws SQLException {
        if (validarDatos()) {
            llenarTabla();
        } else {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("CAMPOS VACIOS");
            alertaError.setContentText("VERIFIQUE QUE LOS CAMPOS ESTÉN LLENOS CORRECTAMENTE");
            alertaError.showAndWait();
        }
    }

    private boolean validarDatos() {
        if (cbxHabitacion.getValue() == null || txfCosto.getText().isEmpty() || txfNombreCosto.getText().isEmpty()
                || txfHoras.getText().isEmpty() || txfMinutos.getText().isEmpty() || txfHorasTolerancia.getText().isEmpty() || txfMinutosTolerancia.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void insertCostoHabitacion() throws SQLException {
        if (modo) {//modo actualizar datos
            Connection connection = null;

            try {
                connection = conexion.conectar2();

                CostoHabitacionDAO costoHabitacionDAO = new CostoHabitacionDAO(connection);
                costoHabitacionDAO.actualizarCostoHabitacion(idHabitacion, txfNombreCosto.getText(), Integer.valueOf(txfCosto.getText()), Integer.valueOf(txfHoras.getText()),
                        Integer.valueOf(txfMinutos.getText()), Integer.valueOf(txfHorasTolerancia.getText()), Integer.valueOf(txfMinutosTolerancia.getText()), idCosto);
                alertaInfo.setTitle("EXITO!");
                alertaInfo.setHeaderText(null);
                alertaInfo.setContentText("PROCEDIMIENTO REALIZADO");
                alertaInfo.showAndWait();
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
            } finally {
                if (connection != null) {
                    connection.close(); // Cierra la conexión
                }
            }

        } else {
            int idHabitacion = 0;
            Connection connection = null;

            try {
                connection = conexion.conectar2(); // Abre la conexión

                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT h.id_tipo FROM tipoHabitacion h WHERE h.tipo ='" + cbxHabitacion.getValue() + "'");
                try {
                    while (rs.next()) {
                        idHabitacion = rs.getInt(1);
                    }
                } catch (Exception e) {
                 
                    e.printStackTrace();
                }

                for (int i = 0; i < tabla.getItems().size(); i++) {
                    CostoHabitacionDAO costoDao = new CostoHabitacionDAO(connection);
                    costoDao.insertCostoHabitacion(tabla.getItems().get(i).getIdHabitacion(),
                            tabla.getItems().get(i).getNombre(),
                            tabla.getItems().get(i).getPrecio(),
                            tabla.getItems().get(i).getHoras(),
                            tabla.getItems().get(i).getMinutos(),
                            tabla.getItems().get(i).getHorasTolerancia(),
                            tabla.getItems().get(i).getMinutosTolerancia());
                }

//                costoDao.insertCostoHabitacion(idHabitacion, txfNombreCosto.getText().toUpperCase(), Double.valueOf(txfCosto.getText()), cbxHorasPaquete.getValue(), cbxMinutosPaquete.getValue(), cbxHorasTolerancia.getValue(), cbxMinutosTolerancia.getValue());
                alertaInfo.setTitle("EXITO!");
                alertaInfo.setHeaderText(null);
                alertaInfo.setContentText("PROCEDIMIENTO REALIZADO");
                alertaInfo.showAndWait();
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
                cbxHabitacion.setValue(null);
                txfCosto.clear();
                txfNombreCosto.clear();
                txfHoras.clear();
                txfMinutos.clear();
                txfHorasTolerancia.clear();
                txfMinutosTolerancia.clear();
            } catch (SQLException e) {
                // Manejo de excepciones
            } finally {
                if (connection != null) {
                    connection.close(); // Cierra la conexión
                }
            }
        }

    }

    public void llenarHabitaciones() throws SQLException {
        HabitacionDAO habitacionDAO = new HabitacionDAO(conexion.conectar2());
        List<Habitacion> habitaciones = habitacionDAO.obtenerTiposHabitacion();

        for (Habitacion habitacion : habitaciones) {
            cbxHabitacion.getItems().add(habitacion.getTipo());
        }
    }

    private void costoHabitacion() {
        txfCosto.setOnKeyTyped(event -> {
            String numeros = event.getCharacter();
            if (!numeros.matches("[0-9.]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            } else if (numeros.equals(".") && txfCosto.getText().contains(".")) {
                event.consume(); // Consumimos el evento si ya existe un punto en el texto
            } else if (txfCosto.getText().contains(".")) {
                int index = txfCosto.getText().indexOf(".");
                if (txfCosto.getText().substring(index + 1).length() >= 2) {
                    event.consume(); // Consumimos el evento si ya hay dos decimales
                }
            }
        });
        txfHoras.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
        txfHorasTolerancia.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
        txfMinutos.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
        txfMinutosTolerancia.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
    }

    private void llenarTabla() throws SQLException {
        int idHabitacion = 0;
        Connection connection = null;

        CostoHabitacion costoH;
        ObservableList<CostoHabitacion> costos = FXCollections.observableArrayList();

        clmCosto.setCellValueFactory(new PropertyValueFactory("precio"));
        clmTipo.setCellValueFactory(new PropertyValueFactory("tipoHabitacion"));
        clmTipoCosto.setCellValueFactory(new PropertyValueFactory("nombre"));

        connection = conexion.conectar2(); // Abre la conexión
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT h.id_tipo FROM tipoHabitacion h WHERE h.tipo ='" + cbxHabitacion.getValue() + "'");
            try {
                while (rs.next()) {
                    idHabitacion = rs.getInt(1);
                }
            } catch (Exception e) {
                
                e.printStackTrace();
            }

            costoH = new CostoHabitacion(idHabitacion, txfNombreCosto.getText().toUpperCase(), Double.valueOf(txfCosto.getText()),
                    Integer.valueOf(txfHoras.getText()), Integer.valueOf(txfMinutos.getText()), Integer.valueOf(txfHorasTolerancia.getText()), Integer.valueOf(txfMinutosTolerancia.getText()), cbxHabitacion.getValue());

            costos.add(costoH);
            tabla.getItems().add(costoH);
            cbxHabitacion.setValue(null);
            txfCosto.clear();
            txfNombreCosto.clear();
            txfHoras.clear();
            txfMinutos.clear();
            txfHorasTolerancia.clear();
            txfMinutosTolerancia.clear();

        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close(); // Cierra la conexión
            }
        }
    }

    public void recibirDatos(String tipoHabitacion, double costo, String nombreCosto, int horas, int minutos, int horasTolerancia, int minutosTolerancia, boolean modo, int idCosto, int idHabitacion) {
        cbxHabitacion.setValue(tipoHabitacion);
        txfCosto.setText(String.valueOf(costo));
        txfNombreCosto.setText(nombreCosto);
        txfHoras.setText(String.valueOf(horas));
        txfMinutos.setText(String.valueOf(minutos));
        txfHorasTolerancia.setText(String.valueOf(horasTolerancia));
        txfMinutosTolerancia.setText(String.valueOf(minutosTolerancia));
        this.modo = modo;
        this.idCosto = idCosto;
        btnEnviarATabla.setDisable(modo);
        this.idHabitacion = idHabitacion;

    }
}
