package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.model.Avaliacao;
import com.plkrhone.sisrh.model.avaliacao.AvaliacaoCondicao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.fxutils.maskedtextfield.MaskTextField;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AvaliacaoCadastroCondicaoController extends UtilsController implements Initializable{

    @FXML
    private Label lbTitulo;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private MaskTextField txDe;

    @FXML
    private MaskTextField txAte;

    @FXML
    private Label txIdCondicao;

    @FXML
    private Label txIndexTable;

    @FXML
    private JFXTextArea txDescricao;

    @FXML
    private TableView<AvaliacaoCondicao> tbPrincipal;

    private Stage stage;
    private Set<AvaliacaoCondicao> condicaoSet;
    private Avaliacao avaliacao;

    public AvaliacaoCadastroCondicaoController(Stage stage, Avaliacao avaliacao, Set<AvaliacaoCondicao> condicaoSet){
        this.stage = stage;
        this.condicaoSet=condicaoSet;
        this.avaliacao = avaliacao;
    }
    @FXML
    void incluir(ActionEvent event) {
        AvaliacaoCondicao ava = new AvaliacaoCondicao();
        ava.setAvaliacao(this.avaliacao);
        ava.setDe(Double.parseDouble(txDe.getText()));
        ava.setAte(Double.parseDouble(txAte.getText()));
        ava.setDescricao(txDescricao.getText());
        if(!txIdCondicao.getText().equals(""))
            ava.setId(Long.parseLong(txIdCondicao.getText()));
        if(!txIndexTable.getText().equals(""))
            tbPrincipal.getItems().set(Integer.parseInt(txIndexTable.getText()), ava);
        else tbPrincipal.getItems().add(ava);
        limpar();
    }
    private double converter(String value){
        value = value.trim();
        if(value.equals("")) return -1;
        else{
            try{
                double newValue = Double.parseDouble(value);
                return newValue;
            }catch (NumberFormatException e){
                alert(Alert.AlertType.ERROR,"Erro","","Verifique o valor informado "+value);
                return -1;
            }

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabela();
        tbPrincipal.getItems().addAll(condicaoSet);
    }
    private void limpar(){
        tbPrincipal.refresh();
        txIdCondicao.setText("");
        txIndexTable.setText("");
        txDe.setText("");
        txAte.setText("");
        txDescricao.setText("");
        condicaoSet = tbPrincipal.getItems().stream().collect(Collectors.toSet());

    }
    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }
    void tabela() {
        TableColumn<AvaliacaoCondicao, Long> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

        TableColumn<AvaliacaoCondicao, Double> colunaDe = new TableColumn<>("De");
        colunaDe.setCellValueFactory(new PropertyValueFactory<>("de"));
        colunaDe.setPrefWidth(60);

        TableColumn<AvaliacaoCondicao, Double> colunaAte = new TableColumn<>("Até");
        colunaAte.setCellValueFactory(new PropertyValueFactory<>("ate"));
        colunaAte.setPrefWidth(60);

        TableColumn<AvaliacaoCondicao, String> colunaDescricao = new TableColumn<>("Descricao");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaDescricao.setCellFactory((TableColumn<AvaliacaoCondicao, String> param) -> {
            final TableCell<AvaliacaoCondicao, String> cell = new TableCell<AvaliacaoCondicao, String>() {
                final JFXTextArea textArea = new JFXTextArea();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        textArea.setText(item);
                        setGraphic(textArea);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        colunaDescricao.setPrefWidth(200);
        TableColumn<AvaliacaoCondicao, String> colunaEditar = new TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaEditar.setCellFactory((TableColumn<AvaliacaoCondicao, String> param) -> {
            final TableCell<AvaliacaoCondicao, String> cell = new TableCell<AvaliacaoCondicao, String>() {
                final JFXButton button = new JFXButton();
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btDefault");
                        try {
                            buttonTable(button, IconsEnum.BUTTON_EDIT);
                        } catch (IOException e) {
                        }
                        button.setOnAction(event -> {
                            AvaliacaoCondicao ac = tbPrincipal.getItems().get(getIndex());
                            txIdCondicao.setText(ac.getId()!=null?String.valueOf(ac.getId()):"");
                            txIndexTable.setText(String.valueOf(getIndex()));
                            txDe.setText(String.valueOf(ac.getDe()));
                            txAte.setText(String.valueOf(ac.getAte()));
                            txDescricao.setText(ac.getDescricao());
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        colunaDescricao.setPrefWidth(510);

        TableColumn<AvaliacaoCondicao, Number> colunaExcluir = new TableColumn<>("");
        colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaExcluir.setCellFactory(param -> new TableCell<AvaliacaoCondicao, Number>() {
            JFXButton button = new JFXButton();// excluir
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_REMOVE);
                    } catch (IOException e) {
                    }
                    button.setOnAction(event -> tbPrincipal.getItems().remove(getIndex()));
                    limpar();
                    setGraphic(button);
                }
            }
        });
        tbPrincipal.setFixedCellSize(150);
        tbPrincipal.getColumns().addAll(colunaDe, colunaAte,colunaDescricao, colunaEditar, colunaExcluir);
    }

    public Set<AvaliacaoCondicao> getCondicaoSet() {
        return condicaoSet;
    }

}
