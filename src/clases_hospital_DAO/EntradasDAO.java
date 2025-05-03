/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Entradas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class EntradasDAO {

    private Connection connection;

    public EntradasDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(Entradas entrada) throws SQLException {
        String query = "INSERT INTO entradas (id_insumo, tipo_entrada, id_proveedor, costo_compra_total, fecha_entrada, "
                + "lote_compra, lote_insumo, cantidad_caja, caducidad, inventario_inicial, entrada, inventario_final, "
                + "id_usuario_mod, fecha_mod) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(2, entrada.getTipo_entrada());
            statement.setInt(3, entrada.getId_proveedor());
            statement.setDouble(4, entrada.getCosto_compra_total());
            statement.setDate(5, entrada.getFecha_entrada());
            statement.setString(6, entrada.getLote_compra());
            statement.setString(7, entrada.getLote_insumo());
            statement.setInt(8, entrada.getCantidad_caja());
            statement.setDate(9, entrada.getCaducidad());
            statement.setInt(10, entrada.getInventario_inicial());
            statement.setInt(11, entrada.getEntrada());
            statement.setInt(12, entrada.getInventario_final());
            statement.setInt(13, entrada.getId_usuario_mod());
            statement.setDate(14, entrada.getFecha_mod());

            statement.executeUpdate();
        }
    }

    public void actualizar(Entradas entrada) throws SQLException {
        String query = "UPDATE entradas SET id_insumo = ?, tipo_entrada = ?, id_proveedor = ?, costo_compra_total = ?, "
                + "fecha_entrada = ?, lote_compra = ?, lote_insumo = ?, cantidad_caja = ?, caducidad = ?, "
                + "inventario_inicial = ?, entrada = ?, inventario_final = ?, id_usuario_mod = ?, fecha_mod = ? "
                + "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entrada.getId_insumo());
            statement.setInt(2, entrada.getTipo_entrada());
            statement.setInt(3, entrada.getId_proveedor());
            statement.setDouble(4, entrada.getCosto_compra_total());
            statement.setDate(5, entrada.getFecha_entrada());
            statement.setString(6, entrada.getLote_compra());
            statement.setString(7, entrada.getLote_insumo());
            statement.setInt(8, entrada.getCantidad_caja());
            statement.setDate(9, entrada.getCaducidad());
            statement.setInt(10, entrada.getInventario_inicial());
            statement.setInt(11, entrada.getEntrada());
            statement.setInt(12, entrada.getInventario_final());
            statement.setInt(13, entrada.getId_usuario_mod());
            statement.setDate(14, entrada.getFecha_mod());
            statement.setInt(15, entrada.getId());
            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM entradas WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Entradas obtenerPorId(int id) throws SQLException {
        String query = "SELECT e.*, i.nombre FROM entradas e INNER JOIN insumos i ON e.id_insumo = i.id WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearEntradaDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<Entradas> obtenerTodos() throws SQLException {
        List<Entradas> entradas = new ArrayList<>();
        String query = "SELECT e.*, i.nombre FROM entradas e INNER JOIN insumos i ON e.id_insumo = i.id";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Entradas entrada = crearEntradaDesdeResultSet(resultSet);
                entradas.add(entrada);
            }
        }

        return entradas;
    }

    private Entradas crearEntradaDesdeResultSet(ResultSet resultSet) throws SQLException {
        Entradas entrada = new Entradas();
        entrada.setId(resultSet.getInt("id"));
        entrada.setId_insumo(resultSet.getInt("id_insumo"));
        entrada.setTipo_entrada(resultSet.getInt("tipo_entrada"));
        entrada.setId_proveedor(resultSet.getInt("id_proveedor"));
        entrada.setCosto_compra_total(resultSet.getDouble("costo_compra_total"));
        entrada.setFecha_entrada(resultSet.getDate("fecha_entrada"));
        entrada.setLote_compra(resultSet.getString("lote_compra"));
        entrada.setLote_insumo(resultSet.getString("lote_insumo"));
        entrada.setCantidad_caja(resultSet.getInt("cantidad_caja"));
        entrada.setCaducidad(resultSet.getDate("caducidad"));
        entrada.setInventario_inicial(resultSet.getInt("inventario_inicial"));
        entrada.setEntrada(resultSet.getInt("entrada"));
        entrada.setInventario_final(resultSet.getInt("inventario_final"));
        entrada.setId_usuario_mod(resultSet.getInt("id_usuario_mod"));
        entrada.setFecha_mod(resultSet.getDate("fecha_mod"));
        entrada.setNombre(resultSet.getString("nombre"));
        return entrada;
    }

}
