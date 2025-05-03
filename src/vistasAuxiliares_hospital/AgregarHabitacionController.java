/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.EstatusHabitacion;
import clases_hospital.Habitacion;
import clases_hospital_DAO.HabitacionDAO;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class AgregarHabitacionController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);

    boolean modo = false;
    int idHabitacioActualizacion;
    
    @FXML
    private JFXButton btnSalir;
    @FXML
    private ChoiceBox<String> cbxTipoHabitacion;
    @FXML
    private TextField txfNumero;
    @FXML
    private TextField txfPrioridad;
    @FXML
    private TextField txfPiso;
    @FXML
    private Button btnAgregar;
    @FXML
    private ChoiceBox<String> cbxEstatus;
    @FXML
    private TextField txfObservacion;
    @FXML
    private Text txtTitulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarHabitaciones();
            restringirCareacteres();
            llenarEstatus();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarHabitacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionBtnAgregar(ActionEvent event) throws SQLException {
        if (modo) {
            actualizarHabitacion();
        } else {
            incertarHabitacion();
        }

    }

    @FXML
    private void actionBtnSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void restringirCareacteres() {
        txfNumero.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
        txfPiso.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
        txfPrioridad.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
    }

    public void llenarHabitaciones() throws SQLException {
        HabitacionDAO habitacionDAO = new HabitacionDAO(conexion.conectar2());
        List<Habitacion> habitaciones = habitacionDAO.obtenerTiposHabitacion();

        for (Habitacion habitacion : habitaciones) {
            cbxTipoHabitacion.getItems().add(habitacion.getTipo());
        }
    }

    private void llenarEstatus() throws SQLException {
        HabitacionDAO habitacionDAO = new HabitacionDAO(conexion.conectar2());
        List<EstatusHabitacion> estatus = habitacionDAO.obtenerEstatus();

        for (EstatusHabitacion estatu : estatus) {
            cbxEstatus.getItems().add(estatu.getTipo());
        }
        cbxEstatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                EstatusHabitacion estatusSeleccionado = estatus.stream().filter(estatu -> estatu.getTipo().equals(newValue)).findFirst().orElse(null);

                if (estatusSeleccionado != null) {
                    String observaciones = estatusSeleccionado.getObservaciones();
                    txfObservacion.setText(observaciones);
                }
            }
        });
    }

    private boolean validarDatos() {
        if (cbxTipoHabitacion.getValue() == null || cbxTipoHabitacion.getValue() == null || txfNumero.getText().isEmpty() || txfPiso.getText().isEmpty() || txfPrioridad.getText().isEmpty() || txfObservacion.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void actualizarHabitacion() throws SQLException {
        Connection connection = null;
        int idTipoHabitacion = 0;
        int idEstatus = 0;

        if (validarDatos()) { // verificamos que los campos de la vista no esten vacios
            connection = conexion.conectar2();
            // traemos el id del tipo deacuerdo al tipo seleccionado
            Statement stmtTipoHab = connection.createStatement();
            ResultSet rsTipoHabitacion = stmtTipoHab.executeQuery("SELECT h.id_tipo FROM tipoHabitacion h WHERE h.tipo ='" + cbxTipoHabitacion.getValue() + "'");
            // traesmos el id del estatus deacuerdo al tipo seleccionado
            Statement stmtEstatus = connection.createStatement();
            ResultSet rsEstatus = stmtEstatus.executeQuery(" SELECT eh.id_estatus FROM estatus_habitacion eh WHERE eh.tipo ='" + cbxEstatus.getValue() + "'");

            //traemos el id del tipo de la habitacion
            try {
                while (rsTipoHabitacion.next()) { //trameos el id tipo de habitacion de la base de datos
                    idTipoHabitacion = rsTipoHabitacion.getInt(1);
                }
            } catch (Exception e) {
            
                e.printStackTrace();
            }
            // traemos el id del tipo de estatus
            try {
                while (rsEstatus.next()) { // traemos el id tipo de estatus de la base de datos

                    idEstatus = rsEstatus.getInt(1);
                }
            } catch (Exception e) {
             
                e.printStackTrace();
            }

            // declaramos una variable booleana para guardar el resultado que regresa el metodo verificador de numeros y prioridades repetidos
            boolean datosRepetidos;
            datosRepetidos = repetido(idTipoHabitacion, Integer.valueOf(txfPiso.getText()));

            
            // verificamso que las habitaciones y las prioridades no esten repetidas
            if (datosRepetidos) {
                alertaPrecaucion.setTitle("ALERTA!");
                alertaPrecaucion.setHeaderText("DATOS REPETIDOS");
                alertaPrecaucion.setContentText("POR FAVOR VERIFIQUE QUE EL NUMEOR DE HABITACION O LA PRIORIDAD NO ESTEN REPETIDOS");
                alertaPrecaucion.showAndWait();
            } // si el numero de habitacion o prioridad no esta repetido agregamos la habitacion
            else {
                HabitacionDAO habitacionDAO = new HabitacionDAO(connection);
                habitacionDAO.actualizarHabitacion(idHabitacioActualizacion, idTipoHabitacion, Integer.valueOf(txfNumero.getText()), Integer.valueOf(txfPiso.getText()), Integer.valueOf(txfPrioridad.getText()), idEstatus, txfObservacion.getText(), VPMedicaPlaza.userSystem);

                alertaInfo.setTitle("EXITO!");
                alertaInfo.setHeaderText(null);
                alertaInfo.setContentText("PROCEDIMIENTO REALIZADO");
                alertaInfo.showAndWait();
                txfNumero.clear();
                txfPrioridad.clear();
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();
            }

            try {

            } catch (Exception e) {
            } finally {
                if (connection != null) {
                    connection.close(); // Cierra la conexión
                }
            }

        } else {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("CAMPOS VACIOS");
            alertaError.setContentText("VERIFIQUE QUE LOS CAMPOS ESTÉN LLENOS CORRECTAMENTE");
            alertaError.showAndWait();
        }
    }

    private void incertarHabitacion() throws SQLException {
        Connection connection = null;
        int idTipoHabitacion = 0;
        int idEstatus = 0;

        if (validarDatos()) { // verificamos que los campos de la vista no esten vacios
            try {
                connection = conexion.conectar2();
                // traemos el id del tipo deacuerdo al tipo seleccionado
                Statement stmtTipoHab = connection.createStatement();
                ResultSet rsTipoHabitacion = stmtTipoHab.executeQuery("SELECT h.id_tipo FROM tipoHabitacion h WHERE h.tipo ='" + cbxTipoHabitacion.getValue() + "'");
                // traesmos el id del estatus deacuerdo al tipo seleccionado
                Statement stmtEstatus = connection.createStatement();
                ResultSet rsEstatus = stmtEstatus.executeQuery(" SELECT eh.id_estatus FROM estatus_habitacion eh WHERE eh.tipo ='" + cbxEstatus.getValue() + "'");
                //traemos el id del tipo de la habitacion
                try {
                    while (rsTipoHabitacion.next()) {
                        idTipoHabitacion = rsTipoHabitacion.getInt(1);
                        
                    }
                } catch (Exception e) {
                
                    e.printStackTrace();
                }
                // traemos el id del tipo de estatus
                try {
                    while (rsEstatus.next()) {
                        idEstatus = rsEstatus.getInt(1);
                        
                    }
                } catch (Exception e) {
                    
                    e.printStackTrace();
                }

                // declaramos una variable booleana para guardar el resultado que regresa el metodo verificador de numeros y prioridades repetidos
                boolean datosRepetidos;
                datosRepetidos = repetido(idTipoHabitacion, Integer.valueOf(txfPiso.getText()));
              

                // verificamso que las habitaciones y las prioridades no esten repetidas
                if (datosRepetidos) {
                    alertaPrecaucion.setTitle("ALERTA!");
                    alertaPrecaucion.setHeaderText("DATOS REPETIDOS");
                    alertaPrecaucion.setContentText("POR FAVOR VERIFIQUE QUE EL NUMEOR DE HABITACION O LA PRIORIDAD NO ESTEN REPETIDOS");
                    alertaPrecaucion.showAndWait();
                    
                } // si el numero de habitacion o prioridad no esta repetido agregamos la habitacion
                else {
                    HabitacionDAO habitacionDAO = new HabitacionDAO(connection);
                    habitacionDAO.insertCostoHabitacion(idTipoHabitacion, Integer.valueOf(txfNumero.getText()), Integer.valueOf(txfPiso.getText()), Integer.valueOf(txfPrioridad.getText()), idEstatus, txfObservacion.getText().toUpperCase(), VPMedicaPlaza.userSystem);

                    alertaInfo.setTitle("EXITO!");
                    alertaInfo.setHeaderText(null);
                    alertaInfo.setContentText("PROCEDIMIENTO REALIZADO");
                    alertaInfo.showAndWait();

                    // cerramos 
                    Stage stage = (Stage) btnAgregar.getScene().getWindow();
                    stage.close();
                }

            } catch (Exception e) {
            } finally {
                if (connection != null) {
                    connection.close(); // Cierra la conexión
                }
            }

        } else {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("CAMPOS VACIOS");
            alertaError.setContentText("VERIFIQUE QUE LOS CAMPOS ESTÉN LLENOS CORRECTAMENTE");
            alertaError.showAndWait();
        }
    }

    public void recibirDatos(String tipo, int numero, int piso, int idEstatus, String observaciones, int prioridad) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT eh.tipo FROM estatus_habitacion eh WHERE eh.id_estatus ='" + idEstatus + "'");

        while (rs.next()) {
            cbxEstatus.setValue(rs.getString(1));
        }

        cbxTipoHabitacion.setValue(tipo);
        txfNumero.setText(String.valueOf(numero));
        txfPiso.setText(String.valueOf(piso));
        txfObservacion.setText(observaciones);
        txfPrioridad.setText(String.valueOf(prioridad));
    }

    public boolean modo(boolean modo) {
        this.modo = modo;
        btnAgregar.setText("EDITAR");
        txtTitulo.setText("EDITAR HABITACION");
        
        return modo;
    }

    public int recibirIdHabitacion(int id) {
        this.idHabitacioActualizacion = id;
        return idHabitacioActualizacion;
    }

    private boolean repetido(int idTipoHabitacion, int piso) throws SQLException {// verificamos si el piso y la prioridad de la habitacion no esta repetida
        int numeroRepetido = 0;
        int prioridadRepetida = 0;
        ArrayList<Integer> numeros = new ArrayList<>();
        ArrayList<Integer> prioridades = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = null;
            if (modo) {
             
                rs = statement.executeQuery("SELECT h.numero_habitacion, h.id_prioridad FROM habitacion h WHERE h.id_tipo = '" + idTipoHabitacion + "' AND h.piso = '" + piso + "' AND h.id_habitacion != '" + idHabitacioActualizacion + "'");
            } else {
               
                rs = statement.executeQuery("SELECT h.numero_habitacion, h.id_prioridad FROM habitacion h WHERE h.id_tipo = '" + idTipoHabitacion + "' AND h.piso = '" + piso + "'");
            }
            while (rs.next()) {
                numeroRepetido = rs.getInt(1);
               
                numeros.add(numeroRepetido);
                prioridadRepetida = rs.getInt(2);
              
                prioridades.add(prioridadRepetida);

            }
            for (int i = 0; i < numeros.size(); i++) {
                if (numeros.get(i) == Integer.valueOf(txfNumero.getText())) {
                
                    txfNumero.clear();
                   

                    return true;
                }
            }
            for (int i = 0; i < prioridades.size(); i++) {
                if (prioridades.get(i) == Integer.valueOf(txfPrioridad.getText())) {
                  
                    txfPrioridad.clear();
                  
                    return true;
                }
            }

        } catch (Exception e) {

        }
       
        return false;
    }
}
