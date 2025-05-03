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
public class IndicacionesMedicas {

    private int id, id_paciente, id_habitacion, usuario_modificacion, id_estatus_folio, id_doctor;

    private String folio, nombre_paciente, id_lista_cuidados, hora_creacion;
    private Date fehca_creacion, fecha_nacimento;
    SimpleDateFormat formatFehca_Creacion = new SimpleDateFormat("dd/MM/yyyy"), formatfecha_nacimento = new SimpleDateFormat("dd/MM/yyyy");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public int getUsuario_modificacion() {
        return usuario_modificacion;
    }

    public void setUsuario_modificacion(int usuario_modificacion) {
        this.usuario_modificacion = usuario_modificacion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getId_lista_cuidados() {
        return id_lista_cuidados;
    }

    public void setId_lista_cuidados(String id_lista_cuidados) {
        this.id_lista_cuidados = id_lista_cuidados;
    }

    public String getHora_creacion() {
        return hora_creacion;
    }

    public void setHora_creacion(String hora_creacion) {
        this.hora_creacion = hora_creacion;
    }

    public void setFehca_creacion(Date fehca_creacion) {
        this.fehca_creacion = fehca_creacion;
    }

    public void setFecha_nacimento(Date fecha_nacimento) {
        this.fecha_nacimento = fecha_nacimento;
    }

    public String getFormatFehca_Creacion() {
        return formatFehca_Creacion.format(fehca_creacion);
    }

    public String getFormatfecha_nacimento() {
        return formatfecha_nacimento.format(fecha_nacimento);
    }

    public int getId_estatus_folio() {
        return id_estatus_folio;
    }

    public void setId_estatus_folio(int id_estatus_folio) {
        this.id_estatus_folio = id_estatus_folio;
    }

}
