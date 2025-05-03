/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author gamae
 */
public class CuentaPaciente {
    int id_paciente;
    int id_folio;
    int id_habitacion_asignada;
    String folio;
    String nombre_Paciente;
    Date fecha;
    String tipo_habitacion;
    double montoHastaElMomento;
    double saldoACubrir;
    double totalDeAbono;
    
    int idPaquete;
    
    public CuentaPaciente(int idPaciente,int idFolio, int id_habitacion_asignada,String folio,String nombre_paciente, Date fecha, String tipoHabitacion,double montoHastaElMomento, double saldoACubrir, double totalDeAbono){
        this.id_paciente = idPaciente;
        this.id_folio = idFolio;
        this.id_habitacion_asignada = id_habitacion_asignada;
        this.folio = folio;
        this.nombre_Paciente = nombre_paciente;
        this.fecha = fecha;
        this.tipo_habitacion = tipoHabitacion;
        this.montoHastaElMomento =montoHastaElMomento;
        this.saldoACubrir = saldoACubrir;
        this.totalDeAbono = totalDeAbono;
    }

    public CuentaPaciente(int id_paciente, int id_folio, int id_habitacion_asignada, String folio, String nombre_Paciente, Date fecha, String tipo_habitacion, double montoHastaElMomento, double saldoACubrir, double totalDeAbono, int idPaquete) {
        this.id_paciente = id_paciente;
        this.id_folio = id_folio;
        this.id_habitacion_asignada = id_habitacion_asignada;
        this.folio = folio;
        this.nombre_Paciente = nombre_Paciente;
        this.fecha = fecha;
        this.tipo_habitacion = tipo_habitacion;
        this.montoHastaElMomento = montoHastaElMomento;
        this.saldoACubrir = saldoACubrir;
        this.totalDeAbono = totalDeAbono;
        this.idPaquete = idPaquete;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }
    
    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getId_folio() {
        return id_folio;
    }

    public void setId_folio(int id_folio) {
        this.id_folio = id_folio;
    }

    public int getId_habitacion_asignada() {
        return id_habitacion_asignada;
    }

    public void setId_habitacion_asignada(int id_habitacion_asignada) {
        this.id_habitacion_asignada = id_habitacion_asignada;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombre_Paciente() {
        return nombre_Paciente;
    }

    public void setNombre_Paciente(String nombre_Paciente) {
        this.nombre_Paciente = nombre_Paciente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo_habitacion() {
        return tipo_habitacion;
    }

    public void setTipo_habitacion(String tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    public double getMontoHastaElMomento() {
        return montoHastaElMomento;
    }

    public void setMontoHastaElMomento(double montoHastaElMomento) {
        this.montoHastaElMomento = montoHastaElMomento;
    }

    public double getSaldoACubrir() {
        return saldoACubrir;
    }

    public void setSaldoACubrir(double saldoACubrir) {
        this.saldoACubrir = saldoACubrir;
    }

    public double getTotalDeAbono() {
        return totalDeAbono;
    }

    public void setTotalDeAbono(double totalDeAbono) {
        this.totalDeAbono = totalDeAbono;
    }
    
    
}
