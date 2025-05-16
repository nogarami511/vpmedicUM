/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM_DAO;

import clase.Conexion;
import clase.UM.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.spi.DirStateFactory;


/**
 *
 * @author olver
 */
public class PacienteUmDAO {
   
    Conexion conexion;
    Connection con;
    
    
    public List<PacienteUM> ejecutarProcedimientoPaciente(String opcion, PacienteUM paciente) throws SQLException{
        conexion = new Conexion();
        con = conexion.conectar2();
            List<PacienteUM> pacientes = new ArrayList<>();
            PacienteUM pac;
               
            try(CallableStatement stm = con.prepareCall("{call Proc_PacienteUm(?,?,?,?,?,?,?,?,?,?,?,?)}")){
                stm.setString("opcion", opcion);
                stm.setInt("id_paciente_", paciente.getIdPaciente());
                stm.setInt("tipo_tab_", paciente.getTipoTab().getIdTipoTabulacion());
                stm.setString("curp_", paciente.getCurp());
                stm.setString("nombre_paciente_", paciente.getNombrePaciente());
                stm.setString("apellido_paterno_", paciente.getApellidoPaterno());
                stm.setString("apellido_materno_", paciente.getApellidoMaterno());
                stm.setString("sexo", paciente.getSexoPaciente());
                stm.setDate("fecha_nacimiento_", paciente.getFechaNacimientoPaciente());
                stm.setInt("edad_", paciente.getEdad());
                stm.setInt("id_usuario_", paciente.getUsuarioCreacion().getIdUsuario());
                stm.setBoolean("estatus_", paciente.getEstatus());
                stm.execute();
                
                ResultSet rs = stm.getResultSet();
                
                while(rs.next()){
                    pac = new PacienteUM();
                    pac.setIdPaciente(rs.getInt("id_paciente"));
                    pac.setCurp(rs.getString("curp"));
                    pac.setNombrePaciente(rs.getString("nombre_paciente"));
                    pac.setApellidoPaterno(rs.getString("apellido_paterno"));
                    pac.setApellidoMaterno(rs.getString("apellido_materno"));
                    pac.setSexoPaciente(rs.getString("sexo_paciente"));
                    pac.setFechaNacimientoPaciente(rs.getDate("fecha_nacimiento_paciente"));
                    pac.setEdad(rs.getInt("edad"));
                    pac.getUsuarioCreacion().setIdUsuario(rs.getInt("id_usuario"));
                    pac.getUsuarioCreacion().setUsuario(rs.getString("nombre_usuario"));
                    pac.getUsuarioCreacion().setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                    pac.getTipoTab().setIdTipoTabulacion(rs.getInt("tipo_tabulacion"));
                    pac.getTipoTab().setTipo(rs.getString("tipo"));
                    pac.setEstatus(rs.getBoolean("estatus"));
                    
                    pacientes.add(pac);
                    
                }
                       
                
                
                
            }catch(SQLException ex)
            {
                System.err.println(ex);
            }
            finally{
                con.close();
            }
            return pacientes;
    }
    
}
