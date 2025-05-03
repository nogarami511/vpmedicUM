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
public class ComprasInternas {

    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
    private int idComprasInternasp;
    private String folioPedido;
    private String cliente;
    private Date fechaPedido;
    private double subtotal;
    private double descuento;
    private double impuesto;
    private double total;
    private int rubro;
    private int estatusCompra;
    private int idSolicitudesPagos;
    private int diasTranscurridos;
    private double montoSolicitado;
    private double montoAutorizado;
    private int usuarioModificacion;
    private Date fechaModificacion;

    boolean solicitar_compra;
    double monto_pagado;
    double saldo_saldo;

    int usuario_solicitud;
    private Date fecha_solocitud;
    int id_proveedor;

    double comision;
    double total_sin_comicion;

    String nombre_rubro;
    String razonSocial;

    int id_confirmacion_autorizacion;
    int id_estatus_autorizacion;

    int id_estatus_pagos_compras;
    String nombreComercial;
    String estatus_pago_compra;

    Proveedor proveedor;

    int id_formaPago;
    String forma_pago_nombre;

    String saldoFormateado, montoPagadoFormateado, montoAutorizadoFormateado, totalFormateado, montoSolicitadoFormateado;

    public ComprasInternas() {
    }

    public int getIdComprasInternasp() {
        return idComprasInternasp;
    }

    public void setIdComprasInternasp(int idComprasInternasp) {
        this.idComprasInternasp = idComprasInternasp;
    }

    public String getFolioPedido() {
        return folioPedido;
    }

    public void setFolioPedido(String folioPedido) {
        this.folioPedido = folioPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        totalFormateado = formatoMoneda.format(total);
        this.total = total;
    }

    public int getRubro() {
        return rubro;
    }

    public void setRubro(int rubro) {
        this.rubro = rubro;
    }

    public int getEstatusCompra() {
        return estatusCompra;
    }

    public void setEstatusCompra(int estatusCompra) {
        this.estatusCompra = estatusCompra;
    }

    public int getIdSolicitudesPagos() {
        return idSolicitudesPagos;
    }

    public void setIdSolicitudesPagos(int idSolicitudesPagos) {
        this.idSolicitudesPagos = idSolicitudesPagos;
    }

    public int getDiasTranscurridos() {
        return diasTranscurridos;
    }

    public void setDiasTranscurridos(int diasTranscurridos) {
        this.diasTranscurridos = diasTranscurridos;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(double montoSolicitado) {
        montoSolicitadoFormateado = formatoMoneda.format(montoSolicitado);
        this.montoSolicitado = montoSolicitado;
    }

    public double getMontoAutorizado() {
        return montoAutorizado;
    }

    public void setMontoAutorizado(double montoAutorizado) {
        montoAutorizadoFormateado = formatoMoneda.format(montoAutorizado);
        this.montoAutorizado = montoAutorizado;
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

    public boolean isSolicitar_compra() {
        return solicitar_compra;
    }

    public void setSolicitar_compra(boolean solicitar_compra) {
        this.solicitar_compra = solicitar_compra;
    }

    public double getMonto_pagado() {
        return monto_pagado;
    }

    public void setMonto_pagado(double monto_pagado) {
        montoPagadoFormateado = formatoMoneda.format(monto_pagado);
        this.monto_pagado = monto_pagado;
    }

    public double getSaldo_saldo() {
        return saldo_saldo;
    }

    public void setSaldo_saldo(double saldo_saldo) {
        saldoFormateado = formatoMoneda.format(saldo_saldo);
        this.saldo_saldo = saldo_saldo;
    }

    public int getUsuario_solicitud() {
        return usuario_solicitud;
    }

    public void setUsuario_solicitud(int usuario_solicitud) {
        this.usuario_solicitud = usuario_solicitud;
    }

    public Date getFecha_solocitud() {
        return fecha_solocitud;
    }

    public void setFecha_solocitud(Date fecha_solocitud) {
        this.fecha_solocitud = fecha_solocitud;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getTotal_sin_comicion() {
        return total_sin_comicion;
    }

    public void setTotal_sin_comicion(double total_sin_comicion) {
        this.total_sin_comicion = total_sin_comicion;
    }

    public String getNombre_rubro() {
        return nombre_rubro;
    }

    public void setNombre_rubro(String nombre_rubro) {
        this.nombre_rubro = nombre_rubro;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getId_confirmacion_autorizacion() {
        return id_confirmacion_autorizacion;
    }

    public void setId_confirmacion_autorizacion(int id_confirmacion_autorizacion) {
        this.id_confirmacion_autorizacion = id_confirmacion_autorizacion;
    }

    public int getId_estatus_autorizacion() {
        return id_estatus_autorizacion;
    }

    public void setId_estatus_autorizacion(int id_estatus_autorizacion) {
        this.id_estatus_autorizacion = id_estatus_autorizacion;
    }

    public int getId_estatus_pagos_compras() {
        return id_estatus_pagos_compras;
    }

    public void setId_estatus_pagos_compras(int id_estatus_pagos_compras) {
        this.id_estatus_pagos_compras = id_estatus_pagos_compras;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getEstatus_pago_compra() {
        return estatus_pago_compra;
    }

    public void setEstatus_pago_compra(String estatus_pago_compra) {
        this.estatus_pago_compra = estatus_pago_compra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getId_formaPago() {
        return id_formaPago;
    }

    public void setId_formaPago(int id_formaPago) {
        this.id_formaPago = id_formaPago;
    }

    public String getForma_pago_nombre() {
        return forma_pago_nombre;
    }

    public void setForma_pago_nombre(String forma_pago_nombre) {
        this.forma_pago_nombre = forma_pago_nombre;
    }

    //----------------------------------------------
    public String getSaldoFormateado() {
        return saldoFormateado;
    }

    public String getMontoPagadoFormateado() {
        return montoPagadoFormateado;
    }

    public String getMontoAutorizadoFormateado() {
        return montoAutorizadoFormateado;
    }

    public String getTotalFormateado() {
        return totalFormateado;
    }

    public String getMontoSolicitadoFormateado() {
        return montoSolicitadoFormateado;
    }

}
