/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author Gerardo
 */
public class Folio2DAO {

    private Connection connection;

    public Folio2DAO(Connection connection) {
        this.connection = connection;
    }

    public void cambiarDePaquete(int idFolio, int idPaquete) throws SQLException {
        String query = "UPDATE folios SET id_paquete = ? WHERE  id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPaquete);
            statement.setInt(2, idFolio);
            statement.executeUpdate();
        }
    }
    
    
}
