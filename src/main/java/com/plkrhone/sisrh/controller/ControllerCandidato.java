package com.plkrhone.sisrh.controller;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import com.plkrhone.sisrh.config.init.UsuarioLogado;
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
import com.plkrhone.sisrh.config.init.PaisesConfig;
import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Cidade;
import com.plkrhone.sisrh.model.CursoSuperior;
import com.plkrhone.sisrh.model.Endereco;
import com.plkrhone.sisrh.model.Estado;
import com.plkrhone.sisrh.model.PfPj;
import com.plkrhone.sisrh.model.Vaga;
import com.plkrhone.sisrh.repository.helper.CandidatosImp;
import com.plkrhone.sisrh.repository.helper.CidadesImp;
import com.plkrhone.sisrh.repository.helper.CursosSuperioresImp;
import com.plkrhone.sisrh.repository.helper.VagasImp;
import com.plkrhone.sisrh.repository.helper.filter.CandidatoAnuncioFilter;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.EnderecoUtil;
import com.plkrhone.sisrh.util.storage.PathStorageEnum;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerCandidato extends PersistenciaController implements Initializable {
	@FXML
	private JFXTabPane tabPane;

	@FXML
	private Tab tabPesquisa;

	@FXML
	private TableView<Candidato> tbCandidatos;

	@FXML
	private ComboBox<Vaga> cbObjetivoPesquisa;

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
	private JFXComboBox<CursoSuperior> cbCursoSuperior;
	
	@FXML
	private JFXComboBox<CursoSuperior> cbCursoSuperiorPesquisa;
	
	@FXML
	private ComboBox<Vaga> cbExperienciaPesquisa;

	@FXML
	private JFXCheckBox ckIndisponivelPesquisa;
	
	@FXML
	private MaskTextField txIdadeInicioPesquisa;

	@FXML
	private MaskTextField txIdadeFimPesquisa;

	@FXML
	private Tab tabCadastro;

	@FXML
	private AnchorPane pnCadastro;

	@FXML
	private JFXTextField txCodigo;

	@FXML
	private JFXTextField txDataCriacao;

	@FXML
	private JFXTextField txNome;

	@FXML
	private JFXRadioButton rbSexoF;

	@FXML
	private JFXRadioButton rbSexoM;

	@FXML
	private MaskTextField txIdade;

	@FXML
	private JFXDatePicker dtNascimento;

	@FXML
	private JFXComboBox<Candidato.EstadoCivil> cbEstadoCivil;

	@FXML
	private JFXCheckBox ckFumante;

	@FXML
	private JFXCheckBox ckFilhos;

	@FXML
	private MaskTextField txQtdFilhos;

	@FXML
	private JFXComboBox<Candidato.Escolaridade> cbEscolaridade;

	@FXML
	private JFXComboBox<String> cbNacionalidade;

	@FXML
	private MaskedTextField txTelefone;

	@FXML
	private MaskedTextField txCelular;

	@FXML
	private JFXTextField txEmail;

	@FXML
	private JFXTextField txLogradouro;
	
	@FXML
	private MaskedTextField txCep;
	@FXML
	private JFXTextField txNumero;
	@FXML
	private JFXTextField txBairro;
	@FXML
	private JFXComboBox<Estado> cbEstado;
	@FXML
	private JFXComboBox<Cidade> cbCidade;
	@FXML
	private ComboBox<Vaga> cbObjetivo1;

	@FXML
	private ComboBox<Vaga> cbObjetivo2;

	@FXML
	private ComboBox<Vaga> cbObjetivo3;

	@FXML
	private JFXCheckBox ckPossuiIndicacao;

	@FXML
	private AnchorPane pnCadastroIndicacao;

	@FXML
	private JFXTextField txEmpresaIndicacao;

	@FXML
	private JFXTextArea txDetalhesIndicacao;

	@FXML
	private JFXTextField txCarreiraEmpresa1;

	@FXML
	private JFXTextArea txCarreiraDescricao1;
	@FXML
	private JFXTextArea txCarreiraDescricao2;
	@FXML
	private JFXTextArea txCarreiraDescricao3;
	@FXML
	private ComboBox<Vaga> cbCarreiraObjetivo1;

	@FXML
	private JFXTextField txCarreiraEmpresa2;

	@FXML
	private ComboBox<Vaga> cbCarreiraObjetivo2;

	@FXML
	private JFXTextField txCarreiraEmpresa3;

	@FXML
	private ComboBox<Vaga> cbCarreiraObjetivo3;

	@FXML
	private JFXTextField txFormulario;

	@FXML
	private JFXCheckBox ckNaoDisponivel;

	@FXML
	private JFXTextArea txNaoDisponivelDetalhes;

	@FXML
	private JFXButton btAnexar;

	@FXML
	private JFXButton btnVisualizar;

	@FXML
	private JFXButton btnRemover;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnAlterar;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private JFXButton btAddAnuncio;

	@FXML
	private JFXButton btnExcluir;
	@FXML
	private JFXTextField txComplemento;
	
	private Candidato candidato;
	private Anuncio anuncio = null;
	private CandidatosImp candidatos;
	private VagasImp vagas;
	private CursosSuperioresImp cursos;
	private CidadesImp cidades;
	Storage storage = StorageProducer.newConfig();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		long tempoInicial = System.currentTimeMillis();
		limparTela(pnCadastro.getChildren());
		tabela();
		// tabelaCadastro();
		txIdade.setPrefColumnCount(3);
		try {
			loadFactory();
			candidatos = new CandidatosImp(getManager());
			combos();
			List<Candidato> lista = candidatos.getAll();
			tbCandidatos.setItems(FXCollections.observableList(lista));
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
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
	void adicionarNoAnuncio(ActionEvent event) {
		anuncio.getCurriculoSet().add(this.candidato);
		btAddAnuncio.setDisable(true);
		tbCandidatos.refresh();
	}

	@FXML
	void alterar(ActionEvent event) {
		if (!txCodigo.getText().equals(""))
			desbloquear(true, pnCadastro.getChildren());
	}

	@FXML
	void alterarIdade(KeyEvent event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!txIdade.getText().trim().equals("")) {
					try {
						int idade = Integer.parseInt(txIdade.getText());
						LocalDate date1 = LocalDate.now();
						LocalDate dataNas = LocalDate.of(date1.getYear() - idade, 01, 01);
						dtNascimento.setValue(dataNas);
						txIdade.positionCaret(txIdade.getText().length());
					} catch (Exception e) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Valor incorreto");
						alert.setHeaderText("Valor informado no campo esta incorreto 'SOMENTE NUMEROS'");
						alert.setContentText("Tente novamente digitando apenas numeros");
						alert.showAndWait();
					}
				} else
					dtNascimento.setValue(null);
			}
		});
	}

	@FXML
	void anexarFormulario(ActionEvent event) {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
		Set<FileChooser.ExtensionFilter> filters = new HashSet<>();
//		alert = new Alert(Alert.AlertType.CONFIRMATION);
//		alert.setTitle("Informação importante!");
//		alert.setHeaderText("Deseja usar modelo do Word para imprimir seus formulários pré-prenchidos?");
//		alert.setContentText(
//				"Para usar essa técnica, salve seus formularios no padrão Microsoft Word 2007 (.doc),\n"
//						+ "Caso contrario clique em cancelar");
//		Optional<ButtonType> buttonType = alert.showAndWait();
//		if (buttonType.get() == ButtonType.CANCEL) {
//			filters.add(new FileChooser.ExtensionFilter("MS Word 2007", "*.doc"));
//			filters.add(new FileChooser.ExtensionFilter("*.pdf", "*.doc|*.pdf|*.docx"));
//			filters.add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
//		}
		
		filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc","*.pdf","*.docx"));
		File file = storage.carregarArquivo(new Stage(), filters);
		if (file != null) {
			String novoNome = storage.gerarNome(file, PathStorageEnum.CURRICULO.getDescricao());
			try{
				storage.uploadFile(file, novoNome);
				txFormulario.setText(novoNome);
			} catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Armazenamento de arquivo");
				alert.setHeaderText("Não foi possivel enviar o arquivo para o servidor");
				alert.setContentText("Um erro desconhecido não permitiu o envio do arquivo para uma pasta do servidor\n"+e);
				alert.showAndWait();
				e.printStackTrace();
			}
		}
			}
		};
		Platform.runLater(run);
	}
	@FXML
	void buscarCep(ActionEvent event){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("CEP");
		try{
            loadFactory();
            EnderecoUtil util = EnderecoUtil.getInstance();
			if(txCep.getPlainText().trim().length()==8) {
				Endereco endereco = util.pegarCEP(txCep.getPlainText());
				if(endereco!=null){
					txLogradouro.setText(endereco.getLogradouro());
                    txNumero.setText("");
                    txComplemento.setText(endereco.getComplemento());
                    txBairro.setText(endereco.getBairro());
                    cidades = new CidadesImp(getManager());
                    cbCidade.getItems().clear();
                    cbCidade.getItems().addAll(cidades.findByEstado(endereco.getUf()));
                    Cidade cidade = cidades.findByNome(endereco.getLocalidade());
                    cbCidade.setValue(cidade);
                    cbEstado.setValue(endereco.getUf());
				}
				else {
				    alert.setContentText("Verifique se o cep informado é valido ou se existe uma conexão com a internet");
				    alert.show();
				}
            }
			else{
			    alert.setContentText("Verifique o cep informado");
			    alert.show();
			}
		}catch(Exception e){
		    alert.setTitle("Falha na conexão com o banco de dados");
		    alert.setContentText("Houve uma falha na conexão com o banco de dados");
		    alert.show();
		    
		}finally {
		    close();
		}
	}
	void combos() {
		vagas = new VagasImp(getManager());
		cursos = new CursosSuperioresImp(getManager());
		List<Vaga> vagasList = new ArrayList<>();
		Vaga vaga = new Vaga();
		vaga.setId(new Long(-1));
		vaga.setNome("Qualquer");
		vagasList.add(vaga);
		vagasList.addAll(vagas.getAll());
		cbObjetivoPesquisa.getItems().addAll(vagasList);
		cbExperienciaPesquisa.getItems().addAll(cbObjetivoPesquisa.getItems());
		cbObjetivo1.getItems().addAll(cbObjetivoPesquisa.getItems());
		cbObjetivo2.getItems().addAll(cbObjetivoPesquisa.getItems());
		cbObjetivo3.getItems().addAll(cbObjetivoPesquisa.getItems());

		new ComboBoxAutoCompleteUtil<>(cbObjetivoPesquisa);
		new ComboBoxAutoCompleteUtil<>(cbExperienciaPesquisa);
		new ComboBoxAutoCompleteUtil<>(cbObjetivo1);
		new ComboBoxAutoCompleteUtil<>(cbObjetivo2);
		new ComboBoxAutoCompleteUtil<>(cbObjetivo3);

		cbCarreiraObjetivo1.getItems().addAll(cbObjetivoPesquisa.getItems());
		cbCarreiraObjetivo2.getItems().addAll(cbObjetivoPesquisa.getItems());
		cbCarreiraObjetivo3.getItems().addAll(cbObjetivoPesquisa.getItems());
		new ComboBoxAutoCompleteUtil<>(cbCarreiraObjetivo1);
		new ComboBoxAutoCompleteUtil<>(cbCarreiraObjetivo2);
		new ComboBoxAutoCompleteUtil<>(cbCarreiraObjetivo3);

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
		ckIndisponivelPesquisa.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				filtrar();
			}
		});		
		ChangeListener<Object> pesquisaCombo = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				filtrar();
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
		cbCursoSuperior.getItems().addAll(cbCursoSuperiorPesquisa.getItems());
		new ComboBoxAutoCompleteUtil<>(cbCursoSuperior);
		
		pnCadastroIndicacao.setVisible(false);
		txQtdFilhos.setDisable(true);

		ToggleGroup group = new ToggleGroup();
		group.getToggles().addAll(rbSexoF, rbSexoM);
		
		ckNaoDisponivel.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(oldValue && !txCodigo.getText().equals("")) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Status do Candidato");
					alert.setContentText("Desmarcando essa opção fará com que o candidato\n"
							+ "fique disponivel para outros processos seletivos\n"
							+ "Tem certeza disso?");
					Optional<ButtonType> result = alert.showAndWait();
					if(result.get()==ButtonType.CANCEL) ckNaoDisponivel.setSelected(true);
				}
			}
		});
		ckFilhos.setOnAction(event -> {
			if (ckFilhos.isSelected()) {
				txQtdFilhos.setDisable(false);
			} else {
				txQtdFilhos.setDisable(true);
				txQtdFilhos.setText("");
			}
		});
		txQtdFilhos.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				try {
					Integer.parseInt(txQtdFilhos.getText());
				} catch (Exception e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Valor incorreto");
					alert.setHeaderText("Digite um numero válido!");
					alert.setContentText("O valor informado esta incorreto, por favor digite um numero valido!");
					alert.showAndWait();
					txQtdFilhos.setText("");
				}
			}
		});
		dtNascimento.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				if (newValue != null) {
					LocalDate date1 = LocalDate.now();
					LocalDate date2 = newValue;
					Period period = Period.between(date2, date1);
					txIdade.setText("" + period.getYears());
				}
			}
		});
		ckPossuiIndicacao.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (ckPossuiIndicacao.isSelected()) {
					pnCadastroIndicacao.setVisible(true);
				} else
					pnCadastroIndicacao.setVisible(false);
			}
		});
		cbEstadoCivil.getItems().addAll(Candidato.EstadoCivil.values());
		cbEscolaridade.getItems().addAll(Candidato.Escolaridade.values());

		
		cbNacionalidade.getItems().addAll(PaisesConfig.getInstance().getAll());
		cbNacionalidade.setValue("Brasil");
		new ComboBoxAutoCompleteUtil<>(cbNacionalidade);
		
		cbEstado.getItems().setAll(Arrays.asList(Estado.values()));
		cbEstado.setValue(Estado.SP);
		cidades = new CidadesImp(getManager());
		Cidade cidade = cidades.findByNome("São Paulo");

		cbCidade.getItems().setAll(cidades.findByEstado(Estado.SP));
		cbCidade.setValue(cidade);
		cbEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				try {
					loadFactory();
					cidades = new CidadesImp(getManager());
					List<Cidade> listCidades = cidades.findByEstado(newValue);
					cbCidade.getItems().setAll(listCidades);
				} catch (Exception e) {
				} finally {
					close();
				}
			}
		});
		new ComboBoxAutoCompleteUtil<>(cbCidade);

		
	}

	@SuppressWarnings("rawtypes")
	private void desbloquear(boolean value, ObservableList<Node> nodes) {
		nodes.forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setEditable(value);
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setDisable(!value);
			} else if (n instanceof ComboBox) {
				((ComboBox) n).setDisable(!value);
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setEditable(value);
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setEditable(value);
			} else if (n instanceof MaskTextField) {
				((MaskTextField) n).setEditable(value);
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
		if (candidato != null && anuncio != null && !txCodigo.getText().equals("")
				&& !anuncio.getCurriculoSet().contains(this.candidato))
			btAddAnuncio.setDisable(false);
		else
			btAddAnuncio.setDisable(true);
	}

	@FXML
	void excluir(ActionEvent event) {
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
					candidatos = new CandidatosImp(getManager());
					Candidato can = candidatos.findById(Long.parseLong(txCodigo.getText()));
					candidatos.remove(can);
					tbCandidatos.getItems().remove(can);
					tabPane.getSelectionModel().select(tabCadastro);
					alert.setAlertType(Alert.AlertType.INFORMATION);
					alert.setContentText("Registro excluido com sucesso!");
					alert.showAndWait();
					limparTela(pnCadastro.getChildren());
					desbloquear(false, pnCadastro.getChildren());
					candidato = null;

				} catch (Exception e) {
					alert = new Alert(AlertType.ERROR);
					alert.setContentText("Falha ao excluir o registro\n" + e);
					alert.showAndWait();
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

	private void filtrar() {
		Alert alert = new Alert(AlertType.ERROR);
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
			return;
		}
		if (dtPerfilInicioPesquisa.getValue() != null) {
			if (dtPerfilFimPesquisa.getValue() == null) {
				dtPerfilFimPesquisa.setValue(LocalDate.now());
			} else if (dtPerfilFimPesquisa.getValue().isBefore(dtPerfilInicioPesquisa.getValue())) {
				alert.setContentText("Data final do curriculo não pode ser maior que a data de inicio");
				alert.showAndWait();
				return;
			}
		}
		if (cbFormacaoMinPesquisa.getValue() != null && cbFormacaoMaxPesquisa.getValue() != null) {
			boolean maior = cbFormacaoMinPesquisa.getValue().getValor() > cbFormacaoMaxPesquisa.getValue().getValor();
			if (maior) {
				alert.setContentText(
						"Verifique a escolaridade informada, " + cbFormacaoMinPesquisa.getValue().getDescricao()
								+ " não pode ser superior a " + cbFormacaoMaxPesquisa.getValue().getDescricao());
				alert.showAndWait();
				return;
			}
		}
		try {
			loadFactory();
			candidatos = new CandidatosImp(getManager());
			List<Candidato> lista = candidatos.filtrar(cbObjetivoPesquisa.getSelectionModel().getSelectedItem(), cbExperienciaPesquisa.getValue(),
					cbSexoPesquisa.getSelectionModel().getSelectedItem(), idadeMin, idadeMax, cbIndicacaoPesquisa.getValue(),
					cbFormacaoMinPesquisa.getValue(), cbFormacaoMaxPesquisa.getValue(),
					dtPerfilInicioPesquisa.getValue(), dtPerfilFimPesquisa.getValue(), cbBuscarPorPesquisa.getValue(),
					txValorPesquisa.getText().trim(),ckIndisponivelPesquisa.isSelected());
			tbCandidatos.getItems().clear();
			tbCandidatos.getItems().addAll(FXCollections.observableList(lista));
			tbCandidatos.refresh();
		} catch (Exception e) {
			alert.setTitle("Erro");
			alert.setHeaderText(null);
			alert.setContentText("Erro ao filtrar candidatos\n" + e);
			alert.show();
		} finally {
			close();
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void limparTela(ObservableList<Node> nodes) {
		nodes.forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setText("");
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setValue(null);
			} else if (n instanceof ComboBox) {
				((ComboBox) n).setValue(null);
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setText("");
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setText("");
			} else if (n instanceof MaskTextField) {
				((MaskTextField) n).setText("");
			}  else if (n instanceof TableView) {
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
		rbSexoF.setSelected(true);
		pnCadastroIndicacao.setVisible(false);
	}

	private void incluirAnuncioCandidato(Candidato candidato) {
		anuncio.getCurriculoSet().add(candidato);
	}

	@FXML
	public void pesquisar(KeyEvent event) {
		filtrar();
	}

	@FXML
	void novo(ActionEvent event) {
		limparTela(pnCadastro.getChildren());
		desbloquear(true, pnCadastro.getChildren());
		cbNacionalidade.setValue("Brasil");
		tabPane.getSelectionModel().select(tabCadastro);
		this.candidato = null;
	}

	public void receberAnuncio(Anuncio anuncio, CandidatoAnuncioFilter filter) {
		this.anuncio = anuncio;
		cbCursoSuperiorPesquisa.setValue(filter.getCursoSuperior());
		cbFormacaoMinPesquisa.setValue(filter.getEscolaridade());
		cbObjetivoPesquisa.setValue(filter.getVaga());
		cbExperienciaPesquisa.setValue(filter.getVaga());
		//cbfilter.getVaga();
		filtrar();
		tbCandidatos.refresh();
		desbloquear(true, pnCadastro.getChildren());
	}

	public Set<Candidato> receberCandidatos() {
		return anuncio.getCurriculoSet();
	}

	public void preencherFormulario(Candidato candidato) {
		tabPane.getSelectionModel().select(tabCadastro);
		txCodigo.setText(candidato.getId() + "");
		txDataCriacao.setText(candidato.getCriadoEm()!=null?new SimpleDateFormat("dd/MM/yyyy").format(candidato.getCriadoEm().getTime()):"");
		txNome.setText(candidato.getNome());
		if (candidato.getSexo().equals("M"))
			rbSexoM.setSelected(true);
		else
			rbSexoF.setSelected(true);

		try {
			if (candidato.getDataNascimento() != null) {
				dtNascimento.setValue(candidato.getDataNascimento().getTime().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cbEstadoCivil.setValue(candidato.getEstadoCivil());
		if (candidato.getFumante() == 1)
			ckFumante.setSelected(true);
		if (candidato.getFilhos() == 1) {
			ckFilhos.setSelected(true);
			txQtdFilhos.setDisable(false);
		} else
			txQtdFilhos.setDisable(true);
		txQtdFilhos.setText(String.valueOf(candidato.getQtdeFilhos()));
		cbEscolaridade.setValue(candidato.getEscolaridade());
		cbCursoSuperior.setValue(candidato.getCursoSuperior());
		cbNacionalidade.setValue(candidato.getNacionalidade());
		PfPj pfpj = candidato.getPessoaFisica();
		if (pfpj != null) {
			txTelefone.setText(pfpj.getTelefone());
			txCelular.setText(pfpj.getCelular());
			txEmail.setText(pfpj.getEmail());
			txCep.setPlainText(pfpj.getCep());
			txLogradouro.setText(pfpj.getLogradouro());
			txNumero.setText(pfpj.getNumero());
			txComplemento.setText(pfpj.getComplemento());;
			txBairro.setText(pfpj.getBairro());
			cbEstado.setValue(pfpj.getEstado());
			cbCidade.setValue(pfpj.getCidade());
		}
		cbObjetivo1.setValue(candidato.getObjetivo1());
		cbObjetivo2.setValue(candidato.getObjetivo2());
		cbObjetivo3.setValue(candidato.getObjetivo3());

		txCarreiraEmpresa1.setText(candidato.getEmpresa1());
		txCarreiraEmpresa2.setText(candidato.getEmpresa2());
		txCarreiraEmpresa3.setText(candidato.getEmpresa3());

		cbCarreiraObjetivo1.setValue(candidato.getCargo1());
		cbCarreiraObjetivo2.setValue(candidato.getCargo2());
		cbCarreiraObjetivo3.setValue(candidato.getCargo3());

		txCarreiraDescricao1.setText(candidato.getDescricaoCargo1());
		txCarreiraDescricao2.setText(candidato.getDescricaoCargo2());
		txCarreiraDescricao3.setText(candidato.getDescricaoCargo3());

		txFormulario.setText(candidato.getFormulario());
		if (candidato.getIndicacao() == 1) {
			pnCadastroIndicacao.setVisible(true);
			ckPossuiIndicacao.setSelected(true);
			txEmpresaIndicacao.setText(candidato.getEmpresaIndicacao());
			txDetalhesIndicacao.setText(candidato.getDetalhesIndicacao());
		} else {
			pnCadastroIndicacao.setVisible(false);
			txEmpresaIndicacao.clear();
			txDetalhesIndicacao.clear();
		}
		ckNaoDisponivel.setSelected(candidato.getOcupado() == 1);
		txNaoDisponivelDetalhes.setText(candidato.getOcupadoDetalhes());
		this.candidato = candidato;
	}

	@FXML
	void removerFormulario(ActionEvent event) {
		try{
			storage.delete(txFormulario.getText());
			txFormulario.setText("");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(candidato!=null) {
			try{
				loadFactory();
				candidatos = new CandidatosImp(getManager());
				candidato.setFormulario(txFormulario.getText());
				candidatos.save(candidato);
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
		}
	}

	@FXML
	void salvar(ActionEvent event) {
		salvarFim();
	}

	private void salvarFim() {
		if (txEmail.getText().trim().equals("") || dtNascimento.getValue()==null) {
			String message ="Os campos a seguir não foram informados \n" +  (txEmail.getText().trim().equals("")? "E-mail não informado, \n":"");
			message+=dtNascimento.getValue()==null?"Idade ou data de nascimento nao informada":"";
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Campo não informado! Deseja continuar?");
			alert.setHeaderText("!");
			alert.setContentText(message);
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get()== ButtonType.CANCEL)
				return;
		}
		try {
			loadFactory();
			candidatos = new CandidatosImp(getManager());
			if (txCodigo.getText().equals("")) {
				candidato = new Candidato();
				candidato.setCriadoEm(Calendar.getInstance());
				candidato.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
				if (!txEmail.getText().trim().equals("")) {
					Candidato can = candidatos.findByEmail(txEmail.getText().trim());
					if (can != null) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Erro Registro");
						alert.setHeaderText("Valor duplicado!");
						alert.setContentText("E-mail informado ja existe!"+can.getId()+"-"+can.getNome()+"\nClique em OK para salvar");
						Optional<ButtonType> result = alert.showAndWait();
						if(result.get()== ButtonType.CANCEL)
							return;
					}
				}
			}else if(!txEmail.getText().trim().equals("")){
				Candidato can = candidatos.findByEmail(txEmail.getText().trim());
				if (can!=null && can.getId() != candidato.getId()) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Erro Registro");
					alert.setHeaderText("Valor duplicado!");
					alert.setContentText("E-mail informado ja existe!"+can.getId()+"-"+can.getNome()+"\nClique em OK para salvar");
					Optional<ButtonType> result = alert.showAndWait();
					if(result.get()== ButtonType.CANCEL)
						return;
				}
			}
			candidato.setNome(txNome.getText());
			candidato.setSexo(rbSexoF.isSelected() ? "F" : "M");
//			if(dtNascimento.getValue()==null) {
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//				alert.setTitle("Erro Registro");
//				alert.setHeaderText("Campo obrigatorio!");
//				alert.setContentText("é obrigatorio informar a data de nascimento ou a idade!");
//				alert.showAndWait();
//				return;
//			}
			if(dtNascimento.getValue()==null){
				candidato.setDataNascimento(null);
			}
			else{
				candidato.setDataNascimento(dtNascimento.getValue()==null?null
						:GregorianCalendar.from(dtNascimento.getValue().atStartOfDay(ZoneId.systemDefault())));
			}
			candidato.setEstadoCivil(cbEstadoCivil.getValue());
			candidato.setFumante(ckFumante.isSelected() ? 1 : 0);
			candidato.setFilhos(ckFilhos.isSelected() ? 1 : 0);
			if (ckFilhos.isSelected()) {
				try {
					int value = Integer.parseInt(txQtdFilhos.getText());
					candidato.setQtdeFilhos(value);
				} catch (Exception e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Erro de conversão");
					alert.setHeaderText("Valor informado esta incorreto!");
					alert.setContentText("Informe uma quantidade de filhos valida para salvar essa operação!");
					alert.showAndWait();
					return;
				}
			}
			candidato.setEscolaridade(cbEscolaridade.getValue());
			candidato.setCursoSuperior(cbCursoSuperior.getValue());
			candidato.setNacionalidade(cbNacionalidade.getValue());
			PfPj pfpj = new PfPj();
			
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
			candidato.setPessoaFisica(pfpj);

			// curriculo
			candidato.setObjetivo1(cbObjetivo1.getValue()!=null?(cbObjetivo1.getValue().getId()==-1?null:cbObjetivo1.getValue()):null);
			candidato.setObjetivo2(cbObjetivo2.getValue()!=null?(cbObjetivo2.getValue().getId()==-1?null:cbObjetivo2.getValue()):null);
			candidato.setObjetivo3(cbObjetivo3.getValue()!=null?(cbObjetivo3.getValue().getId()==-1?null:cbObjetivo3.getValue()):null);

			candidato.setIndicacao(ckPossuiIndicacao.isSelected() ? 1 : 0);
			candidato.setEmpresaIndicacao(txEmpresaIndicacao.getText());
			candidato.setDetalhesIndicacao(txDetalhesIndicacao.getText());

			candidato.setEmpresa1(txCarreiraEmpresa1.getText());
			candidato.setEmpresa2(txCarreiraEmpresa2.getText());
			candidato.setEmpresa3(txCarreiraEmpresa3.getText());

			candidato.setCargo1(cbCarreiraObjetivo1.getValue()!=null?(cbCarreiraObjetivo1.getValue().getId()==-1?null:cbCarreiraObjetivo1.getValue()):null);
			candidato.setCargo2(cbCarreiraObjetivo2.getValue()!=null?(cbCarreiraObjetivo2.getValue().getId()==-1?null:cbCarreiraObjetivo2.getValue()):null);
			candidato.setCargo3(cbCarreiraObjetivo3.getValue()!=null?(cbCarreiraObjetivo3.getValue().getId()==-1?null:cbCarreiraObjetivo3.getValue()):null);

			candidato.setDescricaoCargo1(txCarreiraDescricao1.getText());
			candidato.setDescricaoCargo2(txCarreiraDescricao2.getText());
			candidato.setDescricaoCargo3(txCarreiraDescricao3.getText());

			candidato.setUltimaModificacao(Calendar.getInstance());
			candidato.setFormulario(txFormulario.getText());

			candidato.setOcupado(ckNaoDisponivel.isSelected() ? 1 : 0);
			candidato.setOcupadoDetalhes(txNaoDisponivelDetalhes.getText());

			if (!txFormulario.getText().equals("") && !txFormulario.getText().startsWith(PathStorageEnum.CURRICULO.getDescricao()+"/")) {
				try {
					storage.transferTo(txFormulario.getText(), PathStorageEnum.CURRICULO.getDescricao()+"/"+txFormulario.getText());
					txFormulario.setText(PathStorageEnum.CURRICULO.getDescricao()+"/" + txFormulario.getText());
					candidato.setFormulario(txFormulario.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
				candidato.setFormulario(txFormulario.getText());

			candidato = candidatos.save(candidato);
			txCodigo.setText(String.valueOf(candidato.getId()));
			desbloquear(false, pnCadastro.getChildren());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Salvo com sucesso!");
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Falha ao salvar o registro\n" + e);
			alert.showAndWait();
			e.printStackTrace();
		} finally {
			close();
		}
		filtrar();
	}

	@SuppressWarnings("unchecked")
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
		TableColumn<Candidato, String> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
		colunaEditar.setCellFactory((TableColumn<Candidato, String> param) -> {
			final TableCell<Candidato, String> cell = new TableCell<Candidato, String>() {
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
							tabPane.getSelectionModel().select(tabCadastro);
							limparTela(pnCadastro.getChildren());
							preencherFormulario(tbCandidatos.getItems().get(getIndex()));
							desbloquear(true, pnCadastro.getChildren());

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
						Candidato candidato1 = tbCandidatos.getItems().get(getIndex());
//						if (anuncio != null && !anuncio.getCurriculoSet().contains(candidato1)) {
//							setGraphic(button);
//						}
						if(anuncio!=null) {
							if(candidato1.getOcupado()==1) {
								button.setText("Não Disponivel");
								button.setDisable(true);
							}
							else if(anuncio.getCurriculoSet() != null && anuncio.getCurriculoSet().contains(candidato1)) {
								button.setText("Já Vinculado");
								button.setDisable(true);
							}
							setGraphic(button);
						}
						button.setOnAction(event -> {
							try {
								incluirAnuncioCandidato(candidato1);
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setTitle("Confirmação!");
								alert.setHeaderText("Candidato incluido no anuncio");
								alert.setContentText("O candidato " + candidato1.getNome()
										+ " foi incluido em \nAnuncio: " + anuncio.getNome() + "\nCliente: "
										+ anuncio.getCliente().getNome() + "  !\n Não esqueça de salvar o anuncio!");
								alert.showAndWait();
								setGraphic(null);

							} catch (Exception e) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Confirmação!");
								alert.setHeaderText("Erro");
								alert.setContentText(
										"O candidato não foi incluido \nEntre em contato com o administrador e informe o erro: \n"
												+ e);
								alert.showAndWait();
							}
						});
						setText(null);
					}
				}
			};
			return cell;
		});
		tbCandidatos.getColumns().addAll(colunaId, colunaDataCriacao, colunaNome, colunaIdade, colunaIndicacao,
				colunaSelecoes, colunaEntrevista, colunaPreAprovacoes, colunaAprovacoes, colunaDisponivel, colunaEditar,
				colunaAdicionarAnuncio);
	}
	@FXML
	void visualizarFormulario(ActionEvent event) {
		Runnable run = () -> {
            if(!txFormulario.getText().equals("")){
                try {
                    File file  = storage.downloadFile(txFormulario.getText());
                    if(file!=null)
                        Desktop.getDesktop().open(file);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
		Platform.runLater(run);
		
	}
}
