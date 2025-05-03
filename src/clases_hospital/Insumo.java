/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author PC
 */
public class Insumo {
    Locale mexico = new Locale("es", "MX");
    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mexico);
    
    int id, id_presentacion, tipo_insumo, id_estatus_insumo, id_usuarioModificacion, id_comprainsumoP, id_comprainsdet, idTipoInsumoMedicoMacro;
    String clave, nombre, nombreMacro, formula, marca, calve_sat, observaciones, presentacion;
    Date fechaModificacion;
    
    double cantidad_unitariaxcaja, utilidad, maximos, minimos, iva,cantidad,cantidadd_unitarias;


    double importe, descuento;

   


    double costo_compra_caja, costo_compra_unitaria, precio_venta_caja, precio_venta_unitaria;
    
    double utilidadPaquete;
    double precioVentaCajaPaquete;
    double precioVentaUnitariaPaquete;
    
    double costoAnteriror;
    
    boolean kit_consumible;
    
    int modificacion;
    
    int idFamiliaInsumo;
    
    boolean familia;
    
    String costo_compra_cajaFormato, costo_compra_unitariaFormato, precio_venta_cajaFormato, precio_venta_unitariaFormato,importeFormato ;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
        public double getCantidad() {
        return cantidad;
    }
            public int getId_comprainsumoP() {
        return id_comprainsumoP;
    }

    public void setId_comprainsumoP(int id_comprainsumoP) {
        this.id_comprainsumoP = id_comprainsumoP;
    }

    public int getId_comprainsdet() {
        return id_comprainsdet;
    }

    public void setId_comprainsdet(int id_comprainsdet) {
        this.id_comprainsdet = id_comprainsdet;
    }
    

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
        public String getImporteFormato() {
        importeFormato = formatoMoneda.format(importe);
        
        return importeFormato;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
     public double getCantidadd_unitarias() {
        return cantidadd_unitarias;
    }

    public void setCantidadd_unitarias(double cantidadd_unitarias) {
        this.cantidadd_unitarias = cantidadd_unitarias;
    }

    public int getIdPresentacion() {
        return id_presentacion;
    }

    public void setIdPresentacion(int id_presentacion) {
        this.id_presentacion = id_presentacion;
    }

    public double getMaximos() {
        return maximos;
    }

    public void setMaximos(double maximos) {
        this.maximos = maximos;
    }

    public double getMinimos() {
        return minimos;
    }

    public void setMinimos(double minimos) {
        this.minimos = minimos;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getTipoInsumo() {
        return tipo_insumo;
    }

    public void setTipoInsumo(int tipo_insumo) {
        this.tipo_insumo = tipo_insumo;
    }

    public int getIdEstatusInsumo() {
        return id_estatus_insumo;
    }

    public void setIdEstatusInsumo(int id_estatus_insumo) {
        this.id_estatus_insumo = id_estatus_insumo;
    }

    public int getIdUsuarioModificacion() {
        return id_usuarioModificacion;
    }

    public void setIdUsuarioModificacion(int id_usuarioModificacion) {
        this.id_usuarioModificacion = id_usuarioModificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCalve_sat() {
        return calve_sat;
    }

    public void setCalve_sat(String calve_sat) {
        this.calve_sat = calve_sat;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getId_presentacion() {
        return id_presentacion;
    }

    public void setId_presentacion(int id_presentacion) {
        this.id_presentacion = id_presentacion;
    }

    public int getTipo_insumo() {
        return tipo_insumo;
    }

    public void setTipo_insumo(int tipo_insumo) {
        this.tipo_insumo = tipo_insumo;
    }

    public int getId_estatus_insumo() {
        return id_estatus_insumo;
    }

    public void setId_estatus_insumo(int id_estatus_insumo) {
        this.id_estatus_insumo = id_estatus_insumo;
    }

    public int getId_usuarioModificacion() {
        return id_usuarioModificacion;
    }

    public void setId_usuarioModificacion(int id_usuarioModificacion) {
        this.id_usuarioModificacion = id_usuarioModificacion;
    }

    public double getCantidad_unitariaxcaja() {
        return cantidad_unitariaxcaja;
    }

    public void setCantidad_unitariaxcaja(double cantidad_unitariaxcaja) {
        this.cantidad_unitariaxcaja = cantidad_unitariaxcaja;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }

    public double getCosto_compra_caja() {
        return costo_compra_caja;
    }

    public void setCosto_compra_caja(double costo_compra_caja) {
        this.costo_compra_caja = costo_compra_caja;
    }

    public double getCosto_compra_unitaria() {
        return costo_compra_unitaria;
    }

    public void setCosto_compra_unitaria(double costo_compra_unitaria) {
        this.costo_compra_unitaria = costo_compra_unitaria;
    }

    public double getPrecio_venta_caja() {
        return precio_venta_caja;
    }

    public void setPrecio_venta_caja(double precio_venta_caja) {
        this.precio_venta_caja = precio_venta_caja;
    }

    public double getPrecio_venta_unitaria() {
        return precio_venta_unitaria;
    }

    public void setPrecio_venta_unitaria(double precio_venta_unitaria) {
        this.precio_venta_unitaria = precio_venta_unitaria;
    }

    public double getUtilidadPaquete() {
        return utilidadPaquete;
    }

    public void setUtilidadPaquete(double utilidadPaquete) {
        this.utilidadPaquete = utilidadPaquete;
    }

    public double getPrecioVentaCajaPaquete() {
        return precioVentaCajaPaquete;
    }

    public void setPrecioVentaCajaPaquete(double precioVentaCajaPaquete) {
        this.precioVentaCajaPaquete = precioVentaCajaPaquete;
    }

    public double getPrecioVentaUnitariaPaquete() {
        return precioVentaUnitariaPaquete;
    }

    public void setPrecioVentaUnitariaPaquete(double precioVentaUnitariaPaquete) {
        this.precioVentaUnitariaPaquete = precioVentaUnitariaPaquete;
    }

    public boolean isKit_consumible() {
        return kit_consumible;
    }

    public void setKit_consumible(boolean kit_consumible) {
        this.kit_consumible = kit_consumible;
    }

    public double getCostoAnteriror() {
        return costoAnteriror;
    }

    public void setCostoAnteriror(double costoAnteriror) {
        this.costoAnteriror = costoAnteriror;
    }

    public int getModificacion() {
        return modificacion;
    }

    public void setModificacion(int modificacion) {
        this.modificacion = modificacion;
    }

    public int getIdFamiliaInsumo() {
        return idFamiliaInsumo;
    }

    public void setIdFamiliaInsumo(int idFamiliaInsumo) {
        this.idFamiliaInsumo = idFamiliaInsumo;
    }

    public boolean isFamilia() {
        return familia;
    }

    public void setFamilia(boolean familia) {
        this.familia = familia;
    }

    public String getCosto_compra_cajaFormato() {
        costo_compra_cajaFormato = formatoMoneda.format(costo_compra_caja);
        return costo_compra_cajaFormato;
    }

    public String getCosto_compra_unitariaFormato() {
        costo_compra_unitariaFormato = formatoMoneda.format(costo_compra_unitaria);
        return costo_compra_unitariaFormato;
    }

    public String getPrecio_venta_cajaFormato() {
        precio_venta_cajaFormato = formatoMoneda.format(precio_venta_caja);
        return precio_venta_cajaFormato;
    }

    public String getPrecio_venta_unitariaFormato() {
        precio_venta_unitariaFormato = formatoMoneda.format(precio_venta_unitaria);
        return precio_venta_unitariaFormato;
    }

    public int getIdTipoInsumoMedicoMacro() {
        return idTipoInsumoMedicoMacro;
    }

    public void setIdTipoInsumoMedicoMacro(int idTipoInsumoMedicoMacro) {
        this.idTipoInsumoMedicoMacro = idTipoInsumoMedicoMacro;
    }

    public String getNombreMacro() {
        return nombreMacro;
    }

    public void setNombreMacro(String nombreMacro) {
        this.nombreMacro = nombreMacro;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
