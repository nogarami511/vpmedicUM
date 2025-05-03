/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.ArmadoPaqueteMedico;
import clases_hospital.FamiliaInsumos;
import clases_hospital.Insumo;
import clases_hospital_DAO.ArmadoPaqueteMedicoDAO;
import clases_hospital_DAO.FamiliasInsumosDAO;
import clases_hospital_DAO.InsumosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class AgregarInsumoPaqueteMedicoController implements Initializable {

    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    ObservableList<Insumo> observableListInsumos = FXCollections.observableArrayList();//para la tabla
    List<Insumo> insumos;//para el autocompletado
    List<ArmadoPaqueteMedico> objetosArmadoPaqueteMedicos = new ArrayList<>(); // para enviarlo al DAO de armado
    Conexion conexion = new Conexion();
    Connection con;
    Insumo insumoSeleccionado;
    FamiliaInsumos familiaInsumos;
    int modo = 0; //1 insumo medico, 2 insumo alimenticio

    FamiliasInsumosDAO familiasInsumosDAO;

    @FXML
    private TextField nombreInsumoMedico;
    @FXML
    private TextField cantidadInsumoMedico;
    @FXML
    private Button btnSalir;
    @FXML
    private Text nombrePaquete;
    @FXML
    private Text idPaquete;
    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private TableColumn nombreInsumoPaquete;
    @FXML
    private TableColumn cantidadInsumoPaquete;
    @FXML
    private RadioButton rdbInsumo;
    @FXML
    private ToggleGroup toggleFamilia;
    @FXML
    private RadioButton rdbFamilia;
    @FXML
    private Button btnngresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nombreInsumoPaquete.setCellValueFactory(new PropertyValueFactory("nombre"));
        cantidadInsumoPaquete.setCellValueFactory(new PropertyValueFactory("cantidad_unitariaxcaja"));
    }

    @FXML
    private void ingresarInsumoAPaquete(ActionEvent event) throws SQLException {
        if (rdbFamilia.isSelected()) {
         
            insumoSeleccionado = new Insumo();
            insumoSeleccionado.setId(familiaInsumos.getIdFamiliaInsumo());
            insumoSeleccionado.setNombre(familiaInsumos.getNombreFamiliaInsumo());
            insumoSeleccionado.setCantidad_unitariaxcaja(Double.parseDouble(cantidadInsumoMedico.getText()));
            insumoSeleccionado.setFamilia(true);
            observableListInsumos.add(insumoSeleccionado);
            tabla.setItems(observableListInsumos);
        } else {
            insumoSeleccionado.setCantidad_unitariaxcaja(Double.parseDouble(cantidadInsumoMedico.getText()));
            insumoSeleccionado.setFamilia(false);
            observableListInsumos.add(insumoSeleccionado);
            tabla.setItems(observableListInsumos);
        }
        nombreInsumoMedico.setText("");
        cantidadInsumoMedico.setText("");
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionInusmo(ActionEvent event) throws SQLException {
        llenarFamiliasInsumos();
        accionDesbloquaerTextFild();
    }

    @FXML
    private void accionFamilia(ActionEvent event) throws SQLException {
        llenarFamiliasMedicas();
        accionDesbloquaerTextFild();
    }

    private void accionDesbloquaerTextFild() {
        nombreInsumoMedico.setDisable(false);
        cantidadInsumoMedico.setDisable(false);
        btnngresar.setDisable(false);
    }

    private void llenarFamiliasMedicas() {
        con = conexion.conectar2();
        try {
            
            familiasInsumosDAO = new FamiliasInsumosDAO(con);

            AutoCompletionBinding<FamiliaInsumos> medicamentos = TextFields.bindAutoCompletion(nombreInsumoMedico, familiasInsumosDAO.obtenerFamiliasConDatosNecesarios());
            medicamentos.setPrefWidth(1000);
            medicamentos.setOnAutoCompleted(event -> {
                familiaInsumos = event.getCompletion();
                medicamentos.dispose();
            });
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarInsumoPaqueteMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarFamiliasInsumos() {
        con = conexion.conectar2();
        try {
            InsumosDAO insumosDAO = new InsumosDAO(con);
            insumos = insumosDAO.optenerDatosInsumosMedicosConValoresIndispensables();
            AutoCompletionBinding<Insumo> medicamentos = TextFields.bindAutoCompletion(nombreInsumoMedico, insumos);
            medicamentos.setPrefWidth(1000);
            medicamentos.setOnAutoCompleted(event -> {
                insumoSeleccionado = event.getCompletion();
            });
        } catch (SQLException ex) {
            Logger.getLogger(AgregarInsumoPaqueteMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void llenarAutocompletado() throws SQLException {
//        con = conexion.conectar2();
//        if (modo == 1) {
//            if (rdbFamilia.isSelected()) {

//                //Escribir codigo aqui.
//
//            } else {

//
//            }
//        } else {
//            InsumosDAO insumosDAO = new InsumosDAO(con);
//            insumos = insumosDAO.optenerDatosInsumosAlimenticiosConValoresIndispensables();
//            AutoCompletionBinding<Insumo> medicamentos = TextFields.bindAutoCompletion(nombreInsumoMedico, insumos);
//            medicamentos.setPrefWidth(1000);
//            medicamentos.setOnAutoCompleted(event -> {
//                insumoSeleccionado = event.getCompletion();
//            });
//        }
//        con.close();
//    }
    public void recibirDatos(String nombrePaquete, String idPaquete, int modo) throws SQLException {
        this.nombrePaquete.setText(nombrePaquete);
        this.idPaquete.setText(idPaquete);
        this.modo = modo;//1 insumo medico // 2 insumo alimenticio
//        llenarAutocompletado();
    }

    @FXML
    private void capturar(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        alertaConfirmacion.setTitle("AGREGAR INSUMOS A PAQUETE");
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setContentText("¿DESEA INGRESAR LOS INSUMOS AL PAQUETE?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
           
            if (modo == 1) {
               
                hacerObjeto();
              
                ArmadoPaqueteMedicoDAO armadoPaqueteMedicoDAO = new ArmadoPaqueteMedicoDAO(con);

                armadoPaqueteMedicoDAO.insertAllLista(objetosArmadoPaqueteMedicos);
                alertaConfirmacion.setHeaderText("PROCEDIMIENTO EJECUTADO CON EXITO");
                alertaConfirmacion.setContentText("INSUMOS INGRESADOS CORRECTAMENTE AL PAQUETE");
                alertaConfirmacion.showAndWait();
                salir(event);
            } else {
                System.err.println("valores nulos");
            }
        }
        con.close();
    }

    public void hacerObjeto() {
        ArmadoPaqueteMedico armadoPaqueteMedico;
        for (Insumo i : observableListInsumos) {
            armadoPaqueteMedico = new ArmadoPaqueteMedico();
            armadoPaqueteMedico.setIdInsumo(i.getId());
            /*setCantidad_unitariaxcaja*/
            armadoPaqueteMedico.setCantidad(i.getCantidad_unitariaxcaja());
            armadoPaqueteMedico.setIdPaquete(Integer.parseInt(idPaquete.getText()));
            armadoPaqueteMedico.setCosto(i.getPrecioVentaUnitariaPaquete());
            
            armadoPaqueteMedico.setFamilia(i.isFamilia());
            armadoPaqueteMedico.setPrecioPaquete(0.0);
            objetosArmadoPaqueteMedicos.add(armadoPaqueteMedico);
        }
    }

}
