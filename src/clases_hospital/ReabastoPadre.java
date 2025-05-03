/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author alfar
 */
public class ReabastoPadre {
    private int idRabastosPadre;
    private String folioReabasto;
    private int idProveedor;
    private double costoTotalInicial;
    private double costoTotalFinal;
    private Timestamp fechaGenerado;
    private Timestamp fechaReabasto;
    private int usuarioGenerado;
    private int usuarioReabasto;
    private Timestamp fechaModificacion;
    private int usuarioModificacion;
    private int estatu_reabasto;
    private String nombre;
    
    double monto_pagado;
    double saldo_saldar;
    int forma_pago;
    int usuario_pago;
    Date fecha_pago;
    int estatus_pago;
    
    String nombreProveedor;
    
    double montoSolicitado;
    double montoAutorizado;
    
    boolean pedir;
    
    public int getIdRabastosPadre() {
        return idRabastosPadre;
    }

    public void setIdRabastosPadre(int idRabastosPadre) {
        this.idRabastosPadre = idRabastosPadre;
    }

    public String getFolioReabasto() {
        return folioReabasto;
    }

    public void setFolioReabasto(String folioReabasto) {
        this.folioReabasto = folioReabasto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public double getCostoTotalInicial() {
        return costoTotalInicial;
    }

    public void setCostoTotalInicial(double costoTotalInicial) {
        this.costoTotalInicial = costoTotalInicial;
    }

    public double getCostoTotalFinal() {
        return costoTotalFinal;
    }

    public void setCostoTotalFinal(double costoTotalFinal) {
        this.costoTotalFinal = costoTotalFinal;
    }

    public Timestamp getFechaGenerado() {
        return fechaGenerado;
    }

    public void setFechaGenerado(Timestamp fechaGenerado) {
        this.fechaGenerado = fechaGenerado;
    }

    public Timestamp getFechaReabasto() {
        return fechaReabasto;
    }

    public void setFechaReabasto(Timestamp fechaReabasto) {
        this.fechaReabasto = fechaReabasto;
    }

    public int getUsuarioGenerado() {
        return usuarioGenerado;
    }

    public void setUsuarioGenerado(int usuarioGenerado) {
        this.usuarioGenerado = usuarioGenerado;
    }

    public int getUsuarioReabasto() {
        return usuarioReabasto;
    }

    public void setUsuarioReabasto(int usuarioReabasto) {
        this.usuarioReabasto = usuarioReabasto;
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

    public int getEstatu_reabasto() {
        return estatu_reabasto;
    }

    public void setEstatu_reabasto(int estatu_reabasto) {
        this.estatu_reabasto = estatu_reabasto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMonto_pagado() {
        return monto_pagado;
    }

    public void setMonto_pagado(double monto_pagado) {
        this.monto_pagado = monto_pagado;
    }

    public double getSaldo_saldar() {
        return saldo_saldar;
    }

    public void setSaldo_saldar(double saldo_saldar) {
        this.saldo_saldar = saldo_saldar;
    }

    public int getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(int forma_pago) {
        this.forma_pago = forma_pago;
    }

    public int getUsuario_pago() {
        return usuario_pago;
    }

    public void setUsuario_pago(int usuario_pago) {
        this.usuario_pago = usuario_pago;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public int getEstatus_pago() {
        return estatus_pago;
    }

    public void setEstatus_pago(int estatus_pago) {
        this.estatus_pago = estatus_pago;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public double getMontoAutorizado() {
        return montoAutorizado;
    }

    public void setMontoAutorizado(double montoAutorizado) {
        this.montoAutorizado = montoAutorizado;
    }

    public boolean isPedir() {
        return pedir;
    }

    public void setPedir(boolean pedir) {
        this.pedir = pedir;
    }

    @Override
    public String toString() {
        return folioReabasto;
    }
}
