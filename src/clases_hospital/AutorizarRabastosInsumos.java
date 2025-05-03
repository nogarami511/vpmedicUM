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
public class AutorizarRabastosInsumos {
    
    private int id_AtorizarRabasotsInsumos, id_estatus, id_usario_autrorizacion;
    private String clave_reabastos, lista_id_rabastos, lista_rabastos, lista_id_reabastos_parciales, lista_rabastos_parciales, stringEstatus;
    Date fecha_autorizacion;
    SimpleDateFormat formatterFecha_autorizacion = new SimpleDateFormat("dd/MM/yyyy");

    public int getId_AtorizarRabasotsInsumos() {
        return id_AtorizarRabasotsInsumos;
    }

    public void setId_AtorizarRabasotsInsumos(int id_AtorizarRabasotsInsumos) {
        this.id_AtorizarRabasotsInsumos = id_AtorizarRabasotsInsumos;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }

    public int getId_usario_autrorizacion() {
        return id_usario_autrorizacion;
    }

    public void setId_usario_autrorizacion(int id_usario_autrorizacion) {
        this.id_usario_autrorizacion = id_usario_autrorizacion;
    }

    public String getClave_reabastos() {
        return clave_reabastos;
    }

    public void setClave_reabastos(String clave_reabastos) {
        this.clave_reabastos = clave_reabastos;
    }

    public String getLista_id_rabastos() {
        return lista_id_rabastos;
    }

    public void setLista_id_rabastos(String lista_id_rabastos) {
        this.lista_id_rabastos = lista_id_rabastos;
    }

    public String getLista_rabastos() {
        return lista_rabastos;
    }

    public void setLista_rabastos(String lista_rabastos) {
        this.lista_rabastos = lista_rabastos;
    }

    public String getLista_id_reabastos_parciales() {
        return lista_id_reabastos_parciales;
    }

    public void setLista_id_reabastos_parciales(String lista_id_reabastos_parciales) {
        this.lista_id_reabastos_parciales = lista_id_reabastos_parciales;
    }

    public String getLista_rabastos_parciales() {
        return lista_rabastos_parciales;
    }

    public void setLista_rabastos_parciales(String lista_rabastos_parciales) {
        this.lista_rabastos_parciales = lista_rabastos_parciales;
    }

    public String getStringEstatus() {
        return stringEstatus;
    }

    public void setStringEstatus(String stringEstatus) {
        this.stringEstatus = stringEstatus;
    }

    public void setFecha_autorizacion(Date fecha_autorizacion) {
        this.fecha_autorizacion = fecha_autorizacion;
    }

    public String getFormatterFecha_autorizacion() {
        return formatterFecha_autorizacion.format(fecha_autorizacion);
    }
    
}
