package com.plkrhone.sisrh.view;

import java.net.URL;

import com.plkrhone.sisrh.config.StageList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Tiago on 07/07/2017.
 */
public class LoginView extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //Icons estilo = Icons.getInstance();
            URL url = getClass().getResource("/fxml/Login.fxml");
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Controle de Acesso");
            //stage.getIcons().add(new Image(estilo.getIcon().toString()));
            primaryStage.show();
            StageList.getInstance().addScene(LoginView.class, primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	launch(args);
    }
}
