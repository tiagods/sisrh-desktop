package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.*;
import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.repository.helper.*;
import com.plkrhone.sisrh.repository.helper.filter.CandidatoAnuncioFilter;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.ExcelGenericoUtil;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fxutils.maskedtextfield.MaskTextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class CandidatoPesquisaController extends UtilsController implements Initializable{
    @FXML
    private JFXComboBox<Cargo> cbObjetivoPesquisa;

    @FXML
    private JFXComboBox<String> cbIndicacaoPesquisa;

    @FXML
    private JFXComboBox<String> cbSexoPesquisa;

    @FXML
    private JFXTextField txValorPesquisa;

    @FXML
    private JFXComboBox<String> cbBuscarPorPesquisa;

    @FXML
    private JFXDatePicker dtPerfilInicioPesquisa;

    @FXML
    private JFXDatePicker dtPerfilFimPesquisa;

    @FXML
    private JFXComboBox<Candidato.Escolaridade> cbFormacaoMinPesquisa;

    @FXML
    private JFXComboBox<Candidato.Escolaridade> cbFormacaoMaxPesquisa;

    @FXML
    private JFXComboBox<Cargo> cbExperienciaPesquisa;

    @FXML
    private MaskTextField txIdadeInicioPesquisa;

    @FXML
    private MaskTextField txIdadeFimPesquisa;

    @FXML
    private JFXComboBox<Curso> cbCursoSuperiorPesquisa;

    @FXML
    private JFXCheckBox ckIndisponivelPesquisa;

    @FXML
    private TableView<Candidato> tbPrincipal;

    private Anuncio anuncio;
    private AnunciosImp anuncios;
    private CandidatosImp candidatos;
    private CargosImpl cargos;
    private CursosImpl cursos;
    private CidadesImpl cidades;
    Storage storage = StorageProducer.newConfig();
    private Stage stage;
    private CandidatoAnuncioFilter filter;
    private boolean houveAtualizacaoCombo = false;

    public CandidatoPesquisaController(Stage stage, Anuncio anuncio,CandidatoAnuncioFilter filter){
        this.stage = stage;
        this.anuncio = anuncio;
        this.filter= filter;
    }
    private void abrirCadastro(Candidato t) {
        try {
            loadFactory();
            candidatos = new CandidatosImp(getManager());
            Stage stage = new Stage();
            if(this.anuncio!=null) stage.setTitle("Anuncio: " + anuncio.getNome() + "-Cliente: " + anuncio.getCliente().getNome());
            FXMLLoader loader = loaderFxml(FXMLEnum.CANDIDATO_CADASTRO);
            if(t!=null)
                t = candidatos.findById(t.getId());
            CandidatoCadastroController controller = new CandidatoCadastroController(stage,t,this.anuncio);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                try {
                    loadFactory();
                    candidatos = new CandidatosImp(getManager());
                    if(controller.isHouveAtualizacaoCombo()) {
                        cargos = new CargosImpl(getManager());
                        Cargo value1 = cbObjetivoPesquisa.getValue();
                        Cargo value2 = cbExperienciaPesquisa.getValue();

                        cbObjetivoPesquisa.getItems().clear();
                        cbObjetivoPesquisa.getItems().add(new Cargo(-1L, "Qualquer"));

                        cbObjetivoPesquisa.getItems().addAll(cargos.getAll());
                        cbExperienciaPesquisa.getItems().setAll(cbObjetivoPesquisa.getItems());
                        if (value1 != null)
                            cbObjetivoPesquisa.getSelectionModel().select(value1);
                        if (value2 != null)
                            cbExperienciaPesquisa.getSelectionModel().select(value2);
                        new ComboBoxAutoCompleteUtil<>(cbObjetivoPesquisa);
                        new ComboBoxAutoCompleteUtil<>(cbExperienciaPesquisa);
                    }

                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                            "Falha ao localizar o arquivo" + FXMLEnum.CANDIDATO_CADASTRO, e, true);

                    e.printStackTrace();
                } finally {
                    close();
                }
                filtrar();
                //filtrar(this.paginacao);
            });
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo" + FXMLEnum.CANDIDATO_CADASTRO, e, true);
        } finally {
            close();
        }
    }
    private boolean adicionarNoAnuncio(Candidato candidato) {
        anuncios = new AnunciosImp(getManager());
        candidatos = new CandidatosImp(getManager());

        candidato = candidatos.findById(candidato.getId());
        this.anuncio = anuncios.findById(anuncio.getId());
        Long id = candidato.getId();
        Optional<Candidato> result = this.anuncio.getCurriculoSet().stream().filter(f->f.getId()==id).findFirst();
        if(!result.isPresent()){
            candidato.setTotalRecrutamento(candidato.getTotalRecrutamento()+1);
            Set<Candidato> curriculos = anuncio.getCurriculoSet();
            curriculos.add(candidato);
            anuncio.setCurriculoSet(curriculos);
            anuncio = anuncios.save(anuncio);
            candidatos.save(candidato);
        }
        tbPrincipal.refresh();
        return true;
    }
    void combos() {
        cargos = new CargosImpl(getManager());
        cursos = new CursosImpl(getManager());
        cbObjetivoPesquisa.getItems().add(new Cargo(-1L,"Qualquer"));
        cbObjetivoPesquisa.getItems().addAll(cargos.getAll());
        cbExperienciaPesquisa.getItems().addAll(cbObjetivoPesquisa.getItems());

        new ComboBoxAutoCompleteUtil<>(cbObjetivoPesquisa);
        new ComboBoxAutoCompleteUtil<>(cbExperienciaPesquisa);

        cbSexoPesquisa.getItems().addAll("Qualquer", "M", "F");
        cbSexoPesquisa.getSelectionModel().selectFirst();
        cbIndicacaoPesquisa.getItems().addAll("Qualquer", "Sim", "Não");
        cbIndicacaoPesquisa.getSelectionModel().selectFirst();

        cbFormacaoMinPesquisa.getItems().add(null);
        cbFormacaoMinPesquisa.getItems().addAll(Candidato.Escolaridade.values());
        cbFormacaoMaxPesquisa.getItems().addAll(cbFormacaoMinPesquisa.getItems());

        cbBuscarPorPesquisa.getItems().addAll(new String[] { "Nome", "Email"});
        cbBuscarPorPesquisa.getSelectionModel().selectFirst();

        ckIndisponivelPesquisa.setSelected(true);
        ckIndisponivelPesquisa.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadFactory();
                candidatos = new CandidatosImp(getManager());
                filtrar();
            } catch (Exception e) {
                alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registro", e, true);
            } finally {
                close();
            }
        });
        ChangeListener<Object> pesquisaCombo = (observable, oldValue, newValue) -> {
            try {
                loadFactory();
                candidatos = new CandidatosImp(getManager());
                filtrar();
            } catch (Exception e) {
                alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registro", e, true);
            } finally {
                close();
            }
        };

        cbObjetivoPesquisa.valueProperty().addListener(pesquisaCombo);
        cbExperienciaPesquisa.valueProperty().addListener(pesquisaCombo);
        cbSexoPesquisa.valueProperty().addListener(pesquisaCombo);
        cbIndicacaoPesquisa.valueProperty().addListener(pesquisaCombo);
        cbFormacaoMinPesquisa.valueProperty().addListener(pesquisaCombo);
        cbFormacaoMaxPesquisa.valueProperty().addListener(pesquisaCombo);
        cbBuscarPorPesquisa.valueProperty().addListener(pesquisaCombo);
        dtPerfilInicioPesquisa.valueProperty().addListener(pesquisaCombo);
        dtPerfilFimPesquisa.valueProperty().addListener(pesquisaCombo);

        cbCursoSuperiorPesquisa.getItems().addAll(cursos.getAll());
        new ComboBoxAutoCompleteUtil<>(cbCursoSuperiorPesquisa);
    }

    boolean excluir(Candidato candidato) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exclusão...");
        alert.setContentText("Tem certeza disso?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            try {
                loadFactory();
                candidatos = new CandidatosImp(getManager());
                Candidato can = candidatos.findById(candidato.getId());
                candidatos.remove(can);
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

    @FXML
    void exportar(ActionEvent event){
            try {
                FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
                Alert progress = new Alert(Alert.AlertType.INFORMATION);
                progress.setHeaderText("");
                DialogPane dialogPane = new DialogPane();
                dialogPane.setContent(loader.load());
                progress.setDialogPane(dialogPane);
                Stage sta = (Stage) dialogPane.getScene().getWindow();

                Task<Void> run = new Task<Void>() {
                    {
                        setOnFailed(a ->sta.close());
                        setOnSucceeded(a ->sta.close());
                        setOnCancelled(a ->sta.close());
                    }
                    @Override
                    protected Void call() {

                        File export = salvarTemp("xls");
                        Platform.runLater(() -> sta.show());
                        ArrayList<ArrayList> listaImpressao = new ArrayList<>();
                        Integer[] colunasLenght = new Integer[] { 10, 18, 18, 14, 10, 30, 10, 10, 10, 10,20, 15, 15 };
                        String[] cabecalho = new String[] { "Tarefa", "Mês", "Prazo", "Andamento", "Status", "Detalhes", "Tipo",
                                "Registro", "Nome", "Ultimo Negocio","Status Negocio / Ultimo Negocio", "Atendente", "Criado_Por" };
                        listaImpressao.add(new ArrayList<>());
                        for (String c : cabecalho) {
                            listaImpressao.get(0).add(c);
                        }
                        try {

                            loadFactory();
                            candidatos = new CandidatosImp(getManager());
                            List<Candidato> candidatoList = filtrar();

                            if (candidatoList != null && ) {
                                for (int i = 1; i <= finalList.size(); i++) {
                                    listaImpressao.add(new ArrayList<String>());
                                    NegocioTarefa c = finalList.get(i-1);
                                    listaImpressao.get(i).add(c.getId());
                                    listaImpressao.get(i).add(new SimpleDateFormat("MM/yyyy").format(c.getDataEvento().getTime()));
                                    listaImpressao.get(i).add(sdfH.format(c.getDataEvento().getTime()));
                                    listaImpressao.get(i).add(c.getTipoTarefa().getDescricao());
                                    listaImpressao.get(i).add(c.getFinalizado()==0?"Pendente":"Finalizado");

                                    listaImpressao.get(i).add(c.getDescricao());
                                    String classe = "Contato";
                                    long id = 0;
                                    String nome="";
                                    String ultimoNegocio = "";
                                    String statusUltimoNegocio= "";

                                    if(c instanceof NegocioTarefaProposta) {
                                        classe = "Proposta";
                                        id = ((NegocioTarefaProposta)c).getProposta().getId();
                                        nome = ((NegocioTarefaProposta)c).getProposta().getNome();
                                        statusUltimoNegocio = ((NegocioTarefaProposta)c).getProposta().getTipoStatus().toString();
                                    }
                                    else if(c instanceof NegocioTarefaContato) {
                                        classe = "Contato";
                                        id = ((NegocioTarefaContato)c).getContato().getId();
                                        nome = ((NegocioTarefaContato)c).getContato().getNome();
                                        NegocioProposta p = ((NegocioTarefaContato)c).getContato().getUltimoNegocio();
                                        if(p!=null) {
                                            statusUltimoNegocio = p.getTipoStatus().getDescricao();
                                            ultimoNegocio = ""+p.getId();
                                        }
                                    }
                                    listaImpressao.get(i).add(classe);
                                    listaImpressao.get(i).add(id);
                                    listaImpressao.get(i).add(nome);
                                    listaImpressao.get(i).add(ultimoNegocio);
                                    listaImpressao.get(i).add(statusUltimoNegocio);
                                    listaImpressao.get(i).add(c.getAtendente().getNome());
                                    listaImpressao.get(i).add(c.getCriadoPor()!=null?c.getCriadoPor().getNome():"");
                                }
                            }
                            ExcelGenericoUtil planilha = new ExcelGenericoUtil(export.getAbsolutePath(), listaImpressao, colunasLenght);
                            planilha.gerarExcel();
                            Platform.runLater(() ->alert(Alert.AlertType.INFORMATION,"Sucesso", "Relatorio gerado com sucesso","",null,false));
                            Desktop.getDesktop().open(export);
                        } catch (Exception e1) {
                            Platform.runLater(() ->alert(Alert.AlertType.ERROR,"Erro","","Erro ao criar a planilha",e1,true));
                        } finally {
                            close();
                        }
                        return null;
                    }

                };
                if (tbPrincipal.getItems().size() >= 1) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Exportação");
                    alert.setContentText("Para exportar, realize um filtro antes!\nVocê ja filtrou os dados?");
                    ButtonType ok = new ButtonType("Exportar e Abrir");
                    ButtonType cancelar = new ButtonType("Cancelar");
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(ok, cancelar);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ok) {
                        Thread thread = new Thread(run);
                        thread.start();
                        sta.setOnCloseRequest(ae -> {
                            try {
                                thread.interrupt();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } else {
                    alert(Alert.AlertType.ERROR,"Erro","Parâmetros vazios","Nenhum registro foi encontrato",null,false);
                }
            }catch (IOException e){
                alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o progresso", "O arquivo nao foi localizado",null,false);
            }
    }

    private List<Candidato> filtrar() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        int idadeMin = 0;
        int idadeMax = 0;
        try {
            if (!txIdadeInicioPesquisa.getText().trim().equals("")) {
                idadeMin = Integer.parseInt(txIdadeInicioPesquisa.getText());
            }
            if (!txIdadeFimPesquisa.getText().trim().equals("")) {
                idadeMax = Integer.parseInt(txIdadeFimPesquisa.getText());
            }
        } catch (Exception e) {
            alert.setContentText("Idade informada esta incorreta");
            alert.showAndWait();
            return null;
        }
        if (dtPerfilInicioPesquisa.getValue() != null) {
            if (dtPerfilFimPesquisa.getValue() == null) {
                dtPerfilFimPesquisa.setValue(LocalDate.now());
            } else if (dtPerfilFimPesquisa.getValue().isBefore(dtPerfilInicioPesquisa.getValue())) {
                alert.setContentText("Data final do curriculo não pode ser maior que a data de inicio");
                alert.showAndWait();
                return null;
            }
        }
        if (cbFormacaoMinPesquisa.getValue() != null && cbFormacaoMaxPesquisa.getValue() != null) {
            boolean maior = cbFormacaoMinPesquisa.getValue().getValor() > cbFormacaoMaxPesquisa.getValue().getValor();
            if (maior) {
                alert.setContentText(
                        "Verifique a escolaridade informada, " + cbFormacaoMinPesquisa.getValue().getDescricao()
                                + " não pode ser superior a " + cbFormacaoMaxPesquisa.getValue().getDescricao());
                alert.showAndWait();
                return null;
            }
        }
        List<Candidato> lista = candidatos.filtrar(cbObjetivoPesquisa.getSelectionModel().getSelectedItem(), cbExperienciaPesquisa.getValue(),
                cbSexoPesquisa.getSelectionModel().getSelectedItem(), idadeMin, idadeMax, cbIndicacaoPesquisa.getValue(),
                cbFormacaoMinPesquisa.getValue(), cbFormacaoMaxPesquisa.getValue(),
                dtPerfilInicioPesquisa.getValue(), dtPerfilFimPesquisa.getValue(), cbBuscarPorPesquisa.getValue(),
                txValorPesquisa.getText().trim(),ckIndisponivelPesquisa.isSelected());
        tbPrincipal.getItems().clear();
        tbPrincipal.getItems().addAll(lista);
        tbPrincipal.refresh();
        return lista;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        long tempoInicial = System.currentTimeMillis();
        tabela();
        try {
            loadFactory();
            candidatos = new CandidatosImp(getManager());
            combos();
            if(anuncio!=null && filter!=null){
                cbCursoSuperiorPesquisa.setValue(filter.getCurso());
                cbFormacaoMinPesquisa.setValue(filter.getEscolaridade());
                cbObjetivoPesquisa.setValue(filter.getCargo());
                cbExperienciaPesquisa.setValue(filter.getCargo());
            }
            filtrar();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tela de candidatos");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao listar candidatos\n" + e);
            alert.show();
            e.printStackTrace();
        } finally {
            close();
        }
        long tempoFinal = System.currentTimeMillis();
        System.out.println((tempoFinal - tempoInicial) + " ms");
    }

    @FXML
    void novo(ActionEvent event) {
        abrirCadastro(null);
    }
    @FXML
    void pesquisar(KeyEvent event) {
        try {
            loadFactory();
            candidatos = new CandidatosImp(getManager());
            filtrar();
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registros", e, true);
        } finally {
            close();
        }
    }
    private void tabela() {
        TableColumn<Candidato, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

        TableColumn<Candidato, Calendar> colunaDataCriacao = new TableColumn<>("Atualização");
        colunaDataCriacao.setCellValueFactory(new PropertyValueFactory<>("ultimaModificacao"));
        colunaDataCriacao.setCellFactory((TableColumn<Candidato, Calendar> param) -> new TableCell<Candidato, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    setText(simpleDateFormat.format(item.getTime()));
                }
            }
        });


        TableColumn<Candidato, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setCellFactory((TableColumn<Candidato, String> param) -> new TableCell<Candidato, String>() {
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

        TableColumn<Candidato, Number> colunaIdade = new TableColumn<>("Idade");
        colunaIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colunaIdade.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item.intValue()));
                }
            }
        });
        TableColumn<Candidato, Number> colunaIndicacao = new TableColumn<>("Indicação");
        colunaIndicacao.setCellValueFactory(new PropertyValueFactory<>("indicacao"));
        colunaIndicacao.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
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

        TableColumn<Candidato, Number> colunaSelecoes = new TableColumn<>("Seleções");
        colunaSelecoes.setCellValueFactory(new PropertyValueFactory<>("totalRecrutamento"));
        colunaSelecoes.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item.intValue()));
                }
            }
        });
        TableColumn<Candidato, Number> colunaEntrevista = new TableColumn<>("Entrevistas");
        colunaEntrevista.setCellValueFactory(new PropertyValueFactory<>("totalEntrevista"));
        colunaEntrevista.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item.intValue()));
                }
            }
        });
        TableColumn<Candidato, Number> colunaPreAprovacoes = new TableColumn<>("Pré-Seleção");
        colunaPreAprovacoes.setCellValueFactory(new PropertyValueFactory<>("totalPreSelecao"));
        colunaPreAprovacoes.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item.intValue()));
                }
            }
        });
        TableColumn<Candidato, Number> colunaAprovacoes = new TableColumn<>("Aprovações");
        colunaAprovacoes.setCellValueFactory(new PropertyValueFactory<>("totalAprovacao"));
        colunaAprovacoes.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item.intValue()));
                }
            }
        });
        TableColumn<Candidato, Number> colunaDisponivel = new TableColumn<>("Disponivel");
        colunaDisponivel.setCellValueFactory(new PropertyValueFactory<>("ocupado"));
        colunaDisponivel.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    if(item.intValue()==0)
                        setText("Sim");
                    else
                        setText("Não");
                }
            }
        });
        TableColumn<Candidato, Number> colunaAnexo = new TableColumn<>("");
        colunaAnexo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaAnexo.setCellFactory(param -> new TableCell<Candidato, Number>() {
            JFXButton button = new JFXButton();//

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    Candidato c = tbPrincipal.getItems().get(getIndex());
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
        TableColumn<Candidato, String> colunaEditar = new TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaEditar.setCellFactory((TableColumn<Candidato, String> param) -> {
            final TableCell<Candidato, String> cell = new TableCell<Candidato, String>() {
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
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });

        TableColumn<Candidato, String> colunaAdicionarAnuncio = new TableColumn<>("");
        colunaAdicionarAnuncio.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaAdicionarAnuncio.setCellFactory((TableColumn<Candidato, String> param) -> {
            final TableCell<Candidato, String> cell = new TableCell<Candidato, String>() {
                final JFXButton button = new JFXButton("Adicionar no Anuncio");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        final Candidato candidato1 = tbPrincipal.getItems().get(getIndex());
                        if(anuncio!=null) {
                            Optional<Candidato> result = anuncio.getCurriculoSet().
                                    stream().
                                    filter(f->f.getId()==candidato1.getId()).
                                    findFirst();
                            if(candidato1.getOcupado()==1) {
                                button.setText("Não Disponivel");
                                button.setDisable(true);
                            }
                            else if(anuncio.getCurriculoSet() != null && result.isPresent()) {
                                button.setText("Já Vinculado");
                                button.setDisable(true);
                            }
                            setGraphic(button);
                        }
                        button.setOnAction(event -> {
                            try {
                                loadFactory();
                                adicionarNoAnuncio(candidato1);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Confirmação!");
                                alert.setHeaderText("Candidato incluido no anuncio");
                                alert.setContentText("O candidato " + candidato1.getNome()
                                            + " foi incluido em \nAnuncio: " + anuncio.getNome() + "\nCliente: "
                                            + anuncio.getCliente().getNome() + "  !\n Feche esta janela para salvar!");
                                alert.showAndWait();
                                button.setDisable(true);
                                tbPrincipal.refresh();
                            } catch (Exception e) {
                                alert(Alert.AlertType.ERROR,"Erro","",
                                        "Nao foi possivel incluir o candidato no anunciol, informe o erro ao administrador do sistema",
                                        new Exception("Candidato > "+candidato1.getId()+"\n "+e), true);
                            }finally {
                                close();
                            }
                        });
                        setText(null);
                    }
                }
            };
            return cell;
        });
        TableColumn<Candidato, Number> colunaExcluir = new TableColumn<>("");
        colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaExcluir.setCellFactory(param -> new TableCell<Candidato, Number>() {
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
        tbPrincipal.getColumns().addAll(colunaId, colunaDataCriacao, colunaNome, colunaIdade, colunaIndicacao,
                colunaSelecoes, colunaEntrevista, colunaPreAprovacoes, colunaAprovacoes, colunaDisponivel, colunaAnexo,
                colunaAdicionarAnuncio,colunaEditar,colunaExcluir);
    }
    private void visualizarFormulario(String formulario){
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

    public boolean isHouveAtualizacaoCombo() {
        return houveAtualizacaoCombo;
    }
}
