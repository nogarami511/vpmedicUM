/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import clase.PagosAlimentos;
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
public class PagosAlimentosDAO {
    private Connection connection;

    public PagosAlimentosDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to create a new payment record
    public void create(PagosAlimentos pago) throws SQLException {
        String query = "INSERT INTO pagos_alimentos (nombre_cliente_pago_alimento, precio_unitario_pago_alimento, " +
                "id_forma_pago_alimento, descuento_pago_alimento, sub_total_pago_alimento, iva_pago_alimento, " +
                "total_pago_alimento, forma_pago_alimento, usuario_cobro_pago_alimento, fecha_pago_alimento, " +
                "fecha_modificacion_pago_alimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pago.getNombreCliente());
            statement.setDouble(2, pago.getPrecioUnitario());
            statement.setInt(3, pago.getIdFormaPago());
            statement.setDouble(4, pago.getDescuento());
            statement.setDouble(5, pago.getSubTotal());
            statement.setDouble(6, pago.getIva());
            statement.setDouble(7, pago.getTotal());
            statement.setString(8, pago.getFormaPago());
            statement.setInt(9, pago.getUsuarioCobro());

            statement.executeUpdate();
        }
    }

    // Method to update an existing payment record
    public void update(PagosAlimentos pago) throws SQLException {
        String query = "UPDATE pagos_alimentos SET nombre_cliente_pago_alimento = ?, " +
                "precio_unitario_pago_alimento = ?, id_forma_pago_alimento = ?, descuento_pago_alimento = ?, " +
                "sub_total_pago_alimento = ?, iva_pago_alimento = ?, total_pago_alimento = ?, " +
                "forma_pago_alimento = ?, usuario_cobro_pago_alimento = ?, fecha_modificacion_pago_alimento = ? " +
                "WHERE id_pago_alimento = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pago.getNombreCliente());
            statement.setDouble(2, pago.getPrecioUnitario());
            statement.setInt(3, pago.getIdFormaPago());
            statement.setDouble(4, pago.getDescuento());
            statement.setDouble(5, pago.getSubTotal());
            statement.setDouble(6, pago.getIva());
            statement.setDouble(7, pago.getTotal());
            statement.setString(8, pago.getFormaPago());
            statement.setInt(9, pago.getUsuarioCobro());
            statement.setTimestamp(10, pago.getFechaModificacion());
            statement.setInt(11, pago.getIdPagoAlimento());

            statement.executeUpdate();
        }
    }

    // Method to delete a payment record by its ID
    public void delete(int idPagoAlimento) throws SQLException {
        String query = "DELETE FROM pagos_alimentos WHERE id_pago_alimento = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPagoAlimento);
            statement.executeUpdate();
        }
    }

    // Method to retrieve a payment record by its ID
    public PagosAlimentos getById(int idPagoAlimento) throws SQLException {
        String query = "SELECT * FROM pagos_alimentos WHERE id_pago_alimento = ?";
        PagosAlimentos pago = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPagoAlimento);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                pago = mapResultSetToPagosAlimentos(resultSet);
            }
        }

        return pago;
    }

    // Method to retrieve all payment records
    public List<PagosAlimentos> getAll() throws SQLException {
        String query = "SELECT * FROM pagos_alimentos";
        List<PagosAlimentos> pagos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PagosAlimentos pago = mapResultSetToPagosAlimentos(resultSet);
                pagos.add(pago);
            }
        }

        return pagos;
    }

    // Helper method to map the ResultSet to a PagosAlimentos object
    private PagosAlimentos mapResultSetToPagosAlimentos(ResultSet resultSet) throws SQLException {
        PagosAlimentos pago = new PagosAlimentos();
        pago.setIdPagoAlimento(resultSet.getInt("id_pago_alimento"));
        pago.setNombreCliente(resultSet.getString("nombre_cliente_pago_alimento"));
        pago.setPrecioUnitario(resultSet.getDouble("precio_unitario_pago_alimento"));
        pago.setIdFormaPago(resultSet.getInt("id_forma_pago_alimento"));
        pago.setDescuento(resultSet.getDouble("descuento_pago_alimento"));
        pago.setSubTotal(resultSet.getDouble("sub_total_pago_alimento"));
        pago.setIva(resultSet.getDouble("iva_pago_alimento"));
        pago.setTotal(resultSet.getDouble("total_pago_alimento"));
        pago.setFormaPago(resultSet.getString("forma_pago_alimento"));
        pago.setUsuarioCobro(resultSet.getInt("usuario_cobro_pago_alimento"));
        pago.setFechaPago(resultSet.getTimestamp("fecha_pago_alimento"));
        pago.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion_pago_alimento"));

        return pago;
    }
}
