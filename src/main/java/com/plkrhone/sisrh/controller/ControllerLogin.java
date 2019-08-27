package com.plkrhone.sisrh.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.config.init.VersaoSistema;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.plkrhone.sisrh.config.init.PaisesConfig;
import com.plkrhone.sisrh.model.Usuario;
import com.plkrhone.sisrh.repository.helper.UsuariosImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.CriptografiaUtil;
import com.plkrhone.sisrh.view.MenuView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ControllerLogin extends UtilsController implements Initializable{
	@FXML
	private JFXPasswordField txSenha;
	@FXML
	private JFXComboBox<Usuario> cbNome;
	@FXML
	private JFXButton btnEntrar;
	@FXML
	private JFXButton btnCancelar;
	@FXML
	private Label lbVersao;
	@FXML
	private Label lbBanco;

	private Stage stage;

	private UsuarioLogado logado = UsuarioLogado.getInstance();

	private VersaoSistema versao = VersaoSistema.getInstance();

	public ControllerLogin(Stage stage){
		this.stage=stage;
	}

	private static Logger log = LoggerFactory.getLogger(ControllerLogin.class);
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		long tempoInicial = System.currentTimeMillis();
		try {
			String detalhes = "Versão do Sistema: "+versao.getVersao()+" de "+versao.getDate();
			lbVersao.setText(detalhes);
			lbBanco.setText("Versao do Banco:" +versao.getVersaoBanco());

			loadFactory();
			UsuariosImp usuarios = new UsuariosImp(getManager());
			List<Usuario> contas = usuarios.filtrar(null, 0, "nome");
			cbNome.setConverter(new StringConverter<Usuario>() {
				@Override
				public String toString(Usuario object) {
					return object.getLogin();
				}

				@Override
				public Usuario fromString(String string) {return null;}
				
			});
			cbNome.getItems().addAll(contas);
			cbNome.getSelectionModel().selectFirst();
			if(!logado.lastLogin().equals("")){
				Optional<Usuario> result = contas.stream().filter(c->c.getLogin().equals(logado.lastLogin())).findFirst();
				if(result.isPresent()) cbNome.setValue(result.get());
			}
			Platform.runLater(() -> {
				txSenha.setFocusTraversable(true);
				txSenha.requestFocus();
			});
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		new ComboBoxAutoCompleteUtil<>(cbNome);
		Runnable run = () -> PaisesConfig.getInstance();
		new Thread(run).start();
		long tempoFinal = System.currentTimeMillis();
		if (log.isDebugEnabled()) {
			log.debug("Tela " + getClass().getSimpleName().replace("Controller", "") + " abriu em : "
					+ (tempoFinal - tempoInicial) + " ms");
		}


	}

	@FXML
	public void enterAcionado(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			logon();
		}
	}
	@FXML
	public void entrar(ActionEvent event) {
		logon();
	}
	private void logon() {
		String mensagem;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Erro de Acesso");
		if (cbNome.getValue() == null) {
			mensagem = "Usuario ou senha em branco!";
			alert.setContentText(mensagem);
			alert.showAndWait();
			return;
		} else {
			try {
				loadFactory();
				UsuariosImp usuarios = new UsuariosImp(getManager());
				Usuario usuario = usuarios.findByLoginAndSenha(
						cbNome.getValue().getLogin(), 
						new CriptografiaUtil().criptografar(txSenha.getText().trim())
						);
				if (usuario != null) {
					UsuarioLogado.getInstance().setUsuario(usuario);
					MenuView menu = new MenuView();
					menu.start(new Stage());
					stage.close();
					if(log.isDebugEnabled())
						log.debug("Usuario valido");
				} else {
					mensagem = "Usuario ou senha inválidos!";
					alert.setContentText(mensagem);
					alert.showAndWait();
					if(log.isDebugEnabled())
						log.debug("Usuario e senha incorretors");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
		}
	}
	@FXML
	void sair(ActionEvent event) {
		System.exit(0);
	}

}
