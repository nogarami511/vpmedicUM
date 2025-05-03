/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author olver
 */
public class ArmadoPaqueteMedicoCostosDAO {
    Connection con;
    
    public ArmadoPaqueteMedicoCostosDAO(Connection con) {
        this.con = con;
    }
    
    public void ActualizarArmadoPaquete(ArmadoPaqueteconMedicoCostos armadotraido ){
        try(CallableStatement stm = con.prepareCall("call ACTUALIZAR_ARMADO_PAQUETE_MEDICO (?,?,?,?)")){
            stm.setInt(1, armadotraido.getId_armado_paquete());
            stm.setDouble(2, armadotraido.getCosto_original());
            stm.setDouble(3, armadotraido.getFactor_insuno());
            stm.setInt(4, VPMedicaPlaza.userSystem);
            stm.execute();
           
            
        }catch ( SQLException e){
            e.printStackTrace();
        }
        
        
    }
    
    public List<ArmadoPaqueteconMedicoCostos> traerArmadoPaqueteCostos(long id_paquete){
        List<ArmadoPaqueteconMedicoCostos> listaPaqueteCostos = new ArrayList<>();
        
        try(CallableStatement stm = con.prepareCall("call TRAER_ARMADO_PAQUETE_NVO_FORMATO (?)")){
            stm.setLong(1, id_paquete);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                listaPaqueteCostos.add(mapearCostosArmadoPaquete(rs));
            }
            
        }catch ( SQLException e){
            e.printStackTrace();
        }
        return listaPaqueteCostos;
    }
    private ArmadoPaqueteconMedicoCostos mapearCostosArmadoPaquete(ResultSet rs) throws SQLException{
        ArmadoPaqueteconMedicoCostos armado = new  ArmadoPaqueteconMedicoCostos();
        // DATOS RELEVANTES
        armado.setId_armado_paquete(rs.getInt("id_armadopaquete"));
        armado.setNombre_insumo(rs.getString("nombre_insumo"));
        // COSTO UNITARIO POR INSUMO
        armado.setCosto_original(rs.getDouble("costo_original"));
        armado.setCosto_nuevo(rs.getDouble("costo_nuevo"));
        armado.setDiferencia_costo(rs.getDouble("diferencia_costo"));
        armado.setDiferencia_costo_porcentaje(rs.getDouble("diferencia_costo_porcentaje"));
        // CANTIDAD POR INSUMO
        armado.setCantidad(rs.getDouble("cantidad"));
        // SUBTOTAL EN COSTO
        armado.setCosto_subtotal_original(rs.getDouble("subtotal_costo_actual"));
        armado.setCosto_subtotal_nuevo(rs.getDouble("subtotal_costo_nuevo"));
        armado.setDiferencia_costo_subtotal(rs.getDouble("diferencia_costo_subtotal"));
        armado.setDiferencia_costo_subtotal_porcentaje(rs.getDouble("diferencia_costo_subtotal_porcentaje"));
        // FANTOR POR INSIMO
        armado.setFactor_insuno(rs.getDouble("factor_insumo"));
        // PRECIO VENTA
        armado.setPrecio_total_actual(rs.getDouble("precio_total_actual"));
        armado.setPrecio_total_nuevo(rs.getDouble("precio_total_nvo"));
        armado.setDiferencia_total(rs.getDouble("diferencia_precio_total"));
        armado.setDiferencia_total_porcentaje(rs.getDouble("diferencia_precio_total_porcentaje"));
        // GANANCIA
        armado.setMonto_ganancia_original(rs.getDouble("ganancia_original"));
        armado.setMoonto_ganancia_nuevo(rs.getDouble("ganancia_nueva"));
        // COLOR 
        armado.setColorrow(rs.getInt("color"));
        
        // VALOR X
        armado.setValor_original_no_cambiado(rs.getDouble("costo_original"));
        
              
        return armado;
        
    }
    
    
}
