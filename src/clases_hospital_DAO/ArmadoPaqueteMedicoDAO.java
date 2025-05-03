/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital_DAO;

import clase.ArmadoPaqueteMedicoLista;
import clases_hospital.ArmadoPaqueteMedico;
import clases_hospital.ConfiguracionPaquete;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vpmedicaplaza.VPMedicaPlaza;

public class ArmadoPaqueteMedicoDAO {

    private Connection connection;

    public ArmadoPaqueteMedicoDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(ArmadoPaqueteMedico armado) throws SQLException {
        String query = "INSERT INTO armadopaquetemedico (id_insumo, id_paquete, cantidad, costo, precio_paquete, idUsuario, familia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("ID INSUMO: " + armado.getIdInsumo() + " ID PAQUETE " + armado.getIdPaquete());
            statement.setInt(1, armado.getIdInsumo());
            statement.setInt(2, armado.getIdPaquete());
            statement.setDouble(3, armado.getCantidad());
            statement.setDouble(4, armado.getCosto());
            statement.setDouble(5, armado.getPrecioPaquete());
            statement.setInt(6, VPMedicaPlaza.userSystem);
            statement.setBoolean(7, armado.isFamilia());

            statement.executeUpdate();
        }
    }

    public List<ArmadoPaqueteMedico> obtenerArmadoMedicoPorID(int id) throws SQLException {
        List<ArmadoPaqueteMedico> armados = new ArrayList<>();
//        String query = "SELECT a.id, a.id_insumo, a.cantidad, i.nombre \n"
//                + "  FROM armadopaquetemedico a \n"
//                + "  INNER JOIN insumos i ON i.id = a.id_insumo WHERE a.id_paquete = '" + id + "' ";

        String query = "SELECT \n"
                + "    a.id, \n"
                + "    a.id_insumo, \n"
                + "    a.cantidad, \n"
                + "    i.nombre AS nombre\n"
                + "  FROM armadopaquetemedico a\n"
                + "  INNER JOIN insumos i ON i.id = a.id_insumo \n"
                + "  WHERE a.id_paquete = " + id + " AND a.familia = 0\n"
                + "\n"
                + "  UNION ALL \n"
                + "\n"
                + "   SELECT \n"
                + "    a.id, \n"
                + "    a.id_insumo, \n"
                + "    a.cantidad, \n"
                + "    fi.nombre_familia_inusmo AS nombre\n"
                + "  FROM armadopaquetemedico a\n"
                + "  INNER JOIN familias_insumos fi ON fi.id_familia_inusmo = a.id_insumo \n"
                + "  WHERE a.id_paquete = " + id + " AND a.familia = 1;";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ArmadoPaqueteMedico armado = new ArmadoPaqueteMedico();
                armado.setId(resultSet.getInt("id"));
                armado.setIdInsumo(resultSet.getInt("id_insumo"));
                armado.setCantidad(resultSet.getDouble("cantidad"));
                armado.setNombreInsumo(resultSet.getString("nombre"));
                armados.add(armado);
            }
        }
        return armados;
    }


    public void insertAllLista(List<ArmadoPaqueteMedico> armados) throws SQLException {


        String query = "INSERT INTO armadopaquetemedico (id_insumo, id_paquete, cantidad, costo, precio_paquete, idUsuario, familia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < armados.size(); i++) {
                statement.setInt(1, armados.get(i).getIdInsumo());
                statement.setInt(2, armados.get(i).getIdPaquete());
                statement.setDouble(3, armados.get(i).getCantidad());
                statement.setDouble(4, armados.get(i).getCosto());
                statement.setDouble(5, armados.get(i).getPrecioPaquete());
                statement.setInt(6, VPMedicaPlaza.userSystem);
                statement.setBoolean(7, armados.get(i).isFamilia());

                statement.addBatch(); // Agregar la operación a un lote
            }

            statement.executeBatch(); // Ejecutar todas las inserciones en un lote
        }
    }


    public void insertAll(ArmadoPaqueteMedico armados) throws SQLException {

        String query = "INSERT INTO armadopaquetemedico (id_insumo, id_paquete, cantidad, costo, precio_paquete, idUsuario, familia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (CallableStatement statement = connection.prepareCall("{call AGREGAR_ARMADO_CON_FACTOR (?, ?, ?, ?, ?, ?, ?)}")) {
            statement.setInt(1, armados.getIdInsumo());
            statement.setInt(2, armados.getIdPaquete());
            statement.setDouble(3, armados.getCantidad());
            statement.setDouble(4, armados.getCosto());
            statement.setDouble(5, armados.getPrecioPaquete());
            statement.setInt(6, VPMedicaPlaza.userSystem);
            statement.setBoolean(7, armados.isFamilia());
           

            statement.execute(); // Ejecutar todas las inserciones en un lote
        }
    }

    public void insertAllConFamilias(List<ArmadoPaqueteMedico> armados) throws SQLException {
        System.err.println("HEY LISTEN =============== NETSIL YEH");
        String query = "INSERT INTO armadopaquetemedico (id_insumo, id_paquete, cantidad, costo, precio_paquete, idUsuario, familia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.err.println("HEY LISTEN =============== NETSIL YEH");
            for (ArmadoPaqueteMedico armado : armados) {

                statement.setInt(1, armado.getIdInsumo());
                statement.setInt(2, armado.getIdPaquete());
                statement.setDouble(3, armado.getCantidad());
                statement.setDouble(4, armado.getCosto());
                statement.setDouble(5, armado.getPrecioPaquete());
                statement.setInt(6, VPMedicaPlaza.userSystem);
                statement.setBoolean(7, armado.isFamilia());

                statement.addBatch(); // Agregar la operación a un lote
            }

            statement.executeBatch(); // Ejecutar todas las inserciones en un lote
        }
    }

    public void actulizarCantidad(ArmadoPaqueteMedico armado) throws SQLException {
        String query = "UPDATE armadopaquetemedico a SET a.cantidad = ?, a.idUsuario = ?, a.fechaModificacion = NOW() WHERE a.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, armado.getCantidad());
            statement.setInt(2, VPMedicaPlaza.userSystem);
            statement.setInt(3, armado.getId());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas == 0) {
                throw new SQLException("La actualización falló, no se modificó ninguna fila.");
            }
        }
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM armadopaquetemedico WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }

    public ObservableList<ConfiguracionPaquete> armadoAConfiguracionPorIDPaquete(int id, int idFolio) throws SQLException {
        ObservableList<ConfiguracionPaquete> configuracion = FXCollections.observableArrayList();
        String query = "SELECT a.id, a.id_insumo, a.cantidad, i.nombre \n"
                + "  FROM armadopaquetemedico a \n"
                + "  INNER JOIN insumos i ON i.id = a.id_insumo WHERE a.id_paquete = '" + id + "' ";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ConfiguracionPaquete configuracionPaquete = new ConfiguracionPaquete();
                configuracionPaquete.setId_insumo(resultSet.getInt("id_insumo"));
                configuracionPaquete.setNombre_insumo(resultSet.getString("nombre"));
                configuracionPaquete.setPrecio_insumo(0.0);
                configuracionPaquete.setId_folio(idFolio);
                configuracion.add(configuracionPaquete);
            }
        }

        return configuracion;
    }

    public List<ArmadoPaqueteMedicoLista> aramdoPaqueteMedicoListas(int idPaqeute) throws SQLException {
        List<ArmadoPaqueteMedicoLista> consumoList = new ArrayList<>();

        String query = "SELECT a.id_insumo, sum(a.cantidad) AS cantidad, a.familia FROM armadopaquetemedico a WHERE a.id_paquete = ? GROUP BY a.id_insumo;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPaqeute);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    consumoList.add(createArmadoPaqueteMedicoListaFromResultSet(resultSet));
                }
                return consumoList;
            }
        }
    }

    public List<ArmadoPaqueteMedico> aramdoPaqueteMedicosDatos(int idPaqeute) throws SQLException {
        List<ArmadoPaqueteMedico> consumoList = new ArrayList<>();

        String query = "SELECT a.id, a.id_insumo, a.id_paquete, CASE WHEN a.familia = 0 THEN i.nombre ELSE (SELECT fi.nombre_familia_inusmo FROM familias_insumos fi WHERE fi.id_familia_inusmo = a.id_insumo) END AS nombre, a.cantidad AS cantidad, CASE WHEN a.costo = 0 THEN c.costo_compra_unitaria ELSE a.costo END AS costo, CASE WHEN a.precio_paquete = 0 THEN ROUND((c.costo_compra_unitaria * a.cantidad) / (p.factor_paquete / 100),2) ELSE a.precio_paquete END AS precio_paquete, a.familia FROM armadopaquetemedico a INNER JOIN insumos i ON a.id_insumo = i.id INNER JOIN costos c ON a.id_insumo = c.id_insumo INNER JOIN paquetesmedicos p ON a.id_paquete = p.id WHERE a.id_paquete = ? GROUP BY a.id_insumo;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPaqeute);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    consumoList.add(createArmadoPaqueteMedicoListaFromResultSeDatost(resultSet));
                }
                return consumoList;
            }
        }
    }

    public List<ArmadoPaqueteMedico> aramdoPaqueteMedicosDatosCambio(int idPaqeute) throws SQLException {
        List<ArmadoPaqueteMedico> consumoList = new ArrayList<>();
        
        String query = "SELECT a.id, a.id_insumo, a.id_paquete, CASE WHEN a.familia = 0 THEN i.nombre ELSE (SELECT fi.nombre_familia_inusmo FROM familias_insumos fi WHERE fi.id_familia_inusmo = a.id_insumo) END AS nombre, a.cantidad AS cantidad, CASE WHEN a.costo = 0 THEN c.costo_compra_unitaria ELSE a.costo END AS costo, CASE WHEN a.precio_paquete = 0 THEN ROUND((c.costo_compra_unitaria * a.cantidad) / (p.factor_paquete / 100),2) ELSE a.precio_paquete END AS precio_paquete, a.familia, CASE WHEN DATE(p.fechaModificacion) = DATE(c.fechaModificacion) THEN 1 ELSE 0 END AS modificado FROM armadopaquetemedico a INNER JOIN insumos i ON a.id_insumo = i.id INNER JOIN costos c ON a.id_insumo = c.id_insumo INNER JOIN paquetesmedicos p ON a.id_paquete = p.id WHERE a.id_paquete = ? GROUP BY a.id_insumo;";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPaqeute);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    consumoList.add(createArmadoPaqueteMedicoListaFromResultSeDatostModificdo(resultSet));
                }
                return consumoList;
            }
        }
    }

    public double sumaArmadoPaqueteMedicoInsumos(int idPaqeute) throws SQLException {
        double regreso = 0;
        String query = "SELECT ROUND(SUM(a.cantidad*c.costo_compra_unitaria), 2) AS suma FROM armadopaquetemedico a INNER JOIN costos c ON a.id_insumo = c.id_insumo WHERE a.id_paquete = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPaqeute);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    regreso = resultSet.getDouble("suma");
                }
                return regreso;
            }
        }
    }
    
    public List<ArmadoPaqueteMedico> optenerArmadoPaqueteMedicoListas(int idPaquete) throws SQLException {
        List<ArmadoPaqueteMedico> armadoPaqueteMedicos = new ArrayList<>();
        String query = "CALL STR_OPTENER_PAQUETE(?)";
        try(CallableStatement stm = connection.prepareCall(query)){
            stm.setInt(1, idPaquete);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ArmadoPaqueteMedico armadoPaqueteMedico = createArmadoPaqueteMedicoFromResultSet(rs);
                armadoPaqueteMedicos.add(armadoPaqueteMedico);
            }
        }
        return armadoPaqueteMedicos;
    }

    private ArmadoPaqueteMedicoLista createArmadoPaqueteMedicoListaFromResultSet(ResultSet resultSet) throws SQLException {
        ArmadoPaqueteMedicoLista armadoPaqueteMedicoLista = new ArmadoPaqueteMedicoLista();

        armadoPaqueteMedicoLista.setIdInsumo(resultSet.getInt("id_insumo"));
        armadoPaqueteMedicoLista.setCantidadInsumo(resultSet.getDouble("cantidad"));
        armadoPaqueteMedicoLista.setFamilia(resultSet.getBoolean("familia"));

        return armadoPaqueteMedicoLista;
    }
    
    private ArmadoPaqueteMedico createArmadoPaqueteMedicoFromResultSet(ResultSet resultSet) throws SQLException {
        ArmadoPaqueteMedico armadoPaqueteMedicoLista = new ArmadoPaqueteMedico();

        armadoPaqueteMedicoLista.setId(resultSet.getInt("id"));
        armadoPaqueteMedicoLista.setIdPaquete(resultSet.getInt("id_paquete"));
        armadoPaqueteMedicoLista.setIdInsumo(resultSet.getInt("id_insumo"));
        armadoPaqueteMedicoLista.setCantidad(resultSet.getDouble("cantidad"));
        armadoPaqueteMedicoLista.setCosto(resultSet.getDouble("costo"));
        armadoPaqueteMedicoLista.setPrecioPaquete(resultSet.getDouble("precio_paquete"));
        armadoPaqueteMedicoLista.setFamilia(resultSet.getBoolean("familia"));

        return armadoPaqueteMedicoLista;
    }

    private ArmadoPaqueteMedico createArmadoPaqueteMedicoListaFromResultSeDatost(ResultSet resultSet) throws SQLException {
        ArmadoPaqueteMedico armadoPaqueteMedicoLista = new ArmadoPaqueteMedico();

        armadoPaqueteMedicoLista.setId(resultSet.getInt("id"));
        armadoPaqueteMedicoLista.setIdPaquete(resultSet.getInt("id_paquete"));
        armadoPaqueteMedicoLista.setIdInsumo(resultSet.getInt("id_insumo"));
        armadoPaqueteMedicoLista.setNombreInsumo(resultSet.getString("nombre"));
        armadoPaqueteMedicoLista.setCantidad(resultSet.getDouble("cantidad"));
        armadoPaqueteMedicoLista.setCosto(resultSet.getDouble("costo"));
        armadoPaqueteMedicoLista.setPrecioPaquete(resultSet.getDouble("precio_paquete"));
        armadoPaqueteMedicoLista.setFamilia(resultSet.getBoolean("familia"));

        armadoPaqueteMedicoLista.setCostoxcantidad(resultSet.getDouble("costo") * resultSet.getDouble("cantidad"));
        armadoPaqueteMedicoLista.setCostoConFormula(resultSet.getDouble("precio_paquete"));

        armadoPaqueteMedicoLista.setsCosto("$" + (resultSet.getDouble("costo") * resultSet.getDouble("cantidad")));
        armadoPaqueteMedicoLista.setsCostoConFormula("$" + resultSet.getDouble("precio_paquete"));

        return armadoPaqueteMedicoLista;
    }
    
    private ArmadoPaqueteMedico createArmadoPaqueteMedicoListaFromResultSeDatostModificdo(ResultSet resultSet) throws SQLException {
        ArmadoPaqueteMedico armadoPaqueteMedicoLista = new ArmadoPaqueteMedico();

        armadoPaqueteMedicoLista.setId(resultSet.getInt("id"));
        armadoPaqueteMedicoLista.setIdPaquete(resultSet.getInt("id_paquete"));
        armadoPaqueteMedicoLista.setIdInsumo(resultSet.getInt("id_insumo"));
        armadoPaqueteMedicoLista.setNombreInsumo(resultSet.getString("nombre"));
        armadoPaqueteMedicoLista.setCantidad(resultSet.getDouble("cantidad"));
        armadoPaqueteMedicoLista.setCosto(resultSet.getDouble("costo"));
        armadoPaqueteMedicoLista.setPrecioPaquete(resultSet.getDouble("precio_paquete"));
        armadoPaqueteMedicoLista.setFamilia(resultSet.getBoolean("familia"));

        armadoPaqueteMedicoLista.setCostoxcantidad(resultSet.getDouble("costo") * resultSet.getDouble("cantidad"));
        armadoPaqueteMedicoLista.setCostoConFormula(resultSet.getDouble("precio_paquete"));

        armadoPaqueteMedicoLista.setsCosto("$" + (resultSet.getDouble("costo") * resultSet.getDouble("cantidad")));
        armadoPaqueteMedicoLista.setsCostoConFormula("$" + resultSet.getDouble("precio_paquete"));
        
        armadoPaqueteMedicoLista.setModificado(resultSet.getInt("modificado"));

        return armadoPaqueteMedicoLista;
    }

}
