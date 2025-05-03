/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.text.DecimalFormat;

/**
 *
 * @author alfar
 */
public class CalculoNomina {
    private int id, id_colaborador, cantidad_hora_extra, faltas, usuario_modficacion, id_estatus_nomina;
    private double sueldo_semanal, pago_tarjeta, pago_hora_extra, importe_hora_extra, bono, importe_por_faltas, aguinaldo, pago_finiquito, pago_neto;
    private String clave, nombreColaborador;
    
    private DecimalFormat df = new DecimalFormat("#,###.00");
    
    private String stringSueldo_semanal, string_pago_tarjeta, stringPago_hora_extra, stringImporte_hora_extra, stringBono, stringImporte_por_faltas, stringAguinaldo, stringPago_finiquito, stringPago_neto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_colaborador() {
        return id_colaborador;
    }

    public void setId_colaborador(int id_colaborador) {
        this.id_colaborador = id_colaborador;
    }

    public double getSueldo_semanal() {
        return sueldo_semanal;
    }

    public void setSueldo_semanal(double sueldo_semanal) {
        this.sueldo_semanal = sueldo_semanal;
    }

    public double getPago_tarjeta() {
        return pago_tarjeta;
    }

    public void setPago_tarjeta(double pago_tarjeta) {
        this.pago_tarjeta = pago_tarjeta;
    }
    
    public int getCantidad_hora_extra() {
        return cantidad_hora_extra;
    }

    public void setCantidad_hora_extra(int cantidad_hora_extra) {
        this.cantidad_hora_extra = cantidad_hora_extra;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getUsuario_modficacion() {
        return usuario_modficacion;
    }

    public void setUsuario_modficacion(int usuario_modficacion) {
        this.usuario_modficacion = usuario_modficacion;
    }

    public int getId_estatus_nomina() {
        return id_estatus_nomina;
    }

    public void setId_estatus_nomina(int id_estatus_nomina) {
        this.id_estatus_nomina = id_estatus_nomina;
    }

    public double getPago_hora_extra() {
        return pago_hora_extra;
    }

    public void setPago_hora_extra(double pago_hora_extra) {
        this.pago_hora_extra = pago_hora_extra;
    }

    public double getImporte_hora_extra() {
        return importe_hora_extra;
    }

    public void setImporte_hora_extra(double importe_hora_extra) {
        this.importe_hora_extra = importe_hora_extra;
    }

    public double getBono() {
        return bono;
    }

    public void setBono(double bono) {
        this.bono = bono;
    }

    public double getImporte_por_faltas() {
        return importe_por_faltas;
    }

    public void setImporte_por_faltas(double importe_por_faltas) {
        this.importe_por_faltas = importe_por_faltas;
    }

    public double getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(double aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public double getPago_finiquito() {
        return pago_finiquito;
    }

    public void setPago_finiquito(double pago_finiquito) {
        this.pago_finiquito = pago_finiquito;
    }

    public double getPago_neto() {
        return pago_neto;
    }

    public void setPago_neto(double pago_neto) {
        this.pago_neto = pago_neto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreColaborador() {
        return nombreColaborador;
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador = nombreColaborador;
    }
    
    //==========================================================================

    public String getStringSueldo_semanal() {
        stringSueldo_semanal = "$ " + df.format(sueldo_semanal);
        return stringSueldo_semanal;
    }

    public String getString_pago_tarjeta() {
        string_pago_tarjeta = "$ " + df.format(pago_tarjeta);
        return string_pago_tarjeta;
    }

    public String getStringPago_hora_extra() {
        stringPago_hora_extra = "$ " + df.format(pago_hora_extra);
        return stringPago_hora_extra;
    }

    public String getStringImporte_hora_extra() {
        stringImporte_hora_extra = "$ " + df.format(importe_hora_extra);
        return stringImporte_hora_extra;
    }

    public String getStringBono() {
        stringBono = "$ " + df.format(bono);
        return stringBono;
    }

    public String getStringImporte_por_faltas() {
        stringImporte_por_faltas = "$ " + df.format(importe_por_faltas);
        return stringImporte_por_faltas;
    }

    public String getStringAguinaldo() {
        stringAguinaldo = "$ " + df.format(aguinaldo);
        return stringAguinaldo;
    }

    public String getStringPago_finiquito() {
        stringPago_finiquito = "$ " + df.format(pago_finiquito);
        return stringPago_finiquito;
    }

    public String getStringPago_neto() {
        stringPago_neto = "$ " + df.format(pago_neto);
        return stringPago_neto;
    }
    
}
