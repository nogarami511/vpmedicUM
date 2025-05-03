/**
 * ARREGLAR ESTA VISTA LE HACE FALTA ALGO MUY IMPORTANTE.
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.CategoriaPago;
import clases_hospital.Folio;
import clases_hospital.FondoCuentaBancoo;
import clases_hospital.FondoEfectivo;
import clases_hospital.Fondocuentabanco;
import clases_hospital.FormaPago;
import clases_hospital.Pagos;
import clases_hospital.TipoTarjeta;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.FondoBancoDAO;
import clases_hospital_DAO.FondoEfectivoDAO;
import clases_hospital_DAO.FondoEfectivoFijoDAO;
import clases_hospital_DAO.FondocuentabancoDAO;
import clases_hospital_DAO.FormaPagoDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.PagosDAO;
import clases_hospital_DAO.TipoPagoDAO;
import clases_hospital_DAO.TipoTarjetaDAO;
import com.jfoenix.controls.JFXButton;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import reportes.ReporteC;

//import reportes.Reporte;
//import reportes.ReporteEntradasInsumos;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ModuloPagosFXMLController implements Initializable {

    //VISTA POR ARREGLAR, PARA VISUALIZAR EN LA TABLA LO QUE SE PAGA Y NO PAGA
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaWarning = new Alert(Alert.AlertType.WARNING);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    ToggleGroup toggleGroup = new ToggleGroup();
    ObservableList<Pagos> pagos = FXCollections.observableArrayList();
    ObservableList<CategoriaPago> categoriaspagos = FXCollections.observableArrayList();
    ObservableList<FormaPago> formaspagos = FXCollections.observableArrayList();
    ObservableList<TipoTarjeta> tiposTarjetas = FXCollections.observableArrayList();
    private ArrayList<Folio> list = new ArrayList();
    private String folioPaciente, nombre_catPAgo;
    private int idQuirofao = 0, estatusFolio, idPacientePago, cantidadpago, idFolio, id_pago, estatusHospitalizacion;
    private double precioUnitario, precioGuardado, precioaAterirorGuardado;
    private int id_catPago, id_Forma_pago;
    private boolean vistaAuxiliar;
    FoliosDAO foliodao;
    PagosDAO pagosdao;
    TipoPagoDAO tipopagodao;
    PacientesDAO pacientedao;
    FormaPagoDAO formapagodao;
    TipoTarjetaDAO tipoTarjetaDAO;
    private final String contraaingresar = "Caja321";

    TipoTarjeta tipotarjeta;

    @FXML
    private TableView<Pagos> tabla;
    @FXML
    private TableColumn colTipo;
    @FXML
    private TableColumn<Pagos, Double> colMonto;
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
    private TableColumn<Pagos, Double> colDescuento;
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
    private TableColumn<Pagos, Double> colPrecioUnitario;
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
    @FXML
    private Label lblTarjeta;
    @FXML
    private ComboBox<TipoTarjeta> cmbTarjetas;

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
            estatusHospitalizacion = folioDAO.verificarEstatusHospitalizacion(idFolio);
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
        if (pagos.get(0).getPrecio_unitario_pago() <= 0) {//
            alertaError.setHeaderText("MONTO INVALIDO");
            alertaError.setContentText("EL MONTO INGRESADO NO PUEDE SER PROCESADO YA QUE ES NULO O NEGATIVO");
            alertaError.showAndWait();
        } else {
            con = conexion.conectar2();
       
            generarPago();
            actualizarFolio();
            llamarReciboPagp();
        }

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

    public void llenarTablaCaja(Folio folio) throws SQLException {
        vistaAuxiliar = false;
        btnPrincipal.setVisible(false);

        DecimalFormat df = new DecimalFormat("0.00");
        idFolio = folio.getId();
        double calculoiva;
        double subtotal;
        double descuento = 0;
        
        System.out.println("--> " + folio.getIdPaciente()+ " <--");
        idPacientePago = folio.getIdPaciente();
        lblPaciente.setText(folio.getNombre_paciente());
        folioPaciente = folio.getFolio();
        calculoiva = (folio.getMontoHastaElMomento() * 1.16) - folio.getMontoHastaElMomento();
        subtotal = (folio.getMontoHastaElMomento() * 1.16) - calculoiva;

        lblSubTotal.setText("" + df.format(subtotal));
        lblIVA.setText("" + df.format(calculoiva));
        lblCuenta.setText("" + df.format((folio.getMontoHastaElMomento() * 1.16)));
        lblTotalPagar.setText("" + df.format(folio.getMontoHastaElMomento() * 1.16));

        llenarTabla(folio, descuento);
    }
    
//    public void llenarTablaCajaFolioHemodinamia(Folio folioHemodinamia){
//    }

    public void llenarTabla(Folio folio, double descuento) {
        DecimalFormat df = new DecimalFormat("0.00");
        Pagos pago = new Pagos();
        pago.setFolio_paciente(folioPaciente);
        pago.setCantidad_pago(1);
        cantidadpago = 1;
        BigDecimal truncadoBigDecimal = new BigDecimal((folio.getMontoHastaElMomento()*1.16)-folio.getTotalDeAbono()).setScale(2, RoundingMode.UP);
        double truncadoDouble = truncadoBigDecimal.doubleValue();
        System.out.println(truncadoDouble);
        pago.setPrecio_unitario_pago(truncadoDouble);
        
        if (pago.getPrecio_unitario_pago() <= 0){
            pago.setPrecio_unitario_pago(0);
        }
        precioUnitario = folio.getSaldoACubrir();
        pago.setDescuento_pago(descuento);
        pago.setTotal_pago((pago.getCantidad_pago() * folio.getSaldoACubrir() - pago.getDescuento_pago()));
        if(pago.getTotal_pago() <= 0){
            pago.setTotal_pago(0);
        }
        pagos.add(pago);

        precioaAterirorGuardado = folio.getSaldoACubrir();

        colTipo.setCellValueFactory(new PropertyValueFactory("folio_paciente"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("precio_unitario_pago"));
        colPrecioUnitario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrecioUnitario.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            Pagos pay = event.getRowValue();
   
            if (folio.getId_estatus_pago_deposito() == 0) {
                if (event.getNewValue() <= 0) {
                   
                    alertaError.setHeaderText("MONTO INVALIDO");
                    alertaError.setContentText("NO PUEDES INGRESAR UN PAGO MENOR O IGUAL A 0 ");
                    alertaError.showAndWait();
                } else {
                    if (event.getNewValue() < folio.getCosoto_deposito()) {
                   
                        precioGuardado = event.getNewValue();
                        alertaError.setHeaderText(null);
                        alertaError.setTitle("Confirmación");
                        alertaError.setContentText("INGRESAR CONTRASEÑA PARA CONTINUAR");
                        Optional<ButtonType> accionCoonfirmar = alertaError.showAndWait();
                        if (accionCoonfirmar.get() == ButtonType.OK) {
                            apnCuadrodeDialogo.setVisible(true);
                        } else {
                            alertaSuccess.setHeaderText("CANTIDAD INVALIDA");
                            alertaSuccess.setContentText("EL DEPOSITO DEBE SER IGUAL O SUPERIOR AL COSTO DEL PAQUETE SELECCIONADO");
                            alertaSuccess.showAndWait();
                            pay.setPrecio_unitario_pago(folio.getCosoto_deposito());
                        }
                    } else {
                        if (event.getNewValue() <= 0) {
                      
                            alertaError.setHeaderText("MONTO INVALIDO");
                            alertaError.setContentText("NO PUEDES INGRESAR UN PAGO MENOR O IGUAL A 0 ");
                            alertaError.showAndWait();
                        } else {
                            pay.setPrecio_unitario_pago(event.getNewValue());
                        }

                    }
                }
            } else {
                // actualizar el valor de cantidad en el objeto Consumo
                //pay.setPrecio_unitario_pago(event.getNewValue());
                if (event.getNewValue() <= 0) {
            
                    alertaError.setHeaderText("MONTO INVALIDO");
                    alertaError.setContentText("NO PUEDES INGRESAR UN PAGO MENOR O IGUAL A 0 ");
                    alertaError.showAndWait();
                } else {
                    pay.setPrecio_unitario_pago(event.getNewValue());
                }
            }
            precioUnitario = pay.getPrecio_unitario_pago();

            pay.setTotal_pago((pay.getCantidad_pago() * pay.getPrecio_unitario_pago()) - pay.getDescuento_pago());
            lblDescuento.setText("" + pay.getDescuento_pago());
            lblSubTotal.setText("" + df.format((pay.getTotal_pago() / 1.16) - pay.getDescuento_pago()));
            lblIVA.setText("" + df.format((pay.getTotal_pago()) - ((pay.getTotal_pago() / 1.16) - pay.getDescuento_pago())));
            lblTotalPagar.setText("" + df.format(pay.getTotal_pago()));
            tabla.refresh();
        });
        colDescuento.setCellValueFactory(new PropertyValueFactory("descuento_pago"));
        colDescuento.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colDescuento.setOnEditCommit(event -> {

            // Crear un cuadro de diálogo de entrada de texto
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("REQUIERE AUTORIZACION");
            dialog.setHeaderText("INGRESE LA CONTRASEÑA PARA AUTORIZACION");

            // Obtener el campo de entrada y establecerlo como PasswordField
            PasswordField passwordField = new PasswordField();
            GridPane grid = new GridPane();
            grid.add(passwordField, 1, 1);
            dialog.getDialogPane().setContent(grid);
            dialog.showAndWait().ifPresent(password -> {
                if (passwordField.getText().equals("vp-sistemas")) {
                    // obtener el objeto Consumo que está siendo editado
                    Pagos pay = event.getRowValue();
                    // actualizar el valor de cantidad en el objeto Consumo
                    pay.setDescuento_pago(event.getNewValue());

                    pay.setTotal_pago((pay.getCantidad_pago() * pay.getPrecio_unitario_pago()) - pay.getDescuento_pago());
                    lblDescuento.setText("" + pay.getDescuento_pago());
                    lblSubTotal.setText("" + df.format((pay.getTotal_pago() / 1.16) - pay.getDescuento_pago()));
                    lblIVA.setText("" + df.format((pay.getTotal_pago()) - ((pay.getTotal_pago() / 1.16) - pay.getDescuento_pago())));
                    lblTotalPagar.setText("" + df.format(pay.getTotal_pago()));
                    tabla.refresh();

              
                } else {
                   
                    Pagos pay = event.getRowValue();
                    pay.setDescuento_pago(0.0);
                    lblDescuento.setText("0.0");
                    alertaWarning.setTitle("ATENCION");
                    alertaWarning.setHeaderText("NECESITA AUTORIZACION");
                    alertaWarning.setContentText("NECESITA AUTORIZACION");
                    alertaWarning.showAndWait();
                }
            });
            event.getOldValue();
            tabla.refresh();
            lblDescuento.setText("0.0");
        });
        colPrecioUnitario.setEditable(true);
        colDescuento.setEditable(true);
        colMonto.setCellValueFactory(new PropertyValueFactory("total_pago"));

        tabla.setItems(pagos);
    }

    private void centrarTextoTabla() {
        colTipo.setStyle("-fx-alignment: CENTER;");
        colPrecioUnitario.setStyle("-fx-alignment: CENTER;");
        colDescuento.setStyle("-fx-alignment: CENTER;");
        colMonto.setStyle("-fx-alignment: CENTER;");
    }

    private void llenarComboBox() throws SQLException {
        con = conexion.conectar2();
        formapagodao = new FormaPagoDAO(con);
        formaspagos.addAll(formapagodao.obtenerTodosEstatus1());
            
        for(int i = 0; i < formaspagos.size(); i++ ){
            if(formaspagos.get(i).getId() != 1 || formaspagos.get(i).getId() != 2 || formaspagos.get(i).getId() != 13 || formaspagos.get(i).getId() != 14  ){
                if(formaspagos.get(i).getId() == 13){
                    formaspagos.get(i).setTipo("TARJETA CREDITO");
                }else if(formaspagos.get(i).getId() == 14){
                    formaspagos.get(i).setTipo("TARJETA DEBITO");
                }
            }
        }
        
        cmbFormaPagos.setItems(formaspagos);
        cmbFormaPagos.setOnAction(e -> {
            FormaPago formapago = cmbFormaPagos.getValue();
            id_Forma_pago = cmbFormaPagos.getValue().getId();
            if (formapago.getId() == 13 || formapago.getId() == 12) {
                btnPagar.setDisable(true);
                lblTarjeta.setVisible(true);
                cmbTarjetas.setVisible(true);
                cmbTarjetas.setPromptText("SELECCIONAR");
            } else {
                btnPagar.setDisable(false);
                lblTarjeta.setVisible(false);
                cmbTarjetas.setVisible(false);
                tipotarjeta = null;
            }
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

        tipoTarjetaDAO = new TipoTarjetaDAO(con);
        tiposTarjetas.addAll(tipoTarjetaDAO.selectTipoTarjetas());
        cmbTarjetas.getItems().addAll(tiposTarjetas);
        cmbTarjetas.setOnAction(event -> {
            tipotarjeta = cmbTarjetas.getValue();
            btnPagar.setDisable(false);
        });

    }

    private void actualizarFolio() throws SQLException {
        foliodao = new FoliosDAO(conexion.conectar2());
        Folio folio = foliodao.obtenerFolioPorId(idFolio);
        double totalpagar = Double.parseDouble(lblTotalPagar.getText());

        if (folio.getId_estatus_pago_deposito() == 0) {
            folio.setTotalDeAbono(folio.getTotalDeAbono() + totalpagar);
            folio.setSaldoACubrir(folio.getSaldoACubrir() - totalpagar);
            folio.setId_estatus_folio(1);
            folio.setId_estatus_pago_deposito(1);

           
        } else {
            if (folio.getIdEstatus() == 0) {
                if ((folio.getTotalDeAbono() + totalpagar) >= folio.getMontoHastaElMomento()) {
                    folio.setTotalDeAbono(folio.getTotalDeAbono() + totalpagar);
                    folio.setSaldoACubrir(folio.getSaldoACubrir() - totalpagar);
                    folio.setId_estatus_folio(1);
                } else {
                    folio.setTotalDeAbono(folio.getTotalDeAbono() + totalpagar);
                    folio.setSaldoACubrir(folio.getSaldoACubrir() - totalpagar);
                }
            }
        }

        foliodao.actualizarFolio(folio);
        

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
        pagosdao = new PagosDAO(conexion.conectar2());
        Pagos pago = new Pagos();
        String descripcion = txaObcerbaciones.getText();
        double descuento = Double.parseDouble(lblDescuento.getText());
        double subtotal = Double.parseDouble(lblSubTotal.getText());
        double iva = Double.parseDouble(lblIVA.getText());
        double totalpagar = Double.parseDouble(lblTotalPagar.getText());
        String formaPago = cmbFormaPagos.getSelectionModel().getSelectedItem().toString();
        int tipopago_tipo = cmbTipoPago.getSelectionModel().getSelectedItem().getId();
        System.out.println("ppp "+idPacientePago);
        pago.setId_pasiente(idPacientePago);
        pago.setFolio_paciente(folioPaciente);
//        pago.setIdTipoPago(id_catPago);
        pago.setIdTipoPago(id_Forma_pago);
        pago.setDescripcionPago(descripcion);
        pago.setCantidad_pago(cantidadpago);
        pago.setPrecio_unitario_pago(precioUnitario);
        pago.setDescuento_pago(descuento);
        pago.setSub_total_pago(subtotal);
        pago.setIva_pago(iva);
        pago.setTotal_pago(totalpagar);
        pago.setFormaPago(formaPago);
        pago.setUsuario_cobro(userSystem);
        pago.setEstatus_pago_reembolso(1);
        pago.setDescripcion_reembolso("");
        pago.setId_folio(idFolio);                    //  HAY QUE AGREGAR EL ID DEL FOLIO
        pago.setPago_tipo(tipopago_tipo);
        if (tipotarjeta == null) {
            pago.setId_tipo_tarjeta(0);
        } else {
            pago.setId_tipo_tarjeta(tipotarjeta.getIdTipoTarjeta());
        }

        if(id_Forma_pago == 1){
            insertFondoEfectivo(totalpagar, lblPaciente.getText(), idPacientePago);
        }else if(id_Forma_pago == 2){
            inserFondoBanco(totalpagar, lblPaciente.getText(), idPacientePago);
        }

        id_pago = pagosdao.crearYDevolverIntPagos(pago);
    }

    public void insertFondoEfectivo(double totalpagar, String nombrePaciente, int idPaciente) throws SQLException {
        FondoEfectivoDAO fondoEfectivoDAO = new FondoEfectivoDAO(con);
        FondoEfectivo paraTraerElFEFijo = new FondoEfectivo();
        FondoEfectivoFijoDAO fondoefecfijoDAO = new FondoEfectivoFijoDAO(con);
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
        paraTraerElFEFijo.traerFondoEfectivoFijo();
        double monto = paraTraerElFEFijo.getTotalFondoEfectivoFijo() + totalpagar;
        FondoEfectivo fondoEfectivo2 = new FondoEfectivo(0, timestamp, "ENTRADA", totalpagar, monto, "ABONO PACIENTE '" + nombrePaciente + "' ", idPaciente);
        fondoefecfijoDAO.actualizarFondoEfectivoFijo(1, monto);
        fondoEfectivoDAO.insertarFondoEfectivo(fondoEfectivo2);

    }
    
    public void inserFondoBanco(double totalpagar, String paciente, int idPaciente) throws SQLException{
        //FondoBancoDAO fondoBancoDAO = new FondoBancoDAO(con);
        FondocuentabancoDAO fondoBancoDAO = new FondocuentabancoDAO(con);        
        Fondocuentabanco fondoCuentaBancoo = new Fondocuentabanco();
        
        double saldo = fondoBancoDAO.fondocuentabancofijo();
        
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        fondoCuentaBancoo.setTipoOperacion("ENTRADA");
        fondoCuentaBancoo.setIdCliente(idPaciente);
        fondoCuentaBancoo.setImporte(totalpagar);
        fondoCuentaBancoo.setSaldo(saldo);
        fondoCuentaBancoo.setConcepto("ABONO " + paciente);
        
        fondoBancoDAO.crearFondoCuentaBanco(fondoCuentaBancoo);
        
        fondoBancoDAO.actualizarfindobancofijo(saldo + totalpagar);
        //fALTA POR AGREGAR COSASA
        
    }

//    private void llenarCmbTipoPago() throws SQLException {
//        tipopagodao = new TipoPagoDAO(conexion.conectar2());
//        categoriaspagos.addAll(tipopagodao.obtenerTodos());
//        cmbTipoPago.getItems().addAll(categoriaspagos);
//        cmbTipoPago.setOnAction(event -> {
//            CategoriaPago selectCategoriaPago = cmbTipoPago.getValue();
//            id_catPago = selectCategoriaPago.getId();

//            nombre_catPAgo = selectCategoriaPago.getNombre();
//            tabla.setDisable(false);
//            cmbFormaPagos.setDisable(false);
//        });
//    }
    private void llamarReciboPagp() {
        double totalpagar = Double.parseDouble(lblTotalPagar.getText());
        ReporteC repc = new ReporteC("TicketPagoAnticipoColumnas2");

//        if (id_catPago < 3) {

        repc.generarReportePago(idPacientePago, id_pago, totalpagar);
//        } else {
//            repc.generarReportePagoFiniquito(idPacientePago, id_pago, totalpagar);
//        }

        Stage stage = (Stage) btnPagar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionIngresarContra(ActionEvent event) {
        if (txfContra.getText().equals(contraaingresar)) {
            alertaSuccess.setHeaderText("CONTRASEÑA VALIDA");
            alertaSuccess.setContentText("CONTRASEÑA VALIDA, CANTIDAD INGRESADA: " + precioGuardado);
            alertaSuccess.showAndWait();

            pagos.get(0).setPrecio_unitario_pago(precioGuardado);
            pagos.get(0).setTotal_pago(precioGuardado);
            cambiarDatos();
            apnCuadrodeDialogo.setVisible(false);
        } else {
            alertaError.setHeaderText("CONTRASEÑA INCORRECTA");
            alertaError.setContentText("CONTACTE AL PERSONAL AUTORIZADO PARA INGRESARLA.");
            alertaError.showAndWait();
            pagos.get(0).setPrecio_unitario_pago(precioaAterirorGuardado);
            pagos.get(0).setTotal_pago(precioaAterirorGuardado);
        }
        tabla.refresh();
    }

    @FXML
    private void accionCancelarContra(ActionEvent event) {
        apnCuadrodeDialogo.setVisible(false);
        pagos.get(0).setPrecio_unitario_pago(precioaAterirorGuardado);
        pagos.get(0).setTotal_pago(precioaAterirorGuardado);
        tabla.refresh();
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
