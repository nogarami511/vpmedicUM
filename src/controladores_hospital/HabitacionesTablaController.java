/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Folio;
import clases_hospital.Habitacion;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.HabitacionDAO;
import clases_hospital_DAO.PacientesDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vistasAuxiliares_hospital.CambiarHabitacionPacienteController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class HabitacionesTablaController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    ObservableList<Habitacion> habitaciones = FXCollections.observableArrayList();

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();//hospital

    FoliosDAO foliodao;
    PacientesDAO pacientedao;

    @FXML
    private TableView<Habitacion> tabla;
    @FXML
    private TableColumn<Habitacion, Integer> numeroHabitacion;
    @FXML
    private TableColumn<Habitacion, Integer> pisoHabitacion;
    @FXML
    private TableColumn<Habitacion, String> tipoHabitacion;
    @FXML
    private TableColumn<Habitacion, String> pasienteHabitacion;
    @FXML
    private TableColumn<Habitacion, String> exit;
    @FXML
    private TableColumn<Habitacion, String> colVer;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
            if (VPMedicaPlaza.userNameSystem.equals("SISTEMAS")) {
                exit.setVisible(true);
            } else {
                exit.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HabitacionesTablaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        Connection connection = null;
        try {
            connection = conexion.conectar2();
            numeroHabitacion.setCellValueFactory(new PropertyValueFactory("numeroHabitacion"));
            pisoHabitacion.setCellValueFactory(new PropertyValueFactory("piso"));
            tipoHabitacion.setCellValueFactory(new PropertyValueFactory("tipo"));
            pasienteHabitacion.setCellValueFactory(new PropertyValueFactory("nombre_paciente"));
            HabitacionDAO habitacionDAO = new HabitacionDAO(connection);
            habitaciones = habitacionDAO.llenarTablaAsignacionHabitaciones();

            generarBonton2();
            generarBonton();
            tabla.setItems(habitaciones);
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    //          AGREGA UN PACIENTE A UNA HABITACION         //
    private void vincularPacienteHabitacion(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VincularPacienteCuarto.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("PACIENTE NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    private void generarBonton2() {
        Callback<TableColumn<Habitacion, String>, TableCell<Habitacion, String>> ver = (TableColumn<Habitacion, String> param) -> {
            final TableCell<Habitacion, String> cell = new TableCell<Habitacion, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Habitacion habitacion = getTableView().getItems().get(getIndex());
                        ImageView vizualizar = new ImageView("/img/icons/icons8-entrar-50.png");
                        vizualizar.setFitHeight(20);
                        vizualizar.setFitWidth(20);
                        if (habitacion.getId_paciente() != 0) {
                            btnVer.setDisable(false);
                        } else {
                            btnVer.setDisable(true);
                        }
                        btnVer.setOnAction(event -> {

                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de terminar estadia a: " + habitacion.getNombre_paciente() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                con = conexion.conectar2();
                                pacientedao = new PacientesDAO(con);
                                foliodao = new FoliosDAO(con);
                                //  Folio folio;
                                int habit = habitacion.getId();
                                try {
                                    CallableStatement stm = con.prepareCall("{call SACARPACIENTE(?,?)}");
                                    stm.setInt(1, habit);
                                    stm.setInt(2, VPMedicaPlaza.userSystem);
                                    stm.execute();
                                    System.out.println(habit);
                                    //llamarVistaCambioHabitacionUpgrate(folio);
                                    alertaConfirmacion.setHeaderText(null);
                                    alertaConfirmacion.setTitle("Confirmación");
                                    alertaConfirmacion.setContentText("Termino estadia correctamente");
                                    Optional<ButtonType> action2 = alertaConfirmacion.showAndWait();
                                    if (action.get() == ButtonType.OK) {
//                                        tabla.refresh();
                                        llenarTabla();
                                    }
                                } catch (SQLException ex) {
                                    alertaError.setHeaderText(null);
                                    alertaError.setTitle("ERROR");
                                    alertaError.setContentText("HA OCURRIDO UN ERROR INNESPERADO");
                                    alertaError.showAndWait();
                                }
                            }
                        });
                        setGraphic(btnVer);
                        setText(null);
                        btnVer.setGraphic(vizualizar);
                    }
                }
            };
            return cell;
        };

        exit.setCellFactory(ver);

    }

    private void generarBonton() {
        Callback<TableColumn<Habitacion, String>, TableCell<Habitacion, String>> ver = (TableColumn<Habitacion, String> param) -> {
            final TableCell<Habitacion, String> cell = new TableCell<Habitacion, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Habitacion habitacion = getTableView().getItems().get(getIndex());
                        ImageView vizualizar = new ImageView("/img/icons/icons8-entrar-50.png");
                        vizualizar.setFitHeight(20);
                        vizualizar.setFitWidth(20);
                        if (habitacion.getId_paciente() != 0) {
                            btnVer.setDisable(false);
                        } else {
                            btnVer.setDisable(true);
                        }
                        btnVer.setOnAction(event -> {

                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de cambiar de habitacion a: " + habitacion.getNombre_paciente() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                con = conexion.conectar2();
                                pacientedao = new PacientesDAO(con);
                                foliodao = new FoliosDAO(con);
                                Folio folio;

                                try {
                                    Paciente datatoscompletos = pacientedao.obtenerPacientePorId(habitacion.getId_paciente());
                                    System.out.println(datatoscompletos.getIdPaciente());
                                    folio = foliodao.obtenerFolio(datatoscompletos.getIdfolio());
                                    System.out.println(folio.getId_habitacion());

                                    llamarVistaCambioHabitacionUpgrate(folio);
                                    tabla.refresh();
                                } catch (SQLException ex) {
                                    alertaError.setHeaderText(null);
                                    alertaError.setTitle("ERROR");
                                    alertaError.setContentText("HA OCURRIDO UN ERROR INNESPERADO");
                                    alertaError.showAndWait();
                                }
                            }
                        });
                        setGraphic(btnVer);
                        setText(null);
                        btnVer.setGraphic(vizualizar);
                    }
                }
            };
            return cell;
        };

        colVer.setCellFactory(ver);
    }

    private void llamarVistaCambioHabitacionUpgrate(Folio folio) throws SQLException {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CambiarHabitacionPaciente.fxml"));
            Parent root = fxml.load();

            CambiarHabitacionPacienteController controller = fxml.getController();
            controller.obtenerInformacion(folio);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setTitle("CAMBIO DE HABITACION");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
            llenarTabla();
        } catch (IOException ex) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ERROR");
            alertaError.setContentText("HA OCURRIDO UN ERROR INNESPERADO");
            alertaError.setContentText(ex.getMessage());
            alertaError.showAndWait();
        }
    }
}
