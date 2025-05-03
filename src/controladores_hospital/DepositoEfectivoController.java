/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.FondoEfectivo;
import clases_hospital.Fondocuentabanco;
import clases_hospital.Pagos;
import clases_hospital_DAO.FondoEfectivoDAO;
import clases_hospital_DAO.FondoEfectivoFijoDAO;
import clases_hospital_DAO.FondocuentabancoDAO;
import clases_hospital_DAO.PagosDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class DepositoEfectivoController implements Initializable {

    @FXML
    private BorderPane bpPagos;
    @FXML
    private TextField txfBuscar;
    @FXML
    private TextField txtmonto;
    @FXML
    private Button btnGenerarDeposito;
    @FXML
    private TableView<Pagos> tabla;
    @FXML
    private TableColumn<?, ?> folio;
    @FXML
    private TableColumn<?, ?> colPaciente;
    @FXML
    private TableColumn<?, ?> fechapago;
    @FXML
    private TableColumn<?, ?> colPagar;
    @FXML
    private Label lblMonto;

    private ObservableList<Pagos> listapagos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();//clase conexion creada en el paquete clase
    Connection con = conexion.conectar2();//clase default de java
    Pagos pagogeneral = new Pagos();
    @FXML
    private AnchorPane panemergente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            llenartabla();
            txfBuscar.setOnKeyReleased(e -> filtrarLista(txfBuscar.getText()));
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(DepositoEfectivoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionGenerarDeposito(ActionEvent event) {
        panemergente.setVisible(true);
        //   Pagos pago = ;
        pagogeneral = tabla.getSelectionModel().getSelectedItem();
        lblMonto.setText("$ " + tabla.getSelectionModel().getSelectedItem().getTotal_pago());

    }
    @FXML
    private void accioncancelar(ActionEvent event) {
        panemergente.setVisible(false);
        //   Pagos pago = ;
   

    }

    @FXML
    private void acciondeposito(ActionEvent event) throws SQLException {
        PagosDAO pagosDAO = new PagosDAO(con);
        FondocuentabancoDAO fondobancoDAO = new FondocuentabancoDAO(con);
        FondoEfectivoDAO fondoefectivoDAO = new FondoEfectivoDAO(con);
        Fondocuentabanco fondobanco =  new Fondocuentabanco();
        FondoEfectivo fondoefectivo = new FondoEfectivo();
        FondoEfectivoFijoDAO fondoEfectivoFijoDAO = new FondoEfectivoFijoDAO(con);
        String respuesta ="";
        double monto = 0.0, montobancofijo = 0.0, montoefectivofijo = 0.0;
        
        fondoefectivo.traerFondoEfectivoFijo();
        montoefectivofijo = fondoefectivo.getTotalFondoEfectivoFijo();
        montobancofijo = fondobancoDAO.fondocuentabancofijo();
        
        
        try {
            
            if (txtmonto.getText().isEmpty()){
                monto = 0.0;
            }
            else{
                monto = Double.parseDouble(txtmonto.getText());
                 
            }
         

            // Puedes realizar otras operaciones con el double aquí
        } catch (NumberFormatException e) {
            
          //  e.printStackTrace(); // Opcional: Imprimir la traza de la excepción para obtener más detalles
            respuesta = "es un dato no valido";
        }
        
        if (respuesta == "es un dato no valido"){
            
        }else{
            respuesta = pagosDAO.DepositoEfectivo(pagogeneral.getId_pago(), monto, VPMedicaPlaza.userSystem);
            // LLENO EL FONDOCUENTAABANCO
            fondobanco.setTipoOperacion("INGRESO");
            fondobanco.setImporte(monto);
            fondobanco.setSaldo(montobancofijo);
            fondobanco.setConcepto("DEPOSITO DE EFECTIVO "+pagogeneral.getNombrePaciente());
            fondobanco.setIdCliente(pagogeneral.getId_pago());
            // INSERTO A CUENTA BANCO
            fondobancoDAO.crearFondoCuentaBanco(fondobanco);
            
            //LLENO FONDO EFECTIVO
            fondoefectivo.setTipoOperacion("EGRESO");
            fondoefectivo.setImporte(monto);
            fondoefectivo.setSaldo(montoefectivofijo);
            fondoefectivo.setConcepto("DEPOSITO DE EFECTIVO "+ pagogeneral.getNombrePaciente());
            fondoefectivo.setIdCliente(pagogeneral.getId_pago());
            fondoefectivoDAO.insertarFondoEfectivo(fondoefectivo);
            
            
            // ACTUALIZO VALORES DE MONTOS FIJOS
            montobancofijo = montobancofijo + monto;
            montoefectivofijo = montoefectivofijo - monto;
            
            // MANDO LOS NUEVOS VALORES
            
            fondobancoDAO.actualizarfindobancofijo(montobancofijo);
            fondoEfectivoFijoDAO.actualizarFondoEfectivoFijo(1, montoefectivofijo);
            
            
            
        }
        
       
        panemergente.setVisible(false);
      
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Ejecucion de Operación");
        alerta.setHeaderText(null); // Para evitar el encabezado predeterminado
        alerta.setContentText(respuesta);

        // Puedes personalizar más la alerta si es necesario
        // alerta.initOwner(ventanaPrincipal);

        alerta.showAndWait(); // Muestra la alerta y espera hasta que el usuario la cierre
        txtmonto.clear();
        listapagos.clear();
        llenartabla();
    }
    

    private void llenartabla() throws SQLException {

        PagosDAO pagosDAO = new PagosDAO(con);

        listapagos.addAll(pagosDAO.obtenerPagosPacientes());

        folio.setCellValueFactory(new PropertyValueFactory("Id_pago"));
        colPaciente.setCellValueFactory(new PropertyValueFactory("NombrePaciente"));
        fechapago.setCellValueFactory(new PropertyValueFactory("Fecha_pago"));
        colPagar.setCellValueFactory(new PropertyValueFactory("Total_pago"));

        tabla.setItems(listapagos);
    }

    private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(listapagos);
        } else {
            ObservableList<Pagos> listaFiltrada = FXCollections.observableArrayList();
            for (Pagos pagos : listapagos) {
                if (pagos.getNombrePaciente().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(pagos);
                }
            }
            tabla.setItems(listaFiltrada);
        }
    }

    @FXML
    private void restamomentanea(KeyEvent event) {
         double monto = 0.0, total_parcial = 0.0;

        try {
            
            if (txtmonto.getText().isEmpty()){
                monto = 0.0;
            }
            else{
                monto = Double.parseDouble(txtmonto.getText());
                 
            }
         

            // Puedes realizar otras operaciones con el double aquí
        } catch (NumberFormatException e) {
            System.err.println("La cadena no es un double válido");
          //  e.printStackTrace(); // Opcional: Imprimir la traza de la excepción para obtener más detalles
         //   respuesta = "es un dato no valido";
        }
        total_parcial = tabla.getSelectionModel().getSelectedItem().getTotal_pago() - monto;
        
        lblMonto.setText("$ " + total_parcial);
        
        
        
        
    }

}
