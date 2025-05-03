/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.AgendaQuirofano;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class AgendaQuirofanoDAO {
    Connection connection;

    public AgendaQuirofanoDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<AgendaQuirofano> traaerAgenda(int filtro) throws SQLException{
        List<AgendaQuirofano> listaagenda = new ArrayList<>();
        
        try(CallableStatement stm = connection.prepareCall("{CALL TRAER_AGENDA_QUIROFANO(?) }")){
            stm.setInt(1, filtro);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                listaagenda.add(mapearAgendaQuirofano(rs));
            }
            
        }
        
        return listaagenda;
    }
    public AgendaQuirofano rellernarparaeditar(AgendaQuirofano agendaQuirofano) throws SQLException{
        
        
        try(CallableStatement stm = connection.prepareCall("{CALL TRAER_CITA_AGENDA_QUIROFANO_ID(?) }")){
            stm.setInt(1, agendaQuirofano.getId_agenda_quirofano());
            stm.execute();
            ResultSet rs = stm.getResultSet();
           if(rs.next()){
               agendaQuirofano.setId_quirofano(rs.getInt("id_quirofano"));
               agendaQuirofano.setId_medico(rs.getInt("id_medico"));
               
               
           }
            
        }
        
        return agendaQuirofano;
    }
    public void agregarcitaagenda(AgendaQuirofano agendaQuirofano) throws SQLException{
        try(CallableStatement stm = connection.prepareCall("{call AGREGAR_CITA_AGENDA_QUIROFANO(?,?,?,?,?,?,?) }")){
            stm.setInt(1, agendaQuirofano.getId_quirofano());
            stm.setInt(2, agendaQuirofano.getId_medico());
            stm.setString(3, agendaQuirofano.getNombre_paciente());
            stm.setString(4, agendaQuirofano.getObservaciones());
            stm.setDate(5, agendaQuirofano.getFechaagenda());
            stm.setString(6, agendaQuirofano.getHora_agenda());
            stm.setInt(7, agendaQuirofano.getId_usuario_modificacion());
            stm.execute();
            
        }
        
    }
    public void editarrcitaagenda(AgendaQuirofano agendaQuirofano) throws SQLException{
        try(CallableStatement stm = connection.prepareCall("{call EDITAR_CITA_AGENDA_QUIROFANO(?,?,?,?,?,?,?,?) }")){
            stm.setInt(1, agendaQuirofano.getId_quirofano());
            stm.setInt(2, agendaQuirofano.getId_medico());
            stm.setString(3, agendaQuirofano.getNombre_paciente());
            stm.setString(4, agendaQuirofano.getObservaciones());
            stm.setDate(5, agendaQuirofano.getFechaagenda());
            stm.setString(6, agendaQuirofano.getHora_agenda());
            stm.setInt(7, agendaQuirofano.getId_usuario_modificacion());
            stm.setInt(8, agendaQuirofano.getId_agenda_quirofano());
            stm.execute();
            
        }
        
    }
    
    private AgendaQuirofano mapearAgendaQuirofano(ResultSet rs) throws SQLException{
        AgendaQuirofano agendaQuirofano = new AgendaQuirofano();
        
        agendaQuirofano.setId_agenda_quirofano(rs.getInt("id_agenda_de_quirofano"));
        agendaQuirofano.setNombre_quirofano(rs.getString("nombre_quirofano"));
        agendaQuirofano.setNombre_paciente(rs.getString("nombre_paciente"));
        agendaQuirofano.setNombre_medico(rs.getString("nombre_medico"));
        agendaQuirofano.setObservaciones(rs.getString("observaciones"));
        agendaQuirofano.setFechaagenda(rs.getDate("fecha_agenda"));
        agendaQuirofano.setHora_agenda(rs.getString("hora_agenda"));
        
        
        return agendaQuirofano;
    }
    
    
}
