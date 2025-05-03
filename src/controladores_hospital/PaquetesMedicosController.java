/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clase.Usuario;
import clases_hospital.PaqueteMedico;
import clases_hospital.PaquetesNvo;
import clases_hospital_DAO.PaqueteMedicoDAO;
import clases_hospital_DAO.PaquetesMedicosDAO;
import clases_hospital_DAO.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.apache.commons.codec.digest.DigestUtils;
import reportes.ReporteC;
import vistasAuxiliares_hospital.AgregarNuevoPaqueteController;
import vistasAuxiliares_hospital.AgregarNuevoPaqueteControllerConLista;
import vistasAuxiliares_hospital.NVO_paque_medicoController;
import vistasAuxiliares_hospital.VerModificacionesPaquetesPController;
import vistasAuxiliares_hospital.VerPaquetesMedicosController;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author Gerardo
 */
public class PaquetesMedicosController implements Initializable {

    @FXML
    private TableView<PaquetesNvo> tabla;
    @FXML
    private TableColumn nombrePaquete;
    private TableColumn descripcionPaquete;
    private TableColumn<PaquetesNvo, String> precioPaquete;

    /**
     * Initializes the controller class.
     */
    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    ObservableList<PaquetesNvo> paquetes;
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    PaqueteMedicoDAO paquetedao;
    @FXML
    private TextField txfNombrePaqueteMedico;
    @FXML
    private Button btnVerArmado;
    @FXML
    private Button btnCopiarConfiguracion;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    private TableColumn<?, ?> precioCosto;
    private TableColumn<?, ?> diferencia;
    private TableColumn<?, ?> porDiferencia;
    private TableColumn<?, ?> precioVentaOriginal;
    private TableColumn<?, ?> precioventanvo;
    private TableColumn<?, ?> diferencia_precioventa;
    private TableColumn<?, ?> porprecioventa;
    private TableColumn<?, ?> precioventaivaorignal;
    private TableColumn<?, ?> precioventaivanvo;
    private TableColumn<?, ?> montogananciaoriginal;
    private TableColumn<?, ?> montoganancianvo;
    private TableColumn<?, ?> porgananciaoriginal;
    private TableColumn<?, ?> porganancianvo;
    @FXML
    private Button btnUltimaModificacion;
    @FXML
    private Button btnListaPreciosPM;
    @FXML
    private TableColumn<PaquetesNvo, String> precioVenta;
    @FXML
    private TableColumn<?, ?> costoInsumos;
    @FXML
    private TableColumn<?, ?> costosServicios;
    @FXML
    private TableColumn<?, ?> Utilidad;
    @FXML
    private TableColumn<?, ?> porUtilidad;
    @FXML
    private TableColumn<?, ?> PrecioUHE;
    @FXML
    private TableColumn<?, ?> totalHE;
    @FXML
    private TableColumn<?, ?> utilidadcHE;
    @FXML
    private TableColumn<?, ?> porUtilidadcHE;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (VPMedicaPlaza.userNameSystem.equals("MEZCLAS")) {
            btnCopiarConfiguracion.setVisible(false);
            btnAgregar.setVisible(false);
            btnEditar.setVisible(false);
            btnEliminar.setVisible(true);
        }

