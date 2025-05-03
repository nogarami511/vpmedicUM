/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Consumo;
import clases_hospital.IndicaDetalle;
import clases_hospital.IndicaSuministroPacientes;
import clases_hospital.Indicasp;
import clases_hospital.Insumo;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.IndicaDetalleDAO;
import clases_hospital_DAO.IndicaSuministroPacientesDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class Ingresar_suministro_pacienteController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaSucces = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaWarring = new Alert(Alert.AlertType.WARNING);

    ObservableList<IndicaSuministroPacientes> indicasSuministrosPacientes = FXCollections.observableArrayList();

    InventariosDAO inventariosDAO;
    IndicaspDAO indicaspDAO;
    IndicaDetalleDAO indicasDetalleDAO;
    IndicaSuministroPacientesDAO indicasSuministroPacientesDAO;
    ConsumosDAO consumodao;
    InsumosDAO insumodao;
    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;

    Paciente pasiienteGuardado;

    boolean romper = false;
    int idIndicasp;

    private final ArrayList<RadioButton> radioButtons = new ArrayList<>();

    @FXML
    private Label lblNombre;
    @FXML
    private Button btnSuministrar;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<IndicaSuministroPacientes> tabla;
    @FXML
    private TableColumn<?, ?> conInsumo;
    @FXML
    private TableColumn colSuministrar;
    @FXML
    private Button btnSeleccionarTodo;
    @FXML
    private Button btnDeseleccionarTodo;
    @FXML
    private Button btnDeshacer;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void acciionSuministrar(ActionEvent event) {
        alertaWarring.setTitle("CONFIRMACION");
        alertaWarring.setHeaderText("¿ESTÁ SEGURO AGREGAR CONSUMO AL PACIENTE?");
        alertaWarring.setContentText("Esta accion no puede ser revertida");
        Optional<ButtonType> action = alertaWarring.showAndWait();
        if (action.get() == ButtonType.OK) {

//            canitdadDevolucion();
//            llenarSuministroDevolucion();
//            actualizarEstatusIndicaPadre();
            if (!romper) {
                alertaSucces.setTitle("CONFIRMACION");
                alertaSucces.setHeaderText("PROCESO EJECUTADO CORRECTAMENTE");
                alertaSucces.showAndWait();

                Stage stage = (Stage) btnSuministrar.getScene().getWindow();
                stage.close();
            }
        }

    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionSeleccionarTodo(ActionEvent event) {
        indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(conexion.conectar2());
        for (int i = 0; i < indicasSuministrosPacientes.size(); i++) {
            //radioButtons.get(i).setSelected(true);
            indicasSuministrosPacientes.get(i).setSuministro(true);
            indicasSuministrosPacientes.get(i).setDevolucion(false);
            indicasSuministrosPacientes.get(i).setUsuarioModificacion(VPMedicaPlaza.userSystem);
            indicasSuministroPacientesDAO.updateSuministro(indicasSuministrosPacientes.get(i));
        }
        tabla.refresh();
    }

    @FXML
    private void accionDeseleccionarTodo(ActionEvent event) {
        indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(conexion.conectar2());
        for (int i = 0; i < indicasSuministrosPacientes.size(); i++) {
            //radioButtons.get(i).setSelected(true);
            indicasSuministrosPacientes.get(i).setSuministro(false);
            indicasSuministrosPacientes.get(i).setDevolucion(true);
            indicasSuministrosPacientes.get(i).setUsuarioModificacion(VPMedicaPlaza.userSystem);
            indicasSuministroPacientesDAO.updateSuministro(indicasSuministrosPacientes.get(i));
        }
        tabla.refresh();
    }

    @FXML
    private void actionDeshacer(ActionEvent event) {
        alertaSucces.setHeaderText(null);
        alertaSucces.setTitle("Confirmación");
        alertaSucces.setContentText("¿Estas seguro de realizar esta accion?");
        alertaSucces.setContentText("En caso de equivocarse tendra que hacer nuevamente el proceso");
        Optional<ButtonType> action = alertaSucces.showAndWait();
        if (action.get() == ButtonType.OK) {
            con = conexion.conectar2();
            indicaspDAO = new IndicaspDAO(con);
            indicasDetalleDAO = new IndicaDetalleDAO(con);
            
            indicasDetalleDAO.updateDeshacerIndica(idIndicasp);
            
        }
    }

    public void setObjeto(Paciente paciente, int idIndicasp) {
        pasiienteGuardado = paciente;
        lblNombre.setText(paciente.getNombre());

        this.idIndicasp = idIndicasp;

        llenartabla();
    }

    public void setObjetoDeshacer(Paciente paciente, int idIndicasp) {
        btnDeshacer.setVisible(true);
        btnSeleccionarTodo.setVisible(false);
        btnDeseleccionarTodo.setVisible(false);
        tabla.setDisable(true);
        pasiienteGuardado = paciente;
        lblNombre.setText(paciente.getNombre());

        this.idIndicasp = idIndicasp;

        llenartabla();
    }

    private void llenartabla() {
        try {
            con = conexion.conectar2();
            indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(con);
            indicasSuministrosPacientes.addAll(indicasSuministroPacientesDAO.getAllIndicaSuministroPacientesesFromIndicaP(idIndicasp));

            conInsumo.setCellValueFactory(new PropertyValueFactory("nombre_insumo"));
            generarRadioButton();

            tabla.setItems(indicasSuministrosPacientes);

        } catch (SQLException ex) {
            Logger.getLogger(Ingresar_suministro_pacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarRadioButton() {
        Callback<TableColumn<IndicaSuministroPacientes, String>, TableCell<IndicaSuministroPacientes, String>> suministro = (TableColumn<IndicaSuministroPacientes, String> param) -> {
            final TableCell<IndicaSuministroPacientes, String> cell = new TableCell<IndicaSuministroPacientes, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton rdbCompra = new RadioButton();
                        radioButtons.add(rdbCompra);
                        IndicaSuministroPacientes ismp = getTableView().getItems().get(getIndex());
                        rdbCompra.setSelected(ismp.isSuministro());
                        rdbCompra.setOnAction(event -> {
                            if (ismp.isSuministro()) {
                                ismp.setSuministro(false);
                                ismp.setDevolucion(true);
                            } else {
                                ismp.setSuministro(true);
                                ismp.setDevolucion(false);
                            }
                            indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(conexion.conectar2());
                            ismp.setUsuarioModificacion(VPMedicaPlaza.userSystem);
                            indicasSuministroPacientesDAO.updateSuministro(ismp);
                        });
                        setGraphic(rdbCompra);
                        setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colSuministrar.setCellFactory(suministro);
    }

}
