/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class Colaborador {

    private String nombre="@", nss="@", rfc="@", puesto="@", nacimiento="2000-01-01", id, ingreso="2000-01-01";

//    public Colaborador(String nombre, String nss, String rfc, String puesto, Date fNacimiento, Date fIngreso, int id) {
//        this.nombre = nombre;
//        this.nss = nss;
//        this.rfc = rfc;
//        this.puesto = puesto;
//        this.fNacimiento = fNacimiento;
//        this.fIngreso = fIngreso;
//        this.id = id;
//    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }


    
    
}
