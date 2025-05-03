/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author alfar
 */
public class AgendaHabitacion {
    
    private int id, id_paciente, usuario_mofificacion, id_estatus;
    private String nombre_paciente, tipo_habitacion, hora_apartado, hora_salida, observaciones;
    private Date fecha_apartado, fecha_salida;
    SimpleDateFormat formatter_fecha_apartado = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatter_fecha_salida = new SimpleDateFormat("dd/MM/yyyy");
    private double abonos, montoTotal, porPagar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getUsuario_mofificacion() {
        return usuario_mofificacion;
    }

    public void setUsuario_mofificacion(int usuario_mofificacion) {
        this.usuario_mofificacion = usuario_mofificacion;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }

    public String getTipo_habitacion() {
        return tipo_habitacion;
    }

    public void setTipo_habitacion(String tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    public String getHora_apartado() {
        return hora_apartado;
    }

    public void setHora_apartado(String hora_apartado) {
        this.hora_apartado = hora_apartado;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFecha_apartado() {
        return fecha_apartado;
    }

    public void setFecha_apartado(Date fecha_apartado) {
        this.fecha_apartado = fecha_apartado;
    }

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public double getAbonos() {
        return abonos;
    }

    public void setAbonos(double abonos) {
        this.abonos = abonos;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getPorPagar() {
        return porPagar;
    }

    public void setPorPagar(double porPagar) {
        this.porPagar = porPagar;
    }

    public String getFormatter_fecha_apartado() {
        return formatter_fecha_apartado.format(fecha_apartado);
    }

    public String getFormatter_fecha_salida() {
        return formatter_fecha_salida.format(fecha_salida);
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }
    
}
