/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Colaborador;
import clase.Conexion;
import clases_hospital.CalculoNomina;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class NominaInternaController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    DateTimeFormatter formatterClave = DateTimeFormatter.ofPattern("ddMMyyyy");
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Connection conHopital = conexion.conectar2();
    ArrayList<Colaborador> listaColaborador = new ArrayList<>();
    ArrayList<Double> salariosPuesto = new ArrayList<>();
    ObservableList<CalculoNomina> calculosNominasInternas = FXCollections.observableArrayList();
    private LocalDate viernesAnterior, unDiaAntes;
    private double suma_pagos;
    private double suma_pagos_tarjeta;
    private double suma_pagos_efectivo;

    @FXML
    private DatePicker fechaFechaPago;
    @FXML
    private Label lblClaveNomina;
    @FXML
    private Label fechaFechaInicio;
    @FXML
    private Label fechaFechaFin;
    @FXML
    private Button btnCalcular;
    @FXML
    private TableView<CalculoNomina> tabla;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colSueldo;
    @FXML
    private TableColumn<CalculoNomina, Double> colPagoxHoraExtra;
    @FXML
    private TableColumn<CalculoNomina, Integer> colHorasExtra;
    @FXML
    private TableColumn colImporteHoraextra;
    @FXML
    private TableColumn<CalculoNomina, Double> colBono;
    @FXML
    private TableColumn<CalculoNomina, Integer> colFaltas;
    @FXML
    private TableColumn colImportancia;
    @FXML
    private TableColumn<CalculoNomina, Double> colAguinaldo;
    @FXML
    private TableColumn<CalculoNomina, Double> colFiniquiito;
    @FXML
    private TableColumn colPagoNeto;
    @FXML
    private Label lblTotal;
    @FXML
    private TableColumn<CalculoNomina, Double> colPagoConTarjeta;
    @FXML
    private Label lblTarjeta;
    @FXML
    private Label lblEfectivo;
    @FXML
    private JFXButton lblaux;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabla.setEditable(true);
        centrarTabla();
        generarClaveFechaInicioFin();
    }

    @FXML
    private void accionCalcualar(ActionEvent event) throws SQLException {
        String clave = lblClaveNomina.getText();
        String fechaInicio = "" + viernesAnterior;
        String fechaFin = "" + unDiaAntes;

        CallableStatement stmt = null;
        String callIngresarCalculoNomina = "{call ingresarCalculoNomina(?,?,?,?,?,?)}";

        if (existeClave()) {
            alertaSuccess.setHeaderText("NOMINA EXISTENTE");
            alertaSuccess.setContentText("La clave de la nómina ya fue calculada.");
            alertaSuccess.showAndWait();
        } else {
            llenarListaColaboradores();
            for (int i = 0; i < listaColaborador.size(); i++) {
                try {
                    stmt = conHopital.prepareCall(callIngresarCalculoNomina);
                    stmt.setString(1, listaColaborador.get(i).getId());
                    stmt.setString(2, clave);
                    stmt.setDouble(3, salariosPuesto.get(i));
                    System.out.println("i = " + i + " { id: " + listaColaborador.get(i).getId() + " SALARIO: ---> " + salariosPuesto.get(i) + " }");
                    stmt.setString(4, fechaInicio);
                    stmt.setString(5, fechaFin);
                    stmt.setInt(6, userSystem);
                    stmt.execute();

                } catch (Exception e) {
                    alertaError.setHeaderText("Error");
                    alertaError.setContentText("Error: al ingresar los datos");
                    alertaError.showAndWait();

                    e.printStackTrace(System.out);
                }

            }
            alertaConfirmacion.setTitle("Nómina Generada");
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setContentText("La nómina ha sido generada correctamente.");
            alertaConfirmacion.showAndWait();

            llenarTabla();
            generarAutorizacionDePagop(clave, fechaInicio, fechaFin);
            nominaAutorizada();
        }

    }

    private void generarClaveFechaInicioFin() {
        fechaFechaPago.setOnAction(event -> {
            LocalDate selectedDate = fechaFechaPago.getValue();
            if (selectedDate.getDayOfWeek() != DayOfWeek.FRIDAY) {
                alertaError.setTitle("Error");
                alertaError.setHeaderText(null);
                alertaError.setContentText("Por favor, seleccione un viernes");
                alertaError.showAndWait();
            } else {
                viernesAnterior = selectedDate.with(DayOfWeek.FRIDAY).minusWeeks(1);
                unDiaAntes = selectedDate.minusDays(1);
                fechaFechaInicio.setText(formatter.format(viernesAnterior));
                fechaFechaFin.setText(formatter.format(unDiaAntes));
                lblClaveNomina.setText(formatterClave.format(fechaFechaPago.getValue()));
                try {
                    if (existeClave()) {
                        llenarTabla();
                        nominaAutorizada();
                    } else {
                        limpiarTablaLista();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        });
    }

    private boolean existeClave() throws SQLException {
        conHopital = conexion.conectar2();
        String clave = lblClaveNomina.getText();
        Statement stmt = conHopital.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM nominasinternas WHERE clave_nomina = '" + clave + "';");
        boolean respuesta = rs.next();

        return respuesta;
    }

    private void llenarListaColaboradores() throws SQLException {
        this.listaColaborador.clear();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM colaboradores");
        Colaborador colaborador;

        while (rs.next()) {
            try {
                colaborador = new Colaborador();
                colaborador.setId(String.valueOf(rs.getInt(1)));
                colaborador.setNombre(rs.getString(2));
                colaborador.setNss(rs.getString(4));
                colaborador.setRfc(rs.getString(5));
                colaborador.setNacimiento(format.format(rs.getDate(3)));
                colaborador.setIngreso(format.format(rs.getDate(6)));
                colaborador.setPuesto(rs.getString(7));

                listaColaborador.add(colaborador);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
        for (int i = 0; i < listaColaborador.size(); i++) {
            System.out.println("i = " + i + " { id: " + listaColaborador.get(i).getId() + " nombre: " + listaColaborador.get(i).getNombre() + " }");
        }
        optenerSalarios();
    }

    private void optenerSalarios() throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT salario FROM puestos WHERE nombre = ? ");
        int j = 0;
        for (int i = 0; i < listaColaborador.size(); i++) {
            stmt.setString(1, listaColaborador.get(i).getPuesto());

            ResultSet rs = stmt.executeQuery();
            double salario;

            if (rs.next()) {
                salario = rs.getDouble(1);
                System.out.println("Puesto: " + listaColaborador.get(i).getPuesto() + "{ i = " + i + " j =  " + j + "}");
                salariosPuesto.add(salario);
            }
        }
    }

    private void llenarTabla() throws SQLException {
        conHopital = conexion.conectar2();
        DecimalFormat df = new DecimalFormat("0.00");
        this.tabla.getItems().clear();
        this.calculosNominasInternas.clear();

        String clave = lblClaveNomina.getText();

        Statement stmt = conHopital.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM nominasinternas WHERE clave_nomina = '" + clave + "'");

        CalculoNomina calculonomina;

        try {
            while (rs.next()) {
                calculonomina = new CalculoNomina();

                calculonomina.setId(rs.getInt(1));
                calculonomina.setClave(rs.getString(2));
                calculonomina.setId_colaborador(rs.getInt(3));
                calculonomina.setNombreColaborador(nombrePaciente(rs.getInt(3)));
                calculonomina.setSueldo_semanal(rs.getDouble(4));
                calculonomina.setPago_hora_extra(rs.getDouble(5));
                calculonomina.setCantidad_hora_extra(rs.getInt(6));
                calculonomina.setImporte_hora_extra(rs.getDouble(7));
                calculonomina.setBono(rs.getDouble(8));
                calculonomina.setFaltas(rs.getInt(9));
                calculonomina.setImporte_por_faltas(rs.getDouble(10));
                calculonomina.setPago_tarjeta(rs.getDouble("uniforme_PAGO_TARJETA"));
                calculonomina.setAguinaldo(rs.getDouble(16));
                calculonomina.setPago_finiquito(rs.getDouble(17));
                calculonomina.setPago_neto(rs.getDouble(19));
                calculosNominasInternas.add(calculonomina);
//                id_estatus_nomina int(11) NOT NULL;                           24
            }

            colNombre.setCellValueFactory(new PropertyValueFactory("nombreColaborador"));
            colSueldo.setCellValueFactory(new PropertyValueFactory("sueldo_semanal"));
            colPagoxHoraExtra.setCellValueFactory(new PropertyValueFactory("pago_hora_extra"));
            colPagoxHoraExtra.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colPagoxHoraExtra.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                CalculoNomina nompay = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                nompay.setPago_hora_extra(event.getNewValue());

                nompay.setImporte_hora_extra(Double.parseDouble(df.format(nompay.getPago_hora_extra() * nompay.getCantidad_hora_extra())));
                nompay.setPago_neto(Double.parseDouble(df.format((nompay.getImporte_hora_extra() + nompay.getBono() + nompay.getAguinaldo() + nompay.getPago_finiquito() + nompay.getSueldo_semanal()) - nompay.getImporte_por_faltas())));

                suma_pagos = 0.0;
                suma_pagos_tarjeta = 0.0;
                tabla.getItems().forEach((item) -> {
                    suma_pagos += item.getPago_neto();
                    suma_pagos_tarjeta += item.getPago_tarjeta();
                });
                suma_pagos_efectivo = suma_pagos - suma_pagos_tarjeta;
                lblEfectivo.setText("$ " + suma_pagos_efectivo);
                lblTarjeta.setText("$ " + suma_pagos_tarjeta);
                lblTotal.setText("$ " + df.format(suma_pagos));

                actualizarNominaInternaa(nompay.getId(), nompay.getClave(), nompay.getPago_hora_extra(), nompay.getCantidad_hora_extra(), nompay.getImporte_hora_extra(), nompay.getBono(), nompay.getFaltas(), nompay.getImporte_por_faltas(), nompay.getAguinaldo(), nompay.getPago_finiquito(), nompay.getPago_neto(), nompay.getPago_tarjeta());
                actualizarAutorizacionDePago(lblClaveNomina.getText());
                tabla.refresh();
            });
            colHorasExtra.setCellValueFactory(new PropertyValueFactory("cantidad_hora_extra"));
            colHorasExtra.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colHorasExtra.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                CalculoNomina nompay = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                nompay.setCantidad_hora_extra(event.getNewValue());
                //
                nompay.setImporte_hora_extra(Double.parseDouble(df.format(nompay.getPago_hora_extra() * nompay.getCantidad_hora_extra())));
                nompay.setPago_neto(Double.parseDouble(df.format((nompay.getImporte_hora_extra() + nompay.getBono() + nompay.getAguinaldo() + nompay.getPago_finiquito() + nompay.getSueldo_semanal()) - nompay.getImporte_por_faltas())));

                suma_pagos = 0.0;
                suma_pagos_tarjeta = 0.0;
                tabla.getItems().forEach((item) -> {
                    suma_pagos += item.getPago_neto();
                    suma_pagos_tarjeta += item.getPago_tarjeta();
                });
                suma_pagos_efectivo = suma_pagos - suma_pagos_tarjeta;
                lblEfectivo.setText("$ " + suma_pagos_efectivo);
                lblTarjeta.setText("$ " + suma_pagos_tarjeta);
                lblTotal.setText("$ " + df.format(suma_pagos));

                actualizarNominaInternaa(nompay.getId(), nompay.getClave(), nompay.getPago_hora_extra(), nompay.getCantidad_hora_extra(), nompay.getImporte_hora_extra(), nompay.getBono(), nompay.getFaltas(), nompay.getImporte_por_faltas(), nompay.getAguinaldo(), nompay.getPago_finiquito(), nompay.getPago_neto(), nompay.getPago_tarjeta());
                actualizarAutorizacionDePago(lblClaveNomina.getText());
                tabla.refresh();
            });
            colImporteHoraextra.setCellValueFactory(new PropertyValueFactory("importe_hora_extra"));
            colBono.setCellValueFactory(new PropertyValueFactory("bono"));
            colBono.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colBono.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                CalculoNomina nompay = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                nompay.setBono(event.getNewValue());

                nompay.setPago_neto(Double.parseDouble(df.format((nompay.getImporte_hora_extra() + nompay.getBono() + nompay.getAguinaldo() + nompay.getPago_finiquito() + nompay.getSueldo_semanal()) - nompay.getImporte_por_faltas())));

                suma_pagos = 0.0;
                suma_pagos_tarjeta = 0.0;
                tabla.getItems().forEach((item) -> {
                    suma_pagos += item.getPago_neto();
                    suma_pagos_tarjeta += item.getPago_tarjeta();
                });
                suma_pagos_efectivo = suma_pagos - suma_pagos_tarjeta;
                lblEfectivo.setText("$ " + suma_pagos_efectivo);
                lblTarjeta.setText("$ " + suma_pagos_tarjeta);
                lblTotal.setText("$ " + df.format(suma_pagos));

                actualizarNominaInternaa(nompay.getId(), nompay.getClave(), nompay.getPago_hora_extra(), nompay.getCantidad_hora_extra(), nompay.getImporte_hora_extra(), nompay.getBono(), nompay.getFaltas(), nompay.getImporte_por_faltas(), nompay.getAguinaldo(), nompay.getPago_finiquito(), nompay.getPago_neto(), nompay.getPago_tarjeta());
                actualizarAutorizacionDePago(lblClaveNomina.getText());
                tabla.refresh();
            });
            colFaltas.setCellValueFactory(new PropertyValueFactory("faltas"));
            colFaltas.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colFaltas.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                CalculoNomina nompay = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                nompay.setFaltas(event.getNewValue());

                nompay.setImporte_por_faltas(Double.parseDouble(df.format((nompay.getSueldo_semanal() / 7) * nompay.getFaltas())));
                nompay.setPago_neto(Double.parseDouble(df.format((nompay.getImporte_hora_extra() + nompay.getBono() + nompay.getAguinaldo() + nompay.getPago_finiquito() + nompay.getSueldo_semanal()) - nompay.getImporte_por_faltas())));

                suma_pagos = 0.0;
                suma_pagos_tarjeta = 0.0;
                tabla.getItems().forEach((item) -> {
                    suma_pagos += item.getPago_neto();
                    suma_pagos_tarjeta += item.getPago_tarjeta();
                });
                suma_pagos_efectivo = suma_pagos - suma_pagos_tarjeta;
                lblEfectivo.setText("$ " + suma_pagos_efectivo);
                lblTarjeta.setText("$ " + suma_pagos_tarjeta);
                lblTotal.setText("$ " + df.format(suma_pagos));

                actualizarNominaInternaa(nompay.getId(), nompay.getClave(), nompay.getPago_hora_extra(), nompay.getCantidad_hora_extra(), nompay.getImporte_hora_extra(), nompay.getBono(), nompay.getFaltas(), nompay.getImporte_por_faltas(), nompay.getAguinaldo(), nompay.getPago_finiquito(), nompay.getPago_neto(), nompay.getPago_tarjeta());
                actualizarAutorizacionDePago(lblClaveNomina.getText());
                tabla.refresh();
            });
            colImportancia.setCellValueFactory(new PropertyValueFactory("importe_por_faltas"));
            colPagoConTarjeta.setCellValueFactory(new PropertyValueFactory("pago_tarjeta"));
            colPagoConTarjeta.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colPagoConTarjeta.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                CalculoNomina nompay = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                nompay.setPago_tarjeta(event.getNewValue());

                suma_pagos = 0.0;
                suma_pagos_tarjeta = 0.0;
                tabla.getItems().forEach((item) -> {
                    suma_pagos += item.getPago_neto();
                    suma_pagos_tarjeta += item.getPago_tarjeta();
                });
                suma_pagos_efectivo = suma_pagos - suma_pagos_tarjeta;
                lblEfectivo.setText("$ " + suma_pagos_efectivo);
                lblTarjeta.setText("$ " + suma_pagos_tarjeta);
                lblTotal.setText("$ " + df.format(suma_pagos));

                actualizarNominaInternaa(nompay.getId(), nompay.getClave(), nompay.getPago_hora_extra(), nompay.getCantidad_hora_extra(), nompay.getImporte_hora_extra(), nompay.getBono(), nompay.getFaltas(), nompay.getImporte_por_faltas(), nompay.getAguinaldo(), nompay.getPago_finiquito(), nompay.getPago_neto(), nompay.getPago_tarjeta());
                actualizarAutorizacionDePago(lblClaveNomina.getText());
                tabla.refresh();
            });
            colAguinaldo.setCellValueFactory(new PropertyValueFactory("aguinaldo"));
            colAguinaldo.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colAguinaldo.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                CalculoNomina nompay = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                nompay.setAguinaldo(event.getNewValue());

                nompay.setPago_neto(Double.parseDouble(df.format((nompay.getImporte_hora_extra() + nompay.getBono() + nompay.getAguinaldo() + nompay.getPago_finiquito() + nompay.getSueldo_semanal()) - nompay.getImporte_por_faltas())));

                suma_pagos = 0.0;
                suma_pagos_tarjeta = 0.0;
                tabla.getItems().forEach((item) -> {
                    suma_pagos += item.getPago_neto();
                    suma_pagos_tarjeta += item.getPago_tarjeta();
                });
                suma_pagos_efectivo = suma_pagos - suma_pagos_tarjeta;
                lblEfectivo.setText("$ " + suma_pagos_efectivo);
                lblTarjeta.setText("$ " + suma_pagos_tarjeta);
                lblTotal.setText("$ " + df.format(suma_pagos));

                actualizarNominaInternaa(nompay.getId(), nompay.getClave(), nompay.getPago_hora_extra(), nompay.getCantidad_hora_extra(), nompay.getImporte_hora_extra(), nompay.getBono(), nompay.getFaltas(), nompay.getImporte_por_faltas(), nompay.getAguinaldo(), nompay.getPago_finiquito(), nompay.getPago_neto(), nompay.getPago_tarjeta());
                actualizarAutorizacionDePago(lblClaveNomina.getText());
                tabla.refresh();
            });
            colFiniquiito.setCellValueFactory(new PropertyValueFactory("pago_finiquito"));
            colFiniquiito.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colFiniquiito.setOnEditCommit(event -> {
                // obtener el objeto Consumo que está siendo editado
                CalculoNomina nompay = event.getTableView().getItems().get(event.getTablePosition().getRow());
                // actualizar el valor de cantidad en el objeto Consumo
                nompay.setPago_finiquito(event.getNewValue());

                nompay.setPago_neto(Double.parseDouble(df.format((nompay.getImporte_hora_extra() + nompay.getBono() + nompay.getAguinaldo() + nompay.getPago_finiquito() + nompay.getSueldo_semanal()) - nompay.getImporte_por_faltas())));

                suma_pagos = 0.0;
                suma_pagos_tarjeta = 0.0;
                tabla.getItems().forEach((item) -> {
                    suma_pagos += item.getPago_neto();
                    suma_pagos_tarjeta += item.getPago_tarjeta();
                });
                suma_pagos_efectivo = suma_pagos - suma_pagos_tarjeta;
                lblEfectivo.setText("$ " + suma_pagos_efectivo);
                lblTarjeta.setText("$ " + suma_pagos_tarjeta);
                lblTotal.setText("$ " + df.format(suma_pagos));

                actualizarNominaInternaa(nompay.getId(), nompay.getClave(), nompay.getPago_hora_extra(), nompay.getCantidad_hora_extra(), nompay.getImporte_hora_extra(), nompay.getBono(), nompay.getFaltas(), nompay.getImporte_por_faltas(), nompay.getAguinaldo(), nompay.getPago_finiquito(), nompay.getPago_neto(), nompay.getPago_tarjeta());
                actualizarAutorizacionDePago(lblClaveNomina.getText());
                tabla.refresh();
            });
            colPagoNeto.setCellValueFactory(new PropertyValueFactory("pago_neto"));

            colPagoConTarjeta.setEditable(true);
            colPagoxHoraExtra.setEditable(true);
            colHorasExtra.setEditable(true);
            colBono.setEditable(true);
            colFaltas.setEditable(true);
            colAguinaldo.setEditable(true);
            colFiniquiito.setEditable(true);

            System.err.println("HOLA MUNDO");

            tabla.setItems(calculosNominasInternas);

            suma_pagos = 0.0;
            suma_pagos_tarjeta = 0.0;
            tabla.getItems().forEach((item) -> {
                suma_pagos += item.getPago_neto();
                suma_pagos_tarjeta += item.getPago_tarjeta();
            });
            suma_pagos_efectivo = suma_pagos - suma_pagos_tarjeta;
            lblEfectivo.setText("$ " + df.format(suma_pagos_efectivo));
            lblTarjeta.setText("$ " + df.format(suma_pagos_tarjeta));
            lblTotal.setText("$ " + df.format(suma_pagos));
            String fechaInicio = "" + viernesAnterior;
            String fechaFin = "" + unDiaAntes;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void limpiarTablaLista() {
        this.tabla.getItems().clear();
        this.calculosNominasInternas.clear();
        lblTotal.setText("");
    }

    private void actualizarNominaInternaa(int id, String clave, double horaextra, int cantidadhoraextra, double importehoraextra, double bono, int faltas, double importeporfaltas, double aguinaldo, double finiquito, double pagoneto, double pago_tarjeta) {
        conHopital = conexion.conectar2();
        CallableStatement stmt = null;
//       String sql = "{call actualizarCalculoNominaInterna(?,?,?,?,?,?,?,?,?,?,?,?)}";
         String sql = "{call actualizarCalculoNominaInterna(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

        try {
            stmt = conHopital.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setString(2, clave);
            stmt.setDouble(3, horaextra);
            stmt.setInt(4, cantidadhoraextra);
            stmt.setDouble(5, importehoraextra);
            stmt.setDouble(6, bono);
            stmt.setInt(7, faltas);
            stmt.setDouble(8, importeporfaltas);
            stmt.setDouble(9, aguinaldo);
            stmt.setDouble(10, finiquito);
            stmt.setDouble(11, pagoneto);
            stmt.setInt(12, userSystem);
             stmt.setDouble(13, pago_tarjeta);

            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private String nombrePaciente(int idColaborador) throws SQLException {
        String paciente = "";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nombre FROM colaboradores WHERE id = '" + idColaborador + "'");

        if (rs.next()) {
            paciente = rs.getString(1);
        }

        return paciente;
    }

    private void centrarTabla() {
        colNombre.setStyle("-fx-alignment: CENTER;");
        colSueldo.setStyle("-fx-alignment: CENTER;");
        colPagoxHoraExtra.setStyle("-fx-alignment: CENTER;");
        colHorasExtra.setStyle("-fx-alignment: CENTER;");
        colImporteHoraextra.setStyle("-fx-alignment: CENTER;");
        colBono.setStyle("-fx-alignment: CENTER;");
        colFaltas.setStyle("-fx-alignment: CENTER;");
        colImportancia.setStyle("-fx-alignment: CENTER;");
        colAguinaldo.setStyle("-fx-alignment: CENTER;");
        colFiniquiito.setStyle("-fx-alignment: CENTER;");
        colPagoNeto.setStyle("-fx-alignment: CENTER;");
    }

    private void generarAutorizacionDePagop(String clave, String fechainicio, String fechafin) {
        conHopital = conexion.conectar2();
        CallableStatement stmt;
        String callsql = "{call generarautorizaciondepagoestatuscalculado (?,?,?,?)}";

        try {
            stmt = conHopital.prepareCall(callsql);
            stmt.setString(1, clave);
            stmt.setString(2, fechainicio);
            stmt.setString(3, fechafin);
            stmt.setInt(4, userSystem);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void actualizarAutorizacionDePago(String clave) {
        conHopital = conexion.conectar2();
        CallableStatement stmt;
        String callsql = "{call actualizarautorizarypagosnominas (?,?,?)}";

        try {

            stmt = conHopital.prepareCall(callsql);
            stmt.setString(1, clave);
            stmt.setDouble(2, suma_pagos);
            stmt.setInt(3, userSystem);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void nominaAutorizada() throws SQLException {
        conHopital = conexion.conectar2();
        boolean respuesta;
        if (existeClave()) {
            String clave = lblClaveNomina.getText();
            Statement stmt = conHopital.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM autorizarypagosnominas WHERE clavenomina = '" + clave + "' AND estatus = 0;");
            respuesta = rs.next();

        } else {
            respuesta = true;
        }

        colPagoxHoraExtra.setEditable(true);
        colHorasExtra.setEditable(true);
        colBono.setEditable(true);
        colFaltas.setEditable(true);
        colAguinaldo.setEditable(true);
        colFiniquiito.setEditable(true);
    }

}
