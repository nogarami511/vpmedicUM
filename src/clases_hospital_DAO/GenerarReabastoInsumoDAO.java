/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.GenerarReabastoInsumo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import static vpmedicaplaza.VPMedicaPlaza.*;

/**
 *
 * @author alfar
 */
public class GenerarReabastoInsumoDAO {

    private Connection connection;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);

    public GenerarReabastoInsumoDAO(Connection connection) {
        this.connection = connection;
    }
    
    
    public void ActualizarInsumoporIdGenerarRI(GenerarReabastoInsumo gr) throws SQLException{
        
        try(CallableStatement stm = connection.prepareCall("call ACTUALIZAR_GENERAR_REABASTO(?,?,?,?)")){
            stm.setInt(1, gr.getIdGenerarReabastoInsumo());
            stm.setInt(2, gr.getId_proveedor());
            stm.setDouble(3, gr.getTotalUnidadesFaltantes());
            stm.setInt(4, userSystem);
            stm.execute();
            
        }catch(SQLException e){
            e.printStackTrace();
            alertaError.setHeaderText("PEDIDO");
            alertaError.setTitle("ACTUALIZAR PEDIDO");
            alertaError.setContentText("OCURRIO UN ERROR AL ACTUALIZAR PEDIDO ");
            alertaError.setContentText(e.getCause().toString());
                    
            alertaError.showAndWait();
            
        }
    }
    
    
    public List<GenerarReabastoInsumo> ListaReabasto(int id_reabastoP){
        List<GenerarReabastoInsumo> listaReabastoInsumos = new ArrayList<>();
        
        try (CallableStatement stm = connection.prepareCall("{call TRAER_INSUMOS_REABASTO_PARA_PEDIDO(?)}")){
            stm.setInt(1, id_reabastoP);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            
            while(rs.next()){
                listaReabastoInsumos.add(mapearReabastoInsumos(rs));
                
            }
        } catch (Exception e) {
            alertaError.setHeaderText("REABASTO");
            alertaError.setTitle("Generar Reabasto");
            alertaError.setContentText("OCURRIO UN ERROR AL GENERAR REABASTO ");
            alertaError.setContentText(e.getCause().toString());
                    
            alertaError.showAndWait();
        }
        
        
        
        return listaReabastoInsumos;
    }
    
    private GenerarReabastoInsumo mapearReabastoInsumos(ResultSet rs) throws SQLException{
        GenerarReabastoInsumo gr = new GenerarReabastoInsumo();
        
        gr.setIdGenerarReabastoInsumo(rs.getInt("id_generarreabastosinsumo"));
        gr.setNombre(rs.getString("nombre"));
        gr.setPresentacion(rs.getString("presentacion"));
        gr.setId_proveedor(rs.getInt("id_proveedor"));
        gr.setTotalUnidadesFaltantes(rs.getInt("total_unidades_faltantes"));
        gr.setCantidad_unitariaxcaja(rs.getDouble("cantidad_unitariaxcaja"));
        gr.setCostoUnitarioInicial(rs.getDouble("costo_unitario_inicial"));
        gr.setCosto_total_inicial(rs.getDouble("costo_total_inicial"));
        gr.setIdEstatusReabasto(rs.getInt("id_estatus_reabasto"));
        
        
        
        
        
        
        
        return gr;
    }
    
    
    

    public void GenerarReabastoporTipoInsumo() {
        try (CallableStatement stm = connection.prepareCall("{ call REABASTO_POR_TIPO_INSUMO()}")) {
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if (rs.next()) {
                alertaSuccess.setHeaderText("REABASTO");
                alertaSuccess.setTitle("Generar Reabasto");
                alertaSuccess.setContentText("SE GENERO EL REABASTO CORRECTAMENTE \n "
                        + "MEDICO: " + rs.getInt("conteo_medico")
                        + "\nALIMENTOS: " + rs.getInt("conteo_alimento"));
                alertaSuccess.showAndWait();
            }
        } catch (SQLException e) {
                e.printStackTrace();
            alertaError.setHeaderText("REABASTO");
            alertaError.setTitle("Generar Reabasto");
            alertaError.setContentText("OCURRIO UN ERROR AL GENERAR REABASTO ");
            alertaError.setContentText(e.getCause().toString());
                    
            alertaError.showAndWait();
        }

    }

    // Método para crear un nuevo generar reabasto insumo en la base de datos
    public void create(GenerarReabastoInsumo generarReabastoInsumo) throws SQLException {
        String query = "INSERT INTO generarreabastosinsumo (id_rabastos_padre, id_insumo, total_unidades_faltantes, costo_unitario_inicial, costo_total_inicial, pedir, fechaCreacion, id_estatus_reabasto, usuario_creacion, fecha_modificacion, usuario_modificacion, id_estatus, id_proveedor) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?, NOW(), ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, generarReabastoInsumo.getIdRabastosPadre());
            statement.setInt(2, generarReabastoInsumo.getIdInsumo());
            statement.setDouble(3, generarReabastoInsumo.getTotalUnidadesFaltantes());
            statement.setDouble(4, generarReabastoInsumo.getCostoUnitarioInicial());
            statement.setDouble(5, generarReabastoInsumo.getCosto_total_inicial());
            statement.setBoolean(6, generarReabastoInsumo.isPedir());
            statement.setInt(7, generarReabastoInsumo.getIdEstatusReabasto());
            statement.setInt(8, generarReabastoInsumo.getUsuarioCreacion());
            statement.setInt(9, generarReabastoInsumo.getUsuarioModificacion());
            statement.setInt(10, generarReabastoInsumo.getIdEstatus());
            statement.setInt(11, generarReabastoInsumo.getId_proveedor());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generarReabastoInsumo.setIdGenerarReabastoInsumo(generatedKeys.getInt(1));
            }
        }
    }

    // Método para actualizar un generar reabasto insumo existente en la base de datos
    public void update(GenerarReabastoInsumo generarReabastoInsumo) throws SQLException {
        String query = "UPDATE generarreabastosinsumo SET id_rabastos_padre = ?, id_insumo = ?, total_unidades_faltantes = ?, costo_unitario_inicial = ?, pedir = ?, id_estatus_reabasto = ?, usuario_creacion = ?, fecha_modificacion = NOW(), usuario_modificacion = ?, id_estatus = ?, id_proveedor = ?, costo_total_inicial = ? WHERE id_generarreabastosinsumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, generarReabastoInsumo.getIdRabastosPadre());
            statement.setInt(2, generarReabastoInsumo.getIdInsumo());
            statement.setDouble(3, generarReabastoInsumo.getTotalUnidadesFaltantes());
            statement.setDouble(4, generarReabastoInsumo.getCostoUnitarioInicial());
            statement.setBoolean(5, generarReabastoInsumo.isPedir());
            statement.setInt(6, generarReabastoInsumo.getIdEstatusReabasto());
            statement.setInt(7, generarReabastoInsumo.getUsuarioCreacion());
            statement.setInt(8, generarReabastoInsumo.getUsuarioModificacion());
            statement.setInt(9, generarReabastoInsumo.getIdEstatus());
            statement.setInt(10, generarReabastoInsumo.getId_proveedor());
            statement.setDouble(11, generarReabastoInsumo.getCosto_total_inicial());
            statement.setInt(12, generarReabastoInsumo.getIdGenerarReabastoInsumo());

            statement.executeUpdate();
        }
    }

    public void updateSinFecha(GenerarReabastoInsumo generarReabastoInsumo) throws SQLException {
        String query = "UPDATE generarreabastosinsumo SET id_rabastos_padre = ?, id_insumo = ?, total_unidades_faltantes = ?, costo_unitario_inicial = ?, costo_total_inicial = ?, pedir = ?, id_estatus_reabasto = ?, usuario_creacion = ?, fecha_modificacion = NOW(), usuario_modificacion = ?, id_estatus = ?, id_proveedor = ? WHERE id_generarreabastosinsumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, generarReabastoInsumo.getIdRabastosPadre());
            statement.setInt(2, generarReabastoInsumo.getIdInsumo());
            statement.setDouble(3, generarReabastoInsumo.getTotalUnidadesFaltantes());
            statement.setDouble(4, generarReabastoInsumo.getCostoUnitarioInicial());
            statement.setDouble(5, generarReabastoInsumo.getCosto_total_inicial());
            statement.setBoolean(6, generarReabastoInsumo.isPedir());
            statement.setInt(7, generarReabastoInsumo.getIdEstatusReabasto());
            statement.setInt(8, generarReabastoInsumo.getUsuarioCreacion());
            statement.setInt(9, generarReabastoInsumo.getUsuarioModificacion());
            statement.setInt(10, generarReabastoInsumo.getIdEstatus());
            statement.setInt(11, generarReabastoInsumo.getId_proveedor());
            statement.setInt(12, generarReabastoInsumo.getIdGenerarReabastoInsumo());

            statement.executeUpdate();
        }
    }

    // Método para eliminar un generar reabasto insumo de la base de datos
    public void delete(int idGenerarReabastoInsumo) throws SQLException {
        String query = "DELETE FROM generarreabastosinsumo WHERE id_generarreabastosinsumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idGenerarReabastoInsumo);
            statement.executeUpdate();
        }
    }

    // Método para obtener un generar reabasto insumo por su ID
    public GenerarReabastoInsumo getById(int idGenerarReabastoInsumo) throws SQLException {
        String query = "SELECT * FROM generarreabastosinsumo WHERE id_generarreabastosinsumo = ?";
        GenerarReabastoInsumo generarReabastoInsumo = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idGenerarReabastoInsumo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                generarReabastoInsumo = mapResultSetToGenerarReabastoInsumo(resultSet);
            }
        }

        return generarReabastoInsumo;
    }

    // Método para obtener todos los generar reabasto insumo de la base de datos
    public List<GenerarReabastoInsumo> getAll() throws SQLException {
        String query = "SELECT * FROM generarreabastosinsumo";
        List<GenerarReabastoInsumo> generarReabastoInsumos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GenerarReabastoInsumo generarReabastoInsumo = mapResultSetToGenerarReabastoInsumo(resultSet);
                generarReabastoInsumos.add(generarReabastoInsumo);
            }
        }

        return generarReabastoInsumos;
    }

    public List<GenerarReabastoInsumo> getAllName() throws SQLException {
        String query = "SELECT i.nombre, p.presentacion, g.* FROM generarreabastosinsumo g INNER JOIN insumos i ON g.id_insumo = i.id INNER JOIN presentaciones_insumos p ON i.id_presentacion = p.id WHERE g.id_rabastos_padre = 1 AND g.id_estatus_reabasto = 1;";
        List<GenerarReabastoInsumo> generarReabastoInsumos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GenerarReabastoInsumo generarReabastoInsumo = mapResultSetToGenerarReabastoInsumoConNombreyPresentacion(resultSet);
                generarReabastoInsumos.add(generarReabastoInsumo);
            }
        }

        return generarReabastoInsumos;
    }

    public List<GenerarReabastoInsumo> getAllNameAndProv() throws SQLException {
        String query = "SELECT i.nombre, p.presentacion, provedor.nombreComercial, g.* FROM generarreabastosinsumo g INNER JOIN insumos i ON g.id_insumo = i.id INNER JOIN presentaciones_insumos p ON i.id_presentacion = p.id INNER JOIN proveedores provedor ON g.id_proveedor = provedor.id WHERE g.id_rabastos_padre = 1 AND g.id_estatus_reabasto = 1;";
        List<GenerarReabastoInsumo> generarReabastoInsumos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GenerarReabastoInsumo generarReabastoInsumo = mapResultSetToGenerarReabastoInsumoConNombreyPresentacion(resultSet);
                generarReabastoInsumos.add(generarReabastoInsumo);
            }
        }

        return generarReabastoInsumos;
    }

    public List<GenerarReabastoInsumo> getAllNameProvCosto() throws SQLException {
        String query = "SELECT i.id, i.nombre, p.presentacion, provedor.nombreComercial, c.cantidad_unitariaxcaja, c.costo_compra_caja, c.costo_compra_unitaria, g.* FROM generarreabastosinsumo g INNER JOIN insumos i ON g.id_insumo = i.id INNER JOIN presentaciones_insumos p ON i.id_presentacion = p.id INNER JOIN proveedores provedor ON g.id_proveedor = provedor.id INNER JOIN costos c ON g.id_insumo = c.id_insumo WHERE g.id_rabastos_padre = 1 AND g.id_estatus_reabasto = 1";
        List<GenerarReabastoInsumo> generarReabastoInsumos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GenerarReabastoInsumo generarReabastoInsumo = mapResultSetToGenerarReabastoInsumoConNombreyPresentacionCosto(resultSet);
                generarReabastoInsumos.add(generarReabastoInsumo);
            }
        }

        return generarReabastoInsumos;
    }

    public List<GenerarReabastoInsumo> getAllNameAndProvPorIdReabastoPadre(int id_reabasto_padre) throws SQLException {
        String query = "SELECT i.nombre, p.presentacion, provedor.nombreComercial, g.* FROM generarreabastosinsumo g INNER JOIN insumos i ON g.id_insumo = i.id INNER JOIN presentaciones_insumos p ON i.id_presentacion = p.id INNER JOIN proveedores provedor ON g.id_proveedor = provedor.id WHERE g.id_rabastos_padre = ? AND g.id_estatus_reabasto = 1;";
        List<GenerarReabastoInsumo> generarReabastoInsumos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_reabasto_padre);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GenerarReabastoInsumo generarReabastoInsumo = mapResultSetToGenerarReabastoInsumoConNombreyPresentacion(resultSet);
                generarReabastoInsumos.add(generarReabastoInsumo);
            }
        }

        return generarReabastoInsumos;
    }

    public List<GenerarReabastoInsumo> getAllNameAndProvPorIdReabastoPadreConEstatus(int id_reabasto_padre) throws SQLException {
        String query = "SELECT i.nombre, p.presentacion, provedor.nombreComercial, er.nombre AS estatusreabasto, g.* FROM generarreabastosinsumo g INNER JOIN insumos i ON g.id_insumo = i.id INNER JOIN presentaciones_insumos p ON i.id_presentacion = p.id INNER JOIN proveedores provedor ON g.id_proveedor = provedor.id INNER JOIN estatus_reabastos er ON g.id_estatus_reabasto = er.id WHERE g.id_rabastos_padre = ? AND g.id_estatus_reabasto > 1 AND g.id_estatus_reabasto < 4;";
        List<GenerarReabastoInsumo> generarReabastoInsumos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_reabasto_padre);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GenerarReabastoInsumo generarReabastoInsumo = mapResultSetToGenerarReabastoInsumoConNombreyPresentacionConEstatus(resultSet);
                generarReabastoInsumos.add(generarReabastoInsumo);
            }
        }

        return generarReabastoInsumos;
    }

    public boolean existeReabasto(int idInsumo) throws SQLException {
        String query = "SELECT * FROM generarreabastosinsumo WHERE id_insumo = ? AND id_rabastos_padre = 1 AND id_estatus_reabasto = 1;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idInsumo);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    public double getTotalCostoUnitarioInicial() throws SQLException {
        String query = "SELECT SUM(costo_total_inicial) FROM generarreabastosinsumo WHERE id_estatus_reabasto = 1";
        double totalCostoUnitarioInicial = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalCostoUnitarioInicial = resultSet.getDouble(1);
            }
        }

        return totalCostoUnitarioInicial;
    }

    public List<GenerarReabastoInsumo> getAllNameInsumos() throws SQLException {
        String query = "SELECT g.id_generarreabastosinsumo, g.id_insumo, g.total_unidades_faltantes, g.costo_unitario_inicial, g.descuento, g.costo_total_inicial, g.pedir, i.nombre FROM generarreabastosinsumo g INNER JOIN insumos i ON g.id_insumo = i.id WHERE g.id_rabastos_padre = 1 AND g.id_estatus_reabasto = 1 AND g.pedir = 0";
        List<GenerarReabastoInsumo> generarReabastoInsumos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GenerarReabastoInsumo generarReabastoInsumo = mapResultSetToGenerarReabastoNameInsumos(resultSet);
                generarReabastoInsumos.add(generarReabastoInsumo);
            }
        }

        return generarReabastoInsumos;
    }

    // Método para mapear el resultado de la consulta a un objeto GenerarReabastoInsumo
    private GenerarReabastoInsumo mapResultSetToGenerarReabastoInsumo(ResultSet resultSet) throws SQLException {
        GenerarReabastoInsumo generarReabastoInsumo = new GenerarReabastoInsumo();
        generarReabastoInsumo.setIdGenerarReabastoInsumo(resultSet.getInt("id_generarreabastosinsumo"));
        generarReabastoInsumo.setIdRabastosPadre(resultSet.getInt("id_rabastos_padre"));
        generarReabastoInsumo.setIdInsumo(resultSet.getInt("id_insumo"));
        generarReabastoInsumo.setTotalUnidadesFaltantes(resultSet.getDouble("total_unidades_faltantes"));
        generarReabastoInsumo.setCostoUnitarioInicial(resultSet.getDouble("costo_unitario_inicial"));
        generarReabastoInsumo.setCostoUnitarioFinal(resultSet.getDouble("costo_unitario_final"));
        generarReabastoInsumo.setPedir(resultSet.getBoolean("pedir"));
        generarReabastoInsumo.setFechaCreacion(resultSet.getTimestamp("fechaCreacion"));
        generarReabastoInsumo.setIdEstatusReabasto(resultSet.getInt("id_estatus_reabasto"));
        generarReabastoInsumo.setUsuarioCreacion(resultSet.getInt("usuario_creacion"));
        generarReabastoInsumo.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        generarReabastoInsumo.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        generarReabastoInsumo.setIdEstatus(resultSet.getInt("id_estatus"));
        generarReabastoInsumo.setId_proveedor(resultSet.getInt("id_proveedor"));
        generarReabastoInsumo.setCosto_total_inicial(resultSet.getDouble("costo_total_inicial"));
        generarReabastoInsumo.setCosto_total_final(resultSet.getDouble("costo_total_final"));

        return generarReabastoInsumo;
    }

    private GenerarReabastoInsumo mapResultSetToGenerarReabastoInsumoConNombreyPresentacion(ResultSet resultSet) throws SQLException {
        GenerarReabastoInsumo generarReabastoInsumo = new GenerarReabastoInsumo();
        generarReabastoInsumo.setIdGenerarReabastoInsumo(resultSet.getInt("id_generarreabastosinsumo"));
        generarReabastoInsumo.setIdRabastosPadre(resultSet.getInt("id_rabastos_padre"));
        generarReabastoInsumo.setIdInsumo(resultSet.getInt("id_insumo"));
        generarReabastoInsumo.setTotalUnidadesFaltantes(resultSet.getDouble("total_unidades_faltantes"));
        generarReabastoInsumo.setCostoUnitarioInicial(resultSet.getDouble("costo_unitario_inicial"));
        generarReabastoInsumo.setCostoUnitarioFinal(resultSet.getDouble("costo_unitario_final"));
        generarReabastoInsumo.setPedir(resultSet.getBoolean("pedir"));
        generarReabastoInsumo.setFechaCreacion(resultSet.getTimestamp("fechaCreacion"));
        generarReabastoInsumo.setIdEstatusReabasto(resultSet.getInt("id_estatus_reabasto"));
        generarReabastoInsumo.setUsuarioCreacion(resultSet.getInt("usuario_creacion"));
        generarReabastoInsumo.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        generarReabastoInsumo.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        generarReabastoInsumo.setIdEstatus(resultSet.getInt("id_estatus"));
        generarReabastoInsumo.setId_proveedor(resultSet.getInt("id_proveedor"));
        generarReabastoInsumo.setCosto_total_inicial(resultSet.getDouble("costo_total_inicial"));
        generarReabastoInsumo.setCosto_total_final(resultSet.getDouble("costo_total_final"));
        generarReabastoInsumo.setNombre(resultSet.getString("nombre"));
        generarReabastoInsumo.setPresentacion(resultSet.getString("presentacion"));
        generarReabastoInsumo.setNombrePoveedor(resultSet.getString("nombreComercial"));

        return generarReabastoInsumo;
    }

    private GenerarReabastoInsumo mapResultSetToGenerarReabastoInsumoConNombreyPresentacionCosto(ResultSet resultSet) throws SQLException {
        GenerarReabastoInsumo generarReabastoInsumo = new GenerarReabastoInsumo();
        generarReabastoInsumo.setIdGenerarReabastoInsumo(resultSet.getInt("id_generarreabastosinsumo"));
        generarReabastoInsumo.setIdRabastosPadre(resultSet.getInt("id_rabastos_padre"));
        generarReabastoInsumo.setIdInsumo(resultSet.getInt("id_insumo"));
        generarReabastoInsumo.setTotalUnidadesFaltantes(resultSet.getDouble("total_unidades_faltantes"));
        generarReabastoInsumo.setCostoUnitarioInicial(resultSet.getDouble("costo_unitario_inicial"));
        generarReabastoInsumo.setCostoUnitarioFinal(resultSet.getDouble("costo_unitario_final"));
        generarReabastoInsumo.setPedir(resultSet.getBoolean("pedir"));
        generarReabastoInsumo.setFechaCreacion(resultSet.getTimestamp("fechaCreacion"));
        generarReabastoInsumo.setIdEstatusReabasto(resultSet.getInt("id_estatus_reabasto"));
        generarReabastoInsumo.setUsuarioCreacion(resultSet.getInt("usuario_creacion"));
        generarReabastoInsumo.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        generarReabastoInsumo.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        generarReabastoInsumo.setIdEstatus(resultSet.getInt("id_estatus"));
        generarReabastoInsumo.setId_proveedor(resultSet.getInt("id_proveedor"));
        generarReabastoInsumo.setCosto_total_inicial(resultSet.getDouble("costo_total_inicial"));
        generarReabastoInsumo.setCosto_total_final(resultSet.getDouble("costo_total_final"));
        generarReabastoInsumo.setNombre(resultSet.getString("nombre"));
        generarReabastoInsumo.setPresentacion(resultSet.getString("presentacion"));
        generarReabastoInsumo.setNombrePoveedor(resultSet.getString("nombreComercial"));
        generarReabastoInsumo.setCantidad_unitariaxcaja(resultSet.getDouble("cantidad_unitariaxcaja"));
        generarReabastoInsumo.setCosto_compra_caja(resultSet.getDouble("costo_compra_caja"));
        generarReabastoInsumo.setCostoUnitarioFinal(resultSet.getDouble("costo_compra_unitaria"));

        return generarReabastoInsumo;
    }

    private GenerarReabastoInsumo mapResultSetToGenerarReabastoInsumoConNombreyPresentacionConEstatus(ResultSet resultSet) throws SQLException {
        GenerarReabastoInsumo generarReabastoInsumo = new GenerarReabastoInsumo();
        generarReabastoInsumo.setIdGenerarReabastoInsumo(resultSet.getInt("id_generarreabastosinsumo"));
        generarReabastoInsumo.setIdRabastosPadre(resultSet.getInt("id_rabastos_padre"));
        generarReabastoInsumo.setIdInsumo(resultSet.getInt("id_insumo"));
        generarReabastoInsumo.setTotalUnidadesFaltantes(resultSet.getDouble("total_unidades_faltantes"));
        generarReabastoInsumo.setCostoUnitarioInicial(resultSet.getDouble("costo_unitario_inicial"));
        generarReabastoInsumo.setCostoUnitarioFinal(resultSet.getDouble("costo_unitario_final"));
        generarReabastoInsumo.setPedir(resultSet.getBoolean("pedir"));
        generarReabastoInsumo.setFechaCreacion(resultSet.getTimestamp("fechaCreacion"));
        generarReabastoInsumo.setIdEstatusReabasto(resultSet.getInt("id_estatus_reabasto"));
        generarReabastoInsumo.setUsuarioCreacion(resultSet.getInt("usuario_creacion"));
        generarReabastoInsumo.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        generarReabastoInsumo.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        generarReabastoInsumo.setIdEstatus(resultSet.getInt("id_estatus"));
        generarReabastoInsumo.setId_proveedor(resultSet.getInt("id_proveedor"));
        generarReabastoInsumo.setCosto_total_inicial(resultSet.getDouble("costo_total_inicial"));
        generarReabastoInsumo.setCosto_total_final(resultSet.getDouble("costo_total_final"));
        generarReabastoInsumo.setNombre(resultSet.getString("nombre"));
        generarReabastoInsumo.setPresentacion(resultSet.getString("presentacion"));
        generarReabastoInsumo.setNombrePoveedor(resultSet.getString("nombreComercial"));
        generarReabastoInsumo.setEstatusreabasto(resultSet.getString("estatusreabasto"));

        return generarReabastoInsumo;
    }

    //SELECT g.id_insumo, g.total_unidades_faltantes, g.costo_unitario_inicial, g.descuento, g.costo_total_inicial, g.pedir, i.nombre FROM generarreabastosinsumo g INNER JOIN insumos i ON g.id_insumo = i.id
    private GenerarReabastoInsumo mapResultSetToGenerarReabastoNameInsumos(ResultSet resultSet) throws SQLException {
        GenerarReabastoInsumo generarReabastoInsumo = new GenerarReabastoInsumo();
        generarReabastoInsumo.setIdGenerarReabastoInsumo(resultSet.getInt("id_generarreabastosinsumo"));
        generarReabastoInsumo.setIdInsumo(resultSet.getInt("id_insumo"));
        generarReabastoInsumo.setTotalUnidadesFaltantes(resultSet.getDouble("total_unidades_faltantes"));
        generarReabastoInsumo.setCostoUnitarioInicial(resultSet.getDouble("costo_unitario_inicial"));
        generarReabastoInsumo.setDescuento(resultSet.getDouble("descuento"));
        generarReabastoInsumo.setCosto_total_inicial(resultSet.getDouble("costo_total_inicial"));
        generarReabastoInsumo.setPedir(resultSet.getBoolean("pedir"));
        generarReabastoInsumo.setNombre(resultSet.getString("nombre"));
        return generarReabastoInsumo;
    }

}
