/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.GenerarReabastos;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import vpmedicaplaza.VPMedicaPlaza;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class GenerarReabastosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    ObservableList<GenerarReabastos> listasGenerarReabastos = FXCollections.observableArrayList();
    private final ArrayList<GenerarReabastos> arraylistGenerarReabastos_almacenar_datos_iniciales = new ArrayList<>();
    DateTimeFormatter formatterClave = DateTimeFormatter.ofPattern("ddMMyyyy");
    private boolean lleanarArray = true;
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();

    @FXML
    private Button btnGenerar;
    @FXML
    private TableView<GenerarReabastos> tabla;
    @FXML
    private TableColumn colInsumo;
    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colPresentacion;
    @FXML
    private TableColumn colMedida;
    @FXML
    private TableColumn colAccion;
    @FXML
    private TableColumn<GenerarReabastos, Double> colPaquetes;
    @FXML
    private TableColumn colCajas;
    @FXML
    private TableColumn colUnidad;
    @FXML
    private TableColumn colCosto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
        tabla.setEditable(true);
        centrarColumnas();
    }

    @FXML
    private void accionGenerar(ActionEvent event) {
        con = conexion.conectar2();
        String sql = "{call atualizargenerarreabastos(?,?,?,?,?,?,?,?)}";
        String sqlreabastopedir = "{call ingresarReabastos (?,?,?,?,?)}";
        String listareabasto = "", listareabastos_parciales = "";
        String clave = generarClavePedido();
        CallableStatement stmt = null;
        try {
            for (int i = 0; i < listasGenerarReabastos.size(); i++) {
                if (listasGenerarReabastos.get(i).isGenerar()) {
                    stmt = con.prepareCall(sql);
                    stmt.setInt(1, listasGenerarReabastos.get(i).getId_reabastos());
                    stmt.setDouble(2, listasGenerarReabastos.get(i).getPaquetes());
                    stmt.setDouble(3, listasGenerarReabastos.get(i).getCajas());
                    stmt.setDouble(4, listasGenerarReabastos.get(i).getFalta());
                    stmt.setDouble(5, listasGenerarReabastos.get(i).getCosto());
                    stmt.setInt(6, VPMedicaPlaza.userSystem);
                    if (listasGenerarReabastos.get(i).getPaquetes() == arraylistGenerarReabastos_almacenar_datos_iniciales.get(i).getPaquetes()) {
                        stmt.setInt(7, 2);
                        if (listareabasto.equals("")) {
                            listareabasto = "" + listasGenerarReabastos.get(i).getId_reabastos();
                        } else {
                            listareabasto = listareabasto + "," + listasGenerarReabastos.get(i).getId_reabastos();
                        }
                    } else {
                        stmt.setInt(7, 3);
                        if (listareabasto.equals("")) {
                            listareabastos_parciales = "" + listasGenerarReabastos.get(i).getId_reabastos();
                        } else {
                            listareabastos_parciales = listareabastos_parciales + "," + listasGenerarReabastos.get(i).getId_reabastos();
                        }
                    }
                    stmt.setString(8, clave);
                    stmt.execute();
                    System.out.println("PARA: " + listasGenerarReabastos.get(i).getNombre() + " SE HA GENERADOPEDIDO");
                } else {
                    System.err.println("PARA: " + listasGenerarReabastos.get(i).getNombre() + " NO HAY PEDIDO");
                }
            }
            stmt = con.prepareCall(sqlreabastopedir);
            System.out.println(sqlreabastopedir);
            stmt.setString(1, clave);
            stmt.setString(2, listareabasto);
            stmt.setString(3, listareabastos_parciales);
            stmt.setInt(4, 0);
            stmt.setInt(5, userSystem);
            stmt.execute();
            alertaConfirmacion.setTitle("Peticion de rebastos Generada");
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setContentText("La peticion de rebastos ha sido generada correctamente.");
            alertaConfirmacion.showAndWait();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void llenarTabla() {
        con = conexion.conectar2();
        String callSqlSlecet = "{call seleccionarreabastos()}";

        CallableStatement stmt = null;
        GenerarReabastos generarrabasto;
        try {
            stmt = con.prepareCall(callSqlSlecet);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                generarrabasto = new GenerarReabastos();
                generarrabasto.setId_reabastos(rs.getInt(1));
                generarrabasto.setId_insumo(rs.getInt(2));
                generarrabasto.setNombre(rs.getString(3));
                generarrabasto.setMarca(rs.getString(4));
                generarrabasto.setPresentacion(rs.getString(5));
                generarrabasto.setMedida(rs.getString(6));
                generarrabasto.setPaquetes(rs.getInt(7));
                generarrabasto.setCajas(rs.getInt(8));
                generarrabasto.setFalta(rs.getInt(9));
                generarrabasto.setCosto(rs.getDouble(10));
                generarrabasto.setId_estatus_generar_reporte(rs.getInt(11));
                generarrabasto.setGenerar(rs.getBoolean(12));
                generarrabasto.setCantidad_cajas_unidad_paquete(rs.getInt(13));
                generarrabasto.setCantidad_unidadades_paquete(rs.getInt(14));
                generarrabasto.setCosto_unidad_paquete(rs.getDouble(15));
                listasGenerarReabastos.add(generarrabasto);
                if (lleanarArray) {
                    GenerarReabastos generarrabastoArray = new GenerarReabastos(generarrabasto.getId_insumo(), generarrabasto.getFalta(), generarrabasto.getCajas(), generarrabasto.getPaquetes(), generarrabasto.getCosto());
                    arraylistGenerarReabastos_almacenar_datos_iniciales.add(generarrabasto);
                }
            }
            lleanarArray = false;

            columnas();

            tabla.setItems(listasGenerarReabastos);
            con.close();

        } catch (Exception e) {
        }
    }

    private void columnas() {
        colInsumo.setCellValueFactory(new PropertyValueFactory("nombre"));
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colPresentacion.setCellValueFactory(new PropertyValueFactory("presentacion"));
        colMedida.setCellValueFactory(new PropertyValueFactory("medida"));
        colPaquetes.setCellValueFactory(new PropertyValueFactory("paquetes"));
        colCajas.setCellValueFactory(new PropertyValueFactory("cajas"));
        colUnidad.setCellValueFactory(new PropertyValueFactory("falta"));
        colCosto.setCellValueFactory(new PropertyValueFactory("costo"));

        colPaquetes.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colPaquetes.setOnEditCommit(event -> {
            // obtener el objeto Consumo que está siendo editado
            GenerarReabastos genreab = event.getTableView().getItems().get(event.getTablePosition().getRow());
            // actualizar el valor de cantidad en el objeto Consumo
            genreab.setPaquetes(event.getNewValue());
            //
            genreab.setFalta(genreab.getPaquetes() * (genreab.getCantidad_cajas_unidad_paquete() * genreab.getCantidad_unidadades_paquete()));
            genreab.setCajas(genreab.getPaquetes() * genreab.getCantidad_cajas_unidad_paquete());
            genreab.setCosto(genreab.getPaquetes() * genreab.getCosto_unidad_paquete());
            System.out.println(event.getTablePosition().getRow());
            tabla.refresh();
        });

        Callback<TableColumn<GenerarReabastos, String>, TableCell<GenerarReabastos, String>> confirmar = (TableColumn<GenerarReabastos, String> param) -> {
            final TableCell<GenerarReabastos, String> cell = new TableCell<GenerarReabastos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final RadioButton rdbReabastecer = new RadioButton();
                        GenerarReabastos grSlt = getTableView().getItems().get(getIndex());
                        rdbReabastecer.setSelected(grSlt.isGenerar());
                        rdbReabastecer.setOnAction(event -> {
                            if (grSlt.isGenerar()) {
                                grSlt.setGenerar(false);
                            } else {
                                grSlt.setGenerar(true);
                            }
                            System.out.println(grSlt.isGenerar());
                            try {
                                String sql = "{call actualizarreabastospedido()}";
                                CallableStatement stmt = con.prepareCall(sql);
                                stmt.setInt(1, grSlt.getId_reabastos());
                                stmt.setBoolean(2, grSlt.isGenerar());
                                stmt.setInt(3, VPMedicaPlaza.userSystem);
                                stmt.execute();
                                tabla.refresh();
                            } catch (Exception e) {
                            }
                        });
                        setGraphic(rdbReabastecer);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        colAccion.setCellFactory(confirmar);

        colPaquetes.setEditable(true);

    }

    private String generarClavePedido() {
        LocalDate currentDate = LocalDate.now();
        String rett = currentDate.format(formatterClave);
        return "reabasto" + rett;
    }

    private void centrarColumnas() {
        colInsumo.setStyle("-fx-alignment: CENTER;");
        colMarca.setStyle("-fx-alignment: CENTER;");
        colPresentacion.setStyle("-fx-alignment: CENTER;");
        colMedida.setStyle("-fx-alignment: CENTER;");
        colPaquetes.setStyle("-fx-alignment: CENTER;");
        colCajas.setStyle("-fx-alignment: CENTER;");
        colUnidad.setStyle("-fx-alignment: CENTER;");
        colAccion.setStyle("-fx-alignment: CENTER;");
    }
}
