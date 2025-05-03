/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Timestamp;

/**
 *
 * @author alfar
 */
public class ProductoVenta {
    private int id;
    private String codigoProducto;
    private String nombreProducto;
    private int tipoProducto;
    private String descripcion;
    private double precioVenta;
    private int iva;
    private String claveSAT;
    private String idEstatusProducto;
    private Timestamp fechaModificacion;
    private int usuarioModificacion;

    // Constructor
    public ProductoVenta() {
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(int tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getIVA() {
        return iva;
    }

    public void setIVA(int iva) {
        this.iva = iva;
    }

    public String getClaveSAT() {
        return claveSAT;
    }

    public void setClaveSAT(String claveSAT) {
        this.claveSAT = claveSAT;
    }

    public String getIdEstatusProducto() {
        return idEstatusProducto;
    }

    public void setIdEstatusProducto(String idEstatusProducto) {
        this.idEstatusProducto = idEstatusProducto;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }
}
