/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.*;
import clases_hospital_DAO.*;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import reportes.ReporteC;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class FoliosPacientesController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con; 
    
     Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    
    PacientesDAO pacienteDAO;
    
    int id_Paciente;
    
    ObservableList<Folio> Folios = FXCollections.observableArrayList();
    @FXML
    private Label lblNombre;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<Folio> tabla;
    @FXML
    private TableColumn<?, ?> colFolio;
    @FXML
    private TableColumn<?, ?> colFechaIngreso;
    @FXML
    private TableColumn<?, ?> colFechaEgreso;
    @FXML
    private TableColumn<?, ?> colSaldo;
    @FXML
    private TableColumn<?, ?> colAbonos;

    @FXML
    private TableColumn<?, ?> colAdeudo;
    @FXML
    private TableColumn<Folio, String> colImprimir;
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    
 private void llenarTabla(){
    pacienteDAO = new PacientesDAO(con);
    
    System.out.println("prueba 2");
    List<Folio> foliosList = pacienteDAO.TraerFoliosxIdPacientes(id_Paciente);
    if (foliosList == null) {
        System.out.println("foliosList es null");
    } else {
        System.out.println("foliosList tamaño: " + foliosList.size());
    }
    
    Folios.addAll(foliosList);
    System.out.println("prueba 4");
    
    if (Folios.isEmpty()) {
        System.out.println("Folios está vacío");
    } else {
        System.out.println("Folios contiene elementos");
    }
    
    colFolio.setCellValueFactory(new PropertyValueFactory("id"));
    colFechaEgreso.setCellValueFactory(new PropertyValueFactory("fecha_salidaString"));
    colFechaIngreso.setCellValueFactory(new PropertyValueFactory("fecha_ingresoString"));
    colSaldo.setCellValueFactory(new PropertyValueFactory("montoHastaElMomento"));
    colAbonos.setCellValueFactory(new PropertyValueFactory("totalDeAbono"));
    colAdeudo.setCellValueFactory(new PropertyValueFactory("adeudo"));
    generarBotones();
    System.out.println("prueba 5");
    
    tabla.setItems(Folios);
    System.out.println("prueba 6");
}
   private void generarBotones() {
        Callback<TableColumn<Folio, String>, TableCell<Folio, String>> imprimir = (TableColumn<Folio, String> param) -> {
            final TableCell<Folio, String> cell = new TableCell<Folio, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Folio folio = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-imprimir-30.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA PARA MEZCLAS
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Confirmación");
                            alertaConfirmacion.setContentText("¿Estas seguro ver la cuenta deo folio: " + folio.getFolio()+ " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                //aqui imprimir reporte
                                reporteDetallado(folio);
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

        colImprimir.setCellFactory(imprimir);
    }
    
    public void setObjeto(int id_paciente){
        System.out.println("prueba 1 ");
        this.id_Paciente = id_paciente;
        con = conexion.conectar2();
        llenarTabla();
    }
    
        private void reporteDetallado(Folio folio) {
        con = conexion.conectar2();
        NumerosALetras numeroLetras = new NumerosALetras(folio.getAdeudo() * 1.16);
        String numeroALetra = numeroLetras.getCantidadString();

        
            //AGARRA LA ULTIMA FECHA DE SALIDA
            ReporteC reporte = new ReporteC("Rpt_CorteDetalleCuenta_1_Alta");
            reporte.generarCorteDetalles1Alta(folio.getId(),
                    folio.getId_paquete(),
                    numeroALetra.toUpperCase(),
                    folio.getId_medico(),
                    folio.getId_habitacion(),
                    folio.getNumero_habitacion());
            
        
    }
    


    @FXML
    private void accionSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }
}
