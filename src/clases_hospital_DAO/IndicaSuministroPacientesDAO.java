/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.IndicaSuministroPacientes;
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
public class IndicaSuministroPacientesDAO {

    private Connection connection;

    public IndicaSuministroPacientesDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(IndicaSuministroPacientes indicaSuministroPacientes) {
        String query = "INSERT INTO indicas_suministro_pacientes (id_indica_detalle, id_folio, id_insimo, suministro, devolucion, precio_unitario, precio_unitario_paquete, usuario_creacion, id_area, usuario_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, indicaSuministroPacientes.getIdIndicaDetalle());
            statement.setInt(2, indicaSuministroPacientes.getIdFolio());
            statement.setInt(3, indicaSuministroPacientes.getIdInsimo());
            statement.setBoolean(4, indicaSuministroPacientes.isSuministro());
            statement.setBoolean(5, indicaSuministroPacientes.isDevolucion());
            statement.setDouble(6, indicaSuministroPacientes.getPrecioUnitario());
            statement.setDouble(7, indicaSuministroPacientes.getPrecioUnitarioPaquete());
            statement.setInt(8, indicaSuministroPacientes.getUsuarioCreacion());
            statement.setInt(9, indicaSuministroPacientes.getId_area());
            statement.setInt(10, indicaSuministroPacientes.getUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                indicaSuministroPacientes.setIdIndicaSuministroPacientes(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public IndicaSuministroPacientes read(int idIndicaSuministroPacientes) {
        String query = "SELECT * FROM indicas_suministro_pacientes WHERE id_indicas_suministro_pacientes = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicaSuministroPacientes);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createIndicaSuministroPacientesFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(IndicaSuministroPacientes indicaSuministroPacientes) {
        String query = "UPDATE indicas_suministro_pacientes SET id_indica_detalle = ?, id_folio = ?, id_insimo = ?, "
                + "suministro = ?, devolucion = ?, usuario_creacion = ?, usuario_modificacion = ? "
                + "WHERE id_indicas_suministro_pacientes = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, indicaSuministroPacientes.getIdIndicaDetalle());
            statement.setInt(2, indicaSuministroPacientes.getIdFolio());
            statement.setInt(3, indicaSuministroPacientes.getIdInsimo());
            statement.setBoolean(4, indicaSuministroPacientes.isSuministro());
            statement.setBoolean(5, indicaSuministroPacientes.isDevolucion());
            statement.setInt(6, indicaSuministroPacientes.getUsuarioCreacion());
            statement.setInt(7, indicaSuministroPacientes.getUsuarioModificacion());
            statement.setInt(8, indicaSuministroPacientes.getIdIndicaSuministroPacientes());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateIndicasSuministroPaciente(int idindicasP){
        String query = "UPDATE indicas_suministro_pacientes isp INNER JOIN indicas_detalles ide ON isp.id_indica_detalle = ide.id_indica_detalle  SET isp.suministro = 0, isp.devolucion = 0 WHERE ide.id_indicasp = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idindicasP);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateSuministro(IndicaSuministroPacientes indicaSuministroPacientes) {
        String query = "UPDATE indicas_suministro_pacientes SET suministro = ?, devolucion = ?, usuario_modificacion = ?, fecha_modificacion = NOW() WHERE id_indicas_suministro_pacientes = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, indicaSuministroPacientes.isSuministro());
            statement.setBoolean(2, indicaSuministroPacientes.isDevolucion());
            statement.setInt(3, indicaSuministroPacientes.getUsuarioModificacion());
            statement.setInt(4, indicaSuministroPacientes.getIdIndicaSuministroPacientes());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //ESTOY TRABAJANDO EN DEVOLUCION DE INSUMOS PARA QUE NO TE OLVIDES DE LO QUE ESTABAS HACIENDO.
    public void updateDevolucion(int idindicaDetalle) {
        String query = "UPDATE indicas_suministro_pacientes SET devolucion = ?, usuario_modificacion = ?, fecha_modificacion = NOW() WHERE suministro = 0 AND id_indica_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, true);
            statement.setInt(2, VPMedicaPlaza.userSystem);
            statement.setInt(3, idindicaDetalle);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int idIndicaSuministroPacientes) {
        String query = "DELETE FROM indicas_suministro_pacientes WHERE id_indicas_suministro_pacientes = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicaSuministroPacientes);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<IndicaSuministroPacientes> getAllIndicaSuministroPacientes() {
        List<IndicaSuministroPacientes> indicaSuministroPacientesList = new ArrayList<>();

        String query = "SELECT * FROM indicas_suministro_pacientes";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                IndicaSuministroPacientes indicaSuministroPacientes = createIndicaSuministroPacientesFromResultSet(resultSet);
                indicaSuministroPacientesList.add(indicaSuministroPacientes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return indicaSuministroPacientesList;
    }
    
    public List<IndicaSuministroPacientes> getAllIndicaSuministroPacientesesFromFolio(int id_folio) throws SQLException{
        List<IndicaSuministroPacientes> indicaSuministroPacientesList = new ArrayList<>();
        
        String query = "SELECT isp.id_indicas_suministro_pacientes, isp.id_insimo, isp.id_indica_detalle, isp.suministro, isp.devolucion, i.nombre, idet.id_indicasp AS indica_folio FROM indicas_suministro_pacientes isp LEFT JOIN indicas_detalles idet ON isp.id_indica_detalle = idet.id_indica_detalle LEFT JOIN insumos i ON isp.id_insimo = i.id WHERE isp.id_folio = ? AND (isp.suministro = 0 AND isp.devolucion = 0)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    indicaSuministroPacientesList.add(createIndicaSuministroPacientesFromResultSetConNombre(resultSet));
                }
            }
        }

        return indicaSuministroPacientesList;
    }
    
    public List<IndicaSuministroPacientes> getAllIndicaSuministroPacientesesFromIndicaP(int idIndicaP) throws SQLException{
        List<IndicaSuministroPacientes> indicaSuministroPacientesList = new ArrayList<>();
        
        String query = "SELECT isp.id_indicas_suministro_pacientes, isp.id_insimo, isp.id_indica_detalle, isp.suministro, isp.devolucion, i.nombre, idet.id_indicasp AS indica_folio FROM indicas_suministro_pacientes isp LEFT JOIN indicas_detalles idet ON isp.id_indica_detalle = idet.id_indica_detalle LEFT JOIN insumos i ON isp.id_insimo = i.id WHERE idet.id_indicasp = ? -- AND (isp.suministro = 0 AND isp.devolucion = 0)"; // Le quuite la parte final del scrip para revisar por que falla
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicaP);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    indicaSuministroPacientesList.add(createIndicaSuministroPacientesFromResultSetConNombre(resultSet));
                }
            }
        }

