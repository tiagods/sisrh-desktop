package com.plkrhone.sisrh.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.model.Cargo;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.repository.helper.CargosImpl;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * Created by Tiago on 21/07/2017.
 */
public class CargoPesquisaController extends UtilsController implements Initializable {

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private JFXTextField txPesquisa;

    @FXML
    private TableView<Cargo> tbPrincipal;

    private CargosImpl cargos;
    private Stage stage;

    private static Logger log = LoggerFactory.getLogger(CargoPesquisaController.class);

    public CargoPesquisaController(Stage stage) {
        this.stage = stage;
    }

    private void abrirCadastro(Cargo t) {
        try {
            loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CARGO_CADASTRO);
            loader.setController(new CargoCadastroController(stage, t));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                try {
                    loadFactory();
                    filtrar();
                    //filtrar(this.paginacao);
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registros", e, true);
                } finally {
                    close();
                }
            });
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo" + FXMLEnum.CARGO_CADASTRO, e, true);
        } finally {
            close();
        }
    }

    boolean excluir(Cargo cargo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão...");
        alert.setContentText("Tem certeza disso?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            try {
                loadFactory();
                CargosImpl vagas = new CargosImpl(getManager());
                Cargo c = vagas.findById(cargo.getId());
                vagas.remove(c);
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

    void filtrar() {
        cargos = new CargosImpl(getManager());
        List<Cargo> cargoList = cargos.getVagasByNome(txPesquisa.getText().trim());
        tbPrincipal.getItems().clear();
        tbPrincipal.setItems(FXCollections.observableArrayList(cargoList));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        long tempoInicial = System.currentTimeMillis();
        tabela();
        try {
            loadFactory();
            filtrar();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        long tempoFinal = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("Tela " + getClass().getSimpleName().replace("Controller", "") + " abriu em : "
                    + (tempoFinal - tempoInicial) + " ms");
        }
    }

    @FXML
    void novo(ActionEvent event) {
        abrirCadastro(null);
    }

    @FXML
    void pesquisar(KeyEvent event) {
        try {
            loadFactory();
            filtrar();
            //filtrar(this.paginacao);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @SuppressWarnings("unchecked")
    void tabela() {
        TableColumn<Cargo, Long> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

        TableColumn<Cargo, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(200);

        TableColumn<Cargo, String> colunaDescricao = new TableColumn<>("Descricao");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaDescricao.setCellFactory((TableColumn<Cargo, String> param) -> {
            final TableCell<Cargo, String> cell = new TableCell<Cargo, String>() {
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
        colunaDescricao.setPrefWidth(400);
        TableColumn<Cargo, String> colunaEditar = new TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaEditar.setCellFactory((TableColumn<Cargo, String> param) -> {
            final TableCell<Cargo, String> cell = new TableCell<Cargo, String>() {
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

                        Cargo cargo = tbPrincipal.getItems().get(getIndex());
                        if (cargo != null && cargo.getId() <= 2855) {
                            alert(Alert.AlertType.ERROR, "Cadastro Padronizado", "Registro não pode ser modificado",
                                    "Esse registro foi cadastrado de forma automatica e seguindo padrões, portanto não deve ser modificado!");
                            button.setDisable(true);
                        }

                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });

        TableColumn<Cargo, Number> colunaExcluir = new TableColumn<>("");
        colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaExcluir.setCellFactory(param -> new TableCell<Cargo, Number>() {
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
                    Cargo cargo = tbPrincipal.getItems().get(getIndex());
                    if (cargo != null && cargo.getId() <= 2855) {
                        alert(Alert.AlertType.ERROR, "Cadastro Padronizado", "Registro não pode ser modificado",
                                "Esse registro foi cadastrado de forma automatica e seguindo padrões, portanto não deve ser modificado!");
                        button.setDisable(true);
                    }

                    setGraphic(button);

                }
            }
        });
        tbPrincipal.setFixedCellSize(70);
        tbPrincipal.getColumns().addAll(colunaId,colunaNome, colunaDescricao, colunaEditar, colunaExcluir);
    }
}
