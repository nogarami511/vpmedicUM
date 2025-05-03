/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.CitaQuirofano;
import clases_hospital.CuentaPaciente2;
import clases_hospital.Folio;
import clases_hospital.NumerosALetras;
import clases_hospital_DAO.ActualizacionFolioDAO;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.ConsumoPacienteMezclasDAO;
import clases_hospital_DAO.CuentaPacienteDAO2;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.PagosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import reportes.ReporteC;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CalculoSegurosController implements Initializable {
    
    Conexion conexion = new Conexion();
    Connection con;
    ObservableList<CuentaPaciente2> pacientesHospitalizados;

    Alert alertError = new Alert(Alert.AlertType.ERROR);
    Alert alertinfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaOpciones = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaWARNING = new Alert(Alert.AlertType.WARNING);

    CitaQuirofano citaQuirofano;
    Folio folio;

    IndicaspDAO indicaspDAO;

    PagosDAO pagodao;
    FoliosDAO foliodao;
    ActualizacionFolioDAO actualizarfoliodao;
    CitaQuirofanoDAO citaQuirofanodao;
    ConsumoPacienteMezclasDAO consumoPacienteMezclasDAO;

    double abonoTotal;
    double adeudo;
    double totaconiva;
    int id_CitaQuirofano;

    @FXML
    private TableView<CuentaPaciente2> tabla;
    @FXML
    private TableColumn<?, ?> colPaciente;
    @FXML
    private TableColumn<?, ?> colHabitacion;
    @FXML
    private TableColumn<CuentaPaciente2, String> colImprimir;
    @FXML
    private Button btnBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try {
            // TODO
            llenarTabla();
            colPaciente.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
            colHabitacion.setCellValueFactory(new PropertyValueFactory("tipohabitacion"));
            corteParcial();
        } catch (SQLException ex) {
            Logger.getLogger(CuentaPaciente2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        CuentaPacienteDAO2 cuentaPacienteDAO2 = new CuentaPacienteDAO2(con);
        pacientesHospitalizados = FXCollections.observableArrayList(cuentaPacienteDAO2.obtenerPacientesHospitalizadosConHemodinamia());
        tabla.setItems(pacientesHospitalizados);
        con.close();
    }
    
    private void corteParcial() {
        //generarColumnaComplemento(foliodao);
        Callback<TableColumn<CuentaPaciente2, String>, TableCell<CuentaPaciente2, String>> corteParcial = (TableColumn<CuentaPaciente2, String> param) -> {
            final TableCell<CuentaPaciente2, String> cell = new TableCell<CuentaPaciente2, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        CuentaPaciente2 cuentpaciente = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaOpciones.setHeaderText(null);
                                alertaOpciones.setTitle("Confirmación");
                                alertaOpciones.setContentText("¿Estas seguro de ver el Consumo de: " + cuentpaciente.getNombrePaciente() + " ?");
                                Optional<ButtonType> action = alertaOpciones.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    actualizarFolioParcial(cuentpaciente);
                                    verConsumoParcial(folio);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(entrada);
                    }
                }
            };
            return cell;
        };

        colImprimir.setCellFactory(corteParcial);

    }
    
    private void actualizarFolioParcial(CuentaPaciente2 cuentpaciente) {
        con = conexion.conectar2();
        try {
            pagodao = new PagosDAO(con);
            foliodao = new FoliosDAO(con);
            actualizarfoliodao = new ActualizacionFolioDAO(con);
            citaQuirofanodao = new CitaQuirofanoDAO(con);

            citaQuirofano = citaQuirofanodao.obteCitaQuirofanoPorIdFolio(cuentpaciente.getIdFolio());
            folio = foliodao.obtenerFolioPorId(cuentpaciente.getIdFolio());

            if (citaQuirofano == null) {
                id_CitaQuirofano = 0;
            } else {
                id_CitaQuirofano = citaQuirofano.getIdQuirofano();
            }

            double sumatoriatotal = actualizarfoliodao.calculateTotalSubtotal(actualizarfoliodao.getReportItemsParcial(folio.getId_paquete(), cuentpaciente.getIdFolio(), id_CitaQuirofano, folio.getId_habitacion(), folio.getNumero_habitacion()));

            abonoTotal = pagodao.optenerTotalPagosPaciente(cuentpaciente.getIdFolio());
            adeudo = (sumatoriatotal * 1.16) - abonoTotal;
            totaconiva = sumatoriatotal * 1.16;

            folio.setMontoHastaElMomento(sumatoriatotal);
            //double saldoacubir = totaconiva - abonoTotal;
            //folio.setSaldoACubrir(saldoacubir);
            foliodao.actualizarFolio(folio);

        } catch (SQLException ex) {
            Logger.getLogger(CuentaPaciente2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void verConsumoParcial(Folio folio) {

        try {
            NumerosALetras numeroLetras = new NumerosALetras(totaconiva);
            String numeroALetra = numeroLetras.getCantidadString();
            
            con = conexion.conectar2();
            citaQuirofanodao = new  CitaQuirofanoDAO(con);
            System.out.println(id_CitaQuirofano);
            int idquiro = citaQuirofanodao.obtenerIdQuirofanoPorId(id_CitaQuirofano);
            
            ReporteC reporteParcial = new ReporteC("Rpt_CorteDetalleCuenta_1");
            System.out.println("{ID FOLIO: " + folio.getId() + " ID PAQUETE: " + folio.getId_paquete() + " ID TIPO HABITACION: " + folio.getId_habitacion() + " QUIROFANO: " + idquiro + " NUMERO HABITACION: " + folio.getNumero_habitacion() + "}");
            reporteParcial.generarCorteDetalles1CorteParcial(folio.getId(), folio.getId_paquete(), numeroALetra, idquiro, folio.getId_habitacion(), folio.getNumero_habitacion());
        } catch (SQLException ex) {
            Logger.getLogger(CuentaPaciente2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionBuscarr(ActionEvent event) {
    }
    
}
