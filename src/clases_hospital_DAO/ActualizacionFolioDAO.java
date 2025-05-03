/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ActualizacionFolio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class ActualizacionFolioDAO {

    private Connection connection;

    public ActualizacionFolioDAO(Connection connection) {
        this.connection = connection;
    }
    // Método para obtener los resultados de la consulta

    public List<ActualizacionFolio> getReportItems(int idFolio) {
        List<ActualizacionFolio> reportItems = new ArrayList<>();

        try {   
            CallableStatement statement = connection.prepareCall("{CALL ACTUALIZARFOLIO2(?)}");

            // Asigna los valores de los parámetros
            statement.setInt(1, idFolio);
            

            // Ejecuta el procedimiento almacenado
            boolean hasResult = statement.execute();

            if (hasResult) {
                ResultSet resultSet = statement.getResultSet();

                // Procesa los resultados y crea objetos ActualizacionFolio
                while (resultSet.next()) {
                    int idInsumo = resultSet.getInt("id_insumo");
                    String tipoInsumo = resultSet.getString("tipo_insumo");
                    int cantidadEntregada = resultSet.getInt("cantidad_entregada");
                    int consumido = resultSet.getInt("consumido");
                    int incluidoEnPaquete = resultSet.getInt("incluido_en_paquete");
                    int excedente = resultSet.getInt("excedente");
                    int devolucion = resultSet.getInt("devolucion");
                    double precioVentaUnitariaSinIva = resultSet.getDouble("precio_venta_unitaria_sin_iva");
                    double subtotalSinIva = resultSet.getDouble("subtotal_sin_iva");

                    ActualizacionFolio item = new ActualizacionFolio(idInsumo, tipoInsumo, cantidadEntregada,
                            consumido, incluidoEnPaquete, excedente,
                            devolucion, precioVentaUnitariaSinIva, subtotalSinIva);

                    reportItems.add(item);
                }

                // Cierra los recursos
                resultSet.close();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportItems;
    }
    
    public List<ActualizacionFolio> getReportItemsParcial(int idPaquete, int idFolio, int idQuirofano, int idTipoHabitacion, int numeroHabitacion) {
        List<ActualizacionFolio> reportItems = new ArrayList<>();

        try {   
            CallableStatement statement = connection.prepareCall("{CALL RPT_CORTEPARCIAL(?, ?, ?, ?, ?)}");

            // Asigna los valores de los parámetros
            statement.setInt(1, idQuirofano);
            statement.setInt(2, idTipoHabitacion);
            statement.setInt(3, idFolio);
            statement.setInt(4, idPaquete);
            statement.setInt(5, numeroHabitacion);

            // Ejecuta el procedimiento almacenado
            boolean hasResult = statement.execute();

            if (hasResult) {
                ResultSet resultSet = statement.getResultSet();

                // Procesa los resultados y crea objetos ActualizacionFolio
                while (resultSet.next()) {
                    int idInsumo = resultSet.getInt("id_insumo");
                    String tipoInsumo = resultSet.getString("tipo_insumo");
                    int cantidadEntregada = resultSet.getInt("cantidad_entregada");
                    int consumido = resultSet.getInt("consumido");
                    int incluidoEnPaquete = resultSet.getInt("incluido_en_paquete");
                    int excedente = resultSet.getInt("excedente");
                    int devolucion = resultSet.getInt("devolucion");
                    double precioVentaUnitariaSinIva = resultSet.getDouble("precio_venta_unitaria_sin_iva");
                    double subtotalSinIva = resultSet.getDouble("subtotal_sin_iva");

                    ActualizacionFolio item = new ActualizacionFolio(idInsumo, tipoInsumo, cantidadEntregada,
                            consumido, incluidoEnPaquete, excedente,
                            devolucion, precioVentaUnitariaSinIva, subtotalSinIva);

                    reportItems.add(item);
                }

                // Cierra los recursos
                resultSet.close();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reportItems;
    }


    public ActualizacionFolio obtenerInfoHospitalizacionUrgencias(int id_folio) throws SQLException {
        ActualizacionFolio actualizarfolio = new ActualizacionFolio();
        String query = "{CALL EXISTEHOSPITALIZACION(?)}";
        try (CallableStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id_folio);
            // Ejecuta el procedimiento almacenado
            boolean hasResult = statement.execute();
            if(hasResult){
                ResultSet rs = statement.getResultSet();
                if(rs.next()){
                    actualizarfolio.setIdInsumo(rs.getInt("id_insumo"));
                    actualizarfolio.setTipoInsumo("tipo_insumo");
                    actualizarfolio.setCantidadEntregada(rs.getInt("cantidad_entregada"));
                    actualizarfolio.setConsumido(rs.getInt("consumido"));
                    actualizarfolio.setIncluidoEnPaquete(rs.getInt("incluido_en_paquete"));
                    actualizarfolio.setExcedente(rs.getInt("excedente"));
                    actualizarfolio.setDevolucion(rs.getInt("devolucion"));
                    actualizarfolio.setPrecioVentaUnitariaSinIva(rs.getDouble("precio_venta_unitaria_sin_iva"));
                    actualizarfolio.setPrecioVentaUnitariaSinIva(rs.getDouble("subtotal_sin_iva"));
                    
                    return actualizarfolio;
                }else {
                    return null;
                }
            }else {
                return null;
            }
        }
    }

    // Método para calcular la sumatoria del campo subtotal_sin_iva
    public double calculateTotalSubtotal(List<ActualizacionFolio> reportItems) {
        double totalSubtotal = 0.0;

        totalSubtotal = reportItems.stream().map((item) -> item.getSubtotalSinIva()).reduce(totalSubtotal, (accumulator, _item) -> accumulator + _item);

        return totalSubtotal;
    }
}
