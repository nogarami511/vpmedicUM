/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 *
 * @author alfar
 */
public class Pagos {

    private int id_pago, id_pasiente, cantidad_pago, usuario_cobro, estatus_pago_reembolso, usuario_reembolso;
    private double precio_unitario_pago, descuento_pago, sub_total_pago, iva_pago, total_pago;
    private String folio_paciente, descripcion_reembolso, folio_modificado;
    
    private int idTipoPago, id_folio;
    String descripcionPago, formaPago;
    private Timestamp fecha_pago, fecha_reembolso;
    
    int num_pago;
    int pago_tipo;
    String nombrePaciente, totalPagoFormateado;
    DecimalFormat pesoFormat = new DecimalFormat("$#,##0.00");
    
    int id_tipo_tarjeta;
    
    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public int getId_pasiente() {
        return id_pasiente;
    }

    public void setId_pasiente(int id_pasiente) {
        this.id_pasiente = id_pasiente;
    }

    public int getCantidad_pago() {
        return cantidad_pago;
    }

    public void setCantidad_pago(int cantidad_pago) {
        this.cantidad_pago = cantidad_pago;
    }

    public int getEstatus_pago_reembolso() {
        return estatus_pago_reembolso;
    }

    public void setEstatus_pago_reembolso(int estatus_pago_reembolso) {
        this.estatus_pago_reembolso = estatus_pago_reembolso;
    }

    public double getPrecio_unitario_pago() {
        return precio_unitario_pago;
    }

    public void setPrecio_unitario_pago(double precio_unitario_pago) {
        this.precio_unitario_pago = precio_unitario_pago;
    }

    public double getTotal_pago() {
        return total_pago;
    }

    public void setTotal_pago(double total_pago) {
        this.total_pago = total_pago;
        totalPagoFormateado = pesoFormat.format(total_pago);
    }

    public String getFolio_paciente() {
        return folio_paciente;
    }

    public void setFolio_paciente(String folio_paciente) {
        this.folio_paciente = folio_paciente;
    }

    public Timestamp getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Timestamp fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public int getUsuario_cobro() {
        return usuario_cobro;
    }

    public void setUsuario_cobro(int usuario_cobro) {
        this.usuario_cobro = usuario_cobro;
    }

    public int getUsuario_reembolso() {
        return usuario_reembolso;
    }

    public void setUsuario_reembolso(int usuario_reembolso) {
        this.usuario_reembolso = usuario_reembolso;
    }

    public double getDescuento_pago() {
        return descuento_pago;
    }

    public void setDescuento_pago(double descuento_pago) {
        this.descuento_pago = descuento_pago;
    }

    public double getSub_total_pago() {
        return sub_total_pago;
    }

    public void setSub_total_pago(double sub_total_pago) {
        this.sub_total_pago = sub_total_pago;
    }

    public double getIva_pago() {
        return iva_pago;
    }

    public void setIva_pago(double iva_pago) {
        this.iva_pago = iva_pago;
    }

    public Timestamp getFecha_reembolso() {
        return fecha_reembolso;
    }

    public void setFecha_reembolso(Timestamp fecha_reembolso) {
        this.fecha_reembolso = fecha_reembolso;
    }

    public String getDescripcion_reembolso() {
        return descripcion_reembolso;
    }

    public void setDescripcion_reembolso(String descripcion_reembolso) {
        this.descripcion_reembolso = descripcion_reembolso;
    }

    public int getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(int idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public String getDescripcionPago() {
        return descripcionPago;
    }

    public void setDescripcionPago(String descripcionPago) {
        this.descripcionPago = descripcionPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public int getId_folio() {
        return id_folio;
    }

    public void setId_folio(int id_folio) {
        this.id_folio = id_folio;
    }

    public int getNum_pago() {
        return num_pago;
    }

    public void setNum_pago(int num_pago) {
        this.num_pago = num_pago;
    }

    public int getPago_tipo() {
        return pago_tipo;
    }

    public void setPago_tipo(int pago_tipo) {
        this.pago_tipo = pago_tipo;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getTotalPagoFormateado() {
        return totalPagoFormateado;
    }

    public int getId_tipo_tarjeta() {
        return id_tipo_tarjeta;
    }

    public void setId_tipo_tarjeta(int id_tipo_tarjeta) {
        this.id_tipo_tarjeta = id_tipo_tarjeta;
    }
    public  String getFolioMod() {
        
        return folio_modificado;
    }
    public void setFolioMod(String folio_modificado) {
        this.folio_modificado = folio_modificado;
    }

}
