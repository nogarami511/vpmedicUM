/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author noga
 */
public class InventarioDetalle  {
    int id_inventario_detalle, id_inventario, cantidad;
    String lote, estado;
    Date caducidad,fecha_modificacion;
    boolean estatus;

    public int getId_inventario_detalle() {
        return id_inventario_detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public void setId_inventario_detalle(int id_inventario_detalle) {
        this.id_inventario_detalle = id_inventario_detalle;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    @Override
    public String toString() {
         SimpleDateFormat formatoFecha = new SimpleDateFormat("MM/yyyy");
         String fechaCaducidad = formatoFecha.format(caducidad);
        return fechaCaducidad + " "+ lote;
    }
    
}
