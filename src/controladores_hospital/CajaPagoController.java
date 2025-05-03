/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.TipoVistaPago;
import clases_hospital.Folio;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.PacientesDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vistasAuxiliares_hospital.ModuloPagosFXMLController;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CajaPagoController implements Initializable {
    
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    ObservableList<Folio> folios = FXCollections.observableArrayList();
     ObservableList<TipoVistaPago> tipoVistaPagos = FXCollections.observableArrayList();
    private String foliopaciente;
    private int id_paciente, filtro = 1;
    private String nombre_paciente;
    PacientesDAO pacientedao;
    FoliosDAO foliodao;

    Folio folioselect;

    @FXML
    private TextField txfBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private BorderPane bpPagos;
    @FXML
    private TableColumn<?, ?> colCuenta;
    @FXML
    private TableColumn<?, ?> colPaciente;
    @FXML
    private TableColumn<?, ?> colMedico;
    @FXML
    private TableColumn<?, ?> colHabitacion;
    @FXML
    private TableColumn<?, ?> colMontoAlMomento;
    @FXML
    private TableColumn colPagar;
    @FXML
    private TableView<Folio> tabla;
    @FXML
    private Button btnPagosAlimentos;
    @FXML 
    private ComboBox<TipoVistaPago> filtroBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarcmbx();
            llenarTabla(filtro);
        } catch (SQLException ex) {
            Logger.getLogger(CajaPagoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void llenarcmbx(){
      List<TipoVistaPago> listafiltro = new ArrayList<>();
      listafiltro.add(new TipoVistaPago(1, "HABITACIÓN"));
      listafiltro.add(new TipoVistaPago(2, "APARTADO"));
      listafiltro.add(new TipoVistaPago(3, "GENERAL"));
      tipoVistaPagos.addAll(listafiltro);
       filtroBox.setItems(tipoVistaPagos);
       
      
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

    private void llenarTabla(int filtro) throws SQLException {
        folios.clear();
        tabla.getItems().clear();
        foliodao = new FoliosDAO(conexion.conectar2());

        folios.addAll(foliodao.obtenerTodosFoliosConNombrePacienteYMedicoconfiltro(filtro));

        colCuenta.setCellValueFactory(new PropertyValueFactory("folio"));
        colPaciente.setCellValueFactory(new PropertyValueFactory("nombre_paciente"));
        colMedico.setCellValueFactory(new PropertyValueFactory("medico_nombre"));
        colHabitacion.setCellValueFactory(new PropertyValueFactory("numero_habitacion"));
        colMontoAlMomento.setCellValueFactory(new PropertyValueFactory("montoHastaElMomento"));
        generarBotonPago();
        tabla.setItems(folios);
        llenarCmb();
    }

    private void llenarTablaBusqueda() throws SQLException {
        folios.clear();
        tabla.getItems().clear();
        folios.addAll(folioselect);

        colCuenta.setCellValueFactory(new PropertyValueFactory("folio"));
        colPaciente.setCellValueFactory(new PropertyValueFactory("nombre_paciente"));
        colMedico.setCellValueFactory(new PropertyValueFactory("medico_nombre"));
        colHabitacion.setCellValueFactory(new PropertyValueFactory("numero_habitacion"));
        colMontoAlMomento.setCellValueFactory(new PropertyValueFactory("montoHastaElMomento"));
        generarBotonPago();
        tabla.setItems(folios);
    }

    private void llenarCmb() throws SQLException {
        foliodao = new FoliosDAO(conexion.conectar2());
        AutoCompletionBinding<Folio> folio = TextFields.bindAutoCompletion(txfBuscar, folios);
        folio.setOnAutoCompleted(event -> {
            folioselect = event.getCompletion();
        });
    }

    private void traerVistaPagos(Folio folio) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ModuloPagosFXML.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        ModuloPagosFXMLController generarPago = fxml.getController();

        generarPago.llenarTablaCaja(folio);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.setTitle("PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla(filtro);
    }

    private void traerVistaComandas() throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas_hospital/CajaPagoCosina.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setMaximized(false);
        //stage.setTitle("PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
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
        Callback<TableColumn<Folio, String>, TableCell<Folio, String>> pago = (TableColumn<Folio, String> param) -> {
            final TableCell<Folio, String> cell = new TableCell<Folio, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        Folio folio = getTableView().getItems().get(getIndex());
                        ImageView pago = new ImageView("/img/icons/icons8-paga-48.png");
                        pago.setFitHeight(20);
                        pago.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("Esta por generar el pago para: " + folio.getNombre_paciente() + " ");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    traerVistaPagos(folio);
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

    @FXML
    private void accionPagos(ActionEvent event) throws IOException {
        traerVistaComandas();
    }

    @FXML
    private void historialPagos(ActionEvent event) throws IOException, SQLException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas_hospital/EliminarPagos.fxml"));
        Parent root = loader.load();
        EliminarPagosController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        Folio folio = tabla.getSelectionModel().getSelectedItem();
    
        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(folio.getId(), folio.getNombre_paciente());

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setMaximized(true);
        destinoStage.setTitle("HISTORIAL DE PAGOS");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();
    }

    @FXML
    private void pagosEnTransito(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/PagosEnTransito.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("PAGOS EN TRANSITO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void filtrar(ActionEvent event) throws SQLException {
        llenarTabla(filtroBox.getValue().getId_tipo());
    }
}
