/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clase.Paciente;
import clase.Usuario;
import clases_hospital.AsignacionHabitacion;
import clases_hospital.ConsumoHabitacion;
import clases_hospital.CostoHabitacion;
import clases_hospital.CostoHabitacionDAO;
import clases_hospital.Folio;
import clases_hospital.Habitacion;
import clases_hospital.NumerosALetras;
import clases_hospital.ReporteCierre;
import clases_hospital_DAO.AsignacionHabitacionDAO;
import clases_hospital_DAO.ConsumoHabitacionDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.HabitacionDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.PagosDAO;
import clases_hospital_DAO.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.ReporteC;
import vistasAuxiliares.PacienteNuevoController;
import vistasAuxiliares_hospital.ModuloPagosFXMLController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class PacientesController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
    PacientesDAO pascientedao;
    FoliosDAO foliodao;
    //UsuarioDAO usuariodao;
    int idPacienteBuscador;

    @FXML
    private Button btnIngresar;
    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn nombrePaciente;
    @FXML
    private TableColumn telefonoPaciente;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private TableColumn colMedico;
    @FXML
    private TableColumn colEntrada;
    @FXML
    private TableColumn colAlta;
    @FXML
    private TableColumn colReingreso;
    @FXML
    private TextField txfBuscarPaciente;
    @FXML
    private Button btnBuscarPaciente;
    @FXML
    private TableColumn colAcciones;
    private final TableColumn colAgregarInformacion = new TableColumn<>("COMPLEMENTAR");
    private final TableColumn colImprimirReportes = new TableColumn<>("IMPRIMIR");
    @FXML
    private Pagination pagPaciente;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //usuariodao = new UsuarioDAO(conexion.conectar());
        try {
            // TODO
            //Usuario usuario = usuariodao.leer(VPMedicaPlaza.userSystem);
            switch (VPMedicaPlaza.userNameSystem) {
                case "SISTEMAS":
                    generarYColocarColumnaComplemento();
                    break;
                case "ENFERMERIA":
                    generarYColocarColumnaComplemento();
                    break;
                case "MEDICOS":
                    generarYColocarColumnaComplemento();
                    break;
                case "MÉDICOS":
                    generarYColocarColumnaComplemento();
                    break;
            }

            llenarTabla();
            llenarBuscador();
        } catch (SQLException ex) {
            Logger.getLogger(PacientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/PacienteNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("PACIENTE NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
    }

    @FXML
    private void editar(ActionEvent event) throws IOException, SQLException {
        Paciente paciente = this.tabla.getSelectionModel().getSelectedItem();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/PacienteNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);

        PacienteNuevoController pnc = fxml.getController();

     

        pnc.obtenerDatos(paciente);
        Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.setTitle("PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();

    }

    @FXML
    private void accionBuscarPaciente(ActionEvent event) throws SQLException {
        pascientedao = new PacientesDAO(conexion.conectar2());
        if (txfBuscarPaciente.getText().equals("")) {

        } else {
            tabla.getItems().clear();
            pacientes.clear();

            pacientes.add(pascientedao.obtenerPacientePorId(idPacienteBuscador));
            nombrePaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
            telefonoPaciente.setCellValueFactory(new PropertyValueFactory("telefono"));
            colMedico.setCellValueFactory(new PropertyValueFactory("nombreMedico"));

            generarBotones();
            centrarTabla();

            tabla.setItems(pacientes);
        }
    }

    private void llenarTabla() throws SQLException {
        this.tabla.getItems().clear();
        pascientedao = new PacientesDAO(conexion.conectar2());

        pacientes.addAll(pascientedao.obtenerTodosPacientesConMeidicoTipoSagreYDatosFolioHospitalizados());

        nombrePaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
        telefonoPaciente.setCellValueFactory(new PropertyValueFactory("telefono"));
        colMedico.setCellValueFactory(new PropertyValueFactory("nombreMedico"));

        generarBotones();
        centrarTabla();

        try {
            int paginas = (int) Math.ceil((double) pascientedao.contarFilas() / filaPorPagina());
            pagPaciente.setPageCount(paginas);
            pagPaciente.setCurrentPageIndex(0); // Establecemos la página actual en 0

            pagPaciente.setPageFactory(this::crearPagina); // Llamamos al método crearPagina para cargar los datos

//            llenarBuscador(); // Llena el buscador para autocompletar
        } catch (SQLException ex) {
            Logger.getLogger(PacientesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int filaPorPagina() {
        return 10;
    }

    private Node crearPagina(int pageIndex) {
        int fromIndex = pageIndex * filaPorPagina();

        try {
            List<Paciente> insumosPaginados = pascientedao.getDatosByPage(pageIndex, filaPorPagina());
            tabla.getItems().setAll(insumosPaginados);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BorderPane(tabla);
    }

    private void centrarTabla() {
        nombrePaciente.setStyle("-fx-alignment: CENTER;");
        telefonoPaciente.setStyle("-fx-alignment: CENTER;");
        colMedico.setStyle("-fx-alignment: CENTER;");
        colEntrada.setStyle("-fx-alignment: CENTER;");
        colAlta.setStyle("-fx-alignment: CENTER;");
        colReingreso.setStyle("-fx-alignment: CENTER;");
    }

    private void generarBotones() {
        foliodao = new FoliosDAO(conexion.conectar2());

        generarColumnaComplemento(foliodao);

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> entrada = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnEntrada = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView entrada = new ImageView("/img/icons/icons8-entrar-50.png");
                        entrada.setFitHeight(20);
                        entrada.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnEntrada.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de confirmar el ingrso de: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    Folio folio = foliodao.obtenerFolioPorId(paciente.getIdfolio());
                                    folio.setId_estatus_hospitalizacion(1);
                                    alertaConfirmacion.setHeaderText(null);
                                    alertaConfirmacion.setTitle("Confirmación");
                                    alertaConfirmacion.setContentText("Se ha confirmar el ingrso de: " + paciente.getNombre());
                                    alertaConfirmacion.showAndWait();
                                    foliodao.actualizarFolioFechaIngreso(folio);
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                              
                            }
                        });
                        setGraphic(btnEntrada);
                        setText(null);
                        btnEntrada.setGraphic(entrada);
                        switch (paciente.getId_estatus_hospitalizacion()) {
                            case 0:
                                btnEntrada.setDisable(false);
                                break;
                            case 1:
                                btnEntrada.setDisable(true);
                                break;
                            case 2:
                                btnEntrada.setDisable(true);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colEntrada.setCellFactory(entrada);

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> alta = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnSalida = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView altapaciente = new ImageView("/img/icons/icons8-salida-50.png");
                        altapaciente.setFitHeight(20);
                        altapaciente.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnSalida.setOnAction(event -> {

                            try {
                                Folio folio = foliodao.obtenerFolioPorId(paciente.getIdfolio());

                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de confirmar el Alta de: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    Connection connection = null;
                                    connection = conexion.conectar2();
                                    LocalDate fechaLocal = LocalDate.now();
                                    Date fecha_ingreso = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                    java.sql.Date fechaSQL_ingreso = new java.sql.Date(fecha_ingreso.getTime());
                                    folio.setId_estatus_hospitalizacion(2);//2 alta
                                    folio.setId_estatus_folio(0);//0 inactivo, 1 activo

                                    PagosDAO pagosDAO = new PagosDAO(conexion.conectar2());
                                    ReporteCierre rptCierre = pagosDAO.datosReporteCierre(folio.getId());
                                    if (rptCierre == null) {
                                        alertaError.setHeaderText(null);
                                        alertaError.setTitle("ERROR");
                                        alertaError.setContentText("FINIQUITO DE CUENTA NO ENCONTRADO. ¿GENERAR PAGO FININIQUITO?");
                                        Optional<ButtonType> pagar = alertaConfirmacion.showAndWait();
                                        if (pagar.get() == ButtonType.OK) {
                                            llamarModuloPagos(folio);
                                            ReporteCierre rptCier = pagosDAO.datosReporteCierre(folio.getId());
                                            if (rptCier != null) {
                                            
                                                reporte(rptCierre, folio, paciente.getNombre());
                                            } else {
                                                alertaError.setHeaderText(null);
                                                alertaError.setTitle("ERROR");
                                                alertaError.setContentText("FINIQUITO DE CUENTA NO ENCONTRADO.");
                                                alertaConfirmacion.showAndWait();
                                            }
                                        }
                                    } else {
                                        
                                        reporte(rptCierre, folio, paciente.getNombre());

                                    }
                                    foliodao.actualizarFolio(folio);
                                    llenarTabla();
                                    Habitacion habitacion = new Habitacion();
                                    HabitacionDAO habitacionDAO = new HabitacionDAO(connection);
                                    habitacion.setId(habitacionDAO.obtenerIDHabitacion(folio.getNumero_habitacion()));
                                    habitacion.setIdTipo(habitacionDAO.obtenerIdTipoHabitacion(habitacion.getId()));
                                    CostoHabitacion costoHabitacion = new CostoHabitacion();
                                    CostoHabitacionDAO costoHabitacionDAO = new CostoHabitacionDAO(connection);
                                    costoHabitacion.setPrecio(costoHabitacionDAO.obtenerIdTipoHabitacion(habitacion.getId()));
                                    habitacionDAO.liberarHabitacion(habitacion);
                                    AsignacionHabitacionDAO asignacionHabitacionDAO = new AsignacionHabitacionDAO(connection);
                                    AsignacionHabitacion asignacion = new AsignacionHabitacion();
                                    asignacion.setId(habitacion.getId());
                                    asignacionHabitacionDAO.actualizarLiberacionDeHabitacion(asignacion);
                                    ConsumoHabitacion consumoHabitacion = new ConsumoHabitacion();
                                    ConsumoHabitacionDAO consumoHabitacionDAO = new ConsumoHabitacionDAO(connection);
                                    consumoHabitacion.setIdHabitacion(habitacion.getId());
                                    consumoHabitacion.setIdTipoHabitacion(habitacion.getIdTipo());
                                    consumoHabitacion.setIdFolio(folio.getId());
                                    consumoHabitacion.setFechaIngreso(folio.getFecha_ingreso());
                                    consumoHabitacion.setCantidad(1);
                                    consumoHabitacion.setMontoAlMomento(costoHabitacion.getPrecio());
                                    consumoHabitacion.setUsuarioModificacion(VPMedicaPlaza.userSystem);
                                    consumoHabitacionDAO.insert(consumoHabitacion);
                                    connection.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        setGraphic(btnSalida);
                        setText(null);
                        btnSalida.setGraphic(altapaciente);
                        switch (paciente.getId_estatus_hospitalizacion()) {
                            case 0:
                                btnSalida.setDisable(true);
                                break;
                            case 1:
                                btnSalida.setDisable(false);
                                break;
                            case 2:
                                btnSalida.setDisable(true);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colAlta.setCellFactory(alta);

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> reingreso = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnReingreso = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView reingreso = new ImageView("/img/icons/agregar-usuario.png");
                        reingreso.setFitHeight(20);
                        reingreso.setFitWidth(20);

                        setGraphic(btnReingreso);
                        setText(null);
                        btnReingreso.setGraphic(reingreso);
                        btnReingreso.setDisable(true);
                    }
                }
            };
            return cell;
        };
        colReingreso.setCellFactory(reingreso);
    }

    private void llenarBuscador() throws SQLException {
        pascientedao = new PacientesDAO(conexion.conectar2());
        AutoCompletionBinding<Paciente> pacientesBuscar = TextFields.bindAutoCompletion(txfBuscarPaciente, pascientedao.obtenerTodosPacientes());
        pacientesBuscar.setOnAutoCompleted(e -> {
            Paciente pacselec = e.getCompletion();
            idPacienteBuscador = pacselec.getIdPaciente();
        });
    }

    private void generarYColocarColumnaComplemento() {
        colAgregarInformacion.setMaxWidth(2500);
        colAcciones.getColumns().add(colAgregarInformacion);

        colImprimirReportes.setMaxWidth(2500);
        colAcciones.getColumns().add(colImprimirReportes);
    }

    private void generarColumnaComplemento(FoliosDAO foliosdao) {

        //  AGREGAR FUNCIONALIDAD DE EDITAR REGISTRO.
        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> complementar = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnReingreso = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView editar = new ImageView("/img/icons/icons8-lápiz-30.png");
                        editar.setFitHeight(20);
                        editar.setFitWidth(20);

                        btnReingreso.setOnAction(event -> {

                            try {
                                Folio folio = foliosdao.obtenerFolioPorId(paciente.getIdfolio());

                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de compleatar el registro de: " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    completarRegistroPaciente(paciente);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                              
                            }
                        });

                        setGraphic(btnReingreso);
                        setText(null);
                        setGraphic(btnReingreso);
                        setText(null);
                        btnReingreso.setGraphic(editar);
                        switch (paciente.getId_estatus_hospitalizacion()) {
                            case 0:
                                btnReingreso.setDisable(true);
                                break;
                            case 1:
                                btnReingreso.setDisable(false);
                                break;
                            case 2:
                                btnReingreso.setDisable(true);
                                break;
                        }

                    }
                }
            };
            return cell;
        };

        colAgregarInformacion.setCellFactory(complementar);
        colAgregarInformacion.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> imprimir = (TableColumn<Paciente, String> param) -> {
            final TableCell<Paciente, String> cell = new TableCell<Paciente, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnReingreso = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        Paciente paciente = getTableView().getItems().get(getIndex());
                        ImageView editar = new ImageView("/img/icons/icons8-imprimir-30.png");
                        editar.setFitHeight(20);
                        editar.setFitWidth(20);

                        btnReingreso.setOnAction(event -> {

                            try {
                                Folio folio = foliosdao.obtenerFolioPorId(paciente.getIdfolio());

                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿IMPRIMIR INFORMACION DE : " + paciente.getNombre() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    imprimirPulcera(paciente.getIdPaciente());
                                    imprimirDetalle(paciente.getIdfolio());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });

                        setGraphic(btnReingreso);
                        setText(null);

                        setGraphic(btnReingreso);
                        setText(null);
                        btnReingreso.setGraphic(editar);
                        switch (paciente.getId_estatus_hospitalizacion()) {
                            case 0:
                                btnReingreso.setDisable(true);
                                break;
                            case 1:
                                btnReingreso.setDisable(false);
                                break;
                            case 2:
                                btnReingreso.setDisable(true);
                                break;
                        }

                    }
                }
            };
            return cell;
        };

        colImprimirReportes.setCellFactory(imprimir);
        colImprimirReportes.setStyle("-fx-alignment: CENTER;");

    }

    private void completarRegistroPaciente(Paciente paciente) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares/PacienteNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);

        PacienteNuevoController pnc = fxml.getController();

        

        pnc.integrarInformacion(paciente);
        Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.setTitle("PACIENTE");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();

        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("Se ha completado el registro de: " + paciente.getNombre());
        alertaConfirmacion.showAndWait();

        llenarTabla();
    }

    private void imprimirPulcera(int id) {
        ReporteC repc = new ReporteC("Rpt_Pulcera_paciente");
        repc.generarPulcera(id);
    }

    private void imprimirDetalle(int id_folio) {
        ReporteC repc = new ReporteC("Rpt_Formato1_Deposito");
        repc.generarReporteDetalle(id_folio);
    }

    private void llamarModuloPagos(Folio folio) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ModuloPagosFXML.fxml"));
            Parent root = fxml.load();
            Scene scene = new Scene(root);
            ModuloPagosFXMLController generarPago = fxml.getController();

            generarPago.llenarTablaCaja(folio);
            Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(false);
            stage.setTitle("PACIENTE");
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setScene(scene);
            stage.showAndWait();
            llenarTabla();
        } catch (IOException ex) {
            Logger.getLogger(PacientesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PacientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reporte(ReporteCierre rptCierre, Folio folio, String nombre) {
        NumerosALetras numeroLetras = new NumerosALetras(rptCierre.getMontoHaastaElMomento());
        String numeroALetra = numeroLetras.getCantidadString();

        ReporteC reporte = new ReporteC("Rpt_CorteCierreCuenta");
        reporte.generarCierre(folio.getId(),
                rptCierre.getFolio(),
                rptCierre.getFecha(),
                rptCierre.getHora(),
                rptCierre.getPaciente(),
                rptCierre.getMedico(),
                rptCierre.getHabitacion(),
                rptCierre.getTipoPaquete(),
                rptCierre.getDepocito(),
                rptCierre.getMontoHaastaElMomento(),
                rptCierre.getSaldoACubrir(),
                rptCierre.getFormaDePago(),
                rptCierre.getConcepto(),
                rptCierre.getIdPaquete(),
                numeroALetra);

        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("Se ha confirmar el Alta de: " + nombre);
        alertaConfirmacion.showAndWait();
    }

}
