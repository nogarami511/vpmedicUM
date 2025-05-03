/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.text.DecimalFormat;

/**
 *
 * @author olver
 */
public class ArmadoPaqueteconMedicoCostos {
    DecimalFormat df = new DecimalFormat("#,###.00");

    int id_paquete, id_armado_paquete, colorrow, actualizar_valores;
    String nombre_insumo;
    double costo_original, costo_nuevo, diferencia_costo, diferencia_costo_porcentaje;
    double costo_subtotal_original, costo_subtotal_nuevo, diferencia_costo_subtotal, diferencia_costo_subtotal_porcentaje;


    double precio_total_actual, precio_total_nuevo, diferencia_total, diferencia_total_porcentaje;
    double monto_ganancia_original, moonto_ganancia_nuevo, cantidad, factor_insuno, valor_original_no_cambiado;


    
    String df_costo_original, df_costo_nuevo, df_diferencia_costo, df_diferencia_costo_porcentaje;
    String df_costo_subtotal_original, df_costo_subtotal_nuevo, df_diferencia_costo_subtotal, df_diferencia_costo_subtotal_porcentaje;
    String df_precio_total_actual, df_precio_total_nuevo, df_diferencia_total, df_diferencia_total_porcentaje;
    String df_monto_ganancia_original, df_moonto_ganancia_nuevo, df_factor_insuno;
    
    // VALORES CON FORMATO

    public String getDf_costo_original() {
        df_costo_original = "$ "+ df.format(costo_original);
        return df_costo_original;
    }

    public String getDf_costo_nuevo() {
        df_costo_nuevo = "$ "+ df.format(costo_nuevo);
        return df_costo_nuevo;
    }

    public String getDf_diferencia_costo() {
        df_diferencia_costo ="$ " + df.format(diferencia_costo);
        return df_diferencia_costo;
    }

    public String getDf_diferencia_costo_porcentaje() {
        df_diferencia_costo_porcentaje = df.format(diferencia_costo_porcentaje) + " %";
        return df_diferencia_costo_porcentaje;
    }

    public String getDf_costo_subtotal_original() {
        df_costo_subtotal_original = "$ " + df.format(costo_subtotal_original);
        return df_costo_subtotal_original;
    }

    public String getDf_costo_subtotal_nuevo() {
        df_costo_subtotal_nuevo = "$ " + df.format(costo_subtotal_nuevo);
        return df_costo_subtotal_nuevo;
    }

    public String getDf_diferencia_costo_subtotal() {
        df_diferencia_costo_subtotal = "$ " + df.format(diferencia_costo_subtotal);
        return df_diferencia_costo_subtotal;
    }

    public String getDf_diferencia_costo_subtotal_porcentaje() {
        df_diferencia_costo_subtotal_porcentaje = df.format(diferencia_costo_subtotal_porcentaje) + " %";
        return df_diferencia_costo_subtotal_porcentaje;
    }

    public String getDf_precio_total_actual() {
        df_precio_total_actual = "$ " + df.format(precio_total_actual);
        return df_precio_total_actual;
    }

    public String getDf_precio_total_nuevo() {
        df_precio_total_nuevo = "$ " + df.format(precio_total_nuevo);
        return df_precio_total_nuevo;
    }

    public String getDf_diferencia_total() {
        df_diferencia_total = "$ " + df.format(diferencia_total);
        return df_diferencia_total;
    }

    public String getDf_diferencia_total_porcentaje() {
        df_diferencia_total_porcentaje = df.format(diferencia_total_porcentaje) + " %";
        return df_diferencia_total_porcentaje;
    }

    public String getDf_monto_ganancia_original() {
        df_monto_ganancia_original = "$ " + df.format(monto_ganancia_original);
        return df_monto_ganancia_original;
    }

    public String getDf_moonto_ganancia_nuevo() {
        df_moonto_ganancia_nuevo = "$ " + df.format(moonto_ganancia_nuevo);
        return df_moonto_ganancia_nuevo;
    }

    public String getDf_factor_insuno() {
        df_factor_insuno = factor_insuno + " %";
        return df_factor_insuno;
    }
    
    // VALORES SIN FORMATO

    public int getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(int id_paquete) {
        this.id_paquete = id_paquete;
    }

    public int getId_armado_paquete() {
        return id_armado_paquete;
    }

