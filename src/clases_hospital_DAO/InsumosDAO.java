/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Costo;
import clases_hospital.Insumo;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class InsumosDAO {

    private Connection connection;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);

    public InsumosDAO(Connection connection) {
        this.connection = connection;
    }
    
    
    
    
    public List<Insumo> InsumosConPrecios(){
        List<Insumo> listaInsumos = new ArrayList<>();
        
       try(CallableStatement stm = connection.prepareCall("{call TraerInsumosConCosntos()}")){
           stm.execute();
           ResultSet rs = stm.getResultSet();
           
           while(rs.next()){
               listaInsumos.add(mapearInsumoCostos(rs));
           }
           
           
       }catch(SQLException e){
           e.printStackTrace();
       }
        
        
        
        
        return listaInsumos;
    }
    public List<Insumo> InsumosporIdMacro(int id_macro){
        List<Insumo> listaInsumos = new ArrayList<>();
        
       try(CallableStatement stm = connection.prepareCall("{call STR_VER_INSUMOS_POR_MACRO(?)}")){
           stm.setInt("id_macro", id_macro);
           stm.execute();
           ResultSet rs = stm.getResultSet();
           
           while(rs.next()){
               listaInsumos.add(mapearconMacro(rs));
           }
           
           
       }catch(SQLException e){
           alertaError.setTitle("ERROR EN BASE DE DATOS");
           alertaError.setHeaderText("ERROR N° " + e.getErrorCode());
           alertaError.setContentText(e.getMessage());
           alertaError.showAndWait();
           e.printStackTrace();
       }
        
        
        
        
        return listaInsumos;
    }
    public List<Insumo> InsumosEquipoMedicos(){
        List<Insumo> listaInsumos = new ArrayList<>();
        
       try(CallableStatement stm = connection.prepareCall("{call STR_PROC_traerequiposmedicos()}")){
           stm.execute();
           ResultSet rs = stm.getResultSet();
           
           while(rs.next()){
               listaInsumos.add(mapearInsumoEquipos(rs));
           }
           
           
       }catch(SQLException e){
           e.printStackTrace();
           alertaError.setTitle("ERROR EN BASE DE DATOS");
           alertaError.setHeaderText("ERROR N° " + e.getErrorCode());
           alertaError.setContentText(e.getMessage());
           alertaError.showAndWait();
       }
        
        
        
        
        return listaInsumos;
    }
      private Insumo mapearconMacro(ResultSet rs) throws SQLException{
        Insumo insumo = new Insumo();
        
            insumo.setId(rs.getInt("id"));
            insumo.setNombre(rs.getString("nombre"));
            insumo.setNombreMacro(rs.getString("tipo"));
            
        
        
        return insumo;
    }
    
    private Insumo mapearInsumoCostos(ResultSet rs) throws SQLException{
        Insumo insumo = new Insumo();
        
            insumo.setId(rs.getInt("id_insumo"));
            insumo.setNombre(rs.getString("nombre_insumo"));
            insumo.setCosto_compra_unitaria(rs.getDouble("costo_compra_unitaria"));
            insumo.setCosto_compra_caja(rs.getDouble("costo_compra_caja"));
            insumo.setCantidad_unitariaxcaja(rs.getDouble("cantidad_unitariaxcaja"));
            
        
        
        return insumo;
    }
    private Insumo mapearInsumoEquipos(ResultSet rs) throws SQLException{
        Insumo insumo = new Insumo();
        
            insumo.setId(rs.getInt("id"));
            insumo.setNombre(rs.getString("nombre"));
            
        
        
        return insumo;
    }

    public void insertarInsumos(Insumo insumo, Costo costo) throws SQLException {
        String query = "INSERT INTO insumos (clave, nombre, formula, marca, id_presentacion, maximos, minimos, iva, calve_sat, tipo_insumo, observaciones, id_estatus_insumo, id_usuarioModificacion, fechaModificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        String costoQuery = "INSERT INTO costos (clave, id_insumo, cantidad_unitariaxcaja, costo_compra_caja, costo_compra_unitaria, utilidad, precio_venta_caja, precio_venta_unitaria, observacion, id_usuarioModificacion, fechaModificacion, utilidad_paquete, precio_venta_caja_paquete, precio_venta_unitaria_paquete) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?)";

        try (PreparedStatement insumoStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement costoStatement = connection.prepareStatement(costoQuery)) {
            insumoStatement.setString(1, insumo.getClave());
            insumoStatement.setString(2, insumo.getNombre());
            insumoStatement.setString(3, insumo.getFormula());
            insumoStatement.setString(4, insumo.getMarca());
            insumoStatement.setInt(5, insumo.getIdPresentacion());
            insumoStatement.setDouble(6, insumo.getMaximos());
            insumoStatement.setDouble(7, insumo.getMinimos());
            insumoStatement.setDouble(8, insumo.getIva());
            insumoStatement.setString(9, insumo.getCalve_sat());
            insumoStatement.setInt(10, insumo.getTipoInsumo());
            insumoStatement.setString(11, insumo.getObservaciones());
            insumoStatement.setInt(12, insumo.getIdEstatusInsumo());
            insumoStatement.setInt(13, insumo.getIdUsuarioModificacion());
            insumoStatement.executeUpdate();

            ResultSet generatedKeys = insumoStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idInsumoGenerado = generatedKeys.getInt(1);

                costoStatement.setString(1, costo.getClave());
                costoStatement.setInt(2, idInsumoGenerado);
                costoStatement.setDouble(3, costo.getCantidadUnitariaxCaja());
                costoStatement.setDouble(4, costo.getCostoCompraCaja());
                costoStatement.setDouble(5, costo.getCostoCompraUnitaria());
                costoStatement.setDouble(6, costo.getUtilidad());
                costoStatement.setDouble(7, costo.getPrecioVentaCaja());
                costoStatement.setDouble(8, costo.getPrecioVentaUnitaria());
                costoStatement.setDouble(9, 0);
                costoStatement.setInt(10, costo.getIdUsuarioModificacion());
                costoStatement.setDouble(11, costo.getUtilidadPaquete());
                costoStatement.setDouble(12, costo.getPrecioVentaCajaPaquete());
                costoStatement.setDouble(13, costo.getPrecioVentaUnitariaPaquete());
                costoStatement.executeUpdate();
            } else {
                throw new SQLException("No se pudo obtener el ID generado para el insumo");
            }

        }
    }

    public void actualizarInsumos(Insumo insumo) throws SQLException {
        String query = "UPDATE insumos SET clave = ?, nombre = ?, formula = ?, marca = ?, id_presentacion = ?, maximos = ?, "
                + "minimos = ?, iva = ?, calve_sat = ?, tipo_insumo = ?, observaciones = ?, id_estatus_insumo = ?, "
                + "id_usuarioModificacion = ?, fechaModificacion = NOW() WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, insumo.getClave());
            statement.setString(2, insumo.getNombre());
            statement.setString(3, insumo.getFormula());
            statement.setString(4, insumo.getMarca());
            statement.setInt(5, insumo.getIdPresentacion());
            statement.setDouble(6, insumo.getMaximos());
            statement.setDouble(7, insumo.getMinimos());
            statement.setDouble(8, insumo.getIva());
            statement.setString(9, insumo.getCalve_sat());
            statement.setInt(10, insumo.getTipoInsumo());
            statement.setString(11, insumo.getObservaciones());
            statement.setInt(12, insumo.getIdEstatusInsumo());
            statement.setInt(13, insumo.getIdUsuarioModificacion());
            statement.setInt(14, insumo.getId());

            statement.executeUpdate();
        }
    }
    
    public void actualizarInsumosTipoInusmo(Insumo insumo) throws SQLException {
        String query = "UPDATE insumos i SET i.id_tipo_insumos_medico = ?, i.fechaModificacion = NOW() WHERE i.id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, insumo.getIdTipoInsumoMedicoMacro());
            statement.setInt(2, insumo.getId());

            statement.executeUpdate();
        }
    }

    public void actualizarInsumosKit_consumible(Insumo insumo) throws SQLException {
        String query = "UPDATE insumos SET id_usuarioModificacion = ?, fechaModificacion = NOW(), kit_consumible = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, VPMedicaPlaza.userSystem);
            statement.setBoolean(2, insumo.isKit_consumible());
            statement.setInt(3, insumo.getId());

            statement.executeUpdate();
        }
    }

