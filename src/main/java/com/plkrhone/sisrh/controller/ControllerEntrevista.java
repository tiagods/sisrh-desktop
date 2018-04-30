package com.plkrhone.sisrh.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAnalise;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaFormularioTexto;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaPerfilTexto;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistaAnaliseImp;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistasImp;
import com.plkrhone.sisrh.util.UserSession;
import com.plkrhone.sisrh.util.office.FileOfficeEnum;
import com.plkrhone.sisrh.util.office.OfficeEditor;
import com.plkrhone.sisrh.util.office.OfficeEditorProducer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Prolink on 11/08/2017.
 */
public class ControllerEntrevista extends PersistenciaController implements Initializable {
    @FXML
    private AnchorPane pnTelaInicial;
    @FXML
    private FlowPane pnPerfil;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private JFXButton btSalvar;
    @FXML
    private JFXButton btExcluir;

	private AnuncioEntrevistaAnalise e;
	private AnuncioEntrevista anuncioEntrevista;
	private Stage stage;
	private AnuncioEntrevistaAnaliseImp entrevistas;
	private AnuncioEntrevistasImp anunciosEntrevistas;
	
	private AnuncioEntrevistaPerfilTexto perfilTexto;
	private AnuncioEntrevistaFormularioTexto formTexto;
	
	private OfficeEditor officeJob = OfficeEditorProducer.newConfig(FileOfficeEnum.entrevista.getDescricao());
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	public void iniciar(AnuncioEntrevista  ae,Stage stage) {
		this.anuncioEntrevista=ae;
		this.stage=stage;
		if(ae.getEntrevista()!=null) preencherFormulario(ae.getEntrevista());
	}
	@FXML
	void excluir(ActionEvent event) {
		if (e != null && e.getId() != null) {
			try {
				loadFactory();
				entrevistas = new AnuncioEntrevistaAnaliseImp(getManager());
				entrevistas.remove(e);
				this.e = null;
				stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
	}

	@FXML
	private void formRequisicaoAvancar(ActionEvent event) {
		Tab t = proximaTabVagas(true);
		if (t != null)
			tabPane.getSelectionModel().select(t);
	}

	@FXML
	private void formRequisicaoRetroceder(ActionEvent event) {
		Tab t = proximaTabVagas(false);
		if (t != null)
			tabPane.getSelectionModel().select(t);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void limparTela(ObservableList<Node> nodes) {
		nodes.stream().forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setText("");
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox) n).setValue(null);
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
			}
		});

	}
	void preencherFormulario(AnuncioEntrevistaAnalise entrevista) {
		ckDesenvoltura.setSelected(entrevista.getBoaDesenvoltura() == 1);
		ckComunicacao.setSelected(entrevista.getBoaComunicacao() == 1);
		ckAtualizar.setSelected(entrevista.getCapaCidadeDeAtualizacao() == 1);
		ckDeterminacao.setSelected(entrevista.getDeterminacao() == 1);
		ckElegancia.setSelected(entrevista.getElegancia() == 1);
		ckLideranca.setSelected(entrevista.getLideranca() == 1);
		ckEtica.setSelected(entrevista.getEtica() == 1);
		ckPontualidade.setSelected(entrevista.getPontualidade() == 1);
		ckProfissionalismo.setSelected(entrevista.getProfissionalismo() == 1);
		ckSimpatia.setSelected(entrevista.getSimpatia() == 1);

		tx1.setText(entrevista.getD1());
		tx2.setText(entrevista.getD2());
		tx3.setText(entrevista.getD3());
		tx4.setText(entrevista.getD4());
		tx5.setText(entrevista.getD5());
		tx6.setText(entrevista.getD6());
		tx7.setText(entrevista.getD7());
		tx8.setText(entrevista.getD8());
		tx9.setText(entrevista.getD9());
		tx10.setText(entrevista.getD10());
		tx11.setText(entrevista.getD11());
		tx12.setText(entrevista.getD12());
		tx13.setText(entrevista.getD13());
		tx14.setText(entrevista.getD14());
		tx15.setText(entrevista.getD15());
		tx16.setText(entrevista.getD16());

	}

	private Tab proximaTabVagas(boolean avancar) {
		int t = tabPane.getSelectionModel().getSelectedIndex();
		if (avancar) {
			return tabPane.getTabs().get(++t);
		} else {
			return tabPane.getTabs().get(--t);
		}
	}
	@FXML
	void salvar(ActionEvent event) {
		AnuncioEntrevistaAnalise entrevista;
		if(this.e==null) {
			entrevista = new AnuncioEntrevistaAnalise();
			entrevista.setCriadoEm(Calendar.getInstance());
			entrevista.setCriadoPor(UserSession.getInstance().getUsuario());
		}
		else
			entrevista = this.e;
			
		entrevista.setAnuncioEntrevista(this.anuncioEntrevista);
		entrevista.setBoaDesenvoltura(ckDesenvoltura.isSelected() ? 1 : 0);
		entrevista.setBoaComunicacao(ckComunicacao.isSelected() ? 1 : 0);
		entrevista.setCapaCidadeDeAtualizacao(ckAtualizar.isSelected() ? 1 : 0);
		entrevista.setDeterminacao(ckDeterminacao.isSelected() ? 1 : 0);
		entrevista.setElegancia(ckElegancia.isSelected() ? 1 : 0);
		entrevista.setLideranca(ckLideranca.isSelected() ? 1 : 0);
		entrevista.setEtica(ckEtica.isSelected() ? 1 : 0);
		entrevista.setPontualidade(ckPontualidade.isSelected() ? 1 : 0);
		entrevista.setProfissionalismo(ckProfissionalismo.isSelected() ? 1 : 0);
		entrevista.setSimpatia(ckSimpatia.isSelected() ? 1 : 0);

		entrevista.setD1(tx1.getText());
		entrevista.setD2(tx2.getText());
		entrevista.setD3(tx3.getText());
		entrevista.setD4(tx4.getText());
		entrevista.setD5(tx5.getText());
		entrevista.setD6(tx6.getText());
		entrevista.setD7(tx7.getText());
		entrevista.setD8(tx8.getText());
		entrevista.setD9(tx9.getText());
		entrevista.setD10(tx10.getText());
		entrevista.setD11(tx11.getText());
		entrevista.setD12(tx12.getText());
		entrevista.setD13(tx13.getText());
		entrevista.setD14(tx14.getText());
		entrevista.setD15(tx15.getText());
		entrevista.setD16(tx16.getText());
		this.anuncioEntrevista.setEntrevista(entrevista);
		this.e = entrevista;
		try {
			loadFactory();
			anunciosEntrevistas = new AnuncioEntrevistasImp(getManager());
			anunciosEntrevistas.save(this.anuncioEntrevista);
			this.stage.close();	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	@FXML
	void visualizarFormulario(ActionEvent event) {
		try{
			File f = officeJob.edit(new HashMap<>(), new File(FileOfficeEnum.entrevista.getDescricao()));
			officeJob.openFile(f);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Entrevista getEntrevista() {
		return this.e;
	}

}
