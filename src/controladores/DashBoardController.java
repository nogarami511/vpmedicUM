/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.UsuarioDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class DashBoardController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane anchorPanel;
    @FXML
    private TitledPane tpContaduria;
    @FXML
    private Button btnSalir;
    Conexion connection = new Conexion();
    Connection con = connection.conectar2();
    @FXML
    private Button btnMinimizar;
    @FXML
    private JFXButton S;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenFondo();
        if (VPMedicaPlaza.userNameSystem.equals("SISTEMAS")) {
            tpContaduria.setVisible(true);
        }
    }

    @FXML
    private void accionCerrarSesion(ActionEvent event) throws SQLException {
        List<String> listanombres = new ArrayList<>();
        UsuarioDAO usuariodao = new UsuarioDAO(con);
        ConsumosDAO consumodao = new ConsumosDAO(con);
        listanombres = usuariodao.IndicasFaltantes(VPMedicaPlaza.userArea);

        if (listanombres.isEmpty()) {

            usuariodao.EstadoSesion(VPMedicaPlaza.userSystem, false);

            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();
        } else {

            StringBuilder contentText = new StringBuilder("Lista de nombres:\n");
            for (String nombre : listanombres) {
                contentText.append("- ").append(nombre).append("\n");
            }

            // Crear el cuadro de diálogo de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(" Tienes Indicas pendientes \n¿Quieres continuar?");
            alert.setContentText(contentText.toString());

            // Obtener la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();

            // Verificar la respuesta del usuario
            if (result.isPresent() && result.get() == ButtonType.OK) {

                // Hacer algo si el usuario seleccionó "Sí"
                consumodao.actualizarIndicasporarea(VPMedicaPlaza.userArea);
                usuariodao.EstadoSesion(VPMedicaPlaza.userSystem, false);
                Stage stage = (Stage) btnSalir.getScene().getWindow();
                stage.close();

            } else {

                // Hacer algo si el usuario seleccionó "No" o cerró el cuadro de diálogo
            }
        }

    }

    private void imagenFondo() {
        try {
            Image image = new Image("/img/dash.png");
            BackgroundImage backgroundImage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true));
            Background background = new Background(backgroundImage);
            anchorPanel.setBackground(background);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/" + page + ".fxml"));
        bp.setCenter(root);
    }

    private void loadPageHospital(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas_hospital/" + page + ".fxml"));
        bp.setCenter(root);
    }
    
    private void loadPageUM(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas_UM/"+ page + ".fxml"));
        bp.setCenter(root);
    }

    @FXML
    private void irPacientes(ActionEvent event) throws IOException {
        loadPage("Pacientes2");
    }

    @FXML
    private void irMedicos(ActionEvent event) throws IOException {
        loadPageHospital("Medicos");
    }

    @FXML
    private void irHabitaciones(ActionEvent event) throws IOException {
        loadPageHospital("Habitaciones");
    }

    @FXML
    private void irQuirofanos(ActionEvent event) throws IOException {
        loadPageHospital("Quirofanos");
    }

    private void irServiciosAdicionales(ActionEvent event) throws IOException {
        loadPageHospital("ServiciosAdicionales");
    }

    @FXML
    private void irProcedimientos(ActionEvent event) throws IOException {
        loadPageHospital("Procedimientos");
    }

    @FXML
    private void irInventario(ActionEvent event) throws IOException {
        loadPageHospital("Inventario");
    }

    private void actionVerHabitaciones(ActionEvent event) throws IOException {
        loadPageHospital("HabitacionesTabla");
    }

    @FXML
    private void irInsumo(ActionEvent event) throws IOException {
        loadPageHospital("Insumo");
    }

    private void irRedyLab(ActionEvent event) throws IOException {
        loadPageHospital("Redylab");
    }

    private void irRofer(ActionEvent event) throws IOException {
        loadPageHospital("Rofer");
    }

    @FXML
    private void irCitasQuirofanos(ActionEvent event) throws IOException {
        loadPageHospital("CitasQuirofanos");
    }

    private void actionVerAgendaHabitaciones(ActionEvent event) throws IOException {
        loadPageHospital("AgendaHabitacionesFXML");
    }

    @FXML
    private void irColaboradores(ActionEvent event) throws IOException {
        loadPage("Colaboradores");
    }

    @FXML
    private void irPuestos(ActionEvent event) throws IOException {
        loadPage("Puestos");
    }

    private void irCalculoNomina(ActionEvent event) throws IOException {
        loadPageHospital("NominaInterna");
    }

    @FXML
    private void irAutorizaryPagarNomina(ActionEvent event) throws IOException {
        loadPageHospital("AutorizaryPagarNominaTabla");
    }

    private void irIndicacionesMedicas(ActionEvent event) throws IOException {
        loadPageHospital("IndicacionesMedicas");
    }

    private void actionVerUrgencias(ActionEvent event) throws IOException {
        loadPageHospital("Urgencias");
    }

    private void irGenerarReabastos(ActionEvent event) throws IOException {
        loadPageHospital("GenerarReabastos");
    }

    private void irVerFolio(ActionEvent event) throws IOException {
        loadPageHospital("Folios");
    }

    @FXML
    private void irAutorizarReabastos(ActionEvent event) throws IOException {
        loadPageHospital("ComprasInsumos");
    }

    private void irPanelDeInformacionQuirofano(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas_hospital/AereopuertoQuirofano.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("AGENDA QUIROFANO");
        stage.setMaximized(true);
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void irMovimientoInventario(ActionEvent event) throws IOException {
        loadPageHospital("MovimientosInventario");
    }

    @FXML
    private void irCaja(ActionEvent event) throws IOException {
        loadPageHospital("CajaPago");
    }

    @FXML
    private void irDepositoEfectivo(ActionEvent event) throws IOException {
        loadPageHospital("DepositoEfectivo");
    }

    private void irDietas(ActionEvent event) {
        try {
            loadPageHospital("PaquetesAlimentos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irPaquetesMedicos(ActionEvent event) {
        try {
            loadPageHospital("PaquetesMedicos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void irInsumoAlimenticio(ActionEvent event) throws IOException {
        loadPageHospital("InsumosAlimenticios");
    }

    private void irVentas(ActionEvent event) throws IOException, IOException {
        loadPageHospital("FarmaciaVentas");
    }

    @FXML
    private void irRubro(ActionEvent event) throws IOException {
        loadPage("Rubros");
    }

    @FXML
    private void irPaquetesAlimenticios(ActionEvent event) throws IOException {
        loadPageHospital("PaquetesAlimentos");
    }

    @FXML
    private void irCostoHabitacion(ActionEvent event) throws IOException {
        loadPageHospital("HabitacionCostos");
    }

    @FXML
    private void irComandas(ActionEvent event) throws IOException {
        loadPageHospital("Comandas");
    }

    private void irDevolucion(ActionEvent event) throws IOException {
        loadPageHospital("DevolucionCirugia");
    }

    @FXML
    private void irAsiganacionHabitaciones(ActionEvent event) throws IOException {
        loadPageHospital("HabitacionesTabla");
    }

    @FXML
    private void irVerCuentaPaciente(ActionEvent event) throws IOException {
        loadPageHospital("CuentaPaciente2");
    }

    private void irConsumoPacienteTotal(ActionEvent event) throws IOException {
        loadPageHospital("ConsumoPacienteMesclas");
    }

    @FXML
    private void irAgendaHabitaciones(ActionEvent event) throws IOException {
        loadPageHospital("HabitacionesPaciente");
    }

    @FXML
    private void irProveedores(ActionEvent event) throws IOException {
        loadPage("Proveedores");
    }

    @FXML
    private void irCuentasxPagar(ActionEvent event) throws IOException {
        loadPageHospital("ComprasInternas");
    }

    @FXML
    private void irFondoEfectivo(ActionEvent event) throws IOException {
        loadPageHospital("FondoEfectivo");
    }

    @FXML
    private void irCXPComprasInternas(ActionEvent event) throws IOException {
        loadPageHospital("CientasxPagarComprasInternas");
    }

    @FXML
    private void irNominaInterna(ActionEvent event) throws IOException {
        loadPageHospital("NominaInterna");
    }

    @FXML
    private void irEstudiosMedicos(ActionEvent event) throws IOException {
        loadPageHospital("EstudiosMedicos");
    }

    @FXML
    private void irLaboratorios(ActionEvent event) throws IOException {
        loadPageHospital("Laboratorios");
    }

    @FXML
    private void irOrdenesEstudios(ActionEvent event) throws IOException {
        loadPageHospital("OrdenesEstudios");
    }

    @FXML
    private void irCuentasPorPAgarEstudiosMedicos(ActionEvent event) throws IOException {
        loadPageHospital("CuentasPorPagarEstudiosMedicos");
    }

    @FXML
    private void irComisionesPorPagos(ActionEvent event) throws IOException {
        loadPageHospital("ComisionesPorPago");
    }

    @FXML
    private void irCuentasPorPAgarExternas(ActionEvent event) throws IOException {//CientasxPagarExternas
        loadPageHospital("CuentasPorPagarInsumosPagos");
    }

    @FXML
    private void irUsuarios(ActionEvent event) throws IOException {
        loadPage("Usuarios");
    }

    @FXML
    private void irAlmacenes(ActionEvent event) throws IOException {
        loadPageHospital("Almacenes");
    }

    @FXML
    private void irCuentasxPagarExternas(ActionEvent event) throws IOException {
        loadPageHospital("ComprasExternasInsumos");
    }

    @FXML
    private void irEstadodecuenta(ActionEvent event) throws IOException {
        loadPageHospital("IngresosyEgresosHosptila");
    }

    @FXML
    private void irEstadodecuentaEgreso(ActionEvent event) throws IOException {
        loadPageHospital("EgresoseIngresosHosptila");
    }

    @FXML
    private void irCuemtaPaciente(ActionEvent event) throws IOException {
        loadPageHospital("AdeudoPaciente");
    }

    @FXML
    private void irAdeudo(ActionEvent event) throws IOException {
        loadPageHospital("AdeudoPaciente");
    }

    @FXML
    private void irIndicas(ActionEvent event) throws IOException {
        loadPageHospital("Indicas");
    }

    @FXML
    private void irConsumoPaciente(ActionEvent event) throws IOException {
        loadPageHospital("Suministro_insumos");
    }

    @FXML
    private void irOtrosIngresos(ActionEvent event) throws IOException {
        loadPageHospital("OtrosIngresos");
    }

    @FXML
    private void irAgregarConsumoPaciente2(ActionEvent event) throws IOException {
        loadPageHospital("Folios");
    }

    @FXML
    private void irFamilias(ActionEvent event) throws IOException {
        loadPageHospital("FamiliasInventario");
    }

    @FXML
    private void irFacturacion(ActionEvent event) throws IOException {
        loadPageHospital("FXMLFacturacion");
    }

    @FXML
    private void irActividadUsuarios(ActionEvent event) throws IOException {
        loadPageHospital("ActividadUsuarios");
    }

    @FXML
    private void irHemodinamia(ActionEvent event) throws IOException {
        loadPageHospital("Hemdinamia");
    }

    @FXML
    private void irFondoBanco(ActionEvent event) throws IOException {
        loadPageHospital("FondoBanco");
    }

    @FXML
    private void accionMinimizarVentana(ActionEvent event) {
        Stage stage = (Stage) btnMinimizar.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void irSuministroPacienteSistema(ActionEvent event) throws IOException {
        loadPageHospital("Suministro_insumosSISTEMAS");
    }

    @FXML
    private void irconcentradoIE(ActionEvent event) throws IOException {
        loadPageHospital("ConcentradoIngresosEgresos");
    }

    @FXML
    private void irGenerarDevolucion(ActionEvent event) throws IOException {
        loadPageHospital("DevolucionesdePagos");
    }

    @FXML
    private void irAgendadeQuirofano(ActionEvent event) throws IOException {
        loadPageHospital("AgendadeQuirofano");
    }

    @FXML
    private void irdeudasdelmes(ActionEvent event) throws IOException {
        loadPageHospital("AdeudoProveedores");
    }

    @FXML
    private void irConsumoDia(ActionEvent event) throws IOException {
        loadPageHospital("Consumo_Dia");
    }

    @FXML
    private void irCorteParcial(ActionEvent event) throws IOException {
        loadPageHospital("CorteCaja");
    }


    @FXML
    private void irComprasInsumosProveedor(ActionEvent event) throws IOException {
        loadPageHospital("ComprasInsumosProveedor");
    }

    @FXML
    private void irSeguros(ActionEvent event) throws IOException {
        loadPageHospital("CalculoSeguros");
    }

    @FXML
    private void irCXPInsumos(ActionEvent event) throws IOException {
        loadPageHospital("CXPInsumosSolicitudes");
    }

    @FXML
    private void irCenso(ActionEvent event) throws IOException {
        loadPageHospital("CensoFXML");
    }
    @FXML
    private void irIndicaPorHabitacion(ActionEvent event) throws IOException {
        loadPageHospital("IndicasPorHabitacion");
    }

    @FXML
    private void irAseguradora(ActionEvent event) throws IOException {
        loadPageHospital("Aseguradoras"); 
    }

    @FXML
    private void irVPHU(ActionEvent event) throws IOException {
        loadPageHospital("VPHU");
    }

    @FXML
    private void irTabuladores(ActionEvent event) throws IOException {
        loadPageUM("TabuladorCatalogo");
    }

}
