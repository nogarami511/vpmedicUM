/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Rubro;
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
public class RubrosDAO {
    private Connection connection;

    public RubrosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(Rubro rubro) throws SQLException {
        String query = "INSERT INTO rubros (nombre, monto, observaciones, ministracion, gasto_mintracion) VALUES (?, ?, ?, ?, 0)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rubro.getNombre());
            statement.setDouble(2, rubro.getMonto());
            statement.setString(3, rubro.getObservaciones());
            statement.setInt(4, rubro.getMinistracion());

            statement.executeUpdate();
        }
    }

    public void actualizar(Rubro rubro) throws SQLException {
        String query = "UPDATE rubros SET nombre = ?, monto = ?, observaciones = ?, ministracion = ?, gasto_mintracion = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rubro.getNombre());
            statement.setDouble(2, rubro.getMonto());
            statement.setString(3, rubro.getObservaciones());
            statement.setInt(4, rubro.getMinistracion());
            statement.setDouble(5, rubro.getGasto_mintracion());
            statement.setInt(6, rubro.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM rubros WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Rubro obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM rubros WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearRubroDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<Rubro> obtenerTodos() throws SQLException {
        List<Rubro> rubros = new ArrayList<>();
        String query = "SELECT * FROM rubros";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Rubro rubro = crearRubroDesdeResultSet(resultSet);
                rubros.add(rubro);
            }
        }

        return rubros;
    }
    
    public List<Rubro> obtenerTodosConNombreMinistracion() throws SQLException {
        List<Rubro> rubros = new ArrayList<>();
        String query = "SELECT r.*, m.nombre AS nombreminis FROM rubros r INNER JOIN ministracion m ON r.ministracion = m.id;";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Rubro rubro = crearRubroDesdeResultSetConNombreMinistracion(resultSet);
                rubros.add(rubro);
            }
        }

        return rubros;
    }

    private Rubro crearRubroDesdeResultSet(ResultSet resultSet) throws SQLException {
        Rubro rubro = new Rubro();
        rubro.setId(resultSet.getInt("id"));
        rubro.setNombre(resultSet.getString("nombre"));
        rubro.setMonto(resultSet.getDouble("monto"));
        rubro.setObservaciones(resultSet.getString("observaciones"));
        rubro.setMinistracion(resultSet.getInt("ministracion"));
        rubro.setGasto_mintracion(resultSet.getDouble("gasto_mintracion"));
        return rubro;
    }
    
    private Rubro crearRubroDesdeResultSetConNombreMinistracion(ResultSet resultSet) throws SQLException {
        Rubro rubro = new Rubro();
        rubro.setId(resultSet.getInt("id"));
        rubro.setNombre(resultSet.getString("nombre"));
        rubro.setMonto(resultSet.getDouble("monto"));
        rubro.setObservaciones(resultSet.getString("observaciones"));
        rubro.setMinistracion(resultSet.getInt("ministracion"));
        rubro.setGasto_mintracion(resultSet.getDouble("gasto_mintracion"));
        rubro.setNombreMinis(resultSet.getString("nombreminis"));
        return rubro;
    }
}
