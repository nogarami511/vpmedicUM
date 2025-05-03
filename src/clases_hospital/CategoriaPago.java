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
public class CategoriaPago {
    
    private int id;
    private String nombre;
    
    private int UsuarioModificacion;
    private Timestamp fechaModificacion;
    
    public CategoriaPago(){}

    public CategoriaPago(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUsuarioModificacion() {
        return UsuarioModificacion;
    }

    public void setUsuarioModificacion(int UsuarioModificacion) {
        this.UsuarioModificacion = UsuarioModificacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    @Override
    public String toString() {
        return  nombre;
    }
    
}
