/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.Conexion;
import clase.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }
    public List<String> IndicasFaltantes(int id_area) throws SQLException{
        
        List<String> pacientes =  new ArrayList<>();
        if(id_area == 1 || id_area == 4 || id_area ==5){
            try (CallableStatement stm = connection.prepareCall("{call INDICASPENDIENTESPORAREA(?)}")){
             stm.setInt(1, id_area);
             stm.execute();
             ResultSet rs = stm.getResultSet();
             
             while(rs.next()){
                  pacientes.add(rs.getString("nombre_paciente"));
                 
             }
            
         }catch(SQLException e){
             
         }
        }
        
         
        
        return pacientes;
        
    }
    public void EstadoSesion(int id_usuario, boolean status) throws SQLException{
        String respuesta = "";
        try (CallableStatement stm = connection.prepareCall("{call dvrt_ACTUALIZARSESION(?,?)}")){
            stm.setInt(1, id_usuario);
            stm.setBoolean(2, status);
            
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if(rs.next()){
                respuesta = rs.getString("respuesta");
                
            }
        } catch (Exception e) {
        }
        
        
    }

    public void crear(Usuario usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre, pass, cargo, salario, piso) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getContra());
            statement.setString(3, usuario.getCargo());
            statement.setDouble(4, usuario.getSalario());
            statement.setInt(5, usuario.getPiso());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                usuario.setId(generatedKeys.getInt(1));
            }
        }
    }

    public Usuario leer(int id) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = mapearUsuario(resultSet);
                    return usuario;
                }
            }
        }
        return null;
    }

    public List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Usuario usuario = mapearUsuario(resultSet);
                listaUsuarios.add(usuario);
            }
        }
        return listaUsuarios;
    }

    public void actualizar(Usuario usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, pass = ?, cargo = ?, salario = ?, piso = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getContra());
            statement.setString(3, usuario.getCargo());
            statement.setDouble(4, 0);
            statement.setInt(5, 0);
            statement.setInt(6, usuario.getId());

            statement.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    public List<Usuario> ActividadUsuarios () throws SQLException{
        List<Usuario> listausuarios = new ArrayList<>();
        try (Connection con = new Conexion().conectar2();
                CallableStatement stm = con.prepareCall("{call ESTADOUSUARIO()}")){
            
            stm.execute();
            
            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                
                listausuarios.add(mapearactividad(rs));
            }
         
        }catch(SQLException e){
         
        }
            
        
        
        
        return listausuarios;
    }
    
    private Usuario mapearactividad(ResultSet rs) throws SQLException{
        Usuario actividadusuario = new Usuario();
        
        actividadusuario.setNombre(rs.getString("usuario"));
        actividadusuario.setEstado(rs.getString("estado"));
        actividadusuario.setFecha(rs.getString("fecha"));
        actividadusuario.setHora(rs.getString("hora"));
        actividadusuario.setHoras_acumuladas(rs.getInt("horas_acumuladas"));
        return actividadusuario;
    }

    private Usuario mapearUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getInt("id"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setContra(resultSet.getString("pass"));
        usuario.setCargo(resultSet.getString("cargo"));
        usuario.setSalario(resultSet.getDouble("salario"));
        usuario.setPiso(resultSet.getInt("piso"));
        return usuario;
    }
}
