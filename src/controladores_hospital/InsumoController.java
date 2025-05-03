/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Usuario;
import clases_hospital.Costo;
import clases_hospital.HistorialCostosInsumo;
import clases_hospital.Insumo;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.HistorialCostosInsumoDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.PaquetesMedicosDAO;
import clases_hospital_DAO.UsuarioDAO;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import vistasAuxiliares_hospital.AgregarInventarioController;
import vistasAuxiliares_hospital.HistorialCostosInsumoController;
import vistasAuxiliares_hospital.KitsMedicosyConsumiblesCosinaController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class InsumoController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Insumo> insumos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    InsumosDAO insumosdao;
    UsuarioDAO usuariodao;
    CostosDAO costodao;
    int id_isnumo;

    private boolean administrativa = false;

    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private TableColumn<Insumo, String> nombreInventario;
    @FXML
    private TableColumn<?, ?> presentacionInventario;
    @FXML
    private TableColumn<?, ?> formulaInventario;
    @FXML
    private TableColumn<Insumo, Double> maximosInventario;
    @FXML
    private TableColumn<Insumo, Double> minimosInventario;
    @FXML
    private TableColumn<?, ?> colClave;
    @FXML
    private Button btnAgregarInventario;
    @FXML
    private Button btnEditar;
    @FXML
    private TextField txfBuscar;
    @FXML
    private TableColumn<Insumo, Double> colPiezasUnitarias;
    @FXML
    private TableColumn<Insumo, Double> colCostos;
    @FXML
    private TableColumn<Insumo, Double> colCostoUnitario;
    @FXML
    private TableColumn<Insumo, Double> colPorUtilidad;
    @FXML
    private TableColumn<Insumo, Double> colPrecioVenta;
    @FXML
    private TableColumn<Insumo, Double> colPrecioUnitario;
    @FXML
    private TableView<Insumo> tablaEnfermeros;
    @FXML
    private TableColumn<?, ?> colClaveEnfermeros;
    @FXML
    private TableColumn<?, ?> nombreInventarioEnfermeros;
    @FXML
    private TableColumn<?, ?> formulaInventarioEnfermeros;
    @FXML
    private TableColumn<?, ?> presentacionInventarioEnfermeros;
    @FXML
    private TableColumn<?, ?> maximosInventarioEnfermeros;
    @FXML
    private TableColumn<?, ?> minimosInventarioEnfermeros;
    @FXML
    private RadioButton rdbVerTablaPaquete;
    @FXML
    private TableView<Insumo> tablaPaquete;
    @FXML
    private TableColumn<?, ?> colClavePaquete;
    @FXML
    private TableColumn<?, ?> nombreInventarioPaquete;
    @FXML
    private TableColumn<?, ?> presentacionInventarioPaquete;
    @FXML
    private TableColumn<?, ?> colPiezasUnitariasPaquete;
    @FXML
    private TableColumn<Insumo, Integer> colPorUtilidadPaquete;
    @FXML
    private TableColumn<?, ?> colPrecioVentaPaquete;
    @FXML
    private TableColumn<?, ?> colPrecioUnitarioPaquete;
    @FXML
    private Button btnConvertir;
    @FXML
    private Button btnVerKit;
    @FXML
    private Button btnConfigurarMacro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            usuariodao = new UsuarioDAO(conexion.conectar2());
            Usuario usuario = usuariodao.leer(VPMedicaPlaza.userSystem);
            if (usuario.getCargo().equals("ADMINISTRATIVO") || usuario.getCargo().equals("SISTEMAS") || usuario.getCargo().equals("CONTABILIDAD") 
                    || usuario.getCargo().equals("CONTRALORIA")  || usuario.getCargo().equals("BIOMEDICO")) {
                tabla.setVisible(true);
                llenarTabla();
                tabla.setEditable(true);
                administrativa = true;
            } else {
                tablaEnfermeros.setVisible(true);
                llenarTablaEnfermeros();
                administrativa = false;
            }
            txfBuscar.setOnKeyReleased(e -> filtrarLista(txfBuscar.getText()));
            btnEditar.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla() throws SQLException {
        insumos.clear();
        tabla.getItems().clear();

        insumosdao = new InsumosDAO(conexion.conectar2());

        insumos.addAll(insumosdao.obtnerTodsInusumosConInformacion());

        colClave.setCellValueFactory(new PropertyValueFactory("clave"));
        nombreInventario.setCellValueFactory(new PropertyValueFactory("nombre"));
        formulaInventario.setCellValueFactory(new PropertyValueFactory("formula"));
        presentacionInventario.setCellValueFactory(new PropertyValueFactory("presentacion"));
        maximosInventario.setCellValueFactory(new PropertyValueFactory("maximos"));
        minimosInventario.setCellValueFactory(new PropertyValueFactory("minimos"));
        colPiezasUnitarias.setCellValueFactory(new PropertyValueFactory("cantidad_unitariaxcaja"));
        colCostos.setCellValueFactory(new PropertyValueFactory("costo_compra_caja"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory("costo_compra_unitariaFormato"));
        colPorUtilidad.setCellValueFactory(new PropertyValueFactory("utilidad"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory("precio_venta_cajaFormato"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("precio_venta_unitariaFormato"));

        editarTablaAdministrativa();

        tabla.setItems(insumos);
        estilosTabla();

    }

    private void llenarTablaEnfermeros() throws SQLException {
        insumos.clear();
        tablaEnfermeros.getItems().clear();

        insumosdao = new InsumosDAO(conexion.conectar2());

        insumos.addAll(insumosdao.obtenerTodosInsumos());

        colClaveEnfermeros.setCellValueFactory(new PropertyValueFactory("clave"));
        nombreInventarioEnfermeros.setCellValueFactory(new PropertyValueFactory("nombre"));
        formulaInventarioEnfermeros.setCellValueFactory(new PropertyValueFactory("formula"));
        presentacionInventarioEnfermeros.setCellValueFactory(new PropertyValueFactory("presentacion"));
        maximosInventarioEnfermeros.setCellValueFactory(new PropertyValueFactory("maximos"));
        minimosInventarioEnfermeros.setCellValueFactory(new PropertyValueFactory("minimos"));

        tablaEnfermeros.setItems(insumos);
//        con.close();

    }

    @FXML
    private void agregarInv(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarInventario.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("INSUMO NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void editar(ActionEvent event) throws IOException, SQLException {
        Insumo insumoSeleccionado;
        if (administrativa) {
            insumoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        } else {
            insumoSeleccionado = tablaEnfermeros.getSelectionModel().getSelectedItem();
        }
        if (insumoSeleccionado == null) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("SELECCIONE UN ELEMENTO TE LA TABLA PARA EDITARLO");
            alertaError.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarInventario.fxml"));
            Parent root = loader.load();

            AgregarInventarioController agregarInventario = loader.getController();
            agregarInventario.recibirDatos(insumoSeleccionado);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setTitle("INSUMO");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
            this.tabla.getItems().clear();
            llenarTabla();

        }
    }

    private void llenarBuscador(List<Insumo> listaInsumo) {
//        AutoCompletionBinding<Insumo> insumobuscar = TextFields.bindAutoCompletion(txfBuscar, listaInsumo);
//        insumobuscar.setOnAutoCompleted(e -> {
//            Insumo insumoSelect = e.getCompletion();
//            id_isnumo = insumoSelect.getId();
//        });
    }


    private void accionBuscar(ActionEvent event) throws SQLException {
        usuariodao = new UsuarioDAO(conexion.conectar2());
        Usuario usuario = usuariodao.leer(VPMedicaPlaza.userSystem);
 
        if (usuario.getCargo().equals("ADMINISTRATIVO") || usuario.getCargo().equals("SISTEMAS") || usuario.getCargo().equals("BIOMEDICO")) {
            busquedaAdministrativa();

            tabla.setEditable(true);
        } else {
            busquedaEnfermeros();
        }
    }

    private void busquedaEnfermeros() throws SQLException {
        tablaEnfermeros.getItems().clear();

        insumosdao = new InsumosDAO(conexion.conectar2());

        insumos.addAll(insumosdao.obtenerInsumosPorId(id_isnumo));

        colClave.setCellValueFactory(new PropertyValueFactory("clave"));
        nombreInventario.setCellValueFactory(new PropertyValueFactory("nombre"));
        formulaInventario.setCellValueFactory(new PropertyValueFactory("formula"));
        presentacionInventario.setCellValueFactory(new PropertyValueFactory("presentacion"));
        maximosInventario.setCellValueFactory(new PropertyValueFactory("maximos"));
        minimosInventario.setCellValueFactory(new PropertyValueFactory("minimos"));

        tablaEnfermeros.setItems(insumos);
    }

    private void busquedaAdministrativa() throws SQLException {
        tabla.getItems().clear();

        insumosdao = new InsumosDAO(conexion.conectar2());

        insumos.addAll(insumosdao.obtenerInsumosPorIdConInformacion(id_isnumo));

        colClave.setCellValueFactory(new PropertyValueFactory("clave"));
        nombreInventario.setCellValueFactory(new PropertyValueFactory("nombre"));
        formulaInventario.setCellValueFactory(new PropertyValueFactory("formula"));
        presentacionInventario.setCellValueFactory(new PropertyValueFactory("presentacion"));
        maximosInventario.setCellValueFactory(new PropertyValueFactory("maximos"));
        minimosInventario.setCellValueFactory(new PropertyValueFactory("minimos"));
        colPiezasUnitarias.setCellValueFactory(new PropertyValueFactory("cantidad_unitariaxcaja"));
        colCostos.setCellValueFactory(new PropertyValueFactory("costo_compra_caja"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory("costo_compra_unitaria"));
        colPorUtilidad.setCellValueFactory(new PropertyValueFactory("utilidad"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory("precio_venta_caja"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("precio_venta_unitaria"));

        editarTablaAdministrativa();

        tabla.setItems(insumos);
    }

    private void editarTablaAdministrativa() {

        nombreInventario.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreInventario.setOnEditCommit(event -> {
            Insumo insumoSeleccionado = event.getRowValue();
            insumoSeleccionado.setNombre(event.getNewValue());
            insumoSeleccionado.setClave("VP-MED-" + encrypt(insumoSeleccionado.getNombre()));

            actualizarNombreClaveTablaAdministrativa(insumoSeleccionado);
            tabla.refresh();
        });

        maximosInventario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        maximosInventario.setOnEditCommit(event -> {
            insumosdao = new InsumosDAO(conexion.conectar2());
            // obtener el objeto Consumo que está siendo editado
            Insumo insuselec = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            insuselec.setMaximos(event.getNewValue());
            insuselec.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

            try {
                // ACTUALIZAMOS LOS VALORES DE LAS COLUMNAS
                insumosdao.actualizarInsumos(insuselec);
            } catch (SQLException ex) {
                Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            tabla.refresh();
        });

        minimosInventario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        minimosInventario.setOnEditCommit(event -> {
            insumosdao = new InsumosDAO(conexion.conectar2());
            // obtener el objeto Consumo que está siendo editado
            Insumo insuselec = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            insuselec.setMinimos(event.getNewValue());
            insuselec.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

            try {
                // ACTUALIZAMOS LOS VALORES DE LAS COLUMNAS
                insumosdao.actualizarInsumos(insuselec);
            } catch (SQLException ex) {
                Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            tabla.refresh();
        });

        colPiezasUnitarias.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPiezasUnitarias.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            Insumo insuselec = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            insuselec.setCantidad_unitariaxcaja(event.getNewValue());

            // ACTUALIZAMOS LOS VALORES DE LAS COLUMNAS
            calculosTablaAdministratica(insuselec);

            tabla.refresh();
        });

        colCostos.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCostos.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            Insumo insuselec = event.getTableView().getItems().get(event.getTablePosition().getRow());
            double costoAnterior = insuselec.getCosto_compra_caja();
            // actualizar el valor de cantidad en el objeto Consumo
            insuselec.setCosto_compra_caja(event.getNewValue());
            double costoNuevo = insuselec.getCosto_compra_caja();
            // ACTUALIZAMOS LOS VALORES DE LAS COLUMNAS
            calculosTablaAdministratica(insuselec);
            try {
                //ingresar al historial de costos de insumo
                ingresarAlHistorialCosto(insuselec, costoAnterior, costoNuevo);
                //cambiar el estatus del insumo en el paquete
                cambiarEstatusPaquete(insuselec.getId());
            } catch (SQLException ex) {
                Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabla.refresh();
        });

        colPorUtilidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPorUtilidad.setOnEditCommit(event -> {

            // obtener el objeto Consumo que está siendo editado
            Insumo insuselec = event.getTableView().getItems().get(event.getTablePosition().getRow());
            double utilidadAnterior = insuselec.getUtilidad();
            double costoAnterior = insuselec.getCosto_compra_caja();
            // actualizar el valor de cantidad en el objeto Consumo
            insuselec.setUtilidad(event.getNewValue());
            double utilidadNueva = insuselec.getUtilidad();
            // ACTUALIZAMOS LOS VALORES DE LAS COLUMNAS
            calculosTablaAdministratica(insuselec);

            try {
                ingresarAlHistorialUtilidad(insuselec, utilidadAnterior, utilidadNueva, costoAnterior);
            } catch (SQLException ex) {
                Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabla.refresh();
        });

        nombreInventario.setEditable(true);
        colPiezasUnitarias.setEditable(true);
        colCostos.setEditable(true);
        colPorUtilidad.setEditable(true);

    }

    private void calculosTablaAdministratica(Insumo insuselec) {
        costodao = new CostosDAO(conexion.conectar2());
        DecimalFormat df = new DecimalFormat("0.00");

        double cantidad_unitariaxcaja = insuselec.getCantidad_unitariaxcaja();
        double utilidad = insuselec.getUtilidad();
        double costo_compra_caja = insuselec.getCosto_compra_caja();

        double costo_compra_unitaria = Double.parseDouble(df.format(insuselec.getCosto_compra_caja() / insuselec.getCantidad_unitariaxcaja()));

        double precio_venta_caja = Double.parseDouble(df.format(costo_compra_caja / (utilidad / 100.00)));
        double precio_venta_unitaria = Double.parseDouble(df.format(costo_compra_unitaria / (utilidad / 100.00)));;

        insuselec.setCosto_compra_unitaria(costo_compra_unitaria);
        insuselec.setPrecio_venta_caja(precio_venta_caja);
        insuselec.setPrecio_venta_unitaria(precio_venta_unitaria);

        //ACTUALIZAMOS EN LA BASE DE DATOS
        try {
            int id_insumo = insuselec.getId();
            Costo costo = costodao.obtenerPorIdInsumo(id_insumo);

            costo.setCantidadUnitariaxCaja(cantidad_unitariaxcaja);
            costo.setUtilidad(utilidad);
            costo.setCostoCompraCaja(costo_compra_caja);
            costo.setCostoCompraUnitaria(costo_compra_unitaria);
            costo.setPrecioVentaCaja(precio_venta_caja);
            costo.setPrecioVentaUnitaria(precio_venta_unitaria);
            costo.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

            costodao.actualizar(costo);

        } catch (SQLException ex) {
            Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarNombreClaveTablaAdministrativa(Insumo insumo) {
        insumosdao = new InsumosDAO(conexion.conectar2());
        try {
            insumosdao.actualizarInsumos(insumo);
        } catch (SQLException ex) {
            Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        calculosTablaAdministratica(insumo);
    }

    private void estilosTabla() {
        maximosInventario.setStyle("-fx-alignment: CENTER;");
        minimosInventario.setStyle("-fx-alignment: CENTER;");
        colPiezasUnitarias.setStyle("-fx-alignment: CENTER;");
        colCostos.setStyle("-fx-alignment: CENTER;");
        colPorUtilidad.setStyle("-fx-alignment: CENTER;");
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

    private void llenarTablaPaquetes() throws SQLException {
        tablaPaquete.getItems().clear();
        insumos.clear();

        insumosdao = new InsumosDAO(conexion.conectar2());

        insumos.addAll(insumosdao.obtenerTodosInsumosConInformacionPaquete());

        colClavePaquete.setCellValueFactory(new PropertyValueFactory("clave"));
        nombreInventarioPaquete.setCellValueFactory(new PropertyValueFactory("nombre"));
        presentacionInventarioPaquete.setCellValueFactory(new PropertyValueFactory("presentacion"));
        colPiezasUnitariasPaquete.setCellValueFactory(new PropertyValueFactory("cantidad_unitariaxcaja"));
        colPorUtilidadPaquete.setCellValueFactory(new PropertyValueFactory("utilidadPaquete"));
        colPrecioVentaPaquete.setCellValueFactory(new PropertyValueFactory("precioVentaCajaPaquete"));
        colPrecioUnitarioPaquete.setCellValueFactory(new PropertyValueFactory("precioVentaUnitariaPaquete"));

        editarTablaPaquete();

        tablaPaquete.setItems(insumos);
    }

    private void editarTablaPaquete() {
        colPorUtilidadPaquete.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colPorUtilidadPaquete.setOnEditCommit(event -> {

            // obtener el objeto Consumo que está siendo editado
            Insumo insuselec = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            insuselec.setUtilidadPaquete(event.getNewValue());
            // ACTUALIZAMOS LOS VALORES DE LAS COLUMNAS
            calculaoTablaPaquete(insuselec);

            tablaPaquete.refresh();
        });

        colPorUtilidadPaquete.setEditable(true);
    }

    private void calculaoTablaPaquete(Insumo insuselec) {
        costodao = new CostosDAO(conexion.conectar2());
        DecimalFormat df = new DecimalFormat("0.00");

        double utilidad = insuselec.getUtilidadPaquete();
        double costo_compra_caja = insuselec.getCosto_compra_caja();

        double costo_compra_unitaria = Double.parseDouble(df.format(insuselec.getCosto_compra_caja() / insuselec.getCantidad_unitariaxcaja()));

        double precio_venta_caja = Double.parseDouble(df.format(costo_compra_caja / (utilidad / 100.00)));
        double precio_venta_unitaria = Double.parseDouble(df.format(costo_compra_unitaria / (utilidad / 100.00)));;

        insuselec.setPrecioVentaCajaPaquete(precio_venta_caja);
        insuselec.setPrecioVentaUnitariaPaquete(precio_venta_unitaria);

        //ACTUALIZAMOS EN LA BASE DE DATOS
        try {
            int id_insumo = insuselec.getId();
            Costo costo = costodao.obtenerPorIdInsumo(id_insumo);

            costo.setUtilidad(utilidad);
            costo.setCostoCompraUnitaria(costo_compra_unitaria);
            costo.setPrecioVentaCaja(precio_venta_caja);
            costo.setPrecioVentaUnitaria(precio_venta_unitaria);
            costo.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

            costodao.actualizarConCostoPaquete(costo);

        } catch (SQLException ex) {
            Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionTablaPaquete(ActionEvent event) throws SQLException {
        if (rdbVerTablaPaquete.isSelected()) {
            tabla.setVisible(false);
            tablaPaquete.setEditable(true);
            tablaPaquete.setVisible(true);
            llenarTablaPaquetes();
        } else {
            tablaPaquete.setVisible(false);
            tabla.setVisible(true);
            llenarTabla();
        }

    }

    @FXML
    private void accionConvertir(ActionEvent event) throws IOException, SQLException {
        Insumo insumoSeleccionado;
        if (administrativa) {
            insumoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        } else {
            insumoSeleccionado = tablaEnfermeros.getSelectionModel().getSelectedItem();
        }
        if (insumoSeleccionado == null) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("SELECCIONE UN ELEMENTO TE LA TABLA PARA EDITARLO");
            alertaError.showAndWait();
        } else {
            if (insumoSeleccionado.isKit_consumible()) {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("EL INUSMO SELECCIONADO YA ES UN KIT-CONSUMIBLE");
                alertaError.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/KitsMedicosyConsumiblesCosina.fxml"));
                Parent root = loader.load();

                KitsMedicosyConsumiblesCosinaController kitconsumible = loader.getController();
                kitconsumible.recibirDatos(insumoSeleccionado);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setResizable(false);
                stage.setTitle("INSUMO");
                stage.getIcons().add(new Image("/img/icono.png"));
                stage.setScene(scene);
                stage.showAndWait();
                this.tabla.getItems().clear();
                llenarTabla();
            }
        }
    }

    @FXML
    private void accionVerKit(ActionEvent event) throws IOException, SQLException {
        Insumo insumoSeleccionado;
        if (administrativa) {
            insumoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        } else {
            insumoSeleccionado = tablaEnfermeros.getSelectionModel().getSelectedItem();
        }
        if (insumoSeleccionado == null) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("SELECCIONE UN ELEMENTO TE LA TABLA PARA EDITARLO");
            alertaError.showAndWait();
        } else {
            if (insumoSeleccionado.isKit_consumible()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/KitsMedicosyConsumiblesCosina.fxml"));
                Parent root = loader.load();

                KitsMedicosyConsumiblesCosinaController kitconsumible = loader.getController();
                kitconsumible.recibirDatosEditarVer(insumoSeleccionado);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setResizable(false);
                stage.setTitle("INSUMO");
                stage.getIcons().add(new Image("/img/icono.png"));
                stage.setScene(scene);
                stage.showAndWait();
                this.tabla.getItems().clear();
                llenarTabla();
            } else {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("EL INUSMO SELECCIONADO NO ES UN KIT-CONSUMIBLE");
                alertaError.showAndWait();
            }
        }
    }

    private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(insumos);
        } else {
            ObservableList<Insumo> listaFiltrada = FXCollections.observableArrayList();
            for (Insumo insumo : insumos) {
                if (insumo.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(insumo);
                }
            }
            tabla.setItems(listaFiltrada);
            tablaPaquete.setItems(listaFiltrada);
            tablaEnfermeros.setItems(listaFiltrada);
        }
    }

    public void ingresarAlHistorialCosto(Insumo insumoSelecc, double costoAnterior, double costoNuevo) throws SQLException {
        con = conexion.conectar2();
        HistorialCostosInsumo historialConsumo = new HistorialCostosInsumo(insumoSelecc.getId(), costoAnterior, costoNuevo, insumoSelecc.getUtilidad(), insumoSelecc.getUtilidad());
        HistorialCostosInsumoDAO historialCostosInsumoDAO = new HistorialCostosInsumoDAO(con);
        historialCostosInsumoDAO.insert(historialConsumo);
        con.close();
    }

    public void ingresarAlHistorialUtilidad(Insumo insumoSelecc, double utilidadAnterior, double utilidadNuevo, double costoCajaAnterior) throws SQLException {
        con = conexion.conectar2();
        HistorialCostosInsumo historialConsumo = new HistorialCostosInsumo(insumoSelecc.getId(), costoCajaAnterior, insumoSelecc.getPrecio_venta_caja(), utilidadAnterior, utilidadNuevo);
        HistorialCostosInsumoDAO historialCostosInsumoDAO = new HistorialCostosInsumoDAO(con);
        historialCostosInsumoDAO.insert(historialConsumo);
        con.close();
    }

    public void cambiarEstatusPaquete(int idInsumo) throws SQLException {
        con = conexion.conectar2();
        //aqui vamos a cambiar el estatus de los paquetes ya que el precio de un isumo cambió 
        PaquetesMedicosDAO paqueteMedicoDAO = new PaquetesMedicosDAO(con);
        paqueteMedicoDAO.cambiarEstatusPaquete(idInsumo);
        con.close();
    }

    @FXML
    private void historialCostosInsumo(ActionEvent event) throws IOException, SQLException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/HistorialCostosInsumo.fxml"));
        Parent root = loader.load();
        HistorialCostosInsumoController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
        Insumo insumo = tabla.getSelectionModel().getSelectedItem();

        // Pasar el objeto a la vista de destino
        destinoController.setObjeto(insumo.getId());

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("HISTORIAL DE COSTOS DE INSUMO");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

        llenarTabla();
    }

    @FXML
    private void accionConfigurarMacro(ActionEvent event) throws IOException {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ConfiguracionMacros.fxml"));
            Parent root = loader.load();
            
            // Crear un nuevo Stage para la vista de destino
            Stage destinoStage = new Stage();
            destinoStage.setTitle("Configurar Macros");
            destinoStage.setScene(new Scene(root));
            destinoStage.initModality(Modality.APPLICATION_MODAL);
            
            // Mostrar el nuevo Stage de forma modal
            destinoStage.showAndWait();
            
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void VerInsumosporMacro(ActionEvent event) throws IOException, SQLException {
         FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VisualizarMacros.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("MACROS");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }
}