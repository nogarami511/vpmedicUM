/*
 * NO FUNCIONA AUN PARA COSINA NO AGREGAR A COSINA AUN.
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Almacen;
import clases_hospital.Consumo;
import clases_hospital.Folio;
import clases_hospital.Insumo;
import clases_hospital.Inventario;
import clases_hospital.InventarioAlmacen;
import clases_hospital.KitMedicoyConsumiblesCocina;
import clases_hospital.MovimientoDetalle;
import clases_hospital.MovimientoInventarioP;
import clases_hospital.PaqueteAlimento;
import clases_hospital_DAO.AlmacenDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.InventarioAlmacenDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.KitMedicoyConsumiblesCocinaDAO;
import clases_hospital_DAO.MovimientoDetalleDAO;
import clases_hospital_DAO.MovimientoPadreDAO;
import controladores_hospital.ConfiguracionPaqueteMedicoController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AgregarConsumoPacienteController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaSucces = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaWarring = new Alert(Alert.AlertType.WARNING);

    ObservableList<Almacen> almacenes = FXCollections.observableArrayList();
    ObservableList<Consumo> conusmoInusmoAlimenticio = FXCollections.observableArrayList();
    ObservableList<Consumo> conusmoInusmoMedico = FXCollections.observableArrayList();
    List<Consumo> consumosKits = FXCollections.observableArrayList();

    AlmacenDAO almacendao;
    InventariosDAO inventariosDAO;
    InventarioAlmacenDAO inventarioalmacendao;
    InsumosDAO insumodao;
    KitMedicoyConsumiblesCocinaDAO kitmedicoyconsumiblecosinadao;
    ConsumosDAO consumodao;
    FoliosDAO foliosDAO;
    MovimientoPadreDAO movimientopadredao;
    MovimientoDetalleDAO movimientodetalledao;

    Almacen almacen;

    int idInsumo;
    int idPaqueteAlimento;
    int idPaciente;
    int idFolio;
    int idPaquete;

    @FXML
    private Label lblPaciente;
    @FXML
    private Label lblFolio;
    @FXML
    private Button btnCapturar;
    @FXML
    private Button btnConfPaquete;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Consumo> tablaInsumos;
    @FXML
    private TableColumn<Consumo, String> colInsumos;
    @FXML
    private TableColumn<Consumo, Integer> colCantidadInsumos;
    @FXML
    private ComboBox<Almacen> cmbAlmacen;
    @FXML
    private TextField txfInsumo;
    @FXML
    private TextField txfInsumosCantidad;
    @FXML
    private Button btnAgregarInsumos;
    @FXML
    private TableView<Consumo> tablaAlimentos;
    @FXML
    private TableColumn<Consumo, String> colAlimentos;
    @FXML
    private TableColumn<Consumo, Double> colCanitdadAlimentos;
    @FXML
    private RadioButton rdbTablaMezclas;
    @FXML
    private ToggleGroup visualizarTablas;
    @FXML
    private RadioButton rdbTablaCosina;
    @FXML
    private Label lblAlmacen;
    @FXML
    private Label lblInsumo;
    @FXML
    private Label lblCantidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        switch (VPMedicaPlaza.userNameSystem) {
            case ("SISTEMAS"):
                rdbTablaCosina.setVisible(true);
                rdbTablaMezclas.setVisible(true);
                break;
            case ("MEZCLAS"):
                lblAlmacen.setVisible(true);
                cmbAlmacen.setVisible(true);
                llenarCmbAlmacen();
                tablaInsumos.setVisible(true);
                break;
            case ("COCINA"):
                llenarTextfildAimentos();
                tablaAlimentos.setVisible(true);
                break;
        }

    }

    @FXML
    private void accionCapturar(ActionEvent event) {
        if (VPMedicaPlaza.userNameSystem.equals("SISTEMAS")) {
            if (rdbTablaMezclas.isSelected()) {
                capturarDatosMezclas();
            } else {
                capturarDatosCocina();
            }
        } else {
            if (VPMedicaPlaza.userNameSystem.equals("MEZCLAS")) {
                capturarDatosMezclas();
            } else {
                capturarDatosCocina();
            }
        }
    }

    @FXML
    private void acciionConfPaquete(ActionEvent event) throws IOException {
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

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionAgregarInsumos(ActionEvent event) {
      
        if (txfInsumosCantidad.getText().isEmpty() || txfInsumo.getText().isEmpty() || Integer.valueOf(txfInsumosCantidad.getText()) == 0) {
            alertaError.setTitle("ERROR!");
            alertaError.setHeaderText("CAMPOS VACIOS");
            alertaError.setContentText("POR FAVOR VERIFIQUE QUE LOS CAMPOS\n(1) INSUMO\n(2) CANTIDAD\nESTEN LLENOS\n(3) LA CANTIDAD INGRESADA SEA MAYOR QUE 0");
            alertaError.showAndWait();
        } else {
            if (almacen.getIdAlmacen() == 1) {
                evaluarKitMedico();
            } else {
                
                llanarTablaInsumosMedicos();
            }
        }
    }

    @FXML
    private void accionTablaMezclas(ActionEvent event) {
        tablaInsumos.setVisible(true);
        tablaAlimentos.setVisible(false);
        cmbAlmacen.setVisible(true);
        llenarCmbAlmacen();
        lblAlmacen.setVisible(true);
        cmbAlmacen.setVisible(true);
    }

    @FXML
    private void accionTablaCoina(ActionEvent event) {
        tablaInsumos.setVisible(false);
        tablaAlimentos.setVisible(true);
        cmbAlmacen.setVisible(false);
        llenarTextfildAimentos();
        lblAlmacen.setVisible(false);
        cmbAlmacen.setVisible(false);
    }

    private void llenarCmbAlmacen() {
        almacenes.clear();
        cmbAlmacen.getItems().clear();
        try {
            almacendao = new AlmacenDAO(conexion.conectar2());
            almacenes.addAll(almacendao.getAll());
            cmbAlmacen.setItems(almacenes);
            cmbAlmacen.setOnAction(e -> {
                almacen = cmbAlmacen.getValue();
                obtenerDatos();
            });
        } catch (SQLException ex) {
            //Colocar una alarta aqui
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerDatos() {
        if (almacen.getIdAlmacen() == 1) {
            llenarTextFieldAlmacenGenerarl();
        } else {
            llenarTextFildAlmacenes();
        }
    }

    private void llenarTextFieldAlmacenGenerarl() {
        try {
            con = conexion.conectar2();
            inventariosDAO = new InventariosDAO(con);
            List<Inventario> inventarios = inventariosDAO.otenerDatosBusqueda(1);
            AutoCompletionBinding<Inventario> nombres = TextFields.bindAutoCompletion(txfInsumo, inventarios);
            nombres.setPrefWidth(1000);
            nombres.setOnAutoCompleted(event -> {
                Inventario insumoSeleccionado = event.getCompletion();
                idInsumo = insumoSeleccionado.getId_insumo();

            });
        } catch (SQLException ex) {
            //Colocar una alarta aqui
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTextFildAlmacenes() {
        try {
            con = conexion.conectar2();
            inventarioalmacendao = new InventarioAlmacenDAO(con);
            List<InventarioAlmacen> inventarios = inventarioalmacendao.getDataByIdAlmacen(almacen.getIdAlmacen());
            AutoCompletionBinding<InventarioAlmacen> nombres = TextFields.bindAutoCompletion(txfInsumo, inventarios);
            nombres.setPrefWidth(1000);
            nombres.setOnAutoCompleted(event -> {
                InventarioAlmacen insumoSeleccionado = event.getCompletion();
                idInsumo = insumoSeleccionado.getIdInsumo();

            });
        } catch (SQLException ex) {
            //Colocar una alarta aqui
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTextfildAimentos() {
        List<PaqueteAlimento> paquetesAlimenticios = llenarTXFAlimentos();
        AutoCompletionBinding<PaqueteAlimento> alimentos = TextFields.bindAutoCompletion(txfInsumo, paquetesAlimenticios);
        alimentos.setPrefWidth(1000);
        alimentos.setOnAutoCompleted(event -> {
            PaqueteAlimento paqueteAlimentoSeleccionado = event.getCompletion();
            idPaqueteAlimento = paqueteAlimentoSeleccionado.getId();
        });
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
        }
        return paquetealimento;
    }

    private void evaluarKitMedico() {
        
        try {
            con = conexion.conectar2();
            insumodao = new InsumosDAO(con);
            kitmedicoyconsumiblecosinadao = new KitMedicoyConsumiblesCocinaDAO(con);
            int existencia;
            Insumo insumo = insumodao.obtenerInsumosPorId(idInsumo);
           
         
            if (insumo.isKit_consumible()) {
               
                boolean falta = false;
              
                List<KitMedicoyConsumiblesCocina> kitmedicoscisnas = new ArrayList<>();

                kitmedicoscisnas.addAll(kitmedicoyconsumiblecosinadao.obtenerTodosDelKitSoloDatosImportantes(idInsumo));

                List<Consumo> consumoKit = new ArrayList<>();
                for (int i = 0; i < kitmedicoscisnas.size(); i++) {
                    int cantidad = Integer.valueOf(txfInsumosCantidad.getText()) * (int) kitmedicoscisnas.get(i).getCantidad();
                    if (inventariosDAO.existenciaTotalInventario(kitmedicoscisnas.get(i).getIdIsnumo()) >= cantidad) {
                        Consumo consumoMedicoKit = new Consumo();
                        consumoMedicoKit.setTipo(kitmedicoscisnas.get(i).getNombreInsumo());
                        consumoMedicoKit.setCantidad(cantidad);
                        consumoMedicoKit.setMonto((cantidad * kitmedicoscisnas.get(i).getPrecioUnitario()));
                        consumoMedicoKit.setFolio(lblFolio.getText());
                        consumoMedicoKit.setId_pasiente(idPaciente);
                        consumoMedicoKit.setId_PaqueteAlimento(0);
                        consumoMedicoKit.setId_tipo_consumo(0);
                        consumoMedicoKit.setId_folio(idFolio);
                        consumoMedicoKit.setId_producto_venta(kitmedicoscisnas.get(i).getIdIsnumo());
                        consumoMedicoKit.setId_estatus_consumo(0);
                        consumoMedicoKit.setPrecioUnitario(kitmedicoscisnas.get(i).getPrecioUnitario());
                        consumoKit.add(consumoMedicoKit);
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
                 
                    consumosKits.addAll(consumoKit);
                    llanarTablaInsumosMedicos();
                }

            } else {
                if (txfInsumosCantidad.getText().isEmpty() || txfInsumo.getText().isEmpty() || Integer.valueOf(txfInsumosCantidad.getText()) == 0) {
                    alertaError.setTitle("ERROR!");
                    alertaError.setHeaderText("CAMPOS VACIOS");
                    alertaError.setContentText("POR FAVOR VERIFIQUE QUE LOS CAMPOS\n(1) INSUMO\n(2) CANTIDAD\nESTEN LLENOS\n(3) LA CANTIDAD INGRESADA SEA MAYOR QUE 0");
                    alertaError.showAndWait();
                } else {
                    llanarTablaInsumosMedicos();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llanarTablaInsumosMedicos() {
        
        try {
           
            con = conexion.conectar2();
            insumodao = new InsumosDAO(con);
            Insumo insumo = insumodao.optenerDatosInsumos(idInsumo);
            colInsumos.setCellValueFactory(new PropertyValueFactory("tipo"));
            colCantidadInsumos.setCellValueFactory(new PropertyValueFactory("cantidad"));

            //Agregar los datos que hacen falta aqui para tenerlos listos mañana.
            editarTabla();
            /*agregar consumo a quirofano desde consumoQuirofanoDAO.agegarInsumo*/
            Consumo consumoMedico = new Consumo();
            consumoMedico.setTipo(txfInsumo.getText());
            consumoMedico.setCantidad(Integer.parseInt(txfInsumosCantidad.getText()));
            consumoMedico.setMonto((Double.parseDouble(txfInsumosCantidad.getText()) * insumo.getPrecio_venta_unitaria()));
            consumoMedico.setFolio(lblFolio.getText());
            consumoMedico.setId_pasiente(idPaciente);
            consumoMedico.setId_PaqueteAlimento(0);
            consumoMedico.setId_tipo_consumo(1);
            consumoMedico.setId_folio(idFolio);
            consumoMedico.setId_producto_venta(idInsumo);
            consumoMedico.setId_estatus_consumo(1);
            consumoMedico.setPrecioUnitario(insumo.getPrecio_venta_unitaria());
            conusmoInusmoMedico.add(consumoMedico);
            tablaInsumos.setItems(conusmoInusmoMedico);
            txfInsumosCantidad.clear();
            txfInsumo.clear();
        } catch (SQLException ex) {
            Logger.getLogger(AgregarConsumoPacienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarTablaAlimentos() {
        con = conexion.conectar2();
    }

    public void recibirDatos(Folio folio) throws SQLException {
        lblPaciente.setText(folio.getNombre());
        lblFolio.setText(folio.getFolio());
        idFolio = folio.getId();
        idPaciente = folio.getIdPaciente();
        Connection connection = null;
        try {
            connection = conexion.conectar2();
            foliosDAO = new FoliosDAO(connection);
            idPaquete = foliosDAO.paqueteExistente(idPaciente);
            if (idPaquete > 1) {
                btnConfPaquete.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private void editarTabla() {
        colCantidadInsumos.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colCantidadInsumos.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            Consumo consumoEditar = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            consumoEditar.setCantidad(event.getNewValue());

            tablaInsumos.refresh();
        });

        colCantidadInsumos.setEditable(true);
        tablaInsumos.setEditable(true);

    }

    private void capturarDatosMezclas() {
        con = conexion.conectar2();

        try {
            Consumo consumo;

            for (int i = 0; i < conusmoInusmoMedico.size(); i++) {
                int id = 0;
                double costo = conusmoInusmoMedico.get(i).getPrecioUnitario();

                consumo = conusmoInusmoMedico.get(i);
                consumo.setPrecioUnitario(costo);
                consumo.setId_tipo_consumo(1);
                consumodao = new ConsumosDAO(con);
                consumodao.insertarConsumoConEstatus(consumo);

                insumodao = new InsumosDAO(con);
                if (!insumodao.optenerCantidadyKit_consumibleporId(consumo.getId_producto_venta()).isKit_consumible()) {
                    movimientoInventario(costo, lblFolio.getText(), consumo.getId_producto_venta(), consumo.getCantidad(), consumo.getTipo());
                }
            }

            if (consumosKits != null) {
                for (int i = 0; i < consumosKits.size(); i++) {
                    double costo = consumosKits.get(i).getPrecioUnitario();
                    movimientoInventario(costo, lblFolio.getText(), consumosKits.get(i).getId_producto_venta(), consumosKits.get(i).getCantidad(), consumosKits.get(i).getTipo());
                }
            }

//            agregarConsumoAlimento();
            tablaInsumos.getItems().clear();
            txfInsumosCantidad.clear();
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

    private void capturarDatosCocina() {
        con = conexion.conectar2();
        consumodao = new ConsumosDAO(con);
        if (conusmoInusmoAlimenticio.isEmpty()) {
          
        } else {
            for (Consumo consumo : tablaAlimentos.getItems()) {
                consumodao.insertarConsumoConEstatus(consumo);
            }
        }
    }

    private void movimientoInventario(Double costo, String folioPaciente, int id_insumo, double cantidad, String tipo) throws SQLException {
        /*NO SE SI LLEGA LA CANTIDAD BIEN HASTA ACÁ double cantidad*/

        Connection connection = conexion.conectar2();
        MovimientoInventarioP movimientoInventarioP = new MovimientoInventarioP();//objeto
        MovimientoDetalle movimientoDetalle = new MovimientoDetalle();// objeto
        movimientopadredao = new MovimientoPadreDAO(connection);
        movimientodetalledao = new MovimientoDetalleDAO(connection);
        LocalDate fechaActual = LocalDate.now();
        Date fechaHoy = Date.valueOf(fechaActual);
        int id = 0;

        movimientoInventarioP.setTipo_mov(8);
        movimientoInventarioP.setId_origen(1);
        movimientoInventarioP.setId_destino(1);
        movimientoInventarioP.setId_proveedor(0);
        movimientoInventarioP.setFolio_mov(folioPaciente);
        movimientoInventarioP.setSubtotal(0);
        movimientoInventarioP.setDescuento(0);
        movimientoInventarioP.setImporte_impuesto(0);

        inventariosDAO = new InventariosDAO(connection);

        double existenciaTotal = inventariosDAO.obtenerPorId(id_insumo).getTotalExistencia();

        movimientoInventarioP.setTotal(costo * cantidad);

        movimientoInventarioP.setEstatus_movimiento(1);
        movimientoInventarioP.setObservaciones("COSUMO DE PACIENTE");
        movimientoInventarioP.setUsuario_registro(VPMedicaPlaza.userSystem);

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

        movimientoDetalle.setUsuario_modificacion(VPMedicaPlaza.userSystem);
        movimientoDetalle.setNombre(tipo);
        movimientodetalledao.create(movimientoDetalle);
    }

}
