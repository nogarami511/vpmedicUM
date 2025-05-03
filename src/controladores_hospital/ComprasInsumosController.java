/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.GenerarReabastoInsumo;
import clases_hospital.MovimientoInventarioP;
import clases_hospital.ReabastoPadre;
import clases_hospital_DAO.GenerarReabastoInsumoDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import clases_hospital_DAO.ReabastoPadreDAO;
import java.io.IOException;
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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.Reporte;
import reportes.ReporteC;
import vistasAuxiliares_hospital.VistaRecibirCompraController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ComprasInsumosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<ReabastoPadre> reabastospadpadres = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    private int id_reabastoP;

    ReabastoPadreDAO reabastopadredao;
    GenerarReabastoInsumoDAO generarreabastoinsumodao;

    MovimientoPadreDAO movimientopadredao;

    @FXML
    private Button btnComprar;
    @FXML
    private TextField txfBuscar;
    @FXML
    private Button colBuscar;
    @FXML
    private TableView<ReabastoPadre> tabla;
    @FXML
    private TableColumn<?, ?> colFolo;
    @FXML
    private TableColumn<?, ?> colCostoInical;
    @FXML
    private TableColumn<?, ?> colCostoFinal;
    @FXML
    private TableColumn<?, ?> colFechaPedido;
    @FXML
    private TableColumn<?, ?> colAcciones;
    @FXML
    private TableColumn colRecibir;
    @FXML
    private TableColumn colImprimir;
    @FXML
    private TableColumn colCancelar1;
    @FXML
    private TableColumn<?, ?> colEstatus;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnComprar.setVisible(false);
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ComprasInsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        llenarBuscador();
    }

    @FXML
    private void accionBuscar(ActionEvent event) throws SQLException {
        if (id_reabastoP > 0) {
            llenarTablaIdReabastoPadre();
        }
    }

    private void llenarTabla() throws SQLException {
        reabastospadpadres.clear();
        tabla.getItems().clear();

        reabastopadredao = new ReabastoPadreDAO(conexion.conectar2());
        reabastospadpadres.addAll(reabastopadredao.getPedidos());

        colFolo.setCellValueFactory(new PropertyValueFactory("folioReabasto"));
        colCostoInical.setCellValueFactory(new PropertyValueFactory("costoTotalInicial"));
        colCostoFinal.setCellValueFactory(new PropertyValueFactory("costoTotalFinal"));
        colFechaPedido.setCellValueFactory(new PropertyValueFactory("fechaGenerado"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("nombre"));

        generarBotones();

        tabla.setItems(reabastospadpadres);
    }

    private void llenarTablaIdReabastoPadre() throws SQLException {
        reabastospadpadres.clear();
        tabla.getItems().clear();

        reabastopadredao = new ReabastoPadreDAO(conexion.conectar2());
        reabastospadpadres.addAll(reabastopadredao.getById(id_reabastoP));

        colFolo.setCellValueFactory(new PropertyValueFactory("folioReabasto"));
        colCostoInical.setCellValueFactory(new PropertyValueFactory("costoTotalInicial"));
        colCostoFinal.setCellValueFactory(new PropertyValueFactory("costoTotalFinal"));
        colFechaPedido.setCellValueFactory(new PropertyValueFactory("fechaGenerado"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("nombre"));

        generarBotones();

        tabla.setItems(reabastospadpadres);
        txfBuscar.setText("");
        id_reabastoP = 0;
    }

    private void generarBotones() {
        Callback<TableColumn<ReabastoPadre, String>, TableCell<ReabastoPadre, String>> recibir = (TableColumn<ReabastoPadre, String> param) -> {
            final TableCell<ReabastoPadre, String> cell = new TableCell<ReabastoPadre, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnCofirmar = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        ReabastoPadre rp = getTableView().getItems().get(getIndex());
                        ImageView imgRecibir = new ImageView("/img/icons/icons8-insertar-50.png");
                        imgRecibir.setFitHeight(20);
                        imgRecibir.setFitWidth(20);

                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnCofirmar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Esta recibiendo la orden compra:  " + rp.getFolioReabasto());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    vistaRecibir(rp);
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnCofirmar);
                        setText(null);
                        btnCofirmar.setGraphic(imgRecibir);
                        if (rp.getEstatu_reabasto() == 0 || rp.getEstatu_reabasto() > 3) {
                            btnCofirmar.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        Callback<TableColumn<ReabastoPadre, String>, TableCell<ReabastoPadre, String>> imprimir = (TableColumn<ReabastoPadre, String> param) -> {
            final TableCell<ReabastoPadre, String> cell = new TableCell<ReabastoPadre, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnCofirmar = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        ReabastoPadre rp = getTableView().getItems().get(getIndex());
                        ImageView imprimirImg = new ImageView("/img/icons/icons8-imprimir-30.png");
                        imprimirImg.setFitHeight(20);
                        imprimirImg.setFitWidth(20);

                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnCofirmar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Imprimir orden compra:  " + rp.getFolioReabasto());
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    if (rp.getEstatu_reabasto()== 1 || rp.getEstatu_reabasto() == 2 || rp.getEstatu_reabasto() == 3) {
                                        imprimirPedido(rp);
                                    } else {
                                        imprimir(rp);
                                    }
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                               
                            }
                        });
                        setGraphic(btnCofirmar);
                        setText(null);
                        btnCofirmar.setGraphic(imprimirImg);
                        if (rp.getEstatu_reabasto() == 0) {
                            btnCofirmar.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        Callback<TableColumn<ReabastoPadre, String>, TableCell<ReabastoPadre, String>> cancelar = (TableColumn<ReabastoPadre, String> param) -> {
            final TableCell<ReabastoPadre, String> cell = new TableCell<ReabastoPadre, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnCofirmar = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        ReabastoPadre rp = getTableView().getItems().get(getIndex());
                        ImageView cancelarImg = new ImageView("/img/icons/icons8-cancelar-2-50.png");
                        cancelarImg.setFitHeight(20);
                        cancelarImg.setFitWidth(20);

                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnCofirmar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Esta seguro de cancelar orden compra:  " + rp.getFolioReabasto() + "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    cancelarCompra(rp);
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            
                            }
                        });
                        setGraphic(btnCofirmar);
                        setText(null);
                        btnCofirmar.setGraphic(cancelarImg);

                        if (rp.getEstatu_reabasto() == 0 || rp.getEstatu_reabasto() > 3) {
                            btnCofirmar.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colRecibir.setCellFactory(recibir);
        colImprimir.setCellFactory(imprimir);
        colCancelar1.setCellFactory(cancelar);

        colRecibir.setStyle("-fx-alignment: CENTER;");
        colImprimir.setStyle("-fx-alignment: CENTER;");
        colCancelar1.setStyle("-fx-alignment: CENTER;");
    }

    private void vistaRecibir(ReabastoPadre rp) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VistaRecibirCompra.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        VistaRecibirCompraController vrcc = fxml.getController();
        vrcc.recibirDatos(rp);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    private void imprimir(ReabastoPadre rp) {
        movimientopadredao = new MovimientoPadreDAO(conexion.conectar2());
        try {
            MovimientoInventarioP movimientoinventariop = movimientopadredao.obtenerMovimientoInventarioPPorFolio(rp.getFolioReabasto());
            Reporte reporte = new Reporte("ReporteEntradas4");
            reporte.generarReporte(movimientoinventariop.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ComprasInsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void imprimirPedido(ReabastoPadre rp) {
        ReporteC reporte = new ReporteC("ReporteGenerarCompra");
        reporte.generarReporteImpresionPedido(rp.getIdRabastosPadre(), rp.getIdProveedor());
    }

    private void cancelarCompra(ReabastoPadre rp) throws SQLException {
        con = conexion.conectar2();
        reabastopadredao = new ReabastoPadreDAO(con);

        rp.setEstatu_reabasto(0);
        rp.setUsuarioModificacion(VPMedicaPlaza.userSystem);

        reabastopadredao.update(rp);

        generarreabastoinsumodao = new GenerarReabastoInsumoDAO(con);

        List<GenerarReabastoInsumo> gris = generarreabastoinsumodao.getAllNameAndProvPorIdReabastoPadre(rp.getIdRabastosPadre());
        for (GenerarReabastoInsumo gri : gris) {
            gri.setIdEstatusReabasto(0);
            gri.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            generarreabastoinsumodao.update(gri);
            gri.setIdRabastosPadre(1);
            gri.setIdEstatusReabasto(1);
            gri.setId_proveedor(1);
            gri.setUsuarioCreacion(VPMedicaPlaza.userSystem);
            generarreabastoinsumodao.create(gri);
        }
    }

    private void llenarBuscador() {
        try {
            reabastopadredao = new ReabastoPadreDAO(conexion.conectar2());
            AutoCompletionBinding<ReabastoPadre> insumobuscar = TextFields.bindAutoCompletion(txfBuscar, reabastopadredao.getAll());
            insumobuscar.setOnAutoCompleted(e -> {
                ReabastoPadre reabastopSelect = e.getCompletion();
                id_reabastoP = reabastopSelect.getIdRabastosPadre();
            });
        } catch (SQLException ex) {
            Logger.getLogger(ComprasInsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void comprar(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ComprasInternas.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
//        FXMLLoader fxml = new FXMLLoader(getClass().getResource("vistasAuxiliares_hospital/ComprasInternasController.fxml"));
//        Parent root = fxml.load();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.showAndWait();
//        vistasAuxiliares_hospital.ComprasInternasController
    }

}
