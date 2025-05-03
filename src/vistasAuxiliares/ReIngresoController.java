/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares;

import clase.Conexion;
import clase.Paciente;
import clases_hospital.Consumo;
import clases_hospital.Folio;
import clases_hospital.Medico;
import clases_hospital.PaqueteMedico;
import clases_hospital.TipoSangre;
import clases_hospital_DAO.ConsumoQuirofanoDAO;
import clases_hospital_DAO.CostosDAO;
import clases_hospital_DAO.FoliosDAO;
import clases_hospital_DAO.MedicoDAO;
import clases_hospital_DAO.PacientesDAO;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.TipoSangreDAO;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ReIngresoController implements Initializable {

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfnombreIngresado;
    @FXML
    private ComboBox<Medico> cmbMedico;
    @FXML
    private RadioButton rdbPaquete;
    @FXML
    private TextField txfPaquete;
    @FXML
    private RadioButton rdbAbierta;
    @FXML
    private RadioButton rdbHospital;
    @FXML
    private RadioButton rdbUrgenciasentrada;
    @FXML
    private RadioButton rdbConsulta;
    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn<?, ?> nomnrePacienteTabla;
    public String nombre = " ";
    private int idMedicoSeleccion = 9;
    private int idPaquete = 1;
    boolean esPaquete = false, esCuentaabierta = true;
     boolean entraHospital = false, entraUrgencia = false;
    Conexion conexion = new Conexion();
    private Connection con; // = new Conexion().conectar2();
    ObservableList<Paciente> pacientesOB = FXCollections.observableArrayList();
    ObservableList<Medico> medicos = FXCollections.observableArrayList();
    ToggleGroup toggleGroup = new ToggleGroup();
    ToggleGroup toggleGroup2 = new ToggleGroup();
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);

    PacientesDAO pacientesDAO;
//    List<Paciente> pacient = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            rdbPaquete.setToggleGroup(toggleGroup);
            rdbAbierta.setToggleGroup(toggleGroup);
            rdbUrgenciasentrada.setToggleGroup(toggleGroup2);
            rdbHospital.setToggleGroup(toggleGroup2);
            rdbConsulta.setDisable(false);

            con = conexion.conectar2();
            pacientesDAO = new PacientesDAO(con);
            pacientesOB.addAll(pacientesDAO.reingreso(nombre));
            setObjetoIngresarNuevo(pacientesOB);
