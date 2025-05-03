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
public class NumerosALetras {

    double cantidad;
    String cantidadString;
    int centavos;

    public NumerosALetras(double cantidad) {
        this.cantidad = cantidad;
        conversionDatos();
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    private void conversionDatos() {
        this.cantidadString = convertirNumeroALetras((int) cantidad);
        this.centavos = (int) (Math.round((cantidad % 1) * 100));
    }

    public String convertirNumeroALetras(int numero) {
        String[] unidades = {"", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"};
        String[] decenas = {"", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
        String[] centenas = {"", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};

        String letras = "";

        if (numero < 0) {
            letras = "menos " + convertirNumeroALetras(Math.abs(numero));
        } else if (numero < 20) {
            letras = unidades[numero];
        } else if (numero < 100) {
            letras = decenas[numero / 10];
            if ((numero % 10) > 0) {
                letras = letras + " y " + unidades[numero % 10];
            }
        } else if (numero < 1000) {
            letras = centenas[numero / 100];
            if ((numero % 100) > 0) {
                letras = letras + " " + convertirNumeroALetras(numero % 100);
            }
        } else if (numero < 1000000) {
            letras = convertirNumeroALetras(numero / 1000) + " mil";
            if ((numero % 1000) > 0) {
                letras = letras + " " + convertirNumeroALetras(numero % 1000);
            }
        } else {
            letras = "Número fuera de rango";
        }
        return letras;
    }

    public String getCantidadString() {
        String centavosSting;
        if(centavos < 10){
            centavosSting = "0" + centavos;
        } else {
            centavosSting = "" + centavos;
        }
        return "(" + cantidadString.toUpperCase() + " PESOS " + centavosSting + "/100 M.N.)";
    }
    
}
