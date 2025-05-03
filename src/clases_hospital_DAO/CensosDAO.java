/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clases_hospital.Censos;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfar
 */
public class CensosDAO {

    Connection con;

    public CensosDAO(Connection con) {
        this.con = con;
    }

    public List<Censos> ejecutarProcedimiento(String fechaIngresada, int idUsuarioCreacionCenso) {
        List<Censos> censos = new ArrayList<>();

        String callProcedure = "{CALL STR_CENSOS(?, ?)}";

        try (CallableStatement stmt = con.prepareCall(callProcedure)) {

            stmt.setString(1, fechaIngresada);
            stmt.setInt(2, idUsuarioCreacionCenso);

            boolean hasResults = stmt.execute();

            while (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        Censos censo = new Censos(
                                rs.getInt("numero_habitacion"),
                                rs.getString("nombre_paciente"),
                                rs.getInt("edad"),
                                rs.getString("nombre"),
                                rs.getString("fecha_ingreso"),
                                rs.getInt("dias_internado"),
                                rs.getString("diagnostico_censo"),
                                rs.getString("plan_censo")
                        );
                        censos.add(censo);
                    }
                }
                hasResults = stmt.getMoreResults();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return censos;
    }
}
