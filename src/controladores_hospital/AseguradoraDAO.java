/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clases_hospital.Aseguradora;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author PC
 */
public class AseguradoraDAO {

    private Connection connection;
    
     Alert alertaError = new Alert(Alert.AlertType.ERROR);

    public AseguradoraDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<Aseguradora> traerCatalogoAseguradoras(){
        List<Aseguradora> listaAseguradora = new ArrayList<>();
        try(CallableStatement stm = connection.prepareCall("{CALL STR_VERASEGURADORAS ()}")){
            stm.execute();
            ResultSet rs = stm.getResultSet();
            while(rs.next()){
                listaAseguradora.add(mapearcatalogoAseguradora(rs));
            }
                
        }catch(SQLException e){
            e.printStackTrace();
            alertaError.setTitle("OCURRIO UN ERROR");
            alertaError.setHeaderText("ocurrio un error " + e.getErrorCode());
            alertaError.setContentText(e.getMessage());
            alertaError.showAndWait();
        }
        
        return listaAseguradora;
    }

    public int addAseguradora(Aseguradora aseguradora) throws SQLException {
    String query = "INSERT INTO aseguradoras (nombre_aseguradora, factor_aseguradora, material_curacion_aseguradora, medicamentos_aseguradora, equipo_medico_aseguradora, usuario_modificacion_aseguradora, fecha_moficacion_aseguradora) VALUES (?, ?, ?, ?, ?, ?, NOW())";
    try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, aseguradora.getNombreAseguradora());
        statement.setDouble(2, aseguradora.getFactorAseguradora());
        statement.setBoolean(3, aseguradora.isMaterialCuracionAseguradora());
        statement.setBoolean(4, aseguradora.isMedicamentosAseguradora());
        statement.setBoolean(5, aseguradora.isEquipoMedicoAseguradora());
        statement.setInt(6, aseguradora.getUsuarioModificacionAseguradora());
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating aseguradora failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating aseguradora failed, no ID obtained.");
            }
        }
    }
}
    
    public void updateAseguradora(Aseguradora aseguradora) throws SQLException {
        String query = "UPDATE aseguradoras SET nombre_aseguradora = ?, factor_aseguradora = ?, material_curacion_aseguradora = ?, medicamentos_aseguradora = ?, equipo_medico_aseguradora = ?, usuario_modificacion_aseguradora = ?, fecha_moficacion_aseguradora = NOW() WHERE id_aseguradora = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, aseguradora.getNombreAseguradora());
            statement.setDouble(2, aseguradora.getFactorAseguradora());
            statement.setBoolean(3, aseguradora.isMaterialCuracionAseguradora());
            statement.setBoolean(4, aseguradora.isMedicamentosAseguradora());
            statement.setBoolean(5, aseguradora.isEquipoMedicoAseguradora());
            statement.setInt(6, aseguradora.getUsuarioModificacionAseguradora());
            statement.setInt(8, aseguradora.getIdAseguradora());
            statement.executeUpdate();
        }
    }

    public Aseguradora getAseguradora(int id) throws SQLException {
        String query = "SELECT * FROM aseguradoras WHERE id_aseguradora = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Aseguradora aseguradora = mapResultSetToAseguradora(resultSet);
                    return aseguradora;
                }
            }
        }
        return null;
    }

    public List<Aseguradora> getAllAseguradoras() throws SQLException {
        List<Aseguradora> aseguradoras = new ArrayList<>();
        String query = "SELECT * FROM aseguradoras";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Aseguradora aseguradora = mapResultSetToAseguradora(resultSet);
                aseguradoras.add(aseguradora);
                
            }
        }
        return aseguradoras;
    }

    private Aseguradora mapResultSetToAseguradora(ResultSet resultSet) throws SQLException {
        Aseguradora aseguradora = new Aseguradora();
        aseguradora.setIdAseguradora(resultSet.getInt("id_aseguradora"));
        aseguradora.setNombreAseguradora(resultSet.getString("nombre_aseguradora"));
        aseguradora.setFactorAseguradora(resultSet.getDouble("factor_aseguradora"));
        aseguradora.setMaterialCuracionAseguradora(resultSet.getBoolean("material_curacion_aseguradora"));
        aseguradora.setMedicamentosAseguradora(resultSet.getBoolean("medicamentos_aseguradora"));
        aseguradora.setEquipoMedicoAseguradora(resultSet.getBoolean("equipo_medico_aseguradora"));
        aseguradora.setUsuarioModificacionAseguradora(resultSet.getInt("usuario_modificacion_aseguradora"));
        aseguradora.setFechaModificacionAseguradora(resultSet.getDate("fecha_moficacion_aseguradora"));
        return aseguradora;
    }
    private Aseguradora mapearcatalogoAseguradora(ResultSet resultSet) throws SQLException {
        Aseguradora aseguradora = new Aseguradora();
        aseguradora.setIdAseguradora(resultSet.getInt("id_aseguradora"));
        aseguradora.setNombreAseguradora(resultSet.getString("nombre_aseguradora"));
        aseguradora.setFactorString(resultSet.getString("factor"));
        aseguradora.setMaterialCuracionAseguradora(resultSet.getBoolean("material_curacion_aseguradora"));
        aseguradora.setMedicamentosAseguradora(resultSet.getBoolean("medicamentos_aseguradora"));
        aseguradora.setEquipoMedicoAseguradora(resultSet.getBoolean("equipo_medico_aseguradora"));
        return aseguradora;
    }
}
