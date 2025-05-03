/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author alfar
 */
public class Compras {
    
    private int id_insumo, cantidad;
    private String nombre_insumo, tipo_insumo, clave_peidodo;
    private double costo;

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre_insumo() {
        return nombre_insumo;
    }

    public void setNombre_insumo(String nombre_insumo) {
        this.nombre_insumo = nombre_insumo;
    }

    public String getTipo_insumo() {
        return tipo_insumo;
    }

    public void setTipo_insumo(String tipo_insumo) {
        this.tipo_insumo = tipo_insumo;
    }

    public String getClave_peidodo() {
        return clave_peidodo;
    }

    public void setClave_peidodo(String clave_peidodo) {
        this.clave_peidodo = clave_peidodo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
}
