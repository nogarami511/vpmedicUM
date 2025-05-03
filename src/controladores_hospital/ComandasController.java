/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Comanda;
import clases_hospital.Folio;
import clases_hospital_DAO.ComandaDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.PagosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import reportes.Reporte;
import vistasAuxiliares_hospital.CobroAlimentosController;
import vistasAuxiliares_hospital.VisualizarComandaController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class ComandasController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaWarning = new Alert(Alert.AlertType.WARNING);

    Connection connection = null;
    Conexion conexion = new Conexion();

    ObservableList<Comanda> comandas = FXCollections.observableArrayList();

    ComandaDAO comandaDAO;

    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<Comanda> tabla;
    @FXML
    private TableColumn<Comanda, String> clmFolio;
    @FXML
    private TableColumn<Comanda, String> clmCliente;
    @FXML
    private TableColumn<Comanda, String> clmPagar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ComandasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        connection = conexion.conectar2();
        comandaDAO = new ComandaDAO(connection);

        tabla.getItems().clear();

        comandas.addAll(comandaDAO.TraerComandas());

        clmFolio.setCellValueFactory(new PropertyValueFactory("idComanda"));
        clmCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        generarBotonPagar();

        tabla.setItems(comandas);

    }

    private void generarBotonPagar() {
        Callback<TableColumn<Comanda, String>, TableCell<Comanda, String>> pagar = (TableColumn<Comanda, String> param) -> {
            final TableCell<Comanda, String> cell = new TableCell<Comanda, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnPagar = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Comanda comandaSelect = getTableView().getItems().get(getIndex());
                        ImageView altapaciente = new ImageView("/img/icons/icons8-paga-48.png");
                        altapaciente.setFitHeight(20);
                        altapaciente.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnPagar.setOnAction(event -> {

                            try {
                                accionPagarComanda(comandaSelect);
                            } catch (IOException ex) {
                                Logger.getLogger(ComandasController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(ComandasController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        setGraphic(btnPagar);
                        setText(null);
                        btnPagar.setGraphic(altapaciente);

                    }
                }
            };
            return cell;
        };

        clmPagar.setCellFactory(pagar);
    }

    @FXML
    private void visualizar(ActionEvent event) throws JRException {
        Comanda comanda = tabla.getSelectionModel().getSelectedItem();
        Reporte reporte = new Reporte("Rpt_TicketVentaAlimentos");
        reporte.generarReporteTicket(comanda.getIdComanda());

    }

    @FXML
    private void verComanda(ActionEvent event) throws IOException, SQLException {

        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VisualizarComanda.fxml"));
        Parent root = loader.load();
        VisualizarComandaController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        Comanda comanda = tabla.getSelectionModel().getSelectedItem();

        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(comanda.getIdComanda());

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("CAMBIO DE INSUMO BASE POR VARIANTE");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

    }

    @FXML
    private void actionBtnAgregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/agregarComandas.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("COMANDA");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    private void accionPagarComanda(Comanda comanda) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CobroAlimentos.fxml"));
        Parent root = fxml.load();

        CobroAlimentosController destinoController = fxml.getController();

        destinoController.setObjeto(comanda);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(true);
        stage.setTitle("Pago Alimentos");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

}
