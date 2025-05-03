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
 * @author alfar
 */
public class PaquetesNvo {
    private long id;
    private String nombre;
    private String nombreUsuario;
    private String fechaModificacion;
    private double precioVenta;
    private double costoActual;
    private double costoNuevo;
    private double diferenciaCostos;
    private double diferenciaCostosPorcentaje;
    private double precioVentaActual;
    private double precioVentaNuevo;
    private double diferenciaPrecioVenta;
    private double diferenciaPrecioVentaPorcentaje;
    private double precioOriginalMasIva;
    private double precioNuevoMasIva;
    private double gananciaActual;
    private double gananciaNueva;
    private double gananciaMontoOriginalPorcentaje;
    private double gananciaMontoNuevoPorcentaje;
    private boolean colorRow;
    
    
    // Nuevos valores 2024-10-29
    
    private double costosInsumis,kitBasicocF,utilidad,utilidadCHE,costosHE,totalHe;
    
    
    // Dinero
    private String precioVentaMoneda;
    private String costoActualMoneda;
    private String costoNuevoMoneda;
    private String diferenciaCostosMoneda;
    private String precioVentaActualMoneda;
    private String precioVentaNuevoMoneda;
    private String diferenciaPrecioVentaMoneda;
    private String precioOriginalMasIvaMoneda;
    private String precioNuevoMasIvaMoneda;
    private String gananciaActualMoneda;
    private String gananciaNuevaMoneda;
    // Porcentaje
    private String diferenciaCostosPorcentajeFormato;
    private String diferenciaPrecioVentaPorcentajeFormato;
    private String gananciaMontoOriginalPorcentajeFormato;
    private String gananciaMontoNuevoPorcentajeFormato;
    
    // Getters y setters para cada campo

    public PaquetesNvo() {
    }

    public double getCostosInsumis() {
        return costosInsumis;
    }

    public void setCostosInsumis(double costosInsumis) {
        this.costosInsumis = costosInsumis;
    }

    public double getKitBasicocF() {
        return kitBasicocF;
    }

    public void setKitBasicocF(double kitBasicocF) {
        this.kitBasicocF = kitBasicocF;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }

    public double getUtilidadCHE() {
        return utilidadCHE;
    }

    public void setUtilidadCHE(double utilidadCHE) {
        this.utilidadCHE = utilidadCHE;
    }

    public double getCostosHE() {
        return costosHE;
    }

    public void setCostosHE(double costosHE) {
        this.costosHE = costosHE;
    }

    public double getTotalHe() {
        return totalHe;
    }

    public void setTotalHe(double totalHe) {
        this.totalHe = totalHe;
    }

 
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getCostoActual() {
        return costoActual;
    }

    public void setCostoActual(double costoActual) {
        this.costoActual = costoActual;
    }

    public double getCostoNuevo() {
        return costoNuevo;
    }

    public void setCostoNuevo(double costoNuevo) {
        this.costoNuevo = costoNuevo;
    }

    public double getDiferenciaCostos() {
        return diferenciaCostos;
    }

    public void setDiferenciaCostos(double diferenciaCostos) {
        this.diferenciaCostos = diferenciaCostos;
    }

    public double getDiferenciaCostosPorcentaje() {
        return diferenciaCostosPorcentaje;
    }

    public void setDiferenciaCostosPorcentaje(double diferenciaCostosPorcentaje) {
        this.diferenciaCostosPorcentaje = diferenciaCostosPorcentaje;
    }

    public double getPrecioVentaActual() {
        return precioVentaActual;
    }

    public void setPrecioVentaActual(double precioVentaActual) {
        this.precioVentaActual = precioVentaActual;
    }

    public double getPrecioVentaNuevo() {
        return precioVentaNuevo;
    }

    public void setPrecioVentaNuevo(double precioVentaNuevo) {
        this.precioVentaNuevo = precioVentaNuevo;
    }

    public double getDiferenciaPrecioVenta() {
        return diferenciaPrecioVenta;
    }

    public void setDiferenciaPrecioVenta(double diferenciaPrecioVenta) {
        this.diferenciaPrecioVenta = diferenciaPrecioVenta;
    }

    public double getDiferenciaPrecioVentaPorcentaje() {
        return diferenciaPrecioVentaPorcentaje;
    }

    public void setDiferenciaPrecioVentaPorcentaje(double diferenciaPrecioVentaPorcentaje) {
        this.diferenciaPrecioVentaPorcentaje = diferenciaPrecioVentaPorcentaje;
    }

    public double getGananciaActual() {
        return gananciaActual;
    }

    public void setGananciaActual(double gananciaActual) {
        this.gananciaActual = gananciaActual;
    }

    public double getGananciaNueva() {
        return gananciaNueva;
    }

