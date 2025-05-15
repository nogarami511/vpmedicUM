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
public class Servicio {
    
   private int idTabServ;   
   private String lote;
   private int cantidad;
   private String descripcion;
   private String marca;
   private double precioUnitario;   
   private double total;
   private boolean estatus;
   private Unidad unidadServicio = new Unidad();
   private Tabulacion tabulador = new Tabulacion();

    public Servicio() {
    }
    
    
    
    public Servicio(int idTabServ, String lote, int cantidad, String descripcion, String marca, double precioUnitario, double total, boolean estatus, Unidad unidadServicio, Tabulacion tabulador) {
        this.idTabServ = idTabServ;
        this.lote = lote;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precioUnitario = precioUnitario;
        //this.unidad = unidad; 
        this.total = total;
        this.estatus = estatus;
        this.unidadServicio = unidadServicio;
        this.tabulador = tabulador;
    }

    public int getIdTabServ() {
        return idTabServ;
    }

    public void setIdTabServ(int idTabServ) {
        this.idTabServ = idTabServ;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    /*
    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }
    */
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public Tabulacion getTabulador() {
        return tabulador;
    }

    public void setTabulador(Tabulacion tabulador) {
        this.tabulador = tabulador;
    }    

    public Unidad getUnidadServicio() {
        return unidadServicio;
    }

    public void setUnidadServicio(Unidad unidadServicio) {
        this.unidadServicio = unidadServicio;
    } 

    @Override
    public String toString() {
        return "Servicio{" + "idTabServ=" + idTabServ + ", lote=" + lote + ", cantidad=" + cantidad + ", descripcion=" + descripcion + ", marca=" + marca + ", precioUnitario=" + precioUnitario + /*", unidad=" + unidad + */" total=" + total + ", estatus=" + estatus + ", unidadServicio=" + unidadServicio + '}';
    }
    
         
    
}
