/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.TipoInsumo;
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
public class TipoInsumoDAO {

    private Connection connection;

    public TipoInsumoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TipoInsumo> obtenerTodos() throws SQLException {
        List<TipoInsumo> inventarios = new ArrayList<>();
        String query = "SELECT * FROM tipoinsumos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                TipoInsumo inventario = obtenerTodosDesdeResultSet(resultSet);
                inventarios.add(inventario);
            }
        }

        return inventarios;
    }

    private TipoInsumo obtenerTodosDesdeResultSet(ResultSet resultSet) throws SQLException {
        TipoInsumo tipoinsumo = new TipoInsumo();
        tipoinsumo.setId(resultSet.getInt("id"));
        tipoinsumo.setNombre(resultSet.getString("nombre"));

        return tipoinsumo;
    }
}
