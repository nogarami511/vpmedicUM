/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author alfar
 */
public class CitaQuirofano {

    private int id;
    private int idQuirofano;
    private int idMedico;
    private int idPaciente;
    private int idTipoHabitacion;
    private String idServiciosAdicionales;
    private String cirugia;
    private String contacto;
    private Time horaInicio;
    private Time horaFin;
    private int duracionHora;
    private int duracionMinutos;
    private Date fechaCirugia;
    private String observaciones;
    private int idEstatusAgenda;
    private Date fechaHabitacionApartado;
    private Timestamp fechaModificacion;
    private int idUsuarioModificacion;
    private int idEstatusPanelInformacionQuirofano;
    private int id_folios;

    SimpleDateFormat formatterFechaCirugia = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatterFechaHabitacionApartado = new SimpleDateFormat("dd/MM/yyyy");
    
    private String quirofano;
    private String nombreMedico;
    private String nombrePaciente;
    private String duracion;
    
    private String listacirugias;
    private String listaserviciosadicionales;
    
    private Date fecha_ingreso_quirofano;
    private Time hora_ingreso_quirofano;
    private Time hora_salida_quirofano;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuirofano() {
        return idQuirofano;
    }

    public void setIdQuirofano(int idQuirofano) {
        this.idQuirofano = idQuirofano;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(int idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public String getIdServiciosAdicionales() {
        return idServiciosAdicionales;
    }

    public void setIdServiciosAdicionales(String idServiciosAdicionales) {
        this.idServiciosAdicionales = idServiciosAdicionales;
    }

    public String getCirugia() {
        return cirugia;
    }

    public void setCirugia(String cirugia) {
        this.cirugia = cirugia;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public int getDuracionHora() {
        return duracionHora;
    }

    public void setDuracionHora(int duracionHora) {
        this.duracionHora = duracionHora;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public Date getFechaCirugia() {
        return fechaCirugia;
    }

    public void setFechaCirugia(Date fechaCirugia) {
        this.fechaCirugia = fechaCirugia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdEstatusAgenda() {
        return idEstatusAgenda;
    }

    public void setIdEstatusAgenda(int idEstatusAgenda) {
        this.idEstatusAgenda = idEstatusAgenda;
    }

    public Date getFechaHabitacionApartado() {
        return fechaHabitacionApartado;
    }

    public void setFechaHabitacionApartado(Date fechaHabitacionApartado) {
        this.fechaHabitacionApartado = fechaHabitacionApartado;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public int getIdEstatusPanelInformacionQuirofano() {
        return idEstatusPanelInformacionQuirofano;
    }

    public void setIdEstatusPanelInformacionQuirofano(int idEstatusPanelInformacionQuirofano) {
        this.idEstatusPanelInformacionQuirofano = idEstatusPanelInformacionQuirofano;
    }

    public String getFormatterFechaCirugia() {
        return formatterFechaCirugia.format(fechaCirugia);
    }

    public String getFormatterFechaHabitacionApartado() {
        return formatterFechaHabitacionApartado.format(fechaHabitacionApartado);
    }

    public String getQuirofano() {
        return quirofano;
    }

    public void setQuirofano(String quirofano) {
        this.quirofano = quirofano;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getListacirugias() {
        return listacirugias;
    }

    public void setListacirugias(String listacirugias) {
        this.listacirugias = listacirugias;
    }

    public String getListaserviciosadicionales() {
        return listaserviciosadicionales;
    }

    public void setListaserviciosadicionales(String listaserviciosadicionales) {
        this.listaserviciosadicionales = listaserviciosadicionales;
    }
    
    public String getDuracion(){
        
        if(duracionHora < 10){
            duracion = "0"+duracionHora;
        }else{
            duracion = ""+ duracionHora;
        }
        
        if(duracionMinutos < 10){
            duracion = duracion + ":" + "0" + duracionMinutos;
        }else {
             duracion = duracion + ":" + duracionMinutos;
        }
        
        return duracion;
    }

    public int getId_folios() {
        return id_folios;
    }

    public void setId_folios(int id_folios) {
        this.id_folios = id_folios;
    }

    public Date getFecha_ingreso_quirofano() {
        return fecha_ingreso_quirofano;
    }

    public void setFecha_ingreso_quirofano(Date fecha_ingreso_quirofano) {
        this.fecha_ingreso_quirofano = fecha_ingreso_quirofano;
    }

    public Time getHora_ingreso_quirofano() {
        return hora_ingreso_quirofano;
    }

    public void setHora_ingreso_quirofano(Time hora_ingreso_quirofano) {
        this.hora_ingreso_quirofano = hora_ingreso_quirofano;
    }

    public Time getHora_salida_quirofano() {
        return hora_salida_quirofano;
    }

    public void setHora_salida_quirofano(Time hora_salida_quirofano) {
        this.hora_salida_quirofano = hora_salida_quirofano;
    }

    @Override
    public String toString() {
        return nombrePaciente;
    }
    
}
