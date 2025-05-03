/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.TipoVistaPago;
import clases_hospital.*;
import clases_hospital_DAO.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import reportes.ReporteC;
import vistasAuxiliares_hospital.AgregarCitaaQuirofanoController;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AgendadeQuirofanoController implements Initializable {

    Conexion conexion = new Conexion();
    AgendaQuirofanoDAO agendaQuirofanoDAO;

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    List<AgendaQuirofano> agendaquirofanolist = new ArrayList<>();
    ObservableList<AgendaQuirofano> agendaquirofanoOB = FXCollections.observableArrayList();
    ObservableList<TipoVistaPago> listafiltro = FXCollections.observableArrayList();

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private TableColumn<?, ?> col_quirofano;
    @FXML
    private TableColumn<?, ?> col_medico;
    @FXML
    private TableColumn<?, ?> col_paciente;
    @FXML
    private TableColumn<?, ?> col_observacion;
    @FXML
    private TableColumn<?, ?> col_fecha;
    @FXML
    private TableColumn<?, ?> col_hora;
    @FXML
    private TableView<AgendaQuirofano> tabla;

    int filtro = 1;
    @FXML
    private ComboBox<TipoVistaPago> cmbfiltro;
    @FXML
    private Button btnReporte;
    @FXML
    private AnchorPane pane_report;
    @FXML
    private Button report_cancel;
    @FXML
    private Button report_aceptar;
    @FXML
    private DatePicker dtp_ini;
    @FXML
    private DatePicker dtp_fin;
    
    DateTimeFormatter dateFormatter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        try {
            // TODO
            llenartabla();
            llenarfiltro();
        } catch (SQLException ex) {
            Logger.getLogger(AgendadeQuirofanoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionAgregar(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarCitaaQuirofano.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);

        //Mandamos a llamar el metodo Cita moficiar de la vista CITAQUIROFANONUEVO
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void accionEditar(ActionEvent event) throws IOException, SQLException{
        if (!tabla.getSelectionModel().isEmpty()) {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarCitaaQuirofano.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);
            AgregarCitaaQuirofanoController editarCita = fxml.getController();
            editarCita.setObjeto(tabla.getSelectionModel().getSelectedItem());
         //   System.out.println(""+tabla.getSelectionModel().getSelectedItem().getId_agenda_quirofano());

            // Mandamos a llamar el metodo Cita modificar de la vista CITAQUIROFANONUEVO
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.showAndWait();
        } else {
            // Mostrar mensaje indicando que no se ha seleccionado ninguna cita en la tabla
            // Por ejemplo, usando un cuadro de diálogo

            alertaError.setTitle("Advertencia");
            alertaError.setHeaderText("No se ha seleccionado una cita");
            alertaError.setContentText("Por favor, seleccione una cita de la tabla.");
            alertaError.showAndWait();
        }
        llenartabla();
    }

    @FXML
    private void filtrartabla(ActionEvent event) throws SQLException {
        filtro = cmbfiltro.getValue().getId_tipo();
        llenartabla();
    }

    private void llenartabla() throws SQLException {
        tabla.getItems().clear();
        Connection con = conexion.conectar2();
        agendaQuirofanoDAO = new AgendaQuirofanoDAO(con);
        agendaquirofanoOB.addAll(agendaQuirofanoDAO.traaerAgenda(filtro));
        tabla.setItems(agendaquirofanoOB);
        col_quirofano.setCellValueFactory(new PropertyValueFactory("nombre_quirofano"));
        col_medico.setCellValueFactory(new PropertyValueFactory("nombre_medico"));
        col_paciente.setCellValueFactory(new PropertyValueFactory("nombre_paciente"));
        col_observacion.setCellValueFactory(new PropertyValueFactory("observaciones"));
        col_fecha.setCellValueFactory(new PropertyValueFactory("fechaagenda"));
        col_hora.setCellValueFactory(new PropertyValueFactory("hora_agenda"));

    }

    private void llenarfiltro() {
        List<TipoVistaPago> filtroList = new ArrayList<>();
        filtroList.add(new TipoVistaPago(1, "PENDIENTES"));
        filtroList.add(new TipoVistaPago(2, "PASADAS"));
        filtroList.add(new TipoVistaPago(3, "GENERAL"));
        listafiltro.addAll(filtroList);
        cmbfiltro.setItems(listafiltro);
    }

    @FXML
    private void accionReporte(ActionEvent event) {
        pane_report.setVisible(true);
        
    }

    @FXML
    private void accioncancelar_reporte(ActionEvent event) {
        
        
        
        pane_report.setVisible(false);
        dtp_ini.setValue(null);
        dtp_fin.setValue(null);
    }

    @FXML
    private void acciongenerar_reporte(ActionEvent event) {
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        //ReporteC reporteardo = new ReporteC("AgendaQuirofano");
        if (dtp_ini.getValue() != null && dtp_fin.getValue() != null) {

            LocalDate fechaInicio = dtp_ini.getValue();
            LocalDate fechaFin = dtp_fin.getValue();
            System.out.println("" + fechaInicio + fechaFin);

            String formattedDateStar = fechaInicio.format(dateFormatter);
            String formattedDateEnd = fechaFin.format(dateFormatter);
            
            reporteardo(formattedDateStar, formattedDateEnd);
        }
        else{
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("No se ha seleccionado VALRO NECESARIO");
            alertaError.setContentText("Por favor, seleccione una LOS DIAS.");
            alertaError.showAndWait();
        }
        
        pane_report.setVisible(false);
        dtp_ini.setValue(null);
        dtp_fin.setValue(null);
    }
        private void reporteardo(String inicio , String fin) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/AgendadeCitas/AgendadeCitasQuirofano.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("inicio", inicio);
            parametro.put("fin", fin);
            

            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, conexion.conectar2());

            JRViewer test = new JRViewer(jp);
            JFrame frame = new JFrame("reporte");
            frame.getContentPane().add(test);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
