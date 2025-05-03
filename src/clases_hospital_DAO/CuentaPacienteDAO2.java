/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.CuentaPaciente2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gerardo
 */
public class CuentaPacienteDAO2 {

    private Connection connection;

    public CuentaPacienteDAO2(Connection connection) {
        this.connection = connection;
    }

    public List<CuentaPaciente2> obtenerPacientesHospitalizados() throws SQLException {
        List<CuentaPaciente2> cuentaPacientes = new ArrayList<>();
        String sql = "SELECT p.id_paciente, f.id AS id_folio, p.nombre_paciente, h.tipo, f.id_paquete, paquetes.nombre AS nombrepaquete FROM folios f INNER JOIN pacientes p ON f.id = p.id_folio INNER JOIN paquetesmedicos paquetes ON f.id_paquete = paquetes.id INNER JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente INNER JOIN tipoHabitacion h ON h.id_tipo = ah.id_tipo_habitacion WHERE f.id_estatus_folio = 1;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cuentaPacientes.add(new CuentaPaciente2(
                            resultSet.getInt("id_paciente"),
                            resultSet.getInt("id_folio"),
                            resultSet.getString("nombre_paciente"),
                            resultSet.getString(4),
                            resultSet.getInt("id_paquete"),
                            resultSet.getString("nombrepaquete")
                    ));
                }
            }
        }
        return cuentaPacientes;
    }
    
    public List<CuentaPaciente2> obtenerPacientesHospitalizadosConHemodinamia() throws SQLException {
        List<CuentaPaciente2> cuentaPacientes = new ArrayList<>();
        String sql = "SELECT p.id_paciente, f.id AS id_folio, p.nombre_paciente, h.tipo, f.id_paquete, paquetes.nombre AS nombrepaquete, CASE WHEN (SELECT f1.folio FROM folios f1 WHERE f1.folio = CONCAT('H-',f.folio)) = CONCAT('H-',f.folio) THEN 1 ELSE 0 END AS estatusHemodinamia FROM folios f INNER JOIN pacientes p ON f.id = p.id_folio INNER JOIN paquetesmedicos paquetes ON f.id_paquete = paquetes.id INNER JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente INNER JOIN tipoHabitacion h ON h.id_tipo = ah.id_tipo_habitacion WHERE f.id_estatus_folio = 1;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cuentaPacientes.add(new CuentaPaciente2(
                            resultSet.getInt("id_paciente"),
                            resultSet.getInt("id_folio"),
                            resultSet.getString("nombre_paciente"),
                            resultSet.getString(4),
                            resultSet.getInt("id_paquete"),
                            resultSet.getString("nombrepaquete"),
                            resultSet.getBoolean("estatusHemodinamia")
                    ));
                }
            }
        }
        return cuentaPacientes;
    }
    
    public CuentaPaciente2 obtenerPacientesHospitalizadoporFolio(int if_folio) throws SQLException {
        CuentaPaciente2 cuentaPacientes = new CuentaPaciente2();
        String sql = "SELECT p.id_paciente, f.id AS id_folio, p.nombre_paciente, h.tipo, f.id_paquete, paquetes.nombre AS nombrepaquete FROM folios f INNER JOIN pacientes p ON f.id = p.id_folio INNER JOIN paquetesmedicos paquetes ON f.id_paquete = paquetes.id INNER JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente INNER JOIN tipoHabitacion h ON h.id_tipo = ah.id_tipo_habitacion WHERE f.id_estatus_folio = 1 AND f.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, if_folio);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cuentaPacientes.setIdPaciente(resultSet.getInt("id_paciente"));
                    cuentaPacientes.setIdFolio(resultSet.getInt("id_folio"));
                    cuentaPacientes.setNombrePaciente(resultSet.getString("nombre_paciente"));
                    cuentaPacientes.setTipohabitacion(resultSet.getString(4));
                    cuentaPacientes.setIdPaquete(resultSet.getInt("id_paquete"));
                    cuentaPacientes.setNombrepaquete(resultSet.getString("nombrepaquete"));
                }
            }
        }
        return cuentaPacientes;
    }
    
    
    
    public List<CuentaPaciente2> obtenerPacientes() throws SQLException {
        List<CuentaPaciente2> cuentaPacientes = new ArrayList<>();
        String sql = "SELECT p.id_paciente, f.id AS id_folio, p.nombre_paciente, f.id_habitacion, f.id_paquete, paquetes.nombre AS nombrepaquete FROM folios f INNER JOIN pacientes p ON f.id = p.id_folio INNER JOIN paquetesmedicos paquetes ON f.id_paquete = paquetes.id -- INNER JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente INNER JOIN tipoHabitacion h ON h.id_tipo = ah.id_tipo_habitacion;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cuentaPacientes.add(new CuentaPaciente2(
                            resultSet.getInt("id_paciente"),
                            resultSet.getInt("id_folio"),
                            resultSet.getString("nombre_paciente"),
                            resultSet.getString(4),
                            resultSet.getInt("id_paquete"),
                            resultSet.getString("nombrepaquete")
                    ));
                }
            }
        }
        return cuentaPacientes;
    }

}
