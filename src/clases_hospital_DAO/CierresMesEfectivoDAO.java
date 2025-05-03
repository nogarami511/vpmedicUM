/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.CierresMesEfectivo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class CierresMesEfectivoDAO {

    private Connection connection;

    public CierresMesEfectivoDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertCierreMesEfectivo(CierresMesEfectivo cierresMesEfectivo) throws SQLException {
        String sql = "INSERT INTO cierresmesefectivo (mes_cierremes, year_cierremes, monto_efectivo_cierremes, fehca_cerremes) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cierresMesEfectivo.getMesCierreMes());
            preparedStatement.setInt(2, cierresMesEfectivo.getYearCierreMes());
            preparedStatement.setDouble(3, cierresMesEfectivo.getMontoEfectivoCierreMes());
            preparedStatement.executeUpdate();
        }
    }

    public List<CierresMesEfectivo> getAllCierresMesEfectivo() throws SQLException {
        List<CierresMesEfectivo> cierresMesEfectivoList = new ArrayList<>();
        String sql = "SELECT * FROM ghregioc_vphospital.cierresmesefectivo";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                CierresMesEfectivo cierresMesEfectivo = mapCierresMesEfectivoFromResultSet(resultSet);
                cierresMesEfectivoList.add(cierresMesEfectivo);
            }
        }
        return cierresMesEfectivoList;
    }

    public boolean getCierresMesEfectivoByFecha(int mes, int anio) throws SQLException {
        
        String sql = "SELECT * FROM cierresmesefectivo c WHERE MONTH(c.fehca_cerremes) = ? AND YEAR(c.fehca_cerremes) = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, mes);
            preparedStatement.setInt(2, anio);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }catch(SQLException e){
            System.err.println("no se encontro");
            return false;
        }
    }

    private CierresMesEfectivo mapCierresMesEfectivoFromResultSet(ResultSet rs) throws SQLException {
        CierresMesEfectivo cierresMesEfectivo = new CierresMesEfectivo();
        cierresMesEfectivo.setIdCierreMesEfectivo(rs.getInt("id_cierresmesefectivo"));
        cierresMesEfectivo.setMesCierreMes(rs.getString("mes_cierremes"));
        cierresMesEfectivo.setYearCierreMes(rs.getInt("year_cierremes"));
        cierresMesEfectivo.setMontoEfectivoCierreMes(rs.getDouble("monto_efectivo_cierremes"));
        cierresMesEfectivo.setFechaCierreMes(rs.getDate("fecha_cerremes"));
        return cierresMesEfectivo;
    }
}
