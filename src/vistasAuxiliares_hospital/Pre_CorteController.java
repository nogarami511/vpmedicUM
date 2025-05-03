/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.CorteCaja;
import clases_hospital_DAO.CorteCajaDAO;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import reportes.ReporteC;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Pre_CorteController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);

    Conexion conexion = new Conexion();
    Connection con;

    @FXML
    private Label lbl_efectivo;
    @FXML
    private Label lbl_banco;
    @FXML
    private Button validar;
    @FXML
    private TextField txt_efectivo;
    @FXML
    private TextField txt_banco;

    double dao_banco, dao_efectivo;
    @FXML
    private Button btn_Salir;

    int idUsuario;
    int idCorteCaja;
    CorteCaja cortecaja;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = conexion.conectar2();

    }

    public void setDatos(CorteCaja cortecaja) {
        System.out.println(cortecaja.toString());
        this.idUsuario = cortecaja.getId_usuario();
        this.idCorteCaja = cortecaja.getId_corte_caja();
        this.cortecaja = cortecaja;
        
        llenardatos();
    }

    private void llenardatos() {
//        --DecimalFormta df =  new De

        try (CallableStatement stm = con.prepareCall("call PRE_CORTE(?,?)")) {
            stm.setInt(1, idUsuario);
            stm.setInt(2, idCorteCaja);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if (rs.next()) {
                dao_efectivo = rs.getDouble("efectivo");
                dao_banco = rs.getDouble("banco");
                
                System.out.println("" + rs.getDouble("efectivo"));
                System.out.println("" + rs.getDouble("banco"));
                        
            }

        } catch (SQLException e) {
            e.getErrorCode();
            e.printStackTrace();
            System.out.println("" + e.getCause());

        }
        
        lbl_banco.setText("$ " + dao_banco);
        lbl_efectivo.setText("$ " + dao_efectivo);
    }

    @FXML
    private void accionvalidar(ActionEvent event) {
        CorteCajaDAO corteDAO = new CorteCajaDAO(con);
       // llenardatos();
        double efect_ini = 0;

        double efectivo = 0, banco = 0;
        try {
            efectivo = Double.parseDouble(txt_efectivo.getText());
            banco = Double.parseDouble(txt_banco.getText());
            // Los valores son válidos como números decimales.

        } catch (NumberFormatException e) {
            // Si ocurre una excepción, significa que al menos uno de los valores no es un número decimal válido.
            alertaError.setHeaderText("Algo salio mal");
            alertaError.setContentText("ALGUNO DE LOS NUMEROS NO ES UN VALOR VALIDO");
            alertaError.showAndWait();
            e.printStackTrace(); // Esto imprime la traza de la excepción para depuración.
        }

        if (dao_banco == banco && dao_efectivo == efectivo) {
            ReporteC reporte = new ReporteC("hola mundo");
            corteDAO.ValidarCorte(cortecaja);
            System.out.println("" + cortecaja.getId_usuario()+ cortecaja.getId_corte_caja());
            reporte.generarReportePRE_CORTE("" + cortecaja.getId_usuario(),""+ cortecaja.getId_corte_caja());
            alertaSuccess.setHeaderText("Validacion Exitosa");
            alertaSuccess.setContentText("La validacion fue exitosa");
            alertaSuccess.showAndWait();
            Stage stage = (Stage) validar.getScene().getWindow();
            stage.close();

        } else if (dao_banco > banco) {
            alertaError.setHeaderText("Algo salio mal");
            alertaError.setContentText("hacen falta pagos que generar en banco");
            alertaError.showAndWait();
        } else if (dao_efectivo > efectivo) {
            alertaError.setHeaderText("Algo salio mal");
            alertaError.setContentText("hacen falta pagos que generar en efectivo");
            alertaError.showAndWait();

        } else if (dao_banco < banco) {
            alertaError.setHeaderText("Algo salio mal");
            alertaError.setContentText("Se generaron pagos extra de banco");
            alertaError.showAndWait();
        } else if (dao_efectivo < efectivo) {
            alertaError.setHeaderText("Algo salio mal");
            alertaError.setContentText("Se generaron pagos extra de efectivo");
            alertaError.showAndWait();
        }

    }

    @FXML
    private void accionsalir(ActionEvent event) {
        Stage stage = (Stage) btn_Salir.getScene().getWindow();
        stage.close();
    }

}
