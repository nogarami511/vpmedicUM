/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.FamiliaInsumos;
import clases_hospital_DAO.FamiliasInsumosDAO;
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
import vistasAuxiliares_hospital.AgregarNuevaFamiliaController;
import vistasAuxiliares_hospital.InsumosFamiliaController;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class FamiliasInventarioController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    ObservableList<FamiliaInsumos> familiasinsumos = FXCollections.observableArrayList();

    FamiliasInsumosDAO familiasInsumosDAO;

    @FXML
    private TableView<FamiliaInsumos> tabla;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn<FamiliaInsumos, String> colVerInsumos;
    @FXML
    private TableColumn<FamiliaInsumos, String> colAgregar;
    @FXML
    private TableColumn<FamiliaInsumos, String> colEditar;
    @FXML
    private Button btnCrearFamilia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
    }

    private void llenarTabla() {
        try {
            
            con = conexion.conectar2();
            familiasInsumosDAO = new FamiliasInsumosDAO(con);
            familiasinsumos.clear();

            familiasinsumos.addAll(familiasInsumosDAO.obtenerTodasFamiliasInsumos());

            colNombre.setCellValueFactory(new PropertyValueFactory("nombreFamiliaInsumo"));
            generarBotones();

            tabla.setItems(familiasinsumos);

        } catch (SQLException ex) {
            Logger.getLogger(FamiliasInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionCrearFamilia(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarNuevaFamilia.fxml"));
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
        llenarTabla();
    }

    private void generarBotones() {
        //generarColumnaComplemento(foliodao);
        Callback<TableColumn<FamiliaInsumos, String>, TableCell<FamiliaInsumos, String>> ver = (TableColumn<FamiliaInsumos, String> param) -> {
            final TableCell<FamiliaInsumos, String> cell = new TableCell<FamiliaInsumos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVerInsumos = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        FamiliaInsumos familiaInsumo = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnVerInsumos.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Vizualisar : " + familiaInsumo.getNombreFamiliaInsumo() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    verInsumos(familiaInsumo.getIdFamiliaInsumo());
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnVerInsumos);
                        setText(null);
                        btnVerInsumos.setGraphic(entrada);
                    }
                }
            };
            return cell;
        };

        colVerInsumos.setCellFactory(ver);

        //generarColumnaComplemento(foliodao);
        Callback<TableColumn<FamiliaInsumos, String>, TableCell<FamiliaInsumos, String>> ingresar = (TableColumn<FamiliaInsumos, String> param) -> {
            final TableCell<FamiliaInsumos, String> cell = new TableCell<FamiliaInsumos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnAgregar = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        FamiliaInsumos familiaInsumo = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnAgregar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Agregar insumos a : " + familiaInsumo.getNombreFamiliaInsumo() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    agregarInsumos(familiaInsumo.getIdFamiliaInsumo());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnAgregar);
                        setText(null);
                        btnAgregar.setGraphic(entrada);
                        if (familiaInsumo.getIdFamiliaInsumo() == 1) {
                            btnAgregar.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colAgregar.setCellFactory(ingresar);

        Callback<TableColumn<FamiliaInsumos, String>, TableCell<FamiliaInsumos, String>> editar = (TableColumn<FamiliaInsumos, String> param) -> {
            final TableCell<FamiliaInsumos, String> cell = new TableCell<FamiliaInsumos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnAgregar = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        FamiliaInsumos familiaInsumo = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnAgregar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Editar familia : " + familiaInsumo.getNombreFamiliaInsumo() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    editarFamila(familiaInsumo);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                               
                            }
                        });
                        setGraphic(btnAgregar);
                        setText(null);
                        btnAgregar.setGraphic(entrada);
                        if (familiaInsumo.getIdFamiliaInsumo() == 1) {
                            btnAgregar.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colEditar.setCellFactory(editar);
    }

    private void verInsumos(int idFamilia) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/InsumosFamilia.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        InsumosFamiliaController agregarInsumo = fxml.getController();

        agregarInsumo.setObejtos(idFamilia);
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

    private void agregarInsumos(int idFamilia) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/InsumosFamilia.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        InsumosFamiliaController verInsumo = fxml.getController();

        verInsumo.setIdfamilia(idFamilia);
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

    private void editarFamila(FamiliaInsumos familiaInsumo) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarNuevaFamilia.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        
        AgregarNuevaFamiliaController editarFamilia = fxml.getController();
        editarFamilia.setObjeto(familiaInsumo);
        
        Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.setTitle("PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

}
