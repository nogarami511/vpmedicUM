/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.CitaQuirofano;
import clases_hospital.Procedimiento;
import clases_hospital.ServicioAdicional;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;
import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ModificarAgendaQuirofanoController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<CitaQuirofano> citasquirofanos = FXCollections.observableArrayList();
    ObservableList<Procedimiento> procedimientos = FXCollections.observableArrayList();
    ObservableList<ServicioAdicional> servicioAdicionales = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Connection conVPMP = conexion.conectar2();
    int idQuirofano;
    String quirofanoConsulta;
    int idQuirofanoConsulta;
    String idServiciosAdicionales;
    String idsProcedimineto;
    private int idHabitacion;
    private int idPaciente;
    private String horaFin, horaInicio, duracion;

    @FXML
    private Button btnSalir;
    @FXML
    private TextField pacienteCitasQ;
    @FXML
    private TextField contactoCitasQ;
    @FXML
    private DatePicker fechaCitasQ;
    @FXML
    private TextField medicoCitasQ;
    @FXML
    private ComboBox habitacionCitasQ;
    @FXML
    private ComboBox quirofanoCitasQ;
    @FXML
    private TextField cirugiaCitasQ;
    @FXML
    private Button btnColocarQuirofano;
    @FXML
    private TextArea observacionesCitasQ;
    @FXML
    private TextField serviciosCitaQ;
    @FXML
    private ComboBox cbmHoraInicio;
    @FXML
    private ComboBox cbmMinutosInicio;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnServiciosAdicionales;
    @FXML
    private TableView<Procedimiento> tablaCirugia;
    @FXML
    private TableColumn nombreProcedimiento;
    @FXML
    private TableColumn colTipoProcedimiento;
    @FXML
    private TableView<ServicioAdicional> tablaServiciosAdicionales;
    @FXML
    private TableColumn NombreServicioAdicional;
    @FXML
    private DatePicker fechaInternarCitasQ;
    @FXML
    private ComboBox cbmHoraFin;
    @FXML
    private ComboBox cbmMinutosFin;
    @FXML
    private Label lbmDuracionHora;
    @FXML
    private Label lbmDuracionMinutos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*ARREGLAR ESTA VISTA PARA QUE PUEDA ACTUALIZAR DATOS.*/
        pacienteCitasQ.setDisable(true);
        llenarChoiseBox();
        desactivarFechasPasadas();
    }

    @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void colocarQuirofano(ActionEvent event) throws SQLException {
        if (cirugiaCitasQ.getText().equals("")) {
            alertaError.setHeaderText("Error");
            alertaError.setContentText("Asegurese que se este ingresando la cirugia \nen su campo correspondiete");
            alertaError.showAndWait();
        } else {
            consultaColocarQuirofano();
        }
    }

    private void consultaColocarQuirofano() throws SQLException {
        ArrayList<String> arrayQuirofano = new ArrayList<>();
        if ((fechaCitasQ.getValue() == null || fechaCitasQ.getValue().equals(LocalDate.MIN))
                || ((cbmHoraInicio.getValue() == null || cbmMinutosInicio.getValue() == null))
                || (pacienteCitasQ.getText().equals(""))) {
            alertaError.setHeaderText("Error");
            alertaError.setContentText("Nombre del paciente, hora, minutos de inicio y fin, y/o fecha no ingresados");
            alertaError.showAndWait();
        } else {
            String citasProcedimiento = "";
            String tipoPro = "";
            String citas = cirugiaCitasQ.getText();
            llenarTablaProcedimiento(citas);
            for (int i = 0; i < procedimientos.size(); i++) {
                if (quirofanoOcupado()) {
                    citasProcedimiento = citas;
                    quirofanoCitasQ.setValue(optenerIdQuirofano());
                    if (quirofanoCitasQ.getSelectionModel().getSelectedItem().toString().equals("")) {
                        alertaError.setHeaderText("Error");
                        alertaError.setContentText("LOS QUIROFANOS SE ECUENTRAN OCUPADOS, PORFAVOR ELIJA OTRA FECHA");
                        alertaError.showAndWait();
                    }
                } else {
                    if (i == 0) {
                        tipoPro = procedimientos.get(i).getTipo_procedimiento();
                        citasProcedimiento = procedimientos.get(i).getNombre();
                    } else if (tipoPro.compareTo(procedimientos.get(i).getTipo_procedimiento()) > 0) {
                        citasProcedimiento = citasProcedimiento;
                    } else {
                        tipoPro = procedimientos.get(i).getTipo_procedimiento();
                        citasProcedimiento = procedimientos.get(i).getNombre();
                    }
                    try {
                        String quiro = "";
                        CallableStatement stmt = null;
                        String sql = "{call seleccionarQuirofno (?)}";
                        stmt = con.prepareCall(sql);
                        stmt.setString(1, citasProcedimiento);
                        ResultSet rs = stmt.executeQuery();
                       
                        quirofanoCitasQ.setValue(rs.getString(1));
                        quiro = rs.getString(1);
                        // hay que agregarle cosas a esto
                       
                        if (idQuirofanoConsulta != 0) {
                           
                            quirofanoCitasQ.getItems().clear();
                            arrayQuirofano.clear();
                            quirofanoCitasQ.setValue(rs.getString(1));
                            rs = stmt.executeQuery("SELECT nombre FROM quirofanos WHERE id != " + idQuirofanoConsulta);
                            while (rs.next()) {
                                arrayQuirofano.add(rs.getString(1));
                            }
                            quirofanoCitasQ.getItems().addAll(arrayQuirofano);
                            quirofanoCitasQ.setValue(quiro);
                        } else {
                            quirofanoCitasQ.getItems().clear();
                            arrayQuirofano.clear();
                            quirofanoCitasQ.setValue(rs.getString(1));
                            rs = stmt.executeQuery("select nombre from quirofanos");
                            while (rs.next()) {
                                arrayQuirofano.add(rs.getString(1));
                            }
                            quirofanoCitasQ.getItems().addAll(arrayQuirofano);
                        }
                        horaFin();

                        if (tipoPro.equals("AAA")) {
                            quirofanoCitasQ.setDisable(true);
                        } else {
                            quirofanoCitasQ.setDisable(false);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    private void llenarChoiseBox() {
        ArrayList<String> arrayMedico = new ArrayList<>();
        ArrayList<String> arrayQuirofano = new ArrayList<>();
        ArrayList<String> arrayCirugias = new ArrayList<>();
        ArrayList<Paciente> arrayPacientes = new ArrayList<>();
        ArrayList<String> arrayHabitacion = new ArrayList<>();
        ArrayList<String> arrayServicios = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            Statement stmtVPMP = conVPMP.createStatement();
            ResultSet rs = stmtVPMP.executeQuery("select razonSocial from clientes WHERE proyecto = 'TORRE MEDICA' OR proyecto = 'HOSPITAL'");
            while (rs.next()) {
                arrayMedico.add(rs.getString(1));
            }
            TextFields.bindAutoCompletion(medicoCitasQ, arrayMedico);

            rs = stmt.executeQuery("select nombre from procedimiento");
            while (rs.next()) {
                arrayCirugias.add(rs.getString(1));
            }
            TextFields.bindAutoCompletion(cirugiaCitasQ, arrayCirugias);

            rs = stmt.executeQuery("select id, nombre from pacientes");
            while (rs.next()) {
                arrayPacientes.add(new Paciente(rs.getInt(1), rs.getString(2)));
            }
            AutoCompletionBinding<Paciente> pacientes = TextFields.bindAutoCompletion(pacienteCitasQ, arrayPacientes);
            pacientes.setOnAutoCompleted(event -> {
                Paciente selectPaciente = event.getCompletion();
                idPaciente = 0 ; //selectPaciente.getId();
            });

            rs = stmt.executeQuery("SELECT DISTINCT tipo FROM habitacion");
            while (rs.next()) {
                arrayHabitacion.add(rs.getString(1));
            }
            habitacionCitasQ.getItems().addAll(arrayHabitacion);

            rs = stmt.executeQuery("select nombre from serviciosadicionales");

            while (rs.next()) {
                arrayServicios.add(rs.getString(1));
            }
            TextFields.bindAutoCompletion(serviciosCitaQ, arrayServicios);

        } catch (SQLException e) {
        }
        String hh;
        String mm;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hh = "0" + i;
                cbmHoraInicio.getItems().add(hh);
            } else {
                cbmHoraInicio.getItems().add(00 + i);
            }
        }
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                mm = "0" + i;
                cbmMinutosInicio.getItems().add(mm);
            } else {
                cbmMinutosInicio.getItems().add(00 + i);
            }
        }

        cbmHoraInicio.setOnAction(e -> {
            if (cbmMinutosInicio.isDisable()) {
                cbmMinutosInicio.setDisable(false);
                horaFin();
            } else {
                horaFin();
            }
        });
        cbmMinutosInicio.setOnAction(e -> {
            lbmDuracionMinutos.setText(cbmMinutosInicio.getSelectionModel().getSelectedItem().toString());
            cbmMinutosFin.setValue(cbmMinutosInicio.getSelectionModel().getSelectedItem().toString());
        });

        cbmHoraFin.setOnAction(e -> {
            int horafin = Integer.parseInt(cbmHoraFin.getSelectionModel().getSelectedItem().toString()) - Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString());
            if (horafin <= 9) {
                lbmDuracionHora.setText("0" + horafin);
            } else {
                lbmDuracionHora.setText("" + horafin);
            }

           
        });

    }

    public void horaFin() {
        int hora;
        if (quirofanoCitasQ.getItems().isEmpty()) {
            System.err.println(quirofanoCitasQ.isDisable());
            lbmDuracionHora.setText("01");
            
            hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 1;
            if (hora <= 9) {
                cbmHoraFin.setValue("0" + hora);
            } else {
                if (hora > 23) {
                    cbmHoraFin.setValue("00");
                }
                cbmHoraFin.setValue("" + hora);
            }
        } else {
            String quirofano = quirofanoCitasQ.getSelectionModel().getSelectedItem().toString();
           
            int cantidad;
            if (quirofano.equals("SANTA RITA")) {
                //En caso de ser true, se coloca la hora de fin a "1 HORA POSTRIOR AL INICO"
                hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 1;
                cantidad = 1;
                lbmDuracionHora.setText("01");
            } else {
                //En caso de ser false, se coloca la hora de fin a "3 HORA POSTRIOR AL INICO"
                hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 3;
                cantidad = 3;
                lbmDuracionHora.setText("0" + 3);
            }
            if (hora <= 9) {
                cbmHoraFin.setValue("0" + (Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + cantidad));
            } else {
                if (hora > 20 && cantidad == 3) {
                    switch (hora) {
                        case 21:
                            cbmHoraFin.setValue("00");
                            break;
                        case 22:
                            cbmHoraFin.setValue("01");
                            break;
                        case 23:
                            cbmHoraFin.setValue("02");
                            break;
                    }
                } else if (hora > 23 && cantidad == 1) {
                    cbmHoraFin.setValue("00");
                } else {
                    cbmHoraFin.setValue("" + (Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + cantidad));
                }
            }
        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        int hora = 0;
        int id = this.idQuirofano;
        String quirofano = quirofanoCitasQ.getSelectionModel().getSelectedItem().toString();
        String paciente = pacienteCitasQ.getText();
        for (int i = 0; i < servicioAdicionales.size(); i++) {
            if (i == 0) {
                idServiciosAdicionales = "" + servicioAdicionales.get(i).getId();
            } else {
                idServiciosAdicionales = idServiciosAdicionales + "," + servicioAdicionales.get(i).getId();
            }
        }
        String habitacion = habitacionCitasQ.getSelectionModel().getSelectedItem().toString();
        for (int i = 0; i < procedimientos.size(); i++) {
            if (i == 0) {
                idsProcedimineto = "" + procedimientos.get(i).getId();
            } else {
                idsProcedimineto = idsProcedimineto + "," + procedimientos.get(i).getId();
            }
        }
        String obcervacion = observacionesCitasQ.getText();
        String hInicio = cbmHoraInicio.getSelectionModel().getSelectedItem().toString() + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
        String hFin = horaFin;
        if (!(hInicio.equals(horaInicio))) {
            if (quirofano.equals("SANTA RITA")) {
                hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 1;
            } else {
                hora = Integer.parseInt(cbmHoraInicio.getSelectionModel().getSelectedItem().toString()) + 3;
            }
            if (hora <= 9) {
                hFin = "0" + hora + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
            } else {
                hFin = "" + hora + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
            }
        }
        String contacto = contactoCitasQ.getText();
        Date fechaCita = Date.valueOf(fechaCitasQ.getValue());
        Date fechaHabitacionApartada = Date.valueOf(fechaInternarCitasQ.getValue());

        try {
            CallableStatement stmt = null;
            String sql = "{call ModificarCitasQuirofano (?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setString(2, quirofano);
            stmt.setString(3, idServiciosAdicionales);
            stmt.setString(4, habitacion);
            stmt.setString(5, idsProcedimineto);
            stmt.setString(6, contacto);
            stmt.setString(7, hInicio);
            stmt.setString(8, hFin);
            stmt.setString(9, duracion);
            stmt.setDate(10, fechaCita);
            stmt.setString(11, obcervacion);
            stmt.setDate(12, fechaHabitacionApartada);
            stmt.setInt(13, userSystem);
            stmt.execute();
            agregarCuenta(quirofano);
            String sql2 = "{call ActualizarHabitacionQuirofano (?,?,?)}";
            stmt = con.prepareCall(sql2);
            stmt.setInt(1, idHabitacion);
            stmt.setInt(2, idPaciente);
            stmt.setInt(3, userSystem);
            stmt.execute();
            alertaSuccess.setHeaderText("Cita de " + pacienteCitasQ.getText() + " Reagendado");
            alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
            alertaSuccess.showAndWait();
            Stage stage = (Stage) btnModificar.getScene().getWindow();;
            stage.close();
        } catch (Exception e) {
            alertaError.setHeaderText("Error");
            alertaError.setContentText("Error: " + e);
            alertaError.showAndWait();

            e.printStackTrace(System.out);
        }
    }

    public void citaModificar(int id, String quirofano, String medico, String paciente, String habitacion, String serviciosAdicionales, String cirugia, String contacto, String hInicio, String hFin, Date fechaCita, String obcervacion, int idPaciente, Date fechaApartadoHab, String horaFin, String hDuracion) throws SQLException {
        this.idQuirofano = id;
        observacionesCitasQ.setText(obcervacion);
        tablaServiciosAdicionalesEditar(serviciosAdicionales);
        horaInicio = hInicio;
        String[] hInicioParts = hInicio.split(":");
        cbmHoraInicio.setValue(hInicioParts[0]);
        cbmMinutosInicio.setValue(hInicioParts[1]);
        horaFin = hFin;
        String[] hFinParts = horaFin.split(":");
        cbmHoraFin.setValue(hFinParts[0]);
        cbmMinutosFin.setValue(hFinParts[1]);
        pacienteCitasQ.setText(paciente);
        contactoCitasQ.setText(contacto);
        fechaCitasQ.setValue(fechaCita.toLocalDate());
        medicoCitasQ.setText(medico);
        habitacionCitasQ.setValue(habitacion);
        quirofanoCitasQ.setValue(quirofano);
        tablaProcedimientoEditar(cirugia);
        fechaInternarCitasQ.setValue(fechaApartadoHab.toLocalDate());
        duracion = hDuracion;
        String[] hDuracionParts = hDuracion.split(":");
        lbmDuracionHora.setText(hDuracionParts[0]);
        lbmDuracionMinutos.setText(hDuracionParts[1]);
        this.idPaciente = idPaciente;
        consultaColocarQuirofano();
    }

    private void llenarTablaProcedimiento(String cirugia) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from procedimiento WHERE nombre ='" + cirugia + "'");
        Procedimiento procedimiento;
        try {
            while (rs.next()) {
                procedimiento = new Procedimiento();
                procedimiento.setId(rs.getInt(1));
                procedimiento.setNombre(rs.getString(2));
                procedimiento.setId_especialidad(rs.getInt(3));
                procedimiento.setTipo_procedimiento(rs.getString(6));

                procedimientos.add(procedimiento);
            }

            nombreProcedimiento.setCellValueFactory(new PropertyValueFactory("nombre"));
            colTipoProcedimiento.setCellValueFactory(new PropertyValueFactory("tipo_procedimiento"));
            tablaCirugia.setItems(procedimientos);

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void serviciosAdicionales(ActionEvent event) throws SQLException {
        String servicios = serviciosCitaQ.getText();
        llenarTablaServiciosAdicionales(servicios);
    }

    private void llenarTablaServiciosAdicionales(String ServiciosAdicionales) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from serviciosadicionales WHERE nombre ='" + ServiciosAdicionales + "'");
        ServicioAdicional servicioAdicional;
        try {
            while (rs.next()) {
                servicioAdicional = new ServicioAdicional();
                servicioAdicional.setId(rs.getInt(1));
                servicioAdicional.setNombre(rs.getString(2));
                servicioAdicional.setCosto(3);
                servicioAdicional.setPropietario(rs.getString(4));
                servicioAdicional.setMarca(rs.getString(5));
                servicioAdicional.setModelo(rs.getString(6));
                servicioAdicional.setDescripcion(rs.getString(7));
                servicioAdicionales.add(servicioAdicional);
            }

            NombreServicioAdicional.setCellValueFactory(new PropertyValueFactory("nombre"));
            tablaServiciosAdicionales.setItems(servicioAdicionales);

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public boolean quirofanoOcupado() throws SQLException {
        System.err.println("QirofanoOcupado");
        String citas = cirugiaCitasQ.getText();
        Date fechaCita = Date.valueOf(fechaCitasQ.getValue());
        String hInicio = cbmHoraInicio.getSelectionModel().getSelectedItem().toString() + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();

        boolean respuesta = false;

        CallableStatement stmt = null;
        String sql = "{call QuirofanoOcupado (?,?,?)}";
        stmt = con.prepareCall(sql);

        stmt.setString(1, citas);
        stmt.setDate(2, fechaCita);
        stmt.setString(3, hInicio);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            if (rs.getInt(3) == 5) {
                quirofanoConsulta = "";
                idQuirofanoConsulta = 0;
                respuesta = true;
            } else {
                quirofanoConsulta = rs.getString(1);
                idQuirofanoConsulta = rs.getInt(3);
                respuesta = rs.getString(2).equals("si");
            }

        }
        System.err.println(respuesta);
        return respuesta;
    }

    private String optenerIdQuirofano() throws SQLException {
        String citas = cirugiaCitasQ.getText();
        String hInicio = cbmHoraInicio.getSelectionModel().getSelectedItem().toString() + ":" + cbmMinutosInicio.getSelectionModel().getSelectedItem().toString();
        Date fechaCita = Date.valueOf(fechaCitasQ.getValue());
        String quirofano;
        String whileR = "SI";
        int idQuirfano = 0;
        ResultSet rs;
        Statement stmtQuirofno = con.createStatement();;
        CallableStatement stmt = null;
        String sqlQuirofano;
        while (whileR.equals("SI")) {
            sqlQuirofano = "{call SiguienteQuirofano (?,?,?,?)}";
            stmt = con.prepareCall(sqlQuirofano);
            stmt.setString(1, quirofanoConsulta);
            stmt.setString(2, citas);
            stmt.setString(3, hInicio);
            stmt.setDate(4, fechaCita);

            rs = stmt.executeQuery();
            if (rs.next()) {
                idQuirfano = rs.getInt(1);
                whileR = rs.getString(2);

                if (idQuirfano <= 4) {
                    rs = stmtQuirofno.executeQuery("SELECT nombre FROM quirofanos WHERE id = " + idQuirfano);
                    if (rs.next()) {
                        quirofanoConsulta = rs.getString(1);
                      
                    }
                }else{
                    whileR = "AQUI SOLO SE ESTA CUANDO SE ES 5";
                }

                
            } else {
               
                whileR = "NO";
            }
        }

        if (idQuirfano <= 4) {
            rs = stmtQuirofno.executeQuery("SELECT nombre FROM quirofanos WHERE id = " + idQuirfano);
            if (rs.next()) {
                quirofano = rs.getString(1);
            } else {
                quirofano = "";
            }
        } else {
            quirofano = "";
        }
        return quirofano;
    }

    private void agregarCuenta(String quirofano) throws SQLException {
        //Cambiar el metodo aqui para colocar el nuevo costo al editar o mantener
        String folioPaciente = "";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT folio FROM folios WHERE id_paciente = " + idPaciente + " AND id_estatus = 1");

        if (rs.next()) {
            folioPaciente = rs.getString(1);
        }

        CallableStatement stmtactualizar = null;
        String sqlact = "{call ActualizarQuirofanoConsumo (?,?,?)}";

        stmtactualizar = con.prepareCall(sqlact);
        stmtactualizar.setString(1, quirofano);
        stmtactualizar.setInt(2, idPaciente);
        stmtactualizar.setString(3, folioPaciente);
        stmtactualizar.execute();

        String sql = "{call ActualizarMontoFolioQuirofano(?,?)}";
        CallableStatement stmtfolio = con.prepareCall(sql);

        stmtfolio.setInt(1, userSystem);
        stmtfolio.setString(2, folioPaciente);
        stmtfolio.execute();

        alertaSuccess.setHeaderText("CUENTA ACTUALIZADA");
        alertaSuccess.setContentText("Monto actualizado");
        alertaSuccess.showAndWait();
    }

    private void tablaProcedimientoEditar(String arrayId) throws SQLException {
        Procedimiento procedimiento;
        Statement stmt = con.createStatement();

        String[] idpro = arrayId.split(",");

        for (String pro : idpro) {
            ResultSet rs = stmt.executeQuery("select * from procedimiento WHERE id =" + pro + "");
            while (rs.next()) {
                procedimiento = new Procedimiento();
                procedimiento.setId(rs.getInt(1));
                procedimiento.setNombre(rs.getString(2));
                procedimiento.setId_especialidad(rs.getInt(3));
                procedimiento.setTipo_procedimiento(rs.getString(6));
                if (procedimiento.getTipo_procedimiento().equals("AAA")) {
                    quirofanoCitasQ.setDisable(true);
                } else {
                    quirofanoCitasQ.setDisable(false);
                }
                procedimientos.add(procedimiento);
            }

        }
        nombreProcedimiento.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTipoProcedimiento.setCellValueFactory(new PropertyValueFactory("tipo_procedimiento"));
        tablaCirugia.setItems(procedimientos);

        tablaCirugia.setRowFactory(tableView -> {
            TableRow<Procedimiento> row = new TableRow<>();
            ContextMenu cxmCirugia = new ContextMenu();

            MenuItem descartarCirugia = new MenuItem("Descartar Cirugia");
            descartarCirugia.setOnAction(event -> {
                Procedimiento procedimineto = row.getItem();
                if (procedimineto.getTipo_procedimiento().equals("AAA")) {
                    quirofanoCitasQ.setDisable(false);
                }
                procedimientos.remove(procedimineto);
            });
            cxmCirugia.getItems().add(descartarCirugia);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(cxmCirugia)
            );
            return row;
        });
    }

    private void tablaServiciosAdicionalesEditar(String arrayId) throws SQLException {
        ServicioAdicional servicioAdicional;
        Statement stmt = con.createStatement();

        String[] idServiAdic = arrayId.split(",");

        for (String servi : idServiAdic) {
            ResultSet rs = stmt.executeQuery("select * from serviciosadicionales WHERE id =" + servi);
            while (rs.next()) {
                servicioAdicional = new ServicioAdicional();
                servicioAdicional.setId(rs.getInt(1));
                servicioAdicional.setNombre(rs.getString(2));
                servicioAdicional.setCosto(3);
                servicioAdicional.setPropietario(rs.getString(4));
                servicioAdicional.setMarca(rs.getString(5));
                servicioAdicional.setModelo(rs.getString(6));
                servicioAdicional.setDescripcion(rs.getString(7));
                servicioAdicionales.add(servicioAdicional);
            }

        }
        NombreServicioAdicional.setCellValueFactory(new PropertyValueFactory("nombre"));
        tablaServiciosAdicionales.setItems(servicioAdicionales);

        tablaServiciosAdicionales.setRowFactory(tableView -> {
            TableRow<ServicioAdicional> row = new TableRow<>();
            ContextMenu cxmCirugia = new ContextMenu();

            MenuItem descartarCirugia = new MenuItem("Descartar Servicio Adicional");
            descartarCirugia.setOnAction(event -> {
                ServicioAdicional sercicios = row.getItem();
                servicioAdicionales.remove(sercicios);
            });
            cxmCirugia.getItems().add(descartarCirugia);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(cxmCirugia)
            );
            return row;
        });
    }

    private void desactivarFechasPasadas() {
        fechaCitasQ.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        fechaCitasQ.valueProperty().addListener((observable, oldValue, newValue) -> {
            desactivarFechasInternarPasadasyFuturas();
        });
    }

    private void desactivarFechasInternarPasadasyFuturas() {
        fechaInternarCitasQ.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
                LocalDate futuro = fechaCitasQ.getValue();
                if (date.isAfter(futuro)) {
                    setDisable(true);
                }
            }
        });
    }
}
