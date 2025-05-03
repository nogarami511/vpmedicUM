/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import clases_hospital.InventarioAlmacen;

/**
 *
 * @author alfar
 */
public class InventarioAlmacenDAO {

    private Connection connection;

    public InventarioAlmacenDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(InventarioAlmacen inventario) throws SQLException {
        String query = "INSERT INTO inventarios_almacen (id_insumo, total_existencia, fondo_fijo, id_almacen, usuario_modificacion, fecha_modificiacion) VALUES (?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, inventario.getIdInsumo());
            statement.setDouble(2, inventario.getTotalExistencia());
            statement.setDouble(3, inventario.getFondoFijo());
            statement.setInt(4, inventario.getIdAlmacen());
            statement.setInt(5, inventario.getUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                inventario.setIdInventarioAlmacen(generatedKeys.getInt(1));
            }
        }
    }

    public void insertAll(List<InventarioAlmacen> inventarios) throws SQLException {
        String query = "INSERT INTO inventarios_almacen (id_insumo, total_existencia, fondo_fijo, id_almacen, usuario_modificacion, fecha_modificiacion) VALUES (?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (InventarioAlmacen inventario : inventarios) {
                statement.setInt(1, inventario.getIdInsumo());
                statement.setDouble(2, inventario.getTotalExistencia());
                statement.setDouble(3, inventario.getFondoFijo());
                statement.setInt(4, inventario.getIdAlmacen());
                statement.setInt(5, inventario.getUsuarioModificacion());

                statement.addBatch(); // Agregar la operación a un lote
            }
            statement.executeBatch(); // Ejecutar todas las inserciones en un lote
        }
    }

    public void update(InventarioAlmacen inventario) throws SQLException {
        String query = "UPDATE inventarios_almacen SET id_insumo = ?, total_existencia = ?, fondo_fijo = ?, id_almacen = ?, usuario_modificacion = ?, fecha_modificiacion = NOW() WHERE id_inventario_almacen = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, inventario.getIdInsumo());
            statement.setDouble(2, inventario.getTotalExistencia());
            statement.setDouble(3, inventario.getFondoFijo());
            statement.setInt(4, inventario.getIdAlmacen());
            statement.setInt(5, inventario.getUsuarioModificacion());
            statement.setInt(6, inventario.getIdInventarioAlmacen());

            statement.executeUpdate();
        }
    }

    public void delete(int idInventarioAlmacen) throws SQLException {
        String query = "DELETE FROM inventarios_almacen WHERE id_inventario_almacen = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idInventarioAlmacen);
            statement.executeUpdate();
        }
    }

    public InventarioAlmacen getById(int idInventarioAlmacen) throws SQLException {
        String query = "SELECT * FROM inventarios_almacen WHERE id_inventario_almacen = ?";
        InventarioAlmacen inventario = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idInventarioAlmacen);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                inventario = mapResultSetToInventarioAlmacen(resultSet);
            }
        }

        return inventario;
    }

    public List<InventarioAlmacen> getAll() throws SQLException {
        String query = "SELECT * FROM inventarios_almacen";
        List<InventarioAlmacen> inventarios = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                InventarioAlmacen inventario = mapResultSetToInventarioAlmacen(resultSet);
                inventarios.add(inventario);
            }
        }

        return inventarios;
    }

    public List<InventarioAlmacen> getAllByIdAlmacen(int id_almacen) throws SQLException {
        String query = "SELECT ia.id_inventario_almacen, ia.id_insumo, ia.total_existencia, ia.fondo_fijo, ia.id_almacen, i.nombre, a.almacen, (ia.fondo_fijo - ia.total_existencia) AS faltante FROM inventarios_almacen ia INNER JOIN insumos i ON ia.id_insumo = i.id INNER JOIN almacen a ON ia.id_almacen = a.id_almacen WHERE ia.id_almacen = ?";
        List<InventarioAlmacen> inventarios = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_almacen);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    InventarioAlmacen inventario = mapResultSetToInventarioAlmacenDatos(resultSet);
                    inventarios.add(inventario);
                }
            }
        }

        return inventarios;
    }
    
    public List<InventarioAlmacen> getDataByIdAlmacen(int idAlmacen) throws SQLException{
        String querry = "SELECT i.nombre, ia.id_inventario_almacen, ia.id_insumo, ia.total_existencia, ia.id_almacen FROM inventarios_almacen ia INNER JOIN insumos i ON ia.id_insumo = i.id WHERE ia.id_almacen = ?";
        List<InventarioAlmacen> inventarios = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(querry)) {
            statement.setInt(1, idAlmacen);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    InventarioAlmacen inventario = mapResultSetToInventarioAlmacenDatosParaConsumoPaciente(resultSet);
                    inventarios.add(inventario);
                }
            }
        }

        return inventarios;
    }

    private InventarioAlmacen mapResultSetToInventarioAlmacen(ResultSet resultSet) throws SQLException {
        InventarioAlmacen inventario = new InventarioAlmacen();
        inventario.setIdInventarioAlmacen(resultSet.getInt("id_inventario_almacen"));
        inventario.setIdInsumo(resultSet.getInt("id_insumo"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setFondoFijo(resultSet.getDouble("fondo_fijo"));
        inventario.setIdAlmacen(resultSet.getInt("id_almacen"));
        inventario.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        inventario.setFechaModificacion(resultSet.getDate("fecha_modificiacion"));

        return inventario;
    }

    private InventarioAlmacen mapResultSetToInventarioAlmacenDatos(ResultSet resultSet) throws SQLException {
        InventarioAlmacen inventario = new InventarioAlmacen();
        inventario.setIdInventarioAlmacen(resultSet.getInt("id_inventario_almacen"));
        inventario.setIdInsumo(resultSet.getInt("id_insumo"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setFondoFijo(resultSet.getDouble("fondo_fijo"));
        inventario.setIdAlmacen(resultSet.getInt("id_almacen"));
        inventario.setNombre_inusmo(resultSet.getString("nombre"));
        inventario.setNombre_almacen(resultSet.getString("almacen"));
        inventario.setFalta(resultSet.getDouble("faltante"));

        return inventario;
    }
    
    private InventarioAlmacen mapResultSetToInventarioAlmacenDatosParaConsumoPaciente(ResultSet resultSet) throws SQLException {
        InventarioAlmacen inventario = new InventarioAlmacen();
        inventario.setIdInventarioAlmacen(resultSet.getInt("id_inventario_almacen"));
        inventario.setIdInsumo(resultSet.getInt("id_insumo"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setIdAlmacen(resultSet.getInt("id_almacen"));
        inventario.setNombre_inusmo(resultSet.getString("nombre"));

        return inventario;
    }

}
