/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Timestamp;

/**
 *
 * @author PC
 */
public class Quirofano {
    int id, idCosto, id_estatus;
    String nombre, descripcion, tipo_procedimiento;
    
    int idUsuarioModificacion, idEstatus;
    double costo;
    Timestamp fechaModificacion;
    
    int id_tipo_procedimiento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(int idCosto) {
        this.idCosto = idCosto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo_procedimiento() {
        return tipo_procedimiento;
    }

    public void setTipo_procedimiento(String tipo_procedimiento) {
        this.tipo_procedimiento = tipo_procedimiento;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getId_tipo_procedimiento() {
        return id_tipo_procedimiento;
    }

    public void setId_tipo_procedimiento(int id_tipo_procedimiento) {
        this.id_tipo_procedimiento = id_tipo_procedimiento;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
