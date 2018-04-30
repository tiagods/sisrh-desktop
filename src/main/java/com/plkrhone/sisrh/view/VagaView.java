package com.plkrhone.sisrh.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

import com.plkrhone.sisrh.config.StageList;

/**
 * Created by Tiago on 07/07/2017.
 */
public class VagaView extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //Icons estilo = Icons.getInstance();
            URL url = getClass().getResource("/fxml/Vaga.fxml");
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Controle de Vagas");
            //stage.getIcons().add(new Image(estilo.getIcon().toString()));
            primaryStage.show();
            StageList.getInstance().addScene(VagaView.class, primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	launch(args);
    }
}
