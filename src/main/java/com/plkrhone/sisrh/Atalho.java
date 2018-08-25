package com.plkrhone.sisrh;

import java.io.IOException;

import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.controller.ControllerMenu;
import com.plkrhone.sisrh.model.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Atalho extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Stage stage = new Stage();
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
            //loader.setController(new ControllerMenu());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Acesso");
            stage.initModality(Modality.WINDOW_MODAL);
	        stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
            stage.show();
            
    	}catch (IOException ex) {
		    ex.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch();
	}
}
