/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author gamae
 */
public class Folio {

    int id, idPaciente, idEstatus, idUsuarioModificacion, id_estatus_folio, id_paquete;
    String folio;
    Date fecha;
    double montoHastaElMomento, totalDeAbono, saldoACubrir, precioUnitario, cantidad, adeudo;

   
    String EstatusTexto, nombre, tipo;
    Timestamp fechaModificacion;
    int horasTolerancia;

    int id_estatus_pago_deposito;
    double cosoto_deposito;
    String fecha_salidaString;
    int numero_habitacion;

    String nombre_paciente, medico_nombre;
    int id_medico;

    boolean urgencias;
    boolean upgrade;
    boolean paquete;
      int id_habitacion, id_estatus_hospitalizacion;
    Timestamp fecha_ingreso;
    Timestamp fecha_salida;
    String fecha_ingresoString;
    
    int dias, horas;

    String fecha_Ingreso_String, nombre_Paquete_String;

    public void setFecha_ingresoString(String fecha_ingresoString) {
        this.fecha_ingresoString = fecha_ingresoString;
    }

    public void setFecha_salidaString(String fecha_salidaString) {
        this.fecha_salidaString = fecha_salidaString;
    }

    public void setFecha_Ingreso_String(String fecha_Ingreso_String) {
        this.fecha_Ingreso_String = fecha_Ingreso_String;
    }

  

    public String getFecha_ingresoString() {
      
        return fecha_ingresoString;
    }

    public String getFecha_salidaString() {
       
        return fecha_salidaString;
    }
    

    //id, nombre, fecha_ingreso, nombre_Paquete_String, dias, horas, paquete
    public Folio() {
    }
    
     public double getAdeudo() {
        return adeudo;
    }

    public void setAdeudo(double adeudo) {
        this.adeudo = adeudo;
    }
    
    public Timestamp getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Timestamp fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public Folio(int id, String folio, int idPaciente, Date fecha, double monto, double totalAbono, double saldoTotalAcubrir, int idEstatus) {
        this.id = id;
        this.folio = folio;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.montoHastaElMomento = monto;
        this.totalDeAbono = totalAbono;
        this.saldoACubrir = saldoTotalAcubrir;
        this.idEstatus = idEstatus;
    }

    public Folio(String folio, String nombre, Date fecha, double montoHastaElMomento, String tipo, double saldoACubrir, double totalDeHabono) {
        this.folio = folio;
        this.nombre = nombre;
        this.fecha = fecha;
        this.montoHastaElMomento = montoHastaElMomento;
        this.tipo = tipo;
        this.saldoACubrir = saldoACubrir;
        this.totalDeAbono = totalDeHabono;
    }

    public Folio(int idPaciente, int idFolio, String folio, String nombrePaciente) {
        this.idPaciente = idPaciente;
        this.id = idFolio;
        this.folio = folio;
        this.nombre = nombrePaciente;
    }

    //Constructor para llenar la tabla de agregar consumo a paciente
    public Folio(int id, String nombre, double precioUnitario, double cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;

    }

    public Folio(int idPaciente, int idFolio, String folio, String nombrePaciente, int idPaquete) {
        this.idPaciente = idPaciente;
        this.id = idFolio;
        this.folio = folio;
        this.nombre = nombrePaciente;
        this.id_paquete = idPaquete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getFolio() {
        return folio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMontoHastaElMomento() {
        return montoHastaElMomento;
    }

    public void setMontoHastaElMomento(double montoHastaElMomento) {
        this.montoHastaElMomento = montoHastaElMomento;
    }

    public double getTotalDeAbono() {
        return totalDeAbono;
    }

    public void setTotalDeAbono(double totalDeAbono) {
        this.totalDeAbono = totalDeAbono;
    }

    public double getSaldoACubrir() {
        return saldoACubrir;
    }

    public void setSaldoACubrir(double saldoACubrir) {
        this.saldoACubrir = saldoACubrir;
    }

    public String getEstatusTexto() {
        return EstatusTexto;
    }

    public void setEstatusTexto(String EstatusTexto) {
        this.EstatusTexto = EstatusTexto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getId_estatus_folio() {
        return id_estatus_folio;
    }

    public void setId_estatus_folio(int id_estatus_folio) {
        this.id_estatus_folio = id_estatus_folio;
    }

    public int getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(int id_paquete) {
        this.id_paquete = id_paquete;
    }

    public int getId_estatus_pago_deposito() {
        return id_estatus_pago_deposito;
    }

    public void setId_estatus_pago_deposito(int id_estatus_pago_deposito) {
        this.id_estatus_pago_deposito = id_estatus_pago_deposito;
    }

    public double getCosoto_deposito() {
        return cosoto_deposito;
    }

    public void setCosoto_deposito(double cosoto_deposito) {
        this.cosoto_deposito = cosoto_deposito;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(int id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public Timestamp getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Timestamp fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public int getId_estatus_hospitalizacion() {
        return id_estatus_hospitalizacion;
    }

    public void setId_estatus_hospitalizacion(int id_estatus_hospitalizacion) {
        this.id_estatus_hospitalizacion = id_estatus_hospitalizacion;
    }

    public int getNumero_habitacion() {
        return numero_habitacion;
    }

    public void setNumero_habitacion(int numero_habitacion) {
        this.numero_habitacion = numero_habitacion;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getMedico_nombre() {
        return medico_nombre;
    }

    public void setMedico_nombre(String medico_nombre) {
        this.medico_nombre = medico_nombre;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    @Override
    public String toString() {
        return nombre_paciente;
    }

    public int getHorasTolerancia() {
        return horasTolerancia;
    }

    public void setHorasTolerancia(int horasTolerancia) {
        this.horasTolerancia = horasTolerancia;
    }

    public boolean isUrgencias() {
        return urgencias;
    }

    public void setUrgencias(boolean urgencias) {
        this.urgencias = urgencias;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public boolean isPaquete() {
        return paquete;
    }

    public void setPaquete(boolean paquete) {
        this.paquete = paquete;
    }

    public String getNombre_Paquete_String() {
        return nombre_Paquete_String;
    }

    public void setNombre_Paquete_String(String nombre_Paquete_String) {
        this.nombre_Paquete_String = nombre_Paquete_String;
    }

    public String getFecha_Ingreso_String() {
        Date date = new Date(fecha_ingreso.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        fecha_Ingreso_String = sdf.format(date);
        return fecha_Ingreso_String;
    }
    
}
