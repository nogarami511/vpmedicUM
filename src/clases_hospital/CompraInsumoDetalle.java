/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author PC
 */
public class CompraInsumoDetalle extends  Costo{
    Locale mexico = new Locale("es", "MX");
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mexico);
   private int id_compras_insumo_detalle,
            id_compra_insumop,
            id_insumo,
            estatus_compras_det;
    
   private double cantidad,
            precio_unitario,
            descuento,
            importe,
           cantidad_recibida,
           cantidad_faltante;
   private String importeFormato, precio_unitarioFormato;

    public String getPrecio_unitarioFormato() {
        this.importeFormato = formatoMoneda.format(precio_unitario);
        return precio_unitarioFormato;
    }

    public String getImporteFormato() {
        this.importeFormato = formatoMoneda.format(importe);
        return importeFormato;
    }

  


    
     private String nombreInsumo;
         
     
         public double getCantidad_recibida() {
        return cantidad_recibida;
    }

    public void setCantidad_recibida(double cantidad_recibida) {
        this.cantidad_recibida = cantidad_recibida;
    }
      public double getCantidad_faltante() {
        return cantidad_faltante;
    }

    public void setCantidad_faltante(double cantidad_faltante) {
        this.cantidad_faltante = cantidad_faltante;
    }
     
     public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }



    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }
    
   

    public int getId_compras_insumo_detalle() {
        return id_compras_insumo_detalle;
    }

    public void setId_compras_insumo_detalle(int id_compras_insumo_detalle) {
        this.id_compras_insumo_detalle = id_compras_insumo_detalle;
    }

    public int getId_compra_insumop() {
        return id_compra_insumop;
    }

    public void setId_compra_insumop(int id_compra_insumop) {
        this.id_compra_insumop = id_compra_insumop;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public int getEstatus_compras_det() {
        return estatus_compras_det;
    }

    public void setEstatus_compras_det(int estatus_compras_det) {
        this.estatus_compras_det = estatus_compras_det;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}
