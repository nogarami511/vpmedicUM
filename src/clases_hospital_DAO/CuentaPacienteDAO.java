/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Consumo;
import clases_hospital.ConsumoPacienteMezclas;
import clases_hospital.CuentaPaciente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author gamae
 */
public class CuentaPacienteDAO {

    private Connection connection;

    public CuentaPacienteDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<CuentaPaciente> llenarTabla() throws SQLException {
        CuentaPaciente cuentaPaciente;
        ObservableList<CuentaPaciente> cuentaPacientes = FXCollections.observableArrayList();
        String sql = "SELECT p.id_paciente, f.id AS id_folio, ah.id_asignacion, f.folio, p.nombre_paciente, f.fechaCreacion, h.tipo, ROUND(f.montohastaelmomento, 2) AS montohastaelmomento, ROUND(f.saldoacubrir, 2) AS saldoacubrir, ROUND(f.totaldeabono, 2) AS totaldeabono, f.id_paquete FROM folios f INNER JOIN pacientes p ON f.id = p.id_folio INNER JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente INNER JOIN tipoHabitacion h ON h.id_tipo = ah.id_tipo_habitacion WHERE f.id_estatus_folio < 2;";

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                double subtotal = 0;
                subtotal = subtotalCuentaPaciente(resultSet.getInt(2));
                cuentaPaciente = new CuentaPaciente(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getString(7), subtotal + resultSet.getDouble(8), subtotal + resultSet.getDouble(9), resultSet.getDouble(10), resultSet.getInt(11));

                cuentaPacientes.add(cuentaPaciente);
            }
        }

        return cuentaPacientes;
    }

    public ObservableList<Consumo> llenarTablaVisualizarFolioController(int idFolio) throws SQLException {
        ObservableList<Consumo> cuentaPacientes = FXCollections.observableArrayList();
        String sql = "SELECT c.cantidad, c.tipo, c.fecha, c.monto FROM consumos c WHERE c.id_folio = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFolio);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int cantidad = resultSet.getInt("cantidad");
                String tipo = resultSet.getString("tipo");
                Date fecha = resultSet.getDate("fecha");
                double monto = resultSet.getDouble("monto");

                Consumo consumo = new Consumo(cantidad, tipo, fecha, monto);
                cuentaPacientes.add(consumo);
            }
        }

        return cuentaPacientes;
    }

    private double subtotalCuentaPaciente(int idFolio) throws SQLException {
        double subtotal = 0;
        int idPaquete = optenerIdPaquete(idFolio);
        String sql = "SELECT\n"
                + "    c.id_producto_venta AS idInsumo,\n"
                + "    i.nombre,\n"
                + "    midt.movimiento AS CantidadEntregada,\n"
                + "    c.cantidad AS Cantidad_consumida,\n"
                + "    IFNULL(ap.cantidad, 0) AS CantidadPakete,\n"
                + "    (c.cantidad - IFNULL(ap.cantidad, 0)) AS Excedente,\n"
                + "    COALESCE((midt.movimiento - c.cantidad), 0) AS Devolucion,\n"
                + "    c1.precio_venta_unitaria,\n"
                + "    ((c.cantidad - IFNULL(ap.cantidad, 0)) * c1.precio_venta_unitaria) AS subtotal\n"
                + "FROM\n"
                + "    consumos c\n"
                + "    LEFT JOIN armadopaquetemedico ap ON c.id_producto_venta = ap.id_insumo\n"
                + "    LEFT JOIN costos c1 ON c1.id_insumo = c.id_producto_venta\n"
                + "    INNER JOIN movimientos_inventariop mi ON c.folio = mi.folio_mov\n"
                + "    INNER JOIN movimientos_inventario_detalle midt ON mi.id = midt.movimientos_inventariop AND c.id_producto_venta = midt.id_insumo\n"
                + "    INNER JOIN insumos i ON i.id = midt.id_insumo\n"
                + "WHERE\n"
                + "    c.id_folio = ?\n"
                + "    AND c.id_tipo_de_consumo <> 4\n"
                + "\n"
                + "-- UNION ALL --pakete\n"
                + "UNION ALL\n"
                + "\n"
                + "SELECT\n"
                + "    a.id_insumo,\n"
                + "    i.nombre,\n"
                + "    0 AS Cantidad_Entregada,\n"
                + "    0 AS Cantidad_consumida,\n"
                + "    a.cantidad AS CantidadPakete,\n"
                + "    0 AS Excedente,\n"
                + "    0 AS Devolucion,\n"
                + "    c.precio_venta_unitaria,\n"
                + "    0 AS subtotal\n"
                + "FROM\n"
                + "    armadopaquetemedico a\n"
                + "    INNER JOIN insumos i ON i.id = a.id_insumo\n"
                + "    LEFT JOIN costos c ON c.id_insumo = i.id\n"
                + "WHERE\n"
                + "    a.id_paquete = ?\n"
                + "    AND NOT EXISTS (\n"
                + "        SELECT 1\n"
                + "        FROM consumos c\n"
                + "        WHERE\n"
                + "            c.id_producto_venta = a.id_insumo\n"
                + "            AND c.id_folio = ?\n"
                + "            AND c.id_tipo_de_consumo <> 4);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idFolio);
            statement.setInt(2, idPaquete);
            statement.setInt(3, idFolio);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                subtotal = subtotal + resultSet.getDouble(7);

            }
        }
        return subtotal;
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

}
