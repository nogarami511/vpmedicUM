/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author Gerardo
 */
public class EstudioMedico {
    int id;
    int idInsumo;
    String nombreEstudio;
    int idFolio;
    int idPaciente;
    int usuarioPedido;
    Date fechaPedido;
    int estatusEstudio;
    int estatusPagoEstudio;
    int usuarioPago;
    Date fechaPago;
    int usuarioModificacion;
    Date fechaModificacion;
    
    int id_estudios_laboratorios;
    int id_laboratorio;
    
    double precio_venta_unitaria_paquete;
    
    String nombre_comercial_laboratorio;
    
    double saldo_saldar;
    double monto_abonado;
    
    double precio_sin_iva;
    double iva;
    double precio_con_iva;
    int id_solicitud;
    
    boolean solicitar;
    
    String formapagotipo;
    int formapagoint;
    Laboratorio laboratorio;
    
    String estatuspagonombre;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public int getId_estudios_laboratorios() {
        return id_estudios_laboratorios;
    }

    public void setId_estudios_laboratorios(int id_estudios_laboratorios) {
        this.id_estudios_laboratorios = id_estudios_laboratorios;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNombreEstudio() {
        return nombreEstudio;
    }

    public void setNombreEstudio(String nombreEstudio) {
        this.nombreEstudio = nombreEstudio;
    }

    public int getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(int idFolio) {
        this.idFolio = idFolio;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getUsuarioPedido() {
        return usuarioPedido;
    }

    public void setUsuarioPedido(int usuarioPedido) {
        this.usuarioPedido = usuarioPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getEstatusEstudio() {
        return estatusEstudio;
    }

    public void setEstatusEstudio(int estatusEstudio) {
        this.estatusEstudio = estatusEstudio;
    }

    public int getEstatusPagoEstudio() {
        return estatusPagoEstudio;
    }

    public void setEstatusPagoEstudio(int estatusPagoEstudio) {
        this.estatusPagoEstudio = estatusPagoEstudio;
    }

    public int getUsuarioPago() {
        return usuarioPago;
    }

    public void setUsuarioPago(int usuarioPago) {
        this.usuarioPago = usuarioPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public int getId_laboratorio() {
        return id_laboratorio;
    }

    public void setId_laboratorio(int id_laboratorio) {
        this.id_laboratorio = id_laboratorio;
    }

    public double getPrecio_venta_unitaria_paquete() {
        return precio_venta_unitaria_paquete;
    }

    public void setPrecio_venta_unitaria_paquete(double precio_venta_unitaria_paquete) {
        this.precio_venta_unitaria_paquete = precio_venta_unitaria_paquete;
    }

    public String getNombre_comercial_laboratorio() {
        return nombre_comercial_laboratorio;
    }

    public void setNombre_comercial_laboratorio(String nombre_comercial_laboratorio) {
        this.nombre_comercial_laboratorio = nombre_comercial_laboratorio;
    }

    public double getPrecio_sin_iva() {
        return precio_sin_iva;
    }

    public void setPrecio_sin_iva(double precio_sin_iva) {
        this.precio_sin_iva = precio_sin_iva;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getPrecio_con_iva() {
        return precio_con_iva;
    }

    public void setPrecio_con_iva(double precio_con_iva) {
        this.precio_con_iva = precio_con_iva;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public double getSaldo_saldar() {
        return saldo_saldar;
    }

    public void setSaldo_saldar(double saldo_saldar) {
        this.saldo_saldar = saldo_saldar;
    }

    public double getMonto_abonado() {
        return monto_abonado;
    }

    public void setMonto_abonado(double monto_abonado) {
        this.monto_abonado = monto_abonado;
    }

    public boolean isSolicitar() {
        return solicitar;
    }

    public void setSolicitar(boolean solicitar) {
        this.solicitar = solicitar;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getFormapagotipo() {
        return formapagotipo;
    }

    public void setFormapagotipo(String formapagotipo) {
        this.formapagotipo = formapagotipo;
    }

    public int getFormapagoint() {
        return formapagoint;
    }

    public void setFormapagoint(int formapagoint) {
        this.formapagoint = formapagoint;
    }

    public String getEstatuspagonombre() {
        return estatuspagonombre;
    }

    public void setEstatuspagonombre(String estatuspagonombre) {
        this.estatuspagonombre = estatuspagonombre;
    }

    @Override
    public String toString() {
        return nombreEstudio;
    }
    
}
