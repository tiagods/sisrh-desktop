package com.plkrhone.sisrh.controller;

import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.model.Cidade;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public abstract class UtilsController extends PersistenciaController {
	private JFXButton buttonNovo;
	private JFXButton buttonEditar;
	private JFXButton buttonSalvar;
	private JFXButton buttonExcluir;
	private JFXButton buttonCancelar;

	public Alert alert(Alert.AlertType alertType, String title, String header, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contentText);
		return alert;
	}
	public void alert(Alert.AlertType alertType, String title, String header, String contentText, Exception ex, boolean print) {
		Alert alert = alert(alertType,title,header,contentText);
		alert.getDialogPane().setExpanded(true);
		alert.getDialogPane().setMinSize(600,150);
		if(alert.getAlertType()== Alert.AlertType.ERROR && ex!=null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();
			Label label = new Label("Mais detalhes do erro:");
			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);
			// Set expandable Exception into the dialog pane.
			alert.getDialogPane().setExpandableContent(expContent);

			if(print) {
				try {
					LocalDateTime dateTime = LocalDateTime.now();
					File log = new File(System.getProperty("user.dir") + "/log/" +
							dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "-erro.txt");
					if (!log.getParentFile().exists())
						log.getParentFile().mkdir();
					FileWriter fw = new FileWriter(log, true);
					String line = System.getProperty("line.separator");
					fw.write(
							dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "=" +
									header + ":" +
									contentText +
									line +
									exceptionText
					);
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		alert.showAndWait();
	}
	public String carregarArquivo(String title){
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle(title);
		String local = "";
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Planilha do Excel (*.xls)", ".xls"));
		int retorno = chooser.showSaveDialog(null);
		if(retorno== JFileChooser.APPROVE_OPTION){
			local = chooser.getSelectedFile().getAbsolutePath(); //
		}
		return local;
	}
	public void buttonTable(JFXButton btn,IconsEnum icon) throws IOException {
		ImageView imageview = createImage(30,30,icon);
		btn.setGraphic(imageview);
	}

	public Optional<String> cadastroRapido(){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Cadastro rapido");
		dialog.setHeaderText("Crie um novo registro:");
		dialog.setContentText("Por favor entre com um novo nome");
		return dialog.showAndWait();
	}
	private ImageView createImage(int x, int y, IconsEnum icon) {
		Image image = new Image(icon.getLocalizacao().toString());
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(x);
		imageview.setFitWidth(y);
		imageview.setPreserveRatio(true);
		return imageview;
	}
	public Stage initPanel(FXMLLoader loader, Stage stage, Modality modality, StageStyle ss) throws IOException{
		final Parent root = loader.load();
		final Scene scene = new Scene(root);
		stage.initModality(modality);
		stage.initStyle(ss);
		stage.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
		stage.setScene(scene);
		stage.show();
		return stage;
	}
	public FXMLLoader loaderFxml(FXMLEnum e) throws IOException{
		final FXMLLoader loader = new FXMLLoader(e.getLocalizacao());
		return loader;
	}
	public UtilsController(JFXButton buttonNovo, JFXButton buttonEditar, 
			JFXButton buttonSalvar, JFXButton buttonExcluir,JFXButton buttonCancelar) {
		this.buttonNovo=buttonNovo;
		this.buttonEditar=buttonEditar;
		this.buttonSalvar=buttonSalvar;
		this.buttonExcluir=buttonExcluir;
		this.buttonCancelar=buttonCancelar;
		this.buttonNovo.setOnAction(new NovoEditar());
		this.buttonEditar.setOnAction(new NovoEditar());
		this.buttonSalvar.setOnAction(new Salvar());
		this.buttonExcluir.setOnAction(new Excluir());
		this.buttonCancelar.setOnAction(new Cancelar());
	}
	public class NovoEditar implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			buttonNovo.setDisable(true);
			buttonEditar.setDisable(true);
			buttonSalvar.setDisable(false);
			buttonExcluir.setDisable(true);
			buttonCancelar.setDisable(false);
		}
		
	}
	public class Excluir implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			buttonNovo.setDisable(false);
			buttonEditar.setDisable(false);
			buttonSalvar.setDisable(true);
			buttonExcluir.setDisable(false);
			buttonCancelar.setDisable(true);	
		}		
	}
	public class Salvar implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			buttonNovo.setDisable(false);
			buttonEditar.setDisable(false);
			buttonSalvar.setDisable(true);
			buttonExcluir.setDisable(false);
			buttonCancelar.setDisable(true);	
		}
	}
	public class Cancelar implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			buttonNovo.setDisable(false);
			buttonEditar.setDisable(false);
			buttonSalvar.setDisable(true);
			buttonExcluir.setDisable(false);
			buttonCancelar.setDisable(true);	
		}
	}
	public void desbloquear(boolean value, ObservableList<Node> nodes) {
		nodes.forEach((n) -> {
			if (n instanceof JFXTextField)
				((JFXTextField) n).setEditable(value);
			else if (n instanceof MaskTextField)
				((MaskTextField) n).setEditable(value);
			else if (n instanceof MaskedTextField)
				((MaskedTextField) n).setEditable(value);
			else if (n instanceof JFXTextArea)
				((JFXTextArea) n).setEditable(value);
			else if (n instanceof TextArea) 
				((TextArea)n).setEditable(value);
			else if (n instanceof JFXComboBox
					||n instanceof ComboBox
					||n instanceof TableView
					||n instanceof JFXCheckBox
					||n instanceof JFXDatePicker
					||n instanceof JFXRadioButton
					) 
				n.setDisable(!value);			
//			else if (n instanceof JFXComboBox) {
//				((JFXComboBox) n).setDisable(!value);
//			} else if (n instanceof ComboBox) {
//				((ComboBox) n).setDisable(!value);
//			} else if (n instanceof TableView) {
//				((TableView) n).setDisable(!value);
//			} else if (n instanceof JFXCheckBox) {
//				((JFXCheckBox) n).setDisable(!value);
//			} else if (n instanceof JFXDatePicker) {
//				((JFXDatePicker) n).setDisable(!value);
//			} else if (n instanceof JFXRadioButton) {
//				((JFXRadioButton) n).setDisable(!value);
//			} 
			else if (n instanceof Pane)
				desbloquear(value, ((Pane) n).getChildren());
			else if (n instanceof AnchorPane) 
				desbloquear(value, ((AnchorPane) n).getChildren());
			else if (n instanceof JFXTabPane) {
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
	public void limpar(ObservableList<Node> nodes) {
		nodes.forEach((n) -> {
			if (n instanceof JFXTextField) {
				((JFXTextField) n).setText("");
			} else if (n instanceof MaskTextField) {
				((MaskTextField) n).setText("");
			} else if (n instanceof MaskedTextField) {
				((MaskedTextField) n).setText("");
			} else if (n instanceof JFXComboBox) {
				((JFXComboBox<?>) n).getSelectionModel().clearSelection();
			} else if (n instanceof ComboBox) {
				((ComboBox<?>) n).getSelectionModel().clearSelection();
			} else if (n instanceof JFXTextArea) {
				((JFXTextArea) n).setText("");
			} else if (n instanceof TableView) {
				((TableView<?>) n).getItems().clear();
			} else if (n instanceof JFXCheckBox) {
				((JFXCheckBox) n).setSelected(false);
			} else if (n instanceof JFXRadioButton) {
				((JFXRadioButton) n).setSelected(false);
			} else if (n instanceof JFXDatePicker) {
				((JFXDatePicker) n).setValue(null);
			} else if (n instanceof Pane) {
				limpar(((Pane) n).getChildren());
			} else if (n instanceof AnchorPane) {
				limpar(((AnchorPane) n).getChildren());
			} else if (n instanceof Accordion) {
				ObservableList<TitledPane> titledPanes = ((Accordion) n).getPanes();
				titledPanes.forEach(c -> {
					limpar(c.getChildrenUnmodifiable());
				});
			} else if (n instanceof JFXTabPane) {
				ObservableList<Tab> tabs = ((JFXTabPane) n).getTabs();
				tabs.forEach(c -> {
					if (c.getContent() instanceof AnchorPane)
						limpar(((AnchorPane) c.getContent()).getChildren());
				});
			} else if (n instanceof Accordion) {
				ObservableList<TitledPane> panes = ((Accordion) n).getPanes();
				panes.forEach(c -> limpar(c.getChildrenUnmodifiable()));
			}
		});
	}
	
}
