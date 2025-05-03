/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles_um;

import clase.UM.Tabulacion;
import clase.UM_DAO.TabuladorCatalogoDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class TabuladorCatalogoController implements Initializable {

    @FXML
    private TableView<Tabulacion> tablaTabulacion;
    @FXML
    private TableColumn<?, ?> nombreTabulacion;
    @FXML
    private TableColumn<?, ?> notaTabulacion;
    @FXML
    private TableColumn<?, ?> colArmado;
    @FXML
    private TableColumn<?, ?> colEliminar;
    @FXML
    private TableColumn<?, ?> ColEditar;

    ObservableList<Tabulacion> obListTabuladores = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarTabla();
    }

    public void llenarTabla() {
        TabuladorCatalogoDAO tabuladorDAO = new TabuladorCatalogoDAO();
        Tabulacion tabulador = new Tabulacion();
        tablaTabulacion.getItems().clear();
        obListTabuladores.clear();
        nombreTabulacion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        notaTabulacion.setCellValueFactory(new PropertyValueFactory<>("nota"));

        obListTabuladores.addAll(tabuladorDAO.ejecutarProcedimiento("listar", tabulador));
        tablaTabulacion.setItems(obListTabuladores);

    }
    
    public void loadPageIngresarTabulacion() throws IOException{
        
        System.out.println("loadPAGE INICIADO");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas_UM/IngresarTabulador.fxml"));
        Parent root = loader.load();

        Stage nuevoStage = new Stage();
        nuevoStage.setTitle("INGRESAR NUEVA TABULACIÓN");  // Usa el título correcto
        nuevoStage.setScene(new Scene(root));
        nuevoStage.initModality(Modality.APPLICATION_MODAL);
        nuevoStage.setResizable(false);

        // Mostrar de forma modal (bloquea la ventana anterior hasta que se cierre esta)
        nuevoStage.showAndWait();

        
    }

    @FXML
    private void btnIngresarTabulacion(ActionEvent event) throws IOException {
        loadPageIngresarTabulacion();
    }


}
