/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vpmedicaplaza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

/**
 *
 * @author PC
 */
public class VPMedicaPlaza extends Application {

    public static boolean animacionEjecutada = false;//para que solo se ejecute una vez la animacion de inicio
    public static int userSystem = 0;//identificador para saber qué usuario modifica
    public static String userNameSystem = "";//cargo
    public static int userArea = 0;
    public static  int corte_caja =0;
    public static String pass;

    @Override
    public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image("/img/icono.png"));
            stage.setTitle("VP Médica Plaza");
            stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
