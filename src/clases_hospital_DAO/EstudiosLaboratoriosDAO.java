/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.EstudioLaboratorio;
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
public class EstudiosLaboratoriosDAO {

    private Connection connection;

    public EstudiosLaboratoriosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarEstudioLaboratorio(EstudioLaboratorio estudioLaboratorio) throws SQLException {
        String query = "INSERT INTO estudios_laboratorios (id_insumo, id_laboratorio, usuario_modificacion, fecha_modificacion, estatus, costo_sin_iva, nombre_estudio) VALUES (?, ?, ?, NOW(), ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, estudioLaboratorio.getIdInsumo());
            statement.setInt(2, estudioLaboratorio.getIdLaboratorio());
            statement.setInt(3, estudioLaboratorio.getUsuarioModificacion());
            statement.setInt(4, estudioLaboratorio.getEstatus());
            statement.setDouble(5, estudioLaboratorio.getCostoSinIVA());
            statement.setString(6, estudioLaboratorio.getNombreEstudio());
            statement.executeUpdate();
        }
    }

    public void actualizarEstudioLaboratorio(EstudioLaboratorio estudioLaboratorio) throws SQLException {
        String query = "UPDATE estudios_laboratorios SET id_insumo = ?, id_laboratorio = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), estatus = ?, costo_sin_iva = ?, nombre_estudio = ? WHERE id_estudios_laboratorios = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, estudioLaboratorio.getIdInsumo());
            statement.setInt(2, estudioLaboratorio.getIdLaboratorio());
            statement.setInt(3, estudioLaboratorio.getUsuarioModificacion());
            statement.setInt(4, estudioLaboratorio.getEstatus());
            statement.setDouble(5, estudioLaboratorio.getCostoSinIVA());
            statement.setString(6, estudioLaboratorio.getNombreEstudio());
            statement.setInt(7, estudioLaboratorio.getIdEstudiosLaboratorios());
            statement.executeUpdate();
        }
    }

    public List<EstudioLaboratorio> obtenerTodosLosEstudiosLaboratorios() throws SQLException {
        List<EstudioLaboratorio> estudiosLaboratorios = new ArrayList<>();
        String query = "SELECT * FROM estudios_laboratorios";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EstudioLaboratorio estudioLaboratorio = crearEstudioLaboratorioDesdeResultSet(resultSet);
                estudiosLaboratorios.add(estudioLaboratorio);
            }
        }

        return estudiosLaboratorios;
    }

    public EstudioLaboratorio obtenerEstudioLaboratorioPorId(int idEstudiosLaboratorios) throws SQLException {
        String query = "SELECT * FROM estudios_laboratorios WHERE id_estudios_laboratorios = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEstudiosLaboratorios);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearEstudioLaboratorioDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<EstudioLaboratorio> obtenerTodosLosEstudiosLaboratoriosPorIdLaboratorio(int id_laboratorio) throws SQLException {
        List<EstudioLaboratorio> estudiosLaboratorios = new ArrayList<>();
        String query = "SELECT id_estudios_laboratorios, id_insumo, id_laboratorio, estatus, costo_sin_iva, nombre_estudio FROM estudios_laboratorios WHERE id_laboratorio = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_laboratorio);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    EstudioLaboratorio estudioLaboratorio = crearEstudioLaboratorioDesdeResultSetSinFechamodificacionYUsuarioMod(resultSet);
                    estudiosLaboratorios.add(estudioLaboratorio);
                }
            }
        }

        return estudiosLaboratorios;
    }
    
    public List<EstudioLaboratorio> obtenerTodosLosEstudiosLaboratoriosIdIdInsumoNombre() throws SQLException {
        List<EstudioLaboratorio> estudiosLaboratorios = new ArrayList<>();
        String query = "SELECT * FROM estudios_laboratorios";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EstudioLaboratorio estudioLaboratorio = crearEstudioLaboratorioDesdeResultSet(resultSet);
                estudiosLaboratorios.add(estudioLaboratorio);
            }
        }

        return estudiosLaboratorios;
    }

    private EstudioLaboratorio crearEstudioLaboratorioDesdeResultSet(ResultSet resultSet) throws SQLException {
        EstudioLaboratorio estudiolaboratorio = new EstudioLaboratorio();
        estudiolaboratorio.setIdEstudiosLaboratorios(resultSet.getInt("id_estudios_laboratorios"));
        estudiolaboratorio.setIdInsumo(resultSet.getInt("id_insumo"));
        estudiolaboratorio.setIdLaboratorio(resultSet.getInt("id_laboratorio"));
        estudiolaboratorio.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        estudiolaboratorio.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        estudiolaboratorio.setEstatus(resultSet.getInt("estatus"));
        estudiolaboratorio.setCostoSinIVA(resultSet.getDouble("costo_sin_iva"));
        estudiolaboratorio.setNombreEstudio(resultSet.getString("nombre_estudio"));

        return estudiolaboratorio;
    }

    private EstudioLaboratorio crearEstudioLaboratorioDesdeResultSetSinFechamodificacionYUsuarioMod(ResultSet resultSet) throws SQLException {
        EstudioLaboratorio estudiolaboratorio = new EstudioLaboratorio();
        estudiolaboratorio.setIdEstudiosLaboratorios(resultSet.getInt("id_estudios_laboratorios"));
        estudiolaboratorio.setIdInsumo(resultSet.getInt("id_insumo"));
        estudiolaboratorio.setIdLaboratorio(resultSet.getInt("id_laboratorio"));
        estudiolaboratorio.setEstatus(resultSet.getInt("estatus"));
        estudiolaboratorio.setCostoSinIVA(resultSet.getDouble("costo_sin_iva"));
        estudiolaboratorio.setNombreEstudio(resultSet.getString("nombre_estudio"));

        return estudiolaboratorio;
    }
}
