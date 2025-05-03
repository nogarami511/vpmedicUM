/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author PC
 */
public class AdeudoPaciente {
    int id;
    int id_paciente;
    String nombre_paciente;
    String folio;
    Date fecha_ingreso;
    double importetotal;
    double totaldeabono;
    double saldoacubrir;
    
    Locale mexicoLocale = new Locale("es", "MX");
    NumberFormat formatoMonedaMexico = NumberFormat.getCurrencyInstance(mexicoLocale);
    String importetotalFORMATEADO;
    String totaldeabonoFORMATEADO;
    String saldoacubrirFORMATEADO;


    public AdeudoPaciente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public double getTotaldeabono() {
        return totaldeabono;
    }

    public void setTotaldeabono(double totaldeabono) {
        this.totaldeabono = totaldeabono;
    }

    public double getSaldoacubrir() {
        return saldoacubrir;
    }

    public void setSaldoacubrir(double saldoacubrir) {
        this.saldoacubrir = saldoacubrir;
    }

    public String getImportetotalFORMATEADO() {
        importetotalFORMATEADO = formatoMonedaMexico.format(importetotal);
        return importetotalFORMATEADO;
    }

    public String getTotaldeabonoFORMATEADO() {
        totaldeabonoFORMATEADO = formatoMonedaMexico.format(totaldeabono);
        return totaldeabonoFORMATEADO;
    }

    public String getSaldoacubrirFORMATEADO() {
        saldoacubrirFORMATEADO = formatoMonedaMexico.format(saldoacubrir);
        return saldoacubrirFORMATEADO;
    }
    
    
}
