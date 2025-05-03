/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.ConfiguracionPaquete;
import clases_hospital.Folio;
import clases_hospital.IndicaDetalle;
import clases_hospital.IndicaSuministroPacientes;
import clases_hospital.Indicasp;
import clases_hospital.Insumo;
import clases_hospital_DAO.AreaDAO;
import clases_hospital_DAO.ArmadoPaqueteMedicoDAO;
import clases_hospital_DAO.ConfiguracionPaqueteDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.IndicaDetalleDAO;
import clases_hospital_DAO.IndicaSuministroPacientesDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventariosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class CambiarInsumoConfiguracionPaqueteController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Insumo insumoSelect;
    ObservableList<Insumo> listaInsumos = FXCollections.observableArrayList();//para el autocompletado
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<ConfiguracionPaquete> configuracion = FXCollections.observableArrayList();//para tabla

    ObservableList<IndicaDetalle> indicasDetalles = FXCollections.observableArrayList();
    //double sumaPaqueteBase, sumaConfiguracionPaquete, diferencia;
    double diferencia;
    int idPaquete, idFolio, id_indicasp;

    InventariosDAO inventariosDAO;
    IndicaspDAO indicaspDAO;
    IndicaDetalleDAO indicasDetalleDAO;
    IndicaSuministroPacientesDAO indicasSuministroPacientesDAO;
    AreaDAO areaDAO;
    FoliosDAO folioDAO;

    @FXML
    private TextField insumoVariante;
    @FXML
    private Button btnCapturar;
    @FXML
    private TableView<ConfiguracionPaquete> tabla;
    @FXML
    private TableColumn nombreInsumoConfiguracion;
    @FXML
    private TextField cantidadTXF;
    @FXML
    private TableColumn<ConfiguracionPaquete, Integer> cantidadInsumoConfiguracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setObjeto(int idFolio, int idPaquete, double diferencia, boolean configurado) throws SQLException {
        this.idFolio = idFolio;
    
        this.idPaquete = idPaquete;//aqui esta el idPaquete---------------------------------------------------------------
        this.diferencia = diferencia;
        autocompletado();
     
        tabla.setEditable(true);
        if (configurado == false) {
            llenarConfigArmadoORIGINAL();
        }
        nombreInsumoConfiguracion.setCellValueFactory(new PropertyValueFactory("nombre_insumo"));
        cantidadInsumoConfiguracion.setCellValueFactory(new PropertyValueFactory("cantidad"));
        lambda();
    }

    public void lambda() {
        cantidadInsumoConfiguracion.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cantidadInsumoConfiguracion.setOnEditCommit(event -> {
            ConfiguracionPaquete configuracionPaqueteSeleccionado = event.getRowValue();
            configuracionPaqueteSeleccionado.setCantidad(event.getNewValue());
            tabla.refresh();
        });
        cantidadInsumoConfiguracion.setEditable(true);

        tabla.setRowFactory(tableView -> {
            TableRow<ConfiguracionPaquete> row = new TableRow<>();
            ContextMenu cxmConfiguracion = new ContextMenu();

            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
            descartarConsumo.setOnAction(event -> {
                //con = conexion.conectar2();
                ConfiguracionPaquete confPaq = row.getItem();
             
                configuracion.remove(confPaq);

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

    @FXML
    private void capturar(ActionEvent event) throws SQLException {

        alertaConfirmacion.setTitle("CONFIRMACION");
        alertaConfirmacion.setHeaderText("CONFIRMACION DE USUARIO");
        alertaConfirmacion.setContentText("¿ESTÁ SEGURO DE INGRESAR LA CONFIGURACION DE PAQUETE?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();

        if (action.get() == ButtonType.OK) {
            con = conexion.conectar2();
            ConfiguracionPaqueteDAO configuracionPaqueteDAO = new ConfiguracionPaqueteDAO(con);
            for (ConfiguracionPaquete cf : configuracion) {
                for (int i = 0; i < cf.getCantidad(); i++) {
                    configuracionPaqueteDAO.insert(cf);
                }
            }
            alertaConfirmacion.setTitle("EXITO");
            alertaConfirmacion.setHeaderText("PROCEDIMIENTO EJECUTADO");
            alertaConfirmacion.setContentText("CONFIGURACION DE PAQUETE AGREGADA");
            alertaConfirmacion.showAndWait();
            Stage stage = new Stage();
            Stage stage_actual = (Stage) btnCapturar.getScene().getWindow();
            stage_actual.close();
        }

        ingresarAIndica(con);
        reporte();

        con.close();
    }

    public void autocompletado() throws SQLException {
        con = conexion.conectar2();
        InsumosDAO insumosdao = new InsumosDAO(conexion.conectar2());

        listaInsumos.addAll(insumosdao.obtenerTodosInsumosConInformacionPaquete());
        AutoCompletionBinding<Insumo> insumobuscar = TextFields.bindAutoCompletion(insumoVariante, listaInsumos);
        insumobuscar.setOnAutoCompleted(e -> {
            insumoSelect = e.getCompletion();
        });
        insumobuscar.setPrefWidth(1000.00);
        con.close();
    }

    @FXML
    private void ingresarATabla(ActionEvent event) throws SQLException {

        ConfiguracionPaquete configuracionPaquete = new ConfiguracionPaquete();
        configuracionPaquete.setId_insumo(insumoSelect.getId());
        configuracionPaquete.setPrecio_insumo(insumoSelect.getPrecioVentaUnitariaPaquete());
        configuracionPaquete.setNombre_insumo(insumoSelect.getNombre());
        configuracionPaquete.setId_folio(idFolio);
        configuracionPaquete.setCantidad(Integer.valueOf(cantidadTXF.getText()));
        configuracion.add(configuracionPaquete);

//        indicasDetalles
        IndicaDetalle indicadetalle = new IndicaDetalle();
        indicadetalle.setIdInsumo(insumoSelect.getId());
        indicadetalle.setCantidadEntregada(Integer.valueOf(cantidadTXF.getText()));
        indicadetalle.setCantidadDevolucion(0.0);
        indicadetalle.setIdUsuarioCreacion(VPMedicaPlaza.userSystem);
        indicadetalle.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
        indicadetalle.setNombreInsumo(insumoSelect.getNombre());
        indicasDetalles.add(indicadetalle);

        insumoVariante.clear();
        cantidadTXF.clear();

        tabla.setItems(configuracion);
     
    }

    public void llenarConfigArmadoORIGINAL() throws SQLException {
        con = conexion.conectar2();
       
        ArmadoPaqueteMedicoDAO armadoPaqueteMedicoDAO = new ArmadoPaqueteMedicoDAO(con);
        configuracion = armadoPaqueteMedicoDAO.armadoAConfiguracionPorIDPaquete(idPaquete, idFolio);
        tabla.setItems(configuracion);
        con.close();
    }

    private void ingresarAIndica(Connection con) {
        try {
            indicaspDAO = new IndicaspDAO(con);
            indicasDetalleDAO = new IndicaDetalleDAO(con);
            indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(con);
            folioDAO = new FoliosDAO(con);

            Folio folio = folioDAO.obtenerFolio(idFolio);

            Indicasp indicap = new Indicasp();
            indicap.setIdPaciente(folio.getIdPaciente());
            indicap.setIdFolio(idFolio);
            indicap.setIdUsuarioEntrega(VPMedicaPlaza.userSystem);
            indicap.setIdUsuarioSolicitud(0);
            indicap.setEstatusIndica(0);
            indicap.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
            indicap.setId_area(4);

            id_indicasp = indicaspDAO.create(indicap);

            for (int i = 0; i < configuracion.size(); i++) {
                indicasDetalles.get(i).setIdIndicasp(id_indicasp);
                int id_indica_detalle = indicasDetalleDAO.createIndica(indicasDetalles.get(i));

                for (int j = 0; j < indicasDetalles.get(i).getCantidadEntregada(); j++) {

                    IndicaSuministroPacientes indicasuministropaciente = new IndicaSuministroPacientes();
                    indicasuministropaciente.setIdIndicaDetalle(id_indica_detalle);
                    indicasuministropaciente.setIdFolio(idFolio);
                    indicasuministropaciente.setIdInsimo(indicasDetalles.get(i).getIdInsumo());
                    indicasuministropaciente.setSuministro(false);
                    indicasuministropaciente.setDevolucion(false);
                    indicasuministropaciente.setUsuarioCreacion(VPMedicaPlaza.userSystem);
                    indicasuministropaciente.setUsuarioModificacion(VPMedicaPlaza.userSystem);
                    indicasuministropaciente.setId_area(4);

                    indicasSuministroPacientesDAO.create(indicasuministropaciente);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(CambiarInsumoConfiguracionPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void reporte() {
        ReporteC reporte = new ReporteC("reporteIndicaEtrega");
        reporte.generarReporteIndicaEntrega(idFolio, id_indicasp);
    }

    /*
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
     */
}
