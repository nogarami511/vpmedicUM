/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles_um;

import clase.UM.Servicio;
import clase.UM.Tabulacion;
import clase.UM_DAO.ServicioDAO;
import clase.UM_DAO.TabuladorCatalogoDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;


/**
 *
 * @author theso
 */
public class ServicioCatalogoController implements Initializable {
    @FXML
    private TableColumn<Servicio, String> colEliminar;
    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private TableColumn<?, ?> colDescripcion;
    @FXML
    private TableColumn<?, ?> colMarca;
    @FXML
    private TableColumn<?, ?> colLote;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TableColumn<Servicio, String> colActualizar;
    @FXML
    private TableView<Servicio> tablaServicios;

    ObservableList<Servicio> obListServicios = FXCollections.observableArrayList();
    ServicioDAO servicioDAO;
   
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);
    
    String nombreTab;
    
    @FXML
    private void btnIngresarServicio(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        
    }
    

    
    public void recibirDatos(Tabulacion tab) {
        nombreTab = tab.getNombre();        
    }
    
    public void llenarTabla() throws SQLException{
        generarBotonActualizar();
        generarBotonEliminar();
        
        servicioDAO = new ServicioDAO();
        Servicio servicio = new Servicio();
        tablaServicios.getItems().clear();
        obListServicios.clear();
        colID.setCellValueFactory(new PropertyValueFactory<>("idTabServ"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colLote.setCellValueFactory(new PropertyValueFactory<>("lote"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        obListServicios.addAll(servicioDAO.ejecutarProcedimiento("listar", servicio));
        tablaServicios.setItems(obListServicios);
    }
    
    
    private void generarBotonActualizar(){
        
    }
    
    private void generarBotonEliminar(){
        Callback<TableColumn<Tabulacion, String>, TableCell<Tabulacion, String>> Eliminar = (TableColumn<Tabulacion, String> param) -> {
            final TableCell<Tabulacion, String> cell = new TableCell<Tabulacion, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button btnVer = new Button("");
                        Tabulacion tabulador = getTableView().getItems().get(getIndex());
                        ImageView imgVer = new ImageView("/img/icons/icons8-eliminar-30.png");
                        imgVer.setFitHeight(20);
                        imgVer.setFitWidth(20);

                        btnVer.setOnAction(event -> {
                            //AQUI VA LO NECESARIO PARA MANDAR A TRAER LA SIGUIENTE VISTA
                            alertaConfirmacion.setHeaderText(null);
                            alertaConfirmacion.setTitle("Eliminar a: " + tabulador.getNombre());
                            alertaConfirmacion.setContentText("¿Estás seguro de eliminar a: " + tabulador.getNombre() + " ?");
                            Optional<ButtonType> action = alertaConfirmacion.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                System.out.println("Se desactivo: " + tabulador.getNombre());
                                tabulador.setEstatus(false);
                                 tabuladorDAO = new TabuladorCatalogoDAO();
                                 tabuladorDAO.ejecutarProcedimiento("editar", tabulador);
                                 llenarTabla();
                                
                            }
                        });

                        setGraphic(btnVer);
                        setText(null);
                        btnVer.setGraphic(imgVer);
                    }
                }
            };
            return cell;
        };
        colEliminar.setCellFactory(Eliminar);
    }
    
}
