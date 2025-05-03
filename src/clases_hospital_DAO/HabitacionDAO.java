/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Conexion;
import clases_hospital.EstatusHabitacion;
import clases_hospital.Folio;
import clases_hospital.Habitacion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author gamae
 */
public class HabitacionDAO {
    
    Alert alertaError = new Alert(Alert.AlertType.ERROR);

    private Connection connection;

    public HabitacionDAO(Connection connection) {
        this.connection = connection;
    }
    
    
    public void CambiarHabitacion(Folio folio){
        try(CallableStatement stm = connection.prepareCall("CALL PROC_CAMBIO_HABITACION(?,?,?)")){
            
            stm.setInt("id_folio",folio.getId() );
            stm.setInt("id_habitacion_nueva",folio.getId_habitacion());
            stm.setInt("id_usuario_mod",folio.getId() );
            stm.execute();
            
            
            
        }catch(SQLException ex){
        alertaError.setTitle("ERROR");
        alertaError.setHeaderText("Algo salio mal");
        alertaError.setContentText("Error : " + ex.getMessage());
        alertaError.showAndWait();
        }
    }

    public List<Habitacion> obtenerTiposHabitacion() throws SQLException {
        List<Habitacion> tiposHabitacion = new ArrayList<>();
        Habitacion h;
        String query = "SELECT * FROM tipoHabitacion h";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                h = new Habitacion(resultSet.getInt(1), resultSet.getString(2));
                tiposHabitacion.add(h);

            }
        }
        return tiposHabitacion;
    }

    public List<Habitacion> obtenerNombreTipoHabitacionPorId(int idTipoHabitacion) throws SQLException {
        List<Habitacion> tiposHabitacion = new ArrayList<>();
        Habitacion h;
        String query = "SELECT h.id_tipo FROM tipoHabitacion h WHERE h.id_tipo = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                h = new Habitacion(resultSet.getInt(1), resultSet.getString(2));
                tiposHabitacion.add(h);

            }
        }
        return tiposHabitacion;
    }

    public List<EstatusHabitacion> obtenerEstatus() throws SQLException {
        List<EstatusHabitacion> tiposEstatus = new ArrayList<>();
        EstatusHabitacion estatus;
        String sql = "SELECT eh.id_estatus,eh.tipo,eh.observaciones FROM estatus_habitacion eh";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                estatus = new EstatusHabitacion(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                tiposEstatus.add(estatus);
            }
            return tiposEstatus;
        }
    }

    public void insertCostoHabitacion(int idTipo, int numero, int piso, int idPrioridad, int idEstatus, String observacion, int idUsuarioModificacion) throws SQLException {
        String query = "INSERT INTO habitacion (id_tipo, numero_habitacion ,piso ,id_prioridad ,id_estautas , observacion,id_usuario_modificacion ,fecha_modificacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipo);
            statement.setInt(2, numero);
            statement.setInt(3, piso);
            statement.setInt(4, idPrioridad);
            statement.setInt(5, idEstatus);
            statement.setString(6, observacion);
            statement.setInt(7, VPMedicaPlaza.userSystem);
            statement.executeUpdate();
        }
    }

    public void actualizarHabitacion(int id, int idTipoHabitacion, int numero, int piso, int idPrioridad, int idEstatus, String observacion, int idUsuarioModificacion) throws SQLException {
        String sql = "UPDATE habitacion SET id_tipo = ? ,numero_habitacion =? ,piso = ?, id_prioridad = ?, id_estautas = ?, observacion = ?, id_usuario_modificacion =? "
                + ",fecha_modificacion = NOW() WHERE id_habitacion = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idTipoHabitacion);
            statement.setInt(2, numero);
            statement.setInt(3, piso);
            statement.setInt(4, idPrioridad);
            statement.setInt(5, idEstatus);
            statement.setString(6, observacion);
            statement.setInt(7, VPMedicaPlaza.userSystem);
            statement.setInt(8, id);
            statement.executeUpdate();
        }
    }

    public void habitacionOcupada(int id) throws SQLException {
        String sql = "UPDATE habitacion h SET h.id_estautas = 2, h.observacion = 'HABITACION OCUPADA' , h.id_usuario_modificacion = ? AND h.fecha_modificacion = NOW()\n"
                + "  WHERE h.id_habitacion = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, VPMedicaPlaza.userSystem);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    //llenamos la tabla de la vista de habitaciones(para crear habitaciones)
    public ObservableList<Habitacion> llenarTabla() throws SQLException {
        int idTipo;
        String tipoH = null;
        ObservableList<Habitacion> habitaciones = FXCollections.observableArrayList();
        Habitacion h = null;

        String sql = "SELECT h.id_habitacion,h.id_tipo,h.numero_habitacion,h.piso,h.id_prioridad,h.id_estautas, h.observacion FROM habitacion h";

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                idTipo = resultSet.getInt(2);

                String sqlTipoH = "SELECT h.tipo FROM tipoHabitacion h WHERE h.id_tipo ='" + idTipo + "'";
                PreparedStatement stmtTipoH = connection.prepareStatement(sqlTipoH);
                ResultSet rsTipoH = stmtTipoH.executeQuery();
                while (rsTipoH.next()) {
                    tipoH = rsTipoH.getString(1);
                }
                h = new Habitacion(resultSet.getInt(1),//id
                        resultSet.getInt(2),//id habitacion
                        resultSet.getInt(3),//numero de habitacion
                        resultSet.getInt(4),//piso
                        resultSet.getInt(5),//prioridad
                        resultSet.getInt(6),// id estatus
                        resultSet.getString(7),//observaciones
                        tipoH);//tipo(string)
                habitaciones.add(h);
            }

        }
        return habitaciones;
    }
