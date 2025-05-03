/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.EgresosEfectivo;
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
public class EgresosEfectivoDAO {

    private Connection connection;

    public EgresosEfectivoDAO(Connection connection) {
        this.connection = connection;
    }

    public void crearEgresoEfectivo(EgresosEfectivo egresoEfectivo) throws SQLException {
        String query = "INSERT INTO egresosefectivo (concepto_egresoefectivo, folio_egresoefectivo, forma_pago_egresoefectivo, importe_egresoefectivo, usuario_creacion_egresoefectivo, usuario_modificiacion_egresoefectivo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, egresoEfectivo.getConcepto());
            statement.setString(2, egresoEfectivo.getFolio());
            statement.setInt(3, egresoEfectivo.getFormaPago());
            statement.setDouble(4, egresoEfectivo.getImporte());
            statement.setInt(5, egresoEfectivo.getUsuarioCreacion());
            statement.setInt(6, egresoEfectivo.getUsuarioModificacion());
            statement.executeUpdate();
        }
    }

    public List<EgresosEfectivo> leerTodosEgresosEfectivo() throws SQLException {
        List<EgresosEfectivo> egresosEfectivoList = new ArrayList<>();
        String query = "SELECT * FROM egresosefectivo";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EgresosEfectivo egresoEfectivo = mapEgresosEfectivoFromResultSet(resultSet);
                egresosEfectivoList.add(egresoEfectivo);
            }
        }
        return egresosEfectivoList;
    }

    public EgresosEfectivo leerEgresoEfectivoPorId(int id) throws SQLException {
        EgresosEfectivo egresoEfectivo = null;
        String query = "SELECT * FROM egresosefectivo WHERE id_egresoefectivo = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    egresoEfectivo = mapEgresosEfectivoFromResultSet(resultSet);
                }
            }
        }
        return egresoEfectivo;
    }

    public void actualizarEgresoEfectivo(EgresosEfectivo egresoEfectivo) throws SQLException {
        String query = "UPDATE egresosefectivo SET concepto_egresoefectivo = ?, folio_egresoefectivo = ?, "
                + "fecha_egresoefectivo = ?, forma_pago_egresoefectivo = ?, "
                + "importe_egresoefectivo = ?, usuario_creacion_egresoefectivo = ?, "
                + "usuario_modificiacion_egresoefectivo = ?, fecha_creacion_egresoefectivo = ?, "
                + "fecha_modificacion_egresoefectivo = ? WHERE id_egresoefectivo = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, egresoEfectivo.getConcepto());
            statement.setString(2, egresoEfectivo.getFolio());
            statement.setDate(3, egresoEfectivo.getFecha());
            statement.setInt(4, egresoEfectivo.getFormaPago());
            statement.setDouble(5, egresoEfectivo.getImporte());
            statement.setInt(6, egresoEfectivo.getUsuarioCreacion());
            statement.setInt(7, egresoEfectivo.getUsuarioModificacion());
            statement.setDate(8, egresoEfectivo.getFechaCreacion());
            statement.setDate(9, egresoEfectivo.getFechaModificacion());
            statement.setInt(10, egresoEfectivo.getId());
            statement.executeUpdate();
        }
    }

    private EgresosEfectivo mapEgresosEfectivoFromResultSet(ResultSet resultSet) throws SQLException {
        EgresosEfectivo egresoEfectivo = new EgresosEfectivo();
        egresoEfectivo.setId(resultSet.getInt("id_egresoefectivo"));
        egresoEfectivo.setConcepto(resultSet.getString("concepto_egresoefectivo"));
        egresoEfectivo.setFolio(resultSet.getString("folio_egresoefectivo"));
        egresoEfectivo.setFecha(resultSet.getDate("fecha_egresoefectivo"));
        egresoEfectivo.setFormaPago(resultSet.getInt("forma_pago_egresoefectivo"));
        egresoEfectivo.setImporte(resultSet.getDouble("importe_egresoefectivo"));
        egresoEfectivo.setUsuarioCreacion(resultSet.getInt("usuario_creacion_egresoefectivo"));
        egresoEfectivo.setUsuarioModificacion(resultSet.getInt("usuario_modificiacion_egresoefectivo"));
        egresoEfectivo.setFechaCreacion(resultSet.getDate("fecha_creacion_egresoefectivo"));
        egresoEfectivo.setFechaModificacion(resultSet.getDate("fecha_modificacion_egresoefectivo"));
        return egresoEfectivo;
    }
}
