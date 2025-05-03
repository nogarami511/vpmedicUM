/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import clase.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfiguracionPaquete {

    Conexion conexion = new Conexion();
    Connection con;

    private int id;
    private int id_insumo;
    private String nombre_insumo;
    private double precio_insumo;
    private int id_folio;
    private double sumaPaqueteBase;
    private double sumaConfiguracion;
    private int cantidad;

    public ConfiguracionPaquete() throws SQLException {

    }

    public ConfiguracionPaquete(int id, int id_insumo, String nombre_insumo, double precio_insumo, int id_folio) throws SQLException {
        this.id = id;
        this.id_insumo = id_insumo;
        this.nombre_insumo = nombre_insumo;
        this.precio_insumo = precio_insumo;
        this.id_folio = id_folio;
        traerNombreInsumoVariante();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void traerNombreInsumoVariante() throws SQLException {
        con = conexion.conectar2();
        String query = "SELECT i.nombre FROM insumos i WHERE i.id = '" + id_insumo + "' ";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            nombre_insumo = rs.getString(1);
        }
        con.close();
    }

    public Conexion getConexion() {
        return conexion;
    }

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public String getNombre_insumo() {
        return nombre_insumo;
    }

    public void setNombre_insumo(String nombre_insumo) {
        this.nombre_insumo = nombre_insumo;
    }

    public double getPrecio_insumo() {
        return precio_insumo;
    }

    public void setPrecio_insumo(double precio_insumo) {
        this.precio_insumo = precio_insumo;
    }

    public int getId_folio() {
        return id_folio;
    }

    public void setId_folio(int id_folio) {
        this.id_folio = id_folio;
    }

    @Override
    public String toString() {
        return "ConfiguracionPaquete{" + "id=" + id + ", id_insumo=" + id_insumo + ", nombre_insumo=" + nombre_insumo + ", precio_insumo=" + precio_insumo + ", id_folio=" + id_folio + '}';
    }

}
