/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.IndicaDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.IndicaDetalleDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.InventarioDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ValidarHojaConsumoController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    ObservableList<IndicaDetalle> indicasdetalles = FXCollections.observableArrayList();
    ObservableList<IndicaDetalle> indicasdetallesMezclas = FXCollections.observableArrayList();

    IndicaDetalleDAO indicaDetalleDAO;
    IndicaspDAO indicaspDAO;
    InventarioDetalleDAO inventariodetalleDAO;
    MovimientoPadreDAO movimientopadredao;

    private int idIndicaP;

    private boolean priemraVez = true;

    @FXML
    private Label lblCacbecera;
    @FXML
    private Button btnSallir;
    @FXML
    private Button btnValidar;
    @FXML
    private Label lblValores;
    @FXML
    private Label lblNombre;
    @FXML
    private TableView<IndicaDetalle> tablaMezclas;
    @FXML
    private TableColumn<?, ?> colInsumosMezclas;
    @FXML
    private TableColumn<?, ?> colLoteMezclas;
    @FXML
    private TableColumn<?, ?> colCantidadEntregadaMezclas;
    @FXML
    private TableColumn<IndicaDetalle, Double> colCantidadConsumidaMezclas;
    @FXML
    private TableColumn<IndicaDetalle, Double> colCantidadDevolucionMezclas;
    @FXML
    private TableView<IndicaDetalle> tablaEnfermeria;
    @FXML
    private TableColumn<?, ?> colInsumosEnfermeria;
    @FXML
    private TableColumn<?, ?> colCantidadEntregadaEnfermeria;
    @FXML
    private TableColumn<?, ?> colCantidadConsumidaEnfermeria;
    @FXML
    private TableColumn<?, ?> colCantidadDevolucion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tablaMezclas.setEditable(true);
        btnValidar.setDisable(false);
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSallir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionValidar(ActionEvent event) {
        if (priemraVez) {
            alertaInfo.setHeaderText(null);
            alertaInfo.setTitle("ALERTA");
            alertaInfo.setContentText("¿LOS VALORES SON CORRECTOS? LA TABLA NO HA SIDO MODIFICADA,\nLOS CAMBIOS NO SE PUEDEN DESHACER");
            Optional<ButtonType> action = alertaInfo.showAndWait();
            if (action.get() == ButtonType.OK) {
                actualizarIndicaDetalle();
                actulizarInventarioDetalle();

                Stage stage = (Stage) btnValidar.getScene().getWindow();
                stage.close();
            }
        } else {
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("ALERTA");
            alertaConfirmacion.setContentText("¿LOS VALORES SON CORRECTOS? LOS CAMBIOS NO SE PUEDEN DESHACER");
            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
            if (action.get() == ButtonType.OK) {
                actualizarIndicaDetalle();
                actulizarInventarioDetalle();

                alertaInfo.setHeaderText(null);
                alertaInfo.setTitle("CONFIRMACION");
                alertaInfo.setContentText("DATOS VALIDADOS CORRECTAMENTE");
                alertaInfo.showAndWait();

                Stage stage = (Stage) btnValidar.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void setObjetos(int idIndicaP, Paciente paciente) {
        this.idIndicaP = idIndicaP;
        lblNombre.setText(paciente.getNombre());
        lblCacbecera.setText("CONSUMO: " + idIndicaP);
        llenarTablaFarmacia();
        llenarTablaMezclas();
    }

    private void llenarTablaMezclas() {
        try {
            indicaDetalleDAO = new IndicaDetalleDAO(conexion.conectar2());
            indicasdetallesMezclas.addAll(indicaDetalleDAO.getAllIndicaDetallesbyFolioRLote(idIndicaP));

            colInsumosMezclas.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
            colLoteMezclas.setCellValueFactory(new PropertyValueFactory("lote"));
            colCantidadEntregadaMezclas.setCellValueFactory(new PropertyValueFactory("cantidadEntregada"));
            colCantidadConsumidaMezclas.setCellValueFactory(new PropertyValueFactory("cantidadSuministrada"));
            colCantidadDevolucionMezclas.setCellValueFactory(new PropertyValueFactory("cantidadDevolucion"));

            tablaMezclas.setItems(indicasdetallesMezclas);
        } catch (SQLException ex) {
            Logger.getLogger(ValidarHojaConsumoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTablaFarmacia() {
        try {
            indicaDetalleDAO = new IndicaDetalleDAO(conexion.conectar2());
            indicasdetalles.addAll(indicaDetalleDAO.getAllIndicaDetallesbyFolioRSumaData(idIndicaP));

            colInsumosEnfermeria.setCellValueFactory(new PropertyValueFactory("nombreInsumo"));
            colCantidadEntregadaEnfermeria.setCellValueFactory(new PropertyValueFactory("cantidadEntregada"));
            colCantidadConsumidaEnfermeria.setCellValueFactory(new PropertyValueFactory("cantidadSuministrada"));
            colCantidadDevolucion.setCellValueFactory(new PropertyValueFactory("cantidadDevolucion"));

            editarTablaMezclas();

            tablaEnfermeria.setItems(indicasdetalles);
        } catch (SQLException ex) {
            Logger.getLogger(ValidarHojaConsumoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editarTablaMezclas() {

        colCantidadConsumidaMezclas.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        colCantidadConsumidaMezclas.setOnEditCommit(event -> {
            IndicaDetalle indetSELECT = event.getTableView().getItems().get(event.getTablePosition().getRow());
            double suministroNuevo = event.getNewValue();
            double cantidadDevolucion = indetSELECT.getCantidadEntregada() - suministroNuevo;

            if (cantidadDevolucion > 0) {
                indetSELECT.setCantidadSuministrada(suministroNuevo);

                indetSELECT.setCantidadDevolucion(cantidadDevolucion);
                System.out.println(indetSELECT.getCantidadEntregada() - indetSELECT.getCantidadSuministrada());

                activarDesactivarBotones();
            } else {
                alertaInfo.setHeaderText(null);
                alertaInfo.setTitle("ALERTA");
                alertaInfo.setContentText("LA CANTIDAD DEVUELTA NO PUEDE SER NEGATIVA");
                alertaInfo.showAndWait();
            }
            tablaMezclas.refresh();
        });

        colCantidadDevolucionMezclas.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        colCantidadDevolucionMezclas.setOnEditCommit(event -> {
            IndicaDetalle indetSELECT = event.getTableView().getItems().get(event.getTablePosition().getRow());
            double cantidadDevolucion = event.getNewValue();
            double suministroNuevo = indetSELECT.getCantidadEntregada() - cantidadDevolucion;

            if (suministroNuevo > 0) {
                indetSELECT.setCantidadDevolucion(cantidadDevolucion);

                indetSELECT.setCantidadSuministrada(suministroNuevo);
                System.out.println(indetSELECT.getCantidadEntregada() - indetSELECT.getCantidadDevolucion());

                activarDesactivarBotones();
            } else {
                alertaInfo.setHeaderText(null);
                alertaInfo.setTitle("ALERTA");
                alertaInfo.setContentText("LA CANTIDAD SUMINISTRADA NO PUEDE SER NEGATIVA");
                alertaInfo.showAndWait();
            }

            tablaMezclas.refresh();
        });

        colCantidadConsumidaMezclas.setEditable(true);
        colCantidadDevolucionMezclas.setEditable(true);
        priemraVez = false;
    }

    private void activarDesactivarBotones() {
        List<IndicaDetalle> listaTablaMezcalasComparacionEnfermeria = new ArrayList();
        double cantidadInsumosSuministrados = 0;
        double cantidadInsumosDevolucion = 0;

        for (int i = 0; i < indicasdetalles.size(); i++) {
            IndicaDetalle indet = new IndicaDetalle();
            for (int j = 0; j < tablaMezclas.getItems().size(); j++) {
                if (indicasdetalles.get(i).getIdInsumo() == tablaMezclas.getItems().get(j).getIdInsumo()) {
                    cantidadInsumosSuministrados += tablaMezclas.getItems().get(j).getCantidadSuministrada();
                    cantidadInsumosDevolucion += tablaMezclas.getItems().get(j).getCantidadDevolucion();

                    System.out.println("");
                    System.out.println(tablaMezclas.getItems().get(i).getNombreInsumo() + " SUMINISTRO: " + cantidadInsumosSuministrados + " DEVOLUCION: " + cantidadInsumosDevolucion);

                    indet.setIdIndicaDetalle(tablaMezclas.getItems().get(j).getIdIndicaDetalle());
                    indet.setIdInsumo(tablaMezclas.getItems().get(j).getIdIndicaDetalle());
                    indet.setCantidadEntregada(tablaMezclas.getItems().get(j).getCantidadEntregada());
                    indet.setCantidadSuministrada(cantidadInsumosSuministrados);
                    indet.setCantidadDevolucion(cantidadInsumosDevolucion);
                    indet.setNombreInsumo(tablaMezclas.getItems().get(j).getNombreInsumo());
                }
            }
            listaTablaMezcalasComparacionEnfermeria.add(indet);
            cantidadInsumosSuministrados = 0;
            cantidadInsumosDevolucion = 0;
        }

        for (int i = 0; i < indicasdetalles.size(); i++) {
            if (indicasdetalles.get(i).getCantidadSuministrada() == listaTablaMezcalasComparacionEnfermeria.get(i).getCantidadSuministrada() && indicasdetalles.get(i).getCantidadDevolucion() == listaTablaMezcalasComparacionEnfermeria.get(i).getCantidadDevolucion()) {
                btnValidar.setDisable(false);
                lblValores.setVisible(false);
            } else {
                btnValidar.setDisable(true);
                lblValores.setVisible(true);
            }
        }
    }

    private void actualizarIndicaDetalle() {
        con = conexion.conectar2();
        indicaDetalleDAO = new IndicaDetalleDAO(con);

        for (int i = 0; i < tablaMezclas.getItems().size(); i++) {
            try {
                indicaDetalleDAO.actualizarSuministroDevolucion(tablaMezclas.getItems().get(i));
            } catch (SQLException ex) {
                Logger.getLogger(ValidarHojaConsumoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        actualizarIndicasP();
    }

    private void actualizarIndicasP() {
        con = conexion.conectar2();
        indicaspDAO = new IndicaspDAO(con);

        indicaspDAO.updateValidacion(idIndicaP);
    }

    private void actulizarInventarioDetalle()  {
        try {
            con = conexion.conectar2();
            inventariodetalleDAO = new InventarioDetalleDAO(con);
            
            MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();//objeto
            movimientopadredao = new MovimientoPadreDAO(con);
            LocalDate fechaActual = LocalDate.now();
            int id = 0;
            
            movimientoInventarioP.setTipo_mov(1);
            movimientoInventarioP.setId_origen(1);
            movimientoInventarioP.setId_destino(1);
            movimientoInventarioP.setId_proveedor(0);
            movimientoInventarioP.setFolio_mov("" + idIndicaP);
            movimientoInventarioP.setSubtotal(0);
            movimientoInventarioP.setDescuento(0);
            movimientoInventarioP.setImporte_impuesto(0);
            movimientoInventarioP.setTotal(0);
            movimientoInventarioP.setEstatus_movimiento(1);
            movimientoInventarioP.setObservaciones("DEVOLUCION DE PACIENTE");
            movimientoInventarioP.setUsuario_registro(VPMedicaPlaza.userSystem);
            
            id = movimientopadredao.agregarMovimientoInventarioPINT(movimientoInventarioP);
            
            for (int i = 0; i < tablaMezclas.getItems().size(); i++) {
                IndicaDetalle indet = tablaMezclas.getItems().get(i);
                if (indet.getCantidadDevolucion() > 0) {
                    inventariodetalleDAO.actualizarInventarioDetalle(indet.getIdInsumo(), indet.getLote(), indet.getCantidadDevolucion(), id);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValidarHojaConsumoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
/*
colHorasExtra.setCellValueFactory(new PropertyValueFactory("cantidad_hora_extra"));
            colHorasExtra.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colHorasExtra.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                CalculoNomina nompay = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                nompay.setCantidad_hora_extra(event.getNewValue());
                //
                nompay.setImporte_hora_extra(Double.parseDouble(df.format(nompay.getPago_hora_extra() * nompay.getCantidad_hora_extra())));
                nompay.setPago_neto(Double.parseDouble(df.format((nompay.getImporte_hora_extra() + nompay.getBono() + nompay.getAguinaldo() + nompay.getPago_finiquito() + nompay.getSueldo_semanal()) - nompay.getImporte_por_faltas())));

                suma_pagos = 0.0;
                tabla.getItems().forEach((item) -> {
                    suma_pagos += item.getPago_neto();
                });
                lblTotal.setText("" + df.format(suma_pagos));

                actualizarNominaInternaa(nompay.getId(), nompay.getClave(), nompay.getPago_hora_extra(), nompay.getCantidad_hora_extra(), nompay.getImporte_hora_extra(), nompay.getBono(), nompay.getFaltas(), nompay.getImporte_por_faltas(), nompay.getAguinaldo(), nompay.getPago_finiquito(), nompay.getPago_neto());
                actualizarAutorizacionDePago(lblClaveNomina.getText());
                tabla.refresh();
            });
 */
