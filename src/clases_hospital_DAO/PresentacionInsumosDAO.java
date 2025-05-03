/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.PresentacionInsumos;
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
public class PresentacionInsumosDAO {
    private Connection connection;

    public PresentacionInsumosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarPresentacionInsumos(PresentacionInsumos presentacion) throws SQLException {
        String query = "INSERT INTO presentaciones_insumos (presentacion, id_estatus, fecha_modificacion, usuario_modificacion) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, presentacion.getPresentacion());
            statement.setInt(2, presentacion.getId_estatus());
            statement.setDate(3, presentacion.getFecha_modificacion());
            statement.setInt(4, presentacion.getUsuario_modificacion());

            statement.executeUpdate();
        }
    }

    public void actualizarPresentacionInsumos(PresentacionInsumos presentacion) throws SQLException {
        String query = "UPDATE presentaciones_insumos SET presentacion = ?, id_estatus = ?, fecha_modificacion = ?, " +
                "usuario_modificacion = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, presentacion.getPresentacion());
            statement.setInt(2, presentacion.getId_estatus());
            statement.setDate(3, presentacion.getFecha_modificacion());
            statement.setInt(4, presentacion.getUsuario_modificacion());
            statement.setInt(5, presentacion.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM presentaciones_insumos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public PresentacionInsumos obtenerPresentacionInsumosPorId(int id) throws SQLException {
        String query = "SELECT * FROM presentaciones_insumos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearPresentacion(resultSet);
                }
            }
        }

        return null;
    }

    public List<PresentacionInsumos> obtenerPresentacionInsumosTodos() throws SQLException {
        List<PresentacionInsumos> presentaciones = new ArrayList<>();
        String query = "SELECT * FROM presentaciones_insumos";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                PresentacionInsumos presentacion = mapearPresentacion(resultSet);
                presentaciones.add(presentacion);
            }
        }

        return presentaciones;
    }

    private PresentacionInsumos mapearPresentacion(ResultSet resultSet) throws SQLException {
        PresentacionInsumos presentacion = new PresentacionInsumos();

        presentacion.setId(resultSet.getInt("id"));
        presentacion.setPresentacion(resultSet.getString("presentacion"));
        presentacion.setId_estatus(resultSet.getInt("id_estatus"));
        presentacion.setFecha_modificacion(resultSet.getDate("fecha_modificacion"));
        presentacion.setUsuario_modificacion(resultSet.getInt("usuario_modificacion"));

        return presentacion;
    }
}
