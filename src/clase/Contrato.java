/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class Contrato {
    int id = 0, mesesGracia = 0;
    String razonSocial,clave,numero;
    double rentaMensual,pagoApartado,porcentaje;
    Date fechaPagoApartado,fechaCelebracion,fechaInicio,fechaInicioPago,fechaTermino;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMesesGracia() {
        return mesesGracia;
    }

    public void setMesesGracia(int mesesGracia) {
        this.mesesGracia = mesesGracia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String calve) {
        this.clave = calve;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getRentaMensual() {
        return rentaMensual;
    }

    public void setRentaMensual(double rentaMensual) {
        this.rentaMensual = rentaMensual;
    }

    public double getPagoApartado() {
        return pagoApartado;
    }

    public void setPagoApartado(double pagoApartado) {
        this.pagoApartado = pagoApartado;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFechaPagoApartado() {
        return fechaPagoApartado;
    }

    public void setFechaPagoApartado(Date fechaPagoApartado) {
        this.fechaPagoApartado = fechaPagoApartado;
    }

    public Date getFechaCelebracion() {
        return fechaCelebracion;
    }

    public void setFechaCelebracion(Date fechaCelebracion) {
        this.fechaCelebracion = fechaCelebracion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaInicioPago() {
        return fechaInicioPago;
    }

    public void setFechaInicioPago(Date fechaInicioPago) {
        this.fechaInicioPago = fechaInicioPago;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }
    
    
    
    
}
