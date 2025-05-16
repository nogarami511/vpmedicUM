/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM_DAO;

import clase.Conexion;
import clase.UM.PacienteUM;
import controles_um.TipoSexo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author olver
 */
public class TipoSexoDAO {
    
    Conexion conexion;
    Connection con;
    
    
    public List<TipoSexo> ejecutarProcedimientoPaciente() throws SQLException{
        conexion = new Conexion();
        con = conexion.conectar2();
            List<TipoSexo> sexos = new ArrayList<>();
               
            try(CallableStatement stm = con.prepareCall("{call Proc_TipoSexo()}")){
                
                stm.execute();
                
                ResultSet rs = stm.getResultSet();
                
                while(rs.next()){
                    TipoSexo tip = new TipoSexo();
                    tip.setIdTipoSexo(rs.getInt("id_ts"));
                    tip.setTipo(rs.getString("tipo"));
                    tip.setEstatus(rs.getBoolean("estatus"));
                    sexos.add(tip);
                    
                }
                       
                
                
                
            }catch(SQLException ex)
            {
                System.err.println(ex);
            }
            finally{
                con.close();
            }
            return sexos;
    }
}
