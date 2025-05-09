/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM;

/**
 *
 * @author theso
 */
public class Unidad {
    
    private int idUnidadVenta;
    private String unidad;
    private String simbolo;
    private boolean estatus;

    public Unidad() {
        
    }   
    
    public Unidad(int idUnidadVenta, String unidad, String simbolo, boolean estatus) {
        this.idUnidadVenta = idUnidadVenta;
        this.unidad = unidad;
        this.simbolo = simbolo;
        this.estatus = estatus;
    }

    /**
     * @return the idUnidadVenta
     */
    public int getIdUnidadVenta() {
        return idUnidadVenta;
    }

    /**
     * @param idUnidadVenta the idUnidadVenta to set
     */
    public void setIdUnidadVenta(int idUnidadVenta) {
        this.idUnidadVenta = idUnidadVenta;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the simbolo
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * @param simbolo the simbolo to set
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return the estatus
     */
    public boolean getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Unidad{" + "idUnidadVenta=" + idUnidadVenta + ", unidad=" + unidad + ", simbolo=" + simbolo + ", estatus=" + estatus + '}';
    }
        
}
