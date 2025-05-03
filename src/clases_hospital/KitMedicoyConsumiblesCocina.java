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
public class KitMedicoyConsumiblesCocina {
    int id;
    int idIsnumo;
    double cantidad;
    int usuarioModificacion;
    Date fechaModificacion;
    int estatus;
    String nombreInsumo;
    
    int id_insumo_padre;
    double precioUnitario;

    public KitMedicoyConsumiblesCocina() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdIsnumo() {
        return idIsnumo;
    }

    public void setIdIsnumo(int idIsnumo) {
        this.idIsnumo = idIsnumo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFchaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public int getId_insumo_padre() {
        return id_insumo_padre;
    }

    public void setId_insumo_padre(int id_insumo_padre) {
        this.id_insumo_padre = id_insumo_padre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public String toString() {
        return nombreInsumo;
    }
    
}
