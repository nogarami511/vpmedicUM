/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author alfar
 */
public class ConsumoHabitacion {

    private int idConsumosHabitacion;
    private int idHabitacion;
    private int idTipoHabitacion;
    private int idFolio;
    private Timestamp fechaIngreso;
    private Timestamp fechaSalida;
    private int cantidad;
    private double montoAlMomento;
    private int usuarioModificacion;
    private Date fechaModificacion;


    public int getIdConsumosHabitacion() {
        return idConsumosHabitacion;
    }

    public void setIdConsumosHabitacion(int idConsumosHabitacion) {
        this.idConsumosHabitacion = idConsumosHabitacion;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(int idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public int getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(int idFolio) {
        this.idFolio = idFolio;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Timestamp getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Timestamp fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getMontoAlMomento() {
        return montoAlMomento;
    }

    public void setMontoAlMomento(double montoAlMomento) {
        this.montoAlMomento = montoAlMomento;
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

    @Override
    public String toString() {
        return "ConsumoHabitacion{" + "idConsumosHabitacion=" + idConsumosHabitacion + ", idHabitacion=" + idHabitacion + ", idTipoHabitacion=" + idTipoHabitacion + ", idFolio=" + idFolio + ", fechaIngreso=" + fechaIngreso + ", fechaSalida=" + fechaSalida + ", cantidad=" + cantidad + ", montoAlMomento=" + montoAlMomento + ", usuarioModificacion=" + usuarioModificacion + ", fechaModificacion=" + fechaModificacion + '}';
    }

}
