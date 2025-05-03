/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.SolicitudPagoEstudioMedico;
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
public class SolicitudPagosEstudiosMedicosDAO {

    private Connection connection;

    public SolicitudPagosEstudiosMedicosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarSolicitudPagoEstudioMedico(SolicitudPagoEstudioMedico solicitudPago) throws SQLException {
        String query = "INSERT INTO solicitud_pagos_estudios_medicos (fecha_generada, forma_pago, solicitante, estatus_solicitud, usuario_autorizacion, fecha_autorizacion, usuario_modificacion, fecha_modificacion, montototal) VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, solicitudPago.getFechaGenerada());
            statement.setInt(2, solicitudPago.getFormaPago());
            statement.setInt(3, solicitudPago.getSolicitante());
            statement.setInt(4, solicitudPago.getEstatusSolicitud());
            statement.setInt(5, solicitudPago.getUsuarioAutorizacion());
            statement.setDate(6, solicitudPago.getFechaAutorizacion());
            statement.setInt(7, solicitudPago.getUsuarioModificacion());
            statement.setDouble(8, solicitudPago.getMontototal());
            statement.executeUpdate();
        }
    }

    public int insertarSolicitudPagoEstudioMedicoYRegresarInt(SolicitudPagoEstudioMedico solicitudPago) throws SQLException {
        String query = "INSERT INTO solicitud_pagos_estudios_medicos (fecha_generada, forma_pago, solicitante, estatus_solicitud, usuario_autorizacion, fecha_autorizacion, usuario_modificacion, fecha_modificacion, montototal) VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, solicitudPago.getFechaGenerada());
            statement.setInt(2, solicitudPago.getFormaPago());
            statement.setInt(3, solicitudPago.getSolicitante());
            statement.setInt(4, solicitudPago.getEstatusSolicitud());
            statement.setInt(5, solicitudPago.getUsuarioAutorizacion());
            statement.setDate(6, solicitudPago.getFechaAutorizacion());
            statement.setInt(7, solicitudPago.getUsuarioModificacion());
            statement.setDouble(8, solicitudPago.getMontototal());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                return 0;
            }
        }
    }

    public void actualizarSolicitudPagoEstudioMedico(SolicitudPagoEstudioMedico solicitudPago) throws SQLException {
        String query = "UPDATE solicitud_pagos_estudios_medicos SET fecha_generada = ?, forma_pago = ?, solicitante = ?, estatus_solicitud = ?, usuario_autorizacion = ?, fecha_autorizacion = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), montototal = ? WHERE id_solicitud_pagos_estudios_medicos = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, solicitudPago.getFechaGenerada());
            statement.setInt(2, solicitudPago.getFormaPago());
            statement.setInt(3, solicitudPago.getSolicitante());
            statement.setInt(4, solicitudPago.getEstatusSolicitud());
            statement.setInt(5, solicitudPago.getUsuarioAutorizacion());
            statement.setDate(6, solicitudPago.getFechaAutorizacion());
            statement.setInt(7, solicitudPago.getUsuarioModificacion());
            statement.setDouble(8, solicitudPago.getMontototal());
            statement.setInt(9, solicitudPago.getIdSolicitudPagosEstudiosMedicos());
            statement.executeUpdate();
        }
    }

    public List<SolicitudPagoEstudioMedico> obtenerTodasLasSolicitudes() throws SQLException {
        List<SolicitudPagoEstudioMedico> solicitudes = new ArrayList<>();
        String query = "SELECT * FROM solicitud_pagos_estudios_medicos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SolicitudPagoEstudioMedico solicitudPago = crearSolicitudPagoDesdeResultSet(resultSet);
                solicitudes.add(solicitudPago);
            }
        }

        return solicitudes;
    }
    
    public List<SolicitudPagoEstudioMedico> obtenerTodasLasSolicitudesConInformacionPorAutorizar() throws SQLException {
        List<SolicitudPagoEstudioMedico> solicitudes = new ArrayList<>();
        String query = "SELECT spem.*, es.nombre_estatus_solicitud, fp.tipo FROM solicitud_pagos_estudios_medicos spem INNER JOIN estatus_solicitud es  ON spem.estatus_solicitud = es.id_estatus_solicitud INNER JOIN forma_pagos fp ON spem.forma_pago = fp.id WHERE spem.estatus_solicitud = 1;";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SolicitudPagoEstudioMedico solicitudPago = crearSolicitudPagoDesdeResultSetConInfo(resultSet);
                solicitudes.add(solicitudPago);
            }
        }

        return solicitudes;
    }
    
    public List<SolicitudPagoEstudioMedico> obtenerTodasLasSolicitudesConInformacionAutorisadas() throws SQLException {
        List<SolicitudPagoEstudioMedico> solicitudes = new ArrayList<>();
        String query = "SELECT spem.*, es.nombre_estatus_solicitud, fp.tipo FROM solicitud_pagos_estudios_medicos spem INNER JOIN estatus_solicitud es  ON spem.estatus_solicitud = es.id_estatus_solicitud INNER JOIN forma_pagos fp ON spem.forma_pago = fp.id WHERE spem.estatus_solicitud >= 2;";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SolicitudPagoEstudioMedico solicitudPago = crearSolicitudPagoDesdeResultSetConInfo(resultSet);
                solicitudes.add(solicitudPago);
            }
        }

        return solicitudes;
    }

    public SolicitudPagoEstudioMedico obtenerSolicitudPorId(int id) throws SQLException {
        String query = "SELECT * FROM solicitud_pagos_estudios_medicos WHERE id_solicitud_pagos_estudios_medicos = ?";

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

    private SolicitudPagoEstudioMedico crearSolicitudPagoDesdeResultSet(ResultSet resultSet) throws SQLException {
        SolicitudPagoEstudioMedico solicitudPago = new SolicitudPagoEstudioMedico();
        solicitudPago.setIdSolicitudPagosEstudiosMedicos(resultSet.getInt("id_solicitud_pagos_estudios_medicos"));
        solicitudPago.setFechaGenerada(resultSet.getDate("fecha_generada"));
        solicitudPago.setFormaPago(resultSet.getInt("forma_pago"));
        solicitudPago.setSolicitante(resultSet.getInt("solicitante"));
        solicitudPago.setEstatusSolicitud(resultSet.getInt("estatus_solicitud"));
        solicitudPago.setUsuarioAutorizacion(resultSet.getInt("usuario_autorizacion"));
        solicitudPago.setFechaAutorizacion(resultSet.getDate("fecha_autorizacion"));
        solicitudPago.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        solicitudPago.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        solicitudPago.setMontototal(resultSet.getDouble("montototal"));
        return solicitudPago;
    }
    
    private SolicitudPagoEstudioMedico crearSolicitudPagoDesdeResultSetConInfo(ResultSet resultSet) throws SQLException {
        SolicitudPagoEstudioMedico solicitudPago = new SolicitudPagoEstudioMedico();
        solicitudPago.setIdSolicitudPagosEstudiosMedicos(resultSet.getInt("id_solicitud_pagos_estudios_medicos"));
        solicitudPago.setFechaGenerada(resultSet.getDate("fecha_generada"));
        solicitudPago.setFormaPago(resultSet.getInt("forma_pago"));
        solicitudPago.setSolicitante(resultSet.getInt("solicitante"));
        solicitudPago.setEstatusSolicitud(resultSet.getInt("estatus_solicitud"));
        solicitudPago.setUsuarioAutorizacion(resultSet.getInt("usuario_autorizacion"));
        solicitudPago.setFechaAutorizacion(resultSet.getDate("fecha_autorizacion"));
        solicitudPago.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        solicitudPago.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        solicitudPago.setMontototal(resultSet.getDouble("montototal"));
        solicitudPago.setEstatusString(resultSet.getString("nombre_estatus_solicitud"));
        solicitudPago.setFormapagString(resultSet.getString("tipo"));
        return solicitudPago;
    }
}
