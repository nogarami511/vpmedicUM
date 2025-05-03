/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author alfar
 */
public class ListasIndicacionesMedicas {
    private int id, id_estatus, dias, dosis;
    private String folio_paciente, indicacion_medica, tratamiento, intervalo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }

    public String getFolio_paciente() {
        return folio_paciente;
    }

    public void setFolio_paciente(String folio_paciente) {
        this.folio_paciente = folio_paciente;
    }

    public String getIndicacion_medica() {
        return indicacion_medica;
    }

    public void setIndicacion_medica(String indicacion_medica) {
        this.indicacion_medica = indicacion_medica;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    
}
