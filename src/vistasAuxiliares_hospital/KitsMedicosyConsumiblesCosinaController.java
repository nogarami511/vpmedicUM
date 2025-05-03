/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Insumo;
import clases_hospital.KitMedicoyConsumiblesCocina;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.KitMedicoyConsumiblesCocinaDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class KitsMedicosyConsumiblesCosinaController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    ObservableList<KitMedicoyConsumiblesCocina> KitMedicosyConsumiblesCocinas = FXCollections.observableArrayList();
    ObservableList<KitMedicoyConsumiblesCocina> KitMedicosyConsumiblesCocinasGuardado = FXCollections.observableArrayList();

    InsumosDAO insumosdao;
    KitMedicoyConsumiblesCocinaDAO kitmedicoycinsumiblecosinadao;

    int id_inusmo;
    String nombre_inusmo;
    boolean editar = false;
    String valores = "";

    Insumo insumo;

    @FXML
    private TableView<KitMedicoyConsumiblesCocina> tabla;
    @FXML
    private TableColumn<KitMedicoyConsumiblesCocina, String> colInsumo;
    @FXML
    private TableColumn<KitMedicoyConsumiblesCocina, Integer> colCantidad;
    @FXML
    private TextField txfInsumos;
    @FXML
    private TextField txfCantidad;
    @FXML
    private Button btnAcptarTabla;
    @FXML
    private Button btnCancelarTabla;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblinfoInsumo1;
    @FXML
    private Label lblinfoCantiad1;
    @FXML
    private Button btnEditar;
    @FXML
    private TableView<KitMedicoyConsumiblesCocina> tablaEditar;
    @FXML
    private TableColumn<KitMedicoyConsumiblesCocina, String> colInsumoEditar;
    @FXML
    private TableColumn<KitMedicoyConsumiblesCocina, Double> colCantidadEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionAceptarTabla(ActionEvent event) {
        if (editar) {
            KitMedicoyConsumiblesCocina kitmedicoCosina = new KitMedicoyConsumiblesCocina();
            kitmedicoCosina.setNombreInsumo(nombre_inusmo);
            kitmedicoCosina.setCantidad(Double.parseDouble(txfCantidad.getText()));
            kitmedicoCosina.setIdIsnumo(id_inusmo);
            kitmedicoCosina.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            kitmedicoCosina.setEstatus(1);
            kitmedicoCosina.setId_insumo_padre(insumo.getId());

            KitMedicosyConsumiblesCocinasGuardado.add(kitmedicoCosina);

            colInsumoEditar.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
            colCantidadEditar.setCellValueFactory(new PropertyValueFactory("cantidad"));

            tablaEditar.setItems(KitMedicosyConsumiblesCocinasGuardado);

        } else {
            KitMedicoyConsumiblesCocina kitmedicoCosina = new KitMedicoyConsumiblesCocina();
            kitmedicoCosina.setNombreInsumo(nombre_inusmo);
            kitmedicoCosina.setCantidad(Double.parseDouble(txfCantidad.getText()));
            kitmedicoCosina.setIdIsnumo(id_inusmo);
            kitmedicoCosina.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            kitmedicoCosina.setEstatus(1);
            kitmedicoCosina.setId_insumo_padre(insumo.getId());

            KitMedicosyConsumiblesCocinas.add(kitmedicoCosina);

            colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
            colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));

            tabla.setItems(KitMedicosyConsumiblesCocinas);
        }
        KitMedicoyConsumiblesCocina kitmedicoCosina = new KitMedicoyConsumiblesCocina();
        kitmedicoCosina.setNombreInsumo(nombre_inusmo);
        kitmedicoCosina.setCantidad(Double.parseDouble(txfCantidad.getText()));
        kitmedicoCosina.setIdIsnumo(id_inusmo);
        kitmedicoCosina.setUsuarioModificacion(VPMedicaPlaza.userSystem);
        kitmedicoCosina.setEstatus(1);
        kitmedicoCosina.setId_insumo_padre(insumo.getId());

        KitMedicosyConsumiblesCocinas.add(kitmedicoCosina);

        colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));

        tabla.setItems(KitMedicosyConsumiblesCocinas);

        txfInsumos.setText("");
        txfInsumos.setText("");
    }

    @FXML
    private void accionCancelarTabla(ActionEvent event) {
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        kitmedicoycinsumiblecosinadao = new KitMedicoyConsumiblesCocinaDAO(conexion.conectar2());
        try {
            for (KitMedicoyConsumiblesCocina kitmedcosina : KitMedicosyConsumiblesCocinas) {
                kitmedicoycinsumiblecosinadao.crear(kitmedcosina);
            }

            insumo.setKit_consumible(true);
            insumosdao.actualizarInsumosKit_consumible(insumo);

            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(KitsMedicosyConsumiblesCosinaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void llenarbuscador() throws SQLException {
        insumosdao = new InsumosDAO(conexion.conectar2());
        AutoCompletionBinding<Insumo> insumos = TextFields.bindAutoCompletion(txfInsumos, insumosdao.optenerDatosInsumosMedicosConValoresIndispensables());
        insumos.setMaxWidth(1000);
        insumos.setOnAutoCompleted(e -> {
            System.out.println("VALOR: " + e.getCompletion().getId());
            id_inusmo = e.getCompletion().getId();
            nombre_inusmo = e.getCompletion().getNombre();
        });
    }

    public void recibirDatos(Insumo insumoSeleccionado) {
        try {
            llenarbuscador();
            insumo = insumoSeleccionado;
        } catch (SQLException ex) {
            Logger.getLogger(KitsMedicosyConsumiblesCosinaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recibirDatosEditarVer(Insumo insumoSeleccionado) {
        try {
            insumo = insumoSeleccionado;
            kitmedicoycinsumiblecosinadao = new KitMedicoyConsumiblesCocinaDAO(conexion.conectar2());
//            KitMedicosyConsumiblesCocinasGuardado.addAll(kitmedicoycinsumiblecosinadao.obtenerTodosDelKitSeleccionado(insumoSeleccionado.getId()));
            llenarbuscador();
//            llenarTabla();
            editar = true;
            tabla.setVisible(false);
            tablaEditar.setVisible(true);
            tablaEditar.setDisable(false);
            btnEditar.setVisible(true);
            btnAgregar.setVisible(false);
            tabla.setDisable(false);
            btnAgregar.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(KitsMedicosyConsumiblesCosinaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() {
        colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));

        tabla.setItems(KitMedicosyConsumiblesCocinas);
    }

    @FXML
    private void accionEditar(ActionEvent event) {
        kitmedicoycinsumiblecosinadao = new KitMedicoyConsumiblesCocinaDAO(conexion.conectar2());
        try {
            for (KitMedicoyConsumiblesCocina kitmedcosina : KitMedicosyConsumiblesCocinasGuardado) {
                kitmedicoycinsumiblecosinadao.crear(kitmedcosina);
            }
            
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(KitsMedicosyConsumiblesCosinaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
