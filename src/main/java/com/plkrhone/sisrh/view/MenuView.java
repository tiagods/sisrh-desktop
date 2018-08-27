package com.plkrhone.sisrh.view;

import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by Tiago on 07/07/2017.
 */
public class MenuView extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //Icons estilo = Icons.getInstance();
            URL url = getClass().getResource("/fxml/Menu.fxml");
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Menu Principal");
            //stage.getIcons().add(new Image(estilo.getIcon().toString()));
            primaryStage.show();
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					System.exit(0);
				}
			});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] arg0) {
    	launch(arg0);
    }
}
