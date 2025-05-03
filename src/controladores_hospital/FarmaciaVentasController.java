/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Insumo;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class FarmaciaVentasController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Connection conUsuarios = conexion.conectar2();
    private int idInsumo;
    int modo = 0;
    private double montoHataElMoneto;
    private boolean mandarCuenta = false;

    int idPaciente = 0;
    String folioPaciente = null;

    String ticket = null;
    String modoVenta = null;
    String tipoPago = null;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private TableColumn<Insumo, String> clmArticulo;
    @FXML
    private TableColumn<Insumo, Integer> clmPiezas;
    @FXML
    private TableColumn<Insumo, Double> clmCosto;
    @FXML
    private TableColumn<Insumo, ?> clmCancelar;
    @FXML
    private TextField txfBuscador;
    @FXML
    private Text txtUsuario;
    @FXML
    private TextField txfNombreCliente;
    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private Text txtTotalPago;
    @FXML
    private TextField txfMontoRecibido;
    @FXML
    private ChoiceBox<String> choiseBoxMetodoDePago;
    @FXML
    private Text txtCambio;
    @FXML
    private Text txtFecha;
    @FXML
    private Button btnPagar;
    @FXML
    private Text txtTotalArticulos;
    @FXML
    private TextField txfNumeroDePiezas;
    @FXML
    private Button btnAgregarArticulo;
    @FXML
    private TextField txfNumeroDeCajas;
    @FXML
    private JFXComboBox<String> choiseBoxModoDeVenta;
    @FXML
    private TextField txfBuscadorAlimento;
    @FXML
    private Button btnBuscarPaciente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            // TODO
////            // buscador();
////            piezasRestriccion();
////            cajasRestriccion();
////            llenarChoiseBoxModoDeVenta();
////            llenarChoiseBoxMetodoDePago();
////            montoRecibido();
////            NombreCajero();
////            buscarCliente();
//        } catch (SQLException ex) {
//            Logger.getLogger(FarmaciaVentasController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void actionBtnAgregarArticulo(ActionEvent event) throws SQLException {
//        // OBJETO INSUMO USADO PARA LLENAR LAS TABLAS
//        Insumo insumo;
//        // DATOS INSUMO MEDICAMENTOS
//        int nuevoId = 0;
//        String tipo, marca, precentacion, medida;
//        double costo;
//        //DATOS INSUMO ALIMENTOS
//        int idAlimento = 0;
//        String nombreAlimento = null;
//        double costoAlimento = 0;
//
//        /*
//         *  LLENAMOS LA TABLA CUANDO ES UN MEDICAMENTO 
//         */
//        if (modoVenta.equals("FARMACIA")) {
//            if (txfBuscador.getText().isEmpty() || txfNumeroDePiezas.getText().isEmpty() || txfNumeroDeCajas.getText().isEmpty()) {
//                alertaError.setTitle("ERROR!");
//                alertaError.setHeaderText("CAMPOS VACIOS");
//                alertaError.setContentText("FAVOR DE VERIFICAR\n(1) EL NOMBRE DEL MEDICAMENTO\n(2) LA CANTIDAD DE CAJAS\n(3) LA CANTIDAD DE PIEZAS QUE TRAE LA CAJA");
//                alertaError.showAndWait();
//            } else {
//                clmArticulo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
//                clmPiezas.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
//                clmCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
//
//                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT i.id, i.nombre, i.marca, i.presentacion, i.medida, i.precio_caja FROM insumos i WHERE i.id ='" + idInsumo + "'");
//
//                try {
//                    while (rs.next()) {
//                        nuevoId = rs.getInt(1);
//                        tipo = rs.getString(2);
//                        marca = rs.getString(3);
//                        precentacion = rs.getString(4);
//                        medida = rs.getString(5);
//                        costo = rs.getDouble(6);
//                        String nombreCompleto = tipo + " " + precentacion + " " + marca + " de " + medida;
////                        insumo = new Insumo(nuevoId, nombreCompleto, Integer.valueOf(txfNumeroDeCajas.getText()), precentacion, marca, medida, costo, modo, Integer.valueOf(txfNumeroDePiezas.getText()));
////                        tabla.getItems().add(insumo);
////                        actualizarMontoHastaElMomento();
//                    }
//                } catch (Exception e) {
//
//                }
//                txfBuscador.clear();
//                txfNumeroDeCajas.clear();
//                txfNumeroDePiezas.clear();
//            }
//
//            /*
//             *  LLENAMOS LA TABLA CUANDO ES UN ALIMENTO 
//             */
//        } else if (modoVenta.equals("ALIMENTOS")) {
//            if (txfBuscadorAlimento.getText().isEmpty() || txfNumeroDeCajas.getText().isEmpty() || txfNumeroDePiezas.getText().isEmpty()) {
//                alertaError.setTitle("ERROR!");
//                alertaError.setHeaderText("CAMPOS VACIOS");
//                alertaError.setContentText("FAVOR DE VERIFICAR\n(1) EL NOMBRE DEl ALIMENTO\n(2) LA CANTIDAD DE CAJAS\n(3) LA CANTIDAD DE PIEZAS QUE TRAE LA CAJA");
//                alertaError.showAndWait();
//            } else {

