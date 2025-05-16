/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM;

import controles_um.TipoSexo;
import java.sql.Date;

/**
 *
 * @author olver
 */
public class PacienteUM {

    private int idPaciente;
    private TipoTabulacion tipoTab = new TipoTabulacion();
    private String curp;
    private String nombrePaciente;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private TipoSexo tipoSexo = new TipoSexo();
    private java.sql.Date fechaNacimientoPaciente;
    private int edad;
    private UsuarioUm usuarioCreacion = new UsuarioUm();
    private boolean estatus;

    public PacienteUM() {
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public TipoTabulacion getTipoTab() {
        return tipoTab;
    }

    public void setTipoTab(TipoTabulacion tipoTab) {
        this.tipoTab = tipoTab;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public TipoSexo getTipoSexo() {
        return tipoSexo;
    }

    public void setTipoSexo(TipoSexo tipoSexo) {
        this.tipoSexo = tipoSexo;
    }

    public Date getFechaNacimientoPaciente() {
        return fechaNacimientoPaciente;
    }

    public void setFechaNacimientoPaciente(Date fechaNacimientoPaciente) {
        this.fechaNacimientoPaciente = fechaNacimientoPaciente;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public UsuarioUm getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(UsuarioUm usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return nombrePaciente + " " + apellidoPaterno + " " + apellidoMaterno;
    }

}
