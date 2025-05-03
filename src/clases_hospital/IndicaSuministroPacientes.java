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
public class IndicaSuministroPacientes {
    private int idIndicaSuministroPacientes;
    private int idIndicaDetalle;
    private int idFolio;
    private int idInsimo;
    private boolean suministro;
    private boolean devolucion;
    private int usuarioCreacion;
    private int usuarioModificacion;
    
    private double precioUnitario;
    private double precioUnitarioPaquete;
    
    private String nombre_insumo;
    private int id_indica_p;
    
    private int id_area;
    
    public IndicaSuministroPacientes() {
    }

    public int getIdIndicaSuministroPacientes() {
        return idIndicaSuministroPacientes;
    }

    public void setIdIndicaSuministroPacientes(int idIndicaSuministroPacientes) {
        this.idIndicaSuministroPacientes = idIndicaSuministroPacientes;
    }

    public int getIdIndicaDetalle() {
        return idIndicaDetalle;
    }

    public void setIdIndicaDetalle(int idIndicaDetalle) {
        this.idIndicaDetalle = idIndicaDetalle;
    }

    public int getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(int idFolio) {
        this.idFolio = idFolio;
    }

    public int getIdInsimo() {
        return idInsimo;
    }

    public void setIdInsimo(int idInsimo) {
        this.idInsimo = idInsimo;
    }

    public boolean isSuministro() {
        return suministro;
    }

    public void setSuministro(boolean suministro) {
        this.suministro = suministro;
    }

    public boolean isDevolucion() {
        return devolucion;
    }

    public void setDevolucion(boolean devolucion) {
        this.devolucion = devolucion;
    }

    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getNombre_insumo() {
        return nombre_insumo;
    }

    public void setNombre_insumo(String nombre_insumo) {
        this.nombre_insumo = nombre_insumo;
    }

    public int getId_indica_p() {
        return id_indica_p;
    }

    public void setId_indica_p(int id_indica_p) {
        this.id_indica_p = id_indica_p;
    }

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioUnitarioPaquete() {
        return precioUnitarioPaquete;
    }

    public void setPrecioUnitarioPaquete(double precioUnitarioPaquete) {
        this.precioUnitarioPaquete = precioUnitarioPaquete;
    }
    
}
