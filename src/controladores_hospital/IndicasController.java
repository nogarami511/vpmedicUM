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
import vistasAuxiliares_hospital.GeneararIndica2Controller;
import vistasAuxiliares_hospital.GeneararIndicaBiomedicaController;
//import vistasAuxiliares_hospital.GenerarIndica2Controller;
//import vistasAuxiliares_hospital.GenerarIndica3Controller;
import vistasAuxiliares_hospital.VerIndicasController;
import static vpmedicaplaza.VPMedicaPlaza.userNameSystem;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class IndicasController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    ObservableList<Paciente> pacintes = FXCollections.observableArrayList();

    PacientesDAO pacienteDAO;

    @FXML
    private TextField txfBuscarPaciente;
    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn<?, ?> colPaciente;
    @FXML
    private TableColumn<?, ?> colHabitacion;
    @FXML
    private TableColumn<Paciente, String> colVer;
    @FXML
    private TableColumn<Paciente, String> colCrear;
    @FXML
    private TableColumn<Paciente, String> colFechaIngreso;
    @FXML
    private TableColumn<Paciente, String> colServicios;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
        txfBuscarPaciente.setOnKeyReleased(e -> filtrarLista(txfBuscarPaciente.getText()));
        
        
        if("MEZCLAS".equals(userNameSystem)){
            colCrear.setVisible(true);
            colVer.setVisible(true);
        }else if("ENFERMERIA".equals(userNameSystem) || "CAJA".equals(userNameSystem) || "BIOMEDICO".equals(userNameSystem) || "MEDICO".equals(userNameSystem)){
            colServicios.setVisible(true);
            colFechaIngreso.setVisible(false);
        }
        else{
            colCrear.setVisible(true);
            colServicios.setVisible(true);
            colVer.setVisible(true);
            
            
        }
        
    }

    private void llenarTabla() {
        try {
            pacienteDAO = new PacientesDAO(conexion.conectar2());
            pacintes.addAll(pacienteDAO.getDataPacientesIndicas());

            colPaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
            colHabitacion.setCellValueFactory(new PropertyValueFactory("habitacion"));
            generarBotones();

            tabla.setItems(pacintes);

        } catch (SQLException ex) {
            Logger.getLogger(IndicasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarBotones() {

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> ver = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-entrar-50.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de ver hojas de consumos de: " + paciente.getNombre() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                verIndicas(paciente);
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
        colVer.setCellFactory(ver);
        
        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> servicios = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-entrar-50.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de ver hojas de consumos de: " + paciente.getNombre() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                agregarservicios(paciente);
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

        colServicios.setCellFactory(servicios);

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> crear = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnCrear = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgCrear = new ImageView("/img/icons/icons8-entrar-50.png");
                        imgCrear.setFitHeight(20);
                        imgCrear.setFitWidth(20);

                        btnCrear.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de generar una hoja de consumo para: " + paciente.getNombre() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                generarIndica(paciente);
                            }
                        });

                        setGraphic(btnCrear);
                        setText(null);
                        btnCrear.setGraphic(imgCrear);
                    }
                }
            };
            return cell;
        };

        colCrear.setCellFactory(crear);

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> confPaq = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-entrar-50.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Desea ir a la configuración de paquete?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                try {
                                    // Cargar la vista de destino
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas_hospital/ConfiguracionPaqueteMedico.fxml"));
                                    Parent root = loader.load();
                                    ConfiguracionPaqueteMedicoController destinoController = loader.getController();
                                    destinoController.setObjeto(paciente.getIdfolio(), paciente.getId_paquete());
                                    // Crear un nuevo Stage para la vista de destino
                                    Stage destinoStage = new Stage();
                                    destinoStage.setTitle("EDITAR PACIENTE");
                                    destinoStage.setScene(new Scene(root));
                                    destinoStage.initModality(Modality.APPLICATION_MODAL);
                                    // Mostrar el nuevo Stage de forma modal
                                    destinoStage.showAndWait();
                                } catch (IOException ex) {
                                    Logger.getLogger(IndicasController.class.getName()).log(Level.SEVERE, null, ex);
                                    ex.printStackTrace();
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

        colFechaIngreso.setCellFactory(confPaq);
    }

    private void verIndicas(Paciente paciente) {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VerIndicas.fxml"));
            Parent root = loader.load();
            VerIndicasController destinoController = loader.getController();

            // Pasar el objeto a la vista de destino
            destinoController.setObjeto(paciente);

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
    private void agregarservicios(Paciente paciente) {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/GeneararIndicaBiomedica.fxml"));
            Parent root = loader.load();
            GeneararIndicaBiomedicaController destinoController = loader.getController();

            // Pasar el objeto a la vista de destino
            destinoController.setObjeto(paciente,1);

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

    private void generarIndica(Paciente paciente) {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/GeneararIndica2.fxml"));
            Parent root = loader.load();
            GeneararIndica2Controller destinoController = loader.getController();

            // Pasar el objeto a la vista de destino
            destinoController.setObjeto(paciente);

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

    private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(pacintes);
        } else {
            ObservableList<Paciente> listaFiltrada = FXCollections.observableArrayList();
            for (Paciente paciente : pacintes) {
                if (paciente.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(paciente);
                }
            }
            tabla.setItems(listaFiltrada);
        }
    }
}
