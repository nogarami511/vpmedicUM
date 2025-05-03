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
public class AgendaQuirofano {
    int id_agenda_quirofano, id_quirofano, id_medico,id_usuario_modificacion;
    String nombre_paciente, nombre_medico, observaciones,nombre_quirofano, hora_agenda;
    Date fechaagenda;

    public int getId_agenda_quirofano() {
        return id_agenda_quirofano;
    }

    public void setId_agenda_quirofano(int id_agenda_quirofano) {
        this.id_agenda_quirofano = id_agenda_quirofano;
    }

    public int getId_quirofano() {
        return id_quirofano;
    }

    public void setId_quirofano(int id_quirofano) {
        this.id_quirofano = id_quirofano;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public int getId_usuario_modificacion() {
        return id_usuario_modificacion;
    }

    public void setId_usuario_modificacion(int id_usuario_modificacion) {
        this.id_usuario_modificacion = id_usuario_modificacion;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getNombre_medico() {
        return nombre_medico;
    }

    public void setNombre_medico(String nombre_medico) {
        this.nombre_medico = nombre_medico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombre_quirofano() {
        return nombre_quirofano;
    }

    public void setNombre_quirofano(String nombre_quirofano) {
        this.nombre_quirofano = nombre_quirofano;
    }

    public String getHora_agenda() {
        return hora_agenda;
    }

    public void setHora_agenda(String hora_agenda) {
        this.hora_agenda = hora_agenda;
    }

    public Date getFechaagenda() {
        return fechaagenda;
    }

    public void setFechaagenda(Date fechaagenda) {
        this.fechaagenda = fechaagenda;
    }
    
}
