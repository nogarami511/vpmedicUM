/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Proveedor;
import clases_hospital.CuentasPorPagar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class CuentasPorPagarDAO {
    private Connection connection;

    public CuentasPorPagarDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(CuentasPorPagar cuentasPorPagar) throws SQLException {
        String query = "INSERT INTO cuentas_por_pagar (id_compra, id_tipo_compras, total, abono, saldo, id_estatus_CxP, id_autorizacion_pago, id_forma_pago, importe_autorizado, id_usuario_creacion, fecha_creacion, id_usuario_modificacion, fecha_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println("IDAUTORIZACION: " + cuentasPorPagar.getIdAutorizacionPago() + " ABONO: " + cuentasPorPagar.getAbono() + "  Saldo: " + cuentasPorPagar.getSaldo() + " FORMA PAGO: " + cuentasPorPagar.getIdFormaPago());
            statement.setInt(1, cuentasPorPagar.getIdCompra());
            statement.setInt(2, cuentasPorPagar.getIdTipoCompras());
            statement.setDouble(3, cuentasPorPagar.getTotal());
            statement.setDouble(4, cuentasPorPagar.getAbono());
            statement.setDouble(5, cuentasPorPagar.getSaldo());
            statement.setInt(6, cuentasPorPagar.getIdEstatusCxP());
            statement.setInt(7, cuentasPorPagar.getIdAutorizacionPago());
            statement.setInt(8, cuentasPorPagar.getIdFormaPago());
            statement.setDouble(9, cuentasPorPagar.getImporteAutorizado());
            statement.setInt(10, cuentasPorPagar.getIdUsuarioCreacion());
            statement.setInt(11, cuentasPorPagar.getIdUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                cuentasPorPagar.setIdCuentasPorPagar(generatedKeys.getInt(1));
            }
        }
    }

    public void update(CuentasPorPagar cuentasPorPagar) throws SQLException {
        String query = "UPDATE cuentas_por_pagar SET id_compra = ?, id_tipo_compras = ?, total = ?, abono = ?, saldo = ?, id_estatus_CxP = ?, id_autorizacion_pago = ?, id_forma_pago = ?, importe_autorizado = ?, id_usuario_modificacion = ?, fecha_modificacion = NOW(), id_estatus_pagos = ? WHERE id_cuentas_por_pagar = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("IDAUTORIZACION: " + cuentasPorPagar.getIdAutorizacionPago() + " ABONO: " + cuentasPorPagar.getAbono() + "  Saldo: " + cuentasPorPagar.getSaldo() + " FormaPago: " + cuentasPorPagar.getIdFormaPago());
            statement.setInt(1, cuentasPorPagar.getIdCompra());
            statement.setInt(2, cuentasPorPagar.getIdTipoCompras());
            statement.setDouble(3, cuentasPorPagar.getTotal());
            statement.setDouble(4, cuentasPorPagar.getAbono());
            statement.setDouble(5, cuentasPorPagar.getSaldo());
            statement.setInt(6, cuentasPorPagar.getIdEstatusCxP());
            statement.setInt(7, cuentasPorPagar.getIdAutorizacionPago());
            statement.setInt(8, cuentasPorPagar.getIdFormaPago());
            statement.setDouble(9, cuentasPorPagar.getImporteAutorizado());
            statement.setInt(10, VPMedicaPlaza.userSystem);
            statement.setInt(11, cuentasPorPagar.getId_estatus_pagos());
            statement.setInt(12, cuentasPorPagar.getIdCuentasPorPagar());

            statement.executeUpdate();
        }
    }
    
    public void updateEstatusSolicitud(CuentasPorPagar cuentasPorPagar) throws SQLException {
        String query = "UPDATE cuentas_por_pagar SET id_compra = ?, id_tipo_compras = ?, total = ?, abono = ?, saldo = ?, id_estatus_CxP = ?, id_autorizacion_pago = ?, id_forma_pago = ?, id_usuario_modificacion = ?, fecha_modificacion = NOW(), estatus_solicitado = ?, id_autorizacion_pago = ? WHERE id_cuentas_por_pagar = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("IDAUTORIZACION: " + cuentasPorPagar.getIdAutorizacionPago() + " ABONO: " + cuentasPorPagar.getAbono() + "  Saldo: " + cuentasPorPagar.getSaldo() + " FormaPago: " + cuentasPorPagar.getIdFormaPago());
            statement.setInt(1, cuentasPorPagar.getIdCompra());
            statement.setInt(2, cuentasPorPagar.getIdTipoCompras());
            statement.setDouble(3, cuentasPorPagar.getTotal());
            statement.setDouble(4, cuentasPorPagar.getAbono());
            statement.setDouble(5, cuentasPorPagar.getSaldo());
            statement.setInt(6, cuentasPorPagar.getIdEstatusCxP());
            statement.setInt(7, cuentasPorPagar.getIdFormaPago());
            statement.setDouble(8, cuentasPorPagar.getIdFormaPago());
            statement.setInt(9, VPMedicaPlaza.userSystem);
            statement.setBoolean(10, cuentasPorPagar.isEstatusSolicitado());
            statement.setInt(11, cuentasPorPagar.getIdAutorizacionPago());
            statement.setInt(12, cuentasPorPagar.getIdCuentasPorPagar());

            statement.executeUpdate();
        }
    }

    public void delete(int idCuentasPorPagar) throws SQLException {
        String query = "DELETE FROM cuentas_por_pagar WHERE id_cuentas_por_pagar = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCuentasPorPagar);
            statement.executeUpdate();
        }
    }

    public CuentasPorPagar getById(int idCuentasPorPagar) throws SQLException {
        String query = "SELECT * FROM cuentas_por_pagar WHERE id_cuentas_por_pagar = ?";
        CuentasPorPagar cuentasPorPagar = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCuentasPorPagar);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cuentasPorPagar = mapResultSetToCuentasPorPagar(resultSet);
            }
        }

        return cuentasPorPagar;
    }

    public List<CuentasPorPagar> getAll() throws SQLException {
        String query = "SELECT * FROM cuentas_por_pagar";
        List<CuentasPorPagar> cuentasPorPagarList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CuentasPorPagar cuentasPorPagar = mapResultSetToCuentasPorPagar(resultSet);
                cuentasPorPagarList.add(cuentasPorPagar);
            }
        }

        return cuentasPorPagarList;
    }
    
    public List<CuentasPorPagar> getAllProveedores() throws SQLException{
        String query = "CALL STR_TABLA_VER_COMPRAS()";
        List<CuentasPorPagar> cuentasPorPagarList = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CuentasPorPagar cuentasPorPagar = mapResultSetToCuentasPorPagarProveedor(resultSet);
                cuentasPorPagarList.add(cuentasPorPagar);
            }
        }

        return cuentasPorPagarList;
    }
    
    public List<CuentasPorPagar> obtenerTodosConNombreRubroYrazonSocialPorId(int id_confirmacion_autorizacion) throws SQLException {
        List<CuentasPorPagar> compras = new ArrayList<>();
        String query = "SELECT ci.*, 'INSUMO' AS nombre_rubro, 'ADEUDO' AS razonSocial FROM cuentas_por_pagar ci WHERE ci.id_autorizacion_pago = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_confirmacion_autorizacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CuentasPorPagar compra = mapResultSetToCuentasPorPagarConNombreRubroYrazonSocial(resultSet);
                    compras.add(compra);
                }
            }

        }

        return compras;
    }
    
    public CuentasPorPagar obtenerPorIdConfirmacionAutorizacion(int id_confirmacionAutorizacion) throws SQLException {
        String query = "SELECT * FROM cuentas_por_pagar WHERE id_autorizacion_pago = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_confirmacionAutorizacion);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCuentasPorPagar(resultSet);
                }
            }
        }

        return null;
    }
    
    public List<CuentasPorPagar> obtenerAdeudoProveedores() throws SQLException {
        List<CuentasPorPagar> compras = new ArrayList<>();
        String query = "SELECT p.id, p.razonSocial, SUM(ci.total_compra) AS cuenta, SUM(cpp.abono) AS abono, SUM(cpp.saldo) AS saldo FROM compra_insumosp ci INNER JOIN proveedores p ON ci.id_proveedor = p.id INNER JOIN cuentas_por_pagar cpp ON ci.id_compra_insumosp = cpp.id_compra GROUP BY ci.id_proveedor ORDER BY ci.usuario_creacion;";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CuentasPorPagar cuentasPorPagar = mapResultSetToAdeudoProveedor(resultSet);
                compras.add(cuentasPorPagar);
            }
        }

        return compras;
    }
    
    public List<CuentasPorPagar> obtenerCPPInsumoPorLiquidar() throws SQLException {
        List<CuentasPorPagar> estudiosMedicos = new ArrayList<>();
        String query = "SELECT cpp.*, p.*, fp.id AS formapagoint, fp.tipo AS formapagotipo, ep.nombre AS estatuspagonombre\n" +
                       "FROM cuentas_por_pagar cpp" +
                       "  INNER JOIN compra_insumosp ci ON cpp.id_compra = ci.id_compra_insumosp" +
                       "  INNER JOIN proveedores p ON ci.id_proveedor = p.id" +
                       "  INNER JOIN forma_pagos fp ON cpp.id_forma_pago = fp.id" +
                       "  INNER JOIN estatus_pagos ep ON cpp.id_estatus_pagos = ep.id " +
                       "WHERE cpp.id_estatus_pagos = 0";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CuentasPorPagar estudioMedico = crearConDatosCostoConDatosInsusmos(resultSet);
                estudiosMedicos.add(estudioMedico);
            }
        }

        return estudiosMedicos;
    }

    public List<CuentasPorPagar> obtenerCPPInsumoLiquidadas() throws SQLException {
        List<CuentasPorPagar> estudiosMedicos = new ArrayList<>();
        String query = "SELECT cpp.*, p.*, fp.id AS formapagoint, fp.tipo AS formapagotipo, ep.nombre AS estatuspagonombre\n" +
                       "FROM cuentas_por_pagar cpp" +
                       "  INNER JOIN compra_insumosp ci ON cpp.id_compra = ci.id_compra_insumosp" +
                       "  INNER JOIN proveedores p ON ci.id_proveedor = p.id" +
                       "  INNER JOIN forma_pagos fp ON cpp.id_forma_pago = fp.id" +
                       "  INNER JOIN estatus_pagos ep ON cpp.id_estatus_pagos = ep.id " +
                       "WHERE cpp.id_estatus_pagos = 1";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CuentasPorPagar estudioMedico = crearConDatosCostoConDatosInsusmos(resultSet);
                estudiosMedicos.add(estudioMedico);
            }
        }

        return estudiosMedicos;
    }
    
    private CuentasPorPagar mapResultSetToCuentasPorPagar(ResultSet resultSet) throws SQLException {
        CuentasPorPagar cuentasPorPagar = new CuentasPorPagar();
        cuentasPorPagar.setIdCuentasPorPagar(resultSet.getInt("id_cuentas_por_pagar"));
        cuentasPorPagar.setIdProveedor(resultSet.getInt("id_compra"));
        cuentasPorPagar.setIdTipoCompras(resultSet.getInt("id_tipo_compras"));
        cuentasPorPagar.setTotal(resultSet.getDouble("total"));
        cuentasPorPagar.setAbono(resultSet.getDouble("abono"));
        cuentasPorPagar.setSaldo(resultSet.getDouble("saldo"));
        cuentasPorPagar.setIdEstatusCxP(resultSet.getInt("id_estatus_CxP"));
        cuentasPorPagar.setIdAutorizacionPago(resultSet.getInt("id_autorizacion_pago"));
        cuentasPorPagar.setIdFormaPago(resultSet.getInt("id_forma_pago"));
        cuentasPorPagar.setImporteAutorizado(resultSet.getDouble("importe_autorizado"));
        cuentasPorPagar.setIdUsuarioCreacion(resultSet.getInt("id_usuario_creacion"));
        cuentasPorPagar.setFechaCreacion(resultSet.getDate("fecha_creacion"));
        cuentasPorPagar.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        cuentasPorPagar.setFechaModificacion(resultSet.getDate("fecha_modificacion"));

        return cuentasPorPagar;
    }

    private CuentasPorPagar mapResultSetToCuentasPorPagarProveedor(ResultSet resultSet) throws SQLException {
        CuentasPorPagar cuentasPorPagar = new CuentasPorPagar();
        cuentasPorPagar.setIdCuentasPorPagar(resultSet.getInt("id_cuentas_por_pagar"));
        cuentasPorPagar.setTipoCompra(resultSet.getString("tipo_compra"));
        cuentasPorPagar.setIdCompra(resultSet.getInt("id_compra"));
        cuentasPorPagar.setRazonSocial(resultSet.getString("razonSocial"));
        cuentasPorPagar.setFechaCreacion(resultSet.getDate("fecha_creacion"));
        cuentasPorPagar.setAbono(resultSet.getDouble("abono"));
        cuentasPorPagar.setSaldo(resultSet.getDouble("saldo"));
        cuentasPorPagar.setTotal(resultSet.getDouble("total"));
        cuentasPorPagar.setEstatusSolicitado(resultSet.getBoolean("estatus_solicitado"));
        cuentasPorPagar.setIdAutorizacionPago(resultSet.getInt("id_autorizacion_pago"));
        cuentasPorPagar.setIdAutorizacionPago(resultSet.getInt("id_autorizacion_pago"));
        cuentasPorPagar.setMontoSolicitadoSoliciatado(resultSet.getInt("total"));

        return cuentasPorPagar;
    }
    
    private CuentasPorPagar mapResultSetToCuentasPorPagarConNombreRubroYrazonSocial(ResultSet resultSet) throws SQLException {
        CuentasPorPagar cuentasPorPagar = new CuentasPorPagar();
        cuentasPorPagar.setIdCuentasPorPagar(resultSet.getInt("id_cuentas_por_pagar"));
        cuentasPorPagar.setTipoCompra(resultSet.getString("id_tipo_compras"));
        cuentasPorPagar.setIdCompra(resultSet.getInt("id_compra"));
        cuentasPorPagar.setRazonSocial(resultSet.getString("razonSocial"));
        cuentasPorPagar.setFechaCreacion(resultSet.getDate("fecha_creacion"));
        cuentasPorPagar.setAbono(resultSet.getDouble("abono"));
        cuentasPorPagar.setSaldo(resultSet.getDouble("saldo"));
        cuentasPorPagar.setTotal(resultSet.getDouble("total"));
        cuentasPorPagar.setEstatusSolicitado(resultSet.getBoolean("estatus_solicitado"));
        cuentasPorPagar.setIdAutorizacionPago(resultSet.getInt("id_autorizacion_pago"));
        cuentasPorPagar.setNombre_rubro(resultSet.getString("nombre_rubro"));

        return cuentasPorPagar;
    }
    
    private CuentasPorPagar mapResultSetToAdeudoProveedor(ResultSet resultSet) throws SQLException {
        CuentasPorPagar cuentasPorPagar = new CuentasPorPagar();
        cuentasPorPagar.setIdProveedor(resultSet.getInt("id"));
        cuentasPorPagar.setRazonSocial(resultSet.getString("razonSocial"));
        cuentasPorPagar.setTotal(resultSet.getDouble("cuenta"));
        cuentasPorPagar.setAbono(resultSet.getDouble("abono"));
        cuentasPorPagar.setSaldo(resultSet.getDouble("saldo"));

        return cuentasPorPagar;
    }
    
    private CuentasPorPagar crearConDatosCostoConDatosInsusmos(ResultSet resultSet) throws SQLException {
        CuentasPorPagar cuentasPorPagar = new CuentasPorPagar();
        cuentasPorPagar.setIdCuentasPorPagar(resultSet.getInt("id_cuentas_por_pagar"));
        cuentasPorPagar.setIdCompra(resultSet.getInt("id_compra"));
        cuentasPorPagar.setTotal(resultSet.getDouble("total"));
        cuentasPorPagar.setAbono(resultSet.getInt("abono"));
        cuentasPorPagar.setSaldo(resultSet.getInt("saldo"));
        cuentasPorPagar.setIdEstatusCxP(resultSet.getInt("id_estatus_CxP"));
        cuentasPorPagar.setIdAutorizacionPago(resultSet.getInt("id_autorizacion_pago"));
        cuentasPorPagar.setIdFormaPago(resultSet.getInt("id_forma_pago"));
        cuentasPorPagar.setImporteAutorizado(resultSet.getInt("importe_autorizado"));
        cuentasPorPagar.setFechaCreacion(resultSet.getDate("fecha_creacion"));
        cuentasPorPagar.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        Proveedor proveedor = new Proveedor();
        proveedor.setId(resultSet.getInt("id"));
        proveedor.setNombreComercial(resultSet.getString("nombreComercial"));
        proveedor.setRazonSocial(resultSet.getString("razonSocial"));
        proveedor.setDireccion(resultSet.getString("direccion"));
        proveedor.setRfc(resultSet.getString("rfc"));
        
        cuentasPorPagar.setProveedor(proveedor);
        cuentasPorPagar.setIdFormaPago(resultSet.getInt("id_forma_pago"));
        cuentasPorPagar.setFormapagotipo(resultSet.getString("formapagotipo"));
        cuentasPorPagar.setEstatuspagonombre(resultSet.getString("estatuspagonombre"));
        return cuentasPorPagar;
    }
}