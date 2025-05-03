/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.TipoInsumo;
import clases_hospital_DAO.InsumosDAO;
import clases_hospital_DAO.TipoInsumoDAO;
import controladores_hospital.InventarioController;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import reportes.ReporteC;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class ImprimirReporteController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    ObservableList<TipoInsumo> tipoinsumos = FXCollections.observableArrayList();
    TipoInsumoDAO tipoinsumodao;
    int idTipodeINSUMO;
    InsumosDAO insumodao;

    @FXML
    private ComboBox<TipoInsumo> cmbInventario;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnImprimir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarCombo();
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirReporteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarCombo() throws SQLException {
        tipoinsumodao = new TipoInsumoDAO(conexion.conectar2());
        tipoinsumos.addAll(tipoinsumodao.obtenerTodos());
        cmbInventario.setItems(tipoinsumos);

        cmbInventario.setOnAction(e -> {
            TipoInsumo tipoInsumo = cmbInventario.getValue();
            idTipodeINSUMO = tipoInsumo.getId();
       
        });
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionImprimir(ActionEvent event) {
        if (idTipodeINSUMO == 1) {
            ReporteC repc = new ReporteC("Rpt_ListadoExistencias_x_Tipo1");
            repc.generarReporteInsumos();
        } else if (idTipodeINSUMO == 2) {
            ReporteC repc = new ReporteC("Rpt_ListadoExistencias_x_Tipo1");
            repc.generarReporteInsumosAlimenticios();
        }
       

        Stage stage = (Stage) btnImprimir.getScene().getWindow();
        stage.close();
    }

}
