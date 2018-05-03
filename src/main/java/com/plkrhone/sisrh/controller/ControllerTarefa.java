package com.plkrhone.sisrh.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
import com.jfoenix.controls.JFXTimePicker;
import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Anuncio.AnuncioStatus;
import com.plkrhone.sisrh.model.Anuncio.Cronograma;
import com.plkrhone.sisrh.model.Cliente;
import com.plkrhone.sisrh.model.Tarefa;
import com.plkrhone.sisrh.model.Usuario;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistasImp;
import com.plkrhone.sisrh.repository.helper.AnunciosImp;
import com.plkrhone.sisrh.repository.helper.ClientesImp;
import com.plkrhone.sisrh.repository.helper.TarefasImp;
import com.plkrhone.sisrh.repository.helper.UsuariosImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.UserSession;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ControllerTarefa extends PersistenciaController implements Initializable {

	@FXML
	private JFXComboBox<Cronograma> cbCronogramaPesquisa;

	@FXML
	private JFXDatePicker dtInicioPesquisa;

	@FXML
	private JFXDatePicker dtFimPesquisa;

	@FXML
	private JFXComboBox<Cliente> cbClientePesquisa;

	@FXML
	private JFXComboBox<Anuncio> cbAnuncioPesquisa;

	@FXML
	private JFXComboBox<AnuncioStatus> cbAnuncioStatusPesquisa;

	@FXML
	private JFXComboBox<String> cbTarefaStatusPesquisa;

	@FXML
	private JFXComboBox<Usuario> cbAtendentePesquisa;

	@FXML
	private JFXComboBox<Cronograma> cbCrograma;

	@FXML
	private JFXComboBox<Usuario> cbAtendente;

	@FXML
	private JFXTextArea txDescricao;

	@FXML
	private JFXTextField txCodigo;

	@FXML
	private JFXDatePicker dtEvento;

	@FXML
	private JFXCheckBox ckFinalizado;

	@FXML
	private JFXCheckBox ckDiaTodo;

	@FXML
	private JFXDatePicker dtFimEvento;
	
	@FXML
	private JFXTimePicker timeInicio;
	@FXML
	private JFXTimePicker timeFim;
	
	@FXML
	private JFXComboBox<Anuncio> cbAnuncio;
	@FXML
	private Tab tabPesquisa;
	@FXML
	private Tab tabCadastro;
	@FXML
	private JFXTabPane tabPrincipal;
	@FXML
	private AnchorPane pnCadastro;
	@FXML
	private JFXComboBox<AnuncioEntrevista> cbAnuncioEntrevista;
	@FXML
	private TableView<Tarefa> tbTarefa;
	private Tarefa tarefa;
	private TarefasImp tarefas;
	private AnunciosImp anuncios;
	private ClientesImp clientes;
	private UsuariosImp usuarios;
	private AnuncioEntrevistasImp anunciosEntrevistas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			tabela();
			loadFactory();
			tarefas = new TarefasImp(getManager());
			combos();
			tbTarefa.getItems().addAll(tarefas.filtrar(null, null, null, null, null, null, null, 0));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@FXML
	void acionarAvaliacao(ActionEvent event) {
		if(cbAnuncioEntrevista.getValue()!=null)
			carregarAvaliacao();
	}

	@FXML
	void acionarEntrevista(ActionEvent event) {
		if(cbAnuncioEntrevista.getValue()!=null)
			carregarEntrevista();
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
	}
	Stage carregarEntrevista() {
		try {
			AnuncioEntrevista ae = cbAnuncioEntrevista.getValue();
			FXMLLoader loader = carregarFxmlLoader("Entrevista");
			Stage stage = carregarStage(loader, "");
			ControllerEntrevista controller = loader.getController();
			controller.iniciar(ae,stage);
			stage.show();
			stage.setOnCloseRequest(event1 -> {
				if (event1.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
					if(controller.getEntrevista()!=null) {
						try {
							loadFactory();
							anunciosEntrevistas = new AnuncioEntrevistasImp(getManager());
							AnuncioEntrevista ae2 = anunciosEntrevistas.findById(ae.getId());
							cbAnuncioEntrevista.setValue(ae2);
						}catch (Exception e) {
							e.printStackTrace();
						}finally {
							close();
						}
					}
				}
			});
			return stage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	Stage carregarAvaliacao() {
		try {
			loadFactory();
			anunciosEntrevistas = new AnuncioEntrevistasImp(getManager());
			AnuncioEntrevista ae = anunciosEntrevistas.findById(cbAnuncioEntrevista.getValue().getId());
			
			FXMLLoader loader = carregarFxmlLoader("EntrevistaAvaliacao");
			Stage stage = carregarStage(loader, "Cadastro de Avaliacao ");
			ControllerEntrevistaAvaliacao controller = loader.getController();
			controller.iniciar(ae.getAvaliacao(), ae);
			stage.show();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
						try {
							loadFactory();
							anunciosEntrevistas = new AnuncioEntrevistasImp(getManager());
							AnuncioEntrevista ae2 = anunciosEntrevistas.findById(ae.getId());
							cbAnuncioEntrevista.setValue(ae2);
						}catch (Exception e) {
							e.printStackTrace();
						}finally {
							close();
						}
					}
				}
			});
			return stage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally {
			close();
		}
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

	void combos() {
		dtInicioPesquisa.setValue(LocalDate.now());
		dtFimPesquisa.setValue(LocalDate.now());
		dtEvento.setValue(LocalDate.now());
		timeFim.setIs24HourView(true);
		timeInicio.setIs24HourView(true);
		
		ChangeListener<Object> pesquisas = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				filtrar();
			}
		};
		
		cbCronogramaPesquisa.getItems().addAll(null, Cronograma.AGENDAMENTO_DE_ENTREVISTA);
		cbCrograma.getItems().add(Cronograma.AGENDAMENTO_DE_ENTREVISTA);
		cbCrograma.getSelectionModel().selectFirst();

		anuncios = new AnunciosImp(getManager());
		List<Anuncio> anuncioList = anuncios.filtrar(null, null, -1, null, null, null, null, null,null);
		cbAnuncioPesquisa.getItems().add(null);
		cbAnuncioPesquisa.getItems().addAll(anuncioList);

		anuncioList = anuncios.filtrar(null, AnuncioStatus.EM_ANDAMENTO, -1, null, null, null, null, null,null);
		cbAnuncio.getItems().add(null);
		cbAnuncio.getItems().addAll(anuncioList);
		cbAnuncio.getSelectionModel().selectFirst();
		
		cbAnuncio.valueProperty().addListener(new ChangeListener<Anuncio>() {
			@Override
			public void changed(ObservableValue<? extends Anuncio> observable, Anuncio oldValue, Anuncio newValue) {
				if (newValue != null) {
					try {
						loadFactory();
						anuncios = new AnunciosImp(getManager());
						Anuncio anuncio = anuncios.findById(cbAnuncio.getValue().getId());
						cbAnuncioEntrevista.getItems().clear();
						cbAnuncioEntrevista.getItems().addAll(anuncio.getEntrevistaSet());
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						close();
					}
				}

			}
		});
		ckDiaTodo.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					dtFimEvento.setEditable(false);
					timeFim.setEditable(false);
					dtFimEvento.setValue(LocalDate.now());
					timeFim.setValue(LocalTime.of(23,59,59));
				}
				else{
					dtFimEvento.setEditable(true);
					timeFim.setEditable(true);
					dtFimEvento.setValue(LocalDate.now());
					timeFim.setValue(LocalTime.now().plusHours(1));
				}
			}
		});
		
		clientes = new ClientesImp(getManager());
		cbClientePesquisa.getItems().add(null);
		cbClientePesquisa.getItems().addAll(clientes.getAll());

		usuarios = new UsuariosImp(getManager());
		List<Usuario> usuarioList = usuarios.getAll();
		cbAtendentePesquisa.getItems().add(null);
		cbAtendentePesquisa.getItems().addAll(usuarioList);
		cbAtendente.getItems().addAll(usuarioList);
		cbAtendente.getSelectionModel().select(UserSession.getInstance().getUsuario());
		cbAnuncioStatusPesquisa.getItems().addAll(AnuncioStatus.values());
		cbTarefaStatusPesquisa.getItems().addAll("Aberto", "Finalizado", "Qualquer");
		cbTarefaStatusPesquisa.getSelectionModel().select("Aberto");
		
		dtInicioPesquisa.valueProperty().addListener(pesquisas);
		dtFimPesquisa.valueProperty().addListener(pesquisas);
		cbCronogramaPesquisa.valueProperty().addListener(pesquisas);
		cbAnuncioPesquisa.valueProperty().addListener(pesquisas);
		cbClientePesquisa.valueProperty().addListener(pesquisas);
		cbAtendentePesquisa.valueProperty().addListener(pesquisas);
		cbAnuncioStatusPesquisa.valueProperty().addListener(pesquisas);
		cbTarefaStatusPesquisa.valueProperty().addListener(pesquisas);
		
		
		new ComboBoxAutoCompleteUtil<>(cbAnuncioPesquisa);
		new ComboBoxAutoCompleteUtil<>(cbClientePesquisa);
		new ComboBoxAutoCompleteUtil<>(cbAnuncio);
		//new ComboBoxAutoCompleteUtil<>(cbAnuncioEntrevista);
		limparTela(pnCadastro.getChildren());
	}


	void filtrar() {
		try {
			loadFactory();
			tarefas = new TarefasImp(getManager());
			String status = cbTarefaStatusPesquisa.getValue();
			int value = status.equals("Aberto")?0:(status.equals("Finalizado")?1:-1);
			LocalDateTime inicio = dtInicioPesquisa.getValue().atTime(LocalTime.of(00, 00, 00));
			LocalDateTime fim = dtFimPesquisa.getValue().atTime(LocalTime.of(23, 59, 59));
			List<Tarefa> lista = tarefas.filtrar(cbAnuncioPesquisa.getValue(), cbCronogramaPesquisa.getValue(), 
					cbAnuncioStatusPesquisa.getValue(), cbClientePesquisa.getValue(), 
					cbAtendentePesquisa.getValue(), inicio, fim, value);
			tbTarefa.getItems().clear();
			tbTarefa.getItems().addAll(lista);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void limparTela(ObservableList<Node> nodes) {
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
				((ComboBox) n).getSelectionModel().selectFirst();
				// ((ComboBox) n).setValue(null);
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
		dtEvento.setValue(LocalDate.now());
		cbCrograma.getSelectionModel().selectFirst();
		cbAnuncio.getSelectionModel().selectFirst();
		timeInicio.setValue(LocalTime.now());
		timeFim.setValue(LocalTime.now().plusHours(1));
		dtFimEvento.setValue(LocalDate.now());
	}
	
	@FXML
	void novo(ActionEvent event) {
		limparTela(pnCadastro.getChildren());
		desbloquear(true, pnCadastro.getChildren());
		tabPrincipal.getSelectionModel().select(tabCadastro);
		this.tarefa=null;
	}
	void preencherFormulario(Tarefa tarefa){
		try {
			txCodigo.setText(String.valueOf(tarefa.getId()));
			cbAnuncio.setValue(tarefa.getAnuncio());
			cbCrograma.setValue(tarefa.getCronograma());
			cbAtendente.setValue(tarefa.getAtendente());
			txDescricao.setText(tarefa.getDescricao());
			dtEvento.setValue(tarefa.getDataInicioEvento().getTime().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate());
			timeInicio.setValue(tarefa.getDataInicioEvento().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
			dtFimEvento.setValue(tarefa.getDataFimEvento().getTime().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate());
			timeFim.setValue(tarefa.getDataFimEvento().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
			ckDiaTodo.setSelected(tarefa.getDiaTodo()==1);
			ckFinalizado.setSelected(tarefa.getFinalizado()==1);
			loadFactory();
			anuncios = new AnunciosImp(getManager());
			//area de entrevista
			Anuncio a = anuncios.findById(tarefa.getAnuncio().getId());
			cbAnuncioEntrevista.getItems().clear();
			cbAnuncioEntrevista.getItems().addAll(a.getEntrevistaSet());
			cbAnuncioEntrevista.setValue(tarefa.getAnuncioEntrevista());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		this.tarefa=tarefa;
	}
	@FXML
	void remover(ActionEvent event) {
		if(this.tarefa!=null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirme a operação");
			alert.setContentText("Deseja remover a seguinte a tarefa?");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get()==ButtonType.OK) {
				try {
					loadFactory();
					tarefas.remove(tarefa);
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					close();	
				}
			}
			filtrar();
		}
	}

	@FXML
	void salvar(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Erro");
		if(cbAnuncio.getValue()==null) {
			alert.setContentText("Selecione um anuncio");
			alert.show();
			return;
		}
		if(cbCrograma.getValue()==Cronograma.AGENDAMENTO_DE_ENTREVISTA && cbAnuncioEntrevista.getValue()==null) {
			alert.setContentText("Selecione um candidato");
			alert.show();
			return;
		}
		if(timeInicio.getValue()==null) {
			alert.setContentText("Hora de inicio do evento incorreta");
			alert.show();
			return;
		}
		if (!ckDiaTodo.isSelected() && timeFim.getValue()==null) {
			alert.setContentText("Hora de fim do evento incorreta");
			alert.show();
			return;
		}		
		try {
			loadFactory();
			//anuncios = new AnunciosImp(getManager());
			tarefas = new TarefasImp(getManager());
			//Anuncio anuncio = anuncios.findById(cbAnuncio.getValue().getId());
			
			if(tarefa==null) {
				tarefa = new Tarefa();
				tarefa.setCriadoEm(Calendar.getInstance());
				tarefa.setCriadoPor(UserSession.getInstance().getUsuario());
			}
			tarefa.setAnuncio(cbAnuncio.getValue());
			tarefa.setCronograma(cbCrograma.getValue());
			tarefa.setAtendente(cbAtendente.getValue());
			tarefa.setDescricao(txDescricao.getText());

			LocalDateTime dataEvento = dtEvento.getValue().atTime(timeInicio.getValue());
			tarefa.setDataInicioEvento(GregorianCalendar.from(dataEvento.atZone(ZoneId.systemDefault())));
			LocalTime horaFimEvento = LocalTime.of(23, 59, 59);
			tarefa.setDiaTodo(1);
			
			if(!ckDiaTodo.isSelected()) {
				horaFimEvento = timeFim.getValue();
				tarefa.setDiaTodo(0);
			}
			LocalDateTime dataEventoFim= LocalDate.now().atTime(horaFimEvento);
			if(dataEvento.isAfter(dataEventoFim)) {
				alert.setContentText("Tempo de inicio é maior que o termino");
				alert.show();
				return;
			}
			tarefa.setDataFimEvento(GregorianCalendar.from(dataEventoFim.atZone(ZoneId.systemDefault())));
			
			//anuncio.setEntrevistaSet(cbAnuncioEntrevista.getItems().stream().collect(Collectors.toSet()));
			//cbAnuncioEntrevista.getValue()
			//anuncios.save(anuncio);
			tarefa.setAnuncioEntrevista(cbAnuncioEntrevista.getValue());
			tarefa.setFinalizado(ckFinalizado.isSelected() ? 1 : 0);
			this.tarefa = tarefas.save(tarefa);
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Sucesso");
			alert.setHeaderText("Salvo com sucesso!");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		filtrar();
	}

	@SuppressWarnings("unchecked")
	void tabela() {
		//id
		TableColumn<Tarefa, Number> colunaId = new TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		//datainicio
		TableColumn<Tarefa,Calendar> colunaData = new TableColumn<>("Inicio");
		colunaData.setCellValueFactory(new PropertyValueFactory<>("dataInicioEvento"));
		colunaData.setCellFactory((TableColumn<Tarefa, Calendar> param) -> new TableCell<Tarefa, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(item.getTime()));
				}
			}
		});
		//datafim
		TableColumn<Tarefa,Calendar> colunaDataFim = new TableColumn<>("Termino");
		colunaDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFimEvento"));
		colunaDataFim.setCellFactory((TableColumn<Tarefa, Calendar> param) -> new TableCell<Tarefa, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(item.getTime()));
				}
			}
		});
		//cronograma
		TableColumn<Tarefa,Cronograma> colunaCronograma = new TableColumn<>("Cronograma");
		colunaCronograma.setCellValueFactory(new PropertyValueFactory<>("cronograma"));
		colunaCronograma.setCellFactory((TableColumn<Tarefa, Cronograma> param) -> new TableCell<Tarefa, Cronograma>() {
			@Override
			protected void updateItem(Cronograma item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(item.getDescricao());
				}
			}
		});
		//anuncio
		TableColumn<Tarefa,Anuncio> colunaAnuncio = new TableColumn<>("Anuncio");
		colunaAnuncio.setCellValueFactory(new PropertyValueFactory<>("anuncio"));
		colunaAnuncio.setCellFactory((TableColumn<Tarefa, Anuncio> param) -> new TableCell<Tarefa, Anuncio>() {
			@Override
			protected void updateItem(Anuncio item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(item.getId()+"-"+item.getNome());
				}
			}
		});
		//status
		TableColumn<Tarefa,Number> colunaStatus = new TableColumn<>("Status");
		colunaStatus.setCellValueFactory(new PropertyValueFactory<>("finalizado"));
		colunaStatus.setCellFactory((TableColumn<Tarefa, Number> param) -> new TableCell<Tarefa, Number>() {
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(item.intValue()==1?"Finalizado":"Pendente");
				}
			}
		});
		
		//atendente
		TableColumn<Tarefa,Usuario> colunaAtendente= new TableColumn<>("Atendente");
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		colunaAtendente.setCellFactory((TableColumn<Tarefa, Usuario> param) -> new TableCell<Tarefa, Usuario>() {
			@Override
			protected void updateItem(Usuario item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					String[] nome = item.getNome().split(" ");
					setText(nome[0]);
				}
			}
		});
		//editar
		TableColumn<Tarefa, String> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
		colunaEditar.setCellFactory((TableColumn<Tarefa, String> param) -> {
			final TableCell<Tarefa, String> cell = new TableCell<Tarefa, String>() {
				final JFXButton button = new JFXButton("Editar");
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						button.getStyleClass().add("btJFXDefault");
						setText("Editar");
						button.setOnAction(event -> {
							tabPrincipal.getSelectionModel().select(tabCadastro);
							desbloquear(true,pnCadastro.getChildren());
							limparTela(pnCadastro.getChildren());
							preencherFormulario(tbTarefa.getItems().get(getIndex()));
						});
						setGraphic(button);
						setText(null);
					}
				}
			};
			return cell;
		});
		tbTarefa.getColumns().addAll(colunaId, colunaData,colunaDataFim,colunaCronograma,colunaAnuncio,colunaStatus,colunaAtendente,colunaEditar);
	}
	@FXML
	void verAnuncio(ActionEvent event) {

	}
	@FXML
	void verPerfil(ActionEvent event) {
		try {
			FXMLLoader loader = carregarFxmlLoader("Candidato");
			Stage stage = carregarStage(loader, "Perfil de Candidatos");
			ControllerCandidato controllerCandidato = loader.getController();
			controllerCandidato.preencherFormulario(cbAnuncioEntrevista.getValue().getCandidato());
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
