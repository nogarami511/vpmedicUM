/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.HistorialCostosInsumo;
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
public class HistorialCostosInsumoDAO {

    private Connection connection;

    public HistorialCostosInsumoDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un registro en la tabla
    public void insert(HistorialCostosInsumo historial) throws SQLException {
        String query = "INSERT INTO historial_costos_insumo (idInsumo, costoAnterior, costoActual, utilidadAnterior, utilidadActual, usuarioMod, fechaMod) "
                + "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, historial.getIdInsumo());
            stmt.setDouble(2, historial.getCostoAnterior());
            stmt.setDouble(3, historial.getCostoActual());
            stmt.setDouble(4, historial.getUtilidadAnterior());
            stmt.setDouble(5, historial.getUtilidadActual());
            stmt.setInt(6, VPMedicaPlaza.userSystem);

            stmt.executeUpdate();
        }
    }

    public List<HistorialCostosInsumo> traerPorInsumo(int idInsumo) throws SQLException {
        String query = "SELECT h.*,i.nombre FROM historial_costos_insumo h INNER JOIN insumos i ON i.id = h.idInsumo WHERE idInsumo = ?";
        List<HistorialCostosInsumo> historiales = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idInsumo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    historiales.add(mapRow(rs));
                }
            }
        }

        return historiales;
    }

    // Método para mapear un registro desde un ResultSet a un objeto HistorialCostosInsumo
    private HistorialCostosInsumo mapRow(ResultSet rs) throws SQLException {
        HistorialCostosInsumo historial = new HistorialCostosInsumo();
        historial.setId(rs.getInt("id"));
        historial.setIdInsumo(rs.getInt("idInsumo"));
        historial.setCostoAnterior(rs.getDouble("costoAnterior"));
        historial.setCostoActual(rs.getDouble("costoActual"));
        historial.setUtilidadAnterior(rs.getDouble("utilidadAnterior"));
        historial.setUtilidadActual(rs.getDouble("utilidadActual"));
        historial.setUsuarioMod(rs.getInt("usuarioMod"));
        historial.setFechaMod(rs.getTimestamp("fechaMod"));
        historial.setNombreInsumo(rs.getString("nombre"));
        return historial;
    }
}
