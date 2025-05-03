/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.CitaQuirofano;
import clases_hospital.Consumo;
import clases_hospital.ConsumoPacienteMezclas;
import clases_hospital.Folio;
import clases_hospital.Inventario;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital.NumerosALetras;
import clases_hospital_DAO.ActualizacionFolioDAO;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.ConsumoPacienteMezclasDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import clases_hospital_DAO.PagosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import reportes.ReporteC;
import vistasAuxiliares.AgregarConsumoPacienteController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class VisualizarFolioController implements Initializable {

    String tipo;
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    int idPacient;
    int idFolio;
    double montoHastaElMomento;
    FoliosDAO foliodao;
    ConsumoPacienteMezclasDAO consumoPacienteMezclasDAO;
    CostosDAO costodao;
    ActualizacionFolioDAO actualizarfoliodao;
    CitaQuirofanoDAO citaQuirofanodao;
    double totaconiva;
    int idpaquete;
    double abonoTotal;
    double adeudo;

    PagosDAO pagodao;

    @FXML
    private Text txtNombre;
    @FXML
    private Text txtCuentaTotal;
    @FXML
    private Text txtFolio;
    @FXML
    private TableView<ConsumoPacienteMezclas> tablaConsumo;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, String> clmTipoConsumo;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnRerporte;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Integer> clmConsumido;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Double> clmPaquete;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Double> clmExedente;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Double> clmPrecioUnitario;
    @FXML
    private TableColumn<ConsumoPacienteMezclas, Double> clmSubTotal;
    @FXML
    private Text txtPaquete;
    @FXML
    private Text txtCostoPaquete;
    @FXML
    private Button btnReporteCierre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    public void recibirDatos(int idPaciente, int idFolio, String nombrePaciente, String folio, double montoHastaElMomento, int idpaq) throws SQLException {

        txtNombre.setText(nombrePaciente);
        txtFolio.setText(folio);
        this.montoHastaElMomento = montoHastaElMomento;
        this.idPacient = idPaciente;
        this.idFolio = idFolio;
        this.idpaquete = idpaq;
        llenarTabla();
        acutalizarFolio();

        if (VPMedicaPlaza.userNameSystem.equals("ENFERMERIA")) {
            btnRerporte.setVisible(false);
            btnReporteCierre.setVisible(false);
        }
    }

    public void llenarTabla() throws SQLException {
        Connection connection = null;

        try {
            connection = conexion.conectar2();
            double totalSubtotal = 0.0;
            DecimalFormat formato = new DecimalFormat("#.##");

            ConsumoPacienteMezclas consumoPacienteMezclas;
            consumoPacienteMezclasDAO = new ConsumoPacienteMezclasDAO(connection);
            ObservableList<ConsumoPacienteMezclas> consumoPaciente = consumoPacienteMezclasDAO.llenarTablaVisualizarFolioController(idFolio, idpaquete);
            consumoPacienteMezclas = consumoPacienteMezclasDAO.datosPaquete(idFolio);
            txtCostoPaquete.setText(String.valueOf(consumoPacienteMezclas.getMonto()));
            txtPaquete.setText(String.valueOf(consumoPacienteMezclas.getNombre_paquete()));
            clmTipoConsumo.setCellValueFactory(new PropertyValueFactory("insumo"));
            clmConsumido.setCellValueFactory(new PropertyValueFactory("consumo"));
            editarTablaConsumo();
            clmPaquete.setCellValueFactory(new PropertyValueFactory("cantidadPaquete"));
            clmExedente.setCellValueFactory(new PropertyValueFactory("exedenteMezcla"));
            clmPrecioUnitario.setCellValueFactory(new PropertyValueFactory("precioUnitario"));
            clmSubTotal.setCellValueFactory(new PropertyValueFactory("subtotal"));

            tablaConsumo.setItems(consumoPaciente);
            eliminarTablaConsumoPaciente(consumoPaciente);
            for (ConsumoPacienteMezclas consumo : tablaConsumo.getItems()) {
                double subtotal = consumo.getSubtotal();
                totalSubtotal += subtotal;
               
            }
           
            double iva = totalSubtotal * 1.16;
       
//            System.out.print("SUB TOTAL: "); System.err.println(iva);
//            System.out.print("TOTAL IVA: "); System.err.println(totalIva); System.out.print("TOTAL IVA:asdasdasdasdasdasdasdasdasdasd ");
//            String ivatex = formato.format(totalIva);

//            txtCuentaTotal.setText(ivatex);

            pagodao = new PagosDAO(connection);
            abonoTotal = pagodao.optenerTotalPagosPaciente(idFolio);
//         
            adeudo = iva - abonoTotal;
          
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    @FXML
    private void actionAgregarConsumo(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares/AgregarConsumoPaciente.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setMaximized(true);
        stage.setTitle("SERVICIO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        AgregarConsumoPacienteController agregarConsumoPaciente = loader.getController();
        stage.showAndWait();
        this.tablaConsumo.getItems().clear();
        llenarTabla();

    }

    @FXML
    private void actionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionBtnReporteCierre(ActionEvent event) throws IOException, SQLException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CambioDEPaquete.fxml"));
        Parent root = loader.load();
        CambioDEPaqueteController destinoController = loader.getController();

        // Pasar el objeto a la vista de destino
        destinoController.recibirDatos(this.idFolio, txtNombre.getText(), this.idPacient, this.idpaquete);

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("CAMBIO DE PAQUETE MEDICO");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.WINDOW_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

    }

    @FXML
    private void actionBtnReporteDetalle(ActionEvent event) {
        Connection connection = null;
        try {
            NumerosALetras numeroLetras = new NumerosALetras(totaconiva);
            String numeroALetra = numeroLetras.getCantidadString();
            int idPaquete;
            DecimalFormat formato = new DecimalFormat("#.##");
            String numeroFormateado;

            ConsumoPacienteMezclas consumo;
            connection = conexion.conectar2();
            foliodao = new FoliosDAO(connection);
            CitaQuirofanoDAO citaquirofanodao = new CitaQuirofanoDAO(connection);
            consumoPacienteMezclasDAO = new ConsumoPacienteMezclasDAO(connection);
            idPaquete = consumoPacienteMezclasDAO.optenerIdPaquete(idFolio);
            consumo = consumoPacienteMezclasDAO.datosReporte(idFolio);
            Folio folio = foliodao.obtenerFolioPorIdConNumeroHabitacion(idFolio);
       
            CitaQuirofano citaquirofano = citaquirofanodao.obteCitaQuirofanoPorIdFolio(idFolio);
            int idQuirofano;
            if (citaquirofano == null) {
                idQuirofano = 0;
            } else {
                idQuirofano = citaquirofano.getIdQuirofano();
            }

            if (folio.getId_estatus_hospitalizacion() < 2) {
                //SIGNIFICA QUE ESTA EN EL HOSPITAL Y REFRESCA SU HORA DE SALIDA DE LA HABITACION CON UN NOW()
              
                foliodao.actualizarHoraSalida(folio.getId(), folio.getId_habitacion(), folio.getNumero_habitacion());
                ReporteC reporte = new ReporteC("Rpt_CorteDetalleCuenta");
               
                numeroFormateado = formato.format(adeudo);
                Double numeroyaformateado = Double.parseDouble(numeroFormateado);
                reporte.generarCorteDetalles(idFolio,
                        txtFolio.getText(),
                        consumo.getFecha(),
                        consumo.getHora(),
                        txtNombre.getText(),
                        consumo.getMedico(),
                        consumo.getHabitacion(),
                        txtPaquete.getText(),
                        abonoTotal,//Aqui
                        totaconiva,
                        numeroyaformateado,
                        idPaquete,
                        numeroALetra.toUpperCase(),
                        idQuirofano,
                        folio.getId_habitacion(),
                        folio.getNumero_habitacion()
                );
            } else {
                //AGARRA LA ULTIMA FECHA DE SALIDA
                ReporteC reporte = new ReporteC("Rpt_CorteDetalleCuenta");
                numeroFormateado = formato.format(adeudo);
                Double numeroyaformateado = Double.parseDouble(numeroFormateado);
                reporte.generarCorteDetalles(idFolio,
                        txtFolio.getText(),
                        consumo.getFecha(),
                        consumo.getHora(),
                        txtNombre.getText(),
                        consumo.getMedico(),
                        consumo.getHabitacion(),
                        txtPaquete.getText(),
                        adeudo,//AQUI
                        totaconiva,
                        numeroyaformateado,
                        idPaquete,
                        numeroALetra.toUpperCase(),
                        idQuirofano,
                        folio.getId_habitacion(),
                        folio.getNumero_habitacion()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

    }

    private void acutalizarFolio() throws SQLException {
        con = conexion.conectar2();
        actualizarfoliodao = new ActualizacionFolioDAO(con);
        foliodao = new FoliosDAO(con);
        citaQuirofanodao = new CitaQuirofanoDAO(con);
        Folio folio = foliodao.obtenerFolioPorId(idFolio);
        CitaQuirofano citaQuirofano = citaQuirofanodao.obteCitaQuirofanoPorIdFolio(idFolio);
        int id_CitaQuirofano;
        if (citaQuirofano == null) {
            id_CitaQuirofano = 0;
        } else {
            id_CitaQuirofano = citaQuirofano.getIdQuirofano();
        }
        double sumatoriatotal = actualizarfoliodao.calculateTotalSubtotal(actualizarfoliodao.getReportItems(folio.getId_paquete()));
        adeudo = (sumatoriatotal*1.16) - abonoTotal;
        
        totaconiva = sumatoriatotal * 1.16;
        folio.setMontoHastaElMomento(sumatoriatotal);
        double saldoacubir = totaconiva - abonoTotal;
        folio.setSaldoACubrir(saldoacubir);
        foliodao.actualizarFolio(folio);
    }

    private void eliminarTablaConsumoPaciente(ObservableList<ConsumoPacienteMezclas> consumopaciente) {
//        tablaConsumo.setRowFactory(tableView -> {
//            TableRow<ConsumoPacienteMezclas> row = new TableRow<>();
//            ContextMenu cxmCirugia = new ContextMenu();
//
//            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
//            descartarConsumo.setOnAction(event -> {
//                con = conexion.conectar2();
//                ConsumoPacienteMezclas sercicios = row.getItem();
//                actualizarInventario(sercicios, 0);
//
//                consumopaciente.remove(sercicios);
//            });
//            cxmCirugia.getItems().add(descartarConsumo);
//
//            row.contextMenuProperty().bind(
//                    Bindings.when(row.emptyProperty())
//                            .then((ContextMenu) null)
//                            .otherwise(cxmCirugia)
//            );
//            return row;
//        });
    }

    private void editarTablaConsumo() {

//        clmConsumido.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        clmConsumido.setOnEditCommit(event -> {
//            // obtener el objeto Consumo que está siendo editado
//            ConsumoPacienteMezclas consumopacientemezclascalc = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            int cantidadInicial = consumopacientemezclascalc.getConsumo();
//            // actualizar el valor de cantidad en el objeto Consumo
//            consumopacientemezclascalc.setConsumo(event.getNewValue());
//            actualizarInventario(consumopacientemezclascalc, (consumopacientemezclascalc.getConsumo() - cantidadInicial));
//
//            tablaConsumo.refresh();
//        });
//        clmConsumido.setEditable(true);
//        tablaConsumo.setEditable(true);
    }

    private void actualizarInventario(ConsumoPacienteMezclas conumopacmez, int cantidaddevolucion) {
        con = conexion.conectar2();
        InventariosDAO inventariodao = new InventariosDAO(con);
        MovimientoPadreDAO mopdao = new MovimientoPadreDAO(con);
        MovimientoDetalleDAO moddao = new MovimientoDetalleDAO(con);
        ConsumosDAO consumodao = new ConsumosDAO(con);
        foliodao = new FoliosDAO(con);
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
            Inventario inventariosUpdate = inventariodao.obtenerDatosPorIdInsumo(conumopacmez.getId_insumo());
            Consumo consumo = consumodao.obtenerConsumoPorIdInsumoandIdFolio(conumopacmez.getId_insumo(), idFolio);
            LocalDate currentDate = LocalDate.now();
            Date fecha = Date.valueOf(currentDate);

            mod.setId_insumo_mov_padre(id_moip);
            mod.setId_insumo(conumopacmez.getId_insumo());
            mod.setCaducidad(fecha);
            mod.setLote_insumo(folio.getFolio());
            mod.setInventario_inicial(inventariosUpdate.getTotalExistencia());
            if (cantidaddevolucion == 0) {
                mod.setMovimineto(conumopacmez.getCantidadEntregada());
                mod.setInventario_final(inventariosUpdate.getTotalExistencia() + conumopacmez.getConsumo());
                consumo.setId_estatus_consumo(0);
            } else {
                mod.setMovimineto(cantidaddevolucion);
                mod.setInventario_final(inventariosUpdate.getTotalExistencia() + cantidaddevolucion);
                consumo.setCantidad(conumopacmez.getConsumo());
                consumo.setId_estatus_consumo(1);
            }
            mod.setCosto(0);
            mod.setUsuario_modificacion(VPMedicaPlaza.userSystem);

            moddao.create(mod);
            consumodao.actualizarConumo(consumo);

            acutalizarFolio();

        } catch (SQLException ex) {
            Logger.getLogger(VisualizarFolioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
