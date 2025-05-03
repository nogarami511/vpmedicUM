/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ConsumoHabitacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class ConsumoHabitacionDAO {

    private Connection connection;

    public ConsumoHabitacionDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(ConsumoHabitacion consumoHabitacion) throws SQLException {
        String query = "INSERT INTO consumos_habitacion (id_habitacion, id_tipo_habitacion, id_folio, fecha_ingreso, cantidad, monto_al_momento, usuario_modificacion, fecha_modificacion) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, consumoHabitacion.getIdHabitacion());
            statement.setInt(2, consumoHabitacion.getIdTipoHabitacion());
            statement.setInt(3, consumoHabitacion.getIdFolio());
            statement.setTimestamp(4, consumoHabitacion.getFechaIngreso());
            statement.setInt(5, consumoHabitacion.getCantidad());
            statement.setDouble(6, consumoHabitacion.getMontoAlMomento());
            statement.setInt(7, consumoHabitacion.getUsuarioModificacion());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                consumoHabitacion.setIdConsumosHabitacion(generatedKeys.getInt(1));
            }
        }
    }

    public void update(ConsumoHabitacion consumoHabitacion) throws SQLException {
        String query = "UPDATE consumos_habitacion SET id_habitacion = ?, id_tipo_habitacion = ?, id_folio = ?, fecha_ingreso = NOW(), fecha_salida = ?, cantidad = ?, monto_al_momento = ?, usuario_modificacion = ?, fecha_modificacion = NOW() WHERE id_consumos_habitacion = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, consumoHabitacion.getIdHabitacion());
            statement.setInt(2, consumoHabitacion.getIdTipoHabitacion());
            statement.setInt(3, consumoHabitacion.getIdFolio());
            //statement.setDate(4, consumoHabitacion.getFechaIngreso());
            statement.setTimestamp(4, consumoHabitacion.getFechaSalida());
            statement.setInt(5, consumoHabitacion.getCantidad());
            statement.setDouble(6, consumoHabitacion.getMontoAlMomento());
            statement.setInt(7, consumoHabitacion.getUsuarioModificacion());
            //statement.setTimestamp(9, new Timestamp(new Date().getTime())); 
            statement.setInt(8, consumoHabitacion.getIdConsumosHabitacion());

            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM consumos_habitacion WHERE id_consumos_habitacion = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public ConsumoHabitacion getById(int id) throws SQLException {
        String query = "SELECT * FROM consumos_habitacion WHERE id_consumos_habitacion = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToConsumoHabitacion(resultSet);
            }
        }

        return null;
    }

    public List<ConsumoHabitacion> getAll() throws SQLException {
        List<ConsumoHabitacion> consumosHabitacion = new ArrayList<>();
        String query = "SELECT * FROM consumos_habitacion";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ConsumoHabitacion consumoHabitacion = mapResultSetToConsumoHabitacion(resultSet);
                consumosHabitacion.add(consumoHabitacion);
            }
        }

        return consumosHabitacion;
    }

    public ConsumoHabitacion optenerDatosHabitacionPaciente(int _id_paquete, int _id_tipo_habitacion, int _num_habitacion, int _id_folio) throws SQLException {
        ConsumoHabitacion consumohabitacion = new ConsumoHabitacion();
        String query = "{CALL CAMBIOHABITACION(?,?,?,?)}";
        try (CallableStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, _id_paquete);
            statement.setInt(2, _id_tipo_habitacion);
            statement.setInt(3, _num_habitacion);
            statement.setInt(4, _id_folio);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
               
                consumohabitacion.setIdHabitacion(rs.getInt("id_insumo"));
                consumohabitacion.setIdTipoHabitacion(_id_tipo_habitacion);
                consumohabitacion.setIdFolio(_id_folio);
                consumohabitacion.setFechaIngreso(rs.getTimestamp("fecha_ingreso"));//HAY QUE REVISAR ESTO
                consumohabitacion.setFechaSalida(rs.getTimestamp("fechaactual"));//HAY QUE REVISAR ESTO
                consumohabitacion.setCantidad(rs.getInt("cantidad_entregada"));
                consumohabitacion.setMontoAlMomento(rs.getDouble("subtotal_sin_iva"));
                consumohabitacion.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            } else {
                consumohabitacion = null;
            }
        }
        return consumohabitacion;
    }

    public ConsumoHabitacion optenerDatosHabitacionPacientePorFechadeIngreso(int _id_paquete, int _id_tipo_habitacion, int _num_habitacion, int _id_folio, Date fechaIngreso) throws SQLException {
        ConsumoHabitacion consumohabitacion = new ConsumoHabitacion();
        String query = "{CALL CAMBIOHABITACIONPORFECHAINICIO(?,?,?,?,?)}";
        try (CallableStatement statement = connection.prepareCall(query)) {
            statement.setInt(1, _id_paquete);
            statement.setInt(2, _id_tipo_habitacion);
            statement.setInt(3, _num_habitacion);
            statement.setInt(4, _id_folio);
            statement.setDate(5, fechaIngreso);

          
            //creo que ya no entra al resulset
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
            
                consumohabitacion.setIdHabitacion(rs.getInt("id_insumo"));
                consumohabitacion.setIdTipoHabitacion(_id_tipo_habitacion);
                consumohabitacion.setIdFolio(_id_folio);
                consumohabitacion.setFechaSalida(rs.getTimestamp("fechaactual"));
                consumohabitacion.setCantidad(rs.getInt("cantidad_entregada"));
                consumohabitacion.setMontoAlMomento(rs.getDouble("subtotal_sin_iva"));
                consumohabitacion.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            } else {
                consumohabitacion = null;
            }
        }
        return consumohabitacion;
    }

    public ConsumoHabitacion existeHabitacionConsumida(int id_folio) throws SQLException {
        String query = "SELECT ch.id_consumos_habitacion, ch.fecha_salida FROM consumos_habitacion ch WHERE ch.id_folio = ? ORDER BY ch.id_consumos_habitacion ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToConsumoHabitacionSOLOIDYFECHASALIDA(resultSet);
            }
        }
        return null;
    }

    public ConsumoHabitacion existeHabitacionConsumidaCONTODOSDATOS(int id_folio) throws SQLException {
        String query = "SELECT ch.id_consumos_habitacion, ch.fecha_salida FROM consumos_habitacion ch WHERE ch.id_folio = ? ORDER BY ch.id_consumos_habitacion ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToConsumoHabitacion(resultSet);
            }
        }
        return null;
    }

    private ConsumoHabitacion mapResultSetToConsumoHabitacion(ResultSet resultSet) throws SQLException {
        ConsumoHabitacion consumoHabitacion = new ConsumoHabitacion();
        consumoHabitacion.setIdConsumosHabitacion(resultSet.getInt("id_consumos_habitacion"));
        consumoHabitacion.setIdHabitacion(resultSet.getInt("id_habitacion"));
        consumoHabitacion.setIdTipoHabitacion(resultSet.getInt("id_tipo_habitacion"));
        consumoHabitacion.setIdFolio(resultSet.getInt("id_folio"));
        consumoHabitacion.setFechaIngreso(resultSet.getTimestamp("fecha_ingreso"));
        consumoHabitacion.setFechaSalida(resultSet.getTimestamp("fecha_salida"));
        consumoHabitacion.setCantidad(resultSet.getInt("cantidad"));
        consumoHabitacion.setMontoAlMomento(resultSet.getDouble("monto_al_momento"));
        consumoHabitacion.setUsuarioModificacion(resultSet.getInt("usuario_modificacion"));
        consumoHabitacion.setFechaModificacion(resultSet.getDate("fecha_modificacion"));

        return consumoHabitacion;
    }

    private ConsumoHabitacion mapResultSetToConsumoHabitacionSOLOIDYFECHASALIDA(ResultSet resultSet) throws SQLException {
        ConsumoHabitacion consumoHabitacion = new ConsumoHabitacion();
        consumoHabitacion.setIdConsumosHabitacion(resultSet.getInt("id_consumos_habitacion"));
        consumoHabitacion.setFechaSalida(resultSet.getTimestamp("fecha_salida"));

        return consumoHabitacion;
    }
}
