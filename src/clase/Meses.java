/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Meses {



    private int numeroMes;
    private String nombreMes;
    private ObservableList<Meses> meses = FXCollections.observableArrayList();

    // Constructor
    public Meses() {
        // Meses del año predefinidos como objetos 
        meses.add(new Meses(1, "ENERO"));
        meses.add(new Meses(2, "FEBRERO"));
        meses.add(new Meses(3, "MARZO"));
        meses.add(new Meses(4, "ABRIL"));
        meses.add(new Meses(5, "MAYO"));
        meses.add(new Meses(6, "JUNIO"));
        meses.add(new Meses(7, "JULIO"));
        meses.add(new Meses(8, "AGOSTO"));
        meses.add(new Meses(9, "SEPTIEMBRE"));
        meses.add(new Meses(10, "OCTUBRE"));
        meses.add(new Meses(11, "NOVIEMBRE"));
        meses.add(new Meses(12, "DICIEMBRE"));

    }

    public Meses(int numeroMes, String nombreMes) {
        this.numeroMes = numeroMes;
        this.nombreMes = nombreMes;
    }

// Getters
    public int getNumeroMes() {
        return numeroMes;
    }

    public String getNombreMes() {
        return nombreMes;
    }

    public ObservableList<Meses> mesesAnio() {
        return this.meses;
    }

    @Override
    public String toString() {
        return nombreMes;
    }

}
