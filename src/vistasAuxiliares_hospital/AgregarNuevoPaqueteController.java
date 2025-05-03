/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Habitacion;
import clases_hospital.PaqueteMedico;
import clases_hospital.TipoHabitacion;
import clases_hospital_DAO.ArmadoPaqueteMedicoDAO;
import clases_hospital_DAO.CostoHabitacionDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.PaqueteAlimentoDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.PaquetesMedicosDAO;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class AgregarNuevoPaqueteController implements Initializable {

    ToggleGroup toggleGroup = new ToggleGroup();
    ToggleGroup toggleGroup2 = new ToggleGroup();
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Conexion conexion = new Conexion();
    Connection con;
    PaqueteMedico paqueteMedicoRecibido;
    ArmadoPaqueteMedicoDAO armadopaquetemedicoDAO;
    double precioDelPaquete;
    double costoInsumos;
    ObservableList<TipoHabitacion> habitacion = FXCollections.observableArrayList();

    @FXML
    private TextField nombrePaquete;
    @FXML
    private TextField descripcionPaquete;
    @FXML
    private TextField precioPaquete;
    // @FXML
    //  private TextField produccionpaquete;
    @FXML
    private TextField claveSATPaquete;
    @FXML
    private TextField diasHospitalizacionPaquete;
    @FXML
    private TextField numeroComidasPaquete;
    @FXML
    private TextField horasToleranciaPaquete;
    @FXML
    private TextField utilidadPaquete;
    @FXML
    private TextField costoPaquete;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnActualizar;
    @FXML
    private ComboBox<TipoHabitacion> cmbtipohabitacion;
    @FXML
    private ComboBox<Double> cmbPrecioSugerido;// no se si hacerlo con un double
    @FXML
    private RadioButton rdbPrecioManual;
    @FXML
    private RadioButton rdbPrecioSugerido;
    @FXML
    private Label lblSugerido;
    @FXML
    private Text lblCostoHabitacion;
    @FXML
    private Text lblCostoComidas;

    private double costo, costoconproduccion;
    private double servMyE, totalservMyE;
    private double costoxcomida, totalxcomida;
    private double costoxhabitacion, totalxhabitacion;
    private int dias, diascomida;

    @FXML
    private TextField produccionpaquete;
    @FXML
    private RadioButton rdbAmbulante;
    @FXML
    private RadioButton rdbDia;
    @FXML
    private RadioButton rbServicioMyE;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // redondeo();
        rdbPrecioManual.setToggleGroup(toggleGroup);
        rdbPrecioSugerido.setToggleGroup(toggleGroup);
        rdbAmbulante.setToggleGroup(toggleGroup2);
        rdbDia.setToggleGroup(toggleGroup2);

        /// rdbPrecioSugerido.setToggleGroup(toggleGroup);
    }

    @FXML
    private void ingresar(ActionEvent event) {
        con = conexion.conectar2();
        CallableStatement stmt = null;
        String sqlIngresarInsumoPaquete = "{call ingresarPaqueteMedico2(?,?,?,?,?,?,?,?,?)}";
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
            stmt.setInt(9, cmbtipohabitacion.getValue().getIdTipo());
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

        PaquetesMedicosDAO paquetesMedicosDAO = new PaquetesMedicosDAO(con);
        double precioCostoPaquete = paquetesMedicosDAO.precioCostoPaquete(paqueteMedico.getId());
        costoPaquete.setText(String.valueOf(precioCostoPaquete));

        armadopaquetemedicoDAO = new ArmadoPaqueteMedicoDAO(con);
        costoInsumos = armadopaquetemedicoDAO.sumaArmadoPaqueteMedicoInsumos(paqueteMedico.getId());

        utilidadPaquete.setText("99.99999");
        
        
        
        List<Double> lista = new ArrayList();

        String query = "{CALL TRAER_COSTOS_SERVICIO_PAQUETES()}";
        try (CallableStatement stmt = con.prepareCall(query)) {
            // Ejecutar el procedimiento almacenado
            ResultSet rs = stmt.executeQuery();
            // Procesar el resultado
            while (rs.next()) {
                // Recuperar datos y crear objetos CostoServicioPaquete
                lista.add(rs.getDouble(2));
                // Agregar a la lista
            }
        }
        double dieta = lista.get(0);
        double havutacion = lista.get(1);
        double quirofano = lista.get(2);
        double enfermeria = lista.get(3); 
        double medico = lista.get(4);
        double lavanderia = lista.get(5);
        
        double suma = costoInsumos + dieta + havutacion + quirofano + enfermeria + medico + lavanderia;

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
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println(suma);
        costoPaquete.setText(df.format((suma)));
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
        paqueteMedicoRecibido.setId_tipo_habitacion(cmbtipohabitacion.getValue().getIdTipo());

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
    private void accionRdbAmbulatorio(ActionEvent event) {
        try {
            diasHospitalizacionPaquete.setDisable(true);
            llenardatos2();

            costo = totalxcomida + totalxhabitacion + totalservMyE;
            costoPaquete.setText("" + costo);
        } catch (SQLException ex) {
            Logger.getLogger(AgregarNuevoPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionRdbDia(ActionEvent event) {
        try {
            diasHospitalizacionPaquete.setDisable(false);
            llenardatos();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarNuevoPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void accionRdbservEyM(ActionEvent event) {
        if (rbServicioMyE.isSelected()) {

        } else {
            totalservMyE = 0.0;
        }

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
        redondeo();
    }

    @FXML
    private void accionNumeroComidas(KeyEvent event) {
        try {
            diascomida = Integer.parseInt(numeroComidasPaquete.getText());
            totalxcomida = costoxcomida * diascomida;

            lblCostoComidas.setText("$" + totalxcomida);
            costo = totalxcomida + totalxhabitacion + totalservMyE;
            costoPaquete.setText("" + costo);
        } catch (NumberFormatException e) {
            System.err.println("Error: Ingresa un número válido para las comidas");
        }

    }

    @FXML
    private void accionDiasHospitalizacion(KeyEvent event) {

        try {

            dias = Integer.parseInt(diasHospitalizacionPaquete.getText());
            if (rbServicioMyE.isSelected()) {
                totalservMyE = servMyE * dias;

            } else {
                totalservMyE = 0.0;
            }
            totalxhabitacion = costoxhabitacion * dias;

            lblCostoHabitacion.setText("$" + totalxhabitacion);

            costo = totalxcomida + totalxhabitacion + totalservMyE;
            costoPaquete.setText("" + costo);

        } catch (NumberFormatException e) {
            // Manejar la excepción de formato incorrecto

            // Puedes mostrar un mensaje de error al usuario si es necesario
            // También puedes establecer un valor predeterminado para 'dias' o 'totalhabitacion'
        }

    }

    @FXML
    private void accionProduccion(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String textoCostoPaquete = produccionpaquete.getText();
            String cos = costoPaquete.getText();

            if (!textoCostoPaquete.isEmpty()) {
                try {
                    double produccion = Double.parseDouble(textoCostoPaquete) / 100;
                    double precioproduccion = Double.parseDouble(cos) * produccion;
                    precioproduccion = precioproduccion + Double.parseDouble(cos);

                    costoconproduccion = precioproduccion;

                } catch (NumberFormatException e) {
                    System.err.println("Error: Ingresa un número válido para costo del paquete");
                    // Puedes manejar el error según tus necesidades
                }
            } else {
                System.err.println("Error: Ingresa un valor para costo del paquete");
                // Puedes manejar el error según tus necesidades
            }
        }
    }

    @FXML
    private void accionUtilidad(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String textoCostoPaquete = utilidadPaquete.getText(), formatoa;
            DecimalFormat formato = new DecimalFormat("#.00");

            if (!textoCostoPaquete.isEmpty()) {
                try {
                    double utilidad = Double.parseDouble(textoCostoPaquete) / 100;
                    double precioutilidad = costoconproduccion * utilidad;
                    precioutilidad = precioutilidad + costoconproduccion;

                    costoconproduccion = precioutilidad;

                    formatoa = formato.format(precioutilidad);
                    lblSugerido.setText("$" + formatoa);
                } catch (NumberFormatException e) {
                    System.err.println("Error: Ingresa un número válido para costo del paquete");
                    // Puedes manejar el error según tus necesidades
                }
            } else {
                System.err.println("Error: Ingresa un valor para costo del paquete");
                // Puedes manejar el error según tus necesidades
            }
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

    public void llenardatos() throws SQLException {
        con = conexion.conectar2();
        CostoHabitacionDAO habitacionDAO = new CostoHabitacionDAO(con);
        PaqueteAlimentoDAO comida = new PaqueteAlimentoDAO(con);
        InsumosDAO enfermera = new InsumosDAO(con);
        InsumosDAO medico = new InsumosDAO(con);

        servMyE = enfermera.optenerDatosInsumos(617).getPrecio_venta_unitaria() + medico.optenerDatosInsumos(616).getPrecio_venta_unitaria();

        costoxcomida = comida.CostoPorComida();
        lblCostoComidas.setText("$" + costoxcomida);
        // Obtener la lista de tipos de habitación
        habitacion.addAll(habitacionDAO.TiposHabitacion());

        // Configurar el ComboBox con la lista de tipos de habitación
        cmbtipohabitacion.setItems(habitacion);
        cmbtipohabitacion.setOnAction(e -> {

            try {
                costoxhabitacion = habitacionDAO.traerTodoPorID(cmbtipohabitacion.getValue().getIdTipo()).getPrecio();
                lblCostoHabitacion.setText("$" + costoxhabitacion);

                //dias = Integer.parseInt(diasHospitalizacionPaquete.getText());
            } catch (SQLException ex) {
                Logger.getLogger(AgregarNuevoPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    public void llenardatos2() throws SQLException {
        con = conexion.conectar2();
        CostoHabitacionDAO habitacionDAO = new CostoHabitacionDAO(con);
        PaqueteAlimentoDAO comida = new PaqueteAlimentoDAO(con);
        InsumosDAO enfermera = new InsumosDAO(con);
        InsumosDAO medico = new InsumosDAO(con);
        InsumosDAO ambulatorio = new InsumosDAO(con);

        servMyE = enfermera.optenerDatosInsumos(623).getPrecio_venta_unitaria() + medico.optenerDatosInsumos(622).getPrecio_venta_unitaria();
        System.out.println(servMyE);

        costoxhabitacion = ambulatorio.optenerDatosInsumos(1151).getPrecio_venta_unitaria();
        costoxcomida = comida.CostoPorComida();
        lblCostoComidas.setText("$" + costoxcomida);
        // Obtener la lista de tipos de habitación
        habitacion.addAll(habitacionDAO.TiposHabitacion());

        // Configurar el ComboBox con la lista de tipos de habitación
        cmbtipohabitacion.setItems(habitacion);
        cmbtipohabitacion.setOnAction(e -> {
            totalxhabitacion = costoxhabitacion;
            totalservMyE = servMyE;

            System.out.println(totalxhabitacion);
            lblCostoHabitacion.setText("$" + totalxhabitacion);
            costoPaquete.setText("" + (totalxhabitacion + totalservMyE));
//            try {
//              //  costoxhabitacion = habitacionDAO.traerTodoPorID(cmbtipohabitacion.getValue().getIdTipo()).getPrecio();
//               
//             
//                //dias = Integer.parseInt(diasHospitalizacionPaquete.getText());
//
//            } catch (SQLException ex) {
//                Logger.getLogger(AgregarNuevoPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
//            }

        });

    }

    public void redondeo() {
        double redondeoArriba = 0.0, redondeoAbajo = 0.0;
        double precio = 0.0;
        String cadena = lblSugerido.getText().replace("$", "");

        precio = Double.parseDouble(cadena);

        // Redondear hacia abajo
        redondeoAbajo = (Math.floor((precio / 100) / 5) * 5) * 100;

        // Redondear hacia arriba
        redondeoArriba = (Math.ceil((precio / 100) / 5) * 5) * 100;

        ObservableList<Double> preciosSugeridos = FXCollections.observableArrayList();
        preciosSugeridos.add(redondeoAbajo);
        preciosSugeridos.add(redondeoArriba);
        cmbPrecioSugerido.setItems(preciosSugeridos);
        cmbPrecioSugerido.setOnAction(e -> {
            precioPaquete.setText(cmbPrecioSugerido.getValue().toString());
        });

    }
}
