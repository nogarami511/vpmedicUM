/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Conexion;
import clases_hospital.Pagos;
import clases_hospital.ReporteCierre;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author alfar
 */
public class PagosDAO {

    private Connection connection;

    public PagosDAO(Connection connection) {
        this.connection = connection;
    }
    
    
    
    public int realizarPagosdeAlimento(Pagos pagoAlimento){
        
        try(CallableStatement stm = connection.prepareCall("{CALL PAGOALIMENTO(?,?,?,?,?) }")){
            stm.setInt(1, pagoAlimento.getId_folio());
            stm.setInt(2, pagoAlimento.getUsuario_cobro());
            stm.setInt(3, pagoAlimento.getIdTipoPago());
            stm.setString(4, pagoAlimento.getFormaPago());
            stm.setInt(5, pagoAlimento.getId_tipo_tarjeta());
            
            stm.execute();
            ResultSet rs = stm.getResultSet();
            
            if (rs.next()){
                pagoAlimento.setId_pago(rs.getInt("id_pago"));
            }
            
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        
        return pagoAlimento.getId_pago();
    }

    public void crear(Pagos pago) throws SQLException {
        String query = "INSERT INTO pagos (id_paciente, folio_paciente, id_tipo_pago, descripcion_pago, cantidad_pago, precio_unitario_pago, descuento_pago, sub_total_pago, iva_pago, total_pago, forma_pago, fecha_pago, usuario_cobro, estatus_pago_reembolso, fecha_reembolso, descripcion_reembolso, usuario_reembolso, id_folio, num_pago) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, pago.getId_pasiente());
            statement.setString(2, pago.getFolio_paciente());
            statement.setInt(3, pago.getIdTipoPago());
            statement.setString(4, pago.getDescripcionPago());
            statement.setInt(5, pago.getCantidad_pago());
            statement.setDouble(6, pago.getPrecio_unitario_pago());
            statement.setDouble(7, pago.getDescuento_pago());
            statement.setDouble(8, pago.getSub_total_pago());
            statement.setDouble(9, pago.getIva_pago());
            statement.setDouble(10, pago.getTotal_pago());
            statement.setString(11, pago.getFormaPago());
            statement.setInt(12, pago.getUsuario_cobro());
            statement.setInt(13, pago.getEstatus_pago_reembolso());
            statement.setTimestamp(14, pago.getFecha_reembolso() != null ? pago.getFecha_reembolso() : null);
            statement.setString(15, pago.getDescripcion_reembolso());
            statement.setInt(16, pago.getUsuario_reembolso());
            statement.setInt(17, pago.getId_folio());
            statement.setInt(18, pago.getNum_pago());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                pago.setId_pago(generatedKeys.getInt(1));
            }
        }
    }

    public int crearYDevolverIntPagos(Pagos pago) throws SQLException {
        int id_pagos = 0;
        String query = "INSERT INTO pagos (id_paciente, folio_paciente, id_tipo_pago, descripcion_pago, cantidad_pago, precio_unitario_pago, descuento_pago, sub_total_pago, iva_pago, total_pago, forma_pago, fecha_pago, usuario_cobro, estatus_pago_reembolso, fecha_reembolso, descripcion_reembolso, usuario_reembolso, id_folio, num_pago, pago_tipo, id_tipo_tarjeta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, pago.getId_pasiente());
            statement.setString(2, pago.getFolio_paciente());
            statement.setInt(3, pago.getIdTipoPago());
            statement.setString(4, pago.getDescripcionPago());
            statement.setInt(5, pago.getCantidad_pago());
            statement.setDouble(6, pago.getPrecio_unitario_pago());
            statement.setDouble(7, pago.getDescuento_pago());
            statement.setDouble(8, pago.getSub_total_pago());
            statement.setDouble(9, pago.getIva_pago());
            statement.setDouble(10, pago.getTotal_pago());
            statement.setString(11, pago.getFormaPago());
            statement.setInt(12, pago.getUsuario_cobro());
            statement.setInt(13, pago.getEstatus_pago_reembolso());
            statement.setTimestamp(14, pago.getFecha_reembolso() != null ? pago.getFecha_reembolso() : null);
            statement.setString(15, pago.getDescripcion_reembolso());
            statement.setInt(16, pago.getUsuario_reembolso());
            statement.setInt(17, pago.getId_folio());
            statement.setInt(18, generarNumeroDePago(pago.getId_folio()));
            statement.setInt(19, pago.getPago_tipo());
            statement.setInt(20, pago.getId_tipo_tarjeta());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                pago.setId_pago(generatedKeys.getInt(1));
                
                id_pagos = pago.getId_pago();
            }
        }
        return id_pagos;
    }

    private int generarNumeroDePago(int id_folio) throws SQLException {
        int numPago;
        String queryPregunta = "SELECT * FROM pagos WHERE id_folio = ?";
        String query = "SELECT num_pago FROM pagos WHERE id_folio = ? ORDER BY id DESC LIMIT 1;";
        try (PreparedStatement statement = connection.prepareStatement(queryPregunta)) {
            statement.setInt(1, id_folio);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    try (PreparedStatement statementInt = connection.prepareStatement(query)) {
                        statementInt.setInt(1, id_folio);
                        try (ResultSet resultSetInt = statementInt.executeQuery()) {
                            if (resultSetInt.next()) {
                             
                                numPago = resultSetInt.getInt(1) + 1;
                            } else {
                                numPago = 0;
                            }
                        }
                    }
                } else {
                    numPago = 1;
                }
            }
        }
        return numPago;
    }

    public Pagos leer(int id) throws SQLException {
        String query = "SELECT * FROM pagos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pagos pago = mapearPago(resultSet);
                    return pago;
                }
            }
        }
        return null;
    }
       public String DepositoEfectivo(int id_pago, double monto, int usuario_modificacion) throws SQLException{
          String respuesta = "";
            try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call DEPOSITOdeEFECTIVO(?,?,?)}")) {
                stm.setInt(1, id_pago);
                stm.setDouble(2, monto);
                stm.setInt(3, usuario_modificacion);

            // Ejecutar el procedimiento almacenado
            stm.execute();
            ResultSet rs = stm.getResultSet();
                if(rs.next()){
                    
                  respuesta = rs.getString("respuesta");
                }

            

        } catch (SQLException e) {
                System.err.println(""+ e.getNextException());
                respuesta = "Algo salio mal";
        }
           
           return respuesta;
       }
              public int GenerarDevolucion(int id_pago, double monto, int usuario_modificacion) throws SQLException{
          int id = 0;
            try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call DEVOLUCION_PAGOS(?,?,?)}")) {
                stm.setInt(1, id_pago);
                stm.setDouble(2, monto);
                stm.setInt(3, usuario_modificacion);

            // Ejecutar el procedimiento almacenado
            stm.execute();
            ResultSet rs = stm.getResultSet();
                if(rs.next()){
                    
                  id = rs.getInt("respuesta");
                }

            

        } catch (SQLException e) {
                System.err.println(""+ e.getNextException());
                id = 0;
        }
           
           return id;
       }
       
       
       
       public List<Pagos> obtenerPagosPacientes() throws SQLException{
           List<Pagos> listapagos = new ArrayList<>();
            try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call PAGOSenEFECTIVO()}")) {

            // Ejecutar el procedimiento almacenado
            stm.execute();
            ResultSet rs = stm.getResultSet();
                while(rs.next()){
                    
                    listapagos.add(mapearpagohecho(rs));
                }

         

        } catch (SQLException e) {
                System.err.println(""+ e.getNextException());
        }
           
           return listapagos;
       }
       public Pagos mapearpagohecho(ResultSet rs)throws SQLException{
           Pagos pago = new Pagos();
           
           pago.setId_pago(rs.getInt("id"));
           pago.setNombrePaciente(rs.getString("nombre_paciente"));
           pago.setFecha_pago(rs.getTimestamp("fecha_pago"));
           pago.setTotal_pago(rs.getDouble("total_pago"));
           
           return pago;
       }
    
    public List<Pagos> obtenerTodos() throws SQLException {
        List<Pagos> listaPagos = new ArrayList<>();
        String query = "SELECT * FROM pagos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pagos pago = mapearPago(resultSet);
                listaPagos.add(pago);
            }
        }
        return listaPagos;
    }

    public List<Pagos> ObtenerListaFactura() throws SQLException {
        Conexion con = new Conexion();
        connection = con.conectar2();
        
        CallableStatement stm = null;
        ResultSet rs = null;
        List<Pagos> listaPagos = new ArrayList<>();
        try{
        stm = connection.prepareCall("{call EFECTIVOaBANCO()}");

        stm.execute();

        rs = stm.getResultSet();
        while (rs.next()) {
            Pagos pago = mapearPacientesPago(rs);
            listaPagos.add(pago);
          
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPagos;

    }
    
    public String ActualizarFactura(String folio) throws SQLException{
        Conexion con = new Conexion();
        connection = con.conectar2();
        
        CallableStatement stm = null;
        try{
        stm = connection.prepareCall("{call AGREGARFACTURA(?)}");
        stm.setString(1, folio); // Reemplaza con el valor real para _nom
        stm.execute();
        return "SE REALIZO EL CAMBIO CORRECTAMENTE CORRECTA";
        }
        catch(SQLException e){
            e.printStackTrace();
            return  "ALGO SALIO MAL";
        }
    }

    public List<Pagos> obtenerPagosPorTarjeta() throws SQLException {
        List<Pagos> listaPagos = new ArrayList<>();
        String query = "";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pagos pago = mapearPago(resultSet);
                listaPagos.add(pago);
            }
        }
        return listaPagos;
    }

    public void actualizar(Pagos pago) throws SQLException {
        String query = "UPDATE pagos SET id_paciente = ?, folio_paciente = ?, id_tipo_pago = ?, descripcion_pago = ?, cantidad_pago = ?, precio_unitario_pago = ?, descuento_pago = ?, sub_total_pago = ?, iva_pago = ?, total_pago = ?, forma_pago = ?, fecha_pago = NOW(), usuario_cobro = ?, estatus_pago_reembolso = ?, fecha_reembolso = ?, descripcion_reembolso = ?, usuario_reembolso = ?, id_folio = ?, num_pago = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, pago.getId_pasiente());
            statement.setString(2, pago.getFolio_paciente());
            statement.setInt(3, pago.getIdTipoPago());
            statement.setString(4, pago.getDescripcionPago());
            statement.setInt(5, pago.getCantidad_pago());
            statement.setDouble(6, pago.getPrecio_unitario_pago());
            statement.setDouble(7, pago.getDescuento_pago());
            statement.setDouble(8, pago.getSub_total_pago());
            statement.setDouble(9, pago.getIva_pago());
            statement.setDouble(10, pago.getTotal_pago());
            statement.setString(11, pago.getFormaPago());
            statement.setInt(13, pago.getUsuario_cobro());
            statement.setInt(14, pago.getEstatus_pago_reembolso());
            statement.setTimestamp(15, pago.getFecha_reembolso() != null ? pago.getFecha_reembolso() : null);
            statement.setString(16, pago.getDescripcion_reembolso());
            statement.setInt(17, pago.getUsuario_reembolso());
            statement.setInt(18, pago.getId_folio());
            statement.setInt(19, pago.getNum_pago());
            statement.setInt(20, pago.getId_pago());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM pagos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Double optenerTotalPagosPaciente(int id_folio) throws SQLException {
        String query = "SELECT SUM(p.total_pago) AS total_pago FROM pagos p WHERE p.id_folio = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }
            }
        }
        return 0.0;
    }
    private Pagos mapearPacientesPago(ResultSet resultSet) throws SQLException{
        Pagos pago = new Pagos();
        
        pago.setFolioMod(resultSet.getString("Folio"));
        pago.setNombrePaciente(resultSet.getString("nombre"));
        pago.setTotal_pago(resultSet.getDouble("total"));
        
        
        
        return pago;
    }

    private Pagos mapearPago(ResultSet resultSet) throws SQLException {
        Pagos pago = new Pagos();
        pago.setId_pago(resultSet.getInt("id"));
        pago.setId_pasiente(resultSet.getInt("id_paciente"));
        pago.setFolio_paciente(resultSet.getString("folio_paciente"));
        pago.setIdTipoPago(resultSet.getInt("id_tipo_pago"));
        pago.setDescripcionPago(resultSet.getString("descripcion_pago"));
        pago.setCantidad_pago(resultSet.getInt("cantidad_pago"));
        pago.setPrecio_unitario_pago(resultSet.getDouble("precio_unitario_pago"));
        pago.setDescuento_pago(resultSet.getDouble("descuento_pago"));
        pago.setSub_total_pago(resultSet.getDouble("sub_total_pago"));
        pago.setIva_pago(resultSet.getDouble("iva_pago"));
        pago.setTotal_pago(resultSet.getDouble("total_pago"));
        pago.setFormaPago(resultSet.getString("forma_pago"));
        pago.setFecha_pago(resultSet.getTimestamp("fecha_pago"));
        pago.setUsuario_cobro(resultSet.getInt("usuario_cobro"));
        pago.setEstatus_pago_reembolso(resultSet.getInt("estatus_pago_reembolso"));
        pago.setFecha_reembolso(resultSet.getTimestamp("fecha_reembolso") != null ? resultSet.getTimestamp("fecha_reembolso") : null);
        pago.setDescripcion_reembolso(resultSet.getString("descripcion_reembolso"));
        pago.setUsuario_reembolso(resultSet.getInt("usuario_reembolso"));
        pago.setId_folio(resultSet.getInt("id_folio"));
        pago.setNum_pago(resultSet.getInt("num_pago"));
        pago.setPago_tipo(resultSet.getInt("pago_tipo"));
        return pago;
    }

    public ReporteCierre datosReporteCierre(int idFolio) throws SQLException {
        ReporteCierre rptCierre = null;
        String folio = null;
        Date fecha;
        Time hora;
        String fechaTexto = null;
        String horaTexto = null;
        String paciente = null;
        String medico = null;
        String habitacion = null;
        String descripcion = null;
        double costoDepocito = 0;
        double montoHastaElMomento = 0;
        double saldoACubrir = 0;
        String formaDePago = null;
        String concepto = null;
        int idPaquete;
        String sql = "SELECT f.folio, DATE(f.fecha_ingreso), TIME(f.fecha_ingreso), "
                + "p1.nombre_paciente AS paciente, m.nombre AS medico, "
                + "CONCAT(h1.tipo, ' ', h.numero_habitacion) AS habitacion, "
                + "p2.descripcion, f.cosoto_deposito, f.montohastaelmomento, f.saldoacubrir, "
                + "p.forma_pago, tp.nombre AS concepto, f.id_paquete "
                + "FROM pagos p "
                + "INNER JOIN tipo_pago tp ON tp.id = p.id_tipo_pago "
                + "INNER JOIN folios f ON p.id_folio = f.id "
                + "INNER JOIN pacientes p1 ON p1.id_folio = p.id_folio "
                + "INNER JOIN medicos m ON m.id_medico = p1.id_medico "
                + "INNER JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente "
                + "INNER JOIN habitacion h ON h.id_habitacion = ah.id_habitacion "
                + "INNER JOIN tipoHabitacion h1 ON h.id_tipo = h1.id_tipo "
                + "INNER JOIN paquetesmedicos p2 ON p2.id = f.id_paquete "
                + "WHERE p.id_folio = ? -- AND p.id_tipo_pago = 3";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFolio);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                folio = rs.getString(1);

                fecha = rs.getDate(2);
                hora = rs.getTime(3);
                paciente = rs.getString(4);
                medico = rs.getString(5);
                habitacion = rs.getString(6) + " " + rs.getString(7);
                descripcion = rs.getString(7);
                costoDepocito = rs.getDouble(8);
                montoHastaElMomento = rs.getDouble(9);
                saldoACubrir = rs.getDouble(10);
                formaDePago = rs.getString(11);
                concepto = rs.getString(12);
                idPaquete = rs.getInt(13);
                fechaTexto = String.valueOf(fecha);
                horaTexto = String.valueOf(hora);
                rptCierre = new ReporteCierre(folio, fechaTexto, horaTexto, paciente, medico, habitacion, descripcion, formaDePago, concepto, costoDepocito, montoHastaElMomento, saldoACubrir, idPaquete);

                
            }
        }
        return rptCierre;
    }

    public ObservableList<Pagos> obtenerPagosXIDPaciente(int idFolio) throws SQLException {
        ObservableList<Pagos> listaPagos = FXCollections.observableArrayList();
        String query = "SELECT * FROM pagos p WHERE p.id_folio = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pagos pago = mapearPago(resultSet);
                    listaPagos.add(pago);
                }
            }
        }
        return listaPagos;
    }

    public void actualizarFormaPago(int idPago, int idFormaPago, String tipoPago) throws SQLException {
        String query = "UPDATE pagos SET id_tipo_pago = ?, forma_pago = ?, fecha_pago = NOW() WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFormaPago);
            statement.setString(2, tipoPago);
            statement.setInt(3, idPago);
            statement.executeUpdate();
        }
       
    }

    public List<Pagos> obtenerPagosEnTransito() throws SQLException {
        List<Pagos> listaPagos = new ArrayList<>();
        String query = "SELECT p.*, p1.nombre_paciente FROM pagos p INNER JOIN pacientes p1 ON p1.id_paciente = p.id_paciente WHERE p.id_tipo_pago = 6";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pagos pago = mapearPagoConNombrePaciente(resultSet);
                listaPagos.add(pago);
            }
        }
        return listaPagos;
    }

    private Pagos mapearPagoConNombrePaciente(ResultSet resultSet) throws SQLException {
        Pagos pago = new Pagos();
        pago.setId_pago(resultSet.getInt("id"));
        pago.setId_pasiente(resultSet.getInt("id_paciente"));
        pago.setFolio_paciente(resultSet.getString("folio_paciente"));
        pago.setIdTipoPago(resultSet.getInt("id_tipo_pago"));
        pago.setDescripcionPago(resultSet.getString("descripcion_pago"));
        pago.setCantidad_pago(resultSet.getInt("cantidad_pago"));
        pago.setPrecio_unitario_pago(resultSet.getDouble("precio_unitario_pago"));
        pago.setDescuento_pago(resultSet.getDouble("descuento_pago"));
        pago.setSub_total_pago(resultSet.getDouble("sub_total_pago"));
        pago.setIva_pago(resultSet.getDouble("iva_pago"));
        pago.setTotal_pago(resultSet.getDouble("total_pago"));
        pago.setFormaPago(resultSet.getString("forma_pago"));
        pago.setFecha_pago(resultSet.getTimestamp("fecha_pago"));
        pago.setUsuario_cobro(resultSet.getInt("usuario_cobro"));
        pago.setEstatus_pago_reembolso(resultSet.getInt("estatus_pago_reembolso"));
        pago.setFecha_reembolso(resultSet.getTimestamp("fecha_reembolso") != null ? resultSet.getTimestamp("fecha_reembolso") : null);
        pago.setDescripcion_reembolso(resultSet.getString("descripcion_reembolso"));
        pago.setUsuario_reembolso(resultSet.getInt("usuario_reembolso"));
        pago.setId_folio(resultSet.getInt("id_folio"));
        pago.setNum_pago(resultSet.getInt("num_pago"));
        pago.setPago_tipo(resultSet.getInt("pago_tipo"));
        pago.setNombrePaciente(resultSet.getString("nombre_paciente"));
        return pago;
    }
}
