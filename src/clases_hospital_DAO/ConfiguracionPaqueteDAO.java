/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.ConfiguracionPaqueteLista;
import clases_hospital.ConfiguracionPaquete;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConfiguracionPaqueteDAO {

    private Connection con;

    public ConfiguracionPaqueteDAO(Connection conexion) {
        con = conexion;
    }

    public void insert(ConfiguracionPaquete configuracionPaquete) throws SQLException {
        String query = "INSERT INTO configuracionpaquete (id_insumo, nombre_insumo, precio_insumo, id_folio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, configuracionPaquete.getId_insumo());
            statement.setString(2, configuracionPaquete.getNombre_insumo());
            statement.setDouble(3, configuracionPaquete.getPrecio_insumo());
            statement.setInt(4, configuracionPaquete.getId_folio());

            statement.executeUpdate();
        }
    }

    public void update(ConfiguracionPaquete configuracionPaquete) throws SQLException {
        String query = "UPDATE configuracionpaquete SET id_insumo=?, nombre_insumo=?, precio_insumo=?, id_folio=? WHERE id=?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, configuracionPaquete.getId_insumo());
            statement.setString(2, configuracionPaquete.getNombre_insumo());
            statement.setDouble(3, configuracionPaquete.getPrecio_insumo());
            statement.setInt(4, configuracionPaquete.getId_folio());
            statement.setInt(5, configuracionPaquete.getId());

            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM configuracionpaquete WHERE id=?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public ObservableList<ConfiguracionPaquete> traerArmadoPorFolio(int idFolio) throws SQLException {
        ObservableList<ConfiguracionPaquete> configuraciones = FXCollections.observableArrayList();
        String query = "SELECT * FROM configuracionpaquete WHERE id_folio=?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, idFolio);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ConfiguracionPaquete configuracionPaquete = new ConfiguracionPaquete();
                    configuracionPaquete.setId(resultSet.getInt("id"));
                    configuracionPaquete.setId_insumo(resultSet.getInt("id_insumo"));
                    configuracionPaquete.setNombre_insumo(resultSet.getString("nombre_insumo"));
                    configuracionPaquete.setPrecio_insumo(resultSet.getDouble("precio_insumo"));
                    configuracionPaquete.setId_folio(resultSet.getInt("id_folio"));

                    configuraciones.add(configuracionPaquete);
                }
            }
        }
        return configuraciones;
    }

    public List<ConfiguracionPaquete> listaPaquete(int idFolio) throws SQLException {
        List<ConfiguracionPaquete> configuraciones = new ArrayList();
        String query = "SELECT * FROM configuracionpaquete WHERE id_folio=?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, idFolio);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ConfiguracionPaquete configuracionPaquete = new ConfiguracionPaquete();
                    configuracionPaquete.setId(resultSet.getInt("id"));
                    configuracionPaquete.setId_insumo(resultSet.getInt("id_insumo"));
                    configuracionPaquete.setNombre_insumo(resultSet.getString("nombre_insumo"));
                    configuracionPaquete.setPrecio_insumo(resultSet.getDouble("precio_insumo"));
                    configuracionPaquete.setId_folio(resultSet.getInt("id_folio"));

                    configuraciones.add(configuracionPaquete);
                }
            }
        }
        return configuraciones;
    }

    public List<ConfiguracionPaqueteLista> configuracionPaqueteListas(int idFolio) throws SQLException {
        List<ConfiguracionPaqueteLista> consumoList = new ArrayList<>();

        String query = "SELECT c.id_insumo, COUNT(c.id_insumo) AS cantidad FROM configuracionpaquete c WHERE c.id_folio = ? GROUP BY c.id_insumo;";

        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            try (ResultSet resultSet = statement.executeQuery()) {
                
                while (resultSet.next()) {
                    consumoList.add(createConfiguracionPaqueteListaLista(resultSet));
                }
                return consumoList;
            }
        }
    }

    private ConfiguracionPaqueteLista createConfiguracionPaqueteListaLista(ResultSet resultSet) throws SQLException {
        ConfiguracionPaqueteLista configuracionPaqueteLista = new ConfiguracionPaqueteLista();

        configuracionPaqueteLista.setIdInusmo(resultSet.getInt("id_insumo"));
        configuracionPaqueteLista.setCantidadInsumo(resultSet.getDouble("cantidad"));


        return configuracionPaqueteLista;
    }
}
