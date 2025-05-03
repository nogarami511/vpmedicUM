/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ProductoVenta;
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
public class ProductoVentaDAO {
    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public ProductoVentaDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un nuevo producto de venta
    public void insertar(ProductoVenta producto) throws SQLException {
        String sql = "INSERT INTO producto_ventas (codigo_producto, nombre_producto, tipo_producto, descripcion, precio_venta, iva, clave_sat, id_estatus_producto, fecha_modificacion, usuario_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getCodigoProducto());
            statement.setString(2, producto.getNombreProducto());
            statement.setInt(3, producto.getTipoProducto());
            statement.setString(4, producto.getDescripcion());
            statement.setDouble(5, producto.getPrecioVenta());
            statement.setInt(6, producto.getIVA());
            statement.setString(7, producto.getClaveSAT());
            statement.setString(8, producto.getIdEstatusProducto());
            statement.setTimestamp(9, producto.getFechaModificacion());
            statement.setInt(10, producto.getUsuarioModificacion());

            statement.executeUpdate();
        }
    }

    // Método para actualizar un producto de venta existente
    public void actualizar(ProductoVenta producto) throws SQLException {
        String sql = "UPDATE producto_ventas SET codigo_producto=?, nombre_producto=?, tipo_producto=?, descripcion=?, precio_venta=?, iva=?, clave_sat=?, id_estatus_producto=?, fecha_modificacion=?, usuario_modificacion=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getCodigoProducto());
            statement.setString(2, producto.getNombreProducto());
            statement.setInt(3, producto.getTipoProducto());
            statement.setString(4, producto.getDescripcion());
            statement.setDouble(5, producto.getPrecioVenta());
            statement.setInt(6, producto.getIVA());
            statement.setString(7, producto.getClaveSAT());
            statement.setString(8, producto.getIdEstatusProducto());
            statement.setTimestamp(9, producto.getFechaModificacion());
            statement.setInt(10, producto.getUsuarioModificacion());
            statement.setInt(11, producto.getId());

            statement.executeUpdate();
        }
    }

    // Método para eliminar un producto de venta existente por su ID
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM producto_ventas WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Método para obtener un producto de venta por su ID
    public ProductoVenta obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM producto_ventas WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearProducto(resultSet);
                }
            }
        }
        return null;
    }

    // Método para obtener todos los productos de venta
    public List<ProductoVenta> obtenerTodos() throws SQLException {
        List<ProductoVenta> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto_ventas";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ProductoVenta producto = mapearProducto(resultSet);
                productos.add(producto);
            }
        }
        return productos;
    }

    // Método auxiliar para mapear los datos del resultado de la consulta a un objeto ProductoVenta
    private ProductoVenta mapearProducto(ResultSet resultSet) throws SQLException {
        ProductoVenta producto = new ProductoVenta();
        producto.setId(resultSet.getInt("id"));
        producto.setCodigoProducto(resultSet.getString("codigo_producto"));
        producto.setNombreProducto(resultSet.getString("nombre_producto"));
        producto.setTipoProducto(resultSet.getInt("tipo_producto"));
        producto.setDescripcion(resultSet.getString("descripcion"));
        producto.setPrecioVenta(resultSet.getDouble("precio_venta"));
        producto.setIVA(resultSet.getInt("iva"));
        producto.setClaveSAT(resultSet.getString("clave_sat"));
        producto.setIdEstatusProducto(resultSet.getString("id_estatus_producto"));
        producto.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        producto.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        return producto;
    }
}
