/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class AseguradorasController implements Initializable {

    /*ESTABLECER CONEXIONES*/
    Conexion conexion = new Conexion();
    Connection con;

    /*CONEXIONES A BASE DE DATOS*/
    AseguradoraDAO aseguradoraDAO;

    /*MODELOS*/
    Aseguradora aseguradoramodel;

    /*LISTA DE MODELOS*/
    ObservableList<Aseguradora> CatalogoAseguradorasOB = FXCollections.observableArrayList();

    /*MENSAJES DE ALERTA*/
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaWarning = new Alert(Alert.AlertType.WARNING);

    @FXML
    private AnchorPane apTabla;
    @FXML
    private TableView<Aseguradora> tabla;
    @FXML
    private TableColumn<?, ?> colAseguradora;
    @FXML
    private TableColumn<?, ?> colFactor;
    @FXML
    private TableColumn<Aseguradora, String>colMaterialCuracion;
    @FXML
    private TableColumn<Aseguradora, String> colMedicamento;
    @FXML
    private TableColumn<Aseguradora, String> colEquipoMedico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = conexion.conectar2();
        llenartabla();
    }
    @FXML
    private void accionAgregarCriterios(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarAseguradoras.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("INSUMO NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        
    }

    private void llenartabla() {
        aseguradoraDAO = new AseguradoraDAO(con);

        CatalogoAseguradorasOB.addAll(aseguradoraDAO.traerCatalogoAseguradoras());

        colAseguradora.setCellValueFactory(new PropertyValueFactory("nombreAseguradora"));
        colFactor.setCellValueFactory(new PropertyValueFactory("factorString"));
        colEquipoMedico.setCellValueFactory(new PropertyValueFactory<>("equipoString"));
        colEquipoMedico.setCellFactory(new Callback<TableColumn<Aseguradora, String>, TableCell<Aseguradora, String>>() {
            @Override
            public TableCell<Aseguradora, String> call(TableColumn<Aseguradora, String> column) {
                return new TableCell<Aseguradora, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);

                            Aseguradora current = getTableView().getItems().get(getIndex());
                            String validacion = current.getEquipoString();
                            //int estatus = current.getEstatusIndica();

                            String style = "-fx-border-color: white; -fx-border-width: 0.5;";
                            if ("INCLUIDO".equals(validacion)) {
                                setStyle(style + "-fx-background-color: green;");
                            } else if ("NO INCLUIDO".equals(validacion)) {
                                setStyle(style + "-fx-background-color: orange;");
                            } else {
                                setStyle(style);
                            }
                        }
                    }
                };
            }
        });
        
        colMedicamento.setCellValueFactory(new PropertyValueFactory<>("MedicamentoString"));
        colMedicamento.setCellFactory(new Callback<TableColumn<Aseguradora, String>, TableCell<Aseguradora, String>>() {
            @Override
            public TableCell<Aseguradora, String> call(TableColumn<Aseguradora, String> column) {
                return new TableCell<Aseguradora, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);

                            Aseguradora current = getTableView().getItems().get(getIndex());
                            String validacion = current.getMedicamentoString();
                            //int estatus = current.getEstatusIndica();

                            String style = "-fx-border-color: white; -fx-border-width: 0.5;";
                            if ("INCLUIDO".equals(validacion)) {
                                setStyle(style + "-fx-background-color: green;");
                            } else if ("NO INCLUIDO".equals(validacion)) {
                                setStyle(style + "-fx-background-color: orange;");
                            } else {
                                setStyle(style);
                            }
                        }
                    }
                };
            }
        });
        colMaterialCuracion.setCellValueFactory(new PropertyValueFactory<>("matterialString"));
        colMaterialCuracion.setCellFactory(new Callback<TableColumn<Aseguradora, String>, TableCell<Aseguradora, String>>() {
            @Override
            public TableCell<Aseguradora, String> call(TableColumn<Aseguradora, String> column) {
                return new TableCell<Aseguradora, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);

                            Aseguradora current = getTableView().getItems().get(getIndex());
                            String validacion = current.getMatterialString();
                            //int estatus = current.getEstatusIndica();

                            String style = "-fx-border-color: white; -fx-border-width: 0.5;";
                            if ("INCLUIDO".equals(validacion)) {
                                setStyle(style + "-fx-background-color: green;");
                            } else if ("NO INCLUIDO".equals(validacion)) {
                                setStyle(style + "-fx-background-color: orange;");
                            } else {
                                setStyle(style);
                            }
                        }
                    }
                };
            }
        });
        tabla.setItems(CatalogoAseguradorasOB);
        
        

    }

    private void checarconexion() {
        try {
            if (con != null && !con.isClosed()) {
                System.out.println("La conexión ya está establecida.");

            } else {
                System.out.println("La conexión no está establecida.");
                con = conexion.conectar2(); // Llama a conectar2 si no está conectada

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void pintarceldas() {
        
    }

}
