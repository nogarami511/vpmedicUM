/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import clase.Proveedor;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author alfar
 */
public class CuentasPorPagar {

    Locale mexico = new Locale("es", "MX");
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mexico);
    private int idCuentasPorPagar;
    private int idProveedor;
    private int idTipoCompras;
    private int idCompra;
    private double total;
    private double abono;
    private double saldo;
    private int idEstatusCxP;
    private int idAutorizacionPago;
    private int idFormaPago;
    private double importeAutorizado;
    private int idUsuarioCreacion;
    private Date fechaCreacion;
    private int idUsuarioModificacion;
    private Date fechaModificacion;
    private String tipoCompra;
    private String razonSocial;
    private boolean estatusSolicitado;

    private String formatoDineroMx;
    private String nombre_rubro;

    private String TotalMX;
    private String abonoMX;
    private String saldoMX;
    
    private Proveedor proveedor;
    private String formapagotipo;
    private String estatuspagonombre;
    
    private double montoSolicitadoSoliciatado;
    private String montoSolicitadoSoliciatadoMX;
    
    private int id_estatus_pagos;
    
    // Constructor
    public CuentasPorPagar() {
    }

    // Getters y Setters
    public int getIdCuentasPorPagar() {
        return idCuentasPorPagar;
    }

    public void setIdCuentasPorPagar(int idCuentasPorPagar) {
        this.idCuentasPorPagar = idCuentasPorPagar;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdTipoCompras() {
        return idTipoCompras;
    }

    public void setIdTipoCompras(int idTipoCompras) {
        this.idTipoCompras = idTipoCompras;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getIdEstatusCxP() {
        return idEstatusCxP;
    }

    public void setIdEstatusCxP(int idEstatusCxP) {
        this.idEstatusCxP = idEstatusCxP;
    }

    public int getIdAutorizacionPago() {
        return idAutorizacionPago;
    }

    public void setIdAutorizacionPago(int idAutorizacionPago) {
        this.idAutorizacionPago = idAutorizacionPago;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public double getImporteAutorizado() {
        return importeAutorizado;
    }

    public void setImporteAutorizado(double importeAutorizado) {
        this.importeAutorizado = importeAutorizado;
    }

    public int getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(int idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public boolean isEstatusSolicitado() {
        return estatusSolicitado;
    }

    public void setEstatusSolicitado(boolean estatusSolicitado) {
        this.estatusSolicitado = estatusSolicitado;
    }

    public String getFormatoDineroMx() {
        Locale mexico = new Locale("es", "MX");
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mexico);
        formatoDineroMx = formatoMoneda.format(total);
        return formatoDineroMx;
    }

    public String getNombre_rubro() {
        return nombre_rubro;
    }

    public void setNombre_rubro(String nombre_rubro) {
        this.nombre_rubro = nombre_rubro;
    }

    public String getTotalMX() {
        TotalMX = formatoMoneda.format(total);
        return TotalMX;
    }

    public String getAbonoMX() {
        abonoMX = formatoMoneda.format(abono);
        return abonoMX;
    }

    public String getSaldoMX() {
        saldoMX = formatoMoneda.format(saldo);
        return saldoMX;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getFormapagotipo() {
        return formapagotipo;
    }

    public void setFormapagotipo(String formapagotipo) {
        this.formapagotipo = formapagotipo;
    }

    public String getEstatuspagonombre() {
        return estatuspagonombre;
    }

    public void setEstatuspagonombre(String estatuspagonombre) {
        this.estatuspagonombre = estatuspagonombre;
    }

    public int getId_estatus_pagos() {
        return id_estatus_pagos;
    }

    public void setId_estatus_pagos(int id_estatus_pagos) {
        this.id_estatus_pagos = id_estatus_pagos;
    }

    public double getMontoSolicitadoSoliciatado() {
        return montoSolicitadoSoliciatado;
    }

    public void setMontoSolicitadoSoliciatado(double montoSolicitadoSoliciatado) {
        this.montoSolicitadoSoliciatado = montoSolicitadoSoliciatado;
    }

    public String getMontoSolicitadoSoliciatadoMX() {
        montoSolicitadoSoliciatadoMX = formatoMoneda.format(montoSolicitadoSoliciatado);
        return montoSolicitadoSoliciatadoMX;
    }
}