//                clmArticulo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
//                clmPiezas.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
//                clmCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
//
//                Statement stmt = con.createStatement();
//                ResultSet rs = stmt.executeQuery("SELECT pa.id ,pa.nombre, pa.precio FROM paquetesAlimentos pa\n"
//                        + "  WHERE pa.id ='" + idInsumo + "'");
//
//                try {
//                    while (rs.next()) {
//                        idAlimento = rs.getInt(1);
//                        nombreAlimento = rs.getString(2);
//                        costoAlimento = rs.getDouble(3);
//
////                        insumo = new Insumo(idAlimento, nombreAlimento, costoAlimento, Integer.valueOf(txfNumeroDeCajas.getText()), modo, Integer.valueOf(txfNumeroDePiezas.getText()));
////                        tabla.getItems().add(insumo);
////                        actualizarMontoHastaElMomento();
//                    }
//                } catch (Exception e) {
//                }
//                txfBuscadorAlimento.clear();
//                txfNumeroDeCajas.clear();
//                txfNumeroDePiezas.clear();
//            }
//        }

    }

//    private void buscador(String consulta, int modo) throws SQLException {
//        ArrayList<Insumo> arrayMedicamento = new ArrayList<>();
//        ArrayList<Insumo> arrayAlimento = new ArrayList<>();
//        String sql = consulta;
//        int modoBuscador = modo;
//        Statement stmt = con.createStatement();
//        Insumo insu, insumoAlimento;
//        /*
//         * MODO 1 BUSCADOR DE MEDICAMENTOS
//         */
//        if (modoBuscador == 1) {

//            ResultSet rs = stmt.executeQuery(sql);
//            try {
//                while (rs.next()) {
//
//                    String nombreConcatenado = rs.getString(2) + " " + rs.getString(3) + "  " + rs.getString(4) + "  " + rs.getString(5) + " - EXISTENCIA: " + rs.getInt(6) + ",";
//
////                    insu = new Insumo(rs.getInt(1), nombreConcatenado, rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
//
//                    arrayMedicamento.add(insu);
//                }
//
//                AutoCompletionBinding<Insumo> insumos = TextFields.bindAutoCompletion(txfBuscador, arrayMedicamento);
//                insumos.setPrefWidth(800);
//
//                insumos.setOnAutoCompleted(event -> {
//                    Insumo selectInsumo = event.getCompletion();
//                    idInsumo = selectInsumo.getId();

//                });
//
//            } catch (Exception e) {

//                alertaError.setTitle(null);
//                alertaError.setContentText("HA PASADO MUCHO TIEMPO INACTIVO\nPORFAVOR REINICIE LA VISTA");
//                alertaError.setHeaderText("ALERTA DE INACTIVIDAD");
//                alertaError.showAndWait();
//            }
//
//            /*
//             * MODO 2 BUSCADOR DE ALIMENTOS
//             */
//        } else {

//
//            ResultSet rs = stmt.executeQuery(sql);

//            try {
//                while (rs.next()) {
//
////                    insumoAlimento = new Insumo(rs.getInt(1), rs.getString(2));
//
////                    arrayAlimento.add(insumoAlimento);
//                }
//
//                AutoCompletionBinding<Insumo> insumos = TextFields.bindAutoCompletion(txfBuscadorAlimento, arrayAlimento);
//                insumos.setPrefWidth(800);
//
//                insumos.setOnAutoCompleted(event -> {
//                    Insumo selectInsumo = event.getCompletion();
//                    idInsumo = selectInsumo.getId();

//                });
//            } catch (Exception e) {

//            }
//
//        }
//    }
//
//    private void piezasRestriccion() {
//        txfNumeroDePiezas.setOnKeyTyped(event -> {
//            String caracter = event.getCharacter();
//            if (!caracter.matches("[0-9]")) {
//                event.consume(); // Consumimos el evento si no es un número o un punto
//            }
//        });
//    }
//
//    private void cajasRestriccion() {
//        txfNumeroDeCajas.setOnKeyTyped(event -> {
//            String caracter = event.getCharacter();
//            if (!caracter.matches("[0-9]")) {
//                event.consume(); // Consumimos el evento si no es un número o un punto
//            }
//        });
//    }
//
//    private void llenarChoiseBoxModoDeVenta() throws SQLException {
//
//        Statement stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT m.tipo FROM ventasModos m WHERE m.id_estatus = 1");
//
//        try {
//            while (rs.next()) {
//                choiseBoxModoDeVenta.getItems().addAll(rs.getString(1));
//            }
//        } catch (Exception e) {
//        }
//
//        choiseBoxModoDeVenta.setOnAction(e -> {
//            modoVenta = choiseBoxModoDeVenta.getValue();

