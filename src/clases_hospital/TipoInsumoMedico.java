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
public class TipoInsumoMedico {
    private int idTipoInsumoMedico;
    private String nombreTipoInsumoMedico;

    public int getIdTipoInsumoMedico() {
        return idTipoInsumoMedico;
    }

    public void setIdTipoInsumoMedico(int idTipoInsumoMedico) {
        this.idTipoInsumoMedico = idTipoInsumoMedico;
    }

    public String getNombreTipoInsumoMedico() {
        return nombreTipoInsumoMedico;
    }

    public void setNombreTipoInsumoMedico(String nombreTipoInsumoMedico) {
        this.nombreTipoInsumoMedico = nombreTipoInsumoMedico;
    }

    @Override
    public String toString() {
        return nombreTipoInsumoMedico;
    }
}
