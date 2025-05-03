/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import clase.Conexion;
import clases_hospital.Area;
import clases_hospital_DAO.AreaDAO;
import clases_hospital_DAO.ConsumosDAO;
import clases_hospital_DAO.UsuarioDAO;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class DashBoard_1Controller implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane anchorPanel;
    private TitledPane tpContaduria;
     Conexion connection = new Conexion();
    Connection con = connection.conectar2();
    @FXML
    private Button btnSalir;
    @FXML
    private ComboBox<Area> cmbArea;
    private ObservableList<Area> areaOBL = FXCollections.observableArrayList(); ;
    @FXML
    private TitledPane A;
    @FXML
    private TitledPane B;
    @FXML
    private TitledPane C;
    @FXML
    private Button btnMinimizar;
    @FXML
    private Label lblSeleccionarArea;
    @FXML
    private ImageView imgFlecha;
    @FXML
    private TitledPane tpQuirofano;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenFondo();
        A.setVisible(false);
        B.setVisible(false);
        C.setVisible(false);
        if (VPMedicaPlaza.userNameSystem.equals("SISTEMAS")) {
            tpContaduria.setVisible(true);
        }
        try {
            llenarcbxarea();
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard_1Controller.class.getName()).log(Level.SEVERE, null, ex);
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
    private void accionCerrarSesion(ActionEvent event) throws SQLException {
        List<String> listanombres = new ArrayList<>();
        UsuarioDAO usuariodao = new UsuarioDAO(con);
        ConsumosDAO consumodao = new ConsumosDAO(con);
        listanombres = usuariodao.IndicasFaltantes(VPMedicaPlaza.userArea);
        

        if (listanombres.isEmpty()) {
            System.out.println("hola mundo: " + VPMedicaPlaza.userSystem + " - " + VPMedicaPlaza.userArea);
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
                //consumodao.actualizarIndicasporarea(VPMedicaPlaza.userArea);
                System.out.println("SE CERRO LA SECION");
                usuariodao.EstadoSesion(VPMedicaPlaza.userSystem, false);
                Stage stage = (Stage) btnSalir.getScene().getWindow();
                stage.close();

            } else {
                System.out.println("SE CERRO LA SECION PERO ENTRANDO EN ELSE");
                // Hacer algo si el usuario seleccionó "No" o cerró el cuadro de diálogo
            }
        }

    }
    
    private void llenarcbxarea() throws SQLException{
        con = connection.conectar2();
        ArrayList<Area> arealist = new ArrayList<>();      
        AreaDAO areaDAO = new AreaDAO(con);
        arealist.addAll( areaDAO.getAll());
        areaOBL.addAll(arealist);
        cmbArea.setItems(areaOBL);
        
        
    }

    @FXML
    private void irPacientes(ActionEvent event) throws IOException {
        loadPage("Pacientes2");
    }

    private void irMedicos(ActionEvent event) throws IOException {
        loadPageHospital("Medicos");
    }

    private void irHabitaciones(ActionEvent event) throws IOException {
        loadPageHospital("Habitaciones");
    }

    private void irQuirofanos(ActionEvent event) throws IOException {
        loadPageHospital("Quirofanos");
    }

    private void irServiciosAdicionales(ActionEvent event) throws IOException {
        loadPageHospital("ServiciosAdicionales");
    }

    private void irProcedimientos(ActionEvent event) throws IOException {
        loadPageHospital("Procedimientos");
    }

    private void irInventario(ActionEvent event) throws IOException {
        loadPageHospital("Inventario");
    }

    private void actionVerHabitaciones(ActionEvent event) throws IOException {
        loadPageHospital("HabitacionesTabla");
    }

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

    private void irDietas(ActionEvent event) {
        try {
            loadPageHospital("PaquetesAlimentos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    private void irPaquetesAlimenticios(ActionEvent event) throws IOException {
        loadPageHospital("PaquetesAlimentos");
    }

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

    private void irLaboratorios(ActionEvent event) throws IOException {
        loadPageHospital("Laboratorios");
    }

    private void irOrdenesEstudios(ActionEvent event) throws IOException {
        loadPageHospital("OrdenesEstudios");
    }

    private void irCuentasPorPAgarEstudiosMedicos(ActionEvent event) throws IOException {
        loadPageHospital("CuentasPorPagarEstudiosMedicos");
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
    private void CambearArea(ActionEvent event) {
        System.out.println(""+ VPMedicaPlaza.userArea);
        VPMedicaPlaza.userArea = cmbArea.getSelectionModel().getSelectedItem().getIdArea();
        System.out.println(""+ VPMedicaPlaza.userArea);
        cmbArea.setVisible(false);
         A.setVisible(true);
        B.setVisible(true);
        C.setVisible(true);
        lblSeleccionarArea.setVisible(false);
        imgFlecha.setVisible(false);
        if(cmbArea.getSelectionModel().getSelectedItem().getIdArea() == 4){
            tpQuirofano.setVisible(true);
        }
              
    }

    @FXML
    private void accionMinimizarVentana(ActionEvent event) {
        Stage stage = (Stage) btnMinimizar.getScene().getWindow();
        stage.setIconified(true);
    }
}
