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
public class Tabulacion {
        private int id;
        private String nombre;
        private String nota;
        private boolean estatus;
        private TipoTabulacion tipoTabulacion = new TipoTabulacion();

    public Tabulacion() {
    }

    public Tabulacion(int id, String nombre, String nota, boolean estatus, TipoTabulacion tipoTabulacion) {
        this.id = id;
        this.nombre = nombre;
        this.nota = nota;
        this.estatus = estatus;
        this.tipoTabulacion = tipoTabulacion;
    }
        

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nota
     */
    public String getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * @return the estatus
     */
    public boolean getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public TipoTabulacion getTipoTabulacion() {
        return tipoTabulacion;
    }

    public void setTipoTabulacion(TipoTabulacion tipoTabulacion) {
        this.tipoTabulacion = tipoTabulacion;
    }

    @Override
    public String toString() {
        return "Tabulacion{" + "id=" + id + ", nombre=" + nombre + ", nota=" + nota + ", estatus=" + estatus + '}';
    }
        
        
        
}
