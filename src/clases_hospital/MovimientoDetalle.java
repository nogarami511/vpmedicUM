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
public class MovimientoDetalle {

    private int id, id_insumo_mov_padre, id_insumo, usuario_modificacion,
            existe_lote, id_inventario_detalle, id_tipo_mov_almacen;

    private Date caducidad, fecha_modificacion;
    private String lote_insumo, nombre,tipo_movimientoString;
    
    private double costo, inventario_inicial, inventario_final, movimineto;

    

    public String getTipo_movimientoString() {
        return tipo_movimientoString;
    }

    public void setTipo_movimientoString(String tipo_movimientoString) {
        this.tipo_movimientoString = tipo_movimientoString;
    }
    public int getId_inventario_detalle() {
        return id_inventario_detalle;
    }

    public void setId_inventario_detalle(int id_inventario_detalle) {
        this.id_inventario_detalle = id_inventario_detalle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_insumo_mov_padre() {
        return id_insumo_mov_padre;
    }

    public void setId_insumo_mov_padre(int id_insumo_mov_padre) {
        this.id_insumo_mov_padre = id_insumo_mov_padre;
    }

    public int getId_tipo_mov_almacen() {
        return id_tipo_mov_almacen;
    }

    public void setId_tipo_mov_almacen(int id_tipo_mov_almacen) {
        this.id_tipo_mov_almacen = id_tipo_mov_almacen;
    }

    public double getInventario_inicial() {
        return inventario_inicial;
    }

    public void setInventario_inicial(double inventario_inicial) {
        this.inventario_inicial = inventario_inicial;
    }

    public double getMovimineto() {
        return movimineto;
    }

    public void setMovimineto(double movimineto) {
        this.movimineto = movimineto;
    }

    public double getInventario_final() {
        return inventario_final;
    }

    public void setInventario_final(double inventario_final) {
        this.inventario_final = inventario_final;
    }

    public int getUsuario_modificacion() {
        return usuario_modificacion;
    }

    public void setUsuario_modificacion(int usuario_modificacion) {
        this.usuario_modificacion = usuario_modificacion;
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

    public String getLote_insumo() {
        return lote_insumo;
    }

    public void setLote_insumo(String lote_insumo) {
        this.lote_insumo = lote_insumo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getExiste_lote() {
        return existe_lote;
    }

    public void setExiste_lote(int existe_lote) {
        this.existe_lote = existe_lote;
    }

    @Override
    public String toString() {
        return "MovimientoDetalle{" + "id=" + id + ", id_insumo_mov_padre=" + id_insumo_mov_padre + ", id_insumo=" + id_insumo + ", inventario_inicial=" + inventario_inicial + ", movimineto=" + movimineto + ", inventario_final=" + inventario_final + ", usuario_modificacion=" + usuario_modificacion + ", caducidad=" + caducidad + ", fecha_modificacion=" + fecha_modificacion + ", lote_insumo=" + lote_insumo + ", nombre=" + nombre + ", costo=" + costo + '}';
    }

}
