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
public class CierresMesEfectivo {
    private int idCierreMesEfectivo;
    private String mesCierreMes;
    private int yearCierreMes;
    private double montoEfectivoCierreMes;
    private Date fechaCierreMes;

    public int getIdCierreMesEfectivo() {
        return idCierreMesEfectivo;
    }

    public void setIdCierreMesEfectivo(int idCierreMesEfectivo) {
        this.idCierreMesEfectivo = idCierreMesEfectivo;
    }

    public String getMesCierreMes() {
        return mesCierreMes;
    }

    public void setMesCierreMes(String mesCierreMes) {
        this.mesCierreMes = mesCierreMes;
    }

    public int getYearCierreMes() {
        return yearCierreMes;
    }

    public void setYearCierreMes(int yearCierreMes) {
        this.yearCierreMes = yearCierreMes;
    }

    public double getMontoEfectivoCierreMes() {
        return montoEfectivoCierreMes;
    }

    public void setMontoEfectivoCierreMes(double montoEfectivoCierreMes) {
        this.montoEfectivoCierreMes = montoEfectivoCierreMes;
    }

    public Date getFechaCierreMes() {
        return fechaCierreMes;
    }

    public void setFechaCierreMes(Date fechaCierreMes) {
        this.fechaCierreMes = fechaCierreMes;
    }
}
