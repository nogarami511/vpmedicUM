/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.CategoriaPago;
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
public class TipoPagoDAO {
    private Connection connection;

    public TipoPagoDAO(Connection connection) {
        this.connection = connection;
    }

    public void crear(CategoriaPago tipoPago) throws SQLException {
        String query = "INSERT INTO tipo_pago (nombre, fecha_moficacion, usuario_modificacion) VALUES (?, NOW(), ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tipoPago.getNombre());
            statement.setInt(2, tipoPago.getUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                tipoPago.setId(generatedKeys.getInt(1));
            }
        }
    }

    public CategoriaPago leer(int id) throws SQLException {
        String query = "SELECT * FROM tipo_pago WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    CategoriaPago tipoPago = mapearTipoPago(resultSet);
                    return tipoPago;
                }
            }
        }
        return null;
    }

    public List<CategoriaPago> obtenerTodos() throws SQLException {
        List<CategoriaPago> listaTipoPagos = new ArrayList<>();
        String query = "SELECT * FROM tipo_pago";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CategoriaPago tipoPago = mapearTipoPago(resultSet);
                listaTipoPagos.add(tipoPago);
            }
        }
        return listaTipoPagos;
    }

    public void actualizar(CategoriaPago tipoPago) throws SQLException {
        String query = "UPDATE tipo_pago SET nombre = ?, fecha_modificacion = NOW(), fecha_moficacion = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tipoPago.getNombre());
            statement.setInt(3, tipoPago.getUsuarioModificacion());
            statement.setInt(4, tipoPago.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM tipo_pago WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private CategoriaPago mapearTipoPago(ResultSet resultSet) throws SQLException {
        CategoriaPago tipoPago = new CategoriaPago();
        tipoPago.setId(resultSet.getInt("id"));
        tipoPago.setNombre(resultSet.getString("nombre"));
        tipoPago.setFechaModificacion(resultSet.getTimestamp("fecha_moficacion"));
        tipoPago.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        return tipoPago;
    }
}
