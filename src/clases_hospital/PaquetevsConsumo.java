/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_hospital;

import clase.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import vpmedicaplaza.VPMedicaPlaza;

/**
 *
 * @author Gerardo
 */
public class PaquetevsConsumo {

    /*
        ** El total que consumió el paciente se retorna con
        el array "insumosExtra"
     */
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ArrayList<ConsumoPaciente> arrayListConsumo = new ArrayList<>();//todos los insumos del paciente
    ArrayList<InsumosPaquete> arrayListInsumosPaquete = new ArrayList<>();// lista de insumos del paquete

    //ArrayList<ConsumoPaciente> insumosExtra = new ArrayList<>();
    ArrayList<ConsumoPaciente> devolucionInsumos = new ArrayList<>();
    ArrayList<ExcedenteConsumo> excedenteConsumos = new ArrayList<>();
    int idFolio = 0;
    int idPaquete = 0;

    public PaquetevsConsumo(int folio, int idPaquete) {
        this.idFolio = folio;
        this.idPaquete = idPaquete;
        traerConsumidoPaciente();
        traerInsumosPaquete();
    }

    public void enviarExcedenteConsumo() {
        try {
            con = conexion.conectar2();
            CallableStatement stmt = null;
            String sql = "{call ingresarExcedenteConsumo (?,?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            for (ExcedenteConsumo e : excedenteConsumos) {
                stmt.setInt(1, idFolio);
                stmt.setInt(2, e.getIdInsumo());
                stmt.setInt(3, e.getCantidadExcedente());
                stmt.setDouble(4, e.getPrecioUnitario());
                stmt.setDouble(5, e.getMonto());
                stmt.setInt(6, VPMedicaPlaza.userSystem);
                stmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean verificarConsumo() {
        try {
            ConsumoPaciente consumoPaciente;
            con = conexion.conectar2();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * \n"
                    + "  FROM consumos c\n"
                    + "  WHERE c.id_folio = '" + idFolio + "';");
            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void comparativa() {
        double monto = 0;
        int consumido = 0, cantidadPaquete = 0;

        for (ConsumoPaciente consumoPaciente : arrayListConsumo) {
            boolean encontrado = false; // Bandera para indicar si se encontró una coincidencia
            for (InsumosPaquete insumosPaquete : arrayListInsumosPaquete) {
                if (consumoPaciente.getIdInsumo() == insumosPaquete.getIdInsumo()) {
                    consumido = consumoPaciente.getCantidad();
                    cantidadPaquete = insumosPaquete.getCantidad();
                    if (cantidadPaquete >= consumido) {
                        cantidadPaquete -= consumido;
                        insumosPaquete.setCantidad(cantidadPaquete);
                    } else {
                        consumido -= cantidadPaquete;
                        cantidadPaquete -= consumido;
                        insumosPaquete.setCantidad(cantidadPaquete);
                        ExcedenteConsumo excedenteConsumo = new ExcedenteConsumo();
                        excedenteConsumo.setIdFolio(idFolio);
                        excedenteConsumo.setIdInsumo(consumoPaciente.getIdInsumo());
                        excedenteConsumo.setCantidadExcedente(consumido);
                        excedenteConsumo.setPrecioUnitario(consumoPaciente.getMonto());
                        monto = (consumoPaciente.getMonto() * consumido);
                        excedenteConsumo.setMonto(monto);
                        excedenteConsumos.add(excedenteConsumo);
                    }
                    encontrado = true; // Se encontró una coincidencia
                    break; // Salir del bucle interno
                }
            }

            if (!encontrado) { // No se encontró una coincidencia, agregar excedenteConsumo
                ExcedenteConsumo excedenteConsumo = new ExcedenteConsumo();
                excedenteConsumo.setIdFolio(idFolio);
                excedenteConsumo.setIdInsumo(consumoPaciente.getIdInsumo());
                excedenteConsumo.setCantidadExcedente(consumoPaciente.getCantidad());
                excedenteConsumo.setPrecioUnitario(consumoPaciente.getMonto());
                monto = (consumoPaciente.getMonto() * consumoPaciente.getCantidad());
                excedenteConsumo.setMonto(monto);
                excedenteConsumos.add(excedenteConsumo);
            }
        }

    }

    private void traerConsumidoPaciente() {
        try {
            ConsumoPaciente consumoPaciente;
            con = conexion.conectar2();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT i.id, i.nombre, SUM(c.cantidad), pin.presentacion, c1.precio_venta_unitaria, c.id AS idConsumo\n"
                    + "                                        FROM consumos c \n"
                    + "                                        INNER JOIN insumos i ON i.nombre = c.tipo\n"
                    + "                                        INNER JOIN costos c1 ON i.id = c1.id_insumo\n"
                    + "                                        INNER JOIN presentaciones_insumos pin ON pin.id = i.id_presentacion\n"
                    + "                                        WHERE c.id_folio = '" + idFolio + "' \n"
                    + "        GROUP BY i.id;");
            while (rs.next()) {
                consumoPaciente = new ConsumoPaciente();
                consumoPaciente.setIdInsumo(rs.getInt(1));
                consumoPaciente.setNombre(rs.getString(2));
                consumoPaciente.setCantidad(rs.getInt(3));
                consumoPaciente.setPresentacion(rs.getString(4));
                consumoPaciente.setMonto(rs.getDouble(5));
                consumoPaciente.setIdConsumo(rs.getInt(6));
                arrayListConsumo.add(consumoPaciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void traerInsumosPaquete() {
        try {
            InsumosPaquete insumosPaquete;
            con = conexion.conectar2();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("  SELECT a.id_insumo, i.nombre, SUM(a.cantidad) AS cantidad , pin.presentacion\n"
                    + "  FROM armadopaquetemedico a\n"
                    + "  INNER JOIN insumos i ON a.id_insumo = i.id \n"
                    + "  INNER JOIN presentaciones_insumos pin ON pin.id = i.id_presentacion\n"
                    + "WHERE a.id_paquete = '" + idPaquete + "' \n"
                    + "    GROUP BY a.id_insumo");
            while (rs.next()) {
                insumosPaquete = new InsumosPaquete();
                insumosPaquete.setIdInsumo(rs.getInt(1));
                insumosPaquete.setNombre(rs.getString(2));
                insumosPaquete.setCantidad(rs.getInt(3));
                insumosPaquete.setPresentacion(rs.getString(4));
                arrayListInsumosPaquete.add(insumosPaquete);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList getListaConsumidos() {
        return arrayListConsumo;
    }

    public ArrayList getListaInsumosPaquete() {
        return arrayListInsumosPaquete;
    }

    public ArrayList getExcedentesConsumos() {
        return excedenteConsumos;
    }

    public ArrayList getArrayListDevolucionInsumos() {
        return devolucionInsumos;
    }

    public class ConsumoPaciente {

        int idInsumo, cantidad, idConsumo;
        String nombre, presentacion;
        Double monto;

        public int getIdConsumo() {
            return idConsumo;
        }

        public void setIdConsumo(int idConsumo) {
            this.idConsumo = idConsumo;
        }

        public Double getMonto() {
            return monto;
        }

        public void setMonto(Double monto) {
            this.monto = monto;
        }

        public int getIdInsumo() {
            return idInsumo;
        }

        public void setIdInsumo(int idInsumo) {
            this.idInsumo = idInsumo;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPresentacion() {
            return presentacion;
        }

        public void setPresentacion(String presentacion) {
            this.presentacion = presentacion;
        }

    }

    public class InsumosPaquete {

        int idInsumo, cantidad;
        String nombre, presentacion;

        public int getIdInsumo() {
            return idInsumo;
        }

        public void setIdInsumo(int idInsumo) {
            this.idInsumo = idInsumo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPresentacion() {
            return presentacion;
        }

        public void setPresentacion(String presentacion) {
            this.presentacion = presentacion;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

    }

    public class ExcedenteConsumo {

        int id, idFolio, idInsumo, cantidadExcedente;
        Double precioUnitario, monto;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdFolio() {
            return idFolio;
        }

        public void setIdFolio(int idFolio) {
            this.idFolio = idFolio;
        }

        public int getIdInsumo() {
            return idInsumo;
        }

        public void setIdInsumo(int idInsumo) {
            this.idInsumo = idInsumo;
        }

        public int getCantidadExcedente() {
            return cantidadExcedente;
        }

        public void setCantidadExcedente(int cantidadExcedente) {
            this.cantidadExcedente = cantidadExcedente;
        }

        public Double getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(Double precioUnitario) {
            this.precioUnitario = precioUnitario;
        }

        public Double getMonto() {
            return monto;
        }

        public void setMonto(Double monto) {
            this.monto = monto;
        }
    }
}
