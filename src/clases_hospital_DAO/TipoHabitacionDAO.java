/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.TipoHabitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class TipoHabitacionDAO {

    private Connection connection;

    public TipoHabitacionDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(TipoHabitacion tipoHabitacion) {
        String query = "INSERT INTO tipoHabitacion (tipo) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tipoHabitacion.getTipo());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                tipoHabitacion.setIdTipo(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TipoHabitacion read(int idTipo) {
        String query = "SELECT * FROM tipoHabitacion WHERE id_tipo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipo);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createTipoHabitacionFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(TipoHabitacion tipoHabitacion) {
        String query = "UPDATE tipoHabitacion SET tipo = ? WHERE id_tipo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tipoHabitacion.getTipo());
            statement.setInt(2, tipoHabitacion.getIdTipo());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int idTipo) {
        String query = "DELETE FROM tipoHabitacion WHERE id_tipo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipo);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TipoHabitacion> getAllTiposHabitacion() {
        List<TipoHabitacion> tiposHabitacion = new ArrayList<>();

        String query = "SELECT * FROM tipoHabitacion";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                TipoHabitacion tipoHabitacion = createTipoHabitacionFromResultSet(resultSet);
                tiposHabitacion.add(tipoHabitacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiposHabitacion;
    }

    private TipoHabitacion createTipoHabitacionFromResultSet(ResultSet resultSet) throws SQLException {
        TipoHabitacion tipoHabitacion = new TipoHabitacion();
        tipoHabitacion.setIdTipo(resultSet.getInt("id_tipo"));
        tipoHabitacion.setTipo(resultSet.getString("tipo"));

        return tipoHabitacion;
    }
}
