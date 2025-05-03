/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author PC
 */
public class TipoHospitalizaciones {
    int id_tipo_hozpitalizacion;
    String nombre_tipo_hozpitalizacion;

    public TipoHospitalizaciones() {
    }

    public int getId_tipo_hozpitalizacion() {
        return id_tipo_hozpitalizacion;
    }

    public void setId_tipo_hozpitalizacion(int id_tipo_hozpitalizacion) {
        this.id_tipo_hozpitalizacion = id_tipo_hozpitalizacion;
    }

    public String getNombre_tipo_hozpitalizacion() {
        return nombre_tipo_hozpitalizacion;
    }

    public void setNombre_tipo_hozpitalizacion(String nombre_tipo_hozpitalizacion) {
        this.nombre_tipo_hozpitalizacion = nombre_tipo_hozpitalizacion;
    }

    @Override
    public String toString() {
        return nombre_tipo_hozpitalizacion;
    }
    
}
