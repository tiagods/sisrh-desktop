package com.plkrhone.sisrh.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.model.*;
import org.fxutils.maskedtextfield.MaskTextField;
import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.Curso;
import com.plkrhone.sisrh.model.anuncio.AnuncioCronograma;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAnalise;
import com.plkrhone.sisrh.observer.CandidatoObserver;
import com.plkrhone.sisrh.observer.EnumCandidato;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistasImp;
import com.plkrhone.sisrh.repository.helper.AnunciosImp;
import com.plkrhone.sisrh.repository.helper.CandidatosImp;
import com.plkrhone.sisrh.repository.helper.CidadesImp;
import com.plkrhone.sisrh.repository.helper.ClientesImp;
import com.plkrhone.sisrh.repository.helper.CursosSuperioresImp;
import com.plkrhone.sisrh.repository.helper.TreinamentosImp;
import com.plkrhone.sisrh.repository.helper.VagasImp;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by Tiago on 08/08/2017.
 */
public class ControllerAnuncio extends PersistenciaController implements Initializable {
	@FXML
	private JFXTabPane tab;

	@FXML
	private Tab tabPesquisa;

	@FXML
	private AnchorPane panPesquisa;

	@FXML
	private JFXComboBox<String> cbEmpresaPesquisa;

	@FXML
	private ComboBox<Cargo> cbVagaPesquisa;

	@FXML
	private JFXComboBox<Anuncio.Cronograma> cbCronogramaPesquisa;

	@FXML
	private JFXComboBox<Anuncio.AnuncioStatus> cbStatusPesquisa;

	@FXML
	private JFXDatePicker dtInicialPesquisa;

	@FXML
	private JFXDatePicker dtFinalPesquisa;

	@FXML
	private TableView<Anuncio> tbAnuncio;

	@FXML
	private JFXComboBox<String> cbFiltro;

	@FXML
	private JFXTextField txPesquisa;

	@FXML
	private JFXComboBox<String> cbDatasFiltro;

	@FXML
	private PieChart grafico;

	@FXML
	private JFXRadioButton rbStatusGrafico;

	@FXML
	private JFXRadioButton rbCronogramaGrafico;

	@FXML
	private Tab tabCadastro;

	@FXML
	private AnchorPane pnCadastro;

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
	private ComboBox<Cargo> cbVaga1Cargo;

	@FXML
	private JFXComboBox<String> cbVaga1CargaHoraria;

	@FXML
	private JFXTextField txVaga1HorarioTrabalho;

	@FXML
	private JFXRadioButton rbVaga1ProcessoSigilosoNao;

	@FXML
	private JFXRadioButton rbVaga1ProcessoSigilosoSim;

	@FXML
	private JFXButton btVagaAvancar1;

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
	private MaskTextField txVaga4Salario;

	@FXML
	private JFXCheckBox ckVaga4ACombinar;

	@FXML
	private JFXTextArea txVaga4Observacao;

	@FXML
	private MaskTextField txVaga4Comissao;

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
	private MaskTextField txVaga41VRValor;

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
	private JFXButton btAnexarFormulario;

	@FXML
	private JFXButton btAbrirFormulario;

	@FXML
	private JFXButton btRemoverFormulario;
	
	@FXML
	private JFXTextField txDocumento;
	
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
	private JFXComboBox<Candidato> cbCandidatoConclusao;

	@FXML
	private JFXCheckBox ckTreinamentoConclusao;

	@FXML
	private JFXComboBox<Treinamento> cbNomeTreinamento;

	@FXML
	private JFXDatePicker dtInicioConclusao;

	@FXML
	private JFXDatePicker dtFimConclusao;

	@FXML
	private JFXButton btnVerCandidatoConclusao;

	@FXML
	private JFXButton btnNovoTreinamentoConclusao;

	@FXML
	private JFXButton btnRemoverCandidato;

	@FXML
	private JFXButton btnAprovarCandidato;

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

	@FXML
	private JFXButton btSalvarEConcluir;

	@FXML
	private JFXComboBox<CargoNivel> cbCargoNivel;

	@FXML
	private JFXTextField txCargoAdc;

	@FXML
	private JFXButton btNovo;

	@FXML
	private JFXButton btAlterar;

	@FXML
	private JFXButton btExcluir;

	@FXML
	private JFXButton btSalvar;

	Anuncio anuncio;
	AnunciosImp anuncios;
	AnuncioEntrevistasImp anunciosEntrevistas;
	VagasImp vagas;
	CandidatosImp candidatos;
	ClientesImp clientes;
	CursosSuperioresImp cursos;
	CidadesImp cidades;
	TreinamentosImp treinamentos;
	CandidatoObserver observer = CandidatoObserver.getInstance();
	Storage storage = StorageProducer.newConfig();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		tabelaCurriculo();
		tabelaEntrevista();
		tabelaPreSelecao();
		limparTela(pnCadastro.getChildren());
		try {
			loadFactory();
			anuncios = new AnunciosImp(getManager());
			combos();
			List<Anuncio> anuncioList = anuncios.filtrar(null, Anuncio.AnuncioStatus.EM_ANDAMENTO, 1, null, "", null, null, "",
					"");
			tbAnuncio.setItems(FXCollections.observableList(anuncioList));
			desbloquear(true, pnCadastro.getChildren());
			limparTela(pnCadastro.getChildren());
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setContentText("Erro ao listar anuncios\n " + e);
			alert.showAndWait();
			e.printStackTrace();
		} finally {
			close();
		}
		cbStatus.setValue(Anuncio.AnuncioStatus.EM_ANDAMENTO);
		cbCronograma.setValue(Anuncio.Cronograma.DIVULGACAO_DA_VAGA);

