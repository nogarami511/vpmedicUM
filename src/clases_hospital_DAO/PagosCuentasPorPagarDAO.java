/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.PagoCuentaPorPagar;
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
public class PagosCuentasPorPagarDAO {

    private Connection connection;

    public PagosCuentasPorPagarDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarPago(PagoCuentaPorPagar pago) throws SQLException {
        String query = "INSERT INTO pagos_cuentas_por_pagar (id_compras_internasp, num_pago, fecha_pago, importe_pago, usuario_modificiacion, fecha_modificiacion, id_forma_pago) VALUES (?, ?, ?, ?, ?, NOW(), ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pago.getIdComprasInternasP());
            statement.setInt(2, pago.getNumPago());
            statement.setDate(3, pago.getFechaPago());
            statement.setDouble(4, pago.getImportePago());
            statement.setInt(5, pago.getUsuarioModificacion());
            statement.setInt(6, pago.getId_forma_pago());
            statement.executeUpdate();
        }
    }

    public void actualizarPago(PagoCuentaPorPagar pago) throws SQLException {
        String query = "UPDATE pagos_cuentas_por_pagar SET id_compras_internasp = ?, num_pago = ?, fecha_pago = ?, importe_pago = ?, usuario_modificiacion = ?, fecha_modificiacion = NOW(), id_forma_pago = ? WHERE id_pagos_cuentas_por_pagar = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pago.getIdComprasInternasP());
            statement.setInt(2, pago.getNumPago());
            statement.setDate(3, pago.getFechaPago());
            statement.setDouble(4, pago.getImportePago());
            statement.setInt(5, pago.getUsuarioModificacion());
            statement.setInt(6, pago.getId_forma_pago());
            statement.setInt(7, pago.getIdPagosCuentasPorPagar());
            statement.executeUpdate();
        }
    }

    public void eliminarPago(int idPagosCuentasPorPagar) throws SQLException {
        String query = "DELETE FROM pagos_cuentas_por_pagar WHERE id_pagos_cuentas_por_pagar = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPagosCuentasPorPagar);
            statement.executeUpdate();
        }
    }

    public PagoCuentaPorPagar obtenerPagoPorId(int idPagosCuentasPorPagar) throws SQLException {
        String query = "SELECT * FROM pagos_cuentas_por_pagar WHERE id_pagos_cuentas_por_pagar = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPagosCuentasPorPagar);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearPagoDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public int obtenerCantidadFilasPorIdOrdenCompra(int id_compras_internasp) throws SQLException {
        int filas;
        String query = "SELECT COUNT(*) AS count FROM pagos_cuentas_por_pagar WHERE id_compras_internasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_compras_internasp);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    filas = resultSet.getInt("count");
                    if (filas > 0) {
                        return filas;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        }
    }

    public List<PagoCuentaPorPagar> obtenerTodosLosPagosPorIdOrdenCompra(int id_compras_internasp) throws SQLException {
        List<PagoCuentaPorPagar> pagos = new ArrayList<>();
        String query = "SELECT pcpp.*, fp.tipo AS fptipoformapagonombre FROM pagos_cuentas_por_pagar pcpp INNER JOIN forma_pagos fp ON pcpp.id_forma_pago = fp.id WHERE id_compras_internasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_compras_internasp);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PagoCuentaPorPagar pago = crearPagoDesdeResultSetConTipoFormaPago(resultSet);
                    pagos.add(pago);
                }
            }
        }

        return pagos;
    }

    public List<PagoCuentaPorPagar> obtenerTodosLosPagos() throws SQLException {
        List<PagoCuentaPorPagar> pagos = new ArrayList<>();
        String query = "SELECT * FROM pagos_cuentas_por_pagar";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                PagoCuentaPorPagar pago = crearPagoDesdeResultSet(resultSet);
                pagos.add(pago);
            }
        }

        return pagos;
    }

    private PagoCuentaPorPagar crearPagoDesdeResultSet(ResultSet resultSet) throws SQLException {
        PagoCuentaPorPagar pago = new PagoCuentaPorPagar();
        pago.setIdPagosCuentasPorPagar(resultSet.getInt("id_pagos_cuentas_por_pagar"));
        pago.setIdComprasInternasP(resultSet.getInt("id_compras_internasp"));
        pago.setNumPago(resultSet.getInt("num_pago"));
        pago.setFechaPago(resultSet.getDate("fecha_pago"));
        pago.setImportePago(resultSet.getDouble("importe_pago"));
        pago.setUsuarioModificacion(resultSet.getInt("usuario_modificiacion"));
        pago.setFechaModificacion(resultSet.getTimestamp("fecha_modificiacion"));
        pago.setId_forma_pago(resultSet.getInt("id_forma_pago"));
        return pago;
    }
    
    private PagoCuentaPorPagar crearPagoDesdeResultSetConTipoFormaPago(ResultSet resultSet) throws SQLException {
        PagoCuentaPorPagar pago = new PagoCuentaPorPagar();
        pago.setIdPagosCuentasPorPagar(resultSet.getInt("id_pagos_cuentas_por_pagar"));
        pago.setIdComprasInternasP(resultSet.getInt("id_compras_internasp"));
        pago.setNumPago(resultSet.getInt("num_pago"));
        pago.setFechaPago(resultSet.getDate("fecha_pago"));
        pago.setImportePago(resultSet.getDouble("importe_pago"));
        pago.setUsuarioModificacion(resultSet.getInt("usuario_modificiacion"));
        pago.setFechaModificacion(resultSet.getTimestamp("fecha_modificiacion"));
        pago.setId_forma_pago(resultSet.getInt("id_forma_pago"));
        pago.setFptipoformapagonombre(resultSet.getString("fptipoformapagonombre"));
        return pago;
    }
}
