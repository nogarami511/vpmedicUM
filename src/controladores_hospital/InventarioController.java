/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Usuario;
import clases_hospital.Costo;
import clases_hospital.GenerarReabastoInsumo;
import clases_hospital.Insumo;
import clases_hospital.Inventario;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital.TipoInsumo;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.GenerarReabastoInsumoDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import clases_hospital_DAO.TipoInsumoDAO;
import clases_hospital_DAO.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;
import vistasAuxiliares_hospital.VerLotesInsumosController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class InventarioController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Inventario> inventarios = FXCollections.observableArrayList();
    ObservableList<TipoInsumo> tipoinsumos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    InventariosDAO inventariodao;
    InsumosDAO insumodao;
    GenerarReabastoInsumoDAO generarReabastoinsumodao;
    TipoInsumoDAO tipoinsumodao;
    CostosDAO costodao;
    int inttipo, id, idTipodeINSUMO;
    public int id_movimiento_padre = 0;
    boolean primeravez;

    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;
    UsuarioDAO usuariodao;

    Usuario usuario;

    @FXML
    private TableView<Inventario> tabla;
    @FXML
    private TableColumn inventarioNombre;
    @FXML
    private TableColumn<Inventario, Double> inventarioExistencia;
    @FXML
    private TableColumn colMaximos;
    @FXML
    private TableColumn colMinimos;
    @FXML
    private TableColumn inventarioPresentacion;
    @FXML
    private TableColumn colFormula;
    @FXML
    private TableColumn<Inventario, Double> colFalta;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txfFormula;
    @FXML
    private RadioButton rdbTipo;
    @FXML
    private ComboBox<TipoInsumo> cmbTipo;
    @FXML
    private RadioButton rdbNombre;
    @FXML
    private Button btnBuscar;
    @FXML
    private RadioButton rdbReabastecer;
    @FXML
    private Button btnReabastecer;
    @FXML
    private Pagination pagInven;
    @FXML
    private Button btnGenerarReabasto;
    @FXML
    private Button btnConcilacionInventario1;
    @FXML
    private Button btnCaducados;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipo.setDisable(true);
        btnBuscar.setDisable(true);
        txfFormula.setDisable(true);
        primeravez = true;
