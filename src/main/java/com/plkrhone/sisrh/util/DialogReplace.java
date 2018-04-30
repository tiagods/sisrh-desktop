package com.plkrhone.sisrh.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javafx.collections.ObservableList;
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

/**
 * Created by Tiago on 27/07/2017.
 */
public class DialogReplace extends Alert {
    public DialogReplace(AlertType alertType) {
        super(alertType);
    }
    @SuppressWarnings("unchecked")
	public Alert construir(ObservableList<Keys> list){
        TableView<Keys> tableView = new TableView<>();
        Label mensagem = new Label();
        setTitle("Formulario Pré-Preenchido");
        setHeaderText("Informe os seguinte campos Chave dentro do arquivo word(.DOC) ");
        BorderPane borderPane = new BorderPane();
        TableColumn<Keys, String> colunaNome = new TableColumn<>("Valor");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(200);
        TableColumn<Keys, String> colunaValor = new TableColumn<>("Chave");
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colunaValor.setPrefWidth(200);
        colunaValor.setCellFactory((TableColumn<Keys,String> param) -> new TableCell<Keys, String>() {
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
        TableColumn<Keys, String> colunaCopy = new TableColumn<>("");
        colunaCopy.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaCopy.setPrefWidth(30);
        colunaCopy.setCellFactory((TableColumn<Keys, String> param) -> {
            return new TableCell<Keys, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    final Button button = new Button("Copiar");
                    super.updateItem(item, empty);
                    button.setPrefWidth(25);
                    button.setOnAction(event -> {
                        Keys vault = tableView.getItems().get(getIndex());
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(new StringSelection(vault.getValor()), null);
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
        getDialogPane().setContent(borderPane);
        return this;
    }

}

