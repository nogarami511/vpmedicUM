/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author gamae
 */
public class CostoHabitacionDAO {

    private Connection connection;

    public CostoHabitacionDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertCostoHabitacion(int idHabitacion, String precioNombre, double precio, int horas, int minutos, int horasTolerancia, int minutosTolerancia) throws SQLException {
        String query = "INSERT INTO costoHabitacion (id_habitacion, precio_nombre, precio, horas, minutos, horas_tolerancia, minutos_tolerancia, id_usuario_modificacion, fecha_modificacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idHabitacion);
            statement.setString(2, precioNombre);
            statement.setDouble(3, precio);
            statement.setInt(4, horas);
            statement.setInt(5, minutos);
            statement.setInt(6, horasTolerancia);
            statement.setInt(7, minutosTolerancia);
            statement.setInt(8, VPMedicaPlaza.userSystem);

            statement.executeUpdate();
        }
    }

    public void actualizarCostoHabitacion(int idHabitacion, String precioNombre, double precio, int horas, int minutos, int horasTolerancia, int minutosTolerancia, int idPrecio) throws SQLException {
        String sql = "UPDATE costoHabitacion SET id_habitacion = ?, precio_nombre = ?, precio =?, horas = ?, minutos = ?, horas_tolerancia =?, minutos_tolerancia = 0?,"
                + "id_usuario_modificacion =?, fecha_modificacion = NOW() WHERE id_precio =?;";
        //FALTA EL USUARIO MODIFICCACION PARA LA CONSULTA
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idHabitacion);
            statement.setString(2, precioNombre);
            statement.setDouble(3, precio);
            statement.setInt(4, horas);
            statement.setInt(5, minutos);
            statement.setInt(6, horasTolerancia);
            statement.setInt(7, minutosTolerancia);
            statement.setInt(8, VPMedicaPlaza.userSystem);
            statement.setInt(9, idPrecio);
            statement.executeUpdate();
        }
    }

    public ObservableList<CostoHabitacion> llenarTabla() throws SQLException {

        ObservableList<CostoHabitacion> costos = FXCollections.observableArrayList();
        CostoHabitacion c = null;
        String tipoC = null;
        String sql = "SELECT h.id_precio, h.id_habitacion, h.precio_nombre, h.precio,h.horas,h.minutos,h.horas_tolerancia,h.minutos_tolerancia FROM costoHabitacion h";

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

                String sqlTipoCosto = "SELECT h.tipo FROM tipoHabitacion h WHERE h.id_tipo ='" + resultSet.getInt(2) + "'";
                PreparedStatement stmtTipoC = connection.prepareStatement(sqlTipoCosto);
                ResultSet rsTipoCosto = stmtTipoC.executeQuery();
                while (rsTipoCosto.next()) {
                    tipoC = rsTipoCosto.getString(1);
                }

                c = new CostoHabitacion(
                        resultSet.getInt(1),//id costo
                        resultSet.getInt(2),//idHabitacion
                        resultSet.getString(3),//nomre del costo
                        resultSet.getDouble(4),//precio 
                        resultSet.getInt(5),//horas
                        resultSet.getInt(6),//minutos
                        resultSet.getInt(7),//horas tolerancia
                        resultSet.getInt(8),// minutos tolerancia
                        tipoC);//nombre del tipo de habitacion
                costos.add(c);
            }

        }
        return costos;
    }

    public double obtenerIdTipoHabitacion(int idHabitacion) throws SQLException {
        double costoHabitacion = 0;
                                                                    /*idTipoHabitacion*/
        String query = "SELECT c.precio FROM costohabitacion c WHERE c.id_habitacion = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idHabitacion);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    costoHabitacion = resultSet.getDouble(1);
                }
            }
        }
        return costoHabitacion;
    }
}
