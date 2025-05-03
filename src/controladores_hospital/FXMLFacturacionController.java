/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Medico;
import clases_hospital.Pagos;
import clases_hospital_DAO.PagosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.action.ActionUtils;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class FXMLFacturacionController implements Initializable {
    Conexion conexion = new Conexion();
     private static final int ITEMS_PER_PAGE = 50;
    Connection con;
    ObservableList<Pagos> pagos = FXCollections.observableArrayList();
    @FXML
    private TextField txtNombre;
    @FXML
    private TableView<Pagos> tabla;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colMonto;
    
     Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
      @FXML
    private Pagination pagination;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            configurarTabla();
            txtNombre.setOnKeyReleased(e -> filtrarLista(txtNombre.getText()));
            LlenarTabla(0); // Llenar la primera página al iniciar
        } catch (SQLException ex) {
            Logger.getLogger(FXMLFacturacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configurarTabla() {
        colFolio.setCellValueFactory(new PropertyValueFactory<>("FolioMod"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("NombrePaciente"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("Total_pago"));
    }

    private void LlenarTabla(int pageIndex) throws SQLException {
        tabla.getItems().clear();
        con = conexion.conectar2();
        PagosDAO pago = new PagosDAO(con);
        pagos.addAll(pago.ObtenerListaFactura());

        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, pagos.size());
        tabla.setItems(FXCollections.observableArrayList(pagos.subList(fromIndex, toIndex)));
    }
    
//        public void setObjetoIngresarNuevo(ObservableList<Pagos> pagos) throws SQLException {
//        this.pagos = pagos;
//        
//   
//        
//     //   txtNombre.setOnKeyReleased(e -> LenarTabla(txtNombre.getText()));
//      // nomnrePacienteTabla.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
//    }
     private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(pagos);
        } else {
            ObservableList<Pagos> listaFiltrada = FXCollections.observableArrayList();
            for (Pagos pagos : pagos) {
                if (pagos.getNombrePaciente().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(pagos);
                }
            }
            tabla.setItems(listaFiltrada);
        }
    }

    @FXML
    private void AgregarFactura() throws SQLException {
        Pagos modelo = tabla.getSelectionModel().getSelectedItem();
        PagosDAO pag = new PagosDAO(con);
        String resultado = pag.ActualizarFactura(modelo.getFolioMod());
      
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(resultado);
        alert.showAndWait();

        pagos.clear();
        LlenarTabla(0); // Recargar la primera página después de la actualización
    }
    


    
}
