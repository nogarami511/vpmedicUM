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
public class Costo {
    private int id, idInsumo, idUsuarioModificacion;
    private String clave, observacion;
    private double costoCompraCaja, costoCompraUnitaria, precioVentaCaja, precioVentaUnitaria, cantidadUnitariaxCaja;
    private Date fechaModificacion;
    
    double utilidadPaquete;
    double precioVentaCajaPaquete,utilidad;
    double precioVentaUnitariaPaquete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public double getCantidadUnitariaxCaja() {
        return cantidadUnitariaxCaja;
    }

    public void setCantidadUnitariaxCaja(double cantidadUnitariaxCaja) {
        this.cantidadUnitariaxCaja = cantidadUnitariaxCaja;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public double getCostoCompraCaja() {
        return costoCompraCaja;
    }

    public void setCostoCompraCaja(double costoCompraCaja) {
        this.costoCompraCaja = costoCompraCaja;
    }

    public double getCostoCompraUnitaria() {
        return costoCompraUnitaria;
    }

    public void setCostoCompraUnitaria(double costoCompraUnitaria) {
        this.costoCompraUnitaria = costoCompraUnitaria;
    }

    public double getPrecioVentaCaja() {
        return precioVentaCaja;
    }

    public void setPrecioVentaCaja(double precioVentaCaja) {
        this.precioVentaCaja = precioVentaCaja;
    }

    public double getPrecioVentaUnitaria() {
        return precioVentaUnitaria;
    }

    public void setPrecioVentaUnitaria(double precioVentaUnitaria) {
        this.precioVentaUnitaria = precioVentaUnitaria;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }

    public double getUtilidadPaquete() {
        return utilidadPaquete;
    }

    public void setUtilidadPaquete(double utilidadPaquete) {
        this.utilidadPaquete = utilidadPaquete;
    }

    public double getPrecioVentaCajaPaquete() {
        return precioVentaCajaPaquete;
    }

    public void setPrecioVentaCajaPaquete(double precioVentaCajaPaquete) {
        this.precioVentaCajaPaquete = precioVentaCajaPaquete;
    }

    public double getPrecioVentaUnitariaPaquete() {
        return precioVentaUnitariaPaquete;
    }

    public void setPrecioVentaUnitariaPaquete(double precioVentaUnitariaPaquete) {
        this.precioVentaUnitariaPaquete = precioVentaUnitariaPaquete;
    }

}
