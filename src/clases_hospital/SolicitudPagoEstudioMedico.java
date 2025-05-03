/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author alfar
 */
public class SolicitudPagoEstudioMedico {
    private int idSolicitudPagosEstudiosMedicos;
    private Date fechaGenerada;
    private int formaPago;
    private int solicitante;
    private int estatusSolicitud;
    private int usuarioAutorizacion;
    private Date fechaAutorizacion;
    private int usuarioModificacion;
    private Date fechaModificacion;
    
    double montototal;
    
    String estatusString;
    String formapagString;

    public int getIdSolicitudPagosEstudiosMedicos() {
        return idSolicitudPagosEstudiosMedicos;
    }

    public void setIdSolicitudPagosEstudiosMedicos(int idSolicitudPagosEstudiosMedicos) {
        this.idSolicitudPagosEstudiosMedicos = idSolicitudPagosEstudiosMedicos;
    }

    public Date getFechaGenerada() {
        return fechaGenerada;
    }

    public void setFechaGenerada(Date fechaGenerada) {
        this.fechaGenerada = fechaGenerada;
    }

    public int getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(int formaPago) {
        this.formaPago = formaPago;
    }

    public int getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(int solicitante) {
        this.solicitante = solicitante;
    }

    public int getEstatusSolicitud() {
        return estatusSolicitud;
    }

    public void setEstatusSolicitud(int estatusSolicitud) {
        this.estatusSolicitud = estatusSolicitud;
    }

    public int getUsuarioAutorizacion() {
        return usuarioAutorizacion;
    }

    public void setUsuarioAutorizacion(int usuarioAutorizacion) {
        this.usuarioAutorizacion = usuarioAutorizacion;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public double getMontototal() {
        return montototal;
    }

    public void setMontototal(double montototal) {
        this.montototal = montototal;
    }

    public String getEstatusString() {
        return estatusString;
    }

    public void setEstatusString(String estatusString) {
        this.estatusString = estatusString;
    }

    public String getFormapagString() {
        return formapagString;
    }

    public void setFormapagString(String formapagString) {
        this.formapagString = formapagString;
    }
    
}
