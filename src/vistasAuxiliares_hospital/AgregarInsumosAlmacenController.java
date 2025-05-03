/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Almacen;
import clases_hospital.Insumo;
import clases_hospital.InventarioAlmacen;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventarioAlmacenDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AgregarInsumosAlmacenController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<InventarioAlmacen> inventariosalmacenes = FXCollections.observableArrayList();
    //ObservableList<InventarioAlmacen> inventariosalmacenesNuevo = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    Almacen almacen = new Almacen();
    InventarioAlmacenDAO inventarioalmacendao;

    InsumosDAO insumosdao;

    Insumo insumo = new Insumo();

    private int id_almacen;

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnCapturar;
    @FXML
    private TextField txfInsumos;
    @FXML
    private TextField txfFonfoFijo;
    @FXML
    private Button btnAgregarTabla;
    @FXML
    private TableView<InventarioAlmacen> tabla;
    @FXML
    private TableColumn<?, ?> colInsumos;
    @FXML
    private TableColumn<InventarioAlmacen, Double> colFondoFijo;

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
    private void accionCapturar(ActionEvent event) {
        try {
            inventarioalmacendao = new InventarioAlmacenDAO(conexion.conectar2());
            inventarioalmacendao.insertAll(inventariosalmacenes);

            Stage stage = (Stage) btnCapturar.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarInsumosAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionInsumos(ActionEvent event) {
    }

    @FXML
    private void accionFondoFijo(ActionEvent event) {
    }

    @FXML
    private void accionAgregarTabla(ActionEvent event) {
        if (txfInsumos.getText().equals("") || txfFonfoFijo.getText().equals("") || insumo.getId() < 1) {
        } else {
            InventarioAlmacen inventarioAlmacen = new InventarioAlmacen();
            inventarioAlmacen.setNombre_inusmo(txfInsumos.getText());
            inventarioAlmacen.setIdInsumo(insumo.getId());
            inventarioAlmacen.setFondoFijo(Double.parseDouble(txfFonfoFijo.getText()));
            inventarioAlmacen.setFalta(Double.parseDouble(txfFonfoFijo.getText()));
            inventarioAlmacen.setIdAlmacen(id_almacen);
            inventarioAlmacen.setUsuarioModificacion(VPMedicaPlaza.userSystem);

            inventariosalmacenes.add(inventarioAlmacen);
            //inventariosalmacenesNuevo.add(inventarioAlmacen);

            colInsumos.setCellValueFactory(new PropertyValueFactory("nombre_inusmo"));
            colFondoFijo.setCellValueFactory(new PropertyValueFactory("fondoFijo"));

            tabla.setItems(inventariosalmacenes);
            txfInsumos.clear();
            txfFonfoFijo.clear();
        }
    }

    public void recibirDatos(int id_almacen) throws SQLException {
        this.id_almacen = id_almacen;
        llenarBuscador();
        lambda();
    }

    private void llenarBuscador() throws SQLException {
        insumosdao = new InsumosDAO(conexion.conectar2());
        AutoCompletionBinding<Insumo> insumobuscador = TextFields.bindAutoCompletion(txfInsumos, insumosdao.optenerDatosInsumosMedicosConValoresIndispensables());
        insumobuscador.setPrefWidth(850);
        insumobuscador.setOnAutoCompleted(e -> {
            insumo = e.getCompletion();
        });
    }

    public void lambda() {
        colFondoFijo.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colFondoFijo.setOnEditCommit(event -> {
            InventarioAlmacen inventarioAlmacenSeleccionado = event.getTableView().getItems().get(event.getTablePosition().getRow());
            inventarioAlmacenSeleccionado.setFondoFijo(event.getNewValue());
            tabla.refresh();
        });
        colFondoFijo.setEditable(true);
        tabla.setEditable(true);
        tabla.setRowFactory(tableView -> {
            TableRow<InventarioAlmacen> row = new TableRow<>();
            ContextMenu cxmConfiguracion = new ContextMenu();
            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
            descartarConsumo.setOnAction(event -> {
                //con = conexion.conectar2();
                InventarioAlmacen inventarioAlmacen = row.getItem();
                
                inventariosalmacenes.remove(inventarioAlmacen);
          
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

}
