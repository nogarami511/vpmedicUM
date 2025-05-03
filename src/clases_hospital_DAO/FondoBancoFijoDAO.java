/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author alfar
 */
public class FondoBancoFijoDAO {
    private Connection connection;

    public FondoBancoFijoDAO(Connection connection) {
        this.connection = connection;
    }

    public void actualizarFondoEfectivoFijo(int id, double monto) throws SQLException {
        String query = "UPDATE fondoefectivofijo SET monto = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, monto);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

//    public FondoEfectivoFijo leerFondoEfectivoFijoPorId(int id) throws SQLException {
//        FondoEfectivoFijo fondoEfectivoFijo = null;
//        String query = "SELECT * FROM fondoefectivofijo WHERE id = ?";
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, id);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    fondoEfectivoFijo = crearFondoEfectivoFijoDesdeResultSet(resultSet);
//                }
//            }
//        }
//
//        return fondoEfectivoFijo;
//    }
//
//    private FondoEfectivoFijo crearFondoEfectivoFijoDesdeResultSet(ResultSet resultSet) throws SQLException {
//        int id = resultSet.getInt("id");
//        double monto = resultSet.getDouble("monto");
//
//        return new FondoEfectivoFijo(id, monto);
//    }
}
