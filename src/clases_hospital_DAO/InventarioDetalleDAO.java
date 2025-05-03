/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.InventarioDetalle;
import clases_hospital.MovimientoDetalle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author PC
 */
public class InventarioDetalleDAO {

    private Connection connection;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);

    public InventarioDetalleDAO(Connection connection) {
        this.connection = connection;
    }
    
    // SELECT

    public List<InventarioDetalle> traerlotes(int id_insumo) throws SQLException {
        List<InventarioDetalle> listalotes = new ArrayList<>();

        try (CallableStatement stm = connection.prepareCall("{call TRAER_LOTES_X_ID_INSUMO (?)}")) {
            stm.setInt(1, id_insumo);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while (rs.next()) {
                listalotes.add(mapearInventarioDetalle(rs));
            }
        } catch (SQLException e) {
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\n" + e.getMessage());
            alertaError.showAndWait();
            e.printStackTrace();
        }

        return listalotes;
    }
    public List<InventarioDetalle> traerlotesconCaducados(int id_insumo) throws SQLException {
        List<InventarioDetalle> listalotes = new ArrayList<>();

        try (CallableStatement stm = connection.prepareCall("{call TRAER_LOTES_X_ID_INSUMO_CON_CADUCADOS (?)}")) {
            stm.setInt(1, id_insumo);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while (rs.next()) {
                listalotes.add(mapearInventarioDetalle(rs));
            }
        } catch (SQLException e) {
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\n" + e.getMessage());
            alertaError.showAndWait();
            e.printStackTrace();
        }

        return listalotes;
    }
     public List<InventarioDetalle> visualizarlotesconestado(int id_insumo) throws SQLException {
        List<InventarioDetalle> listalotes = new ArrayList<>();

        try (CallableStatement stm = connection.prepareCall("{call VIZUALIZAR_LOTES_POR_ID_INSUMO (?)}")) {
            stm.setInt(1, id_insumo);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while (rs.next()) {
                listalotes.add(mapearInventarioDetalleconestado(rs));
            }
        } catch (SQLException e) {

            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\n" + e.getMessage());
            alertaError.showAndWait();
            e.printStackTrace();
        }

        return listalotes;
    }

         public boolean existeInventarioDetalle(int id_inventario) {
        boolean existe = false;
        try (CallableStatement stm = connection.prepareCall("{CALL EXISTE_LOTE_POR_ID_INVENTARIO(?)}")) {
            stm.setInt(1, id_inventario);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if (rs.next()) {
                existe = rs.getBoolean("respuesta");
            } else {
                existe = false;
            }
        } catch (SQLException e) {
             e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\n" + e.getMessage());
            alertaError.showAndWait();
           
        }

        return existe;
    }
    
    // INSERT AND UPDATE
    
    public void EntradasInventarioDetConCompras(MovimientoDetalle movimiento, int id_compra_ins_det, int usersystem) {
        try (CallableStatement stm = connection.prepareCall("{ call ENTRADAS_INVENTARIO_DETALLE_con_EstatusCompra_Y_movInv (?,?,?,?,?,?,?,?)}")) {
            stm.setInt(1, id_compra_ins_det);
            stm.setInt(2, movimiento.getId_insumo());
            stm.setInt(3, movimiento.getId_inventario_detalle());
            stm.setString(4, movimiento.getLote_insumo());
            stm.setDate(5, movimiento.getCaducidad());      
            stm.setInt(6, (int) movimiento.getMovimineto());        
            stm.setInt(7, movimiento.getId_insumo_mov_padre());
            stm.setInt(8, usersystem);
            stm.execute();

        } catch (SQLException e) {
             e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\n" + e.getMessage());
            alertaError.showAndWait();
        }

    }
    


    public void actualizarInventarioDetalle(int idInusmo, String lote, double cantidad, int idMovP) {
        try (CallableStatement stm = connection.prepareCall("{call ATUALIZAR_INVENTARIO_DETALLE (?,?,?,?,?)}")) {

            stm.setInt(1, idInusmo);
            stm.setString(2, lote);
            stm.setInt(3, (int) cantidad);
            stm.setInt(4, idMovP);
            stm.setInt(5, VPMedicaPlaza.userSystem);
            stm.execute();

        } catch (SQLException e) {
             e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\n" + e.getMessage());
            alertaError.showAndWait();
        }
    }

    public void RetirarInsumosInventario(MovimientoDetalle movimiento) {
        try (CallableStatement stm = connection.prepareCall("{call RETIRAR_INSUMOS_INVENTARIO (?,?,?,?,?,?)}")) {

            
            stm.setInt(1, movimiento.getId_insumo_mov_padre());
            stm.setInt(2, movimiento.getId_insumo());
            stm.setInt(3, movimiento.getId_inventario_detalle());
            stm.setInt(4, movimiento.getId_tipo_mov_almacen());
            stm.setDouble(5, movimiento.getMovimineto());
            stm.setInt(6, VPMedicaPlaza.userSystem);
            
            stm.execute();

        } catch (SQLException e) {
            
            e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\n" + e.getMessage());
            alertaError.showAndWait();
        }
    }

    public String ActualizarCantidadPorIdInventadioDetalle(int id_inv_det, int cantidad_actualizada) {
        String respuesta;
        try (CallableStatement stm = connection.prepareCall("{call ACTUALIZACION_INVENTARIO_DET_POR_ID (?,?)}")) {

            stm.setInt(1, id_inv_det);
            stm.setInt(2, (int) cantidad_actualizada);

            stm.execute();

            ResultSet rs = stm.getResultSet();
            if (rs.next()) {
                respuesta = rs.getString("respuesta");
            } else {
                respuesta = "tuulo";
            }

        } catch (SQLException e) {
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE\n" + e.getMessage());
            alertaError.showAndWait();
            e.printStackTrace();
            respuesta = e.getMessage();
        }
        return respuesta;
    }



    public void conciliacioninventario(InventarioDetalle inv_det, int id_insumo, int id_mov_p, int usuario_mod) {
        try (CallableStatement stm = connection.prepareCall("{call CONCILIACION_INVENTARIO_POR_LOTE (?,?,?,?,?,?,?)}")) {

            stm.setInt(1, inv_det.getId_inventario());
            stm.setString(2, inv_det.getLote());
            stm.setDate(3, inv_det.getCaducidad());
            stm.setInt(4, inv_det.getCantidad());
            stm.setInt(5, id_insumo);
            stm.setInt(6, id_mov_p);
            stm.setInt(7, usuario_mod);
            stm.execute();

        } catch (SQLException e) {
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ALGO SALIO MAL, FAVOR DE REINTENTAR MAS TARDE");
            alertaError.showAndWait();
            e.printStackTrace();
        }

    }
    
    // MAPEOS DE SELECTS

    public InventarioDetalle mapearInventarioDetalle(ResultSet rs) throws SQLException {
        InventarioDetalle inventarioDetalle = new InventarioDetalle();
        inventarioDetalle.setId_inventario_detalle(rs.getInt("id_inventario_detalle"));
        inventarioDetalle.setId_inventario(rs.getInt("id_inventario"));
        inventarioDetalle.setLote(rs.getString("lote"));
        inventarioDetalle.setCaducidad(rs.getDate("caducidad"));
        inventarioDetalle.setCantidad(rs.getInt("cantidad"));

        return inventarioDetalle;
    }

    public InventarioDetalle mapearInventarioDetalleconestado(ResultSet rs) throws SQLException {
        InventarioDetalle inventarioDetalle = new InventarioDetalle();
        inventarioDetalle.setId_inventario_detalle(rs.getInt("id_inventario_detalle"));
        inventarioDetalle.setId_inventario(rs.getInt("id_inventario"));
        inventarioDetalle.setLote(rs.getString("lote"));
        inventarioDetalle.setCaducidad(rs.getDate("caducidad"));
        inventarioDetalle.setCantidad(rs.getInt("cantidad"));
        inventarioDetalle.setEstado(rs.getString("estado"));
        inventarioDetalle.setFecha_modificacion(rs.getDate("fecha_modificacion"));

        return inventarioDetalle;
    }

}
