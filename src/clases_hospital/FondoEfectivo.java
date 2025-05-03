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
import java.sql.Timestamp;

public class FondoEfectivo {

    Conexion conexion = new Conexion();
    Connection con;

    private int idFondoEfectivo;
    private Timestamp fecha;
    private String tipoOperacion;
    private double importe;
    private double saldo;
    private String concepto;
    private int idCliente; //idPaciente

    private double totalFondoEfectivoFijo;
  
     public FondoEfectivo(){}
    // Constructor
    public FondoEfectivo(int idFolioEfectivo, Timestamp fecha, String tipoOperacion, double importe, double saldo, String concepto, int idCliente) throws SQLException {
        this.idFondoEfectivo = idFolioEfectivo;
        this.fecha = fecha;
        this.tipoOperacion = tipoOperacion;
        this.importe = importe;
        this.saldo = saldo;
        this.concepto = concepto;
        this.idCliente = idCliente;
        //traerFondoEfectivoFijo();
    }


    public void traerFondoEfectivoFijo() throws SQLException {
        con = conexion.conectar2();
        String query = "SELECT * FROM fondoefectivofijo f";

        try (PreparedStatement statement = con.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalFondoEfectivoFijo = resultSet.getDouble(2);
            }
        }
        con.close();

    }

    public double getTotalFondoEfectivoFijo() {
        return totalFondoEfectivoFijo;
    }

    // Getters and Setters

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getIdFondoEfectivo() {
        return idFondoEfectivo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
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

    // Other methods, if any
}
