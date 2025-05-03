/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.ServicioAdicional;
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
public class ServiciosAdicionalesDAO {

    private Connection connection;

    public ServiciosAdicionalesDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(ServicioAdicional servicio) {
        String query = "INSERT INTO serviciosadicionales (nombre, costo, propietario, marca, modelo, descripcion, tipo, id_usuarioModificacion, fechaModificacion, existencia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, servicio.getNombre());
            statement.setDouble(2, servicio.getCosto());
            statement.setString(3, servicio.getPropietario());
            statement.setString(4, servicio.getMarca());
            statement.setString(5, servicio.getModelo());
            statement.setString(6, servicio.getDescripcion());
            statement.setString(7, servicio.getTipo());
            statement.setInt(8, servicio.getIdUsuarioModificacion());
            statement.setString(9, servicio.getExistencia());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                servicio.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ServicioAdicional obtenerServicioAdicionalPorId(int id) {
        String query = "SELECT * FROM serviciosadicionales WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createServicioFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(ServicioAdicional servicio) {
        String query = "UPDATE serviciosadicionales SET nombre = ?, costo = ?, propietario = ?, marca = ?, modelo = ?, descripcion = ?, tipo = ?, id_usuarioModificacion = ?, fechaModificacion = NOW(), existencia = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, servicio.getNombre());
            statement.setDouble(2, servicio.getCosto());
            statement.setString(3, servicio.getPropietario());
            statement.setString(4, servicio.getMarca());
            statement.setString(5, servicio.getModelo());
            statement.setString(6, servicio.getDescripcion());
            statement.setString(7, servicio.getTipo());
            statement.setInt(8, servicio.getIdUsuarioModificacion());
            statement.setString(9, servicio.getExistencia());
            statement.setInt(10, servicio.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM serviciosadicionales WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ServicioAdicional> getAllServicios() {
        List<ServicioAdicional> servicios = new ArrayList<>();

        String query = "SELECT * FROM serviciosadicionales";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                ServicioAdicional servicio = createServicioFromResultSet(resultSet);
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicios;
    }

    public String obtenerListaServiciosAdicionales(String listaId) throws SQLException {
        String lista = "";
        String query = "select nombre from serviciosadicionales WHERE id = ?";
        if (listaId.equals("")) {
            lista = "";
        } else {
            String[] idServi = listaId.split(",");
            for (String servicios : idServi) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, Integer.parseInt(servicios));
                    try (ResultSet rs = statement.executeQuery()) {
                        if (rs.next()) {
                            lista = lista + rs.getString(1) + " ";
                        }
                    }
                }
            }
        }
        return lista;
    }

    private ServicioAdicional createServicioFromResultSet(ResultSet resultSet) throws SQLException {
        ServicioAdicional servicio = new ServicioAdicional();
        servicio.setId(resultSet.getInt("id"));
        servicio.setNombre(resultSet.getString("nombre"));
        servicio.setCosto(resultSet.getDouble("costo"));
        servicio.setPropietario(resultSet.getString("propietario"));
        servicio.setMarca(resultSet.getString("marca"));
        servicio.setModelo(resultSet.getString("modelo"));
        servicio.setDescripcion(resultSet.getString("descripcion"));
        servicio.setTipo(resultSet.getString("tipo"));
        servicio.setIdUsuarioModificacion(resultSet.getInt("id_usuarioModificacion"));
        servicio.setFechaModificacion(resultSet.getTimestamp("fechaModificacion"));
        servicio.setExistencia(resultSet.getString("existencia"));

        return servicio;
    }
}
