/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles_um;

/**
 *
 * @author olver
 */
public class TipoSexo {
    private int idTipoSexo;
    private String Tipo;
    private boolean estatus;

    public TipoSexo() {
    }

    public TipoSexo(int idTipoSexo, String Tipo, boolean estatus) {
        this.idTipoSexo = idTipoSexo;
        this.Tipo = Tipo;
        this.estatus = estatus;
    }

    public int getIdTipoSexo() {
        return idTipoSexo;
    }

    public void setIdTipoSexo(int idTipoSexo) {
        this.idTipoSexo = idTipoSexo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return  Tipo ;
    }
    
    
}
