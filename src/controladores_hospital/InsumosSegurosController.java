/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.AseguradorasCostos;
import clase.Conexion;
import clases_hospital.Aseguradora;
import clases_hospital_DAO.AseguradorasCostosDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class InsumosSegurosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<AseguradorasCostos> insumosaseguradorascostos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    AseguradorasCostosDAO aeguradoracostosDAO;
    AseguradoraDAO aseguradoraDAO;

    Aseguradora aseguradora;

    @FXML
    private TextField txfBuscar;
    @FXML
    private ComboBox<Aseguradora> cmbAseguradora;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAgregarInventario;
    @FXML
    private TableView<AseguradorasCostos> tabla;
    @FXML
    private TableColumn<?, ?> nombreInventario;
    @FXML
    private TableColumn<AseguradorasCostos, String> colCostos;
    @FXML
    private TableColumn<AseguradorasCostos, Double> colCostoUnitario;
    @FXML
    private TableColumn<?, ?> colPrecioVenta;
    @FXML
    private TableColumn<?, ?> colPrecioUnitario;
    @FXML
    private TableColumn<AseguradorasCostos, String> colInformacion;
    @FXML
    private TableColumn<?, ?> colPorDescuento;
    @FXML
    private TableColumn<?, ?> colTipo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla(1);
            llenarCmb();
            txfBuscar.setOnKeyReleased(e -> filtrarLista(txfBuscar.getText()));
        } catch (SQLException ex) {
            Logger.getLogger(InsumosSegurosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionEditar(ActionEvent event) {
    }

    
    

    private void llenarTabla(int id_aseguradora) throws SQLException {
        con = conexion.conectar2();
        insumosaseguradorascostos.clear();
        tabla.getItems().clear();

        aeguradoracostosDAO = new AseguradorasCostosDAO(con);

        insumosaseguradorascostos.addAll(aeguradoracostosDAO.getByAseguradoraId(id_aseguradora));

        nombreInventario.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
        colCostos.setCellValueFactory(new PropertyValueFactory("formatoDineroCostoAseguradorasCostos"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory("formatoDineroCostoUnitarioAseguradorasCostos"));
        colPorDescuento.setCellValueFactory(new PropertyValueFactory("formatoPorcentajeDescuento"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory("formatoDineroPrecioVentaAseguradorasCosto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("formatoDineroPrecioVentaUnitarioAseguradorasCosto"));
        colTipo.setCellValueFactory(new PropertyValueFactory("nombreTipoInsumoMedico"));
        generarBotones();
        editarTabla();

        tabla.setItems(insumosaseguradorascostos);
    }

    private void editarTabla() {
        colCostos.setCellFactory(TextFieldTableCell.forTableColumn());
        colCostos.setOnEditCommit(event -> {
            AseguradorasCostos aseguradoraCOS = event.getRowValue();
            if (esNumerico("" + event.getNewValue())) {
                aseguradoraCOS.setCostoAseguradorasCostos(Double.parseDouble(event.getNewValue()));
                aseguradoraCOS = editarDatosTabla(aseguradoraCOS);
                actualizarAesguradoraCostos(aseguradoraCOS);
            } else if (esDecimal("" + event.getNewValue())) {
                aseguradoraCOS.setCostoAseguradorasCostos(Double.parseDouble(event.getNewValue()));
                aseguradoraCOS = editarDatosTabla(aseguradoraCOS);
                actualizarAesguradoraCostos(aseguradoraCOS);
            } else {
                alertaError.setTitle("ERROR");
                alertaError.setHeaderText("INGRESE UN NUEMERO VALIDO");
                alertaError.showAndWait();
            }
            tabla.refresh();
        });
        colCostos.setEditable(true);
        tabla.setEditable(true);
    }

    private void llenarCmb() {
        con = conexion.conectar2();
        aseguradoraDAO = new AseguradoraDAO(con);
        cmbAseguradora.getItems().clear();
        loadAseguradoras();

        cmbAseguradora.setOnAction(event -> {
            Aseguradora selectedAseguradora = cmbAseguradora.getSelectionModel().getSelectedItem();
            if (selectedAseguradora != null) {
                handleAseguradoraSelection(selectedAseguradora);
            }
        });
    }

    private void loadAseguradoras() {
        try {
            List<Aseguradora> aseguradorasList = aseguradoraDAO.getAllAseguradoras();
            ObservableList<Aseguradora> observableAseguradorasList = FXCollections.observableArrayList(aseguradorasList);
            cmbAseguradora.setItems(observableAseguradorasList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleAseguradoraSelection(Aseguradora aseguradora) {
        try {
            this.aseguradora = aseguradora;
            System.out.println("Aseguradora seleccionada: " + aseguradora.getNombreAseguradora());
            llenarTabla(aseguradora.getIdAseguradora());
        } catch (SQLException ex) {
            Logger.getLogger(InsumosSegurosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean esNumerico(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean esDecimal(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private AseguradorasCostos editarDatosTabla(AseguradorasCostos aseguradorasCostos) {
        AseguradorasCostos asecos = aseguradorasCostos;

        asecos.setCostoUnitarioAseguradorasCostos(asecos.getCostoAseguradorasCostos() / asecos.getCantidadInsumosAseguradorasCostos());
        asecos.setPrecioVentaAseguradorasCosto(asecos.getCostoAseguradorasCostos() - ((asecos.getDescuento() / 100) * asecos.getCostoAseguradorasCostos()));
        asecos.setPrecioVentaUnitarioAseguradorasCosto(asecos.getPrecioVentaAseguradorasCosto() / asecos.getCantidadInsumosAseguradorasCostos());

        return asecos;
    }

    private void actualizarAesguradoraCostos(AseguradorasCostos asecos) {
        try {
            con = conexion.conectar2();
            aeguradoracostosDAO = new AseguradorasCostosDAO(con);
            asecos.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            aeguradoracostosDAO.actualizarCostosAseguradoraInsumos(asecos);
        } catch (SQLException ex) {
            Logger.getLogger(InsumosSegurosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarBotones() {
        Callback<TableColumn<AseguradorasCostos, String>, TableCell<AseguradorasCostos, String>> entrada = (TableColumn<AseguradorasCostos, String> param) -> {
            final TableCell<AseguradorasCostos, String> cell = new TableCell<AseguradorasCostos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnInformacion = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        AseguradorasCostos asecos = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnInformacion.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿ESTA SEGURO DE MODIFICAR INFORMACION PARA: " + asecos.getNombreInsumo() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {

                                    cuadroDeTexto(asecos);
                                    actualizarAesguradoraCostos(asecos);

                                    alertaConfirmacion.setHeaderText(null);
                                    alertaConfirmacion.setTitle("Confirmación");
                                    alertaConfirmacion.setContentText("Se ha confirmar el cambio de valor para: " + asecos.getNombreInsumo());
                                    alertaConfirmacion.showAndWait();
                                    llenarTabla(asecos.getIdAseguradorap());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        });
                        setGraphic(btnInformacion);
                        setText(null);
                        btnInformacion.setGraphic(entrada);
                    }
                }
            };
            return cell;
        };

        colInformacion.setCellFactory(entrada);
    }

    private void cuadroDeTexto(AseguradorasCostos asecos) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Entrada de datos");
        dialog.setHeaderText("Por favor, ingrese un valor");
        dialog.setContentText("Valor actual: " + asecos.getCantidadInsumosAseguradorasCostos());

        // Mostrar el cuadro de diálogo y esperar la respuesta del usuario
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> {
            System.out.println("Valor ingresado: " + value);
            if (esNumerico("" + value)) {
                asecos.setCantidadInsumosAseguradorasCostos(Double.parseDouble(value));
                editarDatosTabla(asecos);
            } else if (esDecimal("" + value)) {
                asecos.setCantidadInsumosAseguradorasCostos(Double.parseDouble(value));
                editarDatosTabla(asecos);
            } else {
                alertaError.setTitle("ERROR");
                alertaError.setHeaderText("INGRESE UN NUEMERO VALIDO");
                alertaError.showAndWait();
            }
        });
    }
    
    private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(insumosaseguradorascostos);
        } else {
            ObservableList<AseguradorasCostos> listaFiltrada = FXCollections.observableArrayList();
            insumosaseguradorascostos.stream().filter((aseco) -> (aseco.getNombreInsumo().toLowerCase().contains(filtro.toLowerCase()))).forEachOrdered((aseco) -> {
                listaFiltrada.add(aseco);
            });
            tabla.setItems(listaFiltrada);
        }
    }

}
