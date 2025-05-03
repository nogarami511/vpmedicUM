/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.AseguradorasCostos;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class AseguradorasCostosDAO {

    private Connection connection;

    public AseguradorasCostosDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insertarCostosAseguradoraInsumos(int idAseguradora) throws SQLException {
        String query = "{CALL STR_INGRESAR_COSTOS_ASEGURADORA_INSUMOS(?)}";
        
        try (CallableStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, idAseguradora);
            System.out.println("idAseguradora" + idAseguradora);
            statement.executeUpdate();
        }
    }
    
    public void actualizarCostosAseguradoraInsumos(AseguradorasCostos cosotoAseguradoraInsumos) throws  SQLException{
        String query = "UPDATE aseguradoras_costos ac SET ac.costo_aseguradoras_costos = ?, ac.cantidad_insumos_aseguradoras_costos = ?, ac.costo_unitario_aseguradoras_costos = ?, ac.precio_venta_aseguradoras_costo = ?, ac.precio_venta_unitario_aseguradoras_costo = ?, ac.usuario_modificacion = ?, ac.fecha_modificacion = NOW() WHERE ac.id_aseguradoras_costo = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, cosotoAseguradoraInsumos.getCostoAseguradorasCostos());
            statement.setDouble(2, cosotoAseguradoraInsumos.getCantidadInsumosAseguradorasCostos());
            statement.setDouble(3, cosotoAseguradoraInsumos.getCostoUnitarioAseguradorasCostos());
            statement.setDouble(4, cosotoAseguradoraInsumos.getPrecioVentaAseguradorasCosto());
            statement.setDouble(5, cosotoAseguradoraInsumos.getPrecioVentaUnitarioAseguradorasCosto());
            statement.setInt(6, cosotoAseguradoraInsumos.getUsuarioModificacion());
            statement.setInt(7, cosotoAseguradoraInsumos.getIdAseguradorasCosto());
            statement.executeUpdate();
        }
    }

    public List<AseguradorasCostos> getByAseguradoraId(int idAseguradora) throws SQLException {
        String query = "{CALL STR_TRAER_TABLA_ASEGURADORA_COSTOS(?)}";
        List<AseguradorasCostos> aseguradorasCostosList = new ArrayList<>();

        try (CallableStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, idAseguradora);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AseguradorasCostos aseguradorasCostos = mapResultSetToAseguradorasCostos(resultSet);
                aseguradorasCostosList.add(aseguradorasCostos);
            }
        }

        return aseguradorasCostosList;
    }

    private AseguradorasCostos mapResultSetToAseguradorasCostos(ResultSet resultSet) throws SQLException {
        AseguradorasCostos aseguradorasCostos = new AseguradorasCostos();
        aseguradorasCostos.setIdAseguradorasCosto(resultSet.getInt("id_aseguradoras_costo"));
        aseguradorasCostos.setNombreInsumo(resultSet.getString("nombre"));
        aseguradorasCostos.setIdInsumo(resultSet.getInt("id_insumo"));
        aseguradorasCostos.setIdAseguradorap(resultSet.getInt("id_aseguradorap"));
        aseguradorasCostos.setCostoAseguradorasCostos(resultSet.getDouble("costo_aseguradoras_costos"));
        aseguradorasCostos.setCantidadInsumosAseguradorasCostos(resultSet.getDouble("cantidad_insumos_aseguradoras_costos"));
        aseguradorasCostos.setCostoUnitarioAseguradorasCostos(resultSet.getDouble("costo_unitario_aseguradoras_costos"));
        aseguradorasCostos.setPrecioVentaAseguradorasCosto(resultSet.getDouble("precio_venta_aseguradoras_costo"));
        aseguradorasCostos.setPrecioVentaUnitarioAseguradorasCosto(resultSet.getDouble("precio_venta_unitario_aseguradoras_costo"));
        aseguradorasCostos.setIdTipoInsumosMedico(resultSet.getInt("id_tipo_insumos_medico"));
        aseguradorasCostos.setNombreTipoInsumoMedico(resultSet.getString("nombre_tipo_insumo_medico"));
        aseguradorasCostos.setNombreAseguradora(resultSet.getString("nombre_aseguradora"));
        aseguradorasCostos.setDescuento(resultSet.getDouble("factor_aseguradora"));
        return aseguradorasCostos;
    }
}
