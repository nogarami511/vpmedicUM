/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clases_hospital.Area;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class DashBoard_SegurosController implements Initializable {
    
    Conexion connection = new Conexion();
    Connection con = connection.conectar2();
    private ObservableList<Area> areaOBL = FXCollections.observableArrayList(); ;

    @FXML
    private BorderPane bp;
    @FXML
    private TitledPane tpContaduria;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnMinimizar;
    @FXML
    private AnchorPane anchorPanel;

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

    private void irCitasQuirofanos(ActionEvent event) throws IOException {
        loadPageHospital("CitasQuirofanos");
    }

    private void actionVerAgendaHabitaciones(ActionEvent event) throws IOException {
        loadPageHospital("AgendaHabitacionesFXML");
    }

    private void irColaboradores(ActionEvent event) throws IOException {
        loadPage("Colaboradores");
    }

    private void irPuestos(ActionEvent event) throws IOException {
        loadPage("Puestos");
    }

    private void irCalculoNomina(ActionEvent event) throws IOException {
        loadPageHospital("NominaInterna");
    }

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

    private void irCaja(ActionEvent event) throws IOException {
        loadPageHospital("CajaPago");
    }

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

    private void irProveedores(ActionEvent event) throws IOException {
        loadPage("Proveedores");
    }

    private void irCuentasxPagar(ActionEvent event) throws IOException {
        loadPageHospital("ComprasInternas");
    }

    private void irFondoEfectivo(ActionEvent event) throws IOException {
        loadPageHospital("FondoEfectivo");
    }

    private void irCXPComprasInternas(ActionEvent event) throws IOException {
        loadPageHospital("CientasxPagarComprasInternas");
    }

    private void irNominaInterna(ActionEvent event) throws IOException {
        loadPageHospital("NominaInterna");
    }

    private void irEstudiosMedicos(ActionEvent event) throws IOException {
        loadPageHospital("EstudiosMedicos");
    }

    @FXML
    private void irLaboratorios(ActionEvent event) throws IOException {
        loadPageHospital("Laboratorios");
    }

    private void irOrdenesEstudios(ActionEvent event) throws IOException {
        loadPageHospital("OrdenesEstudios");
    }

    private void irCuentasPorPAgarEstudiosMedicos(ActionEvent event) throws IOException {
        loadPageHospital("CuentasPorPagarEstudiosMedicos");
    }

    private void irComisionesPorPagos(ActionEvent event) throws IOException {
        loadPageHospital("ComisionesPorPago");
    }

    private void irCuentasPorPAgarExternas(ActionEvent event) throws IOException {//CientasxPagarExternas
        loadPageHospital("CientasxPagarExternas");
    }

    private void irUsuarios(ActionEvent event) throws IOException {
        loadPage("Usuarios");
    }

    private void irAlmacenes(ActionEvent event) throws IOException {
        loadPageHospital("Almacenes");
    }

    private void irCuentasxPagarExternas(ActionEvent event) throws IOException {
        loadPageHospital("ComprasExternasInsumos");
    }

    private void irEstadodecuenta(ActionEvent event) throws IOException {
        loadPageHospital("IngresosyEgresosHosptila");
    }

    private void irEstadodecuentaEgreso(ActionEvent event) throws IOException {
        loadPageHospital("EgresoseIngresosHosptila");
    }

    private void irCuemtaPaciente(ActionEvent event) throws IOException {
        loadPageHospital("AdeudoPaciente");
    }

    private void irAdeudo(ActionEvent event) throws IOException {
        loadPageHospital("AdeudoPaciente");
    }

    private void irIndicas(ActionEvent event) throws IOException {
        loadPageHospital("Indicas");
    }

    private void irConsumoPaciente(ActionEvent event) throws IOException {
        loadPageHospital("Suministro_insumos");
    }

    @FXML
    private void irOtrosIngresos(ActionEvent event) throws IOException {
        loadPageHospital("OtrosIngresos");
    }

    private void irAgregarConsumoPaciente2(ActionEvent event) throws IOException {
        loadPageHospital("Folios");
    }

    private void irFamilias(ActionEvent event) throws IOException {
        loadPageHospital("FamiliasInventario");
    }

    private void irFacturacion(ActionEvent event) throws IOException {
        loadPageHospital("FXMLFacturacion");
    }

    private void irActividadUsuarios(ActionEvent event) throws IOException {
        loadPageHospital("ActividadUsuarios");
    }

    private void irHemodinamia(ActionEvent event) throws IOException {
        loadPageHospital("Hemdinamia");
    }

    private void irFondoBanco(ActionEvent event) throws IOException {
        loadPageHospital("FondoBanco");
    }

    @FXML
    private void accionMinimizarVentana(ActionEvent event) {
        Stage stage = (Stage) btnMinimizar.getScene().getWindow();
        stage.setIconified(true);
    }

    private void irSuministroPacienteSistema(ActionEvent event) throws IOException {
        loadPageHospital("Suministro_insumosSISTEMAS");
    }

    private void irconcentradoIE(ActionEvent event) throws IOException{
        loadPageHospital("ConcentradoIngresosEgresos");
    }
    private void irGenerarDevolucion(ActionEvent event) throws IOException{
        loadPageHospital("DevolucionesdePagos");
    }
    private void irAgendadeQuirofano(ActionEvent event) throws IOException{
        loadPageHospital("AgendadeQuirofano");
    }
    private void irdeudasdelmes(ActionEvent event) throws IOException {
        loadPageHospital("DeudasdelMes");
    }
    private void irConsumoDia(ActionEvent event) throws IOException {
        loadPageHospital("Consumo_Dia");
    }
    private void irCorteParcial(ActionEvent event) throws IOException {
        loadPageHospital("CorteCaja");
    }

    @FXML
    private void irSeguros(ActionEvent event) throws IOException {
        loadPageHospital("CalculoSeguros");
    }
    
}
