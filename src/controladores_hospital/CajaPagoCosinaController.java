/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Comanda;
import clases_hospital.Folio;
import clases_hospital_DAO.ComandaDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.PacientesDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vistasAuxiliares_hospital.ModuloPagosCosinaFXMLController;
import vistasAuxiliares_hospital.ModuloPagosFXMLController;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CajaPagoCosinaController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    ObservableList<Comanda> comandas = FXCollections.observableArrayList();
    private String foliopaciente;
    private int id_paciente;
    private String nombre_paciente;
    ComandaDAO comandadao;

    Comanda comandaselect;

    @FXML
    private TextField txfBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private BorderPane bpPagos;
    @FXML
    private TableColumn<?, ?> colMontoAlMomento;
    @FXML
    private TableColumn colPagar;
    @FXML
    private TableView<Comanda> tabla;
    @FXML
    private TableColumn<?, ?> colComanda;
    @FXML
    private TableColumn<?, ?> colCliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(CajaPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionBuscar(ActionEvent event) {
        if (txfBuscar.getText().equals("")) {
            alertaSuccess.setHeaderText("ADVERTENCIA");
            alertaSuccess.setContentText("INGRESE EL NOMBRE DEL PACIENTE PRIMERO.");
            alertaSuccess.showAndWait();
        } else {
            try {
                llenarTablaBusqueda();
            } catch (SQLException ex) {
                Logger.getLogger(CajaPagoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void llenarTabla() throws SQLException {
        comandas.clear();
        tabla.getItems().clear();
        comandadao = new ComandaDAO(conexion.conectar2());

        comandas.addAll(comandadao.getDatosComandaNoPagadas());

        colComanda.setCellValueFactory(new PropertyValueFactory("idComanda"));
        colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colMontoAlMomento.setCellValueFactory(new PropertyValueFactory("total"));
        generarBotonPago();
        tabla.setItems(comandas);
        llenarCmb();
    }

    private void llenarTablaBusqueda() throws SQLException {
        comandas.clear();
        tabla.getItems().clear();
        comandadao = new ComandaDAO(conexion.conectar2());

        comandas.addAll(comandadao.getDatosComandaNoPagadas());

        colComanda.setCellValueFactory(new PropertyValueFactory("idComanda"));
        colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colMontoAlMomento.setCellValueFactory(new PropertyValueFactory("total"));
        generarBotonPago();
        tabla.setItems(comandas);
    }

    private void llenarCmb() throws SQLException {
        comandadao = new ComandaDAO(conexion.conectar2());
        AutoCompletionBinding<Comanda> comanda = TextFields.bindAutoCompletion(txfBuscar, comandas);
        comanda.setOnAutoCompleted(event -> {
            comandaselect = event.getCompletion();
        });
    }

    private void traerVistaPagos(Comanda comanda) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ModuloPagosCosinaFXML.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        ModuloPagosCosinaFXMLController generarPago = fxml.getController();

        generarPago.llenarTablaCaja(comanda);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.setTitle("PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    private boolean folioExiste() throws SQLException {
        con = conexion.conectar2();
        boolean respuesta;
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM folios WHERE id_paciente = " + id_paciente + " AND id_estatus = 0");

        if (rs.next()) {
            foliopaciente = rs.getString(2);
            respuesta = true;
        } else {
            respuesta = false;
        }

        return respuesta;
    }

    private void generarBotonPago() {
        Callback<TableColumn<Comanda, String>, TableCell<Comanda, String>> pago = (TableColumn<Comanda, String> param) -> {
            final TableCell<Comanda, String> cell = new TableCell<Comanda, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        Comanda comanda = getTableView().getItems().get(getIndex());
                        ImageView pago = new ImageView("/img/icons/icons8-paga-48.png");
                        pago.setFitHeight(20);
                        pago.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Esta por generar el pago para: " + comanda.getCliente()+ " ");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    traerVistaPagos(comanda);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(pago);
                    }
                }
            };
            return cell;
        };

        colPagar.setCellFactory(pago);
    }
}
