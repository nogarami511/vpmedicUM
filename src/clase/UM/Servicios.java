/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM;

/**
 *
 * @author PC
 */
public class Servicios {
    private String lote;
    private int cantidad;
    private int unidad;
    private String descripcion;
    private double precio_unitario;

    public Servicios(String lote, int cantidad, int unidad, String descripcion, double precio_unitario) {
        this.lote = lote;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.descripcion = descripcion;
        this.precio_unitario = precio_unitario;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the unidad
     */
    public int getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the precio_unitario
     */
    public double getPrecio_unitario() {
        return precio_unitario;
    }

    /**
     * @param precio_unitario the precio_unitario to set
     */
    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    @Override
    public String toString() {
        return "Servicios{" + "lote=" + lote + ", cantidad=" + cantidad + ", unidad=" + unidad + ", descripcion=" + descripcion + ", precio_unitario=" + precio_unitario + '}';
    }
    
    
    
}