// POR REFACTORIZAR ESTA.
//    public void eliminar(int id) throws SQLException {
//        String query = "DELETE FROM insumos WHERE id = ?";
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, id);
//            statement.executeUpdate();kit_consumible
//        }
//    }
    public Insumo obtenerInsumosPorId(int id) throws SQLException {
        String query = "SELECT i.*, prei.presentacion FROM insumos i INNER JOIN presentaciones_insumos prei ON prei.id = i.id_presentacion WHERE i.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearInsumo(resultSet);
                }
            }
        }

        return null;
    }

    public Insumo obtenerInsumosPorIdConInformacion(int id) throws SQLException {
        String query = "SELECT i.*, prei.presentacion, c.cantidad_unitariaxcaja, c.costo_compra_caja, c.costo_compra_unitaria, c.utilidad, c.precio_venta_caja, c.precio_venta_unitaria FROM insumos i INNER JOIN presentaciones_insumos prei ON prei.id = i.id_presentacion INNER JOIN costos c ON i.id = c.id_insumo WHERE i.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapeoarInsumosConInformacion(resultSet);
                }
            }
        }

        return null;
    }

    public List<Insumo> obtenerTodosInsumos() throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "SELECT i.*, prei.presentacion FROM insumos i INNER JOIN presentaciones_insumos prei ON prei.id = i.id_presentacion";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Insumo insumo = mapearInsumo(resultSet);
                insumos.add(insumo);
            }
        }

        return insumos;
    }

    public List<Insumo> obtnerTodsInusumosConInformacion() throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "SELECT i.*, prei.presentacion, c.cantidad_unitariaxcaja, c.costo_compra_caja, c.costo_compra_unitaria, c.utilidad, c.precio_venta_caja, c.precio_venta_unitaria FROM insumos i INNER JOIN presentaciones_insumos prei ON prei.id = i.id_presentacion INNER JOIN costos c ON i.id = c.id_insumo";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Insumo insumo = mapeoarInsumosConInformacion(resultSet);
                insumos.add(insumo);
            }
        }

        return insumos;
    }

    public List<Insumo> obtenerTodosInsumosConInformacionPaquete() throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "SELECT i.*, prei.presentacion, c.cantidad_unitariaxcaja, c.costo_compra_caja, c.costo_compra_unitaria, c.utilidad, c.utilidad_paquete, c.precio_venta_caja_paquete, c.precio_venta_unitaria_paquete FROM insumos i INNER JOIN presentaciones_insumos prei ON prei.id = i.id_presentacion INNER JOIN costos c ON i.id = c.id_insumo";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Insumo insumo = mapearInsumosConInformacionPaquete(resultSet);
                insumos.add(insumo);
            }
        }

        return insumos;
    }

    public List<Insumo> optenerDatosEstudios() throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "SELECT i.id, i.nombre, ROUND(c.precio_venta_unitaria, 2) AS precio_venta_unitaria FROM insumos i INNER JOIN costos c ON i.id = c.id_insumo WHERE i.clave LIKE '%VP-EST-%'";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Insumo insumo = mapearInsumosEstudios(resultSet);
                insumos.add(insumo);
            }
        }

        return insumos;
    }

    public List<Insumo> optenerDatosInsumosMedicosConValoresIndispensables() throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "SELECT i.id, i.nombre, c.costo_compra_unitaria FROM insumos i INNER JOIN costos c ON i.id = c.id_insumo WHERE i.tipo_insumo = 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Insumo insumo = mapearInsumosMedicosAlimenticios(resultSet);
                insumos.add(insumo);
            }
        }

        return insumos;
    }

    public List<Insumo> optenerDatosInsumosAlimenticiosConValoresIndispensables() throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "SELECT i.id, i.nombre FROM insumos i WHERE i.tipo_insumo = 2";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Insumo insumo = mapearInsumosMedicosAlimenticios(resultSet);
                insumos.add(insumo);
            }
        }

        return insumos;
    }

    public Insumo optenerCantidadyKit_consumibleporId(int id_insumo) throws SQLException {
        String query = "SELECT i.*, prei.presentacion FROM insumos i INNER JOIN presentaciones_insumos prei ON prei.id = i.id_presentacion WHERE i.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearInsumo(resultSet);
                }
            }
        }

        return null;
    }

    public Insumo optenerDatosInsumos(int idInsumo) throws SQLException {
        String query = "SELECT i.id, i.nombre, c.precio_venta_unitaria FROM insumos i INNER JOIN costos c ON i.id = c.id_insumo WHERE i.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idInsumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearInsumosEstudios(resultSet);
                }
            }
        }

        return null;
    }

    public Insumo optenerDatosInsumosConPrecioPaquete(int idInsumo) throws SQLException {
        String query = "SELECT i.id, i.nombre, c.precio_venta_unitaria, c.precio_venta_unitaria_paquete FROM insumos i INNER JOIN costos c ON i.id = c.id_insumo WHERE i.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idInsumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearInsumosPrecioVentaPaquete(resultSet);
                }
            }
        }

        return null;
    }

    public List<Insumo> optnerDatosInsumo(int idInsumo) throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "{call LISTADOINSUMOSPAQUETEACAMBIAR(?)}";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idInsumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    insumos.add(mapearInsumoDesmostacion(resultSet));
                }
            }
        }

        return insumos;
    }
    
    public List<Insumo> optenerInsumosPorfamilia(int idFamiliaInsumo) throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "SELECT i.id, i.clave, i.nombre, c.precio_venta_unitaria, c.precio_venta_unitaria_paquete FROM insumos i INNER JOIN costos c ON i.id = c.id_insumo WHERE i.id_familia_inusmo = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFamiliaInsumo);

            try (ResultSet resultset = statement.executeQuery()) {
                while (resultset.next()) {
                    insumos.add(mapearInsumoByFamilia(resultset));
                }
            }
        }
        return insumos;
    }

    public List<Insumo> optenerInsumosSinfamilia() throws SQLException {
        List<Insumo> insumos = new ArrayList<>();
        String query = "SELECT i.id, i.clave, i.nombre, c.precio_venta_unitaria, c.precio_venta_unitaria_paquete FROM insumos i INNER JOIN costos c ON i.id = c.id_insumo WHERE i.id_familia_inusmo = 1;";

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                insumos.add(mapearInsumoByFamilia(resultSet));
            }
        }
        return insumos;
    }
    
    public void actualizarFamiliaInaumos(Insumo insumo) throws SQLException {
       
        String query = "UPDATE insumos i SET i.id_familia_inusmo = ?, i.id_usuarioModificacion = ?, i.fechaModificacion = NOW() WHERE i.id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, insumo.getIdFamiliaInsumo());
            statement.setInt(2, VPMedicaPlaza.userSystem);
            statement.setInt(3, insumo.getId());

            statement.executeUpdate();
        }
    }

    private Insumo mapearInsumo(ResultSet resultSet) throws SQLException {
        Insumo insumo = new Insumo();

        insumo.setId(resultSet.getInt("id"));
        insumo.setClave(resultSet.getString("clave"));
        insumo.setNombre(resultSet.getString("nombre"));
        insumo.setFormula(resultSet.getString("formula"));
        insumo.setMarca(resultSet.getString("marca"));
        insumo.setIdPresentacion(resultSet.getInt("id_presentacion"));
        insumo.setMaximos(resultSet.getDouble("maximos"));
        insumo.setMinimos(resultSet.getDouble("minimos"));
        insumo.setIva(resultSet.getDouble("iva"));
        insumo.setCalve_sat(resultSet.getString("calve_sat"));
        insumo.setTipoInsumo(resultSet.getInt("tipo_insumo"));
        insumo.setObservaciones(resultSet.getString("observaciones"));
        insumo.setIdEstatusInsumo(resultSet.getInt("id_estatus_insumo"));
        insumo.setIdUsuarioModificacion(resultSet.getInt("id_usuarioModificacion"));
        insumo.setFechaModificacion(resultSet.getDate("fechaModificacion"));
        insumo.setPresentacion(resultSet.getString("presentacion"));
        insumo.setKit_consumible(resultSet.getBoolean("kit_consumible"));

        return insumo;
    }

    private Insumo mapeoarInsumosConInformacion(ResultSet resultSet) throws SQLException {
        Insumo insumo = new Insumo();

        insumo.setId(resultSet.getInt("id"));
        insumo.setClave(resultSet.getString("clave"));
        insumo.setNombre(resultSet.getString("nombre"));
        insumo.setFormula(resultSet.getString("formula"));
        insumo.setMarca(resultSet.getString("marca"));
        insumo.setIdPresentacion(resultSet.getInt("id_presentacion"));
        insumo.setMaximos(resultSet.getDouble("maximos"));
        insumo.setMinimos(resultSet.getDouble("minimos"));
        insumo.setIva(resultSet.getDouble("iva"));
        insumo.setCalve_sat(resultSet.getString("calve_sat"));
        insumo.setTipoInsumo(resultSet.getInt("tipo_insumo"));
        insumo.setObservaciones(resultSet.getString("observaciones"));
        insumo.setIdEstatusInsumo(resultSet.getInt("id_estatus_insumo"));
        insumo.setIdUsuarioModificacion(resultSet.getInt("id_usuarioModificacion"));
        insumo.setFechaModificacion(resultSet.getDate("fechaModificacion"));
        insumo.setPresentacion(resultSet.getString("presentacion"));
        insumo.setCantidad_unitariaxcaja(resultSet.getDouble("cantidad_unitariaxcaja"));
        insumo.setCosto_compra_caja(resultSet.getDouble("costo_compra_caja"));
        insumo.setCosto_compra_unitaria(resultSet.getDouble("costo_compra_unitaria"));
        insumo.setUtilidad(resultSet.getDouble("utilidad"));
        insumo.setPrecio_venta_caja(resultSet.getDouble("precio_venta_caja"));
        insumo.setPrecio_venta_unitaria(resultSet.getDouble("precio_venta_unitaria"));
        insumo.setKit_consumible(resultSet.getBoolean("kit_consumible"));

        return insumo;
    }

    private Insumo mapearInsumosConInformacionPaquete(ResultSet resultSet) throws SQLException {
        Insumo insumo = new Insumo();

        insumo.setId(resultSet.getInt("id"));
        insumo.setClave(resultSet.getString("clave"));
        insumo.setNombre(resultSet.getString("nombre"));
        insumo.setFormula(resultSet.getString("formula"));
        insumo.setMarca(resultSet.getString("marca"));
        insumo.setIdPresentacion(resultSet.getInt("id_presentacion"));
        insumo.setMaximos(resultSet.getDouble("maximos"));
        insumo.setMinimos(resultSet.getDouble("minimos"));
        insumo.setIva(resultSet.getDouble("iva"));
        insumo.setCalve_sat(resultSet.getString("calve_sat"));
        insumo.setTipoInsumo(resultSet.getInt("tipo_insumo"));
        insumo.setObservaciones(resultSet.getString("observaciones"));
        insumo.setIdEstatusInsumo(resultSet.getInt("id_estatus_insumo"));
        insumo.setIdUsuarioModificacion(resultSet.getInt("id_usuarioModificacion"));
        insumo.setFechaModificacion(resultSet.getDate("fechaModificacion"));
        insumo.setPresentacion(resultSet.getString("presentacion"));
        insumo.setCantidad_unitariaxcaja(resultSet.getDouble("cantidad_unitariaxcaja"));
        insumo.setCosto_compra_caja(resultSet.getDouble("costo_compra_caja"));
        insumo.setCosto_compra_unitaria(resultSet.getDouble("costo_compra_unitaria"));
        insumo.setUtilidadPaquete(resultSet.getDouble("utilidad_paquete"));
        insumo.setPrecioVentaCajaPaquete(resultSet.getDouble("precio_venta_caja_paquete"));
        insumo.setPrecioVentaUnitariaPaquete(resultSet.getDouble("precio_venta_unitaria_paquete"));
        insumo.setKit_consumible(resultSet.getBoolean("kit_consumible"));

        return insumo;
    }

    private Insumo mapearInsumosEstudios(ResultSet resultSet) throws SQLException {
        Insumo insumo = new Insumo();

        insumo.setId(resultSet.getInt("id"));
        insumo.setNombre(resultSet.getString("nombre"));
        insumo.setPrecio_venta_unitaria(resultSet.getDouble("precio_venta_unitaria"));
     
        

        return insumo;
    }

    private Insumo mapearInsumosPrecioVentaPaquete(ResultSet resultSet) throws SQLException {
        Insumo insumo = new Insumo();

        insumo.setId(resultSet.getInt("id"));
        insumo.setNombre(resultSet.getString("nombre"));
        insumo.setPrecio_venta_unitaria(resultSet.getDouble("precio_venta_unitaria"));
        insumo.setPrecioVentaUnitariaPaquete(resultSet.getDouble("precio_venta_unitaria_paquete"));

  

        return insumo;
    }

    private Insumo mapearInsumosMedicosAlimenticios(ResultSet resultSet) throws SQLException {
        Insumo insumo = new Insumo();

        insumo.setId(resultSet.getInt("id"));
        insumo.setNombre(resultSet.getString("nombre"));
        insumo.setCosto_compra_unitaria(resultSet.getDouble("costo_compra_unitaria"));

        return insumo;
    }

    private Insumo mapearInsumoDesmostacion(ResultSet resultSet) throws SQLException {
        Insumo insumo = new Insumo();

        insumo.setNombre(resultSet.getString("nombre"));
        insumo.setCosto_compra_caja(resultSet.getDouble("costo"));
        insumo.setCostoAnteriror(resultSet.getDouble("costoAnterior"));
        insumo.setModificacion(resultSet.getInt("modificado"));

        return insumo;
    }

    private Insumo mapearInsumoByFamilia(ResultSet resultSet) throws SQLException {
        Insumo insumo = new Insumo();
        
        insumo.setId(resultSet.getInt("id"));
        insumo.setClave(resultSet.getString("clave"));
        insumo.setNombre(resultSet.getString("nombre"));
        insumo.setPrecio_venta_unitaria(resultSet.getDouble("precio_venta_unitaria"));
        insumo.setPrecioVentaUnitariaPaquete(resultSet.getDouble("precio_venta_unitaria_paquete"));

        return insumo;
    }

}
