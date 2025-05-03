/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vpmedicaplaza;

import clase.Conexion;
import clases_hospital.CierresMesEfectivo;
import clases_hospital_DAO.CierresMesEfectivoDAO;
import clases_hospital_DAO.UsuarioDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author PC
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField usuarioIngresado;
    @FXML
    private PasswordField contraIngresada;
    @FXML
    private Button btnIngresar;

    Alert alertaError = new Alert(Alert.AlertType.ERROR);

    @FXML
    private AnchorPane root;
    Connection con = new Conexion().conectar2();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        if (!VPMedicaPlaza.animacionEjecutada) {
//            ejecutarAnimacion();
//        }
        ejecutarTareas();
    }

    @FXML
    private void ingresar(ActionEvent event) {
        Conexion conexion = new Conexion();//clase conexion creada en el paquete clase
        Connection conn = conexion.conectar2();//clase default de java
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
        boolean contraCorrecta = false;

        String usuario = usuarioIngresado.getText(), contra = contraIngresada.getText();
        //variables para los datos recabados de la base de datos
        int idUsuario = 0, area = 0;
        String nombreUsuario = "", pass = "", cargo = "";
        
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
                        VPMedicaPlaza.pass = contra;
                        VPMedicaPlaza.userSystem = idUsuario;
                        VPMedicaPlaza.userNameSystem = cargo;
                        VPMedicaPlaza.userArea = area;
                        usuarioDAO.EstadoSesion(VPMedicaPlaza.userSystem, true);

                        switch (cargo) {
                            case "SISTEMAS":
                                cargarDashboard("DashBoard");
                                break;
                            case "ENFERMERIA":
                                cargarDashboard("DashBoard_1");
                                break;
                            case "BIOMEDICO":
                                cargarDashboard("Dashboard_BIOMEDICA");
                                break;
                            case "RECEPCION":
                                cargarDashboard("DashBoard _Recepcion");
                                break;
                            case "CAJA":
                                cargarDashboard("DashBoard _Caja");
                                break;
                            case "MEZCLAS":
                                cargarDashboard("DashBoard_Mezclas");
                                break;
                            case "URGENCIAS":
                                cargarDashboard("DashBoard_Urgencias");
                                break;
                            case "COCINA":
                                cargarDashboard("DashBoard_Cocina");
                                break;
                            case "COMERCIALIZACION":
                                cargarDashboard("DashBoard_Comercializacion");
                                break;
                            case "QUIROFANO":
                                cargarDashboard("DashBoard_Quirofano");
                                break;
                            case "CONTABILIDAD":
                                cargarDashboard("DashBoard_Contabilidad");
              //                        cierreMesEfectivo(); JEFEMEDICO
                  break;
                            case "NOMINA":
                                cargarDashboard("DashBoard_Nomina");
                                break;
                            case "CONTRALORIA":
                                cargarDashboard("DashBoard_Contraloria");
                                break;
                            case "SEGUROS":
                                cargarDashboard("DashBoard_Seguros");
                                break;
                            case "MEDICO":
                                cargarDashboard("DashBoard_DOCTORES");
                                break;
                            case "JEFEMEDICO":
                                cargarDashboard("DashBoard_JefesMedicos");
                                break;
                        }

//                        cierreMesEfectivo(); JEFEMEDICO
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
    }

    private void ejecutarAnimacion() {
        try {
            VPMedicaPlaza.animacionEjecutada = true;
            reproducirSonido();
            StackPane pane = FXMLLoader.load(getClass().getResource("Splash.fxml"));
            root.getChildren().setAll(pane);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2.5), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            //fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            //fadeOut.setCycleCount(1);

            fadeIn.play();
            //reproducirSonido();
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {

                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                    root.getChildren().setAll(parentContent);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarDashboard(String page) throws IOException {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnIngresar.getScene().getWindow();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas/" + page + ".fxml"));
        Parent root = (Parent) fxml.load();
        stage.setTitle(page);
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
        stage_actual.close();
    }

    private void cargarDashboardUrgencias(String page) throws IOException {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnIngresar.getScene().getWindow();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas/" + page + ".fxml"));
        Parent root = (Parent) fxml.load();
        stage.setTitle(page);
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
        stage_actual.close();
    }

    private void cargarDashboardCaja(String page) throws IOException {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnIngresar.getScene().getWindow();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas/" + page + ".fxml"));
        Parent root = (Parent) fxml.load();
        stage.setTitle(page);
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
        stage_actual.close();
    }

    private void cargarDashboardMezclas(String page) throws IOException {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnIngresar.getScene().getWindow();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas/" + page + ".fxml"));
        Parent root = (Parent) fxml.load();
        stage.setTitle(page);
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
        stage_actual.close();
    }

    private void cargarDashboard2(String page) throws IOException {
        Stage stage = new Stage();
        Stage stage_actual = (Stage) btnIngresar.getScene().getWindow();
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistas/" + page + ".fxml"));
        Parent root = (Parent) fxml.load();
        stage.setTitle(page);
        stage.getIcons().add(new Image("/img/icono.png"));
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
        stage_actual.close();
    }

    private void reproducirSonido() {
        try {
            String path = Paths.get("").toAbsolutePath().toString() + "/src/img/aperturaX.wav";

            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    private static boolean esInicioDeMes() {
        LocalDate fechaActual = LocalDate.now();
        int diaDelMes = fechaActual.getDayOfMonth();
        return diaDelMes == 1;
    }

//    private void cierreMesEfectivo() throws SQLException {
//
//        Conexion conexion = new Conexion();//clase conexion creada en el paquete clase
//        Connection con2 = conexion.conectar2();
//        CierresMesEfectivoDAO cierremesefectivoDAO = new CierresMesEfectivoDAO(con2);
//        LocalDate fechaActual = LocalDate.now();
//        int mesActual = fechaActual.getMonthValue(); // Obtiene el mes actual como un número (1 a 12).
//        int anioActual = fechaActual.getYear(); // Obtiene el año actual como un número de 4 dígitos.
//        String query = "SELECT * FROM fondoefectivofijo";
//        Statement statement = con2.createStatement();
//        double montoEnFondoEfectivo = 0;
//        CierresMesEfectivo cierresMesEfectivo;
//
//        // Define el locale en español
//        Locale locale = new Locale("es", "ES");
//        // Obtiene los nombres de los meses
//        DateFormatSymbols symbols = new DateFormatSymbols(locale);
//        String[] nombresMeses = symbols.getMonths();
//
//        if (esInicioDeMes()) {
//
//            ResultSet resultSet = statement.executeQuery(query);
//            if ((resultSet.next())) {
//                montoEnFondoEfectivo = resultSet.getDouble(2);
//            }
//
//            cierresMesEfectivo = new CierresMesEfectivo();
//            if (mesActual - 2 == -1) {
//                cierresMesEfectivo.setMesCierreMes(nombresMeses[11].toUpperCase());
//            } else {
//                cierresMesEfectivo.setMesCierreMes(nombresMeses[mesActual - 2].toUpperCase());
//            }
//            System.out.println("" + anioActual);
//            System.out.println("" + mesActual);
//            if (mesActual == 1) {
//                cierresMesEfectivo.setYearCierreMes(anioActual - 1);
//            } else {
//                cierresMesEfectivo.setYearCierreMes(anioActual);
//            }
//
//            cierresMesEfectivo.setMontoEfectivoCierreMes(montoEnFondoEfectivo);
//            cierremesefectivoDAO.insertCierreMesEfectivo(cierresMesEfectivo);
//        } else {
//            System.out.println("" + anioActual);
//            System.out.println("" + mesActual);
//            if (cierremesefectivoDAO.getCierresMesEfectivoByFecha(mesActual, anioActual)) {
//
//            } else {
//
//                ResultSet resultSet = statement.executeQuery(query);
//                if ((resultSet.next())) {
//                    montoEnFondoEfectivo = resultSet.getDouble(2);
//                }
//
//                cierresMesEfectivo = new CierresMesEfectivo();
//                if (mesActual - 2 == -1) {
//                    cierresMesEfectivo.setMesCierreMes(nombresMeses[11].toUpperCase());
//                } else {
//                    cierresMesEfectivo.setMesCierreMes(nombresMeses[mesActual - 2].toUpperCase());
//                }
//                if (mesActual == 1) {
//                    cierresMesEfectivo.setYearCierreMes(anioActual - 1);
//                } else {
//                    cierresMesEfectivo.setYearCierreMes(anioActual);
//                }
//                cierresMesEfectivo.setMontoEfectivoCierreMes(montoEnFondoEfectivo);
//                cierremesefectivoDAO.insertCierreMesEfectivo(cierresMesEfectivo);
//            }
//        }
//    }

    private void ejecutarTareas() {
        try {
            ejecutarProcedimiento("CONVERSIONDETRANSITO");
            ejecutarProcedimiento("ACTUALIZAR_INVENTARIO_POR_CADUCIDAD");
            ejecutarProcedimiento("REINICIOMINISTRO");
          //  ejecutarProcedimiento("CREARCOMISIONESXMES");
            ejecutarProcedimiento("CIERRE_MES_EFECTIVO");
        } catch (SQLException e) {
            System.err.println("Error al ejecutar las tareas.");
            e.printStackTrace();
            alertaError.setHeaderText("ALGO SALIO MAL");
            alertaError.setContentText("EL PROCEDIMIENTO "+e.getMessage()+" TUVO UN ERROR");
            alertaError.showAndWait();
        }
    }

    private void ejecutarProcedimiento(String nombreProcedimiento) throws SQLException {
        try (
                CallableStatement stm = con.prepareCall("{call " + nombreProcedimiento + "()}")) {

            // Ejecutar el procedimiento almacenado
            stm.execute();

        } catch (SQLException e) {
            System.err.println("No se pudo conectar a la base de datos o se excedió el tiempo de espera.");
            e.printStackTrace();
        }
    }
}
