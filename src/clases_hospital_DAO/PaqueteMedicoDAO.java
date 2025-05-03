/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.PaqueteMedico;
import clases_hospital.PaquetesNvo;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class PaqueteMedicoDAO {

    private Connection connection;

    public PaqueteMedicoDAO(Connection connection) {
        this.connection = connection;
    }

    public void actualizarPrecioVentaPaquete(PaquetesNvo paquetetraido) {
        String qwery = "UPDATE paquetesmedicos p set p.costo = ? , p.idUsuario = ?, p.fechaModificacion = NOW() WHERE p.id = ?;";
        try (PreparedStatement stm = connection.prepareStatement(qwery)) {
            stm.setDouble(1, paquetetraido.getPrecioVenta());
            stm.setInt(2, VPMedicaPlaza.userSystem);
            stm.setLong(3, paquetetraido.getId());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void crear(PaqueteMedico paqueteMedico) throws SQLException {
        String query = "INSERT INTO paquetesmedicos (nombre, descripcion, costo, idUsuario, fechaModificacion, claveSAT, id_tipo_procedimiento, id_quirofano, id_tipo_habitacion, id_procedimiento, dias_hospitalizacion, numero_comidas, horas_tolerancia, horasHospitalizacion, factor_paquete) VALUES (?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, paqueteMedico.getNombre());
            statement.setString(2, paqueteMedico.getDescripcion());
            statement.setDouble(3, paqueteMedico.getPrecio());
            statement.setInt(4, paqueteMedico.getIdUsuario());
            statement.setString(5, paqueteMedico.getClaveSAT());
            statement.setInt(6, paqueteMedico.getId_tipo_procedimiento());
            statement.setInt(7, paqueteMedico.getId_quirofano());
            statement.setInt(8, paqueteMedico.getId_tipo_habitacion());
            statement.setInt(9, paqueteMedico.getId_procedimiento());
            statement.setInt(10, paqueteMedico.getDias_hospitalizacion());
            statement.setInt(11, paqueteMedico.getNumero_comidas());
            statement.setInt(12, paqueteMedico.getHoras_tolerancia());
            statement.setInt(13, paqueteMedico.getHorasHospitalizacion());
            statement.setDouble(14, paqueteMedico.getFactor_paquete());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                paqueteMedico.setId(generatedKeys.getInt(1));
            }
        }
    }

    public int crearInt(PaqueteMedico paqueteMedico) throws SQLException {
        int id;
        String query = "INSERT INTO paquetesmedicos (nombre, descripcion, costo, idUsuario, fechaModificacion, claveSAT, id_tipo_procedimiento, id_quirofano, id_tipo_habitacion, id_procedimiento, dias_hospitalizacion, numero_comidas, horas_tolerancia, horasHospitalizacion, factor_paquete) VALUES (?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, paqueteMedico.getNombre());
            statement.setString(2, paqueteMedico.getDescripcion());
            statement.setDouble(3, paqueteMedico.getPrecio());
            statement.setInt(4, paqueteMedico.getIdUsuario());
            statement.setString(5, paqueteMedico.getClaveSAT());
            statement.setInt(6, paqueteMedico.getId_tipo_procedimiento());
            statement.setInt(7, paqueteMedico.getId_quirofano());
            statement.setInt(8, paqueteMedico.getId_tipo_habitacion());
            statement.setInt(9, paqueteMedico.getId_procedimiento());
            statement.setInt(10, paqueteMedico.getDias_hospitalizacion());
            statement.setInt(11, paqueteMedico.getNumero_comidas());
            statement.setInt(12, paqueteMedico.getHoras_tolerancia());
            statement.setInt(13, paqueteMedico.getHorasHospitalizacion());
            statement.setDouble(14, paqueteMedico.getFactor_paquete());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            } else {
                id = 0;
            }
            return id;
        }
    }

    public PaqueteMedico leer(int id) throws SQLException {
        String query = "SELECT * \n"
                + "FROM paquetesmedicos \n"
                + "WHERE id = ? \n"
                + "UNION \n"
                + "SELECT * \n"
                + "FROM paquetesmedicos \n"
                + "WHERE id = 1 \n"
                + "AND NOT EXISTS (SELECT * FROM paquetesmedicos WHERE id = ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setInt(2,id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    PaqueteMedico paqueteMedico = mapearPaqueteMedico(resultSet);
                    return paqueteMedico;
                }
            }
        }
        return null;
    }

    public List<PaqueteMedico> obtenerTodos() throws SQLException {
        List<PaqueteMedico> listaPaquetesMedicos = new ArrayList<>();
        String query = "SELECT * FROM paquetesmedicos";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                PaqueteMedico paqueteMedico = mapearPaqueteMedico(resultSet);
                listaPaquetesMedicos.add(paqueteMedico);
            }
        }
        return listaPaquetesMedicos;
    }

    public List<PaquetesNvo> obtenerPaquetesNvo() throws SQLException {
        List<PaquetesNvo> paqueteNvoList = new ArrayList<>();

        try (CallableStatement callableStatement = connection.prepareCall("{CALL TRAER_PAQUETES_CON_NVO_FORMATO2()}")) {
            ResultSet resultSet = callableStatement.executeQuery();

            while (resultSet.next()) {
                PaquetesNvo paqueteNvo = new PaquetesNvo();
                paqueteNvo.setId(resultSet.getLong("id"));
                paqueteNvo.setNombre(resultSet.getString("nombre"));
                paqueteNvo.setPrecioVenta(resultSet.getDouble("precio_venta"));
                paqueteNvo.setCostoActual(resultSet.getDouble("costo_actual"));
                paqueteNvo.setCostoNuevo(resultSet.getDouble("costo_nuevo"));
                paqueteNvo.setDiferenciaCostos(resultSet.getDouble("diferencia_costos"));
                paqueteNvo.setDiferenciaCostosPorcentaje(resultSet.getDouble("diferencia_costos_porcentaje"));
                paqueteNvo.setPrecioVentaActual(resultSet.getDouble("precio_venta_actual"));
                paqueteNvo.setPrecioVentaNuevo(resultSet.getDouble("precio_venta_nuevo"));
                paqueteNvo.setDiferenciaPrecioVenta(resultSet.getDouble("diferencia_precio_venta"));
                paqueteNvo.setDiferenciaPrecioVentaPorcentaje(resultSet.getDouble("diferencia_precio_venta_porcentaje"));
                paqueteNvo.setGananciaActual(resultSet.getDouble("ganancia_actual"));
                paqueteNvo.setGananciaNueva(resultSet.getDouble("ganancia_nueva"));
                paqueteNvo.setNombreUsuario(resultSet.getString("nombre"));
                paqueteNvo.setFechaModificacion(resultSet.getString("fechaModificacion"));

                paqueteNvoList.add(paqueteNvo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paqueteNvoList;
    }

    public void actualizar(PaqueteMedico paqueteMedico) throws SQLException {
        String query = "UPDATE paquetesmedicos SET nombre = ?, descripcion = ?, costo = ?, idUsuario = ?, fechaModificacion = NOW(), claveSAT = ?, id_tipo_procedimiento = ?, id_quirofano = ?, id_tipo_habitacion = ?, id_procedimiento = ?, dias_hospitalizacion = ?, numero_comidas = ?, horas_tolerancia = ?, horasHospitalizacion = ?, factor_paquete = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, paqueteMedico.getNombre());
            statement.setString(2, paqueteMedico.getDescripcion());
            statement.setDouble(3, paqueteMedico.getPrecio());
            statement.setInt(4, paqueteMedico.getIdUsuario());
            statement.setString(5, paqueteMedico.getClaveSAT());
            statement.setInt(6, paqueteMedico.getId_tipo_procedimiento());
            statement.setInt(7, paqueteMedico.getId_quirofano());
            statement.setInt(8, paqueteMedico.getId_tipo_habitacion());
            statement.setInt(9, paqueteMedico.getId_procedimiento());
            statement.setInt(10, paqueteMedico.getDias_hospitalizacion());
            statement.setInt(11, paqueteMedico.getNumero_comidas());
            statement.setInt(12, paqueteMedico.getHoras_tolerancia());
            statement.setInt(13, paqueteMedico.getHorasHospitalizacion());
            statement.setDouble(14, paqueteMedico.getFactor_paquete());
            statement.setInt(15, paqueteMedico.getId());

            statement.executeUpdate();
        }
    }

    public void actualizarPecioTabla(int id, double pecio) throws SQLException {
        String query = "UPDATE paquetesmedicos SET costo = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, pecio);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    public void actualizarEstatusMovimiento(int idPaqueteMedico) throws SQLException {
        String query = "UPDATE paquetesmedicos p SET p.estatusMovimiento = 0 WHERE p.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPaqueteMedico);
            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM paquetesmedicos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private PaqueteMedico mapearPaqueteMedico(ResultSet resultSet) throws SQLException {
        PaqueteMedico paqueteMedico = new PaqueteMedico();
        paqueteMedico.setId(resultSet.getInt("id"));
        paqueteMedico.setNombre(resultSet.getString("nombre"));
        paqueteMedico.setDescripcion(resultSet.getString("descripcion"));
        paqueteMedico.setPrecio(resultSet.getDouble("costo"));
        paqueteMedico.setIdUsuario(resultSet.getInt("idUsuario"));
        paqueteMedico.setClaveSAT(resultSet.getString("claveSAT"));
        paqueteMedico.setId_tipo_procedimiento(resultSet.getInt("id_tipo_procedimiento"));
        paqueteMedico.setId_quirofano(resultSet.getInt("id_quirofano"));
        paqueteMedico.setId_tipo_habitacion(resultSet.getInt("id_tipo_habitacion"));
        paqueteMedico.setId_procedimiento(resultSet.getInt("id_procedimiento"));
        paqueteMedico.setDias_hospitalizacion(resultSet.getInt("dias_hospitalizacion"));
        paqueteMedico.setNumero_comidas(resultSet.getInt("numero_comidas"));
        paqueteMedico.setHoras_tolerancia(resultSet.getInt("horas_tolerancia"));
        paqueteMedico.setHorasHospitalizacion(resultSet.getInt("horasHospitalizacion"));
        paqueteMedico.setFactor_paquete(resultSet.getDouble("factor_paquete"));
        return paqueteMedico;
    }
}
