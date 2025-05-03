/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.PaqueteMedico;
import clases_hospital.PaquetesNvo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gerardo
 */
public class PaquetesMedicosDAO {

    Connection connection;

    public PaquetesMedicosDAO(Connection connection) {
        this.connection = connection;
    }

    public double precioCostoPaquete(int idPaquete) throws SQLException {
        double precioCostoPaquete = 0.0;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ROUND(SUM(c.precio_venta_unitaria_paquete*a.cantidad),2) AS sumatoria FROM armadopaquetemedico a INNER JOIN costos c ON c.id_insumo = a.id_insumo WHERE a.id_paquete = ?");
        try {
            preparedStatement.setInt(1, idPaquete);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                precioCostoPaquete = resultSet.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return precioCostoPaquete;
    }

    public void cambiarEstatusPaquete(int idInsumo) throws SQLException {
        /*
            ESTE METODO VA A ACTUALIZAR EL ESTATUS DEL PAQUETE PORQUE EL PRECIO DE UN INSUMO CAMBIÓ
         */
        String query = "UPDATE paquetesmedicos p SET p.estatusMovimiento = 1, p.fechaModificacion = NOW() WHERE p.id IN (SELECT DISTINCT p.id FROM paquetesmedicos p INNER JOIN armadopaquetemedico a ON a.id_paquete = p.id WHERE a.id_insumo = ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idInsumo);
            statement.executeUpdate();
        }
    }

