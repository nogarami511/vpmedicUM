/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.OtrosIngresos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author Gerardo
 */
public class OtrosIngresosDAO {

    private Connection connection;

    public OtrosIngresosDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un registro en la tabla
    public void insert(OtrosIngresos ingreso) throws SQLException {
        String query = "INSERT INTO otros_ingresos (idProveedor, motivo, observaciones, monto, fechaIngreso, estatus, usuarioMod, fechaMod) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ingreso.getIdProveedor());
            stmt.setString(2, ingreso.getMotivo());
            stmt.setString(3, ingreso.getObservaciones());
            stmt.setDouble(4, ingreso.getMonto());
            stmt.setTimestamp(5, ingreso.getFechaIngreso());
            stmt.setInt(6, 1); //1) activo , 0) inactivo/cancelado
            stmt.setString(7, VPMedicaPlaza.userNameSystem);

            stmt.executeUpdate();
        }
    }

    // Método para actualizar un registro en la tabla
    public void update(OtrosIngresos ingreso) throws SQLException {
        String query = "UPDATE otros_ingresos SET idProveedor = ?, motivo = ?, observaciones = ?, monto = ?, estatus = ?, usuarioMod = ?, fechaMod = NOW() "
                + "WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ingreso.getIdProveedor());
            stmt.setString(2, ingreso.getMotivo());
            stmt.setString(3, ingreso.getObservaciones());
            stmt.setDouble(4, ingreso.getMonto());
            stmt.setInt(5, 1);
            stmt.setInt(6, VPMedicaPlaza.userSystem);
            stmt.setInt(7, ingreso.getId());

            stmt.executeUpdate();
        }
    }

    public void cancelarIngreso(int id) throws SQLException {
        String query = "UPDATE estatus = 0, usuarioMod = ?, fechaMod = NOW()"
                + "WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, VPMedicaPlaza.userSystem);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        }
    }

    public List<OtrosIngresos> obtenerPorMes(int mes) throws SQLException {
        String query = "SELECT o.*, p.razonSocial FROM otros_ingresos o  INNER JOIN proveedores p ON p.id = o.idProveedor \n"
                + "  WHERE MONTH(o.fechaIngreso)= ? AND YEAR(o.fechaIngreso) = YEAR(NOW())";
        List<OtrosIngresos> otrosIngresos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, mes);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    otrosIngresos.add(mapRow(rs));
                }
            }
        }

        return otrosIngresos;
    }

    public List<OtrosIngresos> obtenerPorMesActual() throws SQLException {
        String query = "SELECT o.*, p.razonSocial FROM otros_ingresos o  INNER JOIN proveedores p ON p.id = o.idProveedor \n"
                + "  WHERE MONTH(o.fechaIngreso)= MONTH(NOW()) AND YEAR(o.fechaIngreso) = YEAR(NOW())";
        List<OtrosIngresos> ingresos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ingresos.add(mapRow(rs));
            }
        }

        return ingresos;
    }

    private OtrosIngresos mapRow(ResultSet rs) throws SQLException {
        OtrosIngresos ingreso = new OtrosIngresos();
        ingreso.setId(rs.getInt("id"));
        ingreso.setIdProveedor(rs.getInt("idProveedor"));
        ingreso.setMotivo(rs.getString("motivo"));
        ingreso.setObservaciones(rs.getString("observaciones"));
        ingreso.setMonto(rs.getDouble("monto"));
        ingreso.setFechaIngreso(rs.getTimestamp("fechaIngreso"));
        ingreso.setEstatus(rs.getInt("estatus"));
        ingreso.setNombreProveedor(rs.getString("razonSocial"));
        return ingreso;
    }
}
