/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Ministracion;
import clase.Proveedor;
import clase.Rubro;
import clases_hospital.ComprasInternas;
import clases_hospital.ComprasInternasDetalle;
import clases_hospital_DAO.ComprasInternasDAO;
import clases_hospital_DAO.ComprasInternasDetalleDAO;
import clases_hospital_DAO.MinistracionDAO;
import clases_hospital_DAO.ProveedorDAO;
import clases_hospital_DAO.RubrosDAO;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ComprasInternasController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<Ministracion> ministraciones = FXCollections.observableArrayList();
    ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();
    ObservableList<Rubro> rubros = FXCollections.observableArrayList();
    ObservableList<ComprasInternasDetalle> comprasinternasdetalles = FXCollections.observableArrayList();

    Conexion conexion = new Conexion();
    Connection con;

    MinistracionDAO ministraciondao;
    ProveedorDAO proveedordao;
    RubrosDAO rubrodao;

    ComprasInternasDetalleDAO comprasinternasdetalledao;
    ComprasInternasDAO comprasinternasdao;

    Ministracion ministracion;
    Proveedor proveedor;
    Rubro rubro;

    double descuentoConformato;
    double importeConFormato;
    double iva;
    double descuentoinfo;
    double importe;
    double totalPagar;
    double comision;
    double totalsComision;
    double subtotal;

    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnSalir;
    @FXML
    private Label lblDescuento;
    @FXML
    private Label lblSubTotal;
    @FXML
    private Label lblIVA;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblTotalComision;
    @FXML
    private TextField txfComision;
    @FXML
    private ComboBox<Rubro> cmbRubro;
    @FXML
    private TextField txfNombreComercial;
    @FXML
    private TextField txfRfc;
    @FXML
    private TextField txfProducto;
    @FXML
    private Button btnAgregar;
    @FXML
    private Label lblMinistracion;
    @FXML
    private Label lblTipoRubro;
    @FXML
    private Label lblCompraMes;
    @FXML
    private Label lblPorComprar;
    @FXML
    private DatePicker dtpFechaPedido;
    @FXML
    private TextField txfDiasEntrega;
    @FXML
    private DatePicker dtpFechaEntega;
    @FXML
    private TableView<ComprasInternasDetalle> tabla;
    @FXML
    private TableColumn colEliminar;
    @FXML
    private TableColumn<?, ?> colCodigo;
    @FXML
    private TableColumn<ComprasInternasDetalle, String> colModelo;
    @FXML
    private TableColumn<ComprasInternasDetalle, Integer> colCantidad;
    @FXML
    private TableColumn<ComprasInternasDetalle, Double> colPrecioUnitario;
    @FXML
    private TableColumn<ComprasInternasDetalle, Double> colDescuento;
    @FXML
    private TableColumn<?, ?> colImporte;
    @FXML
    private TableColumn<?, ?> colCalvePedido;
    @FXML
    private TableColumn<?, ?> colProducto;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private TextField txfRazonSocial;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabla.setEditable(true);
        txfComision.setDisable(true);
        try {
            // TODO
            llenarCmbProveedor();
            llenarCmbRubro();
        } catch (SQLException ex) {
            Logger.getLogger(ComprasInternasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionGenerar(ActionEvent event) throws SQLException {
        if (dtpFechaPedido.getValue() == null || txfDiasEntrega.getText().equals("")) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("LLENE LOS CAMPOS VACIOS");
            alertaError.showAndWait();
            String css = "-fx-border-color: red; -fx-border-width: 0.2px;";
            dtpFechaPedido.setStyle(css);
            txfDiasEntrega.setStyle(css);
        } else {
            con = conexion.conectar2();
            comprasinternasdetalledao = new ComprasInternasDetalleDAO(con);
            comprasinternasdao = new ComprasInternasDAO(con);

            ComprasInternas comprainterna = new ComprasInternas();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = "VP-CMP-" + currentDate.format(dateFormatter);

            Date fechaPedido = Date.valueOf(dtpFechaPedido.getValue());

            comprainterna.setFolioPedido(formattedDate);
            comprainterna.setCliente("");
            comprainterna.setFechaPedido(fechaPedido);
            comprainterna.setSubtotal(subtotal);
            comprainterna.setDescuento(descuentoConformato);
            comprainterna.setImpuesto((iva / (1 + (16 / 100))));
            comprainterna.setTotal(importe);
            comprainterna.setRubro(rubro.getId());
            comprainterna.setEstatusCompra(1);
            comprainterna.setIdSolicitudesPagos(1);
            comprainterna.setDiasTranscurridos(0);
            comprainterna.setMontoSolicitado(importe);
            comprainterna.setMontoAutorizado(0);
            comprainterna.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            comprainterna.setSolicitar_compra(false);
            comprainterna.setMonto_pagado(0.0);
            comprainterna.setSaldo_saldo(importe);
            comprainterna.setUsuario_solicitud(VPMedicaPlaza.userSystem);
    
            comprainterna.setId_proveedor(proveedor.getId());
            comprainterna.setComision(comision);
            comprainterna.setTotal_sin_comicion(importe - comision);
            comprainterna.setId_confirmacion_autorizacion(1);
            comprainterna.setId_estatus_autorizacion(1);

            int id_compras_internasp = comprasinternasdao.insertarRegresarIdDatosCompletos(comprainterna);

            if (id_compras_internasp > 0) {
               
                for (ComprasInternasDetalle comprainternadetalle : comprasinternasdetalles) {
                    comprainternadetalle.setIdComprasInternasp(id_compras_internasp);
                    comprasinternasdetalledao.insertar(comprainternadetalle);
                  
                }

                rubrodao = new RubrosDAO(conexion.conectar2());
                rubrodao.actualizar(rubro);

                alertaSuccess.setHeaderText("PEDIDO DE COMPRA GENERADO CON EXITO");
                alertaSuccess.setContentText("EN ESPERA DE SU APROVACION");
                alertaSuccess.showAndWait();

                Stage stage = (Stage) btnGenerar.getScene().getWindow();
                stage.close();
            } else {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("Se produjo un error al intentar procesar su solicitud. Por favor, contacte al personal de sistemas.".toUpperCase());
                alertaError.showAndWait();
            }
        }
    }

    @FXML
    private void accionSallir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionAgregar(ActionEvent event) {
        ComprasInternasDetalle compradetalle = new ComprasInternasDetalle();
        compradetalle.setCodigo("VP-CMI-" + encrypt(txfProducto.getText()));
        compradetalle.setClavePedido(encrypt(txfProducto.getText()).toUpperCase());
        compradetalle.setProducto(txfProducto.getText());
        compradetalle.setModelo("");
        compradetalle.setCantidad(1);
        compradetalle.setPrecioUnitario(0);
        compradetalle.setDescuento(0);
        compradetalle.setImporte(0);

        comprasinternasdetalles.add(compradetalle);
        llenarTabla();

        txfProducto.setText("");
        txfComision.setDisable(false);
    }

    @FXML
    private void accionFechaPedidoQuitarCss(ActionEvent event) {
        if (dtpFechaPedido.getValue() == null) {
            dtpFechaPedido.setStyle("-fx-border-color: red; -fx-border-width: 0.2px;");
        } else {
            dtpFechaPedido.setStyle("");
        }
    }

    @FXML
    private void accionDiasEntegasQuitarCss(KeyEvent event) {
        if (txfDiasEntrega.getText().equals("")) {
            txfDiasEntrega.setStyle("-fx-border-color: red; -fx-border-width: 0.2px;");
        } else {
            txfDiasEntrega.setStyle("");
        }
    }

    private void llenarCmbProveedor() throws SQLException {
        proveedordao = new ProveedorDAO(conexion.conectar2());
        proveedores.addAll(proveedordao.obtenerTodos());
        AutoCompletionBinding<Proveedor> provedorlista = TextFields.bindAutoCompletion(txfRazonSocial, proveedores);
        provedorlista.setPrefWidth(1000);
        provedorlista.setOnAutoCompleted(event -> {
            proveedor = event.getCompletion();
            txfNombreComercial.setText(proveedor.getNombreComercial());
            txfRfc.setText(proveedor.getRfc());

        });
//        List<Proveedor> listaproveedores = llenarTXFAlimentos();
//        cmbRazonSocial.setOnAction(e -> {
//            proveedor = cmbRazonSocial.getValue();
//            txfNombreComercial.setText(proveedor.getNombreComercial());
//            txfRfc.setText(proveedor.getRfc());
//        });
    }

    private void llenarCmbRubro() throws SQLException {
        rubrodao = new RubrosDAO(conexion.conectar2());
        rubros.addAll(rubrodao.obtenerTodos());
        cmbRubro.setItems(rubros);
        cmbRubro.setOnAction(e -> {
            rubro = cmbRubro.getValue();

            if (rubro != null) {
                try {
                    llenarMinistracion();
                } catch (SQLException ex) {
                    alertaSuccess.setHeaderText("Error");
                    alertaSuccess.setContentText("Error... \n" + ex);
                    alertaSuccess.showAndWait();
                }
            }
        });
    }

    private void llenarMinistracion() throws SQLException {
        ministraciondao = new MinistracionDAO(conexion.conectar2());
        ministracion = ministraciondao.obtenerPorId(rubro.getMinistracion());

        lblMinistracion.setText(ministracion.getNombre());
        lblTipoRubro.setText("$" + rubro.getMonto());
        lblCompraMes.setText("$" + rubro.getGasto_mintracion());
        lblPorComprar.setText("$" + (rubro.getMonto() - rubro.getGasto_mintracion()));
    }

    private static String encrypt(String nombre) {
        try {
            // Obtener una instancia de MessageDigest con el algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Calcular el hash MD5 de la cadena de entrada
            byte[] messageDigest = md.digest(nombre.getBytes());

            // Convertir los primeros 5 bytes del hash en una representación hexadecimal
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);

            // Rellenar con ceros si es necesario para tener una longitud de 5 caracteres
            while (hashText.length() < 5) {
                hashText = "0" + hashText;
            }

            return hashText.substring(0, 5);
        } catch (NoSuchAlgorithmException e) {
            // Manejo de excepciones si ocurre un error al obtener la instancia del algoritmo MD5
            e.printStackTrace();
            return null;
        }
    }

    private void llenarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        colProducto.setCellValueFactory(new PropertyValueFactory("producto"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("precioUnitario"));
        colDescuento.setCellValueFactory(new PropertyValueFactory("descuento"));
        colImporte.setCellValueFactory(new PropertyValueFactory("importe"));
        colCalvePedido.setCellValueFactory(new PropertyValueFactory("clavePedido"));

        editarTabla();
        genrarBotonEliminar();

        tabla.setItems(comprasinternasdetalles);
    }

    private void editarTabla() {
        DecimalFormat df = new DecimalFormat("0.00");
        colModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        colModelo.setOnEditCommit(event -> {
            ComprasInternasDetalle comprainternaseleccionada = event.getRowValue();
            comprainternaseleccionada.setModelo(event.getNewValue());

            tabla.refresh();
        });

        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colCantidad.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            ComprasInternasDetalle comprainternaseleccionada = event.getTableView().getItems().get(event.getTablePosition().getRow());

            int cantidadAnterior = comprainternaseleccionada.getCantidad();

            // actualizar el valor de cantidad en el objeto Consumo
            comprainternaseleccionada.setCantidad(event.getNewValue());

            int cantidad = comprainternaseleccionada.getCantidad();
            double preciounitario = comprainternaseleccionada.getPrecioUnitario();
            double descuento = comprainternaseleccionada.getDescuento();
            double importeconformatodecimal = Double.parseDouble(df.format(((cantidad * preciounitario) - descuento)));

            if (esMayorOIgual(importeconformatodecimal)) {
                alertaError.setHeaderText(null);
                alertaError.setTitle("ALERTA");
                alertaError.setContentText("LA CANTIDAD INGRESADA SUPERA EL GASTO DE ESTA MINISTRACION");
                alertaError.showAndWait();
                comprainternaseleccionada.setCantidad(cantidadAnterior);
            } else {
                comprainternaseleccionada.setImporte(importeconformatodecimal);
                restarGastoMinistracion(importeconformatodecimal);
                actializarDatos();
            }

            tabla.refresh();
        });

        colPrecioUnitario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrecioUnitario.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            ComprasInternasDetalle comprainternaseleccionada = event.getTableView().getItems().get(event.getTablePosition().getRow());

            double preciounitarioanterior = comprainternaseleccionada.getPrecioUnitario();

            // actualizar el valor de cantidad en el objeto Consumo
            comprainternaseleccionada.setPrecioUnitario(event.getNewValue());

            int cantidad = comprainternaseleccionada.getCantidad();
            double preciounitario = comprainternaseleccionada.getPrecioUnitario();
            double descuento = comprainternaseleccionada.getDescuento();
            double importeconformatodecimal = Double.parseDouble(df.format(((cantidad * preciounitario) - descuento)));

            if (esMayorOIgual(importeconformatodecimal)) {
                alertaError.setHeaderText(null);
                alertaError.setTitle("ALERTA");
                alertaError.setContentText("LA CANTIDAD INGRESADA SUPERA EL GASTO DE ESTA MINISTRACION");
                alertaError.showAndWait();
                comprainternaseleccionada.setPrecioUnitario(preciounitarioanterior);
            } else {
                comprainternaseleccionada.setImporte(importeconformatodecimal);
                restarGastoMinistracion(importeconformatodecimal);
                actializarDatos();
            }

            tabla.refresh();
        });

        colDescuento.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colDescuento.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            ComprasInternasDetalle comprainternaseleccionada = event.getTableView().getItems().get(event.getTablePosition().getRow());
            double descunetoanteriro = comprainternaseleccionada.getDescuento();
            // actualizar el valor de cantidad en el objeto Consumo
            comprainternaseleccionada.setDescuento(event.getNewValue());

            int cantidad = comprainternaseleccionada.getCantidad();
            double preciounitario = comprainternaseleccionada.getPrecioUnitario();
            double descuento = comprainternaseleccionada.getDescuento();
            double importeconformatodecimal = Double.parseDouble(df.format(((cantidad * preciounitario) - descuento)));

            if (esMayorOIgual(importeconformatodecimal)) {
                alertaError.setHeaderText(null);
                alertaError.setTitle("ALERTA");
                alertaError.setContentText("LA CANTIDAD INGRESADA SUPERA EL GASTO DE ESTA MINISTRACION");
                alertaError.showAndWait();
                comprainternaseleccionada.setDescuento(descunetoanteriro);
            } else {
                comprainternaseleccionada.setImporte(importeconformatodecimal);
                restarGastoMinistracion(importeconformatodecimal);
                actializarDatos();
            }
            tabla.refresh();
        });

        colModelo.setEditable(true);
        colCantidad.setEditable(true);
        colPrecioUnitario.setEditable(true);
        colDescuento.setEditable(true);
    }

    private void actializarDatos() {
        DecimalFormat df = new DecimalFormat("0.00");

        descuentoinfo = 0.0;
        importe = 0.0;

        for (int i = 0; i < tabla.getItems().size(); i++) {
            descuentoinfo += tabla.getItems().get(i).getDescuento();
            importe += tabla.getItems().get(i).getImporte();
        }

        if (importe > 0) {
            descuentoConformato = Double.parseDouble(df.format(descuentoinfo));
            importeConFormato = Double.parseDouble(df.format(importe)) + descuentoConformato;
            iva = Double.parseDouble(df.format(importeConFormato * 0.16));
            subtotal = Double.parseDouble(df.format((importeConFormato / 1.16)));
            totalPagar = importe;
            comision = 0;

            lblDescuento.setText("$" + descuentoConformato);
            lblSubTotal.setText("$" + subtotal);
            lblIVA.setText("$" + iva);
            lblTotal.setText("$" + importe);
            lblTotalPagar.setText("$" + importe);
            lblTotalComision.setText("$" + (importe - comision));

        }
    }

    private boolean esMayorOIgual(double importe) {
      
        return importe > (rubro.getMonto() - rubro.getGasto_mintracion()); //; > porcentajeDelNumeroOriginal;
    }

    private void genrarBotonEliminar() {
        Callback<TableColumn<ComprasInternasDetalle, String>, TableCell<ComprasInternasDetalle, String>> eliminar = (TableColumn<ComprasInternasDetalle, String> param) -> {
            final TableCell<ComprasInternasDetalle, String> cell = new TableCell<ComprasInternasDetalle, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEliminar = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        ComprasInternasDetalle compradetalle = getTableView().getItems().get(getIndex());
                        ImageView eliminar = new ImageView("/img/icons/icons8-eliminar-50.png");
                        eliminar.setFitHeight(20);
                        eliminar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEliminar.setOnAction(event -> {
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de elimiar el pedido?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                if (compradetalle != null) {
                                    sumarGastoMinistracion(compradetalle.getImporte());
                                    comprasinternasdetalles.remove(compradetalle);
                                    tabla.refresh();
                                }
                            }
                        });
                        setGraphic(btnEliminar);
                        setText(null);
                        btnEliminar.setGraphic(eliminar);
                    }
                }
            };
            return cell;
        };

        colEliminar.setCellFactory(eliminar);
    }

    private void restarGastoMinistracion(double importeTotal) {
        DecimalFormat df = new DecimalFormat("0.00");
        double gastoActual = rubro.getGasto_mintracion();
        rubro.setGasto_mintracion(Double.parseDouble(df.format((gastoActual + importeTotal))));
        lblCompraMes.setText("$" + rubro.getGasto_mintracion());
        lblPorComprar.setText("$" + df.format((rubro.getMonto() - rubro.getGasto_mintracion())));
    }

    private void sumarGastoMinistracion(double importeTotal) {
        DecimalFormat df = new DecimalFormat("0.00");
        double gastoActual = rubro.getGasto_mintracion();
        rubro.setGasto_mintracion(Double.parseDouble(df.format((gastoActual - importeTotal))));
        lblCompraMes.setText("$" + rubro.getGasto_mintracion());
    }

    @FXML
    private void accionComision(ActionEvent event) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (txfComision.getText().equals("")) {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ALERTA");
            alertaError.setContentText("INGRESE UN VALOR PARA CONTINUAR");
            alertaError.showAndWait();
            btnGenerar.setDisable(true);
        } else {
            if (txfComision.getText().equals("$0.00") || Double.parseDouble(txfComision.getText()) == 0 || txfComision.getText().equals("$0.00")) {
               
                totalPagar = Double.parseDouble(df.format(totalPagar - comision));
                comision = 0;
            } else {
                String texto = txfComision.getText();
                String[] partes = texto.split("\\$");
                comision = Double.parseDouble(partes[0]);
               
                totalPagar = Double.parseDouble(df.format(totalPagar - comision));
            }

            lblTotalPagar.setText("$" + totalPagar);
            lblTotalComision.setText("$" + importe);
            txfComision.setText("$" + comision);

            btnGenerar.setDisable(false);
      
         
        }

    }

}
