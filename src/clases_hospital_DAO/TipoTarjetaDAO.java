/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;
import clases_hospital.TipoTarjeta;
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
public class TipoTarjetaDAO {
    private Connection connection;

    public TipoTarjetaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<TipoTarjeta> selectTipoTarjetas() {
        List<TipoTarjeta> tipoTarjetas = new ArrayList<>();
        String query = "SELECT id_tipo_tarjeta, nombre_tipo_tarjeta FROM tipo_tarjetas";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_tipo_tarjeta");
                String nombre = resultSet.getString("nombre_tipo_tarjeta");
                TipoTarjeta tipoTarjeta = new TipoTarjeta(id, nombre);
                tipoTarjetas.add(tipoTarjeta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoTarjetas;
    }
}
