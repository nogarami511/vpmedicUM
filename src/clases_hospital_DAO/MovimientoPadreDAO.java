/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.MovimientoInventarioP;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class MovimientoPadreDAO {

    private Connection connection;

    public MovimientoPadreDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarMovimientoInventarioP(MovimientoInventarioP movimiento) throws SQLException {
        String query = "INSERT INTO movimientos_inventariop (tipo_mov, id_proveedor, id_origen, id_destino, folio_mov, subtotal, importe_impuesto, descuento, total, estatus_movimiento, observaciones, usuario_registro, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, movimiento.getTipo_mov());
            statement.setInt(2, movimiento.getId_proveedor());
            statement.setInt(3, movimiento.getId_origen());
            statement.setInt(4, movimiento.getId_destino());
            statement.setString(5, movimiento.getFolio_mov());
            statement.setDouble(6, movimiento.getSubtotal());
            statement.setDouble(7, movimiento.getImporte_impuesto());
            statement.setDouble(8, movimiento.getDescuento());
            statement.setDouble(9, movimiento.getTotal());
            statement.setInt(10, movimiento.getEstatus_movimiento());
            statement.setString(11, movimiento.getObservaciones());
            statement.setInt(12, movimiento.getUsuario_registro());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                movimiento.setId(generatedKeys.getInt(1));
            }
        }
    }

    public int agregarMovimientoInventarioPINT(MovimientoInventarioP movimiento) throws SQLException {
        int id;
        String query = "INSERT INTO movimientos_inventariop (tipo_mov, id_proveedor, id_origen, id_destino, folio_mov, subtotal, importe_impuesto, descuento, total, estatus_movimiento, observaciones, usuario_registro, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, movimiento.getTipo_mov());
            statement.setInt(2, movimiento.getId_proveedor());
            statement.setInt(3, movimiento.getId_origen());
            statement.setInt(4, movimiento.getId_destino());
            statement.setString(5, movimiento.getFolio_mov());
            statement.setDouble(6, movimiento.getSubtotal());
            statement.setDouble(7, movimiento.getImporte_impuesto());
            statement.setDouble(8, movimiento.getDescuento());
            statement.setDouble(9, movimiento.getTotal());
            statement.setInt(10, movimiento.getEstatus_movimiento());
            statement.setString(11, movimiento.getObservaciones());
            statement.setInt(12, movimiento.getUsuario_registro());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            } else {
                id = 0;
            }
        }
       
        return id;
    }

    public void actualizarMovimientoInventarioP(MovimientoInventarioP movimiento) throws SQLException {
        String query = "UPDATE movimientos_inventariop SET tipo_mov=?, id_proveedor=?, id_origen=?, id_destino=?, folio_mov=?, subtotal=?, importe_impuesto=?, descuento=?, total=?, estatus_movimiento=?, observaciones=?, usuario_registro=?, fecha_registro=NOW() WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movimiento.getTipo_mov());
            statement.setInt(2, movimiento.getId_proveedor());
            statement.setInt(3, movimiento.getId_origen());
            statement.setInt(4, movimiento.getId_destino());
            statement.setString(5, movimiento.getFolio_mov());
            statement.setDouble(6, movimiento.getSubtotal());
            statement.setDouble(7, movimiento.getImporte_impuesto());
            statement.setDouble(8, movimiento.getDescuento());
            statement.setDouble(9, movimiento.getTotal());
            statement.setInt(10, movimiento.getEstatus_movimiento());
            statement.setString(11, movimiento.getObservaciones());
            statement.setInt(12, movimiento.getUsuario_registro());
            statement.setInt(13, movimiento.getId());

            statement.executeUpdate();
        }
    }

    public void eliminarMovimientoInventarioP(int id) throws SQLException {
        String query = "DELETE FROM movimientos_inventariop WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<MovimientoInventarioP> obtenerTodosMovimientosInventarioP() throws SQLException {
        List<MovimientoInventarioP> movimientos = new ArrayList<>();
        String query = "SELECT * FROM movimientos_inventariop";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                MovimientoInventarioP movimiento = mapearMovimientoInventarioP(resultSet);
                movimientos.add(movimiento);
            }
        }

        return movimientos;
    }

    public MovimientoInventarioP obtenerMovimientoInventarioPPorId(int id) throws SQLException {
        String query = "SELECT * FROM movimientos_inventariop WHERE id=?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearMovimientoInventarioP(resultSet);
                }
            }
        }

        return null;
    }

    public MovimientoInventarioP obtenerMovimientoInventarioPPorFolio(String folio) throws SQLException {
        String query = "SELECT * FROM movimientos_inventariop WHERE folio_mov = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearMovimientoInventarioP(resultSet);
                }
            }
        }

        return null;
    }

    private MovimientoInventarioP mapearMovimientoInventarioP(ResultSet resultSet) throws SQLException {
        MovimientoInventarioP movimiento = new MovimientoInventarioP();
        movimiento.setId(resultSet.getInt("id"));
        movimiento.setTipo_mov(resultSet.getInt("tipo_mov"));
        movimiento.setId_proveedor(resultSet.getInt("id_proveedor"));
        movimiento.setId_origen(resultSet.getInt("id_origen"));
        movimiento.setId_destino(resultSet.getInt("id_destino"));
        movimiento.setFolio_mov(resultSet.getString("folio_mov"));
        movimiento.setSubtotal(resultSet.getDouble("subtotal"));
        movimiento.setImporte_impuesto(resultSet.getDouble("importe_impuesto"));
        movimiento.setDescuento(resultSet.getDouble("descuento"));
        movimiento.setTotal(resultSet.getInt("total"));
        movimiento.setEstatus_movimiento(resultSet.getInt("estatus_movimiento"));
        movimiento.setObservaciones(resultSet.getString("observaciones"));
        movimiento.setUsuario_registro(resultSet.getInt("usuario_registro"));
        movimiento.setFecha_registro(resultSet.getDate("fecha_registro"));

        return movimiento;
    }
}
