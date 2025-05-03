/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.FormaPago;
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
public class FormaPagoDAO {

    private Connection connection;

    public FormaPagoDAO(Connection connection) {
        this.connection = connection;
    }

    public void crear(FormaPago formaPago) throws SQLException {
        String query = "INSERT INTO forma_pagos (tipo, fecha_creacion, usuario_modificacion, fecha_moficiacion, id_estatus) "
                + "VALUES (?, ?, ?, NOW(), ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, formaPago.getTipo());
            statement.setTimestamp(2, formaPago.getFecha_creacion());
            statement.setInt(3, formaPago.getUsuario_modificacion());
            statement.setInt(4, formaPago.getId_estatus());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                formaPago.setId(generatedKeys.getInt(1));
            }
        }
    }

    public FormaPago leer(int id) throws SQLException {
        String query = "SELECT * FROM forma_pagos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    FormaPago formaPago = mapearFormaPago(resultSet);
                    return formaPago;
                }
            }
        }
        return null;
    }

    public List<FormaPago> obtenerTodos() throws SQLException {
        List<FormaPago> listaFormaPagos = new ArrayList<>();
        String query = "SELECT * FROM forma_pagos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                FormaPago formaPago = mapearFormaPago(resultSet);
                listaFormaPagos.add(formaPago);
            }
        }
        return listaFormaPagos;
    }
    
    public List<FormaPago> obtenerTodosEstatus1() throws SQLException {
        List<FormaPago> listaFormaPagos = new ArrayList<>();
        String query = "SELECT * FROM forma_pagos WHERE id_estatus = 1";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                FormaPago formaPago = mapearFormaPago(resultSet);
                listaFormaPagos.add(formaPago);
            }
        }
        return listaFormaPagos;
    }

    public List<FormaPago> obtenerPrimerosCuatro() throws SQLException {
        List<FormaPago> listaFormaPagos = new ArrayList<>();
        String query = "SELECT * FROM forma_pagos -- WHERE id = 1 AND id = 2 AND id = 3 AND id = 4 AND id = 11";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                FormaPago formaPago = mapearFormaPago(resultSet);
                listaFormaPagos.add(formaPago);
            }
        }
        return listaFormaPagos;
    }
    
    public List<FormaPago> obtenerPrimerosCuatroPAGOS() throws SQLException {
        List<FormaPago> listaFormaPagos = new ArrayList<>();
        String query = "SELECT * FROM forma_pagos  WHERE id IN ( 1, 2, 3, 4)";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                FormaPago formaPago = mapearFormaPago(resultSet);
                listaFormaPagos.add(formaPago);
            }
        }
        return listaFormaPagos;
    }

    public void actualizar(FormaPago formaPago) throws SQLException {
        String query = "UPDATE forma_pagos SET tipo = ?, fecha_creacion = ?, usuario_modificacion = NOW(), fecha_moficiacion = ?, id_estatus = ? "
                + "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, formaPago.getTipo());
            statement.setTimestamp(2, formaPago.getFecha_creacion());
            statement.setInt(3, formaPago.getUsuario_modificacion());
            statement.setInt(4, formaPago.getId_estatus());
            statement.setInt(5, formaPago.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM forma_pagos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private FormaPago mapearFormaPago(ResultSet resultSet) throws SQLException {
        FormaPago formaPago = new FormaPago();
        formaPago.setId(resultSet.getInt("id"));
        formaPago.setTipo(resultSet.getString("tipo"));
        formaPago.setFecha_creacion(resultSet.getTimestamp("fecha_creacion"));
        formaPago.setUsuario_modificacion(resultSet.getInt("usuario_modificacion"));
        formaPago.setFecha_modificacion(resultSet.getTimestamp("fecha_moficiacion"));
        formaPago.setId_estatus(resultSet.getInt("id_estatus"));
        return formaPago;
    }

    public List<FormaPago> obtenerTodasFormasPagos() throws SQLException {
        List<FormaPago> listaFormaPagos = new ArrayList<>();
        String query = "SELECT f.id, f.tipo FROM forma_pagos f;";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                FormaPago formaPago = mapearFormaPagoDatosEscenciales(resultSet);
                listaFormaPagos.add(formaPago);
            }
        }
        return listaFormaPagos;
    }

    private FormaPago mapearFormaPagoDatosEscenciales(ResultSet resultSet) throws SQLException {
        FormaPago formaPago = new FormaPago();
        formaPago.setId(resultSet.getInt("id"));
        formaPago.setTipo(resultSet.getString("tipo"));
        return formaPago;
    }
}
