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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class GeneararIndica2Controller implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaSucces = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaWarring = new Alert(Alert.AlertType.WARNING);
    ObservableList<IndicaDetalle> indicasDetalles = FXCollections.observableArrayList();
    ObservableList<Area> areas = FXCollections.observableArrayList();
    ObservableList<InventarioDetalle> lotesOB = FXCollections.observableArrayList();

    List<ConfiguracionPaquete> configuracionPaquete;
    List<Consumo> consumos;

    List<ConsumosLista> consumosListaas = new ArrayList<>();
    List<ConfiguracionPaqueteLista> configuracionPaqueteListas = new ArrayList<>();
    List<ArmadoPaqueteMedicoLista> armadoPaqueteMedicosListas = new ArrayList<>();
    List<IndicaDetalleLista> indicaDetalleListas = new ArrayList<>();
    List<InventarioDetalle> listalotes = new ArrayList<>();

    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;

    MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();
    Paciente pasiienteGuardado;
    IndicaDetalle indicasdetalle;
    Area area;
    InventarioDetalleDAO inventarioDetalleDAO;
    InventariosDAO inventariosDAO;
    IndicaspDAO indicaspDAO;
    IndicaDetalleDAO indicasDetalleDAO;
    IndicaSuministroPacientesDAO indicasSuministroPacientesDAO;
    AreaDAO areaDAO;
    FoliosDAO foliosDAO;
    PaqueteMedicoDAO paquetemedicoDAO;
    ArmadoPaqueteMedicoDAO armadoPaqueteMedicoDAO;
    ConfiguracionPaqueteDAO configuracionPaqueteDAO;
    ConsumosDAO consumosDAO;
    FamiliasInsumosDAO familiasInsumosDAO;
    InsumosDAO insumosDAO;

    int idInsumo;
    int idFamilia;
    int id_indicasp;
    int id_paquete;
    double cantidadInsumoConsumo;
    double cantidadInsumoPaquete;
    double cantidadEnPaquete;
    double servMyE = 0;
    double costoPaquete = 0;
    double sumaInsumosPaqueteHemodinamia = 0;
    boolean isHemodinamia = false;

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
    @FXML
    private RadioButton rdbPaquete;
    @FXML
    private Label lblDisponiblePaquete;
    @FXML
    private Label lblDisponible;
    @FXML
    private Label lblPquete;
    @FXML
    private ComboBox<InventarioDetalle> cmbLote;
    @FXML
    private Label lblLote;

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
    private void accionGenerar(ActionEvent event) throws SQLException {
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
                insumosDAO = new InsumosDAO(con);
                consumosDAO = new ConsumosDAO(con);
                Inventario inventario = inventariosDAO.obtenerDatosPorIdInsumoConPrecios(idInsumo);

                if (cmbLote.getValue().getCantidad() >= Double.valueOf(txfCantidad.getText())) {
                    IndicaDetalle indicaDetalle = new IndicaDetalle();

                    if (isHemodinamia) {

                        double sumaData = consumosDAO.sumatoriaTotalPaquete(pasiienteGuardado.getIdfolio());
                        double sumaTabla = inventario.getPrecioUnitario() * Double.valueOf(txfCantidad.getText());
                        for (int i = 0; i < tabla.getItems().size(); i++) {

                            sumaTabla += (tabla.getItems().get(i).getPrecioUnitario() * Double.valueOf(txfCantidad.getText()));

                        }

                        sumaInsumosPaqueteHemodinamia += sumaData + sumaTabla + (inventario.getPrecioUnitario() * Double.valueOf(txfCantidad.getText()));

                        double suma = servMyE + sumaInsumosPaqueteHemodinamia;

                        indicaDetalle.setIdInsumo(inventario.getId_insumo());
                        indicaDetalle.setCantidadEntregada(Double.valueOf(txfCantidad.getText()));
                        indicaDetalle.setCantidadDevolucion(0.0);
                        indicaDetalle.setIdUsuarioCreacion(VPMedicaPlaza.userSystem);
                        indicaDetalle.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                        indicaDetalle.setNombreInsumo(txfInsumo.getText());
                        indicaDetalle.setLote(cmbLote.getValue().getLote());
                        if (suma < costoPaquete) {

                            indicaDetalle.setPaquete(true);
                        } else {
                            indicaDetalle.setPaquete(false);
                            sumaInsumosPaqueteHemodinamia -= (inventario.getPrecioUnitario() * Double.valueOf(txfCantidad.getText()));

                        }
                        indicaDetalle.setPrecioUnitario(inventario.getPrecioUnitario());
                        indicaDetalle.setPrecioUnitarioPaquete(inventario.getPrecioUnitarioPaquete());

                        indicaDetalle.setIdFamilia(idFamilia);

                    } else {

                        if (cantidadInsumoConsumo < Double.valueOf(txfCantidad.getText()) && cantidadInsumoConsumo > 0) {
                            
                            double cantidadInsumo = Double.valueOf(txfCantidad.getText()) - cantidadInsumoConsumo;
                            System.out.println("cantidadInsumo Es: " + cantidadInsumo + " Txf Es: " + txfCantidad.getText() + " cantidad insumo consumido es: "+ cantidadInsumoConsumo);
                            indicaDetalle.setIdInsumo(inventario.getId_insumo());
                            indicaDetalle.setCantidadEntregada(cantidadInsumoConsumo);
                            indicaDetalle.setCantidadDevolucion(0.0);
                            indicaDetalle.setIdUsuarioCreacion(VPMedicaPlaza.userSystem);
                            indicaDetalle.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                            indicaDetalle.setNombreInsumo(txfInsumo.getText());
                            indicaDetalle.setPaquete(true);
                            indicaDetalle.setPrecioUnitario(inventario.getPrecioUnitario());
                            indicaDetalle.setPrecioUnitarioPaquete(inventario.getPrecioUnitarioPaquete());
                            indicaDetalle.setLote(cmbLote.getValue().getLote());

                            indicaDetalle.setIdFamilia(idFamilia);
                            
                            IndicaDetalle indicaDetalle2 = new IndicaDetalle();
                            indicaDetalle2.setIdInsumo(inventario.getId_insumo());
                            indicaDetalle2.setCantidadEntregada(cantidadInsumo);
                            indicaDetalle2.setCantidadDevolucion(0.0);
                            indicaDetalle2.setIdUsuarioCreacion(VPMedicaPlaza.userSystem);
                            indicaDetalle2.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                            indicaDetalle2.setNombreInsumo(txfInsumo.getText());
                            indicaDetalle2.setPaquete(rdbPaquete.isSelected());
                            indicaDetalle2.setPrecioUnitario(inventario.getPrecioUnitario());
                            indicaDetalle2.setPrecioUnitarioPaquete(inventario.getPrecioUnitarioPaquete());
                            indicaDetalle2.setLote(cmbLote.getValue().getLote());

                            indicaDetalle2.setIdFamilia(idFamilia);
                            llenarTabla(indicaDetalle2);
                        } else {
                            indicaDetalle.setIdInsumo(inventario.getId_insumo());
                            indicaDetalle.setCantidadEntregada(Double.valueOf(txfCantidad.getText()));
                            indicaDetalle.setCantidadDevolucion(0.0);
                            indicaDetalle.setIdUsuarioCreacion(VPMedicaPlaza.userSystem);
                            indicaDetalle.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                            indicaDetalle.setNombreInsumo(txfInsumo.getText());
                            indicaDetalle.setPaquete(rdbPaquete.isSelected());
                            indicaDetalle.setPrecioUnitario(inventario.getPrecioUnitario());
                            indicaDetalle.setPrecioUnitarioPaquete(inventario.getPrecioUnitarioPaquete());
                            indicaDetalle.setLote(cmbLote.getValue().getLote());

                            indicaDetalle.setIdFamilia(idFamilia);
                        }

                    }

                    llenarTabla(indicaDetalle);

                    idFamilia = 0;
                    idInsumo = 0;

                    txfCantidad.setText("");
                    txfInsumo.setText("");
                    rdbPaquete.setSelected(false);
                    rdbPaquete.setDisable(true);
                    lblLote.setText("");
                    cmbLote.getItems().clear();

                    cantidadInsumoConsumo = 0;

                    lblDisponible.setVisible(false);
                    lblDisponiblePaquete.setVisible(false);
                } else {
                    alertaError.setTitle("ERROR!");
                    alertaError.setHeaderText("PRODUCTO INSUFICIENTE");
                    alertaError.setContentText("EL PRODUCTO SOLO CUENTA CON\n" + "\"" + cmbLote.getValue().getCantidad() + "\"" + "\nNUMERO DE PIEZAS");
                    alertaError.showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void accionPaquete(ActionEvent event) {
    }

    @FXML
    private void accionLimitarPaquete(KeyEvent event) {
        if (Double.parseDouble(txfCantidad.getText()) <= cantidadInsumoConsumo) {
            rdbPaquete.setDisable(true);
            rdbPaquete.setSelected(true);
        } else {
            rdbPaquete.setDisable(true);
            rdbPaquete.setSelected(false);
        }
        //System.out.println(rdbPaquete.isSelected());
    }

    public void setObjeto(Paciente paciente) {
        try {
            pasiienteGuardado = paciente;
            lblNombrePaciente.setText(paciente.getNombre());
            llenarBuscadorInsumo();

            con = conexion.conectar2();
            foliosDAO = new FoliosDAO(con);
            paquetemedicoDAO = new PaqueteMedicoDAO(con);

                lblPquete.setText("TIPO DE CUENTA: " + paquetemedicoDAO.leer(foliosDAO.obtenerFolioPorId(paciente.getIdfolio()).getId_paquete()).getNombre());
            rdbPaquete.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarArea() {
        try {
            areaDAO = new AreaDAO(conexion.conectar2());
            areas.addAll(areaDAO.getAll());
            cmbArea.setItems(areas);
        } catch (SQLException ex) {
            Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarBuscadorInsumo() {
        try {
            con = conexion.conectar2();
            inventariosDAO = new InventariosDAO(con);
            foliosDAO = new FoliosDAO(con);

            List<Inventario> inventarios = inventariosDAO.otenerDatosBusquedaConFamilia(1);
            AutoCompletionBinding<Inventario> nombres = TextFields.bindAutoCompletion(txfInsumo, inventarios);
            nombres.setPrefWidth(1000);
            nombres.setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<Inventario> event) -> {
                try {
                    inventarioDetalleDAO = new InventarioDetalleDAO(con);

                    Inventario insumoSeleccionado = event.getCompletion();
                    idInsumo = insumoSeleccionado.getId_insumo();

                    idFamilia = insumoSeleccionado.getIdFamilia();

                    Folio folio = foliosDAO.obtenerFolio(pasiienteGuardado.getIdfolio());

                    //obtenerFolioHemodinamia
                    cantidadInsumoConsumo = 0;

                    if (foliosDAO.obtenerFolioHemodinamia(folio.getFolio()).getFolio() == null) {

                        isHemodinamia = false;
                        comparacionPaquete();
                    } else {

                        id_paquete = folio.getId_paquete();

                        isHemodinamia = true;
                        compararPaqueteHemodinamia();
                    }
                    listalotes = inventarioDetalleDAO.traerlotes(idInsumo);
                    List<InventarioDetalle> lotesConCantidadMayorQueCero = listalotes.stream()
                            .filter(lote -> lote.getCantidad() > 0)
                            .collect(Collectors.toList());
                    lotesOB.addAll(lotesConCantidadMayorQueCero);
                    for (int i = 0; i < indicasDetalles.size(); i++) {
                        if (indicasDetalles.get(i).getIdInsumo() == idInsumo) {
                            for (int j = 0; j < lotesOB.size(); j++) {
                                if (lotesOB.get(j).getLote().equals(indicasDetalles.get(i).getLote())) {
                                    int cant_in = lotesOB.get(j).getCantidad();
                                    double cant_entreg = indicasDetalles.get(i).getCantidadEntregada();
                                    double resta = cant_in - cant_entreg;
                                    lotesOB.get(j).setCantidad((int) resta);
                                }
                            }
                        }
                    }
                    cmbLote.setItems(lotesOB);

                } catch (SQLException ex) {
                    Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionSeleccionarLote(ActionEvent event) {
        if (cmbLote.getValue() != null) {
            // Obtén la cantidad del lote seleccionado y establece el texto en el Label
            lblLote.setText(String.valueOf(cmbLote.getValue().getCantidad()));
        } else {
            // Maneja el caso en que no hay ningún elemento seleccionado
            lblLote.setText("0.0"); // O cualquier otro valor predeterminado
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
                Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
            }

            tabla.refresh();
        });

        colCantidad.setEditable(true);
        tabla.setEditable(true);
    }

    private void generarIndicasPeIndicasDetalle() throws SQLException {
        alertaSucces.setHeaderText("EN PROCESO");
        alertaSucces.setTitle("EN PROCESO");
        alertaSucces.setContentText("SE ESTÁ GENERANDO, POR FAVOR ESPERE");
        alertaSucces.show(); // No esperes a que se cierre con showAndWait()
        con = conexion.conectar2();
        indicaspDAO = new IndicaspDAO(con);
        indicasDetalleDAO = new IndicaDetalleDAO(con);
        indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(con);
        movimientodetalledao = new MovimientoDetalleDAO(conexion.conectar2());
        movimientopadredao = new MovimientoPadreDAO(conexion.conectar2());
        int id_mov_inP;

        Indicasp indicap = new Indicasp();
        indicap.setIdPaciente(pasiienteGuardado.getIdPaciente());
        indicap.setIdFolio(pasiienteGuardado.getIdfolio());
        indicap.setIdUsuarioEntrega(VPMedicaPlaza.userSystem);
        indicap.setIdUsuarioSolicitud(0);
        indicap.setEstatusIndica(0);
        indicap.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
        indicap.setId_area(area.getIdArea());

        id_indicasp = indicaspDAO.create(indicap);

        movimientoInventarioP.setTipo_mov(8);
        movimientoInventarioP.setId_origen(1);
        movimientoInventarioP.setId_destino(area.getIdArea());
        movimientoInventarioP.setId_proveedor(userSystem);
        movimientoInventarioP.setFolio_mov("id_ip " + id_indicasp);
        movimientoInventarioP.setSubtotal(0);
        movimientoInventarioP.setDescuento(0.0);
        movimientoInventarioP.setImporte_impuesto(0);
        movimientoInventarioP.setTotal(0);
        movimientoInventarioP.setEstatus_movimiento(1);
        movimientoInventarioP.setObservaciones("");
        movimientoInventarioP.setUsuario_registro(userSystem);

        id_mov_inP = movimientopadredao.agregarMovimientoInventarioPINT(movimientoInventarioP);

        for (int i = 0; i < indicasDetalles.size(); i++) {
            indicasDetalles.get(i).setIdIndicasp(id_indicasp);
            int id_indica_detalle = indicasDetalleDAO.CREARINDICA_CON_LOTE(indicasDetalles.get(i), id_mov_inP);
            System.out.println("-" + id_indica_detalle + "\n");

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
                indicasuministropaciente.setPrecioUnitario(indicasDetalles.get(i).getPrecioUnitario());
                indicasuministropaciente.setPrecioUnitarioPaquete(indicasDetalles.get(i).getPrecioUnitarioPaquete());

                indicasSuministroPacientesDAO.create(indicasuministropaciente);
            }

        }

        alertaSucces.close();
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

        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().isEmpty()) {
                change.setText("0");
                change.setCaretPosition(1); // Coloca el cursor al final
            }
            return change;
        });

        txfCantidad.setTextFormatter(textFormatter);
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

    private void comparacionPaquete() {
        con = conexion.conectar2();
        foliosDAO = new FoliosDAO(con);

        cantidadInsumoPaquete = 0;

        try {
            Folio folio = foliosDAO.obtenerFolio(pasiienteGuardado.getIdfolio());
            if (folio.getId_paquete() > 1) {
                configuracionPaqueteDAO = new ConfiguracionPaqueteDAO(con);
                consumosDAO = new ConsumosDAO(con);
                configuracionPaqueteListas = configuracionPaqueteDAO.configuracionPaqueteListas(folio.getId());
                consumosListaas.addAll(consumosDAO.consmoLista(folio.getId()));
                if (!configuracionPaqueteListas.isEmpty()) {

                    indicasDetalleDAO = new IndicaDetalleDAO(con);
                    indicaDetalleListas.addAll(indicasDetalleDAO.getIndicaDetallesLista(folio.getId()));

                    if (indicasDetalles.isEmpty()) {//AQUI NOS QUEDAMSO
                        if (indicaDetalleListas.isEmpty()) {
                            comapararListas();
                        } else {
                            comapararListas();
                            for (int i = 0; i < indicaDetalleListas.size(); i++) {
                                if (indicaDetalleListas.get(i).getIdInsumo() == idInsumo) {
                                    cantidadInsumoConsumo -= indicaDetalleListas.get(i).getCantidadInsumo();
                                }
                            }
                        }
                    } else {
                        comapararListas();
                        for (int i = 0; i < indicasDetalles.size(); i++) {
                            if ((indicasDetalles.get(i).getIdInsumo() == idInsumo) && (indicasDetalles.get(i).isPaquete())) {
                                cantidadInsumoConsumo -= indicasDetalles.get(i).getCantidadEntregada();
                            }
                        }
                    }
                    lblDisponible.setText(cantidadInsumoConsumo + " DE " + cantidadEnPaquete);
                    activarDesactivarRdb();
                } else {
                    armadoPaqueteMedicoDAO = new ArmadoPaqueteMedicoDAO(con);
                    armadoPaqueteMedicosListas.addAll(armadoPaqueteMedicoDAO.aramdoPaqueteMedicoListas(folio.getId_paquete()));

                    if (armadoPaqueteMedicosListas.isEmpty()) {

                    } else {
                        indicasDetalleDAO = new IndicaDetalleDAO(con);
                        indicaDetalleListas.addAll(indicasDetalleDAO.getIndicaDetallesLista(folio.getId()));

                        if (indicasDetalles.isEmpty()) {//AQUI NOS QUEDAMSO
                            if (indicaDetalleListas.isEmpty()) {

                                compararListasArmadoPaquete();
                            } else {
                                compararListasArmadoPaquete();
                                for (int i = 0; i < indicaDetalleListas.size(); i++) {
                                    if (indicaDetalleListas.get(i).getIdInsumo() == idInsumo) {
                                        cantidadInsumoConsumo -= indicaDetalleListas.get(i).getCantidadInsumo();
                                    }
                                }
                            }
                        } else {
                            compararListasArmadoPaquete();
                            for (int i = 0; i < indicasDetalles.size(); i++) {
                                if ((indicasDetalles.get(i).getIdInsumo() == idInsumo) && (indicasDetalles.get(i).isPaquete())) {
                                    cantidadInsumoConsumo -= indicasDetalles.get(i).getCantidadEntregada();
                                }
                            }
                        }
                    }
                    lblDisponible.setText(cantidadInsumoConsumo + " DE " + cantidadEnPaquete);
                    activarDesactivarRdb();
                }

                tabla.refresh();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void compararPaqueteHemodinamia() {
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        ///////////////////////////////////////////////////
        con = conexion.conectar2();
        paquetemedicoDAO = new PaqueteMedicoDAO(con);
        insumosDAO = new InsumosDAO(con);
        CostoHabitacionDAO habitacionDAO = new CostoHabitacionDAO(con);

        double enfermera;
        double medico;
        double habitacion;
        try {
            PaqueteMedico paquete = paquetemedicoDAO.leer(id_paquete);
            enfermera = insumosDAO.optenerDatosInsumos(617).getPrecio_venta_unitaria();
            medico = insumosDAO.optenerDatosInsumos(616).getPrecio_venta_unitaria();
            habitacion = habitacionDAO.traerTodoPorID(paquete.getId_tipo_habitacion()).getPrecio();
            costoPaquete = paquete.getPrecio();
            servMyE = ((enfermera * paquete.getDias_hospitalizacion()) + (medico * paquete.getDias_hospitalizacion()) + (habitacion * paquete.getDias_hospitalizacion()) + sumaInsumosPaqueteHemodinamia);
            double resta = costoPaquete - servMyE;

            if (servMyE <= costoPaquete) {
                rdbPaquete.setSelected(true);
            } else {
                rdbPaquete.setSelected(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void comapararListas() {
        for (int i = 0; i < configuracionPaqueteListas.size(); i++) {
            if (configuracionPaqueteListas.get(i).getIdInusmo() == idInsumo) {
                if (consumosListaas.isEmpty() && cantidadInsumoConsumo == 0) {
                    cantidadInsumoConsumo = configuracionPaqueteListas.get(i).getCantidadInsumo();
                    cantidadEnPaquete = cantidadInsumoConsumo;
                } else {
                    for (int j = 0; j < consumosListaas.size(); j++) {
                        if (consumosListaas.get(j).getIdInsumo() == idInsumo) {
                            cantidadInsumoConsumo = configuracionPaqueteListas.get(i).getCantidadInsumo();

                            cantidadInsumoConsumo = configuracionPaqueteListas.get(i).getCantidadInsumo() - consumosListaas.get(j).getCantidadInsumo();
                        }
                    }
                }
            }
        }
    }

    private void compararListasArmadoPaquete() {
        try {
            con = conexion.conectar2();
            familiasInsumosDAO = new FamiliasInsumosDAO(con);
            insumosDAO = new InsumosDAO(con);
            List<FamiliaInsumos> familiaInsumos = familiasInsumosDAO.obtenerTodasFamiliasInsumos();
            List<Insumo> insumos;

            for (int i = 0; i < armadoPaqueteMedicosListas.size(); i++) {

                if (armadoPaqueteMedicosListas.get(i).isFamilia() && armadoPaqueteMedicosListas.get(i).getIdInsumo() == idFamilia) {
                    if (consumosListaas.isEmpty() && cantidadInsumoConsumo == 0) {
                        cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo();
                        cantidadEnPaquete = cantidadInsumoConsumo;
                    } else {
                        for (int j = 0; j < consumosListaas.size(); j++) {
                            if (consumosListaas.get(j).getIdInsumo() == idFamilia) {
                                cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo();

                                cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo() - consumosListaas.get(j).getCantidadInsumo();
                            }
                        }
                    }
                } else {
                    if (armadoPaqueteMedicosListas.get(i).getIdInsumo() == idInsumo) {
                        //   System.out.println("FAMILIA: " + armadoPaqueteMedicosListas.get(i).isFamilia() + "ID FAMILIA: " + idFamilia + " ARMADO: " + armadoPaqueteMedicosListas.get(i).getIdInsumo() + " INSUMO: " + idInsumo + " cantidad insumo consumo: " + cantidadInsumoConsumo);
                        if (consumosListaas.isEmpty() || cantidadInsumoConsumo == 0.0) {
                            //   System.out.println("es vacia y cantidad insumo a 0");
                            cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo();
                            cantidadEnPaquete = cantidadInsumoConsumo;
                            //    System.out.println("Cantidad en paquete: " + cantidadEnPaquete);
                        } else if (armadoPaqueteMedicosListas.get(i).isFamilia()) {
                            //   System.out.println("ARMADO PAQUETE MEDICO FAMILIA");
                        } else if (armadoPaqueteMedicosListas.get(i).getCantidadInsumo() > 1) {
                            // System.out.println("FAMILIA: " + armadoPaqueteMedicosListas.get(i).isFamilia() + "ID FAMILIA: " + idFamilia + " ARMADO: " + armadoPaqueteMedicosListas.get(i).getIdInsumo() + " INSUMO: " + idInsumo);
                            for (int j = 0; j < consumosListaas.size(); j++) {
                                //     System.out.println(j);
                                if (consumosListaas.get(j).getIdInsumo() == idInsumo) {
                                    cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo();
                                    //  System.out.println(cantidadInsumoConsumo);

                                    cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo() - consumosListaas.get(j).getCantidadInsumo();
                                    //    System.out.println(cantidadInsumoConsumo);
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////
                                }
                            }
                        }
                    }
                }
            }
//        try {
//            con = conexion.conectar2();
//            familiasInsumosDAO = new FamiliasInsumosDAO(con);
//            insumosDAO = new InsumosDAO(con);
//            List<FamiliaInsumos> familiaInsumos = familiasInsumosDAO.obtenerTodasFamiliasInsumos();
//            List<Insumo> insumos;
//
//            for (int i = 0; i < armadoPaqueteMedicosListas.size(); i++) {
//             
//              
//                if (armadoPaqueteMedicosListas.get(i).getIdInsumo() == idFamilia) {
//                  
//                    if (consumosListaas.isEmpty() && cantidadInsumoConsumo == 0) {
//                        for (FamiliaInsumos familiaInsumo : familiaInsumos) {
//                           
//                            if (idFamilia == familiaInsumo.getIdFamiliaInsumo()) {
//                                cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo();
//                                cantidadEnPaquete = cantidadInsumoConsumo;
//                            }
//                        }
//                    } else {
//                        for (int j = 0; j < familiaInsumos.size(); j++) {
//                            if (familiaInsumos.get(i).getIdFamiliaInsumo() == idFamilia) {
//                              
//                                cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo();
//
//                                cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo() - consumosListaas.get(j).getCantidadInsumo();
//                            }
//                        }
//                    }
//                } else {
//                    if (consumosListaas.isEmpty() && cantidadInsumoConsumo == 0) {
//
//                        cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo();
//                        cantidadEnPaquete = cantidadInsumoConsumo;
//                    } else {
//                     
//                        for (int j = 0; j < consumosListaas.size(); j++) {
//                            if (consumosListaas.get(j).getIdInsumo() == idInsumo) {
//                                cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo();
//
//                                cantidadInsumoConsumo = armadoPaqueteMedicosListas.get(i).getCantidadInsumo() - consumosListaas.get(j).getCantidadInsumo();
//                            }
//                        }
//                    }
//                }
//
////                if (armadoPaqueteMedicosListas.get(i).getIdInsumo() == idInsumo) {
////                
////
////                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (SQLException ex) {
            Logger.getLogger(GeneararIndicaBiomedicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void activarDesactivarRdb() {
        if (cantidadInsumoConsumo > 0) {
            rdbPaquete.setDisable(false);
            lblDisponiblePaquete.setVisible(true);
            lblDisponible.setVisible(true);
        } else {
            rdbPaquete.setDisable(true);
            rdbPaquete.setSelected(false);
            lblDisponiblePaquete.setVisible(false);
            lblDisponible.setVisible(false);
        }
    }

    private void reporte() {
        ReporteC reporte = new ReporteC("reporteIndicaEtrega");
        reporte.generarReporteIndicaEntrega(pasiienteGuardado.getIdfolio(), id_indicasp);
    }

}
