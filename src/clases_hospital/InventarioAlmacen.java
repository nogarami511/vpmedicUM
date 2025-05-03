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
public class InventarioAlmacen {

    private int idInventarioAlmacen;
    private int idInsumo;
    private double totalExistencia;
    private int usuarioModificacion;
    private Date fechaModificacion;
    private int idAlmacen;
    private double fondoFijo;
    private String nombre_inusmo;
    private String nombre_almacen;
    private double falta;

    // Constructor, getters y setters
    public int getIdInventarioAlmacen() {
        return idInventarioAlmacen;
    }

    public void setIdInventarioAlmacen(int idInventarioAlmacen) {
        this.idInventarioAlmacen = idInventarioAlmacen;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public double getTotalExistencia() {
        return totalExistencia;
    }

    public void setTotalExistencia(double totalExistencia) {
        this.totalExistencia = totalExistencia;
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

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public double getFondoFijo() {
        return fondoFijo;
    }

    public void setFondoFijo(double fondoFijo) {
        this.fondoFijo = fondoFijo;
    }

    public String getNombre_inusmo() {
        return nombre_inusmo;
    }

    public void setNombre_inusmo(String nombre_inusmo) {
        this.nombre_inusmo = nombre_inusmo;
    }

    public String getNombre_almacen() {
        return nombre_almacen;
    }

    public void setNombre_almacen(String nombre_almacen) {
        this.nombre_almacen = nombre_almacen;
    }

    public double getFalta() {
        return falta;
    }

    public void setFalta(double falta) {
        this.falta = falta;
    }

    @Override
    public String toString() {
        return nombre_inusmo;
    }

}
