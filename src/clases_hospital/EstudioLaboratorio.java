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
public class EstudioLaboratorio {
    int idEstudiosLaboratorios;
    int idInsumo;
    int idLaboratorio;
    int usuarioModificacion;
    Date fechaModificacion;
    int estatus;
    double costoSinIVA;
    String nombreEstudio;

    public EstudioLaboratorio() {
    }

    public EstudioLaboratorio(int idInsumo, int idLaboratorio, int usuarioModificacion, int estatus, double costoSinIVA, String nombreEstudio) {
        this.idInsumo = idInsumo;
        this.idLaboratorio = idLaboratorio;
        this.usuarioModificacion = usuarioModificacion;
        this.estatus = estatus;
        this.costoSinIVA = costoSinIVA;
        this.nombreEstudio = nombreEstudio;
    }
    
    public int getIdEstudiosLaboratorios() {
        return idEstudiosLaboratorios;
    }

    public void setIdEstudiosLaboratorios(int idEstudiosLaboratorios) {
        this.idEstudiosLaboratorios = idEstudiosLaboratorios;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public int getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(int idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
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

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public double getCostoSinIVA() {
        return costoSinIVA;
    }

    public void setCostoSinIVA(double costoSinIVA) {
        this.costoSinIVA = costoSinIVA;
    }

    public String getNombreEstudio() {
        return nombreEstudio;
    }

    public void setNombreEstudio(String nombreEstudio) {
        this.nombreEstudio = nombreEstudio;
    }

    @Override
    public String toString() {
        return nombreEstudio;
    }
    
    
}
