/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 *
 * @author alfar
 */
public class Indicasp {
    
    DecimalFormat df = new DecimalFormat("#.##");

    private int idIndicasp;                 ////////// ID_INDICAP
    private int idPaciente;                ///////////ID_PACIENTE
    private int idFolio;
    private int idUsuarioSolicitud;
    private int idUsuarioEntrega;
    private int estatusIndica;             ///// ESTATUS INDICA
    private int idUsuarioModificacion;
    private int statusValidacion;
    private int estatus;
    private int validacion;
    private int id_area;
    private int numero_habitacion;          //////NUMERO HABITACION
    private double porcentaje;

    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;    ///// FECHA MOD
    private Timestamp fechaEntrega;

    private String nombreUsuarioEntrega;
    private String nombreUsuarioSolicitud;

    private String nombrePaciente;          ///////// NOMBRE_ PACEINTE
    private String area;                    ///////// AREA STRING
    private String validacionString;        //////// validacion
    private String string_Estatus_Indica;
    private String stringPorcentaje;

    public int getNumero_habitacion() {
        return numero_habitacion;
    }

    public void setNumero_habitacion(int numero_habitacion) {
        this.numero_habitacion = numero_habitacion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getStringPorcentaje() {
        stringPorcentaje = df.format(porcentaje)+"%";
        return stringPorcentaje;
    }

    public String getValidacionString() {
        return validacionString;
    }

    public void setValidacionString(String validacionString) {
        this.validacionString = validacionString;
    }

    public int getStatusValidacion() {
        return statusValidacion;
    }

    public void setStatusValidacion(int statusValidacion) {
        this.statusValidacion = statusValidacion;
    }

    public Indicasp() {
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public int getValidacion() {
        return validacion;
    }

    public void setValidacion(int validacion) {
        this.validacion = validacion;
    }

    public int getIdIndicasp() {
        return idIndicasp;
    }

    public void setIdIndicasp(int idIndicasp) {
        this.idIndicasp = idIndicasp;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(int idFolio) {
        this.idFolio = idFolio;
    }

    public int getIdUsuarioSolicitud() {
        return idUsuarioSolicitud;
    }

    public void setIdUsuarioSolicitud(int idUsuarioSolicitud) {
        this.idUsuarioSolicitud = idUsuarioSolicitud;
    }

    public int getIdUsuarioEntrega() {
        return idUsuarioEntrega;
    }

    public void setIdUsuarioEntrega(int idUsuarioEntrega) {
        this.idUsuarioEntrega = idUsuarioEntrega;
    }

    public Timestamp getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Timestamp fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getEstatusIndica() {
        return estatusIndica;
    }

    public void setEstatusIndica(int estatusIndica) {
        this.estatusIndica = estatusIndica;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getNombreUsuarioEntrega() {
        return nombreUsuarioEntrega;
    }

    public void setNombreUsuarioEntrega(String nombreUsuarioEntrega) {
        this.nombreUsuarioEntrega = nombreUsuarioEntrega;
    }

    public String getNombreUsuarioSolicitud() {
        return nombreUsuarioSolicitud;
    }

    public void setNombreUsuarioSolicitud(String nombreUsuarioSolicitud) {
        this.nombreUsuarioSolicitud = nombreUsuarioSolicitud;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public String getString_Estatus_Indica() {
        return string_Estatus_Indica;
    }

    public void setString_Estatus_Indica(String string_Estatus_Indica) {
        this.string_Estatus_Indica = string_Estatus_Indica;
    }

    @Override
    public String toString() {
        return "Indicasp{" + "idIndicasp=" + idIndicasp + ", idPaciente=" + idPaciente + ", idFolio=" + idFolio + ", idUsuarioSolicitud=" + idUsuarioSolicitud + ", idUsuarioEntrega=" + idUsuarioEntrega + ", fechaEntrega=" + fechaEntrega + ", estatusIndica=" + estatusIndica + ", idUsuarioModificacion=" + idUsuarioModificacion + ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + ", nombreUsuarioEntrega=" + nombreUsuarioEntrega + ", nombreUsuarioSolicitud=" + nombreUsuarioSolicitud + ", estatus=" + estatus + ", area=" + area + ", id_area=" + id_area + '}';
    }

}
