/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores_hospital;

import clase.Conexion;
import clases_hospital.AutorizarRabastosInsumos;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import vistasAuxiliares_hospital.VisualizarPedidoReabastosController;
import static vpmedicaplaza.VPMedicaPlaza.userSystem;

/**
 * FXML Controller class
 *
 * @author alfar
 */
public class AutorizarReabastosController implements Initializable {

    Alert alertaSuccess = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaCuidado = new Alert(Alert.AlertType.WARNING);
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    ObservableList<AutorizarRabastosInsumos> autorizarRabastosInsumos = FXCollections.observableArrayList();

    @FXML
    private TableView<AutorizarRabastosInsumos> tabla;
    @FXML
    private TableColumn colClave;
    @FXML
    private TableColumn colPInsumosC;
    @FXML
    private TableColumn colPInsumosP;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colFechaAu;
    @FXML
    private TableColumn colVizualizar;
    @FXML
    private TableColumn colAutorizar;
    @FXML
    private TableColumn colReabastecer;
    @FXML
    private TableColumn colDesautorizar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(AutorizarReabastosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pintarTabla();
        centrarTextoTabla();
    }

    private void llenarTabla() throws SQLException {
        con = conexion.conectar2();
        this.tabla.getItems().clear();
        this.autorizarRabastosInsumos.clear();
        String sql = "SELECT * FROM autorizar_reabastos";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        AutorizarRabastosInsumos autorizarRabastoInsumo;
        while (rs.next()) {
            autorizarRabastoInsumo = new AutorizarRabastosInsumos();
            autorizarRabastoInsumo.setId_AtorizarRabasotsInsumos(rs.getInt(1));
            autorizarRabastoInsumo.setClave_reabastos(rs.getString(2));
            autorizarRabastoInsumo.setLista_id_rabastos(rs.getString(3));
            autorizarRabastoInsumo.setLista_rabastos(listaPedidoReabastos(rs.getString(3)));
            autorizarRabastoInsumo.setLista_id_reabastos_parciales(rs.getString(4));
            autorizarRabastoInsumo.setLista_rabastos_parciales(listaPedidoReabastosParcial(rs.getString(4)));
            autorizarRabastoInsumo.setId_estatus(rs.getInt(5));
            autorizarRabastoInsumo.setStringEstatus(sEstatus(rs.getInt(5)));
            autorizarRabastoInsumo.setFecha_autorizacion(rs.getDate(6));
            autorizarRabastoInsumo.setId_usario_autrorizacion(rs.getInt(7));
            autorizarRabastosInsumos.add(autorizarRabastoInsumo);
        }

        colClave.setCellValueFactory(new PropertyValueFactory("clave_reabastos"));
        colPInsumosC.setCellValueFactory(new PropertyValueFactory("lista_rabastos"));
        colPInsumosP.setCellValueFactory(new PropertyValueFactory("lista_rabastos_parciales"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("stringEstatus"));
        colFechaAu.setCellValueFactory(new PropertyValueFactory("formatterFecha_autorizacion"));

        generarBotonesTabla();

        tabla.setItems(autorizarRabastosInsumos);

        con.close();
    }

    private String listaPedidoReabastos(String listaId) {
        String returnListapedidosR = "";
        if (listaId.equals("")) {
            returnListapedidosR = "------";
        } else {
            String[] CadenalistaId = listaId.split(",");
            for (String idString : CadenalistaId) {
                String sql = "SELECT i.nombre FROM generarreabastos g INNER JOIN insumos i ON g.id_insumo = i.id WHERE g.id = ?";
                try {
                    PreparedStatement stmt = con.prepareStatement(sql);
                    
                    stmt.setInt(1, Integer.parseInt(idString));
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        returnListapedidosR = returnListapedidosR + " " + rs.getString(1);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AutorizarReabastosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return returnListapedidosR;
    }

    private String listaPedidoReabastosParcial(String listaId) {
        String returnListapedidosR = "";
        if (listaId.equals("")) {
            returnListapedidosR = "------";
        } else {
            String[] CadenalistaId = listaId.split(",");
            for (String idString : CadenalistaId) {
                String sql = "SELECT i.nombre FROM generarreabastos g INNER JOIN insumos i ON g.id_insumo = i.id WHERE g.id = ?";
                try {
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setInt(1, Integer.parseInt(idString));
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        returnListapedidosR = returnListapedidosR + " " + rs.getString(1);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AutorizarReabastosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return returnListapedidosR;
    }

    private void generarBotonesTabla() {
        /**
         * GENERAR EL BOTON AUTORIZAR REABASTOINSUMO EN LA TABLA
         */
        Callback<TableColumn<AutorizarRabastosInsumos, String>, TableCell<AutorizarRabastosInsumos, String>> autorizar = (TableColumn<AutorizarRabastosInsumos, String> param) -> {
            final TableCell<AutorizarRabastosInsumos, String> cell = new TableCell<AutorizarRabastosInsumos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnAutorizar = new Button("");
                        AutorizarRabastosInsumos autpagreab = getTableView().getItems().get(getIndex());
                        ImageView check = new ImageView("/img/icons/icons8-marca-de-verificación-50.png");
                        check.setFitHeight(20);
                        check.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON CONFIRMAR
                         */
                        btnAutorizar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de AUTORIZAR la nomina con clave: " + autpagreab.getClave_reabastos() + "?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    cambiarEstus(autpagreab.getClave_reabastos(), 1);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnAutorizar);
                        setText(null);
                        btnAutorizar.setGraphic(check);
                        if (esCompra(autpagreab.getClave_reabastos())) {
                            switch (autpagreab.getId_estatus()) {
                                case 0:
                                    btnAutorizar.setDisable(false);
                                    break;
                                case 1:
                                    btnAutorizar.setDisable(true);
                                    break;
                                case 2:
                                    btnAutorizar.setDisable(true);
                                    break;
                            }
                        } else {
                            btnAutorizar.setDisable(true);
                        }
                    }
                }
            };
            return cell;
        };

        colAutorizar.setCellFactory(autorizar);

        /**
         * GENERAR EL BOTON AUTORIZAR VISUALIZAR EN LA TABLA
         */
        Callback<TableColumn<AutorizarRabastosInsumos, String>, TableCell<AutorizarRabastosInsumos, String>> visualizar = (TableColumn<AutorizarRabastosInsumos, String> param) -> {
            final TableCell<AutorizarRabastosInsumos, String> cell = new TableCell<AutorizarRabastosInsumos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVizualizar = new Button("");
                        AutorizarRabastosInsumos autpagreab = getTableView().getItems().get(getIndex());
                        ImageView ver = new ImageView("/img/icons/icons8-ver-archivo-48.png");
                        ver.setFitHeight(20);
                        ver.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON Vizualizar
                         */
                        btnVizualizar.setOnAction(event -> {
                            try {
                                FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vistasAuxiliares_hospital/VisualizarPedidoReabastos.fxml"));
                                Parent root = fxml.load();
                                Scene scene = new Scene(root);
                                VisualizarPedidoReabastosController vprc = fxml.getController();
                                //Mandamos a llamar el metodo Cita moficiar de la vista CITAQUIROFANONUEVO
                                vprc.calvePedido(autpagreab.getClave_reabastos());
                                vprc.listaInsumos(autpagreab.getLista_id_rabastos(), autpagreab.getLista_id_reabastos_parciales());
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.setResizable(false);
                                stage.setScene(scene);
                                stage.showAndWait();
                                llenarTabla();
                                pintarTabla();
                            } catch (IOException ex) {
                                Logger.getLogger(AutorizarReabastosController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(AutorizarReabastosController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        setGraphic(btnVizualizar);
                        setText(null);
                        btnVizualizar.setGraphic(ver);
                    }
                }
            };
            return cell;
        };

        colVizualizar.setCellFactory(visualizar);

        /**
         * GENERAR EL BOTON DE PAGO REABASTOINSUMO EN LA TABLA
         */
        Callback<TableColumn<AutorizarRabastosInsumos, String>, TableCell<AutorizarRabastosInsumos, String>> reabastecer = (TableColumn<AutorizarRabastosInsumos, String> param) -> {
            final TableCell<AutorizarRabastosInsumos, String> cell = new TableCell<AutorizarRabastosInsumos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnPago = new Button("");
                        AutorizarRabastosInsumos autpagreab = getTableView().getItems().get(getIndex());

                        ImageView pago = new ImageView("/img/icons/icons8-paga-48.png");
                        pago.setFitHeight(20);
                        pago.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON PAGO
                         */
                        btnPago.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estás seguro de PAGAR la nómina con clave: " + autpagreab.getClave_reabastos() /*+ " de un valor de: $" + autpagreab.getTotal()*/ + " ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    cambiarEstus(autpagreab.getClave_reabastos(), 2);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                
                            }
                        });
                        setGraphic(btnPago);
                        setText(null);
                        btnPago.setGraphic(pago);
                        if (esCompra(autpagreab.getClave_reabastos())) {
                            switch (autpagreab.getId_estatus()) {
                                case 0:
                                    btnPago.setDisable(true);
                                    break;
                                case 1:
                                    btnPago.setDisable(false);
                                    break;
                                case 2:
                                    btnPago.setDisable(true);
                                    break;
                            }
                        } else {
                            btnPago.setDisable(true);
                        }

                    }
                }
            };
            return cell;
        };

