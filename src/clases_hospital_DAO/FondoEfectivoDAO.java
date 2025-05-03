/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.FondoEfectivo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FondoEfectivoDAO {

    private Connection connection;

    // Constructor
    public FondoEfectivoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<FondoEfectivo> obtenerTodosLosFondosEfectivoDeEsteMes() {
        List<FondoEfectivo> fondosEfectivo = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM fondoefectivo f WHERE YEAR(f.fecha)=YEAR(NOW()) AND MONTH(f.fecha)= MONTH(NOW())");

            while (resultSet.next()) {
                int idFolioEfectivo = resultSet.getInt("id_fondoEfectivo");
                Timestamp fecha = resultSet.getTimestamp("fecha");
                String tipoOperacion = resultSet.getString("tipoOperacion");
                double importe = resultSet.getDouble("importe");
                double saldo = resultSet.getDouble("saldo");
                String concepto = resultSet.getString("concepto");
                int idCliente = resultSet.getInt("idCliente");
                FondoEfectivo fondoEfectivo = new FondoEfectivo(idFolioEfectivo, fecha, tipoOperacion, importe, saldo, concepto, idCliente);
                fondosEfectivo.add(fondoEfectivo);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fondosEfectivo;
    }
    
    public List<FondoEfectivo> obtenerTodosLosFondosEfectivoXMes(int mes) {
        List<FondoEfectivo> fondosEfectivo = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM fondoefectivo f WHERE YEAR(f.fecha)=YEAR(NOW()) AND MONTH(f.fecha)= "+mes+";";
         
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int idFolioEfectivo = resultSet.getInt("id_fondoEfectivo");
                Timestamp fecha = resultSet.getTimestamp("fecha");
                String tipoOperacion = resultSet.getString("tipoOperacion");
                double importe = resultSet.getDouble("importe");
                double saldo = resultSet.getDouble("saldo");
                String concepto = resultSet.getString("concepto");
                int idCliente = resultSet.getInt("idCliente");
                FondoEfectivo fondoEfectivo = new FondoEfectivo(idFolioEfectivo, fecha, tipoOperacion, importe, saldo, concepto, idCliente);
                fondosEfectivo.add(fondoEfectivo);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fondosEfectivo;
    }

    public void insertarFondoEfectivo(FondoEfectivo fondoEfectivo) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO fondoefectivo (fecha, tipoOperacion, importe, saldo, concepto) VALUES (NOW(), ?, ?, ?, ?)");

            statement.setString(1, fondoEfectivo.getTipoOperacion());
            System.out.println(""+ fondoEfectivo.getTipoOperacion());
            statement.setDouble(2, fondoEfectivo.getImporte());
            statement.setDouble(3, fondoEfectivo.getSaldo());
            statement.setString(4, fondoEfectivo.getConcepto());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Otros métodos DAO: actualizarFondoEfectivo, eliminarFondoEfectivo, etc.
    public void cerrar() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private FondoEfectivo fondoEfectivoMapeo(ResultSet rs) {
        FondoEfectivo fondoEfectivo = new FondoEfectivo();
        return fondoEfectivo;
    }
}
