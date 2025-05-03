/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Indicasp;
import com.sun.javafx.scene.control.skin.VirtualFlow;
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
public class IndicaspDAO {

    private Connection connection;

    public IndicaspDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<Indicasp> TraerIndicasPaciente(){
        
        List<Indicasp> listaIndicasporHabitacion = new ArrayList<>();
        
        
        try (CallableStatement stm = connection.prepareCall("{CALL INDICACIONES_POR_PACIENTES_EN_HABITACIONES ()}")) {
           
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while(rs.next()){
                listaIndicasporHabitacion.add(mapearIndicasporHabitacion(rs));
            }

          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaIndicasporHabitacion;
    
    }
    
    public List<Indicasp> traerStrIndicasPorFolioPacienteConPorcentaje(int id_folio){
        List<Indicasp> listaIndicasporHabitacion = new ArrayList<>();
        
        
        try (CallableStatement stm = connection.prepareCall("{CALL STR_INDICAS_POR_FOLIO_PACIENTE_CON_PORCENTAJE (?)}")) {
            //Aqui hace falta agregar cosas
           
            stm.execute();
            ResultSet rs = stm.getResultSet();

            while(rs.next()){
                listaIndicasporHabitacion.add(mapearIndicasporHabitacion(rs));
            }

          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listaIndicasporHabitacion;
    }
    
    private Indicasp mapearIndicasporHabitacion(ResultSet rs) throws SQLException{
        Indicasp indicap = new Indicasp();
        
        indicap.setIdPaciente(rs.getInt("id_paciente"));
        indicap.setNumero_habitacion(rs.getInt("numero_habitacion"));
        indicap.setNombrePaciente(rs.getString("nombre_paciente"));
        indicap.setArea(rs.getString("area"));
        indicap.setIdIndicasp(rs.getInt("id_indicasp"));
        indicap.setEstatusIndica(rs.getInt("estatus_indica"));
        indicap.setValidacionString(rs.getString("validacion"));
        indicap.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        indicap.setString_Estatus_Indica(rs.getString("string_estatus_indica"));
        indicap.setValidacion(rs.getInt("id_estatus_validacion"));
        indicap.setPorcentaje(rs.getDouble("porcentaje_completado_fila"));
       
        return indicap;
    }
    
       public List<Indicasp> getAllIndicaspByFolioSinidEstatus(int id_folio) throws SQLException {
        List<Indicasp> indicasps = new ArrayList<>();

        String query = "{call VerIndicasP_Id_Folio (?)}";

        try (CallableStatement stm = connection.prepareCall(query)) {
            stm.setInt(1, id_folio);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            
                while (rs.next()) {
                    Indicasp indicaDetalle = mapearIndicasPconValidacion(rs);
                    indicasps.add(indicaDetalle);
                }
            
        }

        return indicasps;
    }
       
     

    public int create(Indicasp indicasp) {
        String query = "INSERT INTO indicasp (id_paciente, id_folio, id_usuario_solicitud, id_usuario_entrega, estatus_indica, id_usuario_modificacion, id_area) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, indicasp.getIdPaciente());
            statement.setInt(2, indicasp.getIdFolio());
            statement.setInt(3, indicasp.getIdUsuarioSolicitud());
            statement.setInt(4, indicasp.getIdUsuarioEntrega());
            statement.setInt(5, 0);
            statement.setInt(6, indicasp.getIdUsuarioModificacion());
            statement.setInt(7, indicasp.getId_area());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Devuelve el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Devuelve -1 si hubo un error
    }

    public Indicasp read(int idIndicasp) {
        String query = "SELECT * FROM indicasp WHERE id_indicasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicasp);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createIndicaspFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Indicasp indicasp) {
        String query = "UPDATE indicasp SET id_paciente = ?, id_folio = ?, id_usuario_solicitud = ?, "
                + "id_usuario_entrega = ?, estatus_indica = ?, id_usuario_modificacion = ? WHERE id_indicasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, indicasp.getIdPaciente());
            statement.setInt(2, indicasp.getIdFolio());
            statement.setInt(3, indicasp.getIdUsuarioSolicitud());
            statement.setInt(4, indicasp.getIdUsuarioEntrega());
            statement.setInt(5, indicasp.getEstatusIndica());
            statement.setInt(6, indicasp.getIdUsuarioModificacion());
            statement.setInt(7, indicasp.getIdIndicasp());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateValidacion(int id_indicasP) {
        String query = "UPDATE indicasp i SET i.id_estatus_validacion = ?, i.id_usuario_modificacion = ? WHERE i.id_indicasp = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, true);
            statement.setInt(2, VPMedicaPlaza.userSystem);
            statement.setInt(3, id_indicasP);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int idIndicasp) {
        String query = "DELETE FROM indicasp WHERE id_indicasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicasp);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Indicasp> getAllIndicasp() {
        List<Indicasp> indicasps = new ArrayList<>();

        String query = "SELECT * FROM indicasp";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Indicasp indicasp = createIndicaspFromResultSet(resultSet);
                indicasps.add(indicasp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return indicasps;
    }

    public Indicasp getIndicasp(int id) throws SQLException {
        Indicasp indicasp = new Indicasp();

        String query = "SELECT * FROM indicasp WHERE = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    indicasp = createIndicaspFromResultSet(resultSet);
                }
            }

            return indicasp;
        }
    }

    

    public List<Indicasp> getAllIndicaspByFolio(int id_folio) throws SQLException {
        List<Indicasp> indicasps = new ArrayList<>();

        String query = "SELECT * FROM indicasp i WHERE i.id_folio = ? AND i.estatus_indica = 0 ORDER BY i.id_indicasp;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Indicasp indicaDetalle = createIndicaspFromResultSet(resultSet);
                    indicasps.add(indicaDetalle);
                }
            }
        }

        return indicasps;
    }

 

    public List<Indicasp> getAllIndicaspByFolioSinidEstatusIgualA0(int id_folio) throws SQLException {
        List<Indicasp> indicasps = new ArrayList<>();

        String query = "SELECT i.*, a.nombre_area FROM indicasp i INNER JOIN areas a ON i.id_area = a.id_area WHERE i.id_folio = ? AND i.estatus_indica = 0 ORDER BY i.id_indicasp;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Indicasp indicaDetalle = createIndicaspFromResultSetConNombreArea(resultSet);
                    indicasps.add(indicaDetalle);
                }
            }
        }

        return indicasps;
    }

    public List<Indicasp> obtenertodaslasindicasporidpaciente(int id_pacientee) throws SQLException {
        List<Indicasp> listadeindicasp = new ArrayList<>();

        try (CallableStatement stm = connection.prepareCall("{call TODASINDICASPORPACIENTE(?)}");) {
            stm.setInt(1, id_pacientee);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while (rs.next()) {
                Indicasp indicaDetalle = createIndicaspFromResultSetConNombreArea(rs);
                listadeindicasp.add(indicaDetalle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listadeindicasp;
    }

    public List<Indicasp> getAllIndicaspByFolioSinidEstatusIgualA0ConArea(int id_folio, int id_area) throws SQLException {
        List<Indicasp> indicasps = new ArrayList<>();

        String query = "SELECT i.*, a.nombre_area "
                + "FROM indicasp i "
                + "INNER JOIN areas a ON i.id_area = a.id_area "
                + "WHERE i.id_folio = ? AND i.id_area = ? AND i.estatus_indica = 0 ORDER BY i.id_indicasp;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);
            statement.setInt(2, id_area);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Indicasp indicaDetalle = createIndicaspFromResultSetConNombreArea(resultSet);
                    indicasps.add(indicaDetalle);
                }
            }
        }

        return indicasps;
    }

    public List<Indicasp> indicasPEstatus(int id_folio) throws SQLException {
        List<Indicasp> indicasps = new ArrayList<>();

        String query = "SELECT "
                + "indet.id_indicasp,"
                + "COALESCE( "
                + "    CASE "
                + "        WHEN IF(SUM(1) = SUM(indet.id_estatus_indica_detalle), 1, 0) = 1 THEN 1 "
                + "        WHEN SUM(2) >= SUM(indet.id_estatus_indica_detalle) AND SUM(1) < SUM(indet.id_estatus_indica_detalle) THEN 2 "
                + "        WHEN IF(SUM(3) = SUM(indet.id_estatus_indica_detalle), 3, 0) = 3 THEN 3 "
                + "    END, 0 "
                + ") AS estatus "
                + "FROM indicas_detalles indet "
                + "INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp "
                + "WHERE i.id_folio = ? AND i.estatus_indica = 0 "
                + "GROUP BY indet.id_indicasp ORDER BY indet.id_indicasp;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Indicasp indicaDetalle = createIndicaspEstatus(resultSet);
                    indicasps.add(indicaDetalle);
                }
            }
        }

        return indicasps;
    }

    public Indicasp indicasPEstatusSinLista(int idIndicaP) throws SQLException {   //ahora hace la busqueda por id_inidcaP
        Indicasp indicasps = new Indicasp();

        String query = "SELECT "
                + "indet.id_indicasp,"
                + "COALESCE( "
                + "    CASE "
                + "        WHEN IF(SUM(1) = SUM(indet.id_estatus_indica_detalle), 1, 0) = 1 THEN 1 "
                + "        WHEN SUM(2) >= SUM(indet.id_estatus_indica_detalle) AND SUM(1) < SUM(indet.id_estatus_indica_detalle) THEN 2 "
                + "        WHEN IF(SUM(3) = SUM(indet.id_estatus_indica_detalle), 3, 0) = 3 THEN 3 "
                + "    END, 0 "
                + ") AS estatus "
                + "FROM indicas_detalles indet "
                + "INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp "
                + "WHERE i.id_indicasp = ? AND i.estatus_indica = 0 "
                + "GROUP BY indet.id_indicasp ORDER BY indet.id_indicasp;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicaP);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    indicasps = createIndicaspEstatus(resultSet);

                    indicasps.toString();

                }
            }
        }

        return indicasps;
    }

    public int indicasPSize(int folioPaciente) throws SQLException {
        String query = "SELECT COUNT(i.id_indicasp) AS size FROM indicasp i WHERE i.id_folio = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, folioPaciente);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("size");
                } else {
                    return 0;
                }
            }
        }
    }

    public int indicasPValidados(int folioPaciente) throws SQLException {
        String query = "SELECT COUNT(i.id_indicasp) AS validado FROM indicasp i WHERE i.id_folio = ? AND i.id_estatus_validacion = TRUE;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, folioPaciente);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("validado");
                } else {
                    return 0;
                }
            }
        }
    }

    private Indicasp createIndicaspFromResultSet(ResultSet resultSet) throws SQLException {
        Indicasp indicasp = new Indicasp();
        indicasp.setIdIndicasp(resultSet.getInt("id_indicasp"));
        indicasp.setIdPaciente(resultSet.getInt("id_paciente"));
        indicasp.setIdFolio(resultSet.getInt("id_folio"));
        indicasp.setIdUsuarioSolicitud(resultSet.getInt("id_usuario_solicitud"));
        indicasp.setIdUsuarioEntrega(resultSet.getInt("id_usuario_entrega"));
        indicasp.setFechaEntrega(resultSet.getTimestamp("fecha_entrega"));
        indicasp.setEstatusIndica(resultSet.getInt("estatus_indica"));
        indicasp.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        indicasp.setFechaCreacion(resultSet.getTimestamp("fecha_creacion"));
        indicasp.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));

        return indicasp;
    }

    private Indicasp createIndicaspFromResultSetConNombreArea(ResultSet resultSet) throws SQLException {
        Indicasp indicasp = new Indicasp();
        indicasp.setIdIndicasp(resultSet.getInt("id_indicasp"));
        indicasp.setIdPaciente(resultSet.getInt("id_paciente"));
        indicasp.setIdFolio(resultSet.getInt("id_folio"));
        indicasp.setIdUsuarioSolicitud(resultSet.getInt("id_usuario_solicitud"));
        indicasp.setIdUsuarioEntrega(resultSet.getInt("id_usuario_entrega"));
        indicasp.setFechaEntrega(resultSet.getTimestamp("fecha_entrega"));
        indicasp.setEstatusIndica(resultSet.getInt("estatus_indica"));
        indicasp.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        indicasp.setFechaCreacion(resultSet.getTimestamp("fecha_creacion"));
        indicasp.setFechaModificacion(resultSet.getTimestamp("fecha_modificacion"));
        indicasp.setArea(resultSet.getString("nombre_area"));

        return indicasp;
    }

    private Indicasp createIndicaspEstatus(ResultSet resultSet) throws SQLException {
        Indicasp indicasp = new Indicasp();
        indicasp.setIdIndicasp(resultSet.getInt("id_indicasp"));
        indicasp.setEstatus(resultSet.getInt("estatus"));

        return indicasp;
    }
     private Indicasp mapearIndicasPconValidacion(ResultSet  rs) throws SQLException{
          Indicasp indicaP = new Indicasp();
            
          indicaP.setIdIndicasp(rs.getInt("id_indicasp"));
          indicaP.setEstatusIndica(rs.getInt("estatus_indica"));
          indicaP.setArea(rs.getString("nombre_area"));
          indicaP.setValidacion(rs.getInt("id_estatus_validacion"));
          
          return indicaP;
          
          
      }
}
