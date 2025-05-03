/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Costo;
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
public class CostosDAO {

    private Connection connection;

    public CostosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(Costo costo) throws SQLException {
        String query = "INSERT INTO costos (clave, id_insumo, cantidad_unitariaxcaja, costo_compra_caja, costo_compra_unitaria, "
                + "utilidad, precio_venta_caja, precio_venta_unitaria, observacion, id_usuarioModificacion, fechaModificacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, costo.getClave());
            statement.setInt(2, costo.getIdInsumo());
            statement.setDouble(3, costo.getCantidadUnitariaxCaja());
            statement.setDouble(4, costo.getCostoCompraCaja());
            statement.setDouble(5, costo.getCostoCompraUnitaria());
            statement.setDouble(6, costo.getUtilidad());
            statement.setDouble(7, costo.getPrecioVentaCaja());
            statement.setDouble(8, costo.getPrecioVentaUnitaria());
            statement.setString(9, costo.getObservacion());
            statement.setInt(10, costo.getIdUsuarioModificacion());
            statement.executeUpdate();
        }
    }

    public void insertarConPreciasPaquete(Costo costo) throws SQLException {
        String query = "INSERT INTO costos (clave, id_insumo, cantidad_unitariaxcaja, costo_compra_caja, costo_compra_unitaria, "
                + "utilidad, precio_venta_caja, precio_venta_unitaria, observacion, id_usuarioModificacion, fechaModificacion, "
                + "utilidad_paquete, precio_venta_caja_paquete, precio_venta_unitaria_paquete) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, costo.getClave());
            statement.setInt(2, costo.getIdInsumo());
            statement.setDouble(3, costo.getCantidadUnitariaxCaja());
            statement.setDouble(4, costo.getCostoCompraCaja());
            statement.setDouble(5, costo.getCostoCompraUnitaria());
            statement.setDouble(6, costo.getUtilidad());
            statement.setDouble(7, costo.getPrecioVentaCaja());
            statement.setDouble(8, costo.getPrecioVentaUnitaria());
            statement.setString(9, costo.getObservacion());
            statement.setInt(10, costo.getIdUsuarioModificacion());
            statement.setDouble(11, costo.getUtilidadPaquete());
            statement.setDouble(12, costo.getPrecioVentaCajaPaquete());
            statement.setDouble(13, costo.getPrecioVentaUnitariaPaquete());
            statement.executeUpdate();
        }
    }

    public void actualizar(Costo costo) throws SQLException {
        String query = "UPDATE costos SET clave = ?, id_insumo = ?, cantidad_unitariaxcaja = ?, costo_compra_caja = ?, "
                + "costo_compra_unitaria = ?, utilidad = ?, precio_venta_caja = ?, precio_venta_unitaria = ?, "
                + "observacion = ?, id_usuarioModificacion = ?, fechaModificacion = NOW() WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, costo.getClave());
            statement.setInt(2, costo.getIdInsumo());
            statement.setDouble(3, costo.getCantidadUnitariaxCaja());
            statement.setDouble(4, costo.getCostoCompraCaja());
            statement.setDouble(5, costo.getCostoCompraUnitaria());
            statement.setDouble(6, costo.getUtilidad());
            statement.setDouble(7, costo.getPrecioVentaCaja());
            statement.setDouble(8, costo.getPrecioVentaUnitaria());
            statement.setString(9, costo.getObservacion());
            statement.setInt(10, costo.getIdUsuarioModificacion());
            statement.setInt(11, costo.getId());
            statement.executeUpdate();
        }
    }

    public void actualizarConCostoPaquete(Costo costo) throws SQLException {
        String query = "UPDATE costos SET clave = ?, id_insumo = ?, cantidad_unitariaxcaja = ?, costo_compra_caja = ?, "
                + "costo_compra_unitaria = ?, utilidad = ?, precio_venta_caja = ?, precio_venta_unitaria = ?, "
                + "observacion = ?, id_usuarioModificacion = ?, fechaModificacion = NOW(), "
                + "utilidad_paquete = ?, precio_venta_caja_paquete = ?, precio_venta_unitaria_paquete = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, costo.getClave());
            statement.setInt(2, costo.getIdInsumo());
            statement.setDouble(3, costo.getCantidadUnitariaxCaja());
            statement.setDouble(4, costo.getCostoCompraCaja());
            statement.setDouble(5, costo.getCostoCompraUnitaria());
            statement.setDouble(6, costo.getUtilidad());
            statement.setDouble(7, costo.getPrecioVentaCaja());
            statement.setDouble(8, costo.getPrecioVentaUnitaria());
            statement.setString(9, costo.getObservacion());
            statement.setInt(10, costo.getIdUsuarioModificacion());
            statement.setDouble(11, costo.getUtilidadPaquete());
            statement.setDouble(12, costo.getPrecioVentaCajaPaquete());
            statement.setDouble(13, costo.getPrecioVentaUnitariaPaquete());
            statement.setInt(14, costo.getId());
            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM costos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Costo obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM costos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCosto(resultSet);
                }
            }
        }

        return null;
    }

    public Costo obtenerPorIdInsumo(int id_insumo) throws SQLException {
        String query = "SELECT * FROM costos WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCosto(resultSet);
                }
            }
        }

        return null;
    }

    public Costo obtenerPorIdInsumoMedico(int id_insumo) throws SQLException {
        String query = "SELECT * FROM costos WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCosto(resultSet);
                }
            }
        }

        return null;
    }

    public List<Costo> obtenerTodos() throws SQLException {
        List<Costo> costos = new ArrayList<>();
        String query = "SELECT * FROM costos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Costo costo = mapearCosto(resultSet);
                costos.add(costo);
            }
        }

        return costos;
    }

    public Costo obtenerPorIdConPrecioPaquete(int id) throws SQLException {
        String query = "SELECT * FROM costos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCostoConPrecioPaquete(resultSet);
                }
            }
        }

        return null;
    }

    public Costo obtenerPorIdInsumoConPrecioPaquete(int id_insumo) throws SQLException {
        String query = "SELECT * FROM costos WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCostoConPrecioPaquete(resultSet);
                }
            }
        }

        return null;
    }

    public Costo obtenerPorIdInsumoMedicoConPrecioPaquete(int id_insumo) throws SQLException {
        String query = "SELECT * FROM costos WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCostoConPrecioPaquete(resultSet);
                }
            }
        }

        return null;
    }

    public List<Costo> obtenerTodosConPrecioPaquete() throws SQLException {
        List<Costo> costos = new ArrayList<>();
        String query = "SELECT * FROM costos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Costo costo = mapearCostoConPrecioPaquete(resultSet);
                costos.add(costo);
            }
        }

        return costos;
    }

    private Costo mapearCosto(ResultSet resultSet) throws SQLException {
        Costo costo = new Costo();

        costo.setClave(resultSet.getString("clave"));
        costo.setId(resultSet.getInt("id"));
        costo.setIdInsumo(resultSet.getInt("id_insumo"));
        costo.setCantidadUnitariaxCaja(resultSet.getDouble("cantidad_unitariaxcaja"));
        costo.setCostoCompraCaja(resultSet.getDouble("costo_compra_caja"));
        costo.setCostoCompraUnitaria(resultSet.getDouble("costo_compra_unitaria"));
        costo.setUtilidad(resultSet.getDouble("utilidad"));
        costo.setPrecioVentaCaja(resultSet.getDouble("precio_venta_caja"));
        costo.setPrecioVentaUnitaria(resultSet.getDouble("precio_venta_unitaria"));
        costo.setObservacion(resultSet.getString("observacion"));
        costo.setIdUsuarioModificacion(resultSet.getInt("id_usuarioModificacion"));
        costo.setFechaModificacion(resultSet.getDate("fechaModificacion"));

        return costo;
    }

    private Costo mapearCostoConPrecioPaquete(ResultSet resultSet) throws SQLException {
        Costo costo = new Costo();

        costo.setId(resultSet.getInt("id"));
        costo.setClave(resultSet.getString("clave"));
        costo.setIdInsumo(resultSet.getInt("id_insumo"));
        costo.setCantidadUnitariaxCaja(resultSet.getDouble("cantidad_unitariaxcaja"));
        costo.setCostoCompraCaja(resultSet.getDouble("costo_compra_caja"));
        costo.setCostoCompraUnitaria(resultSet.getDouble("costo_compra_unitaria"));
        costo.setUtilidad(resultSet.getDouble("utilidad"));
        costo.setPrecioVentaCaja(resultSet.getDouble("precio_venta_caja"));
        costo.setPrecioVentaUnitaria(resultSet.getDouble("precio_venta_unitaria"));
        costo.setObservacion(resultSet.getString("observacion"));
        costo.setIdUsuarioModificacion(resultSet.getInt("id_usuarioModificacion"));
        costo.setFechaModificacion(resultSet.getDate("fechaModificacion"));
        costo.setUtilidadPaquete(resultSet.getInt("utilidad_paquete"));
        costo.setPrecioVentaCajaPaquete(resultSet.getDouble("precio_venta_caja_paquete"));
        costo.setPrecioVentaUnitariaPaquete(resultSet.getDouble("precio_venta_unitaria_paquete"));

        return costo;
    }

    public double obtenerCosto(int id) throws SQLException {
        double precioVenta = 0;
        String query = "SELECT c.precio_venta_unitaria FROM costos c WHERE c.id_insumo = 621";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    precioVenta = resultSet.getDouble(1);
                }
            }
        }

        return precioVenta;
    }

}