    public List<PaqueteMedico> obtenerTodos() throws SQLException {
        List<PaqueteMedico> listaPaquetesMedicos = new ArrayList<>();
        String query = "SELECT * FROM paquetesmedicos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                PaqueteMedico paqueteMedico = mapearPaqueteMedico(resultSet);
                listaPaquetesMedicos.add(paqueteMedico);
            }
        }
        return listaPaquetesMedicos;
    }
    
    public List<PaquetesNvo> obtenerPaquetesNvo() throws SQLException {
        List<PaquetesNvo> paqueteNvoList = new ArrayList<>();

        try (CallableStatement callableStatement = connection.prepareCall("{CALL TRAER_PAQUETES_CON_NVO_FORMATO2()}")) {
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                PaquetesNvo paqueteNvo = new PaquetesNvo();
                paqueteNvo.setId(resultSet.getLong("id"));
                paqueteNvo.setNombre(resultSet.getString("nombre"));
                paqueteNvo.setNombreUsuario(resultSet.getString("nombreUsuariossa"));
                paqueteNvo.setFechaModificacion(resultSet.getString("fechaModificacion"));

                // Asignar valores numéricos
                paqueteNvo.setPrecioVenta(resultSet.getDouble("precio_venta"));
                paqueteNvo.setCostoActual(resultSet.getDouble("costo_actual"));
                paqueteNvo.setCostoNuevo(resultSet.getDouble("costo_nuevo"));
                paqueteNvo.setDiferenciaCostos(resultSet.getDouble("diferencia_costos"));
                paqueteNvo.setDiferenciaCostosPorcentaje(resultSet.getDouble("diferencia_costos_porcentaje"));
                paqueteNvo.setPrecioVentaActual(resultSet.getDouble("precio_venta_actual"));
                paqueteNvo.setPrecioVentaNuevo(resultSet.getDouble("precio_venta_nuevo"));
                paqueteNvo.setDiferenciaPrecioVenta(resultSet.getDouble("diferencia_precio_venta"));
                paqueteNvo.setDiferenciaPrecioVentaPorcentaje(resultSet.getDouble("diferencia_precio_venta_porcentaje"));
                paqueteNvo.setPrecioOriginalMasIva(resultSet.getDouble("precio_original_mas_iva"));
                paqueteNvo.setPrecioNuevoMasIva(resultSet.getDouble("precio_nuevo_mas_iva"));
                paqueteNvo.setGananciaActual(resultSet.getDouble("ganancia_actual"));
                paqueteNvo.setGananciaNueva(resultSet.getDouble("ganancia_nueva"));
                paqueteNvo.setGananciaMontoOriginalPorcentaje(resultSet.getDouble("ganancia_monto_original_porcentaje"));
                paqueteNvo.setGananciaMontoNuevoPorcentaje(resultSet.getDouble("porcentaje_ganancia_monto_nuevo"));
                
                paqueteNvo.setColorRow(resultSet.getBoolean("estatusMovimiento"));


                paqueteNvoList.add(paqueteNvo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paqueteNvoList;
    }
    public List<PaquetesNvo> cambiodepaquetesDENUEVO() throws SQLException {
        List<PaquetesNvo> paqueteNvoList = new ArrayList<>();

        try (CallableStatement callableStatement = connection.prepareCall("{CALL TRAER_PAQUETES_CON_NVO_FORMATO29102024()}")) {
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                PaquetesNvo paqueteNvo = new PaquetesNvo();
                paqueteNvo.setId(resultSet.getLong("id"));
                paqueteNvo.setNombre(resultSet.getString("nombre"));
//                paqueteNvo.setNombreUsuario(resultSet.getString("nombreUsuariossa"));
//                paqueteNvo.setFechaModificacion(resultSet.getString("fechaModificacion"));

                // Asignar valores numéricos
                paqueteNvo.setPrecioVenta(resultSet.getDouble("precio_c_iva"));
                paqueteNvo.setCostosInsumis(resultSet.getDouble("costo_insumos"));
                paqueteNvo.setKitBasicocF(resultSet.getDouble("kit_basico_cf"));
                paqueteNvo.setUtilidad(resultSet.getDouble("utilidad"));
                paqueteNvo.setGananciaMontoNuevoPorcentaje(resultSet.getDouble("por_utilidad"));
                paqueteNvo.setCostosHE(resultSet.getDouble("costo_hrs_extra"));
                paqueteNvo.setTotalHe(resultSet.getDouble("precio_c_iva_extras"));
                paqueteNvo.setUtilidadCHE(resultSet.getDouble("utilidad_c_he"));
                paqueteNvo.setGananciaMontoOriginalPorcentaje(resultSet.getDouble("por_utilidad_c_he"));
                
                
                
                
             //   paqueteNvo.setColorRow(resultSet.getBoolean("estatusMovimiento"));


                paqueteNvoList.add(paqueteNvo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paqueteNvoList;
    }

    private PaqueteMedico mapearPaqueteMedico(ResultSet resultSet) throws SQLException {
        PaqueteMedico paqueteMedico = new PaqueteMedico();
        paqueteMedico.setId(resultSet.getInt("id"));
        paqueteMedico.setNombre(resultSet.getString("nombre"));
        paqueteMedico.setDescripcion(resultSet.getString("descripcion"));
        paqueteMedico.setPrecio(resultSet.getDouble("costo"));
        paqueteMedico.setIdUsuario(resultSet.getInt("idUsuario"));
        paqueteMedico.setClaveSAT(resultSet.getString("claveSAT"));
        paqueteMedico.setId_tipo_procedimiento(resultSet.getInt("id_tipo_procedimiento"));
        paqueteMedico.setId_quirofano(resultSet.getInt("id_quirofano"));
        paqueteMedico.setId_tipo_habitacion(resultSet.getInt("id_tipo_habitacion"));
        paqueteMedico.setId_procedimiento(resultSet.getInt("id_procedimiento"));
        paqueteMedico.setDias_hospitalizacion(resultSet.getInt("dias_hospitalizacion"));
        paqueteMedico.setNumero_comidas(resultSet.getInt("numero_comidas"));
        paqueteMedico.setHoras_tolerancia(resultSet.getInt("horas_tolerancia"));
        paqueteMedico.setHorasHospitalizacion(resultSet.getInt("horasHospitalizacion"));
        paqueteMedico.setEstatusMovimiento(resultSet.getInt("estatusMovimiento"));
        paqueteMedico.setFactor_paquete(resultSet.getDouble("factor_paquete"));
        return paqueteMedico;
    }

}
