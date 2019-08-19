package com.plkrhone.sisrh.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.model.avaliacao.AvaliacaoCondicao;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import org.fxutils.maskedtextfield.MaskTextField;
import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.Avaliacao;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAvaliacao;
import com.plkrhone.sisrh.repository.helper.AnuncioEntrevistasAvaliacaoImp;
import com.plkrhone.sisrh.repository.helper.AvaliacoesImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.storage.PathStorageEnum;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ControllerEntrevistaAvaliacao extends UtilsController implements Initializable{
	@FXML
	private AnchorPane pnCadastro;
    @FXML
    private JFXComboBox<Avaliacao.AvaliacaoTipo> cbTipoAvaliacao;

    @FXML
    private JFXTextField txPontuacao;

    @FXML
    private JFXTextField txFormulario;

    @FXML
    private JFXButton btAnexar;

    @FXML
    private JFXButton btnRemover;

    @FXML
    private JFXButton btVisualizar;

    @FXML
    private JFXTextArea txGabarito;
	@FXML
    private JFXTextArea txDescricao;

	@FXML
    private JFXComboBox<Avaliacao> cbAvaliacao;
    
    @FXML
    private TableView<AnuncioEntrevistaAvaliacao> tbPrincipal;
    @FXML
    private JFXTextField txLocation;
    @FXML
    private JFXTextField txCodigo;
    @FXML
    private Label txPontuacaoMaxima;
    
    private AnuncioEntrevista anuncioEntrevista;
    private Set<AnuncioEntrevistaAvaliacao> aeAvaliacao;
    private AvaliacoesImp avaliacoes;
    private AnuncioEntrevistasAvaliacaoImp aeAvaliacoes;
    private AnuncioEntrevistaAvaliacao aeAva;
    private Stage stage;
    private Storage storage = StorageProducer.newConfig();
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadFactory();
			combos();
			tabela();
			aeAvaliacoes = new AnuncioEntrevistasAvaliacaoImp(getManager());
			tbPrincipal.getItems().addAll(aeAvaliacoes.findByAnuncioEntrevista(anuncioEntrevista));
			cbTipoAvaliacao.setDisable(true);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
	}
    public void iniciar(Set<AnuncioEntrevistaAvaliacao> aE, AnuncioEntrevista anuncioEntrevista) {
    	this.anuncioEntrevista=anuncioEntrevista;
    	this.aeAvaliacao = aE;
    	tbPrincipal.getItems().setAll(aE);
    }
    @FXML
    void adicionarAvaliacao(ActionEvent event) {
    	try {
			loadFactory();
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.AVALIACAO_PESQUISA);
			AvaliacaoCadastroController controller = new AvaliacaoCadastroController(stage,null);
			loader.setController(controller);
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
			stage.setOnHiding(event1 -> {
                if(event1.getEventType()==WindowEvent.WINDOW_CLOSE_REQUEST) {
                    try {
                        loadFactory();
                        avaliacoes = new AvaliacoesImp(getManager());
                        cbAvaliacao.getItems().clear();
                        cbAvaliacao.getItems().addAll(avaliacoes.getAll());
                    }catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        close();
                    }
                }
            });
    	}catch(IOException e) {
    	}
    	finally {
    		close();
		}
    }
    @FXML
    void anexarFormulario(ActionEvent event) {
    	Set<FileChooser.ExtensionFilter> filters = new HashSet<>();
		filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc","*.pdf","*.docx"));
		File file = storage.carregarArquivo(new Stage(), filters);
		if (file != null) {
			String novoNome = storage.gerarNome(file,PathStorageEnum.AVALIACAO_APLICADA.getDescricao());
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
    private FXMLLoader carregarFxmlLoader(String fxmlName) {
		URL url = getClass().getResource("/fxml/"+fxmlName+".fxml");
		FXMLLoader loader = new FXMLLoader(url);
		return loader;
    
    }
    private Stage carregarStage(FXMLLoader loader, String title) throws IOException{
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(title);
		return stage;
	}
	private void desbloquear(boolean value,ObservableList<Node> nodes) {
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
	private void combos(){
		avaliacoes = new AvaliacoesImp(getManager());
		//cbAvaliacao.getItems().add(null);
		cbAvaliacao.getItems().addAll(avaliacoes.getAll());
		
		cbTipoAvaliacao.getItems().add(null);
		cbTipoAvaliacao.getItems().addAll(Avaliacao.AvaliacaoTipo.values());
		new ComboBoxAutoCompleteUtil<>(cbAvaliacao);
		
		cbAvaliacao.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                cbTipoAvaliacao.setValue(newValue.getTipo());
                txPontuacao.setText("0");
                if(newValue.getTipo()==Avaliacao.AvaliacaoTipo.DISSERTATIVA) {
					txPontuacaoMaxima.setText("0.00");
                }
                else {
                    txPontuacaoMaxima.setText(String.valueOf(newValue.getPontuacao().doubleValue()));
                }
                txDescricao.setDisable(newValue.getTipo()==Avaliacao.AvaliacaoTipo.CONDICIONAL);
                txGabarito.setText(newValue.getGabarito());
            }
        });
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void limparTela(ObservableList<Node> nodes) {
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
				//((TableView) n).getItems().clear();
			} else if (n instanceof JFXCheckBox) {
				((JFXCheckBox) n).setSelected(false);
			} else if(n instanceof HBox){
				limparTela(((HBox)n).getChildren());
			} else if(n instanceof VBox){
				limparTela(((VBox)n).getChildren());
			}
		});
		txLocation.setText("");
		txCodigo.setText("");
	}
    @FXML
    void novo(ActionEvent event) {
    	limparTela(pnCadastro.getChildren());
		desbloquear(true,pnCadastro.getChildren());
		this.aeAva=null;
    }
    void preencherFormulario(AnuncioEntrevistaAvaliacao aeAva, int index) {
    	txCodigo.setText(aeAva.getId()==null?"":String.valueOf(aeAva.getId()));
    	txLocation.setText(String.valueOf(index));
    	cbAvaliacao.setValue(aeAva.getAvaliacao());
    	cbTipoAvaliacao.setValue(aeAva.getAvaliacaoTipo());
    	txPontuacao.setText(String.valueOf(aeAva.getPontuacao()));
    	txPontuacaoMaxima.setText(String.valueOf(aeAva.getPontuacaoMaxima()));
    	txFormulario.setText(aeAva.getFormulario()==null?"":aeAva.getFormulario());
    	txGabarito.setText(aeAva.getGabarito());
    	txDescricao.setText(aeAva.getDescricao());
    	this.aeAva=aeAva;
    }
    @FXML
    void remover(ActionEvent event) {
    	if(this.aeAva!=null) {
	    	try {
				loadFactory();
				aeAvaliacoes = new AnuncioEntrevistasAvaliacaoImp(getManager());
				AnuncioEntrevistaAvaliacao aeAva = aeAvaliacoes.findById(Long.parseLong(txCodigo.getText()));
				aeAvaliacoes.remove(aeAva);
				tbPrincipal.getItems().clear();
				tbPrincipal.getItems().addAll(aeAvaliacoes.findByAnuncioEntrevista(anuncioEntrevista));
				limparTela(pnCadastro.getChildren());

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Excluido com sucesso");
				alert.showAndWait();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
    	}
    }
    @FXML
    void removerDocumento(ActionEvent event) {
    	try{
			storage.delete(txFormulario.getText());
			txFormulario.setText("");
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (aeAva != null) {
			try {
				loadFactory();
				aeAvaliacoes = new AnuncioEntrevistasAvaliacaoImp(getManager());
				aeAva.setFormulario(txFormulario.getText());
				aeAvaliacoes.save(aeAva);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
    }
    @FXML
    void salvar(ActionEvent event) {
    	try {
			if(txPontuacao.getText().trim().equals("")) txPontuacao.setText("0.00");

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro");
			if(cbAvaliacao.getValue()==null) {
	    		alert.setContentText("Selecione uma avaliação antes de salvar");
	    		alert.showAndWait();
	    		return;
	    	}
			AnuncioEntrevistaAvaliacao ava = new AnuncioEntrevistaAvaliacao();
			if(txPontuacao.getText().trim().equals("")) {
	    		alert.setContentText("O campo pontuação é obrigatório mesmo se a avaliação for "+Avaliacao.AvaliacaoTipo.DISSERTATIVA);
	    		alert.showAndWait();
	    		return;
	    	}
			else{
				try {
					new BigDecimal(txPontuacao.getText().replace(",","."));
					ava.setPontuacao(new BigDecimal(txPontuacao.getText()));
				}catch (NumberFormatException nf){
					alert(Alert.AlertType.ERROR,"Erro","","Pontuação informada esta incorreta");
					return;
				}
			}
			ava.setAvaliacao(cbAvaliacao.getValue());
			ava.setAnuncioEntrevista(anuncioEntrevista);
			ava.setPontuacaoMaxima(new BigDecimal(txPontuacaoMaxima.getText()));
			ava.setGabarito(txGabarito.getText());
			ava.setDescricao(txDescricao.getText());
			ava.setAvaliacaoTipo(cbTipoAvaliacao.getValue());

			if (!txFormulario.getText().equals("") && !txFormulario.getText().startsWith(PathStorageEnum.AVALIACAO_APLICADA.getDescricao()+"/")) {
				try {
					storage.transferTo(txFormulario.getText(), PathStorageEnum.AVALIACAO_APLICADA.getDescricao()+"/"+txFormulario.getText());
					txFormulario.setText(PathStorageEnum.AVALIACAO_APLICADA.getDescricao()+"/" + txFormulario.getText());
					ava.setFormulario(txFormulario.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(txCodigo.getText().equals("")) {
	    		ava.setCriadoEm(Calendar.getInstance());
	    		ava.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
	    		tbPrincipal.getItems().add(ava);
	    	}
	    	else {
	    		ava.setCriadoEm(this.aeAva.getCriadoEm());
	    		ava.setCriadoPor(this.aeAva.getCriadoPor());
	    		ava.setId(Long.parseLong(txCodigo.getText()));
	    		tbPrincipal.getItems().set(Integer.parseInt(txLocation.getText()), ava);
	    	}
			loadFactory();
			if(cbTipoAvaliacao.getValue()== Avaliacao.AvaliacaoTipo.CONDICIONAL){
				avaliacoes = new AvaliacoesImp(getManager());
				Avaliacao a = cbAvaliacao.getValue();
				a = avaliacoes.findById(a.getId());

				double pont = ava.getPontuacao().doubleValue();
				Optional<AvaliacaoCondicao> result = a.getCondicoes().stream().filter(c->c.getDe()>= pont && c.getAte()>=pont).findAny();
				if(result.isPresent()) ava.setCondicao(result.get());
			}else
				ava.setCondicao(null);
			aeAvaliacoes = new AnuncioEntrevistasAvaliacaoImp(getManager());
			aeAvaliacoes.save(ava);
			tbPrincipal.getItems().clear();
			tbPrincipal.getItems().addAll(aeAvaliacoes.findByAnuncioEntrevista(anuncioEntrevista));
			limparTela(pnCadastro.getChildren());
			this.aeAva=null;
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Salvo com sucesso");
			alert.showAndWait();
    	}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
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
    @SuppressWarnings("unchecked")
	void tabela() {
    	TableColumn<AnuncioEntrevistaAvaliacao, Number> colunaId = new TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaId.setPrefWidth(40);

		TableColumn<AnuncioEntrevistaAvaliacao, Calendar> colunaCriadoEm = new TableColumn<>("Data");
		colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("criadoEm"));
		colunaCriadoEm.setCellFactory((TableColumn<AnuncioEntrevistaAvaliacao, Calendar> param) -> new TableCell<AnuncioEntrevistaAvaliacao, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(new SimpleDateFormat("dd/MM/yyyy").format(item.getTime()));
				}
			}
		});
		TableColumn<AnuncioEntrevistaAvaliacao, Avaliacao> colunaNome = new TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("avaliacao"));
		colunaNome.setCellFactory((TableColumn<AnuncioEntrevistaAvaliacao, Avaliacao> param) -> new TableCell<AnuncioEntrevistaAvaliacao, Avaliacao>() {
			@Override
			protected void updateItem(Avaliacao item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(item.getNome());
				}
			}
		});
		colunaNome.setPrefWidth(150);
		TableColumn<AnuncioEntrevistaAvaliacao, Avaliacao.AvaliacaoTipo> colunaTipo = new TableColumn<>("Tipo");
		colunaTipo.setCellValueFactory(new PropertyValueFactory<>("avaliacaoTipo"));
		colunaTipo.setCellFactory((TableColumn<AnuncioEntrevistaAvaliacao, Avaliacao.AvaliacaoTipo> param) -> 
		new TableCell<AnuncioEntrevistaAvaliacao, Avaliacao.AvaliacaoTipo>() {
			@Override
			protected void updateItem(Avaliacao.AvaliacaoTipo item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(item.toString());
				}
			}
		});
		TableColumn<AnuncioEntrevistaAvaliacao, Number> colunaPontuacao= new TableColumn<>("Pts");
		colunaPontuacao.setCellValueFactory(new PropertyValueFactory<>("pontuacao"));
		colunaPontuacao.setCellFactory((TableColumn<AnuncioEntrevistaAvaliacao, Number> param) -> new TableCell<AnuncioEntrevistaAvaliacao, Number>() {
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					if(tbPrincipal.getItems().get(getIndex()).getAvaliacaoTipo()!=Avaliacao.AvaliacaoTipo.DISSERTATIVA)
						setText(String.valueOf(item.intValue()));
				}
			}
		});
		colunaPontuacao.setPrefWidth(40);
		TableColumn<AnuncioEntrevistaAvaliacao, Number> colunaPontuacaoMaxima = new TableColumn<>("Pts Max");
		colunaPontuacaoMaxima.setCellValueFactory(new PropertyValueFactory<>("pontuacaoMaxima"));
		colunaPontuacaoMaxima.setCellFactory((TableColumn<AnuncioEntrevistaAvaliacao, Number> param) -> new TableCell<AnuncioEntrevistaAvaliacao, Number>() {
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					if(tbPrincipal.getItems().get(getIndex()).getAvaliacaoTipo()!=Avaliacao.AvaliacaoTipo.DISSERTATIVA)
						setText(String.valueOf(item.intValue()));
				}
			}
		});
		colunaPontuacaoMaxima.setPrefWidth(40);
		TableColumn<AnuncioEntrevistaAvaliacao, String> colunaDescricao = new TableColumn<>("Descricao");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colunaDescricao.setCellFactory((TableColumn<AnuncioEntrevistaAvaliacao, String> param) -> {
			final TableCell<AnuncioEntrevistaAvaliacao, String> cell = new TableCell<AnuncioEntrevistaAvaliacao, String>() {
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
						AnuncioEntrevistaAvaliacao ava = tbPrincipal.getItems().get(getIndex());
						String param = item;
						if(ava.getAvaliacaoTipo().equals(Avaliacao.AvaliacaoTipo.CONDICIONAL))
							param = ava.getCondicao()!=null?ava.getCondicao().getDescricao():"";
						textArea.setText(param);
						setGraphic(textArea);
						setText(null);
					}
				}
			};
			return cell;
		});

		TableColumn<AnuncioEntrevistaAvaliacao, String> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
		colunaEditar.setCellFactory((TableColumn<AnuncioEntrevistaAvaliacao, String> param) -> {
			final TableCell<AnuncioEntrevistaAvaliacao, String> cell = new TableCell<AnuncioEntrevistaAvaliacao, String>() {
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
							preencherFormulario(tbPrincipal.getItems().get(getIndex()),getIndex());
						});
						setGraphic(button);
						setText(null);
					}
				}
			};
			return cell;
		});
		tbPrincipal.setFixedCellSize(70);
		tbPrincipal.getColumns()
				.addAll(colunaCriadoEm,colunaNome,colunaTipo,colunaPontuacao,colunaPontuacaoMaxima,colunaDescricao, colunaEditar);
    }
   public Set<AnuncioEntrevistaAvaliacao> getItems(){
	   return aeAvaliacao;
   }
}
