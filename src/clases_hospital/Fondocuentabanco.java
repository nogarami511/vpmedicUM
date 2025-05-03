/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Date;

/**
 *
 * @author alfar
 */
public class Fondocuentabanco {
    private int id;
    private Date fecha;
    private String tipoOperacion;
    private double importe;
    private double saldo;
    private String concepto;
    private int idCliente;

    // Constructores
    public Fondocuentabanco() {
        // Constructor vacío
    }

    public Fondocuentabanco(Date fecha, String tipoOperacion, double importe, double saldo, String concepto, int idCliente) {
        this.fecha = fecha;
        this.tipoOperacion = tipoOperacion;
        this.importe = importe;
        this.saldo = saldo;
        this.concepto = concepto;
        this.idCliente = idCliente;
    }

    // Getters y setters para todos los atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
