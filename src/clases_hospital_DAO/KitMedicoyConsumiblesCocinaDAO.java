/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.KitMedicoyConsumiblesCocina;
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
public class KitMedicoyConsumiblesCocinaDAO {

    private Connection connection;

    public KitMedicoyConsumiblesCocinaDAO(Connection connection) {
        this.connection = connection;
    }

    public void crear(KitMedicoyConsumiblesCocina kit) throws SQLException {
        String query = "INSERT INTO kitmedicoyconsumiblescocina (id_insumo, cantidad, usuario_mdoificacion, fecha_modificacion, estatus, id_insumo_padre) VALUES (?, ?, ?, NOW(), ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, kit.getIdIsnumo());
            statement.setDouble(2, kit.getCantidad());
            statement.setInt(3, kit.getUsuarioModificacion());
            statement.setInt(4, kit.getEstatus());
            statement.setInt(5, kit.getId_insumo_padre());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                kit.setId(generatedKeys.getInt(1));
            }
        }
    }

    public KitMedicoyConsumiblesCocina leer(int id) throws SQLException {
        String query = "SELECT * FROM kitmedicoyconsumiblescocina WHERE id_KitMedicoyConsumiblesCocina = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    KitMedicoyConsumiblesCocina kit = mapearKit(resultSet);
                    return kit;
                }
            }
        }
        return null;
    }

    public List<KitMedicoyConsumiblesCocina> obtenerTodos() throws SQLException {
        List<KitMedicoyConsumiblesCocina> listaKits = new ArrayList<>();
        String query = "SELECT * FROM kitmedicoyconsumiblescocina";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                KitMedicoyConsumiblesCocina kit = mapearKit(resultSet);
                listaKits.add(kit);
            }
        }
        return listaKits;
    }

    public void actualizar(KitMedicoyConsumiblesCocina kit) throws SQLException {
        String query = "UPDATE kitmedicoyconsumiblescocina SET id_insumo = ?, cantidad = ?, usuario_mdoificacion = ?, fecha_modificacion = NOW(), estatus = ?, id_insumo_padre = ? WHERE id_KitMedicoyConsumiblesCocina = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, kit.getIdIsnumo());
            statement.setDouble(2, kit.getCantidad());
            statement.setInt(3, kit.getUsuarioModificacion());
            statement.setInt(4, kit.getEstatus());
            statement.setInt(5, kit.getId_insumo_padre());
            statement.setInt(5, kit.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM kitmedicoyconsumiblescocina WHERE id_KitMedicoyConsumiblesCocina = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public KitMedicoyConsumiblesCocina obtenerInsumos(int id) throws SQLException {
        KitMedicoyConsumiblesCocina kticon = new KitMedicoyConsumiblesCocina();
        String query = "SELECT i.id, i.nombre FROM insumos i WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                KitMedicoyConsumiblesCocina kit = mapearKitSoloNombreeInsumo(resultSet);
                return kit;
            }
        }
        return null;
    }
    
    public List<KitMedicoyConsumiblesCocina> obtenerTodosDelKitSeleccionado(int id_insumo) throws SQLException {
        List<KitMedicoyConsumiblesCocina> listaKits = new ArrayList<>();
        String query = "SELECT kc.*, i.nombre FROM kitmedicoyconsumiblescocina kc INNER JOIN insumos i ON kc.id_insumo = i.id WHERE id_insumo_padre = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, id_insumo);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listaKits.add(mapearKit(resultSet));
                }
            }
        }
        return listaKits;
    }
    
    public List<KitMedicoyConsumiblesCocina> obtenerTodosDelKitSoloDatosImportantes(int id_insumo) throws SQLException {
       
        List<KitMedicoyConsumiblesCocina> listaKits = new ArrayList<>();
        String query = "SELECT i.nombre, kmcc.id_insumo, kmcc.cantidad, ROUND(c.precio_venta_unitaria, 2) AS precio_venta_unitaria FROM kitmedicoyconsumiblescocina kmcc INNER JOIN insumos i ON kmcc.id_insumo = i.id INNER JOIN costos c ON kmcc.id_insumo = c.id_insumo WHERE id_insumo_padre = ?";
      

        try (PreparedStatement statement = connection.prepareStatement(query)){
           
            statement.setInt(1, id_insumo);
            try (ResultSet resultSet = statement.executeQuery()) {
               
                while (resultSet.next()) {
                    
                    listaKits.add(mapearkitSoloDatosImportantes(resultSet));
                }
            }
        }
        return listaKits;
    }

    private KitMedicoyConsumiblesCocina mapearKit(ResultSet resultSet) throws SQLException {
        KitMedicoyConsumiblesCocina kit = new KitMedicoyConsumiblesCocina();
        kit.setId(resultSet.getInt("id_KitMedicoyConsumiblesCocina"));
        kit.setIdIsnumo(resultSet.getInt("id_insumo"));
        kit.setCantidad(resultSet.getDouble("cantidad"));
        kit.setUsuarioModificacion(resultSet.getInt("usuario_mdoificacion"));
        kit.setFechaModificacion(resultSet.getDate("fecha_modificacion"));
        kit.setEstatus(resultSet.getInt("estatus"));
        kit.setId_insumo_padre(resultSet.getInt("id_insumo_padre"));
        kit.setNombreInsumo(resultSet.getString("nombre"));
        return kit;
    }
    
    private KitMedicoyConsumiblesCocina mapearkitSoloDatosImportantes(ResultSet resultSet) throws SQLException {
        KitMedicoyConsumiblesCocina kit = new KitMedicoyConsumiblesCocina();
        kit.setIdIsnumo(resultSet.getInt("id_insumo"));
        kit.setCantidad(resultSet.getDouble("cantidad"));
        kit.setNombreInsumo(resultSet.getString("nombre"));
        kit.setPrecioUnitario(resultSet.getDouble("precio_venta_unitaria"));
        return kit;
    }

    private KitMedicoyConsumiblesCocina mapearKitSoloNombreeInsumo(ResultSet resultSet) throws SQLException {
        KitMedicoyConsumiblesCocina kit = new KitMedicoyConsumiblesCocina();
        kit.setIdIsnumo(resultSet.getInt("id_isnumo"));
        kit.setCantidad(resultSet.getDouble("cantidad"));
        return kit;
    }
}
