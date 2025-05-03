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
public class GenerarReabastos {
    int id_reabastos, id_insumo, id_estatus_generar_reporte;
    String nombre, marca, presentacion, medida, reabastecer;
    double costo, costo_unidad_paquete, costo_provedor,falta, cajas, paquetes,cantidad_cajas_unidad_paquete, cantidad_unidadades_paquete;
    boolean generar;

    public GenerarReabastos() {
    }

    public GenerarReabastos(int id_reabastos, double falta, double cajas, double paquetes, double costo) {
        this.id_reabastos = id_reabastos;
        this.falta = falta;
        this.cajas = cajas;
        this.paquetes = paquetes;
        this.costo = costo;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public double getFalta() {
        return falta;
    }

    public void setFalta(double falta) {
        this.falta = falta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getReabastecer() {
        return reabastecer;
    }

    public void setReabastecer(String reabastecer) {
        this.reabastecer = reabastecer;
    }

    public boolean isGenerar() {
        return generar;
    }

    public void setGenerar(boolean generar) {
        this.generar = generar;
    }

    public double getCajas() {
        return cajas;
    }

    public void setCajas(double cajas) {
        this.cajas = cajas;
    }

    public double getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(double paquetes) {
        this.paquetes = paquetes;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getId_reabastos() {
        return id_reabastos;
    }

    public void setId_reabastos(int id_reabastos) {
        this.id_reabastos = id_reabastos;
    }

    public int getId_estatus_generar_reporte() {
        return id_estatus_generar_reporte;
    }

    public void setId_estatus_generar_reporte(int id_estatus_generar_reporte) {
        this.id_estatus_generar_reporte = id_estatus_generar_reporte;
    }

    public double getCantidad_cajas_unidad_paquete() {
        return cantidad_cajas_unidad_paquete;
    }

    public void setCantidad_cajas_unidad_paquete(double cantidad_cajas_unidad_paquete) {
        this.cantidad_cajas_unidad_paquete = cantidad_cajas_unidad_paquete;
    }

    public double getCantidad_unidadades_paquete() {
        return cantidad_unidadades_paquete;
    }

    public void setCantidad_unidadades_paquete(double cantidad_unidadades_paquete) {
        this.cantidad_unidadades_paquete = cantidad_unidadades_paquete;
    }

    public double getCosto_unidad_paquete() {
        return costo_unidad_paquete;
    }

    public void setCosto_unidad_paquete(double costo_unidad_paquete) {
        this.costo_unidad_paquete = costo_unidad_paquete;
    }

    public double getCosto_provedor() {
        return costo_provedor;
    }

    public void setCosto_provedor(double costo_provedor) {
        this.costo_provedor = costo_provedor;
    }
    
    @Override
    public String toString() {
        return "GenerarReabastos{" + "id_insumo=" + id_insumo + ", falta=" + falta + ", nombre=" + nombre + ", marca=" + marca + ", presentacion=" + presentacion + ", medida=" + medida + '}';
    }
        
}
