/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Proveedor;
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
public class ProveedorDAO {
    private Connection connection;

   public ProveedorDAO(Connection connection){
       this.connection = connection;
   }

    public void insertar(Proveedor proveedor) throws SQLException {
        String query = "INSERT INTO proveedores (nombreComercial, razonSocial, direccion, telefono, rfc, id_tipo_proveedor) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getNombreComercial());
            statement.setString(2, proveedor.getRazonSocial());
            statement.setString(3, proveedor.getDireccion());
            statement.setString(4, proveedor.getTelefono());
            statement.setString(5, proveedor.getRfc());
            statement.setInt(6, 1);//proveedor.getId_tipo_proveedor()

            statement.executeUpdate();
        }
    }

    public void actualizar(Proveedor proveedor) throws SQLException {
        String query = "UPDATE proveedores SET nombreComercial = ?, razonSocial = ?, direccion = ?, telefono = ?, rfc = ?, id_tipo_proveedor = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getNombreComercial());
            statement.setString(2, proveedor.getRazonSocial());
            statement.setString(3, proveedor.getDireccion());
            statement.setString(4, proveedor.getTelefono());
            statement.setString(5, proveedor.getRfc());
            statement.setInt(6, 1);
            statement.setInt(7, proveedor.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM proveedores WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Proveedor obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM proveedores WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearProveedorDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<Proveedor> obtenerTodos() throws SQLException {
        List<Proveedor> proveedores = new ArrayList<>();
        String query = "SELECT * FROM proveedores";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Proveedor proveedor = crearProveedorDesdeResultSet(resultSet);
                proveedores.add(proveedor);
            }
        }

        return proveedores;
    }

    private Proveedor crearProveedorDesdeResultSet(ResultSet resultSet) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(resultSet.getInt("id"));
        proveedor.setNombreComercial(resultSet.getString("nombreComercial"));
        proveedor.setRazonSocial(resultSet.getString("razonSocial"));
        proveedor.setDireccion(resultSet.getString("direccion"));
        proveedor.setTelefono(resultSet.getString("telefono"));
        proveedor.setRfc(resultSet.getString("rfc"));
        proveedor.setId_tipo_proveedor(resultSet.getInt("id_tipo_proveedor"));
        return proveedor;
    }
}
