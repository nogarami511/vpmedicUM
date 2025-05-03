/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.CuentasPorPagar;
import clases_hospital.EstudioMedico;
import clases_hospital_DAO.CuentasPorPagarDAO;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import reportes.ReporteC;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AdeudoProveedoresController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<CuentasPorPagar> cuentasporpagar = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    CuentasPorPagarDAO cuentasPorPagarDAO;

    @FXML
    private Label lblTotalITP;
    @FXML
    private Label lblTotalIP;
    @FXML
    private Label lblTotalIA;
    @FXML
    private TableView<CuentasPorPagar> tabla;
    @FXML
    private TableColumn<?, ?> colNombreComercial;
    @FXML
    private TableColumn<?, ?> colITP;
    @FXML
    private TableColumn<?, ?> colIP;
    @FXML
    private TableColumn<?, ?> colIA;
    @FXML
    private TableColumn<CuentasPorPagar, String> colEDOCTA;
    @FXML
    private ComboBox<?> cmbAdeudo;
    @FXML
    private Button btnImprimir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
    }

    private void llenarCMBAdeudo() {
    }

    private void llenarTabla() {
        cuentasporpagar.clear();
        tabla.getItems().clear();
        cuentasPorPagarDAO = new CuentasPorPagarDAO(conexion.conectar2());
        try {
            cuentasporpagar.addAll(cuentasPorPagarDAO.obtenerAdeudoProveedores());

            colNombreComercial.setCellValueFactory(new PropertyValueFactory("razonSocial"));
            colITP.setCellValueFactory(new PropertyValueFactory("TotalMX"));
            colIP.setCellValueFactory(new PropertyValueFactory("abonoMX"));
            colIA.setCellValueFactory(new PropertyValueFactory("saldoMX"));
            generarBotonPagos();
            sumatoriaDatos();
            tabla.setItems(cuentasporpagar);
        } catch (SQLException ex) {
            Logger.getLogger(AdeudoProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void generarBotonPagos() {
        Callback<TableColumn<CuentasPorPagar, String>, TableCell<CuentasPorPagar, String>> guardar = (TableColumn<CuentasPorPagar, String> param) -> {
            final TableCell<CuentasPorPagar, String> cell = new TableCell<CuentasPorPagar, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        CuentasPorPagar estudiomedico = getTableView().getItems().get(getIndex());
                        ImageView pagar = new ImageView("/img/icons/icons8-paga-48.png");
                        pagar.setFitHeight(20);
                        pagar.setFitWidth(20);
                        btnEntrada.setOnAction(event -> {
                          
                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Visualizar EDOCT " + estudiomedico.getRazonSocial()+ "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    estadoCuenta(estudiomedico.getIdProveedor());

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                             
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(pagar);
                    }
                }
            };
            return cell;
        };

        colEDOCTA.setCellFactory(guardar);
    }
    
    private void estadoCuenta(int idProveedor){
        ReporteC reportec = new ReporteC("");
        reportec.generarReportePaquetes(idProveedor);
    }
    
    private void sumatoriaDatos() {

        Locale mexico = new Locale("es", "MX");
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(mexico);

        double total = 0;
        double abono = 0;
        double saldo = 0;

        for (int i = 0; i < cuentasporpagar.size(); i++) {
            total += cuentasporpagar.get(i).getTotal();
            abono += cuentasporpagar.get(i).getAbono();
            saldo += cuentasporpagar.get(i).getSaldo();
        }

        String TotalFormateada = formatoMoneda.format(total);
        String abonoFormateada = formatoMoneda.format(abono);
        String saldoFormateada = formatoMoneda.format(saldo);

        lblTotalIP.setText(TotalFormateada);
        lblTotalIA.setText(abonoFormateada);
        lblTotalITP.setText(saldoFormateada);
    }

    @FXML
    private void accionImprimir(ActionEvent event) {
        ReporteC reportec = new ReporteC("");
        reportec.generarReporteAdeudo();
    }

}
