/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.SolicitudPago;
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
public class SolicitudesPagosDAO {
    private Connection connection;

    public SolicitudesPagosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(SolicitudPago solicitudPago) throws SQLException {
        String query = "INSERT INTO solicitudes_pagos (id_solicitudes_pagos, fecha_generada, forma_pago, solicitante, estatus_solicitud, usuario_autorizacion, fecha_autorizacion, usuario_modificacion, fecha_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, solicitudPago.getIdSolicitudesPagos());
            statement.setInt(2, solicitudPago.getFechaGenerada());
            statement.setDate(3, solicitudPago.getFormaPago());
            statement.setDouble(4, solicitudPago.getSolicitante());
            statement.setInt(5, solicitudPago.getEstatusSolicitud());
            statement.setDouble(6, solicitudPago.getUsuarioAutorizacion());
            statement.setInt(7, solicitudPago.getFechaAutorizacion());
            statement.setDouble(8, solicitudPago.getUsuarioModificacion());
            statement.setDate(9, solicitudPago.getFechaModificacion());

            statement.executeUpdate();
        }
    }

    public void actualizar(SolicitudPago solicitudPago) throws SQLException {
        String query = "UPDATE solicitudes_pagos SET fecha_generada = ?, forma_pago = ?, solicitante = ?, estatus_solicitud = ?, usuario_autorizacion = ?, fecha_autorizacion = ?, usuario_modificacion = ?, fecha_modificacion = ? WHERE id_solicitudes_pagos = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, solicitudPago.getFechaGenerada());
            statement.setDate(2, solicitudPago.getFormaPago());
            statement.setDouble(3, solicitudPago.getSolicitante());
            statement.setInt(4, solicitudPago.getEstatusSolicitud());
            statement.setDouble(5, solicitudPago.getUsuarioAutorizacion());
            statement.setInt(6, solicitudPago.getFechaAutorizacion());
            statement.setDouble(7, solicitudPago.getUsuarioModificacion());
            statement.setDate(8, solicitudPago.getFechaModificacion());
            statement.setInt(9, solicitudPago.getIdSolicitudesPagos());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM solicitudes_pagos WHERE id_solicitudes_pagos = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public SolicitudPago obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM solicitudes_pagos WHERE id_solicitudes_pagos = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearSolicitudPagoDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<SolicitudPago> obtenerTodos() throws SQLException {
        List<SolicitudPago> solicitudesPagos = new ArrayList<>();
        String query = "SELECT * FROM solicitudes_pagos";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SolicitudPago solicitudPago = crearSolicitudPagoDesdeResultSet(resultSet);
                solicitudesPagos.add(solicitudPago);
            }
        }

        return solicitudesPagos;
    }

    private SolicitudPago crearSolicitudPagoDesdeResultSet(ResultSet resultSet) throws SQLException {
        SolicitudPago solicitudPago = new SolicitudPago();
        solicitudPago.setIdSolicitudesPagos(resultSet.getInt("id_solicitudes_pagos"));
        solicitudPago.setFechaGenerada(resultSet.getInt("fecha_generada"));
        solicitudPago.setFormaPago(resultSet.getDate("forma_pago"));
        solicitudPago.setSolicitante(resultSet.getDouble("solicitante"));
        solicitudPago.setEstatusSolicitud(resultSet.getInt("estatus_solicitud"));
        solicitudPago.setUsuarioAutorizacion(resultSet.getDouble("usuario_autorizacion"));
        solicitudPago.setFechaAutorizacion(resultSet.getInt("fecha_autorizacion"));
        solicitudPago.setUsuarioModificacion(resultSet.getDouble("usuario_modificacion"));
        solicitudPago.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        return solicitudPago;
    }
}
