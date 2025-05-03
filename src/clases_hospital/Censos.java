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
public class Censos {
    private int numeroHabitacion;
    private String nombrePaciente;
    private int edad;
    private String nombreMedico;
    private String fechaIngreso;
    private int diasInternado;
    private String diagnosticoCenso;
    private String planCenso;

    public Censos() {
    }

    public Censos(int numeroHabitacion, String nombrePaciente, int edad, String nombreMedico, String fechaIngreso, int diasInternado, String diagnosticoCenso, String planCenso) {
        this.numeroHabitacion = numeroHabitacion;
        this.nombrePaciente = nombrePaciente;
        this.edad = edad;
        this.nombreMedico = nombreMedico;
        this.fechaIngreso = fechaIngreso;
        this.diasInternado = diasInternado;
        this.diagnosticoCenso = diagnosticoCenso;
        this.planCenso = planCenso;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getDiasInternado() {
        return diasInternado;
    }

    public void setDiasInternado(int diasInternado) {
        this.diasInternado = diasInternado;
    }

    public String getDiagnosticoCenso() {
        return diagnosticoCenso;
    }

    public void setDiagnosticoCenso(String diagnosticoCenso) {
        this.diagnosticoCenso = diagnosticoCenso;
    }

    public String getPlanCenso() {
        return planCenso;
    }

    public void setPlanCenso(String planCenso) {
        this.planCenso = planCenso;
    }
    
}
