/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ComprasInsumosP;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author PC
 */
public class CompraInsumoPDAO {
    Connection con;
    
     Alert alertaError = new Alert(Alert.AlertType.ERROR);

    public CompraInsumoPDAO(Connection con) {
        this.con = con;
    }
    
    public void CambiarEstatusdeRecibido(int id_compra_insumoP){
        try(CallableStatement stm = con.prepareCall("{call ACTUALIZAR_COMPRAp_SEGUN_RECIBIDOS(?)}")){
            stm.setInt(1, id_compra_insumoP);
            stm.execute();
        }catch(SQLException e){
            e.printStackTrace();
               alertaError.setTitle("ALGO SALIO MAL");
               alertaError.setHeaderText("OCURRIO ALGO INESPERADO");
               alertaError.setContentText("Error en la base de datos " + e.getMessage());
               alertaError.showAndWait();
        }
    }
    
    public List<ComprasInsumosP> listaComprasPorIdProveedor(int id_proveedor) throws SQLException{
        List<ComprasInsumosP> listaComprasInsumos = new ArrayList<>();
           
             
           try(CallableStatement stm =  con.prepareCall("{call TRAER_COMPRASp_ID_PROVEEDOR(?)}")){
                 
               stm.setInt(1, id_proveedor);
               stm.execute();
               
               ResultSet rs = stm.getResultSet();
               
               while(rs.next()){
                   listaComprasInsumos.add(mapearCompras(rs));
                   
               }
           }catch(SQLException e){
               e.printStackTrace();
               alertaError.setTitle("ALGO SALIO MAL");
               alertaError.setHeaderText("OCURRIO ALGO INESPERADO");
               alertaError.setContentText("Error en la base de datos " + e.getMessage());
               alertaError.showAndWait();
               
           }
        
        
        return  listaComprasInsumos;
    }
    public List<ComprasInsumosP> listaComprasPorIdProveedorSoloFolio(int id_proveedor) throws SQLException{
        List<ComprasInsumosP> listaComprasInsumos = new ArrayList<>();
           
             
           try(CallableStatement stm =  con.prepareCall("{call TRAER_COMPRAS_POR_RECIBIR_id_proveedor(?)}")){
                 
               stm.setInt(1, id_proveedor);
               stm.execute();
               
               ResultSet rs = stm.getResultSet();
               
               while(rs.next()){
                   listaComprasInsumos.add(mapearComprasSoloFolio(rs));
                   
               }
           }catch(SQLException e){
               e.printStackTrace();
               alertaError.setTitle("ALGO SALIO MAL");
               alertaError.setHeaderText("OCURRIO ALGO INESPERADO");
               alertaError.setContentText("Error en la base de datos " + e.getMessage());
               alertaError.showAndWait();
               
           }
        
        
        return  listaComprasInsumos;
    }
    public int ActualizarEstatusComprasInsumosP(ComprasInsumosP compraP) throws SQLException{
        int id_compra_p = 0;
             
           try(CallableStatement stm =  con.prepareCall("{call ACTUALIZAR_ESTATUS_COMPRAS_INSUMOS_P_(?,?,?,?,?,?,?,?,?,?)}")){
                 
               stm.setInt(1, compraP.getId_compra_insumosp());
               stm.setDouble(2, compraP.getDescuento());
               stm.setDouble(3, compraP.getSubtotal());
               stm.setDouble(4, compraP.getIvaDouble());
               stm.setDouble(5, compraP.getTotalCompra());
               stm.setDouble(6, compraP.getTotalCompra());
               stm.setDouble(7, compraP.getImporteComision());
               stm.setDouble(8, compraP.getTotalSinComision());
               stm.setString(9, compraP.getFolioCompra());
               stm.setInt(10, compraP.getId_proveedor());
               
               stm.execute();
               
               ResultSet rs = stm.getResultSet();
               
               if(rs.next()){
                   id_compra_p = rs.getInt(1);
               }
               
               
               
           
           }catch(SQLException e){
               e.printStackTrace();
               alertaError.setTitle("ALGO SALIO MAL");
               alertaError.setHeaderText("OCURRIO ALGO INESPERADO");
               alertaError.setContentText("Error en la base de datos " + e.getMessage());
               alertaError.showAndWait();
               
           }
        
        return  id_compra_p;
     
    }

    private ComprasInsumosP mapearCompras(ResultSet rs) throws SQLException {
        ComprasInsumosP compraP = new ComprasInsumosP();
           compraP.setId_compra_insumosp(rs.getInt("id_compra_insumosp"));
           compraP.setObservacion(rs.getString("observacion"));
           compraP.setFecha_creacion(rs.getString("fecha_creacion"));
           compraP.setEstatString(rs.getString("estatus"));
           compraP.setEstatRecibidoString(rs.getString("estatus_recibido"));
           compraP.setEstatPagoString(rs.getString("estatus_pago"));
           
        
        
        return  compraP;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private ComprasInsumosP mapearComprasSoloFolio(ResultSet rs) throws SQLException {
        ComprasInsumosP compraP = new ComprasInsumosP();
           compraP.setId_compra_insumosp(rs.getInt("id_compra_insumosp"));
           compraP.setFolioCompra(rs.getString("folio_compra"));

           
        
        
        return  compraP;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
