/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author Gerardo
 */
public class CuentaPaciente2 {

    private int idPaciente;
    private int idFolio;
    private String nombrePaciente;
    private String tipohabitacion;
    private int idPaquete;
    private String nombrepaquete;
    private boolean hemodinamia;

    public CuentaPaciente2() {
    }

    public CuentaPaciente2(int idPaciente, int idFolio, String nombrePaciente, String tipohabitacion, int idPaquete, String nombrepaquete) {
        this.idPaciente = idPaciente;
        this.idFolio = idFolio;
        this.nombrePaciente = nombrePaciente;
        this.tipohabitacion = tipohabitacion;
        this.idPaquete = idPaquete;
        this.nombrepaquete = nombrepaquete;
    }
    
    public CuentaPaciente2(int idPaciente, int idFolio, String nombrePaciente, String tipohabitacion, int idPaquete, String nombrepaquete, boolean hemodinamia) {
        this.idPaciente = idPaciente;
        this.idFolio = idFolio;
        this.nombrePaciente = nombrePaciente;
        this.tipohabitacion = tipohabitacion;
        this.idPaquete = idPaquete;
        this.nombrepaquete = nombrepaquete;
        this.hemodinamia = hemodinamia;
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

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getTipohabitacion() {
        return tipohabitacion;
    }

    public void setTipohabitacion(String tipohabitacion) {
        this.tipohabitacion = tipohabitacion;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getNombrepaquete() {
        return nombrepaquete;
    }

    public void setNombrepaquete(String nombrepaquete) {
        this.nombrepaquete = nombrepaquete;
    }

    public boolean isHemodinamia() {
        return hemodinamia;
    }

    public void setHemodinamia(boolean hemodinamia) {
        this.hemodinamia = hemodinamia;
    }

}
