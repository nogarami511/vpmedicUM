/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Timestamp;

/**
 *
 * @author Gerardo
 */
public class OtrosIngresos {

    private int id;
    private int idProveedor;
    private String motivo;
    private String observaciones;
    private double monto;
    private Timestamp fechaIngreso;
    private int estatus;
    private int usuarioMod;
    private Timestamp fechaMod;
    private String nombreProveedor;
    private double cantidad;

    public OtrosIngresos() {

    }

    public OtrosIngresos(int idProveedor, String motivo, String observaciones, double monto, int estatus) {
        this.idProveedor = idProveedor;
        this.motivo = motivo;
        this.observaciones = observaciones;
        this.monto = monto;
        this.estatus = estatus;
        this.usuarioMod = usuarioMod;
        this.fechaMod = fechaMod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getUsuarioMod() {
        return usuarioMod;
    }

    public void setUsuarioMod(int usuarioMod) {
        this.usuarioMod = usuarioMod;
    }

    public Timestamp getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Timestamp fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    @Override
    public String toString() {
        return "OtrosIngresos{" + "id=" + id + ", idProveedor=" + idProveedor + ", motivo=" + motivo + ", observaciones=" + observaciones + ", monto=" + monto + ", fechaIngreso=" + fechaIngreso + ", estatus=" + estatus + ", usuarioMod=" + usuarioMod + ", fechaMod=" + fechaMod + ", nombreProveedor=" + nombreProveedor + '}';
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidad() {
        return cantidad;
    }

}
