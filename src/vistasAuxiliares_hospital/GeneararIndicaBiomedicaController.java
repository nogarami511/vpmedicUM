/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.ArmadoPaqueteMedicoLista;
import clase.Conexion;
import clase.ConfiguracionPaqueteLista;
import clase.ConsumosLista;
import clase.IndicaDetalleLista;
import clase.Paciente;
import clases_hospital.Area;
import clases_hospital.ConfiguracionPaquete;
import clases_hospital.Consumo;
import clases_hospital.FamiliaInsumos;
import clases_hospital.Folio;
import clases_hospital.IndicaDetalle;
import clases_hospital.IndicaSuministroPacientes;
import clases_hospital.Indicasp;
import clases_hospital.Insumo;
import clases_hospital.Inventario;
import clases_hospital.InventarioDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital.PaqueteMedico;
import clases_hospital_DAO.AreaDAO;
import clases_hospital_DAO.ArmadoPaqueteMedicoDAO;
import clases_hospital_DAO.ConfiguracionPaqueteDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.CostoHabitacionDAO;
import clases_hospital_DAO.FamiliasInsumosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.IndicaDetalleDAO;
import clases_hospital_DAO.IndicaSuministroPacientesDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventarioDetalleDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class GeneararIndicaBiomedicaController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    ObservableList<Insumo> equipomedico = FXCollections.observableArrayList();

    ObservableList<Consumo> consumos = FXCollections.observableArrayList();

    List<IndicaDetalleLista> indicaDetalleListas = new ArrayList<>();

    MovimientoInventarioP movimientoInventarioP;
    Paciente pasiienteGuardado;
    Insumo insumo;

    Area area;

    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;
    InventarioDetalleDAO inventarioDetalleDAO;
    IndicaspDAO indicaspDAO;
    IndicaDetalleDAO indicasDetalleDAO;
    IndicaSuministroPacientesDAO indicasSuministroPacientesDAO;
    AreaDAO areaDAO;
    FoliosDAO foliosDAO;
    PaqueteMedicoDAO paquetemedicoDAO;
    ConsumosDAO consumosDAO;
    InsumosDAO insumosDAO;

    double cantidadInsumoConsumo;

    private TextField txfInsumo;
    @FXML
    private TextField txfCantidad;
    @FXML
    private Label lblNombrePaciente;
    private Button btnGenerar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Consumo> tabla;
    @FXML
    private TableColumn<Consumo, Double> colCantidad;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private Button btnAgregarTabla;
    @FXML
    private TextField txfservicio;
    @FXML
    private Button btnAgregar;
    @FXML
    private Label lblPquete;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txfCantidad.setDisable(false);
        btnAgregarTabla.setDisable(false);
        llenarxtf();
        lambda();
        tabla.setEditable(true);

    }

    @FXML
    private void accionLimitarPaquete(KeyEvent event) {
    }

    @FXML
    private void accionAgregarTabla(ActionEvent event) {
        agregarconsumnio();
    }

    @FXML
    private void accionGenerar(ActionEvent event) {
        alertaConfirmacion.setHeaderText("CONFIRMACION");
        alertaConfirmacion.setTitle("CONFIRMACION");
        alertaConfirmacion.setContentText("¿ESTA SEGURO DE AGREGAR SERVICIOS A LA CUENTA DEL PACIENTE?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
            agregarConsumo();

            alertaConfirmacion.setHeaderText("CONFIRMACION");
            alertaConfirmacion.setTitle("CONFIRMACION");
            alertaConfirmacion.setContentText("PROCESOS EJECUTADO CORRECTAMENTE");

            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } else {
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("CONFIRMACION");
            alertaConfirmacion.setContentText("INGRESE CORRECTAMENTE LOS DATOS PARA CONTINUAR");
            alertaConfirmacion.showAndWait();
        }
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
    }

    private void llenarxtf() {
        con = conexion.conectar2();
        insumosDAO = new InsumosDAO(con);
        insumo = new Insumo();

        List<Insumo> eqm = new ArrayList<>();
        eqm.addAll(insumosDAO.InsumosEquipoMedicos());
        AutoCompletionBinding<Insumo> nombres = TextFields.bindAutoCompletion(txfservicio, eqm);
        nombres.setPrefWidth(1000);
        nombres.setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<Insumo> event) -> {

            Insumo insumoSeleccionado = event.getCompletion();
            insumo = insumoSeleccionado;
        //    System.out.println("El insumo es: " + insumo.getNombre() + "ID INSUMO: " + insumo.getId());

        });

    }

    public void setObjeto(Paciente paciente, int modulo) {
        pasiienteGuardado = paciente;
        lblNombrePaciente.setText(paciente.getNombre());

    }

    private void agregarconsumnio() {
        Consumo consumoAgregar = new Consumo();
        int cantidad = 0; // Inicializamos la variable

        try {
            // Intentamos convertir el texto a un entero
            cantidad = Integer.parseInt(txfCantidad.getText());

            // Aquí puedes añadir una validación adicional si lo deseas
            if (cantidad < 0) {
                alertaError.setTitle("ERROR");
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("la cantidad no puede ser 0");
                alertaError.showAndWait();
                // Maneja el caso de cantidad negativaD
            }
        } catch (NumberFormatException e) {
            // Capturamos la excepción si la conversión falla
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("agrege un valor valido");
            alertaError.showAndWait();
            // Maneja el error como desees (puedes establecer 'cantidad' a 0, por ejemplo)
        }
        consumoAgregar.setId_producto_venta(insumo.getId());
        consumoAgregar.setTipo(insumo.getNombre());
        consumoAgregar.setCantidad(cantidad);

        llenarTabla(consumoAgregar);
    }

    private void llenarTabla(Consumo consumoAgregado) {
        consumos.add(consumoAgregado);

        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colInsumo.setCellValueFactory(new PropertyValueFactory("tipo"));
        editarTabla();
        //editarTabla();
        tabla.setItems(consumos);
    }

    private void agregarConsumo() {
        con = conexion.conectar2();
        consumosDAO = new ConsumosDAO(con);

        for (int i = 0; i < consumos.size(); i++) {
            try {
                consumos.get(i).setId_folio(pasiienteGuardado.getIdfolio());
                consumos.get(i).setId_usuario_creacion(VPMedicaPlaza.userSystem);

                consumosDAO.insertarConsumoServicios(consumos.get(i));
            } catch (SQLException ex) {
                Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
       private void lambda() {
        tabla.setRowFactory(tableView -> {
            TableRow<Consumo> row = new TableRow<>();
            ContextMenu cxmConfiguracion = new ContextMenu();
            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
            descartarConsumo.setOnAction(event -> {
                //con = conexion.conectar2();
                Consumo ConsumoTabla = row.getItem();
                consumos.remove(ConsumoTabla);

            });
            cxmConfiguracion.getItems().add(descartarConsumo);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(cxmConfiguracion)
            );

            return row;
        });
    }
        private void editarTabla() {
        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCantidad.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            Consumo cantidadConsumo = event.getTableView().getItems().get(event.getTablePosition().getRow());
           
                    // actualizar el valor de cantidad en el objeto Consumo
                    cantidadConsumo.setCantidad(event.getNewValue());

            tabla.refresh();
        });

        colCantidad.setEditable(true);
        tabla.setEditable(true);
    }
       

}
