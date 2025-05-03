/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Costo;
import clases_hospital.Insumo;
import clases_hospital.PresentacionInsumos;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.PresentacionInsumosDAO;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
//import reportes.GenerarReportes;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class AgregarInventarioController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    int idPresentacion, tipoInsumo, idInsumo, idCosto;
    ObservableList<Insumo> insumos = FXCollections.observableArrayList();
    ObservableList<PresentacionInsumos> presentacionesinusmos = FXCollections.observableArrayList();
    ArrayList<Costo> costos = new ArrayList<>();
    PresentacionInsumosDAO presentacionInsumosdao;
    CostosDAO costodao;
    InsumosDAO insumosdao;
    private String clave;

    @FXML
    private TextField agregarNombre;
    @FXML
    private TextField agregarMarca;
    @FXML
    private TextField agregarOpservaciones;
    @FXML
    private TextField agregarMinimos;
    @FXML
    private TextField agregarMaximos;
    @FXML
    private TextField agregarClaveSAT;
    @FXML
    private TextField agregarFormula;
    @FXML
    private TextField txfIVA;
    @FXML
    private RadioButton rdbMedico;
    @FXML
    private RadioButton rdbAlimenticio;
    @FXML
    private TableView<Insumo> tabla;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colClaveSat;
    @FXML
    private TableColumn colPresentacion;
    @FXML
    private TableColumn colMaximos;
    @FXML
    private TableColumn colMinimos;
    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colFormula;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnAgregarTabla;
    @FXML
    private TextField txfCPC;
    @FXML
    private TextField txfCostoCaja;
    @FXML
    private TextField txfUtilidad;
    @FXML
    private RadioButton rdbSi;
    @FXML
    private RadioButton rdbNo;
    @FXML
    private ComboBox<PresentacionInsumos> cmbPresentacion;
    @FXML
    private RadioButton rdbEstudio;
    @FXML
    private TextField txfPrecioVenta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txfIVA.setDisable(true);
        try {
            // TODO
            llenarBuscador();
            llenarBuscadorInsumo();

        } catch (SQLException ex) {
            Logger.getLogger(AgregarInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void agregarInv(ActionEvent event) {
        insumosdao = new InsumosDAO(conexion.conectar2());
        boolean sinerror = true;

        for (int i = 0; i < insumos.size(); i++) {
            try {
                insumosdao.insertarInsumos(insumos.get(i), costos.get(i));
            } catch (SQLException ex) {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("ERROR AL INGRESAR LOS DATOS ");
                alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
                alertaError.setContentText(ex.getMessage());
                alertaError.showAndWait();
                sinerror = false;
                Logger.getLogger(AgregarInventarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (sinerror) {
            alertaSuccess.setHeaderText("Nuevo Insumo médico Ingresado");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();

            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionBtnEditar(ActionEvent event) {
        if (agregarNombre.getText().equals("") || agregarFormula.getText().equals("") || agregarMarca.getText().equals("") || cmbPresentacion.getValue() == null || agregarMaximos.getText().equals("") || agregarMinimos.getText().equals("") || txfIVA.getText().equals("") || agregarClaveSAT.getText().equals("") || agregarOpservaciones.getText().equals("") || txfCPC.getText().equals("") || txfCostoCaja.getText().equals("") || txfUtilidad.getText().equals("")) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("LLENE TODOS LOS CAMPOS ANTES DE CONTINUAR");
            alertaError.showAndWait();
        } else {
            try {
              
                insumosdao = new InsumosDAO(conexion.conectar2());
                Insumo insumo = new Insumo();
                costodao = new CostosDAO(conexion.conectar2());
                Costo costo = new Costo();

                insumo.setId(idInsumo);
                insumo.setClave(clave);
                insumo.setNombre(agregarNombre.getText());
                insumo.setCalve_sat(agregarClaveSAT.getText());
                insumo.setFormula(agregarFormula.getText());
                insumo.setMarca(agregarMarca.getText());
                insumo.setIdPresentacion(idPresentacion);
                insumo.setMaximos(Double.parseDouble(agregarMaximos.getText()));
                insumo.setMinimos(Double.parseDouble(agregarMinimos.getText()));
                insumo.setIva(Double.parseDouble(txfIVA.getText()));
                insumo.setCalve_sat(agregarClaveSAT.getText());
                insumo.setTipoInsumo(tipoInsumo);
                insumo.setObservaciones(agregarOpservaciones.getText());
                insumo.setIdEstatusInsumo(1);
                insumo.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                insumo.setPresentacion(cmbPresentacion.getValue().getPresentacion());
              
                insumosdao.actualizarInsumos(insumo);

                DecimalFormat formato = new DecimalFormat("#.00");
                double costocaja = Double.parseDouble(txfCostoCaja.getText());
                double cosotoUnitario = Double.parseDouble(formato.format(Double.parseDouble(txfCostoCaja.getText()) / Double.parseDouble(txfCPC.getText())));
                double utilidad = Double.parseDouble(txfUtilidad.getText());
                double porUtilidad = utilidad / 100.0;
                double ventaCaja = Double.parseDouble(formato.format((costocaja / porUtilidad)));
                double ventaUnitaria = Double.parseDouble(formato.format(cosotoUnitario / porUtilidad));

                double utilidadPaquete = utilidad;
                double ventaCajaPaquete = ventaCaja;
                double ventaUnitariaPaquete = ventaUnitaria;

                costo.setId(idCosto);
                costo.setClave(clave);
                costo.setIdInsumo(idInsumo);
                costo.setCantidadUnitariaxCaja(Double.parseDouble(txfCPC.getText()));
                costo.setCostoCompraCaja(Double.parseDouble(txfCostoCaja.getText()));
                costo.setCostoCompraUnitaria(cosotoUnitario);
                costo.setUtilidad(utilidad);
                costo.setPrecioVentaCaja(ventaCaja);
                costo.setPrecioVentaUnitaria(ventaUnitaria);
                costo.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                costo.setObservacion(agregarOpservaciones.getText());
                costo.setUtilidadPaquete(utilidadPaquete);
                costo.setPrecioVentaCajaPaquete(ventaCajaPaquete);
                costo.setPrecioVentaUnitariaPaquete(ventaUnitariaPaquete);
                costodao.actualizar(costo);

                alertaSuccess.setHeaderText("Insumo médico y/o cosoto actualizado correctamente");
                alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
                alertaSuccess.showAndWait();
                Stage stage = (Stage) btnAgregar.getScene().getWindow();
                stage.close();

            } catch (SQLException ex) {
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("ERROR AL INGRESAR LOS DATOS ");
                alertaError.setContentText("CODIGO DE ERROR: " + ex.getErrorCode());
                alertaError.showAndWait();
                Logger.getLogger(AgregarInventarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void accionAgregarTabla(ActionEvent event) {

        if (agregarNombre.getText().equals("") || agregarFormula.getText().equals("") || agregarMarca.getText().equals("") || cmbPresentacion.getValue() == null || agregarMaximos.getText().equals("") || agregarMinimos.getText().equals("") || txfIVA.getText().equals("") || agregarClaveSAT.getText().equals("") || agregarOpservaciones.getText().equals("")) {
            alertaError.setHeaderText("ERROR");
            alertaError.setContentText("LLENE TODOS LOS CAMPOS ANTES DE CONTINUAR");
            alertaError.showAndWait();
        } else {

            String calveinsumo = clave + encrypt(agregarNombre.getText());

            Insumo insumo = new Insumo();

            insumo.setClave(calveinsumo);
            insumo.setNombre(agregarNombre.getText());
            insumo.setFormula(agregarFormula.getText());
            insumo.setMarca(agregarMarca.getText());
            insumo.setIdPresentacion(idPresentacion);
            insumo.setMaximos(Double.parseDouble(agregarMaximos.getText()));
            insumo.setMinimos(Double.parseDouble(agregarMinimos.getText()));
            insumo.setIva(Double.parseDouble(txfIVA.getText()));
            insumo.setCalve_sat(agregarClaveSAT.getText());
            insumo.setTipoInsumo(tipoInsumo);
            insumo.setObservaciones(agregarOpservaciones.getText());
            insumo.setIdEstatusInsumo(1);
            insumo.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
            insumo.setPresentacion(cmbPresentacion.getValue().getPresentacion());

            insumos.add(insumo);

            colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            colFormula.setCellValueFactory(new PropertyValueFactory("formula"));
            colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
            colPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
            colMaximos.setCellValueFactory(new PropertyValueFactory("maximos"));
            colMinimos.setCellValueFactory(new PropertyValueFactory("minimos"));
            colClaveSat.setCellValueFactory(new PropertyValueFactory("calve_sat"));

            tabla.setItems(insumos);

            DecimalFormat formato = new DecimalFormat("#.00");
            double costocaja = Double.parseDouble(txfCostoCaja.getText());
            double cosotoUnitario = Double.parseDouble(formato.format(Double.parseDouble(txfCostoCaja.getText()) / Double.parseDouble(txfCPC.getText())));
            double utilidad = Double.parseDouble(txfUtilidad.getText());
            double porUtilidad = utilidad / 100.0;
            double ventaCaja = Double.parseDouble(formato.format((costocaja / (1- porUtilidad))));
            double ventaUnitaria = Double.parseDouble(formato.format(cosotoUnitario / (1- porUtilidad)));

            double utilidadPaquete = utilidad;
            double ventaCajaPaquete = ventaCaja;
            double ventaUnitariaPaquete = ventaUnitaria;

            Costo costo = new Costo();
            costo.setClave(calveinsumo);
            costo.setCantidadUnitariaxCaja(Double.parseDouble(txfCPC.getText()));
            costo.setCostoCompraCaja(Double.parseDouble(txfCostoCaja.getText()));
            costo.setCostoCompraUnitaria(cosotoUnitario);
            costo.setUtilidad(utilidad);
            costo.setPrecioVentaCaja(ventaCaja);
            costo.setPrecioVentaUnitaria(ventaUnitaria);
            costo.setUtilidadPaquete(utilidadPaquete);
            costo.setPrecioVentaCajaPaquete(ventaCajaPaquete);
            costo.setPrecioVentaUnitariaPaquete(ventaUnitariaPaquete);
            costo.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

            costos.add(costo);
        }

    }

    @FXML
    private void accionMedico(ActionEvent event) {
        clave = "VP-MED-";
        tipoInsumo = 1;
        rdbAlimenticio.setSelected(false);
        rdbEstudio.setSelected(false);
        txfCostoCaja.setText("0");
        txfUtilidad.setText("0");
    }

    @FXML
    private void accionAlimenticio(ActionEvent event) {
        clave = "VP-ALI-";
        tipoInsumo = 2;
        rdbMedico.setSelected(false);
        rdbEstudio.setSelected(false);
        txfCostoCaja.setText("0");
        txfUtilidad.setText("0");
    }

    @FXML
    private void accionSi(ActionEvent event) {
        txfIVA.setDisable(false);
        rdbNo.setSelected(false);
    }

    @FXML
    private void accionNo(ActionEvent event) {
        txfIVA.setText("" + 0);
        txfIVA.setDisable(true);
        rdbSi.setSelected(false);
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

    private void llenarBuscador() throws SQLException {
        presentacionInsumosdao = new PresentacionInsumosDAO(conexion.conectar2());
        presentacionesinusmos.addAll(presentacionInsumosdao.obtenerPresentacionInsumosTodos());
        cmbPresentacion.setItems(presentacionesinusmos);
        cmbPresentacion.setOnAction(e -> {
            PresentacionInsumos presentacionInsumosSelect = cmbPresentacion.getValue();
            idPresentacion = presentacionInsumosSelect.getId();
        });
    }

    private void llenarBuscadorInsumo() throws SQLException {
        insumosdao = new InsumosDAO(conexion.conectar2());

        AutoCompletionBinding<Insumo> insumo = TextFields.bindAutoCompletion(agregarNombre, insumosdao.obtenerTodosInsumos());
        insumo.setOnAutoCompleted(e -> {
            alertaConfirmacion.setHeaderText("Insumo médico Existente");
            alertaConfirmacion.setHeaderText("Por favor, ingrese uno nuevo o edite la información del insumo.");
            alertaConfirmacion.showAndWait();
            agregarNombre.setText("");
        });
    }

    public void recibirDatos(Insumo insumoSeleccionado) throws SQLException {
        tabla.setDisable(true);
        btnAgregarTabla.setDisable(true);

        presentacionInsumosdao = new PresentacionInsumosDAO(conexion.conectar2());
        PresentacionInsumos presentacioninsumos = presentacionInsumosdao.obtenerPresentacionInsumosPorId(insumoSeleccionado.getIdPresentacion());

        idInsumo = insumoSeleccionado.getId();
        agregarNombre.setText(insumoSeleccionado.getNombre());
        agregarMarca.setText(insumoSeleccionado.getMarca());
        cmbPresentacion.setValue(presentacioninsumos);
        idPresentacion = insumoSeleccionado.getIdPresentacion();
        agregarOpservaciones.setText(insumoSeleccionado.getObservaciones());
        agregarMinimos.setText("" + insumoSeleccionado.getMinimos());
        agregarMaximos.setText("" + insumoSeleccionado.getMaximos());
        agregarClaveSAT.setText(insumoSeleccionado.getCalve_sat());
        agregarFormula.setText(insumoSeleccionado.getFormula());
        txfIVA.setText("" + insumoSeleccionado.getIva());
        tipoInsumo = insumoSeleccionado.getTipoInsumo();
        if (insumoSeleccionado.getClave().equals(("VP-MED-" + encrypt(agregarNombre.getText()))) || insumoSeleccionado.getClave().equals(("VP-ALI-" + encrypt(agregarNombre.getText()))) || insumoSeleccionado.getClave().equals(("VP-EST-" + encrypt(agregarNombre.getText())))) {
            clave = insumoSeleccionado.getClave();
        } else {
            if (insumoSeleccionado.getTipoInsumo() == 1) {
                clave = "VP-MED-" + encrypt(agregarNombre.getText());
            } else if (insumoSeleccionado.getTipoInsumo() == 2) {
                clave = "VP-ALI-" + encrypt(agregarNombre.getText());
            } else if (insumoSeleccionado.getTipoInsumo() == 3) {
                clave = "VP-EST-" + encrypt(agregarNombre.getText());
            }
     
        }
        if (insumoSeleccionado.getTipoInsumo() == 1) {
            rdbMedico.setSelected(true);
            txfIVA.setText("0");
            tipoInsumo = 1;
        } else if (insumoSeleccionado.getTipoInsumo() == 2) {
            rdbAlimenticio.setSelected(true);
            txfIVA.setText("16");
            tipoInsumo = 2;
        } else if (insumoSeleccionado.getTipoInsumo() == 3) {
            rdbEstudio.setSelected(true);
            txfIVA.setText("16");
            tipoInsumo = 3;
        }
    

        Costo costo;
        costodao = new CostosDAO(conexion.conectar2());

        try {
            costo = costodao.obtenerPorIdInsumo(insumoSeleccionado.getId());

            idCosto = costo.getId();
            txfCPC.setText("" + costo.getCantidadUnitariaxCaja());
            txfCostoCaja.setText("" + costo.getCostoCompraCaja());
            txfUtilidad.setText("" + costo.getUtilidad());

        } catch (SQLException ex) {
            Logger.getLogger(AgregarInventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnAgregar.setVisible(false);
        btnEditar.setVisible(true);

    }

    @FXML
    private void accionEstudio(ActionEvent event) {
        clave = "VP-EST-";
        tipoInsumo = 2;
        rdbMedico.setSelected(false);
        rdbAlimenticio.setSelected(false);
        txfCostoCaja.setText("0");
        txfUtilidad.setText("0");
    }

    @FXML
    private void utilidadXelPrecio(KeyEvent event) {
        //este es solo para insumos hacer un if para los estudios, creo ques es .70
        double precio_venta =0;
        double costoxcaja =  Double.parseDouble(txfCostoCaja.getText())   / (1 - 0.71);
        txfUtilidad.setText("71");
        txfPrecioVenta.setText("$"+costoxcaja);
    }
}
