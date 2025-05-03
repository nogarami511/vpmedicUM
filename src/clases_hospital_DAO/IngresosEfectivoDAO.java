/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.IngresoEfectivo;
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
public class IngresosEfectivoDAO {

    private Connection connection;

    public IngresosEfectivoDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertIngresoEfectivo(IngresoEfectivo ingreso) {
        String sql = "INSERT INTO ingresosefectivo (concepto_ingresoefectivo, folio_ingresoefectivo, forma_pago_ingresoefectivo, importe_ingresoefectivo, usuario_creacion_ingresoefectivo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ingreso.getConcepto());
            statement.setString(2, ingreso.getFolio());
            statement.setInt(3, ingreso.getFormaPago());
            statement.setDouble(4, ingreso.getImporte());
            statement.setInt(5, ingreso.getUsuarioCreacion());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<IngresoEfectivo> getAllIngresosEfectivo() {
        List<IngresoEfectivo> ingresos = new ArrayList<>();
        String sql = "SELECT * FROM ingresosefectivo";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                IngresoEfectivo ingreso = mapIngresoEfectivoByResultSer(resultSet);
                ingresos.add(ingreso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingresos;
    }

    private IngresoEfectivo mapIngresoEfectivoByResultSer(ResultSet resultSet) throws SQLException {
        IngresoEfectivo ingreso = new IngresoEfectivo();
        ingreso.setId(resultSet.getInt("id_ingresoefectivo"));
        ingreso.setConcepto(resultSet.getString("concepto_ingresoefectivo"));
        ingreso.setFolio(resultSet.getString("folio_ingresoefectivo"));
        ingreso.setFecha(resultSet.getDate("fecha_ingresoefectivo"));
        ingreso.setFormaPago(resultSet.getInt("forma_pago_ingresoefectivo"));
        ingreso.setImporte(resultSet.getDouble("importe_ingresoefectivo"));
        ingreso.setUsuarioCreacion(resultSet.getInt("usuario_creacion_ingresoefectivo"));
        return ingreso;
    }
}
