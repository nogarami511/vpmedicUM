/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Timestamp;

/**
 *
 * @author alfar
 */
public class GenerarReabastoInsumo {
    private int idGenerarReabastoInsumo;
    private int idRabastosPadre;
    private int idInsumo;
    private double totalUnidadesFaltantes;
    private double costoUnitarioInicial;
    private double costoUnitarioFinal;
    private boolean pedir;
    private Timestamp fechaCreacion;
    private int idEstatusReabasto;
    private int usuarioCreacion;
    private Timestamp fechaModificacion;
    private int usuarioModificacion;
    private int idEstatus;
    
    private String nombre;
    private String presentacion;
    private int id_proveedor;
    
    private String nombrePoveedor;
    
    private double costo_total_inicial;
    private double costo_total_final;
    
    private String estatusreabasto;
 
    private double costo_compra_caja, cantidad_unitariaxcaja;
    
    private double descuento;

    public int getIdGenerarReabastoInsumo() {
        return idGenerarReabastoInsumo;
    }

    public void setIdGenerarReabastoInsumo(int idGenerarReabastoInsumo) {
        this.idGenerarReabastoInsumo = idGenerarReabastoInsumo;
    }

    public int getIdRabastosPadre() {
        return idRabastosPadre;
    }

    public void setIdRabastosPadre(int idRabastosPadre) {
        this.idRabastosPadre = idRabastosPadre;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public double getTotalUnidadesFaltantes() {
        return totalUnidadesFaltantes;
    }

    public void setTotalUnidadesFaltantes(double totalUnidadesFaltantes) {
        this.totalUnidadesFaltantes = totalUnidadesFaltantes;
    }

    public double getCostoUnitarioInicial() {
        return costoUnitarioInicial;
    }

    public void setCostoUnitarioInicial(double costoUnitarioInicial) {
        this.costoUnitarioInicial = costoUnitarioInicial;
    }

    public double getCostoUnitarioFinal() {
        return costoUnitarioFinal;
    }

    public void setCostoUnitarioFinal(double costoUnitarioFinal) {
        this.costoUnitarioFinal = costoUnitarioFinal;
    }

    public boolean isPedir() {
        return pedir;
    }

    public void setPedir(boolean pedir) {
        this.pedir = pedir;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdEstatusReabasto() {
        return idEstatusReabasto;
    }

    public void setIdEstatusReabasto(int idEstatusReabasto) {
        this.idEstatusReabasto = idEstatusReabasto;
    }

    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombrePoveedor() {
        return nombrePoveedor;
    }

    public void setNombrePoveedor(String nombrePoveedor) {
        this.nombrePoveedor = nombrePoveedor;
    }

    public double getCosto_total_inicial() {
        return costo_total_inicial;
    }

    public void setCosto_total_inicial(double costo_total_inicial) {
        this.costo_total_inicial = costo_total_inicial;
    }

    public double getCosto_total_final() {
        return costo_total_final;
    }

    public void setCosto_total_final(double costo_total_final) {
        this.costo_total_final = costo_total_final;
    }

    public String getEstatusreabasto() {
        return estatusreabasto;
    }

    public void setEstatusreabasto(String estatusreabasto) {
        this.estatusreabasto = estatusreabasto;
    }

    public double getCantidad_unitariaxcaja() {
        return cantidad_unitariaxcaja;
    }

    public void setCantidad_unitariaxcaja(double cantidad_unitariaxcaja) {
        this.cantidad_unitariaxcaja = cantidad_unitariaxcaja;
    }

    public double getCosto_compra_caja() {
        return costo_compra_caja;
    }

    public void setCosto_compra_caja(double costo_compra_caja) {
        this.costo_compra_caja = costo_compra_caja;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "GenerarReabastoInsumo{" + "idInsumo=" + idInsumo + '}';
    }
    
}
