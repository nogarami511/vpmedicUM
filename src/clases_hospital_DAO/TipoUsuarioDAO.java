/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoUsuarioDAO {

    private Connection connection;

    public TipoUsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(TipoUsuario tipoUsuario) throws SQLException {
        String query = "INSERT INTO tipo_usuario (nombre) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tipoUsuario.getNombre());
            statement.executeUpdate();
        }
    }

    public void update(TipoUsuario tipoUsuario) throws SQLException {
        String query = "UPDATE tipo_usuario SET nombre = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tipoUsuario.getNombre());
            statement.setInt(2, tipoUsuario.getId());
            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM tipo_usuario WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public TipoUsuario getById(int id) throws SQLException {
        String query = "SELECT id, nombre FROM tipo_usuario WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    TipoUsuario tipoUsuario = new TipoUsuario();
                    tipoUsuario.setId(resultSet.getInt("id"));
                    tipoUsuario.setNombre(resultSet.getString("nombre"));
                    return tipoUsuario;
                }
            }
        }
        return null;
    }

    public List<TipoUsuario> obtenerTodos() throws SQLException {
        List<TipoUsuario> tipoUsuarios = new ArrayList<>();
        String query = "SELECT id, nombre FROM tipo_usuario";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                TipoUsuario tipoUsuario = new TipoUsuario();
                tipoUsuario.setId(resultSet.getInt("id"));
                tipoUsuario.setNombre(resultSet.getString("nombre"));
                tipoUsuarios.add(tipoUsuario);
            }
        }
        return tipoUsuarios;
    }
}