//
//            if (modoVenta != null) {
//                txfBuscador.setDisable(false);
//                txfBuscadorAlimento.setDisable(false);
//                txfNumeroDeCajas.setDisable(false);
//                txfNumeroDePiezas.setDisable(false);
//                btnAgregarArticulo.setDisable(false);
//                txfNombreCliente.setDisable(false);
//                btnBuscarPaciente.setDisable(false);
//                choiseBoxMetodoDePago.setDisable(false);
//                txfMontoRecibido.setDisable(false);
//
//                /*
//                 *   MODO Farmacia
//                 */
//                if (modoVenta.equals("FARMACIA")) {
//                    modo = 1;
//                    choiseBoxModoDeVenta.setDisable(true);
//                    choiseBoxModoDeVenta.setStyle("-fx-background-color: #0c569f;  -fx-text-fill: white;");
//                    choiseBoxModoDeVenta.setOpacity(0.99);
//                    txfBuscador.clear();
//                    txfNombreCliente.clear();
//                    txfNumeroDeCajas.clear();
//                    txfNumeroDeCajas.setPromptText("CAJAS");
//                    txfNumeroDePiezas.clear();
//                    txfBuscadorAlimento.setVisible(false);
//                    txfBuscador.setVisible(true);
//
//                    String sql = "SELECT i.id, i.nombre, i.marca, i.presentacion, i.medida, iv.total_existencia FROM insumos i\n"
//                            + "  INNER JOIN inventarios iv ON i.id = iv.id_insumo\n"
//                            + "    WHERE i.tipo_insumo !=2 ";
//
//                    try {
//                        buscador(sql, modo);
//                    } catch (SQLException ex) {

//                        Logger.getLogger(FarmaciaVentasController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                    /*
//                     *   MODO ALIMENTOS
//                     */
//                } else if (modoVenta.equals("ALIMENTOS")) {
//                    modo = 2;
//                    choiseBoxModoDeVenta.setDisable(true);
//                    choiseBoxModoDeVenta.setStyle("-fx-background-color: #0c569f;  -fx-text-fill: white;");
//                    choiseBoxModoDeVenta.setOpacity(0.99);
//                    txfBuscador.clear();
//                    txfNombreCliente.clear();
//                    txfBuscadorAlimento.clear();
//                    txfNumeroDeCajas.clear();
//                    txfNumeroDePiezas.clear();
//                    txfNumeroDeCajas.setPromptText("PLATILLO");
//                    txfBuscador.setVisible(false);
//                    txfBuscadorAlimento.setVisible(true);
//                    String sql = "SELECT pa.id ,pa.nombre, pa.descripcion, apa.precio_paquete FROM paquetesAlimentos pa\n"
//                            + "    INNER JOIN armadoPaqueteAlimento apa ON pa.id = apa.id_paquete";
//
//                    try {
//                        buscador(sql, modo);
//                    } catch (SQLException ex) {

//                        Logger.getLogger(FarmaciaVentasController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            } else {
//            }
//        });
//
//    }
//
//    private void llenarChoiseBoxMetodoDePago() throws SQLException {
//
//        Statement stmtMetodoDePago = con.createStatement();
//        ResultSet rsMetodoDePago = stmtMetodoDePago.executeQuery("SELECT fp.tipo FROM forma_pagos fp");
//
//        try {
//            while (rsMetodoDePago.next()) {
//                choiseBoxMetodoDePago.getItems().add(rsMetodoDePago.getString(1));
//            }
//        } catch (Exception e) {
//        }
//        choiseBoxMetodoDePago.setOnAction(e -> {
//            
//            tipoPago = choiseBoxMetodoDePago.getValue();

//            if (tipoPago != null) {
//                btnPagar.setDisable(false);
//            }
//
//        });
//

