/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.Quirofano;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class QuirofanosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Quirofano> quirofanos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    Connection conHospital = conexion.conectar2();//Hospital

    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<Quirofano> tabla;
    @FXML
    private TableColumn nombreQuirofano;
    @FXML
    private TableColumn costoQuirofano;
    @FXML
    private TableColumn descripcionQuirofano;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableColumn colActivardesactivar;
    @FXML
    private TableColumn<?, ?> colHorario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        centarColumnas();
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(QuirofanosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregarQuirofano(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/QuirofanoNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("QUIROFANO NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    private void llenarTabla() throws SQLException {
        conHospital = conexion.conectar2();
        this.tabla.getItems().clear();
        Statement stmt = conHospital.createStatement();
        ResultSet rs = stmt.executeQuery("select * from quirofanos");
        Quirofano quirofano;
        try {
            while (rs.next()) {
                quirofano = new Quirofano();
                quirofano.setId(rs.getInt(1));
                quirofano.setNombre(rs.getString(2).toUpperCase());
                quirofano.setTipo_procedimiento(rs.getString(3).toUpperCase());
                quirofano.setIdCosto(rs.getInt(7));
                quirofano.setDescripcion(rs.getString(4).toUpperCase());
                quirofano.setId_estatus(rs.getInt(8));
                quirofanos.add(quirofano);
            }

            nombreQuirofano.setCellValueFactory(new PropertyValueFactory("nombre"));
            costoQuirofano.setCellValueFactory(new PropertyValueFactory("idCosto"));
            descripcionQuirofano.setCellValueFactory(new PropertyValueFactory("descripcion"));

            generarRadioButtonActivarDesacticar();

            tabla.setItems(quirofanos);
            conHospital.close();
        } catch (Exception e) {
            
        }
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    private void generarRadioButtonActivarDesacticar() {
        Callback<TableColumn<Quirofano, String>, TableCell<Quirofano, String>> confirmar = (TableColumn<Quirofano, String> param) -> {
            final TableCell<Quirofano, String> cell = new TableCell<Quirofano, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton rdbActivarDesactivarQuirofano = new RadioButton();

                        Quirofano quiro = getTableView().getItems().get(getIndex());
                        if (quiro.getId_estatus() == 1) {
                            rdbActivarDesactivarQuirofano.setSelected(true);
                        }

                        rdbActivarDesactivarQuirofano.setOnAction(e -> {
                            tabla.setDisable(true);
                            String mensaje = "";
                            conHospital = conexion.conectar2();
                            try {
                                String callSql = "{call actualizarestatusquirofano (?,?)}";
                                CallableStatement stmt = null;
                                stmt = conHospital.prepareCall(callSql);

                                stmt.setInt(1, quiro.getId());
                                if (quiro.getId_estatus() == 1) {
                                   
                                    stmt.setInt(2, 0);
                                    mensaje = "INACTIVO";
                                } else {
                                    
                                    stmt.setInt(2, 1);
                                    mensaje = "ACTIVO";
                                }
                                stmt.execute();
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText(quiro.getNombre() + " SE ENCUENTRA " + mensaje);
                                alertaConfirmacion.showAndWait();
                                llenarTabla();
                                conHospital.close();
                                tabla.setDisable(false);
                            } catch (SQLException ex) {
                                Logger.getLogger(QuirofanosController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        setGraphic(rdbActivarDesactivarQuirofano);
                    }
                }
            };
            return cell;
        };

        colActivardesactivar.setCellFactory(confirmar);
    }

    private void centarColumnas() {
        nombreQuirofano.setStyle("-fx-alignment: CENTER;");
        costoQuirofano.setStyle("-fx-alignment: CENTER;");
        descripcionQuirofano.setStyle("-fx-alignment: CENTER;");
        colActivardesactivar.setStyle("-fx-alignment: CENTER;");
    }
}
