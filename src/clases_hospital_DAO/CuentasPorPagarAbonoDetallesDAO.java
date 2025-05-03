/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Proveedor;
import clases_hospital.CuentasPorPagarAbonoDetalle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class CuentasPorPagarAbonoDetallesDAO {
    private Connection connection;

    public CuentasPorPagarAbonoDetallesDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(CuentasPorPagarAbonoDetalle detalle) throws SQLException {
        String query = "INSERT INTO cuentas_por_pagar_abono_detalles (id_cuena_por_pagar, id_autorizacion_pagos, id_forma_pago, total_abonar, abono_abono, saldo_abono, usuario_pago, usuario_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, detalle.getIdCuentaPorPagar());
            statement.setInt(2, detalle.getIdAutorizacionPagos());
            statement.setInt(3, detalle.getIdFormaPago());
            statement.setDouble(4, detalle.getTotalAbonar());
            statement.setDouble(5, detalle.getAbonoAbono());
            statement.setDouble(6, detalle.getSaldoAbono());
            statement.setInt(7, detalle.getUsuarioPago());
            statement.setInt(8, detalle.getUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                detalle.setIdCuentaPorPagarAbonoDetalle(generatedKeys.getInt(1));
            }
        }
    }

    public void update(CuentasPorPagarAbonoDetalle detalle) throws SQLException {
        String query = "UPDATE cuentas_por_pagar_abono_detalles SET id_cuena_por_pagar = ?, id_autorizacion_pagos = ?, id_forma_pago = ?, total_abonar = ?, abono_abono = ?, saldo_abono = ?, usuario_pago = ?, fecha_modificacion = NOW(), usuario_modificacion = ? WHERE id_cuenta_por_pagar_abono_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, detalle.getIdCuentaPorPagar());
            statement.setInt(2, detalle.getIdAutorizacionPagos());
            statement.setInt(3, detalle.getIdFormaPago());
            statement.setDouble(4, detalle.getTotalAbonar());
            statement.setDouble(5, detalle.getAbonoAbono());
            statement.setDouble(6, detalle.getSaldoAbono());
            statement.setInt(7, detalle.getUsuarioPago());
            statement.setInt(8, detalle.getUsuarioModificacion());
            statement.setInt(9, detalle.getIdCuentaPorPagarAbonoDetalle());

            statement.executeUpdate();
        }
    }
    
    public void updateEstatusPago(CuentasPorPagarAbonoDetalle detalle) throws SQLException {
        String query = "UPDATE cuentas_por_pagar_abono_detalles SET id_cuena_por_pagar = ?, id_autorizacion_pagos = ?, id_forma_pago = ?, total_abonar = ?, abono_abono = ?, saldo_abono = ?, usuario_pago = ?, fecha_modificacion = NOW(), usuario_modificacion = ?, estatus_pago = ? WHERE id_cuenta_por_pagar_abono_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, detalle.getIdCuentaPorPagar());
            statement.setInt(2, detalle.getIdAutorizacionPagos());
            statement.setInt(3, detalle.getIdFormaPago());
            statement.setDouble(4, detalle.getTotalAbonar());
            statement.setDouble(5, detalle.getAbonoAbono());
            statement.setDouble(6, detalle.getSaldoAbono());
            statement.setInt(7, detalle.getUsuarioPago());
            statement.setInt(8, detalle.getUsuarioModificacion());
            statement.setBoolean(9, detalle.isEstatus_pago());
            statement.setInt(10, detalle.getIdCuentaPorPagarAbonoDetalle());

            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM cuentas_por_pagar_abono_detalles WHERE id_cuenta_por_pagar_abono_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public CuentasPorPagarAbonoDetalle getById(int id) throws SQLException {
        String query = "SELECT * FROM cuentas_por_pagar_abono_detalles WHERE id_cuenta_por_pagar_abono_detalle = ?";
        CuentasPorPagarAbonoDetalle detalle = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                detalle = mapResultSetToDetalle(resultSet);
            }
        }

        return detalle;
    }
    
    public CuentasPorPagarAbonoDetalle getByIdAutorizacionConfirmacion(int id) throws SQLException {
        String query = "SELECT ci.*, 'INSUMO' AS nombre_rubro, 'ADEUDO' AS razonSocial FROM cuentas_por_pagar_abono_detalles ci WHERE ci.id_autorizacion_pagos = ?";
        CuentasPorPagarAbonoDetalle detalle = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                detalle = mapResultSetToDetalleIdAbonoDetalle(resultSet);
            }
        }

        return detalle;
    }
    
     public CuentasPorPagarAbonoDetalle obtenerPorIdConfirmacionAutorizacion(int id_confirmacionAutorizacion) throws SQLException {
        String query = "SELECT * FROM cuentas_por_pagar_abono_detalles WHERE id_autorizacion_pagos = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_confirmacionAutorizacion);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToDetalle(resultSet);
                }
            }
        }

        return null;
    }

    public List<CuentasPorPagarAbonoDetalle> getAll() throws SQLException {
        String query = "SELECT * FROM cuentas_por_pagar_abono_detalles";
        List<CuentasPorPagarAbonoDetalle> detalles = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CuentasPorPagarAbonoDetalle detalle = mapResultSetToDetalle(resultSet);
                detalles.add(detalle);
            }
        }

        return detalles;
    }
    
    public List<CuentasPorPagarAbonoDetalle> obtenerCPPInsumoPorLiquidar() throws SQLException {
        String query = "SELECT " +
                       "    cppad.*," +
                       "    p.*," +
                       "    fp.id formapagoint," +
                       "    fp.tipo AS formapagotipo," +
                       "    CASE " +
                       "      WHEN cppad.abono_abono = 0 THEN 'NO PAGADO'" +
                       "      WHEN cppad.abono_abono > 0 AND cppad.abono_abono < cppad.total_abonar THEN 'PAGO PARCIAL'" +
                       "      ELSE 'PAGADO'" +
                       "    END AS estatuspagonombre" +
                       "  FROM cuentas_por_pagar_abono_detalles cppad" +
                       "    INNER JOIN confirmacionautorizacion c ON cppad.id_autorizacion_pagos = c.id_confirmacionAutorizacion" +
                       "    INNER JOIN cuentas_por_pagar cpp ON cppad.id_cuena_por_pagar = cpp.id_cuentas_por_pagar" +
                       "    INNER JOIN compra_insumosp ci ON cpp.id_compra = ci.id_compra_insumosp" +
                       "    INNER JOIN proveedores p ON ci.id_proveedor = p.id" +
                       "    INNER JOIN forma_pagos fp ON cppad.id_forma_pago = fp.id" +
                       "  WHERE cppad.estatus_pago = 0;";
        List<CuentasPorPagarAbonoDetalle> detalles = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CuentasPorPagarAbonoDetalle detalle = mapResultSetToDetalleConProveedor(resultSet);
                detalles.add(detalle);
            }
        }

        return detalles;
    }
    
    public List<CuentasPorPagarAbonoDetalle> obtenerCPPInsumoLiquidadas() throws SQLException {
        String query = "SELECT " +
                       "    cppad.*," +
                       "    p.*," +
                       "    fp.id formapagoint," +
                       "    fp.tipo AS formapagotipo," +
                       "    'PAGADO' AS estatuspagonombre" +
                       "  FROM cuentas_por_pagar_abono_detalles cppad" +
                       "    INNER JOIN confirmacionautorizacion c ON cppad.id_autorizacion_pagos = c.id_confirmacionAutorizacion" +
                       "    INNER JOIN cuentas_por_pagar cpp ON cppad.id_cuena_por_pagar = cpp.id_cuentas_por_pagar" +
                       "    INNER JOIN compra_insumosp ci ON cpp.id_compra = ci.id_compra_insumosp" +
                       "    INNER JOIN proveedores p ON ci.id_proveedor = p.id" +
                       "    INNER JOIN forma_pagos fp ON cppad.id_forma_pago = fp.id" +
                       "  WHERE cppad.estatus_pago = 1;";
        List<CuentasPorPagarAbonoDetalle> detalles = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CuentasPorPagarAbonoDetalle detalle = mapResultSetToDetalleConProveedor(resultSet);
                detalles.add(detalle);
            }
        }

        return detalles;
    }
    
    private CuentasPorPagarAbonoDetalle mapResultSetToDetalle(ResultSet resultSet) throws SQLException {
        CuentasPorPagarAbonoDetalle detalle = new CuentasPorPagarAbonoDetalle();
        detalle.setIdCuentaPorPagarAbonoDetalle(resultSet.getInt("id_cuenta_por_pagar_abono_detalle"));
        detalle.setIdCuentaPorPagar(resultSet.getInt("id_cuena_por_pagar"));
        detalle.setIdAutorizacionPagos(resultSet.getInt("id_autorizacion_pagos"));
        detalle.setIdFormaPago(resultSet.getInt("id_forma_pago"));
        detalle.setTotalAbonar(resultSet.getDouble("total_abonar"));
        detalle.setAbonoAbono(resultSet.getDouble("abono_abono"));
        detalle.setSaldoAbono(resultSet.getDouble("saldo_abono"));
        detalle.setFechaPago(resultSet.getDate("fehca_pago"));
        detalle.setUsuarioPago(resultSet.getInt("usuario_pago"));
        detalle.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        detalle.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        return detalle;
    }
    
    private CuentasPorPagarAbonoDetalle mapResultSetToDetalleIdAbonoDetalle(ResultSet resultSet) throws SQLException {
        CuentasPorPagarAbonoDetalle detalle = new CuentasPorPagarAbonoDetalle();
        detalle.setIdCuentaPorPagarAbonoDetalle(resultSet.getInt("id_cuenta_por_pagar_abono_detalle"));
        detalle.setIdCuentaPorPagar(resultSet.getInt("id_cuena_por_pagar"));
        detalle.setIdAutorizacionPagos(resultSet.getInt("id_autorizacion_pagos"));
        detalle.setIdFormaPago(resultSet.getInt("id_forma_pago"));
        detalle.setTotalAbonar(resultSet.getDouble("total_abonar"));
        detalle.setAbonoAbono(resultSet.getDouble("abono_abono"));
        detalle.setSaldoAbono(resultSet.getDouble("saldo_abono"));
        detalle.setFechaPago(resultSet.getDate("fehca_pago"));
        detalle.setUsuarioPago(resultSet.getInt("usuario_pago"));
        detalle.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        detalle.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        detalle.setNombreRubro(resultSet.getString("nombre_rubro"));
        detalle.setRazonSocial(resultSet.getString("razonSocial"));
        return detalle;
    }
    
    private CuentasPorPagarAbonoDetalle mapResultSetToDetalleConProveedor(ResultSet resultSet) throws SQLException {
        CuentasPorPagarAbonoDetalle detalle = new CuentasPorPagarAbonoDetalle();
        detalle.setIdCuentaPorPagarAbonoDetalle(resultSet.getInt("id_cuenta_por_pagar_abono_detalle"));
        detalle.setIdCuentaPorPagar(resultSet.getInt("id_cuena_por_pagar"));
        detalle.setIdAutorizacionPagos(resultSet.getInt("id_autorizacion_pagos"));
        detalle.setIdFormaPago(resultSet.getInt("id_forma_pago"));
        detalle.setTotalAbonar(resultSet.getDouble("total_abonar"));
        detalle.setAbonoAbono(resultSet.getDouble("abono_abono"));
        detalle.setSaldoAbono(resultSet.getDouble("saldo_abono"));
        detalle.setFechaPago(resultSet.getDate("fehca_pago"));
        detalle.setUsuarioPago(resultSet.getInt("usuario_pago"));
        detalle.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        detalle.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        
        Proveedor proveedor = new Proveedor();
        proveedor.setId(resultSet.getInt("id"));
        proveedor.setNombreComercial(resultSet.getString("nombreComercial"));
        proveedor.setRazonSocial(resultSet.getString("razonSocial"));
        proveedor.setDireccion(resultSet.getString("direccion"));
        proveedor.setRfc(resultSet.getString("rfc"));
        
        detalle.setProvedor(proveedor);
        
        detalle.setFormapagoint(resultSet.getInt("formapagoint"));
        detalle.setFormapagotipo(resultSet.getString("formapagotipo"));
        detalle.setEstatuspagonombre(resultSet.getString("estatuspagonombre"));
        
        return detalle;
    }
}