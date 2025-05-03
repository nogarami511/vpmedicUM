/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Folio;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class PacientesDAO {

    private Connection connection;

    // Constructor que recibe una conexión de base de datos
    public PacientesDAO(Connection connection) {
        this.connection = connection;
    }

    public void actualizarmedico(int id_medico, int id_paciente) {
        try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call ACTUALIZARMEDICO(?,?)}")) {
            stm.setInt(1, id_medico);
            stm.setInt(2, id_paciente);
            stm.execute();

        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir información de la excepción
        }
    }
    
    public List<Folio> TraerFoliosxIdPacientes(int id_paciente) {
        List<Folio> Listapacientes = new ArrayList<>();
        try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call FOLIOS_ID_PACIENTES(?)}")) {
            stm.setInt(1, id_paciente);
            stm.execute();
            
            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                System.out.println("prueba 3 ");
                Listapacientes.add(mapearFoliosPacientes(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir información de la excepción
        }
        
        return Listapacientes;
    }
    
    private Folio mapearFoliosPacientes(ResultSet rs) throws SQLException{
        Folio newFolio = new Folio();
        
        
        // necesario para la tabla
        newFolio.setId(rs.getInt("id"));
        newFolio.setFecha_ingresoString(rs.getString("fecha_ingreso"));
        newFolio.setFecha_salidaString(rs.getString("fecha_salida"));
        newFolio.setMontoHastaElMomento(rs.getDouble("montohastaelmomento"));
        newFolio.setTotalDeAbono(rs.getDouble("totaldeabono"));
        newFolio.setAdeudo(rs.getDouble("adeudo"));
        
        // necesario para el reporte
        
         newFolio.setId_paquete(rs.getInt("id_paquete"));
         newFolio.setId_medico(rs.getInt("id_quirofano"));
         newFolio.setId_habitacion(rs.getInt("id_habitacion"));         
         newFolio.setNumero_habitacion(rs.getInt("numero_habitacion"));
         
         
         
        
        
        
        
        
        return newFolio;
        
    }

    public List<Paciente> reingreso(String nombre) throws SQLException {
        List<Paciente> listapaciente = new ArrayList<>();
        try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call PACIENTESALTA(?)}")) {
            stm.setString(1, nombre);
            stm.execute();

            ResultSet rs = stm.getResultSet();

            while (rs.next()) {
                listapaciente.add(mapeoreingreso(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir información de la excepción
        }

        return listapaciente;
    }

    public Paciente mapeoreingreso(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();

        paciente.setIdPaciente(rs.getInt("id_paciente"));
        paciente.setNombre(rs.getString("nombre_paciente"));

        return paciente;
    }

    // Método para insertar un nuevo paciente en la tabla
//    public void insertarPaciente(Paciente paciente) throws SQLException {
//        String query = "INSERT INTO pacientes (curp, nombre_paciente, procedencia_paciente, ocupacion_paciente, sexo_paciente, fecha_nacimiento_paciente, edad, religion, domicilio, telefono, id_medico, id_tipo_sangre, usuario_modificacion, fecha_modificaciones, id_folio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
//        PreparedStatement statement = connection.prepareStatement(query);
//
//        statement.setString(1, paciente.getCurp());
//        statement.setString(2, paciente.getNombre());
//        statement.setString(3, paciente.getProcedencia());
//        statement.setString(4, paciente.getOcupacion());
//        statement.setString(5, paciente.getSexo());
//        statement.setDate(6, paciente.getFechaNacimiento());
//        statement.setInt(7, paciente.getEdad());
//        statement.setString(8, paciente.getReligion());
//        statement.setString(9, paciente.getDomicilio());
//        statement.setString(10, paciente.getTelefono());
//        statement.setInt(11, paciente.getIdMedico());
//        statement.setInt(12, paciente.getIdTipoSangre());
//        statement.setInt(13, paciente.getUsuarioModificacion());
//        statement.setInt(14, 0);
//
//        statement.executeUpdate();
//    }
    public int insertarIntPaciente(Paciente paciente) throws SQLException {
        int idPaciente = 0;
        String query = "INSERT INTO pacientes (curp, nombre_paciente, procedencia_paciente, ocupacion_paciente, sexo_paciente, fecha_nacimiento_paciente, edad, religion, domicilio, telefono, id_medico, id_tipo_sangre, usuario_modificacion, fecha_modificaciones, id_folio) VALUES (?, ?, ?, ?, ?, CURDATE(), ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, "POR INGRESAR");
            statement.setString(2, paciente.getNombre());
            statement.setString(3, "POR INGRESAR");
            statement.setString(4, "POR INGRESAR");
            statement.setString(5, "POR INGRESAR");
            statement.setInt(6, paciente.getEdad());
            statement.setString(7, "POR INGRESAR");
            statement.setString(8, "POR INGRESAR");
            statement.setString(9, paciente.getTelefono());
            statement.setInt(10, paciente.getIdMedico());
            statement.setInt(11, 1);
            statement.setInt(12, paciente.getUsuarioModificacion());
            statement.setInt(13, 0);

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                idPaciente = generatedKeys.getInt(1);

            }
        }
        return idPaciente;
    }

    // Método para actualizar los datos de un paciente en la tabla
    public void actualizarPaciente(Paciente paciente) throws SQLException {
        String query = "UPDATE pacientes SET curp = ?, nombre_paciente = ?, procedencia_paciente = ?, ocupacion_paciente = ?, sexo_paciente = ?, fecha_nacimiento_paciente = ?, edad = ?, religion = ?, domicilio = ?, telefono = ?, id_medico = ?, id_tipo_sangre = ?, usuario_modificacion = ?, fecha_modificaciones = NOW(), id_folio = ? WHERE id_paciente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, paciente.getCurp());
            statement.setString(2, paciente.getNombre());
            statement.setString(3, paciente.getProcedencia());
            statement.setString(4, paciente.getOcupacion());
            statement.setString(5, paciente.getSexo());
            statement.setDate(6, paciente.getFechaNacimiento());
            statement.setInt(7, paciente.getEdad());
            statement.setString(8, paciente.getReligion());
            statement.setString(9, paciente.getDomicilio());
            statement.setString(10, paciente.getTelefono());
            statement.setInt(11, paciente.getIdMedico());
            statement.setInt(12, paciente.getIdTipoSangre());
            statement.setInt(13, paciente.getUsuarioModificacion());
            statement.setInt(14, paciente.getIdfolio());
            statement.setInt(15, paciente.getIdPaciente());

            statement.executeUpdate();
        }
    }

    public void actualizarPacienteSinFechaNacimiento(Paciente paciente) throws SQLException {
        String query = "UPDATE pacientes SET curp = ?, nombre_paciente = ?, procedencia_paciente = ?, ocupacion_paciente = ?, sexo_paciente = ?, edad = ?, religion = ?, domicilio = ?, telefono = ?, id_medico = ?, usuario_modificacion = ?, fecha_modificaciones = NOW(), id_folio = ? WHERE id_paciente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, paciente.getCurp());
            statement.setString(2, paciente.getNombre());
            statement.setString(3, paciente.getProcedencia());
            statement.setString(4, paciente.getOcupacion());
            statement.setString(5, paciente.getSexo());
            statement.setInt(6, paciente.getEdad());
            statement.setString(7, paciente.getReligion());
            statement.setString(8, paciente.getDomicilio());
            statement.setString(9, paciente.getTelefono());
            statement.setInt(10, paciente.getIdMedico());
            statement.setInt(11, paciente.getUsuarioModificacion());
            statement.setInt(12, paciente.getIdfolio());
            statement.setInt(13, paciente.getIdPaciente());

            statement.executeUpdate();
        }
    }

    // Método para obtener un paciente por su ID desde la tabla
    public Paciente obtenerPacientePorId(int idPaciente) throws SQLException {
        String query = "SELECT * FROM pacientes WHERE id_paciente = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idPaciente);
        ResultSet resultSet = statement.executeQuery();

        Paciente paciente = null;
        if (resultSet.next()) {
            paciente = mapearPaciente(resultSet);
        }

        resultSet.close();

        return paciente;
    }

    // Método para obtener todos los pacientes de la tabla
    public List<Paciente> obtenerTodosPacientes() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();

        String query = "SELECT * FROM pacientes WHERE folio";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Paciente paciente = mapearPaciente(resultSet);
            pacientes.add(paciente);
        }

        resultSet.close();

        return pacientes;
    }
    
    public List<Paciente> obtenerTodosPacientesQuirto() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();

        String query = "SELECT p.* FROM pacientes p INNER JOIN folios f ON p.id_folio = f.id";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Paciente paciente = mapearPaciente(resultSet);
            pacientes.add(paciente);
        }

        resultSet.close();

        return pacientes;
    }

    // Método para obtener un paciente por su ID desde la tabla
    public Paciente obtenerPacientePorIdConMedicoYTipoSangre(int idPaciente) throws SQLException {
        String query = "SELECT pa.*, ts.tipo_sangre, me.nombre AS medico FROM pacientes pa INNER JOIN tipo_sangre ts ON pa.id_tipo_sangre = ts.id_tipo_sangre INNER JOIN medicos me ON pa.id_medico = me.id_medico WHERE id_paciente = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idPaciente);
        ResultSet resultSet = statement.executeQuery();

        Paciente paciente = null;
        if (resultSet.next()) {
            paciente = mapearPacienteConTipoDeSangreYMedico(resultSet);
        }

        resultSet.close();

        return paciente;
    }

    // Método para obtener todos los pacientes de la tabla
    public List<Paciente> obtenerTodosPacientesConMeidicoYTipoSagre() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();

        String query = "SELECT pa.*, ts.tipo_sangre, me.nombre AS medico FROM pacientes pa INNER JOIN tipo_sangre ts ON pa.id_tipo_sangre = ts.id_tipo_sangre INNER JOIN medicos me ON pa.id_medico = me.id_medico";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Paciente paciente = mapearPacienteConTipoDeSangreYMedico(resultSet);
            pacientes.add(paciente);
        }

        resultSet.close();

        return pacientes;
    }

    // Método para obtener todos los pacientes de la tabla
    public List<Paciente> obtenerTodosPacientesConMeidicoTipoSagreYDatosFolio() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();

        String query = "SELECT pa.*, ts.tipo_sangre, me.nombre AS medico, f.folio, f.id_estatus_hospitalizacion FROM pacientes pa INNER JOIN tipo_sangre ts ON pa.id_tipo_sangre = ts.id_tipo_sangre INNER JOIN medicos me ON pa.id_medico = me.id_medico INNER JOIN folios f ON pa.id_folio = f.id";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Paciente paciente = mapearPacienteConTipoDeSangreMedicoYDatosFolio(resultSet);
            pacientes.add(paciente);
        }

        resultSet.close();

        return pacientes;
    }

    public List<Paciente> obtenerTodosPacientesConMeidicoTipoSagreYDatosFolioUrgencias() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();

        String query = "SELECT pa.*, ts.tipo_sangre, me.nombre AS medico, f.folio, f.id_estatus_hospitalizacion, f.urgencias FROM pacientes pa INNER JOIN tipo_sangre ts ON pa.id_tipo_sangre = ts.id_tipo_sangre INNER JOIN medicos me ON pa.id_medico = me.id_medico INNER JOIN folios f ON pa.id_folio = f.id WHERE f.urgencias = 1 AND f.id_estatus_hospitalizacion < 2";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Paciente paciente = mapearPacienteConTipoDeSangreMedicoYDatosFolioUrgencias(resultSet);
            pacientes.add(paciente);
        }

        resultSet.close();

        return pacientes;
    }

    public List<Paciente> obtenerTodosPacientesConMeidicoTipoSagreYDatosFolioHospitalizados() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();

        String query = "SELECT pa.*, ts.tipo_sangre, me.nombre AS medico, f.folio, f.id_estatus_hospitalizacion FROM pacientes pa INNER JOIN tipo_sangre ts ON pa.id_tipo_sangre = ts.id_tipo_sangre INNER JOIN medicos me ON pa.id_medico = me.id_medico INNER JOIN folios f ON pa.id_folio = f.id WHERE f.id_estatus_hospitalizacion < 2";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Paciente paciente = mapearPacienteConTipoDeSangreMedicoYDatosFolio(resultSet);
            pacientes.add(paciente);
        }

        resultSet.close();

        return pacientes;
    }

    public int contarFilas() throws SQLException {
        String query = "SELECT COUNT(p.id_paciente) FROM pacientes p INNER JOIN folios f ON p.id_folio = f.id WHERE f.id_estatus_hospitalizacion < 2";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {

                return resultSet.getInt(1);
            } else {
                return 0;
            }
        }
    }

    public List<Paciente> getDatosByPage(int pageIndex, int itemsPerPage) throws SQLException {
        List<Paciente> paciente = new ArrayList<>();

        String query = "SELECT pa.*, ts.tipo_sangre, me.nombre AS medico, f.folio, f.id_estatus_hospitalizacion FROM pacientes pa INNER JOIN tipo_sangre ts ON pa.id_tipo_sangre = ts.id_tipo_sangre INNER JOIN medicos me ON pa.id_medico = me.id_medico INNER JOIN folios f ON pa.id_folio = f.id WHERE f.id_estatus_hospitalizacion < 2 LIMIT ?, ?";

        int offset = pageIndex * itemsPerPage;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            statement.setInt(2, itemsPerPage);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    paciente.add(mapearPacienteConTipoDeSangreMedicoYDatosFolio(resultSet));
                }
            }
        }

        return paciente;
    }

    public List<Paciente> getDataPacientesIndicas() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT p.id_paciente, p.id_folio, p.nombre_paciente, h.tipo, IF(f.numero_habitacion = 0, 'S/H', CONCAT('', f.numero_habitacion)) AS habitacion, f.folio, f.id_paquete FROM pacientes p INNER JOIN folios f ON p.id_folio = f.id INNER JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente INNER JOIN tipoHabitacion h ON h.id_tipo = ah.id_tipo_habitacion WHERE f.id_estatus_folio = 1;";

        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Paciente paciente = mapeoPacienteIndicas(resultSet);
            pacientes.add(paciente);
        }

        resultSet.close();

        return pacientes;
    }
    
    public List<Paciente> getDataPacientesIndicasConFiltro(int filtro, String fechaInicio, String fechafin) throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "{CALL FILTRARCONSUMOSPACIENTESISTEMAS(?,?,?)}";

        try (CallableStatement stm = connection.prepareCall(query);) {
            stm.setInt(1, filtro);
            stm.setString(2, fechaInicio);
            stm.setString(3, fechafin);
            stm.execute();

            ResultSet resultSet = stm.getResultSet();

            while (resultSet.next()) {
                Paciente paciente = mapeoPacienteIndicas(resultSet);
                pacientes.add(paciente);
            }
            
            resultSet.close();
            
        } catch (SQLException e) {
            System.err.println("algo salio mal");
            e.printStackTrace();
        }

        return pacientes;
    }
    
    
    

    // Método privado para mapear un ResultSet a un objeto Paciente
    private Paciente mapearPaciente(ResultSet resultSet) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(resultSet.getInt("id_paciente"));
        paciente.setCurp(resultSet.getString("curp"));
        paciente.setNombre(resultSet.getString("nombre_paciente"));
        paciente.setProcedencia(resultSet.getString("procedencia_paciente"));
        paciente.setOcupacion(resultSet.getString("ocupacion_paciente"));
        paciente.setSexo(resultSet.getString("sexo_paciente"));
        paciente.setFechaNacimiento(resultSet.getDate("fecha_nacimiento_paciente"));
        paciente.setEdad(resultSet.getInt("edad"));
        paciente.setReligion(resultSet.getString("religion"));
        paciente.setDomicilio(resultSet.getString("domicilio"));
        paciente.setTelefono(resultSet.getString("telefono"));
        paciente.setIdMedico(resultSet.getInt("id_medico"));
        paciente.setIdTipoSangre(resultSet.getInt("id_tipo_sangre"));
        paciente.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        paciente.setFechaModificaciones(resultSet.getTimestamp("fecha_modificaciones"));
        paciente.setIdfolio(resultSet.getInt("id_folio"));

        return paciente;
    }

    private Paciente mapearPacienteConTipoDeSangreYMedico(ResultSet resultSet) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(resultSet.getInt("id_paciente"));
        paciente.setCurp(resultSet.getString("curp"));
        paciente.setNombre(resultSet.getString("nombre_paciente"));
        paciente.setProcedencia(resultSet.getString("procedencia_paciente"));
        paciente.setOcupacion(resultSet.getString("ocupacion_paciente"));
        paciente.setSexo(resultSet.getString("sexo_paciente"));
        paciente.setFechaNacimiento(resultSet.getDate("fecha_nacimiento_paciente"));
        paciente.setEdad(resultSet.getInt("edad"));
        paciente.setReligion(resultSet.getString("religion"));
        paciente.setDomicilio(resultSet.getString("domicilio"));
        paciente.setTelefono(resultSet.getString("telefono"));
        paciente.setIdMedico(resultSet.getInt("id_medico"));
        paciente.setIdTipoSangre(resultSet.getInt("id_tipo_sangre"));
        paciente.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        paciente.setFechaModificaciones(resultSet.getTimestamp("fecha_modificaciones"));
        paciente.setTipoSangre(resultSet.getString("tipo_sangre"));
        paciente.setNombreMedico(resultSet.getString("medico"));
        paciente.setIdfolio(resultSet.getInt("id_folio"));

        return paciente;
    }

    private Paciente mapearPacienteConTipoDeSangreMedicoYDatosFolio(ResultSet resultSet) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(resultSet.getInt("id_paciente"));
        paciente.setCurp(resultSet.getString("curp"));
        paciente.setNombre(resultSet.getString("nombre_paciente"));
        paciente.setProcedencia(resultSet.getString("procedencia_paciente"));
        paciente.setOcupacion(resultSet.getString("ocupacion_paciente"));
        paciente.setSexo(resultSet.getString("sexo_paciente"));
        paciente.setFechaNacimiento(resultSet.getDate("fecha_nacimiento_paciente"));
        paciente.setEdad(resultSet.getInt("edad"));
        paciente.setReligion(resultSet.getString("religion"));
        paciente.setDomicilio(resultSet.getString("domicilio"));
        paciente.setTelefono(resultSet.getString("telefono"));
        paciente.setIdMedico(resultSet.getInt("id_medico"));
        paciente.setIdTipoSangre(resultSet.getInt("id_tipo_sangre"));
        paciente.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        paciente.setFechaModificaciones(resultSet.getTimestamp("fecha_modificaciones"));
        paciente.setTipoSangre(resultSet.getString("tipo_sangre"));
        paciente.setNombreMedico(resultSet.getString("medico"));
        paciente.setIdfolio(resultSet.getInt("id_folio"));
        paciente.setFolio(resultSet.getString("folio"));
        paciente.setId_estatus_hospitalizacion(resultSet.getInt("id_estatus_hospitalizacion"));
        return paciente;
    }

    private Paciente mapearPacienteConTipoDeSangreMedicoYDatosFolioUrgencias(ResultSet resultSet) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(resultSet.getInt("id_paciente"));
        paciente.setCurp(resultSet.getString("curp"));
        paciente.setNombre(resultSet.getString("nombre_paciente"));
        paciente.setProcedencia(resultSet.getString("procedencia_paciente"));
        paciente.setOcupacion(resultSet.getString("ocupacion_paciente"));
        paciente.setSexo(resultSet.getString("sexo_paciente"));
        paciente.setFechaNacimiento(resultSet.getDate("fecha_nacimiento_paciente"));
        paciente.setEdad(resultSet.getInt("edad"));
        paciente.setReligion(resultSet.getString("religion"));
        paciente.setDomicilio(resultSet.getString("domicilio"));
        paciente.setTelefono(resultSet.getString("telefono"));
        paciente.setIdMedico(resultSet.getInt("id_medico"));
        paciente.setIdTipoSangre(resultSet.getInt("id_tipo_sangre"));
        paciente.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        paciente.setFechaModificaciones(resultSet.getTimestamp("fecha_modificaciones"));
        paciente.setTipoSangre(resultSet.getString("tipo_sangre"));
        paciente.setNombreMedico(resultSet.getString("medico"));
        paciente.setIdfolio(resultSet.getInt("id_folio"));
        paciente.setFolio(resultSet.getString("folio"));
        paciente.setId_estatus_hospitalizacion(resultSet.getInt("id_estatus_hospitalizacion"));
        paciente.setUrgencias(resultSet.getBoolean("urgencias"));
        return paciente;
    }

    private Paciente mapeadoTablaPrincipal(ResultSet resultSet) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(resultSet.getInt("id_paciente"));
        paciente.setNombre(resultSet.getString("nombre_paciente"));
        paciente.setTelefono(resultSet.getString("telefono"));
        paciente.setIdMedico(resultSet.getInt("id_medico"));
        paciente.setNombreMedico(resultSet.getString("nombre"));
        paciente.setIdfolio(resultSet.getInt("id_folio"));
        paciente.setId_estatus_hospitalizacion(resultSet.getInt("id_estatus_hospitalizacion"));

        return paciente;
    }

    public List<Paciente> llenadoTablaPrinciapal() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT p.id_paciente, p.nombre_paciente, p.telefono, p.id_medico, p.id_folio, m.id_medico, m.nombre, f.id_estatus_hospitalizacion\n"
                + "  FROM pacientes p INNER JOIN medicos m ON p.id_medico = m.id_medico \n"
                + "  INNER JOIN folios f ON f.id = p.id_folio\n"
                + "  WHERE f.id_estatus_hospitalizacion < 2;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Paciente paciente = mapeadoTablaPrincipal(resultSet);
            pacientes.add(paciente);
        }

        resultSet.close();

        return pacientes;

    }

    public List<Paciente> LlenarTabloporFiltrodePacientes(int filtro) throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();

        try (CallableStatement stm = connection.prepareCall("{call FILTRARLISTASDEPACIENTES(?)}");) {
            stm.setInt(1, filtro);
            stm.execute();

            ResultSet resultSet = stm.getResultSet();

            while (resultSet.next()) {
                Paciente paciente = mapeadoTablaPrincipal(resultSet);
                pacientes.add(paciente);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println("algo salio mal");
            e.printStackTrace();
        }

        return pacientes;

    }

    public int insertarPacienteDatosEsenciales(Paciente paciente) throws SQLException {
        String query = "INSERT INTO pacientes (curp, nombre_paciente, procedencia_paciente, ocupacion_paciente, sexo_paciente, fecha_nacimiento_paciente, edad, religion, domicilio, telefono, id_medico, id_tipo_sangre, usuario_modificacion, fecha_modificaciones, id_folio) VALUES (?, ?, ?, ?, ?, CURDATE(), ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
        int idPaciente = 0;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, "POR INGRESAR");
            statement.setString(2, paciente.getNombre());
            statement.setString(3, "POR INGRESAR");
            statement.setString(4, "POR INGRESAR");
            statement.setString(5, "POR INGRESAR");
            statement.setInt(6, 0);
            statement.setString(7, "POR INGRESAR");
            statement.setString(8, "POR INGRESAR");
            statement.setString(9, paciente.getTelefono());
            statement.setInt(10, paciente.getIdMedico());
            statement.setInt(11, 1);
            statement.setInt(12, vpmedicaplaza.VPMedicaPlaza.userSystem);
            statement.setInt(13, 0);

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                idPaciente = generatedKeys.getInt(1);
            }
        }
        return idPaciente;
    }

    public void actualizarFolioPaciente(int idPaciente, int idFolio) throws SQLException {
        String query = "UPDATE pacientes p SET p.id_folio = ? WHERE p.id_paciente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);
            statement.setInt(2, idPaciente);
            statement.executeUpdate();
        }
    }

    public void actualizarPaciente2(Paciente paciente) throws SQLException {
        String query = "UPDATE pacientes SET curp = ?, nombre_paciente = ?, procedencia_paciente = ?, ocupacion_paciente = ?, sexo_paciente = ?, fecha_nacimiento_paciente = ?, edad = ?, religion = ?, domicilio = ?, telefono = ?, id_medico = ?, id_tipo_sangre = ?, usuario_modificacion = ?, fecha_modificaciones = NOW() WHERE id_paciente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, paciente.getCurp());
            statement.setString(2, paciente.getNombre());
            statement.setString(3, paciente.getProcedencia());
            statement.setString(4, paciente.getOcupacion());
            statement.setString(5, paciente.getSexo());
            statement.setDate(6, paciente.getFechaNacimiento());
            statement.setInt(7, paciente.getEdad());
            statement.setString(8, paciente.getReligion());
            statement.setString(9, paciente.getDomicilio());
            statement.setString(10, paciente.getTelefono());
            statement.setInt(11, paciente.getIdMedico());
            statement.setInt(12, paciente.getIdTipoSangre());
            statement.setInt(13, vpmedicaplaza.VPMedicaPlaza.userSystem);
            statement.setInt(14, paciente.getIdPaciente());

            statement.executeUpdate();
        }
    }

    private Paciente mapeoPacienteIndicas(ResultSet resultSet) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setIdPaciente(resultSet.getInt("id_paciente"));
        paciente.setIdfolio(resultSet.getInt("id_folio"));
        paciente.setNombre(resultSet.getString("nombre_paciente"));
        paciente.setTipoHabitacion(resultSet.getString("tipo"));
        paciente.setHabitacion(resultSet.getString("habitacion"));
        paciente.setFolio(resultSet.getString("folio"));
        paciente.setId_paquete(resultSet.getInt("id_paquete"));
        return paciente;
    }
}
