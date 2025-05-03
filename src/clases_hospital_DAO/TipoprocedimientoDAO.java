/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;
import clases_hospital.TipoProcedimiento;
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
public class TipoprocedimientoDAO {
    private Connection connection;

    public TipoprocedimientoDAO(Connection connection) {
        this.connection = connection;
    }

    public void crear(TipoProcedimiento tipoProcedimiento) throws SQLException {
        String query = "INSERT INTO tipoprocedimiento (nombre, categoria, costo, idUsuarioModificacion, fechaModificacion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tipoProcedimiento.getNombre());
            statement.setString(2, tipoProcedimiento.getCategoria());
            statement.setDouble(3, tipoProcedimiento.getCosto());
            statement.setInt(4, tipoProcedimiento.getIdUsuarioModificacion());
            statement.setTimestamp(5, tipoProcedimiento.getFechaModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                tipoProcedimiento.setId(generatedKeys.getInt(1));
            }
        }
    }

    public TipoProcedimiento leer(int id) throws SQLException {
        String query = "SELECT * FROM tipoprocedimiento WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    TipoProcedimiento tipoProcedimiento = mapearTipoProcedimiento(resultSet);
                    return tipoProcedimiento;
                }
            }
        }
        return null;
    }

    public List<TipoProcedimiento> obtenerTodos() throws SQLException {
        List<TipoProcedimiento> listaTipoProcedimientos = new ArrayList<>();
        String query = "SELECT * FROM tipoprocedimiento";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                TipoProcedimiento tipoProcedimiento = mapearTipoProcedimiento(resultSet);
                listaTipoProcedimientos.add(tipoProcedimiento);
            }
        }
        return listaTipoProcedimientos;
    }

    public void actualizar(TipoProcedimiento tipoProcedimiento) throws SQLException {
        String query = "UPDATE tipoprocedimiento SET nombre = ?, categoria = ?, costo = ?, idUsuarioModificacion = ?, fechaModificacion = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tipoProcedimiento.getNombre());
            statement.setString(2, tipoProcedimiento.getCategoria());
            statement.setDouble(3, tipoProcedimiento.getCosto());
            statement.setInt(4, tipoProcedimiento.getIdUsuarioModificacion());
            statement.setTimestamp(5, tipoProcedimiento.getFechaModificacion());
            statement.setInt(6, tipoProcedimiento.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM tipoprocedimiento WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private TipoProcedimiento mapearTipoProcedimiento(ResultSet resultSet) throws SQLException {
        TipoProcedimiento tipoProcedimiento = new TipoProcedimiento();
        tipoProcedimiento.setId(resultSet.getInt("id"));
        tipoProcedimiento.setNombre(resultSet.getString("nombre"));
        tipoProcedimiento.setCategoria(resultSet.getString("categoria"));
        tipoProcedimiento.setCosto(resultSet.getDouble("costo"));
        tipoProcedimiento.setIdUsuarioModificacion(resultSet.getInt("idUsuarioModificacion"));
        tipoProcedimiento.setFechaModificacion(resultSet.getTimestamp("fechaModificacion"));
        return tipoProcedimiento;
    }
}
