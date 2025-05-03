/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.IndicaDetalleLista;
import clases_hospital.IndicaDetalle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class IndicaDetalleDAO {
      Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    private Connection connection;

    public IndicaDetalleDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(IndicaDetalle indicaDetalle) {
        String query = "INSERT INTO indicas_detalles (id_indicasp, id_insumo, cantidad_entregada, cantidad_suministrada, cantidad_devolucion, id_usuario_creacion, id_usaurio_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, indicaDetalle.getIdIndicasp());
            statement.setInt(2, indicaDetalle.getIdInsumo());
            statement.setDouble(3, indicaDetalle.getCantidadEntregada());
            statement.setDouble(4, indicaDetalle.getCantidadSuministrada());
            statement.setDouble(5, indicaDetalle.getCantidadDevolucion());
            statement.setInt(6, indicaDetalle.getIdUsuarioCreacion());
            statement.setInt(7, indicaDetalle.getIdUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                indicaDetalle.setIdIndicaDetalle(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarDevolucionACero(int idIndicadetalle, int idInsumo)throws SQLException{
        String query = "UPDATE indicas_detalles SET cantidad_suministrada = 0, cantidad_devolucion = 0, id_estatus_indica_detalle = 0, id_usaurio_modificacion = ? WHERE id_indica_detalle = ? AND id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, VPMedicaPlaza.userSystem);
            statement.setInt(2, idIndicadetalle);
            statement.setInt(3, idInsumo);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarSuministroDevolucion(IndicaDetalle indicaDetalle)throws SQLException{
        String query = "UPDATE indicas_detalles indet SET indet.cantidad_suministrada = ?, indet.cantidad_devolucion = ?, indet.id_usaurio_modificacion = ? WHERE indet.id_indica_detalle = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, indicaDetalle.getCantidadSuministrada());
            statement.setDouble(2, indicaDetalle.getCantidadDevolucion());
            statement.setInt(3, VPMedicaPlaza.userSystem);
            statement.setInt(4, indicaDetalle.getIdIndicaDetalle());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public List<IndicaDetalle> traerlstaparadovolver(int id_indicasp) throws SQLException{
            List<IndicaDetalle> listaparadevolver = new ArrayList<>();
            try(CallableStatement stm = connection.prepareCall("{ call SELECCIONARINDICASDETALLEPARADEVOLUCION(?)}")){
                stm.setInt(1, id_indicasp);
                stm.execute();
                ResultSet rs = stm.getResultSet();
                while(rs.next()){
                    IndicaDetalle indicaadetalle = new IndicaDetalle();
                    indicaadetalle.setIdIndicaDetalle(rs.getInt("id_indica_detalle"));
                    indicaadetalle.setIdInsumo(rs.getInt("id_insumo"));
                    indicaadetalle.setNombreInsumo(rs.getString("cantidad_entregada"));
                    indicaadetalle.setCantidadEntregada(rs.getDouble("cantidad_entregada"));
                    indicaadetalle.setCantidadSuministrada(rs.getInt("suministro"));
                    indicaadetalle.setCantidadDevolucion(rs.getInt("devolucion"));
                    listaparadevolver.add(indicaadetalle);
                }
               
            } catch(SQLException e){
                System.out.println("algo salio mal");
                e.printStackTrace();
                        
                     }
            
            
        
        return listaparadevolver;
    } 
    
    public int createIndica(IndicaDetalle indicaDetalle) {
        String query = "INSERT INTO indicas_detalles (id_indicasp, id_insumo, cantidad_entregada, cantidad_suministrada, cantidad_devolucion, precio_unitario_paquete, precio_unitario, paquete, id_usuario_creacion, id_usaurio_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, indicaDetalle.getIdIndicasp());
            statement.setInt(2, indicaDetalle.getIdInsumo());
            statement.setDouble(3, indicaDetalle.getCantidadEntregada());
            statement.setDouble(4, 0);
            statement.setDouble(5, 0);
            statement.setDouble(6, indicaDetalle.getPrecioUnitario());
            statement.setDouble(7, indicaDetalle.getPrecioUnitarioPaquete());
            statement.setBoolean(8, indicaDetalle.isPaquete());
            statement.setInt(9, indicaDetalle.getIdUsuarioCreacion());
            statement.setInt(10, indicaDetalle.getIdUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    public int CREARINDICA_CON_LOTE(IndicaDetalle indicaDetalle,int id_mov_pa) throws SQLException{
        int id = 0;
        try(CallableStatement stm = connection.prepareCall("{call GENERAR_INDICA_DETALLE_CON_LOTE(?,?,?,?,?,?,?,?,?)}")){
            stm.setInt(1, indicaDetalle.getIdIndicasp());
            stm.setInt(2, indicaDetalle.getIdInsumo());
            stm.setDouble(3, indicaDetalle.getCantidadEntregada());
            stm.setDouble(4, indicaDetalle.getPrecioUnitario());
            stm.setDouble(5, indicaDetalle.getPrecioUnitarioPaquete());
            stm.setBoolean(6, indicaDetalle.isPaquete());
            stm.setInt(7, indicaDetalle.getIdUsuarioCreacion());
            stm.setString(8, indicaDetalle.getLote());
            stm.setInt(9, id_mov_pa);
            
            stm.execute();
            
            ResultSet rs = stm.getResultSet();
            if(rs.next()){
                id = rs.getInt("id");
            }
            else{
                id = -1;
            }
            
            
        } catch(SQLException e){
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setTitle("ERROR!");
            alertaError.setContentText("ERROR: "+ e.getErrorCode());
            alertaError.showAndWait();
            
        }
        
        
        return id;
    }

    public IndicaDetalle read(int idIndicaDetalle) {
        String query = "SELECT * FROM indicas_detalles WHERE id_indica_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicaDetalle);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createIndicaDetalleFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setTitle("ERROR!");
            alertaError.setContentText("ERROR: "+ e.getErrorCode());
            alertaError.showAndWait();
        }
        return null;
    }

    public void update(IndicaDetalle indicaDetalle) {
        
        String query = "UPDATE indicas_detalles SET id_indicasp = ?, id_insumo = ?, cantidad_entregada = ?, "
                + "cantidad_suministrada = ?, cantidad_devolucion = ?, id_estatus_indica_detalle = ?, id_usuario_creacion = ?, "
                + "id_usaurio_modificacion = ? WHERE id_indica_detalle = ? AND id_insumo = ?";
                double suministro = ( indicaDetalle.getCantidadEntregada() - indicaDetalle.getCantidadDevolucion());
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, indicaDetalle.getIdIndicasp());
            statement.setInt(2, indicaDetalle.getIdInsumo());
            statement.setDouble(3, indicaDetalle.getCantidadEntregada());
            statement.setDouble(4, suministro);
            statement.setDouble(5, indicaDetalle.getCantidadDevolucion());
            statement.setInt(6, indicaDetalle.getIdEstatusIndicaDetalle());
            statement.setInt(7, indicaDetalle.getIdUsuarioCreacion());
            statement.setInt(8, indicaDetalle.getIdUsuarioModificacion());
            statement.setInt(9, indicaDetalle.getIdIndicaDetalle());
            statement.setInt(10, indicaDetalle.getIdInsumo());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setTitle("ERROR!");
            alertaError.setContentText("ERROR: "+ e.getErrorCode());
            alertaError.showAndWait();
        }
    }
    
    public void updateDeshacerIndica(int idIndicaDetalle) {
        
        String query = "UPDATE indicas_detalles ide SET ide.cantidad_suministrada = 0, ide.cantidad_devolucion = 0, ide.id_estatus_indica_detalle = 0 WHERE ide.id_indicasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicaDetalle);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setTitle("ERROR!");
            alertaError.setContentText("ERROR: "+ e.getErrorCode());
            alertaError.showAndWait();
        }
    }

    public void delete(int idIndicaDetalle) {
        String query = "DELETE FROM indicas_detalles WHERE id_indica_detalle = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicaDetalle);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setTitle("ERROR!");
            alertaError.setContentText("ERROR: "+ e.getErrorCode());
            alertaError.showAndWait();
        }
    }

    public List<IndicaDetalle> getAllIndicaDetalles() {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();

        String query = "SELECT * FROM indicas_detalles";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSet(resultSet);
                indicaDetalles.add(indicaDetalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setTitle("ERROR!");
            alertaError.setContentText("ERROR: "+ e.getErrorCode());
            alertaError.showAndWait();
        }

        return indicaDetalles;
    }

    public List<IndicaDetalle> getAllIndicaDetallesbyFolio(int id_folio, int id_indicap) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();

        String query = "SELECT indet.*, insu.nombre FROM indicas_detalles indet INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp INNER JOIN insumos insu ON indet.id_insumo = insu.id WHERE i.id_folio = ? AND i.id_indicasp = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);
            statement.setInt(2, id_indicap);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSetByFolio(resultSet);
                    indicaDetalles.add(indicaDetalle);
                }
            }
        }
        catch(SQLException e){
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setTitle("ERROR!");
            alertaError.setContentText("ERROR: "+ e.getErrorCode());
            alertaError.showAndWait();
        }

        return indicaDetalles;
    }
    
    public List<IndicaDetalle> getAllIndicaDetallesbyFolioR(int id_indicap) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();

        String query = "SELECT isp.id_indica_detalle, indet.id_indicasp, indet.id_insumo, i.nombre, indet.cantidad_entregada, CASE WHEN COUNT(CASE WHEN isp.suministro = 1 THEN 1 END) > 0 THEN COUNT(CASE WHEN isp.suministro = 1 THEN 1 END) ELSE 0 END AS suministro, CASE WHEN COUNT(CASE WHEN isp.devolucion = 1 THEN 1 END) > 0 THEN COUNT(CASE WHEN isp.devolucion = 1 THEN 1 END) ELSE 0 END AS devolucion FROM indicas_suministro_pacientes isp INNER JOIN indicas_detalles indet ON isp.id_indica_detalle = indet.id_indica_detalle INNER JOIN insumos i ON isp.id_insimo = i.id WHERE indet.id_indicasp = ? GROUP BY isp.id_indica_detalle;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_indicap);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSetByFolioR(resultSet);
                    indicaDetalles.add(indicaDetalle);
                }
            }
        }
        catch(SQLException e){
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setTitle("ERROR!");
            alertaError.setContentText("ERROR: "+ e.getErrorCode());
            alertaError.showAndWait();
        }

        return indicaDetalles;
    }
    
    public List<IndicaDetalle> getAllIndicaDetallesbyFolioRSumaData(int id_indicap) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();

        String query = "SELECT indet.id_indica_detalle, indet.id_indicasp, indet.id_insumo, i.nombre, SUM(indet.cantidad_entregada) AS cantidad_entregada, SUM(indet.cantidad_entregada) AS cantidad_entregada, SUM(indet.cantidad_suministrada) AS suministro, SUM(indet.cantidad_devolucion) AS devolucion FROM indicas_detalles indet INNER JOIN insumos i ON indet.id_insumo = i.id WHERE indet.id_indicasp = ? GROUP BY indet.id_insumo;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_indicap);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSetByFolioR(resultSet);
                    indicaDetalles.add(indicaDetalle);
                }
            }
        }

        return indicaDetalles;
    }
    
    public List<IndicaDetalle> getAllIndicaDetallesbyFolioRLote(int id_indicap) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();

        String query = "SELECT isp.id_indica_detalle, indet.id_indicasp, indet.id_insumo, i.nombre, indet.cantidad_entregada, CASE WHEN COUNT(CASE WHEN isp.suministro = 1 THEN 1 END) > 0 THEN COUNT(CASE WHEN isp.suministro = 1 THEN 1 END) ELSE 0 END AS suministro, CASE WHEN COUNT(CASE WHEN isp.devolucion = 1 THEN 1 END) > 0 THEN COUNT(CASE WHEN isp.devolucion = 1 THEN 1 END) ELSE 0 END AS devolucion, indet.lote_insumo FROM indicas_suministro_pacientes isp INNER JOIN indicas_detalles indet ON isp.id_indica_detalle = indet.id_indica_detalle INNER JOIN insumos i ON isp.id_insimo = i.id WHERE indet.id_indicasp = ? GROUP BY isp.id_indica_detalle;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_indicap);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSetByFolioRLote(resultSet);
                    indicaDetalles.add(indicaDetalle);
                }
            }
        }

        return indicaDetalles;
    }

    public List<IndicaDetalle> getAllIndicaDetallesByFolio(int id_folio) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();

        String query = "SELECT * FROM indicas_detalles indet INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE i.id_folio = ? AND i.estatus_indica = 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSet(resultSet);
                    indicaDetalles.add(indicaDetalle); //
                }
            }
        }

        return indicaDetalles;
    }
    
    public List<IndicaDetalle> getAllIndicaDetallesByIndicasP(int id_folio) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();

        String query = "SELECT * FROM indicas_detalles indet INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE indet.id_indicasp = ? AND i.estatus_indica = 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSet(resultSet);
                    indicaDetalles.add(indicaDetalle); //
                }
            }
        }

        return indicaDetalles;
    }

    public List<IndicaDetalle> getAllIndicaDetallesByIndicaP(int idIndicaP) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();

        String query = "SELECT * FROM indicas_detalles indet INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE indet.id_indicasp = ? AND i.estatus_indica = 0 -- ORDER BY indet.id_indica_detalle";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicaP);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSet(resultSet);
                    
                    indicaDetalles.add(indicaDetalle); //
                }
            }
        }

        return indicaDetalles;
    }

    public List<IndicaDetalle> getCantidadInsumosSuministrados(int id_folio) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();
        String query = "SELECT isp.id_indica_detalle, COUNT(isp.id_indicas_suministro_pacientes) AS entregada FROM indicas_suministro_pacientes isp INNER JOIN indicas_detalles indet ON isp.id_indica_detalle = indet.id_indica_detalle INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE isp.suministro = 1 AND isp.id_folio = ? AND i.estatus_indica = 0 GROUP BY isp.id_insimo ORDER BY isp.id_indica_detalle;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaSuministro(resultSet);
                    indicaDetalles.add(indicaDetalle);
                }
            }
        }

        return indicaDetalles;
    }

    public List<IndicaDetalle> getCantidadInsumosSuministradosByIdIndicasP(int idIndicasP) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();
        String query = "SELECT isp.id_indica_detalle, COUNT(isp.id_indicas_suministro_pacientes) AS entregada FROM indicas_suministro_pacientes isp INNER JOIN indicas_detalles indet ON isp.id_indica_detalle = indet.id_indica_detalle INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE isp.suministro = 1 AND indet.id_indicasp = ? AND i.estatus_indica = 0 GROUP BY isp.id_insimo, isp.id_indica_detalle ORDER BY isp.id_indica_detalle;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idIndicasP);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaSuministro(resultSet);
                    indicaDetalles.add(indicaDetalle);
                }
            }
        }

        return indicaDetalles;
    }

    public List<IndicaDetalle> getCantidadInsumosDevolucion(int id_folio) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();
        String query = "SELECT isp.id_indica_detalle, COUNT(isp.id_indicas_suministro_pacientes) AS devuelto FROM indicas_suministro_pacientes isp INNER JOIN indicas_detalles indet ON isp.id_indica_detalle = indet.id_indica_detalle INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE isp.devolucion = 1 AND isp.id_folio = ? AND i.estatus_indica = 0 GROUP BY isp.id_insimo, isp.id_indica_detalle ORDER BY isp.id_indica_detalle;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDevolucion(resultSet);
                    indicaDetalles.add(indicaDetalle);
                }
            }
        }

        return indicaDetalles;
    }
    
    public List<IndicaDetalle> getCantidadInsumosDevolucionPorIdIndicaDetallesint(int id_indicasp) throws SQLException {
        List<IndicaDetalle> indicaDetalles = new ArrayList<>();
        String query = "SELECT isp.id_indica_detalle, COUNT(isp.id_indicas_suministro_pacientes) AS devuelto FROM indicas_suministro_pacientes isp INNER JOIN indicas_detalles indet ON isp.id_indica_detalle = indet.id_indica_detalle INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE isp.devolucion = 1 AND indet.id_indicasp = ? AND i.estatus_indica = 0 GROUP BY isp.id_insimo, isp.id_indica_detalle ORDER BY isp.id_indica_detalle;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_indicasp);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDevolucion(resultSet);
                    indicaDetalles.add(indicaDetalle);
                }
            }
        }

        return indicaDetalles;
    }

    public List<IndicaDetalleLista> getIndicaDetallesLista(int idFolio) throws SQLException {
        List<IndicaDetalleLista> indicaDetalleListas = new ArrayList<>();

        String query = "SELECT indet.id_insumo, CASE WHEN indet.cantidad_suministrada > 0 THEN  SUM(indet.cantidad_suministrada) ELSE SUM(indet.cantidad_entregada) END AS cantidad FROM indicas_detalles indet INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE i.id_folio = ? AND indet.paquete = 1 AND indet.id_estatus_indica_detalle < 3 GROUP BY indet.id_insumo";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalleLista indicaDetallelista = createIndicaDetalleListaFromResultSet(resultSet);
                    indicaDetalleListas.add(indicaDetallelista);
                }
            }
        }

        return indicaDetalleListas;
    }

    public List<IndicaDetalle> getIndicaDetalleDevolucion(int indicasP) throws SQLException {
        List<IndicaDetalle> idnicaDetalleListas = new ArrayList<>();
        String query = "SELECT * FROM indicas_detalles indet INNER JOIN indicasp i ON indet.id_indicasp = i.id_indicasp WHERE i.id_indicasp = ? AND i.estatus_indica = 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, indicasP);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleFromResultSet(resultSet);
                    idnicaDetalleListas.add(indicaDetalle);
                }
            }
        }

        return idnicaDetalleListas;
    }

    public List<IndicaDetalle> getDetallesDevSumDatos(int indicasP) throws SQLException {
      
        List<IndicaDetalle> listaIndicaDetalles = new ArrayList<>();
        String query = "SELECT isp.id_indica_detalle, indet.id_insumo, indet.cantidad_entregada, CASE WHEN COUNT(CASE WHEN isp.suministro = 1 THEN 1 END) > 0 THEN COUNT(CASE WHEN isp.suministro = 1 THEN 1 END) ELSE 0 END AS suministro, CASE WHEN COUNT(CASE WHEN isp.devolucion = 1 THEN 1 END) > 0 THEN COUNT(CASE WHEN isp.devolucion = 1 THEN 1 END) ELSE 0 END AS devolucion FROM indicas_suministro_pacientes isp INNER JOIN indicas_detalles indet ON isp.id_indica_detalle = indet.id_indica_detalle WHERE indet.id_indicasp = ? GROUP BY isp.id_indica_detalle;";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, indicasP);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    IndicaDetalle indicaDetalle = createIndicaDetalleInsumo(resultSet);
                                     listaIndicaDetalles.add(indicaDetalle);
                }
            }
        }

        return listaIndicaDetalles;
    }

    private IndicaDetalle createIndicaDetalleFromResultSet(ResultSet resultSet) throws SQLException {
        IndicaDetalle indicaDetalle = new IndicaDetalle();
        indicaDetalle.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaDetalle.setIdIndicasp(resultSet.getInt("id_indicasp"));
        indicaDetalle.setIdInsumo(resultSet.getInt("id_insumo"));
        indicaDetalle.setCantidadEntregada(resultSet.getDouble("cantidad_entregada"));
        indicaDetalle.setCantidadSuministrada(resultSet.getDouble("cantidad_suministrada"));
        indicaDetalle.setCantidadDevolucion(resultSet.getDouble("cantidad_devolucion"));
        indicaDetalle.setIdUsuarioCreacion(resultSet.getInt("id_usuario_creacion"));
        indicaDetalle.setIdUsuarioModificacion(resultSet.getInt("id_usaurio_modificacion"));
        indicaDetalle.setIdEstatusIndicaDetalle(resultSet.getInt("id_estatus_indica_detalle"));
        indicaDetalle.setPaquete(resultSet.getBoolean("paquete"));

        return indicaDetalle;
    }
    
    private IndicaDetalle createIndicaDetalleFromResultSetByFolio(ResultSet resultSet) throws SQLException {
        IndicaDetalle indicaDetalle = new IndicaDetalle();
        indicaDetalle.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaDetalle.setIdIndicasp(resultSet.getInt("id_indicasp"));
        indicaDetalle.setIdInsumo(resultSet.getInt("id_insumo"));
        indicaDetalle.setCantidadEntregada(resultSet.getDouble("cantidad_entregada"));
        indicaDetalle.setCantidadSuministrada(resultSet.getDouble("cantidad_suministrada"));
        indicaDetalle.setCantidadDevolucion(resultSet.getDouble("cantidad_devolucion"));
        indicaDetalle.setIdUsuarioCreacion(resultSet.getInt("id_usuario_creacion"));
        indicaDetalle.setIdUsuarioModificacion(resultSet.getInt("id_usaurio_modificacion"));
        indicaDetalle.setIdEstatusIndicaDetalle(resultSet.getInt("id_estatus_indica_detalle"));
        indicaDetalle.setNombreInsumo(resultSet.getString("nombre"));

        return indicaDetalle;
    }
    
    //ESTE ES EL RESULTSET QUE TENGO QUE VER
    private IndicaDetalle createIndicaDetalleFromResultSetByFolioR(ResultSet resultSet) throws SQLException {
        IndicaDetalle indicaDetalle = new IndicaDetalle();
        indicaDetalle.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaDetalle.setIdIndicasp(resultSet.getInt("id_indicasp"));
        indicaDetalle.setIdInsumo(resultSet.getInt("id_insumo"));
        indicaDetalle.setCantidadEntregada(resultSet.getDouble("cantidad_entregada"));
        indicaDetalle.setCantidadSuministrada(resultSet.getDouble("suministro"));
        indicaDetalle.setCantidadDevolucion(resultSet.getDouble("devolucion"));
        indicaDetalle.setNombreInsumo(resultSet.getString("nombre"));

        return indicaDetalle;
    }
    
    private IndicaDetalle createIndicaDetalleFromResultSetByFolioRLote(ResultSet resultSet) throws SQLException {
        IndicaDetalle indicaDetalle = new IndicaDetalle();
        indicaDetalle.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaDetalle.setIdIndicasp(resultSet.getInt("id_indicasp"));
        indicaDetalle.setIdInsumo(resultSet.getInt("id_insumo"));
        indicaDetalle.setCantidadEntregada(resultSet.getDouble("cantidad_entregada"));
        indicaDetalle.setCantidadSuministrada(resultSet.getDouble("suministro"));
        indicaDetalle.setCantidadDevolucion(resultSet.getDouble("devolucion"));
        indicaDetalle.setNombreInsumo(resultSet.getString("nombre"));
        indicaDetalle.setLote(resultSet.getString("lote_insumo"));

        return indicaDetalle;
    }

    private IndicaDetalle createIndicaSuministro(ResultSet resultSet) throws SQLException {
        IndicaDetalle indicaDetalle = new IndicaDetalle();
        indicaDetalle.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaDetalle.setSuministrada(resultSet.getDouble("entregada"));

        return indicaDetalle;
    }

    private IndicaDetalle createIndicaDevolucion(ResultSet resultSet) throws SQLException {
        IndicaDetalle indicaDetalle = new IndicaDetalle();
        indicaDetalle.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaDetalle.setDevolucion(resultSet.getDouble("devuelto"));

        return indicaDetalle;
    }

    private IndicaDetalleLista createIndicaDetalleListaFromResultSet(ResultSet resultSet) throws SQLException {
        IndicaDetalleLista indicaDetalleLista = new IndicaDetalleLista();

        indicaDetalleLista.setIdInsumo(resultSet.getInt("id_insumo"));
        indicaDetalleLista.setCantidadInsumo(resultSet.getDouble("cantidad"));

        return indicaDetalleLista;
    }
    
    private IndicaDetalle createIndicaDetalleInsumo(ResultSet resultSet) throws  SQLException{
        IndicaDetalle indicaDetalle = new IndicaDetalle();
        indicaDetalle.setIdIndicaDetalle(resultSet.getInt("id_indica_detalle"));
        indicaDetalle.setIdIndicaDetalle(resultSet.getInt("id_insumo"));
        indicaDetalle.setCantidadEntregada(resultSet.getDouble("cantidad_entregada"));
        indicaDetalle.setCantidadSuministrada(resultSet.getInt("suministro"));
        indicaDetalle.setCantidadDevolucion(resultSet.getInt("devolucion"));
      
        return indicaDetalle;
    }
}
