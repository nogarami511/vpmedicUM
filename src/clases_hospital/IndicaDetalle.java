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
public class IndicaDetalle {
    private int idIndicaDetalle;
    private int idIndicasp;
    private int idInsumo;
    private double cantidadEntregada;
    private double cantidadSuministrada;
    private double cantidadDevolucion;
    private int idUsuarioCreacion;
    private int idUsuarioModificacion;


    private String nombreInsumo,lote;
    
    private double suministrada;
    private double devolucion;
    
    private boolean paquete;
    
    private int idEstatusIndicaDetalle;
    
    private double precioUnitario;
    private double precioUnitarioPaquete;
    
    private int idFamilia;
    
    public IndicaDetalle(){}
    
        public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    

    public int getIdIndicaDetalle() {
        return idIndicaDetalle;
    }

    public void setIdIndicaDetalle(int idIndicaDetalle) {
        this.idIndicaDetalle = idIndicaDetalle;
    }

    public int getIdIndicasp() {
        return idIndicasp;
    }

    public void setIdIndicasp(int idIndicasp) {
        this.idIndicasp = idIndicasp;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public double getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(double cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public double getCantidadSuministrada() {
        return cantidadSuministrada;
    }

    public void setCantidadSuministrada(double cantidadSuministrada) {
        this.cantidadSuministrada = cantidadSuministrada;
    }

    public double getCantidadDevolucion() {
        return cantidadDevolucion;
    }

    public void setCantidadDevolucion(double cantidadDevolucion) {
        this.cantidadDevolucion = cantidadDevolucion;
    }

    public int getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(int idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public double getSuministrada() {
        return suministrada;
    }

    public void setSuministrada(double suministrada) {
        this.suministrada = suministrada;
    }

    public double getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(double devolucion) {
        this.devolucion = devolucion;
    }

    public int getIdEstatusIndicaDetalle() {
        return idEstatusIndicaDetalle;
    }

    public void setIdEstatusIndicaDetalle(int idEstatusIndicaDetalle) {
        this.idEstatusIndicaDetalle = idEstatusIndicaDetalle;
    }

    public boolean isPaquete() {
        return paquete;
    }

    public void setPaquete(boolean paquete) {
        this.paquete = paquete;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioUnitarioPaquete() {
        return precioUnitarioPaquete;
    }

    public void setPrecioUnitarioPaquete(double precioUnitarioPaquete) {
        this.precioUnitarioPaquete = precioUnitarioPaquete;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    @Override
    public String toString() {
        return "IndicaDetalle{" + "idIndicaDetalle=" + idIndicaDetalle + ", idIndicasp=" + idIndicasp + ", idInsumo=" + idInsumo + ", cantidadEntregada=" + cantidadEntregada + ", cantidadSuministrada=" + cantidadSuministrada + ", cantidadDevolucion=" + cantidadDevolucion + ", idUsuarioCreacion=" + idUsuarioCreacion + ", idUsuarioModificacion=" + idUsuarioModificacion + ", nombreInsumo=" + nombreInsumo + ", suministrada=" + suministrada + ", devolucion=" + devolucion + ", paquete=" + paquete + ", idEstatusIndicaDetalle=" + idEstatusIndicaDetalle + '}';
    }
    
    
}
