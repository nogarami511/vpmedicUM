/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM;

import java.sql.Date;

/**
 *
 * @author olver
 */
public class UsuarioUm {
    private int idUsuario;
    private String nombre;
    private String usuario;
    private String password;
    private boolean esdtatus;
    private java.sql.Timestamp fechaCreacion;
    private java.sql.Timestamp fechaModificacion;

    public UsuarioUm() {
    }

    public UsuarioUm(int idUsuario, String nombre, String usuario, String password, boolean esdtatus, java.sql.Timestamp fechaCreacion, java.sql.Timestamp fechaModificacion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.esdtatus = esdtatus;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEsdtatus() {
        return esdtatus;
    }

    public void setEsdtatus(boolean esdtatus) {
        this.esdtatus = esdtatus;
    }

    public java.sql.Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.sql.Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public java.sql.Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(java.sql.Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public String toString() {
        return usuario ;
    }
    
    
    
}
