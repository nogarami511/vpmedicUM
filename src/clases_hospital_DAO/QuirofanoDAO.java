/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Quirofano;
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
public class QuirofanoDAO {
    private Connection connection;

    public QuirofanoDAO(Connection connection) {
        this.connection = connection;
    }

    public void crear(Quirofano quirofano) throws SQLException {
        String query = "INSERT INTO quirofanos (id, nombre, tipo_procedimiento, descripcion, id_usuarioModificacion, fechaModificacion, costo, id_estatus, id_tipo_procedimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quirofano.getId());
            statement.setString(2, quirofano.getNombre());
            statement.setString(3, quirofano.getTipo_procedimiento());
            statement.setString(4, quirofano.getDescripcion());
            statement.setInt(5, quirofano.getIdUsuarioModificacion());
            statement.setTimestamp(6, quirofano.getFechaModificacion());
            statement.setDouble(7, quirofano.getCosto());
            statement.setInt(8, quirofano.getIdEstatus());
            statement.setInt(9, quirofano.getId_tipo_procedimiento());

            statement.executeUpdate();
        }
    }

    public Quirofano leer(int id) throws SQLException {
        String query = "SELECT * FROM quirofanos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Quirofano quirofano = mapearQuirofano(resultSet);
                    return quirofano;
                }
            }
        }
        return null;
    }
    
    public Quirofano leerPorIdTipoQuirofano(int id_tipo_procedimiento) throws SQLException {
        String query = "SELECT * FROM quirofanos WHERE id_tipo_procedimiento = ? ORDER BY id ASC LIMIT 1;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_tipo_procedimiento);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Quirofano quirofano = mapearQuirofano(resultSet);
                    return quirofano;
                }
            }
        }
        return null;
    }
    
    public Quirofano optenerQuirofanoPorId (int id) throws SQLException {
        String query = "SELECT q.* FROM quirofanos q INNER JOIN procedimiento p ON p.id_tipo_procedimiento = q.id_tipo_procedimiento WHERE p.id = ? ORDER BY q.id ASC LIMIT 1;";
        try (PreparedStatement statemet = connection.prepareStatement(query)) {
            statemet.setInt(1, id);
            try (ResultSet resultSet = statemet.executeQuery()) {
                if (resultSet.next()) {
                    return mapearQuirofano(resultSet);
                }
            }
        }
        return null;
    }

    public List<Quirofano> obtenerTodos() throws SQLException {
        List<Quirofano> listaQuirofanos = new ArrayList<>();
        String query = "SELECT * FROM quirofanos";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Quirofano quirofano = mapearQuirofano(resultSet);
                listaQuirofanos.add(quirofano);
            }
        }
        return listaQuirofanos;
    }

    public void actualizar(Quirofano quirofano) throws SQLException {
        String query = "UPDATE quirofanos SET nombre = ?, tipo_procedimiento = ?, descripcion = ?, id_usuarioModificacion = ?, fechaModificacion = ?, costo = ?, id_estatus = ?, id_tipo_procedimiento = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quirofano.getNombre());
            statement.setString(2, quirofano.getTipo_procedimiento());
            statement.setString(3, quirofano.getDescripcion());
            statement.setInt(4, quirofano.getIdUsuarioModificacion());
            statement.setTimestamp(5, quirofano.getFechaModificacion());
            statement.setDouble(6, quirofano.getCosto());
            statement.setInt(7, quirofano.getIdEstatus());
            statement.setInt(8, quirofano.getId_tipo_procedimiento());
            statement.setInt(9, quirofano.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM quirofanos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Quirofano mapearQuirofano(ResultSet resultSet) throws SQLException {
        Quirofano quirofano = new Quirofano();
        quirofano.setId(resultSet.getInt("id"));
        quirofano.setNombre(resultSet.getString("nombre"));
        quirofano.setTipo_procedimiento(resultSet.getString("tipo_procedimiento"));
        quirofano.setDescripcion(resultSet.getString("descripcion"));
        quirofano.setIdUsuarioModificacion(resultSet.getInt("id_usuarioModificacion"));
        quirofano.setFechaModificacion(resultSet.getTimestamp("fechaModificacion"));
        quirofano.setCosto(resultSet.getDouble("costo"));
        quirofano.setIdEstatus(resultSet.getInt("id_estatus"));
        quirofano.setId_tipo_procedimiento(resultSet.getInt("id_tipo_procedimiento"));
        return quirofano;
    }
}
