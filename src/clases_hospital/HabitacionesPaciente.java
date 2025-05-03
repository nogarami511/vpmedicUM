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
public class HabitacionesPaciente {

    int idTipoHabitacion, idFolio, idEstatusHospitalizacion, idPaciente;
    String folio, tipoHabitacion, fechaApartadoString, nombrePaciente, estatusHospitalizacion;
    Date fechaApartado;

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdEstatusHospitalizacion() {
        return idEstatusHospitalizacion;
    }

    public void setIdEstatusHospitalizacion(int idEstatusHospitalizacion) {
        this.idEstatusHospitalizacion = idEstatusHospitalizacion;
    }

    public String getEstatusHospitalizacion() {
        return estatusHospitalizacion;
    }

    public void setEstatusHospitalizacion(String estatusHospitalizacion) {
        this.estatusHospitalizacion = estatusHospitalizacion;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(int idFolio) {
        this.idFolio = idFolio;
    }

    public int getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(int idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public String getFechaApartadoString() {
        return fechaApartadoString;
    }

    public void setFechaApartadoString(String fechaApartadoString) {
        this.fechaApartadoString = fechaApartadoString;
    }

    public Date getFechaApartado() {
        return fechaApartado;
    }

    public void setFechaApartado(Date fechaApartado) {
        this.fechaApartado = fechaApartado;
    }
}
