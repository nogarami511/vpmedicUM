/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ReabastoPadre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class ReabastoPadreDAO {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public ReabastoPadreDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void CambiarStatusReabastoP(int id_reabastop){
        
        try(CallableStatement stm = connection.prepareCall("{call CAMBIAR_ESTATUS_REABASTOP_SEGUN_PEDIDOS(?)}")){
            stm.setInt(1, id_reabastop);
            stm.execute();
            
        }   catch(SQLException e){
            e.printStackTrace();
        }     
    }
    public void GenerarPedidopoProveedor(int id_reabastop, int proveedor){
        
        try(CallableStatement stm = connection.prepareCall("{call CREAR_PEDIDO_COMPRA(?,?,?)}")){
            stm.setInt(1, id_reabastop);
            stm.setInt(2, proveedor);
            stm.setInt(3, VPMedicaPlaza.userSystem);
            stm.execute();
            System.out.println("finalizo el pedido");
        }   catch(SQLException e){
            e.printStackTrace();
        }     
    }
    
    
    
    public List<ReabastoPadre> ultimos2ReabastosCreados(){
        List<ReabastoPadre> listaReabastoP =  new ArrayList<>();
        try (CallableStatement stm = connection.prepareCall("{call TRAER_ULTIMOS_REABASTOS()}")){
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                listaReabastoP.add(mapResultSetToReabastoPadre(rs));
                
                System.out.println(rs.getString("folio_reabasto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        
        return  listaReabastoP;
    }

    // Método para insertar un nuevo reabasto padre en la base de datos
    public void insert(ReabastoPadre reabastoPadre) throws SQLException {
        String query = "INSERT INTO reabastos_padre (folio_reabasto, id_proveedor, costo_total_inicial, costo_total_final, fecha_generado, fecha_rabasto, usuario_generado, usuario_reabasto, fecha_modificacion, usuario_modificacion, monto_pagado, saldo_saldar, forma_pago, usuario_pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0.0, 0, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, reabastoPadre.getFolioReabasto());
            statement.setInt(2, reabastoPadre.getIdProveedor());
            statement.setDouble(3, reabastoPadre.getCostoTotalInicial());
            statement.setDouble(4, reabastoPadre.getCostoTotalFinal());
            statement.setTimestamp(5, new Timestamp(reabastoPadre.getFechaGenerado().getTime()));
            statement.setTimestamp(6, new Timestamp(reabastoPadre.getFechaReabasto().getTime()));
            statement.setInt(7, reabastoPadre.getUsuarioGenerado());
            statement.setInt(8, reabastoPadre.getUsuarioReabasto());
            statement.setTimestamp(9, new Timestamp(reabastoPadre.getFechaModificacion().getTime()));
            statement.setInt(10, reabastoPadre.getUsuarioModificacion());
            statement.setDouble(11, reabastoPadre.getCostoTotalInicial());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                reabastoPadre.setIdRabastosPadre(generatedKeys.getInt(1));
            }
        }
    }

    // Método para insertar un nuevo reabasto padre en la base de datos
    public int insertRegresodeID(ReabastoPadre reabastoPadre) throws SQLException {
        int id;
        String query = "INSERT INTO reabastos_padre (folio_reabasto, id_proveedor, costo_total_inicial, costo_total_final, fecha_generado, fecha_rabasto, usuario_generado, usuario_reabasto, fecha_modificacion, usuario_modificacion, estatu_reabasto, monto_pagado, saldo_saldar, forma_pago, usuario_pago) VALUES (?, ?, ?, ?, NOW(), NOW(), ?, ?, NOW(), ?, ?, 0.0, ?, 0, 0)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, reabastoPadre.getFolioReabasto());
            statement.setInt(2, reabastoPadre.getIdProveedor());
            statement.setDouble(3, reabastoPadre.getCostoTotalInicial());
            statement.setDouble(4, reabastoPadre.getCostoTotalFinal());
            statement.setInt(5, reabastoPadre.getUsuarioGenerado());
            statement.setInt(6, reabastoPadre.getUsuarioReabasto());
            statement.setInt(7, reabastoPadre.getUsuarioModificacion());
            statement.setInt(8, reabastoPadre.getEstatu_reabasto());
            statement.setDouble(9, reabastoPadre.getCostoTotalInicial());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            } else {
                id = 0;
            }
        }
        
        return id;
    }

    // Método para actualizar un reabasto padre existente en la base de datos
    public void update(ReabastoPadre reabastoPadre) throws SQLException {
       
        String query = "UPDATE reabastos_padre SET folio_reabasto = ?, id_proveedor = ?, costo_total_inicial = ?, costo_total_final = ?, fecha_generado = ?, fecha_rabasto = ?, usuario_generado = ?, usuario_reabasto = ?, fecha_modificacion = ?, usuario_modificacion = ?, estatu_reabasto = ? WHERE id_rabastos_padre = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, reabastoPadre.getFolioReabasto());
            statement.setInt(2, reabastoPadre.getIdProveedor());
            statement.setDouble(3, reabastoPadre.getCostoTotalInicial());
            statement.setDouble(4, reabastoPadre.getCostoTotalFinal());
            statement.setTimestamp(5, new Timestamp(reabastoPadre.getFechaGenerado().getTime()));
            statement.setTimestamp(6, new Timestamp(reabastoPadre.getFechaReabasto().getTime()));
            statement.setInt(7, reabastoPadre.getUsuarioGenerado());
            statement.setInt(8, reabastoPadre.getUsuarioReabasto());
            statement.setTimestamp(9, new Timestamp(reabastoPadre.getFechaModificacion().getTime()));
            statement.setInt(10, reabastoPadre.getUsuarioModificacion());
            statement.setInt(11, reabastoPadre.getEstatu_reabasto());
            statement.setInt(12, reabastoPadre.getIdRabastosPadre());

            statement.executeUpdate();
        }
    }

    public void updateDatosPagos(ReabastoPadre reabastoPadre) throws SQLException {
        String query = "UPDATE reabastos_padre SET monto_pagado = ?, saldo_saldar = ?, forma_pago = ?, usuario_pago = ?, fecha_pago = NOW(), estatus_pago = ? WHERE id_rabastos_padre = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, reabastoPadre.getMonto_pagado());
            statement.setDouble(2, reabastoPadre.getSaldo_saldar());
            statement.setInt(3, reabastoPadre.getForma_pago());
            statement.setInt(4, reabastoPadre.getUsuario_pago());
            statement.setInt(5, reabastoPadre.getEstatus_pago());
            statement.setInt(6, reabastoPadre.getIdRabastosPadre());

            statement.executeUpdate();
        }
    }

    // Método para eliminar un reabasto padre de la base de datos
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM reabastos_padre WHERE id_rabastos_padre = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Método para obtener un reabasto padre por su ID
    public ReabastoPadre getById(int id) throws SQLException {
        String query = "SELECT * FROM reabastos_padre WHERE id_rabastos_padre = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToReabastoPadre(resultSet);
            }
        }

        return null; // Si no se encontró ningún reabasto padre con el ID dado
    }

    // Método para obtener todos los reabastos padre de la base de datos
    public List<ReabastoPadre> getAll() throws SQLException {
        List<ReabastoPadre> reabastosPadre = new ArrayList<>();
        String query = "SELECT * FROM reabastos_padre";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReabastoPadre reabastoPadre = mapResultSetToReabastoPadre(resultSet);
                reabastosPadre.add(reabastoPadre);
            }
        }

        return reabastosPadre;
    }

    public List<ReabastoPadre> getPedidos() throws SQLException {
        List<ReabastoPadre> reabastosPadre = new ArrayList<>();
        String query = "SELECT rp.*, er.nombre FROM reabastos_padre rp INNER JOIN estatus_reabastos er ON rp.estatu_reabasto = er.id WHERE rp.estatu_reabasto = 1";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReabastoPadre reabastoPadre = mapResultSetToReabastoPadreConNombre(resultSet);
                reabastosPadre.add(reabastoPadre);
            }
        }

        return reabastosPadre;
    }

    public List<ReabastoPadre> obtenerConNombreRubroYrazonSocialPorIdLista() throws SQLException {
        List<ReabastoPadre> reabastosPadre = new ArrayList<>();
        String query = "SELECT rp.id_rabastos_padre, rp.folio_reabasto, p.razonSocial, rp.fecha_generado, rp.costo_total_inicial, rp.monto_pagado, rp.saldo_saldar, rp.monto_solicitado, rp.monto_autrrizado, rp.estatus_pedido FROM reabastos_padre rp INNER JOIN proveedores p ON rp.id_proveedor = p.id WHERE rp.id_rabastos_padre > 1 AND  rp.estatus_pedido = 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReabastoPadre reabastoPadre = crearCompraDesdeResultSetConNombreRubroYrazonSocial(resultSet);
                reabastosPadre.add(reabastoPadre);
            }
        }

        return reabastosPadre;
    }

    public List<ReabastoPadre> getReabastoPorPagar() throws SQLException {
        List<ReabastoPadre> reabastosPadre = new ArrayList<>();
        String query = "SELECT ROUND(rp.costo_total_inicial, 2) AS costo_total_inicial, rp.monto_pagado, rp.saldo_saldar, rp.forma_pago, rp.usuario_pago, rp.fecha_pago, rp.estatus_pago, p.razonSocial FROM reabastos_padre rp INNER JOIN proveedores p ON rp.id_proveedor = p.id WHERE rp.estatus_pago = 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReabastoPadre reabastoPadre = mapResultSetToSpecificFields(resultSet);
                reabastosPadre.add(reabastoPadre);
            }
        }

        return reabastosPadre;
    }

    public List<ReabastoPadre> getReabastoLiquidado() throws SQLException {
        List<ReabastoPadre> reabastosPadre = new ArrayList<>();
        String query = "SELECT ROUND(rp.costo_total_inicial, 2) AS costo_total_inicial, rp.monto_pagado, rp.saldo_saldar, rp.forma_pago, rp.usuario_pago, rp.fecha_pago, rp.estatus_pago, p.razonSocial FROM reabastos_padre rp INNER JOIN proveedores p ON rp.id_proveedor = p.id WHERE rp.estatus_pago = 1";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ReabastoPadre reabastoPadre = mapResultSetToSpecificFields(resultSet);
                reabastosPadre.add(reabastoPadre);
            }
        }

        return reabastosPadre;
    }

    // Método privado para mapear el resultado de la consulta a un objeto ReabastoPadre
    private ReabastoPadre mapResultSetToReabastoPadre(ResultSet resultSet) throws SQLException {
        ReabastoPadre reabastoPadre = new ReabastoPadre();
        reabastoPadre.setIdRabastosPadre(resultSet.getInt("id_rabastos_padre"));
        reabastoPadre.setFolioReabasto(resultSet.getString("folio_reabasto"));
        reabastoPadre.setIdProveedor(resultSet.getInt("id_proveedor"));
        reabastoPadre.setCostoTotalInicial(resultSet.getDouble("costo_total_inicial"));
        reabastoPadre.setCostoTotalFinal(resultSet.getDouble("costo_total_final"));
        reabastoPadre.setFechaGenerado(resultSet.getTimestamp("fecha_generado"));
        reabastoPadre.setFechaReabasto(resultSet.getTimestamp("fecha_rabasto"));
        reabastoPadre.setUsuarioGenerado(resultSet.getInt("usuario_generado"));
        reabastoPadre.setUsuarioReabasto(resultSet.getInt("usuario_reabasto"));
        reabastoPadre.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        reabastoPadre.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        reabastoPadre.setEstatu_reabasto(resultSet.getInt("estatu_reabasto"));

        return reabastoPadre;
    }

    private ReabastoPadre mapResultSetToReabastoPadreConNombre(ResultSet resultSet) throws SQLException {
        ReabastoPadre reabastoPadre = new ReabastoPadre();
        reabastoPadre.setIdRabastosPadre(resultSet.getInt("id_rabastos_padre"));
        reabastoPadre.setFolioReabasto(resultSet.getString("folio_reabasto"));
        reabastoPadre.setIdProveedor(resultSet.getInt("id_proveedor"));
        reabastoPadre.setCostoTotalInicial(resultSet.getDouble("costo_total_inicial"));
        reabastoPadre.setCostoTotalFinal(resultSet.getDouble("costo_total_final"));
        reabastoPadre.setFechaGenerado(resultSet.getTimestamp("fecha_generado"));
        reabastoPadre.setFechaReabasto(resultSet.getTimestamp("fecha_rabasto"));
        reabastoPadre.setUsuarioGenerado(resultSet.getInt("usuario_generado"));
        reabastoPadre.setUsuarioReabasto(resultSet.getInt("usuario_reabasto"));
        reabastoPadre.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        reabastoPadre.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        reabastoPadre.setEstatu_reabasto(resultSet.getInt("estatu_reabasto"));
        reabastoPadre.setNombre(resultSet.getString("nombre"));
        return reabastoPadre;
    }

    private ReabastoPadre mapResultSetToSpecificFields(ResultSet resultSet) throws SQLException {
        ReabastoPadre reabastoPadre = new ReabastoPadre();
        reabastoPadre.setCostoTotalInicial(resultSet.getDouble("costo_total_inicial"));
        reabastoPadre.setMonto_pagado(resultSet.getDouble("monto_pagado"));
        reabastoPadre.setSaldo_saldar(resultSet.getDouble("saldo_saldar"));
        reabastoPadre.setForma_pago(resultSet.getInt("forma_pago"));
        reabastoPadre.setUsuario_pago(resultSet.getInt("usuario_pago"));
        reabastoPadre.setFecha_pago(resultSet.getDate("fecha_pago"));
        reabastoPadre.setEstatus_pago(resultSet.getInt("estatus_pago"));
        reabastoPadre.setNombre(resultSet.getString("razonSocial"));
        return reabastoPadre;
    }

    private ReabastoPadre crearCompraDesdeResultSetConNombreRubroYrazonSocial(ResultSet resultSet) throws SQLException {
        ReabastoPadre reabastoPadre = new ReabastoPadre();
        reabastoPadre.setIdRabastosPadre(resultSet.getInt("id_rabastos_padre"));
        reabastoPadre.setFolioReabasto(resultSet.getString("folio_reabasto"));
        reabastoPadre.setNombre(resultSet.getString("razonSocial"));
        reabastoPadre.setFechaGenerado(resultSet.getTimestamp("fecha_generado"));
        reabastoPadre.setCostoTotalInicial(resultSet.getDouble("costo_total_inicial"));
        reabastoPadre.setMonto_pagado(resultSet.getDouble("monto_pagado"));
        reabastoPadre.setSaldo_saldar(resultSet.getDouble("saldo_saldar"));
        reabastoPadre.setMontoSolicitado(resultSet.getDouble("monto_solicitado"));
        reabastoPadre.setMontoAutorizado(resultSet.getDouble("monto_autrrizado"));
        reabastoPadre.setPedir(resultSet.getBoolean("estatus_pedido"));
        return reabastoPadre;
    }

}
