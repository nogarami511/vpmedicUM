/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.TipoInsumoMedico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class TipoInsumoMedicoDAO {
    private Connection connection;

    public TipoInsumoMedicoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(TipoInsumoMedico tipoInsumoMedico) throws SQLException {
        String query = "INSERT INTO tipo_insumo_medicos (nombre_tipo_insumo_medico) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tipoInsumoMedico.getNombreTipoInsumoMedico());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                tipoInsumoMedico.setIdTipoInsumoMedico(generatedKeys.getInt(1));
            }
        }
    }

    public void update(TipoInsumoMedico tipoInsumoMedico) throws SQLException {
        String query = "UPDATE tipo_insumo_medicos SET nombre_tipo_insumo_medico = ? WHERE id_tipo_insumo_medico = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tipoInsumoMedico.getNombreTipoInsumoMedico());
            statement.setInt(2, tipoInsumoMedico.getIdTipoInsumoMedico());

            statement.executeUpdate();
        }
    }

    public void delete(int idTipoInsumoMedico) throws SQLException {
        String query = "DELETE FROM tipo_insumo_medicos WHERE id_tipo_insumo_medico = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipoInsumoMedico);
            statement.executeUpdate();
        }
    }

    public TipoInsumoMedico getById(int idTipoInsumoMedico) throws SQLException {
        String query = "SELECT * FROM tipo_insumo_medicos WHERE id_tipo_insumo_medico = ?";
        TipoInsumoMedico tipoInsumoMedico = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipoInsumoMedico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tipoInsumoMedico = mapResultSetToTipoInsumoMedico(resultSet);
            }
        }

        return tipoInsumoMedico;
    }

    public List<TipoInsumoMedico> getAll() throws SQLException {
        String query = "SELECT * FROM tipo_insumo_medicos";
        List<TipoInsumoMedico> tipoInsumoMedicos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TipoInsumoMedico tipoInsumoMedico = mapResultSetToTipoInsumoMedico(resultSet);
                tipoInsumoMedicos.add(tipoInsumoMedico);
            }
        }

        return tipoInsumoMedicos;
    }

    private TipoInsumoMedico mapResultSetToTipoInsumoMedico(ResultSet resultSet) throws SQLException {
        TipoInsumoMedico tipoInsumoMedico = new TipoInsumoMedico();
        tipoInsumoMedico.setIdTipoInsumoMedico(resultSet.getInt("id_tipo_insumo_medico"));
        tipoInsumoMedico.setNombreTipoInsumoMedico(resultSet.getString("nombre_tipo_insumo_medico"));

        return tipoInsumoMedico;
    }
}
