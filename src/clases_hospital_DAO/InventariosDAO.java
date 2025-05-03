/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Inventario;
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
public class InventariosDAO {

    private Connection connection;

    public InventariosDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(Inventario inventario) throws SQLException {
        String query = "INSERT INTO inventarios (id_insumo, descripcion, total_existencia, ubicacion, estatus_rabasto, observaciones, id_usuario_mod, fecha_mod) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, inventario.getId_insumo());
            statement.setString(2, inventario.getDescripcion());
            statement.setDouble(3, inventario.getTotalExistencia());
            statement.setInt(4, inventario.getUbicacion());
            statement.setInt(5, inventario.getEstatus_rabasto());
            statement.setString(6, inventario.getObservaciones());
            statement.setInt(7, inventario.getId_usuario_mod());
            statement.setDate(8, inventario.getFecha_mod());

            statement.executeUpdate();
        }
    }

    public void actualizar(Inventario inventario) throws SQLException {
        String query = "UPDATE inventarios SET descripcion = ?, total_existencia = ?, ubicacion = ?, estatus_rabasto = ?, observaciones = ?, id_usuario_mod = ?, fecha_mod = ? WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, inventario.getDescripcion());
            statement.setDouble(2, inventario.getTotalExistencia());
            statement.setInt(3, inventario.getUbicacion());
            statement.setInt(4, inventario.getEstatus_rabasto());
            statement.setString(5, inventario.getObservaciones());
            statement.setInt(6, inventario.getId_usuario_mod());
            statement.setDate(7, inventario.getFecha_mod());
            statement.setInt(8, inventario.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM inventarios WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public Inventario obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM inventarios WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearInventarioDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public Inventario obtenerPorIdInsumo(int id_insumo) throws SQLException {
        String query = "SELECT * FROM inventarios WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearInventarioDesdeResultSet(resultSet);
                }
            }
        }

        return null;
    }
    
    public Inventario  obteneridinventario(int id_insumo) throws SQLException{
        Inventario inventario = new  Inventario();
        
        String query = "SELECT id,total_existencia  FROM inventarios WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    inventario.setId(resultSet.getInt("id"));
                   
                    inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
                }
                
            }
        }
        
        return inventario ;
    }

    public List<Inventario> obtenerTodos() throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT * FROM inventarios";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Inventario inventario = crearInventarioDesdeResultSet(resultSet);
                inventarios.add(inventario);
            }
        }

        return inventarios;
    }

    public List<Inventario> obtenerTodosConNombre() throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT inventario.*, insumo.nombre, presentacion.presentacion, insumo.formula, insumo.maximos, insumo.minimos, IF((inventario.total_existencia < insumo.minimos), (insumo.maximos - inventario.total_existencia), 0) AS falta, insumo.tipo_insumo FROM inventarios inventario INNER JOIN insumos insumo ON inventario.id_insumo = insumo.id INNER JOIN presentaciones_insumos presentacion ON insumo.id_presentacion = presentacion.id";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Inventario inventario = crearInventarioDesdeResultSetDetallado(resultSet);
                inventarios.add(inventario);
            }
        }

        return inventarios;
    }

    public int contarFilas() throws SQLException {
        String query = "SELECT COUNT(id) FROM inventarios";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
             
                return resultSet.getInt(1);
            } else {
                
                return 0;
            }
        }
    }

    public List<Inventario> getDatosByPage(int pageIndex, int itemsPerPage) throws SQLException {
        List<Inventario> inventario = new ArrayList<>();

        String query = "SELECT inventario.*, insumo.nombre, presentacion.presentacion, insumo.formula, insumo.maximos, insumo.minimos, IF((inventario.total_existencia < insumo.minimos), (insumo.maximos - inventario.total_existencia), 0) AS falta, insumo.tipo_insumo FROM inventarios inventario INNER JOIN insumos insumo ON inventario.id_insumo = insumo.id INNER JOIN presentaciones_insumos presentacion ON insumo.id_presentacion = presentacion.id LIMIT ?, ?";

        int offset = pageIndex * itemsPerPage;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            statement.setInt(2, itemsPerPage);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    inventario.add(crearInventarioDesdeResultSetDetallado(resultSet));
                }
            }
        }

        return inventario;
    }

    public Inventario obtenerDatosPorIdInsumo(int id_insumo) throws SQLException {

        String query = "SELECT inventario.*, insumo.nombre, presentacion.presentacion, insumo.formula, insumo.maximos, insumo.minimos, IF((inventario.total_existencia < insumo.minimos), (insumo.maximos - inventario.total_existencia), 0) AS falta, tipo_insumo FROM inventarios inventario INNER JOIN insumos insumo ON inventario.id_insumo = insumo.id INNER JOIN presentaciones_insumos presentacion ON insumo.id_presentacion = presentacion.id WHERE id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearInventarioDesdeResultSetDetallado(resultSet);
                }
            }
        }

        return null;
    }
    
    public Inventario obtenerDatosPorIdInsumoConPrecios(int id_insumo) throws SQLException {

        String query = "SELECT inventario.*, insumo.nombre, presentacion.presentacion, insumo.formula, insumo.maximos, insumo.minimos, IF((inventario.total_existencia < insumo.minimos), (insumo.maximos - inventario.total_existencia), 0) AS falta, tipo_insumo, c.precio_venta_unitaria, c.precio_venta_unitaria_paquete FROM inventarios inventario INNER JOIN insumos insumo ON inventario.id_insumo = insumo.id INNER JOIN presentaciones_insumos presentacion ON insumo.id_presentacion = presentacion.id INNER JOIN costos c ON inventario.id = c.id_insumo WHERE inventario.id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearInventarioDesdeResultSetDetalladoConPrecios(resultSet);
                }
            }
        }

        return null;
    }

    public Inventario obtenerDatosPorIdInsumoSoloDatosImportantes(int id_insumo) throws SQLException {

        String query = "SELECT i.id, i.id_insumo, i.total_existencia, insumo.nombre FROM inventarios i INNER JOIN insumos insumo ON i.id_insumo = insumo.id WHERE i.id_insumo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearInventarioSoloDatosImportatens(resultSet);
                }
            }
        }

        return null;
    }

    public Inventario obptenerDatosPorIdKitBsico(int id_insumo) throws SQLException {
        String query = "SELECT * FROM inventarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_insumo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return crearInventarioDesdeResultSetDetallado(resultSet);
                }
            }
        }
        return null;
    }

    public List<Inventario> obtenerDatosPorPedir() throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT inventario.*, insumo.nombre, presentacion.presentacion, insumo.formula, insumo.maximos, insumo.minimos, IF((inventario.total_existencia < insumo.minimos), (insumo.maximos - inventario.total_existencia), 0) AS falta, insumo.tipo_insumo FROM inventarios inventario INNER JOIN insumos insumo ON inventario.id_insumo = insumo.id INNER JOIN presentaciones_insumos presentacion ON insumo.id_presentacion = presentacion.id WHERE insumo.minimos >= inventario.total_existencia";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Inventario inventario = crearInventarioDesdeResultSetDetallado(resultSet);
                inventarios.add(inventario);
            }
        }

        return inventarios;
    }

    public List<Inventario> obtenerDatosPorTipoDeInsumo(int idTipoInusmo) throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = " SELECT inventario.*, insumo.nombre, presentacion.presentacion, insumo.formula, insumo.maximos, insumo.minimos, IF((inventario.total_existencia < insumo.minimos), (insumo.maximos - inventario.total_existencia), 0) AS falta, insumo.tipo_insumo FROM inventarios inventario INNER JOIN insumos insumo ON inventario.id_insumo = insumo.id AND insumo.tipo_insumo = ? INNER JOIN presentaciones_insumos presentacion ON insumo.id_presentacion = presentacion.id";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipoInusmo);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Inventario inventario = crearInventarioDesdeResultSetDetallado(resultSet);
                    inventarios.add(inventario);
                }
            }
        }
        return inventarios;

    }

    public List<Inventario> otenerDatosBusqueda(int idTipoInusmo) throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT inventario.id, inventario.id_insumo, inventario.total_existencia, insumo.clave, insumo.nombre, insumo.tipo_insumo FROM inventarios inventario INNER JOIN insumos insumo ON inventario.id_insumo = insumo.id AND insumo.tipo_insumo = ? INNER JOIN presentaciones_insumos presentacion ON insumo.id_presentacion = presentacion.id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipoInusmo);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Inventario inventario = crearInventarioBusqueda(resultSet);
                    inventarios.add(inventario);
                }
            }
        }
        return inventarios;
    }
    
    public List<Inventario> otenerDatosBusquedaConFamilia(int idTipoInusmo) throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT inventario.id, inventario.id_insumo, inventario.total_existencia, insumo.clave, insumo.nombre, insumo.tipo_insumo, insumo.id_familia_inusmo FROM inventarios inventario INNER JOIN insumos insumo ON inventario.id_insumo = insumo.id AND insumo.tipo_insumo = ? INNER JOIN presentaciones_insumos presentacion ON insumo.id_presentacion = presentacion.id";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTipoInusmo);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Inventario inventario = crearInventarioBusquedaConFamilia(resultSet);
                    inventarios.add(inventario);
                }
            }
        }
        return inventarios;
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

    private Inventario crearInventarioDesdeResultSet(ResultSet resultSet) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(resultSet.getInt("id"));
        inventario.setId_insumo(resultSet.getInt("id_insumo"));
        inventario.setDescripcion(resultSet.getString("descripcion"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setUbicacion(resultSet.getInt("ubicacion"));
        inventario.setEstatus_rabasto(resultSet.getInt("estatus_rabasto"));
        inventario.setObservaciones(resultSet.getString("observaciones"));
        inventario.setId_usuario_mod(resultSet.getInt("id_usuario_mod"));
        inventario.setFecha_mod(resultSet.getDate("fecha_mod"));

        return inventario;
    }

    private Inventario crearInventarioDesdeResultSetDetallado(ResultSet resultSet) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(resultSet.getInt("id"));
        inventario.setId_insumo(resultSet.getInt("id_insumo"));
        inventario.setDescripcion(resultSet.getString("descripcion"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setUbicacion(resultSet.getInt("ubicacion"));
        inventario.setEstatus_rabasto(resultSet.getInt("estatus_rabasto"));
        inventario.setObservaciones(resultSet.getString("observaciones"));
        inventario.setId_usuario_mod(resultSet.getInt("id_usuario_mod"));
        inventario.setFecha_mod(resultSet.getDate("fecha_mod"));
        inventario.setNombre(resultSet.getString("nombre"));
        inventario.setPresentacion(resultSet.getString("presentacion"));
        inventario.setFormula(resultSet.getString("formula"));
        inventario.setMaximos(resultSet.getDouble("maximos"));
        inventario.setMinimos(resultSet.getDouble("minimos"));
        inventario.setFalta(resultSet.getDouble("falta"));
        inventario.setIdtipo_insumo(resultSet.getInt("tipo_insumo"));
        return inventario;
    }
    
    private Inventario crearInventarioDesdeResultSetDetalladoConPrecios(ResultSet resultSet) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(resultSet.getInt("id"));
        inventario.setId_insumo(resultSet.getInt("id_insumo"));
        inventario.setDescripcion(resultSet.getString("descripcion"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setUbicacion(resultSet.getInt("ubicacion"));
        inventario.setEstatus_rabasto(resultSet.getInt("estatus_rabasto"));
        inventario.setObservaciones(resultSet.getString("observaciones"));
        inventario.setId_usuario_mod(resultSet.getInt("id_usuario_mod"));
        inventario.setFecha_mod(resultSet.getDate("fecha_mod"));
        inventario.setNombre(resultSet.getString("nombre"));
        inventario.setPresentacion(resultSet.getString("presentacion"));
        inventario.setFormula(resultSet.getString("formula"));
        inventario.setMaximos(resultSet.getDouble("maximos"));
        inventario.setMinimos(resultSet.getDouble("minimos"));
        inventario.setFalta(resultSet.getDouble("falta"));
        inventario.setIdtipo_insumo(resultSet.getInt("tipo_insumo"));
        inventario.setPrecioUnitario(resultSet.getDouble("precio_venta_unitaria"));
        inventario.setPrecioUnitarioPaquete(resultSet.getDouble("precio_venta_unitaria_paquete"));
        return inventario;
    }

    private Inventario crearInventarioBusqueda(ResultSet resultSet) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(resultSet.getInt("id"));
        inventario.setId_insumo(resultSet.getInt("id_insumo"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setClave(resultSet.getString("clave"));
        inventario.setNombre(resultSet.getString("nombre"));
        inventario.setIdtipo_insumo(resultSet.getInt("tipo_insumo"));
        return inventario;
    }
    
    private Inventario crearInventarioBusquedaConFamilia(ResultSet resultSet) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(resultSet.getInt("id"));
        inventario.setId_insumo(resultSet.getInt("id_insumo"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setClave(resultSet.getString("clave"));
        inventario.setNombre(resultSet.getString("nombre"));
        inventario.setIdtipo_insumo(resultSet.getInt("tipo_insumo"));
        inventario.setIdFamilia(resultSet.getInt("id_familia_inusmo"));
        return inventario;
    }

    private Inventario crearInventarioSoloDatosImportatens(ResultSet resultSet) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(resultSet.getInt("id"));
        inventario.setId_insumo(resultSet.getInt("id_insumo"));
        inventario.setTotalExistencia(resultSet.getDouble("total_existencia"));
        inventario.setNombre(resultSet.getString("nombre"));
        return inventario;
    }
}
