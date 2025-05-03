/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.ComprasInternas;
import clases_hospital.ReabastoPadre;
import clases_hospital_DAO.ComprasInternasDAO;
import clases_hospital_DAO.ReabastoPadreDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vistasAuxiliares_hospital.CXPCompraInternaVisualizarPagosController;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CientasxPagarExternasController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<ReabastoPadre> reabastospadres = FXCollections.observableArrayList();

    Conexion conexion = new Conexion();

    ReabastoPadreDAO reabastopadredao;

    ToggleGroup togglegroup = new ToggleGroup();

    @FXML
    private TextField txfBuscarRazonSocial;
    @FXML
    private Button btnBuscar;
    @FXML
    private RadioButton rdbPendientePago;
    @FXML
    private RadioButton rdbLiquidada;
    @FXML
    private TableView<ReabastoPadre> tabla;
    @FXML
    private TableColumn colPagar;
    @FXML
    private TableColumn<?, ?> colProveedor;
    @FXML
    private TableColumn<?, ?> colMontoCompra;
    @FXML
    private TableColumn<?, ?> colImporteAbonos;
    @FXML
    private TableColumn<?, ?> colSaldo;
    @FXML
    private TableColumn<?, ?> colEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        crearToggleGrup();
    }

    @FXML
    private void accionBuscar(ActionEvent event) throws SQLException {
        llenarTablaLiquidados();
    }

    @FXML
    private void accionPendientePago(ActionEvent event) throws SQLException {
        llenarTablaPorPagar();
    }

    @FXML
    private void accionLiquidada(ActionEvent event) throws SQLException {
        llenarTablaLiquidados();
    }

    private void crearToggleGrup() {
        rdbPendientePago.setToggleGroup(togglegroup);
        rdbLiquidada.setToggleGroup(togglegroup);
        togglegroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // No hay botones seleccionados, puedes manejarlo aquí si es necesario
            } else {
                // Desseleccionamos los botones que no estén seleccionados
                if (oldValue != null && !oldValue.equals(newValue)) {
                    oldValue.setSelected(false);
                }
            }
        });
    }

    private void llenarTablaPorPagar() throws SQLException {
        reabastospadres.clear();
        tabla.getItems().clear();
        reabastopadredao = new ReabastoPadreDAO(conexion.conectar2());
        reabastospadres.addAll(reabastopadredao.getReabastoPorPagar());

        colProveedor.setCellValueFactory(new PropertyValueFactory("nombre"));
        colMontoCompra.setCellValueFactory(new PropertyValueFactory("costoTotalInicial"));
        colImporteAbonos.setCellValueFactory(new PropertyValueFactory("monto_pagado"));
        colSaldo.setCellValueFactory(new PropertyValueFactory("saldo_saldar"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus_pago"));
        generarBotonPagos();

        tabla.setItems(reabastospadres);
    }

    private void llenarTablaLiquidados() throws SQLException {
        reabastospadres.clear();
        tabla.getItems().clear();
        reabastopadredao = new ReabastoPadreDAO(conexion.conectar2());
        reabastospadres.addAll(reabastopadredao.getReabastoLiquidado());

        colProveedor.setCellValueFactory(new PropertyValueFactory("nombre"));
        colMontoCompra.setCellValueFactory(new PropertyValueFactory("costoTotalInicial"));
        colImporteAbonos.setCellValueFactory(new PropertyValueFactory("monto_pagado"));
        colSaldo.setCellValueFactory(new PropertyValueFactory("saldo_saldar"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus_pago"));
        generarBotonPagos();

        tabla.setItems(reabastospadres);
    }

    private void generarBotonPagos() {
        Callback<TableColumn<ReabastoPadre, String>, TableCell<ReabastoPadre, String>> guardar = (TableColumn<ReabastoPadre, String> param) -> {
            final TableCell<ReabastoPadre, String> cell = new TableCell<ReabastoPadre, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        ReabastoPadre reabastopadre = getTableView().getItems().get(getIndex());
                        ImageView pagar = new ImageView("/img/icons/icons8-paga-48.png");
                        pagar.setFitHeight(20);
                        pagar.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Visualizar orden compra " + reabastopadre.getNombre()+ "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    llamarVistaCXPCompraInternaVisualizarPagos(reabastopadre);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(pagar);
                    }
                }
            };
            return cell;
        };

        colPagar.setCellFactory(guardar);
    }

    private void llamarVistaCXPCompraInternaVisualizarPagos(ReabastoPadre comprasInternas) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CXPCompraInternaVisualizarPagos.fxml"));
//        Parent root = loader.load();
//
//        CXPCompraInternaVisualizarPagosController cxpcpc = loader.getController();
//        cxpcpc.recibirDatosPago(comprasInternas);
//
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setTitle("CUENTA DEL PACIENTE");
//        stage.getIcons().add(new Image("/img/icono.png"));
//        stage.setScene(scene);
//        stage.showAndWait();
    }

}
