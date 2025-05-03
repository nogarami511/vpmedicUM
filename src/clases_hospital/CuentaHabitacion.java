/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Time;

/**
 *
 * @author gamae
 */
public class CuentaHabitacion {
    
    int id, idPaciente,numeroHabitacion,dias,horas,minutos,idEstatus;
    double tarifa, costoDescuento, pagoTotal, porcentajeDescuento;
    String NombrePaciente, folio, tipoHabitacion, motivoDescuento, motivoCambio;
    Time fechaEntrada, fechaSalida;

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

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public double getCostoDescuento() {
        return costoDescuento;
    }

    public void setCostoDescuento(double costoDescuento) {
        this.costoDescuento = costoDescuento;
    }

    public double getPagoTotal() {
        return pagoTotal;
    }

    public void setPagoTotal(double pagoTotal) {
        this.pagoTotal = pagoTotal;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getNombrePaciente() {
        return NombrePaciente;
    }

    public void setNombrePaciente(String NombrePaciente) {
        this.NombrePaciente = NombrePaciente;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public String getMotivoDescuento() {
        return motivoDescuento;
    }

    public void setMotivoDescuento(String motivoDescuento) {
        this.motivoDescuento = motivoDescuento;
    }

    public String getMotivoCambio() {
        return motivoCambio;
    }

    public void setMotivoCambio(String motivoCambio) {
        this.motivoCambio = motivoCambio;
    }

    public Time getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Time fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Time getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Time fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    @Override
    public String toString() {
        return "CuentaHabitacion{" + "id=" + id + ", idPaciente=" + idPaciente + ", numeroHabitacion=" + numeroHabitacion + ", dias=" + dias + ", horas=" + horas + ", minutos=" + minutos + ", idEstatus=" + idEstatus + ", tarifa=" + tarifa + ", costoDescuento=" + costoDescuento + ", pagoTotal=" + pagoTotal + ", porcentajeDescuento=" + porcentajeDescuento + ", NombrePaciente=" + NombrePaciente + ", folio=" + folio + ", tipoHabitacion=" + tipoHabitacion + ", motivoDescuento=" + motivoDescuento + ", motivoCambio=" + motivoCambio + ", fechaEntrada=" + fechaEntrada + ", fechaSalida=" + fechaSalida + '}';
    }
    
    
    
}
