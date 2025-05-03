/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.ConsumosLista;
import clases_hospital.Consumo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author alfar
 */
public class ConsumosDAO {

    private Connection connection;

    public ConsumosDAO(Connection connection) {
        this.connection = connection;
    }

    public void cancelarconsumo(int id_paciente, int id_insumo, double suministro) throws SQLException {

        try (CallableStatement stm = connection.prepareCall("{call CANCELARCONSUMO(?,?,?)}");) {

            stm.setInt(1, id_paciente);
            stm.setInt(2, id_insumo);
            stm.setDouble(3, suministro);
            stm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String actualizarIndicasporarea(int id_area) {
        String respuesta = "";
        try (CallableStatement stm = connection.prepareCall("{call ACTUALIZARINDICASPORAREA(?)}")) {
            stm.setInt(1, id_area);
            stm.execute();
            respuesta = "las indicas fueron consumidas con exito";

        } catch (SQLException e) {
            respuesta = "algo sali mal";
        }

        return respuesta;
    }

    public void insertarConsumo(Consumo consumo) {
        String query = "INSERT INTO consumos (tipo, cantidad, monto, fecha, folio, id_pasiente, id_PaqueteAlimento, id_tipo_de_consumo, id_folio, id_producto_venta, id_estatus_consumo, monto_unitario) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, 1,?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, consumo.getTipo());
            statement.setDouble(2, consumo.getCantidad());
            statement.setDouble(3, consumo.getMonto());
            statement.setString(4, consumo.getFolio());
            statement.setInt(5, consumo.getId_pasiente());
            statement.setInt(6, consumo.getId_PaqueteAlimento());
            statement.setInt(7, consumo.getId_tipo_consumo());
            statement.setInt(8, consumo.getId_folio());
            statement.setInt(9, consumo.getId_producto_venta());
            statement.setDouble(10, consumo.getPrecioUnitario());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                consumo.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarConsumoParacambioPaquete(Consumo consumo) {
        String query = "INSERT INTO consumos (tipo, cantidad, monto, fecha, folio, id_pasiente, id_PaqueteAlimento, id_tipo_de_consumo, id_folio, id_producto_venta, id_estatus_consumo, monto_unitario) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, 0,?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, consumo.getTipo());
            statement.setDouble(2, consumo.getCantidad());
            statement.setDouble(3, consumo.getMonto());
            statement.setString(4, consumo.getFolio());
            statement.setInt(5, consumo.getId_pasiente());
            statement.setInt(6, consumo.getId_PaqueteAlimento());
            statement.setInt(7, consumo.getId_tipo_consumo());
            statement.setInt(8, consumo.getId_folio());
            statement.setInt(9, consumo.getId_producto_venta());
            statement.setDouble(10, consumo.getPrecioUnitario());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                consumo.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarConsumoConEstatus(Consumo consumo) {
        String query = "INSERT INTO consumos (tipo, cantidad, monto, fecha, folio, id_pasiente, id_PaqueteAlimento, id_tipo_de_consumo, id_folio, id_producto_venta, id_estatus_consumo, monto_unitario) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, consumo.getTipo());
            statement.setDouble(2, consumo.getCantidad());
            statement.setDouble(3, consumo.getMonto());
            statement.setString(4, consumo.getFolio());
            statement.setInt(5, consumo.getId_pasiente());
            statement.setInt(6, consumo.getId_PaqueteAlimento());
            statement.setInt(7, consumo.getId_tipo_consumo());
            statement.setInt(8, consumo.getId_folio());
            statement.setInt(9, consumo.getId_producto_venta());
            statement.setInt(10, consumo.getId_estatus_consumo());
            statement.setDouble(11, consumo.getPrecioUnitario());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                consumo.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarConsumoConEstatusYPaquete(Consumo consumo) {
        String query = "INSERT INTO consumos (tipo, cantidad, monto, fecha, folio, id_pasiente, id_PaqueteAlimento, id_tipo_de_consumo, id_folio, id_producto_venta, id_estatus_consumo, monto_unitario, paquete, id_usuario_creacion, id_usuario_modificacion) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, consumo.getTipo());
            statement.setDouble(2, consumo.getCantidad());
            statement.setDouble(3, consumo.getMonto());
            statement.setString(4, consumo.getFolio());
            statement.setInt(5, consumo.getId_pasiente());
            statement.setInt(6, consumo.getId_PaqueteAlimento());
            statement.setInt(7, consumo.getId_tipo_consumo());
            statement.setInt(8, consumo.getId_folio());
            statement.setInt(9, consumo.getId_producto_venta());
            statement.setInt(10, consumo.getId_estatus_consumo());
            statement.setDouble(11, consumo.getPrecioUnitario());
            statement.setBoolean(12, consumo.isPaquete());
            statement.setInt(13, consumo.getId_usuario_creacion());
            statement.setInt(14, consumo.getId_usuario_modificacion());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                consumo.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarConsumoConEstatusYPaqueteConPrecio(Consumo consumo) {

        String query = "INSERT INTO consumos (tipo, cantidad, monto, fecha, folio, id_pasiente, id_PaqueteAlimento, id_tipo_de_consumo, id_folio, id_producto_venta, id_estatus_consumo, monto_unitario, paquete, id_usuario_creacion, id_usuario_modificacion, monto_unitario_paquete) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, consumo.getTipo());
            statement.setDouble(2, consumo.getCantidad());
            statement.setDouble(3, consumo.getMonto());
            statement.setString(4, consumo.getFolio());
            statement.setInt(5, consumo.getId_pasiente());
            statement.setInt(6, consumo.getId_PaqueteAlimento());
            statement.setInt(7, consumo.getId_tipo_consumo());
            statement.setInt(8, consumo.getId_folio());
            statement.setInt(9, consumo.getId_producto_venta());
            statement.setInt(10, consumo.getId_estatus_consumo());
            statement.setDouble(11, consumo.getPrecioUnitario());
            statement.setBoolean(12, consumo.isPaquete());
            statement.setInt(13, consumo.getId_usuario_creacion());
            statement.setInt(14, consumo.getId_usuario_modificacion());
            statement.setDouble(15, consumo.getPrecioUnitarioPaquete());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                consumo.setId(generatedKeys.getInt(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Consumo obtenerConsumoPorId(int id) {
        String query = "SELECT * FROM consumos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createConsumoFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Consumo obtenerConsumoPorIdInsumoandIdFolio(int id_insumo, int id_folio) {
        String query = "SELECT * FROM consumos WHERE id_producto_venta = ? AND id_folio = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);
            statement.setInt(2, id_folio);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createConsumoFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarConumo(Consumo consumo) {
        String query = "UPDATE consumos SET tipo = ?, cantidad = ?, monto = ?, fecha = NOW(), folio = ?, id_pasiente = ?, id_PaqueteAlimento = ?, id_tipo_de_consumo = ?, id_folio = ?, id_producto_venta = ?, id_estatus_consumo = ? "
                + "WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, consumo.getTipo());
            statement.setDouble(2, consumo.getCantidad());
            statement.setDouble(3, consumo.getMonto());
            statement.setString(4, consumo.getFolio());
            statement.setInt(5, consumo.getId_pasiente());
            statement.setInt(6, consumo.getId_PaqueteAlimento());
            statement.setInt(7, consumo.getId_tipo_consumo());
            statement.setInt(8, consumo.getId_folio());
            statement.setInt(9, consumo.getId_producto_venta());

            statement.setInt(10, consumo.getId_estatus_consumo());
            statement.setInt(11, consumo.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM consumos WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void descartarConsumo(int id) throws SQLException {
//        connection.setAutoCommit(true);
//        String query = "UPDATE consumos c SET c.id_estatus_consumo = 0 WHERE c.id = ?";
//        try (
//                PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setInt(1, id);
//
//            int rowsUpdated = statement.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
//            System.err.println("Código de error SQL: " + e.getErrorCode());
//            System.err.println("Estado de SQL: " + e.getSQLState());
//            e.printStackTrace();
//        }
//    }
    public void actualizarDescarteCosumo(int id) throws SQLException {
        String query = "UPDATE consumos c SET c.id_estatus_consumo = 0 WHERE c.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Consumo> obtenerTodosConsumos() {
        List<Consumo> consumos = new ArrayList<>();

        String query = "SELECT * FROM consumos";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Consumo consumo = createConsumoFromResultSet(resultSet);
                consumos.add(consumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumos;
    }

    public List<ConsumosLista> consmoLista(int idFolio) throws SQLException {
        List<ConsumosLista> consumoList = new ArrayList<>();

        String query = "SELECT c.id_producto_venta, SUM(c.cantidad) AS cantidad FROM consumos c WHERE c.id_folio = ? AND c.paquete = 1 GROUP BY c.id_producto_venta;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    consumoList.add(createCosmoListaFromResultSet(resultSet));
                }
            }
        }

        return consumoList;
    }

    public double sumatoriaTotalPaquete(int idFolio) throws SQLException {
        String query = "SELECT SUM((c.monto_unitario_paquete * c.cantidad)) AS suma FROM consumos c WHERE c.id_folio = ? AND c.id_estatus_consumo = 1 AND c.paquete = 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("suma");
            } else {
                return 0.0;
            }
        }
    }

    public double sumatoriaInsumosPaqueteHemodinamia(int idFolio) throws SQLException {
        String query = "SELECT (SUM((c.monto_unitario_paquete * c.cantidad)) + SUM(isp.precio_unitario)) AS suma FROM consumos c INNER JOIN indicasp i ON c.id_folio = i.id_folio INNER JOIN indicas_detalles indt ON i.id_indicasp = indt.id_indicasp INNER JOIN indicas_suministro_pacientes isp ON indt.id_indica_detalle = isp.id_indica_detalle WHERE c.id_folio = ? AND c.paquete = 1 AND indt.paquete = 1 AND isp.devolucion = 0;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idFolio);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("suma");
            } else {
                return 0.0;
            }
        }
    }

    private Consumo createConsumoFromResultSet(ResultSet resultSet) throws SQLException {
        Consumo consumo = new Consumo();
        consumo.setId(resultSet.getInt("id"));
        consumo.setTipo(resultSet.getString("tipo"));
        consumo.setCantidad(resultSet.getInt("cantidad"));
        consumo.setMonto(resultSet.getDouble("monto"));
        consumo.setFecha(resultSet.getDate("fecha"));
        consumo.setFolio(resultSet.getString("folio"));
        consumo.setId_pasiente(resultSet.getInt("id_pasiente"));
        consumo.setId_PaqueteAlimento(resultSet.getInt("id_PaqueteAlimento"));
        consumo.setId_tipo_consumo(resultSet.getInt("id_tipo_de_consumo"));
        consumo.setId_folio(resultSet.getInt("id_folio"));
        consumo.setId_producto_venta(resultSet.getInt("id_producto_venta"));
        consumo.setId_estatus_consumo(resultSet.getInt("id_estatus_consumo"));

        return consumo;
    }

    public ObservableList<Consumo> obtenerTodosConsumosPorFolio(int idFolio) {
        ObservableList<Consumo> consumos = FXCollections.observableArrayList();

        String query = "SELECT * FROM consumos c WHERE c.id_folio = '" + idFolio + "' AND c.id_estatus_consumo <> 0";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Consumo consumo = createConsumoFromResultSetVISTACONSUMO(resultSet);
                consumos.add(consumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumos;
    }

    public List<Consumo> obtenerTodosConsumosPaquetePorFolio(int idFolio) {
        List<Consumo> consumos = FXCollections.observableArrayList();

        String query = "SELECT * FROM consumos c WHERE c.id_folio = '" + idFolio + "' AND c.id_estatus_consumo = 1 AND c.id_PaqueteAlimento = 0 AND c.paquete = 0 ORDER BY c.monto_unitario_paquete";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Consumo consumo = createConsumoFromResultSetVISTACONSUMO(resultSet);
                consumos.add(consumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumos;
    }

    public ObservableList<Consumo> obtenerConsumosAlimentosPorFolio(int idFolio) {
        ObservableList<Consumo> consumos = FXCollections.observableArrayList();

        String query = "SELECT * FROM consumos c WHERE c.id_folio = '" + idFolio + "' AND c.id_estatus_consumo <> 0 AND c.id_tipo_de_consumo IN (5,6);";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Consumo consumo = createConsumoFromResultSetVISTACONSUMO(resultSet);
                consumos.add(consumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumos;
    }

//    public int optenerIdTipoDeConsumo(int id_paquete, int id_folio, String tipo) throws SQLException {
//        String query = "";
//        int idTipoConsumo = 0;
//        int cantidadComidas = optenerCantidadComidas(id_paquete);
//        double cantidadConsumidas = 0;
//        if (cantidadComidas > 0) {
//            cantidadConsumidas = CantidadConsumoAlMomento(id_folio);
//            if(cantidadConsumidas > 0){
//                
//            }
//        } else {
//            return 0;
//        }
//    }
    public int optenerCantidadComidas(int id_paquete) throws SQLException {
        String query = "SELECT p.numero_comidas FROM paquetesmedicos p WHERE p.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_paquete);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("numero_comidas");
            } else {
                return 0;
            }
        }
    }

    public double CantidadConsumoAlMomento(int id_folio) throws SQLException {
        String query = "SELECT CASE WHEN IFNULL(SUM(c.cantidad), 0) = 0 THEN 0 ELSE SUM(c.cantidad) END AS canitdadConsumida FROM consumos c WHERE c.id_folio = ? AND c.id_PaqueteAlimento > 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_folio);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("canitdadConsumida");
            } else {
                return 0;
            }
        }
    }

    public List<Consumo> optenerTodosCosnumos(int id_folio) throws SQLException {
        List<Consumo> listaConsumoPAciente = new ArrayList<>();
        String query = "CALL STR_OPTENER_CONSUMO(?)";
        try (CallableStatement stm = connection.prepareCall(query)) {
            stm.setInt(1, id_folio);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Consumo consumoPaciente = crearCosumoFromResultSetSTR(rs);
                listaConsumoPAciente.add(consumoPaciente);
            }
        }
        return listaConsumoPAciente;
    }

    public double actualizarPaquete(double cantidad, Consumo consumo) throws SQLException {
        String query = "CALL STR_ACTUALIZAR_CONSUMO_CON_PAQUETE(?, ?, ?, ?, ?)";
        try (CallableStatement statement = connection.prepareCall(query)) {
            // Establecer los parámetros del procedimiento almacenado
            statement.setInt(1, consumo.getId_folio());
            statement.setInt(2, consumo.getId());
            statement.setDouble(3, cantidad);
            statement.setInt(4, VPMedicaPlaza.userSystem);
            statement.registerOutParameter(5, Types.DOUBLE); // Registro del parámetro de salida

            // Ejecutar el procedimiento almacenado
            statement.executeUpdate();
            
            System.out.println("IDFOLIO: " + consumo.getId_folio() + " IDCONSUMOPACIENTE: " + consumo.getId() + " CANTIDAD: " + cantidad + " USUARIODESISTEMA: " + VPMedicaPlaza.userNameSystem + " **ESTO ES LO QUE RERESA: ->"+ statement.getDouble(5) +"<-** ID_PRODUCTO VENTA: " + consumo.getId_producto_venta());
            return statement.getDouble(5);
        }
    }
    
    public void quitarPaquete(int folio_paquete) throws SQLException {
        String query = "CALL STR_QUTAR_PAQUETE(?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Establecer los parámetros del procedimiento almacenado
            statement.setInt(1, folio_paquete);

            // Ejecutar el procedimiento almacenado
            statement.executeUpdate();
        }
    }
    
    public void insertarConsumoServicios(Consumo consumo) throws SQLException{
        String query = "CALL STR_PROC_INSERTAR_CONSUMO_SERVICIOS(?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Establecer los parámetros del procedimiento almacenado
            statement.setInt(1, consumo.getId_folio());
            statement.setInt(2, consumo.getId_producto_venta());
            statement.setInt(3, consumo.getId_usuario_creacion());
            statement.setDouble(4, consumo.getCantidad());
            statement.setString(5, consumo.getTipo());
            

            // Ejecutar el procedimiento almacenado
            statement.execute();
        }
    }

    private Consumo crearCosumoFromResultSetSTR(ResultSet resultSet) throws SQLException {
        Consumo consumo = new Consumo();
        consumo.setId(resultSet.getInt("id"));
        consumo.setTipo(resultSet.getString("tipo"));
        consumo.setCantidad(resultSet.getDouble("cantidad"));
        consumo.setMonto(resultSet.getDouble("monto"));
        consumo.setFecha(resultSet.getDate("fecha"));
        consumo.setFolio(resultSet.getString("folio"));
        consumo.setId_pasiente(resultSet.getInt("id_pasiente"));
        consumo.setId_PaqueteAlimento(resultSet.getInt("id_PaqueteAlimento"));
        consumo.setId_tipo_consumo(resultSet.getInt("id_tipo_de_consumo"));
        consumo.setId_folio(resultSet.getInt("id_folio"));
        consumo.setId_producto_venta(resultSet.getInt("id_producto_venta"));
        consumo.setId_estatus_consumo(resultSet.getInt("id_estatus_consumo"));
        consumo.setPrecioUnitario(resultSet.getDouble("monto_unitario"));
        consumo.setPrecioUnitarioPaquete(resultSet.getDouble("monto_unitario_paquete"));
        consumo.setPaquete(resultSet.getBoolean("paquete"));
        return consumo;
    }

    private Consumo createConsumoFromResultSetVISTACONSUMO(ResultSet resultSet) throws SQLException {
        Consumo consumo = new Consumo();
        consumo.setId(resultSet.getInt("id"));
        consumo.setTipo(resultSet.getString("tipo"));
        consumo.setCantidad(resultSet.getDouble("cantidad"));
        consumo.setMonto(resultSet.getDouble("monto"));
        consumo.setDatetime(resultSet.getTimestamp("fecha"));
        consumo.setFolio(resultSet.getString("folio"));
        consumo.setId_pasiente(resultSet.getInt("id_pasiente"));
        consumo.setId_PaqueteAlimento(resultSet.getInt("id_PaqueteAlimento"));
        consumo.setId_tipo_consumo(resultSet.getInt("id_tipo_de_consumo"));
        consumo.setId_folio(resultSet.getInt("id_folio"));
        consumo.setId_producto_venta(resultSet.getInt("id_producto_venta"));
        consumo.setId_estatus_consumo(resultSet.getInt("id_estatus_consumo"));

        return consumo;
    }

    public void actualizarConsumoPorCambioPaquete(int idFolio) throws SQLException {
//        String query = "UPDATE consumos c SET c.id_estatus_consumo = 0, c.paquete = 0 WHERE c.id_folio = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, idFolio);
//            statement.executeUpdate();
//        }
    }

    public void actualizarConsumoPorCambioPaquetePaquete(Consumo consumopaquete) throws SQLException {

        String query = "UPDATE consumos c SET c.paquete = 1 WHERE c.id_folio = ? AND c.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, consumopaquete.getId_folio());
            statement.setInt(2, consumopaquete.getId());
            statement.executeUpdate();
        }
    }

    public ConsumosLista createCosmoListaFromResultSet(ResultSet resultSet) throws SQLException {
        ConsumosLista consumo = new ConsumosLista();
        consumo.setIdInsumo(resultSet.getInt("id_producto_venta"));
        consumo.setCantidadInsumo(resultSet.getDouble("cantidad"));

        return consumo;
    }

}