        tabla.setEditable(true);
        try {
            // TODO
            con = conexion.conectar2();
            paquetedao = new PaqueteMedicoDAO(con);

            llenarTabla();
            pintarTabla();
            txfNombrePaqueteMedico.setOnKeyReleased(e -> filtrarLista(txfNombrePaqueteMedico.getText()));
            nombrePaquete.setCellValueFactory(new PropertyValueFactory("nombre"));
            precioVenta.setCellValueFactory(new PropertyValueFactory("precioVentaMoneda"));
            costoInsumos.setCellValueFactory(new PropertyValueFactory("costoActualMoneda"));
            costosServicios.setCellValueFactory(new PropertyValueFactory("costoNuevoMoneda"));
            Utilidad.setCellValueFactory(new PropertyValueFactory("diferenciaCostosMoneda"));
            porUtilidad.setCellValueFactory(new PropertyValueFactory("gananciaMontoNuevoPorcentajeFormato"));
            PrecioUHE.setCellValueFactory(new PropertyValueFactory("precioVentaActualMoneda"));
            totalHE.setCellValueFactory(new PropertyValueFactory("precioVentaNuevoMoneda"));
            utilidadcHE.setCellValueFactory(new PropertyValueFactory("diferenciaPrecioVentaMoneda"));
            porUtilidadcHE.setCellValueFactory(new PropertyValueFactory("gananciaMontoOriginalPorcentajeFormato"));
//            precioPaquete.setOnEditCommit(event -> {
//                PaqueteMedico paqmed = event.getTableView().getItems().get(event.getTablePosition().getRow());
//                paqmed.setPrecio(Double.parseDouble(event.getNewValue()));
//                try {
//                    paquetedao.actualizarPecioTabla(paqmed.getId(), paqmed.getPrecio());
//                } catch (SQLException ex) {
//                    Logger.getLogger(PaquetesMedicosController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            precioPaquete.setEditable(true);
        } catch (SQLException ex) {
            Logger.getLogger(PaquetesMedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irAgregar(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/NVO_paque_medico.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("PAQUETE NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void irLlamarUltimaModificacion(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VerPaquetesMedicos.fxml"));
        long valorpaquete = tabla.getSelectionModel().getSelectedItem().getId();
        String nombre_paquete = tabla.getSelectionModel().getSelectedItem().getNombre();
        Parent root = fxml.load();
        VerPaquetesMedicosController control = fxml.getController();
        control.SetObjeto(valorpaquete, nombre_paquete);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("PAQUETE NUEVO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.showAndWait();
        llenarTabla();
    }

    @FXML
    private void irEditar(ActionEvent event) throws IOException, SQLException {

        // Obtener el objeto de la vista de origen
//        PaqueteMedico paqueteMedico = tabla.getSelectionModel().getSelectedItem();
//        if (paqueteMedico.getEstatusMovimiento() == 1) {
//            
//            
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/NVO_paque_medico.fxml"));
//            Parent root = loader.load();
//            NVO_paque_medicoController destinoController = loader.getController();
//
//            // Pasar el objeto a la vista de destino
//            destinoController.setObjetoModificado(paqueteMedico);
//            Stage destinoStage = new Stage();
//            destinoStage.setTitle("EDITAR PAQUETE");
//            destinoStage.setScene(new Scene(root));
//            destinoStage.initModality(Modality.APPLICATION_MODAL);
//            destinoStage.setResizable(false);
//
//            // Mostrar el nuevo Stage de forma modal
//            destinoStage.showAndWait();
//        } else {
//            // Cargar la vista de destino
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/NVO_paque_medico.fxml"));
//            Parent root = loader.load();
//            NVO_paque_medicoController destinoController = loader.getController();
//
//            // Pasar el objeto a la vista de destino
//            destinoController.setObjeto(paqueteMedico);
//            // Crear un nuevo Stage para la vista de destino
//            Stage destinoStage = new Stage();
//            destinoStage.setTitle("EDITAR PAQUETE");
//            destinoStage.setScene(new Scene(root));
//            destinoStage.initModality(Modality.APPLICATION_MODAL);
//            destinoStage.setResizable(false);
//
//            // Mostrar el nuevo Stage de forma modal
//            destinoStage.showAndWait();
//        }
//
//        llenarTabla();
    }

    @FXML
    private void irEliminar(ActionEvent event) {
//        PaquetesNvo paquetess = new PaquetesNvo();
//        paquetess = this.tabla.getSelectionModel().getSelectedItem();
//        con = conexion.conectar2();
//        alertaConfirmacion.setHeaderText(null);
//        alertaConfirmacion.setTitle("ELMINAR PAQUETE");
//        alertaConfirmacion.setContentText("¿DESEA ELIMINAR EL PAQUETE?");
//        Optional<ButtonType> alertaConfirmacion2 = alertaConfirmacion.showAndWait();
//        if (alertaConfirmacion2.get() == ButtonType.OK) {
//            try {
//                CallableStatement stmt = null;
//                String sql = "{call eliminarPaqueteMedico (?)}";
//                stmt = con.prepareCall(sql);
//                stmt.setInt(1, paquetess.getId());
//                stmt.execute();
//                stmt.close();
//                alertaSuccess.setHeaderText("Paquete Alimenticio fuera del Sistema");
//                alertaSuccess.setContentText("Procedimiento Ejecutado con Exito");
//                alertaSuccess.showAndWait();
//                llenarTabla();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @FXML
    private void irVisualizar(ActionEvent event) throws IOException, SQLException {
//        PaqueteMedico paquete = tabla.getSelectionModel().getSelectedItem();
        ReporteC reportec = new ReporteC("Blank_A4");
//        reportec.generarReportePaquetes(paquete.getId(), paquete.getFactor_paquete());
//        PaqueteMedico paquete = tabla.getSelectionModel().getSelectedItem();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VerPaquetesMedicos.fxml"));
//        Parent root = loader.load();
//        VerPaquetesMedicosController controller = loader.getController();
//        controller.recibirDatos(paquete.getId(), paquete.getNombre());
//
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
//        stage.setMaximized(true);
//        stage.setTitle("INSUMOS DE PAQUETE");
//        stage.getIcons().add(new Image("/img/icono.png"));
//        stage.setScene(scene);
//        this.tabla.getItems().clear();
//        llenarTabla();
//        //txfbuscador.clear();
//        stage.showAndWait();
    }

    @FXML
    private void irListaPrecio(ActionEvent event) throws IOException, SQLException {
        ReporteC reportec = new ReporteC("Blank_A4");
//        reportec.generarReportePaquetes(paquete.getId(), paquete.getFactor_paquete());
//        PaqueteMedico paquete = tabla.getSelectionModel().getSelectedItem();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VerPaquetesMedicos.fxml"));
//        Parent root = loader.load();
//        VerPaquetesMedicosController controller = loader.getController();
//        controller.recibirDatos(paquete.getId(), paquete.getNombre());
        reportec.generarReporteListaPreciosPaquetesMedicos();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.initModality(Modality.WINDOW_MODAL);
//        stage.setMaximized(true);
//        stage.setTitle("INSUMOS DE PAQUETE");
//        stage.getIcons().add(new Image("/img/icono.png"));
//        stage.setScene(scene);
//        this.tabla.getItems().clear();
//        llenarTabla();
//        //txfbuscador.clear();
//        stage.showAndWait();
    }

    public void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        PaquetesMedicosDAO paquetesMedicosDAO = new PaquetesMedicosDAO(con);
        paquetes = FXCollections.observableArrayList(paquetesMedicosDAO.cambiodepaquetesDENUEVO());
        tabla.setItems(paquetes);
        editarCostoPaquete();
        con.close();
    }

    @FXML
    private void copiarArmado(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/CopiarArmadoPaquete.fxml"));
        Parent root = fxml.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(true);
        stage.setTitle("COPIAR ARMADO");
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.showAndWait();
    }

    private void filtrarLista(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(paquetes);
        } else {
            ObservableList<PaquetesNvo> listaFiltrada = FXCollections.observableArrayList();
            for (PaquetesNvo paqueteMedico : paquetes) {
                if (paqueteMedico.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(paqueteMedico);
                }
            }
            tabla.setItems(listaFiltrada);
        }
    }

    private void pintarTabla() {
        tabla.setRowFactory(tv -> {
            Tooltip tooltip = new Tooltip();
            return new TableRow<PaquetesNvo>() {
                @Override
                protected void updateItem(PaquetesNvo item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setTooltip(null);
                        setStyle("");
                    } else {
                        tooltip.setText("USUARIO: " + item.getNombreUsuario()
                                + "\n FECHA MODIFICACION: " + item.getFechaModificacion());
                        setTooltip(tooltip);

                        if (item.isColorRow()) {
                            
                            if (item.getCostoActual() == item.getCostoNuevo()) {
                                setStyle("");//confirmado
                            } 
                            else if (item.getCostoActual() > item.getCostoNuevo()) {
                                setStyle("-fx-background-color: #51d000; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
                            } 
                               
                            else if(item.getCostoActual() < item.getCostoNuevo()) {
                                setStyle("-fx-background-color: #FF5733; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//confirmado
                            }
                        } else {
                            setStyle(" ");
                        }
                    }
                }
            };
        });

    }

    private void editarCostoPaquete() {
        precioVenta.setCellFactory(TextFieldTableCell.forTableColumn());
        // CANTIDAD

        precioVenta.setOnEditCommit(event -> {
            // Obtener el valor editado
            String nuevoValor = event.getNewValue();
            String valorDouble = nuevoValor.replace("$", "").replace(",", "");
            Double valordouble = Double.parseDouble(valorDouble);
            //selecciona la linea 
            int indiceFila = event.getTablePosition().getRow();
            // actualiza los valores en esa linea

            paquetes.get(indiceFila).setPrecioVenta(valordouble);
            switch (VPMedicaPlaza.userNameSystem) {
                case "SISTEMAS":
                    actualizarPrecio(paquetes.get(indiceFila));
                    break;
                case "COMERCIALIZACION":
                    actualizarPrecio(paquetes.get(indiceFila));
                    break;
                case "QUIROFANO":
                    actualizarPrecio(paquetes.get(indiceFila));
                    break;
                case "CONTABILIDAD":
                    actualizarPrecio(paquetes.get(indiceFila));
                    break;
                case "CONTRALORIA":
                    actualizarPrecio(paquetes.get(indiceFila));
                    break;
                case "SEGUROS":
                    actualizarPrecio(paquetes.get(indiceFila));
                    break;
                default:
                    alertaError.setHeaderText("ACCION INVALIDA");
                    alertaError.setContentText("NO CUENTA CON LOS PERMISOS PARA REALIZAR ESTA ACCION");
                    alertaError.showAndWait();
                    break;
            }
            tabla.refresh();
        });

        precioVenta.setEditable(true);
    }

    private void actualizarPrecio(PaquetesNvo paquetetraido) {

        showAlertWithTextField(paquetetraido);

    }

    private void showAlertWithTextField(PaquetesNvo paquetetraido) {
        con = conexion.conectar2();
        // Crear el diálogo
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Autorizacion necesaria");
        dialog.setHeaderText("Pars editar este campo es necesario verificar sus credenciasles");

        // Establecer los botones del diálogo
        ButtonType okButtonType = new ButtonType("ACEPTAR", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Crear el TextField y el GridPane
        PasswordField textField = new PasswordField();
        textField.setPromptText("Ingrese su contraseña");

        GridPane grid = new GridPane();
        grid.add(new Label("clave de acceso:"), 0, 0);
        grid.add(textField, 1, 0);
        GridPane.setHgrow(textField, Priority.ALWAYS);

        // Agregar el GridPane al contenido del diálogo
        dialog.getDialogPane().setContent(grid);

        // Convertir el resultado cuando el botón OK es presionado
        dialog.setResultConverter(dialogButton -> {

            String regreso = "";
            if (dialogButton == okButtonType) {
                UsuarioDAO usuarioDAO = new UsuarioDAO(con);
                try {
                    String contra = textField.getText();
                    Usuario usuario = usuarioDAO.leer(VPMedicaPlaza.userSystem);
                    System.out.println(usuario.getNombre());
                    System.out.println(usuario.getContra());
                    //contra = DigestUtils.md5Hex(contra);
                    if (contra.compareTo("") == 0) {
                        alertaError.setHeaderText("Existe al menos un campo vacio");
                        alertaError.setContentText("Favor de revisar que todos los campos requeridos esten completados ");
                        alertaError.showAndWait();
                    } else {
                        contra = DigestUtils.md5Hex(contra);
                        System.out.println(contra);
                        if (contra.equals(usuario.getContra())) {
                            con = conexion.conectar2();
                            paquetedao = new PaqueteMedicoDAO(con);
                            alertaConfirmacion.setTitle("CONFIRMACIÓN");
                            alertaConfirmacion.setHeaderText("¿Está seguro de actualizar el precio a:");
                            alertaConfirmacion.setContentText("" + paquetetraido.getPrecioVentaMoneda());
                            ButtonType botonOK = new ButtonType("OK");
                            alertaConfirmacion.getButtonTypes().setAll(botonOK, ButtonType.CANCEL);
                            Stage escenario = (Stage) alertaConfirmacion.getDialogPane().getScene().getWindow();
                            escenario.setAlwaysOnTop(true);
                            ButtonType resultado = alertaConfirmacion.showAndWait().orElse(ButtonType.CANCEL);
                            regreso = "OK";

// Verifica si el usuario presionó "OK"
                            if (resultado == botonOK) {
                                // Ejecuta la línea de código después de presionar "OK" en el cuadro de diálogo de confirmación
                                paquetedao.actualizarPrecioVentaPaquete(paquetetraido);
                            }
                        } else {
                            regreso = "NO";
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PaquetesMedicosController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return regreso;
            }
            return regreso;
        });

        // Mostrar el diálogo y capturar el resultado
        dialog.showAndWait().ifPresent(result -> {
            if (result.equals("OK")) {
                alertaSuccess.setHeaderText("CAMPO MODIFICADO");
                alertaSuccess.setContentText("ESTA ACCION REALIZO DE FORMA CORRECTA");
                alertaSuccess.showAndWait();
            } else {

                alertaError.setHeaderText("CONTRASEÑA INCORRECTA");
                alertaError.setContentText("ESTA ACCION NO SE PUEDE LLEVAR ACABO");
                alertaError.showAndWait();
            }
        });
    }

    /*
    if (usuario.compareTo("") == 0 || contra.compareTo("") == 0) {
            alertaError.setHeaderText("Existe al menos un campo vacio");
            alertaError.setContentText("Favor de revisar que todos los campos requeridos esten completados ");
            alertaError.showAndWait();
        } else {
            try {
                contra = DigestUtils.md5Hex(contra);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from usuarios where nombre = '" + usuario + "'");

                while (rs.next()) {
                    idUsuario = rs.getInt(1);
                    nombreUsuario = rs.getString(2);
                    pass = rs.getString(3);
                    cargo = rs.getString(4);
                    area = rs.getInt(7);

                }
                if (idUsuario != 0) {
                    rs = stmt.executeQuery("select * from usuarios where id = '"
                            + idUsuario + "' and pass = '" + contra + "'");
                    while (rs.next()) {
                        contraCorrecta = true;
                    }
                    if (contraCorrecta) {
                        
                    } else {
                        alertaError.setHeaderText("Contraseña incorrecta");
                        alertaError.setContentText("La contraseña insertada es incorrecta");
                        alertaError.showAndWait();
                    }
                } else {
                    alertaError.setHeaderText("Usuario incorrecto");
                    alertaError.setContentText("El usuario ingresado no existe ");
                    alertaError.showAndWait();
                }

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
     */
}
