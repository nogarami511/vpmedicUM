/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Almacen;
import clases_hospital.Inventario;
import clases_hospital.InventarioAlmacen;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventarioAlmacenDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import reportes.Reporte;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AlmacenesVistaAuxiliarController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<InventarioAlmacen> inventariosalmacenes = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();

    Almacen almacen = new Almacen();
    InventarioAlmacenDAO inventarioalmacendao;

    @FXML
    private Button btnAgregarInsumo;
    @FXML
    private Label lblAlmacen;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<InventarioAlmacen> tabla;
    @FXML
    private TableColumn<?, ?> colInsumos;
    @FXML
    private TableColumn<?, ?> colTotalExistencia;
    @FXML
    private TableColumn<?, ?> colFonfoFijo;
    @FXML
    private TableColumn<?, ?> colFalta;
    @FXML
    private Button btnReabasto;
    @FXML
    private Button btnEditar;
    @FXML
    private JFXButton txtNombreAlmacen;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void accionAgregarInsumos(ActionEvent event) throws IOException, SQLException {
//        InventarioAlmacen inventarioalmacen = this.tabla.getSelectionModel().getSelectedItem();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/AgregarInsumosAlmacen.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        AgregarInsumosAlmacenController aiac = fxml.getController();
        aiac.recibirDatos(almacen.getIdArea());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionReabasto(ActionEvent event) {
        Connection conection = conexion.conectar2();
        try {
            MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();//objeto
            MovimientoPadreDAO movimientopadredao = new MovimientoPadreDAO(conection);
            MovimientoDetalleDAO movimientodetalledao = new MovimientoDetalleDAO(conection);
            inventarioalmacendao = new InventarioAlmacenDAO(conection);

            LocalDate fechaActual = LocalDate.now();
            Date fechaHoy = Date.valueOf(fechaActual);

            movimientoInventarioP.setTipo_mov(1);
            movimientoInventarioP.setId_origen(1);
            movimientoInventarioP.setId_destino(almacen.getIdArea());
            movimientoInventarioP.setId_proveedor(0);
            movimientoInventarioP.setFolio_mov("MOV-" + fechaHoy);
            movimientoInventarioP.setSubtotal(0);
            movimientoInventarioP.setDescuento(0);
            movimientoInventarioP.setImporte_impuesto(0);
            movimientoInventarioP.setTotal(0);
            movimientoInventarioP.setEstatus_movimiento(1);
            movimientoInventarioP.setObservaciones("COSUMO DE PACIENTE");
            movimientoInventarioP.setUsuario_registro(VPMedicaPlaza.userSystem);

            int id = movimientopadredao.agregarMovimientoInventarioPINT(movimientoInventarioP);

            for (int i = 0; i < inventariosalmacenes.size(); i++) {
                if (inventariosalmacenes.get(i).getTotalExistencia() < inventariosalmacenes.get(i).getFondoFijo()) {
                    double totalExistencia = inventariosalmacenes.get(i).getTotalExistencia();
                    double fondoFijo = inventariosalmacenes.get(i).getFondoFijo();

                    

                    double nuevaExistencia = fondoFijo - totalExistencia;

                    MovimientoDetalle movimientoDetalle = movimientoInventatario(nuevaExistencia, id, fechaHoy, inventariosalmacenes.get(i).getIdInsumo());

                    if (movimientoDetalle != null) {
                        movimientodetalledao.create(movimientoDetalle);
                        InventarioAlmacen inventario = new InventarioAlmacen();

                        inventario.setIdInsumo(inventariosalmacenes.get(i).getIdInsumo());
                        inventario.setTotalExistencia(fondoFijo);
                        inventario.setFondoFijo(fondoFijo);
                        inventario.setIdAlmacen(almacen.getIdArea());
                        inventario.setUsuarioModificacion(VPMedicaPlaza.userSystem);
                        inventario.setIdInventarioAlmacen(inventariosalmacenes.get(i).getIdInventarioAlmacen());

                        inventarioalmacendao.update(inventario);
                    } else {
                        break;
                    }
                }
            }

            Reporte reporte = new Reporte("reportereabastoalmacenes");
            reporte.generarReporteReabasto(id);
        } catch (SQLException ex) {
            Logger.getLogger(AlmacenesVistaAuxiliarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionEditar(ActionEvent event) {
    }

    private void llenarTabla() throws SQLException {
        inventarioalmacendao = new InventarioAlmacenDAO(conexion.conectar2());
        inventariosalmacenes.addAll(inventarioalmacendao.getAllByIdAlmacen(almacen.getIdArea()));

        colInsumos.setCellValueFactory(new PropertyValueFactory("nombre_inusmo"));
        colTotalExistencia.setCellValueFactory(new PropertyValueFactory("totalExistencia"));
        colFonfoFijo.setCellValueFactory(new PropertyValueFactory("fondoFijo"));
        colFalta.setCellValueFactory(new PropertyValueFactory("falta"));

        tabla.setItems(inventariosalmacenes);
    }

    public void recibirDatos(Almacen almacen) {
        try {
            this.almacen = almacen;
            txtNombreAlmacen.setText(almacen.getAlmacen());
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(AlmacenesVistaAuxiliarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private MovimientoDetalle movimientoInventatario(double nuevaEsixtencia, int id_movimeintopadre, Date fechaHoy, int id_insumo) throws SQLException {
        Connection connection = conexion.conectar2();
        MovimientoDetalle movimientoDetalle = new MovimientoDetalle();// objeto

        InventariosDAO inventariosdao = new InventariosDAO(connection);
        Inventario inventario = inventariosdao.obtenerDatosPorIdInsumoSoloDatosImportantes(id_insumo);

        if (inventario.getTotalExistencia() > nuevaEsixtencia) {
            movimientoDetalle.setId_insumo(id_insumo);
            movimientoDetalle.setCaducidad(fechaHoy);
            movimientoDetalle.setLote_insumo("MOV-" + fechaHoy);
            movimientoDetalle.setInventario_inicial(inventario.getTotalExistencia());
            movimientoDetalle.setMovimineto(nuevaEsixtencia);
            movimientoDetalle.setInventario_final(inventario.getTotalExistencia() - nuevaEsixtencia);
            movimientoDetalle.setId_insumo_mov_padre(id_movimeintopadre);
            movimientoDetalle.setCosto(0);
            movimientoDetalle.setUsuario_modificacion(VPMedicaPlaza.userSystem);
            movimientoDetalle.setNombre(inventario.getNombre());
        } else {
            alertaError.setHeaderText(null);
            alertaError.setTitle("ATENCION");
            alertaError.setContentText("Insumo " + inventario.getNombre() + " con poca existencia " + inventario.getTotalExistencia());
            alertaError.showAndWait();
            movimientoDetalle = null;
        }
        return movimientoDetalle;
    }

    public void descartarInsumoAlmacen() {

        /*PENDIENTE, SI SE ELIMINA UN INSUMO DEL ALMACEN HACER UN REIGRESO AL ALMACEN PRINCIPAL*/
//        tabla.setRowFactory(tableView -> {
//            TableRow<InventarioAlmacen> row = new TableRow<>();
//            ContextMenu cxmConfiguracion = new ContextMenu();
//            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
//            descartarConsumo.setOnAction(event -> {
//                
//            });
//            cxmConfiguracion.getItems().add(descartarConsumo);
//
//            row.contextMenuProperty().bind(
//                    Bindings.when(row.emptyProperty())
//                            .then((ContextMenu) null)
//                            .otherwise(cxmConfiguracion)
//            );
//            return row;
//        });
    }
}
