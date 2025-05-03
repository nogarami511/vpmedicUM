/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import java.text.NumberFormat;

/**
 *
 * @author PC
 */
public class Puesto {

    int id, plazas;
    String nombre;
    double salario;
    String salarioFormat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance();
        salarioFormat = formatoImporte.format(salario);
    }

    public String getSalarioFormat() {
        return salarioFormat;
    }

}
