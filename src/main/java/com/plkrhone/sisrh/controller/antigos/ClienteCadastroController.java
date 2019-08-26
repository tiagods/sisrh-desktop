package com.plkrhone.sisrh.controller.antigos;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.controller.UtilsController;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.repository.helper.CidadesImpl;
import com.plkrhone.sisrh.repository.helper.ClienteSetoresImp;
import com.plkrhone.sisrh.repository.helper.ClientesImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.fxutils.maskedtextfield.MaskedTextField;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClienteCadastroController extends UtilsController implements Initializable{
    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private JFXTextField txCodigo;

    @FXML
    private JFXTextField txCriacao;

    @FXML
    private JFXTextField txNome;

    @FXML
    private MaskedTextField txCnpj;

    @FXML
    private JFXTextField txResponsavel;

    @FXML
    private JFXTextField txEmail;

    @FXML
    private MaskedTextField txTelefone;

    @FXML
    private MaskedTextField txCelular;

    @FXML
    private JFXComboBox<ClienteSetor> cbSetor;

    @FXML
    private JFXCheckBox ckClienteContabil;

    @FXML
    private JFXCheckBox ckDesabilitar;

    @FXML
    private MaskedTextField txCep;

    @FXML
    private JFXTextField txLogradouro;

    @FXML
    private JFXTextField txNumero;

    @FXML
    private JFXTextField txComplemento;

    @FXML
    private JFXTextField txBairro;

    @FXML
    private JFXComboBox<Estado> cbEstado;

    @FXML
    private JFXComboBox<Cidade> cbCidade;

    @FXML
    private TableView<Anuncio> tbPrincipal;

    private Stage stage;
    private Cliente cliente;
    private ClienteSetoresImp setores;
    private ClientesImp clientes;
    private CidadesImpl cidades;
    private boolean houveAtualizacaoCombo = false;

    public ClienteCadastroController(Stage stage,Cliente cliente){
        this.stage=stage;
        this.cliente=cliente;
    }

    @FXML
    void buscarCep(javafx.event.ActionEvent event){
        bucarCep(txCep,txLogradouro,txNumero,txComplemento,txBairro,cbCidade,cbEstado);
    }

    private void combo(){
        setores = new ClienteSetoresImp(getManager());
        cbSetor.getItems().add(new ClienteSetor(-1L,"Qualquer"));
        cbSetor.getItems().addAll(setores.getAll());
        new ComboBoxAutoCompleteUtil<>(cbSetor);
        comboRegiao(cbCidade,cbEstado,getManager());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabelaCadastro();
        try{
            loadFactory();
            combo();
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","","Falha ao carregar combos",e,true);
        }finally {
            close();
        }
        if(cliente!=null) preencherFormulario(cliente);
    }

    @FXML
    void novoSetor(ActionEvent event) {
        Optional<String> result = cadastroRapido();
        if (result.isPresent()) {
            if (result.get().trim().length() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Insira um nome");
                alert.setContentText(null);
                alert.showAndWait();
            } else {
                try {
                    loadFactory();
                    setores = new ClienteSetoresImp(getManager());
                    ClienteSetor s = setores.findByNome(result.get().trim());
                    if (s == null) {
                        ClienteSetor setor = new ClienteSetor();
                        setor.setNome(result.get().trim());
                        setor = setores.save(setor);

                        // preencher combos
                        List<ClienteSetor> avaliacaoGrupos = setores.getAll();
                        ObservableList<ClienteSetor> novaLista = FXCollections.observableArrayList();
                        novaLista.add(null);
                        novaLista.addAll(avaliacaoGrupos);

                        cbSetor.getItems().clear();
                        cbSetor.getItems().addAll(novaLista);
                        new ComboBoxAutoCompleteUtil<>(cbSetor);
                        cbSetor.setValue(setor);
                        houveAtualizacaoCombo = true;
                    } else {
                        alert(Alert.AlertType.ERROR,"Valor duplicado","",
                                "Já existe um cadastro com o texto informado!");
                    }
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR,"Erro","",
                            "Falha ao salvar o registro!", e, true);
                } finally {
                    close();
                }
            }
        }
    }

    private void preencherFormulario(Cliente cliente) {
        try {
            txCodigo.setText(String.valueOf(cliente.getId()));
            if (cliente.getCriadoPor() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                txCriacao.setText("Criado em: " + cliente.getCriadoEm() != null ? sdf.format(cliente.getCriadoEm().getTime())
                        : "" + " por:" + cliente.getCriadoPor().getNome());
            }
            txNome.setText(cliente.getNome());
            // txCnpj.setText(cliente.getCnpj());
            txCnpj.setPlainText(cliente.getCnpj());
            txResponsavel.setText(cliente.getResponsavel());

            PfPj pfpj = cliente.getPessoaJuridica();
            if (pfpj != null) {
                txCep.setPlainText(pfpj.getCep());
                txLogradouro.setText(pfpj.getLogradouro());
                txNumero.setText(pfpj.getNumero());
                txComplemento.setText(pfpj.getComplemento());
                txBairro.setText(pfpj.getBairro());
                cbEstado.setValue(pfpj.getEstado());
                cbCidade.setValue(pfpj.getCidade());
            }
            if (cliente.getSetor() != null)
                cbSetor.setValue(cliente.getSetor());
            ckClienteContabil.setSelected(cliente.getClienteContabil() == 1 ? true : false);
            ckDesabilitar.setSelected(cliente.getSituacao() == 0);

            tbPrincipal.getItems().clear();
            tbPrincipal.getItems().addAll(cliente.getAnuncioSet());
            this.cliente = cliente;
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","",
                    "Falha ao preencher o formulario!", e, true);
        }
    }
    @FXML
    void sair(ActionEvent event){
        stage.close();
    }
    @FXML
    void salvar(ActionEvent event) {
        try {
            loadFactory();
            clientes = new ClientesImp(getManager());

            if (txCodigo.getText().equals("")) {
                if (!txCnpj.getPlainText().trim().equals("")){
                    Cliente cli = clientes.findByCnpj(txCnpj.getPlainText());
                    if (cli != null) {
                        alert(Alert.AlertType.ERROR,"Valor duplicado","",
                                "O cnpj informado já foi cadastrado para o cliente: " + cli.getId() + "-"
                                        + cli.getNome());
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
                        alert(Alert.AlertType.ERROR,"Valor duplicado","",
                                "O cnpj informado já foi cadastrado para o cliente: " + cli.getId() + "-"
                                        + cli.getNome());
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

            alert(Alert.AlertType.INFORMATION,"Sucesso","","Salvo com sucesso!");
            stage.close();
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","Erro ao salvar o registro",e,true);
        } finally {
            close();
        }
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
        tbPrincipal.getColumns().addAll(colunaId, colunaData, colunaDataAdmissao, colunaDataFim, colunaNome,
                colunaCronograma, colunaStatus);
    }

    public boolean isHouveAtualizacaoCombo() {
        return this.houveAtualizacaoCombo;
    }
}
