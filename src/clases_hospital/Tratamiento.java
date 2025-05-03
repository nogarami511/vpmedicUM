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
 * @author gamae
 */
public class Tratamiento {
    int id, idInsumo, id_pasiente, cantidad, hora, minutos, dias, dosis_hasta_momento, id_estatus_tratamiento;
    String insumo, folio, hora_ultima_dosis, hora_proxima_dosis;
    Date  fecha_inicio, fecha_fin, fecha_ultima_dosis;
    SimpleDateFormat formatterFecha_inicio = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatterFecha_fin = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatterFecha_ultima_dosis = new SimpleDateFormat("dd/MM/yyyy");
    
    
    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_pasiente() {
        return id_pasiente;
    }

    public void setId_pasiente(int id_pasiente) {
        this.id_pasiente = id_pasiente;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getDosis_hasta_momento() {
        return dosis_hasta_momento;
    }

    public void setDosis_hasta_momento(int dosis_hasta_momento) {
        this.dosis_hasta_momento = dosis_hasta_momento;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getHora_ultima_dosis() {
        return hora_ultima_dosis;
    }

    public void setHora_ultima_dosis(String hora_ultima_dosis) {
        this.hora_ultima_dosis = hora_ultima_dosis;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setFecha_ultima_dosis(Date fecha_ultima_dosis) {
        this.fecha_ultima_dosis = fecha_ultima_dosis;
    }

    public String getFormatterFecha_inicio() {
        return formatterFecha_inicio.format(fecha_inicio);
    }

    public String getFormatterFecha_fin() {
        return formatterFecha_fin.format(fecha_fin);
    }

    public String getFormatterFecha_ultima_dosis() {
        return formatterFecha_ultima_dosis.format(fecha_ultima_dosis);
    }

    public String getHora_proxima_dosis() {
        return hora_proxima_dosis;
    }

    public void setHora_proxima_dosis(String hora_proxima_dosis) {
        this.hora_proxima_dosis = hora_proxima_dosis;
    }

    public int getId_estatus_tratamiento() {
        return id_estatus_tratamiento;
    }

    public void setId_estatus_tratamiento(int id_estatus_tratamiento) {
        this.id_estatus_tratamiento = id_estatus_tratamiento;
    }
    
}