        colReabastecer.setCellFactory(reabastecer);

        /**
         * GENERAR EL BOTON DE DESAUTORIZAR REABASTOINSUMO EN LA TABLA
         */
        Callback<TableColumn<AutorizarRabastosInsumos, String>, TableCell<AutorizarRabastosInsumos, String>> desautorizar = (TableColumn<AutorizarRabastosInsumos, String> param) -> {
            final TableCell<AutorizarRabastosInsumos, String> cell = new TableCell<AutorizarRabastosInsumos, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnDesautorizar = new Button("");
                        AutorizarRabastosInsumos autpagreab = getTableView().getItems().get(getIndex());
                        ImageView cancelar = new ImageView("/img/icons/icons8-cancelar-30.png");
                        cancelar.setFitHeight(20);
                        cancelar.setFitWidth(20);
                        /**
                         * EVENTO DEL BOTON PAGO
                         */
                        btnDesautorizar.setOnAction(event -> {

                            try {
                                alertaConfirmacion.setHeaderText(null);
                                alertaConfirmacion.setTitle("Confirmación");
                                alertaConfirmacion.setContentText("¿Estas seguro de  ?");
                                Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                                if (action.get() == ButtonType.OK) {
                                    cambiarEstus(autpagreab.getClave_reabastos(), 0);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                               
                            }
                        });
                        setGraphic(btnDesautorizar);
                        setText(null);
                        btnDesautorizar.setGraphic(cancelar);
                        if (esCompra(autpagreab.getClave_reabastos())) {
                            switch (autpagreab.getId_estatus()) {
                                case 0:
                                    btnDesautorizar.setDisable(true);
                                    break;
                                case 1:
                                    btnDesautorizar.setDisable(false);
                                    break;
                                case 2:
                                    btnDesautorizar.setDisable(false);
                                    break;
                            }
                        } else {
                            btnDesautorizar.setDisable(true);
                        }

                    }
                }
            };
            return cell;
        };

        colDesautorizar.setCellFactory(desautorizar);
    }

    private String sEstatus(int estatus) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT nombre FROM estatus_autorizacion_reabastos WHERE id = ?");
        stmt.setInt(1, estatus);
        ResultSet rs = stmt.executeQuery();
        String sestatus = "";

        if (rs.next()) {
            sestatus = rs.getString(1);
        }
        return sestatus;
    }

    private void cambiarEstus(String clave, int estatus) {
        con = conexion.conectar2();
        CallableStatement stmt;
        String callsql = "{call actualizarestatusautorizarReabasto(?,?,?)}";

        try {
            stmt = con.prepareCall(callsql);
            stmt.setString(1, clave);
            stmt.setInt(2, estatus);
            stmt.setInt(3, userSystem);
            stmt.execute();
            alertaConfirmacion.setHeaderText(null);
            alertaConfirmacion.setTitle("Confirmación");
            alertaConfirmacion.setContentText("NOMINA " + sEstatus(estatus) + " CORRECTAMENTE");
            alertaConfirmacion.showAndWait();
            llenarTabla();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pintarTabla() {
        tabla.setRowFactory(tv -> new TableRow<AutorizarRabastosInsumos>() {
            @Override
            public void updateItem(AutorizarRabastosInsumos item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getId_estatus() == 0) {
                    setStyle("-fx-background-color: #fbdc32; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//SIN AUTORIZAR
                } else if (item.getId_estatus() == 1) {
                    setStyle("-fx-background-color: #2ECC71; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//AUTORIZADO
                } else if (item.getId_estatus() == 2) {
                    setStyle("-fx-background-color: #E74C3C; -fx-table-cell-border-color: white; -fx-selection-bar: red;");//PAGOADO
                } else {
                    setStyle(" ");
                }

            }
        });
    }

    private boolean esCompra(String clave) {
        con = conexion.conectar2();
        String sql = "SELECT * FROM pedidos_reabastos_proveedor prp WHERE prp.clave_pedido = ? AND prp.id_estatus = 2";
        boolean isCompra = false;

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, clave);
            ResultSet rs = stmt.executeQuery();
            isCompra = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(AutorizarReabastosController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isCompra;
    }

    private void centrarTextoTabla() {
        colClave.setStyle("-fx-alignment: CENTER;");
        colPInsumosC.setStyle("-fx-alignment: CENTER;");
        colPInsumosP.setStyle("-fx-alignment: CENTER;");
        colEstatus.setStyle("-fx-alignment: CENTER;");
        colFechaAu.setStyle("-fx-alignment: CENTER;");
        colVizualizar.setStyle("-fx-alignment: CENTER;");
        colAutorizar.setStyle("-fx-alignment: CENTER;");
        colReabastecer.setStyle("-fx-alignment: CENTER;");
        colDesautorizar.setStyle("-fx-alignment: CENTER;");
    }

}
