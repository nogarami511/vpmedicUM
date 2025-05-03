/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author gamae
 */
public class EstatusHabitacion {
    int id_estatus;
    String tipo;
    String observaciones;
    
    public EstatusHabitacion(){
    }
    public EstatusHabitacion(int id_estatus,String tipo, String observaciones){
        this.id_estatus = id_estatus;
        this.tipo = tipo;
        this.observaciones = observaciones;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
