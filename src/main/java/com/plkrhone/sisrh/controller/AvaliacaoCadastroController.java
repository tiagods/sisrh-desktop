package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.model.Avaliacao;
import com.plkrhone.sisrh.model.avaliacao.AvaliacaoCondicao;
import com.plkrhone.sisrh.model.avaliacao.AvaliacaoGrupo;
import com.plkrhone.sisrh.repository.helper.AvaliacoesGrupoImp;
import com.plkrhone.sisrh.repository.helper.AvaliacoesImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.DialogReplace;
import com.plkrhone.sisrh.util.Keys;
import com.plkrhone.sisrh.util.storage.PathStorageEnum;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxutils.maskedtextfield.MaskTextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AvaliacaoCadastroController extends UtilsController implements Initializable{

    @FXML
    private JFXTextField txNome;

    @FXML
    private JFXComboBox<Avaliacao.AvaliacaoTipo> cbTipoAvaliacao;

    @FXML
    private JFXTextArea txDescricao;

    @FXML
    private JFXTextField txFormulario;

    @FXML
    private JFXTextField txCodigo;

    @FXML
    private MaskTextField txPontuacao;

    @FXML
    private JFXTextField txCriadoEm;

    @FXML
    private JFXComboBox<AvaliacaoGrupo> cbGrupo;

    @FXML
    private JFXTextArea txGabarito;

    @FXML
    private Pane paneCondicional;

    @FXML
    private TableView<AvaliacaoCondicao> tbPrincipal;

    private AvaliacoesImp avaliacoes;
    private AvaliacoesGrupoImp grupos;

    Storage storage = StorageProducer.newConfig();

    private Stage stage;
    private Avaliacao avaliacao;
    private boolean houveAtualizacaoCombo =false;

    public AvaliacaoCadastroController(Stage stage, Avaliacao avaliacao){
        this.stage=stage;
        this.avaliacao = avaliacao;
    }

    @FXML
    private void abrirCondicao(ActionEvent event){

    }
    @FXML
    void anexarFormulario(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atenção");
        alert.setHeaderText("Sempre separe a prova do gabarito!");
        alert.setContentText("Cancele essa operação se a prova e gabarito estiverem juntas!");
        Optional<ButtonType> opcao = alert.showAndWait();
        if (opcao.get() == ButtonType.OK) {
            Set<FileChooser.ExtensionFilter> filters = new HashSet<>();
            filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc","*.pdf","*.docx"));
            File file = storage.carregarArquivo(new Stage(), filters);
            if (file != null) {
                String novoNome = storage.gerarNome(file,PathStorageEnum.AVALIACAO.getDescricao());
                try{
                    storage.uploadFile(file, novoNome);
                    txFormulario.setText(novoNome);
                } catch(Exception e) {
                    alert(Alert.AlertType.ERROR,"Erro","","Não foi possivel enviar o arquivo para o servidor",e,true);
                }
            }
        }
    }
    private void combos() {
        grupos = new AvaliacoesGrupoImp(getManager());
        cbTipoAvaliacao.getItems().addAll(Avaliacao.AvaliacaoTipo.values());
        cbTipoAvaliacao.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                boolean habilitar = false;
                if (newValue==Avaliacao.AvaliacaoTipo.DISSERTATIVA) {
                    txPontuacao.setVisible(false);
                    txPontuacao.setText("0.00");
                } else {
                    txPontuacao.setVisible(true);
                    habilitar = newValue == Avaliacao.AvaliacaoTipo.CONDICIONAL;
                }
                habilitarTabela(habilitar);
            }
        });
        cbTipoAvaliacao.getSelectionModel().selectFirst();
        txPontuacao.setVisible(false);
        txPontuacao.setText("");

        ObservableList<AvaliacaoGrupo> avaliacaoGrupos = FXCollections.observableArrayList(grupos.getAll());

        cbGrupo.getItems().add(null);
        cbGrupo.getItems().addAll(avaliacaoGrupos);
        new ComboBoxAutoCompleteUtil<>(cbGrupo);
    }
    void habilitarTabela(boolean value){
        paneCondicional.setVisible(value);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tabela();
            loadFactory();
            avaliacoes = new AvaliacoesImp(getManager());
            combos();
            if(avaliacao!=null){
                Avaliacao avaliacao = avaliacoes.findById(this.avaliacao.getId());
                preencherFormulario(avaliacao);
            }
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","Erro ao lista registros",e,true);
        } finally {
            close();
        }
    }
    @FXML
    void modelo(ActionEvent event) {
        DialogReplace dialogReplace = new DialogReplace(Alert.AlertType.INFORMATION);
        ObservableList<Keys> list = FXCollections.observableList(Arrays.asList(
                new Keys("Nome:", "{candidato.nome}"),
                new Keys("Cargo:", "{candidato.cargo}"), new Keys("Data de Hoje:", "{data.hoje}"),
                new Keys("Data do Cadastro do Candidato:", "{candidato.cadastro}"),
                new Keys("Telefone:", "{candidado.telefone}"), new Keys("Celular:", "{candidato.celular}"),
                new Keys("Estato Civil:", "{candidato.ec}"), new Keys("Sexo:", "{candidato.sexo}"),
                new Keys("Idade:", "{candidato.idade}"), new Keys("Escolaridade:", "{candidado.escolaridade}"),
                new Keys("Endereço:", "{candidato.endereco}"), new Keys("E-mail:", "{candidato.email}")));
        Alert alerta = dialogReplace.construir(list);
        alerta.showAndWait();
    }

    @FXML
    void novoGrupo(ActionEvent event) {
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
                    AvaliacoesGrupoImp grupos = new AvaliacoesGrupoImp(getManager());
                    AvaliacaoGrupo avaliacaoGrupo = grupos.findByNome(result.get().trim());
                    if (avaliacaoGrupo == null) {
                        avaliacaoGrupo = new AvaliacaoGrupo();
                        avaliacaoGrupo.setNome(result.get().trim());
                        grupos.save(avaliacaoGrupo);
                        // preencher combos
                        List<AvaliacaoGrupo> avaliacaoGrupos = grupos.getAll();
                        ObservableList<AvaliacaoGrupo> novaLista = FXCollections.observableArrayList();
                        novaLista.add(null);
                        novaLista.addAll(avaliacaoGrupos);
                        AvaliacaoGrupo avaliacaoGrupo1 = cbGrupo.getValue();
                        cbGrupo.getItems().clear();
                        cbGrupo.getItems().addAll(novaLista);
                        if (avaliacaoGrupo1 != null) {
                            cbGrupo.getSelectionModel().select(avaliacaoGrupo1);
                        }
                        houveAtualizacaoCombo = true;
                    }
                    else
                        alert(Alert.AlertType.ERROR,"Valor duplicado","","Já existe um cadastro com o texto informado!");

                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR,"Erro","",
                            "Falha ao salvar o registro!", e, true);
                } finally {
                    close();
                }
            }
        }
    }
    void preencherFormulario(Avaliacao avaliacao) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        txCodigo.setText(String.valueOf(avaliacao.getId()));
        txCriadoEm.setText(avaliacao.getCriadoEm() == null ? "" : format.format(avaliacao.getCriadoEm().getTime()));
        txNome.setText(avaliacao.getNome());
        txDescricao.setText(avaliacao.getDescricao());
        cbTipoAvaliacao.setValue(avaliacao.getTipo());
        txPontuacao.setText(avaliacao.getPontuacao().toString());
        cbGrupo.setValue(avaliacao.getDepartamento());
        txFormulario.setText(avaliacao.getFormulario());
        txGabarito.setText(avaliacao.getGabarito());
        tbPrincipal.getItems().clear();
        tbPrincipal.getItems().addAll(avaliacao.getCondicoes());
        this.avaliacao = avaliacao;
    }
    @FXML
    void removerDocumento(ActionEvent event) {
        if(avaliacao!=null) {
            try{
                loadFactory();
                storage.delete(txFormulario.getText());
                txFormulario.setText("");
                avaliacoes = new AvaliacoesImp(getManager());
                avaliacao.setFormulario(txFormulario.getText());
                avaliacoes.save(avaliacao);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                close();
            }
        }
    }

    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {
        try {
            loadFactory();
            avaliacoes = new AvaliacoesImp(getManager());
            if (txCodigo.getText().equals("")) {
                avaliacao = new Avaliacao();
                avaliacao.setCriadoEm(Calendar.getInstance());
                avaliacao.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
                Avaliacao a = avaliacoes.findByNome(txNome.getText().trim());
                if (a != null) {
                    alert(Alert.AlertType.ERROR,"Valor incorreto","","Já existe uma avaliação com esse nome");
                    return;
                }
            }
            avaliacao.setNome(txNome.getText());
            avaliacao.setDescricao(txDescricao.getText());
            avaliacao.setTipo(cbTipoAvaliacao.getValue());
            avaliacao.setGabarito(txGabarito.getText());
            avaliacao.setDepartamento(cbGrupo.getValue());
            if (cbTipoAvaliacao.getValue() != null && cbTipoAvaliacao.getValue().equals(Avaliacao.AvaliacaoTipo.OBJETIVA)
                    || cbTipoAvaliacao.getValue() != null && cbTipoAvaliacao.getValue().equals(Avaliacao.AvaliacaoTipo.MISTA)) {
                try {
                    BigDecimal d = new BigDecimal(new Double(txPontuacao.getText()));
                    avaliacao.setPontuacao(d);
                }catch(Exception e) {
                    alert(Alert.AlertType.ERROR,"Valor incorreto","","A pontuação esta incorreta");
                    return;
                }
            }
            else
                avaliacao.setPontuacao(new BigDecimal(0.00));
            if (!txFormulario.getText().equals("") && !txFormulario.getText().startsWith(PathStorageEnum.AVALIACAO.getDescricao()+"/")) {
                try {
                    storage.transferTo(txFormulario.getText(), PathStorageEnum.AVALIACAO.getDescricao()+"/"+txFormulario.getText());
                    txFormulario.setText(PathStorageEnum.AVALIACAO.getDescricao()+"/" + txFormulario.getText());
                    avaliacao.setFormulario(txFormulario.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                avaliacao.setFormulario(txFormulario.getText());

            Set<AvaliacaoCondicao> condicaoSet = new HashSet<>();
            if(paneCondicional.isVisible()){
                condicaoSet.addAll(tbPrincipal.getItems().stream().collect(Collectors.toSet()));
            }
            avaliacao.setCondicoes(condicaoSet);

            avaliacao = avaliacoes.save(avaliacao);
            txCodigo.setText(String.valueOf(avaliacao.getId()));
            alert(Alert.AlertType.INFORMATION,"Sucesso","Salvo com sucesso!","");
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","Falha ao salvar","Não foi possivel salvar o registro", e,true);
        } finally {
            close();
        }
    }
    void tabela() {
        TableColumn<AvaliacaoCondicao, Number> colunaDescricao = new TableColumn<>("");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaDescricao.setCellFactory((TableColumn<AvaliacaoCondicao, Number> param) -> {
            final TableCell<AvaliacaoCondicao, Number> cell = new TableCell<AvaliacaoCondicao, Number>() {
                final JFXTextArea textArea = new JFXTextArea();
                @Override
                protected void updateItem(Number item, boolean empty) {
                    super.updateItem(item, empty);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);
                    if (item==null || empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        AvaliacaoCondicao av = tbPrincipal.getItems().get(getIndex());
                        textArea.setText(av.toString());
                        setGraphic(textArea);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        tbPrincipal.setFixedCellSize(50);
        tbPrincipal.getColumns().addAll(colunaDescricao);
    }

    @FXML
    void visualizar(ActionEvent event) {
        Runnable run = () -> {
            if(!txFormulario.getText().trim().equals("")){
                try {
                    File file  = storage.downloadFile(txFormulario.getText());
                    if(file!=null)
                        Desktop.getDesktop().open(file);
                }catch (Exception e) {
                    alert(Alert.AlertType.ERROR,"Erro","","Erro ao baixar o formulario",e,true);
                }
            }
        };
        new Thread(run).start();
    }
    public boolean isHouveAtualizacaoCombo(){
        return houveAtualizacaoCombo;
    }
}