    public void setGananciaNueva(double gananciaNueva) {
        this.gananciaNueva = gananciaNueva;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public double getPrecioOriginalMasIva() {
        return precioOriginalMasIva;
    }

    public void setPrecioOriginalMasIva(double precioOriginalMasIva) {
        this.precioOriginalMasIva = precioOriginalMasIva;
    }

    public double getPrecioNuevoMasIva() {
        return precioNuevoMasIva;
    }

    public void setPrecioNuevoMasIva(double precioNuevoMasIva) {
        this.precioNuevoMasIva = precioNuevoMasIva;
    }

    public double getGananciaMontoOriginalPorcentaje() {
        return gananciaMontoOriginalPorcentaje;
    }

    public void setGananciaMontoOriginalPorcentaje(double gananciaMontoOriginalPorcentaje) {
        this.gananciaMontoOriginalPorcentaje = gananciaMontoOriginalPorcentaje;
    }

    public double getGananciaMontoNuevoPorcentaje() {
        return gananciaMontoNuevoPorcentaje;
    }

    public void setGananciaMontoNuevoPorcentaje(double gananciaMontoNuevoPorcentaje) {
        this.gananciaMontoNuevoPorcentaje = gananciaMontoNuevoPorcentaje;
    }

    // Métodos para convertir a formato moneda

    public String getPrecioVentaMoneda() {
        precioVentaMoneda = convertirAMoneda(precioVenta);
        return precioVentaMoneda;
    }

    public String getCostoActualMoneda() {
        costoActualMoneda = convertirAMoneda(costosInsumis);
        return costoActualMoneda;
    }

    public String getCostoNuevoMoneda() {
        costoNuevoMoneda = convertirAMoneda(kitBasicocF);
        return costoNuevoMoneda;
    }

    public String getDiferenciaCostosMoneda() {
        diferenciaCostosMoneda = convertirAMoneda(utilidad);
        return diferenciaCostosMoneda;
    }

    public String getPrecioVentaActualMoneda() {
        precioVentaActualMoneda = convertirAMoneda(costosHE);
        return precioVentaActualMoneda;
    }

    public String getPrecioVentaNuevoMoneda() {
        precioVentaNuevoMoneda = convertirAMoneda(totalHe);
        return precioVentaNuevoMoneda;
    }

    public String getDiferenciaPrecioVentaMoneda() {
        diferenciaPrecioVentaMoneda = convertirAMoneda(utilidadCHE);
        return diferenciaPrecioVentaMoneda;
    }

    public String getGananciaActualMoneda() {
        gananciaActualMoneda = convertirAMoneda(gananciaActual);
        return gananciaActualMoneda;
    }

    public String getGananciaNuevaMoneda() {
        gananciaNuevaMoneda = convertirAMoneda(gananciaNueva);
        return gananciaNuevaMoneda;
    }

    public String getPrecioOriginalMasIvaMoneda() {
        precioOriginalMasIvaMoneda = convertirAMoneda(precioOriginalMasIva);
        return precioOriginalMasIvaMoneda;
    }

    public String getPrecioNuevoMasIvaMoneda() {
        precioNuevoMasIvaMoneda = convertirAMoneda(precioNuevoMasIva);
        return precioNuevoMasIvaMoneda;
    }

    public boolean isColorRow() {
        return colorRow;
    }

    public void setColorRow(boolean colorRow) {
        this.colorRow = colorRow;
    }

    // Métodos para convertir a formato porcentaje

    public String getDiferenciaCostosPorcentajeFormato() {
        diferenciaCostosPorcentajeFormato = convertirAPorcentaje(diferenciaCostosPorcentaje);
        return diferenciaCostosPorcentajeFormato;
    }

    public String getDiferenciaPrecioVentaPorcentajeFormato() {
        diferenciaPrecioVentaPorcentajeFormato = convertirAPorcentaje(diferenciaPrecioVentaPorcentaje);
        return diferenciaPrecioVentaPorcentajeFormato;
    }

    public String getGananciaMontoOriginalPorcentajeFormato() {
         gananciaMontoOriginalPorcentajeFormato = convertirAPorcentaje(gananciaMontoOriginalPorcentaje)  + " %";
        return gananciaMontoOriginalPorcentajeFormato;
    }

    public String getGananciaMontoNuevoPorcentajeFormato() {
        gananciaMontoNuevoPorcentajeFormato = convertirAPorcentaje(gananciaMontoNuevoPorcentaje)   + " %";
        return gananciaMontoNuevoPorcentajeFormato ;
    }

    // Método para convertir un valor double a formato moneda

    private String convertirAMoneda(double valor) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        formatoMoneda.setMaximumFractionDigits(2);
        return formatoMoneda.format(valor);
    }

    // Método para convertir un valor double a formato porcentaje

    private String convertirAPorcentaje(double valor) {
        NumberFormat formatoPorcentaje = NumberFormat.getCurrencyInstance();
        formatoPorcentaje.setMaximumFractionDigits(2);
        return formatoPorcentaje.format(valor);
    }
}
