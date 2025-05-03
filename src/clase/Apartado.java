/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import java.sql.Date;
import java.text.NumberFormat;

/**
 *
 * @author PC
 */
public class Apartado {

    String razonSocial, clave, numero, piso, dimensiones;
    Double precio, pago; //1678092.12
    Date fecha;
    String precioFormat, pagoFormat;//$1,678,092.12

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        precioFormat = formatoImporte.format(precio);
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        pagoFormat = formatoImporte.format(pago);
    }

    public String getPrecioFormat() {
        return precioFormat;
    }

    public String getPagoFormat() {
        return pagoFormat;
    }

}
