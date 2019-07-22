package com.plkrhone.sisrh.controller;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.plkrhone.sisrh.config.enums.FXMLEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.repository.helper.AnunciosImp;
import com.plkrhone.sisrh.repository.helper.TarefasImp;
import com.plkrhone.sisrh.view.AnuncioView;
import com.plkrhone.sisrh.view.EntrevistaView;
import com.plkrhone.sisrh.view.TarefaView;
import com.plkrhone.sisrh.view.UsuarioView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Tiago on 07/07/2017.
 */
public class ControllerMenu extends UtilsController implements Initializable {
	
	private static Logger log = LoggerFactory.getLogger(ControllerMenu.class);
	@FXML
	private Label txAnunciosAbertos;
	@FXML
	private Label txTarefasHoje;
	@FXML
	private Label txEntrevistasHoje;
	
	AnunciosImp anuncios;
	TarefasImp tarefas;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		long tempoInicial = System.currentTimeMillis();
        long tempoFinal = System.currentTimeMillis();
        if(log.isDebugEnabled()) {
        	log.debug("Tela "+getClass().getSimpleName().replace("Controller", "")+" abriu em : "+(tempoFinal-tempoInicial)+" ms");
        }
        try {
            loadFactory();
            carregarCartoes();
        }catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
        
	}
	@FXML
	private Pane pnCenter;

	@FXML
	void abrirCliente(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.CLIENTE_PESQUISA);
			loader.setController(new ClientePesquisaController(stage));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		}catch(IOException e) {
			alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo "+FXMLEnum.CLIENTE_PESQUISA,e,true);
		}
	}

	@FXML
	void abrirAnuncio(ActionEvent event) {
//		AnuncioView anuncio = new AnuncioView();
//		anuncio.start(new Stage());

		try {
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.ANUNCIO_PESQUISA);
			loader.setController(new AnuncioPesquisaController(stage));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		}catch(IOException e) {
			alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo "+FXMLEnum.CLIENTE_PESQUISA,e,true);
		}

	}

	@FXML
	void abrirAvaliacao(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.AVALIACAO_PESQUISA);
			loader.setController(new AvaliacaoPesquisaController());
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		}catch(IOException e) {
			alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo "+FXMLEnum.AVALIACAO_PESQUISA,e,true);
		}
	}
	@FXML
	void abrirCandidato(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.CANDIDATO_PESQUISA);
			loader.setController(new CandidatoPesquisaController(stage,null,null));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		}catch(IOException e) {
			alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo "+FXMLEnum.CANDIDATO_PESQUISA,e,true);
		}
	}
	@FXML
	void abrirManual(ActionEvent event){
		abrirArquivo(System.getProperty("user.dir"),"manual.docx");
	}

	//@FXML
	void abrirEntrevista(ActionEvent event) {
		EntrevistaView entrevistaView = new EntrevistaView();
		entrevistaView.start(new Stage());
	}

	@FXML
	void abrirTarefa(ActionEvent event) {
		TarefaView tarefaView = new TarefaView();
		tarefaView.start(new Stage());
	}

	@FXML
	void abrirCargo(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.CARGO_PESQUISA);
			loader.setController(new CargoPesquisaController(stage));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
		}catch(IOException e) {
			alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo "+FXMLEnum.CARGO_PESQUISA,e,true);
		}
	}

	@FXML
	void abrirUsuario(ActionEvent event) {
		UsuarioView usuarioView = new UsuarioView();
		usuarioView.start(new Stage());
	}
	
	private void carregarCartoes() {
		anuncios = new AnunciosImp(getManager());
		tarefas = new TarefasImp(getManager());
		
		LocalDateTime inicio = LocalDateTime.now().with(LocalTime.of(00, 00, 00));
		LocalDateTime fim = LocalDateTime.now().with(LocalTime.of(23, 59, 59));
		
		long valueTarefas = tarefas.filtrar(null, null, null, null, null, null, null, 0).size();
		long valueEntrevistas = tarefas.filtrar(null, Anuncio.Cronograma.AGENDAMENTO_DE_ENTREVISTA, null, null, null, inicio, fim, 0).size();
				
		long value = anuncios.count(Anuncio.AnuncioStatus.EM_ANDAMENTO);
		txAnunciosAbertos.setText(String.valueOf(value));
		txEntrevistasHoje.setText(String.valueOf(valueEntrevistas));
		txTarefasHoje.setText(String.valueOf(valueTarefas));
		
	}
}
