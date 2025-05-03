/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author gamae
 */
public class Comanda {
    // datos de la comanda
    int idComanda;
    String folio,cliente,recibe,observacion,descripcion;
    Date fecha;
    int idCliente,cantidadProductos;
    double subtotal,iva,total;
    // datos del detalle de la comanda
    int idDetalle,idProducto,cantidad;
    String nombreProducto;
    double costoUnitario,subtotalDetalle,ivaDetalle,totalDetalle;
    
    int idEstatus;
    int idUsuarioModificacion;
    
    
    private Date fechaModificacion;
    private boolean pagado;
    
    public Comanda(){}
    
    // constructor comanda
    public Comanda(String folio,Date fecha, int idCliente, String cliente, int cantidadDeProductos,double subtotal,double iva,double total,String recibe,String observaciones, int idEstatus){
        this.folio = folio;
        this.fecha =fecha;
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.cantidadProductos = cantidadDeProductos;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.recibe = recibe;
        this.observacion = observaciones;
    }
    // constructor para el buscador
    public Comanda (int idProducto,String nombreProducto,double costoUnitario, String descripcion){
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.costoUnitario = costoUnitario;
        this.descripcion = descripcion;
    }
    // constructor para la tabla de productos
    public Comanda (int idProducto,String nombreProducto,double costoUnitario, String descripcion, int cantidad){
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.costoUnitario = costoUnitario;
        this.descripcion = descripcion;
        this.cantidad =cantidad;
    }
    // constructor vista principal comandas
    public Comanda(int idComanda,String folio, String nombre){
        this.idComanda = idComanda;
        this.folio = folio;
        this.cliente = nombre;
    }
    
    // constructosr comanda detalle
    public Comanda(int idComanda,int idProducto, int cantidad, double costoUnitario, double subtotalDetalle, double ivaDetalle, double totalDetalle, String nombreProducto){
        this.idComanda = idComanda;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.subtotalDetalle = subtotalDetalle;
        this.ivaDetalle = ivaDetalle;
        this.totalDetalle = totalDetalle;
        this.nombreProducto = nombreProducto;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public double getSubtotalDetalle() {
        return subtotalDetalle;
    }

    public void setSubtotalDetalle(double subtotalDetalle) {
        this.subtotalDetalle = subtotalDetalle;
    }

    public double getIvaDetalle() {
        return ivaDetalle;
    }

    public void setIvaDetalle(double ivaDetalle) {
        this.ivaDetalle = ivaDetalle;
    }

    public double getTotalDetalle() {
        return totalDetalle;
    }

    public void setTotalDetalle(double totalDetalle) {
        this.totalDetalle = totalDetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public int getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(int idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }
    
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    @Override
    public String toString() {
        return nombreProducto + "(" + descripcion+ ")";
    }
    
}
