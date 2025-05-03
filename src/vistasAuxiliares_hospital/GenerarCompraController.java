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
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class GenerarCompraController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<GenerarReabastoInsumo> generarreabastosinsumos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    GenerarReabastoInsumoDAO generarReabastoinsumodao;

    ProveedorDAO provedordao;
    ReabastoPadreDAO reabastopadredao;

    double total;

    private final ArrayList<RadioButton> radioButtons = new ArrayList<>();

    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnSalir;
    @FXML
    private TableColumn colInsumo;
    @FXML
    private TableColumn colPresentacion;
    @FXML
    private TableColumn colProveedor;
    @FXML
    private TableColumn<GenerarReabastoInsumo, Double> colUnidad; //Aquie estaba en integer de esta forma: private TableColumn<GenerarReabastoInsumo, Integer> colUnidad;
    @FXML
    private TableColumn colCostoHastaElMomento;
    @FXML
    private TableColumn colPedido;
    @FXML
    private TableView<GenerarReabastoInsumo> tabla;
    @FXML
    private Label lblToltal;
    @FXML
    private TableColumn<?, ?> colPiezasxPresentacion;
    @FXML
    private TableColumn<?, ?> colCostoUnitario;
    @FXML
    private Button btnSeleccionar;
    @FXML
    private Button btnDeseleccionar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnDeseleccionar.setDisable(false);
        try {
            // TODO
            llenarTabla();
            tabla.setEditable(true);
        } catch (SQLException ex) {
            Logger.getLogger(GenerarCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionGenerar(ActionEvent event) throws SQLException {
        generarReabastoinsumodao = new GenerarReabastoInsumoDAO(conexion.conectar2());
        List<Proveedor> proveedores = provedordao.obtenerTodos();
        ObservableList<ReabastoPadre> reabastoPadres = FXCollections.observableArrayList();
        int contador = 0;
        String datos = "";
        for (int i = 0; i < proveedores.size(); i++) {
            double costoinicail = 0;
            double costofinal = 0;
            List<GenerarReabastoInsumo> listagenrarreabastosinsumos = new ArrayList<>();
            for (GenerarReabastoInsumo generarreabastosinsumo : generarreabastosinsumos) {
                if (generarreabastosinsumo.getId_proveedor() == proveedores.get(i).getId() && generarreabastosinsumo.isPedir()) {
                    costoinicail += generarreabastosinsumo.getCosto_total_inicial();
                    costofinal += (generarreabastosinsumo.getCostoUnitarioFinal() * generarreabastosinsumo.getTotalUnidadesFaltantes());
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
                sfolio = "" + proveedores.get(i).getNombreComercial() + formattedDateTime;

                ReabastoPadre rp = new ReabastoPadre();
                rp.setFolioReabasto(sfolio);
                rp.setIdProveedor(proveedores.get(i).getId());
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

                rp.setIdRabastosPadre(id_reabasto_padre_generado);
                reabastoPadres.add(rp);
                contador++;
                if (contador == 1) {
                    datos = sfolio;
                } else {
                    datos = datos + ", " + sfolio + "";
                }
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
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnGenerar.getScene().getWindow();
        stage.close();
    }

    private void llenarTabla() throws SQLException {
        generarReabastoinsumodao = new GenerarReabastoInsumoDAO(conexion.conectar2());
        generarreabastosinsumos.addAll(generarReabastoinsumodao.getAllNameProvCosto());
     

        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        editarTablaConsumo();
        colUnidad.setCellValueFactory(new PropertyValueFactory("totalUnidadesFaltantes"));
        colCostoHastaElMomento.setCellValueFactory(new PropertyValueFactory("costo_total_inicial"));
        colPiezasxPresentacion.setCellValueFactory(new PropertyValueFactory("cantidad_unitariaxcaja"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory("costo_compra_caja"));
        DecimalFormat df = new DecimalFormat("0.00");
        total = Double.parseDouble(df.format(generarReabastoinsumodao.getTotalCostoUnitarioInicial()));

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
                                lblToltal.setText("$" + df.format(act - (grSlt.getCostoUnitarioInicial() * grSlt.getTotalUnidadesFaltantes())));
                                grSlt.setPedir(false);

                            } else {
                                grSlt.setPedir(true);
                                lblToltal.setText("$" + df.format(actualizarLabel()));
                            }

                         
                        });
                        setGraphic(rdbReabastecer);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        colPedido.setCellFactory(confirmar);

        tabla.setItems(generarreabastosinsumos);

        lblToltal.setText("$" + df.format(actualizarLabel()));

    }

    private void editarTablaConsumo() {
        provedordao = new ProveedorDAO(conexion.conectar2());
        DecimalFormat df = new DecimalFormat("0.00");

        colUnidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colUnidad.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            GenerarReabastoInsumo genreainsuSelect = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            double anteriorPedidoUnitario = genreainsuSelect.getTotalUnidadesFaltantes();

            genreainsuSelect.setTotalUnidadesFaltantes(event.getNewValue());

            double nuevoPedididoUnitario = genreainsuSelect.getTotalUnidadesFaltantes();
            double costototalfina = Double.parseDouble(df.format((nuevoPedididoUnitario * genreainsuSelect.getCostoUnitarioInicial())));

            if (nuevoPedididoUnitario < anteriorPedidoUnitario) {
                genreainsuSelect.setIdEstatusReabasto(3);
            }
            lblToltal.setText("$" + df.format(actualizarLabel()));
            genreainsuSelect.setCosto_total_inicial(costototalfina);

//            actializarRegistro(genreainsuSelect);
//            
//            actualizarLabel();
            tabla.refresh();
        });

//        colProveedor
        Callback<TableColumn<GenerarReabastoInsumo, Proveedor>, TableCell<GenerarReabastoInsumo, Proveedor>> proveedor = (TableColumn<GenerarReabastoInsumo, Proveedor> param) -> {
            final TableCell<GenerarReabastoInsumo, Proveedor> cell = new TableCell<GenerarReabastoInsumo, Proveedor>() {
                private final ComboBox<Proveedor> comboBox = new ComboBox<>();
                private boolean isFirstTime = true;

                {
                    try {
                        comboBox.getItems().addAll(provedordao.obtenerTodos()); // Agrega las opciones deseadas
                    } catch (SQLException ex) {
                        Logger.getLogger(GenerarCompraController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    comboBox.setOnAction(event -> {
                        GenerarReabastoInsumo grSlt = getTableView().getItems().get(getIndex());
                        Proveedor selectedItem = comboBox.getSelectionModel().getSelectedItem();
                        grSlt.setId_proveedor(selectedItem.getId());
                        getTableView().getItems().set(getIndex(), grSlt);
//                        tabla.refresh();
                    });
                }

                @Override
                public void updateItem(Proveedor item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        GenerarReabastoInsumo grSlt = getTableView().getItems().get(getIndex());
                        if (isFirstTime) {
                            try {
                                Proveedor proveedor = provedordao.obtenerPorId(grSlt.getId_proveedor());
                                comboBox.setPromptText(proveedor.getNombreComercial());
                            } catch (SQLException ex) {
                                Logger.getLogger(GenerarCompraController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        comboBox.getSelectionModel().select(item); // Selecciona el valor correspondiente
                        setGraphic(comboBox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colProveedor.setCellFactory(proveedor);
        colProveedor.setEditable(true);
        colUnidad.setEditable(true);
    }

    private void abrirVentanaImpresion() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ImprimirOrdenDeCompra.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private double actualizarLabel() {
        double totaldevolver = 0;
        for (int i = 0; i < generarreabastosinsumos.size(); i++) {
            if (generarreabastosinsumos.get(i).isPedir()) {
                totaldevolver = totaldevolver + (generarreabastosinsumos.get(i).getCostoUnitarioFinal() * generarreabastosinsumos.get(i).getTotalUnidadesFaltantes());
             
            } 
        }
        return totaldevolver;
    }

    @FXML
    private void accionSeleccionar(ActionEvent event) {
        for (int i = 0; i < generarreabastosinsumos.size(); i++) {
//            radioButtons.get(i).setSelected(true);
            double costo = generarreabastosinsumos.get(i).getTotalUnidadesFaltantes()*generarreabastosinsumos.get(i).getCostoUnitarioFinal();
            generarreabastosinsumos.get(i).setPedir(true);
            generarreabastosinsumos.get(i).setCosto_total_inicial(costo);
          
            actualizarLabel();
          
        }
        tabla.refresh();

        btnSeleccionar.setDisable(true);
        btnDeseleccionar.setDisable(false);
    }

    @FXML
    private void accionDeseleccionar(ActionEvent event) {
        for (int i = 0; i < generarreabastosinsumos.size(); i++) {
//            radioButtons.get(i).setSelected(false);
            generarreabastosinsumos.get(i).setPedir(false);
            actualizarLabel();
         
        }
        tabla.refresh();
        btnSeleccionar.setDisable(false);
        btnDeseleccionar.setDisable(true);
    }

}
