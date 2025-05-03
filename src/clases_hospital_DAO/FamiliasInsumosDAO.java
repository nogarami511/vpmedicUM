/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.FamiliaInsumos;
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
public class FamiliasInsumosDAO {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public FamiliasInsumosDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar una nueva familia de insumos
    public void insertarFamiliaInsumos(FamiliaInsumos familia) throws SQLException {
        
        String query = "INSERT INTO familias_insumos (nombre_familia_inusmo, id_usuario_creacion_familia_inusmo, id_usuario_modificacion_familia_inusmo) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, familia.getNombreFamiliaInsumo());
            statement.setInt(2, familia.getIdUsuarioCreacion());
            statement.setInt(3, familia.getIdUsuarioModificacion());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("La inserción falló, no se creó ninguna fila.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    familia.setIdFamiliaInsumo(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La inserción falló, no se pudo obtener el ID generado.");
                }
            }
        }
    }

    // Método para actualizar una familia de insumos
    public void actualizarFamiliaInsumos(FamiliaInsumos familia) throws SQLException {
        String query = "UPDATE familias_insumos SET nombre_familia_inusmo = ?, id_usuario_modificacion_familia_inusmo = ? WHERE id_familia_inusmo = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, familia.getNombreFamiliaInsumo());
            statement.setInt(2, familia.getIdUsuarioModificacion());
            statement.setInt(3, familia.getIdFamiliaInsumo());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("La actualización falló, no se modificó ninguna fila.");
            }
        }
    }

    // Método para obtener todas las familias de insumos
    public List<FamiliaInsumos> obtenerTodasFamiliasInsumos() throws SQLException {
        List<FamiliaInsumos> familias = new ArrayList<>();
        String query = "SELECT * FROM familias_insumos";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    FamiliaInsumos familia = mapearResultSetAFamiliaInsumos(resultSet);
                    familias.add(familia);
                }
            }
        }
        return familias;
    }

    public List<FamiliaInsumos> obtenerFamiliasConDatosNecesarios() throws SQLException {
        List<FamiliaInsumos> familias = new ArrayList<>();
        String query = "SELECT fi.id_familia_inusmo, fi.nombre_familia_inusmo, (SELECT c.costo_compra_unitaria FROM insumos i INNER JOIN costos c ON i.id = c.id_insumo WHERE fi.id_familia_inusmo = i.id_familia_inusmo  ORDER BY c.costo_compra_unitaria DESC LIMIT 1) AS costo FROM familias_insumos fi WHERE id_familia_inusmo > 1 ";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    FamiliaInsumos familia = mapearResultSetAFamiliaInsumosSoloNecearios(resultSet);
                    familias.add(familia);
                }
            }
        }
        return familias;
    }

    // Método para obtener una familia de insumos por ID
    public FamiliaInsumos obtenerFamiliaInsumosPorId(int idFamiliaInsumo) throws SQLException {
        String query = "SELECT * FROM familias_insumos WHERE id_familia_inusmo = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFamiliaInsumo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearResultSetAFamiliaInsumos(resultSet);
                }
            }
        }
        return null;
    }

    // Método para mapear un ResultSet a una instancia de FamiliaInsumos
    private FamiliaInsumos mapearResultSetAFamiliaInsumos(ResultSet resultSet) throws SQLException {
        FamiliaInsumos familia = new FamiliaInsumos();
        familia.setIdFamiliaInsumo(resultSet.getInt("id_familia_inusmo"));
        familia.setNombreFamiliaInsumo(resultSet.getString("nombre_familia_inusmo"));
        familia.setIdUsuarioCreacion(resultSet.getInt("id_usuario_creacion_familia_inusmo"));
        familia.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion_familia_inusmo"));
        familia.setFechaCreacion(resultSet.getDate("fecha_creacion_familia_inusmo"));
        familia.setFechaModificacion(resultSet.getDate("fecha_modificacion_familia_inusmo"));
        return familia;
    }
    
    private FamiliaInsumos mapearResultSetAFamiliaInsumosSoloNecearios(ResultSet resultSet) throws SQLException {
        FamiliaInsumos familia = new FamiliaInsumos();
        familia.setIdFamiliaInsumo(resultSet.getInt("id_familia_inusmo"));
        familia.setNombreFamiliaInsumo(resultSet.getString("nombre_familia_inusmo"));
        familia.setCosto(resultSet.getDouble("costo"));
        return familia;
    }
}
