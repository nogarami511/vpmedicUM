/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ConfirmacionAutorizacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gerardo
 */
public class ConfirmacionAutorizacionDAO {

    private Connection connection;

    public ConfirmacionAutorizacionDAO(Connection connection) {
        this.connection = connection;
    }

    public void crear(ConfirmacionAutorizacion confirmacionAutorizacion) throws SQLException {
        String query = "INSERT INTO confirmacionautorizacion (fecha_pedio, id_formaPago, montoTotalAAutorizar, estatus, usuario_solicitante) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, confirmacionAutorizacion.getFecha_pedio());
            statement.setInt(2, confirmacionAutorizacion.getId_formaPago());
            statement.setDouble(3, confirmacionAutorizacion.getMontoTotalAAutorizar());
            statement.setInt(4, confirmacionAutorizacion.getEstatus());
            statement.setInt(5, confirmacionAutorizacion.getUsuario_solicitante());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                confirmacionAutorizacion.setId_confirmacionAutorizacion(generatedKeys.getInt(1));
            }
        }
    }

    public int crearYRegresarIdConfirmacion(ConfirmacionAutorizacion confirmacionAutorizacion) throws SQLException {
        String query = "INSERT INTO confirmacionautorizacion (fecha_pedio, id_formaPago, montoTotalAAutorizar, estatus, usuario_solicitante) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, confirmacionAutorizacion.getFecha_pedio());
            statement.setInt(2, confirmacionAutorizacion.getId_formaPago());
            statement.setDouble(3, confirmacionAutorizacion.getMontoTotalAAutorizar());
            statement.setInt(4, confirmacionAutorizacion.getEstatus());
            statement.setInt(5, confirmacionAutorizacion.getUsuario_solicitante());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                return 0;
            }
        }
    }

    public ConfirmacionAutorizacion leer(int id) throws SQLException {
        String query = "SELECT * FROM confirmacionautorizacion WHERE id_confirmacionAutorizacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearConfirmacionAutorizacion(resultSet);
                }
            }
        }
        return null;
    }

    public List<ConfirmacionAutorizacion> obtenerTodos() throws SQLException {
        List<ConfirmacionAutorizacion> listaConfirmaciones = new ArrayList<>();
        String query = "SELECT * FROM confirmacionautorizacion";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ConfirmacionAutorizacion confirmacionAutorizacion = mapearConfirmacionAutorizacion(resultSet);
                listaConfirmaciones.add(confirmacionAutorizacion);
            }
        }
        return listaConfirmaciones;
    }

    public void actualizar(ConfirmacionAutorizacion confirmacionAutorizacion) throws SQLException {
        String query = "UPDATE confirmacionautorizacion SET fecha_pedio = ?, montoTotalAAutorizar = ?, estatus = ?, usuario_solicitante = ? WHERE id_confirmacionAutorizacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, confirmacionAutorizacion.getFecha_pedio());
            statement.setDouble(2, confirmacionAutorizacion.getMontoTotalAAutorizar());
            statement.setInt(3, confirmacionAutorizacion.getEstatus());
            statement.setInt(4, confirmacionAutorizacion.getUsuario_solicitante());
            statement.setInt(5, confirmacionAutorizacion.getId_confirmacionAutorizacion());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM confirmacionautorizacion WHERE id_confirmacionAutorizacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<ConfirmacionAutorizacion> obtenerTodosConInfirmacionAutorizacionConInformacionPorAutorizar(String fechaIncio, String fechaFin) throws SQLException {
        List<ConfirmacionAutorizacion> listaConfirmaciones = new ArrayList<>();
        String query = "SELECT c.id_confirmacionAutorizacion, c.fecha_pedio, c.montoTotalAAutorizar, c.usuario_solicitante, ea.estatus AS nombre_estatu, fp.tipo AS forma_pago, c.id_confirmacionAutorizacion  AS idComprasmenosuno FROM confirmacionautorizacion c INNER JOIN estatus_autorizacion ea ON c.estatus = ea.id_estatus_autorizacion INNER JOIN forma_pagos fp ON c.id_formaPago = fp.id WHERE c.id_confirmacionAutorizacion > 2 AND c.estatus = 1 AND c.fecha_pedio BETWEEN ? AND ?"; //HACE FALTA AGREGAR EL NOMBRE.
//hace falta vere esto PreparedStatement statement = connection.prepareStatement(query)
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fechaIncio);
            statement.setString(2, fechaFin);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ConfirmacionAutorizacion confirmacionAutorizacion = mapearConfirmacionAutorizacionConInformacionsinIdentidicadores(resultSet);
                    listaConfirmaciones.add(confirmacionAutorizacion);
                 
                }
            }
        }
        return listaConfirmaciones;
    }

    public List<ConfirmacionAutorizacion> obtenerTodosConInfirmacionAutorizacionConInformacionAutorizados(String fechaIncio, String fechaFin) throws SQLException {
        List<ConfirmacionAutorizacion> listaConfirmaciones = new ArrayList<>();
        String query = "SELECT c.id_confirmacionAutorizacion, c.fecha_pedio, c.montoTotalAAutorizar, c.usuario_solicitante, ea.estatus AS nombre_estatu, fp.tipo AS forma_pago, c.id_confirmacionAutorizacion  AS idComprasmenosuno FROM confirmacionautorizacion c INNER JOIN estatus_autorizacion ea ON c.estatus = ea.id_estatus_autorizacion INNER JOIN forma_pagos fp ON c.id_formaPago = fp.id WHERE c.id_confirmacionAutorizacion > 2 AND c.estatus >= 2 AND c.fecha_pedio BETWEEN ? AND ?"; //HACE FALTA AGREGAR EL NOMBRE.

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fechaIncio);
            statement.setString(2, fechaFin);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ConfirmacionAutorizacion confirmacionAutorizacion = mapearConfirmacionAutorizacionConInformacionsinIdentidicadores(resultSet);
                    listaConfirmaciones.add(confirmacionAutorizacion);
                }
            }
        }
        return listaConfirmaciones;
    }
    
    private ConfirmacionAutorizacion mapearConfirmacionAutorizacion(ResultSet resultSet) throws SQLException {
        ConfirmacionAutorizacion confirmacionAutorizacion = new ConfirmacionAutorizacion();
        confirmacionAutorizacion.setId_confirmacionAutorizacion(resultSet.getInt("id_confirmacionAutorizacion"));
        confirmacionAutorizacion.setFecha_pedio(resultSet.getDate("fecha_pedio"));
        confirmacionAutorizacion.setId_formaPago(resultSet.getInt("id_formaPago"));
        confirmacionAutorizacion.setMontoTotalAAutorizar(resultSet.getDouble("montoTotalAAutorizar"));
        confirmacionAutorizacion.setEstatus(resultSet.getInt("estatus"));
        confirmacionAutorizacion.setUsuario_solicitante(resultSet.getInt("usuario_solicitante"));
        return confirmacionAutorizacion;
    }

    private ConfirmacionAutorizacion mapearConfirmacionAutorizacionConInformacion(ResultSet resultSet) throws SQLException {
        ConfirmacionAutorizacion confirmacionAutorizacion = new ConfirmacionAutorizacion();
        confirmacionAutorizacion.setId_confirmacionAutorizacion(resultSet.getInt("id_confirmacionAutorizacion"));
        confirmacionAutorizacion.setFecha_pedio(resultSet.getDate("fecha_pedio"));
        confirmacionAutorizacion.setId_formaPago(resultSet.getInt("id_formaPago"));
        confirmacionAutorizacion.setMontoTotalAAutorizar(resultSet.getDouble("montoTotalAAutorizar"));
        confirmacionAutorizacion.setEstatus(resultSet.getInt("estatus"));
        confirmacionAutorizacion.setUsuario_solicitante(resultSet.getInt("usuario_solicitante"));
        //POR AGREGAR NOMBRE DE USUARIO AQUI
        confirmacionAutorizacion.setNombre_estatus(resultSet.getString("nombre_estatu"));
        confirmacionAutorizacion.setForma_pago(resultSet.getString("forma_pago"));
//        confirmacionAutorizacion.setIdComprasmenosuno(resultSet.getInt("idComprasmenosuno"));
        return confirmacionAutorizacion;
    }
    
    private ConfirmacionAutorizacion mapearConfirmacionAutorizacionConInformacionsinIdentidicadores(ResultSet resultSet) throws SQLException {
        ConfirmacionAutorizacion confirmacionAutorizacion = new ConfirmacionAutorizacion();
        confirmacionAutorizacion.setId_confirmacionAutorizacion(resultSet.getInt("id_confirmacionAutorizacion"));
        confirmacionAutorizacion.setFecha_pedio(resultSet.getDate("fecha_pedio"));
        confirmacionAutorizacion.setMontoTotalAAutorizar(resultSet.getDouble("montoTotalAAutorizar"));
        confirmacionAutorizacion.setUsuario_solicitante(resultSet.getInt("usuario_solicitante"));
        //POR AGREGAR NOMBRE DE USUARIO AQUI
        confirmacionAutorizacion.setNombre_estatus(resultSet.getString("nombre_estatu"));
        confirmacionAutorizacion.setForma_pago(resultSet.getString("forma_pago"));
        confirmacionAutorizacion.setIdComprasmenosuno(resultSet.getInt("idComprasmenosuno"));
        return confirmacionAutorizacion;
    }
}
