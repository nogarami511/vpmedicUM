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
public class TipoTarjeta {
    private int idTipoTarjeta;
    private String nombreTipoTarjeta;

    public TipoTarjeta() {
        // Constructor vacío
    }

    public TipoTarjeta(int idTipoTarjeta, String nombreTipoTarjeta) {
        this.idTipoTarjeta = idTipoTarjeta;
        this.nombreTipoTarjeta = nombreTipoTarjeta;
    }

    public int getIdTipoTarjeta() {
        return idTipoTarjeta;
    }

    public void setIdTipoTarjeta(int idTipoTarjeta) {
        this.idTipoTarjeta = idTipoTarjeta;
    }

    public String getNombreTipoTarjeta() {
        return nombreTipoTarjeta;
    }

    public void setNombreTipoTarjeta(String nombreTipoTarjeta) {
        this.nombreTipoTarjeta = nombreTipoTarjeta;
    }

    @Override
    public String toString() {
        return nombreTipoTarjeta;
    }
    
    

}
