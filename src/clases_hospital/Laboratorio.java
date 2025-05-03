/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author alfar
 */
public class Laboratorio {
    private int idLaboratorio;
    private String nombreComercial;
    private String razonSocial;
    private String direccion;
    private String rfc;
    
    public Laboratorio(){}
    
    public Laboratorio(int idLaboratorio, String nombreComercial, String razonSocial, String direccion, String rfc) {
        this.idLaboratorio = idLaboratorio;
        this.nombreComercial = nombreComercial;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.rfc = rfc;
    }

    public int getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(int idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Override
    public String toString() {
        return "Laboratorio{" + "idLaboratorio=" + idLaboratorio + ", nombreComercial=" + nombreComercial + ", razonSocial=" + razonSocial + ", direccion=" + direccion + ", rfc=" + rfc + '}';
    }
    
    
    
}
