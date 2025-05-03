/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clase.CuentaxPagar;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class CuentasxPagarController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    CuentaxPagar cxp;
    ObservableList<CuentaxPagar> cuentasxpagar = FXCollections.observableArrayList();

    @FXML
    private Button btnAbonar;
    private Button btnSalir;
    @FXML
    private TableView<CuentaxPagar> tabla;
    @FXML
    private TableColumn nombreComercialCxP;
    @FXML
    private TableColumn rubroCxP;
    @FXML
    private TableColumn productoCxP;
    @FXML
    private TableColumn cantidadCxP;
    @FXML
    private TableColumn precioCxP;
    @FXML
    private TableColumn fomaPagoCxP;
    @FXML
    private TableColumn totalCxP;
    @FXML
    private TableColumn abonadoCxP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(CuentasxPagarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abonar(ActionEvent event) {
        
    }

    private void salir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnSalir.getScene().getWindow();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas/DashBoard.fxml"));
        Parent root = (Parent) fxml.load();
        stage.setTitle("VP MEDICA PLAZA");
        stage.setScene(new Scene(root));
        stage.show();
        stage_actual.close();
    }

    private void llenarTabla() throws SQLException {
        this.tabla.getItems().clear();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from cuentasxpagar");

        try {
            while (rs.next()) {
                cxp = new CuentaxPagar();
                cxp.setId(rs.getInt(1));
                cxp.setId_pedido(rs.getInt(2));
                cxp.setNombreComercial(rs.getString(3));
                cxp.setRubro(rs.getString(4));
                cxp.setProducto(rs.getString(5));
                cxp.setObservaciones(rs.getString(6));
                cxp.setCantidad(rs.getDouble(7));
                cxp.setPrecio(rs.getDouble(8));
                cxp.setFormaPago(rs.getString(9));
                cxp.setFechaPedido(rs.getDate(10));
                cxp.setSubtotal(rs.getDouble(11));
                cxp.setSubtotal(rs.getDouble(12));
                cxp.setComision(rs.getDouble(13));
                cxp.setTotal(rs.getDouble(14));
                cxp.setAbonado(rs.getDouble(15));
                cuentasxpagar.add(cxp);

                nombreComercialCxP.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
                rubroCxP.setCellValueFactory(new PropertyValueFactory("rubro"));
                productoCxP.setCellValueFactory(new PropertyValueFactory("producto"));
                cantidadCxP.setCellValueFactory(new PropertyValueFactory("cantidad"));
                precioCxP.setCellValueFactory(new PropertyValueFactory("precio"));
                fomaPagoCxP.setCellValueFactory(new PropertyValueFactory("formaPago"));
                totalCxP.setCellValueFactory(new PropertyValueFactory("total"));
                abonadoCxP.setCellValueFactory(new PropertyValueFactory("abonado"));
                //poner los demas faltantes
                tabla.setItems(cuentasxpagar);

            }
        } catch (Exception e) {
        }

    }
}
