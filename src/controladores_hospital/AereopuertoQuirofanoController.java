/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.AereopuertoQuirofano;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AereopuertoQuirofanoController implements Initializable {

    ObservableList<AereopuertoQuirofano> aereopuertoQuirofanos = FXCollections.observableArrayList();
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    @FXML
    private JFXButton btnFecha;
    @FXML
    private TableView<AereopuertoQuirofano> tabla;
    @FXML
    private TableColumn colQuirofano;
    @FXML
    private TableColumn colDoctor;
    @FXML
    private TableColumn colInicio;
    @FXML
    private TableColumn colDuracion;
    @FXML
    private TableColumn colHoraExtra;
    @FXML
    private TableColumn colSalida;
    @FXML
    private TableColumn colCirugia;
    @FXML
    private TableColumn colEstado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO+
        llenarTabla();
        //            centrarTextoTabla();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), (event) -> {
            refreshTable();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaActual = LocalDate.now();
        String fechaFormateada = fechaActual.format(formato);
        btnFecha.setText("PROGRAMACIÓN QUIRÚRGICA " + fechaFormateada);
    }

    public void refreshTable() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    aereopuertoQuirofanos.clear();
                    aereopuertoQuirofanos.addAll(getData());
//                    llamarEventActulizacion();
                } catch (SQLException ex) {
                    Logger.getLogger(AereopuertoQuirofano.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private List<AereopuertoQuirofano> getData() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT c.id, c.id_quirofano, q.tipo_procedimiento, c.id_medico, c.hora_Inicio, TIMEDIFF(c.hora_Fin, c.hora_Inicio) AS duracion, TIMEDIFF(NOW(), c.hora_Fin) AS Hora_Extra, c.hora_Fin, c.cirugia, c.id_estaus_panel_informacion_quirofano, epiq.nombre FROM citasquirofano c INNER JOIN quirofanos q ON c.id_quirofano = q.nombre INNER JOIN estaus_panel_informacion_quirofano epiq ON c.id_estaus_panel_informacion_quirofano = epiq.id WHERE year(c.fecha) = YEAR(NOW()) AND DAY(c.fecha) = DAY(NOW());");
        AereopuertoQuirofano aereopuertoQuirofano;
        List<AereopuertoQuirofano> arq = new LinkedList<>();
        try {
            while (rs.next()) {
                //agregar los campos
                aereopuertoQuirofano = new AereopuertoQuirofano();
                aereopuertoQuirofano.setId_citaQuirfano(rs.getInt(1));
                aereopuertoQuirofano.setNombre_quirofano(rs.getString(2));
                aereopuertoQuirofano.setTipo_quirofano(rs.getString(3));
                aereopuertoQuirofano.setNombre_medico(rs.getString(4));
                aereopuertoQuirofano.setHora_inicio(rs.getString(5));
                aereopuertoQuirofano.setDuracion(rs.getString(6));
                aereopuertoQuirofano.setHora_extra(rs.getString(7));
                aereopuertoQuirofano.setHora_salida(rs.getString(8));
                aereopuertoQuirofano.setLista_nombre_cirugias(listaProcedimientos(rs.getString(9)));
                aereopuertoQuirofano.setId_estaus_panel_informacion_quirofano(rs.getInt(10));
                aereopuertoQuirofano.setNombre_estaus_panel_informacion_quirofano(rs.getString(11));
                arq.add(aereopuertoQuirofano);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arq;
    }

    private void llenarTabla() {
        this.tabla.getItems().clear();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT c.id, c.id_quirofano, q.tipo_procedimiento, c.id_medico, c.hora_Inicio, TIMEDIFF(c.hora_Fin, c.hora_Inicio) AS duracion, TIMEDIFF(NOW(), c.hora_Fin) AS Hora_Extra, c.hora_Fin, c.cirugia, c.id_estaus_panel_informacion_quirofano, epiq.nombre FROM citasquirofano c INNER JOIN quirofanos q ON c.id_quirofano = q.nombre INNER JOIN estaus_panel_informacion_quirofano epiq ON c.id_estaus_panel_informacion_quirofano = epiq.id WHERE year(c.fecha) = YEAR(NOW()) AND DAY(c.fecha) = DAY(NOW());");
            AereopuertoQuirofano aereopuertoQuirofano;
            while (rs.next()) {
                //agregar los campos
                aereopuertoQuirofano = new AereopuertoQuirofano();
                aereopuertoQuirofano.setId_citaQuirfano(rs.getInt(1));
                aereopuertoQuirofano.setNombre_quirofano(rs.getString(2));
                aereopuertoQuirofano.setTipo_quirofano(rs.getString(3));
                aereopuertoQuirofano.setNombre_medico(rs.getString(4));
                aereopuertoQuirofano.setHora_inicio(rs.getString(5));
                aereopuertoQuirofano.setDuracion(rs.getString(6));
                aereopuertoQuirofano.setHora_extra(rs.getString(7));
                aereopuertoQuirofano.setHora_salida(rs.getString(8));
                aereopuertoQuirofano.setLista_nombre_cirugias(listaProcedimientos(rs.getString(9)));
                aereopuertoQuirofano.setId_estaus_panel_informacion_quirofano(rs.getInt(10));
                aereopuertoQuirofano.setNombre_estaus_panel_informacion_quirofano(rs.getString(11));
                aereopuertoQuirofanos.add(aereopuertoQuirofano);
            }

            colQuirofano.setCellValueFactory(new PropertyValueFactory("nombre_quirofano"));
            colCirugia.setCellValueFactory(new PropertyValueFactory("lista_nombre_cirugias"));
            colDoctor.setCellValueFactory(new PropertyValueFactory("nombre_medico"));
            colInicio.setCellValueFactory(new PropertyValueFactory("hora_inicio"));
            colDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));
            colHoraExtra.setCellValueFactory(new PropertyValueFactory("hora_extra"));
            colSalida.setCellValueFactory(new PropertyValueFactory("hora_salida"));
            colEstado.setCellValueFactory(new PropertyValueFactory("nombre_estaus_panel_informacion_quirofano"));

            tabla.setItems(aereopuertoQuirofanos);
            aumentarFuenteTabla();
            pintarTabla();

        } catch (SQLException ex) {
            Logger.getLogger(AereopuertoQuirofanoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String listaProcedimientos(String idProcedimientos) throws SQLException {//NUEVO
        Statement stmt = con.createStatement();
        String[] idpro = idProcedimientos.split(",");

        String cirugia = "";

        for (String pro : idpro) {
            ResultSet rs = stmt.executeQuery("select nombre from procedimiento WHERE id =" + pro + "");
            if (rs.next()) {
                cirugia = cirugia + rs.getString(1) + " ";
            }
        }
        return cirugia;
    }

    private void aumentarFuenteTabla() {
        colQuirofano.setStyle("-fx-alignment: CENTER; -fx-font-size: 30px;");
        colCirugia.setStyle("-fx-alignment: CENTER; -fx-font-size: 30px;");
        colDoctor.setStyle("-fx-alignment: CENTER; -fx-font-size: 25px;");
        colInicio.setStyle("-fx-alignment: CENTER; -fx-font-size: 30px;");
        colDuracion.setStyle("-fx-alignment: CENTER; -fx-font-size: 30px;");
        colHoraExtra.setStyle("-fx-alignment: CENTER; -fx-font-size: 30px;");
        colSalida.setStyle("-fx-alignment: CENTER; -fx-font-size: 30px;");
        colEstado.setStyle("-fx-alignment: CENTER; -fx-font-size: 30px;");
    }

    private void llamarEventActulizacion() {
        try {
            String sql = "{call actualizarEstatusPanelInformacionQuirorano()}";
            
            CallableStatement stmt = con.prepareCall(sql);
            stmt.executeQuery();
//            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AereopuertoQuirofanoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarEstatusTabla() {

        int idmax = 0, idmin = 0;
        Set<String> nombresUnicos = new HashSet<>();

        for (AereopuertoQuirofano aereopuertoQuirofano : aereopuertoQuirofanos) {
            nombresUnicos.add(aereopuertoQuirofano.getNombre_quirofano());
        }
        List<String> nombresSinRepetir = new ArrayList<>(nombresUnicos);

        Statement stmt;
        PreparedStatement pstmt = null;
        ResultSet rs;

        try {
            String sqlMinMax = "SELECT MAX(id), MIN(id)  INTO max_id FROM citasquirofano WHERE YEAR(fecha) = YEAR(NOW()) AND MONTH(fecha) = MONTH(NOW()) AND DAY(fecha) = DAY(NOW());";
            String sqlExiste = "SELECT id FROM citasquirofano WHERE id_estatus = 2 AND id_quirofano = ? AND (YEAR(fecha) = YEAR(NOW()) AND MONTH(fecha) = MONTH(NOW()) AND DAY(fecha) = DAY(NOW()))";

            stmt = con.createStatement();
            rs = stmt.executeQuery(sqlMinMax);

            if (rs.next()) {
                idmax = rs.getInt(1);
                idmin = rs.getInt(2);
            }
            
            boolean existe;
            for (int i = 0; i < nombresSinRepetir.size(); i++) {
                pstmt = con.prepareStatement(sqlExiste);
                pstmt.setString(1, nombresSinRepetir.get(i));
                rs = pstmt.executeQuery();
                
                existe = rs.next();
                for (int j = idmin; j <= idmax; j++) {
                    if (existe) {
                        if(aTiempo(j)){
                            actulizarEstatusPanelInformacionQuierofano(4, j);
                        }else{
                            actulizarEstatusPanelInformacionQuierofano(1, j);
                        }
                    } else {
                        if(estatusSQL(j)){
                            actulizarEstatusPanelInformacionQuierofano(3, j);
                        }else if(true){}
                    }
                }

            }


        } catch (SQLException ex) {
            Logger.getLogger(AereopuertoQuirofanoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void actulizarEstatusPanelInformacionQuierofano(int id_estaus_panel_informacion_quirofano, int id){
        
    }
    
    private boolean aTiempo(int id) throws SQLException{
        String sql = "SELECT c.hora_Inicio FROM citasquirofano c WHERE id = i AND YEAR(fecha) = YEAR(NOW()) AND MONTH(fecha) = MONTH(NOW()) AND DAY(fecha) = DAY(NOW())) < CURTIME()";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
    
    private boolean estatusSQL(int id) throws SQLException{
        boolean respuesta;
        String sql = "SELECT IF((id_estaus_panel_informacion_quirofano < 4) AND (id_estaus_panel_informacion_quirofano < 1), 1, 0) AS estatusIF FROM citasquirofano WHERE id = ?;";
        PreparedStatement  stmt = con.prepareCall(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            respuesta = rs.getBoolean(1);
        }else{
            respuesta = false;
        }
        
        return respuesta;
    }
    
    private boolean retrasoSQL(int id) throws SQLException{
        boolean respuesta;
        String sql = "SELECT IF((TIMEDIFF(CURTIME(), hora_Fin) < '00:30:00'), 1, 0) AS hora  FROM citasquirofano WHERE id = ?;";
        PreparedStatement stmt = con.prepareCall(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        if(true){}
        
        return true;
    }

    private void pintarTabla() {
        tabla.setRowFactory(tv -> new TableRow<AereopuertoQuirofano>() {
            @Override
            public void updateItem(AereopuertoQuirofano item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getId_estaus_panel_informacion_quirofano() == 1) {
                    setStyle("-fx-background-color: #2ECC71; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//A TIEMPO
                } else if (item.getId_estaus_panel_informacion_quirofano() == 2) {
                    setStyle("-fx-background-color: #F1C40F; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//EN CIRUGIA
                } else if (item.getId_estaus_panel_informacion_quirofano() == 3) {
                    setStyle("-fx-background-color: #FF5733; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//RETRASADO
                } else if (item.getId_estaus_panel_informacion_quirofano() == 4) {
                    setStyle("-fx-background-color: #E60026; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//TARDE
                } else {
                    setStyle(" ");
                }

            }
        });
    }
    
//    private void relojAereopuertoQuirofano(){
//        
//        Timer tempor = new Timer();
//        tempor.schedule(new TemporizadorTarea(), 0, 1000);
//    }
    
//    static class TemporizadorTarea extends TimerTask {
//        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
//        public void run() {
//            Date horaActual = new Date();

//        }
//    }

}
