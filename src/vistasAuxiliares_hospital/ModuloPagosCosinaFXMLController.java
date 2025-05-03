/**
 * ARREGLAR ESTA VISTA LE HACE FALTA ALGO MUY IMPORTANTE.
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.PagosAlimentos;
import clases_hospital.CategoriaPago;
import clases_hospital.Comanda;
import clases_hospital.Folio;
import clases_hospital.FondoEfectivo;
import clases_hospital.FormaPago;
import clases_hospital.Pagos;
import clases_hospital.PagosAlimentosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.FondoEfectivoDAO;
import clases_hospital_DAO.FormaPagoDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.PagosDAO;
import clases_hospital_DAO.TipoPagoDAO;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import net.sf.jasperreports.engine.JRException;
import reportes.Reporte;
import reportes.ReporteC;

//import reportes.Reporte;
//import reportes.ReporteEntradasInsumos;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ModuloPagosCosinaFXMLController implements Initializable {

    //VISTA POR ARREGLAR, PARA VISUALIZAR EN LA TABLA LO QUE SE PAGA Y NO PAGA
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaWarning = new Alert(Alert.AlertType.WARNING);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    ToggleGroup toggleGroup = new ToggleGroup();
    ObservableList<PagosAlimentos> pagosalimentos = FXCollections.observableArrayList();
    ObservableList<CategoriaPago> categoriaspagos = FXCollections.observableArrayList();
    ObservableList<FormaPago> formaspagos = FXCollections.observableArrayList();
    ObservableList<Comanda> comandas = FXCollections.observableArrayList();
    private ArrayList<Folio> list = new ArrayList();
    private String folioPaciente, nombre_catPAgo;
    private int estatusFolio, cantidadpago, idComanda, id_pago;
    private double precioUnitario, precioGuardado, precioaAterirorGuardado;
    private int id_catPago, id_Forma_pago;
    private boolean vistaAuxiliar;
    FoliosDAO foliodao;
    PagosDAO pagosdao;
    TipoPagoDAO tipopagodao;
    PacientesDAO pacientedao;
    FormaPagoDAO formapagodao;
    PagosAlimentosDAO pagosalimentosdao;
    private final String contraaingresar = "Caja321";
    
    Comanda comandaLlena;

    @FXML
    private TableView<PagosAlimentos> tabla;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TableColumn<PagosAlimentos, Double> colMonto;
    @FXML
    private Button btnHistorialPagos;
    @FXML
    private Button btnPagar;
    @FXML
    private ComboBox<FormaPago> cmbFormaPagos;
    @FXML
    private Label lblPaciente;
    @FXML
    private Label lblCuenta;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblDescuento;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblSubTotal;
    @FXML
    private Label lblIVA;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableColumn<PagosAlimentos, Double> colPrecioUnitario;
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private AnchorPane apImagenPrincipal;
    @FXML
    private ImageView imgPrincipal;
    @FXML
    private JFXButton btnPrincipal;
    @FXML
    private ComboBox<CategoriaPago> cmbTipoPago;
    @FXML
    private TextArea txaObcerbaciones;
    @FXML
    private AnchorPane apnCuadrodeDialogo;
    @FXML
    private Button btnIngrsarContra;
    @FXML
    private Button btnCancerlarContra;
    @FXML
    private PasswordField txfContra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabla.setEditable(true);
        try {
            // TODO
            llenarComboBox();
            FoliosDAO folioDAO = new FoliosDAO(con);
//            llenarCmbTipoPago();
        } catch (SQLException ex) {
            Logger.getLogger(ModuloPagosFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fechaActual();
        centrarTextoTabla();
        btnAgregar.setVisible(false);
        tabla.setDisable(true);
        btnPagar.setDisable(true);
        cmbFormaPagos.setDisable(true);
    }

    @FXML
    private void accionHistorialPagos(ActionEvent event) {
    }

    @FXML
    private void accionPagar(ActionEvent event) throws SQLException {

//        if (estatusHospitalizacion < 2 && id_catPago == 3) {
//            alertaWarning.setTitle("ATENCION");
//            alertaWarning.setHeaderText("EL PACIENTE AUN NO ESTÁ DADO DE ALTA");
//            alertaWarning.setContentText("EL PACIENTE SE DEBE DE DAR DE ALTA ANTES DE FINIQUITARLO");
//            alertaWarning.showAndWait();
//        } else {
//            alertaConfirmacion.setHeaderText(null);
//            alertaConfirmacion.setTitle("Confirmación");
//            alertaConfirmacion.setContentText("¿El paciente ya pago su cuenta de " + lblTotalPagar.getText() + " ?");
//            Optional<ButtonType> accionCoonfirmar = alertaConfirmacion.showAndWait();
//            if (accionCoonfirmar.get() == ButtonType.OK) {
        con = conexion.conectar2();
        generarPago();
        llamarReciboPagp();

//            }
//            if (vistaAuxiliar) {
//                Stage stage = (Stage) btnPagar.getScene().getWindow();
//                stage.close();
//            }
//        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();;
        stage.close();
    }

    public void llenarTablaCaja(Comanda comanda) throws SQLException {
        this.comandaLlena = comanda;
        vistaAuxiliar = false;
        btnPrincipal.setVisible(false);

        DecimalFormat df = new DecimalFormat("0.00");
        idComanda = comanda.getIdComanda();
        double calculoiva;
        double subtotal;
        double descuento = 0;

        lblPaciente.setText(comanda.getCliente());
        folioPaciente = "" + idComanda;
        calculoiva = (comanda.getTotal() * 0.16);
        subtotal = (comanda.getTotal()) - calculoiva;

        lblSubTotal.setText("" + df.format(subtotal));
        lblIVA.setText("" + df.format(calculoiva));
        lblCuenta.setText("" + df.format(comanda.getTotal()));
        lblTotalPagar.setText("" + df.format(comanda.getTotal()));

        llenarTabla(comanda, descuento);
    }

    public void llenarTabla(Comanda comanda, double descuento) {
        DecimalFormat df = new DecimalFormat("0.00");
        PagosAlimentos pagoalimento = new PagosAlimentos();
        pagoalimento.setNombreCliente(comanda.getCliente());
        cantidadpago = 1;
        precioUnitario = comanda.getTotal();
        pagoalimento.setDescuento(0);
        pagoalimento.setTotal((comanda.getTotal()));
        pagosalimentos.add(pagoalimento);

        precioaAterirorGuardado = comanda.getTotal();

        colTipo.setCellValueFactory(new PropertyValueFactory("folio_paciente"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("precio_unitario_pago"));
        colPrecioUnitario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrecioUnitario.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            PagosAlimentos pay = event.getRowValue();

            // actualizar el valor de cantidad en el objeto Consumo
            pay.setTotal(event.getNewValue());
            precioUnitario = pay.getTotal();

            lblSubTotal.setText("" + df.format((pay.getTotal() / 1.16)));
            lblIVA.setText("" + df.format((pay.getTotal()) - ((pay.getTotal() / 1.16))));
            lblTotalPagar.setText("" + df.format(pay.getTotal()));
            tabla.refresh();
        });
        colPrecioUnitario.setEditable(true);
        colMonto.setCellValueFactory(new PropertyValueFactory("total_pago"));

        tabla.setItems(pagosalimentos);
    }

    private void centrarTextoTabla() {
        colTipo.setStyle("-fx-alignment: CENTER;");
        colPrecioUnitario.setStyle("-fx-alignment: CENTER;");
        colMonto.setStyle("-fx-alignment: CENTER;");
    }

    private void llenarComboBox() throws SQLException {
        con = conexion.conectar2();
        formapagodao = new FormaPagoDAO(con);
        formaspagos.addAll(formapagodao.obtenerTodos());
        cmbFormaPagos.setItems(formaspagos);
        cmbFormaPagos.setOnAction(e -> {
            id_Forma_pago = cmbFormaPagos.getValue().getId();
            btnPagar.setDisable(false);
        });

        /*SI SELECCIONAN FINIQUITO VALIDAR SI YA SE LE DIO DE ALTA
        NO PUEDEN SELECCIONAR FINIQUITO SI NO SE LE HA DADO DE ALTA*/
        tipopagodao = new TipoPagoDAO(con);
        categoriaspagos.addAll(tipopagodao.obtenerTodos());
        cmbTipoPago.getItems().addAll(categoriaspagos);
        cmbTipoPago.setOnAction(event -> {
            CategoriaPago selectCategoriaPago = cmbTipoPago.getValue();
            id_catPago = selectCategoriaPago.getId();
            nombre_catPAgo = selectCategoriaPago.getNombre();
            tabla.setDisable(false);
            cmbFormaPagos.setDisable(false);
        });
    }

    private void llamarActualizarConsumoAbonos(int idConsumo, double deposito, int idStatusConsumo) throws SQLException {
        con = conexion.conectar2();
        String sql = "{call actualizarConsumoAbonos(?,?,?)}";
        CallableStatement stmt = con.prepareCall(sql);
        stmt.setInt(1, idConsumo);
        stmt.setDouble(2, deposito);
        stmt.setInt(3, idStatusConsumo);
        stmt.execute();
    }

    private void fechaActual() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = date.format(formatter);
        lblFecha.setText(dateString);
        
    }

    @FXML
    private void accionAgregarConsumo(ActionEvent event) {
    }

    private void generarPago() throws SQLException {
        pagosalimentosdao = new PagosAlimentosDAO(conexion.conectar2());
        PagosAlimentos pago = new PagosAlimentos();
        String descripcion = txaObcerbaciones.getText();
        double descuento = 0;
        double subtotal = Double.parseDouble(lblSubTotal.getText());
        double iva = Double.parseDouble(lblIVA.getText());
        double totalpagar = Double.parseDouble(lblTotalPagar.getText());
        String formaPago = cmbFormaPagos.getSelectionModel().getSelectedItem().toString();

        pago.setNombreCliente(pagosalimentos.get(0).getNombreCliente());
//        pago.setIdTipoPago(id_catPago);
        pago.setIdFormaPago(id_Forma_pago);
        pago.setPrecioUnitario(precioUnitario);
        pago.setDescuento(descuento);
        pago.setSubTotal(subtotal);
        pago.setIva(iva);
        pago.setTotal(totalpagar);
        pago.setFormaPago(formaPago);
        pago.setUsuarioCobro(userSystem);

        if (id_Forma_pago == 1) {
            insertFondoEfectivo(totalpagar, lblPaciente.getText(), idComanda);
        }
    }

    public void insertFondoEfectivo(double totalpagar, String nombrePaciente, int idPaciente) throws SQLException {
        FondoEfectivoDAO fondoEfectivoDAO = new FondoEfectivoDAO(con);
        FondoEfectivo paraTraerElFEFijo = new FondoEfectivo();
        /*
        int idFolioEfectivo, 
        Timestamp fecha, 
        String tipoOperacion, 
        double importe, 
        double saldo, 
        String concepto  
         */
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
   
        double monto = paraTraerElFEFijo.getTotalFondoEfectivoFijo() + totalpagar;
        FondoEfectivo fondoEfectivo2 = new FondoEfectivo(0, timestamp, "ENTRADA", totalpagar, monto, "ABONO PACIENTE '" + nombrePaciente + "' ", idPaciente);

        fondoEfectivoDAO.insertarFondoEfectivo(fondoEfectivo2);
    }

    private void llenarCmbTipoPago() throws SQLException {
        tipopagodao = new TipoPagoDAO(conexion.conectar2());
        categoriaspagos.addAll(tipopagodao.obtenerTodos());
        cmbTipoPago.getItems().addAll(categoriaspagos);
        cmbTipoPago.setOnAction(event -> {
            CategoriaPago selectCategoriaPago = cmbTipoPago.getValue();
            id_catPago = selectCategoriaPago.getId();
           
            nombre_catPAgo = selectCategoriaPago.getNombre();
            tabla.setDisable(false);
            cmbFormaPagos.setDisable(false);
        });
    }

    private void llamarReciboPagp() {
        Reporte reporte = new Reporte("Rpt_TicketVentaAlimentos");
        try {
            reporte.generarReporteTicket(comandaLlena.getIdComanda());
        } catch (JRException ex) {
            Logger.getLogger(ModuloPagosCosinaFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Stage stage = (Stage) btnPagar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionIngresarContra(ActionEvent event) {
//        if (txfContra.getText().equals(contraaingresar)) {
//            alertaSuccess.setHeaderText("CONTRASEÑA VALIDA");
//            alertaSuccess.setContentText("CONTRASEÑA VALIDA, CANTIDAD INGRESADA: " + precioGuardado);
//            alertaSuccess.showAndWait();
//
//            pagos.get(0).setPrecio_unitario_pago(precioGuardado);
//            pagos.get(0).setTotal_pago(precioGuardado);
//            cambiarDatos();
//            apnCuadrodeDialogo.setVisible(false);
//        } else {
//            alertaError.setHeaderText("CONTRASEÑA INCORRECTA");
//            alertaError.setContentText("CONTACTE AL PERSONAL AUTORIZADO PARA INGRESARLA.");
//            alertaError.showAndWait();
//            pagos.get(0).setPrecio_unitario_pago(precioaAterirorGuardado);
//            pagos.get(0).setTotal_pago(precioaAterirorGuardado);
//        }
//        tabla.refresh();
    }

    @FXML
    private void accionCancelarContra(ActionEvent event) {
//        apnCuadrodeDialogo.setVisible(false);
//        pagos.get(0).setPrecio_unitario_pago(precioaAterirorGuardado);
//        pagos.get(0).setTotal_pago(precioaAterirorGuardado);
//        tabla.refresh();
    }

    private void cambiarDatos() {
        DecimalFormat df = new DecimalFormat("0.00");
        double calculoiva;
        double subtotal;
        double descuento = 0;

        calculoiva = (precioGuardado * 1.16) - precioGuardado;
        subtotal = (precioGuardado * 1.16) - calculoiva;

        lblSubTotal.setText("" + df.format(subtotal));
        lblIVA.setText("" + df.format(calculoiva));
        lblCuenta.setText("" + df.format((precioGuardado)));
        lblTotalPagar.setText("" + df.format(precioGuardado));
    }

}
