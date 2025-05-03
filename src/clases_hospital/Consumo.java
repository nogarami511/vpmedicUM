/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author gamae
 */
public class Consumo {

    int id, id_pasiente, id_PaqueteAlimento, id_estatus, id_tipo_consumo, id_folio, id_producto_venta;
    String tipo, referencia, fechaCorta, folio;
    double monto, total_abono;
    double precioUnitario, cantidad, precioUnitarioPaquete;
    Date fecha;
    Timestamp datetime;
    int id_estatus_consumo;
    boolean paquete;
    int id_usuario_creacion;
    int id_usuario_modificacion;

    public Consumo() {
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Consumo(double cantidad, String tipo, Date fecha, double monto) {
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.fecha = fecha;
        this.monto = monto;
    }

    // cosntructor para crear un objeto tipo consumo para incertar en la bd
    public Consumo(String tipo, double cantidad, double monto, String folio, int idPaciente, int idFolio, int id_producto_venta) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.monto = monto;
        this.folio = folio;
        this.id_pasiente = idPaciente;
        this.id_folio = idFolio;
        this.id_producto_venta = id_producto_venta;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getId_pasiente() {
        return id_pasiente;
    }

    public void setId_pasiente(int id_pasiente) {
        this.id_pasiente = id_pasiente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaCorta() {
        return fechaCorta;
    }

    public void setFechaCorta(String fechaCorta) {
        this.fechaCorta = fechaCorta;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getId_PaqueteAlimento() {
        return id_PaqueteAlimento;
    }

    public void setId_PaqueteAlimento(int id_PaqueteAlimento) {
        this.id_PaqueteAlimento = id_PaqueteAlimento;
    }

    public int getId_estatus() {
        return id_estatus;
    }

    public void setId_estatus(int id_estatus) {
        this.id_estatus = id_estatus;
    }

    public double getTotal_abono() {
        return total_abono;
    }

    public void setTotal_abono(double total_abono) {
        this.total_abono = total_abono;
    }

    public int getId_tipo_consumo() {
        return id_tipo_consumo;
    }

    public void setId_tipo_consumo(int id_tipo_consumo) {
        this.id_tipo_consumo = id_tipo_consumo;
    }

    public int getId_folio() {
        return id_folio;
    }

    public void setId_folio(int id_folio) {
        this.id_folio = id_folio;
    }

    public int getId_producto_venta() {
        return id_producto_venta;
    }

    public void setId_producto_venta(int id_producto_venta) {
        this.id_producto_venta = id_producto_venta;
    }

    public int getId_estatus_consumo() {
        return id_estatus_consumo;
    }

    public void setId_estatus_consumo(int id_estatus_consumo) {
        this.id_estatus_consumo = id_estatus_consumo;
    }

    public boolean isPaquete() {
        return paquete;
    }

    public void setPaquete(boolean paquete) {
        this.paquete = paquete;
    }

    public int getId_usuario_creacion() {
        return id_usuario_creacion;
    }

    public void setId_usuario_creacion(int id_usuario_creacion) {
        this.id_usuario_creacion = id_usuario_creacion;
    }

    public int getId_usuario_modificacion() {
        return id_usuario_modificacion;
    }

    public void setId_usuario_modificacion(int id_usuario_modificacion) {
        this.id_usuario_modificacion = id_usuario_modificacion;
    }

    public double getPrecioUnitarioPaquete() {
        return precioUnitarioPaquete;
    }

    public void setPrecioUnitarioPaquete(double precioUnitarioPaquete) {
        this.precioUnitarioPaquete = precioUnitarioPaquete;
    }

    @Override
    public String toString() {
        return "Consumo{" + "id=" + id + ", cantidad=" + cantidad + ", id_pasiente=" + id_pasiente + ", id_PaqueteAlimento=" + id_PaqueteAlimento + ", id_estatus=" + id_estatus + ", tipo=" + tipo + ", referencia=" + referencia + ", fechaCorta=" + fechaCorta + ", folio=" + folio + ", monto=" + monto + ", total_abono=" + total_abono + ", fecha=" + fecha + "}\n";
    }

}
