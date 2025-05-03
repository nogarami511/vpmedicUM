/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Indicasp;
import clases_hospital.InventarioDetalle;
import clases_hospital_DAO.IndicaspDAO;
import controladores_hospital.IndicasController;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class VerIndicasController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);

    ObservableList<Indicasp> indicasps = FXCollections.observableArrayList();

    Paciente pasiienteGuardado;

    IndicaspDAO indicaspDAO;

    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Indicasp> tabla;
    @FXML
    private TableColumn<?, ?> colFolioIndica;
    @FXML
    private TableColumn<?, ?> colEstatus;
    @FXML
    private TableColumn<Indicasp, String> colVerIndica;
    @FXML
    private Label lblNombre;
    @FXML
    private TableColumn<?, ?> colArea;
    @FXML
    private TableColumn<Indicasp, String> colDeshacer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void setObjeto(Paciente paciente) {
        pasiienteGuardado = paciente;
        lblNombre.setText(paciente.getNombre());
        llenarTabla();
    }

    private void llenarTabla() {
        try {
            con = conexion.conectar2();
            indicaspDAO = new IndicaspDAO(con);
            indicasps.addAll(indicaspDAO.getAllIndicaspByFolioSinidEstatus(pasiienteGuardado.getIdfolio()));

            colFolioIndica.setCellValueFactory(new PropertyValueFactory("idIndicasp"));
            colEstatus.setCellValueFactory(new PropertyValueFactory("estatusIndica"));
            colArea.setCellValueFactory(new PropertyValueFactory("area"));

            generarRadioButton();
            tabla.setItems(indicasps);
            pintarTabla();

        } catch (SQLException ex) {
            Logger.getLogger(Ingresar_suministro_pacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarRadioButton() {
        Callback<TableColumn<Indicasp, String>, TableCell<Indicasp, String>> ver = (TableColumn<Indicasp, String> param) -> {
            final TableCell<Indicasp, String> cell = new TableCell<Indicasp, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Indicasp indicap = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-entrar-50.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de ver indica #" + indicap.getIdIndicasp() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                irVistaIndica(indicap);
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

        colVerIndica.setCellFactory(ver);

        Callback<TableColumn<Indicasp, String>, TableCell<Indicasp, String>> deshacer = (TableColumn<Indicasp, String> param) -> {
            final TableCell<Indicasp, String> cell = new TableCell<Indicasp, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnDeshacer = new Button("");
                        Indicasp indicap = getTableView().getItems().get(getIndex());
                        ImageView imgDeshacer = new ImageView("/img/icons/icons8-entrar-50.png");
                        imgDeshacer.setFitHeight(20);
                        imgDeshacer.setFitWidth(20);

                        btnDeshacer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de ver indica #" + indicap.getIdIndicasp() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                irVistaDevolucionIndica(indicap);
                            }
                        });

                        setGraphic(btnDeshacer);
                        setText(null);
                        btnDeshacer.setGraphic(imgDeshacer);
                        if(indicap.getEstatusIndica() > 0){
                            btnDeshacer.setDisable(false);
                        }else{
                            btnDeshacer.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colDeshacer.setCellFactory(deshacer);
    }

    private void irVistaIndica(Indicasp indicap) {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VerIndicaFolio.fxml"));
            Parent root = loader.load();
            VerIndicaFolioController destinoController = loader.getController();

            // Pasar el objeto a la vista de destino
            destinoController.setObjeto(indicap, pasiienteGuardado);
            // Crear un nuevo Stage para la vista de destino
            Stage destinoStage = new Stage();
            destinoStage.setTitle("EDITAR PACIENTE");
            destinoStage.setScene(new Scene(root));
            destinoStage.initModality(Modality.APPLICATION_MODAL);
            destinoStage.initStyle(StageStyle.UNDECORATED);

            // Mostrar el nuevo Stage de forma modal
            destinoStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(IndicasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void irVistaDevolucionIndica(Indicasp indicap) {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/Ingresar_suministro_paciente.fxml"));
            Parent root = loader.load();
            Ingresar_suministro_pacienteController destinoController = loader.getController();

            // Pasar el objeto a la vista de destino
            destinoController.setObjetoDeshacer(pasiienteGuardado, indicap.getIdIndicasp());
            // Crear un nuevo Stage para la vista de destino
            Stage destinoStage = new Stage();
            destinoStage.setTitle("EDITAR PACIENTE");
            destinoStage.setScene(new Scene(root));
            destinoStage.initModality(Modality.APPLICATION_MODAL);
            destinoStage.initStyle(StageStyle.UNDECORATED);

            // Mostrar el nuevo Stage de forma modal
            destinoStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(IndicasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void pintarTabla() {
    tabla.setRowFactory(tv -> new TableRow<Indicasp>() {
        @Override
        public void updateItem(Indicasp item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null) {
                setStyle("");
              
            } else if (item.getValidacion() == 1) {
                setStyle("-fx-background-color: #c7f5f5; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
            
            } else {
                setStyle(" ");
            }
        }
    });
}

}
