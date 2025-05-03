/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Timestamp;

/**
 *
 * @author Gerardo
 */
public class Folio2 {
    private int id, idPaciente, idEstatusFolio, idPaquete;
    private int idTipoHabitacion, numeroHabitacion, idEstatusHospitalizacion;
    private String nombrePaciente;
    private Timestamp fechaIngreso;
    private boolean urgencias, upgrade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdEstatusFolio() {
        return idEstatusFolio;
    }

    public void setIdEstatusFolio(int idEstatusFolio) {
        this.idEstatusFolio = idEstatusFolio;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(int idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public int getIdEstatusHospitalizacion() {
        return idEstatusHospitalizacion;
    }

    public void setIdEstatusHospitalizacion(int idEstatusHospitalizacion) {
        this.idEstatusHospitalizacion = idEstatusHospitalizacion;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public boolean isUrgencias() {
        return urgencias;
    }

    public void setUrgencias(boolean urgencias) {
        this.urgencias = urgencias;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }
    
    
    
}
