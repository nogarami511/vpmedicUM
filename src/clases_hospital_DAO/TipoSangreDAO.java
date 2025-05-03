/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.TipoSangre;
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
public class TipoSangreDAO {
    private Connection connection;

    public TipoSangreDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TipoSangre> getAll() throws SQLException {
        List<TipoSangre> tiposSangre = new ArrayList<>();
        String query = "SELECT * FROM tipo_sangre";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TipoSangre tipoSangre = mapResultSetToTipoSangre(resultSet);
                tiposSangre.add(tipoSangre);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return tiposSangre;
    }

    public TipoSangre getById(int idTipoSangre) throws SQLException {
        String query = "SELECT * FROM tipo_sangre WHERE id_tipo_sangre = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, idTipoSangre);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToTipoSangre(resultSet);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return null;
    }

    private TipoSangre mapResultSetToTipoSangre(ResultSet resultSet) throws SQLException {
        TipoSangre tipoSangre = new TipoSangre();
        tipoSangre.setId_tipo_sangre(resultSet.getInt("id_tipo_sangre"));
        tipoSangre.setTipo_sangre(resultSet.getString("tipo_sangre"));
        return tipoSangre;
    }
}
