/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class VPHUController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private BorderPane bpData;
    @FXML
    private DatePicker dtpFechaInicio;
    @FXML
    private DatePicker dtpFechaFin;
    @FXML
    private Button btnGenerar;

    String mesanio;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        reporteardo();
    }

    @FXML
    private void accionGenerar(ActionEvent event) {
        if (dtpFechaInicio.getValue() != null && dtpFechaFin.getValue() != null) {

            LocalDate fechaInicio = dtpFechaInicio.getValue();
            LocalDate fechaFin = dtpFechaFin.getValue();

            String formattedDateStar = fechaInicio.format(dateFormatter);
            String formattedDateEnd = fechaFin.format(dateFormatter) + " 23:59:59";

            DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Parsear el String a un LocalDate
            LocalDate fechaInicio2 = LocalDate.parse(formattedDateStar, dateFormatter2);

            // Extraer el mes y el año
            String month = fechaInicio2.getMonth().getDisplayName(TextStyle.FULL, new Locale("es"));
            int year = fechaInicio.getYear();

            // Formatear como "Agosto 2024"
            mesanio = month + " " + year;
            System.out.println(mesanio);

            reporteardo(formattedDateStar, formattedDateEnd);
        }
    }

    private void reporteardo(String inicio, String fin) {
        try {
            // Carga tu informe JasperReport (.jrxml) como un flujo de entrada (input stream)
            InputStream inputStream = getClass().getResourceAsStream("/reportes/ReporteVPHU/RPT_vphu.jrxml");

            // Compila el archivo .jrxml en un archivo .jasper en tiempo de ejecución
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            // Crear un mapa de parámetros
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fechaInicio", inicio); // Reemplaza "parametro1" y "Valor1" con tus parámetros y valores reales
            parametros.put("fechaFin", fin); // Agrega más parámetros según sea necesario MESANIO, MINISTRACION, RUBRO
            parametros.put("MESANIO", mesanio.toUpperCase());
            parametros.put("MINISTRACION", 1);
            parametros.put("RUBRO", 22);

            // Llena el informe con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con);

            // Crea un visor de informes JasperReports
            JRViewer viewer = new JRViewer(jasperPrint);

            // Envuelve el visor en un SwingNode
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(viewer);

            // Limpia cualquier contenido anterior del BorderPane
            bpData.getChildren().clear();

            // Agrega el SwingNode al BorderPane
            bpData.setCenter(swingNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
