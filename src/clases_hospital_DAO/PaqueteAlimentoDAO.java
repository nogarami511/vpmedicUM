/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class PaqueteAlimentoDAO {

    /*aqui me quede
     */
    private Connection connection;

    public PaqueteAlimentoDAO(Connection connection) {
        this.connection = connection;
    }

    public double CostoPorComida() {
        String query = "SELECT p.precio FROM paquetesalimentos p WHERE p.id = 1";
        ResultSet resultSet = null;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getDouble("precio");
            } else {
                return 0.0;
            }
        } catch (SQLException e) {
            // Manejar la excepción o imprimir un mensaje de error
            e.printStackTrace();
            return 0.0; // Otra acción apropiada en caso de excepción
        } finally {
            // Cerrar el ResultSet en el bloque finally para liberar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
