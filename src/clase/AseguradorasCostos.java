/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import java.sql.Date;
import java.text.DecimalFormat;

/**
 *
 * @author PC
 */
public class AseguradorasCostos {

    DecimalFormat df = new DecimalFormat("#.##");
    
    private int idAseguradorasCosto;
    private String nombreInsumo;
    private int idInsumo;
    private int idAseguradorap;
    private double costoAseguradorasCostos;
    private double cantidadInsumosAseguradorasCostos;
    private double costoUnitarioAseguradorasCostos;
    private double precioVentaAseguradorasCosto;
    private double precioVentaUnitarioAseguradorasCosto;
    private int usuarioModificacion;
    private Date fechaModificacion;
    private int idTipoInsumosMedico;
    private String nombreTipoInsumoMedico;
    private String nombreAseguradora;
    private double descuento;

    private String formatoDineroCostoAseguradorasCostos;
    private String formatoDineroCostoUnitarioAseguradorasCostos;
    private String formatoDineroPrecioVentaAseguradorasCosto;
    private String formatoDineroPrecioVentaUnitarioAseguradorasCosto;
    private String formatoPorcentajeDescuento;

    public AseguradorasCostos() {
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public int getIdAseguradorasCosto() {
        return idAseguradorasCosto;
    }

    public void setIdAseguradorasCosto(int idAseguradorasCosto) {
        this.idAseguradorasCosto = idAseguradorasCosto;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public int getIdAseguradorap() {
        return idAseguradorap;
    }

    public void setIdAseguradorap(int idAseguradorap) {
        this.idAseguradorap = idAseguradorap;
    }

    public double getCostoAseguradorasCostos() {
        return costoAseguradorasCostos;
    }

    public void setCostoAseguradorasCostos(double costoAseguradorasCostos) {
        this.costoAseguradorasCostos = costoAseguradorasCostos;
    }

    public double getCantidadInsumosAseguradorasCostos() {
        return cantidadInsumosAseguradorasCostos;
    }

    public void setCantidadInsumosAseguradorasCostos(double cantidadInsumosAseguradorasCostos) {
        this.cantidadInsumosAseguradorasCostos = cantidadInsumosAseguradorasCostos;
    }

    public double getCostoUnitarioAseguradorasCostos() {
        return costoUnitarioAseguradorasCostos;
    }

    public void setCostoUnitarioAseguradorasCostos(double costoUnitarioAseguradorasCostos) {
        this.costoUnitarioAseguradorasCostos = costoUnitarioAseguradorasCostos;
    }

    public double getPrecioVentaAseguradorasCosto() {
        return precioVentaAseguradorasCosto;
    }

    public void setPrecioVentaAseguradorasCosto(double precioVentaAseguradorasCosto) {
        this.precioVentaAseguradorasCosto = precioVentaAseguradorasCosto;
    }

    public double getPrecioVentaUnitarioAseguradorasCosto() {
        return precioVentaUnitarioAseguradorasCosto;
    }

    public void setPrecioVentaUnitarioAseguradorasCosto(double precioVentaUnitarioAseguradorasCosto) {
        this.precioVentaUnitarioAseguradorasCosto = precioVentaUnitarioAseguradorasCosto;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getIdTipoInsumosMedico() {
        return idTipoInsumosMedico;
    }

    public void setIdTipoInsumosMedico(int idTipoInsumosMedico) {
        this.idTipoInsumosMedico = idTipoInsumosMedico;
    }

    public String getNombreTipoInsumoMedico() {
        return nombreTipoInsumoMedico;
    }

    public void setNombreTipoInsumoMedico(String nombreTipoInsumoMedico) {
        this.nombreTipoInsumoMedico = nombreTipoInsumoMedico;
    }

    public String getNombreAseguradora() {
        return nombreAseguradora;
    }

    public void setNombreAseguradora(String nombreAseguradora) {
        this.nombreAseguradora = nombreAseguradora;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    //==========================================================================
    public String getFormatoDineroCostoAseguradorasCostos() {
        formatoDineroCostoAseguradorasCostos = "$ " + df.format(costoAseguradorasCostos);
        return formatoDineroCostoAseguradorasCostos;
    }

    public String getFormatoDineroCostoUnitarioAseguradorasCostos() {
        formatoDineroCostoUnitarioAseguradorasCostos = "$ " + df.format(costoUnitarioAseguradorasCostos);
        return formatoDineroCostoUnitarioAseguradorasCostos;
    }

    public String getFormatoDineroPrecioVentaAseguradorasCosto() {
        formatoDineroPrecioVentaAseguradorasCosto = "$ " + df.format(precioVentaAseguradorasCosto);
        return formatoDineroPrecioVentaAseguradorasCosto;
    }

    public String getFormatoDineroPrecioVentaUnitarioAseguradorasCosto() {
        formatoDineroPrecioVentaUnitarioAseguradorasCosto = "$ " + df.format(precioVentaUnitarioAseguradorasCosto);
        return formatoDineroPrecioVentaUnitarioAseguradorasCosto;
    }

    public String getFormatoPorcentajeDescuento() {
        formatoPorcentajeDescuento = df.format(descuento) + "%";
        return formatoPorcentajeDescuento;
    }

}
