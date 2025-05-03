/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Paciente;
import clases_hospital.Consumo;
import clases_hospital.Folio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 *
 * @author gamae
 */
public class ConsumoQuirofanoDAO {

    private Connection connection;

    public ConsumoQuirofanoDAO(Connection connection) {
        this.connection = connection;
    }

    // llena la tabla de la vista principal(Folios)
    public ObservableList<Folio> llenarTabla() throws SQLException {

        ObservableList<Folio> folios = FXCollections.observableArrayList();
        Folio folio = null;

        String sql = "SELECT p.id_paciente, f.id AS id_folio, f.folio ,p.nombre_paciente , f.numero_habitacion\n"
                + "  FROM folios f  \n"
                + "  INNER JOIN pacientes p ON p.id_folio = f.id INNER JOIN asignacion_habitacion ah ON ah.id_paciente = p.id_paciente INNER JOIN tipoHabitacion h ON h.id_tipo = ah.id_tipo_habitacion WHERE f.id_estatus_folio = 1";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                folio = new Folio();
//resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)
                folio.setIdPaciente(resultSet.getInt(1));
                folio.setId(resultSet.getInt(2));
                folio.setFolio(resultSet.getString(3));
                folio.setNombre(resultSet.getString(4));
                folio.setNumero_habitacion(resultSet.getInt(5));
                
                folios.add(folio);
            }
        }

        return folios;
    }

    // buscador de la vsita principal(Folios)
    public ArrayList<Folio> buscadorPacientes() throws SQLException {
        ArrayList<Folio> folios = new ArrayList<>();
        Folio folio = null;
        String sql = "SELECT p.id_paciente, f.id AS id_folio, f.folio ,p.nombre_paciente FROM pacientes p  INNER JOIN folios f ON p.id_folio = f.id AND f.id_estatus = 0";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                folio = new Folio(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4));
                folios.add(folio);
            }
        }
        return folios;
    }

    // agrega un isumo a la tabla de la vista auxiliar(agregarConsumoPaciente)
    public Folio agegarInsumo(int idInsumo, double cantidad, int idPaquete) throws SQLException {
        Folio folio = null;
        double precioVenta = 0;
        String sql = "SELECT i.nombre, c.precio_venta_unitaria, i.tipo_insumo FROM insumos i INNER JOIN costos c ON i.id = c.id_insumo WHERE i.id ='" + idInsumo + "'";

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                precioVenta = resultSet.getDouble(2);
                folio = new Folio(idInsumo, resultSet.getString(1), precioVenta, cantidad);
            }
        }

        return folio;
    }

    //Aqui hay ponderacion
    public void incertarConsumo(Consumo consumo) {
        String sql = "INSERT INTO consumos\n"
                + "(id,tipo, cantidad ,monto ,fecha ,folio ,id_pasiente ,id_PaqueteAlimento ,id_tipo_de_consumo ,id_folio ,id_producto_venta, id_estatus_consumo, monto_unitario)\n"
                + "VALUES\n"
                + "(0 ,? ,? ,? ,NOW() ,? ,? ,? ,? ,? ,?, 1,?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, consumo.getTipo());
            statement.setDouble(2, consumo.getCantidad());
            statement.setDouble(3, consumo.getMonto());
            statement.setString(4, consumo.getFolio());
            statement.setInt(5, consumo.getId_pasiente());
            statement.setInt(6, 0);
            statement.setInt(7, consumo.getId_tipo_consumo());
            statement.setInt(8, consumo.getId_folio());
            statement.setInt(9, consumo.getId_producto_venta());
            statement.setDouble(10, consumo.getPrecioUnitario());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (por ejemplo, registro en un archivo de log, mostrar un mensaje de error, etc.)
        }
    }

    public void descontarInsumo(Consumo consumo) {
        String sql = "UPDATE inventarios SET total_existencia = ? ,id_usuario_mod = ?  ,fecha_mod = NOW() WHERE id = ?;";
        double existenciaTotal = existenciaTotalInventario(consumo.getId_producto_venta());
        existenciaTotal = existenciaTotal - consumo.getCantidad();
    
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, existenciaTotal);
            statement.setInt(2, userSystem);
            statement.setInt(3, consumo.getId_producto_venta());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int existenciaTotalInventario(int id_insumo) {
        int existenciaTotal = 0;
        String sql = "SELECT i.total_existencia FROM inventarios i WHERE i.id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_insumo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                existenciaTotal = resultSet.getInt(1);
           
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (por ejemplo, registro en un archivo de log, mostrar un mensaje de error, etc.)
        }

        return existenciaTotal;
    }

    public void insertarMovimientoAlInventario(Consumo consumo) {
        String sql = "INSERT INTO movimientos_inventariop\n"
                + "(id ,tipo_mov ,id_proveedor ,id_origen ,id_destino ,folio_mov ,subtotal ,importe_impuesto ,descuento ,total ,estatus_movimiento ,observaciones ,usuario_registro ,fecha_registro) VALUES\n"
                + "(\n"
                + "  0 -- id - INT(11) NOT NULL\n"
                + " ,7 -- tipo_mov - INT(11) NOT NULL\n"
                + " ,0 -- id_proveedor - INT(11) NOT NULL\n"
                + " ,1 -- id_origen - INT(11) NOT NULL\n"
                + " ,1 -- id_destino - INT(11) NOT NULL\n"
                + " ,? -- folio_mov - VARCHAR(255) NOT NULL\n"
                + " ,0 -- subtotal - DOUBLE NOT NULL\n"
                + " ,0 -- importe_impuesto - DOUBLE NOT NULL\n"
                + " ,0 -- descuento - DOUBLE NOT NULL\n"
                + " ,? -- total - DOUBLE NOT NULL\n"
                + " ,0 -- estatus_movimiento - INT(11) NOT NULL\n"
                + " ,'COSUMO DE PACIENTE' -- observaciones - VARCHAR(255) NOT NULL\n"
                + " ,? -- usuario_registro - INT(11) NOT NULL\n"
                + " ,NOW() -- fecha_registro - DATETIME NOT NULL\n"
                + ");";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, consumo.getFolio());
            statement.setDouble(2, consumo.getMonto());
            statement.setInt(3, userSystem);

            statement.executeUpdate();
        } catch (Exception e) {
        }
    }
}
