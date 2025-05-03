/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ComisionPorPago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Gerardo
 */
public class ComisionPorPagoDAO {

    private Connection connection;

    public ComisionPorPagoDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<ComisionPorPago> obtenerPorElMesActual() throws SQLException {
        double totalComision = 0.0;
        ObservableList<ComisionPorPago> comisionesPorPago = FXCollections.observableArrayList();
        String query = "SELECT\n"
                + "    p.id,\n"
                + "    p.id_paciente,\n"
                + "    p.id_folio,\n"
                + "    p1.nombre_paciente,\n"
                + "    p.total_pago,\n"
                + "    p.id_tipo_pago,\n"
                + "    ROUND(\n"
                + "        IFNULL((CASE\n"
                + "                    WHEN p.id_tipo_pago = 3 THEN (((p.total_pago * 2.02) / 100) * 1.16)\n"
                + "                    WHEN p.id_tipo_pago = 4 THEN (((p.total_pago * 1.58) / 100) * 1.16)\n"
                + "                    WHEN p.id_tipo_pago = 8 THEN (((p.total_pago * 2.90) / 100) * 1.16)\n"
                + "                    WHEN p.id_tipo_pago = 9 THEN (((p.total_pago * 2.14) / 100) * 1.16)\n"
                + "                    WHEN p.id_tipo_pago = 10 THEN (((p.total_pago * 2.14) / 100) * 1.16)\n"
                + "                    ELSE 0\n"
                + "                END),0),2) AS comision\n"
                + "FROM\n"
                + "    pagos p\n"
                + "INNER JOIN pacientes p1 ON p1.id_paciente = p.id_paciente\n"
                + "WHERE\n"
                + "    p.id_tipo_pago IN (3,4,8,9,10) AND MONTH(p.fecha_pago)=MONTH(NOW()) AND YEAR(p.fecha_pago)= YEAR(NOW());";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ComisionPorPago comisionPorPago = new ComisionPorPago();
                    comisionPorPago.setId(resultSet.getInt("id"));
                    comisionPorPago.setIdPaciente(resultSet.getInt("id_paciente"));
                    comisionPorPago.setIdFolio(resultSet.getInt("id_folio"));
                    comisionPorPago.setNombrePaciente(resultSet.getString("nombre_paciente"));
                    comisionPorPago.setTotalPago(resultSet.getDouble("total_pago"));
                    comisionPorPago.setTipoPago(resultSet.getInt("id_tipo_pago"));
                    comisionPorPago.setComision(resultSet.getDouble("comision"));
                    totalComision += resultSet.getDouble("comision");
                    comisionesPorPago.add(comisionPorPago);
                }
            }
        }
    
        return comisionesPorPago;
    }

    private ComisionPorPago crearComisionPorPagoDesdeResultSet(ResultSet resultSet) throws SQLException {
        ComisionPorPago comisionPorPago = new ComisionPorPago();
        comisionPorPago.setId(resultSet.getInt("id"));
        comisionPorPago.setIdPaciente(resultSet.getInt("id_paciente"));
        comisionPorPago.setIdFolio(resultSet.getInt("id_folio"));
        comisionPorPago.setNombrePaciente(resultSet.getString("nombre_paciente"));
        comisionPorPago.setTotalPago(resultSet.getDouble("total_pago"));
        comisionPorPago.setTipoPago(resultSet.getInt("id_tipo_pago"));
        comisionPorPago.setComision(resultSet.getDouble("comision"));
        return comisionPorPago;
    }
}
