package com.plkrhone.sisrh.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.plkrhone.sisrh.model.Cargo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.repository.helper.VagasImp;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Tiago on 21/07/2017.
 */
public class ControllerVaga extends PersistenciaController implements Initializable {

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private JFXTextField txPesquisa;

	@FXML
	private JFXButton btExcluir;

	@FXML
	private JFXButton btnAlterar;

	@FXML
	private JFXTextField txCodigo;
	@FXML
	private TableView<Cargo> tbVagas;

	@FXML
	private JFXTextField txCriadoEm;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXTextField txNome;
	@FXML
	private JFXTextField txFonte;
	@FXML
	private JFXTextArea txDescricao;
	@FXML
	private AnchorPane pnCadastro;
	@FXML
	private Tab tabCadastro;
	@FXML
	private Tab tabPesquisa;
	@FXML
	private JFXTabPane tab;

	Cargo cargo;
	VagasImp vagas;

	private static Logger log = LoggerFactory.getLogger(ControllerVaga.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		long tempoInicial = System.currentTimeMillis();
		tabela();
		loadFactory();
		try {
			List<Cargo> lista = new VagasImp(getManager()).getAll();
			tbVagas.setItems(FXCollections.observableList(lista));
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
		long tempoFinal = System.currentTimeMillis();
		if (log.isDebugEnabled()) {
			log.debug("Tela " + getClass().getSimpleName().replace("Controller", "") + " abriu em : "
					+ (tempoFinal - tempoInicial) + " ms");
		}
	}

	private void desbloquear(boolean value) {
		ObservableList<Node> nodes = pnCadastro.getChildren();
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setEditable(value);
			} else if (n instanceof JFXComboBox || n instanceof TableView || n instanceof JFXCheckBox) {
				n.setDisable(!value);
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setEditable(value);
			}
		});
	}

	@FXML
	void editar(ActionEvent event) {
		if (!txCodigo.getText().equals("")) {
			int valor = Integer.parseInt(txCodigo.getText());
			if (valor <= 2855) {
				desbloquear(false);
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Cadastro padronizado");
				alert.setHeaderText("Registro nã pode ser alterado");
				alert.setContentText(
						"Esse registro foi cadastrado automaticamente, portanto não pode ser modificado ou alterado");
				alert.showAndWait();
			} else
				desbloquear(true);
		}

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
					VagasImp vagas = new VagasImp(getManager());
					Cargo cargo = vagas.findById(Long.parseLong(txCodigo.getText()));
					vagas.remove(cargo);
					tbVagas.getItems().remove(cargo);

					tab.getSelectionModel().select(tabCadastro);
					alert.setAlertType(Alert.AlertType.INFORMATION);
					alert.setContentText("Registro excluido com sucesso!");
					alert.showAndWait();
					limparTela();
					desbloquear(false);
					cargo = null;
				} catch (Exception e) {
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Erro ao excluir o registro");
					alert.setContentText("Ocorreu um erro ao tentar excluir o registro.\n" + e);
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

	void filtrar() {
		try {
			loadFactory();
			vagas = new VagasImp(getManager());
			List<Cargo> cargoList = vagas.getVagasByNome(txPesquisa.getText().trim());
			tbVagas.getItems().clear();
			tbVagas.setItems(FXCollections.observableArrayList(cargoList));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void limparTela() {
		ObservableList<Node> nodes = pnCadastro.getChildren();
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setText("");
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setValue(null);
				;
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
	void novo(ActionEvent event) {
		limparTela();
		desbloquear(true);
		tab.getSelectionModel().select(tabCadastro);
		this.cargo = null;
	}

	@FXML
	void pesquisar(KeyEvent event) {
		filtrar();
	}

	void preencherFormulario(Cargo cargo) {
		txCodigo.setText(String.valueOf(cargo.getId()));
		txNome.setText(cargo.getNome());
		txCriadoEm.setText(cargo.getCriadoEm() == null ? ""
				: new SimpleDateFormat("dd/MM/yyyy").format(cargo.getCriadoEm().getTime()));
		txDescricao.setText(cargo.getDescricao());
		txFonte.setText(cargo.getFonte());
		this.cargo = cargo;
	}

	@FXML
	void salvar(ActionEvent event) {
		loadFactory();
		if (txCodigo.getText().equals("")) {
			cargo = new Cargo();
			cargo.setCriadoEm(Calendar.getInstance());
			vagas = new VagasImp(getManager());
			if (vagas.findByNome(txNome.getText().trim()) != null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Valor duplicado");
				alert.setContentText("Ja existe um cadastro com o texto informado");
				alert.showAndWait();
				close();
				return;
			}
		} else {
			Long id = Long.parseLong(txCodigo.getText());
			cargo.setId(id);
			if (id <= 2855)// nao deixar editar os registros ja candidatados
				return;
			else {
				Cargo v = vagas.findByNome(txNome.getText().trim());
				if (v != null && v.getId()!= cargo.getId()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Valor duplicado");
					alert.setContentText("Ja existe um cadastro com o texto informado");
					alert.showAndWait();
					close();
					return;
				}
			}
		}
		cargo.setNome(txNome.getText());
		cargo.setDescricao(txDescricao.getText());
		cargo.setFonte(txFonte.getText());
		try {
			vagas = new VagasImp(getManager());
			cargo = vagas.save(cargo);
			txCodigo.setText(String.valueOf(cargo.getId()));
			desbloquear(false);
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro ao salvar o registro");
			alert.setContentText("Ocorreu um erro ao tentar salvar o registro.\n" + e);
			alert.showAndWait();
		} finally {
			close();
		}
		filtrar();
	}

	@SuppressWarnings("unchecked")
	void tabela() {
		TableColumn<Cargo, Number> colunaId = new TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaId.setPrefWidth(40);
		TableColumn<Cargo, String> colunaNome = new TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaNome.setPrefWidth(200);
		TableColumn<Cargo, String> colunaDescricao = new TableColumn<>("Descricao");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colunaDescricao.setCellFactory((TableColumn<Cargo, String> param) -> {
			final TableCell<Cargo, String> cell = new TableCell<Cargo, String>() {
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
		colunaDescricao.setPrefWidth(400);
		TableColumn<Cargo, String> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
		colunaEditar.setCellFactory((TableColumn<Cargo, String> param) -> {
			final TableCell<Cargo, String> cell = new TableCell<Cargo, String>() {
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
							tab.getSelectionModel().select(tabCadastro);
							Cargo cargo = tbVagas.getItems().get(getIndex());
							if (cargo != null && cargo.getId() <= 2855) {
								desbloquear(false);
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setTitle("Cadastro padronizado");
								alert.setHeaderText("Registro não pode ser alterado");
								alert.setContentText(
										"Esse registro foi cadastrado automaticamente, portanto não pode ser modificado ou alterado");
								alert.showAndWait();
							} else
								desbloquear(true);
							limparTela();
							preencherFormulario(cargo);
						});
						setGraphic(button);
						setText(null);
					}
				}
			};
			return cell;
		});
		tbVagas.setFixedCellSize(50);
		tbVagas.getColumns().addAll(colunaId, colunaNome, colunaDescricao, colunaEditar);
	}
}
