/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.MovimientoDetalle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class MovimientoDetalleDAO {

    private Connection connection;

    public MovimientoDetalleDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(MovimientoDetalle movimiento) throws SQLException {
        String query = "INSERT INTO movimientos_inventario_detalle (movimientos_inventariop, id_insumo, caducidad, "
                + "lote_insumo, inventario_inicial, movimiento, inventario_final, costo, usuario_modificacion, "
                + "fecha_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, movimiento.getId_insumo_mov_padre());
            statement.setDouble(2, movimiento.getId_insumo());
            statement.setDate(3, movimiento.getCaducidad());
            statement.setString(4, movimiento.getLote_insumo());
            statement.setDouble(5, movimiento.getInventario_inicial());
            statement.setDouble(6, movimiento.getMovimineto());
            statement.setDouble(7, movimiento.getInventario_final());
            statement.setDouble(8, movimiento.getCosto());
            statement.setInt(9, movimiento.getUsuario_modificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                movimiento.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
    
    public void CrearmovimientoconLote(MovimientoDetalle movimiento){
        
        try (CallableStatement stm = connection.prepareCall("{CALL INSERTAR_INVENTARIO_X_MOVIMIENTO_DETALLE_CON_LOTE (?,?,?,?,?,?,?,?,?,?)}")){
            stm.setInt(1, movimiento.getId_insumo_mov_padre());
            stm.setInt(2, movimiento.getId_insumo());
            stm.setDate(3, movimiento.getCaducidad());
            stm.setString(4, movimiento.getLote_insumo());
            stm.setDouble(5, movimiento.getInventario_inicial());
            stm.setDouble(6, movimiento.getMovimineto());
            stm.setDouble(7, movimiento.getInventario_final());
            stm.setDouble(8, movimiento.getCosto());
            stm.setInt(9, movimiento.getUsuario_modificacion());
            stm.setInt(10, movimiento.getExiste_lote());
            stm.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void actualizarMovimientoDetalle(MovimientoDetalle movimientoDetalle) throws SQLException {
        String query = "UPDATE movimientos_inventario_detalle SET movimientos_inventariop = ?, id_insumo = ?, caducidad = ?, lote_insumo = ?, "
                + "inventario_inicial = ?, movimiento = ?, inventario_final = ?, costo = ?, usuario_modificacion = ?, fecha_modificacion = NOW() "
                + "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movimientoDetalle.getId_insumo_mov_padre());
            statement.setInt(2, movimientoDetalle.getId_insumo());
            statement.setDate(3, movimientoDetalle.getCaducidad());
            statement.setString(4, movimientoDetalle.getLote_insumo());
            statement.setDouble(5, movimientoDetalle.getInventario_inicial());
            statement.setDouble(6, movimientoDetalle.getMovimineto());
            statement.setDouble(7, movimientoDetalle.getInventario_final());
            statement.setDouble(8, movimientoDetalle.getCosto());
            statement.setInt(9, movimientoDetalle.getUsuario_modificacion());
            statement.setInt(10, movimientoDetalle.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("algo salio mal");
        }
    }

    public void eliminarMovimientoDetalle(int id) throws SQLException {
        String query = "DELETE FROM movimientos_inventario_detalle WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public MovimientoDetalle obtenerMovimientoDetalle(int id) throws SQLException {
        String query = "SELECT * FROM movimientos_inventario_detalle WHERE id = ?";
        MovimientoDetalle movimientoDetalle = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movimientoDetalle = mapearMovimientoDetalle(resultSet);
            }
        }

        return movimientoDetalle;
    }

    public List<MovimientoDetalle> obtenerTodosMovimientosDetalle() throws SQLException {
        String query = "SELECT * FROM movimientos_inventario_detalle";
        List<MovimientoDetalle> movimientosDetalle = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                MovimientoDetalle movimientoDetalle = mapearMovimientoDetalle(resultSet);
                movimientosDetalle.add(movimientoDetalle);
            }
        }

        return movimientosDetalle;
    }

    public List<MovimientoDetalle> obtenerLotesPorIdInsumo(int id_insumo) throws SQLException {
        String query = "SELECT md.* FROM movimientos_inventario_detalle md "
                + "INNER JOIN movimientos_inventariop mp ON md.movimientos_inventariop = mp.id "
                + "WHERE md.id_insumo = ? AND (mp.tipo_mov >= 1 AND mp.tipo_mov <= 6)";
        List<MovimientoDetalle> movimientosDetalle = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                MovimientoDetalle movimientoDetalle = mapearMovimientoDetalle(resultSet);
                movimientosDetalle.add(movimientoDetalle);
            }
        }

        return movimientosDetalle;
    }

    private MovimientoDetalle mapearMovimientoDetalle(ResultSet resultSet) throws SQLException {
        MovimientoDetalle movimientoDetalle = new MovimientoDetalle();
        movimientoDetalle.setId(resultSet.getInt("id"));
        movimientoDetalle.setId_insumo_mov_padre(resultSet.getInt("movimientos_inventariop"));
        movimientoDetalle.setId_insumo(resultSet.getInt("id_insumo"));
        movimientoDetalle.setCaducidad(resultSet.getDate("caducidad"));
        movimientoDetalle.setLote_insumo(resultSet.getString("lote_insumo"));
        movimientoDetalle.setInventario_inicial(resultSet.getDouble("inventario_inicial"));
        movimientoDetalle.setMovimineto(resultSet.getDouble("movimiento"));
        movimientoDetalle.setInventario_final(resultSet.getDouble("inventario_final"));
        movimientoDetalle.setCosto(resultSet.getDouble("costo"));
        movimientoDetalle.setUsuario_modificacion(resultSet.getInt("usuario_modificacion"));
        movimientoDetalle.setFecha_modificacion(resultSet.getDate("fecha_modificacion"));

        return movimientoDetalle;
    }
}
