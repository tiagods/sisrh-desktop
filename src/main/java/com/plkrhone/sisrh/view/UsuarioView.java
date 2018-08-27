package com.plkrhone.sisrh.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
/**
 * Created by Tiago on 07/08/2017.
 */
public class UsuarioView extends Application {

    @Override
    public void start(Stage primaryStage){
        try {
            //Icons estilo = Icons.getInstance();
            URL url = getClass().getResource("/fxml/Usuario.fxml");
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Controle de Usuarios");
            //stage.getIcons().add(new Image(estilo.getIcon().toString()));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	launch(args);
    }
}
