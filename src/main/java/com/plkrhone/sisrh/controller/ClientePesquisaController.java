package com.plkrhone.sisrh.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.repository.helper.ClienteSetoresImp;
import com.plkrhone.sisrh.repository.helper.ClientesImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * Created by Tiago on 13/07/2017.
 */
public class ClientePesquisaController extends UtilsController implements Initializable {
    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private JFXComboBox<String> cbSituacao;

    @FXML
    private JFXComboBox<ClienteSetor> cbSetorPesquisa;

    @FXML
    private JFXTextField txPesquisa;

    @FXML
    private JFXComboBox<String> cbFiltro;

    @FXML
    private PieChart grafico;

    @FXML
    private TableView<Cliente> tbPrincipal;

    private long ativos = 0, inativos = 0;
    private ClienteSetoresImp setores;
    private ClientesImp clientes;

    private Stage stage;

    public ClientePesquisaController(Stage stage){
        this.stage = stage;
    }

    private void abrirCadastro(Cliente t) {
        try {
            loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CLIENTE_CADASTRO);
            ClienteCadastroController controller = new ClienteCadastroController(stage, t);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                try {
                    loadFactory();
                    if(controller.isHouveAtualizacaoCombo()) {
                        setores = new ClienteSetoresImp(getManager());
                        ClienteSetor setor1 = cbSetorPesquisa.getValue();
                        cbSetorPesquisa.getItems().clear();
                        cbSetorPesquisa.getItems().add(new ClienteSetor(-1L, "Qualquer"));
                        cbSetorPesquisa.getItems().addAll(setores.getAll());
                        if (setor1 != null)
                            cbSetorPesquisa.getSelectionModel().select(setor1);
                        new ComboBoxAutoCompleteUtil<>(cbSetorPesquisa);
                    }
                    filtrar();
                    //filtrar(this.paginacao);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close();
                }
            });
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo" + FXMLEnum.CLIENTE_CADASTRO, e, true);
        } finally {
            close();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        long tempoInicial = System.currentTimeMillis();
        tabela();
        try {
            loadFactory();
            combos();
            graficos();
            filtrar();
        } catch (Exception e) {
            alert(AlertType.ERROR,"Erro","","Erro ao listar os registros", e, true);
        } finally {
            close();
        }
        long tempoFinal = System.currentTimeMillis();
        System.out.println((tempoFinal - tempoInicial) + " ms");
    }

    private void combos() {
        cbSituacao.getItems().addAll("Ativo", "Inativo", "Todas");
        cbSituacao.getSelectionModel().selectFirst();
        cbFiltro.getItems().addAll("ID", "Nome", "Responsavel");
        cbFiltro.getSelectionModel().select("Nome");

        setores = new ClienteSetoresImp(getManager());
        cbSetorPesquisa.getItems().add(null);
        cbSetorPesquisa.getItems().addAll(setores.getAll());
        new ComboBoxAutoCompleteUtil<>(cbSetorPesquisa);

        cbSetorPesquisa.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    loadFactory();
                    filtrar();
                    //filtrar(this.paginacao);
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registros", e, true);
                } finally {
                    close();
                }
            }
        });

        cbSituacao.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Situação")) {
                try {
                    loadFactory();
                    filtrar();
                    //filtrar(this.paginacao);
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registro", e, true);
                } finally {
                    close();
                }
            }
        });
        cbFiltro.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Filtrar por:") && !newValue.equals("")) {
                try {
                    loadFactory();
                    filtrar();
                    //filtrar(this.paginacao);
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registro", e, true);
                } finally {
                    close();
                }
            }
        });
    }

    private boolean excluir(Cliente cliente) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão...");
        alert.setContentText("Tem certeza disso?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            try {
                loadFactory();
                clientes = new ClientesImp(getManager());
                Cliente c = clientes.findById(cliente.getId());
                clientes.remove(c);
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
        clientes = new ClientesImp(getManager());
        int situacao = cbSituacao.getValue().equals("Ativo") ? 1
                : (cbSituacao.getValue().equals("Inativo") ? 0 : -1);
        List<Cliente> list = clientes.filtrar(situacao, cbSetorPesquisa.getValue(), txPesquisa.getText(),
                cbFiltro.getValue(), "nome");
        tbPrincipal.getItems().clear();
        tbPrincipal.getItems().addAll(FXCollections.observableArrayList(list));
    }

    private void graficos() {
        clientes = new ClientesImp(getManager());
        ativos = clientes.contar(1);
        inativos = clientes.contar(0);
        grafico.setTitle("Relação de Clientes");
        ObservableList<PieChart.Data> datas = FXCollections.observableArrayList();
        if (ativos > 0)
            datas.add(new PieChart.Data("Ativo(" + ativos + ")", ativos));
        if (inativos > 0)
            datas.add(new PieChart.Data("Inativo(" + inativos + ")", inativos));
        grafico.setData(datas);
        grafico.setLabelsVisible(true);
        grafico.setLegendVisible(true);
        grafico.setLabelLineLength(10);
    }
    @FXML
    public void novo(ActionEvent event) {
        abrirCadastro(null);
    }

    @FXML
    public void pesquisar(KeyEvent event) {
        try {
            loadFactory();
            filtrar();
        } catch (Exception e) {
            alert(AlertType.ERROR,"Erro","","Erro ao listar os registros", e, true);
        } finally {
            close();
        }
    }


    @SuppressWarnings("unchecked")
    private void tabela() {
        TableColumn<Cliente, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);
        TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setCellFactory((TableColumn<Cliente, String> param) -> new TableCell<Cliente, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toUpperCase());
                }
            }
        });
        colunaNome.setPrefWidth(150);
        TableColumn<Cliente, String> colunaResponsavel = new TableColumn<>("Responsavel");
        colunaResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
        colunaResponsavel.setPrefWidth(100);

        TableColumn<Cliente, PfPj> colunaContato = new TableColumn<>("Telefone");
        colunaContato.setCellValueFactory(new PropertyValueFactory<>("pessoaJuridica"));
        colunaContato.setCellFactory((TableColumn<Cliente, PfPj> param) -> new TableCell<Cliente, PfPj>() {
            @Override
            protected void updateItem(PfPj item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getTelefone());
                }
            }
        });
        TableColumn<Cliente, PfPj> colunaCelular = new TableColumn<>("Celular");
        colunaCelular.setCellValueFactory(new PropertyValueFactory<>("pessoaJuridica"));
        colunaCelular.setCellFactory((TableColumn<Cliente, PfPj> param) -> new TableCell<Cliente, PfPj>() {
            @Override
            protected void updateItem(PfPj item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.getCelular());
                }
            }
        });

        TableColumn<Cliente, Number> colunaClienteProlink = new TableColumn<>("Cliente Contábil");
        colunaClienteProlink.setCellValueFactory(new PropertyValueFactory<>("clienteContabil"));
        colunaClienteProlink.setCellFactory((TableColumn<Cliente, Number> param) -> new TableCell<Cliente, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else if (item.intValue() == 1) {
                    setText("Sim");
                } else if (item.intValue() == 0) {
                    setText("Não");
                }
            }
        });

        TableColumn<Cliente, Number> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        colunaStatus.setCellFactory((TableColumn<Cliente, Number> param) -> new TableCell<Cliente, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else if (item.intValue() == 1) {
                    setText("Ativo");
                } else if (item.intValue() == 0) {
                    setText("Inativo");
                }
            }
        });
        TableColumn<Cliente, Number> colunaqtdAnuncios = new TableColumn<>("Anuncios");
        colunaqtdAnuncios.setCellValueFactory(new PropertyValueFactory<>("anunciosTotal"));
        colunaqtdAnuncios.setCellFactory((TableColumn<Cliente, Number> param) -> new TableCell<Cliente, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                }
            }
        });
        TableColumn<Cliente, Number> colunaqtdAnunciosAbertos = new TableColumn<>("Pendentes");
        colunaqtdAnunciosAbertos.setCellValueFactory(new PropertyValueFactory<>("anunciosAbertos"));
        colunaqtdAnunciosAbertos
                .setCellFactory((TableColumn<Cliente, Number> param) -> new TableCell<Cliente, Number>() {
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.toString());
                        }
                    }
                });
        TableColumn<Cliente, Number> colunaqtdAnunciosConcluidos = new TableColumn<>("Concluidos");
        colunaqtdAnunciosConcluidos.setCellValueFactory(new PropertyValueFactory<>("anunciosFechados"));
        colunaqtdAnunciosConcluidos
                .setCellFactory((TableColumn<Cliente, Number> param) -> new TableCell<Cliente, Number>() {
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.toString());
                        }
                    }
                });
        TableColumn<Cliente, String> colunaEditar = new TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaEditar.setCellFactory((TableColumn<Cliente, String> param) -> {
            final TableCell<Cliente, String> cell = new TableCell<Cliente, String>() {
                final JFXButton button = new JFXButton("");

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
                        button.setOnAction(event -> {
                            try {
                                loadFactory();
                                clientes = new ClientesImp(getManager());
                                Cliente cli = clientes.findById(tbPrincipal.getItems().get(getIndex()).getId());
                                abrirCadastro(cli);
                            } catch (Exception e) {
                                alert(AlertType.ERROR,"Erro","","Erro ao carregar registro");
                            } finally {
                                close();
                            }
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        TableColumn<Cliente, Number> colunaExcluir = new TableColumn<>("");
        colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaExcluir.setCellFactory(param -> new TableCell<Cliente, Number>() {
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
                    Cliente c = tbPrincipal.getItems().get(getIndex());
                    setGraphic(button);

                }
            }
        });
        tbPrincipal.getColumns().addAll(colunaNome, colunaResponsavel, colunaContato, colunaCelular,
                colunaClienteProlink, colunaStatus, colunaEditar,colunaExcluir);
    }
}
