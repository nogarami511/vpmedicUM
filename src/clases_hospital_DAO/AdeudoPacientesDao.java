/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.AdeudoPaciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class AdeudoPacientesDao {
    private Connection connection;
    
    public AdeudoPacientesDao(Connection connection){
        this.connection = connection;
    }
    
    public List<AdeudoPaciente> audeudo() throws SQLException{
        List<AdeudoPaciente> adeudospacientes = new ArrayList();
        String query = "SELECT f.id, p.id_paciente, p.nombre_paciente, f.folio, f.fecha_ingreso, ROUND(f.totaldeabono, 2) AS totaldeabono, ROUND(f.saldoacubrir, 2) AS saldoacubrir FROM folios f INNER JOIN pacientes p ON f.id = p.id_folio AND f.id_estatus_hospitalizacion = 1 AND f.saldoacubrir > 0";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AdeudoPaciente adeudopaciente = mapearAdeudoPaciente(resultSet);
                adeudospacientes.add(adeudopaciente);
            }
        }
        return adeudospacientes;
    }
    
    private AdeudoPaciente mapearAdeudoPaciente(ResultSet resultSet) throws SQLException {
        AdeudoPaciente adeudo = new AdeudoPaciente();
        adeudo.setId(resultSet.getInt("id"));
        adeudo.setId_paciente(resultSet.getInt("id_paciente"));
        adeudo.setNombre_paciente(resultSet.getString("nombre_paciente"));
        adeudo.setFolio(resultSet.getString("folio"));
        adeudo.setFecha_ingreso(resultSet.getDate("fecha_ingreso"));
        adeudo.setTotaldeabono(resultSet.getDouble("totaldeabono"));
        adeudo.setSaldoacubrir(resultSet.getDouble("saldoacubrir"));
        return  adeudo;
    }
}
