    package clase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author PC
 */
public class Conexion {

    Connection conexion = null;
    Alert alertaError = new Alert(Alert.AlertType.ERROR);

//    public Connection conectar() 
//
//        try {
//            //DriverManager.setLoginTimeout(5);// 5 segundos de respuesta si no se obtine marca Exception
//            Class.forName("com.mysql.jdbc.Driver");
////          conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/vp_medica", "root", "");
//            conexion = DriverManager.getConnection("jdbc:mysql://ghregio.com:3306/ghregioc_vpmedica", "ghregioc_sistemas", "$Qvm4739b5pA");
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace(System.out);
//            alertaError.setHeaderText("ERROR EN CONEXION A BASE DE DATOS");
//            alertaError.setContentText("Verifique que tenga conexión a Internet\n"
//                    + "Caso contrario comuniquese a Sistemas Ext. 811");
//            alertaError.showAndWait();
//        }
//
//        return conexion;
//    }
    public Connection conectar2() {
        try {
            //DriverManager.setLoginTimeout(5);// 5 segundos de respuesta si no se obtine marca Exception
            Class.forName("com.mysql.jdbc.Driver");
//             conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ghregioc_vphospital", "root", "");
//            conexion = DriverManager.getConnection("jdbc:mysql://ghregio.com:3306/ghregioc_vphospital", "ghregioc_sistemas", "$Qvm4739b5pA");
//            conexion = DriverManager.getConnection("jdbc:mysql://vpmedica.entrydns.org:3306/ghregioc_vphospital", "userghr", "vp67890"); // SERVIDOR VP
//            conexion = DriverManager.getConnection("jdbc:mysql://vpmedica.entrydns.org:3306/ghregioc_vphospital_PRE_PRODUCCION", "userghr", "vp67890");
//            conexion = DriverManager.getConnection("jdbc:mysql://gcim.entrydns.org:3306/ghregioc_vphospital", "userghr", "12345");
            conexion = DriverManager.getConnection("jdbc:mysql://vpmedica.entrydns.org:3306/vpume", "userumvp", "12345");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);

            alertaError.setHeaderText("ERROR EN CONEXION A BASE DE DATOS");
            alertaError.setContentText("Verifique que tenga conexión a Internet\n"
                    + "Caso contrario comuniquese a Sistemas Ext. 811");
            alertaError.showAndWait();
        }

        return conexion;
    }
//
}
