package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.*;
import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.model.anuncio.AnuncioCandidatoConclusao;
import com.plkrhone.sisrh.model.anuncio.AnuncioCronograma;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAnalise;
import com.plkrhone.sisrh.observer.CandidatoObserver;
import com.plkrhone.sisrh.observer.EnumCandidato;
import com.plkrhone.sisrh.repository.helper.*;
import com.plkrhone.sisrh.repository.helper.filter.CandidatoAnuncioFilter;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.office.FileOfficeEnum;
import com.plkrhone.sisrh.util.office.OfficeEditor;
import com.plkrhone.sisrh.util.office.OfficeEditorProducer;
import com.plkrhone.sisrh.util.storage.PathStorageEnum;
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
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class AnuncioCadastroController extends UtilsController implements Initializable {
    @FXML
    private JFXTextField txCodigo;

    @FXML
    private JFXTextField txNome;

    @FXML
    private JFXDatePicker dtEncerramento;

    @FXML
    private JFXDatePicker dtAbertura;

    @FXML
    private JFXComboBox<Cliente> cbCliente;

    @FXML
    private JFXComboBox<Anuncio.AnuncioStatus> cbStatus;

    @FXML
    private JFXComboBox<Anuncio.Cronograma> cbCronograma;

    @FXML
    private JFXTextField txCriacao;

    @FXML
    private JFXTabPane tpProcesso;

    @FXML
    private Tab tabVagas;

    @FXML
    private AnchorPane pnCadastroVaga;

    @FXML
    private JFXTabPane tpVaga;

    @FXML
    private Tab tabVaga1;

    @FXML
    private AnchorPane pnVaga1;

    @FXML
    private JFXRadioButton rbVaga1Clt;

    @FXML
    private JFXRadioButton rbVaga1Estagiario;

    @FXML
    private JFXRadioButton rbVaga1Temporario;

    @FXML
    private JFXDatePicker dtVaga1DataInicio;

    @FXML
    private JFXDatePicker dtVaga1DataFim;

    @FXML
    private JFXComboBox<Cargo> cbVaga1Cargo;

    @FXML
    private JFXTextField txVaga1CargaHoraria;

    @FXML
    private JFXTextField txVaga1HorarioTrabalho;

    @FXML
    private JFXRadioButton rbVaga1ProcessoSigilosoNao;

    @FXML
    private JFXRadioButton rbVaga1ProcessoSigilosoSim;

    @FXML
    private JFXButton btVagaAvancar1;

    @FXML
    private JFXTextField txDocumento;

    @FXML
    private JFXButton btAnexarFormulario;

    @FXML
    private JFXButton btAbrirFormulario;

    @FXML
    private JFXButton btRemoverFormulario;

    @FXML
    private JFXComboBox<CargoNivel> cbVaga1CargoNivel;

    @FXML
    private JFXTextField txCargoAdc;

    @FXML
    private Tab tabVaga2;

    @FXML
    private AnchorPane pnVaga2;

    @FXML
    private JFXRadioButton rbVaga2MotivoQuadro;

    @FXML
    private JFXRadioButton rbVaga2MotivoDesligado;

    @FXML
    private JFXRadioButton rbVaga2MotivoAfastamento;

    @FXML
    private JFXRadioButton rbVaga2MotivoOutro;

    @FXML
    private JFXTextField txVaga2MotivoOutro;

    @FXML
    private JFXButton btVagaAvancar2;

    @FXML
    private JFXButton btVagaVoltar2;

    @FXML
    private Tab tabVaga3;

    @FXML
    private AnchorPane pnVaga3;

    @FXML
    private JFXComboBox<Candidato.Escolaridade> cbVaga3Escolaridade;

    @FXML
    private JFXComboBox<Cidade> cbVaga3Municipio;

    @FXML
    private JFXComboBox<Curso> cbVaga3Formacao;

    @FXML
    private JFXComboBox<Integer> cbVaga3Tempo;

    @FXML
    private JFXComboBox<String> cbVaga3Tipo;

    @FXML
    private JFXComboBox<Estado> cbVaga3Estado;

    @FXML
    private JFXButton btVagaVoltar3;

    @FXML
    private JFXButton btVagaAvancar3;

    @FXML
    private JFXTextField txVaga3Idioma;

    @FXML
    private Tab tabVaga31;

    @FXML
    private AnchorPane pnVaga31;

    @FXML
    private JFXTextArea txVaga31Competencia;

    @FXML
    private JFXButton btVagaAvancar31;

    @FXML
    private JFXButton btVagaVoltar31;

    @FXML
    private Tab tabVaga32;

    @FXML
    private AnchorPane pnVaga32;

    @FXML
    private JFXTextArea txVaga32DescricaoAtividades;

    @FXML
    private JFXButton btVagaVoltar32;

    @FXML
    private JFXButton btVagaAvancar32;

    @FXML
    private Tab tabVaga4;

    @FXML
    private AnchorPane pnVaga4;

    @FXML
    private JFXTextField txVaga4Salario;

    @FXML
    private JFXCheckBox ckVaga4ACombinar;

    @FXML
    private JFXTextArea txVaga4Observacao;

    @FXML
    private JFXTextField txVaga4Comissao;

    @FXML
    private JFXButton btVagaAvancar4;

    @FXML
    private JFXButton btVagaVoltar4;

    @FXML
    private JFXRadioButton rbVaga4ComissaoSim;

    @FXML
    private JFXRadioButton rbVaga4ComissaoNao;

    @FXML
    private Tab tabVaga41;

    @FXML
    private AnchorPane pnVaga41;

    @FXML
    private JFXTextField txVaga41VRValor;

    @FXML
    private JFXButton btVagaVoltar41;

    @FXML
    private JFXRadioButton rbVaga41VRNao;

    @FXML
    private JFXRadioButton rbVaga41VRSim;

    @FXML
    private JFXRadioButton rbVaga41VTSim;

    @FXML
    private JFXRadioButton rbVaga41VTNao;

    @FXML
    private JFXRadioButton rbVaga41AMedicaNao;

    @FXML
    private JFXRadioButton rbVaga41AMedicaSim;

    @FXML
    private JFXTextField txVaga41AMedicaDetalhes;

    @FXML
    private JFXTextField txVaga41Outro;

    @FXML
    private Tab tabCadastroContrato;

    @FXML
    private AnchorPane panContrato;

    @FXML
    private JFXDatePicker dtEnvioContrato;

    @FXML
    private JFXDatePicker dtRetornoContrato;

    @FXML
    private JFXComboBox<Anuncio.StatusContrato> cbStatusContrato;

    @FXML
    private Tab tabCurriculos;

    @FXML
    private AnchorPane panCurriculos;

    @FXML
    private TableView<Candidato> tbCurriculos;

    @FXML
    private JFXButton btAdicionarCurriculo;

    @FXML
    private Tab tabEntrevistas;

    @FXML
    private AnchorPane panEntrevistas;

    @FXML
    private JFXComboBox<Candidato> cbCandidatoEntrevista;

    @FXML
    private JFXButton btnEntrevista;

    @FXML
    private TableView<AnuncioEntrevista> tbEntrevista;

    @FXML
    private Tab tabPreSelecionado;

    @FXML
    private AnchorPane panPreSelecionado;

    @FXML
    private JFXButton btnAdicionar;

    @FXML
    private JFXComboBox<Candidato> cbPreSelecionado;

    @FXML
    private TableView<Candidato> tbPreSelecionado;

    @FXML
    private Tab tabConclusao;

    @FXML
    private JFXButton btnAprovarCandidato;

    @FXML
    private JFXComboBox<Candidato> cbConclusaoCandidato;

    @FXML
    private TableView<AnuncioCandidatoConclusao> tbConclusaoCandidato;

    @FXML
    private JFXDatePicker dtAdmissao;

    @FXML
    private JFXTextField txResponsavel;

    @FXML
    private JFXDatePicker dtInicioDivulgacao;

    @FXML
    private JFXDatePicker dtFimDivulgacao;

    @FXML
    private JFXTextField txDivulgacaoObs;

    @FXML
    private JFXDatePicker dtInicioRecrutamento;

    @FXML
    private JFXDatePicker dtFimRecrutamento;

    @FXML
    private JFXTextField txRecrutamentoObs;

    @FXML
    private JFXDatePicker dtInicioAgendamento;

    @FXML
    private JFXDatePicker dtFimAgendamento;

    @FXML
    private JFXTextField txAgendamentoObs;

    @FXML
    private JFXDatePicker dtInicioEntrevista;

    @FXML
    private JFXDatePicker dtFimEntrevista;

    @FXML
    private JFXTextField txEntrevistaObs;

    @FXML
    private JFXDatePicker dtInicioPreSelecionado;

    @FXML
    private JFXDatePicker dtFimPreSelecionado;

    @FXML
    private JFXTextField txPreSelecionadoObs;

    @FXML
    private JFXDatePicker dtInicioRetorno;

    @FXML
    private JFXDatePicker dtFimRetorno;

    @FXML
    private JFXTextField txRetornoObs;

    private Stage stage;
    private Anuncio anuncio;
    private AnunciosImp anuncios;
    private AnuncioEntrevistasImp anunciosEntrevistas;
    private CargosImpl cargos;
    private CargoNiveisImpl cargoNiveis;
    private CandidatosImp candidatos;
    private ClientesImp clientes;
    private CursosImpl cursos;
    private CidadesImpl cidades;
    private TreinamentosImp treinamentos;
    private Storage storage = StorageProducer.newConfig();

    private CandidatoObserver observer = CandidatoObserver.getInstance();


    public AnuncioCadastroController(Stage stage, Anuncio anuncio) {
        this.anuncio = anuncio;
        this.stage = stage;
    }

    private void abrirPerfilCandidato(Candidato candidato) {
        try {
            loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CANDIDATO_CADASTRO);
            loader.setController(new CandidatoCadastroController(stage, candidato, null));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
            });
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo" + FXMLEnum.CANDIDATO_CADASTRO, e, true);
        } finally {
            close();
        }
    }

    private void abrirCandidatoConclusao(AnuncioCandidatoConclusao candidatoConclusao) {
        try {
            loadFactory();
            Stage stage = new Stage();
            Candidato c = candidatoConclusao.getCandidato();
            stage.setTitle(c.getId() + "-" + c.getNome() + (c.getIdade() == 0 ? "" : "(" + c.getIdade() + ")"));
            FXMLLoader loader = loaderFxml(FXMLEnum.ANUNCIO_CANDIDATO_CONCLUSAO);
            if (candidatoConclusao.getDataInicio() == null && dtAdmissao.getValue() != null) {
                Date date = Date.from(dtAdmissao.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                candidatoConclusao.setDataInicio(calendar);
            }
            AnuncioCandidatoConclusaoController controller = new AnuncioCandidatoConclusaoController(stage, candidatoConclusao);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
            });
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo" + FXMLEnum.ANUNCIO_CANDIDATO_CONCLUSAO, e, true);
        } finally {
            close();
        }
    }

    private void abrirEntrevista(AnuncioEntrevista ae, int index) {
        try {
            loadFactory();
            Candidato c = ae.getCandidato();

            anunciosEntrevistas = new AnuncioEntrevistasImp(getManager());
            anuncios = new AnunciosImp(getManager());

            anuncio.setCurriculoSet(
                    tbCurriculos.getItems().stream().collect(Collectors.toSet()));
            anuncio.setEntrevistaSet(
                    tbEntrevista.getItems().stream().collect(Collectors.toSet()));
            anuncio.setPreSelecaoSet(
                    tbPreSelecionado.getItems().stream().collect(Collectors.toSet()));
            anuncio.setConclusaoSet(
                    tbConclusaoCandidato.getItems().stream().collect(Collectors.toSet()));

            candidatos = new CandidatosImp(getManager());
            anuncio = anuncios.save(anuncio);

            observer.notifyUpdate(candidatos);
            observer.clear();

            // curriculos
            tbCurriculos.getItems().clear();
            tbCurriculos.getItems().addAll(anuncio.getCurriculoSet());
            // entrevistas
            tbEntrevista.getItems().clear();
            tbEntrevista.getItems().addAll(anuncio.getEntrevistaSet());
            // pre selecao
            tbPreSelecionado.getItems().clear();
            tbPreSelecionado.getItems().addAll(anuncio.getPreSelecaoSet());

            FXMLLoader loader = loaderFxml(FXMLEnum.ENTREVISTA);
            Stage stage1 = new Stage();
            ControllerEntrevista controller = new ControllerEntrevista(tbEntrevista.getItems().get(index), stage1);
            loader.setController(controller);
            initPanel(loader, stage1, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage1.setOnHiding(event1 -> {
                try {
                    loadFactory();
                    anuncios = new AnunciosImp(getManager());
                    anuncio = anuncios.findById(anuncio.getId());
                    tbEntrevista.getItems().clear();
                    tbEntrevista.getItems().addAll(anuncio.getEntrevistaSet());
                    tbEntrevista.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @FXML
    private void alterar(ActionEvent event) {
        if (!txCodigo.getText().equals("")) observer.clear();
    }

    @FXML
    void anexarFormulario(ActionEvent event) {
        Set<FileChooser.ExtensionFilter> filters = new HashSet<>();
        filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc", "*.pdf", "*.docx"));
        File file = storage.carregarArquivo(new Stage(), filters);
        if (file != null) {
            String novoNome = storage.gerarNome(file, PathStorageEnum.FORMULARIO_REQUISICAO.getDescricao());
            try {
                storage.uploadFile(file, novoNome);
                txDocumento.setText(novoNome);
            } catch (Exception e) {
                alert(Alert.AlertType.ERROR, "Erro", "Não foi possivel enviar o arquivo para o servidor",
                        "Um erro desconhecido não permitiu o envio do arquivo para uma pasta do servidor:\n" + e, e, true);
            }
        }
    }

    private void combos() {

        cargos = new CargosImpl(getManager());
        List<Cargo> cargoList = cargos.getAll();

        // combos da area de cadastro
        clientes = new ClientesImp(getManager());
        List<Cliente> clienteList = clientes.filtrar(1, null, "", "", "nome");

        cbCliente.getItems().addAll(clienteList);
        cbCliente.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                txResponsavel.setText(newValue.getResponsavel());
        });

        cbStatus.getItems().addAll(Arrays.asList(Anuncio.AnuncioStatus.values()));
        cbStatus.getSelectionModel().select(1);
        cbCronograma.getItems().addAll(Arrays.asList(Anuncio.Cronograma.values()));
        cbCronograma.getSelectionModel().selectFirst();

        cbVaga1Cargo.getItems().addAll(cargoList);

        cargoNiveis = new CargoNiveisImpl(getManager());
        cbVaga1CargoNivel.getItems().add(null);
        cbVaga1CargoNivel.getItems().addAll(cargoNiveis.getAll());
        new ComboBoxAutoCompleteUtil(cbVaga1CargoNivel);

        new ComboBoxAutoCompleteUtil(cbVaga1Cargo);

        ToggleGroup tgVaga1_1 = new ToggleGroup();
        tgVaga1_1.getToggles().addAll(rbVaga1Clt, rbVaga1Estagiario, rbVaga1Temporario);
        EventHandler filter = event -> {
            if (rbVaga1Temporario.isSelected() || rbVaga1Estagiario.isSelected()) {
                dtVaga1DataInicio.setDisable(false);
                dtVaga1DataFim.setDisable(false);
            } else {
                dtVaga1DataInicio.setDisable(true);
                dtVaga1DataFim.setDisable(true);
            }
            event.consume();
        };
        rbVaga1Temporario.addEventFilter(ActionEvent.ACTION, filter);
        rbVaga1Estagiario.addEventFilter(ActionEvent.ACTION, filter);
        rbVaga1Clt.addEventFilter(ActionEvent.ACTION, filter);
        rbVaga1Clt.setSelected(true);
        dtVaga1DataInicio.setDisable(true);
        dtVaga1DataFim.setDisable(true);

        ToggleGroup tgVaga1_2 = new ToggleGroup();
        tgVaga1_2.getToggles().addAll(rbVaga1ProcessoSigilosoSim, rbVaga1ProcessoSigilosoNao);
        rbVaga1ProcessoSigilosoNao.setSelected(true);

        ToggleGroup tgVaga2 = new ToggleGroup();
        tgVaga2.getToggles().addAll(rbVaga2MotivoDesligado, rbVaga2MotivoAfastamento, rbVaga2MotivoQuadro,
                rbVaga2MotivoOutro);
        EventHandler filter2 = event -> {
            if (rbVaga2MotivoAfastamento.isSelected() || rbVaga2MotivoDesligado.isSelected()
                    || rbVaga2MotivoQuadro.isSelected()) {
                txVaga2MotivoOutro.setDisable(true);
            } else
                txVaga2MotivoOutro.setDisable(false);
            event.consume();
        };
        rbVaga2MotivoAfastamento.addEventFilter(ActionEvent.ACTION, filter2);
        rbVaga2MotivoDesligado.addEventFilter(ActionEvent.ACTION, filter2);
        rbVaga2MotivoOutro.addEventFilter(ActionEvent.ACTION, filter2);
        rbVaga2MotivoQuadro.addEventFilter(ActionEvent.ACTION, filter2);
        rbVaga2MotivoDesligado.setSelected(true);
        txVaga2MotivoOutro.setDisable(true);

        cursos = new CursosImpl(getManager());
        List<Curso> cursoSuperiorsList = cursos.getAll();
        cbVaga3Formacao.setItems(FXCollections.observableList(cursoSuperiorsList));
        cbVaga3Formacao.setValue(null);
        cbVaga3Formacao.setDisable(true);
        new ComboBoxAutoCompleteUtil(cbVaga3Formacao);

        cbVaga3Escolaridade.getItems().setAll(Candidato.Escolaridade.values());
        cbVaga3Escolaridade.valueProperty().addListener(new ChangeListener<Candidato.Escolaridade>() {
            @Override
            public void changed(ObservableValue<? extends Candidato.Escolaridade> observable, Candidato.Escolaridade oldValue,
                                Candidato.Escolaridade newValue) {
                ObservableList<String> newEscolaridade = FXCollections.observableArrayList(Arrays.asList("",
                        "Fundamental Incompleto", "Fundamental Completo", "Médio Incompleto", "Médio Completo"));
                if (newValue != null) {
                    if (newEscolaridade.contains(newValue.getDescricao())) {
                        cbVaga3Formacao.setValue(null);
                        cbVaga3Formacao.setDisable(true);
                    } else
                        cbVaga3Formacao.setDisable(false);
                }
            }
        });
        cbVaga3Estado.getItems().setAll(Arrays.asList(Estado.values()));
        cbVaga3Estado.setValue(Estado.SP);
        cidades = new CidadesImpl(getManager());
        Cidade cidade = cidades.findByNome("São Paulo");

        cbVaga3Municipio.getItems().setAll(cidades.findByEstado(Estado.SP));
        cbVaga3Municipio.setValue(cidade);
        cbVaga3Estado.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    loadFactory(getManager());
                    cidades = new CidadesImpl(getManager());
                    List<Cidade> listCidades = cidades.findByEstado(newValue);
                    cbVaga3Municipio.getItems().setAll(listCidades);
                } catch (Exception e) {
                } finally {
                    close();
                }
            }
        });

        new ComboBoxAutoCompleteUtil<>(cbVaga3Municipio);

        for (int i = 1; i <= 100; i++) {
            cbVaga3Tempo.getItems().add(i);
        }
        cbVaga3Tipo.getItems().addAll("anos", "meses");
        ckVaga4ACombinar.setOnAction(event -> {
            if (ckVaga4ACombinar.isSelected()) {
                txVaga4Salario.setDisable(true);
            } else
                txVaga4Salario.setDisable(false);
        });
        ToggleGroup tgVaga4 = new ToggleGroup();
        tgVaga4.getToggles().addAll(rbVaga4ComissaoSim, rbVaga4ComissaoNao);
        EventHandler filter4 = event -> {
            if (rbVaga4ComissaoSim.isSelected()) {
                txVaga4Comissao.setDisable(false);
            } else
                txVaga4Comissao.setDisable(true);
            event.consume();
        };
        rbVaga4ComissaoSim.addEventFilter(ActionEvent.ACTION, filter4);
        rbVaga4ComissaoNao.addEventFilter(ActionEvent.ACTION, filter4);
        rbVaga4ComissaoNao.setSelected(true);
        txVaga4Comissao.setDisable(true);

        ToggleGroup tgVaga41_1 = new ToggleGroup();
        tgVaga41_1.getToggles().addAll(rbVaga41VRSim, rbVaga41VRNao);
        EventHandler filter41_1 = event -> {
            if (rbVaga41VRSim.isSelected()) {
                txVaga41VRValor.setDisable(false);
            } else
                txVaga41VRValor.setDisable(true);
            event.consume();
        };
        rbVaga41VRSim.addEventFilter(ActionEvent.ACTION, filter41_1);
        rbVaga41VRNao.addEventFilter(ActionEvent.ACTION, filter41_1);
        rbVaga41VRNao.setSelected(true);

        ToggleGroup tgVaga41_2 = new ToggleGroup();
        tgVaga41_2.getToggles().addAll(rbVaga41VTSim, rbVaga41VTNao);
        rbVaga41VTNao.setSelected(true);
        ToggleGroup tgVaga41_3 = new ToggleGroup();
        tgVaga41_3.getToggles().addAll(rbVaga41AMedicaSim, rbVaga41AMedicaNao);

        EventHandler filter41_3 = event -> {
            if (rbVaga41AMedicaSim.isSelected()) {
                txVaga41AMedicaDetalhes.setDisable(false);
            } else
                txVaga41AMedicaDetalhes.setDisable(true);
            event.consume();
        };
        rbVaga41AMedicaSim.addEventFilter(ActionEvent.ACTION, filter41_3);
        rbVaga41AMedicaNao.addEventFilter(ActionEvent.ACTION, filter41_3);

        rbVaga41VRNao.setSelected(true);
        txVaga41VRValor.setDisable(true);
        rbVaga41AMedicaNao.setSelected(true);
        rbVaga41AMedicaNao.setSelected(true);
        txVaga41AMedicaDetalhes.setDisable(true);

        cbStatusContrato.getItems().add(null);
        cbStatusContrato.getItems().addAll(Anuncio.StatusContrato.values());

        cbStatus.setValue(Anuncio.AnuncioStatus.EM_ANDAMENTO);
        cbCronograma.setValue(Anuncio.Cronograma.DIVULGACAO_DA_VAGA);
        dtAbertura.setValue(LocalDate.now());

        txVaga4Salario.setText("0,00");
        txVaga4Comissao.setText("0,00");
        txVaga41VRValor.setText("0,00");
    }

    @FXML
    void fecharAnuncio(ActionEvent event) {

    }

    @FXML
    void formRequisicaoAvancar(ActionEvent event) {
        Tab t = proximaTabVagas(true);
        if (t != null) tpVaga.getSelectionModel().select(t);
    }

    @FXML
    void formRequisicaoRetroceder(ActionEvent event) {
        Tab t = proximaTabVagas(false);
        if (t != null) tpVaga.getSelectionModel().select(t);
    }

    @FXML
    void incluirAprovado(ActionEvent event) {
        if (cbConclusaoCandidato.getValue() != null && anuncio != null) {
            Candidato candidato = cbConclusaoCandidato.getValue();
            AnuncioCandidatoConclusao ac = new AnuncioCandidatoConclusao();
            ac.setAnuncio(anuncio);
            ac.setCandidato(candidato);
            if (dtAdmissao.getValue() != null)
                ac.setDataInicio(GregorianCalendar.from(dtAdmissao.getValue().atStartOfDay(ZoneId.systemDefault())));
            tbConclusaoCandidato.getItems().add(ac);
            cbConclusaoCandidato.getItems().remove(candidato);
            cbConclusaoCandidato.setValue(null);
            candidato.setOcupadoDetalhes("Contratado atraves do anúncio " + anuncio.getId() + " da empresa " + anuncio.getCliente());
            observer.registerCandidato(candidato, EnumCandidato.APROVACAO, 1);
        } else {
            alert(Alert.AlertType.ERROR, "Erro", "", "Nenhum candidato foi incluido");
        }
    }

    @FXML
    void incluirCandidato(ActionEvent event) {
        if (anuncio != null && !txCodigo.getText().equals("")) {
            try {
                CandidatoAnuncioFilter filter = new CandidatoAnuncioFilter(cbVaga1Cargo.getValue(),
                        cbVaga3Escolaridade.getValue(), cbVaga3Formacao.getValue());
                loadFactory();
                Stage stage = new Stage();
                stage.setTitle("Anuncio: " + anuncio.getNome() + "-Cliente: " + anuncio.getCliente().getNome());
                FXMLLoader loader = loaderFxml(FXMLEnum.CANDIDATO_PESQUISA);
                loader.setController(new CandidatoPesquisaController(stage, anuncio, filter));
                initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
                stage.setOnHiding(e -> {
                    try {
                        loadFactory();
                        anuncios = new AnunciosImp(getManager());
                        anuncio = anuncios.findById(anuncio.getId());
                        preencherFormulario(anuncio);
                    } catch (Exception ex) {
                        alert(Alert.AlertType.ERROR, "Erro", "", "Falha ao carregar formulario!", ex, true);
                    } finally {
                        close();
                    }
                });
            } catch (IOException e) {
                alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                        "Falha ao localizar o arquivo" + FXMLEnum.CANDIDATO_PESQUISA, e, true);
            } finally {
                close();
            }
        } else {
            alert(Alert.AlertType.WARNING, "Alerta!", "", "Salve antes de avançar!");
        }
    }

    @FXML
    void incluirEntrevista(ActionEvent event) {
        if (cbCandidatoEntrevista.getValue() != null && !cbCandidatoEntrevista.getItems().isEmpty()
                && anuncio != null) {
            Candidato candidato = cbCandidatoEntrevista.getValue();
            AnuncioEntrevista anuncioEntrevista = new AnuncioEntrevista();
            anuncioEntrevista.setAnuncio(this.anuncio);
            anuncioEntrevista.setCliente(this.anuncio.getCliente());
            anuncioEntrevista.setCandidato(candidato);
            tbEntrevista.getItems().add(anuncioEntrevista);
            cbCandidatoEntrevista.getItems().remove(candidato);
            cbCandidatoEntrevista.setValue(null);
            if (!cbPreSelecionado.getItems().contains(candidato)) {
                cbPreSelecionado.getItems().add(candidato);
                observer.registerCandidato(candidato, EnumCandidato.ENTREVISTA, 1);
            }
        } else {
            alert(Alert.AlertType.ERROR, "Erro", "", "Nenhum candidato foi incluido");
        }
    }

    @FXML
    void incluirPreAprovado(ActionEvent event) {
        if (cbPreSelecionado.getValue() != null && anuncio != null) {
            Candidato candidato = cbPreSelecionado.getValue();
            tbPreSelecionado.getItems().add(candidato);
            cbPreSelecionado.getItems().remove(candidato);
            cbPreSelecionado.setValue(null);
            if (!cbConclusaoCandidato.getItems().contains(candidato)) {
                cbConclusaoCandidato.getItems().add(candidato);
                observer.registerCandidato(candidato, EnumCandidato.PRESELECAO, 1);
            }
        } else alert(Alert.AlertType.ERROR, "Erro", null, "Nenhum candidato foi incluido, agende uma entrevista");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabelaCurriculo();
        tabelaEntrevista();
        tabelaPreSelecao();
        tabelaConclusao();
        try {
            loadFactory();
            combos();
            if (anuncio != null) preencherFormulario(anuncio);
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR, "Erro", null, "Erro ao listar anuncios:\n" + e.getMessage(), e, true);
        } finally {
            close();
        }
    }

    @FXML
    void novoCargo(ActionEvent event) {
        try {
            loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CARGO_CADASTRO);
            CargoCadastroController controller = new CargoCadastroController(stage, null);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(e -> {
                try {
                    loadFactory();
                    Cargo cargo = controller.getCargo();
                    //filtrar(this.paginacao);
                    cargos = new CargosImpl(getManager());
                    List<Cargo> cargoList = cargos.getAll();
                    Cargo ca = cbVaga1Cargo.getValue();
                    cbVaga1Cargo.getItems().setAll(cargoList);
                    new ComboBoxAutoCompleteUtil<>(cbVaga1Cargo);
                    if (ca != null) cbVaga1Cargo.getSelectionModel().select(ca);
                    else cbVaga1Cargo.getSelectionModel().select(cargo);
                } catch (Exception ex) {
                    alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registros", ex, true);
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

    @FXML
    void novoCargoNivel(ActionEvent event) {
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
                    cargoNiveis = new CargoNiveisImpl(getManager());
                    CargoNivel s = cargoNiveis.findByNome(result.get().trim());
                    if (s == null) {
                        CargoNivel n = new CargoNivel();
                        n.setNome(result.get().trim());
                        n = cargoNiveis.save(n);
                        // preencher combos

                        CargoNivel old = cbVaga1CargoNivel.getValue();

                        cbVaga1CargoNivel.getItems().clear();
                        cbVaga1CargoNivel.getItems().add(null);
                        cbVaga1CargoNivel.getItems().addAll(cargoNiveis.getAll());
                        new ComboBoxAutoCompleteUtil(cbVaga1CargoNivel);
                        if (old != null) cbVaga1CargoNivel.setValue(old);
                        else cbVaga1CargoNivel.setValue(n);
                    } else {
                        alert(Alert.AlertType.ERROR, "Valor duplicado", "",
                                "Já existe um cadastro com o texto informado!");
                    }
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR, "Erro", "",
                            "Falha ao salvar o registro!", e, true);
                } finally {
                    close();
                }
            }
        }
    }

    void preencherFormulario(Anuncio anuncio) {
        try {
            this.anuncio = anuncio;
            txCodigo.setText(anuncio.getId() + "");
            txCriacao.setText(anuncio.getCriadoPor() != null ? anuncio.getCriadoPor().getNome() : "");
            txNome.setText(anuncio.getNome());
            cbCliente.setValue(anuncio.getCliente());
            txResponsavel.setText(anuncio.getResponsavel());
            cbStatus.setValue(anuncio.getAnuncioStatus());
            cbCronograma.setValue(anuncio.getCronograma());
            dtAbertura.setValue(anuncio.getDataAbertura() == null ? null
                    : anuncio.getDataAbertura().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            dtEncerramento.setValue(anuncio.getDataEncerramento() == null ? null
                    : anuncio.getDataEncerramento().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            dtAdmissao.setValue(anuncio.getDataAdmissao() == null ? null
                    : anuncio.getDataAdmissao().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            txDocumento.setText("");
            FormularioRequisicao form = anuncio.getFormularioRequisicao();
            if (form != null) {
                txDocumento.setText(form.getFormulario()==null?"":form.getFormulario());
                cbVaga1Cargo.setValue(form.getCargo());
                cbVaga1CargoNivel.setValue(form.getNivel());
                txCargoAdc.setText(form.getCargoAds());
                if (form.getTipo().equals("CLT")) {
                    rbVaga1Clt.setSelected(true);
                    dtVaga1DataInicio.setDisable(true);
                    dtVaga1DataFim.setDisable(true);
                } else {
                    if (form.getTipo().equals("Estagio"))
                        rbVaga1Estagiario.setSelected(true);
                    else
                        rbVaga1Temporario.setSelected(true);
                    dtVaga1DataInicio.setDisable(false);
                    dtVaga1DataFim.setDisable(false);
                }
                txVaga1CargaHoraria.setText(form.getCargaHoraria());
                txVaga1HorarioTrabalho.setText(form.getHorarioTrabalho());

                if (form.getSigiloso() == 1)
                    rbVaga1ProcessoSigilosoSim.setSelected(true);
                else
                    rbVaga1ProcessoSigilosoNao.setSelected(true);
                // 2
                if (form.getMotivo() != null) {
                    switch (form.getMotivo()) {
                        case "Desligamemto":
                            rbVaga2MotivoDesligado.setSelected(true);
                            break;
                        case "Quadro":
                            rbVaga2MotivoQuadro.setSelected(true);
                            break;
                        case "Afastamento":
                            rbVaga2MotivoAfastamento.setSelected(true);
                            break;
                        default:
                            rbVaga2MotivoOutro.setSelected(true);
                            txVaga2MotivoOutro.setText(form.getMotivoObs());
                            break;
                    }
                }
                cbVaga3Escolaridade.setValue(form.getEscolaridade());
                cbVaga3Formacao.setValue(form.getCurso());
                Cidade cidade = form.getCidade();
                if (cidade != null) {
                    cbVaga3Estado.setValue(cidade.getEstado());
                    cbVaga3Municipio.setValue(cidade);
                    cbVaga3Tempo.setValue(form.getTempoExperiencia() == -1 ? null : form.getTempoExperiencia());
                }
                cbVaga3Tipo.setValue(form.getTipoExperiencia());
                txVaga31Competencia.setText(form.getCompetencia());
                txVaga32DescricaoAtividades.setText(form.getAtividade());
                ckVaga4ACombinar.setSelected(form.getSalarioCombinar() == 1);
                if (form.getSalarioCombinar() == 1) {
                    txVaga4Salario.setText("0,00");
                    txVaga4Salario.setDisable(true);
                } else {
                    txVaga4Salario.setText(form.getSalario().toString().replace(".", ","));
                    txVaga4Salario.setDisable(false);
                }
                txVaga4Observacao.setText(form.getObservacao());
                if (form.getComissao() == 1) {
                    rbVaga4ComissaoSim.setSelected(true);
                    txVaga4Comissao.setText(form.getComissaoValor().toString().replace(".", ","));
                    txVaga4Comissao.setDisable(false);
                } else {
                    rbVaga4ComissaoNao.setSelected(true);
                    txVaga4Comissao.setText("0,00");
                    txVaga4Comissao.setDisable(true);
                }
                if (form.getValeRefeicao() == 1) {
                    rbVaga41VRSim.setSelected(true);
                    txVaga41VRValor.setDisable(false);
                    txVaga41VRValor.setText(form.getVrValor().toString().replace(".", ","));
                } else {
                    rbVaga41VRNao.setSelected(true);
                    txVaga41VRValor.setDisable(true);
                    txVaga41VRValor.setText("0,00");
                }
                if (form.getValeTransporte() == 1)
                    rbVaga41VTSim.setSelected(true);
                else
                    rbVaga41VTNao.setSelected(true);
                if (form.getAssistenciaMedica() == 1) {
                    rbVaga41AMedicaSim.setSelected(true);
                    txVaga41AMedicaDetalhes.setDisable(false);
                    txVaga41AMedicaDetalhes.setText(form.getAssistenciaMedicaValor());
                } else {
                    rbVaga41AMedicaNao.setSelected(true);
                    txVaga41AMedicaDetalhes.setDisable(true);
                    txVaga41AMedicaDetalhes.setText("");
                }
                txVaga41Outro.setText(form.getOutroBeneficio());
            }
            // contrato
            dtEnvioContrato.setValue(anuncio.getDataEnvioContrato() == null ? null
                    : anuncio.getDataEnvioContrato().getTime().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate());
            dtRetornoContrato.setValue(anuncio.getDataRetornoContrato() == null ? null
                    : anuncio.getDataRetornoContrato().getTime().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate());
            cbStatusContrato.setValue(anuncio.getStatusContrato());

            // curriculos
            tbCurriculos.getItems().clear();
            tbCurriculos.getItems().addAll(anuncio.getCurriculoSet());
            // entrevistas
            tbEntrevista.getItems().clear();
            tbEntrevista.getItems().addAll(anuncio.getEntrevistaSet());
            // pre selecao
            tbPreSelecionado.getItems().clear();
            tbPreSelecionado.getItems().addAll(anuncio.getPreSelecaoSet());

            // conclusao
            tbConclusaoCandidato.getItems().clear();
            tbConclusaoCandidato.getItems().addAll(anuncio.getConclusaoSet());
            AnuncioCronograma ac = anuncio.getCronogramaDetails();
            if (ac != null) {
                dtInicioDivulgacao.setValue(ac.getDataInicioDivulgacao() == null ? null
                        : ac.getDataInicioDivulgacao().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                dtFimDivulgacao.setValue(ac.getDataFimDivulgacao() == null ? null
                        : ac.getDataFimDivulgacao().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                txDivulgacaoObs.setText(ac.getDivulgacao());
                dtInicioRecrutamento.setValue(ac.getDataInicioRecrutamento() == null ? null
                        : ac.getDataInicioRecrutamento().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                dtFimRecrutamento.setValue(ac.getDataFimRecrutamento() == null ? null
                        : ac.getDataFimRecrutamento().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                txRecrutamentoObs.setText(ac.getRecrutamento());
                dtInicioAgendamento.setValue(ac.getDataInicioAgendamento() == null ? null
                        : ac.getDataInicioAgendamento().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                dtFimAgendamento.setValue(ac.getDataFimAgendamento() == null ? null
                        : ac.getDataFimAgendamento().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                txAgendamentoObs.setText(ac.getAgendamento());
                dtInicioEntrevista.setValue(ac.getDataInicioEntrevista() == null ? null
                        : ac.getDataInicioEntrevista().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                dtFimEntrevista.setValue(ac.getDataFimEntrevista() == null ? null
                        : ac.getDataFimEntrevista().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                txEntrevistaObs.setText(ac.getEntrevista());
                dtInicioPreSelecionado.setValue(ac.getDataInicioPreSelecionado() == null ? null
                        : ac.getDataInicioPreSelecionado().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                dtFimPreSelecionado.setValue(ac.getDataFimPreSelecionado() == null ? null
                        : ac.getDataFimPreSelecionado().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
                txPreSelecionadoObs.setText(ac.getPreSelecionado());
                dtInicioRetorno.setValue(ac.getDataInicioRetorno() == null ? null
                        : ac.getDataInicioRetorno().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                dtFimRetorno.setValue(ac.getDataFimRetorno() == null ? null
                        : ac.getDataFimRetorno().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                txRetornoObs.setText(ac.getRetorno());
            }
            cbCandidatoEntrevista.getItems().clear();
            cbPreSelecionado.getItems().clear();
            cbConclusaoCandidato.getItems().clear();

            tbCurriculos.getItems().forEach(cand -> {
                Optional<AnuncioEntrevista> ae = tbEntrevista.getItems().stream()
                        .filter(c -> c.getCandidato() == cand).findAny();
                if (!ae.isPresent() && !cbCandidatoEntrevista.getItems().contains(cand))
                    cbCandidatoEntrevista.getItems().add(cand);
            });
            tbEntrevista.getItems().forEach(ae -> {
                Optional<Candidato> can = tbPreSelecionado.getItems().stream().filter(c -> c == ae.getCandidato()).findAny();
                if (!can.isPresent() && !cbPreSelecionado.getItems().contains(ae.getCandidato()))
                    cbPreSelecionado.getItems().add(ae.getCandidato());
            });
            tbPreSelecionado.getItems().forEach(ae -> {
                Optional<AnuncioCandidatoConclusao> can = tbConclusaoCandidato.getItems().stream().filter(c -> c.getCandidato() == ae)
                        .findAny();
                if (!can.isPresent() && !cbConclusaoCandidato.getItems().contains(ae))
                    cbConclusaoCandidato.getItems().add(ae);
            });
            this.anuncio = anuncio;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Erro ao preencher formulario\n" + e);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private Tab proximaTabVagas(boolean avancar) {
        int t = tpVaga.getSelectionModel().getSelectedIndex();
        if (avancar) return tpVaga.getTabs().get(++t);
        else return tpVaga.getTabs().get(--t);
    }

    private Anuncio.Cronograma receberCronograma() {
        if (dtFimRetorno.getValue() != null)
            return Anuncio.Cronograma.RETORNO_DO_PROCESSO_SELETIVO;
        else if (dtFimPreSelecionado.getValue() != null)
            return Anuncio.Cronograma.ENVIO_DE_CANDIDATO_PRE_SELECIONADO;
        else if (dtFimEntrevista.getValue() != null)
            return Anuncio.Cronograma.ENTREVISTAS_REALIZADAS;
        else if (dtFimAgendamento.getValue() != null)
            return Anuncio.Cronograma.AGENDAMENTO_DE_ENTREVISTA;
        else if (dtFimRecrutamento.getValue() != null)
            return Anuncio.Cronograma.RECRUTAMENTO_DE_CURRICULOS;
        else if (dtFimDivulgacao.getValue() != null)
            return Anuncio.Cronograma.DIVULGACAO_DA_VAGA;
        else if (cbStatusContrato.getValue() != Anuncio.StatusContrato.ACEITO && cbStatusContrato.getValue() != null)
            return Anuncio.Cronograma.CONTRATO;
        else
            return Anuncio.Cronograma.FORMULARIO;
    }

    @FXML
    private void remover(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exclusão...");
        alert.setHeaderText(null);
        if (!txCodigo.getText().equals("")) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Tem certeza disso?");
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get() == ButtonType.OK) {
                try {
                    loadFactory();
                    anuncios = new AnunciosImp(getManager());
                    anuncio = anuncios.findById(Long.parseLong(txCodigo.getText()));

                    // atualizar contadores dos candidatos
                    tbCurriculos.getItems().forEach(c -> observer.registerCandidato(c, EnumCandidato.CURRICULO, -1));
                    tbEntrevista.getItems()
                            .forEach(c -> observer.registerCandidato(c.getCandidato(), EnumCandidato.ENTREVISTA, -1));
                    tbPreSelecionado.getItems()
                            .forEach(c -> observer.registerCandidato(c, EnumCandidato.PRESELECAO, -1));

                    tbConclusaoCandidato.getItems().forEach(can -> {
                        Candidato cand = can.getCandidato();
                        cand.setOcupado(0);
                        cand.setOcupadoDetalhes("Liberado pois anuncio " + anuncio.getId() + " foi removido");
                        observer.registerCandidato(cand, EnumCandidato.APROVACAO, -1);
                    });

                    anuncios.remove(anuncio);
                    candidatos = new CandidatosImp(getManager());
                    observer.notifyUpdate(candidatos);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Registro excluido com sucesso!");
                    alert.showAndWait();
                    anuncio = null;
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Falha ao excluir o registro!\n" + e);
                    alert.showAndWait();
                    e.printStackTrace();
                } finally {
                    close();
                }
            }

        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Nenhum registro selecionado!");
            alert.showAndWait();
        }
    }

    @FXML
    void removerDocumento(ActionEvent event) {
        if (!txDocumento.getText().equals("")) {
            storage.delete(txDocumento.getText());
            txDocumento.setText("");
            if (anuncio != null) {
                try {
                    loadFactory();
                    anuncios = new AnunciosImp(getManager());
                    anuncio.getFormularioRequisicao().setFormulario("");
                    anuncios.save(anuncio);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close();
                }
            }
        }
    }

    @FXML
    private void sair(ActionEvent event) {
        stage.close();
    }

    @FXML
    private void salvar(ActionEvent event) {
        FormularioRequisicao fr = null;
        AnuncioCronograma ac = null;

        // tela inicial
        if (txCodigo.getText().equals("")) {
            anuncio = new Anuncio();
            anuncio.setCriadoEm(Calendar.getInstance());
            anuncio.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
            fr = new FormularioRequisicao();
            fr.setAnuncio(anuncio);
            ac = new AnuncioCronograma();
            ac.setAnuncio(anuncio);
            anuncio.setFormularioRequisicao(fr);
            anuncio.setCronogramaDetails(ac);
        } else {
            fr = anuncio.getFormularioRequisicao();
            ac = anuncio.getCronogramaDetails();
        }
        anuncio.setNome(txNome.getText().trim());
        anuncio.setCliente(cbCliente.getValue());
        anuncio.setResponsavel(txResponsavel.getText());
        anuncio.setAnuncioStatus(cbStatus.getValue());
        anuncio.setCronograma(cbCronograma.getValue());
        anuncio.setDataAbertura(dtAbertura.getValue() == null ? null
                : GregorianCalendar.from(dtAbertura.getValue().atStartOfDay(ZoneId.systemDefault())));
        anuncio.setDataEncerramento(dtEncerramento.getValue() == null ? null
                : GregorianCalendar.from(dtEncerramento.getValue().atStartOfDay(ZoneId.systemDefault())));
        anuncio.setDataAdmissao(dtAdmissao.getValue() == null ? null
                : GregorianCalendar.from(dtAdmissao.getValue().atStartOfDay(ZoneId.systemDefault())));
        /*
         * //anuncio.setDataAdmissao(Date.from(dtAdmissao.getValue().
         * atStartOfDay(ZoneId.systemDefault()).toInstant())); //area da cargo
         */

        // area do formulario

        // 1
        if (cbVaga1Cargo.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Informe um perfil da Cargo antes de prosseguir!");
            alert.show();
            return;
        }
        fr.setCargo(cbVaga1Cargo.getValue());
        fr.setNivel(cbVaga1CargoNivel.getValue());
        fr.setCargoAds(txCargoAdc.getText());
        fr.setCargaHoraria(txVaga1CargaHoraria.getText());
        if (rbVaga1Estagiario.isSelected())
            fr.setTipo("Estagio");
        else if (rbVaga1Temporario.isSelected())
            fr.setTipo("Temporario");
        else
            fr.setTipo("CLT");
        fr.setHorarioTrabalho(txVaga1HorarioTrabalho.getText());
        fr.setInicioPeriodo(dtVaga1DataInicio.getValue() == null ? null
                : GregorianCalendar.from(dtVaga1DataInicio.getValue().atStartOfDay(ZoneId.systemDefault())));
        fr.setFimPeriodo(dtVaga1DataFim.getValue() == null ? null
                : GregorianCalendar.from(dtVaga1DataFim.getValue().atStartOfDay(ZoneId.systemDefault())));
        fr.setSigiloso(rbVaga1ProcessoSigilosoSim.isSelected() ? 1 : 0);
        // 2
        if (rbVaga2MotivoDesligado.isSelected())
            fr.setMotivo("Desligado");
        else if (rbVaga2MotivoQuadro.isSelected())
            fr.setMotivo("Quadro");
        else if (rbVaga2MotivoAfastamento.isSelected())
            fr.setMotivo("Afastamento");
        else if (rbVaga2MotivoOutro.isSelected()) {
            fr.setMotivo("Outro");
            fr.setMotivoObs(txVaga2MotivoOutro.getText());
        }
        // 3
        fr.setEscolaridade(cbVaga3Escolaridade.getValue());
        fr.setCurso(cbVaga3Formacao.getValue());
        fr.setIdioma(txVaga3Idioma.getText());
        fr.setCidade(cbVaga3Municipio.getValue());
        Cidade cidade = fr.getCidade();
        if (cidade != null) {
            cbVaga3Municipio.setValue(cidade);
            cbVaga3Estado.setValue(cidade.getEstado());
        }
        fr.setTempoExperiencia(cbVaga3Tempo.getValue() == null ? -1 : cbVaga3Tempo.getValue());
        fr.setTipoExperiencia(cbVaga3Tipo.getValue());
        // 3.1
        fr.setCompetencia(txVaga31Competencia.getText());
        // 3.2
        fr.setAtividade(txVaga32DescricaoAtividades.getText());
        // 4
        if (ckVaga4ACombinar.isSelected()) {
            fr.setSalarioCombinar(1);
            fr.setSalario(new BigDecimal(0.00));
        } else {
            try {
                fr.setSalarioCombinar(0);
                fr.setSalario(new BigDecimal(txVaga4Salario.getText().trim().replace(",", ".")));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valor Incorretor");
                alert.setContentText("O valor de salário esta incorreto");
                alert.showAndWait();
                return;
            }
        }
        fr.setObservacao(txVaga4Observacao.getText());
        fr.setComissao(rbVaga4ComissaoSim.isSelected() ? 1 : 0);
        if (rbVaga4ComissaoSim.isSelected()) {
            BigDecimal bd = new BigDecimal(txVaga4Comissao.getText().replace(",", "").trim().equals("") ? "0.00"
                    : txVaga4Comissao.getText().replace(",", "."));
            fr.setComissaoValor(bd);
        } else
            ;
        if (rbVaga4ComissaoNao.isSelected()) {
            fr.setComissaoValor(new BigDecimal(0.00));
        }
        // 4.1
        if (rbVaga41VRSim.isSelected()) {
            fr.setValeRefeicao(1);
            BigDecimal bd = new BigDecimal(txVaga41VRValor.getText().replace(",", "").trim().equals("") ? "0.00"
                    : txVaga41VRValor.getText().replace(",", "."));
            fr.setVrValor(bd);
        } else {
            fr.setValeRefeicao(0);
            fr.setVrValor(new BigDecimal(0.00));
        }
        fr.setValeTransporte(rbVaga41VTSim.isSelected() ? 1 : 0);
        fr.setAssistenciaMedica(rbVaga41AMedicaSim.isSelected() ? 1 : 0);
        if (rbVaga41AMedicaSim.isSelected()) {
            fr.setAssistenciaMedicaValor(txVaga41AMedicaDetalhes.getText());
        }
        fr.setOutroBeneficio(txVaga41Outro.getText());
        System.out.println("txdocumentos is null " + (txDocumento == null) + " value=" + txDocumento.getText() + "|");

        if (!txDocumento.getText().trim().equals("") && !txDocumento.getText().startsWith(PathStorageEnum.FORMULARIO_REQUISICAO.getDescricao() + "/")) {
            try {
                storage.transferTo(txDocumento.getText(),
                        PathStorageEnum.FORMULARIO_REQUISICAO.getDescricao() + "/" + txDocumento.getText());
                txDocumento
                        .setText(PathStorageEnum.FORMULARIO_REQUISICAO.getDescricao() + "/" + txDocumento.getText());
                fr.setFormulario(txDocumento.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // area do contrato
        anuncio.setDataEnvioContrato(dtEnvioContrato.getValue() == null ? null
                : GregorianCalendar.from(dtEnvioContrato.getValue().atStartOfDay(ZoneId.systemDefault())));
        anuncio.setDataRetornoContrato(dtRetornoContrato.getValue() == null ? null
                : GregorianCalendar.from(dtRetornoContrato.getValue().atStartOfDay(ZoneId.systemDefault())));
        anuncio.setStatusContrato(cbStatusContrato.getValue());

        // area de curriculos - Candidato
        anuncio.setCurriculoSet(tbCurriculos.getItems().stream().collect(Collectors.toSet()));

        // area de entrevistas - AnuncioEntrevista
        anuncio.setEntrevistaSet(tbEntrevista.getItems().stream().collect(Collectors.toSet()));

        // area de pre selecao - Candidato
        anuncio.setPreSelecaoSet(tbPreSelecionado.getItems().stream().collect(Collectors.toSet()));

        // area de conclusao
        anuncio.setConclusaoSet(tbConclusaoCandidato.getItems().stream().collect(Collectors.toSet()));

        // area de datas do cronograma
        ac.setDataInicioDivulgacao(dtInicioDivulgacao.getValue() == null ? null
                : GregorianCalendar.from(dtInicioDivulgacao.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setDataFimDivulgacao(dtFimDivulgacao.getValue() == null ? null
                : GregorianCalendar.from(dtFimDivulgacao.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setDivulgacao(txDivulgacaoObs.getText());
        ac.setDataInicioRecrutamento(dtInicioRecrutamento.getValue() == null ? null
                : GregorianCalendar.from(dtInicioRecrutamento.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setDataFimRecrutamento(dtFimRecrutamento.getValue() == null ? null
                : GregorianCalendar.from(dtFimRecrutamento.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setRecrutamento(txRecrutamentoObs.getText());
        ac.setDataInicioAgendamento(dtInicioAgendamento.getValue() == null ? null
                : GregorianCalendar.from(dtInicioAgendamento.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setDataFimAgendamento(dtFimAgendamento.getValue() == null ? null
                : GregorianCalendar.from(dtFimAgendamento.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setAgendamento(txAgendamentoObs.getText());
        ac.setDataInicioEntrevista(dtInicioEntrevista.getValue() == null ? null
                : GregorianCalendar.from(dtInicioEntrevista.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setDataFimEntrevista(dtFimEntrevista.getValue() == null ? null
                : GregorianCalendar.from(dtFimEntrevista.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setEntrevista(txEntrevistaObs.getText());
        ac.setDataInicioPreSelecionado(dtInicioPreSelecionado.getValue() == null ? null
                : GregorianCalendar.from(dtInicioPreSelecionado.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setDataFimPreSelecionado(dtFimPreSelecionado.getValue() == null ? null
                : GregorianCalendar.from(dtFimPreSelecionado.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setPreSelecionado(txPreSelecionadoObs.getText());
        ac.setDataInicioRetorno(dtInicioRetorno.getValue() == null ? null
                : GregorianCalendar.from(dtInicioRetorno.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setDataFimRetorno(dtFimRetorno.getValue() == null ? null
                : GregorianCalendar.from(dtFimRetorno.getValue().atStartOfDay(ZoneId.systemDefault())));
        ac.setRetorno(txRetornoObs.getText());
        anuncio.setCronogramaDetails(ac);
        anuncio.setCronograma(receberCronograma());
        try {
            loadFactory();
            anuncios = new AnunciosImp(getManager());
            anuncio = anuncios.save(anuncio);
            txCodigo.setText(String.valueOf(anuncio.getId()));
            candidatos = new CandidatosImp(getManager());
            observer.notifyUpdate(candidatos);
            preencherFormulario(anuncio);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Salvo com sucesso");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Falha ao salvar o registro!" + e);
            alert.showAndWait();
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @SuppressWarnings("unchecked")
    private void tabelaCurriculo() {
        tbCurriculos.getColumns().clear();
        TableColumn<Candidato, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

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
                    setText(item);
                }
            }
        });
        colunaNome.setPrefWidth(150);
        TableColumn<Candidato, Calendar> colunaIdade = new TableColumn<>("Idade");
        colunaIdade.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        colunaIdade.setCellFactory((TableColumn<Candidato, Calendar> param) -> new TableCell<Candidato, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    LocalDate localDate = item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Period period = Period.between(localDate, LocalDate.now());
                    setText(period.getYears() + "");
                }
            }
        });

        TableColumn<Candidato, Calendar> colunaCriadoEm = new TableColumn<>("Atualizacao");
        colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("ultimaModificacao"));
        colunaCriadoEm.setCellFactory((TableColumn<Candidato, Calendar> param) -> new TableCell<Candidato, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getTime()));
                }
            }
        });
        colunaCriadoEm.setPrefWidth(100);
        TableColumn<Candidato, Number> colunaPossuiIndicacao = new TableColumn<>("Indicação");
        colunaPossuiIndicacao.setCellValueFactory(new PropertyValueFactory<>("indicacao"));
        colunaPossuiIndicacao
                .setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.intValue() == 1) {
                                setText("Sim");
                            } else
                                setText("Não");
                        }
                    }
                });
        TableColumn<Candidato, Number> colunaAnuncios = new TableColumn<>("Anuncios");
        colunaAnuncios.setCellValueFactory(new PropertyValueFactory<>("totalRecrutamento"));
        colunaAnuncios.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
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
        TableColumn<Candidato, Number> colunaSelecoes = new TableColumn<>("Entrevistas");
        colunaSelecoes.setCellValueFactory(new PropertyValueFactory<>("totalEntrevista"));
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
        colunaSelecoes.setPrefWidth(100);

        TableColumn<Candidato, String> colunaFormulario = new TableColumn<>("");
        colunaFormulario.setCellValueFactory(new PropertyValueFactory<>("formulario"));
        colunaFormulario.setCellFactory((TableColumn<Candidato, String> param) -> new TableCell<Candidato, String>() {
            JFXButton button = new JFXButton();//

            @Override
            protected void updateItem(String item, boolean empty) {

                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    Candidato c = tbCurriculos.getItems().get(getIndex());
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_CLIP);
                    } catch (IOException e) {
                    }
                    if (c.getFormulario().equals(""))
                        button.setVisible(false);
                    button.setOnAction(event -> visualizarFormulario(c.getFormulario(), storage));
                    setGraphic(button);
                }
            }
        });
        TableColumn<Candidato, String> colunaVer = new TableColumn<>("");
        colunaVer.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaVer.setCellFactory((TableColumn<Candidato, String> param) -> {
            final TableCell<Candidato, String> cell = new TableCell<Candidato, String>() {
                final JFXButton button = new JFXButton("Ver Perfil");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> {
                            abrirPerfilCandidato(tbCurriculos.getItems().get(getIndex()));
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        TableColumn<Candidato, String> colunaRemover = new TableColumn<>("");
        colunaRemover.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaRemover.setCellFactory((TableColumn<Candidato, String> param) -> {
            final TableCell<Candidato, String> cell = new TableCell<Candidato, String>() {
                final JFXButton button = new JFXButton("Remover");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> {
                            boolean aceitar = false;
                            Candidato candidato = tbCurriculos.getItems().get(getIndex());
                            Optional<AnuncioEntrevista> result = tbEntrevista.getItems().stream().filter(f -> f.getCandidato().getId() == candidato.getId()).findFirst();
                            if (result.isPresent()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Operação impossivel");
                                alert.setHeaderText("Candidato não pode ser removido!");
                                alert.setContentText(
                                        "O candidato encontra-se no processo de Entrevista desse anuncio, portanto não pode ser removido");
                                alert.showAndWait();
                            } else {
                                tbCurriculos.getItems().remove(getIndex());
                                observer.registerCandidato(candidato, EnumCandidato.CURRICULO, -1);
                            }
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        tbCurriculos.getColumns().addAll(colunaId, colunaNome, colunaIdade, colunaCriadoEm, colunaPossuiIndicacao,
                colunaAnuncios, colunaSelecoes, colunaFormulario, colunaVer, colunaRemover);
    }

    @SuppressWarnings("unchecked")
    private void tabelaEntrevista() {
        tbEntrevista.getColumns().clear();
        TableColumn<AnuncioEntrevista, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

        TableColumn<AnuncioEntrevista, Candidato> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaNome.setCellFactory(
                (TableColumn<AnuncioEntrevista, Candidato> param) -> new TableCell<AnuncioEntrevista, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getNome());
                        }
                    }
                });
        colunaNome.setPrefWidth(150);
        TableColumn<AnuncioEntrevista, Candidato> colunaIdade = new TableColumn<>("Idade");
        colunaIdade.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaIdade.setCellFactory(
                (TableColumn<AnuncioEntrevista, Candidato> param) -> new TableCell<AnuncioEntrevista, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.getDataNascimento() != null) {
                                LocalDate localDate = item.getDataNascimento().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDate();
                                Period period = Period.between(localDate, LocalDate.now());
                                setText(period.getYears() + "");
                            }
                        }
                    }
                });

        TableColumn<AnuncioEntrevista, Candidato> colunaCriadoEm = new TableColumn<>("Atualização");
        colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaCriadoEm.setCellFactory(
                (TableColumn<AnuncioEntrevista, Candidato> param) -> new TableCell<AnuncioEntrevista, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.getUltimaModificacao() != null)
                                setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getUltimaModificacao().getTime()));
                        }
                    }
                });
        colunaCriadoEm.setPrefWidth(100);
        TableColumn<AnuncioEntrevista, Candidato> colunaPossuiIndicacao = new TableColumn<>("Indicação");
        colunaPossuiIndicacao.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaPossuiIndicacao.setCellFactory(
                (TableColumn<AnuncioEntrevista, Candidato> param) -> new TableCell<AnuncioEntrevista, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.getIndicacao() == 1) {
                                setText("Sim");
                            } else
                                setText("Não");
                        }
                    }
                });
        TableColumn<AnuncioEntrevista, Candidato> colunaAnuncios = new TableColumn<>("Anuncios");
        colunaAnuncios.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaAnuncios.setCellFactory(
                (TableColumn<AnuncioEntrevista, Candidato> param) -> new TableCell<AnuncioEntrevista, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(String.valueOf(item.getTotalRecrutamento()));
                        }
                    }
                });
        TableColumn<AnuncioEntrevista, Candidato> colunaSelecoes = new TableColumn<>("Entrevistas");
        colunaSelecoes.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaSelecoes.setCellFactory(
                (TableColumn<AnuncioEntrevista, Candidato> param) -> new TableCell<AnuncioEntrevista, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(String.valueOf(item.getTotalEntrevista()));
                        }
                    }
                });
        colunaSelecoes.setPrefWidth(100);
        TableColumn<AnuncioEntrevista, AnuncioEntrevistaAnalise> colunaStatusEntrevista = new TableColumn<>("Entrevista");
        colunaStatusEntrevista.setCellValueFactory(new PropertyValueFactory<>("entrevista"));
        colunaStatusEntrevista.setCellFactory(
                (TableColumn<AnuncioEntrevista, AnuncioEntrevistaAnalise> param) -> new TableCell<AnuncioEntrevista, AnuncioEntrevistaAnalise>() {
                    @Override
                    protected void updateItem(AnuncioEntrevistaAnalise item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item == null) {
                                setText("Pendente");
                                setStyle("-fx-text-fill:red");
                            } else {
                                setText("Realizada");
                                setStyle("-fx-text-fill:green");
                            }
                        }
                    }
                });
        colunaStatusEntrevista.setPrefWidth(100);
        TableColumn<AnuncioEntrevista, AnuncioEntrevistaAnalise> colunaEntrevista = new TableColumn<>("");
        colunaEntrevista.setCellValueFactory(new PropertyValueFactory<>("entrevista"));
        colunaEntrevista.setCellFactory((TableColumn<AnuncioEntrevista, AnuncioEntrevistaAnalise> param) -> {
            final TableCell<AnuncioEntrevista, AnuncioEntrevistaAnalise> cell = new TableCell<AnuncioEntrevista, AnuncioEntrevistaAnalise>() {
                final JFXButton button = new JFXButton();

                @Override
                protected void updateItem(AnuncioEntrevistaAnalise item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setText(item == null ? "Nova Entrevista" : "Editar Entrevista");
                        button.setOnAction(event -> {
                            if (button.getText().equals("Nova Entrevista")) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmação");
                                alert.setHeaderText("Por favor, escolha uma operação para avançar");
                                alert.setContentText("Escolha uma opção.");
                                // ButtonType buttonAgendar = new ButtonType("Agendar Entrevista");
                                ButtonType buttonAgora = new ButtonType("Entrevistar Agora");
                                ButtonType buttonCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                                alert.getButtonTypes().setAll(buttonAgora, buttonCancel);

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == buttonAgora)
                                    abrirEntrevista(tbEntrevista.getItems().get(getIndex()), getIndex());
                            } else abrirEntrevista(tbEntrevista.getItems().get(getIndex()), getIndex());

                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        colunaEntrevista.setPrefWidth(150);

        TableColumn<AnuncioEntrevista, Candidato> colunaFormulario = new TableColumn<>("");
        colunaFormulario.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaFormulario.setCellFactory((TableColumn<AnuncioEntrevista, Candidato> param) -> new TableCell<AnuncioEntrevista, Candidato>() {
            JFXButton button = new JFXButton();//

            @Override
            protected void updateItem(Candidato item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    Candidato c = tbEntrevista.getItems().get(getIndex()).getCandidato();
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_CLIP);
                    } catch (IOException e) {
                    }
                    if (c.getFormulario().equals(""))
                        button.setVisible(false);
                    button.setOnAction(event -> visualizarFormulario(c.getFormulario(), storage));
                    setGraphic(button);
                }
            }
        });
        TableColumn<AnuncioEntrevista, String> colunaVer = new TableColumn<>("");
        colunaVer.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaVer.setCellFactory((TableColumn<AnuncioEntrevista, String> param) -> {
            final TableCell<AnuncioEntrevista, String> cell = new TableCell<AnuncioEntrevista, String>() {
                final JFXButton button = new JFXButton("Ver Perfil");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> abrirPerfilCandidato(tbEntrevista.getItems().get(getIndex()).getCandidato()));
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        TableColumn<AnuncioEntrevista, String> colunaAvaliacao = new TableColumn<>("");
        colunaAvaliacao.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaAvaliacao.setCellFactory((TableColumn<AnuncioEntrevista, String> param) -> {
            final TableCell<AnuncioEntrevista, String> cell = new TableCell<AnuncioEntrevista, String>() {
                final JFXButton button = new JFXButton("Avaliações");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> {
                            try {
                                loadFactory();
                                AnuncioEntrevista ae = tbEntrevista.getItems().get(getIndex());

                                Candidato c = ae.getCandidato();
                                anunciosEntrevistas = new AnuncioEntrevistasImp(getManager());
                                anuncios = new AnunciosImp(getManager());
                                anuncio.setCurriculoSet(tbCurriculos.getItems().stream().collect(Collectors.toSet()));
                                anuncio.setEntrevistaSet(tbEntrevista.getItems().stream().collect(Collectors.toSet()));
                                anuncio.setPreSelecaoSet(
                                        tbPreSelecionado.getItems().stream().collect(Collectors.toSet()));
                                anuncio.setConclusaoSet(
                                        tbConclusaoCandidato.getItems().stream().collect(Collectors.toSet()));
                                candidatos = new CandidatosImp(getManager());
                                anuncio = anuncios.save(anuncio);
                                observer.notifyUpdate(candidatos);
                                // curriculos
                                tbCurriculos.getItems().clear();
                                tbCurriculos.getItems().addAll(anuncio.getCurriculoSet());
                                // entrevistas
                                tbEntrevista.getItems().clear();
                                tbEntrevista.getItems().addAll(anuncio.getEntrevistaSet());
                                // pre selecao
                                tbPreSelecionado.getItems().clear();
                                tbPreSelecionado.getItems().addAll(anuncio.getPreSelecaoSet());

                                ae = anunciosEntrevistas.findById(ae.getId());

                                FXMLLoader loader = loaderFxml(FXMLEnum.ENTREVISTA_AVALIACAO);
                                Stage stage1 = new Stage();
                                stage1.setTitle("Avaliações do Candidato: " + ae.getCandidato().getNome());
                                final Parent root = loader.load();
                                ControllerEntrevistaAvaliacao controller = loader.getController();
                                final Scene scene = new Scene(root);
                                stage1.initModality(Modality.APPLICATION_MODAL);
                                stage1.initStyle(StageStyle.DECORATED);
                                stage1.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
                                stage1.setScene(scene);
                                stage1.show();
                                controller.iniciar(ae.getAvaliacao(), ae);
                                stage1.setOnCloseRequest(event1 -> {
                                    if (event1.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                                        try {
                                            loadFactory();
                                            anuncios = new AnunciosImp(getManager());
                                            anuncio = anuncios.findById(anuncio.getId());
                                            tbEntrevista.getItems().clear();
                                            tbEntrevista.getItems().addAll(anuncio.getEntrevistaSet());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        } finally {
                                            close();
                                        }
                                    }

                                });


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
        colunaAvaliacao.setPrefWidth(100);
        TableColumn<AnuncioEntrevista, String> colunaRemover = new TableColumn<>("");
        colunaRemover.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaRemover.setCellFactory((TableColumn<AnuncioEntrevista, String> param) -> {
            final TableCell<AnuncioEntrevista, String> cell = new TableCell<AnuncioEntrevista, String>() {
                final JFXButton button = new JFXButton("Remover");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> {
                            AnuncioEntrevista anuncioE = tbEntrevista.getItems().get(getIndex());

                            if (tbPreSelecionado.getItems().contains(anuncioE.getCandidato())) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Operação impossivel");
                                alert.setHeaderText("Candidato não pode ser removido!");
                                alert.setContentText(
                                        "O candidato encontra-se no processo de Pre-seleção desse anuncio, portanto não pode ser removido");
                                alert.showAndWait();
                            } else {
                                tbEntrevista.getItems().remove(getIndex());
                                observer.registerCandidato(anuncioE.getCandidato(), EnumCandidato.ENTREVISTA, -1);
                            }
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });

        tbEntrevista.getColumns().addAll(colunaId, colunaNome, colunaIdade, colunaCriadoEm,
                colunaPossuiIndicacao, colunaAnuncios, colunaSelecoes, colunaStatusEntrevista,
                colunaEntrevista, colunaAvaliacao, colunaFormulario, colunaVer, colunaRemover);
    }

    @SuppressWarnings("unchecked")
    private void tabelaPreSelecao() {
        tbPreSelecionado.getColumns().clear();
        TableColumn<Candidato, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

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
                    setText(item);
                }
            }
        });
        colunaNome.setPrefWidth(150);
        TableColumn<Candidato, Calendar> colunaIdade = new TableColumn<>("Idade");
        colunaIdade.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        colunaIdade.setCellFactory((TableColumn<Candidato, Calendar> param) -> new TableCell<Candidato, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    LocalDate localDate = item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Period period = Period.between(localDate, LocalDate.now());
                    setText(period.getYears() + "");
                }
            }
        });

        TableColumn<Candidato, Calendar> colunaCriadoEm = new TableColumn<>("Atualização");
        colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("ultimaModificacao"));
        colunaCriadoEm.setCellFactory((TableColumn<Candidato, Calendar> param) -> new TableCell<Candidato, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getTime()));
                }
            }
        });
        colunaCriadoEm.setPrefWidth(100);
        TableColumn<Candidato, Number> colunaPossuiIndicacao = new TableColumn<>("Indicação");
        colunaPossuiIndicacao.setCellValueFactory(new PropertyValueFactory<>("indicacao"));
        colunaPossuiIndicacao
                .setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.intValue() == 1) {
                                setText("Sim");
                            } else
                                setText("Não");
                        }
                    }
                });
        TableColumn<Candidato, Number> colunaAnuncios = new TableColumn<>("Anuncios");
        colunaAnuncios.setCellValueFactory(new PropertyValueFactory<>("totalRecrutamento"));
        colunaAnuncios.setCellFactory((TableColumn<Candidato, Number> param) -> new TableCell<Candidato, Number>() {
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
        TableColumn<Candidato, Number> colunaSelecoes = new TableColumn<>("Entrevistas");
        colunaSelecoes.setCellValueFactory(new PropertyValueFactory<>("totalEntrevista"));
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
        colunaSelecoes.setPrefWidth(100);
        TableColumn<Candidato, String> colunaFormulario = new TableColumn<>("");
        colunaFormulario.setCellValueFactory(new PropertyValueFactory<>("formulario"));
        colunaFormulario.setCellFactory((TableColumn<Candidato, String> param) -> new TableCell<Candidato, String>() {
            JFXButton button = new JFXButton();//

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_CLIP);
                    } catch (IOException e) {
                    }
                    if (item.equals(""))
                        button.setVisible(false);
                    button.setOnAction(event -> visualizarFormulario(item, storage));
                    setGraphic(button);
                }
            }
        });
        TableColumn<Candidato, String> colunaVer = new TableColumn<>("");
        colunaVer.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaVer.setCellFactory((TableColumn<Candidato, String> param) -> {
            final TableCell<Candidato, String> cell = new TableCell<Candidato, String>() {
                final JFXButton button = new JFXButton("Ver Perfil");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> {
                            abrirPerfilCandidato(tbPreSelecionado.getItems().get(getIndex()));
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        TableColumn<Candidato, String> colunaRemover = new TableColumn<>("");
        colunaRemover.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaRemover.setCellFactory((TableColumn<Candidato, String> param) -> {
            final TableCell<Candidato, String> cell = new TableCell<Candidato, String>() {
                final JFXButton button = new JFXButton("Remover");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> {
                            Candidato candidato = tbPreSelecionado.getItems().get(getIndex());
                            Optional<AnuncioCandidatoConclusao> result = tbConclusaoCandidato.getItems().stream().filter(f -> f.getCandidato().getId() == candidato.getId()).findFirst();
                            if (result.isPresent()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Operação impossivel");
                                alert.setHeaderText("Candidato não pode ser removido!");
                                alert.setContentText(
                                        "O candidato foi aprovado no processo, portanto não pode ser removido");
                                alert.showAndWait();
                            } else {
                                tbPreSelecionado.getItems().remove(getIndex());
                                observer.registerCandidato(candidato, EnumCandidato.PRESELECAO, -1);
                            }
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        tbPreSelecionado.getColumns().addAll(colunaId, colunaNome, colunaIdade, colunaCriadoEm,
                colunaPossuiIndicacao, colunaAnuncios, colunaSelecoes, colunaFormulario, colunaVer, colunaRemover);
    }

    private void tabelaConclusao() {
        tbConclusaoCandidato.getColumns().clear();
        TableColumn<AnuncioCandidatoConclusao, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

        TableColumn<AnuncioCandidatoConclusao, Candidato> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaNome.setCellFactory(
                (TableColumn<AnuncioCandidatoConclusao, Candidato> param) -> new TableCell<AnuncioCandidatoConclusao, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getNome());
                        }
                    }
                });
        colunaNome.setPrefWidth(150);
        TableColumn<AnuncioCandidatoConclusao, Candidato> colunaIdade = new TableColumn<>("Idade");
        colunaIdade.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaIdade.setCellFactory(
                (TableColumn<AnuncioCandidatoConclusao, Candidato> param) -> new TableCell<AnuncioCandidatoConclusao, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.getDataNascimento() != null) {
                                LocalDate localDate = item.getDataNascimento().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDate();
                                Period period = Period.between(localDate, LocalDate.now());
                                setText(period.getYears() + "");
                            }
                        }
                    }
                });

        TableColumn<AnuncioCandidatoConclusao, Candidato> colunaCriadoEm = new TableColumn<>("Atualização");
        colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaCriadoEm.setCellFactory(
                (TableColumn<AnuncioCandidatoConclusao, Candidato> param) -> new TableCell<AnuncioCandidatoConclusao, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.getUltimaModificacao() != null)
                                setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getUltimaModificacao().getTime()));
                        }
                    }
                });
        colunaCriadoEm.setPrefWidth(100);
        TableColumn<AnuncioCandidatoConclusao, Candidato> colunaPossuiIndicacao = new TableColumn<>("Indicação");
        colunaPossuiIndicacao.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaPossuiIndicacao.setCellFactory(
                (TableColumn<AnuncioCandidatoConclusao, Candidato> param) -> new TableCell<AnuncioCandidatoConclusao, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            if (item.getIndicacao() == 1) {
                                setText("Sim");
                            } else
                                setText("Não");
                        }
                    }
                });
        TableColumn<AnuncioCandidatoConclusao, Candidato> colunaAnuncios = new TableColumn<>("Anuncios");
        colunaAnuncios.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaAnuncios.setCellFactory(
                (TableColumn<AnuncioCandidatoConclusao, Candidato> param) -> new TableCell<AnuncioCandidatoConclusao, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(String.valueOf(item.getTotalRecrutamento()));
                        }
                    }
                });
        TableColumn<AnuncioCandidatoConclusao, Candidato> colunaSelecoes = new TableColumn<>("Entrevistas");
        colunaSelecoes.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaSelecoes.setCellFactory(
                (TableColumn<AnuncioCandidatoConclusao, Candidato> param) -> new TableCell<AnuncioCandidatoConclusao, Candidato>() {
                    @Override
                    protected void updateItem(Candidato item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(String.valueOf(item.getTotalEntrevista()));
                        }
                    }
                });
        colunaSelecoes.setPrefWidth(100);

        TableColumn<AnuncioCandidatoConclusao, Treinamento> colunaTreinamento = new TableColumn<>("Treinamento");
        colunaTreinamento.setCellValueFactory(new PropertyValueFactory<>("treinamento"));
        colunaTreinamento.setCellFactory((TableColumn<AnuncioCandidatoConclusao, Treinamento> param) -> {
            final TableCell<AnuncioCandidatoConclusao, Treinamento> cell = new TableCell<AnuncioCandidatoConclusao, Treinamento>() {
                final JFXButton button = new JFXButton();

                @Override
                protected void updateItem(Treinamento item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setText(item == null ? "Agendar Treinamento" : "Editar Treinamento");
                        button.setOnAction(event -> abrirCandidatoConclusao(tbConclusaoCandidato.getItems().get(getIndex())));
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        colunaTreinamento.setPrefWidth(180);

        TableColumn<AnuncioCandidatoConclusao, Treinamento> colunaTreinamentoNome = new TableColumn<>("Treinamento");
        colunaTreinamentoNome.setCellValueFactory(new PropertyValueFactory<>("treinamento"));
        colunaTreinamentoNome.setCellFactory((TableColumn<AnuncioCandidatoConclusao, Treinamento> param) -> {
            final TableCell<AnuncioCandidatoConclusao, Treinamento> cell = new TableCell<AnuncioCandidatoConclusao, Treinamento>() {
                @Override
                protected void updateItem(Treinamento item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            };
            return cell;
        });

        TableColumn<AnuncioCandidatoConclusao, Number> colunaTreinamentoInicio = new TableColumn<>("Inicio");
        colunaTreinamentoInicio.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaTreinamentoInicio.setCellFactory((TableColumn<AnuncioCandidatoConclusao, Number> param) -> {
            final TableCell<AnuncioCandidatoConclusao, Number> cell = new TableCell<AnuncioCandidatoConclusao, Number>() {
                @Override
                protected void updateItem(Number item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        AnuncioCandidatoConclusao an = tbConclusaoCandidato.getItems().get(getIndex());
                        setText(an.getDataInicoTreinamento() == null ? "" : sdf.format(an.getDataInicoTreinamento().getTime()));
                    }
                }
            };
            return cell;
        });
        TableColumn<AnuncioCandidatoConclusao, Number> colunaTreinamentoFim = new TableColumn<>("Inicio");
        colunaTreinamentoFim.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaTreinamentoFim.setCellFactory((TableColumn<AnuncioCandidatoConclusao, Number> param) -> {
            final TableCell<AnuncioCandidatoConclusao, Number> cell = new TableCell<AnuncioCandidatoConclusao, Number>() {
                @Override
                protected void updateItem(Number item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        AnuncioCandidatoConclusao an = tbConclusaoCandidato.getItems().get(getIndex());
                        setText(an.getDataFimTreinamento() == null ? "" : sdf.format(an.getDataFimTreinamento().getTime()));
                    }
                }
            };
            return cell;
        });
        TableColumn<AnuncioCandidatoConclusao, Candidato> colunaFormulario = new TableColumn<>("");
        colunaFormulario.setCellValueFactory(new PropertyValueFactory<>("candidato"));
        colunaFormulario.setCellFactory((TableColumn<AnuncioCandidatoConclusao, Candidato> param) -> new TableCell<AnuncioCandidatoConclusao, Candidato>() {
            JFXButton button = new JFXButton();//

            @Override
            protected void updateItem(Candidato item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_CLIP);
                    } catch (IOException e) {
                    }
                    if (item.getFormulario().equals(""))
                        button.setVisible(false);
                    button.setOnAction(event -> visualizarFormulario(item.getFormulario(), storage));
                    setGraphic(button);
                }
            }
        });
        TableColumn<AnuncioCandidatoConclusao, String> colunaVer = new TableColumn<>("");
        colunaVer.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaVer.setCellFactory((TableColumn<AnuncioCandidatoConclusao, String> param) -> {
            final TableCell<AnuncioCandidatoConclusao, String> cell = new TableCell<AnuncioCandidatoConclusao, String>() {
                final JFXButton button = new JFXButton("Ver Perfil");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event ->
                                abrirPerfilCandidato(tbConclusaoCandidato.getItems().get(getIndex()).getCandidato())
                        );
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });

        TableColumn<AnuncioCandidatoConclusao, String> colunaRemover = new TableColumn<>("");
        colunaRemover.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaRemover.setCellFactory((TableColumn<AnuncioCandidatoConclusao, String> param) -> {
            final TableCell<AnuncioCandidatoConclusao, String> cell = new TableCell<AnuncioCandidatoConclusao, String>() {
                final JFXButton button = new JFXButton("Remover");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> {
                            AnuncioCandidatoConclusao cn = tbConclusaoCandidato.getItems().remove(getIndex());
                            observer.registerCandidato(cn.getCandidato(), EnumCandidato.APROVACAO, -1);
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });

        tbConclusaoCandidato.getColumns().addAll(colunaId, colunaNome, colunaIdade, colunaCriadoEm,
                colunaPossuiIndicacao, colunaAnuncios, colunaSelecoes, colunaTreinamento, colunaTreinamentoNome,
                colunaTreinamentoInicio, colunaTreinamentoFim, colunaFormulario, colunaVer, colunaRemover);
    }

    @FXML
    void visualizar(ActionEvent event) {
        visualizarFormulario(txDocumento.getText(), storage);
    }

    @FXML
    void visualizarCronogramaDocumento(ActionEvent event) {
        Runnable run = () -> {
            if (anuncio != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Map<String, String> crono = new HashMap<>();
                crono.put("{anuncio.cliente}", anuncio.getCliente().getNome());
                crono.put("{anuncio.data_inicio}",
                        anuncio.getDataAbertura() != null ? sdf.format(anuncio.getDataAbertura().getTime()) : "");
                String vaga = "";

                FormularioRequisicao fr = anuncio.getFormularioRequisicao();
                if (fr != null) {
                    vaga = fr.getCargo() != null ? fr.getCargo().getNome() : "";
                    vaga += fr.getNivel() == null ? " " + fr.getCargoAds() : "" + fr.getNivel() + " " + fr.getCargoAds();
                }
                crono.put("{anuncio.cargo}", vaga);
                crono.put("{anuncio.cliente.contato}",
                        anuncio.getCliente().getPessoaJuridica() != null
                                ? anuncio.getCliente().getPessoaJuridica().getTelefone() + "/"
                                + anuncio.getCliente().getPessoaJuridica().getCelular()
                                : "");
                crono.put("{anuncio.previsao_admissao}",
                        anuncio.getDataAdmissao() != null ? sdf.format(anuncio.getDataAdmissao().getTime()) : "");
                String v1 = "", v2 = "", v3 = "", v4 = "", v5 = "", v6 = "", v7 = "", v8 = "", v9 = "", v10 = "", v11 = "",
                        v12 = "", v13 = "", v14 = "", v15 = "", v16 = "", v17 = "", v18 = "";

                AnuncioCronograma ac = anuncio.getCronogramaDetails();
                if (ac != null) {
                    v1 = ac.getDataInicioDivulgacao() != null ? sdf.format(ac.getDataInicioDivulgacao().getTime()) : "";
                    v2 = ac.getDataFimDivulgacao() != null ? sdf.format(ac.getDataFimDivulgacao().getTime()) : "";
                    v3 = ac.getDivulgacao();
                    v4 = ac.getDataInicioRecrutamento() != null ? sdf.format(ac.getDataInicioRecrutamento().getTime()) : "";
                    v5 = ac.getDataFimRecrutamento() != null ? sdf.format(ac.getDataFimRecrutamento().getTime()) : "";
                    v6 = ac.getRecrutamento();
                    v7 = ac.getDataInicioAgendamento() != null ? sdf.format(ac.getDataInicioAgendamento().getTime()) : "";
                    v8 = ac.getDataFimAgendamento() != null ? sdf.format(ac.getDataFimAgendamento().getTime()) : "";
                    v9 = ac.getAgendamento();
                    v10 = ac.getDataInicioEntrevista() != null ? sdf.format(ac.getDataInicioEntrevista().getTime()) : "";
                    v11 = ac.getDataFimEntrevista() != null ? sdf.format(ac.getDataFimEntrevista().getTime()) : "";
                    v12 = ac.getEntrevista();
                    v13 = ac.getDataInicioPreSelecionado() != null ? sdf.format(ac.getDataInicioPreSelecionado().getTime())
                            : "";
                    v14 = ac.getDataFimPreSelecionado() != null ? sdf.format(ac.getDataFimPreSelecionado().getTime()) : "";
                    v15 = ac.getPreSelecionado();
                    v16 = ac.getDataInicioRetorno() != null ? sdf.format(ac.getDataInicioDivulgacao().getTime()) : "";
                    v17 = ac.getDataFimRetorno() != null ? sdf.format(ac.getDataFimRetorno().getTime()) : "";
                    v18 = ac.getRetorno();
                }
                crono.put("{anuncio.data_divulgacao}", v1);
                crono.put("{anuncio.data_fim_divulgacao}", v2);
                crono.put("{anuncio.divulgacao}", v3);
                crono.put("{anuncio.data_recrutamento}", v4);
                crono.put("{anuncio.data_fim_recrutamento}", v5);
                crono.put("{anuncio.recrutamento}", v6);
                crono.put("{anuncio.data_agendamento}", v7);
                crono.put("{anuncio.data_fim_agendamento}", v8);
                crono.put("{anuncio.agendamento}", v9);
                crono.put("{anuncio.data_entrevista}", v10);
                crono.put("{anuncio.data_fim_entrevista}", v11);
                crono.put("{anuncio.entrevista}", v12);
                crono.put("{anuncio.data_pre-selecionado}", v13);
                crono.put("{anuncio.data_fim_pre-selecionado}", v14);
                crono.put("{anuncio.pre-selecionado}", v15);
                crono.put("{anuncio.data_retorno}", v16);
                crono.put("{anuncio.data_fim_retorno}", v17);
                crono.put("{anuncio.retorno}", v18);
                crono.put("{data.hoje}", sdf.format(Calendar.getInstance().getTime()));
                try {
                    OfficeEditor officeJob = OfficeEditorProducer
                            .newConfig(FileOfficeEnum.cronograma_selecao.getDescricao());
                    File f = officeJob.edit(crono, new File(FileOfficeEnum.cronograma_selecao.getDescricao()));
                    officeJob.openFile(f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(run).start();
    }

}
