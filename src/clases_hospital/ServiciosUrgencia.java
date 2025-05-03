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
public class ServiciosUrgencia {
    int id;
    String nombre;
    String descripcion;
    double costo;
    
    public ServiciosUrgencia(int id,String nombre, String descripcion, double costo){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.id = id;
    }
    public ServiciosUrgencia(int id, String nombre){
        this.id = id;
        this.nombre =nombre;
    }
    public ServiciosUrgencia(String nombre){
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

//    @Override
//    public String toString() {
//        return "ServiciosUrgencia{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", costo=" + costo + '}';
//    }
    @Override
    public String toString() {
        return nombre.toUpperCase();
    }
}
