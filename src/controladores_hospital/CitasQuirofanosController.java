/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools  Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.CitaQuirofano;
import clases_hospital.Consumo;
import clases_hospital.Folio;
import clases_hospital.Inventario;
import clases_hospital.ProductoVenta;
import clases_hospital_DAO.CitaQuirofanoDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.InventariosDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.ProductoVentaDAO;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import vistasAuxiliares_hospital.CitasQuirofanosNuevoController;
import vistasAuxiliares_hospital.ModuloPagosFXMLController;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class CitasQuirofanosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<CitaQuirofano> citasquirofanos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Statement statement;
    ResultSet resultSet;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    int contador = 0;
    int idCitaQirofano;
    CitaQuirofanoDAO citaquirofanodao;
    FoliosDAO foliodao;
    PacientesDAO pacientedao;

    @FXML
    private TableView<CitaQuirofano> tabla;
    @FXML
    private TableColumn colFechaCitasQ;
    @FXML
    private TableColumn colHoraCitasQ;
    @FXML
    private TableColumn colPacienteCitasQ;
    @FXML
    private TableColumn colMedicoCitasQ;
    @FXML
    private TableColumn colQuirofanoCitasQ;
    @FXML
    private TableColumn colCirugiaCitasQ;
    @FXML
    private TableColumn colObsevacionesCitasQ;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private DatePicker dateFechaI;// = new DatePicker(LocalDate.now());
    @FXML
    private DatePicker dateFechaF;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableColumn colAcciones;
    @FXML
    private TableColumn colAsistencia;
    @FXML
    private TextField txtPaciente;
    @FXML
    private TableColumn colDesocupado;
    @FXML
    private TableColumn colInternar;
    @FXML
    private TableColumn colHoraSalida;
    @FXML
    private TableColumn colDuracion;
    @FXML
    private RadioButton rdbNobrePaciente;
    @FXML
    private RadioButton rdbFechas;
    @FXML
    private RadioButton rdbHoy;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            llenarTabla();
            llenearBuscador();
            pintarTabla();
            centrarTextoTabla();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), (event) -> {
             
                refreshTable();
            }));

