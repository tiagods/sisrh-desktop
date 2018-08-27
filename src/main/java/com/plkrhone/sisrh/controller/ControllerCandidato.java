
package com.plkrhone.sisrh.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Cidade;
import com.plkrhone.sisrh.model.Curso;
import com.plkrhone.sisrh.model.Estado;
import com.plkrhone.sisrh.model.Cargo;
import com.plkrhone.sisrh.repository.helper.CandidatosImp;
import com.plkrhone.sisrh.repository.helper.CidadesImpl;
import com.plkrhone.sisrh.repository.helper.CursosImpl;
import com.plkrhone.sisrh.repository.helper.CargosImpl;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class ControllerCandidato extends UtilsController implements Initializable {
	@FXML
	private JFXTabPane tabPane;

	@FXML
	private Tab tabPesquisa;

	@FXML
	private TableView<Candidato> tbCandidatos;

	@FXML
	private ComboBox<Cargo> cbObjetivoPesquisa;

	@FXML
	private JFXComboBox<String> cbIndicacaoPesquisa;

	@FXML
	private JFXComboBox<String> cbSexoPesquisa;

	@FXML
	private JFXTextField txValorPesquisa;

	@FXML
	private JFXComboBox<String> cbBuscarPorPesquisa;

	@FXML
	private JFXDatePicker dtPerfilInicioPesquisa;

	@FXML
	private JFXDatePicker dtPerfilFimPesquisa;

	@FXML
	private JFXComboBox<Candidato.Escolaridade> cbFormacaoMinPesquisa;

	@FXML
	private JFXComboBox<Candidato.Escolaridade> cbFormacaoMaxPesquisa;

	@FXML
	private JFXComboBox<Curso> cbCursoSuperior;

	@FXML
	private JFXComboBox<Curso> cbCursoSuperiorPesquisa;

	@FXML
	private ComboBox<Cargo> cbExperienciaPesquisa;

	@FXML
	private JFXCheckBox ckIndisponivelPesquisa;

	@FXML
	private MaskTextField txIdadeInicioPesquisa;

	@FXML
	private MaskTextField txIdadeFimPesquisa;

	@FXML
	private Tab tabCadastro;

	@FXML
	private AnchorPane pnCadastro;

	@FXML
	private JFXTextField txCodigo;

	@FXML
	private JFXTextField txDataCriacao;

	@FXML
	private JFXTextField txNome;

	@FXML
	private JFXRadioButton rbSexoF;

	@FXML
	private JFXRadioButton rbSexoM;

	@FXML
	private MaskTextField txIdade;

	@FXML
	private JFXDatePicker dtNascimento;

	@FXML
	private JFXComboBox<Candidato.EstadoCivil> cbEstadoCivil;

	@FXML
	private JFXCheckBox ckFumante;

	@FXML
	private JFXCheckBox ckFilhos;

	@FXML
	private MaskTextField txQtdFilhos;

	@FXML
	private JFXComboBox<Candidato.Escolaridade> cbEscolaridade;

	@FXML
	private JFXComboBox<String> cbNacionalidade;

	@FXML
	private MaskedTextField txTelefone;

	@FXML
	private MaskedTextField txCelular;

	@FXML
	private JFXTextField txEmail;

	@FXML
	private JFXTextField txLogradouro;

	@FXML
	private MaskedTextField txCep;
	@FXML
	private JFXTextField txNumero;
	@FXML
	private JFXTextField txBairro;
	@FXML
	private JFXComboBox<Estado> cbEstado;
	@FXML
	private JFXComboBox<Cidade> cbCidade;
	@FXML
	private ComboBox<Cargo> cbObjetivo1;

	@FXML
	private ComboBox<Cargo> cbObjetivo2;

	@FXML
	private ComboBox<Cargo> cbObjetivo3;

	@FXML
	private JFXCheckBox ckPossuiIndicacao;

	@FXML
	private AnchorPane pnCadastroIndicacao;

	@FXML
	private JFXTextField txEmpresaIndicacao;

	@FXML
	private JFXTextArea txDetalhesIndicacao;

	@FXML
	private JFXTextField txCarreiraEmpresa1;

	@FXML
	private JFXTextArea txCarreiraDescricao1;
	@FXML
	private JFXTextArea txCarreiraDescricao2;
	@FXML
	private JFXTextArea txCarreiraDescricao3;
	@FXML
	private ComboBox<Cargo> cbCarreiraObjetivo1;

	@FXML
	private JFXTextField txCarreiraEmpresa2;

	@FXML
	private ComboBox<Cargo> cbCarreiraObjetivo2;

	@FXML
	private JFXTextField txCarreiraEmpresa3;

	@FXML
	private ComboBox<Cargo> cbCarreiraObjetivo3;

	@FXML
	private JFXTextField txFormulario;

	@FXML
	private JFXCheckBox ckNaoDisponivel;

	@FXML
	private JFXTextArea txNaoDisponivelDetalhes;

	@FXML
	private JFXButton btAnexar;

	@FXML
	private JFXButton btnVisualizar;

	@FXML
	private JFXButton btnRemover;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnAlterar;

	@FXML
	private JFXButton btnSalvar;

	@FXML
	private JFXButton btAddAnuncio;

	@FXML
	private JFXButton btnExcluir;
	@FXML
	private JFXTextField txComplemento;

	private Candidato candidato;
	private Anuncio anuncio = null;
	private CandidatosImp candidatos;
	private CargosImpl vagas;
	private CursosImpl cursos;
	private CidadesImpl cidades;
	Storage storage = StorageProducer.newConfig();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		long tempoInicial = System.currentTimeMillis();
		// tabelaCadastro();
		txIdade.setPrefColumnCount(3);
		try {
			loadFactory();
			candidatos = new CandidatosImp(getManager());
			//combos();
			List<Candidato> lista = candidatos.getAll();
			tbCandidatos.setItems(FXCollections.observableList(lista));
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Tela de candidatos");
			alert.setHeaderText(null);
			alert.setContentText("Erro ao listar candidatos\n" + e);
			alert.show();
			e.printStackTrace();

		} finally {
			close();
		}
		long tempoFinal = System.currentTimeMillis();
		System.out.println((tempoFinal - tempoInicial) + " ms");

	}

	@FXML
	void adicionarNoAnuncio(ActionEvent event) {
		anuncio.getCurriculoSet().add(this.candidato);
		btAddAnuncio.setDisable(true);
		tbCandidatos.refresh();
	}

	public Set<Candidato> receberCandidatos() {
		return anuncio.getCurriculoSet();
	}


}