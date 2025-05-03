/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Ministracion;
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
public class MinistracionDAO {
    private Connection connection;

    public MinistracionDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(Ministracion ministracion) throws SQLException {
        String query = "INSERT INTO ministracion (nombre) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ministracion.getNombre());
            statement.executeUpdate();
        }
    }

    public void actualizar(Ministracion ministracion) throws SQLException {
        String query = "UPDATE ministracion SET nombre = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ministracion.getNombre());
            statement.setInt(2, ministracion.getId());
            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM ministracion WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Ministracion obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM ministracion WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearMinistracionDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<Ministracion> obtenerTodos() throws SQLException {
        List<Ministracion> ministraciones = new ArrayList<>();
        String query = "SELECT * FROM ministracion";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Ministracion ministracion = crearMinistracionDesdeResultSet(resultSet);
                ministraciones.add(ministracion);
            }
        }

        return ministraciones;
    }

    private Ministracion crearMinistracionDesdeResultSet(ResultSet resultSet) throws SQLException {
        Ministracion ministracion = new Ministracion();
        ministracion.setId(resultSet.getInt("id"));
        ministracion.setNombre(resultSet.getString("nombre"));
        return ministracion;
    }
}
