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
public class ComprasInternasDetalle {
    private int idComprasInternasDetalle;
    private int idComprasInternasp;
    private String producto;
    private String codigo;
    private String modelo;
    private int cantidad;
    private double precioUnitario;
    private double descuento;
    private double importe;
    private String clavePedido;
    private int usuarioCreacion;
    private Date fechaCreacion;
    private int usuarioModificacion;
    private Date fechaModificacion; 

    public ComprasInternasDetalle() {
    }

    public int getIdComprasInternasDetalle() {
        return idComprasInternasDetalle;
    }

    public void setIdComprasInternasDetalle(int idComprasInternasDetalle) {
        this.idComprasInternasDetalle = idComprasInternasDetalle;
    }

    public int getIdComprasInternasp() {
        return idComprasInternasp;
    }

    public void setIdComprasInternasp(int idComprasInternasp) {
        this.idComprasInternasp = idComprasInternasp;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getClavePedido() {
        return clavePedido;
    }

    public void setClavePedido(String clavePedido) {
        this.clavePedido = clavePedido;
    }

    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
}
