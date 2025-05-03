/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital_DAO.EstudiosMedicosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vistasAuxiliares_hospital.MovimientoEstudiosMedicosController;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class EstudiosMedicosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Paciente> estudiosMedicos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    EstudiosMedicosDAO estudiomedicodao;

    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<?, ?> colPaciente;
    @FXML
    private TableColumn colAgregar;
    @FXML
    private TableColumn colCancelar;
    @FXML
    private TextField txfBuscarPaciente;
    @FXML
    private TableColumn colVer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
             txfBuscarPaciente.setOnKeyReleased(e -> filtrarLista(txfBuscarPaciente.getText()));
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(EstudiosMedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        estudiosMedicos.clear();
        tabla.getItems().clear();
        estudiomedicodao = new EstudiosMedicosDAO(conexion.conectar2());
        estudiosMedicos.addAll(estudiomedicodao.obtenerPacienteConDatosNecesarios());

        colFolio.setCellValueFactory(new PropertyValueFactory("folio"));
        colPaciente.setCellValueFactory(new PropertyValueFactory("nombre"));

        generarBotones();

        tabla.setItems(estudiosMedicos);
    }

    private void generarBotones() {
                Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> ver = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgAgregar = new ImageView("/img/icons/icons8-ver-archivo-48.png");
                        imgAgregar.setFitHeight(20);
                        imgAgregar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de ver estudios a: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    vistaAuxiliarEstudios(paciente, false);
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                              
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(imgAgregar);
                    }
                }
            };
            return cell;
        };

        colVer.setCellFactory(ver);
        
        
        
        
        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> agregar = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgAgregar = new ImageView("/img/icons/icons8-insertar-mesa-50.png");
                        imgAgregar.setFitHeight(20);
                        imgAgregar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de ingresar estudios a: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    vistaAuxiliarEstudios(paciente, true);
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                              
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(imgAgregar);
                    }
                }
            };
            return cell;
        };

        colAgregar.setCellFactory(agregar);
        


        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> cancelar = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgEliminar = new ImageView("/img/icons/icons8-eliminar-30.png");
                        imgEliminar.setFitHeight(20);
                        imgEliminar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de confirmar el ingrso de: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    vistaAuxiliarEstudios(paciente, false);
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println(e);
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(imgEliminar);
                    }
                }
            };
            return cell;
        };

        colCancelar.setCellFactory(cancelar);
    }

    private void vistaAuxiliarEstudios(Paciente paciente, boolean agregar) throws SQLException {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/MovimientoEstudiosMedicos.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);

            MovimientoEstudiosMedicosController mem = fxml.getController();
            mem.recibirDatos(paciente, agregar);
            Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);
            stage.setTitle("PACIENTE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(EstudiosMedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void visualizarConsumo(Paciente paciente, boolean agregar) throws SQLException {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/MovimientoEstudiosMedicos.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);

            MovimientoEstudiosMedicosController mem = fxml.getController();
            mem.recibirDatos(paciente, agregar);
            Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);
            stage.setTitle("PACIENTE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(EstudiosMedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(estudiosMedicos);
        } else {
            ObservableList<Paciente> listaFiltrada = FXCollections.observableArrayList();
            for (Paciente paciente : estudiosMedicos) {
                if (paciente.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(paciente);
                }
            }
            tabla.setItems(listaFiltrada);
        }
    }

}
