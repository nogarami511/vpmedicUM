/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.ArmadoPaqueteMedico;
import clases_hospital.Consumo;
import clases_hospital.IndicaDetalle;
import clases_hospital.PaqueteMedico;
import clases_hospital_DAO.ArmadoPaqueteMedicoDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.Folio2DAO;
import clases_hospital_DAO.IndicaDetalleDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class CambioDEPaqueteController implements Initializable {

    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    private int idFolio, idPaciente, idPaquete;
    PaqueteMedico paqueteMedicoSeleccion;
    Conexion conexion = new Conexion();
    Connection con;
    ObservableList<PaqueteMedico> paquetesMedicos;
    List<Consumo> listaConsumo;
    List<ArmadoPaqueteMedico> listAmardoArmadoPaqueteMedicos;
    List<IndicaDetalle> listaIndicaDetalle;

    ConsumosDAO consumosDAO;
    ArmadoPaqueteMedicoDAO armadoPaqueteMedicoDAO;
    IndicaDetalleDAO indicaDetalleDAO;

    @FXML
    private TextField txfNombrePaciente;
    @FXML
    private TextField txfPaqueteMedico;
    @FXML
    private Button btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cambiarPaquete(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        alertaConfirmacion.setTitle("CONFIRMACION");
        alertaConfirmacion.setHeaderText("CAMBIO DE PAQUETE");
        alertaConfirmacion.setContentText("¿ESTÁ SEGURO DE CAMBIAR DE PAQUETE AL PACIENTE ?");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
            if (idPaquete == 0) {
                cuentaAbiertaPaquete();
            } else {
                quitarPaqueteConsumo();
            }
            Folio2DAO folio2DAO = new Folio2DAO(con);
            ConsumosDAO consumosDAO = new ConsumosDAO(con);
            /*ACTUALIZAMOS EN COSUMOS TODOS SUS PAQUETES EN ID_ESTATUS_INSUMO EN 0*/
            consumosDAO.actualizarConsumoPorCambioPaquete(idFolio);
            /*CAMBIAMOS EN SU FOLIO EL PAQUETE CON EL QUE VA A ESTAR*/
            folio2DAO.cambiarDePaquete(idFolio, paqueteMedicoSeleccion.getId());
            /*AGREGAMOS A CONSUMO EL NUEVO CAMBIO DE PAQUETE*/
            Consumo consumo = new Consumo();
            consumo.setTipo(paqueteMedicoSeleccion.getNombre());
            consumo.setCantidad(0);
            consumo.setMonto(0);
            consumo.setFolio("");//FOLIO EN STRING NO LO USAMOS, SE DEJARA VACIO
            consumo.setId_pasiente(idPaciente);
            consumo.setId_PaqueteAlimento(0);
            consumo.setId_tipo_consumo(0);//el id tipo consumo 4 son los paquetes medicos
            consumo.setId_folio(idFolio);
            consumo.setId_producto_venta(paqueteMedicoSeleccion.getId());
            consumo.setPrecioUnitario(0);

            consumosDAO.insertarConsumoParacambioPaquete(consumo);

            alertaConfirmacion.setHeaderText("CAMBIO DE PAQUETE EXITOSO");
            alertaConfirmacion.setContentText("PROCEDIMIENTO EJECUTADO CON EXITO");
            alertaConfirmacion.showAndWait();
            salir(event);
        }
        con.close();
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void recibirDatos(int idFolio, String nombrePaciente, int idPaciente, int idPaquete) throws SQLException {
        this.idFolio = idFolio;
        txfNombrePaciente.setText(nombrePaciente);
        this.idPaciente = idPaciente;
        this.idPaquete = idPaquete;
        autocompletado();
    }

    public void autocompletado() throws SQLException {
        con = conexion.conectar2();
        PaqueteMedicoDAO paquetesMedicosDAO = new PaqueteMedicoDAO(con);
        paquetesMedicos = FXCollections.observableArrayList(paquetesMedicosDAO.obtenerTodos());
        AutoCompletionBinding<PaqueteMedico> paquetes = TextFields.bindAutoCompletion(txfPaqueteMedico, paquetesMedicos);
        paquetes.setPrefWidth(1000);
        paquetes.setOnAutoCompleted(event -> {
            paqueteMedicoSeleccion = event.getCompletion();

        });
        con.close();
    }

    private void cuentaAbiertaPaquete() {
        try {
            con = conexion.conectar2();
            consumosDAO = new ConsumosDAO(con);
            armadoPaqueteMedicoDAO = new ArmadoPaqueteMedicoDAO(con);
            listaConsumo = consumosDAO.optenerTodosCosnumos(idFolio);
            listAmardoArmadoPaqueteMedicos = armadoPaqueteMedicoDAO.optenerArmadoPaqueteMedicoListas(paqueteMedicoSeleccion.getId());
            double cantidadEnPaquete;
            double cantidadEnConsumo;

            for (int i = 0; i < listAmardoArmadoPaqueteMedicos.size(); i++) {
                cantidadEnPaquete = listAmardoArmadoPaqueteMedicos.get(i).getCantidad();
                System.out.println("\033[0;34m" + "I = " + i + " ID: " + listAmardoArmadoPaqueteMedicos.get(i).getIdInsumo() + "\033[0m");
                cantidadEnConsumo = 0;
                for (int j = 0; j < listaConsumo.size(); j++) {
                    if (listAmardoArmadoPaqueteMedicos.get(i).getIdInsumo() == listaConsumo.get(j).getId_producto_venta()) {
                        if (cantidadEnConsumo < cantidadEnPaquete) {
                            System.out.println("\033[1m" + "CANTIDAD EN CONSUMO " + cantidadEnConsumo + "\033[0m");
                            cantidadEnConsumo = consumosDAO.actualizarPaquete(cantidadEnConsumo, listaConsumo.get(j));
                            System.out.println("\033[1m" + "=========================");
                            System.out.println("\033[1m" + "CANTIDAD EN CONSUMO " + cantidadEnConsumo + "\033[0m");
                            System.out.println("\033[1m" + "=========================");
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            //Aqui colocare un catch de un error.
            Logger.getLogger(CambioDEPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void quitarPaqueteConsumo() {
        try {
            con = conexion.conectar2();
            consumosDAO = new ConsumosDAO(con);
            consumosDAO.quitarPaquete(idFolio);
            cuentaAbiertaPaquete();
        } catch (SQLException ex) {
            Logger.getLogger(CambioDEPaqueteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void comparacionHojasDeConsumoSinCambioDeTurno() {
        con = conexion.conectar2();
        consumosDAO = new ConsumosDAO(con);
        indicaDetalleDAO = new IndicaDetalleDAO(con);

    }
}
