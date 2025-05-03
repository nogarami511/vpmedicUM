/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ConsumoPacienteMezclas;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author gamae
 */
public class ConsumoPacienteMezclasDAO {

    private Connection connection;

    public ConsumoPacienteMezclasDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<ConsumoPacienteMezclas> llenarTablaMezclasController(int idFolio) throws SQLException {
        int idPaquete = optenerIdPaquete(idFolio);

        ObservableList<ConsumoPacienteMezclas> consumoMezclas = FXCollections.observableArrayList();
        String sql = "SELECT pqm.id AS id_insumo, pqm.nombre AS tipo_insumo, 0 AS cantidad_entregada, "
                    + "       1 AS consumido, IFNULL(1, 0) AS incluido_en_paquete, 0 AS excedente, 0 AS devolucion, "
                    + "       (costo/1.16) AS precio_venta_unitaria_sin_iva, "
                    + "       IFNULL((costo/1.16) * 1, 0) AS subtotal_sin_iva "
                    + "FROM paquetesmedicos pqm "
                    + "WHERE pqm.id =  ? "
                    + "UNION ALL "
                    + "SELECT id_insumo, tipo_insumo, cantidad_entregada, consumido, IFNULL(incluido_en_paquete, 0) AS incluido_en_paquete, "
                    + "       excedente, devolucion, ROUND(precio_venta_unitaria, 2) AS precio_venta_unitaria_sin_iva, "
                    + "       CASE "
                    + "         WHEN IFNULL(incluido_en_paquete, 0) = consumido THEN 0 "
                    + "         ELSE IFNULL((consumido - IFNULL(incluido_en_paquete, 0)) * precio_venta_unitaria, 0) "
                    + "       END AS subtotal_sin_iva "
                    + "FROM( "
                    + "    SELECT c.id_producto_venta AS id_insumo, i.nombre AS tipo_insumo, cp.incluido_en_paquete, "
                    + "           SUM(c.cantidad) AS cantidad_entregada, SUM(c.cantidad) AS consumido, "
                    + "           IF(SUM(c.cantidad) > cp.incluido_en_paquete, SUM(c.cantidad) - cp.incluido_en_paquete, 0) AS excedente, "
                    + "           IF(SUM(c.cantidad) < cp.incluido_en_paquete, cp.incluido_en_paquete - SUM(c.cantidad), 0) AS devolucion, "
                    + "           ROUND(cos.precio_venta_unitaria, 5) AS precio_venta_unitaria "
                    + "    FROM consumos c "
                    + "    INNER JOIN insumos i ON c.id_producto_venta = i.id "
                    + "    INNER JOIN folios f ON c.id_folio = f.id "
                    + "    INNER JOIN costos cos ON cos.id_insumo = i.id "
                    + "    LEFT JOIN ( "
                    + "      SELECT id_insumo, COUNT(*) AS incluido_en_paquete "
                    + "      FROM configuracionpaquete "
                    + "      WHERE id_folio = ? "
                    + "      GROUP BY id_insumo "
                    + "    ) cp ON c.id_producto_venta = cp.id_insumo "
                    + "    WHERE c.id_estatus_consumo = 1 AND c.id_folio = ? "
                    + "    GROUP BY c.id_producto_venta, i.nombre "
                    + "  ) AS subquery ";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idPaquete);
            statement.setInt(2, idFolio);
            statement.setInt(3, idFolio);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ConsumoPacienteMezclas consumoPacienteMezclas = new ConsumoPacienteMezclas(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getDouble(5),
                        resultSet.getDouble(6),
                        resultSet.getInt(7));
                consumoMezclas.add(consumoPacienteMezclas);
            }
        }
        return consumoMezclas;
    }

    public ObservableList<ConsumoPacienteMezclas> llenarTablaVisualizarFolioController(int idFolio, int idpaquete) throws SQLException {
        double precioUnitario = 0;
        double subtotal = 0;
        DecimalFormat formato = new DecimalFormat("#.##");
        String numeroFormateado, subtotalFormato;
        // Formatear el número como cadena

        int cantidadConsumida;
        double cantidadPaquete;
        ObservableList<ConsumoPacienteMezclas> consumoMezclas = FXCollections.observableArrayList();
        String sql = "SELECT pqm.id AS id_insumo, pqm.nombre AS tipo_insumo, 0 AS cantidad_entregada_individual, " +
                  "1 AS consumido_individual, IFNULL(1, 0) AS incluido_en_paquete, 0 AS excedente, 0 AS devolucion, " +
                  "(costo/1.16) AS precio_venta_unitaria_sin_iva, " +
                  "IFNULL((costo/1.16) * 1, 0) AS subtotal_sin_iva " +
                  "FROM paquetesmedicos pqm " +
                  "WHERE pqm.id = ? " +
                  "UNION ALL " +
                  "SELECT id_insumo, " +
                  "tipo_insumo, " +
                  "cantidad_entregada_individual, " +
                  "consumido_individual, " +
                  "IFNULL(subquery.incluido_en_paquete, 0) AS incluido_en_paquete, " +
                  "subquery.excedente, " +
                  "subquery.devolucion, " +
                  "ROUND(subquery.precio_venta_unitaria, 2) AS precio_venta_unitaria_sin_iva, " +
                  "CASE " +
                  "WHEN IFNULL(subquery.incluido_en_paquete, 0) = subquery.consumido_individual THEN 0 " +
                  "ELSE IFNULL((subquery.consumido_individual - IFNULL(subquery.incluido_en_paquete, 0)) * subquery.precio_venta_unitaria, 0) " +
                  "END AS subtotal_sin_iva " +
                  "FROM ( " +
                  "SELECT c.id_producto_venta AS id_insumo, " +
                  "i.nombre AS tipo_insumo, " +
                  "cp.incluido_en_paquete, " +
                  "c.cantidad AS cantidad_entregada_individual, " +
                  "c.cantidad AS consumido_individual, " +
                  "IF(SUM(c.cantidad) > cp.incluido_en_paquete, SUM(c.cantidad) - cp.incluido_en_paquete, 0) AS excedente, " +
                  "IF(SUM(c.cantidad) < cp.incluido_en_paquete, cp.incluido_en_paquete - SUM(c.cantidad), 0) AS devolucion, " +
                  "ROUND(cos.precio_venta_unitaria, 5) AS precio_venta_unitaria " +
                  "FROM consumos c " +
                  "INNER JOIN insumos i ON c.id_producto_venta = i.id " +
                  "INNER JOIN folios f ON c.id_folio = f.id " +
                  "INNER JOIN costos cos ON cos.id_insumo = i.id " +
                  "LEFT JOIN ( " +
                  "SELECT id_insumo, COUNT(*) AS incluido_en_paquete " +
                  "FROM configuracionpaquete " +
                  "WHERE id_folio = ? " +
                  "GROUP BY id_insumo " +
                  ") cp ON c.id_producto_venta = cp.id_insumo " +
                  "WHERE c.id_estatus_consumo = 1 AND c.id_folio = ? " +
                  "AND c.id_estatus_consumo = 1 " +
                  "GROUP BY c.id_producto_venta, i.nombre, cp.incluido_en_paquete, c.cantidad " +
                  ") AS subquery;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idpaquete);
            statement.setInt(2, idFolio);
            statement.setInt(3, idFolio);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cantidadConsumida = resultSet.getInt(4);
                cantidadPaquete = resultSet.getDouble(5);
                precioUnitario = resultSet.getDouble(8);
                subtotal = resultSet.getDouble(9);
                if (cantidadConsumida < cantidadPaquete) {
                    cantidadConsumida = (int) cantidadPaquete;
                } else {
                    cantidadConsumida = cantidadConsumida;
                }

                numeroFormateado = formato.format(precioUnitario);
                subtotalFormato = formato.format(subtotal);
                ConsumoPacienteMezclas consumoPacienteMezclas = new ConsumoPacienteMezclas(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        cantidadConsumida,
                        resultSet.getDouble(5),
                        resultSet.getDouble(6),
                        resultSet.getInt(7),
                        Double.valueOf(numeroFormateado),
                        Double.valueOf(subtotalFormato));

                consumoMezclas.add(consumoPacienteMezclas);
            }
        }
        return consumoMezclas;
    }

    public int optenerIdPaquete(int idFolio) throws SQLException {
        int idPaquete = 0;
        String sql = "SELECT f.id_paquete FROM folios f WHERE f.id = ? AND f.id_estatus = 0";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFolio);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                idPaquete = resultSet.getInt(1);

            }
        }
        return idPaquete;
    }

    public ConsumoPacienteMezclas datosPaquete(int idFolio) throws SQLException {
        ConsumoPacienteMezclas datos = null;
        String sql = "SELECT p.nombre, p.costo FROM folios f INNER JOIN paquetesmedicos p ON p.id = f.id_paquete WHERE f.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFolio);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                datos = new ConsumoPacienteMezclas(resultSet.getString(1), resultSet.getDouble(2));

            }
        }

        return datos;
    }

    public ConsumoPacienteMezclas datosReporte(int idFolio) throws SQLException {
      
        ConsumoPacienteMezclas consumo = null;
        String fecha = null;
        String hora = null;
        String medico = null;
        String tipoHab = null;
        double costoDeposito = 0;
        double saldoACubrir = 0;
        Date fechaIngreso;
        Time horaIngreso;
        String sql = "SELECT DATE(f.fecha_ingreso),TIME(f.fecha_ingreso), m.nombre, IFNULL(h1.tipo, 0) AS tipo, IFNULL(h.numero_habitacion, 0) AS numero_habitacion ,f.totaldeabono,f.saldoacubrir FROM folios f\n"
                + "INNER JOIN pacientes p ON p.id_folio = f.id\n"
                + "  INNER JOIN medicos m ON m.id_medico = p.id_medico\n"
                + "  LEFT JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente\n"
                + "  LEFT JOIN habitacion h ON h.id_habitacion = ah.id_habitacion\n"
                + "  LEFT JOIN tipoHabitacion h1 ON h.id_tipo = h1.id_tipo\n"
                + "  WHERE f.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFolio);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                fechaIngreso = resultSet.getDate(1);
                horaIngreso = resultSet.getTime(2);

                fecha = String.valueOf(fechaIngreso);
                hora = String.valueOf(horaIngreso);

                medico = resultSet.getString(3);
                tipoHab = resultSet.getString(4) + " " + resultSet.getInt(5);
                costoDeposito = resultSet.getDouble(6);
                saldoACubrir = resultSet.getDouble(7);
               
                consumo = new ConsumoPacienteMezclas(fecha, hora, medico, tipoHab, costoDeposito, saldoACubrir);
            }
        }
        return consumo;
    }

}