		dtAbertura.setValue(LocalDate.now());
	}

	private void abrirPerfilCandidato(Candidato candidato) {
		try {
			FXMLLoader loader = carregarFxmlLoader("Candidato");
			Stage stage = carregarStage(loader,
					"Anuncio: " + anuncio.getNome() + "-Cliente: " + anuncio.getCliente().getNome());
			ControllerCandidato controllerCandidato = loader.getController();
			controllerCandidato.preencherFormulario(candidato);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void abrirEntrevista(AnuncioEntrevista ae) {
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
			anuncio.setCandidatoAprovado(cbCandidatoConclusao.getValue());
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

			Optional<AnuncioEntrevista> newAe = 
					tbEntrevista.getItems().stream().filter(can -> can.getCandidato().getId() == c.getId()).findFirst();
			if (newAe.isPresent()) {
				FXMLLoader loader = carregarFxmlLoader("Entrevista");
				Stage stage = carregarStage(loader, "");
				ControllerEntrevista controller = loader.getController();
				controller.iniciar(newAe.get(), stage);
				stage.show();
				stage.setOnCloseRequest(event1 -> {
					if (event1.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
						try {
							loadFactory();
							anuncios = new AnunciosImp(getManager());
							tbEntrevista.getItems().clear();
							tbEntrevista.getItems().addAll(anuncio.getEntrevistaSet());
							tbEntrevista.refresh();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							close();
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	@FXML
	void anexarFormulario(ActionEvent event) {
		Set<FileChooser.ExtensionFilter> filters = new HashSet<>();
		// alert = new Alert(Alert.AlertType.CONFIRMATION);
		// alert.setTitle("Informação importante!");
		// alert.setHeaderText("Deseja usar modelo do Word para imprimir seus
		// formulários pré-prenchidos?");
		// alert.setContentText(
		// "Para usar essa técnica, salve seus formularios no padrão Microsoft Word 2007
		// (.doc),\n"
		// + "Caso contrario clique em cancelar");
		// Optional<ButtonType> buttonType = alert.showAndWait();
		// if (buttonType.get() == ButtonType.CANCEL) {
		// filters.add(new FileChooser.ExtensionFilter("MS Word 2007", "*.doc"));
		// filters.add(new FileChooser.ExtensionFilter("*.pdf", "*.doc|*.pdf|*.docx"));
		// filters.add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		// }
		filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc", "*.pdf", "*.docx"));
		File file = storage.carregarArquivo(new Stage(), filters);
		if (file != null) {
			String novoNome = storage.gerarNome(file, PathStorageEnum.FORMULARIO_REQUISICAO.getDescricao());
			try {
				storage.uploadFile(file, novoNome);
				txDocumento.setText(novoNome);
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Armazenamento de arquivo");
				alert.setHeaderText("Não foi possivel enviar o arquivo para o servidor");
				alert.setContentText(
						"Um erro desconhecido não permitiu o envio do arquivo para uma pasta do servidor\n" + e);
				alert.showAndWait();
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void alterar(ActionEvent event) {
		if (!txCodigo.getText().equals("")) {
			observer.clear();
			desbloquear(true, pnCadastro.getChildren());
		}
	}

	private Candidato aprovarCandidato() {
		List<Candidato> choice = new ArrayList<>();
		choice.addAll(tbPreSelecionado.getItems());
		ChoiceDialog<Candidato> dialog = new ChoiceDialog<>(null, choice);
		dialog.setTitle("Candidatos Pré-Selecionados");
		dialog.setHeaderText("Candidato Aprovado");
		dialog.setContentText("Escolha o candidato aprovado:");
		Optional<Candidato> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		} else
			return null;
	}

	private FXMLLoader carregarFxmlLoader(String fxmlName) {
		URL url = getClass().getResource("/fxml/" + fxmlName + ".fxml");
		FXMLLoader loader = new FXMLLoader(url);
		return loader;
	}

	private Stage carregarStage(FXMLLoader loader, String title) throws IOException {
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(title);
		return stage;
	}

	@FXML
	private void cadastrarTreinamento(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Criação de setores");
		dialog.setHeaderText("Crie um novo setor:");
		dialog.setContentText("Por favor entre com um novo nome:");
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (result.get().trim().length() == 0) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Insira um nome para o departamento");
				alert.setContentText(null);
				alert.showAndWait();
			} else {
				try {
					loadFactory();
					treinamentos = new TreinamentosImp(getManager());
					Treinamento treinamento = treinamentos.findByNome(result.get().trim());
					if (treinamento == null) {
						Treinamento t = new Treinamento();
						t.setNome(result.get().trim());
						treinamentos.save(t);

						// preencher combos
						List<Treinamento> treinamentoList = treinamentos.getAll();
						ObservableList<Treinamento> novaLista = FXCollections.observableArrayList();
						novaLista.add(null);
						novaLista.addAll(treinamentoList);
						Treinamento tTemp = cbNomeTreinamento.getValue();
						cbNomeTreinamento.setItems(novaLista);
						cbNomeTreinamento.setItems(novaLista);
						if (tTemp != null)
							cbNomeTreinamento.getSelectionModel().select(tTemp);
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Valor duplicado");
						alert.setContentText("Já existe um cadastro com o texto informado");
						alert.showAndWait();
					}
				} catch (Exception e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Valor duplicado");
					alert.setContentText("Ocorreu um erro ao salvar o registro\n" + e);
					alert.showAndWait();

				} finally {
					close();
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void combos() {
		// combos da area de pesquisa
		cbCronogramaPesquisa.getItems().add(null);
		cbCronogramaPesquisa.getItems().addAll(FXCollections.observableList(Arrays.asList(Anuncio.Cronograma.values())));
		cbCronogramaPesquisa.setValue(null);
		cbEmpresaPesquisa.getItems().addAll("Qualquer", "Ativo", "Inativo");
		cbEmpresaPesquisa.setValue("Qualquer");
		cbStatusPesquisa.getItems().add(null);
		cbStatusPesquisa.getItems().addAll(FXCollections.observableList(Arrays.asList(Anuncio.AnuncioStatus.values())));
		cbStatusPesquisa.setValue(Anuncio.AnuncioStatus.EM_ANDAMENTO);
		
		
		
		vagas = new VagasImp(getManager());
		List<Cargo> cargoList = vagas.getAll();
		cbVagaPesquisa.getItems().add(null);
		cbVagaPesquisa.getItems().addAll(FXCollections.observableList(cargoList));
		new ComboBoxAutoCompleteUtil<>(cbVagaPesquisa);
		cbDatasFiltro.getItems().addAll("Data de Abertura", "Data de Admissão", "Data de Encerramento");
		cbDatasFiltro.getSelectionModel().selectFirst();
		cbFiltro.getItems().addAll("Nome", "Nome da Cargo", "Nome do Cliente");
		cbFiltro.getSelectionModel().selectFirst();

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

		// grafico
		grafico.setLabelsVisible(true);
		grafico.setLegendVisible(true);
		grafico.setLegendSide(Side.BOTTOM);
		grafico.setLabelLineLength(10);
		graficosStatus();// graficos

		ToggleGroup tgGrafico = new ToggleGroup();
		tgGrafico.getToggles().addAll(rbCronogramaGrafico, rbStatusGrafico);
		EventHandler filterGrafico = event -> {
			filtrar();
			event.consume();
		};
		rbCronogramaGrafico.addEventFilter(ActionEvent.ACTION, filterGrafico);
		rbStatusGrafico.addEventFilter(ActionEvent.ACTION, filterGrafico);
		rbStatusGrafico.setSelected(true);

		cbVaga1Cargo.getItems().addAll(cargoList);

		new ComboBoxAutoCompleteUtil(cbVaga1Cargo);

		cbVaga1CargaHoraria.getItems().addAll("8 horas", "6 horas");
		//cbVaga1CargaHoraria.setEditable(true);

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

		cursos = new CursosSuperioresImp(getManager());
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
		cidades = new CidadesImp(getManager());
		Cidade cidade = cidades.findByNome("São Paulo");

		cbVaga3Municipio.getItems().setAll(cidades.findByEstado(Estado.SP));
		cbVaga3Municipio.setValue(cidade);
		cbVaga3Estado.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				try {
					loadFactory(getManager());
					cidades = new CidadesImp(getManager());
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

		cbCandidatoConclusao.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				btnVerCandidatoConclusao.setDisable(cbCandidatoConclusao.getValue() == null);
				btnRemoverCandidato.setDisable(cbCandidatoConclusao.getValue() == null);
				btnAprovarCandidato.setDisable(cbCandidatoConclusao.getValue() != null);
			}
		});
		btnAprovarCandidato.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Candidato c = aprovarCandidato();
				if (c != null) {
					c.setOcupadoDetalhes("Contratado atraves do anúncio " + anuncio.getId());
					cbCandidatoConclusao.getItems().add(c);
					cbCandidatoConclusao.setValue(c);
					observer.registerCandidato(c, EnumCandidato.APROVACAO, 1);
				}
			}
		});
		btnRemoverCandidato.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (cbCandidatoConclusao.getValue() != null && !txCodigo.getText().equals("")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Aviso...");
					alert.setHeaderText("Tentativa de exclusão!");
					alert.getButtonTypes().clear();
					ButtonType btnOk = new ButtonType("Sim");
					ButtonType btnNo = new ButtonType("Não");
					alert.getButtonTypes().addAll(btnOk, btnNo);
					alert.setContentText("Você esta tentando retirar um candidato aprovado do anuncio\n"
							+ "Ao fazer isso tenha em mente que esse candidato poderá permanecer disponível para os próximos anuncios!\n"
							+ "Essa decisão deverá ser tomada com devido cuidado?"
							+ "Se tiver certeza disso clique em SIM");
					Optional<ButtonType> option = alert.showAndWait();
					if (option.get() == btnOk) {
						Candidato c = cbCandidatoConclusao.getValue();
						observer.registerCandidato(c, EnumCandidato.APROVACAO, -1);
						cbCandidatoConclusao.getItems().clear();
						cbCandidatoConclusao.setValue(null);
					}
				}
			}
		});
		btnVerCandidatoConclusao.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				abrirPerfilCandidato(cbCandidatoConclusao.getValue());
			}
		});
		cbCandidatoConclusao.setDisable(true);
		btnRemoverCandidato.setDisable(true);
		btnVerCandidatoConclusao.setDisable(true);

		ckTreinamentoConclusao.selectedProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				boolean selected = ckTreinamentoConclusao.isSelected();
				cbNomeTreinamento.setDisable(!selected);
				dtInicioConclusao.setDisable(!selected);
				dtFimConclusao.setDisable(!selected);
			}
		});
		
		ChangeListener pesquisas = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				filtrar();
			}
		};
		cbCronogramaPesquisa.valueProperty().addListener(pesquisas);
		dtInicialPesquisa.valueProperty().addListener(pesquisas);
		dtFinalPesquisa.valueProperty().addListener(pesquisas);
		cbEmpresaPesquisa.valueProperty().addListener(pesquisas);
		cbStatusPesquisa.valueProperty().addListener(pesquisas);
	}

	@FXML
	private void criarTreinamento(ActionEvent event) {
		try {
			loadFactory();
			treinamentos = new TreinamentosImp(getManager());

			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Cadastro de Treinamento");
			dialog.setHeaderText("Cadastre um novo treinamento");
			dialog.setContentText("Informe um nome:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				Treinamento t = treinamentos.findByNome(result.get());
				if (t != null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setHeaderText("Erro ao salvar o registro");
					alert.setContentText("Registro informado já existe");
					alert.showAndWait();
				} else {
					Treinamento t1 = new Treinamento();
					t1.setNome(result.get());
					treinamentos.save(t1);
					cbNomeTreinamento.getItems().add(t1);
					cbNomeTreinamento.setValue(t1);
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setContentText("Erro ao salvar o registro");
			alert.showAndWait();
		} finally {
			close();
		}
	}

	@SuppressWarnings("rawtypes")
	private void desbloquear(boolean value, ObservableList<Node> nodes) {
		nodes.forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setEditable(value);
			} else if (n instanceof MaskTextField) {
				((MaskTextField) n).setEditable(value);
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setEditable(value);
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setDisable(!value);
			} else if (n instanceof ComboBox) {
				((ComboBox) n).setDisable(!value);
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setEditable(value);
			} else if (n instanceof TableView) {
				((TableView) n).setDisable(!value);
			} else if (n instanceof JFXCheckBox) {
				((JFXCheckBox) n).setDisable(!value);
			} else if (n instanceof JFXDatePicker) {
				((JFXDatePicker) n).setDisable(!value);
			} else if (n instanceof JFXRadioButton) {
				((JFXRadioButton) n).setDisable(!value);
			} else if (n instanceof Pane) {
				desbloquear(value, ((Pane) n).getChildren());
			} else if (n instanceof AnchorPane) {
				desbloquear(value, ((AnchorPane) n).getChildren());
			} else if (n instanceof JFXTabPane) {
				ObservableList<Tab> tabs = ((JFXTabPane) n).getTabs();
				tabs.forEach(c -> {
					if (c.getContent() instanceof AnchorPane)
						desbloquear(value, ((AnchorPane) c.getContent()).getChildren());
				});
			} else if (n instanceof Accordion) {
				ObservableList<TitledPane> panes = ((Accordion) n).getPanes();
				panes.forEach(c -> desbloquear(value, c.getChildrenUnmodifiable()));
			}
		});
		btSalvarEConcluir.setDisable(!value);
		btAdicionarCurriculo.setDisable(!value);
		btnEntrevista.setDisable(!value);
		btnAdicionar.setDisable(!value);
		btnNovoTreinamentoConclusao.setDisable(!value);

		boolean selected = ckTreinamentoConclusao.isSelected();
		cbNomeTreinamento.setDisable(!selected);
		dtInicioConclusao.setDisable(!selected);
		dtFimConclusao.setDisable(!selected);

	}
	@FXML
	void fecharAnuncio(ActionEvent event) {
		
	}
	private void filtrar() {
		try {
			loadFactory();
			anuncios = new AnunciosImp(getManager());
			if (rbCronogramaGrafico.isSelected())
				graficosCronograma();
			else
				graficosStatus();
			int valor = -1;
			if (cbEmpresaPesquisa.getValue().equals("Ativa"))
				valor = 1;
			else if (cbEmpresaPesquisa.getValue().equals("Inativa"))
				valor = 0;
			List<Anuncio> anunciosList = anuncios.filtrar(cbCronogramaPesquisa.getValue(), cbStatusPesquisa.getValue(),
					valor, cbVagaPesquisa.getValue(), cbDatasFiltro.getValue(), dtInicialPesquisa.getValue(),
					dtFinalPesquisa.getValue(), txPesquisa.getText().trim(), cbFiltro.getValue());
			tbAnuncio.getItems().setAll(FXCollections.observableArrayList(anunciosList));
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setContentText(e.getMessage());
			alert.show();
		} finally {
			close();
		}
	}

	private void graficosCronograma() {
		ObservableList<PieChart.Data> datas = FXCollections.observableArrayList();
		for (Anuncio.Cronograma c : Anuncio.Cronograma.values()) {
			long q = anuncios.count(c);
			if (q > 0)
				datas.add(new PieChart.Data(c.getDescricao() + "(" + q + ")", q));
		}
		grafico.setTitle("Visao por Cronograma");
		grafico.setData(datas);
	}

	private void graficosStatus() {
		ObservableList<PieChart.Data> datas = FXCollections.observableArrayList();
		for (Anuncio.AnuncioStatus c : Anuncio.AnuncioStatus.values()) {
			long q = anuncios.count(c);
			if (q > 0)
				datas.add(new PieChart.Data(c.getDescricao() + "(" + q + ")", q));
		}
		grafico.setTitle("Visão por Status");
		grafico.setData(datas);
	}

	@FXML
	private void incluirCandidato(ActionEvent event) {
		if (anuncio != null && !txCodigo.getText().equals("")) {
			try {
				FXMLLoader loader = carregarFxmlLoader("Candidato");
				Stage stage = carregarStage(loader,
						"Anuncio: " + anuncio.getNome() + "-Cliente: " + anuncio.getCliente().getNome());
				CandidatoAnuncioFilter filter = new CandidatoAnuncioFilter(cbVaga1Cargo.getValue(),
						cbVaga3Escolaridade.getValue(), cbVaga3Formacao.getValue());
				ControllerCandidato controllerCandidato = loader.getController();
				controllerCandidato.receberAnuncio(this.anuncio, filter);
				stage.show();
				stage.setOnCloseRequest(event1 -> {
					if (event1.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
						// preenchendo tabela de curriculos
						controllerCandidato.receberCandidatos().forEach(c -> {
							if (!tbCurriculos.getItems().contains(c)) {
								tbCurriculos.getItems().add(c);
								observer.registerCandidato(c, EnumCandidato.CURRICULO, 1);
							}
						});
						tbCurriculos.getItems().forEach(cand -> {
							Optional<AnuncioEntrevista> ae = tbEntrevista.getItems().stream()
									.filter(c -> c.getCandidato().getId() == cand.getId()).findAny();
							if (!ae.isPresent() && !cbCandidatoEntrevista.getItems().contains(cand))
								cbCandidatoEntrevista.getItems().add(cand);
						});
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta!");
			alert.setHeaderText(null);
			alert.setContentText("Por favor, clique em salvar antes de prosseguir.");
			alert.showAndWait();
			return;
		}
	}

	@FXML
	private void incluirEntrevista(ActionEvent action) {
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
		}

		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta!");
			alert.setHeaderText(null);
			alert.setContentText("Nenhum candidato foi incluido, anexe um curriculo.");
			alert.showAndWait();
		}

	}

	@FXML
	private void incluirPreAprovado(ActionEvent event) {
		if (cbPreSelecionado.getValue() != null && anuncio != null && !tbEntrevista.getItems().isEmpty()) {
			Candidato candidato = cbPreSelecionado.getValue();
			tbPreSelecionado.getItems().add(candidato);
			cbPreSelecionado.getItems().remove(candidato);
			cbPreSelecionado.setValue(null);

			observer.registerCandidato(candidato, EnumCandidato.PRESELECAO, 1);

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta!");
			alert.setHeaderText(null);
			alert.setContentText("Nenhum candidato foi incluído, agende uma entrevista.");
			alert.showAndWait();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void limparTela(ObservableList<Node> nodes) {
		observer.clear();
		nodes.forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setText("");
			} else if (n instanceof MaskTextField) {
				((MaskTextField) n).setText("");
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setText("");
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setValue(null);
			} else if (n instanceof ComboBox) {
				((ComboBox) n).setValue(null);
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setText("");
			} else if (n instanceof TableView) {
				((TableView) n).getItems().clear();
			} else if (n instanceof JFXCheckBox) {
				((JFXCheckBox) n).setSelected(false);
			} else if (n instanceof JFXRadioButton) {
				((JFXRadioButton) n).setSelected(false);
			} else if (n instanceof JFXDatePicker) {
				((JFXDatePicker) n).setValue(null);
			} else if (n instanceof Pane) {
				limparTela(((Pane) n).getChildren());
			} else if (n instanceof AnchorPane) {
				limparTela(((AnchorPane) n).getChildren());
			} else if (n instanceof Accordion) {
				ObservableList<TitledPane> titledPanes = ((Accordion) n).getPanes();
				titledPanes.forEach(c -> {
					limparTela(c.getChildrenUnmodifiable());
				});
			} else if (n instanceof JFXTabPane) {
				ObservableList<Tab> tabs = ((JFXTabPane) n).getTabs();
				tabs.forEach(c -> {
					if (c.getContent() instanceof AnchorPane)
						limparTela(((AnchorPane) c.getContent()).getChildren());
				});
			} else if (n instanceof Accordion) {
				ObservableList<TitledPane> panes = ((Accordion) n).getPanes();
				panes.forEach(c -> limparTela(c.getChildrenUnmodifiable()));
			}
		});
		cbStatusContrato.setValue(null);
		cbCronograma.setValue(Anuncio.Cronograma.FORMULARIO);
		dtAbertura.setValue(LocalDate.now());
		cbStatus.setValue(Anuncio.AnuncioStatus.EM_ANDAMENTO);
		rbVaga1ProcessoSigilosoNao.setSelected(true);
		rbVaga1Clt.setSelected(true);
		dtVaga1DataInicio.setDisable(true);
		dtVaga1DataFim.setDisable(true);
		rbVaga2MotivoOutro.setSelected(true);
		cbVaga3Estado.setValue(Estado.SP);
		ckVaga4ACombinar.setSelected(true);
		txVaga4Salario.setText("0,00");
		rbVaga4ComissaoNao.setSelected(true);
		rbVaga41VRNao.setSelected(true);
		txVaga41VRValor.setText("0,00");
		rbVaga41VTNao.setSelected(true);
		rbVaga41AMedicaNao.setSelected(true);
	}
	@FXML
	private void formRequisicaoAvancar(ActionEvent event) {
		Tab t = proximaTabVagas(true);
		if (t != null)
			tpVaga.getSelectionModel().select(t);
	}
	@FXML
	private void formRequisicaoRetroceder(ActionEvent event) {
		Tab t = proximaTabVagas(false);
		if (t != null)
			tpVaga.getSelectionModel().select(t);
	}
	@FXML
	private void novo(ActionEvent event) {
		limparTela(pnCadastro.getChildren());
		desbloquear(true, pnCadastro.getChildren());
		tab.getSelectionModel().select(tabCadastro);
		this.anuncio = null;
	}

	@FXML
	private void pesquisar(KeyEvent event) {
		filtrar();
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
				if(form.getFormulario()!=null)
					txDocumento.setText(form.getFormulario());
				// 1
				cbVaga1Cargo.setValue(form.getCargo());
				cbCargoNivel.setValue(form.getNivel());
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
				cbVaga1CargaHoraria.setValue(form.getCargaHoraria());
				txVaga1HorarioTrabalho.setText(form.getHorarioTrabalho());
				
				if(form.getSigiloso()==1)
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

					cbVaga3Tipo.setValue(form.getTipoExperiencia());
					txVaga31Competencia.setText(form.getCompetencia());
					txVaga32DescricaoAtividades.setText(form.getAtividade());
				}
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
			cbCandidatoConclusao.getItems().clear();
			cbCandidatoConclusao.setValue(anuncio.getCandidatoAprovado());
			if (anuncio.getHaveraTreinamento() == 1) {
				ckTreinamentoConclusao.setSelected(true);
				cbNomeTreinamento.setDisable(false);
				cbNomeTreinamento.setValue(anuncio.getTreinamento());
				dtInicioConclusao.setDisable(false);
				dtFimConclusao.setDisable(false);
				if (anuncio.getDataInicioTreinamento() != null)
					dtInicioConclusao.setValue(anuncio.getDataInicioTreinamento().getTime().toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate());
				if (anuncio.getDataFimTreinamento() != null)
					dtFimConclusao.setValue(anuncio.getDataFimTreinamento().getTime().toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate());
			} else {
				cbNomeTreinamento.setDisable(false);
				cbNomeTreinamento.setValue(null);
				dtInicioConclusao.setDisable(true);
				dtInicioConclusao.setValue(null);
				dtFimConclusao.setDisable(true);
				dtFimConclusao.setValue(null);
			}
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

			tbCurriculos.getItems().forEach(cand -> {
				Optional<AnuncioEntrevista> ae = tbEntrevista.getItems().stream()
						.filter(c -> c.getCandidato().getId() == cand.getId()).findAny();
				if (!ae.isPresent() && !cbCandidatoEntrevista.getItems().contains(cand))
					cbCandidatoEntrevista.getItems().add(cand);
			});
			tbEntrevista.getItems().forEach(ae -> {
				Optional<Candidato> can = tbPreSelecionado.getItems().stream().filter(c -> c == ae.getCandidato())
						.findAny();
				if (!can.isPresent() && !cbPreSelecionado.getItems().contains(ae.getCandidato()))
					cbPreSelecionado.getItems().add(ae.getCandidato());
			});
			this.anuncio = anuncio;
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setContentText("Erro ao preencher formulario\n" + e);
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	private Tab proximaTabVagas(boolean avancar) {
		int t = tpVaga.getSelectionModel().getSelectedIndex();
		if (avancar) {
			return tpVaga.getTabs().get(++t);
		} else {
			return tpVaga.getTabs().get(--t);
		}
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

					if (cbCandidatoConclusao.getValue() != null) {
						Candidato cand = cbCandidatoConclusao.getValue();
						cand.setOcupado(0);
						cand.setOcupadoDetalhes("Liberado pois anuncio " + anuncio.getId() + " foi removido");
						observer.registerCandidato(cand, EnumCandidato.APROVACAO, -1);
					}
					anuncios.remove(anuncio);
					candidatos = new CandidatosImp(getManager());
					observer.notifyUpdate(candidatos);
					tab.getSelectionModel().select(tabCadastro);
					alert.setAlertType(Alert.AlertType.INFORMATION);
					alert.setContentText("Registro excluido com sucesso!");
					alert.showAndWait();
					limparTela(pnCadastro.getChildren());
					desbloquear(false, pnCadastro.getChildren());
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
			filtrar();
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
	private void salvar(ActionEvent event) {
		FormularioRequisicao fr =null;
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
		}
		else {
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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setContentText("Informe um perfil da Cargo antes de prosseguir!");
			alert.show();
			return;
		}
		fr.setCargo(cbVaga1Cargo.getValue());
		fr.setNivel(cbCargoNivel.getValue());
		fr.setCargoAds(txCargoAdc.getText());
		fr.setCargaHoraria(cbVaga1CargaHoraria.getValue());
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
				Alert alert = new Alert(AlertType.ERROR);
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
		System.out.println("txdocumentos is null "+(txDocumento==null) +" value="+txDocumento.getText()+"|");
				
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
		anuncio.setCandidatoAprovado(cbCandidatoConclusao.getValue());
		anuncio.setHaveraTreinamento(ckTreinamentoConclusao.isSelected() ? 1 : 0);
		anuncio.setDataInicioTreinamento(dtInicioConclusao.getValue() == null ? null
				: GregorianCalendar.from(dtInicioConclusao.getValue().atStartOfDay(ZoneId.systemDefault())));
		anuncio.setDataFimTreinamento(dtFimConclusao.getValue() == null ? null
				: GregorianCalendar.from(dtFimConclusao.getValue().atStartOfDay(ZoneId.systemDefault())));

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
			desbloquear(false, pnCadastro.getChildren());
			candidatos = new CandidatosImp(getManager());
			observer.notifyUpdate(candidatos);
			preencherFormulario(anuncio);
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setContentText("Falha ao salvar o registro!" + e);
			alert.showAndWait();
			e.printStackTrace();
		} finally {
			close();
		}
		filtrar();
	}

	@SuppressWarnings("unchecked")
	private void tabela() {
		TableColumn<Anuncio, Number> colunaId = new TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaId.setPrefWidth(40);

		TableColumn<Anuncio, String> colunaNome = new TableColumn<>("Nome do Anuncio");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaNome.setCellFactory((TableColumn<Anuncio, String> param) -> new TableCell<Anuncio, String>() {
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
		TableColumn<Anuncio, Cliente> colunaCliente = new TableColumn<>("Cliente");
		colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaCliente.setCellFactory((TableColumn<Anuncio, Cliente> param) -> new TableCell<Anuncio, Cliente>() {
			@Override
			protected void updateItem(Cliente item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(item.getNome());
				}
			}
		});
		TableColumn<Anuncio, Cliente> colunaClienteContabil = new TableColumn<>("Cliente Contabil");
		colunaClienteContabil.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaClienteContabil
				.setCellFactory((TableColumn<Anuncio, Cliente> param) -> new TableCell<Anuncio, Cliente>() {
					@Override
					protected void updateItem(Cliente item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null) {
							setText(null);
							setStyle("");
						} else {
							setText(item.getClienteContabil() == 1 ? "Sim" : "Não");
						}
					}
				});
		TableColumn<Anuncio, FormularioRequisicao> colunaVaga = new TableColumn<>("Cargo");
		colunaVaga.setCellValueFactory(new PropertyValueFactory<>("formularioRequisicao"));
		colunaVaga.setCellFactory(
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
							setText(item.getDescricao());
						}
					}
				});
		TableColumn<Anuncio, String> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
		colunaEditar.setCellFactory((TableColumn<Anuncio, String> param) -> {
			final TableCell<Anuncio, String> cell = new TableCell<Anuncio, String>() {
				final JFXButton button = new JFXButton("Editar");

				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						button.getStyleClass().add("btJFXDefault");
						button.setOnAction(event -> {
							desbloquear(true, pnCadastro.getChildren());
							limparTela(pnCadastro.getChildren());
							try {
								loadFactory();
								anuncios = new AnunciosImp(getManager());
								Anuncio a = anuncios.findById(tbAnuncio.getItems().get(getIndex()).getId());
								preencherFormulario(a);
								tab.getSelectionModel().select(tabCadastro);

							} catch (Exception e) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Erro");
								alert.setContentText("Falha ao recuperar registro\n" + e);
								alert.showAndWait();
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
		tbAnuncio.getColumns().addAll(colunaId, colunaNome, colunaVaga, colunaCliente, colunaClienteContabil,
				colunaCronograma, colunaStatus, colunaEditar);
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

							for (AnuncioEntrevista aE : tbEntrevista.getItems()) {
								if (aE.getCandidato() == candidato) {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setTitle("Operação impossivel");
									alert.setHeaderText("Candidato não pode ser removido!");
									alert.setContentText(
											"O candidato encontra-se no processo de Entrevista desse anuncio, portanto não pode ser removido");
									alert.showAndWait();
									aceitar = false;
									break;
								} else
									aceitar = true;
							}
							if (aceitar) {
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
				colunaAnuncios, colunaSelecoes, colunaVer, colunaRemover);
	};

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
							if(item==null) {
								setText("Pendente");
								setStyle("-fx-text-fill:red");
							}
							else {
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
							if(button.getText().equals("Nova Entrevista")){
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Confirmação");
								alert.setHeaderText("Por favor, escolha uma operação para avançar");
								alert.setContentText("Escolha uma opção.");
								// ButtonType buttonAgendar = new ButtonType("Agendar Entrevista");
								ButtonType buttonAgora = new ButtonType("Entrevistar Agora");
								ButtonType buttonCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
								alert.getButtonTypes().setAll(buttonAgora, buttonCancel);
								
								Optional<ButtonType> result = alert.showAndWait();
								// if (result.get() == buttonAgendar){
								// // ... user chose "One"
								// }
								if (result.get() == buttonAgora) {
									abrirEntrevista(tbEntrevista.getItems().get(getIndex()));
								}
							}
							else {
								abrirEntrevista(tbEntrevista.getItems().get(getIndex()));
							}
						});
						setGraphic(button);
						setText(null);
					}
				}
			};
			return cell;
		});
		colunaEntrevista.setPrefWidth(150);
		
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
						button.setOnAction(event -> {
							abrirPerfilCandidato(tbEntrevista.getItems().get(getIndex()).getCandidato());
						});
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
								anuncio.setCandidatoAprovado(cbCandidatoConclusao.getValue());
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

								final AnuncioEntrevista aefinal = tbEntrevista.getItems().get(getIndex());

								Optional<AnuncioEntrevista> newAe = tbEntrevista.getItems().stream()
										.filter(can -> can.getCandidato().getId() == c.getId()).findFirst();
								if (newAe.isPresent()) {
									FXMLLoader loader = carregarFxmlLoader("EntrevistaAvaliacao");
									Stage stage = carregarStage(loader,
											"Avaliações do Candidato: " + aefinal.getCandidato().getNome());
									ControllerEntrevistaAvaliacao controller = loader.getController();
									controller.iniciar(aefinal.getAvaliacao(), aefinal);
									stage.show();
									stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
										@Override
										public void handle(WindowEvent event) {
											if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
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

										}
									});
								}

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

		tbEntrevista.getColumns().addAll(colunaId, colunaNome, colunaIdade, colunaCriadoEm, colunaPossuiIndicacao,
				colunaAnuncios, colunaSelecoes, colunaStatusEntrevista, colunaEntrevista, colunaAvaliacao, colunaVer,
				colunaRemover);
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
		colunaPossuiIndicacao.setCellValueFactory(new PropertyValueFactory<>("possuiIndicacao"));
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
							if (cbCandidatoConclusao.getSelectionModel().getSelectedItem() == candidato) {
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
		tbPreSelecionado.getColumns().addAll(colunaId, colunaNome, colunaIdade, colunaCriadoEm, colunaPossuiIndicacao,
				colunaAnuncios, colunaSelecoes, colunaVer, colunaRemover);
	}

	@FXML
	void visualizar(ActionEvent event) {
		if (!txDocumento.getText().equals("")) {
			try {
				File file = storage.downloadFile(txDocumento.getText());
				if (file != null)
					Desktop.getDesktop().open(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void visualizarCronogramaDocumento(ActionEvent event) {
		if (anuncio != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			Map<String, String> crono = new HashMap<>();
			crono.put("{anuncio.cliente}", anuncio.getCliente().getNome());
			crono.put("{anuncio.data_inicio}",
					anuncio.getDataAbertura() != null ? sdf.format(anuncio.getDataAbertura().getTime()) : "");
			String vaga = "";

			FormularioRequisicao fr = anuncio.getFormularioRequisicao();
			if (fr != null)
				vaga = fr.getCargo() != null ? fr.getCargo().getNome() : "";
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

	}

}