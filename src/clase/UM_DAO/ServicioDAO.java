/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase.UM_DAO;

import clase.Conexion;
import clase.UM.Servicio;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author theso
 */
public class ServicioDAO {
        Conexion conec = new Conexion();
        Connection con;
        
        public List<Servicio> ejecutarProcedimiento(String opcion, Servicio servicio) throws SQLException{
            con = conec.conectar2();
            List<Servicio> servicios = new ArrayList<>();
            String callProcedure = "{Call Proc_ServiciosTabuladores(?,?,?,?,?,?,?,?,?,?,?)}";
            
            try(CallableStatement stmt = con.prepareCall(callProcedure)){
                stmt.setString("", opcion);
                stmt.setInt("idTabulacion_",servicio.getIdTabServ());
                stmt.setString("lote_", servicio.getLote());
                stmt.setInt("cantidad_", servicio.getCantidad());
                stmt.setString("descripcion_", servicio.getDescripcion());
                stmt.setString("marca_", servicio.getMarca());
                stmt.setDouble("precioUnitario_", servicio.getPrecioUnitario());
                stmt.setDouble("total_", servicio.getTotal());
                stmt.setBoolean("estatus", servicio.getEstatus());
                stmt.setInt("idUnidadVenta", servicio.getUnidadServicio().getIdUnidadVenta());
                stmt.setInt("idTabServ_", servicio.getIdTabServ());
                stmt.execute();
                
                ResultSet rs = stmt.getResultSet();
                while(rs.next()){
                    Servicio serv = new Servicio();
                        serv.setIdTabServ(rs.getInt("idTabServ"));
                        serv.setLote(rs.getString("lote"));
                        serv.setCantidad(rs.getInt("cantidad"));
                        serv.setDescripcion(rs.getString("descripcion"));
                        serv.setMarca(rs.getString("marca"));
                        serv.setPrecioUnitario(rs.getDouble("precioU"));
                        serv.setTotal(rs.getDouble("total"));
                        serv.setEstatus(rs.getBoolean("estatus"));
                        serv.getUnidadServicio().setIdUnidadVenta(rs.getInt("idUnidadVenta"));
                        serv.getTabulador().setId(rs.getInt("idTabulador"));
                        servicios.add(serv);                        
                }           
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            return servicios;
        }
}
