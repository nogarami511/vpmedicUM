/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ServicioPaqueteMedico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class ServiciosPaquetesMedicosDAO {

    private Connection connection;

    public ServiciosPaquetesMedicosDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un nuevo servicio de paquete médico en la base de datos
    public void insertar(ServicioPaqueteMedico servicio) throws SQLException {
        String query = "INSERT INTO servicios_paquetes_medicos (nombre_servicios_paquetes_medicos, costo_servicios_paquetes_medicos, tiempo_servicios_paquetes_medicos, descripcion_servicios_paquetes_medicos, usuario_creacion_servicios_paquetes_medicos, usuario_modificacion_servicios_paquetes_medicos) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, servicio.getNombre());
            statement.setDouble(2, servicio.getCosto());
            statement.setString(3, servicio.getTiempo());
            statement.setString(4, servicio.getDescripcion());
            statement.setInt(5, servicio.getUsuarioCreacion());
            statement.setInt(6, servicio.getUsuarioModificacion());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                servicio.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("No se pudo obtener el ID generado.");
            }
        }
    }

    // Método para actualizar un servicio de paquete médico existente en la base de datos
    public void actualizar(ServicioPaqueteMedico servicio) throws SQLException {
        String query = "UPDATE servicios_paquetes_medicos SET nombre_servicios_paquetes_medicos = ?, costo_servicios_paquetes_medicos = ?, tiempo_servicios_paquetes_medicos = ?, descripcion_servicios_paquetes_medicos = ?, usuario_modificacion_servicios_paquetes_medicos = ? WHERE id_servicios_paquetes_medicos = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, servicio.getNombre());
            statement.setDouble(2, servicio.getCosto());
            statement.setString(3, servicio.getTiempo());
            statement.setString(4, servicio.getDescripcion());
            statement.setInt(5, servicio.getUsuarioModificacion());
            statement.setInt(6, servicio.getId());
            statement.executeUpdate();
        }
    }

    // Método para eliminar un servicio de paquete médico existente en la base de datos
    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM servicios_paquetes_medicos WHERE id_servicios_paquetes_medicos = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Método para obtener todos los servicios de paquete médico de la base de datos
    public ServicioPaqueteMedico obtenerPorId(int id_servicios_paquetes_medicos) throws SQLException {
        ServicioPaqueteMedico servicio = new ServicioPaqueteMedico();
        String query = "SELECT * FROM servicios_paquetes_medicos WHERE id_servicios_paquetes_medicos = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_servicios_paquetes_medicos);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                servicio = mapeoServicioPaqueteMedicoDesdeResultSet(resultSet);
            }
        }
        return servicio;
    }

    // Método para obtener todos los servicios de paquete médico de la base de datos
    public List<ServicioPaqueteMedico> obtenerTodos() throws SQLException {
        List<ServicioPaqueteMedico> servicios = new ArrayList<>();
        String query = "SELECT * FROM servicios_paquetes_medicos";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ServicioPaqueteMedico servicio = mapeoServicioPaqueteMedicoDesdeResultSet(resultSet);
                servicios.add(servicio);
            }
        }
        return servicios;
    }

    private ServicioPaqueteMedico mapeoServicioPaqueteMedicoDesdeResultSet(ResultSet resultSet) throws SQLException {
        ServicioPaqueteMedico servicio = new ServicioPaqueteMedico();
        servicio.setId(resultSet.getInt("id_servicios_paquetes_medicos"));
        servicio.setNombre(resultSet.getString("nombre_servicios_paquetes_medicos"));
        servicio.setCosto(resultSet.getDouble("costo_servicios_paquetes_medicos"));
        servicio.setTiempo(resultSet.getString("tiempo_servicios_paquetes_medicos"));
        servicio.setDescripcion(resultSet.getString("descripcion_servicios_paquetes_medicos"));
        return servicio;
    }
}
