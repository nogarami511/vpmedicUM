/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ComprasInternasDetalle;
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
public class ComprasInternasDetalleDAO {
    private final Connection connection;

    public ComprasInternasDetalleDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(ComprasInternasDetalle detalle) throws SQLException {
        String query = "INSERT INTO compras_internas_detalle (id_compras_internasp, producto, codigo, modelo, cantidad, precio_unitario, descuento, importe, clave_pedido, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query)) { 
            statement.setInt(1, detalle.getIdComprasInternasp());
            statement.setString(2, detalle.getProducto());
            statement.setString(3, detalle.getCodigo());
            statement.setString(4, detalle.getModelo());
            statement.setInt(5, detalle.getCantidad());
            statement.setDouble(6, detalle.getPrecioUnitario());
            statement.setDouble(7, detalle.getDescuento());
            statement.setDouble(8, detalle.getImporte());
            statement.setString(9, detalle.getClavePedido());
            statement.setInt(10, detalle.getUsuarioCreacion());
            statement.setInt(11, detalle.getUsuarioModificacion());

            statement.executeUpdate();
        }
    }

    public void actualizar(ComprasInternasDetalle detalle) throws SQLException {
        String query = "UPDATE compras_internas_detalle SET id_compras_internasp = ?, producto = ?, codigo = ?, modelo = ?, cantidad = ?, precio_unitario = ?, descuento = ?, importe = ?, clave_pedido = ?, usuario_creacion = ?, fecha_creacion = ?, usuario_modificacion = ?, fecha_modificacion = NOW() WHERE id_compras_internas_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, detalle.getIdComprasInternasp());
            statement.setString(2, detalle.getProducto());
            statement.setString(3, detalle.getCodigo());
            statement.setString(4, detalle.getModelo());
            statement.setInt(5, detalle.getCantidad());
            statement.setDouble(6, detalle.getPrecioUnitario());
            statement.setDouble(7, detalle.getDescuento());
            statement.setDouble(8, detalle.getImporte());
            statement.setString(9, detalle.getClavePedido());
            statement.setInt(10, detalle.getUsuarioCreacion());
            statement.setDate(11, detalle.getFechaCreacion());
            statement.setInt(12, detalle.getUsuarioModificacion());
            statement.setInt(13, detalle.getIdComprasInternasDetalle());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM compras_internas_detalle WHERE id_compras_internas_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public ComprasInternasDetalle obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM compras_internas_detalle WHERE id_compras_internas_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearDetalleDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<ComprasInternasDetalle> obtenerTodos() throws SQLException {
        List<ComprasInternasDetalle> detalles = new ArrayList<>();
        String query = "SELECT * FROM compras_internas_detalle";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ComprasInternasDetalle detalle = crearDetalleDesdeResultSet(resultSet);
                detalles.add(detalle);
            }
        }

        return detalles;
    }

    private ComprasInternasDetalle crearDetalleDesdeResultSet(ResultSet resultSet) throws SQLException {
        ComprasInternasDetalle detalle = new ComprasInternasDetalle();
        detalle.setIdComprasInternasDetalle(resultSet.getInt("id_compras_internas_detalle"));
        detalle.setIdComprasInternasp(resultSet.getInt("id_compras_internasp"));
        detalle.setProducto(resultSet.getString("producto"));
        detalle.setCodigo(resultSet.getString("codigo"));
        detalle.setModelo(resultSet.getString("modelo"));
        detalle.setCantidad(resultSet.getInt("cantidad"));
        detalle.setPrecioUnitario(resultSet.getDouble("precio_unitario"));
        detalle.setDescuento(resultSet.getDouble("descuento"));
        detalle.setImporte(resultSet.getDouble("importe"));
        detalle.setClavePedido(resultSet.getString("clave_pedido"));
        detalle.setUsuarioCreacion(resultSet.getInt("usuario_creacion"));
        detalle.setFechaCreacion(resultSet.getDate("fecha_creacion"));
        detalle.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        detalle.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        return detalle;
    }
}