        return indicaSuministroPacientesList;
    }
    
    private IndicaSuministroPacientes createIndicaSuministroPacientesFromResultSet(ResultSet resultSet) throws SQLException {
        IndicaSuministroPacientes indicaSuministroPacientes = new IndicaSuministroPacientes();
        indicaSuministroPacientes.setIdIndicaSuministroPacientes(resultSet.getInt("id_indicas_suministro_pacientes"));
        indicaSuministroPacientes.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaSuministroPacientes.setIdFolio(resultSet.getInt("id_folio"));
        indicaSuministroPacientes.setIdInsimo(resultSet.getInt("id_insimo"));
        indicaSuministroPacientes.setSuministro(resultSet.getBoolean("suministro"));
        indicaSuministroPacientes.setDevolucion(resultSet.getBoolean("devolucion"));
        indicaSuministroPacientes.setUsuarioCreacion(resultSet.getInt("usuario_creacion"));
        indicaSuministroPacientes.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));

        return indicaSuministroPacientes;
    }
    
    private IndicaSuministroPacientes createIndicaSuministroPacientesFromResultSetConNombre(ResultSet resultSet) throws SQLException {
        IndicaSuministroPacientes indicaSuministroPacientes = new IndicaSuministroPacientes();
        indicaSuministroPacientes.setIdIndicaSuministroPacientes(resultSet.getInt("id_indicas_suministro_pacientes"));
        indicaSuministroPacientes.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaSuministroPacientes.setIdInsimo(resultSet.getInt("id_insimo"));
        indicaSuministroPacientes.setSuministro(resultSet.getBoolean("suministro"));
        indicaSuministroPacientes.setDevolucion(resultSet.getBoolean("devolucion"));
        indicaSuministroPacientes.setNombre_insumo(resultSet.getString("nombre"));
        indicaSuministroPacientes.setId_indica_p(resultSet.getInt("indica_folio"));

        return indicaSuministroPacientes;
    }
}
