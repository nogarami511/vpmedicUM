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
public class CuentasPorPagarAbonoDetalle {
    
    Locale mexico = new Locale("es", "MX");
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mexico);
    
    private int idCuentaPorPagarAbonoDetalle;
    private int idCuentaPorPagar;
    private Double totalAbonar;
    private Double abonoAbono;
    private Double saldoAbono;
    private Date fechaPago;
    private int usuarioPago;
    private Date fechaModificacion;
    private int usuarioModificacion;
    private int idAutorizacionPagos;
    private int idFormaPago;
    private String nombreRubro;
    private String razonSocial;
    private boolean estatus_pago;
    
    private Proveedor provedor;
    private String estatuspagonombre;
    
    private String formapagotipo;
    private int formapagoint;
    
    private String totalAbonarMX;
    private String abonoAbonoMX;
    private String saldoAbonoMX;
    

    // Constructor
    public CuentasPorPagarAbonoDetalle() {
    }

    // Getters y Setters
    public int getIdCuentaPorPagarAbonoDetalle() {
        return idCuentaPorPagarAbonoDetalle;
    }

    public void setIdCuentaPorPagarAbonoDetalle(int idCuentaPorPagarAbonoDetalle) {
        this.idCuentaPorPagarAbonoDetalle = idCuentaPorPagarAbonoDetalle;
    }

    public int getIdCuentaPorPagar() {
        return idCuentaPorPagar;
    }

    public void setIdCuentaPorPagar(int idCuentaPorPagar) {
        this.idCuentaPorPagar = idCuentaPorPagar;
    }

    public double getTotalAbonar() {
        return totalAbonar;
    }

    public void setTotalAbonar(double totalAbonar) {
        this.totalAbonar = totalAbonar;
    }

    public double getAbonoAbono() {
        return abonoAbono;
    }

    public void setAbonoAbono(double abonoAbono) {
        this.abonoAbono = abonoAbono;
    }

    public double getSaldoAbono() {
        return saldoAbono;
    }

    public void setSaldoAbono(double saldoAbono) {
        this.saldoAbono = saldoAbono;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getUsuarioPago() {
        return usuarioPago;
    }

    public void setUsuarioPago(int usuarioPago) {
        this.usuarioPago = usuarioPago;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public int getIdAutorizacionPagos() {
        return idAutorizacionPagos;
    }

    public void setIdAutorizacionPagos(int idAutorizacionPagos) {
        this.idAutorizacionPagos = idAutorizacionPagos;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public String getNombreRubro() {
        return nombreRubro;
    }

    public void setNombreRubro(String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public boolean isEstatus_pago() {
        return estatus_pago;
    }

    public void setEstatus_pago(boolean estatus_pago) {
        this.estatus_pago = estatus_pago;
    }

    public Proveedor getProvedor() {
        return provedor;
    }

    public void setProvedor(Proveedor provedor) {
        this.provedor = provedor;
    }

    public String getEstatuspagonombre() {
        return estatuspagonombre;
    }

    public void setEstatuspagonombre(String estatuspagonombre) {
        this.estatuspagonombre = estatuspagonombre;
    }

    public String getFormapagotipo() {
        return formapagotipo;
    }

    public void setFormapagotipo(String formapagotipo) {
        this.formapagotipo = formapagotipo;
    }

    public int getFormapagoint() {
        return formapagoint;
    }

    public void setFormapagoint(int formapagoint) {
        this.formapagoint = formapagoint;
    }

    public String getTotalAbonarMX() {
        totalAbonarMX = formatoMoneda.format(totalAbonar);
        return totalAbonarMX;
    }

    public String getAbonoAbonoMX() {
        abonoAbonoMX = formatoMoneda.format(abonoAbono);
        return abonoAbonoMX;
    }

    public String getSaldoAbonoMX() {
        saldoAbonoMX = formatoMoneda.format(saldoAbono);
        return saldoAbonoMX;
    }
}
