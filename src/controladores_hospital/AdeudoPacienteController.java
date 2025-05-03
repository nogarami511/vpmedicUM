/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.AdeudoPaciente;
import clases_hospital.CitaQuirofano;
import clases_hospital.Folio;
import clases_hospital_DAO.ActualizacionFolioDAO;
import clases_hospital_DAO.AdeudoPacientesDao;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.PagosDAO;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AdeudoPacienteController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);

    ObservableList<AdeudoPaciente> adeudospacientes = FXCollections.observableArrayList();

    FoliosDAO foliosdao;
    PagosDAO pagosdao;
    CitaQuirofanoDAO citaQuirofanodao;
    ActualizacionFolioDAO actualizarfoliodao;
    AdeudoPacientesDao adeudoPacientesDao;

    @FXML
    private BorderPane bpReporete;
    @FXML
    private Button btnImprimir;
    @FXML
    private Label lblTotalSaldo;
    @FXML
    private TableView<AdeudoPaciente> tabla;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colIdPaciente;
    @FXML
    private TableColumn<?, ?> colNombrePaciente;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colImporte;
    @FXML
    private TableColumn<?, ?> colAbono;
    @FXML
    private TableColumn<?, ?> colSaldo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            actualizarFolios();
        } catch (SQLException ex) {
            Logger.getLogger(AdeudoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionImprimir(ActionEvent event) {
    }

    private void actualizarFolios() throws SQLException {
        con = conexion.conectar2();
        foliosdao = new FoliosDAO(con);
        pagosdao = new PagosDAO(con);
        citaQuirofanodao = new CitaQuirofanoDAO(con);
        actualizarfoliodao = new ActualizacionFolioDAO(con);
        List<Folio> folios = foliosdao.obtenerFoliosAimprimir();
        for (Folio folio : folios) {
            CitaQuirofano citaQuirofano = citaQuirofanodao.obteCitaQuirofanoPorIdFolio(folio.getId());
            int id_CitaQuirofano;
            if (citaQuirofano == null) {
                id_CitaQuirofano = 0;
            } else {
                id_CitaQuirofano = citaQuirofano.getIdQuirofano();
            }
            double sumatoriatotal = actualizarfoliodao.calculateTotalSubtotal(actualizarfoliodao.getReportItems( folio.getId()));
            double pagos = pagosdao.optenerTotalPagosPaciente(folio.getId());
            double adeudo = (sumatoriatotal * 1.16) - pagos;
            double totaconiva = sumatoriatotal * 1.16;
            folio.setMontoHastaElMomento(totaconiva);
            folio.setSaldoACubrir(adeudo);
            folio.setTotalDeAbono(pagos);
            foliosdao.actualizarFolio(folio);
        }
        llenarTabla();
    }

    private void llenarTabla() {
        try {
            adeudospacientes.clear();
            adeudoPacientesDao = new AdeudoPacientesDao(conexion.conectar2());
            adeudospacientes.addAll(adeudoPacientesDao.audeudo());

            colId.setCellValueFactory(new PropertyValueFactory("id"));
            colIdPaciente.setCellValueFactory(new PropertyValueFactory("id_paciente"));
            colNombrePaciente.setCellValueFactory(new PropertyValueFactory("nombre_paciente"));
            colFolio.setCellValueFactory(new PropertyValueFactory("folio"));
            colFecha.setCellValueFactory(new PropertyValueFactory("fecha_ingreso"));
            colImporte.setCellValueFactory(new PropertyValueFactory("importetotal"));
            colAbono.setCellValueFactory(new PropertyValueFactory("totaldeabonoFORMATEADO"));
            colSaldo.setCellValueFactory(new PropertyValueFactory("saldoacubrirFORMATEADO"));

            tabla.setItems(adeudospacientes);
            sumarTotal();
        } catch (SQLException ex) {
            Logger.getLogger(AdeudoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sumarTotal() {
        double suma = 0.0;
        for (int i = 0; i < adeudospacientes.size(); i++) {
            suma += adeudospacientes.get(i).getSaldoacubrir();
        }
        Locale mexicoLocale = new Locale("es", "MX");
        NumberFormat formatoMonedaMexico = NumberFormat.getCurrencyInstance(mexicoLocale);
        lblTotalSaldo.setText(formatoMonedaMexico.format(suma));
    }

    private void reporteardo() {
        try {
            // Carga tu informe JasperReport (.jrxml) como un flujo de entrada (input stream)
            InputStream inputStream = getClass().getResourceAsStream("/reportes/reportecuentasporpagarpacientes/reportecuentasporpagarpacientes.jrxml");

            // Compila el archivo .jrxml en un archivo .jasper en tiempo de ejecución
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Llena el informe con datos (puedes personalizar esto según tus datos)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);

            // Crea un visor de informes JasperReports
            JRViewer viewer = new JRViewer(jasperPrint);

            // Envuelve el visor en un SwingNode
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(viewer);

            // Limpia cualquier contenido anterior del BorderPane
            bpReporete.getChildren().clear();

            // Agrega el SwingNode al BorderPane
            bpReporete.setCenter(swingNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
