/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.FondoCuentaBancoo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class FondoBancoDAO {
    private Connection connection;

    // Constructor que toma una conexión a la base de datos
    public FondoBancoDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar un nuevo registro en la tabla
    public void insertarFondoCuentaBanco(FondoCuentaBancoo fondoCuentaBanco) throws SQLException {
        String query = "INSERT INTO fondocuentabanco " +
                "(fecha, tipoOperacion, importe, saldo, concepto, idCliente) " +
                "VALUES (NOW(), ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
           // statement.setDate(1, fondoCuentaBanco.getFecha());
            statement.setString(1, fondoCuentaBanco.getTipoOperacion());
            statement.setDouble(2, fondoCuentaBanco.getImporte());
            statement.setDouble(3, 0);
            statement.setString(4, fondoCuentaBanco.getConcepto());
            statement.setInt(5, fondoCuentaBanco.getIdCliente());

            statement.executeUpdate();

            // Obtener la clave generada automáticamente (ID)
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fondoCuentaBanco.setIdFondoCuentaBanco(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado automáticamente.");
                }
            }
        }
    }

    // Método para obtener un registro por su ID
    public FondoCuentaBancoo obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM fondocuentabanco WHERE id_fondocuentabanco = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return convertirResultSetAFondoCuentaBanco(resultSet);
                } else {
                    return null; // No se encontró el registro
                }
            }
        }
    }

    // Método para obtener todos los registros de la tabla
    public List<FondoCuentaBancoo> obtenerTodos() throws SQLException {
        List<FondoCuentaBancoo> fondosCuentaBanco = new ArrayList<>();
        String query = "SELECT * FROM fondocuentabanco";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                FondoCuentaBancoo fondoCuentaBanco = convertirResultSetAFondoCuentaBanco(resultSet);
                fondosCuentaBanco.add(fondoCuentaBanco);
            }
        }
        return fondosCuentaBanco;
    }
    
    public List<FondoCuentaBancoo> obtenerTodosLosFondosBancoDeEsteMes() throws SQLException {
        List<FondoCuentaBancoo> fondosCuentaBanco = new ArrayList<>();
        String query = "SELECT * FROM fondocuentabanco f WHERE YEAR(f.fecha)=YEAR(NOW()) AND MONTH(f.fecha)= MONTH(NOW())";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                FondoCuentaBancoo fondoCuentaBanco = convertirResultSetAFondoCuentaBanco(resultSet);
                fondosCuentaBanco.add(fondoCuentaBanco);
            }
        }
        return fondosCuentaBanco;
    }
    
    public List<FondoCuentaBancoo> obtenerTodosLosFondosEfectivoXMes(int mes) throws SQLException {
        List<FondoCuentaBancoo> fondosEfectivo = new ArrayList<>();
        String query = "SELECT * FROM fondocuentabanco f WHERE YEAR(f.fecha)=YEAR(NOW()) AND MONTH(f.fecha)= "+mes+";";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                FondoCuentaBancoo fondoCuentaBanco = convertirResultSetAFondoCuentaBanco(resultSet);
                fondosEfectivo.add(fondoCuentaBanco);
                //System.out.println(""+fondoCuentaBanco.getConcepto() );
            }
        }
        return fondosEfectivo;
    }

    // Método para convertir un ResultSet a un objeto FondoCuentaBanco
    private FondoCuentaBancoo convertirResultSetAFondoCuentaBanco(ResultSet resultSet) throws SQLException {
        FondoCuentaBancoo fondoCuentaBanco = new FondoCuentaBancoo();
        fondoCuentaBanco.setIdFondoCuentaBanco(resultSet.getInt("id_fondocuentabanco"));
        fondoCuentaBanco.setFecha(resultSet.getDate("fecha"));
        fondoCuentaBanco.setTipoOperacion(resultSet.getString("tipoOperacion"));
        fondoCuentaBanco.setImporte(resultSet.getDouble("importe"));
        fondoCuentaBanco.setSaldo(resultSet.getDouble("saldo"));
        fondoCuentaBanco.setConcepto(resultSet.getString("concepto"));
        fondoCuentaBanco.setIdCliente(resultSet.getInt("idCliente"));
        return fondoCuentaBanco;
    }

}
