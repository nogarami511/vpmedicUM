/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles_um;

import clase.UM.Tabulacion;
import clase.UM_DAO.TabuladorCatalogoDAO;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class TabuladorCatalogoController implements Initializable {

    @FXML
    private TableView<Tabulacion> tablaTabulacion;
    @FXML
    private TableColumn<?, ?> nombreTabulacion;
    @FXML
    private TableColumn<?, ?> notaTabulacion;
    @FXML
    private TableColumn<?, ?> colArmado;
    @FXML
    private TableColumn<?, ?> colEliminar;
    @FXML
    private TableColumn<Tabulacion, String> ColEditar;

    ObservableList<Tabulacion> obListTabuladores = FXCollections.observableArrayList();

    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
    }

    public void llenarTabla() {
        TabuladorCatalogoDAO tabuladorDAO = new TabuladorCatalogoDAO();
        Tabulacion tabulador = new Tabulacion();
        tablaTabulacion.getItems().clear();
        obListTabuladores.clear();
        nombreTabulacion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        notaTabulacion.setCellValueFactory(new PropertyValueFactory<>("nota"));

        generarBotones();
        obListTabuladores.addAll(tabuladorDAO.ejecutarProcedimiento("listar", tabulador));
        tablaTabulacion.setItems(obListTabuladores);

    }

    public void loadPageIngresarTabulacion() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas_UM/IngresarTabulador.fxml"));
        Parent root = loader.load();

        Stage nuevoStage = new Stage();
        nuevoStage.setTitle("INGRESAR NUEVA TABULACIÓN");  // Usa el título correcto
        nuevoStage.setScene(new Scene(root));
        nuevoStage.initModality(Modality.APPLICATION_MODAL);
        nuevoStage.setResizable(false);

        // Mostrar de forma modal (bloquea la ventana anterior hasta que se cierre esta)
        nuevoStage.showAndWait();

    }

    public void loadPageEditarTabulacion(Tabulacion tabulador) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas_UM/IngresarTabulador.fxml"));
            Parent root = loader.load();
            IngresarTabuladorController ingresartabcontroller = loader.getController();
            ingresartabcontroller.recibirDatos(tabulador);
            Stage nuevoStage = new Stage();
            nuevoStage.setTitle("INGRESAR NUEVA TABULACIÓN");  // Usa el título correcto
            nuevoStage.setScene(new Scene(root));
            nuevoStage.initModality(Modality.APPLICATION_MODAL);
            nuevoStage.setResizable(false);
            // Mostrar de forma modal (bloquea la ventana anterior hasta que se cierre esta)
            nuevoStage.showAndWait();

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    private void btnIngresarTabulacion(ActionEvent event) throws IOException {
        loadPageIngresarTabulacion();
        llenarTabla();
    }

    private void generarBotones() {        
        Callback<TableColumn<Tabulacion, String>, TableCell<Tabulacion, String>> Editar = (TableColumn<Tabulacion, String> param) -> {
            final TableCell<Tabulacion, String> cell = new TableCell<Tabulacion, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Tabulacion tabulador = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-lápiz-30.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación de editar");
                            alertaConfirmacion.setContentText("¿Estas seguro de editar a: " + tabulador.getNombre() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                try {
                                    loadPageEditarTabulacion(tabulador);
                                } catch (IOException ex) {
                                    Logger.getLogger(TabuladorCatalogoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                        setGraphic(btnVer);
                        setText(null);
                        btnVer.setGraphic(imgVer);
                    }
                }
            };
            return cell;
        };
        ColEditar.setCellFactory(Editar);
    }

}
