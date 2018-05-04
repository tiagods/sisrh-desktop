package com.plkrhone.sisrh.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Cidade;
import com.plkrhone.sisrh.model.Cliente;
import com.plkrhone.sisrh.model.ClienteSetor;
import com.plkrhone.sisrh.model.Endereco;
import com.plkrhone.sisrh.model.Estado;
import com.plkrhone.sisrh.model.FormularioRequisicao;
import com.plkrhone.sisrh.model.PfPj;
import com.plkrhone.sisrh.repository.helper.CidadesImp;
import com.plkrhone.sisrh.repository.helper.ClienteSetoresImp;
import com.plkrhone.sisrh.repository.helper.ClientesImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.EnderecoUtil;
import com.plkrhone.sisrh.util.UserSession;

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
public class ControllerCliente extends PersistenciaController implements Initializable {
	@FXML
	private PieChart grafico;
	@FXML
	private JFXTextField txPesquisa;

	@FXML
	private JFXComboBox<String> cbFiltro;

	@FXML
	private JFXComboBox<ClienteSetor> cbSetor;
	@FXML
	private JFXComboBox<ClienteSetor> cbSetorPesquisa;
	@FXML
	private MaskedTextField txCnpj;
	@FXML
	private JFXButton btExcluir;
	@FXML
	private JFXCheckBox ckClienteContabil;
	@FXML
	private JFXTextField txCodigo;
	@FXML
	private Tab tabPesquisa;
	@FXML
	private Tab tabCadastro;
	@FXML
	private JFXCheckBox ckDesabilitar;
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
	private JFXTextField txNome;
	@FXML
	private JFXTextField txEmail;
	@FXML
	private JFXTextField txResponsavel;
	@FXML
	private JFXTextField txCriacao;
	@FXML
	private MaskedTextField txTelefone;
	@FXML
	private TableView<Anuncio> tbCadastro;
	@FXML
	private JFXButton btnSalvar;
	@FXML
	private JFXButton btnAlterar;
	@FXML
	private MaskedTextField txCelular;
	@FXML
	private JFXTabPane tabPane;
	@FXML
	private TableView<Cliente> tbClientes;
	@FXML
	private JFXButton btnNovo;
	@FXML
	private JFXComboBox<String> cbSituacao;
	@FXML
	private JFXTextField txComplemento;
	@FXML
	private AnchorPane pnCadastro;

	Cliente cliente;
	private ClientesImp clientes;
	private CidadesImp cidades;
	private long ativos = 0, inativos = 0;
	private ClienteSetoresImp setores;

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
		    e.printStackTrace();
		}finally {
		    close();
		}
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

		cbSituacao.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals("Situação")) {
					filtrar();
				}
			}
		});
		cbFiltro.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals("Filtrar por:") && !newValue.equals("")) {
					filtrar();
				}
			}
		});
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
	private void desbloquear(boolean value) {
		ObservableList<Node> nodes = pnCadastro.getChildren();
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setEditable(value);
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setEditable(value);
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setDisable(!value);
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setEditable(value);
			} else if (n instanceof TableView) {
				((TableView) n).setDisable(!value);
			} else if (n instanceof JFXCheckBox) {
				((JFXCheckBox) n).setDisable(!value);
			}
		});
	}

	@FXML
	private void editar(ActionEvent event) {
		if (!txCodigo.getText().equals(""))
			desbloquear(true);
	}

	@FXML
	private void excluir(ActionEvent event) {
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
					clientes = new ClientesImp(getManager());
					Cliente c = clientes.findById(Long.parseLong(txCodigo.getText()));
					clientes.remove(c);
					tbClientes.getItems().remove(c);
					tabPane.getSelectionModel().select(tabCadastro);
					alert.setAlertType(Alert.AlertType.INFORMATION);
					alert.setContentText("Registro excluido com sucesso!");
					alert.showAndWait();
					limparTela();
					desbloquear(false);
					cliente = null;
				} catch (Exception e) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setHeaderText(null);
					alert.setContentText("Erro ao excluir os registros\n" + e);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void limparTela() {
		ObservableList<Node> nodes = pnCadastro.getChildren();
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setText("");
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setValue(null);
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setText("");
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setText("");
			} else if (n instanceof TableView) {
				((TableView) n).getItems().clear();
			} else if (n instanceof JFXCheckBox) {
				((JFXCheckBox) n).setSelected(false);
			}
		});
	}

	@FXML
	public void novo(ActionEvent event) {
		limparTela();
		desbloquear(true);
		tabPane.getSelectionModel().select(tabCadastro);
		this.cliente = null;
	}

	@FXML
	void novoSetor(ActionEvent event) {
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
					setores = new ClienteSetoresImp(getManager());
					ClienteSetor s = setores.findByNome(result.get().trim());
					if (s == null) {
						ClienteSetor setor = new ClienteSetor();
						setor.setNome(result.get().trim());
						setores.save(setor);

						// preencher combos
						List<ClienteSetor> avaliacaoGrupos = setores.getAll();
						ObservableList<ClienteSetor> novaLista = FXCollections.observableArrayList();
						novaLista.add(null);
						novaLista.addAll(avaliacaoGrupos);
						ClienteSetor setor1 = cbSetor.getValue();
						cbSetor.getItems().clear();
						cbSetor.getItems().addAll(novaLista);
						cbSetorPesquisa.getItems().clear();
						cbSetorPesquisa.getItems().addAll(novaLista);
						if (setor1 != null)
							cbSetor.getSelectionModel().select(setor1);
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Valor duplicado");
						alert.setContentText("Já existe um cadastro com o texto informado");
						alert.showAndWait();
					}
				} catch (Exception e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setContentText("Ocorreu um erro ao salvar o registro\n" + e);
					alert.showAndWait();
				} finally {
					close();
				}
			}
		}
	}

	@FXML
	public void pesquisar(KeyEvent event) {
		filtrar();
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
			if(pfpj!=null) {
				txCep.setPlainText(pfpj.getCep());
				txLogradouro.setText(pfpj.getLogradouro());
				txNumero.setText(pfpj.getNumero());
				txComplemento.setText(pfpj.getComplemento());;
				txBairro.setText(pfpj.getBairro());
				cbEstado.setValue(pfpj.getEstado());
				cbCidade.setValue(pfpj.getCidade());
			}
			if (cliente.getSetor() != null)
				cbSetor.setValue(cliente.getSetor());
			ckClienteContabil.setSelected(cliente.getClienteContabil() == 1 ? true : false);
			ckDesabilitar.setSelected(cliente.getSituacao() == 0);
			desbloquear(true);

			tbCadastro.getItems().clear();
			tbCadastro.getItems().addAll(cliente.getAnuncioSet());
			this.cliente = cliente;
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setContentText("Erro ao listar os registros\n" + e);
			alert.showAndWait();
		}
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
				cliente.setCriadoPor(UserSession.getInstance().getUsuario());
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
		colunaNome.setPrefWidth(120);
		TableColumn<Cliente, String> colunaResponsavel = new TableColumn<>("Responsavel");
		colunaResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));

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
		tbClientes.getColumns().addAll(colunaId, colunaNome, colunaResponsavel, colunaContato, colunaCelular,
				colunaClienteProlink, colunaStatus, colunaEditar);
	}

	@SuppressWarnings("unchecked")
	private void tabelaCadastro() {
		TableColumn<Anuncio, String> colunaId = new TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaId.setPrefWidth(40);

		TableColumn<Anuncio, FormularioRequisicao> colunaNome = new TableColumn<>("Vaga");
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
							setText(item.getVaga() != null ? item.getVaga().getNome() : "");
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
