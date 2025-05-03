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
public class SolicitudPago {
    private int idSolicitudesPagos;
    private int fechaGenerada;
    private Date formaPago;
    private double solicitante;
    private int estatusSolicitud;
    private double usuarioAutorizacion;
    private int fechaAutorizacion;
    private double usuarioModificacion;
    private Date fechaModificacion;

    public SolicitudPago() {
    }

    public int getIdSolicitudesPagos() {
        return idSolicitudesPagos;
    }

    public void setIdSolicitudesPagos(int idSolicitudesPagos) {
        this.idSolicitudesPagos = idSolicitudesPagos;
    }

    public int getFechaGenerada() {
        return fechaGenerada;
    }

    public void setFechaGenerada(int fechaGenerada) {
        this.fechaGenerada = fechaGenerada;
    }

    public Date getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(Date formaPago) {
        this.formaPago = formaPago;
    }

    public double getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(double solicitante) {
        this.solicitante = solicitante;
    }

    public int getEstatusSolicitud() {
        return estatusSolicitud;
    }

    public void setEstatusSolicitud(int estatusSolicitud) {
        this.estatusSolicitud = estatusSolicitud;
    }

    public double getUsuarioAutorizacion() {
        return usuarioAutorizacion;
    }

    public void setUsuarioAutorizacion(double usuarioAutorizacion) {
        this.usuarioAutorizacion = usuarioAutorizacion;
    }

    public int getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(int fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public double getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(double usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