//            tabla(nombre);
            llenarCBX();

        } catch (SQLException ex) {
            Logger.getLogger(ReIngresoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ingresar(ActionEvent event) {
    }

     @FXML
    private void salir(ActionEvent event) {
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void accionPaquete(ActionEvent event) {
        //ingreso por paquete
        txfPaquete.setDisable(false);
        esPaquete = true;
        esCuentaabierta = false;

        rdbHospital.setDisable(true);

        rdbUrgenciasentrada.setDisable(true);
    }

    @FXML
    private void accionCuentaAbierta(ActionEvent event) {
        //ingreso cuenta abierta
        txfPaquete.setDisable(true);
        esPaquete = false;
        esCuentaabierta = true;
        rdbHospital.setDisable(false);
        rdbUrgenciasentrada.setDisable(false);
    }

    @FXML
    private void accionHospital(ActionEvent event) {
    }

    @FXML
    private void accionUrgenciasEntrada(ActionEvent event) {
    }

    @FXML
    private void accionConsulta(ActionEvent event) {
    }

    @FXML
    private void txtnombre(ActionEvent event) throws SQLException {
     
    }
       @FXML
    private void ingresar2(ActionEvent event) throws SQLException {
        int id_foliogen =0;
        con = conexion.conectar2();
        int id_paceinte = this.tabla.getSelectionModel().getSelectedItem().getIdPaciente();
      //  PacientesDAO pacientesDAO = new PacientesDAO(con);
          
        this.pacientesDAO.actualizarmedico(idMedicoSeleccion, id_paceinte);

        id_foliogen = generarFolio2(id_paceinte);


     //   pacientesDAO.actualizarFolioPaciente(idPacienteGenerado, folioGenerado);

        if (rdbConsulta.isSelected()) {
            /*
            agregar a consumo de paciente la consulta de valoracion
            621	CONSULTA DE VALORACION	431.034483
             */
            CostosDAO costosDAO = new CostosDAO(con);
            double costoConsulta = costosDAO.obtenerCosto(621);
            Consumo consumo = new Consumo("CONSULTA DE VALORACION",// tipo
                    1,// cantidad
                    costoConsulta,//costo unitario multiplicado por la cantidad (monto total)
                    "",// folio
                    id_paceinte,//id paciente
                    id_foliogen,// id folio
                    621);// id producto
            consumo.setPrecioUnitario(costoConsulta);
            consumo.setId_tipo_consumo(1);
            ConsumoQuirofanoDAO consumoQuirofanoDAO = new ConsumoQuirofanoDAO(con);
            consumoQuirofanoDAO.incertarConsumo(consumo);
        }

        alertaSuccess.setHeaderText("PACIENTE INGRESADO");
        alertaSuccess.setContentText("PROCEDIMIENTO EJECUTADO CON EXITO");
        alertaSuccess.showAndWait();
        con.close();
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
    }
           private int generarFolio2(int id_paciente) throws SQLException {
               int id_foliogen = 0;
        FoliosDAO foliodao = new FoliosDAO(conexion.conectar2());
        Folio folio = new Folio();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);
        String sfolio = "" + id_paciente + formattedDateTime;

        folio.setFolio(sfolio);
        folio.setIdPaciente(id_paciente);
        folio.setIdUsuarioModificacion(userSystem);
        if (entraUrgencia) {
            folio.setUrgencias(true);
        } else {
            folio.setUrgencias(false);
        }
        if (esPaquete) {
            folio.setId_paquete(idPaquete);
            folio.setCosoto_deposito(0);
        } else {
            folio.setId_paquete(1);
            folio.setCosoto_deposito(10000);
        }
       

      id_foliogen = foliodao.crearfolios(folio);
      
        return id_foliogen;
    }
    public void setObjetoIngresarNuevo(ObservableList<Paciente> pacientes) throws SQLException {
        this.pacientesOB = pacientes;
        tabla.setItems(this.pacientesOB);
        nomnrePacienteTabla.setCellValueFactory(new PropertyValueFactory("nombre"));

        txfnombreIngresado.setOnKeyReleased(e -> {
            filtrarLista(txfnombreIngresado.getText());
        });
        nomnrePacienteTabla.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
    }

    private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(pacientesOB);
        } else {
            ObservableList<Paciente> listaFiltrada = FXCollections.observableArrayList();
            for (Paciente paciente : pacientesOB) {
                if (paciente.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(paciente);
                }
            }
            tabla.setItems(listaFiltrada);
        }
    }

    public void llenarCBX() throws SQLException {

        MedicoDAO medicodao = new MedicoDAO(con);
        TipoSangreDAO tipoSangreDAO = new TipoSangreDAO(con);
        PaqueteMedicoDAO paqueteMedicoDAO = new PaqueteMedicoDAO(con);
        try {
            medicos.addAll(medicodao.traerMedicosConDatosEsenciales());
            cmbMedico.setItems(medicos);
            cmbMedico.setOnAction(e -> {
                Medico medico = (Medico) cmbMedico.getValue();
                idMedicoSeleccion = medico.getId_medico();
            });

        } catch (SQLException ex) {
            Logger.getLogger(PacienteNuevoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AutoCompletionBinding<PaqueteMedico> paquete = TextFields.bindAutoCompletion(txfPaquete, paqueteMedicoDAO.obtenerTodos());
        paquete.setPrefWidth(600);
        paquete.setOnAutoCompleted(event -> {
            PaqueteMedico paqueteMedicoSeleccion = event.getCompletion();
            idPaquete = paqueteMedicoSeleccion.getId();
        });
        con.close();
    }

}
