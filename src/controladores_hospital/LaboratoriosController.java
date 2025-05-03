/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Laboratorio;
import clases_hospital_DAO.LaboratoriosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vistasAuxiliares_hospital.EstudiosLaboratoriosController;
import vistasAuxiliares_hospital.MovimientoLaboratoriosMedicosController;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class LaboratoriosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Laboratorio> laboratorios = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    LaboratoriosDAO laboratoriodao;

    @FXML
    private Button btnAgregarLab;
    @FXML
    private TableView<Laboratorio> tabla;
    @FXML
    private TableColumn<?, ?> colLaboratorio;
    @FXML
    private TableColumn colAgregar;
    @FXML
    private TableColumn colVisualizar;
    @FXML
    private Button btnEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(LaboratoriosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionAgregarLab(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/MovimientoLaboratoriosMedicos.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.setTitle("PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void accionEditar(ActionEvent event) throws IOException {
        Laboratorio lab = this.tabla.getSelectionModel().getSelectedItem();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/MovimientoLaboratoriosMedicos.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        
        MovimientoLaboratoriosMedicosController mlc = fxml.getController();
        mlc.recibirDatos(lab.getIdLaboratorio());

        Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.setTitle("PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void llenarTabla() throws SQLException {
        tabla.getItems().clear();
        laboratorios.clear();
        laboratoriodao = new LaboratoriosDAO(conexion.conectar2());
        laboratorios.addAll(laboratoriodao.obtenerTodosLosLaboratoriosSoloIdyNombre());

        colLaboratorio.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
        genraraBotones();
        centrarBotones();

        tabla.setItems(laboratorios);
    }

    private void genraraBotones() {
        Callback<TableColumn<Laboratorio, String>, TableCell<Laboratorio, String>> agregar = (TableColumn<Laboratorio, String> param) -> {
            final TableCell<Laboratorio, String> cell = new TableCell<Laboratorio, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        Laboratorio laboratorioSeleccionado = getTableView().getItems().get(getIndex());
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
                                alertaConfirmacion.setContentText("¿Estas seguro de agregar estudios a: " + laboratorioSeleccionado.getNombreComercial() + "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    agregarEstudiosAlLaboratorio(laboratorioSeleccionado);
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

        Callback<TableColumn<Laboratorio, String>, TableCell<Laboratorio, String>> vizualizar = (TableColumn<Laboratorio, String> param) -> {
            final TableCell<Laboratorio, String> cell = new TableCell<Laboratorio, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        Laboratorio laboratorioSeleccionado = getTableView().getItems().get(getIndex());
                        ImageView imgVizualizar = new ImageView("/img/icons/icons8-ver-archivo-48.png");
                        imgVizualizar.setFitHeight(20);
                        imgVizualizar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Ver estudios disponibles para: " + laboratorioSeleccionado.getNombreComercial() + "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    vizualizarEstudiosLaboratorio(laboratorioSeleccionado);
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                             
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(imgVizualizar);
                    }
                }
            };
            return cell;
        };

        colVisualizar.setCellFactory(vizualizar);
    }

    private void agregarEstudiosAlLaboratorio(Laboratorio laboratorioSeleccionado) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/EstudiosLaboratorios.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);

            EstudiosLaboratoriosController elc = fxml.getController();
            elc.agregarEstudios(laboratorioSeleccionado);
            Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);
            stage.setTitle("PACIENTE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(EstudiosMedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void vizualizarEstudiosLaboratorio(Laboratorio laboratorioSeleccionado) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/EstudiosLaboratorios.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);

            EstudiosLaboratoriosController elc = fxml.getController();
            elc.visualizarEstudios(laboratorioSeleccionado);
            Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);
            stage.setTitle("PACIENTE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(EstudiosMedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void centrarBotones() {
        colAgregar.setStyle("-fx-alignment: CENTER;");
        colVisualizar.setStyle("-fx-alignment: CENTER;");
    }

}
