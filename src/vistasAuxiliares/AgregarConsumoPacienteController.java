/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.Conexion;
import clases_hospital.Almacen;
import clases_hospital.Consumo;
import clases_hospital.Folio;
import clases_hospital.Insumo;
import clases_hospital.Inventario;
import clases_hospital.KitMedicoyConsumiblesCocina;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital.PaqueteAlimento;
import clases_hospital_DAO.AlmacenDAO;
import clases_hospital_DAO.ConsumoQuirofanoDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.KitMedicoyConsumiblesCocinaDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import controladores_hospital.ConfiguracionPaqueteMedicoController;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import net.sf.jasperreports.engine.JRException;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.Reporte;
import vpmedicaplaza.VPMedicaPlaza;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class AgregarConsumoPacienteController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaSucces = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaWarring = new Alert(Alert.AlertType.WARNING);
    int idInsumo;
    int idPaqueteAlimento;
    int idPaciente;
    int idFolio;
    int idPaquete;
    int idTipoConsumo;
    ObservableList<Folio> folios = FXCollections.observableArrayList();
    ObservableList<PaqueteAlimento> pkAlimento = FXCollections.observableArrayList();
    List<Folio> folioKits = FXCollections.observableArrayList();
    ObservableList<Almacen> almacenes = FXCollections.observableArrayList();

    InsumosDAO insumodao;
    KitMedicoyConsumiblesCocinaDAO kitmedicoyconsumiblecosinadao;
    InventariosDAO inventariodao;

    AlmacenDAO almacendao;
    ConsumosDAO consumosdao;

    Almacen almacen;

    PaqueteAlimento paqueteAlimentoSeleccionado;

    private int idinsumo;
    @FXML
    private TextField txfInsumo;
    @FXML
    private TableView<Folio> tabla;
    @FXML
    private TableColumn<Folio, String> clmProducto;
    @FXML
    private TableColumn<Folio, Double> clmCantidad;
    @FXML
    private TextField txfCantidad;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnCapturar;
    @FXML
    private Text txtNombrePaciente;
    @FXML
    private Text txtFolioPaciente;
    @FXML
    private TextField txfAliemento;
    @FXML
    private TextField txfCantidadAliemento;
    @FXML
    private Button btnAgregarAlimento;
    @FXML
    private TableView<PaqueteAlimento> tablaAlimento;
    @FXML
    private TableColumn clmAlimento;
    @FXML
    private TableColumn<PaqueteAlimento, Integer> clmCantidadAlimento;
    private Button btnConfPaq;
    @FXML
    private ComboBox<Almacen> cmbSeleccionarAlmacen;
    @FXML
    private Text lblInsumo;
    @FXML
    private Text lblCantidad;
    @FXML
    private Text lblNombre;
    @FXML
    private Text lblFolio;
    @FXML
    private Text lblAlimento;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datos();
    }

    public void llenarcmb() {
        try {
            almacendao = new AlmacenDAO(conexion.conectar2());
            almacenes.addAll(almacendao.getAll());
            cmbSeleccionarAlmacen.setItems(almacenes);
            cmbSeleccionarAlmacen.setOnAction(e -> {
                almacen = cmbSeleccionarAlmacen.getValue();
                datos();
            });
        } catch (SQLException ex) {
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void datos() {
        try {
            // TODO
//            if (vpmedicaplaza.VPMedicaPlaza.userNameSystem.equals("COCINA")) {
//                txfInsumo.setDisable(true);
//                txfCantidad.setDisable(true);
//                btnAgregar.setDisable(true);
//                tabla.setDisable(true);
//                btnConfPaq.setDisable(true);
//            } else if (vpmedicaplaza.VPMedicaPlaza.userNameSystem.equals("ENFERMERIA")) {
//                txfAliemento.setDisable(true);
//                txfCantidadAliemento.setDisable(true);
//                btnAgregarAlimento.setDisable(true);
//                tablaAlimento.setDisable(true);
//                btnConfPaq.setDisable(true);
//            } else 
//            if (vpmedicaplaza.VPMedicaPlaza.userNameSystem.equals("MEZCLAS")) {
            txfAliemento.setDisable(false);
            txfCantidadAliemento.setDisable(false);
            btnAgregarAlimento.setDisable(false);
            tablaAlimento.setDisable(false);
//            }
            buscador();
            cantidadRestriccion();
            tabla.setEditable(true);
            lambda();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recibirDatos(Folio folio) throws SQLException {
        txtNombrePaciente.setText(folio.getNombre());
        txtFolioPaciente.setText(folio.getFolio());
        idFolio = folio.getId();
        idPaciente = folio.getIdPaciente();
        Connection connection = null;
        try {
           
            connection = conexion.conectar2();
            FoliosDAO foliosDAO = new FoliosDAO(connection);
            idPaquete = foliosDAO.paqueteExistente(idPaciente);
            if (idPaquete > 1) {
                // btnConfPaq.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void buscador() throws SQLException {
        Connection connection = null;
        try {
            connection = conexion.conectar2();
            InventariosDAO inventariosDAO = new InventariosDAO(connection);
            List<Inventario> inventarios;
//            if (almacen.getIdAlmacen() == 1) {
            inventarios = inventariosDAO.otenerDatosBusqueda(1);
//            } else {
//                inventarios = inventariosDAO.otenerDatosBusqueda(almacen.getIdAlmacen());
//            }
            List<PaqueteAlimento> paquetesAlimenticios = llenarTXFAlimentos();
            AutoCompletionBinding<Inventario> nombres = TextFields.bindAutoCompletion(txfInsumo, inventarios);
            nombres.setPrefWidth(1000);
            nombres.setOnAutoCompleted(event -> {
                Inventario insumoSeleccionado = event.getCompletion();
                idInsumo = insumoSeleccionado.getId_insumo();

            });
            //Agregar buscador de paquetes alimenticios
            AutoCompletionBinding<PaqueteAlimento> alimentos = TextFields.bindAutoCompletion(txfAliemento, paquetesAlimenticios);
            alimentos.setPrefWidth(1000);
            alimentos.setOnAutoCompleted(event -> {
                paqueteAlimentoSeleccionado = event.getCompletion();
                idPaqueteAlimento = paqueteAlimentoSeleccionado.getId();
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close(); // Cierra la conexión
            }
        }

    }

    private void cantidadRestriccion() {
        txfCantidad.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9.]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
    }

    @FXML
    private void actionAgregarInsumo(ActionEvent event) throws SQLException {
        Connection connection = null;
        try {
            if (txfCantidad.getText().isEmpty() || txfInsumo.getText().isEmpty() || Double.valueOf(txfCantidad.getText()) <= 0.0) {
                alertaError.setTitle("ERROR!");
                alertaError.setHeaderText("CAMPOS VACIOS");
                alertaError.setContentText("POR FAVOR VERIFIQUE QUE LOS CAMPOS\n(1) INSUMO\n(2) CANTIDAD\nESTEN LLENOS\n(3) LA CANTIDAD INGRESADA SEA MAYOR QUE 0");
                alertaError.showAndWait();
            } else {
                connection = conexion.conectar2();
                ConsumoQuirofanoDAO consumoQuirofanoDAO = new ConsumoQuirofanoDAO(connection);
                insumodao = new InsumosDAO(connection);
                kitmedicoyconsumiblecosinadao = new KitMedicoyConsumiblesCocinaDAO(connection);
                double existencia;
                Insumo insumo = insumodao.obtenerInsumosPorId(idInsumo);
                if (insumo.isKit_consumible()) {
                    boolean falta = false;
                    
                    List<KitMedicoyConsumiblesCocina> kitmedicoscisnas = new ArrayList<>();

                    kitmedicoscisnas.addAll(kitmedicoyconsumiblecosinadao.obtenerTodosDelKitSoloDatosImportantes(idInsumo));

                    List<Folio> foliokit = new ArrayList<>();
                    for (int i = 0; i < kitmedicoscisnas.size(); i++) {
                        int cantidad = Integer.valueOf(txfCantidad.getText()) * (int) kitmedicoscisnas.get(i).getCantidad();
                        if (consumoQuirofanoDAO.existenciaTotalInventario(kitmedicoscisnas.get(i).getIdIsnumo()) >= cantidad) {
                            Folio folio = consumoQuirofanoDAO.agegarInsumo(kitmedicoscisnas.get(i).getIdIsnumo(), cantidad, idPaquete);
                            foliokit.add(folio);
                        } else {
                            falta = true;
                        }
                    }

                    if (falta) {
                        alertaError.setTitle("ERROR!");
                        alertaError.setHeaderText("PRODUCTO INSUFICIENTE");
                        alertaError.setContentText("POCA EXISTENCIA EN EL INVENTARIO");
                        alertaError.showAndWait();
                    } else {
                        folioKits.addAll(foliokit);

                        clmProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
                        clmCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
                        editarTabla();
                       
                        /*agregar consumo a quirofano desde consumoQuirofanoDAO.agegarInsumo*/
                        Folio folio = consumoQuirofanoDAO.agegarInsumo(idInsumo, Integer.valueOf(txfCantidad.getText()), idPaquete);
                        folios.add(folio);
                        tabla.setItems(folios);
                        txfCantidad.clear();
                        txfInsumo.clear();
                    }

                } else {
                    existencia = consumoQuirofanoDAO.existenciaTotalInventario(idInsumo);
                    if (existencia >= Double.valueOf(txfCantidad.getText())) {
                        clmProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
                        clmCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
                        editarTabla();
                    
                        /*agregar consumo a quirofano desde consumoQuirofanoDAO.agegarInsumo*/
                        Folio folio = consumoQuirofanoDAO.agegarInsumo(idInsumo, Double.valueOf(txfCantidad.getText()), idPaquete);
                        folios.add(folio);
                        tabla.setItems(folios);
                        txfCantidad.clear();
                        txfInsumo.clear();
                    } else {
                        alertaError.setTitle("ERROR!");
                        alertaError.setHeaderText("PRODUCTO INSUFICIENTE");
                        alertaError.setContentText("EL PRODUCTO SOLO CUENTA CON\n" + "\"" + existencia + "\"" + "\nNUMERO DE PIEZAS");
                        alertaError.showAndWait();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @FXML
    private void actionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionCapturarDatos(ActionEvent event) throws SQLException {
        Connection connection = null;

        try {
            connection = conexion.conectar2();

            Consumo consumo;

            ConsumoQuirofanoDAO consumoQuirofanoDAO;
            for (int i = 0; i < folios.size(); i++) {
                int id = 0;
                double costo = folios.get(i).getPrecioUnitario();

                consumo = new Consumo(folios.get(i).getNombre(),// tipo
                        folios.get(i).getCantidad(),// cantidad
                        costo * folios.get(i).getCantidad(),//costo unitario multiplicado por la cantidad (monto total)
                        txtFolioPaciente.getText(),// folio
                        idPaciente,//id paciente
                        idFolio,// id folio
                        folios.get(i).getId());// id producto
                consumo.setPrecioUnitario(costo);
                consumo.setId_tipo_consumo(1);
                consumoQuirofanoDAO = new ConsumoQuirofanoDAO(connection);
                consumoQuirofanoDAO.incertarConsumo(consumo);

                insumodao = new InsumosDAO(connection);
                if (!insumodao.optenerCantidadyKit_consumibleporId(folios.get(i).getId()).isKit_consumible()) {
                    movimientoInventario(costo, txtFolioPaciente.getText(), folios.get(i).getId(), folios.get(i).getCantidad(), folios.get(i).getTipo());
                }
            }

            if (folioKits != null) {
                for (int i = 0; i < folioKits.size(); i++) {
                    double costo = folioKits.get(i).getPrecioUnitario();
                    movimientoInventario(costo, txtFolioPaciente.getText(), folioKits.get(i).getId(), folioKits.get(i).getCantidad(), folioKits.get(i).getTipo());
                }
            }

            agregarConsumoAlimento();
            calculosAgregarCosumosAlimentos();
            tabla.getItems().clear();
            txfCantidad.clear();
            txfInsumo.clear();
            alertaSucces.setTitle("EXITO!");
            alertaSucces.setHeaderText("PROCEDIMIENTO REALIZADO CORRECTAMENTE");
            alertaSucces.setContentText("SE A INGRESADO LOS INSUMOS A LA CUENTA DEL PACIENTE");
            alertaSucces.showAndWait();

            Stage stage = (Stage) btnCapturar.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void movimientoInventario(Double costo, String folioPaciente, int id_insumo, double cantidad, String tipo) throws SQLException {
        /*NO SE SI LLEGA LA CANTIDAD BIEN HASTA ACÁ double cantidad*/

        Connection connection = conexion.conectar2();
        MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();//objeto
        MovimientoDetalle movimientoDetalle = new MovimientoDetalle();// objeto
        MovimientoPadreDAO movimientopadredao = new MovimientoPadreDAO(connection);
        MovimientoDetalleDAO movimientodetalledao = new MovimientoDetalleDAO(connection);
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

        inventariodao = new InventariosDAO(connection);

        double existenciaTotal = inventariodao.obtenerPorId(id_insumo).getTotalExistencia();

        movimientoInventarioP.setTotal(costo * cantidad);

        movimientoInventarioP.setEstatus_movimiento(1);
        movimientoInventarioP.setObservaciones("COSUMO DE PACIENTE");
        movimientoInventarioP.setUsuario_registro(userSystem);

        id = movimientopadredao.agregarMovimientoInventarioPINT(movimientoInventarioP);

        movimientoDetalle.setId_insumo(id_insumo);
        movimientoDetalle.setCaducidad(fechaHoy);
        movimientoDetalle.setLote_insumo(folioPaciente);
        movimientoDetalle.setInventario_inicial(existenciaTotal);
        movimientoDetalle.setMovimineto(cantidad);
        movimientoDetalle.setInventario_final(existenciaTotal - cantidad);
        movimientoDetalle.setId_insumo_mov_padre(id);
        if (idPaquete == 0) {
            movimientoDetalle.setCosto(costo * cantidad);
        } else {
            movimientoDetalle.setCosto(0);
        }

        movimientoDetalle.setUsuario_modificacion(userSystem);
        movimientoDetalle.setNombre(tipo);
        movimientodetalledao.create(movimientoDetalle);
    }

    public List<PaqueteAlimento> llenarTXFAlimentos() {
        PaqueteAlimento paqueteAlimento;
        List<PaqueteAlimento> paquetealimento = new ArrayList<>();
        try {
            con = conexion.conectar2();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.id, p.nombre, p.descripcion, p.precio FROM paquetesalimentos p ");
            while (rs.next()) {
                paqueteAlimento = new PaqueteAlimento();
                paqueteAlimento.setId(rs.getInt(1));
                paqueteAlimento.setNombre(rs.getString(2));
                paqueteAlimento.setDescripcion(rs.getString(3));
                paqueteAlimento.setPrecio(rs.getDouble(4));
                paquetealimento.add(paqueteAlimento);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return paquetealimento;
    }

    @FXML
    private void actionAgregarAlimento(ActionEvent event) {
        Connection connection = conexion.conectar2();
        if (txfCantidadAliemento.getText().isEmpty() || txfAliemento.getText().isEmpty() || Integer.valueOf(txfCantidadAliemento.getText()) == 0) {
            alertaError.setTitle("ERROR!");
            alertaError.setHeaderText("CAMPOS VACIOS");
            alertaError.setContentText("POR FAVOR VERIFIQUE QUE LOS CAMPOS\n(1) INSUMO\n(2) CANTIDAD\nESTEN LLENOS\n(3) LA CANTIDAD INGRESADA SEA MAYOR QUE 0");
            alertaError.showAndWait();
        } else {
            clmAlimento.setCellValueFactory(new PropertyValueFactory("nombre"));
            clmCantidadAlimento.setCellValueFactory(new PropertyValueFactory("cantidad"));
            editarTablaAlimento();
            PaqueteAlimento paqueteAlimento = new PaqueteAlimento();
            paqueteAlimento.setNombre(paqueteAlimentoSeleccionado.getNombre());
            paqueteAlimento.setId(paqueteAlimentoSeleccionado.getId());
            paqueteAlimento.setCantidad(Integer.parseInt(txfCantidadAliemento.getText()));
            paqueteAlimento.setPrecio(paqueteAlimentoSeleccionado.getPrecio());
            pkAlimento.add(paqueteAlimento);//observableList
            tablaAlimento.setItems(pkAlimento);
            txfAliemento.clear();
            txfCantidadAliemento.clear();
//            agregarConsumoAlimento(paqueteAlimentoSeleccionado.getId());
        }
    }

    public void calculosAgregarCosumosAlimentos() {
        try {
            con = conexion.conectar2();
            consumosdao = new ConsumosDAO(con);

            int cantidadComidas = consumosdao.optenerCantidadComidas(idPaquete);
            double comidasAlMomento = consumosdao.CantidadConsumoAlMomento(idFolio);

            if (comidasAlMomento > cantidadComidas) {
                idTipoConsumo = 5;
            } else {
                idTipoConsumo = 6;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarConsumoAlimento() {
        con = conexion.conectar2();
        String sql = "{call ingresarPaqueteAlimentoAConsumo(?,?,?,?,?,?,?,?,?)}";
        
        try {
            if (pkAlimento.isEmpty()) {
               
            } else {
             
                for (PaqueteAlimento pa : tablaAlimento.getItems()) {
                    CallableStatement stmt = con.prepareCall(sql);
                    stmt.setString(1, pa.getNombre());//idTipo
                    stmt.setInt(2, pa.getCantidad());
                    stmt.setDouble(3, pa.getCantidad() * pa.getPrecio());
                    stmt.setString(4, txtFolioPaciente.getText());
                    stmt.setInt(5, idPaciente);
                    stmt.setInt(6, pa.getId());
                    stmt.setInt(7, idFolio);
                    stmt.setDouble(8, idPaquete);
                    stmt.setInt(9, pa.getId());
                   
                    stmt.execute();
                }

                incertarComanda();
            }

        } catch (SQLException ex) {
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    private void incertarComanda() throws SQLException {
        Connection connection = null;
        int idComanda = 0;
        int numProductos = 0;
        double total = 0;
        double totalprodutoi = 0;
        for (int i = 0; i < tablaAlimento.getItems().size(); i++) {
            totalprodutoi = pkAlimento.get(i).getPrecio() * pkAlimento.get(i).getCantidad();
            numProductos = numProductos + tablaAlimento.getItems().get(i).getCantidad();
            total += totalprodutoi;
        }
        double iva = total * 0.16;
        double subtotal = total - iva;
       
        String sql = "INSERT INTO comandas(id_comanda,folio ,fecha ,id_cliente ,cliente ,cantidad_productos ,subtotal ,iva ,total ,recibe ,observacion ,id_estatus ,id_usuario_modificacion ,fecha_modificacion)VALUES(  0 ,? ,NOW(),? ,? ,? ,?  ,? ,? ,? ,?,? ,? ,NOW());";

        String sqlCoamndaDetalle = "INSERT INTO comanda_detalle\n"
                + "(id_comanda ,id_producto ,cantidad ,costo_unitario ,subtotal ,iva ,total ,id_usuario_modificacion ,fecha_modificacion)VALUES (?  ,?  ,?  ,?  ,?  ,?  ,?  ,?  ,NOW());";

        try {
            connection = conexion.conectar2();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, txtFolioPaciente.getText());
                statement.setInt(2, 0);
                statement.setString(3, txtNombrePaciente.getText());
                statement.setInt(4, numProductos);
                statement.setDouble(5, (total - (total * 0.16)));//subtotal
                statement.setDouble(6, (total * 0.16));//iva
                statement.setDouble(7, total);//total
                statement.setString(8, txtNombrePaciente.getText());
                statement.setString(9, "");
                statement.setInt(10, 0);
                statement.setInt(11, VPMedicaPlaza.userSystem);
                statement.executeUpdate();
            }

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT c.id_comanda FROM comandas c WHERE c.folio ='" + txtFolioPaciente.getText() + "'");

            while (rs.next()) {
                idComanda = rs.getInt(1);
            }

            try (PreparedStatement statement = connection.prepareCall(sqlCoamndaDetalle)) {
                for (int i = 0; i < tablaAlimento.getItems().size(); i++) {

                    double totalinsumo = tablaAlimento.getItems().get(i).getPrecio() * tablaAlimento.getItems().get(i).getCantidad();

                    statement.setInt(1, idComanda);
                    statement.setInt(2, tablaAlimento.getItems().get(i).getId());
                    statement.setInt(3, tablaAlimento.getItems().get(i).getCantidad());
                    statement.setDouble(4, tablaAlimento.getItems().get(i).getPrecio());
                    statement.setDouble(5, totalinsumo);
                    statement.setDouble(6, 0);
                    statement.setDouble(7, totalinsumo);
                    statement.setInt(8, VPMedicaPlaza.userSystem);
                    statement.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            alertaSucces.setTitle("EXITO!");
            alertaSucces.setContentText("PROCEDIMIENTO REALIZADO");
            alertaSucces.setHeaderText(null);
            alertaSucces.showAndWait();
            generarTicket(idComanda);

        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la traza de errores
        } finally {
            if (connection != null) {
                connection.close(); // Cierra la conexión
            }
        }
    }

    public void generarTicket(int a) throws JRException {
        Reporte reporte = new Reporte("Rpt_TicketVentaAlimentos");
        reporte.generarReporteTicket(a);

    }

    private void editarTabla() {
        clmCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        clmCantidad.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            Folio folioeditar = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            folioeditar.setCantidad(event.getNewValue());

            tabla.refresh();
        });

        clmCantidad.setEditable(true);
        tabla.setEditable(true);

    }

    private void editarTablaAlimento() {
        clmCantidadAlimento.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clmCantidadAlimento.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            PaqueteAlimento paquetealimentoeditar = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            paquetealimentoeditar.setCantidad(event.getNewValue());

            tablaAlimento.refresh();
        });

        clmCantidadAlimento.setEditable(true);
        tablaAlimento.setEditable(true);
    }

    private void irConfiguracionPaquete(ActionEvent event) throws IOException {
        // Cargar la vista de destino
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas_hospital/ConfiguracionPaqueteMedico.fxml"));
        Parent root = loader.load();
        ConfiguracionPaqueteMedicoController destinoController = loader.getController();

        // Obtener el objeto de la vista de origen
//            int idFolio;
//    int idPaquete;
        // Pasar el objeto a la vista de destino
   
        destinoController.setObjeto(this.idFolio, this.idPaquete);

        // Crear un nuevo Stage para la vista de destino
        Stage destinoStage = new Stage();
        destinoStage.setTitle("EDITAR PACIENTE");
        destinoStage.setScene(new Scene(root));
        destinoStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el nuevo Stage de forma modal
        destinoStage.showAndWait();

    }

    public void lambda() {
        clmCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        clmCantidad.setOnEditCommit(event -> {
            /*aqui se va a ejecutar la actualizacion a consumos*/
            // obtener el objeto Consumo que está siendo editado
            Folio folioSeleccionado = event.getTableView().getItems().get(event.getTablePosition().getRow());
          
            double cantidadInicial = folioSeleccionado.getCantidad();

            if (event.getNewValue().equals(0) || event.getNewValue() < 0) {
                alertaError.setTitle("ATENCION");
                alertaError.setHeaderText("NO PUEDES SELECCIONAR EL VALOR 0");
                alertaError.setContentText("DEBE DESCARTAR CONSUMO CON CLICK DERECHO -> DESCARTAR CONSUMO");
                alertaError.showAndWait();
            } else if (event.getNewValue() <= cantidadInicial) {
                folioSeleccionado.setCantidad(event.getNewValue());
   
                //actualizarInventario(consumoSeleccionado, (cantidadInicial - consumoSeleccionado.getCantidad()));
            } else {
                alertaError.setTitle("ATENCION");
                alertaError.setHeaderText("CANTIDAD MAYOR");
                alertaError.setContentText("LA CANTIDAD NO PUEDE SER MAYOR");
                alertaError.showAndWait();
            }

            tabla.refresh();
        });
        clmCantidad.setEditable(true);

        tabla.setRowFactory(tableView -> {
            TableRow<Folio> row = new TableRow<>();
            ContextMenu cxmConfiguracion = new ContextMenu();
            MenuItem descartarConsumo = new MenuItem("Descartar Consumo");
            descartarConsumo.setOnAction(event -> {
                //con = conexion.conectar2();
                Folio folio = row.getItem();
             
                folios.remove(folio);

            });
            cxmConfiguracion.getItems().add(descartarConsumo);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(cxmConfiguracion)
            );
            return row;
        });
    }

}
