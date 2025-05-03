/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Consumo;
import clases_hospital.EstudioMedico;
import clases_hospital_DAO.ConsumoQuirofanoDAO;
import clases_hospital_DAO.EstudiosMedicosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class MovimientoEstudiosMedicosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<EstudioMedico> estudiosMedicos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    private EstudioMedico estudiomedico;

    int idPaciente;
    int idFolio;
    String folio;

    EstudiosMedicosDAO estudiomedicodao;
    ConsumoQuirofanoDAO consumoQuirofanoDAO;

    ContextMenu contextMenu;
    MenuItem deleteItem;
    
    Paciente paciente;

    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfEstudis;
//    private Label lblEstudio;
//    private Label lblLaboratorio;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<EstudioMedico> tabla;
    @FXML
    private TableColumn<?, ?> colEstudios;
    @FXML
    private TableColumn<?, ?> colLaboratorio;
    @FXML
    private Button btnAgregarEstudio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (estudiomedico != null) {

            double preciosiniva = estudiomedico.getPrecio_venta_unitaria_paquete();
            double precioconiva = Double.parseDouble(df.format(preciosiniva * 1.16));
            double iva = Double.parseDouble(df.format(precioconiva - preciosiniva));

            estudiomedico.setIdPaciente(idPaciente);
            estudiomedico.setIdFolio(idFolio);
            estudiomedico.setUsuarioPedido(VPMedicaPlaza.userSystem);
            estudiomedico.setEstatusEstudio(1);
            estudiomedico.setEstatusPagoEstudio(0);
            estudiomedico.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            estudiomedico.setPrecio_sin_iva(preciosiniva);
            estudiomedico.setIva(iva);
            estudiomedico.setPrecio_con_iva(precioconiva);
            estudiomedico.setId_solicitud(0);
            estudiosMedicos.add(estudiomedico);

            colEstudios.setCellValueFactory(new PropertyValueFactory("nombreEstudio"));
            colLaboratorio.setCellValueFactory(new PropertyValueFactory("nombre_comercial_laboratorio"));

            tabla.setItems(estudiosMedicos);
            estudiomedico = null;
            txfEstudis.setText("");
        } else {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("SELECCIONE UN ESTUDIO PARA CONTINUAR");
            alertaError.showAndWait();
        }
    }

    @FXML
    private void accionAgregarEstudio(ActionEvent event) {
        con = conexion.conectar2();
        estudiomedicodao = new EstudiosMedicosDAO(con);
        consumoQuirofanoDAO = new ConsumoQuirofanoDAO(con);
        try {
            for (EstudioMedico estudio : estudiosMedicos) {
                estudiomedicodao.insertarEstudioMedico(estudio);
                agregarConsumoPaciente(estudio);
            }
            alertaSuccess.setHeaderText(null);
            alertaSuccess.setTitle("INGRESO EXITOSO");
            alertaSuccess.setContentText("ESTUDIO/OS INGRESADO/OS EXITOSAMENTE");
            alertaSuccess.showAndWait();

            Stage stage = (Stage) btnAgregarEstudio.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("HA OCURRIDO UN ERROR, FAVOR DE CONTACTAR AL DEPARTAMENTO DE SISTEMAS");
            alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
            alertaError.showAndWait();
        }
    }
    


    public void recibirDatos(Paciente paciente, boolean agregar) throws SQLException {
        this.paciente = paciente;
        if (agregar) {
            idPaciente = paciente.getIdPaciente();
            idFolio = paciente.getIdfolio();
            folio = paciente.getFolio();
            llenarCmb();
            menuDesplegable();
        } else {
            txfEstudis.setDisable(true);
            btnAgregarEstudio.setDisable(true);
            btnAgregar.setDisable(true);
            llenartabla();
        }
    }
    
    private void llenartabla() throws SQLException{
        conexion = new Conexion();
        con = conexion.conectar2();
        estudiomedicodao = new EstudiosMedicosDAO(con);
        estudiosMedicos.addAll(estudiomedicodao.obtenerconsumoEstudiosPorIdFolio(paciente));
        colEstudios.setCellValueFactory(new PropertyValueFactory("nombreEstudio"));
        colLaboratorio.setCellValueFactory(new PropertyValueFactory("nombre_comercial_laboratorio"));

            tabla.setItems(estudiosMedicos);
        
    }

    private void llenarCmb() {
        try {
            estudiomedicodao = new EstudiosMedicosDAO(conexion.conectar2());
            AutoCompletionBinding<EstudioMedico> estudiosmedicos = TextFields.bindAutoCompletion(txfEstudis, estudiomedicodao.obtenerTodosLosEstudiosMedicosParaIngresarAconsumo());
            estudiosmedicos.setPrefWidth(850);
            estudiosmedicos.setOnAutoCompleted(e -> {
                estudiomedico = e.getCompletion();
//                lblEstudio.setText(estudiomedico.getNombreEstudio());
//                lblLaboratorio.setText(estudiomedico.getNombre_comercial_laboratorio());
            });

        } catch (SQLException ex) {
            Logger.getLogger(MovimientoEstudiosMedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarConsumoPaciente(EstudioMedico item) {
        Consumo consumo = new Consumo(
                item.getNombreEstudio(),// tipo
                1,// cantidad
                item.getPrecio_venta_unitaria_paquete(),//costo unitario multiplicado por la cantidad
                folio,// folio
                idPaciente,//id paciente
                idFolio,// id folio
                item.getIdInsumo()// id producto
        );
        consumo.setPrecioUnitario(item.getPrecio_sin_iva());
        consumo.setId_tipo_consumo(7);
        consumoQuirofanoDAO.incertarConsumo(consumo);
    }

    private void menuDesplegable() {
        contextMenu = new ContextMenu();
        deleteItem = new MenuItem("Eliminar");

        deleteItem.setOnAction(event -> {
            EstudioMedico selectedItem = tabla.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                estudiosMedicos.remove(selectedItem);
            }
        });

        contextMenu.getItems().add(deleteItem);

        tabla.setRowFactory(tv -> {
            TableRow<EstudioMedico> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    tabla.getSelectionModel().select(row.getItem());
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

}
