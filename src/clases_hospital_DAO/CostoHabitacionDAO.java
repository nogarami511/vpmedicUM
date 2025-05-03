/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.CostoHabitacion;
import clases_hospital.Habitacion;
import clases_hospital.TipoHabitacion;
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
public class CostoHabitacionDAO {

    private Connection connection;

    public CostoHabitacionDAO(Connection connection) {
        this.connection = connection;
    }

    public List<CostoHabitacion> traerTodo() throws SQLException {
        List<CostoHabitacion> costos = new ArrayList<>();
        String query = "SELECT * FROM costoHabitacion";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CostoHabitacion costoHabitacion = mapeadoResulSet(resultSet);
                costos.add(costoHabitacion);
            }
        }

        return costos;
    }

    public List<TipoHabitacion> TiposHabitacion() throws SQLException {
        List<TipoHabitacion> tiposHabitacion = new ArrayList<>();
        String query = "SELECT * FROM tipohabitacion";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TipoHabitacion tipoHabitacion = mapeadiResulsethabitacion(resultSet);
                
                tiposHabitacion.add(tipoHabitacion);
            }
        }

        return tiposHabitacion;
    }

    public CostoHabitacion traerTodoPorID(int idTipoHabitacion) throws SQLException {
        CostoHabitacion costoHabitacion = new CostoHabitacion();
        String query = "SELECT * FROM costoHabitacion WHERE id_habitacion = '" + idTipoHabitacion + "' ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                costoHabitacion = mapeadoResulSet(resultSet);
            }
        }

        return costoHabitacion;
    }

    private CostoHabitacion mapeadoResulSet(ResultSet resultSet) throws SQLException {
        CostoHabitacion costoHabitacion = new CostoHabitacion();
        costoHabitacion.setId(resultSet.getInt("id_precio"));
        costoHabitacion.setIdHabitacion(resultSet.getInt("id_habitacion"));
        costoHabitacion.setNombre(resultSet.getString("precio_nombre"));
        costoHabitacion.setPrecio(resultSet.getDouble("precio"));

        return costoHabitacion;
    }
        private TipoHabitacion mapeadiResulsethabitacion(ResultSet resultSet) throws SQLException {
        TipoHabitacion costoHabitacion = new TipoHabitacion();
        costoHabitacion.setIdTipo(resultSet.getInt("id_tipo"));
        costoHabitacion.setTipo(resultSet.getString("tipo"));
        

        return costoHabitacion;
    }

}
