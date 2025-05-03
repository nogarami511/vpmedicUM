/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Consumo;
import clases_hospital.IndicaDetalle;
import clases_hospital.IndicaSuministroPacientes;
import clases_hospital.Indicasp;
import clases_hospital.Insumo;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.IndicaDetalleDAO;
import clases_hospital_DAO.IndicaSuministroPacientesDAO;
import clases_hospital_DAO.IndicaspDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import controladores_hospital.IndicasController;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AgregarConsumoHojasDeConsumosController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);

    ObservableList<Indicasp> indicasps = FXCollections.observableArrayList();
    ObservableList<IndicaSuministroPacientes> indicasSuministrosPacientes = FXCollections.observableArrayList();

    Paciente pasiienteGuardado;

    boolean romper = false;

    IndicaspDAO indicaspDAO;
    InventariosDAO inventariosDAO;
    IndicaDetalleDAO indicasDetalleDAO;
    IndicaSuministroPacientesDAO indicasSuministroPacientesDAO;
    ConsumosDAO consumodao;
    InsumosDAO insumodao;
    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;

    @FXML
    private Label lblNombrePersona;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Indicasp> tabla;
    @FXML
    private TableColumn<?, ?> colFolioHojaConsumo;
    @FXML
    private TableColumn<?, ?> colArea;
    @FXML
    private TableColumn<?, ?> colEstatus;
    @FXML
    private TableColumn<Indicasp, String> colConsumo;
    @FXML
    private Button btnCambiodeTurno;
    @FXML
    private Button btndevolverindica;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void accionCambioTrurno(ActionEvent event) {
        alertaPrecaucion.setTitle("CONFIRMACION");
        alertaPrecaucion.setHeaderText("¿ESTÁ SEGURO AGREGAR CONSUMO AL PACIENTE?");
        alertaPrecaucion.setContentText("Esta accion no puede ser revertida");
        Optional<ButtonType> action = alertaPrecaucion.showAndWait();
        if (action.get() == ButtonType.OK) {
            btnCambiodeTurno.setDisable(true);
//            iterarHojasIndicasCantidadEntregada();
            iterarHojasIndicasCantidadDevolucion();
            llenarSuministroDevolucion();
            actualizarEstatusIndicaPFor();//REVISAR POR QUE NO ESTA ACTULIZANDO ESTO.
            if (!romper) {
                alertaConfirmacion.setTitle("CONFIRMACION");
                alertaConfirmacion.setHeaderText("PROCESO EJECUTADO CORRECTAMENTE");
                alertaConfirmacion.showAndWait();

                Stage stage = (Stage) btnCambiodeTurno.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    public void setObjeto(Paciente paciente, int modulo) {
        pasiienteGuardado = paciente;
        lblNombrePersona.setText(paciente.getNombre());
        if (modulo == 1) {
            llenarTabla();
            btndevolverindica.setVisible(false);
            btnCambiodeTurno.setVisible(true);
        } else if (modulo == 2) {
            llenarTabla2();
            btnCambiodeTurno.setVisible(false);
            btndevolverindica.setVisible(true);

        }

    }

    private void llenarTabla() {
        try {
            con = conexion.conectar2();
            indicaspDAO = new IndicaspDAO(con);

            if (VPMedicaPlaza.userArea > 0) {

                indicasps.addAll(indicaspDAO.getAllIndicaspByFolioSinidEstatusIgualA0ConArea(pasiienteGuardado.getIdfolio(), VPMedicaPlaza.userArea));
            } else {
                indicasps.addAll(indicaspDAO.getAllIndicaspByFolioSinidEstatusIgualA0(pasiienteGuardado.getIdfolio()));
            }

            colFolioHojaConsumo.setCellValueFactory(new PropertyValueFactory("idIndicasp"));
            colEstatus.setCellValueFactory(new PropertyValueFactory("estatusIndica"));
            colArea.setCellValueFactory(new PropertyValueFactory("area"));

            generarRadioButton();
            tabla.setItems(indicasps);

        } catch (SQLException ex) {
            Logger.getLogger(Ingresar_suministro_pacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarRadioButton() {
        Callback<TableColumn<Indicasp, String>, TableCell<Indicasp, String>> ver = (TableColumn<Indicasp, String> param) -> {
            final TableCell<Indicasp, String> cell = new TableCell<Indicasp, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Indicasp indicap = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-entrar-50.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro de ver indica #" + indicap.getIdIndicasp() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                irVistaIndica(indicap.getIdIndicasp());
                            }
                        });

                        setGraphic(btnVer);
                        setText(null);
                        btnVer.setGraphic(imgVer);
                    }
                }
            };
            return cell;
        };

        colConsumo.setCellFactory(ver);

        tabla.setRowFactory(tv -> new TableRow<Indicasp>() {
            @Override
            protected void updateItem(Indicasp item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setTooltip(null);
                } else {
                    String porcentaje = item.getStringPorcentaje(); // Método que calcula el porcentaje
                    String strString;
                    if (item.getPorcentaje() == 100.0000) {
                        strString = "Proceso completado";
                    }else{
                        strString = "Proceso compleatodo en: " + porcentaje + " con " + item.getArea() + " un estatus de: " + item.getString_Estatus_Indica() + " y validacion en farmacia: " + item.getString_Estatus_Indica();
                    }
                    Tooltip tooltip = new Tooltip(strString);
                    setTooltip(tooltip);
                }
            }
        });
    }

    private void irVistaIndica(int idIndicap) {
        try {
            // Cargar la vista de destino
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/Ingresar_suministro_paciente.fxml"));
            Parent root = loader.load();
            Ingresar_suministro_pacienteController destinoController = loader.getController();

            // Pasar el objeto a la vista de destino
            destinoController.setObjeto(pasiienteGuardado, idIndicap);
            // Crear un nuevo Stage para la vista de destino
            Stage destinoStage = new Stage();
            destinoStage.setTitle("EDITAR PACIENTE");
            destinoStage.setScene(new Scene(root));
            destinoStage.initModality(Modality.APPLICATION_MODAL);
            destinoStage.initStyle(StageStyle.UNDECORATED);

            // Mostrar el nuevo Stage de forma modal
            destinoStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(IndicasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ESTO ES LO QUE QUE HAY QUE AGREGAR AQUI
    private void iterarHojasIndicasCantidadEntregada() {
//        for (int i = 0; i < indicasps.size(); i++) {
//            canitdadEntrada(indicasps.get(i).getIdIndicasp());
//        }
    }

    private void iterarHojasIndicasCantidadDevolucion() {
        for (int i = 0; i < indicasps.size(); i++) {
            canitdadDevolucion(indicasps.get(i).getIdIndicasp());
        }
    }

    private void canitdadEntrada(int idIndicasp) {
        try {
            con = new Conexion().conectar2();
            indicasDetalleDAO = new IndicaDetalleDAO(con);
            consumodao = new ConsumosDAO(con);
            insumodao = new InsumosDAO(con);

            int id_estatus_indica_detalle = 0;

            List<IndicaDetalle> indicasDetallesCalculo = indicasDetalleDAO.getCantidadInsumosSuministradosByIdIndicasP(idIndicasp);
            List<IndicaDetalle> indicasDetalles = indicasDetalleDAO.getAllIndicaDetallesByIndicaP(idIndicasp);

            for (int i = 0; i < indicasDetalles.size(); i++) {

                for (int j = 0; j < indicasDetallesCalculo.size(); j++) {

                    if (indicasDetallesCalculo.get(j).getIdInsumo() == indicasDetalles.get(i).getIdInsumo()) {

                        if (indicasDetallesCalculo.get(j).getSuministrada() > 0) {

                            if (indicasDetalles.get(i).getSuministrada() < indicasDetallesCalculo.get(j).getCantidadEntregada()) {
                                id_estatus_indica_detalle = 2;
                            } else if (indicasDetalles.get(i).getSuministrada() == indicasDetallesCalculo.get(j).getCantidadEntregada()) {
                                id_estatus_indica_detalle = 1;
                            }

                            Insumo insumo = insumodao.optenerDatosInsumosConPrecioPaquete(indicasDetalles.get(i).getIdInsumo());
                            //agregarConsumo(insumo, insumo.getNombre(), indicasDetallesCalculo.get(j).getSuministrada(), con, indicasDetalles.get(i).isPaquete());

                        } else {

                            indicasDetalles.get(j).setIdEstatusIndicaDetalle(3);
                            indicasDetalles.get(j).setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                        }
                    }

                    indicasDetalleDAO.update(indicasDetalles.get(i));
                    id_estatus_indica_detalle = 0;

                }

            }
        } catch (SQLException e) {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("EL PROCESO NO PUDO SER COMPLETADO (1 sum)");
            alertaError.setContentText("DETALLE DEL MENSAJE: " + e.getMessage());
            alertaError.showAndWait();
            e.printStackTrace();
        }

    }

    private void canitdadDevolucion(int idIndicasp) {
        con = new Conexion().conectar2();
        indicasDetalleDAO = new IndicaDetalleDAO(con);
        indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(con);

        try {

            List<IndicaDetalle> indicasSuministrosPacientes = indicasDetalleDAO.getIndicaDetalleDevolucion(idIndicasp);

            for (int i = 0; i < indicasSuministrosPacientes.size(); i++) {
                indicasSuministroPacientesDAO.updateDevolucion(indicasSuministrosPacientes.get(i).getIdIndicaDetalle());
            }

            List<IndicaDetalle> indicasDetallesCalculo = indicasDetalleDAO.getCantidadInsumosDevolucion(pasiienteGuardado.getIdfolio());
            List<IndicaDetalle> indicasDetalles = indicasDetalleDAO.getAllIndicaDetallesByFolio(pasiienteGuardado.getIdfolio());
            for (int i = 0; i < indicasDetalles.size(); i++) {
                for (int j = 0; j < indicasDetallesCalculo.size(); j++) {
                    if (indicasDetalles.get(i).getIdIndicaDetalle() == indicasDetallesCalculo.get(j).getIdIndicaDetalle()) {
                        indicasDetalles.get(i).setCantidadDevolucion(indicasDetallesCalculo.get(j).getDevolucion());
                        indicasDetalles.get(i).setIdUsuarioModificacion(VPMedicaPlaza.userSystem);

                        indicasDetalleDAO.update(indicasDetalles.get(i));
                    }
                }
            }
        } catch (SQLException e) {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("EL PROCESO NO PUDO SER COMPLETADO (2 dev)");
            alertaError.setContentText("DETALLE DEL MENSAJE: " + e.getMessage());
            alertaError.showAndWait();
            romper = true;
            e.printStackTrace();
        }

    }

    private void llenarSuministroDevolucion() {
        con = new Conexion().conectar2();
        indicasDetalleDAO = new IndicaDetalleDAO(con);
        indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(con);
        insumodao = new InsumosDAO(con);

        int id_estatus_indica_detalle = 0;
        for (int i = 0; i < indicasps.size(); i++) {

            try {
                List<IndicaDetalle> indicasDetallesSINSUMINISTRO = indicasDetalleDAO.getAllIndicaDetallesByIndicaP(indicasps.get(i).getIdIndicasp());
                List<IndicaDetalle> indicasDetallesInsumos = indicasDetalleDAO.getDetallesDevSumDatos(indicasps.get(i).getIdIndicasp());

                for (int j = 0; j < indicasDetallesSINSUMINISTRO.size(); j++) {

                    indicasDetallesSINSUMINISTRO.get(j).setSuministrada(indicasDetallesInsumos.get(j).getCantidadSuministrada());
                    if (indicasDetallesInsumos.get(j).getCantidadSuministrada() > 0) {

                        if (indicasDetallesInsumos.get(j).getCantidadSuministrada() < indicasDetallesInsumos.get(j).getCantidadEntregada()) {
                            id_estatus_indica_detalle = 2;
                        } else if (indicasDetallesSINSUMINISTRO.get(j).getSuministrada() == indicasDetallesInsumos.get(j).getCantidadEntregada()) {
                            id_estatus_indica_detalle = 1;
                        }

                        Insumo insumo = insumodao.optenerDatosInsumosConPrecioPaquete(indicasDetallesSINSUMINISTRO.get(j).getIdInsumo());
                        agregarConsumo(indicasDetallesSINSUMINISTRO.get(j).getIdInsumo(), insumo, insumo.getNombre(), indicasDetallesInsumos.get(j).getCantidadSuministrada(), con, indicasDetallesSINSUMINISTRO.get(j).isPaquete());
                        indicasDetallesSINSUMINISTRO.get(j).setIdEstatusIndicaDetalle(id_estatus_indica_detalle);
                    } else {
                        indicasDetallesSINSUMINISTRO.get(j).setIdEstatusIndicaDetalle(3);
                        indicasDetallesSINSUMINISTRO.get(j).setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
                    }
                    indicasDetalleDAO.update(indicasDetallesSINSUMINISTRO.get(j));
                    id_estatus_indica_detalle = 0;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgregarConsumoHojasDeConsumosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void agregarConsumo(int idInusmo, Insumo insumo, String tipoInsumo, double cantidad, Connection cone, boolean paquete) {
        Consumo consumoMedico = new Consumo();
        ConsumosDAO consumoobjetodao = new ConsumosDAO(cone);

        consumoMedico.setTipo(tipoInsumo);
        consumoMedico.setCantidad(cantidad);
        consumoMedico.setMonto((cantidad * insumo.getPrecio_venta_unitaria()));
        consumoMedico.setFolio(pasiienteGuardado.getFolio());
        consumoMedico.setId_pasiente(pasiienteGuardado.getIdPaciente());
        consumoMedico.setId_PaqueteAlimento(0);
        consumoMedico.setId_tipo_consumo(1);
        consumoMedico.setId_folio(pasiienteGuardado.getIdfolio());
        consumoMedico.setId_producto_venta(idInusmo);
        consumoMedico.setId_estatus_consumo(1);
        consumoMedico.setPrecioUnitario(insumo.getPrecio_venta_unitaria());
        consumoMedico.setPaquete(paquete);
        consumoMedico.setId_usuario_creacion(VPMedicaPlaza.userSystem);
        consumoMedico.setId_usuario_modificacion(VPMedicaPlaza.userSystem);
        consumoMedico.setPrecioUnitarioPaquete(insumo.getPrecioVentaUnitariaPaquete());

        consumoobjetodao.insertarConsumoConEstatusYPaqueteConPrecio(consumoMedico);
        //   movimientoInventario(insumo.getPrecio_venta_unitaria(), pasiienteGuardado.getFolio(), consumoMedico.getId_producto_venta(), cantidad, consumoMedico.getTipo(), cone);
    }

    private void movimientoInventario(Double costo, String folioPaciente, int id_insumo, double cantidad, String tipo, Connection cone) {
        try {
            MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();//objeto
            MovimientoDetalle movimientoDetalle = new MovimientoDetalle();// objeto
            movimientopadredao = new MovimientoPadreDAO(cone);
            movimientodetalledao = new MovimientoDetalleDAO(cone);
            LocalDate fechaActual = LocalDate.now();
            Date fechaHoy = Date.valueOf(fechaActual);
            int id = 0;

            movimientoInventarioP.setTipo_mov(1);
            movimientoInventarioP.setId_origen(1);
            movimientoInventarioP.setId_destino(1);
            movimientoInventarioP.setId_proveedor(0);
            movimientoInventarioP.setFolio_mov(folioPaciente);
            movimientoInventarioP.setSubtotal(0);
            movimientoInventarioP.setDescuento(0);
            movimientoInventarioP.setImporte_impuesto(0);

            inventariosDAO = new InventariosDAO(cone);

            double existenciaTotal = inventariosDAO.obtenerPorId(id_insumo).getTotalExistencia();

            movimientoInventarioP.setTotal(costo * cantidad);

            movimientoInventarioP.setEstatus_movimiento(1);
            movimientoInventarioP.setObservaciones("COSUMO DE PACIENTE");
            movimientoInventarioP.setUsuario_registro(VPMedicaPlaza.userSystem);

            id = movimientopadredao.agregarMovimientoInventarioPINT(movimientoInventarioP);

            movimientoDetalle.setId_insumo(id_insumo);
            movimientoDetalle.setCaducidad(fechaHoy);
            movimientoDetalle.setLote_insumo(folioPaciente);

            movimientoDetalle.setMovimineto(cantidad);
            movimientoDetalle.setInventario_final(existenciaTotal - cantidad);
            movimientoDetalle.setId_insumo_mov_padre(id);
            if (pasiienteGuardado.getId_paquete() == 0) {
                movimientoDetalle.setCosto(costo * cantidad);
            } else {
                movimientoDetalle.setCosto(0);
            }

            movimientoDetalle.setInventario_inicial(0.0);
            System.out.println("" + movimientoDetalle.getInventario_inicial());
            movimientoDetalle.setUsuario_modificacion(VPMedicaPlaza.userSystem);
            movimientoDetalle.setNombre(tipo);
            movimientoDetalle.setExiste_lote(1);
            movimientodetalledao.CrearmovimientoconLote(movimientoDetalle);
        } catch (SQLException ex) {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("EL PROCESO NO PUDO SER COMPLETADO (1 mov&cons)");
            alertaError.setContentText("DETALLE DEL MENSAJE: " + ex.getMessage());
            alertaError.showAndWait();
            romper = true;
            Logger.getLogger(Ingresar_suministro_pacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarEstatusIndicaPFor() {
        for (int i = 0; i < indicasps.size(); i++) {
            actualizarEstatusIndicaPadre(indicasps.get(i).getIdIndicasp(), indicasps.get(i));
        }
    }

    private void actualizarEstatusIndicaPadre(int idIndicasp, Indicasp indicaspadre) {
        try {
            indicaspDAO = new IndicaspDAO(conexion.conectar2());

            Indicasp indicaspsCalculo = indicaspDAO.indicasPEstatusSinLista(idIndicasp);
//            List<Indicasp> indicaspss = indicaspDAO.getAllIndicaspByFolio(pasiienteGuardado.getIdfolio()); ESTO NO TIENE QUE IR

            indicaspadre.setEstatusIndica(indicaspsCalculo.getEstatus());
            indicaspadre.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
            indicaspDAO.update(indicaspadre);

            reporte(indicaspadre.getIdIndicasp());
        } catch (SQLException ex) {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("EL PROCESO NO PUDO SER COMPLETADO (3)");
            alertaError.setContentText("DETALLE DEL MENSAJE: " + ex.getMessage());
            alertaError.showAndWait();
//            romper = true;
            Logger.getLogger(Ingresar_suministro_pacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void reporte(int id_indicasp) {
        ReporteC reporte = new ReporteC("reporteIndicaConsumo");
        reporte.generarReporteIndicaDevlucion(pasiienteGuardado.getIdfolio(), id_indicasp);
    }

    ///////////////////////////////////////////////// DEVOLUCION DE INVENTARIO///////////////////////////////////////////////////////////////// 
    private void llenarTabla2() {
        try {
            con = conexion.conectar2();
            indicaspDAO = new IndicaspDAO(con);
            indicasps.clear();
            indicasps.addAll(indicaspDAO.obtenertodaslasindicasporidpaciente(pasiienteGuardado.getIdPaciente()));

            colFolioHojaConsumo.setCellValueFactory(new PropertyValueFactory("idIndicasp"));
            colEstatus.setCellValueFactory(new PropertyValueFactory("estatusIndica"));
            colArea.setCellValueFactory(new PropertyValueFactory("area"));

            //  generarRadioButton();
            tabla.setItems(indicasps);

        } catch (SQLException ex) {
            Logger.getLogger(Ingresar_suministro_pacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void acciondevolverindica(ActionEvent event) throws SQLException {
        indicasDetalleDAO = new IndicaDetalleDAO(con);
        indicasSuministroPacientesDAO = new IndicaSuministroPacientesDAO(con);
        consumodao = new ConsumosDAO(con);
        List<IndicaDetalle> listaindicadevo = new ArrayList<>();
        listaindicadevo = indicasDetalleDAO.traerlstaparadovolver(tabla.getSelectionModel().getSelectedItem().getIdIndicasp());
        Indicasp indicap = tabla.getSelectionModel().getSelectedItem();
        for (int i = 0; i < listaindicadevo.size(); i++) {

            System.out.println("indica_detalle " + listaindicadevo.get(i).getIdIndicaDetalle() + " id_insumo " + listaindicadevo.get(i).getIdInsumo() + " suministrada " + listaindicadevo.get(i).getCantidadSuministrada());
            //     movimientoInventario2(0.0, pasiienteGuardado.getFolio(), listaindicadevo.get(i).getIdInsumo(), listaindicadevo.get(i).getCantidadSuministrada(), listaindicadevo.get(i).getNombreInsumo(), con);

            indicasDetalleDAO.actualizarDevolucionACero(listaindicadevo.get(i).getIdIndicaDetalle(), listaindicadevo.get(i).getIdInsumo());

            if (listaindicadevo.get(i).getCantidadSuministrada() != 0.0) {
                consumodao.cancelarconsumo(pasiienteGuardado.getIdPaciente(), listaindicadevo.get(i).getIdInsumo(), listaindicadevo.get(i).getCantidadSuministrada());
            }

        }
        indicasSuministroPacientesDAO.updateIndicasSuministroPaciente(indicap.getIdIndicasp());
        actualizarEstatusIndicaPadre2(indicap.getIdIndicasp(), indicap);

        alertaConfirmacion.setTitle("DEVOLUCION COMPLETA");
        alertaConfirmacion.setHeaderText("DEVOLUCION COMPLETA.");
        //alertaError.setContentText("DETALLE DEL MENSAJE: " + ex.getMessage());
        alertaConfirmacion.showAndWait();
        llenarTabla2();
    }

    private void movimientoInventario2(Double costo, String folioPaciente, int id_insumo, double cantidad, String tipo, Connection cone) {
        try {
            MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();//objeto
            MovimientoDetalle movimientoDetalle = new MovimientoDetalle();// objeto
            movimientopadredao = new MovimientoPadreDAO(cone);
            movimientodetalledao = new MovimientoDetalleDAO(cone);
            LocalDate fechaActual = LocalDate.now();
            Date fechaHoy = Date.valueOf(fechaActual);
            int id = 0;

            movimientoInventarioP.setTipo_mov(4);
            movimientoInventarioP.setId_origen(1);
            movimientoInventarioP.setId_destino(1);
            movimientoInventarioP.setId_proveedor(0);
            movimientoInventarioP.setFolio_mov(folioPaciente);
            movimientoInventarioP.setSubtotal(0);
            movimientoInventarioP.setDescuento(0);
            movimientoInventarioP.setImporte_impuesto(0);

            inventariosDAO = new InventariosDAO(cone);

            double existenciaTotal = inventariosDAO.obtenerPorId(id_insumo).getTotalExistencia();

            movimientoInventarioP.setTotal(costo * cantidad);

            movimientoInventarioP.setEstatus_movimiento(1);
            movimientoInventarioP.setObservaciones("DEVOLUCION DE PACIENTE");
            movimientoInventarioP.setUsuario_registro(VPMedicaPlaza.userSystem);

            id = movimientopadredao.agregarMovimientoInventarioPINT(movimientoInventarioP);

            movimientoDetalle.setId_insumo(id_insumo);
            movimientoDetalle.setCaducidad(fechaHoy);
            movimientoDetalle.setLote_insumo(folioPaciente);
            movimientoDetalle.setInventario_inicial(0.0);
            movimientoDetalle.setMovimineto(cantidad);
            movimientoDetalle.setInventario_final(existenciaTotal + cantidad);
            movimientoDetalle.setId_insumo_mov_padre(id);
            if (pasiienteGuardado.getId_paquete() == 0) {
                movimientoDetalle.setCosto(costo * cantidad);
            } else {
                movimientoDetalle.setCosto(0);
            }

            movimientoDetalle.setUsuario_modificacion(VPMedicaPlaza.userSystem);
            movimientoDetalle.setNombre(tipo);
            movimientoDetalle.setExiste_lote(1);
            movimientodetalledao.CrearmovimientoconLote(movimientoDetalle);
        } catch (SQLException ex) {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("EL PROCESO NO PUDO SER COMPLETADO (1 mov&cons)");
            alertaError.setContentText("DETALLE DEL MENSAJE: " + ex.getMessage());
            alertaError.showAndWait();
            romper = true;
            Logger.getLogger(Ingresar_suministro_pacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarEstatusIndicaPadre2(int idIndicasp, Indicasp indicaspadre) {
        try {
            indicaspDAO = new IndicaspDAO(conexion.conectar2());

            Indicasp indicaspsCalculo = indicaspDAO.indicasPEstatusSinLista(idIndicasp);
//            List<Indicasp> indicaspss = indicaspDAO.getAllIndicaspByFolio(pasiienteGuardado.getIdfolio()); ESTO NO TIENE QUE IR

            indicaspadre.setEstatusIndica(0);
            indicaspadre.setIdUsuarioModificacion(VPMedicaPlaza.userSystem);
            indicaspDAO.update(indicaspadre);

            //  reporte(indicaspadre.getIdIndicasp());
        } catch (SQLException ex) {
            alertaError.setTitle("ERROR");
            alertaError.setHeaderText("EL PROCESO NO PUDO SER COMPLETADO (3)");
            alertaError.setContentText("DETALLE DEL MENSAJE: " + ex.getMessage());
            alertaError.showAndWait();
            romper = true;
            Logger.getLogger(Ingresar_suministro_pacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
