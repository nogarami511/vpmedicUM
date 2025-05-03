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
public class Entradas {
    private int id, id_insumo, id_proveedor, tipo_entrada, cantidad_caja, inventario_inicial, entrada, inventario_final, id_usuario_mod;
    private double costo_compra_total;
    private String lote_compra, lote_insumo, nombre;
    private Date fecha_entrada, caducidad, fecha_mod;

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

    public int getTipo_entrada() {
        return tipo_entrada;
    }

    public void setTipo_entrada(int tipo_entrada) {
        this.tipo_entrada = tipo_entrada;
    }

    public int getCantidad_caja() {
        return cantidad_caja;
    }

    public void setCantidad_caja(int cantidad_caja) {
        this.cantidad_caja = cantidad_caja;
    }

    public int getInventario_inicial() {
        return inventario_inicial;
    }

    public void setInventario_inicial(int inventario_inicial) {
        this.inventario_inicial = inventario_inicial;
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    public int getInventario_final() {
        return inventario_final;
    }

    public void setInventario_final(int inventario_final) {
        this.inventario_final = inventario_final;
    }

    public int getId_usuario_mod() {
        return id_usuario_mod;
    }

    public void setId_usuario_mod(int id_usuario_mod) {
        this.id_usuario_mod = id_usuario_mod;
    }

    public double getCosto_compra_total() {
        return costo_compra_total;
    }

    public void setCosto_compra_total(double costo_compra_total) {
        this.costo_compra_total = costo_compra_total;
    }

    public String getLote_compra() {
        return lote_compra;
    }

    public void setLote_compra(String lote_compra) {
        this.lote_compra = lote_compra;
    }

    public String getLote_insumo() {
        return lote_insumo;
    }

    public void setLote_insumo(String lote_insumo) {
        this.lote_insumo = lote_insumo;
    }

    public Date getFecha_entrada() {
        return fecha_entrada;
    }

    public void setFecha_entrada(Date fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public Date getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Date fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
