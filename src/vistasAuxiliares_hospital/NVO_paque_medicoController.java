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
import clases_hospital.PaqueteMedico;
import clases_hospital.ServicioPaqueteMedico;
import clases_hospital.ServiciosPaquete;
import clases_hospital_DAO.ArmadoPaqueteMedicoDAO;
import clases_hospital_DAO.FamiliasInsumosDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.ServiciosPaqueteDAO;
import clases_hospital_DAO.ServiciosPaquetesMedicosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class NVO_paque_medicoController implements Initializable {

    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaWarning = new Alert(Alert.AlertType.WARNING);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    ObservableList<ArmadoPaqueteMedico> objetosArmadoPaqueteMedicos = FXCollections.observableArrayList();//para la tabla
    ObservableList<ServiciosPaquete> serviciosPaquetes = FXCollections.observableArrayList();//para la tabla
    ObservableList<ServicioPaqueteMedico> serviciosPaquetesMedicos = FXCollections.observableArrayList();//para la tabla
    List<Insumo> insumos;//para el autocompletado
    Conexion conexion = new Conexion();
    Connection con;

    private AutoCompletionBinding<FamiliaInsumos> autoCompletionFamilias;
    private AutoCompletionBinding<Insumo> autoCompletionInsumos;

    ServicioPaqueteMedico servicios;

    Insumo insumoSeleccionado;
    FamiliaInsumos familiaInsumos;
    ServiciosPaquete serviciopaquete;

    PaqueteMedico paqueteMedico;

    FamiliasInsumosDAO familiasInsumosDAO;
    InsumosDAO insumosDAO;
    ServiciosPaquetesMedicosDAO serviciospaquetemedicoDAO;

    PaqueteMedicoDAO paquetemedicoDAO;
    ArmadoPaqueteMedicoDAO armadoPaqueteMedicoDAO;
    ServiciosPaqueteDAO serviciospaqueteDAO;

    double precioHoraExtra;
    double SubtotalSF = 0;
    double subtotalCF = 0;
    double iva = 0;
    double total = 0;

    int id_paquete = 0;

    private final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnSalir;
    @FXML
    private Label txfSubtotalSF;
    @FXML
    private Label txfSubTotalCF;
    @FXML
    private Label txfSubtotalIva;
    @FXML
    private Label txfTotal;
    @FXML
    private RadioButton rdbPrecioManual;
    @FXML
    private RadioButton rdbPresioSugerido;
    @FXML
    private TextField txfPrecioManual;
    @FXML
    private TitledPane tpDatosPaquete;
    @FXML
    private TextField txfNombrePaquete;
    @FXML
    private TextField txfDescripcionPaqete;
    @FXML
    private TextField txfCalveSAT;
    @FXML
    private Button btnSiguienteDP;
    @FXML
    private TitledPane tpServiciosHospitalarios;
    @FXML
    private Button btnSigueinteSH;
    @FXML
    private TextField txfPaquete;
    @FXML
    private Button btnIngresarInsumos;
    @FXML
    private TextField txfInsumo;
    @FXML
    private TextField txfCantidad;
    @FXML
    private Button btnAgregar;
    @FXML
    private RadioButton rdbInusmos;
    @FXML
    private RadioButton rdbFamilia;
    @FXML
    private TableView<ArmadoPaqueteMedico> tabla;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TableColumn<?, ?> colCostoUnitario;
    @FXML
    private TableColumn<?, ?> colCostoPaqueteFormula;
    @FXML
    private Label txfSumatoriaConFormula;
    @FXML
    private Label txfSumatiraSformula;
    @FXML
    private Accordion acordion;
    @FXML
    private TitledPane tfCostos;
    @FXML
    private AnchorPane apTabla;
    @FXML
    private ComboBox<ServicioPaqueteMedico> cmbServicioPaquete;
    @FXML
    private Label lblCostoServicioPaquete;
    @FXML
    private TextField txfCantidadHorasExtras;
    @FXML
    private Label lblIHoraExtra;
    @FXML
    private TextField txfCantidadComidas;
    @FXML
    private Label lblICantidadComidas;
    @FXML
    private Button btnAgrgarTablaServicios;
    @FXML
    private TableView<ServiciosPaquete> tablaServicios;
    @FXML
    private TableColumn<?, ?> colServicio;
    @FXML
    private TableColumn<?, ?> colCosots;
    @FXML
    private Label lblSumatoriaTotalServicios;
    @FXML
    private Button btnCostoDePaquete;
    @FXML
    private Label txfTotalRedondeada;
    @FXML
    private Button btnEditar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tpDatosPaquete.setExpanded(true);
        llenarListaServicios();
        desactivarApane();
        borrarDatosTabla();
    }

    @FXML
    private void accionIngresar(ActionEvent event) {
        if (rdbPrecioManual.isSelected() || rdbPresioSugerido.isSelected()) {
            if (rdbPrecioManual.isSelected()) {
                if (txfPrecioManual.getText().equals("") || txfPrecioManual.getText().equals("0") || txfPrecioManual.getText().equals("0.0")) {
                    alertaWarning.setHeaderText("ADVERTENCIA");
                    alertaWarning.setContentText("LLENAR LOS CAMPOS ANTES DE CONTINUAR");
                    alertaWarning.showAndWait();
                } else {
                    insertarPaqueteMedico(Double.parseDouble(txfPrecioManual.getText()));

                    if (id_paquete > 0) {
                        System.out.println("ID PAQUETE: " + id_paquete);
                        ingresarArmadoPaquete();
                        ingrsarServiciosPaquetes();

                        alertaSuccess.setHeaderText("CONFIRMACION");
                        alertaSuccess.setContentText("PROCEDIMIENTO EJECUTAO CON EXITO");
                        alertaSuccess.showAndWait();

                        reportePaquete(id_paquete, Double.parseDouble(txfPaquete.getText()));

                        Stage stage = (Stage) btnIngresar.getScene().getWindow();
                        stage.close();
                    } else {
                        alertaError.setHeaderText("ADVERTENCIA");
                        alertaError.setContentText("HA OCURRIDO UN ERROR INESPERADO, INTENTELO NUEVAMENTE");
                        alertaError.showAndWait();
                    }
                }
            } else {
                insertarPaqueteMedico(redondeo());
                if (id_paquete > 0) {
                    System.out.println("ID PAQUETE: " + id_paquete);
                    ingresarArmadoPaquete();
                    ingrsarServiciosPaquetes();

                    alertaSuccess.setHeaderText("CONFIRMACION");
                    alertaSuccess.setContentText("PROCEDIMIENTO EJECUTAO CON EXITO");
                    alertaSuccess.showAndWait();

                    reportePaquete(id_paquete, Double.parseDouble(txfPaquete.getText()));

                    Stage stage = (Stage) btnIngresar.getScene().getWindow();
                    stage.close();
                } else {
                    alertaError.setHeaderText("ADVERTENCIA");
                    alertaError.setContentText("HA OCURRIDO UN ERROR INESPERADO, INTENTELO NUEVAMENTE");
                    alertaError.showAndWait();
                }
            }

        } else {
            alertaWarning.setHeaderText("ADVERTENCIA");
            alertaWarning.setContentText("SELECCIONE UNA OPCION PARA CONTINUAR");
            alertaWarning.showAndWait();
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionSiguienteDP(ActionEvent event) {
        if (txfNombrePaquete.getText().equals("") || txfDescripcionPaqete.getText().equals("") || txfCalveSAT.getText().equals("")) {
            alertaWarning.setHeaderText("ADVERTENCIA");
            alertaWarning.setContentText("LLENAR LOS CAMPOS ANTES DE CONTINUAR");
            alertaWarning.showAndWait();
        } else {
            tpDatosPaquete.setExpanded(false);
            tpServiciosHospitalarios.setExpanded(true);
            tfCostos.setExpanded(false);
        }
    }

    @FXML
    private void accionAgregarTablaServicio(ActionEvent event) {    // ES AQUI KRNAL
        if (servicios != null) {                                    // ES AQUI KRNAL
            ServiciosPaquete serviciopaq = new ServiciosPaquete();  // ES AQUI KRNAL
            serviciopaq.setId(0);                                   // ES AQUI KRNAL
            serviciopaq.setNombre(servicios.getNombre());           // ES AQUI KRNAL
            if (servicios.getId() == 8) {
                serviciopaq.setCosto(precioHoraExtra * Integer.parseInt(txfCantidadHorasExtras.getText()));
            } else {
                serviciopaq.setCosto(servicios.getCosto());
            }
            serviciopaq.setIdServiciosPaquetesMedicos(servicios.getId());
            if (servicios.getId() == 8) {
                serviciopaq.setHoraExtraQx(true);
                serviciopaq.setCantidadHoraExtraQx(Integer.parseInt(txfCantidadHorasExtras.getText()));
            } else {
                serviciopaq.setHoraExtraQx(false);
                serviciopaq.setCantidadHoraExtraQx(0);
            }
            if (servicios.getId() == 4 || servicios.getId() == 5) {
                serviciopaq.setHoraExtraQx(true);
                serviciopaq.setCantidadHoraExtraQx(Integer.parseInt(txfCantidadComidas.getText()));
            } else {
                serviciopaq.setHoraExtraQx(false);
                serviciopaq.setCantidadHoraExtraQx(0);
            }
            serviciosPaquetes.add(serviciopaq);

            colServicio.setCellValueFactory(new PropertyValueFactory("nombre"));
            colCosots.setCellValueFactory(new PropertyValueFactory("costo"));

            tablaServicios.setItems(serviciosPaquetes);

            lblSumatoriaTotalServicios.setText("$" + df.format(sumaTablaServicios()));

            lblIHoraExtra.setVisible(false);
            txfCantidadHorasExtras.setVisible(false);
            lblICantidadComidas.setVisible(false);
            txfCantidadComidas.setVisible(false);

            lblCostoServicioPaquete.setText("$00.00");
            cmbServicioPaquete.setValue(null);

            lblSumatoriaTotalServicios.setText("$" + df.format(sumaTablaServicios()));
        } else {
            alertaWarning.setHeaderText("ALERTA");
            alertaWarning.setContentText("SELECCIONE LOS VALORES NECESARIOS PARA EL PAQUETE");
            alertaWarning.showAndWait();
        }
    }

    @FXML
    private void accionHoraExtra(KeyEvent event) {
        servicios.setCosto(precioHoraExtra);
    }

    @FXML
    private void accionSiguienteSH(ActionEvent event) {
        if (tablaServicios.getItems().isEmpty()) {
            alertaWarning.setHeaderText("ALERTA");
            alertaWarning.setContentText("SELECCIONE LOS VALORES NECESARIOS PARA EL PAQUETE");
            alertaWarning.showAndWait();
        } else {
            // arreglar datos
            tpDatosPaquete.setExpanded(false);
            tpServiciosHospitalarios.setExpanded(false);
            tfCostos.setExpanded(true);
        }
    }

    @FXML
    private void accionIngresarInsumos(ActionEvent event) {
        if (txfPaquete.getText().equals("") || txfPaquete.getText().equals("0") || txfPaquete.getText().equals("0.0")) {
            alertaConfirmacion.setHeaderText("ADVERTENCIA");
            alertaConfirmacion.setContentText("LLENAR LOS CAMPOS ANTES DE CONTINUAR");
            Optional<ButtonType> accionCoonfirmar = alertaConfirmacion.showAndWait();
            if (accionCoonfirmar.get() != ButtonType.OK) {
                tpDatosPaquete.setExpanded(false);
                tpServiciosHospitalarios.setExpanded(false);
                tfCostos.setExpanded(false);
                apTabla.setDisable(false);

                SubtotalSF = sumaTablaServicios();
                subtotalCF = sumaTablaServicios();
                total = subtotalCF * 1.16;
                iva = total - subtotalCF;

                txfSubtotalSF.setText("$" + df.format(SubtotalSF));
                txfSubTotalCF.setText("$" + df.format(subtotalCF));
                txfSubtotalIva.setText("$" + df.format(iva));
                txfTotal.setText("$" + df.format(total));

                multiplicarTablaArmadoPaquete();
            }
        } else {
            tpDatosPaquete.setExpanded(false);
            tpServiciosHospitalarios.setExpanded(false);
            tfCostos.setExpanded(false);
            apTabla.setDisable(false);

            if (sumaSumaSinformulaTabla() == 0) {
                SubtotalSF = sumaTablaServicios();
                subtotalCF = sumaTablaServicios();
            } else {
                SubtotalSF = sumaTablaServicios() + sumaSumaSinformulaTabla();
                subtotalCF = sumaTablaServicios() + sumaSumaConformulaTabla();
            }

            total = subtotalCF * 1.16;
            iva = total - subtotalCF;

            txfSubtotalSF.setText("$" + df.format(SubtotalSF));
            txfSubTotalCF.setText("$" + df.format(subtotalCF));
            txfSubtotalIva.setText("$" + df.format(iva));
            txfTotal.setText("$" + df.format(total));

            multiplicarTablaArmadoPaquete();
        }
    }

    @FXML
    private void accionTfCostos(MouseEvent event) {
        apTabla.setDisable(true);
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        if (txfInsumo.getText().equals("") || txfCantidad.getText().equals("")) {
            alertaWarning.setHeaderText("ADVERTENCIA");
            alertaWarning.setContentText("LLENAR LOS CAMPOS ANTES DE CONTINUAR");
            alertaWarning.showAndWait();
        } else {
            alertaConfirmacion.setHeaderText("CONFIRMACION");
            alertaConfirmacion.setContentText("¿ESTA SEGURO DE  AGREGAR: " + txfInsumo.getText() + " AL PAQUETE?");
            Optional<ButtonType> accionCoonfirmar = alertaConfirmacion.showAndWait();
            if (accionCoonfirmar.get() == ButtonType.OK) {

                ArmadoPaqueteMedico armadoPaquete = new ArmadoPaqueteMedico();
                armadoPaquete.setId(0);
                double costo = 0;
                if (rdbFamilia.isSelected()) {
                    armadoPaquete.setIdInsumo(familiaInsumos.getIdFamiliaInsumo());
                    armadoPaquete.setNombreInsumo(familiaInsumos.getNombreFamiliaInsumo());
                    armadoPaquete.setCantidad(Double.parseDouble(txfCantidad.getText()));
                    costo = familiaInsumos.getCosto();
                } else {
                    armadoPaquete.setIdInsumo(insumoSeleccionado.getId());
                    armadoPaquete.setNombreInsumo(insumoSeleccionado.getNombre());
                    armadoPaquete.setCantidad(Double.parseDouble(txfCantidad.getText()));
                    costo = insumoSeleccionado.getCosto_compra_unitaria();
                }
                armadoPaquete.setFamilia(rdbFamilia.isSelected());
                double costoxcantidad = costo * (Double.parseDouble(txfCantidad.getText()));
                double costoconformula = costoxcantidad / (Double.parseDouble(txfPaquete.getText()) / 100);
                armadoPaquete.setCosto(costo);
                armadoPaquete.setCostoxcantidad(costoxcantidad);
                armadoPaquete.setCostoConFormula(costoconformula);
                System.out.println("COSTO: " + costo + " COSTO CON FORMULA: " + costoconformula);
                armadoPaquete.setsCosto("$" + df.format(costo));
                armadoPaquete.setsCostoConFormula("$" + df.format(costoconformula));

                objetosArmadoPaqueteMedicos.add(armadoPaquete);

                colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
                colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
                colCostoUnitario.setCellValueFactory(new PropertyValueFactory("sCosto"));
                colCostoPaqueteFormula.setCellValueFactory(new PropertyValueFactory("sCostoConFormula"));

                tabla.setItems(objetosArmadoPaqueteMedicos);

                bloquearTextFild();
                rdbFamilia.setSelected(false);
                rdbInusmos.setSelected(false);

                txfSumatiraSformula.setText("$" + df.format(sumaSumaSinformulaTabla()));
                txfSumatoriaConFormula.setText("$" + df.format(sumaSumaConformulaTabla()));

                txfTotalRedondeada.setText("$" + df.format(redondeo()));

                limpiarAutoCompletion();
            }
        }
    }

    @FXML
    private void accionInsumos(ActionEvent event) {
        llenarFamiliasInsumos();
        accionDesbloquaerTextFild();
    }

    @FXML
    private void accionFamilia(ActionEvent event) {
        llenarFamiliasMedicas();
        accionDesbloquaerTextFild();
    }

    @FXML
    private void accionCostoDePaquete(ActionEvent event) {
        if (tabla.getItems().isEmpty()) {
            alertaConfirmacion.setHeaderText("CONFIRMACION");
            alertaConfirmacion.setContentText("¿ESTA SEGURO DE CONTINUAR SIN INGRESAR INSUMOS?");
            Optional<ButtonType> accionCoonfirmar = alertaConfirmacion.showAndWait();
            if (accionCoonfirmar.get() == ButtonType.OK) {
                rdbPrecioManual.setDisable(false);
                rdbPresioSugerido.setDisable(false);

                SubtotalSF = sumaTablaServicios();
                subtotalCF = sumaTablaServicios();

                total = subtotalCF * 1.16;
                iva = total - subtotalCF;

                txfSubtotalSF.setText("$" + df.format(SubtotalSF));
                txfSubTotalCF.setText("$" + df.format(subtotalCF));
                txfSubtotalIva.setText("$" + df.format(iva));
                txfTotal.setText("$" + df.format(total));

                txfTotalRedondeada.setText("$" + df.format(redondeo()));
            }
        } else {
            rdbPrecioManual.setDisable(false);
            rdbPresioSugerido.setDisable(false);

            SubtotalSF = sumaTablaServicios() + sumaSumaSinformulaTabla();
            subtotalCF = sumaTablaServicios() + sumaSumaConformulaTabla();

            total = subtotalCF * 1.16;
            iva = total - subtotalCF;

            txfSubtotalSF.setText("$" + df.format(SubtotalSF));
            txfSubTotalCF.setText("$" + df.format(subtotalCF));
            txfSubtotalIva.setText("$" + df.format(iva));
            txfTotal.setText("$" + df.format(total));

            txfTotalRedondeada.setText("$" + df.format(redondeo()));
        }
    }

    @FXML
    private void accionPrecioManual(ActionEvent event) {
        txfPrecioManual.setDisable(false);
        btnIngresar.setDisable(false);
        rdbPresioSugerido.setDisable(false);
        if (btnEditar.isVisible()) {
            btnIngresar.setDisable(false);
            txfPrecioManual.setDisable(false);
            txfPrecioManual.setText("");
        }
    }

    @FXML
    private void accionPrecioSugerido(ActionEvent event) {
        txfPrecioManual.setDisable(true);
        txfPrecioManual.setText("");
        btnIngresar.setDisable(false);
        rdbPrecioManual.setDisable(false);
        if (btnEditar.isVisible()) {
            btnIngresar.setDisable(false);
            txfPrecioManual.setDisable(true);
            txfPrecioManual.setText("");
        }
    }

    private void llenarListaServicios() {
        try {
            con = conexion.conectar2();
            serviciospaquetemedicoDAO = new ServiciosPaquetesMedicosDAO(con);
            serviciosPaquetesMedicos.addAll(serviciospaquetemedicoDAO.obtenerTodos());
            cmbServicioPaquete.setItems(serviciosPaquetesMedicos);
            cmbServicioPaquete.setOnAction(e -> {
                servicios = cmbServicioPaquete.getValue();
                double costo = 0;
                if (cmbServicioPaquete.getValue() != null) {
                    if (servicios.getId() == 4 || servicios.getId() == 5 || servicios.getId() == 8) {
                        switch (servicios.getId()) {
                            case 4:
                                lblICantidadComidas.setVisible(true);
                                txfCantidadComidas.setVisible(true);
                                txfCantidadComidas.setText("0");
                                lblIHoraExtra.setVisible(false);
                                txfCantidadHorasExtras.setVisible(false);
                                costo = servicios.getCosto();
                                break;
                            case 5:
                                lblICantidadComidas.setVisible(true);
                                txfCantidadComidas.setVisible(true);
                                txfCantidadComidas.setText("0");
                                lblIHoraExtra.setVisible(false);
                                txfCantidadHorasExtras.setVisible(false);
                                costo = servicios.getCosto();
                                break;
                            case 8:
                                lblIHoraExtra.setVisible(true);
                                txfCantidadHorasExtras.setVisible(true);
                                txfCantidadHorasExtras.setText("0");
                                lblICantidadComidas.setVisible(false);
                                txfCantidadComidas.setVisible(false);

                                costo = 0;
                                precioHoraExtra = servicios.getCosto();
                                cambiarHoraExtra();
                                break;
                        }
                    } else {
                        lblIHoraExtra.setVisible(false);
                        txfCantidadHorasExtras.setVisible(false);
                        lblICantidadComidas.setVisible(false);
                        txfCantidadComidas.setVisible(false);
                        costo = servicios.getCosto();
                    }
                    lblCostoServicioPaquete.setText("$" + df.format(costo));
                }

            });
        } catch (SQLException ex) {
            Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double sumaServicios() {
        double suma = 0;
        for (int i = 0; i < serviciosPaquetes.size(); i++) {
            suma += serviciosPaquetes.get(i).getCosto();
        }
        return suma;
    }

    private void accionDesbloquaerTextFild() {
        txfInsumo.setDisable(false);
        txfCantidad.setDisable(false);
        btnAgregar.setDisable(false);
    }

    private void bloquearTextFild() {
        txfInsumo.setText("");
        txfCantidad.setText("");
        txfInsumo.setDisable(true);
        txfCantidad.setDisable(true);
        btnAgregar.setDisable(true);
    }

    private void llenarFamiliasMedicas() {
        con = conexion.conectar2();
        try {

            familiasInsumosDAO = new FamiliasInsumosDAO(con);

            autoCompletionFamilias = TextFields.bindAutoCompletion(txfInsumo, familiasInsumosDAO.obtenerFamiliasConDatosNecesarios());
            autoCompletionFamilias.setPrefWidth(1000);
            autoCompletionFamilias.setOnAutoCompleted(event -> {
                familiaInsumos = event.getCompletion();
            });
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarInsumoPaqueteMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarFamiliasInsumos() {
        con = conexion.conectar2();
        try {
            insumosDAO = new InsumosDAO(con);
            insumos = insumosDAO.optenerDatosInsumosMedicosConValoresIndispensables();
            autoCompletionInsumos = TextFields.bindAutoCompletion(txfInsumo, insumos);
            autoCompletionInsumos.setPrefWidth(1000);
            autoCompletionInsumos.setOnAutoCompleted(event -> {
                insumoSeleccionado = event.getCompletion();
            });
        } catch (SQLException ex) {
            Logger.getLogger(AgregarInsumoPaqueteMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cambiarHoraExtra() {
        txfCantidadHorasExtras.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) { // Si el nuevo valor está vacío
                txfCantidadHorasExtras.setText("0"); // Establece el valor en "0"
                lblCostoServicioPaquete.setText("$00.00");
            } else {
                int cantidad_horaExtra = Integer.parseInt(txfCantidadHorasExtras.getText());
                lblCostoServicioPaquete.setText("$" + servicios.getCosto() * cantidad_horaExtra);
            }
        });
    }

    private double sumaTablaServicios() {
        double suma = 0;
        for (int i = 0; i < tablaServicios.getItems().size(); i++) {
            suma += tablaServicios.getItems().get(i).getCosto();
        }
        return suma;
    }

    private void multiplicarTablaArmadoPaquete() {
        if (!tabla.getItems().isEmpty()) {
            for (int i = 0; i < objetosArmadoPaqueteMedicos.size(); i++) {
                objetosArmadoPaqueteMedicos.get(i).setCostoConFormula((objetosArmadoPaqueteMedicos.get(i).getCosto() * objetosArmadoPaqueteMedicos.get(i).getCantidad()) / (Double.parseDouble(txfPaquete.getText()) / 100));
                objetosArmadoPaqueteMedicos.get(i).setsCostoConFormula("$" + objetosArmadoPaqueteMedicos.get(i).getCostoConFormula());
            }
            tabla.refresh();
        }
    }

    private double sumaSumaSinformulaTabla() {
        double suma = 0;
        for (int i = 0; i < tabla.getItems().size(); i++) {
            suma += tabla.getItems().get(i).getCostoxcantidad();
        }
        return suma;
    }

    private double sumaSumaConformulaTabla() {
        double suma = 0;
        for (int i = 0; i < tabla.getItems().size(); i++) {
            suma += tabla.getItems().get(i).getCostoConFormula();
        }
        return suma;
    }

    // Método para limpiar los AutoCompletionBinding
    private void limpiarAutoCompletion() {
        if (autoCompletionFamilias != null) {
            autoCompletionFamilias.dispose();
            autoCompletionFamilias = null;
        }
        if (autoCompletionInsumos != null) {
            autoCompletionInsumos.dispose();
            autoCompletionInsumos = null;
        }
    }

    private void desactivarApane() {
        tfCostos.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            // Si el TitledPane se ha expandido, desactiva el botón
            if (isNowExpanded) {
                apTabla.setDisable(true);
            }
        });
    }

    private double redondeo() {
        double redondeo = 0;
        // Redondear el número al centenar más cercano
        int centenaRedondeada = (int) Math.round(total / 100) * 100;

        // Verificar si el número original está más cerca del centenar inferior o superior
        double residuo = total % 100;
        if (residuo <= 50) {
            redondeo = centenaRedondeada;
        } else {
            redondeo = centenaRedondeada + 100;
        }

        return redondeo;
    }

    private void insertarPaqueteMedico(double costopaquete) {
        con = conexion.conectar2();
        paquetemedicoDAO = new PaqueteMedicoDAO(con);

        paqueteMedico = new PaqueteMedico();

        paqueteMedico.setNombre(txfNombrePaquete.getText());
        paqueteMedico.setDescripcion(txfDescripcionPaqete.getText());
        paqueteMedico.setPrecio(costopaquete);
        paqueteMedico.setIdUsuario(VPMedicaPlaza.userSystem);
        paqueteMedico.setClaveSAT(txfCalveSAT.getText());
        paqueteMedico.setId_tipo_procedimiento(0);
        paqueteMedico.setId_quirofano(2);
        paqueteMedico.setId_tipo_habitacion(2);
        paqueteMedico.setDias_hospitalizacion(1);
        paqueteMedico.setNumero_comidas(3);
        paqueteMedico.setHoras_tolerancia(0);
        paqueteMedico.setHorasHospitalizacion(0);
        paqueteMedico.setFactor_paquete(Double.parseDouble(txfPaquete.getText()));

        try {
            id_paquete = paquetemedicoDAO.crearInt(paqueteMedico);
        } catch (SQLException ex) {
            Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ingresarArmadoPaquete() {
        con = conexion.conectar2();
        armadoPaqueteMedicoDAO = new ArmadoPaqueteMedicoDAO(con);

        for (int i = 0; i < tabla.getItems().size(); i++) {
            try {
                tabla.getItems().get(i).setIdPaquete(id_paquete);
                armadoPaqueteMedicoDAO.insertAll(tabla.getItems().get(i));
            } catch (SQLException ex) {
                Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void ingrsarServiciosPaquetes() {
        con = conexion.conectar2();
        serviciospaqueteDAO = new ServiciosPaqueteDAO(con);

        for (int i = 0; i < tablaServicios.getItems().size(); i++) {
            try {
                tablaServicios.getItems().get(i).setIdPaquete(id_paquete);
                serviciospaqueteDAO.insertar(tablaServicios.getItems().get(i));
            } catch (SQLException ex) {
                Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void borrarDatosTabla() {
        tabla.setRowFactory(tableView -> {
            TableRow<ArmadoPaqueteMedico> row = new TableRow<>();
            ContextMenu cxmCirugia = new ContextMenu();

            MenuItem descartarCirugia = new MenuItem("Descartar Insumo");
            descartarCirugia.setOnAction(event -> {
                ArmadoPaqueteMedico sercicios = row.getItem();
                objetosArmadoPaqueteMedicos.remove(sercicios);
            });
            cxmCirugia.getItems().add(descartarCirugia);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(cxmCirugia)
            );

            txfSumatiraSformula.setText("$" + df.format(sumaSumaSinformulaTabla()));
            txfSumatoriaConFormula.setText("$" + df.format(sumaSumaConformulaTabla()));
            return row;
        });
    }

    /**
     * =============================================================================
     * A PARTIR DE AQUI ESTARAN LOS METODOS PARA EDITAR PAQUETES
     * =============================================================================
     */
    @FXML
    private void accionEditar(ActionEvent event) {
        if (txfPaquete.getText().equals("") || txfPaquete.getText().equals("0") || txfPaquete.getText().equals("0.0")) {
            alertaWarning.setHeaderText("ADVERTENCIA");
            alertaWarning.setContentText("LLENAR LOS CAMPOS ANTES DE CONTINUAR (PESTAÑA FACTOR)");
            alertaWarning.showAndWait();
        } else {
            if (rdbPrecioManual.isSelected()) {
                if (txfPrecioManual.getText().equals("") || txfPrecioManual.getText().equals("0") || txfPrecioManual.getText().equals("0.0")) {
                    alertaWarning.setHeaderText("ADVERTENCIA");
                    alertaWarning.setContentText("LLENAR LOS CAMPOS ANTES DE CONTINUAR");
                    alertaWarning.showAndWait();
                } else {
                    editarDatosPaquete(Double.parseDouble(txfPrecioManual.getText()));
                    editarDatosServiciosPaquete();
                    editarDatosAramadoPaquete();

                    alertaSuccess.setHeaderText("CONFIRMACION");
                    alertaSuccess.setContentText("PROCEDIMIENTO EJECUTAO CON EXITO");
                    alertaSuccess.showAndWait();

                    reportePaquete(id_paquete, Double.parseDouble(txfPaquete.getText()));
                    
                    Stage stage = (Stage) btnIngresar.getScene().getWindow();
                    stage.close();
                }
            } else {
                editarDatosPaquete(redondeo());
                editarDatosServiciosPaquete();
                editarDatosAramadoPaquete();

                alertaSuccess.setHeaderText("CONFIRMACION");
                alertaSuccess.setContentText("PROCEDIMIENTO EJECUTAO CON EXITO");
                alertaSuccess.showAndWait();

                reportePaquete(id_paquete, Double.parseDouble(txfPaquete.getText()));

                Stage stage = (Stage) btnIngresar.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void setObjeto(PaqueteMedico paquetemedico) {
        btnIngresar.setVisible(false);
        btnIngresar.setDisable(true);
        btnEditar.setVisible(true);
        this.paqueteMedico = paquetemedico;
        this.id_paquete = paquetemedico.getId();
        llenarDatosPaquete();
        llenarTablaServicios();
        llenarTabla();
        borrarDatosTabla();
    }

    private void llenarDatosPaquete() {
        System.out.println("ID DEL PAQUETE: " + paqueteMedico.getId());
        txfNombrePaquete.setText(paqueteMedico.getNombre());
        txfDescripcionPaqete.setText(paqueteMedico.getDescripcion());
        txfCalveSAT.setText(paqueteMedico.getClaveSAT());
        txfPaquete.setText("" + paqueteMedico.getFactor_paquete());
    }

    private void llenarTablaServicios() {
        con = conexion.conectar2();
        serviciospaqueteDAO = new ServiciosPaqueteDAO(con);

        try {
            serviciosPaquetes.addAll(serviciospaqueteDAO.obtenerPorIdPaquete(paqueteMedico.getId()));

            colServicio.setCellValueFactory(new PropertyValueFactory("nombre"));
            colCosots.setCellValueFactory(new PropertyValueFactory("costo"));

            tablaServicios.setItems(serviciosPaquetes);

        } catch (SQLException ex) {
            Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() {
        try {
            con = conexion.conectar2();
            armadoPaqueteMedicoDAO = new ArmadoPaqueteMedicoDAO(con);

            objetosArmadoPaqueteMedicos.addAll(armadoPaqueteMedicoDAO.aramdoPaqueteMedicosDatos(paqueteMedico.getId()));

            colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
            colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
            colCostoUnitario.setCellValueFactory(new PropertyValueFactory("sCosto"));
            colCostoPaqueteFormula.setCellValueFactory(new PropertyValueFactory("sCostoConFormula"));

            tabla.setItems(objetosArmadoPaqueteMedicos);
        } catch (SQLException ex) {
            Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editarDatosPaquete(double costopaquete) {
        con = conexion.conectar2();
        paquetemedicoDAO = new PaqueteMedicoDAO(con);

        paqueteMedico = new PaqueteMedico();

        paqueteMedico.setId(id_paquete);
        paqueteMedico.setNombre(txfNombrePaquete.getText());
        paqueteMedico.setDescripcion(txfDescripcionPaqete.getText());
        paqueteMedico.setPrecio(costopaquete);
        paqueteMedico.setIdUsuario(VPMedicaPlaza.userSystem);
        paqueteMedico.setClaveSAT(txfCalveSAT.getText());
        paqueteMedico.setId_tipo_procedimiento(0);
        paqueteMedico.setId_quirofano(2);
        paqueteMedico.setId_tipo_habitacion(2);
        paqueteMedico.setDias_hospitalizacion(1);
        paqueteMedico.setNumero_comidas(3);
        paqueteMedico.setHoras_tolerancia(0);
        paqueteMedico.setHorasHospitalizacion(0);
        paqueteMedico.setFactor_paquete(Double.parseDouble(txfPaquete.getText()));

        try {
            paquetemedicoDAO.actualizar(paqueteMedico);
        } catch (SQLException ex) {
            Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editarDatosServiciosPaquete() {
        con = conexion.conectar2();
        serviciospaqueteDAO = new ServiciosPaqueteDAO(con);

        for (int i = 0; i < tablaServicios.getItems().size(); i++) {
            if (serviciosPaquetes.get(i).getId() == 0) {
                try {
                    serviciosPaquetes.get(i).setIdPaquete(id_paquete);
                    serviciospaqueteDAO.insertar(serviciosPaquetes.get(i));
                } catch (SQLException ex) {
                    Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void editarDatosAramadoPaquete() {
        con = conexion.conectar2();
        armadoPaqueteMedicoDAO = new ArmadoPaqueteMedicoDAO(con);

        for (int i = 0; i < tabla.getItems().size(); i++) {
            System.out.println("ID DEL PAQUETE: " + objetosArmadoPaqueteMedicos.get(i).getIdPaquete() + " ID DEL PAQUETE GUARDADO: " + id_paquete);
            System.out.println("DENTRO DEL FOR-" + objetosArmadoPaqueteMedicos.get(i).getId() + "<--");
            if (objetosArmadoPaqueteMedicos.get(i).getId() == 0) {
                System.out.println("DENTRO DEL IF -" + objetosArmadoPaqueteMedicos.get(i).getId() + "<--");
                try {
                    objetosArmadoPaqueteMedicos.get(i).setIdPaquete(id_paquete);
                    armadoPaqueteMedicoDAO.insert(objetosArmadoPaqueteMedicos.get(i));
                } catch (SQLException ex) {
                    Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * =============================================================================
     * A PARTIR DE AQUI ESTARAN LOS METODOS PARA EDITAR PAQUETES MODIFICADOS
     * =============================================================================
     *
     * @param paquetemedico
     */
    public void setObjetoModificado(PaqueteMedico paquetemedico) {
        btnIngresar.setVisible(false);
        btnIngresar.setDisable(true);
        btnEditar.setVisible(true);
        this.paqueteMedico = paquetemedico;
        this.id_paquete = paquetemedico.getId();
        llenarDatosPaquete();
        llenarTablaServicios();
        llenarTablaModificada();
        borrarDatosTabla();
    }

    private void llenarTablaModificada() {
        try {
            con = conexion.conectar2();
            armadoPaqueteMedicoDAO = new ArmadoPaqueteMedicoDAO(con);

            objetosArmadoPaqueteMedicos.addAll(armadoPaqueteMedicoDAO.aramdoPaqueteMedicosDatosCambio(paqueteMedico.getId()));

            colInsumo.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
            colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
            colCostoUnitario.setCellValueFactory(new PropertyValueFactory("sCosto"));
            colCostoPaqueteFormula.setCellValueFactory(new PropertyValueFactory("sCostoConFormula"));

            pintarTabla();
            tabla.setItems(objetosArmadoPaqueteMedicos);
        } catch (SQLException ex) {
            Logger.getLogger(NVO_paque_medicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pintarTabla() {
        tabla.setRowFactory(tv -> new TableRow<ArmadoPaqueteMedico>() {
            @Override
            public void updateItem(ArmadoPaqueteMedico item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getModificado() == 1) {
                    setStyle("-fx-background-color: #FF5733; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
                } else {
                    setStyle(" ");
                }

            }
        });
    }

    private void reportePaquete(int idPaquete, double factorPaquete) {
        ReporteC reportec = new ReporteC("Blank_A4");
        reportec.generarReportePaquetes(idPaquete, factorPaquete);
    }

}
