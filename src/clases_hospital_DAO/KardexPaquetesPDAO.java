/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.*;
import java.sql.*;

/**
 *
 * @author olver
 */
public class KardexPaquetesPDAO {
    Connection con;

    public KardexPaquetesPDAO(Connection con) {
        this.con = con;
    }
    
    public KardexPaquetesP ultima_modificacion(long id_paquete){
        KardexPaquetesP kdxPP = new KardexPaquetesP();
        
        try(CallableStatement stm = con.prepareCall("{call VER_ULTIMA_MODIFICACION (?) }")){
            stm.setLong(1, id_paquete);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if(rs.next()){
                kdxPP.setId_kdxpaquetesmedico(rs.getInt("id_kdxpaquetesmedico"));
                kdxPP.setUsuario_modificacion(rs.getString("usuario"));
                kdxPP.setNombre(rs.getString("nombre_paquete"));
                kdxPP.setFecha_modificacion(rs.getString("fecha_mod"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        
        
        return kdxPP;
        
    }
    
    
}
