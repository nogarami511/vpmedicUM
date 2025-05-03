/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Area;
import clases_hospital.IndicaDetalle;
import clases_hospital.IndicaSuministroPacientes;
import clases_hospital.Indicasp;
import clases_hospital.Inventario;
import clases_hospital_DAO.AreaDAO;
import clases_hospital_DAO.IndicaDetalleDAO;
import clases_hospital_DAO.IndicaSuministroPacientesDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.InventariosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class GeneararIndicaController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaSucces = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaWarring = new Alert(Alert.AlertType.WARNING);
    ObservableList<IndicaDetalle> indicasDetalles = FXCollections.observableArrayList();
    ObservableList<Area> areas = FXCollections.observableArrayList();

    Paciente pasiienteGuardado;
    IndicaDetalle indicasdetalle;
    Area area;

    InventariosDAO inventariosDAO;
    IndicaspDAO indicaspDAO;
    IndicaDetalleDAO indicasDetalleDAO;
    IndicaSuministroPacientesDAO indicasSuministroPacientesDAO;
    AreaDAO areaDAO;

    int idInsumo;
    int id_indicasp;

    @FXML
    private TextField txfInsumo;
    @FXML
    private TextField txfCantidad;
    @FXML
    private Label lblNombrePaciente;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<IndicaDetalle> tabla;
    @FXML
    private TableColumn<IndicaDetalle, Double> colCantidad;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private Button btnAgregarTabla;
    @FXML
    private ComboBox<Area> cmbArea;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        limitarCampoCantidadAnumeros();
        lambda();
        llenarArea();
    }

    @FXML
    private void accionGenerar(ActionEvent event) {
        generarIndicasPeIndicasDetalle();
        reporte();

        Stage stage = (Stage) btnGenerar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionAgregarTabla(ActionEvent event) {
        if (txfCantidad.getText().isEmpty() || txfInsumo.getText().isEmpty() || Double.valueOf(txfCantidad.getText()) <= 0.0) {
            alertaError.setTitle("ERROR!");
            alertaError.setHeaderText("CAMPOS VACIOS");
            alertaError.setContentText("POR FAVOR VERIFIQUE QUE LOS CAMPOS\n(1) INSUMO\n(2) CANTIDAD\nESTEN LLENOS\n(3) LA CANTIDAD INGRESADA SEA MAYOR QUE 0");
            alertaError.showAndWait();
        } else {
            try {
                con = conexion.conectar2();
                inventariosDAO = new InventariosDAO(con);
                Inventario inventario = inventariosDAO.obtenerDatosPorIdInsumo(idInsumo);

                if (inventario.getTotalExistencia() >= Double.valueOf(txfCantidad.getText())) {
                    IndicaDetalle indicaDetalle = new IndicaDetalle();
                    indicaDetalle.setIdInsumo(inventario.getId_insumo());
                    indicaDetalle.setCantidadEntregada(Double.valueOf(txfCantidad.getText()));
                    indicaDetalle.setCantidadDevolucion(0.0);
                    indicaDetalle.setIdUsuarioCreacion(VPMedicaPlaza.userSystem);
                    indicaDetalle.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                    indicaDetalle.setNombreInsumo(txfInsumo.getText());
                    llenarTabla(indicaDetalle);

                    txfCantidad.setText("");
                    txfInsumo.setText("");
                } else {
                    alertaError.setTitle("ERROR!");
                    alertaError.setHeaderText("PRODUCTO INSUFICIENTE");
                    alertaError.setContentText("EL PRODUCTO SOLO CUENTA CON\n" + "\"" + inventario.getTotalExistencia() + "\"" + "\nNUMERO DE PIEZAS");
                    alertaError.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GeneararIndicaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void accionArea(ActionEvent event) {
        txfInsumo.setDisable(false);
        txfCantidad.setDisable(false);
        btnAgregarTabla.setDisable(false);
        area = cmbArea.getValue();
    }

    public void setObjeto(Paciente paciente) {
        pasiienteGuardado = paciente;
        lblNombrePaciente.setText(paciente.getNombre());
        llenarBuscadorInsumo();
    }

    private void llenarArea() {
        try {
            areaDAO = new AreaDAO(conexion.conectar2());
            areas.addAll(areaDAO.getAll());
            cmbArea.setItems(areas);
        } catch (SQLException ex) {
            Logger.getLogger(GeneararIndicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarBuscadorInsumo() {
        try {
            con = conexion.conectar2();
            inventariosDAO = new InventariosDAO(con);
            List<Inventario> inventarios = inventariosDAO.otenerDatosBusqueda(1);
            AutoCompletionBinding<Inventario> nombres = TextFields.bindAutoCompletion(txfInsumo, inventarios);
            nombres.setPrefWidth(1000);
            nombres.setOnAutoCompleted(event -> {
                Inventario insumoSeleccionado = event.getCompletion();
                idInsumo = insumoSeleccionado.getId_insumo();
                System.out.println(insumoSeleccionado.getNombreCompleto() + " : " + insumoSeleccionado.getId_insumo());

            });
        } catch (SQLException ex) {
            Logger.getLogger(GeneararIndicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla(IndicaDetalle indicadetalle) {
        indicasDetalles.add(indicadetalle);

        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidadEntregada"));
        colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));

        editarTabla();
        tabla.setItems(indicasDetalles);
    }

    private void editarTabla() {
        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCantidad.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            IndicaDetalle indicadetalleeditar = event.getTableView().getItems().get(event.getTablePosition().getRow());
            try {
                con = conexion.conectar2();
                inventariosDAO = new InventariosDAO(con);
                Inventario inventario = inventariosDAO.obtenerDatosPorIdInsumo(idInsumo);

                if (inventario.getTotalExistencia() >= event.getNewValue()) {
                    // actualizar el valor de cantidad en el objeto Consumo
                    indicadetalleeditar.setCantidadEntregada(event.getNewValue());
                } else {
                    alertaError.setTitle("ERROR!");
                    alertaError.setHeaderText("PRODUCTO INSUFICIENTE");
                    alertaError.setContentText("EL PRODUCTO SOLO CUENTA CON\n" + "\"" + inventario.getTotalExistencia() + "\"" + "\nNUMERO DE PIEZAS");
                    alertaError.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GeneararIndicaController.class.getName()).log(Level.SEVERE, null, ex);
            }

            tabla.refresh();
        });

        colCantidad.setEditable(true);
        tabla.setEditable(true);
    }

    private void generarIndicasPeIndicasDetalle() {
        con = conexion.conectar2();
        indicaspDAO = new IndicaspDAO(con);
        indicasDetalleDAO = new IndicaDetalleDAO(con);
        indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(con);

        Indicasp indicap = new Indicasp();
        indicap.setIdPaciente(pasiienteGuardado.getIdPaciente());
        indicap.setIdFolio(pasiienteGuardado.getIdfolio());
        indicap.setIdUsuarioEntrega(VPMedicaPlaza.userSystem);
        indicap.setIdUsuarioSolicitud(0);
        indicap.setEstatusIndica(0);
        indicap.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
        indicap.setId_area(area.getIdArea());

        id_indicasp = indicaspDAO.create(indicap);

        for (int i = 0; i < indicasDetalles.size(); i++) {
            indicasDetalles.get(i).setIdIndicasp(id_indicasp);
            int id_indica_detalle = indicasDetalleDAO.createIndica(indicasDetalles.get(i));

            for (int j = 0; j < indicasDetalles.get(i).getCantidadEntregada(); j++) {

                IndicaSuministroPacientes indicasuministropaciente = new IndicaSuministroPacientes();
                indicasuministropaciente.setIdIndicaDetalle(id_indica_detalle);
                indicasuministropaciente.setIdFolio(pasiienteGuardado.getIdfolio());
                indicasuministropaciente.setIdInsimo(indicasDetalles.get(i).getIdInsumo());
                indicasuministropaciente.setSuministro(false);
                indicasuministropaciente.setDevolucion(false);
                indicasuministropaciente.setUsuarioCreacion(VPMedicaPlaza.userSystem);
                indicasuministropaciente.setUsuarioModificacion(VPMedicaPlaza.userSystem);
                indicasuministropaciente.setId_area(area.getIdArea());

                indicasSuministroPacientesDAO.create(indicasuministropaciente);
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
        txfCantidad.setTextFormatter(formatter);
    }

    private boolean isValidNumeric(String text) {
        // Utiliza una expresión regular para validar números con un punto decimal
        return Pattern.matches("^\\d*\\.?\\d*$", text);
    }

    private void lambda() {
        tabla.setRowFactory(tableView -> {
            TableRow<IndicaDetalle> row = new TableRow<>();
            ContextMenu cxmConfiguracion = new ContextMenu();
            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
            descartarConsumo.setOnAction(event -> {
                //con = conexion.conectar2();
                IndicaDetalle indicaDetalle = row.getItem();
                System.out.println("SE VA A ELIMINAR: " + indicaDetalle.toString());
                indicasDetalles.remove(indicaDetalle);

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

    private void reporte() {
        ReporteC reporte = new ReporteC("reporteIndicaEtrega");
        reporte.generarReporteIndicaEntrega(pasiienteGuardado.getIdfolio(), id_indicasp);
    }

}
