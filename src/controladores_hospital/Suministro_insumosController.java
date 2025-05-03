/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital_DAO.PacientesDAO;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import reportes.ReporteC;
import vistasAuxiliares_hospital.AgregarConsumoHojasDeConsumosController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class Suministro_insumosController implements Initializable {
    
    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    ObservableList<Paciente> pacintes = FXCollections.observableArrayList();
    
    PacientesDAO pacienteDAO;

    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn<?, ?> colPaciente;
    @FXML
    private TableColumn<?, ?> colHabitacion;
    @FXML
    private TableColumn<Paciente, String> colSuministro;
    @FXML
    private TableColumn<Paciente, String> coldesuministrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
      
         if("SISTEMAS".equals(VPMedicaPlaza.userNameSystem)){
        
        coldesuministrar.setVisible(true);
        }else{
           coldesuministrar.setVisible(false);
        }
    }    
    
    private void llenarTabla() {
        try {
            pacienteDAO = new PacientesDAO(conexion.conectar2());
            pacintes.addAll(pacienteDAO.getDataPacientesIndicas());

            colPaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
            colHabitacion.setCellValueFactory(new PropertyValueFactory("habitacion"));
            generarBotonesdeshacer();
            generarBotones();

            tabla.setItems(pacintes);

        } catch (SQLException ex) {
            Logger.getLogger(IndicasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void generarBotones(){
        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> Suministrar = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-insertar-50.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de ingresar insumos a: " + paciente.getNombre() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                generarIndica(paciente);
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

        colSuministro.setCellFactory(Suministrar);
    }
    private void generarBotonesdeshacer(){
        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> Deshacer = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-insertar-50.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de ingresar insumos a: " + paciente.getNombre() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                generarHojasdeIndias(paciente);
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

        coldesuministrar.setCellFactory(Deshacer);
    }
    
    private void generarIndica(Paciente paciente) {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarConsumoHojasDeConsumos.fxml"));
            Parent root = loader.load();
            AgregarConsumoHojasDeConsumosController ispc = loader.getController();

            // Pasar el objeto a la vista de destino
            ispc.setObjeto(paciente,1);

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
    private void generarHojasdeIndias(Paciente paciente) {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarConsumoHojasDeConsumos.fxml"));
            Parent root = loader.load();
            AgregarConsumoHojasDeConsumosController ispc = loader.getController();

            // Pasar el objeto a la vista de destino
            ispc.setObjeto(paciente, 2);

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

    @FXML
    private void generarReceta(ActionEvent event) {
        ReporteC reporte =  new ReporteC("RECETA");
        
        reporte.FormatoGeneralReceta();
    }
    
}
