/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Gerardo
 */
public class Paciente {

    int idPaciente, edad, idTipoSangre, idMedico, usuarioModificacion, idfolio;
    String nombre, telefono, domicilio, procedencia, sexo, ocupacion, religion, curp, nombreMedico, tipoSangre;
    Date fechaNacimiento;
    Timestamp fechaModificaciones;

    String fechaNacimientoString;

    String folio;
    int id_estatus_hospitalizacion;
    
    boolean urgencias;
    String habitacion;
    String tipoHabitacion; 
    
    int id_paquete;
    int id_indicaP;

    public Paciente() {
    }

    public Paciente(int idPaciente, String nombre) { // Esto es para poder hacer el buscador por nombre
        this.idPaciente = idPaciente;
        this.nombre = nombre;
    }

    public Paciente(int idPaciente, String nombre, String telefono) { // Esto es para poder hacer el buscador por nombre
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdTipoSangre() {
        return idTipoSangre;
    }

    public void setIdTipoSangre(int idTipoSangre) {
        this.idTipoSangre = idTipoSangre;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Timestamp getFechaModificaciones() {
        return fechaModificaciones;
    }

    public void setFechaModificaciones(Timestamp fechaModificaciones) {
        this.fechaModificaciones = fechaModificaciones;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public int getIdfolio() {
        return idfolio;
    }

    public void setIdfolio(int idfolio) {
        this.idfolio = idfolio;
    }

    public void setFechaNacimientoString(String fechaNacimientoString) {
        this.fechaNacimientoString = fechaNacimientoString;
    }

    public String getFechaNacimientoString() {
        return fechaNacimientoString;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getId_estatus_hospitalizacion() {
        return id_estatus_hospitalizacion;
    }

    public void setId_estatus_hospitalizacion(int id_estatus_hospitalizacion) {
        this.id_estatus_hospitalizacion = id_estatus_hospitalizacion;
    }

    public boolean isUrgencias() {
        return urgencias;
    }

    public void setUrgencias(boolean urgencias) {
        this.urgencias = urgencias;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(int id_paquete) {
        this.id_paquete = id_paquete;
    }

    @Override
    public String toString() {
        return nombre.toUpperCase();
    }

}
