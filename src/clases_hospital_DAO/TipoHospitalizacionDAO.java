/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.TipoHospitalizaciones;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class TipoHospitalizacionDAO {
    private final Connection connection;

    public TipoHospitalizacionDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<TipoHospitalizaciones> getAllTiposHabitacion() {
        List<TipoHospitalizaciones> tiposHabitacion = new ArrayList<>();

        String query = "SELECT * FROM tipo_hospitalizaciones";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                TipoHospitalizaciones tipoHabitacion = tipoHabitacionesFromResultSet(resultSet);
                tiposHabitacion.add(tipoHabitacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiposHabitacion;
    }
    
    private TipoHospitalizaciones tipoHabitacionesFromResultSet(ResultSet resultSet) throws SQLException{
        TipoHospitalizaciones tipo = new TipoHospitalizaciones();
        tipo.setId_tipo_hozpitalizacion(resultSet.getInt("id_tipo_hozpitalizacion"));
        tipo.setNombre_tipo_hozpitalizacion(resultSet.getString("nombre_tipo_hozpitalizacion"));
        return tipo;
    }
}
