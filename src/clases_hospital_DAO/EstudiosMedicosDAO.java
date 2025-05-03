/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Paciente;
import clases_hospital.EstudioMedico;
import clases_hospital.Laboratorio;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author alfar
 */
public class EstudiosMedicosDAO {

    private Connection connection;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);

    public EstudiosMedicosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarEstudioMedico(EstudioMedico estudioMedico) throws SQLException {
        String query = "INSERT INTO estudiosmedicos (id_insumo, id_estudios_laboratorios, nombre_estudio, id_folio, id_pasiente, usuario_pedido, fecha_pedio, estatus_estudio, estatus_pago_estudio, usuario_pago, fecha_pago, usuario_modificacion, fecha_modificacion, id_laboratorio, precio_sin_iva, iva, precio_con_iva, id_solicitud, saldo_saldar) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, estudioMedico.getIdInsumo());
            statement.setInt(2, estudioMedico.getId_estudios_laboratorios());
            statement.setString(3, estudioMedico.getNombreEstudio());
            statement.setInt(4, estudioMedico.getIdFolio());
            statement.setInt(5, estudioMedico.getIdPaciente());
            statement.setInt(6, estudioMedico.getUsuarioPedido());
            statement.setInt(7, estudioMedico.getEstatusEstudio());
            statement.setInt(8, estudioMedico.getEstatusPagoEstudio());
            statement.setInt(9, estudioMedico.getUsuarioPago());
            statement.setInt(10, estudioMedico.getUsuarioModificacion());
            statement.setInt(11, estudioMedico.getId_laboratorio());
            statement.setDouble(12, estudioMedico.getPrecio_sin_iva());
            statement.setDouble(13, estudioMedico.getIva());
            statement.setDouble(14, estudioMedico.getPrecio_con_iva());
            statement.setInt(15, estudioMedico.getId_solicitud());
            statement.setDouble(16, estudioMedico.getPrecio_con_iva());
            statement.executeUpdate();
        }
    }

    public void actualizarEstudioMedico(EstudioMedico estudioMedico) throws SQLException {
        String query = "UPDATE estudiosmedicos SET id_insumo = ?, id_estudios_laboratorios = ?, nombre_estudio = ?, id_folio = ?, id_pasiente = ?, usuario_pedido = ?, fecha_pedio = ?, estatus_estudio = ?, estatus_pago_estudio = ?, usuario_pago = ?, fecha_pago = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), id_laboratorio = ?, id_laboratorio = ?, precio_sin_iva = ?, iva, precio_con_iva = ?, id_solicitud = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, estudioMedico.getIdInsumo());
            statement.setInt(2, estudioMedico.getId_estudios_laboratorios());
            statement.setString(2, estudioMedico.getNombreEstudio());
            statement.setInt(3, estudioMedico.getIdFolio());
            statement.setInt(4, estudioMedico.getIdPaciente());
            statement.setInt(5, estudioMedico.getUsuarioPedido());
            statement.setDate(6, estudioMedico.getFechaPedido());
            statement.setInt(7, estudioMedico.getEstatusEstudio());
            statement.setInt(8, estudioMedico.getEstatusPagoEstudio());
            statement.setInt(9, estudioMedico.getUsuarioPago());
            statement.setDate(10, estudioMedico.getFechaPago());
            statement.setInt(11, estudioMedico.getUsuarioModificacion());
            statement.setInt(12, estudioMedico.getId_laboratorio());
            statement.setDouble(13, estudioMedico.getPrecio_sin_iva());
            statement.setDouble(14, estudioMedico.getIva());
            statement.setDouble(15, estudioMedico.getPrecio_con_iva());
            statement.setInt(16, estudioMedico.getId_solicitud());
            statement.setInt(17, estudioMedico.getId());
            statement.executeUpdate();
        }
    }

    public void actualizarEstudioMedicoAbonos(EstudioMedico estudioMedico) throws SQLException {
        String query = "UPDATE estudiosmedicos SET id_insumo = ?, id_estudios_laboratorios = ?, nombre_estudio = ?, id_folio = ?, id_pasiente = ?, usuario_pedido = ?, fecha_pedio = ?, estatus_estudio = ?, estatus_pago_estudio = ?, usuario_pago = ?, fecha_pago = ?, usuario_modificacion = ?, fecha_modificacion = NOW(), id_laboratorio = ?, precio_sin_iva = ?, iva = ?, precio_con_iva = ?, id_solicitud = ?, saldo_saldar = ?, monto_abonado = ?, solicitar = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, estudioMedico.getIdInsumo());
            statement.setInt(2, estudioMedico.getId_estudios_laboratorios());
            statement.setString(3, estudioMedico.getNombreEstudio());
            statement.setInt(4, estudioMedico.getIdFolio());
            statement.setInt(5, estudioMedico.getIdPaciente());
            statement.setInt(6, estudioMedico.getUsuarioPedido());
            statement.setDate(7, estudioMedico.getFechaPedido());
            statement.setInt(8, estudioMedico.getEstatusEstudio());
            statement.setInt(9, estudioMedico.getEstatusPagoEstudio());
            statement.setInt(10, estudioMedico.getUsuarioPago());
            statement.setDate(11, estudioMedico.getFechaPago());
            statement.setInt(12, estudioMedico.getUsuarioModificacion());
            statement.setInt(13, estudioMedico.getId_laboratorio());
            statement.setDouble(14, estudioMedico.getPrecio_sin_iva());
            statement.setDouble(15, estudioMedico.getIva());
            statement.setDouble(16, estudioMedico.getPrecio_con_iva());
            statement.setInt(17, estudioMedico.getId_solicitud());
            statement.setDouble(18, estudioMedico.getSaldo_saldar());
            statement.setDouble(19, estudioMedico.getMonto_abonado());
            statement.setBoolean(20, estudioMedico.isSolicitar());
            statement.setInt(21, estudioMedico.getId());
            statement.executeUpdate();
        }
    }

    public List<EstudioMedico> obtenerTodosLosEstudiosMedicos() throws SQLException {
        List<EstudioMedico> estudiosMedicos = new ArrayList<>();
        String query = "SELECT * FROM estudiosmedicos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EstudioMedico estudioMedico = crearEstudioMedicoDesdeResultSet(resultSet);
                estudiosMedicos.add(estudioMedico);
            }
        }

        return estudiosMedicos;
    }

    public List<EstudioMedico> obtenerTodosLosEstudiosMedicosConNombrelaboratoroListosParaSeleccionar() throws SQLException {
        List<EstudioMedico> estudiosMedicos = new ArrayList<>();
        String query = "SELECT em.*, l.nombre_comercial_laboratorio FROM estudiosmedicos em INNER JOIN laboratorios l ON em.id_laboratorio = l.id_laboratorio WHERE em.id_solicitud = 0";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EstudioMedico estudioMedico = crearConDatosCostoConNombrelaboratoroListosParaSeleccionar(resultSet);
                estudiosMedicos.add(estudioMedico);
            }
        }

        return estudiosMedicos;
    }

    public List<EstudioMedico> obtenerTodosLosEstudiosMedicosConNombrelaboratoro(int id_solicitud) throws SQLException {
        List<EstudioMedico> estudiosMedicos = new ArrayList<>();
        String query = " SELECT em.*, l.nombre_comercial_laboratorio FROM estudiosmedicos em INNER JOIN laboratorios l ON em.id_laboratorio = l.id_laboratorio INNER JOIN solicitud_pagos_estudios_medicos spem ON em.id_solicitud = spem.id_solicitud_pagos_estudios_medicos WHERE em.id_solicitud = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_solicitud);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    EstudioMedico compra = crearConDatosCostoConNombrelaboratoroListosParaSeleccionar(resultSet);
                    estudiosMedicos.add(compra);
                }
            }

        }

        return estudiosMedicos;
    }

    public List<EstudioMedico> obtenerTodosLosEstudiosMedicosConCostos() throws SQLException {
        List<EstudioMedico> estudiosMedicos = new ArrayList<>();
        String query = "SELECT * FROM estudiosmedicos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EstudioMedico estudioMedico = crearConDatosCosto(resultSet);
                estudiosMedicos.add(estudioMedico);
            }
        }

        return estudiosMedicos;
    }

    public EstudioMedico obtenerEstudioMedicoPorIdConCostos(int id) throws SQLException {
        String query = "SELECT * FROM estudiosmedicos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearConDatosCosto(resultSet);
                }
            }
        }

        return null;
    }

    public List<EstudioMedico> obtenerTodosLosEstudiosMedicosParaIngresarAconsumo() throws SQLException {
        List<EstudioMedico> estudiosMedicos = new ArrayList<>();
        String query = "SELECT el.id_estudios_laboratorios, el.id_insumo, el.id_laboratorio, l.nombre_comercial_laboratorio, el.nombre_estudio, ROUND(c.precio_venta_unitaria) AS precio_venta_unitaria FROM estudios_laboratorios el INNER JOIN costos c ON el.id_insumo = c.id_insumo INNER JOIN laboratorios l ON el.id_laboratorio = l.id_laboratorio";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EstudioMedico estudioMedico = crearDatosIngrsoConsumo(resultSet);
                estudiosMedicos.add(estudioMedico);
            }
        }

        return estudiosMedicos;
    }
    public List<EstudioMedico> obtenerconsumoEstudiosPorIdFolio(Paciente paciente) throws SQLException {
        List<EstudioMedico> estudiosMedicos = new ArrayList<>();
       
        try (CallableStatement stm = connection.prepareCall("{call STR_TRAER_ESTUDIOSMEDICOS_POR_ID_FOLIO(?)}")) {
            
            stm.setInt("id_folio", paciente.getIdfolio());
            stm.execute();
            
            ResultSet rs = stm.getResultSet();
            while (rs.next()) {
                EstudioMedico estudioMedico = crearDatosIngrsoConsumo(rs);
                estudiosMedicos.add(estudioMedico);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            alertaError.setTitle("Error en base de datos");
            alertaError.setHeaderText("Error");
            alertaError.setContentText(ex.getMessage());
            alertaError.showAndWait();
        }

        return estudiosMedicos;
    }

    public EstudioMedico obtenerEstudioMedicoPorId(int id) throws SQLException {
        String query = "SELECT * FROM estudiosmeobtenerEstudioMedicoPorIddicos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearEstudioMedicoDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<EstudioMedico> obtenerEstudioMedicoConLaboratorioPorLiquidar() throws SQLException {
        List<EstudioMedico> estudiosMedicos = new ArrayList<>();
        String query = "SELECT em.*, l.*, fp.id AS formapagoint, fp.tipo AS formapagotipo, ep.nombre AS estatuspagonombre"
                + "    FROM estudiosmedicos em"
                + "    INNER JOIN laboratorios l ON em.id_laboratorio = l.id_laboratorio"
                + "    INNER JOIN solicitud_pagos_estudios_medicos spem ON em.id_solicitud = spem.id_solicitud_pagos_estudios_medicos"
                + "    INNER JOIN forma_pagos fp ON spem.forma_pago = fp.id"
                + "    INNER JOIN estatus_pagos ep ON em.estatus_pago_estudio = ep.id"
                + "    WHERE em.estatus_pago_estudio = 0";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EstudioMedico estudioMedico = crearConDatosCostoConDatosLaboratorio(resultSet);
                estudiosMedicos.add(estudioMedico);
            }
        }

        return estudiosMedicos;
    }

    public List<EstudioMedico> obtenerEstudioMedicoConLaboratorioLiquidadas() throws SQLException {
        List<EstudioMedico> estudiosMedicos = new ArrayList<>();
        String query = "SELECT em.*, l.*, fp.id AS formapagoint, fp.tipo AS formapagotipo, ep.nombre AS estatuspagonombre"
                + "    FROM estudiosmedicos em"
                + "    INNER JOIN laboratorios l ON em.id_laboratorio = l.id_laboratorio"
                + "    INNER JOIN solicitud_pagos_estudios_medicos spem ON em.id_solicitud = spem.id_solicitud_pagos_estudios_medicos"
                + "    INNER JOIN forma_pagos fp ON spem.forma_pago = fp.id"
                + "    INNER JOIN estatus_pagos ep ON em.estatus_pago_estudio = ep.id"
                + "    WHERE em.estatus_pago_estudio = 1";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                EstudioMedico estudioMedico = crearConDatosCostoConDatosLaboratorio(resultSet);
                estudiosMedicos.add(estudioMedico);
            }
        }

        return estudiosMedicos;
    }

    public EstudioMedico obtenerEstudioMedicoConLaboratorioPorId(int id) throws SQLException {
        String query = "SELECT em.*, l.*, fp.id AS formapagoint, fp.tipo AS formapagotipo FROM estudiosmedicos em INNER JOIN laboratorios l ON em.id_laboratorio = l.id_laboratorio INNER JOIN solicitud_pagos_estudios_medicos spem ON em.id_solicitud = spem.id_solicitud_pagos_estudios_medicos INNER JOIN forma_pagos fp ON spem.forma_pago = fp.id WHERE em.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearConDatosCostoConDatosLaboratorio(resultSet);
                }
            }
        }

        return null;
    }

    public List<Paciente> obtenerPacienteConDatosNecesarios() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT  p.id_paciente, p.nombre_paciente, f.id, f.folio \n"
                + "FROM asignacion_habitacion ah \n"
                + "INNER JOIN pacientes p ON ah.id_paciente = p.id_paciente \n"
                + "INNER JOIN folios f ON p.id_folio = f.id\n"
                + "\n"
                + "WHERE ah.id_paciente != 0;";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Paciente paciente = crearDatosPaciente(resultSet);
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }

    private EstudioMedico crearEstudioMedicoDesdeResultSet(ResultSet resultSet) throws SQLException {
        EstudioMedico estudioMedico = new EstudioMedico();
        estudioMedico.setId(resultSet.getInt("id"));
        estudioMedico.setIdInsumo(resultSet.getInt("id_insumo"));
        estudioMedico.setId_estudios_laboratorios(resultSet.getInt("id_estudios_laboratorios"));
        estudioMedico.setNombreEstudio(resultSet.getString("nombre_estudio"));
        estudioMedico.setIdFolio(resultSet.getInt("id_folio"));
        estudioMedico.setIdPaciente(resultSet.getInt("id_pasiente"));
        estudioMedico.setUsuarioPedido(resultSet.getInt("usuario_pedido"));
        estudioMedico.setFechaPedido(resultSet.getDate("fecha_pedio"));
        estudioMedico.setEstatusEstudio(resultSet.getInt("estatus_estudio"));
        estudioMedico.setEstatusPagoEstudio(resultSet.getInt("estatus_pago_estudio"));
        estudioMedico.setUsuarioPago(resultSet.getInt("usuario_pago"));
        estudioMedico.setFechaPago(resultSet.getDate("fecha_pago"));
        estudioMedico.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        estudioMedico.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        estudioMedico.setId_laboratorio(resultSet.getInt("id_laboratorio"));
        return estudioMedico;
    }

    private Paciente crearDatosPaciente(ResultSet resultSet) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(resultSet.getInt("id_paciente"));
        paciente.setNombre(resultSet.getString("nombre_paciente"));
        paciente.setIdfolio(resultSet.getInt("id"));
        paciente.setFolio(resultSet.getString("folio"));
        return paciente;
    }

    private EstudioMedico crearDatosIngrsoConsumo(ResultSet resultSet) throws SQLException {
        EstudioMedico estudiomedico = new EstudioMedico();
        estudiomedico.setId_estudios_laboratorios(resultSet.getInt("id_estudios_laboratorios"));
        estudiomedico.setIdInsumo(resultSet.getInt("id_insumo"));
        estudiomedico.setId_laboratorio(resultSet.getInt("id_laboratorio"));
        estudiomedico.setNombreEstudio(resultSet.getString("nombre_estudio"));
        estudiomedico.setPrecio_venta_unitaria_paquete(resultSet.getDouble("precio_venta_unitaria"));
        estudiomedico.setNombre_comercial_laboratorio(resultSet.getString("nombre_comercial_laboratorio"));
        return estudiomedico;
    }

    private EstudioMedico crearConDatosCosto(ResultSet resultSet) throws SQLException {
        EstudioMedico estudioMedico = new EstudioMedico();
        estudioMedico.setId(resultSet.getInt("id"));
        estudioMedico.setIdInsumo(resultSet.getInt("id_insumo"));
        estudioMedico.setId_estudios_laboratorios(resultSet.getInt("id_estudios_laboratorios"));
        estudioMedico.setNombreEstudio(resultSet.getString("nombre_estudio"));
        estudioMedico.setIdFolio(resultSet.getInt("id_folio"));
        estudioMedico.setIdPaciente(resultSet.getInt("id_pasiente"));
        estudioMedico.setUsuarioPedido(resultSet.getInt("usuario_pedido"));
        estudioMedico.setFechaPedido(resultSet.getDate("fecha_pedio"));
        estudioMedico.setEstatusEstudio(resultSet.getInt("estatus_estudio"));
        estudioMedico.setEstatusPagoEstudio(resultSet.getInt("estatus_pago_estudio"));
        estudioMedico.setUsuarioPago(resultSet.getInt("usuario_pago"));
        estudioMedico.setFechaPago(resultSet.getDate("fecha_pago"));
        estudioMedico.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        estudioMedico.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        estudioMedico.setId_laboratorio(resultSet.getInt("id_laboratorio"));
        estudioMedico.setPrecio_sin_iva(resultSet.getDouble("precio_sin_iva"));
        estudioMedico.setIva(resultSet.getDouble("iva"));
        estudioMedico.setPrecio_con_iva(resultSet.getDouble("precio_con_iva"));
        estudioMedico.setId_solicitud(resultSet.getInt("id_solicitud"));
        estudioMedico.setSaldo_saldar(resultSet.getDouble("saldo_saldar"));
        estudioMedico.setMonto_abonado(resultSet.getDouble("monto_abonado"));
        estudioMedico.setSolicitar(resultSet.getBoolean("solicitar"));
        return estudioMedico;
    }

    private EstudioMedico crearConDatosCostoConDatosLaboratorio(ResultSet resultSet) throws SQLException {
        EstudioMedico estudioMedico = new EstudioMedico();
        estudioMedico.setId(resultSet.getInt("id"));
        estudioMedico.setIdInsumo(resultSet.getInt("id_insumo"));
        estudioMedico.setId_estudios_laboratorios(resultSet.getInt("id_estudios_laboratorios"));
        estudioMedico.setNombreEstudio(resultSet.getString("nombre_estudio"));
        estudioMedico.setIdFolio(resultSet.getInt("id_folio"));
        estudioMedico.setIdPaciente(resultSet.getInt("id_pasiente"));
        estudioMedico.setUsuarioPedido(resultSet.getInt("usuario_pedido"));
        estudioMedico.setFechaPedido(resultSet.getDate("fecha_pedio"));
        estudioMedico.setEstatusEstudio(resultSet.getInt("estatus_estudio"));
        estudioMedico.setEstatusPagoEstudio(resultSet.getInt("estatus_pago_estudio"));
        estudioMedico.setUsuarioPago(resultSet.getInt("usuario_pago"));
        estudioMedico.setFechaPago(resultSet.getDate("fecha_pago"));
        estudioMedico.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        estudioMedico.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        estudioMedico.setId_laboratorio(resultSet.getInt("id_laboratorio"));
        estudioMedico.setPrecio_sin_iva(resultSet.getDouble("precio_sin_iva"));
        estudioMedico.setIva(resultSet.getDouble("iva"));
        estudioMedico.setPrecio_con_iva(resultSet.getDouble("precio_con_iva"));
        estudioMedico.setId_solicitud(resultSet.getInt("id_solicitud"));
        estudioMedico.setSaldo_saldar(resultSet.getDouble("saldo_saldar"));
        estudioMedico.setMonto_abonado(resultSet.getDouble("monto_abonado"));
        estudioMedico.setSolicitar(resultSet.getBoolean("solicitar"));
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setIdLaboratorio(resultSet.getInt("id_laboratorio"));
        laboratorio.setNombreComercial(resultSet.getString("nombre_comercial_laboratorio"));
        laboratorio.setRazonSocial(resultSet.getString("razon_social_laboratorio"));
        laboratorio.setDireccion(resultSet.getString("direccion_laboratorio"));
        laboratorio.setRfc(resultSet.getString("rfc_laboratorio"));

        estudioMedico.setLaboratorio(laboratorio);
        estudioMedico.setFormapagoint(resultSet.getInt("formapagoint"));
        estudioMedico.setFormapagotipo(resultSet.getString("formapagotipo"));
        estudioMedico.setEstatuspagonombre(resultSet.getString("estatuspagonombre"));
        return estudioMedico;
    }

    private EstudioMedico crearConDatosCostoConNombrelaboratoroListosParaSeleccionar(ResultSet resultSet) throws SQLException {
        EstudioMedico estudioMedico = new EstudioMedico();
        estudioMedico.setId(resultSet.getInt("id"));
        estudioMedico.setIdInsumo(resultSet.getInt("id_insumo"));
        estudioMedico.setId_estudios_laboratorios(resultSet.getInt("id_estudios_laboratorios"));
        estudioMedico.setNombreEstudio(resultSet.getString("nombre_estudio"));
        estudioMedico.setIdFolio(resultSet.getInt("id_folio"));
        estudioMedico.setIdPaciente(resultSet.getInt("id_pasiente"));
        estudioMedico.setUsuarioPedido(resultSet.getInt("usuario_pedido"));
        estudioMedico.setFechaPedido(resultSet.getDate("fecha_pedio"));
        estudioMedico.setEstatusEstudio(resultSet.getInt("estatus_estudio"));
        estudioMedico.setEstatusPagoEstudio(resultSet.getInt("estatus_pago_estudio"));
        estudioMedico.setUsuarioPago(resultSet.getInt("usuario_pago"));
        estudioMedico.setFechaPago(resultSet.getDate("fecha_pago"));
        estudioMedico.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        estudioMedico.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        estudioMedico.setId_laboratorio(resultSet.getInt("id_laboratorio"));
        estudioMedico.setPrecio_sin_iva(resultSet.getDouble("precio_sin_iva"));
        estudioMedico.setIva(resultSet.getDouble("iva"));
        estudioMedico.setPrecio_con_iva(resultSet.getDouble("precio_con_iva"));
        estudioMedico.setId_solicitud(resultSet.getInt("id_solicitud"));
        estudioMedico.setSaldo_saldar(resultSet.getDouble("saldo_saldar"));
        estudioMedico.setMonto_abonado(resultSet.getDouble("monto_abonado"));
        estudioMedico.setSolicitar(resultSet.getBoolean("solicitar"));
        estudioMedico.setNombre_comercial_laboratorio(resultSet.getString("nombre_comercial_laboratorio"));
        return estudioMedico;
    }
}
