package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.model.Avaliacao;
import com.plkrhone.sisrh.model.avaliacao.AvaliacaoGrupo;
import com.plkrhone.sisrh.repository.helper.AvaliacoesGrupoImp;
import com.plkrhone.sisrh.repository.helper.AvaliacoesImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AvaliacaoPesquisaController extends UtilsController implements Initializable{

    @FXML
    private JFXComboBox<Avaliacao.AvaliacaoTipo> cbTipoAvaliacaoPesquisa;

    @FXML
    private JFXComboBox<AvaliacaoGrupo> cbGrupoPesquisa;

    @FXML
    private TableView<Avaliacao> tbPrincipal;

    @FXML
    private JFXTextField txPesquisa;

    private Avaliacao avaliacao;
    private AvaliacoesImp avaliacoes;
    private AvaliacoesGrupoImp grupos;

    Storage storage = StorageProducer.newConfig();
    private void abrirCadastro(Avaliacao t) {
        try {


            loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.AVALIACAO_CADASTRO);
            avaliacoes = new AvaliacoesImp(getManager());
            t = avaliacoes.findById(t.getId());
            AvaliacaoCadastroController controller = new AvaliacaoCadastroController(stage,t);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                try {
                    loadFactory();
                    if(controller.isHouveAtualizacaoCombo()) {
                        AvaliacaoGrupo avaliacaoGrupo1 = cbGrupoPesquisa.getValue();
                        combos();
                        if (avaliacaoGrupo1 != null) {
                            cbGrupoPesquisa.getSelectionModel().select(avaliacaoGrupo1);
                        }
                    }
                    filtrar();
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR, "Erro", "","Falha ao listar os registros", e, true);
                } finally {
                    close();
                }
            });
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo" + FXMLEnum.AVALIACAO_CADASTRO, e, true);
        } finally {
            close();
        }
    }
    private void combos() {
        grupos = new AvaliacoesGrupoImp(getManager());

        txPesquisa.onInputMethodTextChangedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadFactory();
                filtrar();
            } catch (Exception e) {
                alert(Alert.AlertType.ERROR, "Erro", "", "Falha ao listar os registros", e, true);
            } finally {
                close();
            }
        });

        cbGrupoPesquisa.getItems().clear();
        cbTipoAvaliacaoPesquisa.getItems().clear();

        cbTipoAvaliacaoPesquisa.getItems().add(null);
        cbTipoAvaliacaoPesquisa.getItems().addAll(Avaliacao.AvaliacaoTipo.values());
        cbTipoAvaliacaoPesquisa.valueProperty().addListener((observable, oldValue, newValue) -> filtrar());
        cbGrupoPesquisa.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadFactory();
                filtrar();
            } catch (Exception e) {
                alert(Alert.AlertType.ERROR, "Erro", "", "Falha ao listar os registros", e, true);
            } finally {
                close();
            }
        });

        ObservableList<AvaliacaoGrupo> avaliacaoGrupos = FXCollections.observableArrayList(grupos.getAll());
        cbGrupoPesquisa.getItems().add(new AvaliacaoGrupo(-1L,"Qualquer"));
        cbGrupoPesquisa.getItems().addAll(avaliacaoGrupos);
        new ComboBoxAutoCompleteUtil<>(cbGrupoPesquisa);
    }

    boolean excluir(Avaliacao avaliacao) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão...");
        alert.setContentText("Tem certeza disso?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            try {
                loadFactory();
                avaliacoes = new AvaliacoesImp(getManager());
                Avaliacao av = avaliacoes.findById(avaliacao.getId());
                avaliacoes.remove(av);
                alert(Alert.AlertType.INFORMATION, "Sucesso", "", "Excluido com sucesso!");
                return true;
            } catch (Exception e) {
                alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao excluir o registro", e, true);
                return false;
            } finally {
                close();
            }
        }
        return false;
    }

    private void filtrar() {
        avaliacoes = new AvaliacoesImp(getManager());
        List<Avaliacao> avaliacaos = avaliacoes.filtrar(txPesquisa.getText().trim(), cbTipoAvaliacaoPesquisa.getValue(), cbGrupoPesquisa.getValue());
        tbPrincipal.getItems().clear();
        tbPrincipal.getItems().addAll(FXCollections.observableArrayList(avaliacaos));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFactory();
            tabela();
            combos();
            filtrar();
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","Erro ao lista registros",e,true);
        } finally {
            close();
        }
    }

    @FXML
    void novo(ActionEvent event) {
        abrirCadastro(null);
    }

    void tabela() {
        TableColumn<Avaliacao, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);
        TableColumn<Avaliacao, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(120);
        TableColumn<Avaliacao, String> colunaDetalhes = new TableColumn<>("Detalhes");
        colunaDetalhes.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaDetalhes.setCellFactory((TableColumn<Avaliacao, String> param) -> {
            final TableCell<Avaliacao, String> cell = new TableCell<Avaliacao, String>() {
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
        colunaDetalhes.setPrefWidth(120);
        TableColumn<Avaliacao, String> colunaTipo = new TableColumn<>("Tipo");
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        TableColumn<Avaliacao, BigDecimal> colunaNota = new TableColumn<>("Pontuação");
        colunaNota.setCellValueFactory(new PropertyValueFactory<>("pontuacao"));
        colunaNota.setCellFactory((TableColumn<Avaliacao, BigDecimal> param) -> new TableCell<Avaliacao, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString().replace(".", ","));
                }
            }
        });
        TableColumn<Avaliacao, String> colunaFormulario = new TableColumn<>("Formulario");
        colunaFormulario.setCellValueFactory(new PropertyValueFactory<>("formulario"));
        colunaFormulario.setCellFactory((TableColumn<Avaliacao, String> param) -> new TableCell<Avaliacao, String>() {
            JFXButton button = new JFXButton();//
            @Override
            protected void updateItem(String item, boolean empty) {

                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                } else {
                    Avaliacao c = tbPrincipal.getItems().get(getIndex());
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_CLIP);
                    } catch (IOException e) {
                    }
                    if(c.getFormulario().equals("")) button.setVisible(false);
                    button.setOnAction(event -> {
                        visualizarFormulario(c.getFormulario());
                    });
                    setGraphic(button);
                }
            }
        });
        TableColumn<Avaliacao, String> colunaEditar = new TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaEditar.setCellFactory((TableColumn<Avaliacao, String> param) -> {
            final TableCell<Avaliacao, String> cell = new TableCell<Avaliacao, String>() {
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
                            abrirCadastro(tbPrincipal.getItems().get(getIndex()));

                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        TableColumn<Avaliacao, Number> colunaExcluir = new TableColumn<>("");
        colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaExcluir.setCellFactory(param -> new TableCell<Avaliacao, Number>() {
            JFXButton button = new JFXButton();// excluir
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_REMOVE);
                    } catch (IOException e) {
                    }
                    button.setOnAction(event -> {
                        boolean removed = excluir(tbPrincipal.getItems().get(getIndex()));
                        if (removed)
                            tbPrincipal.getItems().remove(getIndex());
                    });
                    setGraphic(button);

                }
            }
        });
        tbPrincipal.setFixedCellSize(50);
        tbPrincipal.getColumns().addAll(colunaId, colunaNome, colunaDetalhes, colunaTipo, colunaNota, colunaFormulario,
                colunaEditar,colunaExcluir);
    }
    void visualizarFormulario(String formulario){
        Runnable run = () -> {
            if(!formulario.trim().equals("")){
                try {
                    File file  = storage.downloadFile(formulario);
                    if(file!=null)
                        Desktop.getDesktop().open(file);
                }catch (Exception e) {
                    alert(Alert.AlertType.ERROR,"Erro","","Erro ao baixar o formulario",e,true);
                }
            }
        };
        new Thread(run).start();
    }
}
