/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles_um;

import clase.UM.Servicio;
import clase.UM.Tabulacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author theso
 */
public class IngresarServicioController {

    @FXML
    private TextField txtLote;
    @FXML
    private TextField txtIDServ;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtMarca;
    @FXML
    private ComboBox<?> cmbUnidad;
    @FXML
    private TextField txtPrecioUnitario;
    @FXML
    private TextField txtTotal;
    @FXML
    private void cerrarVentana(ActionEvent event) {
        // Obtener el botón que disparó el evento y cerrar su ventana
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    private Tabulacion tab = new Tabulacion();
    private Servicio serv = new Servicio();
    
        public void recibirDatos(Tabulacion tabulacion, Servicio servicio){
           
           txtLote.setText(tabulacion.getNombre());
           txtIDServ.setText(servicio.getIdTabServ()+ "");
           txtCantidad.setText(servicio.getCantidad()+ "");
           txtMarca.setText(servicio.getMarca());
           txtDescripcion.setText(servicio.getDescripcion());
           txtPrecioUnitario.setText(servicio.getPrecioUnitario() + "");
           txtTotal.setText(servicio.getTotal() + "");
           this.serv = servicio;
    }

    @FXML
    private void ingresarServicio(ActionEvent event) {
    }
    
}
