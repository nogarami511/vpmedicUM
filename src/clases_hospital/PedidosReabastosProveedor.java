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
public class PedidosReabastosProveedor {
    private int id_pedidos_reabastos_proveedor, id_proveedor, id_estatus;
    private String clave_pedido, lista_profucto_pedir, lista_costos;
    private double costo_inventario, costo_proveedor, importe_inventario, importe_proveedor;

    public int getId_pedidos_reabastos_proveedor() {
        return id_pedidos_reabastos_proveedor;
    }

    public void setId_pedidos_reabastos_proveedor(int id_pedidos_reabastos_proveedor) {
        this.id_pedidos_reabastos_proveedor = id_pedidos_reabastos_proveedor;
    }

    public String getClave_pedido() {
        return clave_pedido;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public void setClave_pedido(String clave_pedido) {
        this.clave_pedido = clave_pedido;
    }

    public String getLista_profucto_pedir() {
        return lista_profucto_pedir;
    }

    public String getLista_costos() {
        return lista_costos;
    }

    public void setLista_costos(String lista_costos) {
        this.lista_costos = lista_costos;
    }

    public void setLista_profucto_pedir(String lista_profucto_pedir) {
        this.lista_profucto_pedir = lista_profucto_pedir;
    }

    public double getCosto_inventario() {
        return costo_inventario;
    }

    public void setCosto_inventario(double costo_inventario) {
        this.costo_inventario = costo_inventario;
    }

    public double getCosto_proveedor() {
        return costo_proveedor;
    }

    public void setCosto_proveedor(double costo_proveedor) {
        this.costo_proveedor = costo_proveedor;
    }

    public double getImporte_inventario() {
        return importe_inventario;
    }

    public void setImporte_inventario(double importe_inventario) {
        this.importe_inventario = importe_inventario;
    }

    public double getImporte_proveedor() {
        return importe_proveedor;
    }

    public void setImporte_proveedor(double importe_proveedor) {
        this.importe_proveedor = importe_proveedor;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }
    
}
