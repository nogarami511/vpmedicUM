/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import clase.Conexion;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Gerardo
 */
public class Reporte {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    private String reporte;//nombre del reporte

    public Reporte(String reporte) {
        this.reporte = reporte;
    }

    public void generarReporte(int id) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/" + reporte + ".jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);

            Map parametro = new HashMap();
            parametro.put("movimiento_inventario_parametro", id);

            // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parametro, con);

            // Crear un visor de reportes y pasarlo a un JFrame
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Reporte");
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            // Cerrar la aplicación cuando se cierra el visor de reportes
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Mostrar el visor de reportes
            viewer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println(e);
        }
    }
    
    public void generarReporteReabasto(int id) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/reportereabastoalmacenes/reportereabastoalmacenes.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);

            Map parametro = new HashMap();
            parametro.put("id_movimiento_padre", id);

            // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parametro, con);

            // Crear un visor de reportes y pasarlo a un JFrame
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Reporte");
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            // Cerrar la aplicación cuando se cierra el visor de reportes
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Mostrar el visor de reportes
            viewer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println(e);
        }
    }
    

    public void generarReporteTicket(int id) throws JRException {//COMANDAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
        // Ruta del archivo JRXML
        String reportPath = "/reportes/Rpt_TicketVentaAlimentos.jrxml";

        // Cargar el archivo JRXML y compilarlo
        JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

        // Crear los parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idComanda", id);

        // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);

        // Crear un visor de reportes y pasarlo a un JFrame
        JasperViewer viewer = new JasperViewer(jasperPrint, false);
        viewer.setTitle("Reporte");
        viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

        // Cerrar la aplicación cuando se cierra el visor de reportes
        viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Mostrar el visor de reportes
        viewer.setVisible(true);
    }
    
    public void generarReporte(String clave) throws JRException {//ESTE ES EL REPORTE DE NOMINA INTERNA
        // Ruta del archivo JRXML
        String reportPath = "/reportes/ReportNomina.jrxml";

        // Cargar el archivo JRXML y compilarlo
        JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

        // Crear los parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clave_nomina", clave);

        // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);

        // Crear un visor de reportes y pasarlo a un JFrame
        JasperViewer viewer = new JasperViewer(jasperPrint, false);
        viewer.setTitle("Reporte");
        viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

        // Cerrar la aplicación cuando se cierra el visor de reportes
        viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Mostrar el visor de reportes
        viewer.setVisible(true);
    }
    
        public void generarRecibosNomina(String clave) throws JRException {
        // Ruta del archivo JRXML
        String reportPath = "/reportes/Ticektpagos2.jrxml";

        // Cargar el archivo JRXML y compilarlo
        JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

        // Crear los parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("_clave_nomina", clave);

        // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);

        // Crear un visor de reportes y pasarlo a un JFrame
        JasperViewer viewer = new JasperViewer(jasperPrint, false);
        viewer.setTitle("Reporte");
        viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

        // Cerrar la aplicación cuando se cierra el visor de reportes
        viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Mostrar el visor de reportes
        viewer.setVisible(true);
    }
}
