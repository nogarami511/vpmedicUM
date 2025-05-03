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
public class HistorialCostosInsumo {

    private int id;
    private int idInsumo;
    private double costoAnterior;
    private double costoActual;
    private double utilidadAnterior;
    private double utilidadActual;
    private int usuarioMod;
    private Timestamp fechaMod;
    private String nombreInsumo;

    public HistorialCostosInsumo() {
    }

    public HistorialCostosInsumo(int idInsumo, double costoAnterior, double costoActual, double utilidadAnterior,
            double utilidadActual) {
        this.idInsumo = idInsumo;
        this.costoAnterior = costoAnterior;
        this.costoActual = costoActual;
        this.utilidadAnterior = utilidadAnterior;
        this.utilidadActual = utilidadActual;
        this.usuarioMod = usuarioMod;
        this.fechaMod = fechaMod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public double getCostoAnterior() {
        return costoAnterior;
    }

    public void setCostoAnterior(double costoAnterior) {
        this.costoAnterior = costoAnterior;
    }

    public double getCostoActual() {
        return costoActual;
    }

    public void setCostoActual(double costoActual) {
        this.costoActual = costoActual;
    }

    public double getUtilidadAnterior() {
        return utilidadAnterior;
    }

    public void setUtilidadAnterior(double utilidadAnterior) {
        this.utilidadAnterior = utilidadAnterior;
    }

    public double getUtilidadActual() {
        return utilidadActual;
    }

    public void setUtilidadActual(double utilidadActual) {
        this.utilidadActual = utilidadActual;
    }

    public int getUsuarioMod() {
        return usuarioMod;
    }

    public void setUsuarioMod(int usuarioMod) {
        this.usuarioMod = usuarioMod;
    }

    public Timestamp getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Timestamp fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

}
