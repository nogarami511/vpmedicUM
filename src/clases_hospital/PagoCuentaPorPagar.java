/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author alfar
 */
public class PagoCuentaPorPagar {
    private int idPagosCuentasPorPagar;
    private int idComprasInternasP;
    private int numPago;
    private Date fechaPago;
    private double importePago;
    private int usuarioModificacion;
    private Timestamp fechaModificacion;
    private int id_forma_pago;
    private String fptipoformapagonombre;

    // Constructor vacío
    public PagoCuentaPorPagar() {}

    // Constructor con parámetros
    public PagoCuentaPorPagar(int idPagosCuentasPorPagar, int idComprasInternasP, int numPago, Date fechaPago, double importePago, int usuarioModificacion, Timestamp fechaModificacion, int id_forma_pago ) {
        this.idPagosCuentasPorPagar = idPagosCuentasPorPagar;
        this.idComprasInternasP = idComprasInternasP;
        this.numPago = numPago;
        this.fechaPago = fechaPago;
        this.importePago = importePago;
        this.usuarioModificacion = usuarioModificacion;
        this.fechaModificacion = fechaModificacion;
        this.id_forma_pago = id_forma_pago;
    }

    // Getters y setters
    public int getIdPagosCuentasPorPagar() {
        return idPagosCuentasPorPagar;
    }

    public void setIdPagosCuentasPorPagar(int idPagosCuentasPorPagar) {
        this.idPagosCuentasPorPagar = idPagosCuentasPorPagar;
    }

    public int getIdComprasInternasP() {
        return idComprasInternasP;
    }

    public void setIdComprasInternasP(int idComprasInternasP) {
        this.idComprasInternasP = idComprasInternasP;
    }

    public int getNumPago() {
        return numPago;
    }

    public void setNumPago(int numPago) {
        this.numPago = numPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getImportePago() {
        return importePago;
    }

    public void setImportePago(double importePago) {
        this.importePago = importePago;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getId_forma_pago() {
        return id_forma_pago;
    }

    public void setId_forma_pago(int id_forma_pago) {
        this.id_forma_pago = id_forma_pago;
    }

    public String getFptipoformapagonombre() {
        return fptipoformapagonombre;
    }

    public void setFptipoformapagonombre(String fptipoformapagonombre) {
        this.fptipoformapagonombre = fptipoformapagonombre;
    }

}
