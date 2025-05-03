/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.AsignacionHabitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class AsignacionHabitacionDAO {

    private Connection connection;

    // Constructor que recibe una conexión a la base de datos
    public AsignacionHabitacionDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar una nueva asignación de habitación
    public void insert(AsignacionHabitacion asignacion) throws SQLException {
        String query = "INSERT INTO asignacion_habitacion "
                + "(id_habitacion, id_tipo_habitacion, id_paciente, id_estatus, id_usuario_modificacion, fecha_modificacion) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getId_habitacion());
            statement.setInt(2, asignacion.getIdTipoHabitacion());
            statement.setInt(3, asignacion.getIdPaciente());
            statement.setInt(4, asignacion.getIdEstatus());
            statement.setInt(5, asignacion.getIdUsuarioModificacion());
            statement.setTimestamp(6, asignacion.getFechaModificacion());
            statement.executeUpdate();
        }
    }

    // Método para actualizar una asignación de habitación existente
    public void update(AsignacionHabitacion asignacion) throws SQLException {
        String query = "UPDATE asignacion_habitacion SET "
                + "id_habitacion = ?, id_tipo_habitacion = ?, id_paciente = ?, "
                + "id_estatus = ?, id_usuario_modificacion = ?, fecha_modificacion = ? "
                + "WHERE id_asignacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getId_habitacion());
            statement.setInt(2, asignacion.getIdTipoHabitacion());
            statement.setInt(3, asignacion.getIdPaciente());
            statement.setInt(4, asignacion.getIdEstatus());
            statement.setInt(5, asignacion.getIdUsuarioModificacion());
            statement.setTimestamp(6, asignacion.getFechaModificacion());
            statement.setInt(7, asignacion.getId());
            statement.executeUpdate();
        }
    }

    // Método para actualizar una asignación de habitación existente
    public void updateOnlyImportantData(AsignacionHabitacion asignacion) throws SQLException {
        String query = "UPDATE asignacion_habitacion SET id_paciente = ?, id_usuario_modificacion = ?, fecha_modificacion = NOW() WHERE id_asignacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getIdPaciente());
            statement.setInt(2, asignacion.getIdUsuarioModificacion());
            statement.setInt(3, asignacion.getId());
            statement.executeUpdate();
        }
    }

    public void updateOnlyImportantData2(AsignacionHabitacion asignacion) throws SQLException {
        String query = "UPDATE asignacion_habitacion SET id_paciente = ?, id_usuario_modificacion = ?, id_estatus = 2, fecha_modificacion = NOW() WHERE id_habitacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getIdPaciente());
            statement.setInt(2, asignacion.getIdUsuarioModificacion());
            statement.setInt(3, asignacion.getId());//IDHABITACION
            statement.executeUpdate();
        }
    }

    public void actualizarLiberacionDeHabitacion(AsignacionHabitacion asignacion) throws SQLException {
        String query = "UPDATE asignacion_habitacion SET id_paciente = ?, id_usuario_modificacion = ?, id_estatus = 1, fecha_modificacion = NOW() WHERE id_habitacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, 0);
            statement.setInt(2, VPMedicaPlaza.userSystem);
            statement.setInt(3, asignacion.getId());//IDHABITACION
            statement.executeUpdate();
        }
    }

    // Método para eliminar una asignación de habitación
    public void delete(int asignacionId) throws SQLException {
        String query = "DELETE FROM asignacion_habitacion WHERE id_asignacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacionId);
            statement.executeUpdate();
        }
    }

    // Método para obtener una asignación de habitación por su ID
    public AsignacionHabitacion getById(int habitacionid) throws SQLException {
        String query = "SELECT * FROM asignacion_habitacion WHERE id_habitacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, habitacionid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAsignacionFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    // Método para obtener todas las asignaciones de habitación
    public List<AsignacionHabitacion> getAll() throws SQLException {
        List<AsignacionHabitacion> asignaciones = new ArrayList<>();
        String query = "SELECT * FROM asignacion_habitacion";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                asignaciones.add(extractAsignacionFromResultSet(resultSet));
            }
        }
        return asignaciones;
    }

    // Método para extraer un objeto AsignacionHabitacion de un conjunto de resultados de ResultSet
    private AsignacionHabitacion extractAsignacionFromResultSet(ResultSet resultSet) throws SQLException {
        AsignacionHabitacion asignacion = new AsignacionHabitacion();
        asignacion.setId(resultSet.getInt("id_asignacion"));
        asignacion.setId_habitacion(resultSet.getInt("id_habitacion"));
        asignacion.setIdTipoHabitacion(resultSet.getInt("id_tipo_habitacion"));
        asignacion.setIdPaciente(resultSet.getInt("id_paciente"));
        asignacion.setIdEstatus(resultSet.getInt("id_estatus"));
        asignacion.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        asignacion.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        return asignacion;
    }
}
