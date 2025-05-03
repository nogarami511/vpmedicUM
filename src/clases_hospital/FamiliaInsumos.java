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
public class FamiliaInsumos {
    private int idFamiliaInsumo;
    private String nombreFamiliaInsumo;
    private int idUsuarioCreacion;
    private int idUsuarioModificacion;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private double costo;

    // Constructor vacío
    public FamiliaInsumos() {
    }

    // Constructor con parámetros
    public FamiliaInsumos(String nombreFamiliaInsumo, int idUsuarioCreacion, int idUsuarioModificacion) {
        this.nombreFamiliaInsumo = nombreFamiliaInsumo;
        this.idUsuarioCreacion = idUsuarioCreacion;
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    // Getters y setters

    public int getIdFamiliaInsumo() {
        return idFamiliaInsumo;
    }

    public void setIdFamiliaInsumo(int idFamiliaInsumo) {
        this.idFamiliaInsumo = idFamiliaInsumo;
    }

    public String getNombreFamiliaInsumo() {
        return nombreFamiliaInsumo;
    }

    public void setNombreFamiliaInsumo(String nombreFamiliaInsumo) {
        this.nombreFamiliaInsumo = nombreFamiliaInsumo;
    }

    public int getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(int idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return nombreFamiliaInsumo;
    }
}
