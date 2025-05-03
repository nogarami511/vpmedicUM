/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import clases_hospital.Almacen;

/**
 *
 * @author alfar
 */
public class AlmacenDAO {
    private Connection connection;

    public AlmacenDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Almacen almacen) throws SQLException {
        String query = "INSERT INTO almacen (almacen, id_estatus, fecha_modificacion, usuario_modificacion, id_area) VALUES (?, ?, NOW(), ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, almacen.getAlmacen());
            statement.setInt(2, almacen.getIdEstatus());
            statement.setInt(3, almacen.getUsuarioModificacion());
            statement.setInt(4, almacen.getIdArea());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                almacen.setIdAlmacen(generatedKeys.getInt(1));
            }
        }
    }

    public void update(Almacen almacen) throws SQLException {
        String query = "UPDATE almacen SET almacen = ?, id_estatus = ?, fecha_modificacion = NOW(), usuario_modificacion = ?, id_area = ? WHERE id_almacen = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, almacen.getAlmacen());
            statement.setInt(2, almacen.getIdEstatus());
            statement.setInt(3, almacen.getUsuarioModificacion());
            statement.setInt(4, almacen.getIdArea());
            statement.setInt(5, almacen.getIdAlmacen());

            statement.executeUpdate();
        }
    }

    public void delete(int idAlmacen) throws SQLException {
        String query = "DELETE FROM almacen WHERE id_almacen = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAlmacen);
            statement.executeUpdate();
        }
    }

    public Almacen getById(int idAlmacen) throws SQLException {
        String query = "SELECT * FROM almacen WHERE id_almacen = ?";
        Almacen almacen = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAlmacen);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                almacen = mapResultSetToAlmacen(resultSet);
            }
        }

        return almacen;
    }

    public List<Almacen> getAll() throws SQLException {
        String query = "SELECT * FROM almacen";
        List<Almacen> almacenes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Almacen almacen = mapResultSetToAlmacen(resultSet);
                almacenes.add(almacen);
            }
        }

        return almacenes;
    }
    
    public List<Almacen> getAllConPiso() throws SQLException {
        String query = "SELECT a.id_almacen, a.almacen, a1.nombre_area, p.nombre_piso FROM almacen a INNER JOIN areas a1 ON a.id_area = a1.id_area INNER JOIN pisos p ON a1.id_piso = p.id_piso";
        List<Almacen> areas = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Almacen area = mapResultSetToAlmacenAreasyPiso(resultSet);
                areas.add(area);
            }
        }

        return areas;
    }

    private Almacen mapResultSetToAlmacen(ResultSet resultSet) throws SQLException {
        Almacen almacen = new Almacen();
        almacen.setIdAlmacen(resultSet.getInt("id_almacen"));
        almacen.setAlmacen(resultSet.getString("almacen"));
        almacen.setIdEstatus(resultSet.getInt("id_estatus"));
        almacen.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        almacen.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        almacen.setIdArea(resultSet.getInt("id_area"));

        return almacen;
    }
    
    private Almacen mapResultSetToAlmacenAreasyPiso(ResultSet resultSet) throws SQLException {
        Almacen almacen = new Almacen();
        almacen.setIdArea(resultSet.getInt("id_almacen"));
        almacen.setAlmacen(resultSet.getString("almacen"));
        almacen.setNombreArea(resultSet.getString("nombre_area"));
        almacen.setPiso(resultSet.getString("nombre_piso"));

        return almacen;
    }
}
