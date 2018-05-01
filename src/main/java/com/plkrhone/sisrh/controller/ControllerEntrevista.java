package com.plkrhone.sisrh.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

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
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaFormulario;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaFormularioTexto;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaPerfil;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaPerfilTexto;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistaAnaliseImp;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistaFormulariosImpl;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistaPerfisTextosImpl;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistasImp;
import com.plkrhone.sisrh.util.UserSession;
import com.plkrhone.sisrh.util.office.FileOfficeEnum;
import com.plkrhone.sisrh.util.office.OfficeEditor;
import com.plkrhone.sisrh.util.office.OfficeEditorProducer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

	private AnuncioEntrevistaAnalise entrevista;
	private AnuncioEntrevista anuncioEntrevista;
	private Stage stage;
	private AnuncioEntrevistaAnaliseImp entrevistas;
	private AnuncioEntrevistasImp anunciosEntrevistas;
		
	private AnuncioEntrevistaFormulariosImpl formsImpl;
	private AnuncioEntrevistaPerfisTextosImpl perfisImpl;
	
	private Map<String, Object[]> mapPerfil = new HashMap<>();
	private Map<String, Object[]> mapFormulario = new HashMap<>();
	private String CHAVE_PERFIL="P";
	private String CHAVE_FORMULARIO="F";
	
	private OfficeEditor officeJob = OfficeEditorProducer.newConfig(FileOfficeEnum.entrevista.getDescricao());
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadFactory();
			
			perfisImpl = new AnuncioEntrevistaPerfisTextosImpl(getManager());
			formsImpl = new AnuncioEntrevistaFormulariosImpl(getManager());
			
			List<AnuncioEntrevistaPerfilTexto> perfis = perfisImpl.findByInativo(false);
			perfis.forEach(c->{
				criarCheckBoxNoPerfil(c);
			});
			
			List<AnuncioEntrevistaFormularioTexto> textos = formsImpl.findByInativo(false);
			textos.forEach(c->{//montar formulario
				criarTextAreaForm(c,textos.size());
			});;
			
		}catch(Exception e) {
			
		}finally {
			close();
		}
	}
	
	private void criarCheckBoxNoPerfil(AnuncioEntrevistaPerfilTexto c) {
		JFXCheckBox checkBox = new JFXCheckBox();
		checkBox.setId(CHAVE_PERFIL+c.getId().longValue());
		checkBox.setText(c.getNome());
		checkBox.setFont(Font.font("System Bold", FontWeight.BOLD,12));
		checkBox.setPadding(new Insets(0,10,20,0));
		
		checkBox.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
			}
		});
		mapPerfil.put(checkBox.getId(), new Object[] {c,checkBox});//armazenando perfis em um mapa
		pnPerfil.getChildren().add(checkBox);		
	}
	private void criarTextAreaForm(AnuncioEntrevistaFormularioTexto c, int size) {
		Tab tab = new Tab(""+c.getSequencia());
		AnchorPane panel = new AnchorPane();
		VBox vbox = new VBox();
		Label label = new Label(c.getPergunta());
		label.setPadding(new Insets(30,10,30,0));
		label.setFont(Font.font("System Bold", FontWeight.BOLD, 16));
		
		JFXTextArea textArea = new JFXTextArea(c.getDescricao());
		textArea.setPadding(new Insets(0,10,50,0));
		textArea.setFont(Font.font("System Bold", 12));
		textArea.setMinHeight(100);
		textArea.setEditable(false);
		
		Label label2 = new Label("Descreva suas anotações abaixo:");
		label2.setPadding(new Insets(0,10,20,0));
		label2.setFont(Font.font("System Bold", FontWeight.BOLD, 16));
		
		JFXTextArea textAreaValue = new JFXTextArea();
		textAreaValue.setPadding(new Insets(0,10,20,0));
		textAreaValue.setFont(Font.font("System Bold", FontWeight.BOLD, 16));
		
		textAreaValue.setId(CHAVE_FORMULARIO+c.getSequencia());
		
		mapFormulario.put(textAreaValue.getId(),new Object[] {c,textAreaValue});
		
		ButtonBar bar = new ButtonBar();
		if(c.getSequencia()>1) {
			JFXButton button = new JFXButton("<");
			button.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					formRequisicaoRetroceder(event);
				}
			});
			bar.getButtons().add(button);
		}
		if(c.getSequencia()<size) {
			JFXButton button = new JFXButton(">");
			button.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					formRequisicaoAvancar(event);
				}
			});
			bar.getButtons().add(button);
		}
		vbox.getChildren().addAll(label,textArea,label2,textAreaValue,bar);
		tab.setContent(vbox);
		tabPane.getTabs().add(tab);
		
	}
	public void iniciar(AnuncioEntrevista  ae,Stage stage) {
		this.anuncioEntrevista=ae;
		this.stage=stage;
		if(ae.getEntrevista()!=null) {
			try {
				loadFactory();
				entrevistas = new AnuncioEntrevistaAnaliseImp(getManager());
				entrevista = entrevistas.findById(ae.getEntrevista().getId());
				preencherFormulario(entrevista);
			}catch(Exception e) {
				e.printStackTrace();
			}finally{
				close();
			}
		
		}
	}
	@FXML
	void excluir(ActionEvent event) {
		if (entrevista != null && entrevista.getId() != null) {
			try {
				loadFactory();
				entrevistas = new AnuncioEntrevistaAnaliseImp(getManager());
				entrevista = entrevistas.findById(entrevista.getId());
				entrevistas.remove(entrevista);
				this.entrevista = null;
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
		Set<AnuncioEntrevistaFormulario> form = entrevista.getFormularios();
		form.forEach(o->{
			Object[] object = mapFormulario.get(CHAVE_FORMULARIO+o.getFormulario().getId());
			if(object == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Um formulario foi removido");
				alert.setContentText("Um formulario foi desativado");
			}
			if(object[1] instanceof JFXTextArea) {
				((JFXTextArea)object[1]).setText(o.getDescricao());
			}
		});
		
		Set<AnuncioEntrevistaPerfil> perfil = entrevista.getPerfis();
		perfil.forEach(o->{
			Object[] object = mapFormulario.get(CHAVE_FORMULARIO+o.getPerfil().getId());
			if(object[1] instanceof JFXCheckBox) {
				((JFXCheckBox)object[1]).setSelected(true);
			}
		});
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
		if(this.entrevista==null) {
			entrevista = new AnuncioEntrevistaAnalise();
			entrevista.setCriadoEm(Calendar.getInstance());
			entrevista.setCriadoPor(UserSession.getInstance().getUsuario());
		}
		Set<AnuncioEntrevistaPerfil> anuPerf = entrevista.getPerfis();
		mapPerfil.values().forEach(c->{
			if(c[1] instanceof JFXCheckBox) {
				System.out.println(((JFXCheckBox)c[1]).getId()+"-> selected ? "+((JFXCheckBox)c[1]).isSelected());
			}
		});		
		
		entrevista.setAnuncioEntrevista(this.anuncioEntrevista);
		
		this.anuncioEntrevista.setEntrevista(entrevista);
		try {
			loadFactory();
			//anunciosEntrevistas = new AnuncioEntrevistasImp(getManager());
			//anunciosEntrevistas.save(this.anuncioEntrevista);
			//this.stage.close();	
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
	public AnuncioEntrevistaAnalise getEntrevista() {
		return this.entrevista;
	}

}