//    }
//
//    private void montoRecibido() {
//        txfMontoRecibido.setOnKeyTyped(event -> {
//            String numeros = event.getCharacter();
//            if (!numeros.matches("[0-9.]")) {
//                event.consume(); // Consumimos el evento si no es un número o un punto
//            } else if (numeros.equals(".") && txfMontoRecibido.getText().contains(".")) {
//                event.consume(); // Consumimos el evento si ya existe un punto en el texto
//            } else if (txfMontoRecibido.getText().contains(".")) {
//                int index = txfMontoRecibido.getText().indexOf(".");
//                if (txfMontoRecibido.getText().substring(index + 1).length() >= 2) {
//                    event.consume(); // Consumimos el evento si ya hay dos decimales
//                }
//            }
//        });
//
//    }
//
//    private void actualizarMontoHastaElMomento() {
//
////        montoHataElMoneto = 0; // reiniciar el monto
////        for (int i = 0; i < tabla.getItems().size(); i++) {
//////            montoHataElMoneto += tabla.getItems().get(i).getCantidad() * tabla.getItems().get(i).getCosto(); // sumar el monto correspondiente a cada elemento de la tabla
////        }
////        txtTotalPago.setText(String.valueOf(montoHataElMoneto));
//    }
//
//    private void NombreCajero() throws SQLException {
//        Statement stmt = conUsuarios.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT u.nombre FROM usuarios u WHERE u.id ='" + userSystem + "'");
//        try {
//            while (rs.next()) {
//                txtUsuario.setText(rs.getString(1));
//            }
//        } catch (Exception e) {
//        }
//    }

    @FXML
    private void actionBtnPagar(ActionEvent event) throws SQLException {
//        Statement stmtArmarPaquete = con.createStatement();
//        CallableStatement stmtDescontarInsumoDeInventario = null;
//        String sqlDescontarInsumoDeInventario = "{call DescontarInsumoDeInventario (?,?)}";
//
//        ArrayList<Insumo> arrayArmarPaquete = new ArrayList<>();
//        Insumo insumoPaquete;
//
//        int idInsumoArmarPaquete, cantidadUtilizadaDeInsumoParaArmarPaquete;
//        //
//        double montoRecibido, totalPago, cambio, pagoPorCaja;
//        int piezasTotalesUnitarias;
//        int numeroDeArticulosVendidos = 0;
        //

//        totalPago = Double.valueOf(txtTotalPago.getText());
////
////        for (int tam = 0; tam < tabla.getItems().size(); tam++) {
////            numeroDeArticulosVendidos += tabla.getItems().get(tam).getCantidad();
////        }

//
//        //INCERTAMOS EN LA TABLA VENTA
//        insertarVenta(numeroDeArticulosVendidos);

        // MODO MANDAR A LA CUENTA DEL PACIENTE
//        if (mandarCuenta == true) {
//            montoRecibido = 0;
//            for (int i = 0; i < tabla.getItems().size(); i++) {
//
//                if (tabla.getItems().get(i).getModo() == 1) {   // MODO 1: LOS OBJETOS A DESCONTAR DEL INVENTARIO SON DE TIPO INSUMO(MEDICAMENTOS)

//
//                    piezasTotalesUnitarias = tabla.getItems().get(i).getPiezas() * tabla.getItems().get(i).getCantidad(); //calculamos el total de unidades que vamos a descontar segun el numero de cajas
//                    pagoPorCaja = tabla.getItems().get(i).getCantidad() * tabla.getItems().get(i).getCosto(); // calculamos el pago por cada caja (piezas vendidas x costo de la caja)
//
//                    // INSERTMOS EL DETALLE DE LA VENTA en la BD tabla ventaDetalle
//                    insertarVentaDetalle(tabla.getItems().get(i).getId(), //mandamos el id del insumo
//                            tabla.getItems().get(i).getNombre(),//mandamos el nombre del insumo
//                            tabla.getItems().get(i).getPiezas(), // mandamos el numero de cajas compradas
//                            tabla.getItems().get(i).getCantidad(),//mandamos la cantidad de piezas que trae la caja
//                            piezasTotalesUnitarias, // mandamos el total de unidades que se descontaron del inventario
//                            tabla.getItems().get(i).getCosto(), // mandamos el costo invibidual de cada caja
//                            pagoPorCaja);                        // mandamos el pago por las cajas
//
//                    // ENVIAMOS A LA CUENTA DEL PACIENTE SI LA OPCUION FUE SELECCIONADA
//                    
//                    
//                    
//                    if (mandarCuenta == true) {

//                        incertarConsumoAcuenta(tabla.getItems().get(i).getNombre(), // enviamos el tipo de insumo
//                                tabla.getItems().get(i).getCantidad(), // enviamos las piezas
//                                tabla.getItems().get(i).getCosto());//  enviamos el costo unitario
//                    } else {

//                    }
//                    
//                    
//                     
//                    try {
//                        //Descontams en la BD Insumos lo que se vendio(insumos vendidos)
//                        stmtDescontarInsumoDeInventario = con.prepareCall(sqlDescontarInsumoDeInventario); // preparamos el elementos para el procedimiento almacenado
//                        stmtDescontarInsumoDeInventario.setInt(1, tabla.getItems().get(i).getId());//cargamos el procedimeinto almacenado con un id (mandamos el id del insumo)
//                        stmtDescontarInsumoDeInventario.setInt(2, piezasTotalesUnitarias); //cargamos el procedimeinto almacenado con las piezas(mandamos el total de piezas de ese insumo a descontar)
//                        stmtDescontarInsumoDeInventario.execute();  // executamos el procedimiento almacenado
//                    } catch (Exception e) { // control de excepciones 
//                     }
//

//                    
//              
//                }
//
//                /*
//                
//                
//                    DESCONTAAR ALIEMNTOS
//                
//                
//                 */
//                if (tabla.getItems().get(i).getModo() == 2) {   // MODO 2: LOS OBJETOS A DESCONTAR DEL INVENTARIO SON DE TIPO PAQUETE(ALIMENTOS)
//
//                    piezasTotalesUnitarias = tabla.getItems().get(i).getPiezas() * tabla.getItems().get(i).getCantidad(); //calculamos el total de unidades que vamos a descontar segun el numero de cajas
//                    pagoPorCaja = tabla.getItems().get(i).getCantidad() * tabla.getItems().get(i).getCosto(); // calculamos el pago por cada caja (piezas vendidas x costo de la caja)
//
//                    // INSERTMOS EL DETALLE DE LA VENTA en la BD tabla ventaDetalle

//                    insertarVentaDetalle(tabla.getItems().get(i).getId(), //mandamos el id del insumo
//                            tabla.getItems().get(i).getNombre(),//mandamos el nombre del insumo
//                            tabla.getItems().get(i).getPiezas(), // mandamos el numero de cajas compradas
//                            tabla.getItems().get(i).getCantidad(),//mandamos la cantidad de piezas que trae la caja
//                            piezasTotalesUnitarias, // mandamos el total de unidades que se descontaron del inventario
//                            tabla.getItems().get(i).getCosto(), // mandamos el costo invibidual de cada caja
//                            pagoPorCaja);                        // mandamos el pago por las cajas
//
//                    // ENVIAMOS A LA CUENTA DEL PACIENTE SI LA OPCUION FUE SELECCIONADA
//                    
//                    
//                    if (mandarCuenta == true) {

//                        incertarConsumoAcuenta(tabla.getItems().get(i).getNombre(), // enviamos el tipo de insumo
//                                tabla.getItems().get(i).getCantidad(), // enviamos las piezas
//                                tabla.getItems().get(i).getCosto());//  enviamos el costo unitario
//                    } else {
//                        System.err.println("cuenta normal");
//                    }
//                    
//                    
//                     
//                    // consulta para traer los insumos que se requieren para armar un paquete(ingredientes que lleva el platillo)
//                    ResultSet rsArmarPaquete = stmtArmarPaquete.executeQuery("SELECT i.nombre, pa.id_insumoAlimento AS ID_INSUMO , pa.cantidad FROM armadoPaqueteAlimento pa\n"
//                            + "    INNER JOIN insumos i ON pa.id_insumoAlimento = i.id\n"
//                            + "    WHERE pa.id_paquete ='" + tabla.getItems().get(i).getId() + "'");
//
//                    try {
//
//                        while (rsArmarPaquete.next()) { // traemos los elementos y los guardamso en varianles
//                            idInsumoArmarPaquete = rsArmarPaquete.getInt(2);
//                            cantidadUtilizadaDeInsumoParaArmarPaquete = rsArmarPaquete.getInt(3);
//                            insumoPaquete = new Insumo(idInsumoArmarPaquete, cantidadUtilizadaDeInsumoParaArmarPaquete); // creamos un objeto de tipo insumo con los elementos traidos
//                            arrayArmarPaquete.add(insumoPaquete); // agragamos el objeto al arryList
//                        }
//                    } catch (Exception e) { // control de excepciones
//                        
//                    }
//                    for (Insumo insumo : arrayArmarPaquete) {// recorremos el arry con los insumos cargados anteriorment para descontarlos de la BD insumos
//                        int id = insumo.getId();
//                        int cantidad = insumo.getCantidad();
//                        try {
//                            stmtDescontarInsumoDeInventario = con.prepareCall(sqlDescontarInsumoDeInventario);// preparamos el procedimeinto almacenado
//                            stmtDescontarInsumoDeInventario.setInt(1, id); //cargamos el procedimeinto almacenado con un id (mandamos el id del insumo)
//                            stmtDescontarInsumoDeInventario.setInt(2, cantidad);//cargamos el procedimeinto almacenado con las piezas(mandamos el total de piezas de ese insumo a descontar)
//                            stmtDescontarInsumoDeInventario.execute();// executamos el procedimiento almacenado
//                        } catch (Exception e) {// control de excepciones
//                            
//                        }
//                    }
//
//                }
//
//            }
//            alertaInfo.setTitle("EXITO!");
//            alertaInfo.setContentText("PAGO SE CARGO A LA CUENTA CON EXITO");
//            alertaInfo.showAndWait();
//            tabla.getItems().clear();
//
//            /*
//            *   
//            *   MODO COBRO NORMAL
//            *
//            *
//             */
//        } else {
//            montoRecibido = Double.valueOf(txfMontoRecibido.getText());
//            if (txfMontoRecibido.getText().isEmpty()) {
//                alertaError.setTitle("ERROR!");
//                alertaError.setHeaderText("MONTO INSUFICIENTE");
//                alertaError.setContentText("POR FAVOR VERIFIQUE, QUE EL MONTO RECIBIDO CUBRA EL TOTAL DE LA CUENTA");
//                alertaError.showAndWait();
//            } else if (montoRecibido >= totalPago) {
//
//                cambio = montoRecibido - totalPago;
//              
//                txtCambio.setText(String.valueOf(Math.abs(cambio))); // volvemos pocitivo el cambie en caso de ser negativo
//              
//
//                for (int i = 0; i < tabla.getItems().size(); i++) {
//
//                    if (tabla.getItems().get(i).getModo() == 1) {   // MODO 1: LOS OBJETOS A DESCONTAR DEL INVENTARIO SON DE TIPO INSUMO(MEDICAMENTOS)
//                  
//
//                        piezasTotalesUnitarias = tabla.getItems().get(i).getPiezas() * tabla.getItems().get(i).getCantidad(); //calculamos el total de unidades que vamos a descontar segun el numero de cajas
//                        pagoPorCaja = tabla.getItems().get(i).getCantidad() * tabla.getItems().get(i).getCosto(); // calculamos el pago por cada caja (piezas vendidas x costo de la caja)
//
//                        // INSERTMOS EL DETALLE DE LA VENTA en la BD tabla ventaDetalle
//                        insertarVentaDetalle(tabla.getItems().get(i).getId(), //mandamos el id del insumo
//                                tabla.getItems().get(i).getNombre(),//mandamos el nombre del insumo
//                                tabla.getItems().get(i).getPiezas(), // mandamos el numero de cajas compradas
//                                tabla.getItems().get(i).getCantidad(),//mandamos la cantidad de piezas que trae la caja
//                                piezasTotalesUnitarias, // mandamos el total de unidades que se descontaron del inventario
//                                tabla.getItems().get(i).getCosto(), // mandamos el costo invibidual de cada caja
//                                pagoPorCaja);                        // mandamos el pago por las cajas
//
//                        try {
//                            //Descontams en la BD Insumos lo que se vendio(insumos vendidos)
//                            stmtDescontarInsumoDeInventario = con.prepareCall(sqlDescontarInsumoDeInventario); // preparamos el elementos para el procedimiento almacenado
//                            stmtDescontarInsumoDeInventario.setInt(1, tabla.getItems().get(i).getId());//cargamos el procedimeinto almacenado con un id (mandamos el id del insumo)
//                            stmtDescontarInsumoDeInventario.setInt(2, piezasTotalesUnitarias); //cargamos el procedimeinto almacenado con las piezas(mandamos el total de piezas de ese insumo a descontar)
//                            stmtDescontarInsumoDeInventario.execute();  // executamos el procedimiento almacenado
//                        } catch (Exception e) { // control de excepciones 
//                                 }
//
//                    
//                        
//                    }
//
//                    /*
//                
//                
//                    DESCONTAAR ALIEMNTOS
//                
//                
//                     */
//                    if (tabla.getItems().get(i).getModo() == 2) {   // MODO 2: LOS OBJETOS A DESCONTAR DEL INVENTARIO SON DE TIPO PAQUETE(ALIMENTOS)
//
//                        piezasTotalesUnitarias = tabla.getItems().get(i).getPiezas() * tabla.getItems().get(i).getCantidad(); //calculamos el total de unidades que vamos a descontar segun el numero de cajas
//                        pagoPorCaja = tabla.getItems().get(i).getCantidad() * tabla.getItems().get(i).getCosto(); // calculamos el pago por cada caja (piezas vendidas x costo de la caja)
//
//                        // INSERTMOS EL DETALLE DE LA VENTA en la BD tabla ventaDetalle
//                        insertarVentaDetalle(tabla.getItems().get(i).getId(), //mandamos el id del insumo
//                                tabla.getItems().get(i).getNombre(),//mandamos el nombre del insumo
//                                tabla.getItems().get(i).getPiezas(), // mandamos el numero de cajas compradas
//                                tabla.getItems().get(i).getCantidad(),//mandamos la cantidad de piezas que trae la caja
//                                piezasTotalesUnitarias, // mandamos el total de unidades que se descontaron del inventario
//                                tabla.getItems().get(i).getCosto(), // mandamos el costo invibidual de cada caja
//                                pagoPorCaja);                        // mandamos el pago por las cajas
//
//                        // consulta para traer los insumos que se requieren para armar un paquete(ingredientes que lleva el platillo)
//                        ResultSet rsArmarPaquete = stmtArmarPaquete.executeQuery("SELECT i.nombre, pa.id_insumoAlimento AS ID_INSUMO , pa.cantidad FROM armadoPaqueteAlimento pa\n"
//                                + "    INNER JOIN insumos i ON pa.id_insumoAlimento = i.id\n"
//                                + "    WHERE pa.id_paquete ='" + tabla.getItems().get(i).getId() + "'");
//
//                        try {
//
//                            while (rsArmarPaquete.next()) { // traemos los elementos y los guardamso en varianles
//                                idInsumoArmarPaquete = rsArmarPaquete.getInt(2);
//                                cantidadUtilizadaDeInsumoParaArmarPaquete = rsArmarPaquete.getInt(3);
//                                insumoPaquete = new Insumo(idInsumoArmarPaquete, cantidadUtilizadaDeInsumoParaArmarPaquete); // creamos un objeto de tipo insumo con los elementos traidos
//                                arrayArmarPaquete.add(insumoPaquete); // agragamos el objeto al arryList
//                            }
//                        } catch (Exception e) { // control de excepciones
//                           
//                        }
//                        for (Insumo insumo : arrayArmarPaquete) {// recorremos el arry con los insumos cargados anteriorment para descontarlos de la BD insumos
//                            int id = insumo.getId();
//                            int cantidad = insumo.getCantidad();
//                            try {
//                                stmtDescontarInsumoDeInventario = con.prepareCall(sqlDescontarInsumoDeInventario);// preparamos el procedimeinto almacenado
//                                stmtDescontarInsumoDeInventario.setInt(1, id); //cargamos el procedimeinto almacenado con un id (mandamos el id del insumo)
//                                stmtDescontarInsumoDeInventario.setInt(2, cantidad);//cargamos el procedimeinto almacenado con las piezas(mandamos el total de piezas de ese insumo a descontar)
//                                stmtDescontarInsumoDeInventario.execute();// executamos el procedimiento almacenado
//                            } catch (Exception e) {// control de excepciones

//                            }
//

//      
//                        }
//
//                    }
////                else {
////                  
////                }
//                }
//
//                alertaInfo.setTitle("EXITO!");
//                alertaInfo.setContentText("PAGO REALIZADO CON EXITO");
//                alertaInfo.showAndWait();
//                tabla.getItems().clear();
//
//            } else {
//                alertaError.setTitle("ERROR!");
//                alertaError.setHeaderText("MONTO INSUFICIENTE");
//                alertaError.setContentText("POR FAVOR VERIFIQUE, QUE EL MONTO RECIBIDO CUBRA EL TOTAL DE LA CUENTA");
//                alertaError.showAndWait();
//            }
//        }
//        txtCambio.setText(String.valueOf(0));
//        txtTotalPago.setText(String.valueOf(0));
//        txfMontoRecibido.clear();
//        choiseBoxMetodoDePago.setValue(null);
//        btnPagar.setDisable(true);
    }

    private void insertarVenta(int totalProductos) {
//        String cliente = null;
//        int numProductos = totalProductos;
//        int estatusCompra = 0;
//        double montoRecibido = 0;
//        if (mandarCuenta == true) {
//            estatusCompra = 1;
//        } else {
//            estatusCompra = 0;
//            montoRecibido = Double.valueOf(txfMontoRecibido.getText());
//        }
//
//        if (txfNombreCliente.getText().isEmpty()) {
//            cliente = "cliente no registrado";
//        } else {
//            cliente = txfNombreCliente.getText();
//        }
//        ticket = generarTicket(numProductos);
//
//        CallableStatement stmtIncerrtarVenta = null;
//        String sqlIncertarVenta = "{call InsertarVenta (?,?,?,?,?,?,?,?,?)}";
//        try {
//            stmtIncerrtarVenta = con.prepareCall(sqlIncertarVenta);
//            stmtIncerrtarVenta.setString(1, ticket);
//            stmtIncerrtarVenta.setString(2, cliente);
//            stmtIncerrtarVenta.setString(3, txtUsuario.getText());
//            stmtIncerrtarVenta.setInt(4, numProductos);
//            stmtIncerrtarVenta.setDouble(5, Double.valueOf(txtTotalPago.getText()));
//            stmtIncerrtarVenta.setDouble(6, montoRecibido);
//            stmtIncerrtarVenta.setDouble(7, Double.valueOf(txtCambio.getText()));
//            stmtIncerrtarVenta.setInt(8, estatusCompra);
//            stmtIncerrtarVenta.setInt(9, userSystem);
//            stmtIncerrtarVenta.execute();
//            
//
//        } catch (Exception e) {
//            e.printStackTrace();
//         
//        }
    }

    private void insertarVentaDetalle(int id_insumo, String nombreInsumo, int piezasCaja, int cajasVenta, int unidadesTotalesVendidas, double costoPorCaja, double costoTotal) throws SQLException {
//      
//        int idventa = 0;
//        int estatus = 0;
//        if (mandarCuenta == true) {
//            estatus = 1;
//        } else {
//            estatus = 0;
//        }
//        Statement stmtIdVenta = con.createStatement();
//        ResultSet rs = stmtIdVenta.executeQuery("SELECT v.id_venta FROM ventas v WHERE v.ticket ='" + ticket + "'");
//        try {
//            while (rs.next()) {
//                idventa = rs.getInt(1);
//            }
//        } catch (Exception e) {
//         
//        }
//
//        CallableStatement stmt = null;
//        String sql = "{call InsertarVentaDetalle (?,?,?,?,?,?,?,?,?,?)}";
//
//        try {
//            stmt = con.prepareCall(sql);
//            stmt.setInt(1, idventa);
//            stmt.setInt(2, id_insumo);
//            stmt.setString(3, nombreInsumo);
//            stmt.setInt(4, piezasCaja);
//            stmt.setInt(5, cajasVenta);
//            stmt.setInt(6, unidadesTotalesVendidas);
//            stmt.setDouble(7, costoPorCaja);
//            stmt.setDouble(8, costoTotal);
//            stmt.setInt(9, estatus);
//            stmt.setInt(10, userSystem);
//            stmt.execute();
//          
//        } catch (Exception e) {
//      
//        }

    }

    private String generarTicket(int totalProductos) {
//        String ticketGenerado = null;
//        String fechaHoraActualString = null;
//        LocalDateTime fechaHoraActual = LocalDateTime.now();
//        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        fechaHoraActualString = fechaHoraActual.format(formato);
//
//        ticketGenerado = "VP" + fechaHoraActualString + totalProductos;
//        return ticketGenerado;
        return "";
    }

    private void buscarCliente() throws SQLException {
//        int idPaciente;
//        Statement stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery("select id, nombre from pacientes");
//
//        ArrayList<Paciente> pacientes = new ArrayList<>();
//        pacientes.clear();
//        try {
//            while (rs.next()) {
//
//                pacientes.add(new Paciente(rs.getInt(1), rs.getString(2)));     // guardamos los pacientes que nos trajo la consulta en un arryList
//                idPaciente = rs.getInt(1);
//            }
//            TextFields.bindAutoCompletion(txfNombreCliente, pacientes); // usamos el metodo de aautocompletado pasando el textfield  y el arryList con el cual ara las comparaciones
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    private void actionBtnBuscarPaciente(ActionEvent event) throws SQLException {

//        if (txfNombreCliente.getText().isEmpty()) {
//            alertaError.setTitle(null);
//            alertaError.setContentText("CAMPOS VACIOS");
//            alertaError.showAndWait();
//        } else {
//
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT p.id, f.folio FROM pacientes p\n"
//                    + "    INNER JOIN folios f ON p.id = f.id_paciente\n"
//                    + "    WHERE p.nombre = '" + txfNombreCliente.getText() + "' AND f.id_estatus = 0");
//
//            try {
//                while (rs.next()) {
//                    idPaciente = rs.getInt(1);
//                    folioPaciente = rs.getString(2);
//                }
//            } catch (Exception e) {
//              
//            }
//
//            if (idPaciente == 0) {
//                alertaError.setTitle(null);
//                alertaError.setContentText("EL PACIENTE NO TINE UNA CUENTA");
//                alertaError.showAndWait();
//            }
//
//          
//            alertaConfirmacion.setTitle(null);
//            alertaConfirmacion.setHeaderText(null);
//            alertaConfirmacion.setContentText("EL CLIENTE TIENE UNA CUENTA ACTIVA\n¿DESEA AGREGAR LAS COMPRAS REALIZADAS A LA CUENTA ACTUAL?");
//
//            Optional<ButtonType> option = alertaConfirmacion.showAndWait();
//
//            if (option.get() == ButtonType.OK) {
//                mandarCuenta = true;
//                choiseBoxMetodoDePago.setDisable(true);
//                txfMontoRecibido.setDisable(true);
//                btnPagar.setDisable(false);
//
//            } else {
//                mandarCuenta = false;
//                alertaInfo.setTitle(null);
//                alertaInfo.setHeaderText(null);
//                alertaInfo.setContentText("LA CUENTA SE COBRARA DE MANERA NORMAL");
//                alertaInfo.showAndWait();
//            }
//
//        }
    }

    private void incertarConsumoAcuenta(String tipo, int piezas, double monto) throws SQLException {
//        try {
//            CallableStatement stmt = null;
//            String sql = "{call InsertarConsumo (?,?,?,?,?,?)}";
//            stmt = con.prepareCall(sql);
//           
//            stmt.setInt(1, idPaciente);
//            stmt.setString(2, tipo);
//            stmt.setInt(3, piezas);
//            stmt.setDouble(4, monto);
//            stmt.setString(5, folioPaciente);
//            stmt.setInt(6, 0);
//            stmt.execute();
//        } catch (Exception e) {
//        
//        }
    }
}
