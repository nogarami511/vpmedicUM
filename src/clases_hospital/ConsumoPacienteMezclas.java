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
public class ConsumoPacienteMezclas {

    int id_folio;
    int id_insumo;
    String nombre_paciente;
    String nombre_paquete;
    String insumo;
    String fecha;
    String hora; 
    String medico;
    String habitacion;
    double costoDepocito;
    double saldoACubrir;
    int consumo;
    int exedente;
    int devolucion;
    int cantidadEntregada;
    double cantidadPaquete;
    double monto;
    double precioUnitario;
    double subtotal;
    double exedenteMezcla;

    public ConsumoPacienteMezclas(int id_folio, String nombre_paciente, String insumo, int consumo, int exedente, int devolucion, double monto) {
        this.id_folio = id_folio;
        this.nombre_paciente = nombre_paciente;
        this.insumo = insumo;
        this.consumo = consumo;
        this.exedente = exedente;
        this.devolucion = devolucion;
        this.monto = monto;
    }

    // constructor para la vista de mezclas
    public ConsumoPacienteMezclas(int id_insumo, String insumo, int cantidadEntregada, int consumo, double cantidadPaquete, double exedenteMezcla, int devolucion) {
        this.id_insumo = id_insumo;
        this.insumo = insumo;
        this.cantidadEntregada = cantidadEntregada;
        this.consumo = consumo;
        this.cantidadPaquete = cantidadPaquete;
        this.exedenteMezcla = exedenteMezcla;
        this.devolucion = devolucion;
    }

    // constructor para la vista de cuenta paciente (visualizar folio)
    public ConsumoPacienteMezclas(int id_insumo, String insumo, int cantidadEntregada, int consumo, double cantidadPaquete, double exedenteMezcla, int devolucion, double precioUnitario, double subtotal) {
        this.id_insumo = id_insumo;
        this.insumo = insumo;
        this.cantidadEntregada = cantidadEntregada;
        this.consumo = consumo;
        this.cantidadPaquete = cantidadPaquete;
        this.exedenteMezcla = exedenteMezcla;
        this.devolucion = devolucion;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }
    // CONSTRUCTOR PARA LLENAR LOS DATOS DEL REPORTE de detalle
    public ConsumoPacienteMezclas(String fecha, String hora, String medico, String habitacion, double costoDepocito, double saldoACubrir){
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.habitacion = habitacion;
        this.costoDepocito = costoDepocito;
        this.saldoACubrir = saldoACubrir;
    }
    // CONSTRUCTOR PARA LLENAR LOS DATOS DEL REPORTE
    public ConsumoPacienteMezclas(int idFolio,String fecha, String hora, String medico, String habitacion, double costoDepocito, double saldoACubrir){
        this.id_folio =idFolio;
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.habitacion = habitacion;
        this.costoDepocito = costoDepocito;
        this.saldoACubrir = saldoACubrir;
    }

    public ConsumoPacienteMezclas(String nombre_paquete, double monto) {
        this.nombre_paquete = nombre_paquete;
        this.monto = monto;
    }

    public ConsumoPacienteMezclas(String nombre_paquete, int id_insumo) {
        this.nombre_paquete = nombre_paquete;
        this.monto = monto;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public int getId_folio() {
        return id_folio;
    }

    public void setId_folio(int id_folio) {
        this.id_folio = id_folio;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }

    public String getInsumo() {
        return insumo;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public int getExedente() {
        return exedente;
    }

    public void setExedente(int exedente) {
        this.exedente = exedente;
    }

    public int getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(int devolucion) {
        this.devolucion = devolucion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(int cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public double getCantidadPaquete() {
        return cantidadPaquete;
    }

    public void setCantidadPaquete(double cantidadPaquete) {
        this.cantidadPaquete = cantidadPaquete;
    }

    public double getExedenteMezcla() {
        return exedenteMezcla;
    }

    public void setExedenteMezcla(double exedenteMezcla) {
        this.exedenteMezcla = exedenteMezcla;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getNombre_paquete() {
        return nombre_paquete;
    }

    public void setNombre_paquete(String nombre_paquete) {
        this.nombre_paquete = nombre_paquete;
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

    public double getCostoDepocito() {
        return costoDepocito;
    }

    public void setCostoDepocito(double costoDepocito) {
        this.costoDepocito = costoDepocito;
    }

    public double getSaldoACubrir() {
        return saldoACubrir;
    }

    public void setSaldoACubrir(double saldoACubrir) {
        this.saldoACubrir = saldoACubrir;
    }

    @Override
    public String toString() {
        return "ConsumoPacienteMezclas{" + "id_folio=" + id_folio + ", id_insumo=" + id_insumo + ", nombre_paciente=" + nombre_paciente + ", nombre_paquete=" + nombre_paquete + ", insumo=" + insumo + ", fecha=" + fecha + ", hora=" + hora + ", medico=" + medico + ", habitacion=" + habitacion + ", costoDepocito=" + costoDepocito + ", saldoACubrir=" + saldoACubrir + ", consumo=" + consumo + ", exedente=" + exedente + ", devolucion=" + devolucion + ", cantidadEntregada=" + cantidadEntregada + ", cantidadPaquete=" + cantidadPaquete + ", monto=" + monto + ", precioUnitario=" + precioUnitario + ", subtotal=" + subtotal + ", exedenteMezcla=" + exedenteMezcla + '}';
    }
    
    

}