//llenamos la tabla de la vista de habitaciones(para crear habitaciones)

    public List<Habitacion> obtenerHabitaciones() throws SQLException {
        List<Habitacion> habitaciones = new ArrayList<>();
        Habitacion habitacion;
        String sql = "SELECT h.id_habitacion,h.id_tipo,h.numero_habitacion,h.piso,h.id_prioridad,h.id_estautas, h.observacion FROM habitacion h";

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                habitacion = new Habitacion();
                habitacion.setId(resultSet.getInt("id_habitacion"));
                habitacion.setIdTipo(resultSet.getInt("id_tipo"));
                habitacion.setNumeroHabitacion(resultSet.getInt("numero_habitacion"));
                habitacion.setPiso(resultSet.getInt("piso"));
                habitacion.setPrioridad(resultSet.getInt("id_prioridad"));
                habitacion.setEstatus(resultSet.getInt("id_estautas"));
                habitaciones.add(habitacion);
            }

        }
        return habitaciones;
    }

    public ObservableList<Habitacion> llenarTablaAsignacionHabitaciones() throws SQLException {
        ObservableList<Habitacion> habitaciones = FXCollections.observableArrayList();
        Habitacion h = null;

        String sql = "SELECT ah.id_asignacion, ah.id_habitacion, ah.id_tipo_habitacion, ah.id_paciente, ah.id_estatus, th.tipo, h.numero_habitacion, p.nombre_paciente, h.piso, h.observacion\n"
                + "FROM asignacion_habitacion ah\n"
                + "INNER JOIN tipoHabitacion th ON ah.id_tipo_habitacion = th.id_tipo\n"
                + "INNER JOIN habitacion h ON ah.id_habitacion = h.id_habitacion\n"
                + "LEFT JOIN pacientes p ON ah.id_paciente = p.id_paciente;";

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                h = new Habitacion(resultSet.getInt(1),//id asignacion
                        resultSet.getInt(2),//id habitacion
                        resultSet.getInt(3),// id tipo habitacion
                        resultSet.getInt(4),// id paciente 
                        resultSet.getInt(5),// id estatus 
                        resultSet.getString(6),//tipo de habitacion
                        resultSet.getInt(7),// numero habitacion
                        resultSet.getString(8),// nombre paciente
                        resultSet.getInt(9),// piso
                        resultSet.getString(10));//observaciones
                habitaciones.add(h);
            }
        }
        return habitaciones;
    }

    public List<Habitacion> obtHabitacionPorIdTipoHabitacion(int idTipoHabitacion) throws SQLException {
        List<Habitacion> habitaciones = new ArrayList<>();
        String query = "SELECT h.id_habitacion, h.numero_habitacion FROM habitacion h WHERE h.id_tipo = ? AND h.id_estautas = 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipoHabitacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Habitacion habitacion = new Habitacion();
                    habitacion.setId(resultSet.getInt("id_habitacion"));
                    habitacion.setNumeroHabitacion(resultSet.getInt("numero_habitacion"));
                    habitaciones.add(habitacion);
                }
            }
        }
        return habitaciones;
    }

    public Habitacion obtenerHabitacion(int numeroHabitacion) throws SQLException {
        Habitacion habitacion = new Habitacion();
        String query = "SELECT id_habitacion, numero_habitacion FROM habitacion WHERE numero_habitacion = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numeroHabitacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    habitacion.setId(resultSet.getInt("id_habitacion"));
                    habitacion.setNumeroHabitacion(resultSet.getInt("numero_habitacion"));
                }
            }
        }
        return habitacion;
    }

    public void actualizarHabitacion(Habitacion habitacion) throws SQLException {
        String query = "UPDATE habitacion SET id_estautas = ?, id_usuario_modificacion = ?, observacion = ? , fecha_modificacion = NOW() WHERE id_habitacion = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, habitacion.getEstatus());
            statement.setInt(2, habitacion.getUsuarioModificacion());
            statement.setString(3, "OCUPADA");
            statement.setInt(4, habitacion.getId());
            statement.executeUpdate();
        }
    }

    public void liberarHabitacion(Habitacion habitacion) throws SQLException {
        String query = "UPDATE habitacion SET id_estautas = ?, id_usuario_modificacion = ?, observacion = ? , fecha_modificacion = NOW() WHERE id_habitacion = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, 1);
            statement.setInt(2, VPMedicaPlaza.userSystem);
            statement.setString(3, "HABITACION DISPONIBLE");
            statement.setInt(4, habitacion.getId());
            statement.executeUpdate();
        }
    }

    public int obtenerIDHabitacion(int numeroHabitacion) throws SQLException {
        int idHabitacion = 0;
        String query = "SELECT id_habitacion FROM habitacion WHERE numero_habitacion = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numeroHabitacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    idHabitacion = resultSet.getInt(1);
                }
            }
        }
        return idHabitacion;
    }

    public int obtenerIdTipoHabitacion(int idHabitacion) throws SQLException {
        int idTipoHabitacion = 0;
        String query = "SELECT id_tipo FROM habitacion WHERE id_habitacion = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idHabitacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    idTipoHabitacion = resultSet.getInt(1);
                }
            }
        }
        return idTipoHabitacion;
    }
}
