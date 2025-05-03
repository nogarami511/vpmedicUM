/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.*;
import clases_hospital_DAO.ArmadoPaqueteMedicoCostosDAO;
import clases_hospital_DAO.ArmadoPaqueteMedicoDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import org.apache.commons.codec.digest.DigestUtils;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class VerPaquetesMedicosController implements Initializable {

    private TextField nombrePaquete;
    private TextField idPaquete;

    /**
     * Initializes the controller class.
     */
    int idPquete;
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    ObservableList<ArmadoPaqueteconMedicoCostos> armadoPaqueteMedico = FXCollections.observableArrayList();

    List<ArmadoPaqueteconMedicoCostos> actualizar = new ArrayList<>();

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    ArmadoPaqueteMedicoCostosDAO armadoDAO;

    ArmadoPaqueteMedicoDAO armadoPaqueteMedicoDAO;

    @FXML
    private TableView<ArmadoPaqueteconMedicoCostos> tabla;
    @FXML
    private Button btnsalir;
    @FXML
    private TableColumn<?, ?> colnombreInsumo;
    @FXML
    private TableColumn<ArmadoPaqueteconMedicoCostos, Double> colcostoOriginal;
    @FXML
    private TableColumn<?, ?> colcostoNuevo;
    @FXML
    private TableColumn<?, ?> coldiferenciaCostos;
    @FXML
    private TableColumn<?, ?> colcantidad;
    @FXML
    private TableColumn<?, ?> colcostoSubtotal;
    @FXML
    private TableColumn<?, ?> colcostoSubtotalNvo;
    @FXML
    private TableColumn<?, ?> coldiferenciaCostosST;
    @FXML
    private TableColumn<?, ?> coldiferenciaCostosSTPorc;
    @FXML
    private TableColumn<ArmadoPaqueteconMedicoCostos, String> colFactorInsumo;
    @FXML
    private TableColumn<?, ?> colPrecioTotal;
    @FXML
    private TableColumn<?, ?> colprecioTotalNvo;
    @FXML
    private TableColumn<?, ?> coldiferenciTotal;
    @FXML
    private TableColumn<?, ?> coldiferenciTotalPorc;
    @FXML
    private TableColumn<?, ?> colgananciaOriginal;
    @FXML
    private TableColumn<?, ?> colgananciaNvo;
    @FXML
    private TableColumn<?, ?> coldiferenciaCostosPorc;
    @FXML
    private Button btnActualizarPaquete;
    @FXML
    private TableColumn<ArmadoPaqueteconMedicoCostos, String> colrbtactualizar;
    @FXML
    private Label lnlnombrePaquete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tabla.setEditable(true);
    }

    @FXML
    private void accionsalir(ActionEvent event) throws SQLException {
        Stage stage = (Stage) btnsalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionguardarcambios(ActionEvent event) {
        con = conexion.conectar2();
        armadoDAO = new ArmadoPaqueteMedicoCostosDAO(con);

       
        // Creamos un PasswordField para la contraseña
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        // Creamos una cuadrícula para organizar los elementos en el diálogo
        GridPane gridPane = new GridPane();
        gridPane.add(passwordField, 0, 0);

        // Establecemos la cuadrícula como el contenido personalizado del alerta
        alertaConfirmacion.setTitle("INGRESE CONTRASEÑA");
        alertaConfirmacion.setContentText("ingrese contraseña de usuario");
        alertaConfirmacion.getDialogPane().setContent(gridPane);

        // Mostramos el alerta y esperamos a que el usuario interactúe
        alertaConfirmacion.showAndWait().ifPresent(result -> {
            // Encriptamos la contraseña ingresada
            String hashValor = DigestUtils.md5Hex(passwordField.getText());
            // Comparar la contraseña encriptada con la contraseña correcta
            if (hashValor == null ? VPMedicaPlaza.pass == null : hashValor.equals(VPMedicaPlaza.pass)) {
                // Si la contraseña es correcta, actualizar los elementos en armadoPaqueteMedico
                System.out.println("-1");
                for (int i = 0; i < armadoPaqueteMedico.size(); i++) {
                    System.out.println("0");
                    if (armadoPaqueteMedico.get(i).getActualizar_valores() == 1 || armadoPaqueteMedico.get(i).getActualizar_valores() == 2) {
                        System.out.println("1");
                        armadoDAO.ActualizarArmadoPaquete(armadoPaqueteMedico.get(i));
                    }
                }
                // Mostrar un mensaje de éxito
                alertaSuccess.setTitle("PROCESO REALIZADO CON EXITO");
                alertaSuccess.setContentText("EL PAQUETE SE ACTUALIZO CORRECTAMENTE");
                alertaSuccess.setHeaderText("EL PAQUETE SE ACTUALIZO CORRECTAMENTE");
                alertaSuccess.showAndWait();
                // Cerrar la ventana
                Stage stage = (Stage) btnsalir.getScene().getWindow();
                stage.close();
            } else {
                // Mostrar un mensaje de error si la contraseña es incorrecta
                alertaError.setTitle("Error de autenticación");
                alertaError.setHeaderText(null);
                alertaError.setContentText("La contraseña ingresada es incorrecta.");
                alertaError.showAndWait();
            }
        });

    }

    public void SetObjeto(long valorpaquete, String nombre_paquete) {

        armadoDAO = new ArmadoPaqueteMedicoCostosDAO(con);
        lnlnombrePaquete.setText(nombre_paquete);

        armadoPaqueteMedico.addAll(armadoDAO.traerArmadoPaqueteCostos(valorpaquete));
        llenartabla();
        pintarTabla();
        editartabla();
    }

    private void llenartabla() {
        // NOMBRE INSUMO
        colnombreInsumo.setCellValueFactory(new PropertyValueFactory<>("nombre_insumo"));

        // COLUMNAS DE COSTOS
        colcostoOriginal.setCellValueFactory(new PropertyValueFactory<>("df_costo_original"));
        colcostoNuevo.setCellValueFactory(new PropertyValueFactory<>("df_costo_nuevo"));
        coldiferenciaCostos.setCellValueFactory(new PropertyValueFactory<>("df_diferencia_costo"));
        coldiferenciaCostosPorc.setCellValueFactory(new PropertyValueFactory<>("df_diferencia_costo_porcentaje"));
        // COLUMNAS DE CANTIDAD
        colcantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        // COLUMNAS DE SUBTOTAL
        colcostoSubtotal.setCellValueFactory(new PropertyValueFactory<>("df_costo_subtotal_original"));
        colcostoSubtotalNvo.setCellValueFactory(new PropertyValueFactory<>("df_costo_subtotal_nuevo"));
        coldiferenciaCostosST.setCellValueFactory(new PropertyValueFactory<>("df_diferencia_costo_subtotal"));
        coldiferenciaCostosSTPorc.setCellValueFactory(new PropertyValueFactory<>("df_diferencia_costo_subtotal_porcentaje"));
        // COLUMNAS DE FACTOR
        colFactorInsumo.setCellValueFactory(new PropertyValueFactory<>("df_factor_insuno"));
        // COLUMNAS DE PRECIO TOTAL
        colPrecioTotal.setCellValueFactory(new PropertyValueFactory<>("df_precio_total_actual"));
        colprecioTotalNvo.setCellValueFactory(new PropertyValueFactory<>("df_precio_total_nuevo"));
        coldiferenciTotal.setCellValueFactory(new PropertyValueFactory<>("df_diferencia_total"));
        coldiferenciTotalPorc.setCellValueFactory(new PropertyValueFactory<>("df_diferencia_total_porcentaje"));
        // COLUMNAS DE GANANCIAS
        colgananciaOriginal.setCellValueFactory(new PropertyValueFactory<>("df_monto_ganancia_original"));
        colgananciaNvo.setCellValueFactory(new PropertyValueFactory<>("df_moonto_ganancia_nuevo"));
        Radiobutton();

        tabla.setItems(armadoPaqueteMedico);

    }

    private void editartabla() {
        colFactorInsumo.setCellFactory(TextFieldTableCell.forTableColumn());
        // CANTIDAD

        colFactorInsumo.setOnEditCommit(event -> {
            // Obtener el valor editado
            String nuevoValor = event.getNewValue();
            String valorDouble = nuevoValor.replace("%", "");
            Double valordouble = Double.parseDouble(valorDouble);
            //selecciona la linea 
            int indiceFila = event.getTablePosition().getRow();
            // actualiza los valores en esa linea

            armadoPaqueteMedico.get(indiceFila).setFactor_insuno(valordouble);

            armadoPaqueteMedico.set(indiceFila, actualizarlinea(armadoPaqueteMedico.get(indiceFila)));
            armadoPaqueteMedico.get(indiceFila).setActualizar_valores(2);

            tabla.refresh();
        });

        colFactorInsumo.setEditable(true);
    }

    private void Radiobutton() {
        Callback<TableColumn<ArmadoPaqueteconMedicoCostos, String>, TableCell<ArmadoPaqueteconMedicoCostos, String>> confirmar = (TableColumn<ArmadoPaqueteconMedicoCostos, String> param) -> {
            final TableCell<ArmadoPaqueteconMedicoCostos, String> cell = new TableCell<ArmadoPaqueteconMedicoCostos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton actualizarPrecio = new RadioButton();

                        ArmadoPaqueteconMedicoCostos actualizarvalores = getTableView().getItems().get(getIndex());
                        if (actualizarvalores.getActualizar_valores() != 0) {

                            actualizarPrecio.setSelected(true);
                        } else {
                            actualizarPrecio.setSelected(false);
                        }
                        actualizarPrecio.setOnAction(event -> {
                            int indiceFila = getTableRow().getIndex();
                            if (actualizarPrecio.isSelected()) {
                                armadoPaqueteMedico.get(indiceFila).setActualizar_valores(1);
                                armadoPaqueteMedico.set(indiceFila, actualizarlinea(armadoPaqueteMedico.get(indiceFila)));
                            } else {
                                armadoPaqueteMedico.get(indiceFila).setActualizar_valores(0);

                                armadoPaqueteMedico.set(indiceFila, actualizarlinea(armadoPaqueteMedico.get(indiceFila)));
                            }
                            tabla.refresh();

                        });
                        setGraphic(actualizarPrecio);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        colrbtactualizar.setCellFactory(confirmar);
    }

    private ArmadoPaqueteconMedicoCostos actualizarlinea(ArmadoPaqueteconMedicoCostos armadotraido) {
        ArmadoPaqueteconMedicoCostos armadoPaqueteMedico2 = new ArmadoPaqueteconMedicoCostos();
        //  System.out.println("VALOR DEL FACTOR = " + armadotraido.getFactor_insuno());

        armadoPaqueteMedico2 = armadotraido;

        //armadoPaqueteMedico2.setValor_original_no_cambiado(armadoPaqueteMedico2.getCosto_original());
        // COSTOS UNITARIOS
        if (armadoPaqueteMedico2.getActualizar_valores() == 1) {
            armadoPaqueteMedico2.setCosto_original(armadoPaqueteMedico2.getCosto_nuevo());
        } else if (armadoPaqueteMedico2.getActualizar_valores() == 0) {
            armadoPaqueteMedico2.setCosto_original(armadoPaqueteMedico2.getValor_original_no_cambiado());
        }

        armadoPaqueteMedico2.setDiferencia_costo(armadoPaqueteMedico2.getCosto_original() - armadoPaqueteMedico2.getCosto_nuevo());

        armadoPaqueteMedico2.setDiferencia_costo_porcentaje(armadoPaqueteMedico2.getDiferencia_costo() / armadoPaqueteMedico2.getCosto_original());

        // SUBTOTALES DE COSTOS
        armadoPaqueteMedico2.setCosto_subtotal_original(armadoPaqueteMedico2.getCosto_original() * armadoPaqueteMedico2.getCantidad());

        armadoPaqueteMedico2.setDiferencia_costo_subtotal(armadoPaqueteMedico2.getCosto_subtotal_original() - armadoPaqueteMedico2.getCosto_subtotal_nuevo());

        armadoPaqueteMedico2.setDiferencia_costo_subtotal_porcentaje(armadoPaqueteMedico2.getDiferencia_costo_subtotal() / armadoPaqueteMedico2.getCosto_subtotal_original());

        // PRECIO CON FACTORES
        armadoPaqueteMedico2.setPrecio_total_actual(armadoPaqueteMedico2.getCosto_subtotal_original() / (armadoPaqueteMedico2.getFactor_insuno() / 100));

        armadoPaqueteMedico2.setPrecio_total_nuevo(armadoPaqueteMedico2.getCosto_subtotal_nuevo() / (armadoPaqueteMedico2.getFactor_insuno() / 100));

        armadoPaqueteMedico2.setDiferencia_total(armadoPaqueteMedico2.getCosto_subtotal_original() - armadoPaqueteMedico2.getCosto_subtotal_nuevo());

        armadoPaqueteMedico2.setDiferencia_total_porcentaje(armadoPaqueteMedico2.getDiferencia_total() / armadoPaqueteMedico2.getPrecio_total_actual());

        // GANANCIASA
        armadoPaqueteMedico2.setMonto_ganancia_original(armadoPaqueteMedico2.getPrecio_total_actual() - armadoPaqueteMedico2.getCosto_subtotal_original());

        armadoPaqueteMedico2.setMonto_ganancia_original(armadoPaqueteMedico2.getPrecio_total_nuevo() - armadoPaqueteMedico2.getCosto_subtotal_nuevo());

        if (armadoPaqueteMedico2.getCosto_original() == armadoPaqueteMedico2.getCosto_nuevo()) {
            armadoPaqueteMedico2.setColorrow(0);
        } else if (armadoPaqueteMedico2.getCosto_original() > armadoPaqueteMedico2.getCosto_nuevo()) {
            armadoPaqueteMedico2.setColorrow(1);
        } else if (armadoPaqueteMedico2.getCosto_original() < armadoPaqueteMedico2.getCosto_nuevo()) {
            armadoPaqueteMedico2.setColorrow(2);
        }
        else {
            armadoPaqueteMedico2.setColorrow(0);
        }

        actualizar.add(armadoPaqueteMedico2);
        return armadoPaqueteMedico2;

    }

    private void pintarTabla() {
        tabla.setRowFactory(tv -> new TableRow<ArmadoPaqueteconMedicoCostos>() {
            @Override
            public void updateItem(ArmadoPaqueteconMedicoCostos item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getColorrow() == 1) {
                    setStyle("-fx-background-color: #008f39; -fx-table-cell-border-color: white; -fx-selection-bar: green;");//confirmado
                } else if (item.getColorrow() == 2) {
                    setStyle("-fx-background-color: #FF5733; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
                } else {
                    setStyle(" ");
                }

            }
        });
    }

}
