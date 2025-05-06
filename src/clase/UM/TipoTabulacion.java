/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM;

/**
 *
 * @author PC
 */
public class TipoTabulacion {
    private int idTipoTabulacion;
    private String tipo;
    private boolean estatus;

    public TipoTabulacion() {
    }

    
    public TipoTabulacion(int idTipoTabulacion, String tipo, boolean estatus) {
        this.idTipoTabulacion = idTipoTabulacion;
        this.tipo = tipo;
        this.estatus = estatus;
    }

    public int getIdTipoTabulacion() {
        return idTipoTabulacion;
    }

    public void setIdTipoTabulacion(int idTipoTabulacion) {
        this.idTipoTabulacion = idTipoTabulacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return this.tipo;
    }
    
    
    
}
