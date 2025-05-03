/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ComandaDetalle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComandaDetalleDAO {
    
    private Connection connection;
    
    public ComandaDetalleDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void agregarComandaDetalle(ComandaDetalle comandaDetalle) throws SQLException {
        String query = "INSERT INTO comanda_detalle (id_comanda, id_producto, cantidad, costo_unitario, subtotal, iva, total, id_usuario_modificacion, fecha_modificacion, id_estatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, comandaDetalle.getIdComanda());
            statement.setInt(2, comandaDetalle.getIdProducto());
            statement.setInt(3, comandaDetalle.getCantidad());
            statement.setDouble(4, comandaDetalle.getCostoUnitario());
            statement.setDouble(5, comandaDetalle.getSubtotal());
            statement.setDouble(6, comandaDetalle.getIva());
            statement.setDouble(7, comandaDetalle.getTotal());
            statement.setInt(8, comandaDetalle.getIdUsuarioModificacion());
            statement.setInt(10, comandaDetalle.getIdEstatus());
            
            statement.executeUpdate();
        }
    }

    // Método para obtener un ComandaDetalle por su ID
    public ComandaDetalle obtenerComandaDetallePorID(int idComanda) throws SQLException {
        String query = "SELECT cd.id_detalle, cd.id_comanda , cd.id_producto, p.nombre, cd.total, cd.cantidad FROM comanda_detalle cd \n"
                + "INNER JOIN paquetesalimentos p ON p.id = cd.id_producto\n"
                + "WHERE cd.id_comanda= ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idComanda);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearComandaDetalleParaDescartar(resultSet);
                }
            }
        }
        
        return null; // Si no se encuentra el ComandaDetalle.
    }

    // Método para obtener todos los ComandaDetalle
    public List<ComandaDetalle> obtenerTodosLosComandaDetalle() throws SQLException {
        List<ComandaDetalle> comandaDetalles = new ArrayList<>();
        String query = "SELECT * FROM comanda_detalle";
        
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                comandaDetalles.add(crearComandaDetalleDesdeResultSet(resultSet));
            }
        }
        
        return comandaDetalles;
    }

    // Método privado para crear un objeto ComandaDetalle desde un ResultSet
    private ComandaDetalle crearComandaDetalleDesdeResultSet(ResultSet resultSet) throws SQLException {
        ComandaDetalle comandaDetalle = new ComandaDetalle();
        comandaDetalle.setIdDetalle(resultSet.getInt("id_detalle"));
        comandaDetalle.setIdComanda(resultSet.getInt("id_comanda"));
        comandaDetalle.setIdProducto(resultSet.getInt("id_producto"));
        comandaDetalle.setCantidad(resultSet.getInt("cantidad"));
        comandaDetalle.setCostoUnitario(resultSet.getDouble("costo_unitario"));
        comandaDetalle.setSubtotal(resultSet.getDouble("subtotal"));
        comandaDetalle.setIva(resultSet.getDouble("iva"));
        comandaDetalle.setTotal(resultSet.getDouble("total"));
        comandaDetalle.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        comandaDetalle.setIdEstatus(resultSet.getInt("id_estatus"));
        
        return comandaDetalle;
    }
    
    private ComandaDetalle crearComandaDetalleParaDescartar(ResultSet resultSet) throws SQLException {
        ComandaDetalle comandaDetalle = new ComandaDetalle();
        comandaDetalle.setIdDetalle(resultSet.getInt("id_detalle"));
        comandaDetalle.setIdComanda(resultSet.getInt("id_comanda"));
        comandaDetalle.setCantidad(resultSet.getInt("cantidad"));
        comandaDetalle.setIdProducto(resultSet.getInt("id_producto"));
        comandaDetalle.setTotal(resultSet.getDouble("total"));
        comandaDetalle.setNombreComida(resultSet.getString("nombre"));
        return comandaDetalle;
    }
}
