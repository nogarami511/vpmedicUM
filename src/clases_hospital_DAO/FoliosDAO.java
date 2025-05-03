/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Conexion;
import clases_hospital.Folio;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class FoliosDAO {

    private Connection connection;

    public FoliosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarFolio(Folio folio) throws SQLException {
        String query = "INSERT INTO folios (folio, id_paciente, fechaCreacion, montohastaelmomento, totaldeabono, saldoacubrir, fechamodificacion, id_usuario_modificacion, id_estatus, id_estatus_folio, id_paquete, id_habitacion, cosoto_deposito, id_estatus_pago_deposito, fecha_ingreso, id_estatus_hospitalizacion, numero_habitacion) VALUES (?, ?, NOW(), ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, folio.getFolio());
            statement.setInt(2, folio.getIdPaciente());
            statement.setDouble(3, folio.getMontoHastaElMomento());
            statement.setDouble(4, folio.getTotalDeAbono());
            statement.setDouble(5, folio.getSaldoACubrir());
            statement.setInt(6, VPMedicaPlaza.userSystem);
            statement.setInt(7, folio.getIdEstatus());
            statement.setInt(8, folio.getId_estatus_folio());
            statement.setInt(9, folio.getId_paquete());
            statement.setInt(10, folio.getId_habitacion());
            statement.setDouble(11, folio.getCosoto_deposito());
            statement.setInt(12, folio.getId_estatus_pago_deposito());
            statement.setInt(13, 0);
            statement.setInt(14, 0);

            statement.executeUpdate();
        }
    }

    public int insertarIntFolio(Folio folio) throws SQLException {
        int idFolio = 0;
        String query = "INSERT INTO folios (folio, id_paciente, fechaCreacion, montohastaelmomento, totaldeabono, saldoacubrir, fechamodificacion, id_usuario_modificacion, id_estatus, id_estatus_folio, id_paquete, id_habitacion, cosoto_deposito, id_estatus_pago_deposito, fecha_ingreso, id_estatus_hospitalizacion, numero_habitacion) VALUES (?, ?, NOW(), ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, folio.getFolio());
            statement.setInt(2, folio.getIdPaciente());
            statement.setDouble(3, folio.getMontoHastaElMomento());
            statement.setDouble(4, folio.getTotalDeAbono());
            statement.setDouble(5, folio.getSaldoACubrir());
            statement.setInt(6, VPMedicaPlaza.userSystem);
            statement.setInt(7, folio.getIdEstatus());
            statement.setInt(8, folio.getId_estatus_folio());
            statement.setInt(9, folio.getId_paquete());
            statement.setInt(10, folio.getId_habitacion());
            statement.setDouble(11, folio.getCosoto_deposito());
            statement.setInt(12, folio.getId_estatus_pago_deposito());
            statement.setInt(13, 0);
            statement.setInt(14, 0);

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                idFolio = generatedKeys.getInt(1);
            }
        }
        return idFolio;
    }

    public int insertarIntFolioConUrgencias(Folio folio) throws SQLException {
        int idFolio = 0;
        String query = "INSERT INTO folios (folio, id_paciente, fechaCreacion, montohastaelmomento, totaldeabono, saldoacubrir, fechamodificacion, id_usuario_modificacion, id_estatus, id_estatus_folio, id_paquete, id_habitacion, cosoto_deposito, id_estatus_pago_deposito, fecha_ingreso, id_estatus_hospitalizacion, numero_habitacion, urgencias) VALUES (?, ?, NOW(), ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, folio.getFolio());
            statement.setInt(2, folio.getIdPaciente());
            statement.setDouble(3, folio.getMontoHastaElMomento());
            statement.setDouble(4, folio.getTotalDeAbono());
            statement.setDouble(5, folio.getSaldoACubrir());
            statement.setInt(6, VPMedicaPlaza.userSystem);
            statement.setInt(7, folio.getIdEstatus());
            statement.setInt(8, folio.getId_estatus_folio());
            statement.setInt(9, folio.getId_paquete());
            statement.setInt(10, folio.getId_habitacion());
            statement.setDouble(11, folio.getCosoto_deposito());
            statement.setInt(12, folio.getId_estatus_pago_deposito());
            statement.setInt(13, 0);
            statement.setInt(14, 0);
            statement.setBoolean(15, folio.isUrgencias());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                idFolio = generatedKeys.getInt(1);
            }
        }
        return idFolio;
    }

    public int crearfolios(Folio folio) throws SQLException {
        int respuesta = 0;

        try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call PACIENTEHEMODINAMIA(?,?,?,?,?,?)}")) {
            stm.setString(1, folio.getFolio());
            stm.setInt(2, folio.getIdPaciente());
            stm.setInt(3, VPMedicaPlaza.userSystem);
            stm.setInt(4, folio.getId_paquete());
            stm.setDouble(5, folio.getCosoto_deposito());
            stm.setBoolean(6, folio.isUrgencias());
            // Ejecutar el procedimiento almacenado
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if (rs.next()) {
                respuesta = rs.getInt("id_folio");
            }

        } catch (SQLException e) {
        }

        return respuesta;
    }

    public void ALTAPACIENTE(int folio, int usuario_modificacion) throws SQLException {

        try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call ALTAPACIENTE(?,?)}")) {

            stm.setInt(1, folio);
            stm.setInt(2, usuario_modificacion);
            // Ejecutar el procedimiento almacenado
            stm.execute();

        } catch (SQLException e) {
        }

    }

    public void actualizarFolio(Folio folio) throws SQLException {
        String query = "UPDATE folios SET folio = ?, id_paciente = ?, fechaCreacion = ?, montohastaelmomento = ?, totaldeabono = ?, saldoacubrir = ?, fechamodificacion = NOW(),  id_usuario_modificacion = ?, id_estatus = ?, id_estatus_folio = ?, id_paquete = ?, id_habitacion = ?, cosoto_deposito = ?, id_estatus_pago_deposito = ?, fecha_ingreso = ?, id_estatus_hospitalizacion = ? WHERE  id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, folio.getFolio());
            statement.setInt(2, folio.getIdPaciente());
            statement.setDate(3, folio.getFecha());
            statement.setDouble(4, folio.getMontoHastaElMomento());
            statement.setDouble(5, folio.getTotalDeAbono());
            statement.setDouble(6, folio.getSaldoACubrir());
            statement.setInt(7, VPMedicaPlaza.userSystem);
            statement.setInt(8, folio.getIdEstatus());
            statement.setInt(9, folio.getId_estatus_folio());
            statement.setInt(10, folio.getId_paquete());
            statement.setInt(11, folio.getId_habitacion());
            statement.setDouble(12, folio.getCosoto_deposito());
            statement.setInt(13, folio.getId_estatus_pago_deposito());
            statement.setTimestamp(14, folio.getFecha_ingreso());
            statement.setInt(15, folio.getId_estatus_hospitalizacion());
            statement.setInt(16, folio.getId());

            statement.executeUpdate();
        }
    }

    public void actualizarHabitacion(Folio folio) throws SQLException {
        String query = "UPDATE folios SET id_habitacion = ? , numero_habitacion = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, folio.getId_habitacion());
            statement.setInt(2, folio.getNumero_habitacion());
            statement.setInt(3, folio.getId());
            statement.executeUpdate();
        }
    }

    public void actualizarFolioHabitacion(Folio folio) throws SQLException {
        String query = "UPDATE folios SET folio = ?, id_paciente = ?, fechaCreacion = ?, montohastaelmomento = ?, totaldeabono = ?, saldoacubrir = ?, fechamodificacion = NOW(),  id_usuario_modificacion = ?, id_estatus = ?, id_estatus_folio = ?, id_paquete = ?, id_habitacion = ?, cosoto_deposito = ?, id_estatus_pago_deposito = ?, fecha_ingreso = ?, id_estatus_hospitalizacion = ?, numero_habitacion = ? WHERE  id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, folio.getFolio());
            statement.setInt(2, folio.getIdPaciente());
            statement.setDate(3, folio.getFecha());
            statement.setDouble(4, folio.getMontoHastaElMomento());
            statement.setDouble(5, folio.getTotalDeAbono());
            statement.setDouble(6, folio.getSaldoACubrir());
            statement.setInt(7, VPMedicaPlaza.userSystem);
            statement.setInt(8, folio.getIdEstatus());
            statement.setInt(9, folio.getId_estatus_folio());
            statement.setInt(10, folio.getId_paquete());
            statement.setInt(11, folio.getId_habitacion());
            statement.setDouble(12, folio.getCosoto_deposito());
            statement.setInt(13, folio.getId_estatus_pago_deposito());
            statement.setTimestamp(14, folio.getFecha_ingreso());
            statement.setInt(15, folio.getId_estatus_hospitalizacion());
            statement.setInt(16, folio.getId());

            statement.executeUpdate();
        }
    }

    public void actualizarFolioHabitacionConUrgencias(Folio folio) throws SQLException {
        String query = "UPDATE folios SET folio = ?, id_paciente = ?, fechaCreacion = ?, montohastaelmomento = ?, totaldeabono = ?, saldoacubrir = ?, fechamodificacion = NOW(),  id_usuario_modificacion = ?, id_estatus = ?, id_estatus_folio = ?, id_paquete = ?, id_habitacion = ?, cosoto_deposito = ?, id_estatus_pago_deposito = ?, fecha_ingreso = ?, id_estatus_hospitalizacion = ?, urgencias = ? WHERE  id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, folio.getFolio());
            statement.setInt(2, folio.getIdPaciente());
            statement.setDate(3, folio.getFecha());
            statement.setDouble(4, folio.getMontoHastaElMomento());
            statement.setDouble(5, folio.getTotalDeAbono());
            statement.setDouble(6, folio.getSaldoACubrir());
            statement.setInt(7, VPMedicaPlaza.userSystem);
            statement.setInt(8, folio.getIdEstatus());
            statement.setInt(9, folio.getId_estatus_folio());
            statement.setInt(10, folio.getId_paquete());
            statement.setInt(11, folio.getId_habitacion());
            statement.setDouble(12, folio.getCosoto_deposito());
            statement.setInt(13, folio.getId_estatus_pago_deposito());
            statement.setTimestamp(14, folio.getFecha_ingreso());
            statement.setInt(15, folio.getId_estatus_hospitalizacion());
            statement.setBoolean(16, folio.isUrgencias());
            statement.setInt(17, folio.getId());

            statement.executeUpdate();
        }
    }

    public void actualizarFolioHabitacionConUrgenciasyUpgrade(Folio folio) throws SQLException {
        String query = "UPDATE folios SET folio = ?, id_paciente = ?, fechaCreacion = ?, montohastaelmomento = ?, totaldeabono = ?, saldoacubrir = ?, fechamodificacion = NOW(),  id_usuario_modificacion = ?, id_estatus = ?, id_estatus_folio = ?, id_paquete = ?, id_habitacion = ?, cosoto_deposito = ?, id_estatus_pago_deposito = ?, fecha_ingreso = ?, id_estatus_hospitalizacion = ?, urgencias = ?, upgrade = ? WHERE  id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, folio.getFolio());
            statement.setInt(2, folio.getIdPaciente());
            statement.setDate(3, folio.getFecha());
            statement.setDouble(4, folio.getMontoHastaElMomento());
            statement.setDouble(5, folio.getTotalDeAbono());
            statement.setDouble(6, folio.getSaldoACubrir());
            statement.setInt(7, VPMedicaPlaza.userSystem);
            statement.setInt(8, folio.getIdEstatus());
            statement.setInt(9, folio.getId_estatus_folio());
            statement.setInt(10, folio.getId_paquete());
            statement.setInt(11, folio.getId_habitacion());
            statement.setDouble(12, folio.getCosoto_deposito());
            statement.setInt(13, folio.getId_estatus_pago_deposito());
            statement.setTimestamp(14, folio.getFecha_ingreso());
            statement.setInt(15, folio.getId_estatus_hospitalizacion());
            statement.setBoolean(16, folio.isUrgencias());
            statement.setBoolean(17, folio.isUpgrade());
            statement.setInt(18, folio.getId());

            statement.executeUpdate();
        }
    }

    public void actualizarFolioFechaIngreso(Folio folio) throws SQLException {
        String query = "UPDATE folios SET folio = ?, id_paciente = ?, fechaCreacion = ?, montohastaelmomento = ?, totaldeabono = ?, saldoacubrir = ?, fechamodificacion = NOW(),  id_usuario_modificacion = ?, id_estatus = ?, id_estatus_folio = ?, id_paquete = ?, id_habitacion = ?, cosoto_deposito = ?, id_estatus_pago_deposito = ?, fecha_ingreso = NOW(), id_estatus_hospitalizacion = ? WHERE  id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, folio.getFolio());
            statement.setInt(2, folio.getIdPaciente());
            statement.setDate(3, folio.getFecha());
            statement.setDouble(4, folio.getMontoHastaElMomento());
            statement.setDouble(5, folio.getTotalDeAbono());
            statement.setDouble(6, folio.getSaldoACubrir());
            statement.setInt(7, VPMedicaPlaza.userSystem);
            statement.setInt(8, folio.getIdEstatus());
            statement.setInt(9, folio.getId_estatus_folio());
            statement.setInt(10, folio.getId_paquete());
            statement.setInt(11, folio.getId_habitacion());
            statement.setDouble(12, folio.getCosoto_deposito());
            statement.setInt(13, folio.getId_estatus_pago_deposito());
            statement.setInt(14, folio.getId_estatus_hospitalizacion());
            statement.setInt(15, folio.getId());

            statement.executeUpdate();
        }
    }

    public Folio obtenerFolioPorId(int idFolio) throws SQLException {
        String query = "SELECT * FROM folios WHERE id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearFolioConNumeroHabitacion(resultSet);
                }
            }
        }

        return null;
    }

    public List<Folio> obtenerFoliosAimprimir() throws SQLException {
        List<Folio> folios = new ArrayList<>();
        String query = "SELECT f.* FROM folios f INNER JOIN pacientes p ON f.id = p.id_folio AND f.id_estatus_hospitalizacion = 1;";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Folio folio = mapearFolio(resultSet);
                folios.add(folio);

            }
        }
        return folios;
    }

    public Folio obtenerFolioPorIdConNumeroHabitacion(int idFolio) throws SQLException {
        String query = "SELECT * FROM folios WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearFolioConNumeroHabitacion(resultSet);
                }
            }
        }
        return null;
    }

    public boolean existeFolio(int idFolio) throws SQLException {
        String query = "SELECT * FROM folios WHERE id_paciente = ? AND id_estatus_folio = 1;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);
            try (ResultSet resultset = statement.executeQuery()) {
                return resultset.next();
            }
        }
    }

    public Folio seleccionarFolioActivo(int idFolio) throws SQLException {
        String query = "SELECT * FROM folios WHERE id_paciente = ? AND id_estatus_folio = 1;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearFolio(resultSet);
                }
            }
        }

        return null;
    }

    public List<Folio> obtenerTodosFolios() throws SQLException {
        List<Folio> folios = new ArrayList<>();
        String query = "SELECT * FROM folios";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Folio folio = mapearFolio(resultSet);
                folios.add(folio);

            }
        }
        return folios;
    }

    public Folio obtenerFolioHemodinamia(String folio) throws SQLException {
        Folio foliors = new Folio();
        String query = "SELECT * FROM folios f WHERE f.folio = CONCAT('H-',?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    foliors = mapearFolio(resultSet);
                }
            }
        }
        return foliors;
    }

    public List<Folio> obtenerTodosFoliosConNombrePacienteYMedico() throws SQLException {
        List<Folio> folios = new ArrayList<>();
        String query = "SELECT f.*, p.nombre_paciente, m.nombre AS medico_nombre, m.id_medico \n"
                + "  FROM folios f \n"
                + "  INNER JOIN pacientes p ON f.id = p.id_folio \n"
                + "  INNER JOIN medicos m ON p.id_medico = m.id_medico\n"
                + "  WHERE f.id_estatus_folio < 3";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Folio folio = mapearFolioConNombrePacienteYMedico(resultSet);
                folios.add(folio);
            }
        }
        return folios;
    }

    public List<Folio> obtenerTodosFoliosConNombrePacienteYMedicoconfiltro(int filtrador) throws SQLException {
        List<Folio> folios = new ArrayList<>();

        try (CallableStatement stm = connection.prepareCall("{call VERLISTAPORTIPODEPAGO(?)}");) {
            stm.setInt(1, filtrador);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while (rs.next()) {
                Folio folio = mapearFolioConNombrePacienteYMedico(rs);
                folios.add(folio);
            }
        }
        return folios;
    }

    public List<Folio> obtenerTodosFoliosConNombrePacienteYMedicoHemdinamia() throws SQLException {
        List<Folio> folios = new ArrayList<>();

        String query = "{CALL FOLIOSHEMODINAMIAXPAGAR()}";
        try (CallableStatement statement = connection.prepareCall(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Folio folio = mapearFolioConNombrePacienteYMedicoHemodinamia(resultSet);
                folios.add(folio);
            }
        }
        return folios;
    }

    private Folio mapearFolio(ResultSet resultset) throws SQLException {
        Folio folio = new Folio();
        folio.setId(resultset.getInt("id"));
        folio.setFolio(resultset.getString("folio"));
        folio.setIdPaciente(resultset.getInt("id_paciente"));
        folio.setFecha(resultset.getDate("fechaCreacion"));
        folio.setMontoHastaElMomento(resultset.getDouble("montohastaelmomento"));
        folio.setTotalDeAbono(resultset.getDouble("totaldeabono"));
        folio.setSaldoACubrir(resultset.getDouble("saldoacubrir"));
        folio.setFechaModificacion(resultset.getTimestamp("fechamodificacion"));
        folio.setIdUsuarioModificacion(resultset.getInt("id_usuario_modificacion"));
        folio.setIdEstatus(resultset.getInt("id_estatus"));
        folio.setId_estatus_folio(resultset.getInt("id_estatus_folio"));
        folio.setId_paquete(resultset.getInt("id_paquete"));
        folio.setId_habitacion(resultset.getInt("id_habitacion"));
        folio.setCosoto_deposito(resultset.getDouble("cosoto_deposito"));
        folio.setId_estatus_pago_deposito(resultset.getInt("id_estatus_pago_deposito"));
        folio.setFecha_ingreso(resultset.getTimestamp("fecha_ingreso"));
        folio.setId_estatus_hospitalizacion(resultset.getInt("id_estatus_hospitalizacion"));
        folio.setNumero_habitacion(resultset.getInt("numero_habitacion"));
        return folio;
    }

    private Folio mapearFolioConNombrePacienteYMedico(ResultSet resultset) throws SQLException {
        Folio folio = new Folio();
        folio.setId(resultset.getInt("id"));
        folio.setFolio(resultset.getString("folio"));
        folio.setIdPaciente(resultset.getInt("id_paciente"));
        folio.setFecha(resultset.getDate("fechaCreacion"));
        folio.setMontoHastaElMomento(resultset.getDouble("montohastaelmomento"));
        folio.setTotalDeAbono(resultset.getDouble("totaldeabono"));
        folio.setSaldoACubrir(resultset.getDouble("saldoacubrir"));
        folio.setFechaModificacion(resultset.getTimestamp("fechamodificacion"));
        folio.setIdUsuarioModificacion(resultset.getInt("id_usuario_modificacion"));
        folio.setIdEstatus(resultset.getInt("id_estatus"));
        folio.setId_estatus_folio(resultset.getInt("id_estatus_folio"));
        folio.setId_paquete(resultset.getInt("id_paquete"));
        folio.setId_habitacion(resultset.getInt("id_habitacion"));
        folio.setCosoto_deposito(resultset.getDouble("cosoto_deposito"));
        folio.setId_estatus_pago_deposito(resultset.getInt("id_estatus_pago_deposito"));
        folio.setFecha_ingreso(resultset.getTimestamp("fecha_ingreso"));
        folio.setId_estatus_hospitalizacion(resultset.getInt("id_estatus_hospitalizacion"));
        folio.setNumero_habitacion(resultset.getInt("numero_habitacion"));
        folio.setNombre_paciente(resultset.getString("nombre_paciente"));
        folio.setMedico_nombre(resultset.getString("medico_nombre"));
        folio.setId_medico(resultset.getInt("id_medico"));
        return folio;
    }

    private Folio mapearFolioConNumeroHabitacion(ResultSet resultset) throws SQLException {
        Folio folio = new Folio();
        folio.setId(resultset.getInt("id"));
        folio.setFolio(resultset.getString("folio"));
        folio.setIdPaciente(resultset.getInt("id_paciente"));
        folio.setFecha(resultset.getDate("fechaCreacion"));
        folio.setMontoHastaElMomento(resultset.getDouble("montohastaelmomento"));
        folio.setTotalDeAbono(resultset.getDouble("totaldeabono"));
        folio.setSaldoACubrir(resultset.getDouble("saldoacubrir"));
        folio.setFechaModificacion(resultset.getTimestamp("fechamodificacion"));
        folio.setIdUsuarioModificacion(resultset.getInt("id_usuario_modificacion"));
        folio.setIdEstatus(resultset.getInt("id_estatus"));
        folio.setId_estatus_folio(resultset.getInt("id_estatus_folio"));
        folio.setId_paquete(resultset.getInt("id_paquete"));
        folio.setId_habitacion(resultset.getInt("id_habitacion"));
        folio.setCosoto_deposito(resultset.getDouble("cosoto_deposito"));
        folio.setId_estatus_pago_deposito(resultset.getInt("id_estatus_pago_deposito"));
        folio.setFecha_ingreso(resultset.getTimestamp("fecha_ingreso"));
        folio.setId_estatus_hospitalizacion(resultset.getInt("id_estatus_hospitalizacion"));
        folio.setNumero_habitacion(resultset.getInt("numero_habitacion"));
        folio.setUrgencias(resultset.getBoolean("urgencias"));
        return folio;
    }

    public int paqueteExistente(int id_Paciente) {
        int id_paquete = 0;

        String sql = "SELECT f.id_paquete FROM folios f WHERE f.id_paciente = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_Paciente);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id_paquete = resultSet.getInt(1);

            }
        } catch (Exception e) {
            // Manejo de excepciones
        }

        return id_paquete;
    }

    public void actualizarHoraSalida(int idfolio, int idTipoHabitacion, int numeroHabitacion) {
        String query = "UPDATE cuentahabitacion c SET c.horaSalida = NOW() WHERE c.id_folio = '" + idfolio + "' AND c.id_tipoHabitacion = '" + idTipoHabitacion + "' AND c.numeroHabitacion = '" + numeroHabitacion + "' ";

        try {
            Statement statement = connection.createStatement();
            int rowsUpdated = statement.executeUpdate(query);
                    System.out.println(""+ rowsUpdated);
        } catch (Exception e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean verificarConsumoHabirtacion(int idFolio) throws SQLException {
        String query = "SELECT * FROM cuentahabitacion c WHERE c.id_folio = '" + idFolio + "'";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }

        return false;
    }

    public Folio obtenerFolio(int idFolio) throws SQLException {
        Folio folio = new Folio();
        String query = "SELECT * FROM folios f WHERE f.id = '" + idFolio + "'";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                folio = mapearFolio(resultSet);

            }
        }
        return folio;
    }

    public int verificarEstatusHospitalizacion(int idFolio) throws SQLException {
        String query = "SELECT f.id_estatus_hospitalizacion FROM folios f WHERE f.id = '" + idFolio + "'";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    public Folio obtenerFechaIngreso(int id_folio) throws SQLException {
        Folio folio = new Folio();
        String query = "SELECT f.fecha_ingreso FROM folios f WHERE f.id = '" + id_folio + "'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    folio = mapearFolioConNumeroHabitacionFechaIngreso(resultSet);
                }
            }
        }
        return folio;
    }

    private Folio mapearFolioConNumeroHabitacionFechaIngreso(ResultSet resultset) throws SQLException {
        Folio folio = new Folio();
        folio.setFecha_ingreso(resultset.getTimestamp("fecha_ingreso"));
        return folio;
    }

    private Folio mapearFolioConNombrePacienteYMedicoHemodinamia(ResultSet resultSet) throws SQLException {
        Folio folio = new Folio();
        //h.id_folio_hemodinamia, p.nombre_paciente,m.nombre AS medico ,f.folio, f1.numero_habitacion
        folio.setId(resultSet.getInt("id_folio_hemodinamia"));
        folio.setIdPaciente(resultSet.getInt("id_pasiente"));
        folio.setNombre_paciente(resultSet.getString("nombre_paciente"));
        folio.setMedico_nombre(resultSet.getString("medico"));
        folio.setFolio(resultSet.getString("folio"));
        folio.setNumero_habitacion(resultSet.getInt("numero_habitacion"));
        folio.setMontoHastaElMomento(resultSet.getDouble("costo_paquete"));
        return folio;
    }

    public Folio optenerInfoHabFolio(int id_folio) throws SQLException {
        Folio folio = new Folio();

        String query = "{CALL STR_OPTENERDATOSPACIENTESHAB(?)}";
        try (CallableStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, id_folio);
            statement.execute();
            ResultSet rs = statement.getResultSet();

            if (rs.next()) {
                folio = mapeoFolioHabitacion(rs);
            }
        }
        return folio;
    }

    private Folio mapeoFolioHabitacion(ResultSet resultSet) throws SQLException {
        Folio folio = new Folio();
        folio.setId(resultSet.getInt("id"));
        folio.setNombre_paciente(resultSet.getString("nombre_paciente"));
        folio.setFecha_ingreso(resultSet.getTimestamp("fecha_ingreso"));
        folio.setNombre_Paquete_String(resultSet.getString("nombre_paquete"));
        folio.setDias(resultSet.getInt("dias"));
        folio.setHoras(resultSet.getInt("horas"));
        folio.setPaquete(resultSet.getBoolean("paquete"));
        return folio;
    }

}
