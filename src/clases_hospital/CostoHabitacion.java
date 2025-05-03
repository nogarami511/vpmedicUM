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
public class CostoHabitacion {

    int id;
    int idHabitacion;
    String nombre;//nombre del tipo de costo
    double precio;
    int horas;
    int minutos;
    int horasTolerancia;
    int minutosTolerancia;
    String tipoHabitacion;

    public CostoHabitacion() {
    }

    //constructor para agregar 
    public CostoHabitacion(int idHabitacion, String nombre, double precio, int horas, int minutos, int horasTolerancia, int minutosTolerancia) {
        this.idHabitacion = idHabitacion;
        this.nombre = nombre;
        this.precio = precio;
        this.horas = horas;
        this.minutos = minutos;
        this.horasTolerancia = horasTolerancia;
        this.minutosTolerancia = minutosTolerancia;
    }

    // constructor para agregar multiples costos en la tabla(vista agregar costo)
    public CostoHabitacion(int idHabitacion, String nombre, double precio, int horas, int minutos, int horasTolerancia, int minutosTolerancia, String tipoHabitacion) {
        this.idHabitacion = idHabitacion;
        this.nombre = nombre;
        this.precio = precio;
        this.horas = horas;
        this.minutos = minutos;
        this.horasTolerancia = horasTolerancia;
        this.minutosTolerancia = minutosTolerancia;
        this.tipoHabitacion = tipoHabitacion;
    }

    //constructor para editar
    public CostoHabitacion(int id, int idHabitacion, String nombre, double precio, int horas, int minutos, int horasTolerancia, int minutosTolerancia, String tipoHabitacion) {
        this.id = id;
        this.idHabitacion = idHabitacion;
        this.nombre = nombre;
        this.precio = precio;
        this.horas = horas;
        this.minutos = minutos;
        this.horasTolerancia = horasTolerancia;
        this.minutosTolerancia = minutosTolerancia;
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getHorasTolerancia() {
        return horasTolerancia;
    }

    public void setHorasTolerancia(int horasTolerancia) {
        this.horasTolerancia = horasTolerancia;
    }

    public int getMinutosTolerancia() {
        return minutosTolerancia;
    }

    public void setMinutosTolerancia(int minutosTolerancia) {
        this.minutosTolerancia = minutosTolerancia;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

}
