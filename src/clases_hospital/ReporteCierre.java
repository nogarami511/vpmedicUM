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
public class ReporteCierre {
    
    String folio,fecha,hora,paciente,medico,habitacion,tipoPaquete,formaDePago,concepto;
    double depocito;
    double montoHaastaElMomento;
    double saldoACubrir;
    int idPaquete;

    public ReporteCierre(String folio, String fecha, String hora, String paciente, String medico, String habitacion, String tipoPaquete, String formaDePago, String concepto, double depocito, double montoHaastaElMomento, double saldoACubrir, int idPaquete) {
        this.folio = folio;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.medico = medico;
        this.habitacion = habitacion;
        this.tipoPaquete = tipoPaquete;
        this.formaDePago = formaDePago;
        this.concepto = concepto;
        this.depocito = depocito;
        this.montoHaastaElMomento = montoHaastaElMomento;
        this.saldoACubrir = saldoACubrir;
        this.idPaquete = idPaquete;
    }
    
    
    
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getTipoPaquete() {
        return tipoPaquete;
    }

    public void setTipoPaquete(String tipoPaquete) {
        this.tipoPaquete = tipoPaquete;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getDepocito() {
        return depocito;
    }

    public void setDepocito(double depocito) {
        this.depocito = depocito;
    }

    public double getMontoHaastaElMomento() {
        return montoHaastaElMomento;
    }

    public void setMontoHaastaElMomento(double montoHaastaElMomento) {
        this.montoHaastaElMomento = montoHaastaElMomento;
    }

    public double getSaldoACubrir() {
        return saldoACubrir;
    }

    public void setSaldoACubrir(double saldoACubrir) {
        this.saldoACubrir = saldoACubrir;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    @Override
    public String toString() {
        return "ReporteCierre{" + "folio=" + folio + ", fecha=" + fecha + ", hora=" + hora + ", paciente=" + paciente + ", medico=" + medico + ", habitacion=" + habitacion + ", tipoPaquete=" + tipoPaquete + ", formaDePago=" + formaDePago + ", concepto=" + concepto + ", depocito=" + depocito + ", montoHaastaElMomento=" + montoHaastaElMomento + ", saldoACubrir=" + saldoACubrir + ", idPaquete=" + idPaquete + '}';
    }

    
    
}
