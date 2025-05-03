/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

/**
 *
 * @author PC
 */
public class ComprasInsumosP {
    
    int id_compra_insumosp,
            id_proveedor,
            id_rubro, 
            estatus_compra,
            estatus_pago, 
            tipo_insumo,
            id_movimiento_inventario,
            usuario_modificacion,
            usuario_creacion;
    String observacion,
            folioCompra,
            fecha_creacion,
            fecha_modificacion,
            estatString,
            estatRecibidoString,
            estatPagoString;


    
    Double subtotal,
            descuento,
            importeComision,
            totalCompra,
            totalAbonado,
            totalSinComision,
            ivaDouble;

        public String getEstatRecibidoString() {
        return estatRecibidoString;
    }

    public void setEstatRecibidoString(String estatRecibidoString) {
        this.estatRecibidoString = estatRecibidoString;
    }

    public String getEstatPagoString() {
        return estatPagoString;
    }

    public void setEstatPagoString(String estatPagoString) {
        this.estatPagoString = estatPagoString;
    }
    public Double getIvaDouble() {
        return ivaDouble;
    }

    public void setIvaDouble(Double ivaDouble) {
        this.ivaDouble = ivaDouble;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstatString() {
        return estatString;
    }

    public void setEstatString(String estatString) {
        this.estatString = estatString;
    }

    public int getId_compra_insumosp() {
        return id_compra_insumosp;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getImporteComision() {
        return importeComision;
    }

    public void setImporteComision(Double importeComision) {
        this.importeComision = importeComision;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Double getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(Double totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public Double getTotalSinComision() {
        return totalSinComision;
    }

    public void setTotalSinComision(Double totalSinComision) {
        this.totalSinComision = totalSinComision;
    }



    public void setId_compra_insumosp(int id_compra_insumosp) {
        this.id_compra_insumosp = id_compra_insumosp;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public int getId_rubro() {
        return id_rubro;
    }

    public void setId_rubro(int id_rubro) {
        this.id_rubro = id_rubro;
    }

    public int getEstatus_compra() {
        return estatus_compra;
    }

    public void setEstatus_compra(int estatus_compra) {
        this.estatus_compra = estatus_compra;
    }

    public int getEstatus_pago() {
        return estatus_pago;
    }

    public void setEstatus_pago(int estatus_pago) {
        this.estatus_pago = estatus_pago;
    }

    public int getTipo_insumo() {
        return tipo_insumo;
    }

    public void setTipo_insumo(int tipo_insumo) {
        this.tipo_insumo = tipo_insumo;
    }

    public int getId_movimiento_inventario() {
        return id_movimiento_inventario;
    }

    public void setId_movimiento_inventario(int id_movimiento_inventario) {
        this.id_movimiento_inventario = id_movimiento_inventario;
    }

    public int getUsuario_modificacion() {
        return usuario_modificacion;
    }

    public void setUsuario_modificacion(int usuario_modificacion) {
        this.usuario_modificacion = usuario_modificacion;
    }

    public int getUsuario_creacion() {
        return usuario_creacion;
    }

    public void setUsuario_creacion(int usuario_creacion) {
        this.usuario_creacion = usuario_creacion;
    }


    public String getFolioCompra() {
        return folioCompra;
    }

    public void setFolioCompra(String folioCompra) {
        this.folioCompra = folioCompra;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    @Override
    public String toString() {
        return   folioCompra ;
    }
    
}
//204 flor de maria y bautista maldonado