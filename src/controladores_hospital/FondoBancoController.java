/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Meses;
import clases_hospital.FondoCuentaBancoo;
import clases_hospital.FondoEfectivo;
import clases_hospital_DAO.FondoBancoDAO;
import clases_hospital_DAO.FondoBancoFijoDAO;
import clases_hospital_DAO.FondoEfectivoDAO;
import clases_hospital_DAO.FondocuentabancoDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.List;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class FondoBancoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<FondoCuentaBancoo> fondosEfectivos;
    ObservableList<Meses> mesesObservableList = FXCollections.observableArrayList();
    int mesSeleccionINT = 0;
    FondocuentabancoDAO fondobancofijoDAO;

    @FXML
    private TableView<FondoCuentaBancoo> tabla;
    @FXML
    private TableColumn fechaFondoEfectivo;
    @FXML
    private TableColumn tipoOperacionFondoEfectivo;
    @FXML
    private TableColumn conceptoFondoEfectivo;
    @FXML
    private TableColumn importeFondoEfectivo;
    @FXML
    private TableColumn saldoFondoEfectivo;
    @FXML
    private ChoiceBox<Meses> mesSeleccion;
    @FXML
    private TextField totalFondoEfectivo;
    @FXML
    private Button btnIngresoEfectivo;
    @FXML
    private Button btnRetiroEfectivo;
    @FXML
    private AnchorPane pane_IngreseContraseña;
    @FXML
    private TextField txtcontraseña;
    @FXML
    private Button btnAceptarcontra;
    @FXML
    private Button btncancelarcontra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
            traerFondoEfectivoFijo();
            Meses mesesObjeto = new Meses();
            mesesObservableList = mesesObjeto.mesesAnio();
            mesSeleccion.setItems(mesesObservableList);
        } catch (SQLException ex) {
            Logger.getLogger(FondoEfectivoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarTabla() throws SQLException {
        try {
            con = conexion.conectar2();
            FondoBancoDAO fondoEfectivoDAO = new FondoBancoDAO(con);
            // Obtener todos los pacientes desde la base de datos
            List<FondoCuentaBancoo> fondoE = fondoEfectivoDAO.obtenerTodosLosFondosBancoDeEsteMes();

            // Crear una lista observable para los datos de la tabla
            fondosEfectivos = FXCollections.observableArrayList(fondoE);

            // Asignar las propiedades de los pacientes a las columnas de la tabla
            fechaFondoEfectivo.setCellValueFactory(new PropertyValueFactory("fecha"));
            tipoOperacionFondoEfectivo.setCellValueFactory(new PropertyValueFactory("tipoOperacion"));
            conceptoFondoEfectivo.setCellValueFactory(new PropertyValueFactory("concepto"));
            importeFondoEfectivo.setCellValueFactory(new PropertyValueFactory("importe"));
            saldoFondoEfectivo.setCellValueFactory(new PropertyValueFactory("saldo"));

            // Asignar los datos a la tabla
            tabla.setItems(fondosEfectivos);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void seleccionMes(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        Meses mes = mesSeleccion.getValue();
        System.out.println(""+ mes.getNumeroMes());;
        FondoBancoDAO fondoEfectivoDAO = new FondoBancoDAO(con);
        List<FondoCuentaBancoo> f = fondoEfectivoDAO.obtenerTodosLosFondosEfectivoXMes(mes.getNumeroMes());
        fondosEfectivos.clear();
        fondosEfectivos.addAll(f);
        // Asignar las propiedades de los pacientes a las columnas de la tabla
        fechaFondoEfectivo.setCellValueFactory(new PropertyValueFactory("fecha"));
        tipoOperacionFondoEfectivo.setCellValueFactory(new PropertyValueFactory("tipoOperacion"));
        conceptoFondoEfectivo.setCellValueFactory(new PropertyValueFactory("concepto"));
        importeFondoEfectivo.setCellValueFactory(new PropertyValueFactory("importe"));
        saldoFondoEfectivo.setCellValueFactory(new PropertyValueFactory("saldo"));

        // Asignar los datos a la tabla
        tabla.setItems(fondosEfectivos);
        con.close();

    }
    
    @FXML
    private void quitarDeFondoEfectivo(ActionEvent event) {
        FondoCuentaBancoo fondoEfectivo = tabla.getSelectionModel().getSelectedItem();
        System.out.println("Concepto: "+fondoEfectivo.getConcepto()+" idCliente: "+fondoEfectivo.getIdCliente());
    }

    @FXML
    private void accionIngresoEfectivo(ActionEvent event) {
        irIngresoEfecticvo();
    }
    
    @FXML
    private void accionRetiroEfectivo(ActionEvent event) {
        retiroEfectivo();
    }

    public void traerFondoEfectivoFijo() throws SQLException {
        con = conexion.conectar2();
        double montoEnFondoEfectivo = 0.0;
        String montomontoEnFondoEfectivoFormateado = "";
        try {
            Statement statement = con.createStatement();
            String query = "SELECT * FROM fondocuentabancofijo";
           // System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            //formatter number
            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();

            while (resultSet.next()) {
                montoEnFondoEfectivo = resultSet.getDouble(2);
            }
            montomontoEnFondoEfectivoFormateado = formatoImporte.format(montoEnFondoEfectivo);
            totalFondoEfectivo.setText(montomontoEnFondoEfectivoFormateado);
            //totalFondoEfectivo.setStyle(""); // centrar el texto

            if (montoEnFondoEfectivo < 0.0) {
                //-fx-background-color
                totalFondoEfectivo.setStyle("-fx-background-color: #d93838");
            } else if (montoEnFondoEfectivo > 0.0) {
                totalFondoEfectivo.setStyle("-fx-background-color: #47e841");
            }else{
                totalFondoEfectivo.setStyle("");
            }

            //System.out.println("Fondo efectivo con formato: " + montomontoEnFondoEfectivoFormateado);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        con.close();
    }
    
    private void retiroEfectivo() {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/EgresoEfectivo.fxml"));
            Parent root = loader.load();

            // Crear un nuevo Stage para la vista de destino
            Stage destinoStage = new Stage();
            destinoStage.setTitle("EDITAR PACIENTE");
            destinoStage.setScene(new Scene(root));
            destinoStage.initModality(Modality.APPLICATION_MODAL);
            destinoStage.initStyle(StageStyle.UNDECORATED);

            // Mostrar el nuevo Stage de forma modal
            destinoStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(IndicasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void irIngresoEfecticvo() {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/IngresoEfectivo.fxml"));
            Parent root = loader.load();

            // Crear un nuevo Stage para la vista de destino
            Stage destinoStage = new Stage();
            destinoStage.setTitle("EDITAR PACIENTE");
            destinoStage.setScene(new Scene(root));
            destinoStage.initModality(Modality.APPLICATION_MODAL);
            destinoStage.initStyle(StageStyle.UNDECORATED);

            // Mostrar el nuevo Stage de forma modal
            destinoStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(IndicasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actualizarfondobancofijo(KeyEvent event) {
           totalFondoEfectivo.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                pane_IngreseContraseña.setVisible(true);

            }
        });
    }

    @FXML
    private void Aceptarcontra(ActionEvent event) throws SQLException {
         con = conexion.conectar2();
        fondobancofijoDAO = new FondocuentabancoDAO(con);
        double montoingresado = 0.0;

        if (txtcontraseña.getText().equals("Caja321")) {
            try {
                 montoingresado = Double.parseDouble(totalFondoEfectivo.getText());

                System.out.println("Monto ingresado: " + montoingresado);

            } catch (NumberFormatException e) {
                montoingresado = fondobancofijoDAO.fondocuentabancofijo();
                System.err.println("Error: Ingrese un número válido.");

            }
            fondobancofijoDAO.actualizarfindobancofijo( montoingresado);
        }
         pane_IngreseContraseña.setVisible(false);
        con.close();
        txtcontraseña.clear();
    }

    @FXML
    private void cancelarcontra(ActionEvent event) {
        pane_IngreseContraseña.setVisible(false);
        txtcontraseña.clear();
    }


}
