package com.plkrhone.sisrh.controller;

import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
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
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.Avaliacao;
import com.plkrhone.sisrh.model.AvaliacaoGrupo;
import com.plkrhone.sisrh.repository.helper.AvaliacoesGrupoImp;
import com.plkrhone.sisrh.repository.helper.AvaliacoesImp;
import com.plkrhone.sisrh.util.DialogReplace;
import com.plkrhone.sisrh.util.Keys;
import com.plkrhone.sisrh.util.storage.PathStorageEnum;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerAvaliacao extends PersistenciaController implements Initializable {

	@FXML
	private JFXTextField txPesquisa;
	@FXML
	private JFXTextArea txDescricao;
	@FXML
	private JFXTextArea txGabarito;
	@FXML
	private JFXButton btExcluir;
	@FXML
	private JFXTextField txCodigo;
	@FXML
	private JFXComboBox<Avaliacao.AvaliacaoTipo> cbTipoAvaliacao;
	@FXML
	private Tab tabPesquisa;
	@FXML
	private Tab tabCadastro;
	@FXML
	private TableView<Avaliacao> tbAvaliacao;
	@FXML
	private JFXButton btAnexar;
	@FXML
	private MaskTextField txPontuacao;
	@FXML
	private JFXTextField txNome;
	@FXML
	private JFXTextField txFormulario;
	@FXML
	private JFXButton btnSalvar;
	@FXML
	private AnchorPane pnCadastro;
	@FXML
	private JFXComboBox<AvaliacaoGrupo> cbGrupoPesquisa;
	@FXML
	private JFXComboBox<Avaliacao.AvaliacaoTipo> cbTipoAvaliacaoPesquisa;
	@FXML
	private JFXComboBox<AvaliacaoGrupo> cbGrupo;
	@FXML
	private JFXButton btnAlterar;
	@FXML
	private JFXButton btnNovoDepartamento;
	@FXML
	private Font x1;
	@FXML
	private Color x2;
	@FXML
	private AnchorPane pnPesquisa;
	@FXML
	private JFXTextField txCriadoEm;
	@FXML
	private JFXTabPane tabPane;
	@FXML
	private JFXButton btnNovo;
	@FXML
	private JFXButton btnRemover;
	@FXML
	private JFXButton btVisualizar;

	private Avaliacao avaliacao;
	private AvaliacoesImp avaliacoes;
	private AvaliacoesGrupoImp grupos;
	
	Storage storage = StorageProducer.newConfig();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadFactory();
			tabela();
			avaliacoes = new AvaliacoesImp(getManager());
			grupos = new AvaliacoesGrupoImp(getManager());
			combos();
			List<Avaliacao> lista = avaliacoes.getAll();
			tbAvaliacao.setItems(FXCollections.observableArrayList(lista));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		desbloquear(true);
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
//			alert = new Alert(Alert.AlertType.CONFIRMATION);
//			alert.setTitle("Informação importante!");
//			alert.setHeaderText("Deseja usar modelo do Word para imprimir seus formulários pré-prenchidos?");
//			alert.setContentText(
//					"Para usar essa técnica, salve seus formularios no padrão Microsoft Word 2007 (.doc),\n"
//							+ "Caso contrario clique em cancelar");
//			Optional<ButtonType> buttonType = alert.showAndWait();
//			if (buttonType.get() == ButtonType.CANCEL) {
//				filters.add(new FileChooser.ExtensionFilter("MS Word 2007", "*.doc"));
//				filters.add(new FileChooser.ExtensionFilter("*.pdf", "*.doc|*.pdf|*.docx"));
//				filters.add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
//			}
			
			filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc","*.pdf","*.docx"));
			
			
			File file = storage.carregarArquivo(new Stage(), filters);
			if (file != null) {
				String novoNome = storage.gerarNome(file,PathStorageEnum.AVALIACAO.getDescricao());
				try{
					storage.uploadFile(file, novoNome);
					txFormulario.setText(novoNome);
				} catch(Exception e) {
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Armazenamento de arquivo");
					alert.setHeaderText("Não foi possivel enviar o arquivo para o servidor");
					alert.setContentText("Um erro desconhecido não permitiu o envio do arquivo para uma pasta do servidor\n"+e);
					alert.showAndWait();
					e.printStackTrace();
				}
			}
		}
	}
	private void combos() {
		cbTipoAvaliacao.getItems().addAll(Avaliacao.AvaliacaoTipo.values());
		cbTipoAvaliacaoPesquisa.getItems().add(null);
		cbTipoAvaliacaoPesquisa.getItems().addAll(Avaliacao.AvaliacaoTipo.values());
		cbTipoAvaliacao.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (newValue==Avaliacao.AvaliacaoTipo.DISSERTATIVA) {
					txPontuacao.setVisible(false);
					txPontuacao.setText("");
				} else
					txPontuacao.setVisible(true);
			}
		});
		cbTipoAvaliacaoPesquisa.valueProperty().addListener(new ChangeListener<Avaliacao.AvaliacaoTipo>() {

			@Override
			public void changed(ObservableValue<? extends Avaliacao.AvaliacaoTipo> observable, Avaliacao.AvaliacaoTipo oldValue,
					Avaliacao.AvaliacaoTipo newValue) {
				filtrar();
				
			}
		});
		cbGrupoPesquisa.valueProperty().addListener(new ChangeListener<AvaliacaoGrupo>() {

			@Override
			public void changed(ObservableValue<? extends AvaliacaoGrupo> observable, AvaliacaoGrupo oldValue,
					AvaliacaoGrupo newValue) {
				filtrar();
				
			}
		});
		
		cbTipoAvaliacao.getSelectionModel().selectFirst();
		txPontuacao.setVisible(false);
		txPontuacao.setText("");

		ObservableList<AvaliacaoGrupo> avaliacaoGrupos = FXCollections.observableArrayList(grupos.getAll());
		
		cbGrupo.getItems().add(null);
		cbGrupo.getItems().addAll(avaliacaoGrupos);
		cbGrupoPesquisa.getItems().addAll(cbGrupo.getItems());
	}

	private void desbloquear(boolean value) {
		ObservableList<Node> nodes = pnCadastro.getChildren();
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setEditable(value);
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setEditable(value);
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setEditable(value);
			} else if (n instanceof MaskTextField) {
				((MaskTextField) n).setEditable(value);
			} else if (n instanceof JFXComboBox || n instanceof TableView || n instanceof JFXCheckBox) {
				n.setDisable(!value);
			}
		});
	}
	@FXML
	void editar(ActionEvent event) {
		if (!txCodigo.getText().equals(""))
			desbloquear(true);
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
				loadFactory();
				try {
					avaliacoes = new AvaliacoesImp(getManager());
					avaliacao = avaliacoes.findById(Long.parseLong(txCodigo.getText()));
					tbAvaliacao.getItems().remove(avaliacao);
					tabPane.getSelectionModel().select(tabCadastro);
					alert.setAlertType(Alert.AlertType.INFORMATION);
					alert.setContentText("Registro excluido com sucesso!");
					alert.showAndWait();
					limparTela();
					desbloquear(false);
					avaliacao = null;
				} catch (Exception e) {
					alert.setAlertType(Alert.AlertType.ERROR);
					alert.setContentText("Erro ao excluir o registro!\n" + e);
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
		try{
			loadFactory();
			avaliacoes = new AvaliacoesImp(getManager());
			List<Avaliacao> avaliacaos = avaliacoes.filtrar(txPesquisa.getText().trim(), cbTipoAvaliacaoPesquisa.getValue(), cbGrupoPesquisa.getValue());
			tbAvaliacao.getItems().clear();
			tbAvaliacao.getItems().addAll(FXCollections.observableArrayList(avaliacaos));
		}catch(Exception e){
		}finally{
			close();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void limparTela() {
		ObservableList<Node> nodes = pnCadastro.getChildren();
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setText("");
			} else if(n instanceof MaskedTextField) {
				((MaskedTextField) n).setText("");
			} else if(n instanceof MaskTextField) {
				((MaskTextField) n).setText("");
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setValue(null);
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setText("");
			} else if (n instanceof TableView) {
				((TableView) n).getItems().clear();
			} else if (n instanceof JFXCheckBox) {
				((JFXCheckBox) n).setSelected(false);
			}
		});
		cbTipoAvaliacao.getSelectionModel().selectFirst();
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
	void novo(ActionEvent event) {
		limparTela();
		desbloquear(true);
		tabPane.getSelectionModel().select(tabCadastro);
		this.avaliacao = null;
	}

	@FXML
	void novoDepartametno(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Criação de grupos");
		dialog.setHeaderText("Crie um novo grupo:");
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
						cbGrupoPesquisa.getItems().clear();
						cbGrupoPesquisa.getItems().addAll(novaLista);
						if (avaliacaoGrupo1 != null) {
							cbGrupo.getSelectionModel().select(avaliacaoGrupo1);
						}
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Valor duplicado");
						alert.setContentText("Já existe um cadastro com o texto informado");
						alert.showAndWait();
					}
				} catch (Exception e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setContentText("Erro ao salvar o registro\n" + e);
					alert.showAndWait();
				} finally {
					close();
				}

			}
		}
	}
	@FXML
	void pesquisar(KeyEvent event) {
		filtrar();
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
		this.avaliacao = avaliacao;
	}

	@FXML
	void removerDocumento(ActionEvent event) {
		try{
			storage.delete(txFormulario.getText());
			txFormulario.setText("");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(avaliacao!=null) {
			try{
				loadFactory();
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
	void salvar(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		try {
			loadFactory();
			avaliacoes = new AvaliacoesImp(getManager());
			if (txCodigo.getText().equals("")) {
				avaliacao = new Avaliacao();
				avaliacao.setCriadoEm(Calendar.getInstance());
				avaliacao.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
				Avaliacao a = avaliacoes.findByNome(txNome.getText().trim());
				if (a != null) {
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Valor duplicado");
					alert.setContentText("Já existe um cadastro com o texto informado");
					alert.showAndWait();
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
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Valor incorreto");
					alert.setContentText("Valor da pontuação informada esta incorreta!");
					alert.showAndWait();
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
			avaliacao = avaliacoes.save(avaliacao);
			txCodigo.setText(String.valueOf(avaliacao.getId()));
			desbloquear(false);
			
		} catch (Exception e) {
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Parâmentro incorreto");
			alert.setHeaderText(null);
			alert.setContentText("A pontuação informada esta incorreta, corrija o valor e tente novamente");
			alert.showAndWait();
		} finally {
			close();
		}
		filtrar();
	}

	@SuppressWarnings("unchecked")
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
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText("");
					setStyle("");
				} else {
					setText(item.trim().length() == 0 ? "Não" : "Sim");
				}
			}
		});
		TableColumn<Avaliacao, String> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
		colunaEditar.setCellFactory((TableColumn<Avaliacao, String> param) -> {
			final TableCell<Avaliacao, String> cell = new TableCell<Avaliacao, String>() {
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
							desbloquear(true);
							limparTela();
							preencherFormulario(tbAvaliacao.getItems().get(getIndex()));
						});
						setGraphic(button);
						setText(null);
					}
				}
			};
			return cell;
		});
		tbAvaliacao.setFixedCellSize(50);
		tbAvaliacao.getColumns().addAll(colunaId, colunaNome, colunaDetalhes, colunaTipo, colunaNota, colunaFormulario,
				colunaEditar);
	}

	@FXML
	void visualizar(ActionEvent event) {
		if(!txFormulario.getText().equals("")) {
			try {
				File file  = storage.downloadFile(txFormulario.getText());
				if(file!=null)
					Desktop.getDesktop().open(file);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}