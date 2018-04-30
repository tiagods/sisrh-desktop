import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by Tiago on 27/07/2017.
 */
public class TesteDialogo2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Criação de grupos");
        dialog.setHeaderText("Crie um novo departamento:");
        dialog.setContentText("Por favor entre com um novo nome:");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(result.get().trim().length()==0){
                System.out.print("Insira um nome para o departamento");
            }
            else
                System.out.println("Hello: "+result.get());
        }
    }
}
