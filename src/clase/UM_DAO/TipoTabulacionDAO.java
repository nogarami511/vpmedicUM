/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM_DAO;

import clase.Conexion;
import clase.UM.TipoTabulacion;
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
public class TipoTabulacionDAO {
    Conexion conec = new Conexion();
    Connection con;
    
    public List<TipoTabulacion> ejecutarProcedimiento(String opcion, TipoTabulacion tipoTab) throws SQLException{
        con = conec.conectar2();
        List<TipoTabulacion> tiposTabulaciones = new ArrayList<>();
        String callProcedure = "{Call Proc_TipoTab(?, ?, ?, ?)}";
        
        try (CallableStatement stmt = con.prepareCall(callProcedure)) {
             
             stmt.setString("opcion", opcion);
             stmt.setInt("idTipoTab_", tipoTab.getIdTipoTabulacion());
             stmt.setString("tipo_", tipoTab.getTipo());
             stmt.setBoolean("estatus_", tipoTab.getEstatus());
             stmt.execute();
             
             ResultSet rs = stmt.getResultSet();
             while(rs.next()){
                 TipoTabulacion nuevoTab = new TipoTabulacion();
                 nuevoTab.setIdTipoTabulacion(rs.getInt("idTipoTabulacion"));
                 nuevoTab.setTipo(rs.getString("tipo"));
                 nuevoTab.setEstatus(rs.getBoolean("estatus"));
                 tiposTabulaciones.add(nuevoTab);
             }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return tiposTabulaciones;
    }
    
}
