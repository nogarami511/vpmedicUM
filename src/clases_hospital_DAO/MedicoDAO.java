/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Medico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author gamae
 */
public class MedicoDAO {

    private Connection connection;

    public MedicoDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarMedico(Medico medico) {
        String sql = "INSERT INTO medicos "
                + "(id_medico, nombre, titulo, cedula, cedula_especialidad, especialidad, certificado, telefono, rfc, direccion, correo, fecha_nacimiento, lugar_nacimiento, curp, id_tipo_medico, id_estatus, id_usuario_modificacion, fecha_modificacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, medico.getId_medico());
            statement.setString(2, medico.getNombre());
            statement.setString(3, medico.getTitulo());
            statement.setString(4, medico.getCedula());
            statement.setString(5, medico.getCedula_especialidad());
            statement.setString(6, medico.getEspecialidad());
            statement.setString(7, medico.getCertificado());
            statement.setString(8, medico.getTelefono());
            statement.setString(9, medico.getRfc());
            statement.setString(10, medico.getDireccion());
            statement.setString(11, medico.getCorreo());
            statement.setDate(12, medico.getFecha_nacimiento());
            statement.setString(13, medico.getLugar_nacimiento());
            statement.setString(14, medico.getCurp());
            statement.setInt(15, medico.getId_tipo());
            statement.setInt(16, medico.getId_estatus());
            statement.setInt(17, VPMedicaPlaza.userSystem);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (por ejemplo, registro en un archivo de log, mostrar un mensaje de error, etc.)
        }
    }

    public void actualizarMedico(Medico medico) throws SQLException {
        String sql = "UPDATE medicos \n"
                + "SET\n"
                + "  nombre = ? -- nombre - VARCHAR(255) NOT NULL\n"
                + " ,titulo = ? -- titulo - VARCHAR(255) NOT NULL\n"
                + " ,cedula = ? -- cedula - VARCHAR(255) NOT NULL\n"
                + " ,cedula_especialidad = ? -- cedula_especialidad - VARCHAR(255) NOT NULL\n"
                + " ,especialidad = ? -- especialidad - VARCHAR(255) NOT NULL\n"
                + " ,certificado = ? -- certificado - VARCHAR(255) NOT NULL\n"
                + " ,telefono = ? -- telefono - VARCHAR(255) NOT NULL\n"
                + " ,rfc = ? -- rfc - VARCHAR(255) NOT NULL\n"
                + " ,direccion = ? -- direccion - VARCHAR(255) NOT NULL\n"
                + " ,correo = ? -- correo - VARCHAR(255) NOT NULL\n"
                + " ,fecha_nacimiento = ? -- fecha_nacimiento - DATE NOT NULL\n"
                + " ,lugar_nacimiento = ? -- lugar_nacimiento - VARCHAR(255) NOT NULL\n"
                + " ,curp = ? -- curp - VARCHAR(255) NOT NULL\n"
                + " ,id_tipo_medico = ? -- id_tipo_medico - INT(11)\n"
                + " ,id_estatus = ? -- id_estatus - INT(11)\n"
                + " ,id_usuario_modificacion = ? -- id_usuario_modificacion - INT(11)\n"
                + " ,fecha_modificacion = NOW() -- fecha_modificacion - DATETIME\n"
                + "WHERE\n"
                + "  id_medico = ? -- id_medico - INT(11) NOT NULL\n"
                + ";";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, medico.getNombre());
            statement.setString(2, medico.getTitulo());
            statement.setString(3, medico.getCedula());
            statement.setString(4, medico.getCedula_especialidad());
            statement.setString(5, medico.getEspecialidad());
            statement.setString(6, medico.getCertificado());
            statement.setString(7, medico.getTelefono());
            statement.setString(8, medico.getRfc());
            statement.setString(9, medico.getDireccion());
            statement.setString(10, medico.getCorreo());
            statement.setDate(11, medico.getFecha_nacimiento());
            statement.setString(12, medico.getLugar_nacimiento());
            statement.setString(13, medico.getCurp());
            statement.setInt(14, medico.getId_tipo());
            statement.setInt(15, 1);//estatus al actualizar no se que enviaba 
            statement.setInt(16, VPMedicaPlaza.userSystem);
            statement.setInt(17, medico.getId_medico());

            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (por ejemplo, registro en un archivo de log, mostrar un mensaje de error, etc.)
        }
    }

    public void update(Medico medico) throws SQLException {
        String query = "UPDATE medicos SET nombre = ?, titulo = ?, cedula = ?, cedula_especialidad = ?, especialidad = ?, certificado = ?, telefono = ?, rfc = ?, direccion = ?, correo = ?, fecha_nacimiento = ?, lugar_nacimiento = ?, curp = ?, id_tipo_medico = ?, id_estatus = ?, id_usuario_modificacion = ?, fecha_modificacion = NOW() WHERE id_medico = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, medico.getNombre());                         //  NOMBRE
            statement.setString(2, medico.getTitulo());                         //  TITULO
            statement.setString(3, medico.getCedula());                         //  CEDULA
            statement.setString(4, medico.getCedula_especialidad());            //  CEDULA_ESPECIALIDAD
            statement.setString(5, medico.getEspecialidad());                   //  ESPECIALIDAD
            statement.setString(6, medico.getCertificado());                    //  CERTIFICACO
            statement.setString(7, medico.getTelefono());                       //  TELEFONO
            statement.setString(8, medico.getRfc());                            //  RFC
            statement.setString(9, medico.getDireccion());                      //  DIRECCION
            statement.setString(10, medico.getCorreo());                        //  CORREO
            statement.setDate(11, medico.getFecha_nacimiento());                //  FECHA DE NACIMIENTO
            statement.setString(12, medico.getLugar_nacimiento());              //  LUGAR DE NACIMIENTO
            statement.setString(13, medico.getCurp());                          //  CURP
            statement.setInt(14, medico.getId_tipo());                          //  ID TIPO MEDICO
            statement.setInt(15, medico.getId_estatus());                       //  ID ESTATUS
            statement.setInt(16, medico.getIdusuariomodificacion());            //  ID USUARIO MODIFICACION
            statement.setInt(17, medico.getId_medico());                        //  ID MEDICO

            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void desactivarMedico(int idMedico) {
        String sql = "UPDATE medicos \n"
                + "SET\n"
                + " id_estatus =?\n"
                + " ,id_usuario_modificacion = ?\n"
                + " ,fecha_modificacion = NOW() \n"
                + "WHERE\n"
                + "  id_medico = ?\n"
                + ";";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 0);
            statement.setInt(2, VPMedicaPlaza.userSystem);
            statement.setInt(3, idMedico);
            statement.executeUpdate();;
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (por ejemplo, registro en un archivo de log, mostrar un mensaje de error, etc.)
        }
    }

    public Medico getById(int idMedico) throws SQLException {
        String query = "SELECT * FROM medicos WHERE id_medico = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, idMedico);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToMedico(resultSet);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return null;
    }

    public Medico leerById(int idMedico) throws SQLException {
        String query = "SELECT * FROM medicos WHERE id_medico = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
          
            statement.setInt(1, idMedico);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Medico medico = mapResultSetToMedico(resultSet);
                    System.err.println(medico.getNombre());
                    return medico;
                }
            }
        }
        return null;
    }

    public List<Medico> getAll() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String query = "SELECT * FROM medicos WHERE id_estatus = 1";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Medico medico = mapResultSetToMedico(resultSet);
                medicos.add(medico);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return medicos;
    }

    private Medico mapResultSetToMedico(ResultSet resultSet) throws SQLException {
        Medico medico = new Medico();
        medico.setId_medico(resultSet.getInt("id_medico"));
        medico.setNombre(resultSet.getString("nombre"));
        medico.setTitulo(resultSet.getString("titulo"));
        medico.setCedula(resultSet.getString("cedula"));
        medico.setCedula_especialidad(resultSet.getString("cedula_especialidad"));
        medico.setEspecialidad(resultSet.getString("especialidad"));
        medico.setCertificado(resultSet.getString("certificado"));
        medico.setTelefono(resultSet.getString("telefono"));
        medico.setRfc(resultSet.getString("rfc"));
        medico.setDireccion(resultSet.getString("direccion"));
        medico.setCorreo(resultSet.getString("correo"));
        medico.setFecha_nacimiento(resultSet.getDate("fecha_nacimiento"));
        medico.setLugar_nacimiento(resultSet.getString("lugar_nacimiento"));
        medico.setCurp(resultSet.getString("curp"));
        medico.setId_tipo(resultSet.getInt("id_tipo_medico"));
        medico.setId_estatus(resultSet.getInt("id_estatus"));
        medico.setIdusuariomodificacion(resultSet.getInt("id_usuario_modificacion"));
        return medico;
    }

    private Medico mapeadoDatosEsenciales(ResultSet resultSet) throws SQLException {
        Medico medico = new Medico();
        medico.setId_medico(resultSet.getInt("id_medico"));
        medico.setNombre(resultSet.getString("nombre"));
        return medico;
    }

    public List<Medico> traerMedicosConDatosEsenciales() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String query = "SELECT id_medico, nombre FROM medicos WHERE id_estatus = 1 ORDER BY nombre ASC";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Medico medico = mapeadoDatosEsenciales(resultSet);
            medicos.add(medico);
        }

        resultSet.close();

        return medicos;

    }

}
