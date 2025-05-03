/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Area;
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
public class AreaDAO {
    private Connection connection;

    public AreaDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Area area) throws SQLException {
        String query = "INSERT INTO areas (nombre_area, id_piso, fecha_creacion) VALUES (?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, area.getNombreArea());
            statement.setInt(2, area.getIdPiso());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                area.setIdArea(generatedKeys.getInt(1));
            }
        }
    }

    public void update(Area area) throws SQLException {
        String query = "UPDATE areas SET nombre_area = ?, id_piso = ?, fecha_creacion = NOW() WHERE id_area = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, area.getNombreArea());
            statement.setInt(2, area.getIdPiso());
            statement.setInt(3, area.getIdArea());

            statement.executeUpdate();
        }
    }

    public void delete(int idArea) throws SQLException {
        String query = "DELETE FROM areas WHERE id_area = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idArea);
            statement.executeUpdate();
        }
    }

    public Area getById(int idArea) throws SQLException {
        String query = "SELECT * FROM areas WHERE id_area = ?";
        Area area = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idArea);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                area = mapResultSetToArea(resultSet);
            }
        }

        return area;
    }

    public List<Area> getAll() throws SQLException {
        String query = "SELECT * FROM areas";
        List<Area> areas = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Area area = mapResultSetToArea(resultSet);
                areas.add(area);
            }
        }

        return areas;
    }
    
    public List<Area> getAllConPiso() throws SQLException {
        String query = "SELECT a.id_area, a.nombre_area, p.nombre_piso FROM areas a INNER JOIN pisos p ON a.id_piso = p.id_piso";
        List<Area> areas = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Area area = mapResultSetToAlmacenAreasyPiso(resultSet);
                areas.add(area);
            }
        }

        return areas;
    }

    private Area mapResultSetToArea(ResultSet resultSet) throws SQLException {
        Area area = new Area();
        area.setIdArea(resultSet.getInt("id_area"));
        area.setNombreArea(resultSet.getString("nombre_area"));
        area.setIdPiso(resultSet.getInt("id_piso"));
        area.setFechaCreacion(resultSet.getDate("fecha_creacion"));

        return area;
    }
    
    private Area mapResultSetToAlmacenAreasyPiso(ResultSet resultSet) throws SQLException {
        Area almacen = new Area();
        almacen.setIdArea(resultSet.getInt("id_area"));
        almacen.setNombreArea(resultSet.getString("nombre_area"));
        almacen.setPiso(resultSet.getString("nombre_piso"));

        return almacen;
    }
}
