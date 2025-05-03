/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistasAuxiliares_hospital;

import clase.Conexion;
import clases_hospital.Comanda;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import reportes.Reporte;
import vpmedicaplaza.VPMedicaPlaza;

/**
 * FXML Controller class
 *
 * @author gamae
 */
public class AgregarComandasController implements Initializable {

    Conexion conexion = new Conexion();
    Connection con = conexion.conectar2();
    Alert alertaError = new Alert(Alert.AlertType.ERROR);
    Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alertaPrecaucion = new Alert(Alert.AlertType.WARNING);

    int idProducto;
    String nombre, Descripcion;
    double costo;
    double totalCuenta, cuentaAnterior;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txfCliente;
    @FXML
    private TextField txfProducto;
    @FXML
    private TableColumn<Comanda, String> clmProducto;
    @FXML
    private TableColumn<Comanda, Integer> clmCantidad;
    @FXML
    private TableColumn<Comanda, Double> clmPrecio;
    @FXML
    private Text txtTotal;
    @FXML
    private TextField txfCantidad;
    @FXML
    private Text txtSubtotal;
    @FXML
    private TextField txfIva;
    @FXML
    private TableView<Comanda> tabla;
    @FXML
    private TextField txfObservaciones;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Buscador();

        } catch (SQLException ex) {
            Logger.getLogger(AgregarComandasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cantidadRestriccion();
    }

    @FXML
    private void actionBtnAgregar(ActionEvent event) throws SQLException {
        String cliente = null;
        String observaciones = null;
        Connection connection = null;
        if (tabla.getItems().isEmpty()) {
            alertaError.setTitle("ERROR!");
            alertaError.setHeaderText("SIN PRODUCTOS");
            alertaError.setContentText("POR FAVOR SELECCIONE PRIMERO UN PRODUCTO");
            alertaError.showAndWait();
        } else {
            if (txfCliente.getText().isEmpty()) {
                cliente = "CLIENTE";
            } else {
                cliente = txfCliente.getText().toUpperCase();
            }
            if (txfObservaciones.getText().isEmpty()) {
                observaciones = "SIN OBSERVACIONES";
            } else {
                observaciones = txfObservaciones.getText().toUpperCase();
            }

            try {
                connection = conexion.conectar2();
                incertarComanda(cliente, observaciones);

            } catch (Exception e) {
            } finally {
                if (connection != null) {
                    connection.close(); // Cierra la conexión
                }
            }
        }
    }

    @FXML
    private void actionBtnSalir(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionAgregarProducto(ActionEvent event) throws SQLException {
        if (validarDatos()) {
            llenarTabla();
            actualizarCostos();
            txfCantidad.clear();
            txfProducto.clear();
        } else {
            alertaError.setTitle("ERROR!");
            alertaError.setHeaderText("CAMPOS VACIOS");
            alertaError.setContentText("POR FAVOR VERIFIQUE\n(1) EL NOMBRE DEL PRODUCTO\n(2) LA CANTIDAD QUE DESEA INGRESAR");
            alertaError.showAndWait();
        }

    }

    private void Buscador() throws SQLException {
        Connection connection = null;
        ArrayList<Comanda> arrayAlimento = new ArrayList<>();
        Comanda comanda;

        try {
            connection = conexion.conectar2();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.id, p.nombre,p.precio, p.descripcion FROM paquetesalimentos p");

            while (rs.next()) {
                comanda = new Comanda(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
                arrayAlimento.add(comanda);
            }
            AutoCompletionBinding<Comanda> comandas = TextFields.bindAutoCompletion(txfProducto, arrayAlimento);
            comandas.setPrefWidth(500);

            comandas.setOnAutoCompleted(event -> {
                Comanda selectComanda = event.getCompletion();
                idProducto = selectComanda.getIdProducto();
                nombre = selectComanda.getNombreProducto();
                costo = selectComanda.getCostoUnitario();
                Descripcion = selectComanda.getDescripcion();
            });

        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.close(); // Cierra la conexión
            }
        }
    }

    private boolean validarDatos() {
        if (txfProducto.getText().isEmpty() || txfCantidad.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void cantidadRestriccion() {
        txfCantidad.setOnKeyTyped(event -> {
            String caracter = event.getCharacter();
            if (!caracter.matches("[0-9]")) {
                event.consume(); // Consumimos el evento si no es un número o un punto
            }
        });
    }

    private void llenarTabla() throws SQLException {
        Comanda comanda;
        ObservableList<Comanda> comandas = FXCollections.observableArrayList();

        clmProducto.setCellValueFactory(new PropertyValueFactory("nombreProducto"));
        clmCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        clmPrecio.setCellValueFactory(new PropertyValueFactory("costoUnitario"));

        comanda = new Comanda(idProducto, nombre, costo, Descripcion, Integer.valueOf(txfCantidad.getText()));
        comandas.add(comanda);
        tabla.getItems().add(comanda);

        try {

        } catch (Exception e) {
        } finally {
//            if (connection != null) {
//                connection.close(); // Cierra la conexión
//            }
        }
    }

    private void actualizarCostos() {
        double costoActual = costo * Double.valueOf(txfCantidad.getText()); // Calcular el costo actual

        totalCuenta += costoActual; // Sumar el costo actual al costo acumulado

        txtTotal.setText(String.valueOf(totalCuenta)); // Actualizar el valor del txtTotal
        txtSubtotal.setText(String.valueOf(totalCuenta));
    }

    private String generarFolio() {
        String folio = "VP-CMA-";
        // Obtener la fecha, hora y segundos actuales
        LocalDateTime now = LocalDateTime.now();

        // Obtener los valores individuales
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        folio = "VP-CMA-" + year + "" + hour + "" + second + "";
        return folio;
    }

    private void incertarComanda(String cliente, String observaciones) throws SQLException {
        Connection connection = null;
        int idComanda = 0;
        int numProductos = 0;
        for (int i = 0; i < tabla.getItems().size(); i++) {
            numProductos = numProductos + tabla.getItems().get(i).getCantidad();
        }
        String sql = "INSERT INTO comandas(id_comanda,folio ,fecha ,id_cliente ,cliente ,cantidad_productos ,subtotal ,iva ,total ,recibe ,observacion ,id_estatus ,id_usuario_modificacion ,fecha_modificacion)VALUES\n"
                + "(  0 ,? ,NOW(),? ,? ,? ,?  ,? ,? ,? ,?,? ,? ,NOW());";

        String sqlCoamndaDetalle = "INSERT INTO comanda_detalle\n"
                + "(\n"
                + "  id_comanda\n"
                + " ,id_producto\n"
                + " ,cantidad\n"
                + " ,costo_unitario\n"
                + " ,subtotal\n"
                + " ,iva\n"
                + " ,total\n"
                + " ,id_usuario_modificacion\n"
                + " ,fecha_modificacion\n"
                + ")\n"
                + "VALUES\n"
                + "(\n"
                + "  ? \n"
                + " ,? \n"
                + " ,? \n"
                + " ,? \n"
                + " ,? \n"
                + " ,? \n"
                + " ,? \n"
                + " ,? \n"
                + " ,NOW() \n"
                + ");";

        try {
            connection = conexion.conectar2(); // Asignar la conexión
            String folio = generarFolio();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, folio);
                statement.setInt(2, 0);
                statement.setString(3, cliente);
                statement.setInt(4, numProductos);
                statement.setDouble(5, Double.valueOf(txtSubtotal.getText()));
                statement.setDouble(6, Double.valueOf(txfIva.getText()));
                statement.setDouble(7, Double.valueOf(txtTotal.getText()));
                statement.setString(8, cliente);
                statement.setString(9, observaciones);
                statement.setInt(10, 1);//estatus de la comanda padre
                statement.setInt(11, VPMedicaPlaza.userSystem);
                statement.executeUpdate();
            }

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT c.id_comanda FROM comandas c WHERE c.folio ='" + folio + "'");

            while (rs.next()) {
                idComanda = rs.getInt(1);
            }

            try (PreparedStatement statement = connection.prepareCall(sqlCoamndaDetalle)) {
                for (int i = 0; i < tabla.getItems().size(); i++) {
                    statement.setInt(1, idComanda);
                    statement.setInt(2, tabla.getItems().get(i).getIdProducto());
                    statement.setInt(3, tabla.getItems().get(i).getCantidad());
                    statement.setDouble(4, tabla.getItems().get(i).getCostoUnitario());
                    statement.setDouble(5, tabla.getItems().get(i).getCantidad() * tabla.getItems().get(i).getCostoUnitario());
                    statement.setDouble(6, 0);
                    statement.setDouble(7, tabla.getItems().get(i).getCantidad() * tabla.getItems().get(i).getCostoUnitario());
                    statement.setInt(8, VPMedicaPlaza.userSystem);
                    statement.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            alertaInfo.setTitle("EXITO!");
            alertaInfo.setContentText("PROCEDIMIENTO REALIZADO");
            alertaInfo.setHeaderText(null);
            alertaInfo.showAndWait();
            generarTicket(idComanda);
            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la traza de errores
        } finally {
            if (connection != null) {
                connection.close(); // Cierra la conexión
            }
        }
    }

    public void generarTicket(int a) throws JRException {
        Reporte reporte = new Reporte("Rpt_TicketVentaAlimentos");
        reporte.generarReporteTicket(a);

    }

}
