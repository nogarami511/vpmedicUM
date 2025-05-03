/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Proveedor;
import clases_hospital.GenerarReabastoInsumo;
import clases_hospital.ReabastoPadre;
import clases_hospital_DAO.GenerarReabastoInsumoDAO;
import clases_hospital_DAO.ProveedorDAO;
import clases_hospital_DAO.ReabastoPadreDAO;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ComprasExternasInsumosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<GenerarReabastoInsumo> generarreabastosinsumos = FXCollections.observableArrayList();
    ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    private final ArrayList<RadioButton> radioButtons = new ArrayList<>();

    Proveedor proveedor;

    GenerarReabastoInsumoDAO generarReabastoinsumodao;
    ProveedorDAO provedordao;
    ReabastoPadreDAO reabastopadredao;
    ProveedorDAO proveedordao;

    double descuentoConformato;
    double importeConFormato;
    double iva;
    double descuentoinfo;
    double importe;
    double totalPagar;
    double comision;
    double totalsComision;

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
    private TextField txfComision;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblTotalComision;
    @FXML
    private Label lblTotalPagar;
    @FXML
    private TextField txfNombreComercial;
    @FXML
    private ComboBox<Proveedor> cmbRazonSocial;
    @FXML
    private TextField txfRfc;
    @FXML
    private TableView<GenerarReabastoInsumo> tabla;
    @FXML
    private TableColumn<?, ?> colEliminar;
    @FXML
    private TableColumn<?, ?> colCodigo;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private TableColumn<GenerarReabastoInsumo, Double> colCantidad;
    @FXML
    private TableColumn<GenerarReabastoInsumo, Double> colPrecioUnitario;
    @FXML
    private TableColumn<GenerarReabastoInsumo, Double> colDescuento;
    @FXML
    private TableColumn<GenerarReabastoInsumo, Double> colImporte;
    @FXML
    private TableColumn<GenerarReabastoInsumo, String> colSeleccionar;
    @FXML
    private Button btnConfirmar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarCmbProveedor();
        llenarTabla();
        tabla.setEditable(true);
    }

    @FXML
    private void accionGenerar(ActionEvent event) throws SQLException {
        generarReabastoinsumodao = new GenerarReabastoInsumoDAO(conexion.conectar2());
        ObservableList<ReabastoPadre> reabastoPadres = FXCollections.observableArrayList();
        int contador = 0;
        String datos = "";
        double costoinicail = 0;
        double costofinal = 0;
        List<GenerarReabastoInsumo> listagenrarreabastosinsumos = new ArrayList<>();
        for (GenerarReabastoInsumo generarreabastosinsumo : generarreabastosinsumos) {
            if (generarreabastosinsumo.isPedir()) {
                costoinicail += generarreabastosinsumo.getCosto_total_inicial();
                costofinal += (generarreabastosinsumo.getCosto_total_inicial() * generarreabastosinsumo.getTotalUnidadesFaltantes());
                if (generarreabastosinsumo.getIdEstatusReabasto() == 3) {
                    generarreabastosinsumo.setIdEstatusReabasto(3);
                } else {
                    generarreabastosinsumo.setIdEstatusReabasto(2);
                }
                listagenrarreabastosinsumos.add(generarreabastosinsumo);
            }
        }
        if (costoinicail > 0 && costofinal > 0) {

            String sfolio;
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formattedDateTime = currentDateTime.format(formatter);
            sfolio = "" + proveedor.getNombreComercial() + formattedDateTime;

            ReabastoPadre rp = new ReabastoPadre();
            rp.setFolioReabasto(sfolio);
            rp.setIdProveedor(proveedor.getId());
            rp.setCostoTotalInicial(costoinicail);
            rp.setCostoTotalFinal(costofinal);
            rp.setUsuarioGenerado(VPMedicaPlaza.userSystem);
            rp.setUsuarioReabasto(0);
            rp.setUsuarioModificacion(VPMedicaPlaza.userSystem);
            rp.setEstatu_reabasto(1);
            
        

            reabastopadredao = new ReabastoPadreDAO(conexion.conectar2());
            int id_reabasto_padre_generado;
            id_reabasto_padre_generado = reabastopadredao.insertRegresodeID(rp);

            for (GenerarReabastoInsumo listagenrarreabastosinsumo : listagenrarreabastosinsumos) {
            
                listagenrarreabastosinsumo.setIdRabastosPadre(id_reabasto_padre_generado);
                generarReabastoinsumodao.update(listagenrarreabastosinsumo);
            }
            
            contador++;
            if (contador == 1) {
                datos = sfolio;
            } else {
                datos = datos + ", " + sfolio + "";
            }
        }

        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("SE HAN GENERADO " + contador + " ORDENES DE COMPRA, CON LOS FOLIOS \n" + datos + "\nPUEDE REVIASR ESTA INFORMACION EN LA VISTA \"COMPRAS\"");
        alertaConfirmacion.showAndWait();

        Stage stage = (Stage) btnGenerar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionSallir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
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
                comision = Double.parseDouble(txfComision.getText());
          
                totalPagar = Double.parseDouble(df.format(totalPagar - comision));
            }

            lblTotalPagar.setText("$" + totalPagar);
            lblTotalComision.setText("$" + importe);
            txfComision.setText("$" + comision);

            btnGenerar.setDisable(false);
        
        }
    }

    @FXML
    private void accionConfirmar(ActionEvent event) {
        btnGenerar.setDisable(false);
    }

    private void llenarTabla() {
        try {
            generarReabastoinsumodao = new GenerarReabastoInsumoDAO(conexion.conectar2());
            generarreabastosinsumos.addAll(generarReabastoinsumodao.getAllNameInsumos());
          

            colCodigo.setCellValueFactory(new PropertyValueFactory("idGenerarReabastoInsumo"));
            colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
            colCantidad.setCellValueFactory(new PropertyValueFactory("totalUnidadesFaltantes"));
            colPrecioUnitario.setCellValueFactory(new PropertyValueFactory("costoUnitarioInicial"));
            colDescuento.setCellValueFactory(new PropertyValueFactory("descuento"));
            colImporte.setCellValueFactory(new PropertyValueFactory("costo_total_inicial"));
            editarTabla();

            tabla.setItems(generarreabastosinsumos);
        } catch (SQLException ex) {
            Logger.getLogger(ComprasExternasInsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarCmbProveedor() {
        try {
            proveedordao = new ProveedorDAO(conexion.conectar2());
            proveedores.addAll(proveedordao.obtenerTodos());
            cmbRazonSocial.setItems(proveedores);
            cmbRazonSocial.setOnAction(e -> {
                proveedor = cmbRazonSocial.getValue();
                txfNombreComercial.setText(proveedor.getNombreComercial());
                txfRfc.setText(proveedor.getRfc());
                tabla.setDisable(false);
                txfComision.setDisable(false);
           });
        } catch (SQLException ex) {
            Logger.getLogger(ComprasExternasInsumosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editarTabla() {
        DecimalFormat df = new DecimalFormat("0.00");
        
        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCantidad.setOnEditCommit(event -> {
            GenerarReabastoInsumo generarreabastoinsumo = event.getTableView().getItems().get(event.getTablePosition().getRow());

            double cantidadAnterior = generarreabastoinsumo.getTotalUnidadesFaltantes();
            double cantidadNuevo = event.getNewValue();
            generarreabastoinsumo.setTotalUnidadesFaltantes(event.getNewValue());

            double cantidad = cantidadNuevo;
            double preciounitario = cantidadNuevo;
            double descuento = generarreabastoinsumo.getDescuento();
            double importeconformatodecimal = Double.parseDouble(df.format((cantidad * preciounitario) - descuento));
            generarreabastoinsumo.setCosto_total_inicial(importeconformatodecimal);
            actializarDatos();
            
            tabla.refresh();
        });
        
        colPrecioUnitario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPrecioUnitario.setOnEditCommit(event -> {
            GenerarReabastoInsumo generarreabastoinsumo = event.getTableView().getItems().get(event.getTablePosition().getRow());

            double precioAnterior = generarreabastoinsumo.getCostoUnitarioInicial();
            double precioNuevo = event.getNewValue();
            generarreabastoinsumo.setCostoUnitarioInicial(precioNuevo);

            double cantidad = generarreabastoinsumo.getTotalUnidadesFaltantes();
            double preciounitario = precioNuevo;
            double descuento = generarreabastoinsumo.getDescuento();
            double importeconformatodecimal = Double.parseDouble(df.format((cantidad * preciounitario) - descuento));
            generarreabastoinsumo.setCosto_total_inicial(importeconformatodecimal);
            actializarDatos();

            tabla.refresh();
        });

        colDescuento.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colDescuento.setOnEditCommit(event -> {
            GenerarReabastoInsumo generarreabastoinsumo = event.getTableView().getItems().get(event.getTablePosition().getRow());

            double descuentoAnterior = generarreabastoinsumo.getDescuento();
            double descuentoNuevo = event.getNewValue();
            generarreabastoinsumo.setDescuento(descuentoNuevo);

            double cantidad = generarreabastoinsumo.getTotalUnidadesFaltantes();
            double preciounitario = generarreabastoinsumo.getCostoUnitarioInicial();
            double descuento = descuentoNuevo;
            double importeconformatodecimal = Double.parseDouble(df.format((cantidad * preciounitario) - descuento));
            generarreabastoinsumo.setCosto_total_inicial(importeconformatodecimal);
            actializarDatos();
            
            tabla.refresh();
        });
        
        colPrecioUnitario.setEditable(true);
        colDescuento.setEditable(true);

        Callback<TableColumn<GenerarReabastoInsumo, String>, TableCell<GenerarReabastoInsumo, String>> confirmar = (TableColumn<GenerarReabastoInsumo, String> param) -> {
            final TableCell<GenerarReabastoInsumo, String> cell = new TableCell<GenerarReabastoInsumo, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton rdbReabastecer = new RadioButton();
                        radioButtons.add(rdbReabastecer);
                        GenerarReabastoInsumo grSlt = getTableView().getItems().get(getIndex());
                        rdbReabastecer.setSelected(grSlt.isPedir());
                        rdbReabastecer.setOnAction(event -> {
                            if (grSlt.isPedir()) {
                                double act = actualizarLabel();
                                lblTotal.setText("$" + df.format(act - (grSlt.getCostoUnitarioInicial() * grSlt.getTotalUnidadesFaltantes())));
                                grSlt.setPedir(false);

                            } else {
                                grSlt.setPedir(true);
                                lblTotal.setText("$" + df.format(actualizarLabel()));
                            }

                    
                        });
                        setGraphic(rdbReabastecer);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        
        colSeleccionar.setCellFactory(confirmar);
    }

    private void actializarDatos() {
        DecimalFormat df = new DecimalFormat("0.00");

        descuentoinfo = 0.0;
        importe = 0.0;

        for (int i = 0; i < tabla.getItems().size(); i++) {
            if (tabla.getItems().get(i).isPedir()) {
                descuentoinfo += tabla.getItems().get(i).getDescuento();
                importe += tabla.getItems().get(i).getCosto_total_inicial();
            }
        }

        if (importe > 0) {
            descuentoConformato = Double.parseDouble(df.format(descuentoinfo));
            importeConFormato = Double.parseDouble(df.format(importe)) + descuentoConformato;
            iva = Double.parseDouble(df.format((importeConFormato / 1.16)));
            totalPagar = importe;
            comision = 0;

            lblDescuento.setText("$" + descuentoConformato);
            lblSubTotal.setText("$" + importeConFormato);
            lblIVA.setText("$" + iva);
            lblTotal.setText("$" + importe);
            lblTotalPagar.setText("$" + importe);
            lblTotalComision.setText("$" + importe);
        }
    }

    private double actualizarLabel() {
        double totaldevolver = 0;
        for (int i = 0; i < generarreabastosinsumos.size(); i++) {
            if (generarreabastosinsumos.get(i).isPedir()) {
                totaldevolver = totaldevolver + (generarreabastosinsumos.get(i).getCostoUnitarioInicial() * generarreabastosinsumos.get(i).getTotalUnidadesFaltantes());
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");

        if (importe > 0) {
            descuentoConformato = Double.parseDouble(df.format(descuentoinfo));
            importeConFormato = Double.parseDouble(df.format(importe)) + descuentoConformato;
            iva = Double.parseDouble(df.format((importeConFormato / 1.16)));
            totalPagar = importe;
            comision = 0;

            lblDescuento.setText("$" + descuentoConformato);
            lblSubTotal.setText("$" + importeConFormato);
            lblIVA.setText("$" + iva);
            lblTotalPagar.setText("$" + importe);
            lblTotalComision.setText("$" + importe);
        }
        return totaldevolver;
    }

}
