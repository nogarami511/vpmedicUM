/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Insumo;
import clases_hospital.PaqueteMedico;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.PaquetesMedicosDAO;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AgregarNuevoPaqueteControllerConLista implements Initializable {
    
    ToggleGroup toggleGroup = new ToggleGroup();
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Conexion conexion = new Conexion();
    Connection con;
    PaqueteMedico paqueteMedicoRecibido;
    double precioDelPaquete;
    
    ObservableList<Insumo> insumos = FXCollections.observableArrayList();
    
    InsumosDAO insumosDAO;

    @FXML
    private TextField nombrePaquete;
    @FXML
    private TextField descripcionPaquete;
    @FXML
    private TextField precioPaquete;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField claveSATPaquete;
    @FXML
    private TextField diasHospitalizacionPaquete;
    @FXML
    private TextField numeroComidasPaquete;
    @FXML
    private TextField horasToleranciaPaquete;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnActualizar;
    @FXML
    private ComboBox<Double> cmbPrecioSugerido;// no se si hacerlo con un double
    @FXML
    private RadioButton rdbPrecioManual;
    @FXML
    private RadioButton rdbPrecioSugerido;
    @FXML
    private TextField utilidadPaquete;
    @FXML
    private TextField costoPaquete;
    @FXML
    private Label lblSugerido;
    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private TableColumn<?, ?> colInsumos;
    @FXML
    private TableColumn<?, ?> colCostoNuevo;
    @FXML
    private TableColumn<?, ?> colCosotoAnteriror;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        rdbPrecioManual.setToggleGroup(toggleGroup);
        rdbPrecioSugerido.setToggleGroup(toggleGroup);
    }

    @FXML
    private void ingresar(ActionEvent event) {
        con = conexion.conectar2();
        CallableStatement stmt = null;
        String sqlIngresarInsumoPaquete = "{call ingresarPaqueteMedico(?,?,?,?,?,?,?,?)}";
        try {
            stmt = con.prepareCall(sqlIngresarInsumoPaquete);
            stmt.setString(1, nombrePaquete.getText());
            stmt.setString(2, descripcionPaquete.getText());
            stmt.setDouble(3, Double.parseDouble(precioPaquete.getText()));
            stmt.setInt(4, vpmedicaplaza.VPMedicaPlaza.userSystem);
            stmt.setString(5, claveSATPaquete.getText());
            stmt.setInt(6, Integer.parseInt(diasHospitalizacionPaquete.getText()));
            stmt.setInt(7, Integer.parseInt(numeroComidasPaquete.getText()));
            stmt.setInt(8, Integer.parseInt(horasToleranciaPaquete.getText()));
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

    public void setObjeto(PaqueteMedico paqueteMedico) throws SQLException {
        con = conexion.conectar2();
        paqueteMedicoRecibido = paqueteMedico;
        btnIngresar.setVisible(false);
        btnActualizar.setVisible(true);
        nombrePaquete.setText(paqueteMedicoRecibido.getNombre());
        descripcionPaquete.setText(paqueteMedicoRecibido.getDescripcion());
        precioPaquete.setText(String.valueOf(paqueteMedicoRecibido.getPrecio()));
        precioDelPaquete = paqueteMedicoRecibido.getPrecio();
        claveSATPaquete.setText(paqueteMedicoRecibido.getClaveSAT());
        diasHospitalizacionPaquete.setText(String.valueOf(paqueteMedicoRecibido.getDias_hospitalizacion()));
        numeroComidasPaquete.setText(String.valueOf(paqueteMedicoRecibido.getNumero_comidas()));
        horasToleranciaPaquete.setText(String.valueOf(paqueteMedicoRecibido.getHoras_tolerancia()));
        limitarCampoCantidadAnumeros();
        
//        if(){}
        
        PaquetesMedicosDAO paquetesMedicosDAO = new PaquetesMedicosDAO(con);
        double precioCostoPaquete = paquetesMedicosDAO.precioCostoPaquete(paqueteMedico.getId());
        costoPaquete.setText(String.valueOf(precioCostoPaquete));

        utilidadPaquete.setText("99.99999");

        double utilidad = Double.parseDouble(utilidadPaquete.getText());

        // Redondear hacia abajo
        double redondeoAbajo = (Math.floor((precioCostoPaquete / (utilidad / 100.00)) / 5) * 5) * 1.16;

        // Redondear hacia arriba
        double redondeoArriba = (Math.ceil((precioCostoPaquete / (utilidad / 100.00)) / 5) * 5) * 1.16;

        ObservableList<Double> preciosSugeridos = FXCollections.observableArrayList();
        preciosSugeridos.add(redondeoAbajo);
        preciosSugeridos.add(redondeoArriba);
        cmbPrecioSugerido.setItems(preciosSugeridos);
        cmbPrecioSugerido.setOnAction(e -> {
            precioPaquete.setText(cmbPrecioSugerido.getValue().toString());
        });
        
        llenarTabla();

    }

    @FXML
    private void actualizar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        PaqueteMedicoDAO paqueteMedicoDAO = new PaqueteMedicoDAO(con);

        paqueteMedicoRecibido.setNombre(nombrePaquete.getText());
        paqueteMedicoRecibido.setDescripcion(descripcionPaquete.getText());
        paqueteMedicoRecibido.setPrecio(Double.parseDouble(precioPaquete.getText()));
        paqueteMedicoRecibido.setIdUsuario(vpmedicaplaza.VPMedicaPlaza.userSystem);
        paqueteMedicoRecibido.setClaveSAT(claveSATPaquete.getText());
        paqueteMedicoRecibido.setDias_hospitalizacion(Integer.parseInt(diasHospitalizacionPaquete.getText()));
        paqueteMedicoRecibido.setNumero_comidas(Integer.parseInt(numeroComidasPaquete.getText()));
        paqueteMedicoRecibido.setHoras_tolerancia(Integer.parseInt(horasToleranciaPaquete.getText()));

        paqueteMedicoDAO.actualizar(paqueteMedicoRecibido);

        if (precioDelPaquete != Double.parseDouble(precioPaquete.getText())) {
            paqueteMedicoDAO.actualizarEstatusMovimiento(paqueteMedicoRecibido.getId());
        }
        alertaConfirmacion.setHeaderText("PAQUETE ACTUALIZADO");
        alertaConfirmacion.setContentText("PROCEDIMIENTO EJECUTADO CON EXITO");
        alertaConfirmacion.showAndWait();

        salir(event);
    }

    @FXML
    private void accionRdbPrecioManual(ActionEvent event) {
        cmbPrecioSugerido.setDisable(true);
        precioPaquete.setDisable(false);

    }

    @FXML
    private void accionRdbPrecioSugerido(ActionEvent event) {
        cmbPrecioSugerido.setDisable(false);
        precioPaquete.setDisable(true);
    }

    @FXML
    private void accionNumeroComidas(KeyEvent event) {

    }

    @FXML
    private void accionDiasHospitalizacion(KeyEvent event) {

    }

    @FXML
    private void accionUtilidad(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            double utilidad = Double.parseDouble(utilidadPaquete.getText());
            double precioConUtilidad = Double.parseDouble(precioPaquete.getText()) / (utilidad / 100.0);

            Locale mexicoLocale = new Locale("es", "MX");
            NumberFormat formatoMonedaMexico = NumberFormat.getCurrencyInstance(mexicoLocale);
            lblSugerido.setText(formatoMonedaMexico.format(precioConUtilidad));

            // Redondear hacia abajo
            double redondeoAbajo = (Math.floor((precioConUtilidad / (utilidad / 100.00)) / 5) * 5) * 1.16;

            // Redondear hacia arriba
            double redondeoArriba = (Math.ceil((precioConUtilidad / (utilidad / 100.00)) / 5) * 5) * 1.16;

            ObservableList<Double> preciosSugeridos = FXCollections.observableArrayList();
            preciosSugeridos.add(redondeoAbajo);
            preciosSugeridos.add(redondeoArriba);
            cmbPrecioSugerido.setItems(preciosSugeridos);
            cmbPrecioSugerido.setOnAction(e -> {
                precioPaquete.setText(cmbPrecioSugerido.getValue().toString());
            });
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
        utilidadPaquete.setTextFormatter(formatter);

        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().isEmpty()) {
                change.setText("0");
                change.setCaretPosition(1); // Coloca el cursor al final
            }
            return change;
        });

        utilidadPaquete.setTextFormatter(textFormatter);
    }

    private boolean isValidNumeric(String text) {
        // Utiliza una expresión regular para validar números con un punto decimal
        return Pattern.matches("^\\d*\\.?\\d*$", text);
    }
    
    private void llenarTabla() {
        try {
            con = conexion.conectar2();
            insumosDAO = new InsumosDAO(con);
            
            insumos.addAll(insumosDAO.optnerDatosInsumo(paqueteMedicoRecibido.getId()));
            
            colInsumos.setCellValueFactory(new PropertyValueFactory("nombre"));
            colCostoNuevo.setCellValueFactory(new PropertyValueFactory("costo_compra_caja"));
            colCosotoAnteriror.setCellValueFactory(new PropertyValueFactory("costoAnteriror"));
            
            tabla.setItems(insumos);
            pintarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarNuevoPaqueteControllerConLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void pintarTabla() {
        tabla.setRowFactory(tv -> new TableRow<Insumo>() {
            @Override
            public void updateItem(Insumo item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getModificacion() == 1) {
                    setStyle("-fx-background-color: #FF5733; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
                } else {
                    setStyle(" ");
                }

            }
        });
    }
    
}
