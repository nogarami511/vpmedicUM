/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author alfar
 */
public class Inventario {

    int id, id_insumo, ubicacion, estatus_rabasto, id_usuario_mod ;
    String nombre, tipo, descripcion, observaciones, formatoCosto,nombreCompleto, presentacion, lote, formula;
    Date fecha_mod;
    double totalExistencia, maximos, minimos, falta, precioUnitario, precioUnitarioPaquete;
    int idtipo_insumo;
    
    int idFamilia;
    
    String clave;
 
    public Inventario(){
        
    }

    public double getTotalExistencia() {
        return totalExistencia;
    }

    public void setTotalExistencia(double totalExistencia) {
        this.totalExistencia = totalExistencia;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFormatoCosto() {
        return formatoCosto;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public int getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getEstatus_rabasto() {
        return estatus_rabasto;
    }

    public void setEstatus_rabasto(int estatus_rabasto) {
        this.estatus_rabasto = estatus_rabasto;
    }

    public int getId_usuario_mod() {
        return id_usuario_mod;
    }

    public void setId_usuario_mod(int id_usuario_mod) {
        this.id_usuario_mod = id_usuario_mod;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Date fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMaximos() {
        return maximos;
    }

    public void setMaximos(double maximos) {
        this.maximos = maximos;
    }

    public double getMinimos() {
        return minimos;
    }

    public void setMinimos(double minimos) {
        this.minimos = minimos;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public double getFalta() {
        return falta;
    }

    public void setFalta(double falta) {
        this.falta = falta;
    }

    public int getIdtipo_insumo() {
        return idtipo_insumo;
    }

    public void setIdtipo_insumo(int idtipo_insumo) {
        this.idtipo_insumo = idtipo_insumo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioUnitarioPaquete() {
        return precioUnitarioPaquete;
    }

    public void setPrecioUnitarioPaquete(double precioUnitarioPaquete) {
        this.precioUnitarioPaquete = precioUnitarioPaquete;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
