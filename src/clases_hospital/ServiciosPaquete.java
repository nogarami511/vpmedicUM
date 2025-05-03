/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class ServiciosPaquete {

    private int id;
    private int idPaquete;
    private int idServiciosPaquetesMedicos;
    private boolean horaExtraQx;
    private int cantidadHoraExtraQx;
    private boolean dietaPaciente;
    private int cantidadDieta;
    private int usuarioCreacion;
    private Date fechaCreacion;
    private int usuarioModificacion;
    private Date fechaModificacion;
    
    private String nombre;
    private double costo;

    // Constructor vacío
    public ServiciosPaquete() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getIdServiciosPaquetesMedicos() {
        return idServiciosPaquetesMedicos;
    }

    public void setIdServiciosPaquetesMedicos(int idServiciosPaquetesMedicos) {
        this.idServiciosPaquetesMedicos = idServiciosPaquetesMedicos;
    }

    public boolean isHoraExtraQx() {
        return horaExtraQx;
    }

    public void setHoraExtraQx(boolean horaExtraQx) {
        this.horaExtraQx = horaExtraQx;
    }

    public int getCantidadHoraExtraQx() {
        return cantidadHoraExtraQx;
    }

    public void setCantidadHoraExtraQx(int cantidadHoraExtraQx) {
        this.cantidadHoraExtraQx = cantidadHoraExtraQx;
    }

    public boolean isDietaPaciente() {
        return dietaPaciente;
    }

    public void setDietaPaciente(boolean dietaPaciente) {
        this.dietaPaciente = dietaPaciente;
    }

    public int getCantidadDieta() {
        return cantidadDieta;
    }

    public void setCantidadDieta(int cantidadDieta) {
        this.cantidadDieta = cantidadDieta;
    }

    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

}
