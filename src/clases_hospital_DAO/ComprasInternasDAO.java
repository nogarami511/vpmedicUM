/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Proveedor;
import clases_hospital.ComprasInternas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class ComprasInternasDAO {

    private Connection connection;

    public ComprasInternasDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(ComprasInternas compra) throws SQLException {
        String query = "INSERT INTO compras_internasp (folioPedido, cliente, fecha_pedido, subtotal, descuento, impuesto, total, rubro, estatus_compra, id_solicitudes_pagos, dias_trasncurridos, monto_solicitado, monto_autorizado, usuario_modificacion, fecha_modificacion) VALUES (?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, compra.getFolioPedido());
            statement.setString(2, compra.getCliente());
            statement.setDouble(3, compra.getSubtotal());
            statement.setDouble(4, compra.getDescuento());
            statement.setDouble(5, compra.getImpuesto());
            statement.setDouble(6, compra.getTotal());
            statement.setInt(7, compra.getRubro());
            statement.setInt(8, compra.getEstatusCompra());
            statement.setInt(9, compra.getIdSolicitudesPagos());
            statement.setInt(10, compra.getDiasTranscurridos());
            statement.setDouble(11, compra.getMontoSolicitado());
            statement.setDouble(12, compra.getMontoAutorizado());
            statement.setInt(13, compra.getUsuarioModificacion());

            statement.executeUpdate();
        }
    }

    public int insertarRegresarId(ComprasInternas compra) throws SQLException {
        String query = "INSERT INTO compras_internasp (folioPedido, cliente, fecha_pedido, subtotal, descuento, impuesto, total, rubro, estatus_compra, id_solicitudes_pagos, dias_trasncurridos, monto_solicitado, monto_autorizado, usuario_modificacion, fecha_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, compra.getFolioPedido());
            statement.setString(2, compra.getCliente());
            statement.setDate(3, compra.getFechaPedido());
            statement.setDouble(4, compra.getSubtotal());
            statement.setDouble(5, compra.getDescuento());
            statement.setDouble(6, compra.getImpuesto());
            statement.setDouble(7, compra.getTotal());
            statement.setInt(8, compra.getRubro());
            statement.setInt(9, compra.getEstatusCompra());
            statement.setInt(10, compra.getIdSolicitudesPagos());
            statement.setInt(11, compra.getDiasTranscurridos());
            statement.setDouble(12, compra.getMontoSolicitado());
            statement.setDouble(13, compra.getMontoAutorizado());
            statement.setInt(14, compra.getUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                return 0;
            }
        }
    }

    public int insertarRegresarIdDatosCompletos(ComprasInternas compra) throws SQLException {
        String query = "INSERT INTO compras_internasp (folioPedido, cliente, fecha_pedido, subtotal, descuento, impuesto, total, rubro, estatus_compra, id_solicitudes_pagos, dias_trasncurridos, monto_solicitado, monto_autorizado, usuario_modificacion, fecha_modificacion, solicitar_compra, monto_pagado, saldo_saldo, usuario_solicitud, fecha_solocitud, id_proveedor, comision, total_sin_comicion, id_confirmacion_autorizacion, id_estatus_autorizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, compra.getFolioPedido());
            statement.setString(2, compra.getCliente());
            statement.setDate(3, compra.getFechaPedido());
            statement.setDouble(4, compra.getSubtotal());
            statement.setDouble(5, compra.getDescuento());
            statement.setDouble(6, compra.getImpuesto());
            statement.setDouble(7, compra.getTotal());
            statement.setInt(8, compra.getRubro());
            statement.setInt(9, compra.getEstatusCompra());
            statement.setInt(10, compra.getIdSolicitudesPagos());
            statement.setInt(11, compra.getDiasTranscurridos());
            statement.setDouble(12, compra.getMontoSolicitado());
            statement.setDouble(13, compra.getMontoAutorizado());
            statement.setInt(14, compra.getUsuarioModificacion());
            statement.setBoolean(15, compra.isSolicitar_compra());
            statement.setDouble(16, compra.getMonto_pagado());
            statement.setDouble(17, compra.getSaldo_saldo());
            statement.setInt(18, compra.getUsuario_solicitud());
        
            statement.setInt(19, compra.getId_proveedor());
            statement.setDouble(20, compra.getComision());
            statement.setDouble(21, compra.getTotal_sin_comicion());
            statement.setInt(22, compra.getId_confirmacion_autorizacion());
            statement.setInt(23, compra.getId_estatus_autorizacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                return 0;
            }
        }
    }
    
    public void actualizarEstatusCompra(ComprasInternas compra) throws SQLException {
        String query = "UPDATE compras_internasp SET id_estatus_pagos_compras = ?, fecha_modificacion = NOW() WHERE id_compras_internasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, compra.getEstatusCompra());
            statement.setInt(2, compra.getIdComprasInternasp());

            statement.executeUpdate();
        }
    }

    public void actualizar(ComprasInternas compra) throws SQLException {
        String query = "UPDATE compras_internasp SET folioPedido = ?, cliente = ?, fecha_pedido = ?, subtotal = ?, descuento = ?, impuesto = ?, total = ?, rubro = ?, estatus_compra = ?, id_solicitudes_pagos = ?, dias_trasncurridos = ?, monto_solicitado = ?, monto_autorizado = ?, usuario_modificacion = ?, fecha_modificacion = NOW() WHERE id_compras_internasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, compra.getFolioPedido());
            statement.setString(2, compra.getCliente());
            statement.setDate(3, compra.getFechaPedido());
            statement.setDouble(4, compra.getSubtotal());
            statement.setDouble(5, compra.getDescuento());
            statement.setDouble(6, compra.getImpuesto());
            statement.setDouble(7, compra.getTotal());
            statement.setInt(8, compra.getRubro());
            statement.setInt(9, compra.getEstatusCompra());
            statement.setInt(10, compra.getIdSolicitudesPagos());
            statement.setInt(11, compra.getDiasTranscurridos());
            statement.setDouble(12, compra.getMontoSolicitado());
            statement.setDouble(13, compra.getMontoAutorizado());
            statement.setInt(14, compra.getUsuarioModificacion());
            statement.setInt(15, compra.getIdComprasInternasp());

            statement.executeUpdate();
        }
    }

    public void actualizarDatosCompletos(ComprasInternas compra) throws SQLException {
        String query = "UPDATE compras_internasp SET folioPedido = ?, cliente = ?, fecha_pedido = ?, subtotal = ?, descuento = ?, impuesto = ?, total = ?, rubro = ?, estatus_compra = ?, id_solicitudes_pagos = ?, dias_trasncurridos = ?, monto_solicitado = ?, monto_autorizado = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), solicitar_compra = ?, monto_pagado = ?, saldo_saldo = ?, usuario_solicitud = ?, fecha_solocitud = ?, id_proveedor = ?, comision = ?, total_sin_comicion = ?, id_confirmacion_autorizacion = ?, id_estatus_autorizacion = ? WHERE id_compras_internasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, compra.getFolioPedido());
            statement.setString(2, compra.getCliente());
            statement.setDate(3, compra.getFechaPedido());
            statement.setDouble(4, compra.getSubtotal());
            statement.setDouble(5, compra.getDescuento());
            statement.setDouble(6, compra.getImpuesto());
            statement.setDouble(7, compra.getTotal());
            statement.setInt(8, compra.getRubro());
            statement.setInt(9, compra.getEstatusCompra());
            statement.setInt(10, compra.getIdSolicitudesPagos());
            statement.setInt(11, compra.getDiasTranscurridos());
            statement.setDouble(12, compra.getMontoSolicitado());
            statement.setDouble(13, compra.getMontoAutorizado());
            statement.setInt(14, compra.getUsuarioModificacion());
            statement.setBoolean(15, compra.isSolicitar_compra());
            statement.setDouble(16, compra.getMonto_pagado());
            statement.setDouble(17, compra.getSaldo_saldo());
            statement.setInt(18, compra.getUsuario_solicitud());
            statement.setDate(19, compra.getFecha_solocitud());
            statement.setInt(20, compra.getId_proveedor());
            statement.setDouble(21, compra.getComision());
            statement.setDouble(22, compra.getTotal_sin_comicion());
            statement.setInt(23, compra.getId_confirmacion_autorizacion());
            statement.setInt(24, compra.getId_estatus_autorizacion());
            statement.setInt(25, compra.getIdComprasInternasp());

            statement.executeUpdate();
        }
    }

    public void actualizarDatosCompletosEstatusCompra(ComprasInternas compra) throws SQLException {
        String query = "UPDATE compras_internasp SET folioPedido = ?, cliente = ?, fecha_pedido = ?, subtotal = ?, descuento = ?, impuesto = ?, total = ?, rubro = ?, estatus_compra = ?, id_solicitudes_pagos = ?, dias_trasncurridos = ?, monto_solicitado = ?, monto_autorizado = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), solicitar_compra = ?, monto_pagado = ?, saldo_saldo = ?, usuario_solicitud = ?, fecha_solocitud = ?, id_proveedor = ?, comision = ?, total_sin_comicion = ?, id_confirmacion_autorizacion = ?, id_estatus_autorizacion = ?, id_estatus_pagos_compras = ? WHERE id_compras_internasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, compra.getFolioPedido());
            statement.setString(2, compra.getCliente());
            statement.setDate(3, compra.getFechaPedido());
            statement.setDouble(4, compra.getSubtotal());
            statement.setDouble(5, compra.getDescuento());
            statement.setDouble(6, compra.getImpuesto());
            statement.setDouble(7, compra.getTotal());
            statement.setInt(8, compra.getRubro());
            statement.setInt(9, compra.getEstatusCompra());
            statement.setInt(10, compra.getIdSolicitudesPagos());
            statement.setInt(11, compra.getDiasTranscurridos());
            statement.setDouble(12, compra.getMontoSolicitado());
            statement.setDouble(13, compra.getMontoAutorizado());
            statement.setInt(14, compra.getUsuarioModificacion());
            statement.setBoolean(15, compra.isSolicitar_compra());
            statement.setDouble(16, compra.getMonto_pagado());
            statement.setDouble(17, compra.getSaldo_saldo());
            statement.setInt(18, compra.getUsuario_solicitud());
            statement.setDate(19, compra.getFecha_solocitud());
            statement.setInt(20, compra.getId_proveedor());
            statement.setDouble(21, compra.getComision());
            statement.setDouble(22, compra.getTotal_sin_comicion());
            statement.setInt(23, compra.getId_confirmacion_autorizacion());
            statement.setInt(24, compra.getId_estatus_autorizacion());
            statement.setInt(25, compra.getId_estatus_pagos_compras());
            statement.setInt(26, compra.getIdComprasInternasp());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM compras_internasp WHERE id_compras_internasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public ComprasInternas obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM compras_internasp WHERE id_compras_internasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearCompraDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<ComprasInternas> obtenerTodos() throws SQLException {
        List<ComprasInternas> compras = new ArrayList<>();
        String query = "SELECT * FROM compras_internasp";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ComprasInternas compra = crearCompraDesdeResultSet(resultSet);
                compras.add(compra);
            }
        }

        return compras;
    }

    public List<ComprasInternas> obtenerTodosConNombreRubroYrazonSocial() throws SQLException {
        List<ComprasInternas> compras = new ArrayList<>();
        String query = "SELECT ci.*, r.nombre AS nombre_rubro, p.razonSocial FROM compras_internasp ci INNER JOIN rubros r ON ci.rubro = r.id INNER JOIN proveedores p ON ci.id_proveedor = p.id WHERE id_confirmacion_autorizacion = 1";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ComprasInternas compra = crearCompraDesdeResultSetConNombreRubroYrazonSocial(resultSet);
                compras.add(compra);
            }
        }

        return compras;
    }

    public ComprasInternas obtenerConNombreRubroYrazonSocialPorId(int id) throws SQLException {
        String query = "SELECT ci.*, r.nombre AS nombre_rubro, p.razonSocial FROM compras_internasp ci INNER JOIN rubros r ON ci.rubro = r.id INNER JOIN proveedores p ON ci.id_proveedor = p.id WHERE id_proveedor = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearCompraDesdeResultSetConNombreRubroYrazonSocial(resultSet);
                }
            }
        }

        return null;
    }

    public List<ComprasInternas> obtenerConNombreRubroYrazonSocialPorIdLista(int id) throws SQLException {
        List<ComprasInternas> compras = new ArrayList<>();
        String query = "SELECT ci.*, r.nombre AS nombre_rubro, p.razonSocial FROM compras_internasp ci INNER JOIN rubros r ON ci.rubro = r.id INNER JOIN proveedores p ON ci.id_proveedor = p.id WHERE id_proveedor = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ComprasInternas compra = crearCompraDesdeResultSetConNombreRubroYrazonSocial(resultSet);
                    compras.add(compra);
                }
            }
            return compras;
        }
    }

    public List<ComprasInternas> obtenerTodosConNombreRubroYrazonSocialPorId(int id_confirmacion_autorizacion) throws SQLException {
        List<ComprasInternas> compras = new ArrayList<>();
        String query = "SELECT ci.*, r.nombre AS nombre_rubro, p.razonSocial FROM compras_internasp ci INNER JOIN rubros r ON ci.rubro = r.id INNER JOIN proveedores p ON ci.id_proveedor = p.id WHERE ci.id_confirmacion_autorizacion = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_confirmacion_autorizacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ComprasInternas compra = crearCompraDesdeResultSetConNombreRubroYrazonSocial(resultSet);
                    compras.add(compra);
                }
            }

        }

        return compras;
    }

    public List<ComprasInternas> obtenerTodosConDatosDeOrdenDeComrpraPorPagar() throws SQLException {
        List<ComprasInternas> compras = new ArrayList<>();
        String query = "SELECT ci.*, p.*, epc.estatus_pago_compra, c.id_formaPago, fp.tipo AS forma_pago_nombre FROM compras_internasp ci INNER JOIN confirmacionautorizacion c ON ci.id_confirmacion_autorizacion = c.id_confirmacionAutorizacion INNER JOIN proveedores p ON ci.id_proveedor = p.id INNER JOIN estatus_pagos_compras epc ON ci.id_estatus_pagos_compras = epc.id_estatus_pagos_compras INNER JOIN forma_pagos fp ON c.id_formaPago = fp.id WHERE c.estatus > 1 AND ci.id_estatus_pagos_compras = 0 ORDER BY ci.id_compras_internasp ASC;";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ComprasInternas compra = crearCompraDesdeResultSetConDatosCompras(resultSet);
                compras.add(compra);
            }
        }

        return compras;
    }

    public List<ComprasInternas> obtenerTodosConDatosDeOrdenDeComrpraLiquidada() throws SQLException {
        List<ComprasInternas> compras = new ArrayList<>();
        String query = "SELECT ci.*, p.*, epc.estatus_pago_compra, c.id_formaPago, fp.tipo AS forma_pago_nombre FROM compras_internasp ci INNER JOIN confirmacionautorizacion c ON ci.id_confirmacion_autorizacion = c.id_confirmacionAutorizacion INNER JOIN proveedores p ON ci.id_proveedor = p.id INNER JOIN estatus_pagos_compras epc ON ci.id_estatus_pagos_compras = epc.id_estatus_pagos_compras INNER JOIN forma_pagos fp ON c.id_formaPago = fp.id WHERE c.estatus > 1 AND ci.id_estatus_pagos_compras = 1";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ComprasInternas compra = crearCompraDesdeResultSetConDatosCompras(resultSet);
                compras.add(compra);
            }
        }

        return compras;
    }
    
    public int obtenerIdRubro(int id_confirmacionAutorizacion) throws SQLException {
        int idrubro = 0;
        String query = "SELECT ci.rubro FROM compras_internasp ci WHERE ci.id_confirmacion_autorizacion = ?s";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, query);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    idrubro = resultSet.getInt("rubro");
                }
            }
        }
        return idrubro;
    }
    
    public ComprasInternas obtenerPorIdConfirmacionAutorizacion(int id_confirmacionAutorizacion) throws SQLException {
        String query = "SELECT * FROM compras_internasp WHERE id_confirmacion_autorizacion = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_confirmacionAutorizacion);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    private ComprasInternas crearCompraDesdeResultSet(ResultSet resultSet) throws SQLException {
        ComprasInternas compra = new ComprasInternas();
        compra.setIdComprasInternasp(resultSet.getInt("id_compras_internasp"));
        compra.setFolioPedido(resultSet.getString("folioPedido"));
        compra.setCliente(resultSet.getString("cliente"));
        compra.setFechaPedido(resultSet.getDate("fecha_pedido"));
        compra.setSubtotal(resultSet.getDouble("subtotal"));
        compra.setDescuento(resultSet.getDouble("descuento"));
        compra.setImpuesto(resultSet.getDouble("impuesto"));
        compra.setTotal(resultSet.getDouble("total"));
        compra.setRubro(resultSet.getInt("rubro"));
        compra.setEstatusCompra(resultSet.getInt("estatus_compra"));
        compra.setIdSolicitudesPagos(resultSet.getInt("id_solicitudes_pagos"));
        compra.setDiasTranscurridos(resultSet.getInt("dias_trasncurridos"));
        compra.setMontoSolicitado(resultSet.getDouble("monto_solicitado"));
        compra.setMontoAutorizado(resultSet.getDouble("monto_autorizado"));
        compra.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        compra.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        compra.setSolicitar_compra(resultSet.getBoolean("solicitar_compra"));
        compra.setMonto_pagado(resultSet.getDouble("monto_pagado"));
        compra.setSaldo_saldo(resultSet.getDouble("saldo_saldo"));
        compra.setUsuario_solicitud(resultSet.getInt("usuario_solicitud"));
        compra.setFecha_solocitud(resultSet.getDate("fecha_solocitud"));
        compra.setId_proveedor(resultSet.getInt("id_proveedor"));
        compra.setComision(resultSet.getDouble("comision"));
        compra.setTotal_sin_comicion(resultSet.getDouble("total_sin_comicion"));
        return compra;
    }

    private ComprasInternas crearCompraDesdeResultSetConNombreRubroYrazonSocial(ResultSet resultSet) throws SQLException {
        ComprasInternas compra = new ComprasInternas();
        compra.setIdComprasInternasp(resultSet.getInt("id_compras_internasp"));
        compra.setFolioPedido(resultSet.getString("folioPedido"));
        compra.setCliente(resultSet.getString("cliente"));
        compra.setFechaPedido(resultSet.getDate("fecha_pedido"));
        compra.setSubtotal(resultSet.getDouble("subtotal"));
        compra.setDescuento(resultSet.getDouble("descuento"));
        compra.setImpuesto(resultSet.getDouble("impuesto"));
        compra.setTotal(resultSet.getDouble("total"));
        compra.setRubro(resultSet.getInt("rubro"));
        compra.setEstatusCompra(resultSet.getInt("estatus_compra"));
        compra.setIdSolicitudesPagos(resultSet.getInt("id_solicitudes_pagos"));
        compra.setDiasTranscurridos(resultSet.getInt("dias_trasncurridos"));
        compra.setMontoSolicitado(resultSet.getDouble("monto_solicitado"));
        compra.setMontoAutorizado(resultSet.getDouble("monto_autorizado"));
        compra.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        compra.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        compra.setSolicitar_compra(resultSet.getBoolean("solicitar_compra"));
        compra.setMonto_pagado(resultSet.getDouble("monto_pagado"));
        compra.setSaldo_saldo(resultSet.getDouble("saldo_saldo"));
        compra.setUsuario_solicitud(resultSet.getInt("usuario_solicitud"));
        compra.setFecha_solocitud(resultSet.getDate("fecha_solocitud"));
        compra.setId_proveedor(resultSet.getInt("id_proveedor"));
        compra.setComision(resultSet.getDouble("comision"));
        compra.setTotal_sin_comicion(resultSet.getDouble("total_sin_comicion"));
        compra.setId_confirmacion_autorizacion(resultSet.getInt("id_confirmacion_autorizacion"));
        compra.setId_estatus_autorizacion(resultSet.getInt("id_estatus_autorizacion"));
        compra.setNombre_rubro(resultSet.getString("nombre_rubro"));
        compra.setRazonSocial(resultSet.getString("razonSocial"));

        return compra;
    }

    private ComprasInternas crearCompraDesdeResultSetConDatosCompras(ResultSet resultSet) throws SQLException {
        ComprasInternas compra = new ComprasInternas();
        Proveedor provedor = new Proveedor();

        compra.setIdComprasInternasp(resultSet.getInt("id_compras_internasp"));
        compra.setFolioPedido(resultSet.getString("folioPedido"));
        compra.setCliente(resultSet.getString("cliente"));
        compra.setFechaPedido(resultSet.getDate("fecha_pedido"));
        compra.setSubtotal(resultSet.getDouble("subtotal"));
        compra.setDescuento(resultSet.getDouble("descuento"));
        compra.setImpuesto(resultSet.getDouble("impuesto"));
        compra.setTotal(resultSet.getDouble("total"));
        compra.setRubro(resultSet.getInt("rubro"));
        compra.setEstatusCompra(resultSet.getInt("estatus_compra"));
        compra.setIdSolicitudesPagos(resultSet.getInt("id_solicitudes_pagos"));
        compra.setDiasTranscurridos(resultSet.getInt("dias_trasncurridos"));
        compra.setMontoSolicitado(resultSet.getDouble("monto_solicitado"));
        compra.setMontoAutorizado(resultSet.getDouble("monto_autorizado"));
        compra.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        compra.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        compra.setSolicitar_compra(resultSet.getBoolean("solicitar_compra"));
        compra.setMonto_pagado(resultSet.getDouble("monto_pagado"));
        compra.setSaldo_saldo(resultSet.getDouble("saldo_saldo"));
        compra.setUsuario_solicitud(resultSet.getInt("usuario_solicitud"));
        compra.setFecha_solocitud(resultSet.getDate("fecha_solocitud"));
        compra.setId_proveedor(resultSet.getInt("id_proveedor"));
        compra.setComision(resultSet.getDouble("comision"));
        compra.setTotal_sin_comicion(resultSet.getDouble("total_sin_comicion"));
        compra.setId_confirmacion_autorizacion(resultSet.getInt("id_confirmacion_autorizacion"));
        compra.setId_estatus_autorizacion(resultSet.getInt("id_estatus_autorizacion"));
        compra.setId_estatus_pagos_compras(resultSet.getInt("id_estatus_pagos_compras"));
        provedor.setId(resultSet.getInt("id"));
        provedor.setNombreComercial(resultSet.getString("nombreComercial"));
        compra.setNombreComercial(resultSet.getString("nombreComercial"));
        provedor.setDireccion(resultSet.getString("direccion"));
        provedor.setRazonSocial(resultSet.getString("razonSocial"));
        provedor.setTelefono(resultSet.getString("telefono"));
        provedor.setRfc(resultSet.getString("rfc"));
        provedor.setId_tipo_proveedor(resultSet.getInt("id_tipo_proveedor"));
        compra.setProveedor(provedor);
        compra.setEstatus_pago_compra(resultSet.getString("estatus_pago_compra"));
        compra.setId_formaPago(resultSet.getInt("id_formaPago"));
        compra.setForma_pago_nombre(resultSet.getString("forma_pago_nombre"));

        return compra;
    }
    
     private ComprasInternas mapearDesdeResultSet(ResultSet resultSet) throws SQLException {
        ComprasInternas compra = new ComprasInternas();

        compra.setIdComprasInternasp(resultSet.getInt("id_compras_internasp"));
        compra.setFolioPedido(resultSet.getString("folioPedido"));
        compra.setCliente(resultSet.getString("cliente"));
        compra.setFechaPedido(resultSet.getDate("fecha_pedido"));
        compra.setSubtotal(resultSet.getDouble("subtotal"));
        compra.setDescuento(resultSet.getDouble("descuento"));
        compra.setImpuesto(resultSet.getDouble("impuesto"));
        compra.setTotal(resultSet.getDouble("total"));
        compra.setRubro(resultSet.getInt("rubro"));
        compra.setEstatusCompra(resultSet.getInt("estatus_compra"));
        compra.setIdSolicitudesPagos(resultSet.getInt("id_solicitudes_pagos"));
        compra.setDiasTranscurridos(resultSet.getInt("dias_trasncurridos"));
        compra.setMontoSolicitado(resultSet.getDouble("monto_solicitado"));
        compra.setMontoAutorizado(resultSet.getDouble("monto_autorizado"));
        compra.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        compra.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        compra.setSolicitar_compra(resultSet.getBoolean("solicitar_compra"));
        compra.setMonto_pagado(resultSet.getDouble("monto_pagado"));
        compra.setSaldo_saldo(resultSet.getDouble("saldo_saldo"));
        compra.setUsuario_solicitud(resultSet.getInt("usuario_solicitud"));
        compra.setFecha_solocitud(resultSet.getDate("fecha_solocitud"));
        compra.setId_proveedor(resultSet.getInt("id_proveedor"));
        compra.setComision(resultSet.getDouble("comision"));
        compra.setTotal_sin_comicion(resultSet.getDouble("total_sin_comicion"));
        compra.setId_confirmacion_autorizacion(resultSet.getInt("id_confirmacion_autorizacion"));
        compra.setId_estatus_autorizacion(resultSet.getInt("id_estatus_autorizacion"));
        compra.setId_estatus_pagos_compras(resultSet.getInt("id_estatus_pagos_compras"));

        return compra;
    }
}
