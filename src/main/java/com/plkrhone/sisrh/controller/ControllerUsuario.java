package com.plkrhone.sisrh.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.fxutils.maskedtextfield.MaskTextField;
import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.PfPj;
import com.plkrhone.sisrh.model.Usuario;
import com.plkrhone.sisrh.repository.helper.UsuariosImp;
import com.plkrhone.sisrh.util.CriptografiaUtil;
import com.plkrhone.sisrh.util.UserSession;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ControllerUsuario extends PersistenciaController implements Initializable {

	@FXML
	private JFXTextField txPesquisa;

	@FXML
	private JFXComboBox<String> cbFiltro;

	@FXML
	private JFXComboBox<Usuario.UsuarioNivel> cbNivel;

	@FXML
	private JFXButton btExcluir;

	@FXML
	private JFXPasswordField txSenha;

	@FXML
	private JFXTextField txCriacao;

	@FXML
	private JFXTextField txCodigo;

	@FXML
	private JFXPasswordField txConfirmarSenha;

	@FXML
	private Tab tabCadastro;

	@FXML
	private Tab tabPesquisa;

	@FXML
	private JFXCheckBox ckDesabilitar;

	@FXML
	private TableView<Usuario> tbUsuarios;

	@FXML
	private JFXTextField txNome;

	@FXML
	private JFXTextField txEmail;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private AnchorPane pnCadastro;

	@FXML
	private JFXButton btnAlterar;

	@FXML
	private MaskedTextField txCelular;

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private MaskTextField txLogin;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXComboBox<String> cbStatusPesquisa;

	private UsuariosImp usuarios;
	private Usuario usuario;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		long tempoInicial = System.currentTimeMillis();
		tabela();
		loadFactory();
		combos();
		try {
			usuarios = new UsuariosImp(getManager());
			List<Usuario> lista = usuarios.filtrar("", 0, "login");
			System.out.println(lista.size());
			tbUsuarios.setItems(FXCollections.observableList(lista));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		long tempoFinal = System.currentTimeMillis();
		System.out.println((tempoFinal - tempoInicial) + " ms");
	}

	void combos() {
		cbFiltro.getItems().add("Login");
		cbFiltro.getSelectionModel().selectFirst();
		cbStatusPesquisa.getItems().addAll("Qualquer", "Ativo", "Inativo");
		cbStatusPesquisa.getSelectionModel().select("Ativo");
		
		
		cbFiltro.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (cbFiltro.getSelectionModel().getSelectedIndex() != -1) {
					filtrar();
				}
			}
		});
		cbStatusPesquisa.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (cbFiltro.getSelectionModel().getSelectedIndex() != -1) {
					filtrar();
				}
			}
		});
		cbNivel.getItems().addAll(FXCollections.observableArrayList(Usuario.UsuarioNivel.values()));
		
	}

	@SuppressWarnings("rawtypes")
	private void desbloquear(boolean value) {
		ObservableList<Node> nodes = pnCadastro.getChildren();
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setEditable(value);
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setEditable(value);
			} else if (n instanceof MaskTextField) {
				((MaskTextField) n).setEditable(value);
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
	void editar(ActionEvent event) {
		if (!txCodigo.getText().equals("")) {
			desbloquear(true);
			//txLogin.setEditable(false);
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
				try {
					loadFactory();
					usuarios = new UsuariosImp(getManager());
					usuario = usuarios.findById(Long.parseLong(txCodigo.getText()));
					tbUsuarios.getItems().remove(usuario);
					tabPane.getSelectionModel().select(tabCadastro);
					alert.setAlertType(Alert.AlertType.INFORMATION);
					alert.setContentText("Registro excluido com sucesso!");
					alert.showAndWait();
					limparTela();
					desbloquear(false);
					usuario = null;
				} catch (Exception e) {
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText("Falha ao excluir o registro");
					alert.setContentText("Houve um erro ao tentar remover o registro!\n" + e);
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
			String s = cbStatusPesquisa.getValue();
			int situacao = s.equals("Ativo") ? 0 : (s.equals("Inativo") ? 1 : -1);
			loadFactory();
			usuarios = new UsuariosImp(getManager());
			List<Usuario> lista = usuarios.filtrar(txPesquisa.getText().trim(), situacao, "login");
			tbUsuarios.getItems().clear();
			tbUsuarios.getItems().addAll(FXCollections.observableArrayList(lista));
		}catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setAlertType(Alert.AlertType.ERROR);
			alert.setContentText("Falaha ao filtrar os registros!\n"+e);
			alert.showAndWait();
		}finally {
			close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void limparTela() {
		ObservableList<Node> nodes = pnCadastro.getChildren();
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setText("");
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setText("");
			} else if (n instanceof MaskTextField) {
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
	}

	@FXML
	void novo(ActionEvent event) {
		limparTela();
		desbloquear(true);
		txLogin.setEditable(true);
		tabPane.getSelectionModel().select(tabCadastro);
		this.usuario = null;
	}

	@FXML
	void pesquisar(KeyEvent event) {
		filtrar();
	}

	void preencherFormulario(Usuario usuario) {
		txCodigo.setText(String.valueOf(usuario.getId()));
		txCriacao.setText(usuario.getCriadoEm() == null ? "" : new SimpleDateFormat("dd/MM/yyyy")
				.format(usuario.getCriadoEm().getTime()));
		txNome.setText(usuario.getNome());
		txLogin.setText(usuario.getLogin());
		txEmail.setText(usuario.getPessoaFisica().getEmail());
		txCelular.setPlainText(usuario.getPessoaFisica().getCelular());
		cbNivel.setValue(usuario.getUsuarioNivel() == null ? null : usuario.getUsuarioNivel());
		ckDesabilitar.setSelected(usuario.getInativo());
		this.usuario = usuario;
	}

	@FXML
	void salvar(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		boolean validarLogin = false;
		try {
			loadFactory();
			usuarios = new UsuariosImp(getManager());
			if (txCodigo.getText().equals("")) {
				usuario = new Usuario();
				usuario.setCriadoEm(Calendar.getInstance());
				usuario.setCriadoPor(UserSession.getInstance().getUsuario());
				validarLogin = validarLogin(txLogin.getText());
				if (!validarLogin)
					return;
				else
					validarLogin = validarSenha();
			} else {
				usuario.setId(Long.parseLong(txCodigo.getText()));
				if (txSenha.getText().trim().equals(""))
					validarLogin = true;
				else
					validarLogin = validarSenha();
			}
			usuario.setNome(txNome.getText());
			usuario.setLogin(txLogin.getText().trim());
			usuario.setInativo(ckDesabilitar.isSelected());
			PfPj pessoaFisica = new PfPj();
			pessoaFisica.setEmail(txEmail.getText());
			pessoaFisica.setCelular(txCelular.getPlainText());
			usuario.setPessoaFisica(pessoaFisica);
			usuario.setUsuarioNivel(cbNivel.getValue());
			if (validarLogin) {
				if (!txSenha.getText().trim().equals("")) {
					CriptografiaUtil cripto = new CriptografiaUtil();
					usuario.setSenha(cripto.criptografar(txSenha.getText()));
				}
				try {
					usuario = usuarios.save(usuario);
					txCodigo.setText(String.valueOf(usuario.getId()));
					desbloquear(false);
					txSenha.setText("");
					txConfirmarSenha.setText("");
					txLogin.setEditable(false);

				} catch (Exception e) {
					alert.setHeaderText("Falha ao salvar o registro");
					alert.setContentText("Houve um erro ao tentar salvar o registro!\n" + e);
					alert.showAndWait();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		filtrar();
	}

	@SuppressWarnings("unchecked")
	void tabela() {
		TableColumn<Usuario, Number> colunaId = new TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaId.setPrefWidth(40);
		TableColumn<Usuario, String> colunaLogin = new TableColumn<>("Login");
		colunaLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		TableColumn<Usuario, String> colunaNome = new TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		TableColumn<Usuario, Boolean> colunaDescricao = new TableColumn<>("Status");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("inativo"));
		colunaDescricao.setCellFactory((TableColumn<Usuario, Boolean> param) -> {
			final TableCell<Usuario, Boolean> cell = new TableCell<Usuario, Boolean>() {
				@Override
				protected void updateItem(Boolean item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						if (item)
							setText("Inativo");
						else
							setText("Ativo");
					}
				}
			};
			return cell;
		});
		TableColumn<Usuario, String> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
		colunaEditar.setCellFactory((TableColumn<Usuario, String> param) -> {
			final TableCell<Usuario, String> cell = new TableCell<Usuario, String>() {
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
							tabPane.getSelectionModel().select(tabCadastro);
							desbloquear(true);
							limparTela();
							preencherFormulario(tbUsuarios.getItems().get(getIndex()));
							//txLogin.setEditable(false);
						});
						setGraphic(button);
						setText(null);
					}
				}
			};
			return cell;
		});
		tbUsuarios.getColumns().addAll(colunaId, colunaLogin, colunaNome, colunaDescricao, colunaEditar);
	}

	private boolean validarLogin(String login){
        Usuario u = usuarios.findByLogin(login);
        if(u!=null) {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informação incompleta");
            alert.setHeaderText("Já existe alguem usando esse login");
            alert.setContentText(u.getNome()+" já utiliza esse login");
            alert.showAndWait();
            return false;
        }
        else
        	return true;
    }

	private boolean validarSenha() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Informação incompleta");
		alert.setContentText(null);
		if (txSenha.getText().trim().equals("")) {
			alert.setHeaderText("Senha não informada!");
			alert.showAndWait();
			return false;
		} else {
			if (txSenha.getText().trim().equals(txConfirmarSenha.getText().trim()))
				return true;
			else {
				alert.setHeaderText("Senhas não conferem!");
				alert.showAndWait();
				return false;
			}
		}
	}
}
