/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.CompraInsumoDetalle;
import clases_hospital.Insumo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author PC
 */
public class ComprasInsumosDetalleDAO {
    Connection con;
    
     Alert alertaError = new Alert(Alert.AlertType.ERROR);

    public ComprasInsumosDetalleDAO(Connection connection) {
        this.con = connection;
    }
    
    
    public List<Insumo> traerInsumosCompra(int Id_CompraInsumosP){
        List<Insumo> listaComprasInsumos = new ArrayList<>();
        
        try(CallableStatement stm = con.prepareCall("{call TRAER_INSUMOS_ID_COMPRA_INSUMOSp(?)}")){
            stm.setInt(1, Id_CompraInsumosP);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            
                while(rs.next()){
                    listaComprasInsumos.add(mapearInsumosdeCompra(rs));
                    
                }
        }catch(SQLException e){
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ERROR EN BASE DE DATOS : "+ e.getMessage());
            alertaError.showAndWait();
            
            e.printStackTrace();
        }
        
        
        return  listaComprasInsumos;
        
    }
    public List<CompraInsumoDetalle> traerCompraInsumoDetallesporCantidad(int Id_CompraInsumosP){
        List<CompraInsumoDetalle> listaComprasInsumos = new ArrayList<>();
        
        try(CallableStatement stm = con.prepareCall("{call TRAER_COMPRAS_DETALLE_ID_COMPRAinsP(?)}")){
            stm.setInt(1, Id_CompraInsumosP);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            
                while(rs.next()){
                    listaComprasInsumos.add(mapearInsumosdeCompraconfaltas(rs));
                    
                }
        }catch(SQLException e){
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ERROR EN BASE DE DATOS : "+ e.getMessage());
            alertaError.showAndWait();
            
            e.printStackTrace();
        }
        
        
        return  listaComprasInsumos;
        
    }
    
    public void actualizaroIngresarCompraInsumoDET(Insumo insumoComprado){
        
        try(CallableStatement stm = con.prepareCall("{call ACTUALIZAR_COMPRA_INSUMO_DET (?,?,?,?,?,?,?) }")){
            stm.setInt(1, insumoComprado.getId_comprainsumoP());
            stm.setInt(2, insumoComprado.getId_comprainsdet());
            stm.setDouble(3, insumoComprado.getCantidadd_unitarias());
            stm.setDouble(4, insumoComprado.getCosto_compra_unitaria());
            stm.setDouble(5, insumoComprado.getDescuento());
            stm.setInt(6, insumoComprado.getId());
            stm.setDouble(7, insumoComprado.getIva());
            stm.execute();
            
            
        }catch(SQLException e){
            e.printStackTrace();
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("ERROR EN BASE DE DATOS : "+ e.getMessage());
            alertaError.showAndWait();
        }
        
        
    }

    private Insumo mapearInsumosdeCompra(ResultSet rs) throws SQLException {
        Insumo insumo = new  Insumo();
        
        insumo.setId_comprainsdet(rs.getInt("id_compras_insumo_detalle"));
        insumo.setId(rs.getInt("id_insumo"));
        insumo.setNombre(rs.getString("nombre"));
        insumo.setCantidad(rs.getDouble("cantidad"));
        insumo.setCantidad_unitariaxcaja(rs.getDouble("cantidad_caja"));
        insumo.setCantidadd_unitarias(rs.getDouble("cantidad_unitaria"));              
        insumo.setCosto_compra_caja(rs.getDouble("costo_compra_caja"));
        insumo.setCosto_compra_unitaria(rs.getDouble("precio_unitario"));
        insumo.setIva(rs.getDouble("iva"));        
        insumo.setDescuento(rs.getDouble("descuento"));
        insumo.setImporte(rs.getDouble("importe"));
        
        
        
        

//  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        return insumo;
    }
    private CompraInsumoDetalle mapearInsumosdeCompraconfaltas(ResultSet rs) throws SQLException {
        CompraInsumoDetalle insumo = new  CompraInsumoDetalle();
        
        insumo.setId_compras_insumo_detalle(rs.getInt("id_compras_insumo_detalle"));
        insumo.setId_insumo(rs.getInt("id_insumo"));
        insumo.setNombreInsumo(rs.getString("nombre"));
        insumo.setCantidad(rs.getDouble("cantidad"));
        insumo.setCantidad_recibida(rs.getDouble("cantidad_recibida"));
        insumo.setCantidad_faltante(rs.getDouble("faltante"));
      
//  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        return insumo;
    }
    
   
    
}
