/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Especialidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class EspeciaidadesDAO {
     private Connection connection;

    public EspeciaidadesDAO(Connection connection) {
        this.connection = connection;
    }

    public void crear(Especialidad especialidad) throws SQLException {
        String query = "INSERT INTO especialidades (nombre, id_usuarioModificacion, fechaModificacion) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, especialidad.getNombre());
            statement.setInt(2, especialidad.getId_usuarioModificacion());
            statement.setTimestamp(3, new Timestamp(especialidad.getFechaModificacion().getTime()));

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                especialidad.setId(generatedKeys.getInt(1));
            }
        }
    }

    public Especialidad leer(int id) throws SQLException {
        String query = "SELECT * FROM especialidades WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Especialidad especialidad = mapearEspecialidad(resultSet);
                    return especialidad;
                }
            }
        }
        return null;
    }

    public List<Especialidad> obtenerTodos() throws SQLException {
        List<Especialidad> listaEspecialidades = new ArrayList<>();
        String query = "SELECT * FROM especialidades";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Especialidad especialidad = mapearEspecialidad(resultSet);
                listaEspecialidades.add(especialidad);
            }
        }
        return listaEspecialidades;
    }

    public void actualizar(Especialidad especialidad) throws SQLException {
        String query = "UPDATE especialidades SET nombre = ?, id_usuarioModificacion = ?, fechaModificacion = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, especialidad.getNombre());
            statement.setInt(2, especialidad.getId_usuarioModificacion());
            statement.setTimestamp(3, new Timestamp(especialidad.getFechaModificacion().getTime()));
            statement.setInt(4, especialidad.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM especialidades WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Especialidad mapearEspecialidad(ResultSet resultSet) throws SQLException {
        Especialidad especialidad = new Especialidad();
        especialidad.setId(resultSet.getInt("id"));
        especialidad.setNombre(resultSet.getString("nombre"));
        especialidad.setId_usuarioModificacion(resultSet.getInt("id_usuarioModificacion"));
        especialidad.setFechaModificacion(resultSet.getDate("fechaModificacion"));
        return especialidad;
    }
}
