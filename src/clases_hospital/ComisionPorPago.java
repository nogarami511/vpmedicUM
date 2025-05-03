/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Gerardo
 */
public class ComisionPorPago {

    private int id;
    private int idPaciente;
    private int idFolio;
    private String nombrePaciente;
    private double totalPago;
    private int tipoPago;
    private String tipoPagoString;
    private double comision;
    private String totalPagoFormateado;
    private String comisionFormateado;
    private Locale locale = new Locale("es", "MX"); // Cambia por la localización que necesites
    DecimalFormatSymbols simbolos = new DecimalFormatSymbols(locale);

    public ComisionPorPago(int id, int idPaciente, int idFolio, String nombrePaciente, double totalPago, int tipoPago, double comision) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idFolio = idFolio;
        this.nombrePaciente = nombrePaciente;
        this.totalPago = totalPago;
        this.tipoPago = tipoPago;
        this.comision = comision;
    }

    public String getComisionFormateado() {
        return comisionFormateado;
    }

    public String getTotalPagoFormateado() {
        return totalPagoFormateado;
    }

    public ComisionPorPago() {
        simbolos.setCurrencySymbol("$"); // Símbolo de la moneda
    }

    public String getTipoPagoString() {
        return tipoPagoString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(int idFolio) {
        this.idFolio = idFolio;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
        DecimalFormat formatoMoneda = new DecimalFormat("¤#,##0.00", simbolos);
        totalPagoFormateado = formatoMoneda.format(totalPago);
    }

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
        if (this.tipoPago == 3) {
            tipoPagoString = "TARJETA DE CREDITO";
        } else if (this.tipoPago == 4) {
            tipoPagoString = "TARJETA DE DEBIDO";
        } else if (this.tipoPago == 8) {
            tipoPagoString = "AMERICAN EXPRESS";
        } else if (this.tipoPago == 9) {
            tipoPagoString = "VISA";
        } else if (this.tipoPago == 10) {
            tipoPagoString = "MASTER CARD";
        }
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
        DecimalFormat formatoMoneda = new DecimalFormat("¤#,##0.00", simbolos);
        comisionFormateado = formatoMoneda.format(comision);
    }

}
