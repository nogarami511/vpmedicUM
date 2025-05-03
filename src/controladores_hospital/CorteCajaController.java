/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.*;
import clases_hospital.CorteCaja;
import clases_hospital.Pagos;
import clases_hospital_DAO.CorteCajaDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vistasAuxiliares_hospital.Pre_CorteController;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class CorteCajaController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    CorteCajaDAO corteDAO;
    ObservableList<CorteCaja> corteOB = FXCollections.observableArrayList();
    ObservableList<Pagos> pagosOB = FXCollections.observableArrayList();

    Conexion conexion = new Conexion();
    Connection con;

    double dao_banco, dao_efectivo;
    @FXML
    private ComboBox<CorteCaja> cmb_corte;
    @FXML
    private TableView<Pagos> tabla;
    @FXML
    private TableColumn<?, ?> col_id_pago;
    @FXML
    private TableColumn<?, ?> col_nombre_paciente;
    @FXML
    private TableColumn<?, ?> col_tipo_pago;
    @FXML
    private TableColumn<?, ?> con_total_pago;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = conexion.conectar2();
        llenarcmb();

    }

    private void llenarcmb() {
        corteDAO = new CorteCajaDAO(con);
        corteOB.addAll(corteDAO.CortesCajaPendientes());
        cmb_corte.setItems(corteOB);

    }

    @FXML
    private void accionvalidar(ActionEvent event) {
        convalidar(cmb_corte.getValue());
    }

    @FXML
    private void accion_valor(ActionEvent event) {
        tabla.getItems().clear();
        pagosOB.addAll(corteDAO.PagosCorteCaja(cmb_corte.getValue()));

        col_id_pago.setCellValueFactory(new PropertyValueFactory("id_pago"));
        col_nombre_paciente.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
        col_tipo_pago.setCellValueFactory(new PropertyValueFactory("formaPago"));
        con_total_pago.setCellValueFactory(new PropertyValueFactory("total_pago"));
        tabla.setItems(pagosOB);
    }

    private void convalidar(CorteCaja cortecaja) {
        if (cortecaja == null) {
            alertaSuccess.setHeaderText("ALERTA");
            alertaSuccess.setContentText("SELECCIONE UN PRE CORTE ANETES DE CONTINUAR");
            alertaSuccess.showAndWait();
        } else {
            try {
                FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/Pre_Corte.fxml"));
                Parent root = fxml.load();
                Scene scene = new Scene(root);
                
                System.out.println(cortecaja.toString());
                
                Pre_CorteController destinoController = fxml.getController();
                destinoController.setDatos(cortecaja);

                //Mandamos a llamar el metodo Cita moficiar de la vista CITAQUIROFANONUEVO
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(CorteCajaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
