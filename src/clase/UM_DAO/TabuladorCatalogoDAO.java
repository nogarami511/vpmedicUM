/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM_DAO;

import clase.Conexion;
import java.sql.Connection;
import clase.UM.Tabulacion;
import clase.UM.TipoTabulacion;
import clases_hospital.TipoHabitacion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author theso
 */
public class TabuladorCatalogoDAO {
    Conexion conec = new Conexion();
    Connection con;
   
    // Listar tabulaciones
    public List<Tabulacion> ejecutarProcedimiento(String opcion, Tabulacion tabulacion){        
        con = conec.conectar2();
        
        List<Tabulacion> tabulaciones = new ArrayList<>();
        String callProcedure = "{Call Proc_Tabuladores(?, ?, ?, ?, ?,?)}";
        
        try (CallableStatement stmt = con.prepareCall(callProcedure)){
            System.out.println(""+ opcion);
            stmt.setString("opcion", opcion); 
            stmt.setInt("idTabulador_", tabulacion.getId());
            stmt.setString("nombre_", tabulacion.getNombre());
            stmt.setString("nota_", tabulacion.getNota());
            stmt.setBoolean("estatus_", tabulacion.getEstatus());
            stmt.setInt("idTipoTabulador_", tabulacion.getTipoTabulacion().getIdTipoTabulacion());
            stmt.execute();
            
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Tabulacion tab = new Tabulacion();
                    tab.setTipoTabulacion(new TipoTabulacion());
                    tab.setId(rs.getInt("idTabulador"));
                    tab.setNombre(rs.getString("nombre"));
                    tab.setNota(rs.getString("nota"));
                    tab.setEstatus(rs.getBoolean("estatus"));
                    tab.getTipoTabulacion().setTipo(rs.getString("tipo"));
                    tab.getTipoTabulacion().setIdTipoTabulacion(rs.getInt("idTipoTabulacion"));
                    tabulaciones.add(tab);
            }            
        }
                       
        catch (SQLException e){
            e.printStackTrace();
        }
        return tabulaciones;
    }
    
    // Agregar Tabulacion 
    public void agregarTabulacion(int id, String nombre, String nota, int tipoTabulacion) {
    // Crear y llenar el objeto Tabulacion
    Tabulacion tab = new Tabulacion();
    tab.setId(id);
    tab.setNombre(nombre);
    tab.setNota(nota);
    tab.setEstatus(true);
    // Configurar tipo de tabulación (esto depende de cómo lo selecciones en tu UI)
    TipoTabulacion tipo = new TipoTabulacion();
    tipo.setIdTipoTabulacion(tipoTabulacion); // Ajusta esto según corresponda
    tab.setTipoTabulacion(tipo);
    // Llamar al procedimiento almacenado
    List<Tabulacion> resultado = ejecutarProcedimiento("agregar", tab);
}
    
    
    
}
