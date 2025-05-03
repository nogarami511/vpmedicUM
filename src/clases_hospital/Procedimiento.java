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
public class Procedimiento {
    int id, id_especialidad, idUsuarioModificacion;
    String nombre, tipo_procedimiento/*int en base de datos*//*int en base de datos*/;
    Timestamp FechaModificacion;
    
    String nombreEspecialidad;
    int id_tipo_procedimiento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_procedimiento() {
        return tipo_procedimiento;
    }

    public void setTipo_procedimiento(String tipo_procedimiento) {
        this.tipo_procedimiento = tipo_procedimiento;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public Timestamp getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFechaModificacion(Timestamp FechaModificacion) {
        this.FechaModificacion = FechaModificacion;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
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
