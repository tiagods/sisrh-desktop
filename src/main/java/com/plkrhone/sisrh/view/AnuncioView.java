package com.plkrhone.sisrh.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by Tiago on 07/07/2017.
 */
public class AnuncioView extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //Icons estilo = Icons.getInstance();
            URL url = getClass().getResource("/fxml/Anuncio.fxml");
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Controle de Anuncios");
            //stage.getIcons().add(new Image(estilo.getIcon().toString()));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            //System.exit(0);
        }
    }
    public static void main(String[] args) {
		launch(args);
	}
}
