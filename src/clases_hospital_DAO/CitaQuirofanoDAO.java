/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.CitaQuirofano;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class CitaQuirofanoDAO {
    private Connection connection;

    public CitaQuirofanoDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertarCita(CitaQuirofano cita) throws SQLException {
        String query = "INSERT INTO citasquirofano (id_quirofano, id_medico, id_paciente, id_tipo_habitacion, id_servicios_adicionales, cirugia, contacto, hora_Inicio, hora_Fin, duracion_hora, duracion_minutos, fecha_cirugia, observaciones, id_estatus_agenda, fecha_habitacion_apartado, fecha_modficacion, id_usuario_modificacion, id_estaus_panel_informacion_quirofano, id_folios, fecha_ingreso_quirofano, hora_ingreso_quirofano, hora_salida_quirofano) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cita.getIdQuirofano());
            statement.setInt(2, cita.getIdMedico());
            statement.setInt(3, cita.getIdPaciente());
            statement.setInt(4, cita.getIdTipoHabitacion());
            statement.setString(5, cita.getIdServiciosAdicionales());
            statement.setString(6, cita.getCirugia());
            statement.setString(7, cita.getContacto());
            statement.setTime(8, cita.getHoraInicio());
            statement.setTime(9, cita.getHoraFin());
            statement.setInt(10, cita.getDuracionHora());
            statement.setInt(11, cita.getDuracionMinutos());
            statement.setDate(12, cita.getFechaCirugia());
            statement.setString(13, "");
            statement.setInt(14, cita.getIdEstatusAgenda());
//            statement.setDate(15, cita.getFechaHabitacionApartado());
            statement.setInt(15, VPMedicaPlaza.userSystem);
            statement.setInt(16, cita.getIdEstatusPanelInformacionQuirofano());
            statement.setInt(17, cita.getId_folios());
            statement.setDate(18, cita.getFecha_ingreso_quirofano());
            statement.setTime(19, cita.getHora_ingreso_quirofano());
            statement.setTime(20, cita.getHora_salida_quirofano());

            statement.executeUpdate();
        }
    }

    public void actualizarCita(CitaQuirofano cita) throws SQLException {
        String query = "UPDATE citasquirofano SET id_quirofano = ?, id_medico = ?, id_paciente = ?, id_tipo_habitacion = ?, id_servicios_adicionales = ?, cirugia = ?, contacto = ?, hora_Inicio = ?, hora_Fin = ?, duracion_hora = ?, duracion_minutos = ?, fecha_cirugia = ?, observaciones = ?, id_estatus_agenda = ?, fecha_habitacion_apartado = ?, fecha_modficacion = NOW(), id_usuario_modificacion = ?, id_estaus_panel_informacion_quirofano = ?, id_folios = ?, fecha_ingreso_quirofano = ?, hora_ingreso_quirofano = ?, hora_salida_quirofano = ? WHERE id_citaquirofano = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cita.getIdQuirofano());
            statement.setInt(2, cita.getIdMedico());
            statement.setInt(3, cita.getIdPaciente());
            statement.setInt(4, cita.getIdTipoHabitacion());
            statement.setString(5, cita.getIdServiciosAdicionales());
            statement.setString(6, cita.getCirugia());
            statement.setString(7, cita.getContacto());
            statement.setTime(8, cita.getHoraInicio());
            statement.setTime(9, cita.getHoraFin());
            statement.setInt(10, cita.getDuracionHora());
            statement.setInt(11, cita.getDuracionMinutos());
            statement.setDate(12, cita.getFechaCirugia());
            statement.setString(13, cita.getObservaciones());
            statement.setInt(14, cita.getIdEstatusAgenda());
            statement.setDate(15, cita.getFechaHabitacionApartado());
            statement.setInt(16, cita.getIdUsuarioModificacion());
            statement.setInt(17, cita.getIdEstatusPanelInformacionQuirofano());
            statement.setInt(18, cita.getId_folios());
            statement.setDate(19, cita.getFecha_ingreso_quirofano());
            statement.setTime(20, cita.getHora_ingreso_quirofano());
            statement.setTime(21, cita.getHora_salida_quirofano());
            statement.setInt(22, cita.getId());

            statement.executeUpdate();
        }
    }

    public void eliminarCita(int idCita) throws SQLException {
        String query = "DELETE FROM citasquirofano WHERE id_citaquirofano = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCita);

            statement.executeUpdate();
        }
    }
    
    public boolean existeCita(int idPaciente) throws SQLException{
        String query = "SELECT * FROM citasquirofano cq WHERE cq.id_paciente = ? AND cq.id_estatus_agenda < 3";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPaciente);
            try (ResultSet resultset = statement.executeQuery()) {
                return resultset.next();
            } 
        }
    }
    
    public boolean hayHoraExtra(CitaQuirofano citaQuirofano) throws SQLException {
        boolean horaextra;
        String query = "SELECT IF(? <= DATE_FORMAT(NOW(), '%H:%i'), TRUE, FALSE) AS horaExtras FROM citasquirofano c WHERE c.id_citaquirofano = ?";
        
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setTime(1, citaQuirofano.getHoraFin());
            statement.setInt(2, citaQuirofano.getId());
            try (ResultSet resultset = statement.executeQuery()) {
                if(resultset.next()){
                    horaextra = resultset.getBoolean(1);
                }else{
                    horaextra = false;
                }
            }
        }
        
        return horaextra;
    }
    
    public int optenerDiferenciaHoras(CitaQuirofano citaQuirofano) throws SQLException {
        String query = "SELECT TIMEDIFF(?, hora_Fin) AS diferencia_horas FROM citasquirofano WHERE id_citaquirofano = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTime(1, citaQuirofano.getHoraFin());
            statement.setInt(2, citaQuirofano.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }
    
    public CitaQuirofano quirofanoOcupado(Date fechaDate, Time inicioTime) throws SQLException {// DATE  TIME  TIME
        String query = "SELECT * FROM citasquirofano c WHERE c.id_quirofano = 1 AND (c.fecha_cirugia = ? AND (? >= c.hora_Inicio AND ? <= c.hora_Fin))";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, fechaDate);
            statement.setTime(2, inicioTime);
            statement.setTime(3, inicioTime);
            
            try (ResultSet resultset = statement.executeQuery()) {
                if(resultset.next()){
                    return mapearCita(resultset);
                }
            }
        }
        return null;
    }

    public CitaQuirofano obtenerCitaPorId(int idCita) throws SQLException {
        String query = "SELECT cq.*, med.nombre AS medico, pac.nombre_paciente, qui.nombre AS quirofano FROM citasquirofano cq INNER JOIN medicos med ON cq.id_medico = med.id_medico INNER JOIN pacientes pac ON cq.id_paciente = pac.id_paciente INNER JOIN quirofanos qui ON cq.id_quirofano = qui.id  WHERE cq.id_citaquirofano = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCita);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCitaConListas(resultSet);
                }
            }
        }
        return null;
    }
    
    public int obtenerIdQuirofanoPorId(int idCita) throws SQLException {
        String query = "SELECT cq.id_quirofano FROM citasquirofano cq WHERE cq.id_citaquirofano = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCita);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_quirofano");
                }
            }
        }
        return 0;
    }

    public List<CitaQuirofano> obtenerTodasLasCitas() throws SQLException {
        List<CitaQuirofano> citas = new ArrayList<>();
        String query = "cq.*, med.nombre AS medico, pac.nombre_paciente, qui.nombre AS quirofano FROM citasquirofano cq INNER JOIN medicos med ON cq.id_medico = med.id_medico INNER JOIN pacientes pac ON cq.id_paciente = pac.id_paciente";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                CitaQuirofano cita = mapearCitaConListas(resultSet);
                citas.add(cita);
            }
        }
        return citas;
    }
    
    public List<CitaQuirofano> obtenerTodasLasCitasPorFecha() throws SQLException {
        List<CitaQuirofano> citas = new ArrayList<>();
        String query = "SELECT cq.*, med.nombre AS medico, pac.nombre_paciente, qui.nombre AS quirofano FROM citasquirofano cq INNER JOIN medicos med ON cq.id_medico = med.id_medico INNER JOIN pacientes pac ON cq.id_paciente = pac.id_paciente INNER JOIN quirofanos qui ON cq.id_quirofano = qui.id WHERE cq.fecha_cirugia >= CURDATE() ORDER BY cq.fecha_cirugia";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                CitaQuirofano cita = mapearCitaConListas(resultSet);
                citas.add(cita);
            }
        }
        return citas;
    }
    
    public List<CitaQuirofano> obtenerTodasLasCitasFiltradoFecha(Date inicio, Date fin) throws SQLException {
        List<CitaQuirofano> citas = new ArrayList<>();
        String query = "SELECT cq.*, med.nombre AS medico, pac.nombre_paciente, qui.nombre AS quirofano FROM citasquirofano cq INNER JOIN medicos med ON cq.id_medico = med.id_medico INNER JOIN pacientes pac ON cq.id_paciente = pac.id_paciente INNER JOIN quirofanos qui ON cq.id_quirofano = qui.id WHERE cq.fecha_cirugia between ? AND ? ORDER BY cq.fecha_cirugia";
        
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setDate(1, inicio);
            statement.setDate(2, fin);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()){
                    CitaQuirofano citasQuirofano = mapearCitaConListas(resultSet);
                    citas.add(citasQuirofano);
                }
            } 
        }
        
        return citas;
    }
    
    public CitaQuirofano obteCitaQuirofanoPorIdFolio (int idFolio) throws SQLException {
        String query = "SELECT * FROM citasquirofano WHERE id_folios = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapearCitaPorIdFolio(resultSet);
                }
            }
        }
        
        return null;
    }
    
    private CitaQuirofano mapearCitaPorIdFolio(ResultSet resultSet) throws SQLException {
        CitaQuirofano cita = new CitaQuirofano();
        cita.setId(resultSet.getInt("id_citaquirofano"));
        cita.setIdQuirofano(resultSet.getInt("id_quirofano"));
        cita.setIdMedico(resultSet.getInt("id_medico"));
        cita.setIdPaciente(resultSet.getInt("id_paciente"));
        cita.setIdTipoHabitacion(resultSet.getInt("id_tipo_habitacion"));
        cita.setIdServiciosAdicionales(resultSet.getString("id_servicios_adicionales"));
        cita.setCirugia(resultSet.getString("cirugia"));
        cita.setContacto(resultSet.getString("contacto"));
        cita.setHoraInicio(resultSet.getTime("hora_Inicio"));
        cita.setHoraFin(resultSet.getTime("hora_Fin"));
        cita.setDuracionHora(resultSet.getInt("duracion_hora"));
        cita.setDuracionMinutos(resultSet.getInt("duracion_minutos"));
        cita.setFechaCirugia(resultSet.getDate("fecha_cirugia"));
        cita.setObservaciones(resultSet.getString("observaciones"));
        cita.setIdEstatusAgenda(resultSet.getInt("id_estatus_agenda"));
        cita.setFechaHabitacionApartado(resultSet.getDate("fecha_habitacion_apartado"));
        cita.setFechaModificacion(resultSet.getTimestamp("fecha_modficacion"));
        cita.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        cita.setIdEstatusPanelInformacionQuirofano(resultSet.getInt("id_estaus_panel_informacion_quirofano"));
        cita.setId_folios(resultSet.getInt("id_folios"));

        return cita;
    }

    private CitaQuirofano mapearCita(ResultSet resultSet) throws SQLException {
        CitaQuirofano cita = new CitaQuirofano();
        cita.setId(resultSet.getInt("id_citaquirofano"));
        cita.setIdQuirofano(resultSet.getInt("id_quirofano"));
        cita.setIdMedico(resultSet.getInt("id_medico"));
        cita.setIdPaciente(resultSet.getInt("id_paciente"));
        cita.setIdTipoHabitacion(resultSet.getInt("id_tipo_habitacion"));
        cita.setIdServiciosAdicionales(resultSet.getString("id_servicios_adicionales"));
        cita.setCirugia(resultSet.getString("cirugia"));
        cita.setContacto(resultSet.getString("contacto"));
        cita.setHoraInicio(resultSet.getTime("hora_Inicio"));
        cita.setHoraFin(resultSet.getTime("hora_Fin"));
        cita.setDuracionHora(resultSet.getInt("duracion_hora"));
        cita.setDuracionMinutos(resultSet.getInt("duracion_minutos"));
        cita.setFechaCirugia(resultSet.getDate("fecha_cirugia"));
        cita.setObservaciones(resultSet.getString("observaciones"));
        cita.setIdEstatusAgenda(resultSet.getInt("id_estatus_agenda"));
        cita.setFechaHabitacionApartado(resultSet.getDate("fecha_habitacion_apartado"));
        cita.setFechaModificacion(resultSet.getTimestamp("fecha_modficacion"));
        cita.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        cita.setIdEstatusPanelInformacionQuirofano(resultSet.getInt("id_estaus_panel_informacion_quirofano"));
        cita.setId_folios(resultSet.getInt("id_folios"));
        cita.setNombrePaciente(resultSet.getString("nombre_paciente"));
        cita.setNombreMedico(resultSet.getString("medico"));
        cita.setQuirofano(resultSet.getString("quirofano"));

        return cita;
    }
    
    private CitaQuirofano mapearCitaConListas(ResultSet resultSet) throws SQLException {
        CitaQuirofano cita = new CitaQuirofano();
        ServiciosAdicionalesDAO serviciosAdicionalesdao = new ServiciosAdicionalesDAO(connection);
        ProcedimientoDAO procedimeintodao = new ProcedimientoDAO(connection);
        cita.setId(resultSet.getInt("id_citaquirofano"));
        cita.setIdQuirofano(resultSet.getInt("id_quirofano"));
        cita.setIdMedico(resultSet.getInt("id_medico"));
        cita.setIdPaciente(resultSet.getInt("id_paciente"));
        cita.setIdTipoHabitacion(resultSet.getInt("id_tipo_habitacion"));
        cita.setIdServiciosAdicionales(resultSet.getString("id_servicios_adicionales"));
        cita.setCirugia(resultSet.getString("cirugia"));
        cita.setContacto(resultSet.getString("contacto"));
        cita.setHoraInicio(resultSet.getTime("hora_Inicio"));
        cita.setHoraFin(resultSet.getTime("hora_Fin"));
        cita.setDuracionHora(resultSet.getInt("duracion_hora"));
        cita.setDuracionMinutos(resultSet.getInt("duracion_minutos"));
        cita.setFechaCirugia(resultSet.getDate("fecha_cirugia"));
        cita.setObservaciones(resultSet.getString("observaciones"));
        cita.setIdEstatusAgenda(resultSet.getInt("id_estatus_agenda"));
        cita.setFechaHabitacionApartado(resultSet.getDate("fecha_habitacion_apartado"));
        cita.setFechaModificacion(resultSet.getTimestamp("fecha_modficacion"));
        cita.setIdUsuarioModificacion(resultSet.getInt("id_usuario_modificacion"));
        cita.setIdEstatusPanelInformacionQuirofano(resultSet.getInt("id_estaus_panel_informacion_quirofano"));
        cita.setId_folios(resultSet.getInt("id_folios"));
        cita.setNombrePaciente(resultSet.getString("nombre_paciente"));
        cita.setNombreMedico(resultSet.getString("medico"));
        cita.setQuirofano(resultSet.getString("quirofano"));
        cita.setListacirugias(procedimeintodao.obtenerListaProcedimiento(resultSet.getString("cirugia")));
        cita.setListaserviciosadicionales(serviciosAdicionalesdao.obtenerListaServiciosAdicionales(resultSet.getString("id_servicios_adicionales")));
        cita.setFecha_ingreso_quirofano(resultSet.getDate("fecha_ingreso_quirofano"));
        cita.setHora_ingreso_quirofano(resultSet.getTime("hora_ingreso_quirofano"));
        cita.setHora_salida_quirofano(resultSet.getTime("hora_salida_quirofano"));
        return cita;
    }
}