//        btnReporte.setVisible(false);
        try {
            usuariodao = new UsuarioDAO(conexion.conectar2());
            usuario = usuariodao.leer(VPMedicaPlaza.userSystem);
            // TODO
            llenarTabla();
            tabla.setEditable(true);
//            genearReabastos();
        } catch (SQLException ex) {
            Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionAgregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/EntradasInventario.fxml"));
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
    private void accionVerlotesInsumos(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VerLotesInsumos.fxml"));
        
        
        Parent root = fxml.load();
        VerLotesInsumosController verlotes = fxml.getController();
        verlotes.recibeIdInsumi(tabla.getSelectionModel().getSelectedItem().getId_insumo(),tabla.getSelectionModel().getSelectedItem().getNombre());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("Lotes Insumos");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML
    private void accionSacarInsumosInventario(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/SacardeInventario.fxml"));
        
        
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
       // stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("RETIRAR DE INVENTARIO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void generarReporte(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ImprimirReporte.fxml"));
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
    private void accionFormula(ActionEvent event) throws SQLException {
        rdbTipo.setSelected(false);
        rdbNombre.setSelected(false);
        txfFormula.setDisable(true);
        cmbTipo.setDisable(true);
        btnBuscar.setDisable(false);
        inttipo = 0;
        llenartablaBusquedaPedir();
    }

    @FXML
    private void accionTipo(ActionEvent event) throws SQLException {

        rdbNombre.setSelected(false);
        rdbReabastecer.setSelected(false);
        txfFormula.setDisable(true);
        cmbTipo.setDisable(false);
        btnBuscar.setDisable(true);
        inttipo = 2;

        tipoinsumodao = new TipoInsumoDAO(conexion.conectar2());

        tipoinsumos.addAll(tipoinsumodao.obtenerTodos());
        cmbTipo.setItems(tipoinsumos);

        cmbTipo.setOnAction(e -> {
            try {
                TipoInsumo tipoInsumo = cmbTipo.getValue();
                idTipodeINSUMO = tipoInsumo.getId();
                System.out.println(idTipodeINSUMO);
                llenarTablaPorTipoDeInsumo();
            } catch (SQLException ex) {
                Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    private void accionNombre(ActionEvent event) {
        rdbTipo.setSelected(false);
        rdbReabastecer.setSelected(false);
        txfFormula.setDisable(false);
        cmbTipo.setDisable(true);
        btnBuscar.setDisable(false);
        inttipo = 1;

        insumodao = new InsumosDAO(conexion.conectar2());
        try {
            AutoCompletionBinding<Insumo> insumos = TextFields.bindAutoCompletion(txfFormula, insumodao.obtenerTodosInsumos());
            insumos.setPrefWidth(800);
            insumos.setOnAutoCompleted(e -> {
                Insumo insumoSelect = e.getCompletion();
                id = insumoSelect.getId();
            });
        } catch (SQLException ex) {
            Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionBuscar(ActionEvent event) throws SQLException {
        switch (inttipo) {
            case 1:
                llenartablaBusquedaUnInsumo();
                break;
            default:
                llenarTabla();
                rdbNombre.setSelected(false);
                rdbReabastecer.setSelected(false);
                txfFormula.setDisable(false);
                break;
        }
    }

    private void llenarTabla() {
        inventariodao = new InventariosDAO(conexion.conectar2());

        inventarioNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colFormula.setCellValueFactory(new PropertyValueFactory("formula"));
        inventarioPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        colMaximos.setCellValueFactory(new PropertyValueFactory("maximos"));
        colMinimos.setCellValueFactory(new PropertyValueFactory("minimos"));
        inventarioExistencia.setCellValueFactory(new PropertyValueFactory("totalExistencia"));
        colFalta.setCellValueFactory(new PropertyValueFactory("falta"));

        pintarCelda();
        centrarTabla();

        editarInventario();

        try {
            int paginas = (int) Math.ceil((double) inventariodao.contarFilas() / filaPorPagina());
            pagInven.setPageCount(paginas);
            pagInven.setCurrentPageIndex(0); // Establecemos la página actual en 0

            pagInven.setPageFactory(this::crearPagina); // Llamamos al método crearPagina para cargar los datos

//            llenarBuscador(); // Llena el buscador para autocompletar
        } catch (SQLException ex) {
            Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int filaPorPagina() {
        return 25;
    }

    private Node crearPagina(int pageIndex) {
        int fromIndex = pageIndex * filaPorPagina();

        try {
            List<Inventario> insumosPaginados = inventariodao.getDatosByPage(pageIndex, filaPorPagina());
            tabla.getItems().setAll(insumosPaginados);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BorderPane(tabla);
    }

    private void llenartablaBusquedaPedir() throws SQLException {
        inventarios.clear();
        tabla.getItems().clear();
        inventariodao = new InventariosDAO(conexion.conectar2());

        inventarios.addAll(inventariodao.obtenerDatosPorPedir());

        inventarioNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colFormula.setCellValueFactory(new PropertyValueFactory("formula"));
        inventarioPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        colMaximos.setCellValueFactory(new PropertyValueFactory("maximos"));
        colMinimos.setCellValueFactory(new PropertyValueFactory("minimos"));
        inventarioExistencia.setCellValueFactory(new PropertyValueFactory("totalExistencia"));
        colFalta.setCellValueFactory(new PropertyValueFactory("falta"));

        pintarCelda();
        centrarTabla();

        editarInventario();

        tabla.setItems(inventarios);
    }

    private void llenarTablaPorTipoDeInsumo() throws SQLException {
        inventarios.clear();
        tabla.getItems().clear();
        inventariodao = new InventariosDAO(conexion.conectar2());

        inventarios.addAll(inventariodao.obtenerDatosPorTipoDeInsumo(idTipodeINSUMO));

        inventarioNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colFormula.setCellValueFactory(new PropertyValueFactory("formula"));
        inventarioPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        colMaximos.setCellValueFactory(new PropertyValueFactory("maximos"));
        colMinimos.setCellValueFactory(new PropertyValueFactory("minimos"));
        inventarioExistencia.setCellValueFactory(new PropertyValueFactory("totalExistencia"));
        colFalta.setCellValueFactory(new PropertyValueFactory("falta"));

        pintarCelda();
        centrarTabla();

        editarInventario();

        tabla.setItems(inventarios);

    }

    private void llenartablaBusquedaUnInsumo() throws SQLException {
        inventarios.clear();
        tabla.getItems().clear();
        insumodao = new InsumosDAO(conexion.conectar2());

        inventarios.addAll(inventariodao.obtenerDatosPorIdInsumo(id));

        inventarioNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colFormula.setCellValueFactory(new PropertyValueFactory("formula"));
        inventarioPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        colMaximos.setCellValueFactory(new PropertyValueFactory("maximos"));
        colMinimos.setCellValueFactory(new PropertyValueFactory("minimos"));
        inventarioExistencia.setCellValueFactory(new PropertyValueFactory("totalExistencia"));
        colFalta.setCellValueFactory(new PropertyValueFactory("falta"));

        pintarCelda();
        centrarTabla();

        editarInventario();

        tabla.setItems(inventarios);

        txfFormula.setText("");
    }

    private void centrarTabla() {
        inventarioNombre.setStyle("-fx-alignment: CENTER;");
        colFormula.setStyle("-fx-alignment: CENTER;");
        inventarioPresentacion.setStyle("-fx-alignment: CENTER;");
        inventarioExistencia.setStyle("-fx-alignment: CENTER;");
        colMaximos.setStyle("-fx-alignment: CENTER;");
        colMinimos.setStyle("-fx-alignment: CENTER;");
        colFalta.setStyle("-fx-alignment: CENTER;");
    }

    private void pintarCelda() {
        colFalta.setCellFactory(column -> {
            return new TableCell<Inventario, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle(""); // Restablecer el estilo de la celda si está vacía
                    } else {
                        setText(item.toString());

                        // Personalizar el estilo de la celda
                        if (item > 0) {
                            setStyle("-fx-background-color: #E74C3C; -fx-alignment: CENTER; -fx-border-color: #c9c9c9; -fx-border-width: 0.5px;");
                        } else {
                            setStyle("-fx-alignment: CENTER;"); // Restablecer el estilo de la celda si el valor es cero
                        }
                    }
                }
            };
        });

    }

   

    private void editarInventario() {
        if (usuario.getCargo().equals("ADMINISTRATIVO") || usuario.getCargo().equals("SISTEMAS") || usuario.getCargo().equals("CONTABILIDAD") || usuario.getCargo().equals("CONTRALORIA")) {
            inventarioExistencia.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            inventarioExistencia.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                Inventario inventaioSelect = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                double anteriorExistencia = inventaioSelect.getTotalExistencia();
                inventaioSelect.setTotalExistencia(event.getNewValue());

                try {
                    actualizarTabla(inventaioSelect.getId_insumo(), inventaioSelect.getTotalExistencia(), anteriorExistencia);
                } catch (SQLException ex) {
                    Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                tabla.refresh();
            });
            inventarioExistencia.setEditable(true);
        }

    }

    private void actualizarTabla(int id_insumo, double cantidadnueva, double cantidadanterior) throws SQLException {
        con = conexion.conectar2();
        inventariodao = new InventariosDAO(con);
        movimientopadredao = new MovimientoPadreDAO(con);
        movimientodetalledao = new MovimientoDetalleDAO(con);

        double movimientoint;

        String observacion;
        LocalDateTime currentDateTime = LocalDateTime.now();
        Date sqlDate = Date.valueOf(currentDateTime.toLocalDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);

        if (primeravez) {
            primeravez = false;
            MovimientoInventarioP movimiento = new MovimientoInventarioP();

            movimiento.setTipo_mov(1);

            movimiento.setId_proveedor(1);
            movimiento.setId_origen(1);
            movimiento.setId_destino(1);
            movimiento.setFolio_mov("A-INVENTARIO" + formattedDateTime);
            movimiento.setSubtotal(0);
            movimiento.setImporte_impuesto(0);
            movimiento.setDescuento(0);
            movimiento.setTotal(0);
            movimiento.setEstatus_movimiento(1);
            movimiento.setObservaciones("AJUSTE DE INVENTARIO");
            movimiento.setUsuario_registro(VPMedicaPlaza.userSystem);

            id_movimiento_padre = movimientopadredao.agregarMovimientoInventarioPINT(movimiento);
        }

        MovimientoDetalle movdet = new MovimientoDetalle();

        if (cantidadanterior > cantidadnueva) {
            observacion = "A-NEGATIVO-" + formattedDateTime;
            movimientoint = cantidadanterior - cantidadnueva;
        } else {
            observacion = "A-POSITIVO-" + formattedDateTime;
            movimientoint = cantidadnueva - cantidadanterior;
        }

        movdet.setId_insumo_mov_padre(id_movimiento_padre);
        movdet.setId_insumo(id_insumo);
        movdet.setCaducidad(sqlDate);
        movdet.setLote_insumo(observacion);
        movdet.setInventario_inicial(cantidadnueva);
        movdet.setMovimineto(movimientoint);
        movdet.setInventario_final(cantidadnueva);
        movdet.setCosto(0);
        movdet.setUsuario_modificacion(VPMedicaPlaza.userSystem);

        movimientodetalledao.create(movdet);
    }
    
    
    ////////////////////////////////////// GENERAR REABASTOS ////////////////////////////////////////
    
        @FXML
    private void accionGenerarReabasto(ActionEvent event) {
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("¿Estas seguro de generar reabasto?");
        alertaConfirmacion.setContentText("Estre proceso podria tardar");
        Optional<ButtonType> action = alertaConfirmacion.showAndWait();
        if (action.get() == ButtonType.OK) {
            btnGenerarReabasto.setDisable(true);
            try {
                genearReabastos();
            } catch (SQLException ex) {
                ex.printStackTrace();
                alertaError.setHeaderText("ERROR");
                alertaError.setContentText("HA OCURRIDO UN ERROR INESPERADO");
                alertaError.setContentText(ex.getMessage());
                alertaError.showAndWait();
            }
            btnGenerarReabasto.setDisable(false);
        }
    }
    
     private void genearReabastos() throws SQLException {
        con = conexion.conectar2();
        generarReabastoinsumodao = new GenerarReabastoInsumoDAO(con);
        
      generarReabastoinsumodao.GenerarReabastoporTipoInsumo();
    }


    
    
    
        @FXML
    private void accionReabastecer(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/GenerarCompra2.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("GENERAR COMPRA");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void accionReporteCaducados(ActionEvent event) {
        ReporteC reporte =  new ReporteC("RPT_CADUCADOS");
        
        reporte.generarReporteInsumosCaducados();
    }

}
