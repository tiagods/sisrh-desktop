/**
 * Created by Prolink on 20/07/2017.
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Enginer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage s) {


        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.load("https://www.catho.com.br/profissoes/assistente-de-service-desk");
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(newValue==Worker.State.SUCCEEDED){
                    String html = (String) engine.executeScript("document.documentElement.outerHTML");
//                    String[] linhas = html.split("\n");
//                    List<String> json = new ArrayList<>();
//                    boolean advance = false;
//                    for(String l : linhas){
//                        if(l.contains("<script type=\"application/ld+json\">")){
//                            advance = true;
//                        }
//                        if(advance){
//                            if(l.contains("\"@graph\"")) break;
//                            if(l.contains("\"Name\"")) {
//                                String[] strings = l.split(":");
//                                for(int i=0;i<strings.length; i++) {
//                                    if(i>0)
//                                        json.add(strings[i] +"");
//                                }
//                            }
//                            else if(l.contains("\"description\""))
//                            {
//                                String[] strings = l.split(":");
//                                for(int i=0;i<strings.length; i++) {
//                                    if(i>0)
//                                        json.add(strings[i] +"");
//                                }
//                            }
//                            else if(l.contains("\"baseSalary\"")) {
//                                String[] strings = l.split(":");
//                                for(int i=0;i<strings.length; i++) {
//                                    if(i>0)
//                                        json.add(strings[i] +"");
//                                }
//                            }
//                        }
//                    }
//                    json.forEach(c->{
//                        System.out.println(c);
//                    });
//                 JSONObject json = new JSONObject(html.substring(html.indexOf("{\n" +
//                         "    \"@context\":")));
//                System.out.println(json.keySet());
                    String inicio="<script type=\"application/ld+json\">\n";
                    System.out.println(html.substring(html.indexOf(inicio)+inicio.length()));
                }
            }
        });
        s.setScene(new Scene(webView));
        // mostramos
        s.show();

    }
}

