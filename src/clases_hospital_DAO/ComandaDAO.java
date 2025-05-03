/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Comanda;
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
public class ComandaDAO {
    private Connection connection;

    public ComandaDAO(Connection connection) {
        this.connection = connection;
    }
    
    

    public void create(Comanda comanda) throws SQLException {
        String query = "INSERT INTO comandas (folio, fecha, id_cliente, cliente, cantidad_productos, subtotal, iva, total, recibe, observacion, id_estatus, id_usuario_modificacion, fecha_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, comanda.getFolio());
            statement.setDate(2, comanda.getFecha());
            statement.setInt(3, comanda.getIdCliente());
            statement.setString(4, comanda.getCliente());
            statement.setInt(5, comanda.getCantidadProductos());
            statement.setDouble(6, comanda.getSubtotal());
            statement.setDouble(7, comanda.getIva());
            statement.setDouble(8, comanda.getTotal());
            statement.setString(9, comanda.getRecibe());
            statement.setString(10, comanda.getObservacion());
            statement.setInt(11, comanda.getIdEstatus());
            statement.setInt(12, comanda.getIdUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                comanda.setIdComanda(generatedKeys.getInt(1));
            }
        }
    }

    public void update(Comanda comanda) throws SQLException {
        String query = "UPDATE comandas SET folio = ?, fecha = ?, id_cliente = ?, cliente = ?, cantidad_productos = ?, subtotal = ?, iva = ?, total = ?, recibe = ?, observacion = ?, id_estatus = ?, id_usuario_modificacion = ?, fecha_modificacion = NOW() WHERE id_comanda = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, comanda.getFolio());
            statement.setDate(2, comanda.getFecha());
            statement.setInt(3, comanda.getIdCliente());
            statement.setString(4, comanda.getCliente());
            statement.setInt(5, comanda.getCantidadProductos());
            statement.setDouble(6, comanda.getSubtotal());
            statement.setDouble(7, comanda.getIva());
            statement.setDouble(8, comanda.getTotal());
            statement.setString(9, comanda.getRecibe());
            statement.setString(10, comanda.getObservacion());
            statement.setInt(11, comanda.getIdEstatus());
            statement.setInt(12, comanda.getIdUsuarioModificacion());
            statement.setInt(13, comanda.getIdComanda());

            statement.executeUpdate();
        }
    }
    
    public void actualizarPapgo(Comanda comanda) throws SQLException{
        String query = "UPDATE comandas SET fecha_modificacion = NOW(), pagado = ? WHERE id_comanda = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, comanda.isPagado());
            statement.setInt(2, comanda.getIdComanda());

            statement.executeUpdate();
        }
    }

    public void delete(int idComanda) throws SQLException {
        String query = "DELETE FROM comandas WHERE id_comanda = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idComanda);
            statement.executeUpdate();
        }
    }

    public Comanda getById(int idComanda) throws SQLException {
        String query = "SELECT * FROM comandas WHERE id_comanda = ?";
        Comanda comanda = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idComanda);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                comanda = mapResultSetToComanda(resultSet);
            }
        }

        return comanda;
    }

    public List<Comanda> getAll() throws SQLException {
        String query = "SELECT * FROM comandas";
        List<Comanda> comandas = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Comanda comanda = mapResultSetToComanda(resultSet);
                comandas.add(comanda);
            }
        }

        return comandas;
    }
    
    public List<Comanda> getDatosComanda() throws SQLException{
        String query = "SELECT id_comanda, folio, cliente, total FROM comandas ORDER BY fecha DESC";
        List<Comanda> comandas = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Comanda comanda = mapResultSetToComanda(resultSet);
                comandas.add(comanda);
            }
        }

        return comandas;
    }
    
    public List<Comanda> getDatosComandaNoPagadas() throws SQLException{
        String query = "SELECT id_comanda, folio, cliente, total FROM comandas WHERE pagado = 0 AND (MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()))  ORDER BY fecha DESC";
        List<Comanda> comandas = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Comanda comanda = mapResultSetToComandaSolodato(resultSet);
                comandas.add(comanda);
            }
        }

        return comandas;
    }
   
    
    public List<Comanda> TraerComandas() throws SQLException{
        List<Comanda> listacomandas = new ArrayList<>();
          String sql = "SELECT * FROM comandas c WHERE c.pagado = 0;";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                
                listacomandas.add(mapResultSetToComanda(rs));
            }

        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close(); // Cierra la conexión
            }
        }
        
        
        
        return listacomandas;
    }
    

    private Comanda mapResultSetToComanda(ResultSet resultSet) throws SQLException {
        Comanda comanda = new Comanda();
        comanda.setIdComanda(resultSet.getInt("id_comanda"));
        comanda.setFolio(resultSet.getString("folio"));
        comanda.setFecha(resultSet.getDate("fecha"));
        comanda.setIdCliente(resultSet.getInt("id_cliente"));
        comanda.setCliente(resultSet.getString("cliente"));
        comanda.setCantidadProductos(resultSet.getInt("cantidad_productos"));
        comanda.setSubtotal(resultSet.getDouble("subtotal"));
        comanda.setIva(resultSet.getDouble("iva"));
        comanda.setTotal(resultSet.getDouble("total"));
        comanda.setRecibe(resultSet.getString("recibe"));
        comanda.setObservacion(resultSet.getString("observacion"));
        comanda.setIdEstatus(resultSet.getInt("id_estatus"));
        comanda.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        comanda.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        comanda.setPagado(resultSet.getBoolean("pagado"));

        return comanda;
    }
    
    private Comanda mapResultSetToComandaSolodato(ResultSet resultSet) throws SQLException {
        Comanda comanda = new Comanda();
        comanda.setIdComanda(resultSet.getInt("id_comanda"));
        comanda.setFolio(resultSet.getString("folio"));
        comanda.setCliente(resultSet.getString("cliente"));
        comanda.setTotal(resultSet.getDouble("total"));

        return comanda;
    }
}
