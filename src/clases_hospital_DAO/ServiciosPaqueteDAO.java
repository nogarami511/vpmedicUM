/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ServiciosPaquete;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author PC
 */
public class ServiciosPaqueteDAO {

    private Connection connection;

    public ServiciosPaqueteDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(ServiciosPaquete servicio) throws SQLException {
       // String query = "INSERT INTO servicios_paquete (id_paquete, id_servicios_paquetes_medicos, hora_extra_qx, cantidad_hora_extra_qx, dieta_paciente, cantidad_dieta, usuario_creacion, fecha_creacion, ususario_modificacion, fecha_modifciacion) VALUES (?, ?, ?, ?, ?, ?, ?, NOW(),  NOW())";
        try (CallableStatement statement = connection.prepareCall("{call INSERTAR_SERICIOS_MEDICOS (?,?,?,?,?,?,?)}")) {
            statement.setInt(1, servicio.getIdPaquete());
            statement.setInt(2, servicio.getIdServiciosPaquetesMedicos());
            statement.setBoolean(3, servicio.isHoraExtraQx());
            statement.setInt(4, servicio.getCantidadHoraExtraQx());
            statement.setBoolean(5, servicio.isDietaPaciente());
            statement.setInt(6, servicio.getCantidadDieta());
            statement.setInt(7, VPMedicaPlaza.userSystem);
            statement.execute();

        }
    }

    public void actualizar(ServiciosPaquete servicio) throws SQLException {
        String query = "UPDATE servicios_paquete SET id_paquete = ?, id_servicios_paquetes_medicos = ?, hora_extra_qx = ?, cantidad_hora_extra_qx = ?, dieta_paciente = ?, cantidad_dieta = ?, usuario_modificacion = ?, fecha_modifciacion = NOW() WHERE id_servicios_paquete = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, servicio.getIdPaquete());
            statement.setInt(2, servicio.getIdServiciosPaquetesMedicos());
            statement.setBoolean(3, servicio.isHoraExtraQx());
            statement.setInt(4, servicio.getCantidadHoraExtraQx());
            statement.setBoolean(5, servicio.isDietaPaciente());
            statement.setInt(6, servicio.getCantidadDieta());
            statement.setInt(7, servicio.getUsuarioModificacion());
            statement.setInt(8, servicio.getId());
            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM servicios_paquete WHERE id_servicios_paquete = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public ServiciosPaquete obtenerPorId(int id) throws SQLException {
        ServiciosPaquete servicio = new ServiciosPaquete();
        String query = "SELECT * FROM servicios_paquete WHERE id_servicios_paquete = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                servicio = mapearServicioPaqueteDesdeResultSet(resultSet);
            }
        }
        return servicio;
    }

    public List<ServiciosPaquete> obtenerTodos() throws SQLException {
        List<ServiciosPaquete> servicios = new ArrayList<>();
        String query = "SELECT * FROM servicios_paquete";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ServiciosPaquete servicio = mapearServicioPaqueteDesdeResultSet(resultSet);
                servicios.add(servicio);
            }
        }
        return servicios;
    }
    
    public List<ServiciosPaquete> obtenerPorIdPaquete(int idPaquete) throws SQLException {
        List<ServiciosPaquete> servicios = new ArrayList<>();
        String query = "SELECT sp.*, spm.costo_servicios_paquetes_medicos, spm.nombre_servicios_paquetes_medicos FROM  servicios_paquete sp INNER JOIN servicios_paquetes_medicos spm ON sp.id_servicios_paquetes_medicos = spm.id_servicios_paquetes_medicos WHERE sp.id_paquete = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPaquete);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ServiciosPaquete servicio = mapearServicioPaqueteDesdeResultSet(resultSet);
                servicios.add(servicio);
            }
        }
        return servicios;
    }

    private ServiciosPaquete mapearServicioPaqueteDesdeResultSet(ResultSet resultSet) throws SQLException {
        ServiciosPaquete servicio = new ServiciosPaquete();
        servicio.setId(resultSet.getInt("id_servicios_paquete"));
        servicio.setIdPaquete(resultSet.getInt("id_paquete"));
        servicio.setIdServiciosPaquetesMedicos(resultSet.getInt("id_servicios_paquetes_medicos"));
        servicio.setHoraExtraQx(resultSet.getBoolean("hora_extra_qx"));
        servicio.setCantidadHoraExtraQx(resultSet.getInt("cantidad_hora_extra_qx"));
        servicio.setDietaPaciente(resultSet.getBoolean("dieta_paciente"));
        servicio.setCantidadDieta(resultSet.getInt("cantidad_dieta"));
        servicio.setCosto(resultSet.getDouble("costo_servicios_paquetes_medicos"));
        servicio.setNombre(resultSet.getString("nombre_servicios_paquetes_medicos"));
        return servicio;
    }

}
//ServiciosPaquete