    public void setId_armado_paquete(int id_armado_paquete) {
        this.id_armado_paquete = id_armado_paquete;
    }

    public String getNombre_insumo() {
        return nombre_insumo;
    }

    public void setNombre_insumo(String nombre_insumo) {
        this.nombre_insumo = nombre_insumo;
    }

    public double getCosto_original() {
        return costo_original;
    }

    public void setCosto_original(double costo_original) {
        this.costo_original = costo_original;
    }

    public double getCosto_nuevo() {
        return costo_nuevo;
    }

    public void setCosto_nuevo(double costo_nuevo) {
        this.costo_nuevo = costo_nuevo;
    }

    public double getDiferencia_costo() {
        return diferencia_costo;
    }

    public void setDiferencia_costo(double diferencia_costo) {
        this.diferencia_costo = diferencia_costo;
    }

    public double getDiferencia_costo_porcentaje() {
        return diferencia_costo_porcentaje;
    }

    public void setDiferencia_costo_porcentaje(double diferencia_costo_porcentaje) {
        this.diferencia_costo_porcentaje = diferencia_costo_porcentaje;
    }

    public double getCosto_subtotal_original() {
        return costo_subtotal_original;
    }

    public void setCosto_subtotal_original(double costo_subtotal_original) {
        this.costo_subtotal_original = costo_subtotal_original;
    }

    public double getCosto_subtotal_nuevo() {
        return costo_subtotal_nuevo;
    }

    public void setCosto_subtotal_nuevo(double costo_subtotal_nuevo) {
        this.costo_subtotal_nuevo = costo_subtotal_nuevo;
    }

    public double getDiferencia_costo_subtotal() {
        return diferencia_costo_subtotal;
    }

    public void setDiferencia_costo_subtotal(double diferencia_costo_subtotal) {
        this.diferencia_costo_subtotal = diferencia_costo_subtotal;
    }

    public double getDiferencia_costo_subtotal_porcentaje() {
        return diferencia_costo_subtotal_porcentaje;
    }

    public void setDiferencia_costo_subtotal_porcentaje(double diferencia_costo_subtotal_porcentaje) {
        this.diferencia_costo_subtotal_porcentaje = diferencia_costo_subtotal_porcentaje;
    }

    public double getPrecio_total_actual() {
        return precio_total_actual;
    }

    public void setPrecio_total_actual(double precio_total_actual) {
        this.precio_total_actual = precio_total_actual;
    }

    public double getPrecio_total_nuevo() {
        return precio_total_nuevo;
    }

    public void setPrecio_total_nuevo(double precio_total_nuevo) {
        this.precio_total_nuevo = precio_total_nuevo;
    }

    public double getDiferencia_total() {
        return diferencia_total;
    }

    public void setDiferencia_total(double diferencia_total) {
        this.diferencia_total = diferencia_total;
    }

    public double getDiferencia_total_porcentaje() {
        return diferencia_total_porcentaje;
    }

    public void setDiferencia_total_porcentaje(double diferencia_total_porcentaje) {
        this.diferencia_total_porcentaje = diferencia_total_porcentaje;
    }

    public double getMonto_ganancia_original() {
        return monto_ganancia_original;
    }

    public void setMonto_ganancia_original(double monto_ganancia_original) {
        this.monto_ganancia_original = monto_ganancia_original;
    }

    public double getMoonto_ganancia_nuevo() {
        return moonto_ganancia_nuevo;
    }

    public void setMoonto_ganancia_nuevo(double moonto_ganancia_nuevo) {
        this.moonto_ganancia_nuevo = moonto_ganancia_nuevo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getFactor_insuno() {
        return factor_insuno;
    }

    public void setFactor_insuno(double factor_insuno) {
        this.factor_insuno = factor_insuno;
    }

    public int getColorrow() {
        return colorrow;
    }

    public void setColorrow(int colorrow) {
        this.colorrow = colorrow;
    }
    
        public double getActualizar_valores() {
        return actualizar_valores;
    }

    public void setActualizar_valores(int actualizar_valores) {
        this.actualizar_valores = actualizar_valores;
    }
        public double getValor_original_no_cambiado() {
        return valor_original_no_cambiado;
    }

    public void setValor_original_no_cambiado(double valor_original_no_cambiado) {
        this.valor_original_no_cambiado = valor_original_no_cambiado;
    }

}
