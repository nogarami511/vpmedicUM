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
import javafx.scene.control.Alert;
import static vpmedicaplaza.VPMedicaPlaza.*;

/**
 *
 * @author PC
 */
public class CorteCajaDAO {

    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);

    public CorteCajaDAO(Connection conexion) {
        this.con = conexion;

    }

    public int CorteCaja(Double ini_efec) {

        try (CallableStatement stm = con.prepareCall("{call CORTE_CAJA_INI_FIN (?,?,?)}")) {
            stm.setInt(1, userSystem);
            stm.setDouble(2, ini_efec);
            stm.setInt(3, corte_caja);
            stm.execute();

            ResultSet rs = stm.getResultSet();
            if (rs.next()) {
                corte_caja = rs.getInt("id_corte");
            }

        } catch (SQLException e) {
            alertaError.setHeaderText("Algo Salio Mal");
            alertaError.setContentText("Algo salio mal favor de intentar de nuevo mas tarde");
            alertaError.showAndWait();
            e.printStackTrace();
        }

        return corte_caja;
    }

    public void ValidarCorte(CorteCaja corte) {

        try (CallableStatement stm = con.prepareCall("{call VALIDARCORTE (?)}")) {
            stm.setInt(1, corte.getId_corte_caja());

            stm.execute();

        } catch (SQLException e) {
            alertaError.setHeaderText("Algo Salio Mal");
            alertaError.setContentText("Algo salio mal favor de intentar de nuevo mas tarde");
            alertaError.showAndWait();
            e.printStackTrace();
        }

    }

    public List<CorteCaja> CortesCajaPendientes() {
        List<CorteCaja> listacortes = new ArrayList<>();
        try (CallableStatement stm = con.prepareCall("{call CORTES_CAJA_PENDIENTES ()}")) {

            stm.execute();

            ResultSet rs = stm.getResultSet();
            while (rs.next()) {
                listacortes.add(mapearcortes(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            alertaError.setHeaderText("Algo Salio Mal");
            alertaError.setContentText("Algo salio mal favor de intentar de nuevo mas tarde");
            alertaError.showAndWait();
        }

        return listacortes;
    }

    public List<Pagos> PagosCorteCaja(CorteCaja cortecaja) {
        List<Pagos> listapagos = new ArrayList<>();
        try (CallableStatement stm = con.prepareCall("{call PAGOS_DEL_DIA_ID_COBRO (?,?)}")) {
            System.out.println(""+ cortecaja.getId_usuario());
            System.out.println(""+ cortecaja.getId_corte_caja());
            stm.setInt(1, cortecaja.getId_usuario());
            stm.setInt(2, cortecaja.getId_corte_caja());
            stm.execute();

            ResultSet rs = stm.getResultSet();
            while (rs.next()) {
                listapagos.add(mapearpagos(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            alertaError.setHeaderText("Algo Salio Mal");
            alertaError.setContentText("Algo salio mal favor de intentar de nuevo mas tarde");
            alertaError.showAndWait();
        }

        return listapagos;
    }

    private CorteCaja mapearcortes(ResultSet rs) throws SQLException {
        CorteCaja corte = new CorteCaja();

        corte.setId_corte_caja(rs.getInt("id_corte_caja"));
        corte.setId_usuario(rs.getInt("id_usuario_cobro"));
        corte.setValidar(rs.getBoolean("validar"));
        corte.setNombre_usuario(rs.getString("nombre"));

        return corte;
    }

    private Pagos mapearpagos(ResultSet rs) throws SQLException {
        Pagos pago = new Pagos();

        pago.setId_pago(rs.getInt("id"));
        pago.setNombrePaciente(rs.getString("nombre_paciente"));
        pago.setFormaPago(rs.getString("forma_pago"));
        pago.setTotal_pago(rs.getDouble("total_pago"));

        return pago;
    }

}
