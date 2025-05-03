/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Fondocuentabanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class FondocuentabancoDAO {
    private Connection connection;

    // Constructor que recibe una conexión a la base de datos
    public FondocuentabancoDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para crear un nuevo registro en la tabla
    public void crearFondoCuentaBanco(Fondocuentabanco fondocuentabanco) throws SQLException {
        String query = "INSERT INTO fondocuentabanco (fecha, tipoOperacion, importe, saldo, concepto, idCliente) VALUES (NOW(), ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, fondocuentabanco.getTipoOperacion());
            statement.setDouble(2, fondocuentabanco.getImporte());
            statement.setDouble(3, fondocuentabanco.getSaldo());
            statement.setString(4, fondocuentabanco.getConcepto());
            statement.setInt(5, fondocuentabanco.getIdCliente());
            statement.executeUpdate();
        }
    }
    public double fondocuentabancofijo() throws SQLException{
        double monto = 0.0;
        String query = "SELECT monto FROM fondocuentabancofijo WHERE id = 1";
          try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()){
              if (resultSet.next()){
                  monto = resultSet.getDouble("monto");
              }
              }
        
        return monto;
    }
    public void actualizarfindobancofijo(double monto) throws SQLException{
        String query = "UPDATE fondocuentabancofijo SET monto = ? WHERE id = 1";
        try (PreparedStatement statement = connection.prepareStatement(query);){
            statement.setDouble(1, monto);
            statement.executeUpdate();
        }
    }

    // Método para leer todos los registros de la tabla
    public List<Fondocuentabanco> leerTodosFondosCuentaBanco() throws SQLException {
        List<Fondocuentabanco> fondosCuentaBanco = new ArrayList<>();
        String query = "SELECT * FROM fondocuentabanco";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Fondocuentabanco fondocuentabanco = new Fondocuentabanco();
                fondocuentabanco.setId(resultSet.getInt("id_fondocuentabanco"));
                fondocuentabanco.setFecha(resultSet.getDate("fecha"));
                fondocuentabanco.setTipoOperacion(resultSet.getString("tipoOperacion"));
                fondocuentabanco.setImporte(resultSet.getDouble("importe"));
                fondocuentabanco.setSaldo(resultSet.getDouble("saldo"));
                fondocuentabanco.setConcepto(resultSet.getString("concepto"));
                fondocuentabanco.setIdCliente(resultSet.getInt("idCliente"));
                fondosCuentaBanco.add(fondocuentabanco);
            }
        }
        return fondosCuentaBanco;
    }

    // Método para leer un registro por su ID
    public Fondocuentabanco leerFondoCuentaBancoPorId(int id) throws SQLException {
        Fondocuentabanco fondocuentabanco = null;
        String query = "SELECT * FROM fondocuentabanco WHERE id_fondocuentabanco = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    fondocuentabanco = new Fondocuentabanco();
                    fondocuentabanco.setId(resultSet.getInt("id_fondocuentabanco"));
                    fondocuentabanco.setFecha(resultSet.getDate("fecha"));
                    fondocuentabanco.setTipoOperacion(resultSet.getString("tipoOperacion"));
                    fondocuentabanco.setImporte(resultSet.getDouble("importe"));
                    fondocuentabanco.setSaldo(resultSet.getDouble("saldo"));
                    fondocuentabanco.setConcepto(resultSet.getString("concepto"));
                    fondocuentabanco.setIdCliente(resultSet.getInt("idCliente"));
                }
            }
        }
        return fondocuentabanco;
    }

    // Método para actualizar un registro en la tabla
    public void actualizarFondoCuentaBanco(Fondocuentabanco fondocuentabanco) throws SQLException {
        String query = "UPDATE fondocuentabanco SET fecha = ?, tipoOperacion = ?, importe = ?, saldo = ?, concepto = ?, idCliente = ? WHERE id_fondocuentabanco = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, fondocuentabanco.getFecha());
            statement.setString(2, fondocuentabanco.getTipoOperacion());
            statement.setDouble(3, fondocuentabanco.getImporte());
            statement.setDouble(4, fondocuentabanco.getSaldo());
            statement.setString(5, fondocuentabanco.getConcepto());
            statement.setInt(6, fondocuentabanco.getIdCliente());
            statement.setInt(7, fondocuentabanco.getId());
            statement.executeUpdate();
        }
    }
}