//            timeline.setCycleCount(Animation.INDEFINITE);
//            timeline.play();
        } catch (SQLException ex) {
            Logger.getLogger(CitasQuirofanosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void actionAgregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CitasQuirofanos2.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("AGENDA QUIROFANO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
        pintarTabla();
    }

    @FXML
    private void buscar(ActionEvent event) throws SQLException {
        /**
         * NOTA PARA FITO NO BUSCA MAS HAYA DE 2023 METODO PARA FILTRADO DE
         * TABLA
         */
        ArrayList<CitaQuirofano> citasquirfoanosSearch = new ArrayList<>();
        String fechaFinNull = "" + dateFechaF.getValue();
        String fechaInicionNull = "" + dateFechaI.getValue();

        if (txtPaciente.getText().isEmpty() && fechaFinNull.equals("null") && fechaInicionNull.equals("null")) {
            alertaError.setHeaderText("ERROR EN LA BUSQUEDA");
            alertaError.setContentText("Ingrese al PACIENTE o el INTERVALO DE FECHAS\nque descea buscar");
            alertaError.showAndWait();
        } else if (!txtPaciente.getText().isEmpty() && !fechaInicionNull.equals("null") && !fechaFinNull.equals("null")) {
            alertaError.setHeaderText("ERROR EN LA BUSQUEDA");
            alertaError.setContentText("Si descea buscar un INTERVALO DE FECHAS ingrese una \n FECHA DE FIN y borre la busqueda del PACIENTE");
            alertaError.showAndWait();
        } else {
            if (txtPaciente.getText().isEmpty()) {
                if (fechaInicionNull.equals("null")) {
                    alertaError.setHeaderText("ERROR EN LA BUSQUEDA");
                    alertaError.setContentText("Ingrese la FECHA DE INICIO de su busqueda");
                    alertaError.showAndWait();
                } else if (fechaFinNull.equals("null")) {
                    alertaError.setHeaderText("ERROR EN LA BUSQUEDA");
                    alertaError.setContentText("Ingrese la FECHA DE FIN de su busqueda");
                    alertaError.showAndWait();
                } else {
                    LocalDate fechainicio = dateFechaI.getValue();
                    Date inicioDate = java.sql.Date.valueOf(fechainicio);
                    LocalDate fechafin = dateFechaF.getValue();
                    Date finDate = java.sql.Date.valueOf(fechafin);

                    citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());
                    citasquirfoanosSearch.addAll(citaquirofanodao.obtenerTodasLasCitasFiltradoFecha(inicioDate, finDate));
                }
            } else {
                if (!fechaInicionNull.equals("null")) {
                    alertaError.setHeaderText("ERROR EN LA BUSQUEDA");
                    alertaError.setContentText("Si descea buscar un INTERVALO DE FECHAS ingrese una \nFECHA DE FIN y borre la busqueda del PACIENTE");
                    alertaError.showAndWait();
                } else if (!fechaFinNull.equals("null")) {
                    alertaError.setHeaderText("ERROR EN LA BUSQUEDA");
                    alertaError.setContentText("Si descea buscar un INTERVALO DE FECHAS ingrese una \nFECHA DE INICIO y borre la busqueda del PACIENTE");
                    alertaError.showAndWait();
                } else {
                    citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());
                    citasquirfoanosSearch.add(citaquirofanodao.obtenerCitaPorId(idCitaQirofano));
                }
            }
        }

        if (citasquirfoanosSearch.isEmpty()) {
            tabla.getItems().clear();
            llenarTabla();
        } else {
            tabla.getItems().clear();

            citasquirofanos.addAll(citasquirfoanosSearch);

            colFechaCitasQ.setCellValueFactory(new PropertyValueFactory("formatterFechaCirugia"));
            colHoraCitasQ.setCellValueFactory(new PropertyValueFactory("horaInicio"));
            colInternar.setCellValueFactory(new PropertyValueFactory("formatterFechaHabitacionApartado"));
            colPacienteCitasQ.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
            colMedicoCitasQ.setCellValueFactory(new PropertyValueFactory("nombreMedico"));
            colQuirofanoCitasQ.setCellValueFactory(new PropertyValueFactory("quirofano"));
            colCirugiaCitasQ.setCellValueFactory(new PropertyValueFactory("listaserviciosadicionales"));
            colObsevacionesCitasQ.setCellValueFactory(new PropertyValueFactory("observaciones"));
            colHoraSalida.setCellValueFactory(new PropertyValueFactory("horaFin"));
            colDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));

            generarBotones();

            tabla.setItems(citasquirofanos);
        }
    }

    @FXML
    private void editar(ActionEvent event) throws IOException, SQLException {
        CitaQuirofano citaQuirofano = this.tabla.getSelectionModel().getSelectedItem();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CitasQuirofanosNuevo.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        CitasQuirofanosNuevoController cqn = fxml.getController();
        //Mandamos a llamar el metodo Cita moficiar de la vista CITAQUIROFANONUEVO
        cqn.recuperarDatos(citaQuirofano);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
        pintarTabla();
    }

    @FXML
    private void eliminar(ActionEvent event) throws SQLException {
        citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());
        CitaQuirofano citaQuirofano = this.tabla.getSelectionModel().getSelectedItem();

        switch (citaQuirofano.getIdEstatusAgenda()) {
            case 1:
                alertaConfirmacion.setHeaderText(null);
                alertaConfirmacion.setTitle("Confirmación");
                alertaConfirmacion.setContentText("¿Estas seguro de cancelar : " + citaQuirofano.getNombrePaciente() + " ?");
                Optional<ButtonType> actionEliminarConConfirmacion = alertaConfirmacion.showAndWait();
                if (actionEliminarConConfirmacion.get() == ButtonType.OK) {

                    citaQuirofano.setIdEstatusAgenda(3);
                    citaquirofanodao.actualizarCita(citaQuirofano);

                    alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
                    alertaSuccess.setContentText("Cita Cancelada");
                    alertaSuccess.showAndWait();
                    llenarTabla();
                    pintarTabla();
                }
                break;
            case 2:
                alertaError.setHeaderText(null);
                alertaError.setTitle("ERROR");
                alertaError.setContentText("ESTA CITA YA FUE ATENDIDA, IMPOSIBLE CANCELAR REGISTRO.");
                Optional<ButtonType> actionCita = alertaError.showAndWait();
                break;
            case 3:
                alertaError.setHeaderText(null);
                alertaError.setTitle("ERROR");
                alertaError.setContentText("ESTA CITA YA FUE CANCELADA, IMPOSIBLE VOLVER A CANCELAR REGISTRO.");
                Optional<ButtonType> actionCancelar = alertaError.showAndWait();
                break;
            default:
                alertaConfirmacion.setHeaderText(null);
                alertaConfirmacion.setTitle("Confirmación");
                alertaConfirmacion.setContentText("¿Estas seguro de cancelar : " + citaQuirofano.getNombrePaciente() + "?");
                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                if (action.get() == ButtonType.OK) {

                    citaQuirofano.setIdEstatusAgenda(3);
                    citaquirofanodao.actualizarCita(citaQuirofano);

                    alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
                    alertaSuccess.setContentText("Cita Cancelada");
                    alertaSuccess.showAndWait();
                    llenarTabla();
                    pintarTabla();
                }
                break;
        }
    }

    private void moduloHabitacionesTabla() throws IOException {
//        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas_hospital/HabitacionesTabla.fxml"));
//        Parent root = fxml.load();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.showAndWait();
    }

    private void generarBotones() {
        /**
         * GENERAR EL BOTON CONFIRMAR EN LA TABLA
         */
        Callback<TableColumn<CitaQuirofano, String>, TableCell<CitaQuirofano, String>> confirmar = (TableColumn<CitaQuirofano, String> param) -> {
            final TableCell<CitaQuirofano, String> cell = new TableCell<CitaQuirofano, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnCofirmar = new Button("");
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        CitaQuirofano citaQuirofano = getTableView().getItems().get(getIndex());
                        ImageView check = new ImageView("/img/icons/icons8-marca-de-verificación-50.png");
                        check.setFitHeight(20);
                        check.setFitWidth(20);
                        ImageView agenda = new ImageView("/img/icons/icons8-calendario-de-rasgar-las-hojas-24.png");
                        agenda.setFitHeight(20);
                        agenda.setFitWidth(20);
                        ImageView cancelar = new ImageView("/img/icons/icons8-cancelar-2-50.png");
                        cancelar.setFitHeight(20);
                        cancelar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnCofirmar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de confirmar a: " + citaQuirofano.getNombrePaciente() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    generarPago(citaQuirofano);
                                    ingresarKitBsicoConsumo();
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                             
                            }
                        });
                        setGraphic(btnCofirmar);
                        setText(null);
                        switch (citaQuirofano.getIdEstatusAgenda()) {
                            case 1: // CUANDO EL PACIENTE APART LA CIT
                                btnCofirmar.setGraphic(check);
                                btnCofirmar.setDisable(true);
                                break;
                            case 2: // CUANDO EL PACIENTE ASISTE lA CITA
                                btnCofirmar.setGraphic(check);
                                btnCofirmar.setDisable(true);
                                break;
                            case 3: // CUANDO LA CITA FUE CANCELDA
                                btnCofirmar.setGraphic(cancelar);
                                btnCofirmar.setDisable(true);
                                break;
                            case 4:
                                btnCofirmar.setGraphic(check);
                                btnCofirmar.setDisable(true);
                                break;
                            default:// CUANDO EL REGISTRO FUE CREADO Y NO HA SIDO MOFICDO. 
                                btnCofirmar.setGraphic(agenda);
                                btnCofirmar.setDisable(false);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colAcciones.setCellFactory(confirmar);

        /**
         * GENERAR EL BOTON ASISTENCIA EN LA TABLA
         */
        Callback<TableColumn<CitaQuirofano, String>, TableCell<CitaQuirofano, String>> entradaQuirofano = (TableColumn<CitaQuirofano, String> param) -> {
            final TableCell<CitaQuirofano, String> cell = new TableCell<CitaQuirofano, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnAsistencia = new Button("");
                        CitaQuirofano citaQuirofano = getTableView().getItems().get(getIndex());
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        ImageView check = new ImageView("/img/icons/icons8-marca-de-verificación-50.png");
                        check.setFitHeight(20);
                        check.setFitWidth(20);
                        ImageView asistencia = new ImageView("/img/icons/icons8-entrar-50.png");
                        asistencia.setFitHeight(20);
                        asistencia.setFitWidth(20);
                        ImageView cancelar = new ImageView("/img/icons/icons8-cancelar-2-50.png");
                        cancelar.setFitHeight(20);
                        cancelar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON ASITENCIA
                         */
                        btnAsistencia.setOnAction(event -> {
                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de confirmar a: " + citaQuirofano.getNombrePaciente() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    ingresarHoraEntradaQuirofano(citaQuirofano);
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                System.err.println("algo salio mal");
                            }
                        });
                        setGraphic(btnAsistencia);
                        setText(null);
                        switch (citaQuirofano.getIdEstatusAgenda()) {
                            case 1: // CUANDO EL LA CITA FUE APARTADA, Y SE ESPERA LA ASISTENCIA DE PACIENTE
                                btnAsistencia.setGraphic(asistencia);
                                btnAsistencia.setDisable(false);
                                break;
                            case 2: // CUANDO EL PACIOENTE ASISTE A L CITA
                                btnAsistencia.setGraphic(check);
                                btnAsistencia.setDisable(true);
                                break;
                            case 3: // CUANDO LA CITA FUE CANCELDA
                                btnAsistencia.setGraphic(cancelar);
                                btnAsistencia.setDisable(true);
                                break;
                            case 4:
                                btnAsistencia.setGraphic(check);
                                btnAsistencia.setDisable(true);
                                break;
                            default:// CUANDO EL REGISTRO FUE CREADO Y NO HA SIDO MOFICDO. 
                                btnAsistencia.setGraphic(asistencia);
                                btnAsistencia.setDisable(false);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colAsistencia.setCellFactory(entradaQuirofano);

        /**
         * GENERAR EL BOTON DESOCUPAR EN LA TABLA
         */
        Callback<TableColumn<CitaQuirofano, String>, TableCell<CitaQuirofano, String>> desocuopado = (TableColumn<CitaQuirofano, String> param) -> {
            final TableCell<CitaQuirofano, String> cell = new TableCell<CitaQuirofano, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnDesocupar = new Button("");
                        CitaQuirofano citaQuirofano = getTableView().getItems().get(getIndex());
                        /**
                         * ICONOS QUE PARA EL BOTON CHECK -> CUANDO SE SE
                         * SELECCIONA EL BOTON ASISTENICA ASISTENCIA -> SE
                         * MUESTRA HASTA SER SELECCIONADO CANCELAR -> SE MUESTRA
                         * CUANDO EL REGISTRO ES CANCELADO
                         */
                        ImageView check = new ImageView("/img/icons/icons8-marca-de-verificación-50.png");
                        check.setFitHeight(20);
                        check.setFitWidth(20);
                        ImageView desocupar = new ImageView("/img/icons/icons8-salida-50.png");
                        desocupar.setFitHeight(20);
                        desocupar.setFitWidth(20);
                        ImageView cancelar = new ImageView("/img/icons/icons8-cancelar-2-50.png");
                        cancelar.setFitHeight(20);
                        cancelar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON ASITENCIA
                         */
                        btnDesocupar.setOnAction(event -> {
                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de confirmar a: " + citaQuirofano.getNombrePaciente() + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    horaSalida(citaQuirofano);
                                    alertaSuccess.setHeaderText("Procedimiento Ejecutado con Exito");
                                    alertaSuccess.setContentText("Colaborador fuera del sistema");
                                    alertaSuccess.showAndWait();
                                    llenarTabla();
                                }
                            } catch (Exception e) {
                                System.err.println("algo salio mal");
                            }
                        });
                        setGraphic(btnDesocupar);
                        setText(null);
                        switch (citaQuirofano.getIdEstatusAgenda()) {
                            case 1: // CUANDO EL LA CITA FUE APARTADA, Y SE ESPERA LA ASISTENCIA DE PACIENTE
                                btnDesocupar.setGraphic(desocupar);
                                btnDesocupar.setDisable(true);
                                break;
                            case 2: // CUANDO EL PACIOENTE ASISTE A L CITA
                                btnDesocupar.setGraphic(desocupar);
                                btnDesocupar.setDisable(false);
                                break;
                            case 3: // CUANDO LA CITA FUE CANCELDA
                                btnDesocupar.setGraphic(cancelar);
                                btnDesocupar.setDisable(true);
                                break;
                            case 4:
                                btnDesocupar.setGraphic(check);
                                btnDesocupar.setDisable(true);
                                break;
                            default:// CUANDO EL REGISTRO FUE CREADO Y NO HA SIDO MOFICDO. 
                                btnDesocupar.setGraphic(desocupar);
                                btnDesocupar.setDisable(true);
                                break;
                        }
                    }
                }
            };
            return cell;
        };

        colDesocupado.setCellFactory(desocuopado);
    }

    private void horaSalida(CitaQuirofano citaQuirofano) throws SQLException {
     
        citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());

        if (citaquirofanodao.hayHoraExtra(citaQuirofano)) {

            foliodao = new FoliosDAO(conexion.conectar2());
            Folio folioPaciente = foliodao.obtenerFolioPorId(citaQuirofano.getId_folios());
            int idProductoVentaQurofano;
            int diferenciaDeHoras = citaquirofanodao.optenerDiferenciaHoras(citaQuirofano);

            if (citaQuirofano.getIdQuirofano() > 1) {
                idProductoVentaQurofano = 2;
            } else {
                if (diferenciaDeHoras > 1) {
                    idProductoVentaQurofano = 2;
                } else {
                    idProductoVentaQurofano = 1;
                }
            }

            ProductoVentaDAO productoventadao = new ProductoVentaDAO(conexion.conectar2());
            ProductoVenta productoventa = productoventadao.obtenerPorId(idProductoVentaQurofano);
            ConsumosDAO consumodao = new ConsumosDAO(conexion.conectar2());

            Consumo consumo = new Consumo();
            consumo.setTipo(productoventa.getNombreProducto());
            consumo.setCantidad(diferenciaDeHoras);
            consumo.setMonto(productoventa.getPrecioVenta() * diferenciaDeHoras);
            consumo.setFolio(folioPaciente.getFolio());
            consumo.setId_pasiente(citaQuirofano.getIdPaciente());
            consumo.setId_PaqueteAlimento(1);
            consumo.setId_tipo_consumo(2);
            consumo.setId_folio(folioPaciente.getId());
            consumo.setId_producto_venta(productoventa.getId());

            consumodao.insertarConsumo(consumo);

        }
        Calendar calendar = Calendar.getInstance();

        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);
        int segundos = calendar.get(Calendar.SECOND);

        Time horaActual = new Time(hora, minutos, segundos);
        
        citaQuirofano.setHora_salida_quirofano(horaActual);
        citaQuirofano.setIdEstatusAgenda(4);
        citaquirofanodao.actualizarCita(citaQuirofano);

    }

    public void refreshTable() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    citasquirofanos.clear();
                    citasquirofanos.addAll(getData());
                } catch (SQLException ex) {
                    Logger.getLogger(CitasQuirofanosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private List<CitaQuirofano> getData() throws SQLException {
        List<CitaQuirofano> listaCitasQuirofanos = new LinkedList<>();
        citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());

        listaCitasQuirofanos.addAll(citaquirofanodao.obtenerTodasLasCitasPorFecha());

        return listaCitasQuirofanos;
    }

    private void llenarTabla() throws SQLException {
        this.tabla.getItems().clear();
        citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());

        citasquirofanos.addAll(citaquirofanodao.obtenerTodasLasCitasPorFecha());

        colFechaCitasQ.setCellValueFactory(new PropertyValueFactory("formatterFechaCirugia"));
        colHoraCitasQ.setCellValueFactory(new PropertyValueFactory("horaInicio"));
        colInternar.setCellValueFactory(new PropertyValueFactory("formatterFechaHabitacionApartado"));
        colPacienteCitasQ.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
        colMedicoCitasQ.setCellValueFactory(new PropertyValueFactory("nombreMedico"));
        colQuirofanoCitasQ.setCellValueFactory(new PropertyValueFactory("quirofano"));
        colCirugiaCitasQ.setCellValueFactory(new PropertyValueFactory("listacirugias"));
        colObsevacionesCitasQ.setCellValueFactory(new PropertyValueFactory("observaciones"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory("horaFin"));
        colDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));

        generarBotones();

        tabla.setItems(citasquirofanos);

    }

    //Metodo para pintar tabla
    private void pintarTabla() {
        tabla.setRowFactory(tv -> new TableRow<CitaQuirofano>() {
            @Override
            public void updateItem(CitaQuirofano item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getIdEstatusAgenda() == 1) {
                    setStyle("-fx-background-color: #c7f5f5; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
                } else if (item.getIdEstatusAgenda() == 2) {
                    setStyle("-fx-background-color:  #2ECC71; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//asistencia
                } else if (item.getIdEstatusAgenda() == 3) {
                    setStyle("-fx-background-color:  #E74C3C; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//cancelado
                } else {
                    setStyle(" ");
                }

            }
        });
    }

    private void llenearBuscador() throws SQLException {
        citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());

        AutoCompletionBinding<CitaQuirofano> pacientes = TextFields.bindAutoCompletion(txtPaciente, citaquirofanodao.obtenerTodasLasCitasPorFecha());
        pacientes.setOnAutoCompleted(event -> {
            CitaQuirofano selectPaciente = event.getCompletion();
            idCitaQirofano = selectPaciente.getId();
        });

    }

    private void generarPago(CitaQuirofano citaquirofano) throws IOException, SQLException {
        pacientedao = new PacientesDAO(conexion.conectar2());
        Paciente paciente = pacientedao.obtenerPacientePorId(citaquirofano.getIdPaciente());
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/ModuloPagosFXML.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        ModuloPagosFXMLController generarPago = fxml.getController();
//        generarPago.llenarTablaCaja(paciente);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void ingresarHoraEntradaQuirofano(CitaQuirofano citaquirofano) throws SQLException, IOException {
        citaquirofanodao = new CitaQuirofanoDAO(conexion.conectar2());
        LocalDate fechaLocal = LocalDate.now();
        java.util.Date fecha_ingreso = java.util.Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date fechaSQL_ingresoQuirofano = new java.sql.Date(fecha_ingreso.getTime());
        Calendar calendar = Calendar.getInstance();

        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);
        int segundos = calendar.get(Calendar.SECOND);

        Time horaActual = new Time(hora, minutos, segundos);

        citaquirofano.setFecha_ingreso_quirofano(fechaSQL_ingresoQuirofano);
        citaquirofano.setHora_ingreso_quirofano(horaActual);
        citaquirofano.setIdEstatusAgenda(2);

        citaquirofanodao.actualizarCita(citaquirofano);
        alertaConfirmacion.setHeaderText(null);
        alertaConfirmacion.setTitle("Confirmación");
        alertaConfirmacion.setContentText("El/La paciente " + citaquirofano.getNombrePaciente() + " ha ingresado cirugia");
        alertaSuccess.showAndWait();

    }

    private void centrarTextoTabla() {
        colFechaCitasQ.setStyle("-fx-alignment: CENTER;");
        colHoraCitasQ.setStyle("-fx-alignment: CENTER;");
        colPacienteCitasQ.setStyle("-fx-alignment: CENTER;");
        colMedicoCitasQ.setStyle("-fx-alignment: CENTER;");
        colQuirofanoCitasQ.setStyle("-fx-alignment: CENTER;");
        colCirugiaCitasQ.setStyle("-fx-alignment: CENTER;");
        colObsevacionesCitasQ.setStyle("-fx-alignment: CENTER;");
        colAcciones.setStyle("-fx-alignment: CENTER;");
        colAsistencia.setStyle("-fx-alignment: CENTER;");
        colDesocupado.setStyle("-fx-alignment: CENTER;");
        colInternar.setStyle("-fx-alignment: CENTER;");
        colHoraSalida.setStyle("-fx-alignment: CENTER;");
        colDuracion.setStyle("-fx-alignment: CENTER;");
    }
    
    private void ingresarKitBsicoConsumo() throws SQLException{
        con = conexion.conectar2();
        ConsumosDAO consumodao = new ConsumosDAO(con);
        InventariosDAO inventariodao = new InventariosDAO(con);
        
        Consumo consumoKitBasico = new Consumo();
        Inventario inventario = inventariodao.obptenerDatosPorIdKitBsico(1);
    }

}
