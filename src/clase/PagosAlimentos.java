/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import java.sql.Timestamp;

/**
 *
 * @author alfar
 */
public class PagosAlimentos {
    private int idPagoAlimento;
    private String nombreCliente;
    private double precioUnitario;
    private int idFormaPago;
    private double descuento;
    private double subTotal;
    private double iva;
    private double total;
    private String formaPago;
    private int usuarioCobro;
    private Timestamp fechaPago;
    private Timestamp fechaModificacion;

    // Constructors
    public PagosAlimentos() {
    }

    public PagosAlimentos(String nombreCliente, double precioUnitario, int idFormaPago, double descuento,
            double subTotal, double iva, double total, String formaPago, int usuarioCobro, Timestamp fechaPago,
            Timestamp fechaModificacion) {
        this.nombreCliente = nombreCliente;
        this.precioUnitario = precioUnitario;
        this.idFormaPago = idFormaPago;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.iva = iva;
        this.total = total;
        this.formaPago = formaPago;
        this.usuarioCobro = usuarioCobro;
        this.fechaPago = fechaPago;
        this.fechaModificacion = fechaModificacion;
    }

    // Getters and Setters (You can generate these automatically in most IDEs)

    public int getIdPagoAlimento() {
        return idPagoAlimento;
    }

    public void setIdPagoAlimento(int idPagoAlimento) {
        this.idPagoAlimento = idPagoAlimento;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public int getUsuarioCobro() {
        return usuarioCobro;
    }

    public void setUsuarioCobro(int usuarioCobro) {
        this.usuarioCobro = usuarioCobro;
    }

    public Timestamp getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Timestamp fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
