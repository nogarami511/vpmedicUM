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
public class PaqueteMedico {

    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
    int id;
    String nombre, descripcion;
    double precio, factor_paquete;

    int idUsuario, id_tipo_procedimiento, id_quirofano, id_tipo_habitacion, id_procedimiento;
    String claveSAT, precioFormateado;

    int dias_hospitalizacion, numero_comidas, horas_tolerancia, horasHospitalizacion, estatusMovimiento;

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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getId_tipo_procedimiento() {
        return id_tipo_procedimiento;
    }

    public void setId_tipo_procedimiento(int id_tipo_procedimiento) {
        this.id_tipo_procedimiento = id_tipo_procedimiento;
    }

    public int getId_quirofano() {
        return id_quirofano;
    }

    public void setId_quirofano(int id_quirofano) {
        this.id_quirofano = id_quirofano;
    }

    public int getId_tipo_habitacion() {
        return id_tipo_habitacion;
    }

    public void setId_tipo_habitacion(int id_tipo_habitacion) {
        this.id_tipo_habitacion = id_tipo_habitacion;
    }

    public String getClaveSAT() {
        return claveSAT;
    }

    public void setClaveSAT(String claveSAT) {
        this.claveSAT = claveSAT;
    }

    public int getId_procedimiento() {
        return id_procedimiento;
    }

    public void setId_procedimiento(int id_procedimiento) {
        this.id_procedimiento = id_procedimiento;
    }

    public int getDias_hospitalizacion() {
        return dias_hospitalizacion;
    }

    public void setDias_hospitalizacion(int dias_hospitalizacion) {
        this.dias_hospitalizacion = dias_hospitalizacion;
    }

    public int getNumero_comidas() {
        return numero_comidas;
    }

    public void setNumero_comidas(int numero_comidas) {
        this.numero_comidas = numero_comidas;
    }

    public int getHoras_tolerancia() {
        return horas_tolerancia;
    }

    public void setHoras_tolerancia(int horas_tolerancia) {
        this.horas_tolerancia = horas_tolerancia;
    }

    public int getHorasHospitalizacion() {
        return horasHospitalizacion;
    }

    public void setHorasHospitalizacion(int horasHospitalizacion) {
        this.horasHospitalizacion = horasHospitalizacion;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getPrecioFormateado() {
        return precioFormateado;
    }

    public int getEstatusMovimiento() {
        return estatusMovimiento;
    }

    public void setEstatusMovimiento(int estatusMovimiento) {
        this.estatusMovimiento = estatusMovimiento;
    }

    public double getFactor_paquete() {
        return factor_paquete;
    }

    public void setFactor_paquete(double factor_paquete) {
        this.factor_paquete = factor_paquete;
    }

}
