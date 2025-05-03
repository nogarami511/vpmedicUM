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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javafx.scene.control.Alert;

/**
 *
 * @author alfar
 */
public class ReporteEntradasInsumos {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    String reporte;
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    private static final String[] UNIDADES = {"", "un", "dos", "tres", "cuatro",
        "cinco", "seis", "siete", "ocho", "nueve", "diez", "once", "doce",
        "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho",
        "diecinueve", "veinte"};

    private static final String[] DECENAS = {"", "", "veinti", "treinta", "cuarenta",
        "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};

    private static final String[] CENTENAS = {"", "ciento", "doscientos",
        "trescientos", "cuatrocientos", "quinientos", "seiscientos",
        "setecientos", "ochocientos", "novecientos"};

    public ReporteEntradasInsumos(String reporte) {
        this.reporte = reporte;
    }

    public void generarReporte(String folio, String datosProveedor, int sumaEntrada) {
//        LocalTime horaActual = LocalTime.now();
//        String hora = "" + horaActual;
        Date horaActual = new Date();

        // Especificar la zona horaria deseada
        TimeZone zonaHoraria = TimeZone.getTimeZone("GMT-6");

        // Formatear la hora actual en el formato deseado
        SimpleDateFormat formatoHora = new SimpleDateFormat("h:mm a");
        formatoHora.setTimeZone(zonaHoraria);
        String horaFormateada = formatoHora.format(horaActual);

        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/valeIngreso.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("_factura", folio);
            String[] splitDatosProvedor = datosProveedor.split(",");
            parametro.put("_nombre_comercial", splitDatosProvedor[0]);
            parametro.put("_rason_social", splitDatosProvedor[1]);
            parametro.put("_rfc", splitDatosProvedor[2]);
            parametro.put("sumaEntrada", sumaEntrada);
            parametro.put("_hora", horaFormateada);

            //PATH A BUSCAR E INSTALAR PROGRAMA
            parametro.put("ruta_absoluta_vale_ingreso", "C:\\vpmedica\\src\\img\\");

            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, con);

            JRViewer test = new JRViewer(jp);
            JFrame frame = new JFrame("reporte");
            frame.getContentPane().add(test);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            alertaError.setHeaderText("Procedimiento Ejecutado con Exito");
            alertaError.setContentText(e.getMessage());
            alertaError.showAndWait();
            e.printStackTrace();
        }
    }

    public void genrarPagoAnticipo(int id, double totalpagar) {
        String cantidadEnLetras = convertirNumeroALetras((int) totalpagar);
        int centavos = (int) (Math.round((totalpagar % 1) * 100));
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/TicketPagoAnticipo.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("_id_paciente", id);
            parametro.put("_ruta_absoluta_parametro", "C:\\vpmedica\\src\\img\\");
            parametro.put("numeros_letras", "(" + cantidadEnLetras.toUpperCase() + " PESOS) " + centavos + "/100 MNX");  //1550.36

            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, con);

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

    public String convertirNumeroALetras(int numero) {
        String[] unidades = {"", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"};
        String[] decenas = {"", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
        String[] centenas = {"", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};

        String letras = "";

        if (numero < 0) {
            letras = "menos " + convertirNumeroALetras(Math.abs(numero));
        } else if (numero < 20) {
            letras = unidades[numero];
        } else if (numero < 100) {
            letras = decenas[numero / 10];
            if ((numero % 10) > 0) {
                letras = letras + " y " + unidades[numero % 10];
            }
        } else if (numero < 1000) {
            letras = centenas[numero / 100];
            if ((numero % 100) > 0) {
                letras = letras + " " + convertirNumeroALetras(numero % 100);
            }
        } else if (numero < 1000000) {
            letras = convertirNumeroALetras(numero / 1000) + " mil";
            if ((numero % 1000) > 0) {
                letras = letras + " " + convertirNumeroALetras(numero % 1000);
            }
        } else {
            letras = "Número fuera de rango";
        }
        return letras;
    }

    public void reporteDetallesConsumo(String folioPaciente) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/ReportDetalleConsumo.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("folioPaciente", folioPaciente);
            //parametro.put("_ruta_absoluta_parametro", "C:\\vpmedica\\src\\img\\");
            //parametro.put("numeros_letras", "(" + cantidadEnLetras.toUpperCase() + " PESOS) " + centavos + "/100 MNX");  //1550.36

            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, con);

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
