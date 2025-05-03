/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author Gerardo
 */
public class ArmadoPaqueteMedico {
    private int id, idInsumo, idPaquete;
    private double cantidad, costo, precioPaquete;
    private String nombreInsumo;
    boolean familia;
    
    private double costoConFormula, costoxcantidad;
    
    private String sCosto, sCostoConFormula;
    
    private int modificado;

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
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

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecioPaquete() {
        return precioPaquete;
    }

    public void setPrecioPaquete(double precioPaquete) {
        this.precioPaquete = precioPaquete;
    }

    public boolean isFamilia() {
        return familia;
    }

    public void setFamilia(boolean familia) {
        this.familia = familia;
    }

    public double getCostoConFormula() {
        return costoConFormula;
    }

    public void setCostoConFormula(double costoConFormula) {
        this.costoConFormula = costoConFormula;
    }

    public String getSCosto() {
        return sCosto;
    }

    public void setsCosto(String sCosto) {
        this.sCosto = sCosto;
    }

    public String getSCostoConFormula() {
        return sCostoConFormula;
    }

    public void setsCostoConFormula(String sCostoConFormula) {
        this.sCostoConFormula = sCostoConFormula;
    }

    public double getCostoxcantidad() {
        return costoxcantidad;
    }

    public void setCostoxcantidad(double costoxcantidad) {
        this.costoxcantidad = costoxcantidad;
    }

    public int getModificado() {
        return modificado;
    }

    public void setModificado(int modificado) {
        this.modificado = modificado;
    }

}
