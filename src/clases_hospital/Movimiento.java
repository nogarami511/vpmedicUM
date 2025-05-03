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
 * @author alfar
 */
public class Movimiento {
    private int id, id_insumo, id_proveedor, cantidad_paquete, cantidad_caja, cantidad_unidad, inventario_inicial, movimiento, inventario_final;
    private String nombre_insumo, factura, lote, nombre_proveedor;
    private double costo, precio_unitario, porcentaje_utilidad;
    private Date fecha_movimiento, caducidad;
    SimpleDateFormat formatterFecha_movimiento = new SimpleDateFormat("dd/MM/yyyy"), formatterCaducidad = new SimpleDateFormat("dd/MM/yyyy"); 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public int getCantidad_paquete() {
        return cantidad_paquete;
    }

    public void setCantidad_paquete(int cantidad_paquete) {
        this.cantidad_paquete = cantidad_paquete;
    }

    public int getCantidad_caja() {
        return cantidad_caja;
    }

    public void setCantidad_caja(int cantidad_caja) {
        this.cantidad_caja = cantidad_caja;
    }

    public int getCantidad_unidad() {
        return cantidad_unidad;
    }

    public void setCantidad_unidad(int cantidad_unidad) {
        this.cantidad_unidad = cantidad_unidad;
    }

    public int getInventario_inicial() {
        return inventario_inicial;
    }

    public void setInventario_inicial(int inventario_inicial) {
        this.inventario_inicial = inventario_inicial;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public int getInventario_final() {
        return inventario_final;
    }

    public void setInventario_final(int inventario_final) {
        this.inventario_final = inventario_final;
    }

    public String getNombre_insumo() {
        return nombre_insumo;
    }

    public void setNombre_insumo(String nombre_insumo) {
        this.nombre_insumo = nombre_insumo;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getPorcentaje_utilidad() {
        return porcentaje_utilidad;
    }

    public void setPorcentaje_utilidad(double porcentaje_utilidad) {
        this.porcentaje_utilidad = porcentaje_utilidad;
    }

    public String getFormatterFecha_movimiento() {
        return formatterFecha_movimiento.format(fecha_movimiento);
    }

    public String getFormatterCaducidad() {
        return formatterCaducidad.format(caducidad);
    }

    public Date getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Date fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }
}