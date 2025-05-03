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
 * @author Gerardo
 */
public class PaqueteAlimento {

    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
    int id;
    String nombre, descripcion, precioFormateado;
    double precio;
    int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        precioFormateado = formatoMoneda.format(precio);
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + precio;
    }

    public String getPrecioFormateado() {
        return precioFormateado;
    }

}
