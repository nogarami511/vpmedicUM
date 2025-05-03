/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Indicasp;
import clases_hospital_DAO.IndicaspDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author olver
 */
public class IndicasPorHabitacionController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    Connection con;
    Conexion conexion = new Conexion();

    IndicaspDAO indicaspDAO;

    ObservableList<Indicasp> IndicasPacientesPorHabitacion = FXCollections.observableArrayList();
    @FXML
    private AnchorPane apTabla;
    @FXML
    private TableView<Indicasp> tabla;
    @FXML
    private TableColumn<?, ?> colPaciente;
    @FXML
    private TableColumn<?, ?> colHabitacion;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<Indicasp, String> colEstatus;
    @FXML
    private TableColumn<Indicasp, String> colValidacion;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colArea;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        revisarconexion();
        llenartabla();
    }

    private void llenartabla() {
        con = conexion.conectar2();
        indicaspDAO = new IndicaspDAO(con);

        IndicasPacientesPorHabitacion.addAll(indicaspDAO.TraerIndicasPaciente());

        colPaciente.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
        colHabitacion.setCellValueFactory(new PropertyValueFactory("numero_habitacion"));
        colArea.setCellValueFactory(new PropertyValueFactory("area"));
        colFolio.setCellValueFactory(new PropertyValueFactory("idIndicasp"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("string_Estatus_Indica"));
        colEstatus.setCellFactory(new Callback<TableColumn<Indicasp, String>, TableCell<Indicasp, String>>() {
            @Override
            public TableCell<Indicasp, String> call(TableColumn<Indicasp, String> column) {
                return new TableCell<Indicasp, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);

                            Indicasp current = getTableView().getItems().get(getIndex());
                            int validacion = current.getValidacion();
                            int estatus = current.getEstatusIndica();

                            String style = "-fx-border-color: white; -fx-border-width: 0.5;";
                            if (estatus == 0 && validacion == 0) {
                                setStyle(style + "-fx-background-color: orange;");
                            } else if (estatus > 0 && validacion == 0) {
                                setStyle(style + "-fx-background-color: yellow;");
                            } else {
                                setStyle(style);
                            }
                        }
                    }
                };
            }
        });

        colValidacion.setCellValueFactory(new PropertyValueFactory("validacionString"));

        colValidacion.setCellFactory(new Callback<TableColumn<Indicasp, String>, TableCell<Indicasp, String>>() {
            @Override
            public TableCell<Indicasp, String> call(TableColumn<Indicasp, String> column) {
                return new TableCell<Indicasp, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);

                            Indicasp current = getTableView().getItems().get(getIndex());
                            int validacion = current.getValidacion();
                            int estatus = current.getEstatusIndica();

                            String style = "-fx-border-color: white; -fx-border-width: 0.5;";
                            if (estatus == 0 && validacion == 0) {
                                setStyle(style + "-fx-background-color: orange;");
                            } else if (validacion == 0) {
                                setStyle(style + "-fx-background-color: red;");
                            } else if (validacion == 1) {
                                setStyle(style + "-fx-background-color: green;");
                            } else {
                                setStyle(style);
                            }
                        }
                    }
                };
            }
        });

        colFecha.setCellValueFactory(new PropertyValueFactory("fechaModificacion"));
        tabla.setItems(IndicasPacientesPorHabitacion);

    }

//    private void revisarconexion() {
//
//        try {
//            if (con == null || con.isClosed()) {
//                con = conexion.conectar2();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // O maneja la excepción de otra manera adecuada
//        }
//    }
}
