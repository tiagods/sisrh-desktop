package com.plkrhone.sisrh.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.repository.helper.CidadesImp;
import com.plkrhone.sisrh.repository.helper.ClienteSetoresImp;
import com.plkrhone.sisrh.repository.helper.ClientesImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.EnderecoUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

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

    private void abrirCadastro(Cliente t) {
        try {
            loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CLIENTE_CADASTRO);
            loader.setController(new CargoCadastroController(stage, t));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                try {
                    loadFactory();
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
        tabelaCadastro();
        try {
            loadFactory();
            clientes = new ClientesImp(getManager());
            combos();
            graficos();
            List<Cliente> lista = clientes.filtrar(1, null, null, null, "nome");
            tbClientes.setItems(FXCollections.observableList(lista));
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao listar os registros\n" + e);
            alert.showAndWait();
            e.printStackTrace();
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
        cbSetor.getItems().add(null);
        cbSetor.getItems().addAll(setores.getAll());
        cbSetorPesquisa.getItems().addAll(cbSetor.getItems());
        new ComboBoxAutoCompleteUtil<>(cbSetorPesquisa);
        new ComboBoxAutoCompleteUtil<>(cbSetor);

        cbSetorPesquisa.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filtrar();
            }
        });

        cbSituacao.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Situação")) {
                filtrar();
            }
        });
        cbFiltro.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Filtrar por:") && !newValue.equals("")) {
                filtrar();
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
        try {
            loadFactory();
            clientes = new ClientesImp(getManager());
            int situacao = cbSituacao.getValue().equals("Ativo") ? 1
                    : (cbSituacao.getValue().equals("Inativo") ? 0 : -1);
            List<Cliente> list = clientes.filtrar(situacao, cbSetorPesquisa.getValue(), txPesquisa.getText(),
                    cbFiltro.getValue(), "nome");
            tbClientes.getItems().clear();
            tbClientes.getItems().addAll(FXCollections.observableArrayList(list));
            graficos();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao filtrar os registros");
            alert.showAndWait();
        } finally {
            close();
        }
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
    void novoSetor(ActionEvent event) {

    }

    @FXML
    public void pesquisar(KeyEvent event) {
        filtrar();
    }



    @FXML
    public void salvar(ActionEvent event) {
        try {
            loadFactory();
            clientes = new ClientesImp(getManager());

            if (txCodigo.getText().equals("")) {
                if (!txCnpj.getPlainText().trim().equals(""))
                    ;
                {
                    Cliente cli = clientes.findByCnpj(txCnpj.getPlainText());
                    if (cli != null) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("CNPJ já existe");
                        alert.setContentText("O cnpj informado já foi cadastrado para o cliente: " + cli.getId() + "-"
                                + cli.getNome());
                        alert.showAndWait();
                        return;
                    }
                }
                cliente = new Cliente();
                cliente.setCriadoEm(Calendar.getInstance());
                cliente.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
            } else {
                cliente.setId(Long.parseLong(txCodigo.getText()));
                if (!txCnpj.getPlainText().trim().equals(""))
                    ;
                {
                    Cliente cli = clientes.findByCnpj(txCnpj.getPlainText());
                    if (cli != null && cli.getId() != cliente.getId()) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("CNPJ já existe");
                        alert.setContentText("O cnpj informado já foi cadastrado para o cliente: " + cli.getId() + "-"
                                + cli.getNome());
                        alert.showAndWait();
                        return;
                    }
                }
            }
            PfPj pfpj = new PfPj();
            cliente.setNome(txNome.getText());
            cliente.setCnpj(txCnpj.getPlainText());
            cliente.setResponsavel(txResponsavel.getText());

            pfpj.setEmail(txEmail.getText());
            pfpj.setTelefone(txTelefone.getPlainText());
            pfpj.setCelular(txCelular.getPlainText());
            pfpj.setCep(txCep.getPlainText());
            pfpj.setLogradouro(txLogradouro.getText());
            pfpj.setNumero(txNumero.getText());
            pfpj.setComplemento(txComplemento.getText());
            pfpj.setBairro(txBairro.getText());
            pfpj.setEstado(cbEstado.getValue());
            pfpj.setCidade(cbCidade.getValue());
            cliente.setPessoaJuridica(pfpj);
            cliente.setSetor(cbSetor.getValue());
            cliente.setClienteContabil(ckClienteContabil.isSelected() ? 1 : 0);
            cliente.setSituacao(ckDesabilitar.isSelected() ? 0 : 1);
            cliente = clientes.save(cliente);
            txCodigo.setText(String.valueOf(cliente.getId()));
            desbloquear(false);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Salvo com sucesso");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao salvar o registro\n" + e);
            alert.showAndWait();
        } finally {
            close();
        }
        filtrar();
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
                final JFXButton button = new JFXButton("Editar");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    button.getStyleClass().add("btOrange");
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> {
                            tabPane.getSelectionModel().select(tabCadastro);
                            desbloquear(true);
                            limparTela();
                            try {
                                loadFactory();
                                clientes = new ClientesImp(getManager());
                                Cliente cli = clientes.findById(tbClientes.getItems().get(getIndex()).getId());
                                preencherFormulario(cli);
                            } catch (Exception e) {
                                e.printStackTrace();
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
        tbClientes.getColumns().addAll(colunaNome, colunaResponsavel, colunaContato, colunaCelular,
                colunaClienteProlink, colunaStatus, colunaEditar);
    }

    @SuppressWarnings("unchecked")
    private void tabelaCadastro() {
        TableColumn<Anuncio, String> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

        TableColumn<Anuncio, FormularioRequisicao> colunaNome = new TableColumn<>("Cargo");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("formularioRequisicao"));
        colunaNome.setCellFactory(
                (TableColumn<Anuncio, FormularioRequisicao> param) -> new TableCell<Anuncio, FormularioRequisicao>() {
                    @Override
                    protected void updateItem(FormularioRequisicao item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getCargo() != null ? item.getCargo().getNome() : "");
                        }
                    }
                });
        TableColumn<Anuncio, Calendar> colunaData = new TableColumn<>("Inicio");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataAbertura"));
        colunaData.setCellFactory((TableColumn<Anuncio, Calendar> param) -> new TableCell<Anuncio, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item != null ? new SimpleDateFormat("dd/MM/yyyy").format(item.getTime()) : "");
                }
            }
        });
        TableColumn<Anuncio, Calendar> colunaDataAdmissao = new TableColumn<>("Previsão Admissão");
        colunaDataAdmissao.setCellValueFactory(new PropertyValueFactory<>("dataAdmissao"));
        colunaDataAdmissao.setCellFactory((TableColumn<Anuncio, Calendar> param) -> new TableCell<Anuncio, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item != null ? new SimpleDateFormat("dd/MM/yyyy").format(item.getTime()) : "");
                }
            }
        });
        TableColumn<Anuncio, Calendar> colunaDataFim = new TableColumn<>("Previsão Encerramento");
        colunaDataFim.setCellValueFactory(new PropertyValueFactory<>("dataEncerramento"));
        colunaDataFim.setCellFactory((TableColumn<Anuncio, Calendar> param) -> new TableCell<Anuncio, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item != null ? new SimpleDateFormat("dd/MM/yyyy").format(item.getTime()) : "");
                }
            }
        });
        TableColumn<Anuncio, Anuncio.Cronograma> colunaCronograma = new TableColumn<>("Cronograma");
        colunaCronograma.setCellValueFactory(new PropertyValueFactory<>("cronograma"));
        colunaCronograma
                .setCellFactory((TableColumn<Anuncio, Anuncio.Cronograma> param) -> new TableCell<Anuncio, Anuncio.Cronograma>() {
                    @Override
                    protected void updateItem(Anuncio.Cronograma item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getDescricao());
                        }
                    }
                });
        TableColumn<Anuncio, Anuncio.AnuncioStatus> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("anuncioStatus"));
        colunaStatus
                .setCellFactory((TableColumn<Anuncio, Anuncio.AnuncioStatus> param) -> new TableCell<Anuncio, Anuncio.AnuncioStatus>() {
                    @Override
                    protected void updateItem(Anuncio.AnuncioStatus item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item != null ? item.getDescricao() : "");
                        }
                    }
                });
        tbCadastro.getColumns().addAll(colunaId, colunaData, colunaDataAdmissao, colunaDataFim, colunaNome,
                colunaCronograma, colunaStatus);
    }
}
