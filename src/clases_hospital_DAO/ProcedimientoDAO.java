/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Procedimiento;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class ProcedimientoDAO {

    private Connection connection;

    public ProcedimientoDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Procedimiento procedimiento) {
        String query = "INSERT INTO procedimiento (nombre, id_especialidad, id_usuariomodificacion, fechaModificacion, tipo_procedimiento, id_tipo_procedimiento) "
                + "VALUES (?, ?, ?, NOW(), ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, procedimiento.getNombre());
            statement.setInt(2, procedimiento.getId_especialidad());
            statement.setInt(3, procedimiento.getIdUsuarioModificacion());
            statement.setString(4, procedimiento.getTipo_procedimiento());
            statement.setInt(5, procedimiento.getId_tipo_procedimiento());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                procedimiento.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Procedimiento obtenerProcedimientoPorId(int id) {
        String query = "SELECT * FROM procedimiento WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createProcedimientoFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String obtenerIdQuirofano(int idQuirofano, Time inicioTime, Date fechaDate) throws SQLException {
        boolean romper = true;
        String respuesta;
        String query = "SELECT id, nombre FROM quirofanos q INNER JOIN citasquirofano c ON c.id_quirofano = q.id WHERE q.id = ? AND ( ( ? >= c.hora_Inicio AND ? <= c.hora_Fin) ) AND c.fecha_cirugia = ?";
        while (romper) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idQuirofano);
                statement.setTime(2, inicioTime);
                statement.setTime(3, inicioTime);
                statement.setDate(4, fechaDate);

                try (ResultSet resultset = statement.executeQuery()) {
                    if (resultset.next()) {
                        resultset.getInt(1);
                        resultset.getString(2);
                        
//AQUI HAY QUE ARREGLAR ESTO, PARA QUE PUEDA HACER EL METODO --> "optenerIdQuirofano" <-- DE LA CLASE CITAQUIEROFANOCONTROLLER
                    }else{
                        romper = false;
                    }
                }
            }
        }
        return "";
    }

    public void update(Procedimiento procedimiento) {
        String query = "UPDATE procedimiento SET nombre = ?, id_especialidad = ?, id_usuariomodificacion = ?, fechaModificacion = ?, tipo_procedimiento = ?, id_tipo_procedimiento = ? "
                + "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, procedimiento.getNombre());
            statement.setInt(2, procedimiento.getId_especialidad());
            statement.setInt(3, procedimiento.getIdUsuarioModificacion());
            statement.setTimestamp(4, procedimiento.getFechaModificacion());
            statement.setString(5, procedimiento.getTipo_procedimiento());
            statement.setInt(6, procedimiento.getId_tipo_procedimiento());
            statement.setInt(7, procedimiento.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM procedimiento WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Procedimiento> getAllProcedimientos() {
        List<Procedimiento> procedimientos = new ArrayList<>();

        String query = "SELECT * FROM procedimiento";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Procedimiento procedimiento = createProcedimientoFromResultSet(resultSet);
                procedimientos.add(procedimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return procedimientos;
    }

    public String obtenerListaProcedimiento(String listaId) throws SQLException {
        String lista = "";
        String query = "select nombre from paquetesmedicos WHERE id = ?";
        
        String[] idpro = listaId.split(",");
        for (String pro : idpro) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, Integer.parseInt(listaId));
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        lista = lista + rs.getString(1) + " ";
                    }
                }
            }
        }
        return lista;
    }

    private Procedimiento createProcedimientoFromResultSet(ResultSet resultSet) throws SQLException {
        Procedimiento procedimiento = new Procedimiento();
        procedimiento.setId(resultSet.getInt("id"));
        procedimiento.setNombre(resultSet.getString("nombre"));
        procedimiento.setId_especialidad(resultSet.getInt("id_especialidad"));
        procedimiento.setIdUsuarioModificacion(resultSet.getInt("id_usuariomodificacion"));
        procedimiento.setFechaModificacion(resultSet.getTimestamp("fechaModificacion"));
        procedimiento.setTipo_procedimiento(resultSet.getString("tipo_procedimiento"));

        return procedimiento;
    }
}
