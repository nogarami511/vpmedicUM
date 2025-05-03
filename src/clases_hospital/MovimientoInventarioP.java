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
public class MovimientoInventarioP {

    private int id, tipo_mov, id_proveedor, id_origen, id_destino, estatus_movimiento, usuario_registro;
    private double subtotal, importe_impuesto, total, descuento;
    private String folio_mov, observaciones;
    private Date fecha_registro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo_mov() {
        return tipo_mov;
    }

    public void setTipo_mov(int tipo_mov) {
        this.tipo_mov = tipo_mov;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public int getId_origen() {
        return id_origen;
    }

    public void setId_origen(int id_origen) {
        this.id_origen = id_origen;
    }

    public int getId_destino() {
        return id_destino;
    }

    public void setId_destino(int id_destino) {
        this.id_destino = id_destino;
    }

    public int getEstatus_movimiento() {
        return estatus_movimiento;
    }

    public void setEstatus_movimiento(int estatus_movimiento) {
        this.estatus_movimiento = estatus_movimiento;
    }

    public int getUsuario_registro() {
        return usuario_registro;
    }

    public void setUsuario_registro(int usuario_registro) {
        this.usuario_registro = usuario_registro;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getImporte_impuesto() {
        return importe_impuesto;
    }

    public void setImporte_impuesto(double importe_impuesto) {
        this.importe_impuesto = importe_impuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getFolio_mov() {
        return folio_mov;
    }

    public void setFolio_mov(String folio_mov) {
        this.folio_mov = folio_mov;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

}
