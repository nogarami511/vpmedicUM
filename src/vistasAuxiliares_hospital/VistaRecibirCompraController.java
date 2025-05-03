/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.GenerarReabastoInsumo;
import clases_hospital.Inventario;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital.ReabastoPadre;
import clases_hospital_DAO.GenerarReabastoInsumoDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import clases_hospital_DAO.ReabastoPadreDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import reportes.Reporte;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class VistaRecibirCompraController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<GenerarReabastoInsumo> generarreabastosinsumos = FXCollections.observableArrayList();
    MovimientoInventarioP movimeintopadre = new MovimientoInventarioP();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    GenerarReabastoInsumoDAO generarreabastoinsumodao;
    ReabastoPadreDAO reabastopadredao;
    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;

    InventariosDAO inventariodao;

    ReabastoPadre rp = new ReabastoPadre();

    @FXML
    private Label lblFolio;
    @FXML
    private Label lblTotalFinal;
    @FXML
    private Label lblEstatus;
    @FXML
    private TableView<GenerarReabastoInsumo> tabla;
    @FXML
    private TableColumn<?, ?> colInsumo;
    @FXML
    private TableColumn<GenerarReabastoInsumo, Double> colUnidad;
    @FXML
    private TableColumn<?, ?> colPresentacion;
    @FXML
    private TableColumn<?, ?> colEstatus;
    @FXML
    private TableColumn<GenerarReabastoInsumo, Double> colCostoUnitario;
    @FXML
    private TableColumn<?, ?> colCostoTotal;
    @FXML
    private Button btnRecibir;
    @FXML
    private Button btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabla.setEditable(true);
    }

    @FXML
    private void accionRecibir(ActionEvent event) throws SQLException {
        llenarMovimentoPadre();
        Stage stage = (Stage) btnRecibir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void recibirDatos(ReabastoPadre reabastopadre) {
        rp = reabastopadre;
        int intreabastopadre = reabastopadre.getIdRabastosPadre();
      
        DecimalFormat df = new DecimalFormat("0.00");
        lblFolio.setText(reabastopadre.getFolioReabasto());
        lblTotalFinal.setText(df.format(reabastopadre.getCostoTotalFinal()));
        lblEstatus.setText(reabastopadre.getNombre()); //ESTATUS
        try {
            llenarTabla(reabastopadre.getIdRabastosPadre());
        } catch (SQLException ex) {
            
//            Logger.getLogger(VistaRecibirCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTabla(int id_rabasto_padre) throws SQLException {
        generarreabastoinsumodao = new GenerarReabastoInsumoDAO(conexion.conectar2());
        generarreabastosinsumos.addAll(generarreabastoinsumodao.getAllNameAndProvPorIdReabastoPadreConEstatus(id_rabasto_padre));

        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
        colUnidad.setCellValueFactory(new PropertyValueFactory("totalUnidadesFaltantes"));
        colPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatusreabasto"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory("costoUnitarioFinal"));
        colCostoTotal.setCellValueFactory(new PropertyValueFactory("costo_total_final"));

        editarDatosTabla();

        tabla.setItems(generarreabastosinsumos);
    }

    private void editarDatosTabla() {
        DecimalFormat df = new DecimalFormat("0.00");
        colUnidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colUnidad.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            GenerarReabastoInsumo genreainsuSelect = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            double anteriorPedidoUnitario = genreainsuSelect.getTotalUnidadesFaltantes();

            genreainsuSelect.setTotalUnidadesFaltantes(event.getNewValue());

            double nuevoPedididoUnitario = genreainsuSelect.getTotalUnidadesFaltantes();
            double costototalfina = Double.parseDouble(df.format((nuevoPedididoUnitario * genreainsuSelect.getCostoUnitarioFinal())));

            if (nuevoPedididoUnitario < anteriorPedidoUnitario) {
                genreainsuSelect.setIdEstatusReabasto(5);
                genreainsuSelect.setEstatusreabasto("REABASTECIDO PARCIAL");
                rp.setEstatu_reabasto(5);
                lblEstatus.setText("REABASTECIDO PARCIAL");
            }

            genreainsuSelect.setCosto_total_final(costototalfina);

            actializarRegistro(genreainsuSelect);

            actualizarLabel();

            tabla.refresh();
        });

        colCostoUnitario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCostoUnitario.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            GenerarReabastoInsumo genreainsuSelect = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            genreainsuSelect.setCostoUnitarioFinal(event.getNewValue());

            double costounitariofinal = genreainsuSelect.getCostoUnitarioFinal();
            double nuevoPedididoUnitario = genreainsuSelect.getTotalUnidadesFaltantes();
            double costototalfina = Double.parseDouble(df.format((nuevoPedididoUnitario * costounitariofinal)));

            genreainsuSelect.setCosto_total_final(costototalfina);
            actializarRegistro(genreainsuSelect);

            actualizarLabel();

            tabla.refresh();
        });

        colUnidad.setEditable(true);
        colCostoUnitario.setEditable(true);
    }

    private void actializarRegistro(GenerarReabastoInsumo genreainsuSelect) {
//        generarreabastoinsumodao = new GenerarReabastoInsumoDAO(conexion.conectar2());
        try {
            generarreabastoinsumodao.update(genreainsuSelect);
        } catch (SQLException ex) {
            Logger.getLogger(VistaRecibirCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarMovimentoPadre() throws SQLException {
        movimeintopadre.setTipo_mov(1);
        movimeintopadre.setId_proveedor(rp.getIdProveedor());
        movimeintopadre.setId_origen(1);
        movimeintopadre.setId_destino(1);
        movimeintopadre.setFolio_mov(rp.getFolioReabasto());
        movimeintopadre.setSubtotal(rp.getCostoTotalFinal());
        movimeintopadre.setImporte_impuesto(0);
        movimeintopadre.setDescuento(0);
        movimeintopadre.setTotal(rp.getCostoTotalFinal());
        movimeintopadre.setEstatus_movimiento(1);
        movimeintopadre.setObservaciones("");
        movimeintopadre.setUsuario_registro(VPMedicaPlaza.userSystem);

        movimientopadredao = new MovimientoPadreDAO(conexion.conectar2());

        int idMovimientoPadre = movimientopadredao.agregarMovimientoInventarioPINT(movimeintopadre);
        agregarMovimientoInventarioDetalle(idMovimientoPadre);
        imprimir(idMovimientoPadre);
    }

    private void agregarMovimientoInventarioDetalle(int idMovimientoDetalle) throws SQLException {
        con = conexion.conectar2();
        inventariodao = new InventariosDAO(con);
        movimientodetalledao = new MovimientoDetalleDAO(con);
        Date fechaActual = new Date();
        java.sql.Date sqlDate = new java.sql.Date(fechaActual.getTime());
        int i = 0;
        for (GenerarReabastoInsumo generarreabastosinsumo : generarreabastosinsumos) {
            MovimientoDetalle movimientodetalle = new MovimientoDetalle();

            Inventario inventario = inventariodao.obtenerPorIdInsumo(generarreabastosinsumo.getIdInsumo());
            movimientodetalle.setId_insumo_mov_padre(idMovimientoDetalle);
            movimientodetalle.setId_insumo(generarreabastosinsumo.getIdInsumo());
            movimientodetalle.setCaducidad(sqlDate);
            movimientodetalle.setLote_insumo("");
            movimientodetalle.setInventario_inicial(inventario.getTotalExistencia());
            movimientodetalle.setInventario_inicial(0);
            movimientodetalle.setMovimineto(generarreabastosinsumo.getTotalUnidadesFaltantes());
            movimientodetalle.setInventario_final(inventario.getTotalExistencia() + generarreabastosinsumo.getTotalUnidadesFaltantes());
//            movimientodetalle.setInventario_final(inventario.getTotalExistencia() + generarreabastosinsumo.getTotalUnidadesFaltantes());
            movimientodetalle.setCosto(generarreabastosinsumo.getCosto_total_final());
            movimientodetalle.setUsuario_modificacion(VPMedicaPlaza.userSystem);

            movimientodetalledao.create(movimientodetalle);
           
            i++;
        }
        actualizarEstatusReabasto();
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("COMPRA RECIBIDA CORRECTAMENTE");
        alertaConfirmacion.showAndWait();
    }

    private void actualizarEstatusReabasto() {
        int contador = 0;
        int i = 0;
        generarreabastoinsumodao = new GenerarReabastoInsumoDAO(conexion.conectar2());
        for (GenerarReabastoInsumo generarreabastosinsumo : generarreabastosinsumos) {
            if (generarreabastosinsumo.getIdEstatusReabasto() == 5) {
                generarreabastosinsumo.setIdEstatusReabasto(5);
                contador++;
            } else {
                generarreabastosinsumo.setIdEstatusReabasto(4);
            }
            actializarRegistro(generarreabastosinsumo);
            
            i++;
        }
        reabastopadredao = new ReabastoPadreDAO(conexion.conectar2());
        if (contador == 0) {
            rp.setEstatu_reabasto(4);
        }
        rp.setUsuarioModificacion(VPMedicaPlaza.userSystem);
        rp.setUsuarioReabasto(VPMedicaPlaza.userSystem);
        try {
            reabastopadredao.update(rp);
        } catch (SQLException ex) {
            Logger.getLogger(VistaRecibirCompraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarLabel() {
        DecimalFormat df = new DecimalFormat("0.00");
        double sumatoria = 0.0;

        DoubleStringConverter converter = new DoubleStringConverter();
        for (int i = 0; i < generarreabastosinsumos.size(); i++) {
            sumatoria = Double.parseDouble(df.format((sumatoria + generarreabastosinsumos.get(i).getCosto_total_final())));
        }
        String dato = converter.toString(sumatoria);
        lblTotalFinal.setText(dato);
    }

    private void imprimir(int idMovimientoPadre) {
        Reporte reporte = new Reporte("ReporteEntradas4");
        reporte.generarReporte(idMovimientoPadre);
    }

}
