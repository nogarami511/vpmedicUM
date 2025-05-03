/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 *
 * @author alfar
 */
public class AutorizarPagoNomina {
    
    private int id, usuario_modifiicacion, ejercisiofiscal, estatus;
    private String clavenomina, stringStatus, tipo_nomina, perioridad;
    private Date  fechainicio, fechafin, fechacaluclo, fechaautorizado, fechapago;
    private double total;
    SimpleDateFormat formatterFechaInicio = new SimpleDateFormat("dd/MM/yyyy"), formatterFechaFin = new SimpleDateFormat("dd/MM/yyyy"), formatterFechacaluclo = new SimpleDateFormat("dd/MM/yyyy HH:mm"), formatterFechaautorizado = new SimpleDateFormat("dd/MM/yyyy HH:mm"), formatterFechapago = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_modifiicacion() {
        return usuario_modifiicacion;
    }

    public void setUsuario_modifiicacion(int usuario_modifiicacion) {
        this.usuario_modifiicacion = usuario_modifiicacion;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getStringStatus() {
        return stringStatus;
    }

    public void setStringStatus(String stringStatus) {
        this.stringStatus = stringStatus;
    }

    public String getClavenomina() {
        return clavenomina;
    }

    public void setClavenomina(String clavenomina) {
        this.clavenomina = clavenomina;
    }

    public String getTipo_nomina() {
        return tipo_nomina;
    }

    public void setTipo_nomina(String tipo_nomina) {
        this.tipo_nomina = tipo_nomina;
    }

    public String getPerioridad() {
        return perioridad;
    }

    public void setPerioridad(String perioridad) {
        this.perioridad = perioridad;
    }

    public int getEjercisiofiscal() {
        return ejercisiofiscal;
    }

    public void setEjercisiofiscal(int ejercisiofiscal) {
        this.ejercisiofiscal = ejercisiofiscal;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getFechacaluclo() {
        return fechacaluclo;
    }

    public void setFechacaluclo(Date fechacaluclo) {
        this.fechacaluclo = fechacaluclo;
    }

    public Date getFechaautorizado() {
        return fechaautorizado;
    }

    public void setFechaautorizado(Date fechaautorizado) {
        this.fechaautorizado = fechaautorizado;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFormatterFechaInicio() {
        return formatterFechaInicio.format(fechainicio);
    }

    public String getFormatterFechaFin() {
        return formatterFechaFin.format(fechafin);
    }

    public String getFormatterFechacaluclo() {
        return formatterFechacaluclo.format(fechacaluclo);
    }

    public String getFormatterFechaautorizado() {
        return formatterFechaautorizado.format(fechaautorizado);
    }

    public String getFormatterFechapago() {
        return formatterFechapago.format(fechapago);
    }
    
    
    
}
