/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import clase.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Gerardo
 */
public class ConfirmacionAutorizacion {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

    private int id_confirmacionAutorizacion, id_compras_internasp, id_formaPago,
            estatus;
    private Double montoTotalAAutorizar;
    private String formaPagoString;
    Date fecha_pedio;
    int usuario_solicitante;

    String nombre_estatus;
    String nombre_usuario;
    String forma_pago, montoTotalAAutorizarFormateado;

    int idComprasmenosuno;

    public ConfirmacionAutorizacion(int id_confirmacionAutorizacion, Date fecha_pedio, int id_formaPago, int estatus, Double montoTotalAAutorizar, int usuario_solicitante) {
        this.id_confirmacionAutorizacion = id_confirmacionAutorizacion;
        this.fecha_pedio = fecha_pedio;
        this.id_formaPago = id_formaPago;
        this.estatus = estatus;
        this.montoTotalAAutorizar = montoTotalAAutorizar;
        this.usuario_solicitante = usuario_solicitante;
    }

    public ConfirmacionAutorizacion() {
    }

    public String getFormaPagoString() {
        return formaPagoString;
    }

    public void traerFormaPago() throws SQLException {
        con = conexion.conectar2();
        String query = "SELECT * FROM forma_pagos f WHERE f.id = ?";

        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id_formaPago);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                formaPagoString = resultSet.getString(2);
            }
        }
        con.close();
    }

    public int getId_confirmacionAutorizacion() {
        return id_confirmacionAutorizacion;
    }

    public void setId_confirmacionAutorizacion(int id_confirmacionAutorizacion) {
        this.id_confirmacionAutorizacion = id_confirmacionAutorizacion;
    }

    public Date getFecha_pedio() {
        return fecha_pedio;
    }

    public void setFecha_pedio(Date fecha_pedio) {
        this.fecha_pedio = fecha_pedio;
    }

    public int getId_formaPago() {
        return id_formaPago;
    }

    public void setId_formaPago(int id_formaPago) {
        this.id_formaPago = id_formaPago;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Double getMontoTotalAAutorizar() {
        return montoTotalAAutorizar;
    }

    public void setMontoTotalAAutorizar(Double montoTotalAAutorizar) {
        montoTotalAAutorizarFormateado = formatoMoneda.format(montoTotalAAutorizar);
        this.montoTotalAAutorizar = montoTotalAAutorizar;
    }

    public int getUsuario_solicitante() {
        return usuario_solicitante;
    }

    public void setUsuario_solicitante(int usuario_solicitante) {
        this.usuario_solicitante = usuario_solicitante;
    }

    public String getNombre_estatus() {
        return nombre_estatus;
    }

    public void setNombre_estatus(String nombre_estatus) {
        this.nombre_estatus = nombre_estatus;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public int getIdComprasmenosuno() {
        return idComprasmenosuno;
    }

    public void setIdComprasmenosuno(int idComprasmenosuno) {
        this.idComprasmenosuno = idComprasmenosuno;
    }

    public String getMontoTotalAAutorizarFormateado() {
        return montoTotalAAutorizarFormateado;
    }
}
