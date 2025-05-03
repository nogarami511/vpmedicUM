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
public class Habitacion {
    //datos para el tipo de habitacion
   int idTipo;
   String tipo;
   //datos para la habitacion
   int id;
   int numeroHabitacion;
   int piso;
   int prioridad;
   int estatus;
   String observaciones;
   
   int usuarioModificacion;
   
   // datos para la asignacion del paciente
   int id_asignacion;
   int id_paciente;
   String nombre_paciente;
   public Habitacion(){
   }
   public Habitacion(int id, String tipo){
       this.idTipo = id;
       this.tipo = tipo;
   }
   public Habitacion(int id, int idTipo, int numeroHabitacion,int piso, int prioridad, int estatus, String observaciones, String tipo){
       this.id = id;
       this.idTipo = idTipo;
       this.numeroHabitacion = numeroHabitacion;
       this.piso = piso;
       this.prioridad = prioridad;
       this.estatus = estatus;
       this.observaciones = observaciones;
       this.tipo = tipo;
   }
   //constructor para llenar la tabla de asignacion de habitaciones(vista habitaciones tabla)
   public Habitacion(int id_asignacion,int id_habitacion,int id_tipo_habitacion, int id_paciente,int id_estatus,String tipo, int numero_habitacion,String nombre_paciente, int piso,String observaciones){
       this.id_asignacion =id_asignacion;
       this.id = id_habitacion;
       this.idTipo = id_tipo_habitacion;
       this.id_paciente = id_paciente;
       this.estatus = id_estatus;
       this.tipo = tipo;
       this.numeroHabitacion = numero_habitacion;
       this.nombre_paciente = nombre_paciente;
       this.piso = piso;
       this.observaciones = observaciones;
       
   }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    @Override
    public String toString() {
        return "Habitacion: " + numeroHabitacion;
    }
   
}
