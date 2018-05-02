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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAnalise;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaFormulario;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaFormularioTexto;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaPerfilTexto;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistaAnaliseImp;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistaFormulariosImpl;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistaPerfisTextosImpl;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistasImp;
import com.plkrhone.sisrh.repository.helper.AnunciosImp;
import com.plkrhone.sisrh.util.UserSession;
import com.plkrhone.sisrh.util.office.FileOfficeEnum;
import com.plkrhone.sisrh.util.office.OfficeEditor;
import com.plkrhone.sisrh.util.office.OfficeEditorProducer;

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
		mapPerfil.put(checkBox.getId(), new Object[] {c,checkBox});//armazenando perfis em um mapa
		pnPerfil.getChildren().add(checkBox);		
	}
	private void criarTextAreaForm(AnuncioEntrevistaFormularioTexto c, int size) {
		Tab tab = new Tab(""+c.getSequencia());
		//AnchorPane panel = new AnchorPane();
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

		textAreaValue.setId(CHAVE_FORMULARIO+c.getId().longValue());

		mapFormulario.put(textAreaValue.getId(),new Object[] {c,textAreaValue});

		ButtonBar bar = new ButtonBar();
		if(c.getSequencia()>1) {
			JFXButton button = new JFXButton("<");
			button.getStyleClass().add("btJFXDefault");
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
			button.getStyleClass().add("btJFXDefault");
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
				entrevista = entrevistas.findById(ae.getEntrevista());
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
		entrevista.getFormularios().forEach(o->{
			Object[] object = mapFormulario.get(CHAVE_FORMULARIO+o.getFormulario().getId().longValue());
			if(object == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Um formulario foi desativado");
				alert.setContentText("Um formulario foi desativado, será mostrado na impressão");
				alert.showAndWait();
			}
			if(object[1] instanceof JFXTextArea) {
				((JFXTextArea)object[1]).setText(o.getDescricao());
			}
		});
		entrevista.getPerfis().forEach(o->{
			Object[] object = mapPerfil.get(CHAVE_PERFIL+o.getId().longValue());
			if(object[1] instanceof JFXCheckBox) ((JFXCheckBox)object[1]).setSelected(true);
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
		try {
			loadFactory();

			if(this.entrevista==null) {
				entrevista = new AnuncioEntrevistaAnalise();
				entrevista.setCriadoEm(Calendar.getInstance());
				entrevista.setCriadoPor(UserSession.getInstance().getUsuario());
			}
			Set<AnuncioEntrevistaFormulario> formularios = entrevista.getFormularios();
			Set<AnuncioEntrevistaPerfilTexto> perfis = entrevista.getPerfis();

			mapPerfil.values().forEach(c->{
				if(c[1] instanceof JFXCheckBox) {
					AnuncioEntrevistaPerfilTexto p = (AnuncioEntrevistaPerfilTexto)c[0];
					final boolean selected = ((JFXCheckBox)c[1]).isSelected();
					if(selected) {
						perfis.add(p);
					}
					else if(!selected){
						perfis.remove(p);			
					}

					//	System.out.println(((JFXCheckBox)c[1]).getId()+"-> selected ? "+((JFXCheckBox)c[1]).isSelected());
				}
			});		
			mapFormulario.values().forEach(c->{
				if(c[1] instanceof JFXTextArea) {
					AnuncioEntrevistaFormularioTexto aeft = (AnuncioEntrevistaFormularioTexto)c[0];
					String texto = ((JFXTextArea)c[1]).getText().trim();
					Optional<AnuncioEntrevistaFormulario> key = formularios.stream().filter(d->d.getFormulario().getId().longValue()==aeft.getId().longValue()).findAny();
					if(!key.isPresent() && !texto.equals("")) {
						AnuncioEntrevistaFormulario f = new AnuncioEntrevistaFormulario();
						f.setDescricao(texto);
						f.setFormulario(aeft);
						formularios.add(f);
						//System.out.println("Inserindo novo");
					}
					else if(key.isPresent() && !key.get().getDescricao().equals(texto)) {
						key.get().setDescricao(texto);
						/*formularios.remove(key.get());
					AnuncioEntrevistaFormulario f = new AnuncioEntrevistaFormulario();
					f.setDescricao(texto);
					f.setFormulario(aeft);
					formularios.add(f);
						 */
						//System.out.println("Atualizando registro");
					}
					else {
						//System.out.println("Nao fiz nada");
					}
					//	System.out.println(a.getPergunta()+":sequencia"+a.getSequencia()+"-> text ? "+((JFXTextArea)c[1]).getText());
				}
			});
			entrevista.setPerfis(perfis);
			entrevista.setFormularios(formularios);

			entrevista.setAnuncioEntrevista(this.anuncioEntrevista);
			
			entrevistas = new AnuncioEntrevistaAnaliseImp(getManager());
			//entrevista  = entrevistas.save(entrevista);
				
			anunciosEntrevistas = new AnuncioEntrevistasImp(getManager());
			this.anuncioEntrevista = anunciosEntrevistas.findById(this.anuncioEntrevista.getId());
			this.anuncioEntrevista.setEntrevista(entrevista);
			anunciosEntrevistas.save(this.anuncioEntrevista);
			
			System.out.println(anuncioEntrevista.getEntrevista()==null?"vazio":"entrevista id "+anuncioEntrevista.getEntrevista().getId());

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Sucesso");
			alert.setHeaderText("Salvo com sucesso!");
			alert.showAndWait();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	@FXML
	void visualizarFormulario(ActionEvent event) {
		try{
			Map<String,String> map = new HashMap<>();
			String lineSeparator = "\u000b";

			String candidatoNome="";
			String candidatoIdade="";
			String candidatoSexo="";

			if(anuncioEntrevista!=null && anuncioEntrevista.getCandidato()!=null) {
				Candidato can = anuncioEntrevista.getCandidato();
				candidatoNome = can.getNome();
				candidatoIdade = String.valueOf(can.getIdade());
				candidatoSexo = can.getSexo();
			}
			map.put("{candidato.nome}", candidatoNome);
			map.put("{candidato.idade}", candidatoIdade);
			map.put("{candidato.sexo}", candidatoSexo);

			StringBuilder perfil = new StringBuilder();

			int i = 1;
			for(Object[] c : mapPerfil.values()){
				if(c[1] instanceof JFXCheckBox) {
					AnuncioEntrevistaPerfilTexto  a = (AnuncioEntrevistaPerfilTexto)c[0];
					JFXCheckBox check = (JFXCheckBox)c[1];
					String value = "("+(check.isSelected()?"X":"  ")+")"+a.getNome();
					perfil.append(value);
					if(i % 2==0) {
						perfil.append(lineSeparator);
					}
					else 
						perfil.append("\t\t");
					i++;
				}
			}

			map.put("{perfil}", perfil.toString());
			mapFormulario.values().forEach(c->{
				if(c[1] instanceof JFXTextArea) {
					AnuncioEntrevistaFormularioTexto a = (AnuncioEntrevistaFormularioTexto)c[0];
					map.put("{pergunta"+a.getSequencia()+"}", a.getPergunta());
					map.put("{descricao"+a.getSequencia()+"}", a.getDescricao());
					map.put("{resposta"+a.getSequencia()+"}", ((JFXTextArea)c[1]).getText().trim());
				}
			});
			StringBuilder extra = new StringBuilder();
			if(entrevista!=null) {
				entrevista.getFormularios().forEach(c->{
					if(c.getFormulario().isInativo()) {
						extra.append(c.getFormulario().getPergunta());
						extra.append(lineSeparator);
						extra.append(c.getFormulario().getDescricao());
						extra.append(lineSeparator);
						extra.append(c.getDescricao());
						extra.append(lineSeparator);
						extra.append(lineSeparator);
					}
				});
			}
			map.put("{extras}", extra.toString());
			File f = officeJob.edit(map, new File(FileOfficeEnum.entrevista.getDescricao()));
			officeJob.openFile(f);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public AnuncioEntrevistaAnalise getEntrevista() {
		return this.entrevista;
	}
}
