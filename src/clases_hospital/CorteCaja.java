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
public class CorteCaja {
    
    int id_corte_caja, id_usuario;
    String hora_inicio, hora_fin, nombre_usuario;
    double inicio_efectivo;
    boolean validar;

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public int getId_corte_caja() {
        return id_corte_caja;
    }

    public void setId_corte_caja(int id_corte_caja) {
        this.id_corte_caja = id_corte_caja;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public double getInicio_efectivo() {
        return inicio_efectivo;
    }

    public void setInicio_efectivo(double inicio_efectivo) {
        this.inicio_efectivo = inicio_efectivo;
    }

    public boolean isValidar() {
        return validar;
    }

    public void setValidar(boolean validar) {
        this.validar = validar;
    }

    @Override
    public String toString() {
        return id_corte_caja + " " + nombre_usuario; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
