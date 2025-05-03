/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import clase.Conexion;
import clases_hospital.NumerosALetras;
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
 * @author gamae
 */
public class ReporteC {

    String reporte;
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    public ReporteC(String reporte) {
        this.reporte = reporte;
    }

    public void generarReporte() {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/Inventario.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
//            int id_paciente = 55;
            Map parametro = new HashMap();
            parametro.put("_ruta_absoluta_parametro", "C:\\vpmedica\\src\\img\\");

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
    public void generarReporteListaPreciosPaquetesMedicos() {
        try {
            // Ruta del archivo JRXML
            String reportPath = "/reportes/PaquetesMedicos/ListaPreciosPaquetesMedicos.jrxml";

            // Cargar el archivo JRXML y compilarlo
            JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

            // Crear los parámetros del reporte
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("idFolio", idFolio);
            // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);

            // Crear un visor de reportes y pasarlo a un JFrame
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Reporte");
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            // Cerrar la aplicación cuando se cierra el visor de reportes
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Mostrar el visor de reportes
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generarReportePRE_CORTE(String usuario_cobro, String cortecaja) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/pre_cortecaja/prueba_corte.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
//            int id_paciente = 55;
            Map parametro = new HashMap();
            parametro.put("usuario_cobro", usuario_cobro);
            parametro.put("id_corte_caja", cortecaja);

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
    public void generarReportePagoAlimento(String id_comanda, String cantidad_letra) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/PagosAlimento/Rpt_PagoAlimento.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
//            int id_paciente = 55;
            Map parametro = new HashMap();
            parametro.put("id_comanda", id_comanda);
            parametro.put("cantidad_letra", cantidad_letra);

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

    public void ReporteComprasInsumosDetalles(String id_compra_insump) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/Compras/RPT_INSUMOS_COMPRAS.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
//            int id_paciente = 55;
            Map parametro = new HashMap();
            parametro.put("id_compras_insumos_p", id_compra_insump);

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

    public void generarReporteInsumos() {
        try {
            // Ruta del archivo JRXML
            String reportPath = "/reportes/Rpt_ListadoExistencias_x_Tipo.jrxml";

            // Cargar el archivo JRXML y compilarlo
            JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

            // Crear los parámetros del reporte
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("idFolio", idFolio);
            // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);

            // Crear un visor de reportes y pasarlo a un JFrame
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Reporte");
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            // Cerrar la aplicación cuando se cierra el visor de reportes
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Mostrar el visor de reportes
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public void generarReporteInsumosCaducados() {
        try {
            // Ruta del archivo JRXML
            String reportPath = "/reportes/LotesCaducados/InsumosLotesCaducados.jrxml";

            // Cargar el archivo JRXML y compilarlo
            JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

            // Crear los parámetros del reporte
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("idFolio", idFolio);
            // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);

            // Crear un visor de reportes y pasarlo a un JFrame
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Reporte");
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            // Cerrar la aplicación cuando se cierra el visor de reportes
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Mostrar el visor de reportes
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void FormatoGeneralReceta() {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/formatos/RecetaGeneralHospitalario.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
//            int id_paciente = 55;
//            Map parametro = new HashMap();
//            parametro.put("_ruta_absoluta_parametro", "C:\\vpmedica\\src\\img\\");            

            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JRViewer test = new JRViewer(jp);
            JFrame frame = new JFrame("reporte");
            frame.getContentPane().add(test);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generarReporteInsumosAlimenticios() {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/Rpt_Existencia_Inventario/Rpt_ListadoExistencias_x_Tipo2.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
//            int id_paciente = 55;
//            Map parametro = new HashMap();
//            parametro.put("_ruta_absoluta_parametro", "C:\\vpmedica\\src\\img\\");            

            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
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

    public void generarReportePago(int id_paciente, int id_pagos, double cantidad) {
        NumerosALetras numLtra = new NumerosALetras(cantidad);
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/rptReciboPago/Rpt_ReciboPagoAnticipo.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_paciente", id_paciente);
            parametro.put("id_pagos", id_pagos);
            parametro.put("cantidad_letra", numLtra.getCantidadString().toUpperCase());

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
        }
    }

    public void generarPulcera(int id) {
        try {
            // Ruta del archivo JRXML
            String reportPath = "/reportes/Rpt_Existencia_Inventario/Rpt_Pulcera_paciente.jrxml";

            // Cargar el archivo JRXML y compilarlo
            JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

            // Crear los parámetros del reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("id_paciente", id);

            // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);

            // Crear un visor de reportes y pasarlo a un JFrame
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Pulcera");
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);

            // Cerrar la aplicación cuando se cierra el visor de reportes
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Mostrar el visor de reportes
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }//Rpt_Pulcera_paciente

    public void generarCorteDetalles(int idFolio, String folio, String fecha, String hora, String paciente, String doctor, String cuenta, String descripcion, double deposito, double importeTotal, double saldoACubrir, int idPaquete, String numeroLetra, int idquirofano, int idtipohabitacion, int numerohabitacion) {
        try {
            // Ruta del archivo JRXML
            String reportPath = "/reportes/Rpt_CorteDetalleCuenta.jrxml";

            // Cargar el archivo JRXML y compilarlo
            JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

            // Crear los parámetros del reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("idFolio", idFolio);
            parameters.put("folio", folio);
            parameters.put("fecha", fecha);
            parameters.put("hora", hora);
            parameters.put("paciente", paciente);
            parameters.put("doctor", doctor);
            parameters.put("cuenta", cuenta);
            parameters.put("descripcion", descripcion);
            parameters.put("deposito", deposito);
            parameters.put("importeTotal", importeTotal);
            parameters.put("saldoACubrir", saldoACubrir);
            parameters.put("idPaquete", idPaquete);
            parameters.put("numeroLetra", numeroLetra);
            parameters.put("id_quirodano", idquirofano);
            parameters.put("id_tipo_habitacion", idtipohabitacion);
            parameters.put("numero_habitacion", numerohabitacion);

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
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generarCorteDetalles1(int idFolio, int idPaquete, String numeroLetra, int idquirofano, int idtipohabitacion, int numerohabitacion) {
        try {
            // Ruta del archivo JRXML
            String reportPath = "/reportes/Rpt_CorteDetalleCuenta_1.jrxml";

            // Cargar el archivo JRXML y compilarlo
            JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

            // Crear los parámetros del reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("idFolio", idFolio);
            parameters.put("idPaquete", idPaquete);
            parameters.put("numeroLetra", numeroLetra);
            parameters.put("id_quirodano", idquirofano);
            parameters.put("id_tipo_habitacion", idtipohabitacion);
            parameters.put("numero_habitacion", numerohabitacion);

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
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generarCorteDetalles1Alta(int idFolio, int idPaquete, String numeroLetra, int idquirofano, int idtipohabitacion, int numerohabitacion) {
        try {
            // Ruta del archivo JRXML
            String reportPath = "/reportes/Rpt_CorteDetalleCuenta_1_Alta.jrxml";

            // Cargar el archivo JRXML y compilarlo
            JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

            // Crear los parámetros del reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("idFolio", idFolio);
            parameters.put("idPaquete", idPaquete);
            parameters.put("numeroLetra", numeroLetra);
            parameters.put("id_quirodano", idquirofano);
            parameters.put("id_tipo_habitacion", idtipohabitacion);
            parameters.put("numero_habitacion", numerohabitacion);

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
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generarCorteDetalles1CorteParcial(int idFolio, int idPaquete, String numeroLetra, int idquirofano, int idtipohabitacion, int numerohabitacion) {
        try {
            // Ruta del archivo JRXML
            String reportPath = "/reportes/rtp_corteParcialCuentaDetalle/Rpt_CorteDetalleCuenta_1.jrxml";

            // Cargar el archivo JRXML y compilarlo
            JasperReport jasperReport = JasperCompileManager.compileReport(JasperViewer.class.getResourceAsStream(reportPath));

            // Crear los parámetros del reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("idFolio", idFolio);
            parameters.put("idPaquete", idPaquete);
            parameters.put("numeroLetra", numeroLetra);
            parameters.put("id_quirodano", idquirofano);
            parameters.put("id_tipo_habitacion", idtipohabitacion);
            parameters.put("numero_habitacion", numerohabitacion);

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
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void generarCierre(int idFolio, String folio, String fecha, String hora, String paciente, String doc, String cuenta, String descripcion, double depocito, double importeTotal, double saldoACubrir, String fomaDePago, String consepto, int idPaquete, String numeroLetra) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/Rpt_CorteCierreCuenta.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("idFolio", idFolio);
            parametro.put("folio", folio);
            parametro.put("fecha", fecha);
            parametro.put("hora", hora);
            parametro.put("paciente", paciente);
            parametro.put("doc", doc);
            parametro.put("cuenta", cuenta);
            parametro.put("desc", descripcion);
            parametro.put("deposito", depocito);
            parametro.put("importeTotal", importeTotal);
            parametro.put("saldoACubrir", saldoACubrir);
            parametro.put("formaDePago", fomaDePago);
            parametro.put("consepto", consepto);
            parametro.put("idPaquete", idPaquete);
            parametro.put("numeroLetra", numeroLetra);
            JasperPrint jp = JasperFillManager.fillReport(jr, parametro, con);
            JRViewer test = new JRViewer(jp);
            JFrame frame = new JFrame("reporte");
            frame.getContentPane().add(test);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }//Rpt_Pulcera_paciente

    public void generarReportePagoFiniquito(int id_paciente, int id_pagos, double cantidad) {
        NumerosALetras numLtra = new NumerosALetras(cantidad);
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/rptReciboPago/Rpt_ReciboPagoFiniquito.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_paciente", id_paciente);
            parametro.put("id_pagos", id_pagos);
            parametro.put("cantidad_letra", numLtra.getCantidadString().toUpperCase());

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

    public void generarReporteDetalle(int id_folio) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/FormatoDeposito/Rpt_Formato1_Deposito.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_folio", id_folio);

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
    }//Rpt_Formato1_Deposito

    public void generarReporteCompraInterna(int id_compras_internas, String totalenleta) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/reportecomprainterna/rpt_compra_interna.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_compras_internas", id_compras_internas);
            parametro.put("totalenleta", totalenleta);

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
        }
    }//Rpt_Formato1_Deposito

    public void generarReporteImpresionPedido(int idrp, int idpro) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/ReporteGenerarReabasto/ReporteGenerarCompra.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_reporte_padre", idrp);
            parametro.put("id_proveedor", idpro);

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
        }
    }

    public void generarReporteImpresionSolicitud(int idAc, String totalenletras) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/Rpt_SolicitudCompra.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("idConfirmacionAutorizacion", idAc);
            parametro.put("totalenletras", totalenletras);

            // Crear una instancia de JasperPrint usando el archivo compilado y los parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parametro, conexion.conectar2());

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
        }
    }
    

    public void generarReporteImpresionAutorizacion(int idAc, String totalenletras) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/Rpt_ConfirmacionAutorizacion.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("idConfirmacionAutorizacion", idAc);
            parametro.put("totalenletras", totalenletras);

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
        }
    }

    public void generarReporteImpresionAutorizacionINSUprove(int idAc, String totalenletras) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/RPT_SOLICITUD_COMPRA_INSUMO_PROVEEDOR/Rpt_SolicitudCompra.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("idConfirmacionAutorizacion", idAc);
            parametro.put("totalenletras", totalenletras);

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
        }
    }

    public void generarReporteImpresionPagosOrdenesComra(String totalenletra, int id_compras_internas, int id_pagos_cuentas_por_pagar) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/comprobantepagocuentaporpagar/comprobantedepagocuentaporpagar.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("totalenletra", totalenletra);
            parametro.put("id_compras_internas", id_compras_internas);
            parametro.put("id_pagos_cuentas_por_pagar", id_pagos_cuentas_por_pagar);

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
        }
    }

    public void generarReporteImpresionreEstudioMedico(int idAc, String totalenletras) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/reporteestudiosmedicos/rpt_Estudio_Medico.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_compras_internas", idAc);
            parametro.put("totalenleta", totalenletras);

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
        }
    }

    public void generarReporteImpresionPagosOrdenesComraEstudios(String totalenletra, int id_compras_internas, int id_pagos_cuentas_por_pagar) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/comprobantepagoEstudios/comprobantedepagoEstudios.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("totalenletra", totalenletra);
            parametro.put("id_compras_internas", id_compras_internas);
            parametro.put("id_pagos_cuentas_por_pagar", id_pagos_cuentas_por_pagar);

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
        }
    }

    public void generarReporteIndicaEntrega(int id_folio, int id_inidcap) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/reporteIndicaEtrega/reporteIndicaEtrega.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_folio_param", id_folio);
            parametro.put("id_indicasp_param", id_inidcap);

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
        }
    }

    public void generarReporteIndicaDevlucion(int id_folio, int id_inidcap) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/reporteIndicaConsumo/reporteIndicaConsumo.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_folio_param", id_folio);
            parametro.put("id_indicasp_param", id_inidcap);

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
        }
    }

    public void generarReportePaquetes(int idPaquete, double factorPaquete) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/Blank_A4.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_paquete", idPaquete);
            parametro.put("utilidad", factorPaquete);

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
        }
    }

    public void generarReporteAdeudo() {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/RPT_adeudo_proveedores/RPT_ADEUDO.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();

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
        }
    }

    public void generarReportePaquetes(int id_proveedor) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/RPT_adeudo_proveedores/RPT_EDOCTO.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
            Map parametro = new HashMap();
            parametro.put("id_proveedor", id_proveedor);

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
        }
    }

    public void ReporteRecepciondeCompras(String id_compra_insump) {
        try {
            InputStream dir = getClass().getResourceAsStream("/reportes/InsumoRecibidos/InsumosRecibidosPorLote.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(dir);
//            int id_paciente = 55;
            Map parametro = new HashMap();
            parametro.put("id_compra_insumoP", id_compra_insump);

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
