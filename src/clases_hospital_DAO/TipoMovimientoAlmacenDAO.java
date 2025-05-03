/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.TipoMovimientoAlmacen;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class TipoMovimientoAlmacenDAO {
    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public TipoMovimientoAlmacenDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para seleccionar todos los registros con tipo = "+"
    public List<TipoMovimientoAlmacen> seleccionarTipoMovimientoPositivos() throws SQLException {
        List<TipoMovimientoAlmacen> movimientos = new ArrayList<>();

        String query = "SELECT * FROM tipo_movimiento_almacen WHERE tipo = '+' AND activo = 1";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                TipoMovimientoAlmacen movimiento = new TipoMovimientoAlmacen();
                movimiento.setId_movimiento(resultSet.getInt("id_movimiento"));
                movimiento.setTipo_movimiento(resultSet.getString("tipo_movimiento"));
                movimiento.setTipo(resultSet.getString("tipo"));
                movimiento.setActivo(resultSet.getInt("activo"));
                movimientos.add(movimiento);
            }
        }

        return movimientos;
    }

    // Método para seleccionar todos los registros con tipo = "-"
    public List<TipoMovimientoAlmacen> seleccionarTipoMovimientoNegativos() throws SQLException {
        List<TipoMovimientoAlmacen> movimientos = new ArrayList<>();

        String query = "SELECT * FROM tipo_movimiento_almacen WHERE tipo = '-' AND activo = 1";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                TipoMovimientoAlmacen movimiento = new TipoMovimientoAlmacen();
                movimiento.setId_movimiento(resultSet.getInt("id_movimiento"));
                movimiento.setTipo_movimiento(resultSet.getString("tipo_movimiento"));
                movimiento.setTipo(resultSet.getString("tipo"));
                movimiento.setActivo(resultSet.getInt("activo"));
                movimientos.add(movimiento);
            }
        }

        return movimientos;
    }
}
