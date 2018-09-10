package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.model.Curso;
import com.plkrhone.sisrh.repository.helper.CursosImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class CursoCadastroController extends UtilsController implements Initializable {

    @FXML
    private JFXTextField txNome;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private TableView<Curso> tbPrincipal;

    @FXML
    private JFXComboBox<Curso.Nivel> cbNivel;

    private Stage stage;
    private Curso.Nivel nivel;
    private CursosImpl cursos;
    private boolean houveAtualizacao = false;

    public CursoCadastroController(Stage stage,Curso.Nivel nivel){
        this.stage = stage;
        this.nivel = nivel;
    }

    private void combos(){
        cbNivel.getItems().addAll(Curso.Nivel.values());
        if(nivel!=null) {
            cbNivel.setValue(nivel);
            cbNivel.setDisable(true);
        }
        txNome.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!txNome.getText().equals("")) {
                Runnable run = () -> {
                    try {
                        loadFactory();
                        cursos = new CursosImpl(getManager());
                        List<Curso> cursoList = cursos.listByNome(txNome.getText());
                        Platform.runLater(()->tbPrincipal.getItems().setAll(cursoList));
                    } catch (Exception e) {
                        alert(Alert.AlertType.ERROR,"Erro","","Erro ao listar registros",e,true);
                    } finally {
                        close();
                    }
                };
                new Thread(run).start();

            }
        });
    }

    public boolean isHouveAtualizacao() {
        return houveAtualizacao;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabela();
        combos();
    }

    public String normalize(String valor){
        return Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "").toUpperCase();
    }

    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {
        if(txNome.getText().trim().equals("")) {
            alert(Alert.AlertType.ERROR,"Erro","Nome não identificado","Escreva algo para o titulo");
            return;
        }
        try{
            loadFactory();

            String nome = normalize(txNome.getText().trim());
            cursos = new CursosImpl(getManager());

            Set<Curso> result = tbPrincipal.getItems()
                    .stream()
                    .filter(c->normalize(c.getNome()).equals(nome))
                    .collect(Collectors.toSet());
            boolean salvar  = false;
            if(result.isEmpty()) salvar = true;
            else {
                Optional<Curso> first = result.stream()
                        .filter(c -> c.getNivel() == cbNivel.getValue())
                        .findAny();

                Optional<Curso> second = result.stream()
                        .filter(c->normalize(c.getNome()).equals(nome))
                        .findAny();

                if(first.isPresent()){
                    alert(Alert.AlertType.ERROR,"Registro duplicado","Já existe um registro com esse nome","Um registro com esse nome já existe, tente outro");
                    salvar = false;
                }
                else if(second.isPresent()){
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("");
                    alert.setHeaderText("Existe um cadastro parecido: Nome->"+second.get().getNome()+"\tNivel-> "+second.get().getNivel());
                    alert.getButtonTypes().clear();
                    ButtonType ok = new ButtonType("Salvar");
                    ButtonType cancelar = new ButtonType("Cancelar");
                    alert.getButtonTypes().addAll(ok,cancelar);
                    Optional<ButtonType> escolha = alert.showAndWait();
                    if(escolha.get() == ok) salvar = true;
                }
            }
            if(salvar){
                Curso curso = new Curso();
                curso.setNivel(cbNivel.getValue());
                curso.setNome(txNome.getText().trim());
                cursos.save(curso);
                houveAtualizacao = true;

                tbPrincipal.getItems().setAll(cursos.listByNome(txNome.getText().trim()));
                alert(Alert.AlertType.INFORMATION,"Sucesso","Salvo com sucesso!","");
            }
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","","Falha ao salvar registro",e,true);
        }finally {
            close();
        }
    }

    void tabela(){
        TableColumn<Curso, String> colunaNome = new TableColumn<>("");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setCellFactory(param -> new TableCell<Curso, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                } else {
                    setText(item.toString());
                }
            }
        });
        colunaNome.setPrefWidth(250);
        TableColumn<Curso, Curso.Nivel> colunaNivel = new TableColumn<>("");
        colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));

        tbPrincipal.getColumns().addAll(colunaNome,colunaNivel);
    }
}
