/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Laboratorio;
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
public class LaboratoriosDAO {
    private Connection connection;

    public LaboratoriosDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insertarLaboratorio(Laboratorio laboratorio) throws SQLException {
        String query = "INSERT INTO laboratorios (id_laboratorio, nombre_comercial_laboratorio, razon_social_laboratorio, direccion_laboratorio, rfc_laboratorio) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, laboratorio.getIdLaboratorio());
            statement.setString(2, laboratorio.getNombreComercial());
            statement.setString(3, laboratorio.getRazonSocial());
            statement.setString(4, laboratorio.getDireccion());
            statement.setString(5, laboratorio.getRfc());
            statement.executeUpdate();
        }
    }

    public List<Laboratorio> obtenerTodosLosLaboratorios() throws SQLException {
        List<Laboratorio> laboratorios = new ArrayList<>();
        String query = "SELECT * FROM laboratorios";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Laboratorio laboratorio = crearLaboratorioDesdeResultSet(resultSet);
                laboratorios.add(laboratorio);
            }
        }

        return laboratorios;
    }
    
    public List<Laboratorio> obtenerTodosLosLaboratoriosSoloIdyNombre() throws SQLException {
        List<Laboratorio> laboratorios = new ArrayList<>();
        String query = "SELECT id_laboratorio, nombre_comercial_laboratorio FROM laboratorios;";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Laboratorio laboratorio = crearLaboratorioDesdeResultSetSoloIdyNombre(resultSet);
                laboratorios.add(laboratorio);
            }
        }

        return laboratorios;
    }

    public Laboratorio obtenerLaboratorioPorId(int idLaboratorio) throws SQLException {
        String query = "SELECT * FROM laboratorios WHERE id_laboratorio = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idLaboratorio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearLaboratorioDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }
    
    

    public void actualizarLaboratorio(Laboratorio laboratorio) throws SQLException {
        String query = "UPDATE laboratorios SET nombre_comercial_laboratorio = ?, razon_social_laboratorio = ?, direccion_laboratorio = ?, rfc_laboratorio = ? WHERE id_laboratorio = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, laboratorio.getNombreComercial());
            statement.setString(2, laboratorio.getRazonSocial());
            statement.setString(3, laboratorio.getDireccion());
            statement.setString(4, laboratorio.getRfc());
            statement.setInt(5, laboratorio.getIdLaboratorio());
            statement.executeUpdate();
        }
    }

    private Laboratorio crearLaboratorioDesdeResultSet(ResultSet resultSet) throws SQLException {
        int idLaboratorio = resultSet.getInt("id_laboratorio");
        String nombreComercial = resultSet.getString("nombre_comercial_laboratorio");
        String razonSocial = resultSet.getString("razon_social_laboratorio");
        String direccion = resultSet.getString("direccion_laboratorio");
        String rfc = resultSet.getString("rfc_laboratorio");

        return new Laboratorio(idLaboratorio, nombreComercial, razonSocial, direccion, rfc);
    }
    
    private Laboratorio crearLaboratorioDesdeResultSetSoloIdyNombre(ResultSet resultSet) throws SQLException {
        Laboratorio laboratorio = new Laboratorio();
        
        laboratorio.setIdLaboratorio(resultSet.getInt("id_laboratorio"));;
        laboratorio.setNombreComercial(resultSet.getString("nombre_comercial_laboratorio"));

        return laboratorio;
    }
}
