/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Paciente;
import clase.UM.PacienteUM;
import clase.UM.Tabulacion;
import clase.UM_DAO.PacienteUmDAO;
import controles_um.TabuladorCatalogoController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import vistasAuxiliares.PacienteNuevo2Controller;
/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class Pacientes2Controller implements Initializable {

   
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaWarning = new Alert(Alert.AlertType.WARNING);
    @FXML
    private TextField txfBuscarPaciente;
    @FXML
    private TableView<PacienteUM> tabla;
    @FXML
    TableColumn<PacienteUM, String> nombrePaciente = new TableColumn<>("Paciente");
    @FXML
    private TableColumn<?, ?> telefonoPaciente1;
    @FXML
    private TableColumn<?, ?> fioliosPaciente;
    @FXML
    private TableColumn<PacienteUM, String> editarPaciente;
    @FXML
    private TableColumn<?, ?> eliminarPaciente;
    
    private ObservableList<PacienteUM> pacientes = FXCollections.observableArrayList();
    
    PacienteUmDAO dao ;
    PacienteUM paciente;
    @FXML
    private TableColumn<?, ?> tabulacionPaciente;

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            llenarTala();
        } catch (SQLException ex) {
            Logger.getLogger(Pacientes2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void llenarTala() throws SQLException{
        dao = new PacienteUmDAO();
        paciente = new PacienteUM();
        pacientes.clear();
        tabla.getItems().clear();
        pacientes.addAll(dao.ejecutarProcedimientoPaciente("listar", paciente));
        
        nombrePaciente.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().toString()));
        tabulacionPaciente.setCellValueFactory(new PropertyValueFactory("tipoTab"));
        
        tabla.setItems(pacientes);
        
    }
    
    private void generarBotones() {        
        Callback<TableColumn<PacienteUM, String>, TableCell<PacienteUM, String>> Editar = (TableColumn<PacienteUM, String> param) -> {
            final TableCell<PacienteUM, String> cell = new TableCell<PacienteUM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        PacienteUM paciente = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-lápiz-30.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación de editar");
                            alertaConfirmacion.setContentText("¿Estas seguro de editar a: " + paciente.getNombrePaciente()+ " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                try {
                                    EditarPaciente(paciente);
                                } catch (IOException ex) {
                                    Logger.getLogger(TabuladorCatalogoController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(Pacientes2Controller.class.getName()).log(Level.SEVERE, null, ex);
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
        editarPaciente.setCellFactory(Editar);
    }
    
        private void EditarPaciente(PacienteUM paciente) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/PacienteNuevo2.fxml"));
        Parent root = loader.load();
        PacienteNuevo2Controller destinoController = loader.getController();

        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(paciente);

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("PACIENTE NUEVO");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);
        destinoStage.setResizable(false);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

        llenarTala();
    }


    

    @FXML
    private void agregarPacienteNuevo(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/PacienteNuevo2.fxml"));
        Parent root = loader.load();
        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("PACIENTE NUEVO");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);
        destinoStage.setResizable(false);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

        llenarTala();
    }

 
   

}
