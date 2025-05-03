/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author gamae
 */
public class Medico {
    // id_tipo = 1(interno), 2(externo)
    int id_medico, id_estatus,id_tipo, idusuariomodificacion;
    String nombre, titulo, cedula, cedula_especialidad, especialidad, certificado, rfc, direccion, correo, lugar_nacimiento, curp,telefono;
    Date fecha_nacimiento;
    
    //constructor vacio ٩(˘◡˘)۶
    public Medico() {

    }

    // constructor para editar un medico （っ＾▿＾）(con el id) 
    public Medico(int id_medico, String nombre, String titulo, String cedula, String cedula_especialidad, String especialidad, String certificado, String telefono, String rfc,
            String direccion, String correo, Date fecha_nacimiento, String lugar_nacimiento, String curp, int id_estatus,int id_tipo) {
        this.id_medico = id_medico;                                             
        this.nombre = nombre;                                                   
        this.titulo = titulo;                                                   
        this.cedula = cedula;                                                   
        this.cedula_especialidad = cedula_especialidad;                         
        this.especialidad = especialidad;                                       
        this.certificado = certificado;                                         
        this.telefono = telefono;                                               
        this.rfc = rfc;
        this.direccion = direccion;
        this.correo = correo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lugar_nacimiento = lugar_nacimiento;
        this.curp = curp;
        this.id_estatus = id_estatus;
        this.id_tipo = id_tipo;
    }

    // constructor para Agregar un medico (>‿◠)/ (no lleva id) 
    public Medico(String nombre, String titulo, String cedula, String cedula_especialidad, String especialidad, String certificado, String telefono, String rfc,
            String direccion, String correo, Date fecha_nacimiento, String lugar_nacimiento, String curp, int id_estatus,int id_tipo) {
        this.nombre = nombre;
        this.titulo = titulo;
        this.cedula = cedula;
        this.cedula_especialidad = cedula_especialidad;
        this.especialidad = especialidad;
        this.certificado = certificado;
        this.telefono = telefono;
        this.rfc = rfc;
        this.direccion = direccion;
        this.correo = correo;
        this.fecha_nacimiento = fecha_nacimiento;
        this.lugar_nacimiento = lugar_nacimiento;
        this.curp = curp;
        this.id_estatus = id_estatus;
        this.id_tipo = id_tipo;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public int getIdusuariomodificacion() {
        return idusuariomodificacion;
    }

    public void setIdusuariomodificacion(int idusuariomodificacion) {
        this.idusuariomodificacion = idusuariomodificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCedula_especialidad() {
        return cedula_especialidad;
    }

    public void setCedula_especialidad(String cedula_especialidad) {
        this.cedula_especialidad = cedula_especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getLugar_nacimiento() {
        return lugar_nacimiento;
    }

    public void setLugar_nacimiento(String lugar_nacimiento) {
        this.lugar_nacimiento = lugar_nacimiento;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}
