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
public class ActualizacionFolio {
    
    int idInsumo; 
    String tipoInsumo; 
    int cantidadEntregada; 
    int consumido; 
    int incluidoEnPaquete; 
    int excedente; 
    int devolucion; 
    double precioVentaUnitariaSinIva; 
    double subtotalSinIva;
    
    public ActualizacionFolio(){}

    public ActualizacionFolio(int idInsumo, String tipoInsumo, int cantidadEntregada, int consumido, int incluidoEnPaquete, int excedente, int devolucion, double precioVentaUnitariaSinIva, double subtotalSinIva) {
        this.idInsumo = idInsumo;
        this.tipoInsumo = tipoInsumo;
        this.cantidadEntregada = cantidadEntregada;
        this.consumido = consumido;
        this.incluidoEnPaquete = incluidoEnPaquete;
        this.excedente = excedente;
        this.devolucion = devolucion;
        this.precioVentaUnitariaSinIva = precioVentaUnitariaSinIva;
        this.subtotalSinIva = subtotalSinIva;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(String tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public int getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(int cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public int getConsumido() {
        return consumido;
    }

    public void setConsumido(int consumido) {
        this.consumido = consumido;
    }

    public int getIncluidoEnPaquete() {
        return incluidoEnPaquete;
    }

    public void setIncluidoEnPaquete(int incluidoEnPaquete) {
        this.incluidoEnPaquete = incluidoEnPaquete;
    }

    public int getExcedente() {
        return excedente;
    }

    public void setExcedente(int excedente) {
        this.excedente = excedente;
    }

    public int getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(int devolucion) {
        this.devolucion = devolucion;
    }

    public double getPrecioVentaUnitariaSinIva() {
        return precioVentaUnitariaSinIva;
    }

    public void setPrecioVentaUnitariaSinIva(double precioVentaUnitariaSinIva) {
        this.precioVentaUnitariaSinIva = precioVentaUnitariaSinIva;
    }

    public double getSubtotalSinIva() {
        return subtotalSinIva;
    }

    public void setSubtotalSinIva(double subtotalSinIva) {
        this.subtotalSinIva = subtotalSinIva;
    }

    @Override
    public String toString() {
        return "ActualizacionFolio{" + "idInsumo=" + idInsumo + ", tipoInsumo=" + tipoInsumo + ", cantidadEntregada=" + cantidadEntregada + ", consumido=" + consumido + ", incluidoEnPaquete=" + incluidoEnPaquete + ", excedente=" + excedente + ", devolucion=" + devolucion + ", precioVentaUnitariaSinIva=" + precioVentaUnitariaSinIva + ", subtotalSinIva=" + subtotalSinIva + '}';
    }

    
    
}
