/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.*;
import clases_hospital_DAO.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class CobroAlimentosController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;

    Comanda comanda;
    Pagos pago;

    ComandaDetalleDAO DetalleDAO;
    FormaPagoDAO formapagodao;
    TipoPagoDAO tipopagodao;
    TipoTarjetaDAO tipoTarjetaDAO;
    PagosDAO pagosDAO;

    ObservableList<ComandaDetalle> OBDetalleComanda = FXCollections.observableArrayList();
    ObservableList<FormaPago> formaspagos = FXCollections.observableArrayList();
    ObservableList<CategoriaPago> categoriaspagos = FXCollections.observableArrayList();
    ObservableList<TipoTarjeta> tiposTarjetas = FXCollections.observableArrayList();

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private AnchorPane apImagenPrincipal;
    @FXML
    private ImageView imgPrincipal;
    @FXML
    private TableView<ComandaDetalle> tabla;
    @FXML
    private Button btnPagar;
    @FXML
    private Label lblDescuento;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblSubTotal;
    @FXML
    private Label lblIVA;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private ComboBox<CategoriaPago> cmbTipoPago;
    @FXML
    private TextArea txaObcerbaciones;
    @FXML
    private ComboBox<FormaPago> cmbFormaPagos;
    @FXML
    private AnchorPane apnCuadrodeDialogo;
    @FXML
    private Button btnIngrsarContra;
    @FXML
    private Button btnCancerlarContra;
    @FXML
    private PasswordField txfContra;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblComanda;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<?, ?> colPreoducto;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TableColumn<?, ?> colTotal;
    @FXML
    private ComboBox<TipoTarjeta> cmbTarjetas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(CobroAlimentosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        DetalleDAO = new ComandaDetalleDAO(con);

        OBDetalleComanda.addAll(DetalleDAO.obtenerComandaDetallePorID(comanda.getIdComanda()));

        colFolio.setCellValueFactory(new PropertyValueFactory("idDetalle"));
        colPreoducto.setCellValueFactory(new PropertyValueFactory("nombreComida"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));

        tabla.setItems(OBDetalleComanda);
    }

    private void llenarComboBox() throws SQLException {
        con = conexion.conectar2();
        formapagodao = new FormaPagoDAO(con);
        formaspagos.addAll(formapagodao.obtenerTodos());
        cmbFormaPagos.setItems(formaspagos);
        cmbFormaPagos.setOnAction(e -> {
            FormaPago formapago = cmbFormaPagos.getValue();
            if (formapago.getId() == 13 || formapago.getId() == 12) {
                btnPagar.setDisable(true);
                cmbTarjetas.setVisible(true);
                cmbTarjetas.setPromptText("SELECCIONAR");
            } else {
                btnPagar.setDisable(false);
                cmbTarjetas.setVisible(false);
            }
        });

        tipoTarjetaDAO = new TipoTarjetaDAO(con);
        tiposTarjetas.addAll(tipoTarjetaDAO.selectTipoTarjetas());
        cmbTarjetas.getItems().addAll(tiposTarjetas);
        cmbTarjetas.setOnAction(event -> {
            btnPagar.setDisable(false);
        });
    }

    public void setObjeto(Comanda comanda) throws SQLException {
        this.comanda = comanda;

        lblComanda.setText(comanda.getFolio());
        lblFecha.setText("" + comanda.getFecha());
        lblCliente.setText(comanda.getCliente());
        lblSubTotal.setText("" + comanda.getSubtotal());
        lblIVA.setText("" + comanda.getIva());
        lblTotalPagar.setText("" + comanda.getTotal());
        llenarTabla();
    }
    
    
    private void imprimirReporte(){
        NumerosALetras totalLetras = new NumerosALetras(pago.getTotal_pago());
        ReporteC reporteC = new ReporteC("Reporte Pago Alimento");
        
        reporteC.generarReportePagoAlimento(""+comanda.getIdComanda(),totalLetras.getCantidadString() );
    }

    @FXML
    private void accionPagar(ActionEvent event) {
        con = conexion.conectar2();
        pagosDAO = new PagosDAO(con);
        pago = new Pagos();

        pago.setId_folio(comanda.getIdComanda());
        pago.setUsuario_cobro(VPMedicaPlaza.userSystem);
        pago.setIdTipoPago(cmbFormaPagos.getValue().getId());
        pago.setFormaPago(cmbFormaPagos.getValue().getTipo());

        if (cmbTarjetas.getValue() != null && cmbTarjetas.getValue().getIdTipoTarjeta() != 0) {
            pago.setId_tipo_tarjeta(cmbTarjetas.getValue().getIdTipoTarjeta());
        } else {
            pago.setId_tipo_tarjeta(0);
        }

        pago.setId_pago(pagosDAO.realizarPagosdeAlimento(pago));
        
        System.out.println(""+ pago.getId_pago());
        imprimirReporte();

        Stage stage = (Stage) btnPagar.getScene().getWindow();;
        stage.close();

    }
    
    

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();;
        stage.close();
    }

    @FXML
    private void accionIngresarContra(ActionEvent event) {
    }

    @FXML
    private void accionCancelarContra(ActionEvent event) {
    }

}
