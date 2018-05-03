import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Tiago on 26/07/2017.
 */
public class TesteDialogReplace extends Application{
    @SuppressWarnings("unchecked")
	@Override
    public void start(Stage primaryStage) throws Exception {

        TableView<Vault> tableView = new TableView<>();
        Label mensagem = new Label();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Formulario Pré-Preenchido");
        alert.setHeaderText("Informe os seguinte campos Chave dentro do arquivo word(.DOC) ");
        BorderPane borderPane = new BorderPane();
        ObservableList<Vault> list = FXCollections.observableList(
                Arrays.asList(
                        new Vault("Nome:","{nome}"),
                        new Vault("Cargo:","{cargo}"),
                        new Vault("Data de Hoje:","{data}"),
                        new Vault("Telefone:","{telefone}"),
                        new Vault("Celular:","{celular}"),
                        new Vault("Estato Civil:","{estadocivil}"),
                        new Vault("Sexo:","{sexo}"),
                        new Vault("Idade:","{idade}"),
                        new Vault("Escolaridade:","{escolaridade}"),
                        new Vault("Endereço:","{endereco}"),
                        new Vault("E-mail:","{email}")
                ));
        TableColumn<Vault, String> colunaNome = new TableColumn<>("Valor");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(200);
        TableColumn<Vault, String> colunaValor = new TableColumn<>("Chave");
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colunaValor.setPrefWidth(200);
        colunaValor.setCellFactory((TableColumn<Vault,String> param) -> new TableCell<Vault, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                final TextField textField = new TextField();
                textField.setEditable(false);
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                    setGraphic(null);
                }else{
                    textField.setText(item);
                    setText(null);
                    setGraphic(textField);
                }
            }
        });
        TableColumn<Vault, String> colunaCopy = new TableColumn<>("");
        colunaCopy.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaCopy.setPrefWidth(30);
        colunaCopy.setCellFactory((TableColumn<Vault, String> param) -> {
            return new TableCell<Vault, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    final Button button = new Button("Copiar");
                    super.updateItem(item, empty);
                    button.setPrefWidth(25);
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Vault vault = tableView.getItems().get(getIndex());
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(new StringSelection(vault.getValor()), null);
                        }
                    });
                    setText(null);
                    setGraphic(button);
                }
            };
        });
        tableView.getColumns().addAll(colunaNome,colunaValor,colunaCopy);
        tableView.getItems().addAll(list);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        borderPane.setCenter(tableView);
        borderPane.setBottom(mensagem);
        borderPane.setPrefSize(400,400);
        alert.getDialogPane().setContent(borderPane);
        alert.showAndWait();
    }
    public class Vault{
        public Vault(String nome, String valor){
            this.nome = nome;
            this.valor = valor;
        }
        public String getNome() {
            return nome;
        }
        public void setNome(String nome) {
            this.nome = nome;
        }
        public String getValor() {
            return valor;
        }
        public void setValor(String valor) {
            this.valor = valor;
        }
        private String nome;
        private String valor;

    }
}
