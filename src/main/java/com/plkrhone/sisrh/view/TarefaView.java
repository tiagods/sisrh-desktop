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
public class TarefaView extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //Icons estilo = Icons.getInstance();
        	URL url = getClass().getResource("/fxml/Tarefa.fxml");
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Controle de Tarefas");
            //stage.getIcons().add(new Image(estilo.getIcon().toString()));
            primaryStage.show();
            StageList.getInstance().addScene(TarefaView.class, primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
