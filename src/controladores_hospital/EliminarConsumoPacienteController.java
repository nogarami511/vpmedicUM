/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.CitaQuirofano;
import clases_hospital.Consumo;
import clases_hospital.Folio;
import clases_hospital.Inventario;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.ActualizacionFolioDAO;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class EliminarConsumoPacienteController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Consumo> consumos;
    Conexion conexion = new Conexion();
    Connection con;
    int idFolio;
    @FXML
    private TableView<Consumo> tabla;
    @FXML
    private TableColumn nombreInsumo;
    @FXML
    private TableColumn<Consumo, Double> cantidadInsumo;
    @FXML
    private TableColumn fechaAplicacionInsumo;
    @FXML
    private Button btnDescartarConsumo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void cancelarAplicacionMedicamento(ActionEvent event) throws SQLException {
        con = conexion.conectar2();
        Consumo consumoSeleccionado = tabla.getSelectionModel().getSelectedItem();
      
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
            try {
                if (passwordField.getText().equals("vp-sistemas")) {
                    con = conexion.conectar2();
                    ConsumosDAO consumosDAO = new ConsumosDAO(con);
                   
                    consumosDAO.actualizarDescarteCosumo(consumoSeleccionado.getId());
                    consumos.remove(consumoSeleccionado);
                    //llenarTabla();

                    if (VPMedicaPlaza.userNameSystem.equals("COCINA")) {

                    } else {
                        actualizarInventario(consumoSeleccionado, consumoSeleccionado.getCantidad());
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText(null);
                    alert.setContentText("Contraseña correcta. ¡Acceso concedido!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Contraseña incorrecta. Acceso denegado.");
                    alert.showAndWait();
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EliminarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        con.close();
    }

    public void setObjeto(int idFolio) {
        try {
            this.idFolio = idFolio;
            if (VPMedicaPlaza.userNameSystem.equals("COCINA")) {
                llenarTablaCocina();
            } else {
                llenarTabla();
            }

            tabla.setEditable(true);
            lambda();
        } catch (SQLException ex) {
            Logger.getLogger(EliminarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        ConsumosDAO consumosDAO = new ConsumosDAO(con);
        consumos = consumosDAO.obtenerTodosConsumosPorFolio(idFolio);//folio
        nombreInsumo.setCellValueFactory(new PropertyValueFactory("tipo"));
        cantidadInsumo.setCellValueFactory(new PropertyValueFactory("cantidad"));
        fechaAplicacionInsumo.setCellValueFactory(new PropertyValueFactory("datetime"));
        tabla.setItems(consumos);
        con.close();
    }

    public void llenarTablaCocina() throws SQLException {
        con = conexion.conectar2();
        ConsumosDAO consumosDAO = new ConsumosDAO(con);
        consumos = consumosDAO.obtenerConsumosAlimentosPorFolio(idFolio);//folio
        nombreInsumo.setCellValueFactory(new PropertyValueFactory("tipo"));
        cantidadInsumo.setCellValueFactory(new PropertyValueFactory("cantidad"));
        fechaAplicacionInsumo.setCellValueFactory(new PropertyValueFactory("datetime"));
        tabla.setItems(consumos);
        btnDescartarConsumo.setVisible(false);
        con.close();
    }

    public void lambda() {
        cantidadInsumo.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        cantidadInsumo.setOnEditCommit(event -> {
            /*aqui se va a ejecutar la actualizacion a consumos*/
            // obtener el objeto Consumo que está siendo editado
            Consumo consumoSeleccionado = event.getTableView().getItems().get(event.getTablePosition().getRow());
          
            double cantidadInicial = consumoSeleccionado.getCantidad();

            if (event.getNewValue().equals(0) || event.getNewValue() < 0) {
                alertaError.setTitle("ATENCION");
                alertaError.setHeaderText("NO PUEDES SELECCIONAR EL VALOR '0' O MENOR");
                alertaError.setContentText("PARA DESCARTAR EL CONSUMO, PRIMERO SELECCIONELO Y DESPUES HAGA CLICK EN EL BOTON 'DESCARTAR CONSUMO'");
                alertaError.showAndWait();
            } else if (event.getNewValue() <= cantidadInicial) {
                consumoSeleccionado.setCantidad(event.getNewValue());
            
                actualizarInventario(consumoSeleccionado, (cantidadInicial - consumoSeleccionado.getCantidad()));
            } else {
                alertaError.setTitle("ATENCION");
                alertaError.setHeaderText("CANTIDAD MAYOR");
                alertaError.setContentText("LA CANTIDAD NO PUEDE SER MAYOR A LA SUMINISTRADA, PARA ESO DEBE IR AL MODULO DE 'AGREGAR CONSUMO PACIENTE'");
                alertaError.showAndWait();
            }

            tabla.refresh();
        });
        cantidadInsumo.setEditable(true);
    }

    private void actualizarInventario(Consumo consumoPaciente, double cantidaddevolucion) {
        con = conexion.conectar2();
        InventariosDAO inventariodao = new InventariosDAO(con);
        MovimientoPadreDAO mopdao = new MovimientoPadreDAO(con);
        MovimientoDetalleDAO moddao = new MovimientoDetalleDAO(con);
        ConsumosDAO consumodao = new ConsumosDAO(con);
        FoliosDAO foliodao = new FoliosDAO(con);
        try {
            Folio folio = foliodao.obtenerFolioPorId(idFolio);
            MovimientoInventarioP moip = new MovimientoInventarioP();
            moip.setTipo_mov(4);
            moip.setId_proveedor(1);
            moip.setId_origen(1);
            moip.setId_destino(1);
            moip.setFolio_mov(folio.getFolio());
            moip.setSubtotal(0);
            moip.setImporte_impuesto(0);
            moip.setDescuento(0);
            moip.setTotal(0);
            moip.setEstatus_movimiento(1);
            moip.setObservaciones("DEVOLUCION PACIENTE");
            moip.setUsuario_registro(VPMedicaPlaza.userSystem);

            int id_moip = mopdao.agregarMovimientoInventarioPINT(moip);

            MovimientoDetalle mod = new MovimientoDetalle();
            Inventario inventariosUpdate = inventariodao.obtenerDatosPorIdInsumo(consumoPaciente.getId_producto_venta());
            Consumo consumo = consumodao.obtenerConsumoPorIdInsumoandIdFolio(consumoPaciente.getId_producto_venta(), idFolio);
            LocalDate currentDate = LocalDate.now();
            Date fecha = Date.valueOf(currentDate);

            mod.setId_insumo_mov_padre(id_moip);
            mod.setId_insumo(consumoPaciente.getId_producto_venta());
            mod.setCaducidad(fecha);
            mod.setLote_insumo(folio.getFolio());
            mod.setInventario_inicial(inventariosUpdate.getTotalExistencia());
            if (cantidaddevolucion == 0) {
                //conumopacmez.getCantidadEntregada()
                mod.setMovimineto(consumoPaciente.getCantidad());


                //conumopacmez.getConsumo()
                /*checar el movimiento a inventario que lo quita*/ /*absoluto*/
            
                mod.setInventario_final(inventariosUpdate.getTotalExistencia() + Math.abs(consumoPaciente.getCantidad()));
                consumo.setId_estatus_consumo(0);
            } else {
                mod.setMovimineto(cantidaddevolucion);
                mod.setInventario_final(inventariosUpdate.getTotalExistencia() + cantidaddevolucion);
                consumo.setCantidad(consumoPaciente.getCantidad());
            }
            mod.setCosto(0);
            mod.setUsuario_modificacion(VPMedicaPlaza.userSystem);

            moddao.create(mod);
            consumodao.actualizarConumo(consumo);

            acutalizarFolio();

        } catch (SQLException ex) {
            Logger.getLogger(EliminarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void acutalizarFolio() throws SQLException {
        con = conexion.conectar2();
        ActualizacionFolioDAO actualizarfoliodao = new ActualizacionFolioDAO(con);
        FoliosDAO foliodao = new FoliosDAO(con);
        CitaQuirofanoDAO citaQuirofanodao = new CitaQuirofanoDAO(con);
        Folio folio = foliodao.obtenerFolioPorId(idFolio);
        CitaQuirofano citaQuirofano = citaQuirofanodao.obteCitaQuirofanoPorIdFolio(idFolio);
        int id_CitaQuirofano;
        if (citaQuirofano == null) {
            id_CitaQuirofano = 0;
        } else {
            id_CitaQuirofano = citaQuirofano.getIdQuirofano();
        }
        double sumatoriatotal = actualizarfoliodao.calculateTotalSubtotal(actualizarfoliodao.getReportItems(folio.getId_paquete()));
     
        
        double totaconiva = sumatoriatotal * 1.16;
        folio.setMontoHastaElMomento(sumatoriatotal);
        double saldoacubir = totaconiva - folio.getTotalDeAbono();
        folio.setSaldoACubrir(saldoacubir);
        foliodao.actualizarFolio(folio);
    }

//    public void descartar(Consumo consumoSelect) throws SQLException {

//        con = conexion.conectar2();
//        ConsumosDAO consumosDAO = new ConsumosDAO(con);
//        consumosDAO.descartarConsumo(consumoSelect.getId());

//        actualizarInventario(consumoSelect, consumoSelect.getCantidad());
//        con.close();
//    }
}
