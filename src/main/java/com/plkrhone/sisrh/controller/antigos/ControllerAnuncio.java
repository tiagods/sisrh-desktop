package com.plkrhone.sisrh.controller.antigos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.controller.UtilsController;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.model.anuncio.AnuncioCandidatoConclusao;
import com.plkrhone.sisrh.repository.helper.*;
import javafx.stage.*;
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
import com.plkrhone.sisrh.model.Curso;
import com.plkrhone.sisrh.model.anuncio.AnuncioCronograma;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAnalise;
import com.plkrhone.sisrh.observer.CandidatoObserver;
import com.plkrhone.sisrh.observer.EnumCandidato;
import com.plkrhone.sisrh.repository.helper.filter.CandidatoAnuncioFilter;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.office.FileOfficeEnum;
import com.plkrhone.sisrh.util.office.OfficeEditor;
import com.plkrhone.sisrh.util.office.OfficeEditorProducer;
import com.plkrhone.sisrh.util.storage.PathStorageEnum;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Created by Tiago on 08/08/2017.
 */
public class ControllerAnuncio extends UtilsController implements Initializable {

    @FXML
    private JFXTabPane tab;

    @FXML
    private Tab tabPesquisa;

    @FXML
    private AnchorPane panPesquisa;

    @FXML
    private JFXComboBox<String> cbEmpresaPesquisa;

    @FXML
    private ComboBox<Cargo> cbVagaPesquisa;

    @FXML
    private JFXComboBox<Anuncio.Cronograma> cbCronogramaPesquisa;

    @FXML
    private JFXComboBox<Anuncio.AnuncioStatus> cbStatusPesquisa;

    @FXML
    private JFXDatePicker dtInicialPesquisa;

    @FXML
    private JFXDatePicker dtFinalPesquisa;

    @FXML
    private TableView<Anuncio> tbAnuncio;

    @FXML
    private JFXComboBox<String> cbFiltro;

    @FXML
    private JFXTextField txPesquisa;

    @FXML
    private JFXComboBox<String> cbDatasFiltro;

    @FXML
    private PieChart grafico;

    @FXML
    private JFXRadioButton rbStatusGrafico;

    @FXML
    private JFXRadioButton rbCronogramaGrafico;

    @FXML
    private Tab tabCadastro;

    @FXML
    private AnchorPane pnCadastro;

    @FXML
    private JFXTextField txCodigo;

    @FXML
    private JFXTextField txNome;

    @FXML
    private JFXDatePicker dtEncerramento;

    @FXML
    private JFXDatePicker dtAbertura;

    @FXML
    private JFXComboBox<Cliente> cbCliente;

    @FXML
    private JFXComboBox<Anuncio.AnuncioStatus> cbStatus;

    @FXML
    private JFXComboBox<Anuncio.Cronograma> cbCronograma;

    @FXML
    private JFXTextField txCriacao;

    @FXML
    private JFXTabPane tpProcesso;

    @FXML
    private Tab tabVagas;

    @FXML
    private AnchorPane pnCadastroVaga;

    @FXML
    private JFXTabPane tpVaga;

    @FXML
    private Tab tabVaga1;

    @FXML
    private AnchorPane pnVaga1;

    @FXML
    private JFXRadioButton rbVaga1Clt;

    @FXML
    private JFXRadioButton rbVaga1Estagiario;

    @FXML
    private JFXRadioButton rbVaga1Temporario;

    @FXML
    private JFXDatePicker dtVaga1DataInicio;

    @FXML
    private JFXDatePicker dtVaga1DataFim;

    @FXML
    private ComboBox<Cargo> cbVaga1Cargo;

    @FXML
    private JFXTextField txVaga1CargaHoraria;

    @FXML
    private JFXTextField txVaga1HorarioTrabalho;

    @FXML
    private JFXRadioButton rbVaga1ProcessoSigilosoNao;

    @FXML
    private JFXRadioButton rbVaga1ProcessoSigilosoSim;

    @FXML
    private JFXButton btVagaAvancar1;

    @FXML
    private Tab tabVaga2;

    @FXML
    private AnchorPane pnVaga2;

    @FXML
    private JFXRadioButton rbVaga2MotivoQuadro;

    @FXML
    private JFXRadioButton rbVaga2MotivoDesligado;

    @FXML
    private JFXRadioButton rbVaga2MotivoAfastamento;

    @FXML
    private JFXRadioButton rbVaga2MotivoOutro;

    @FXML
    private JFXTextField txVaga2MotivoOutro;

    @FXML
    private JFXButton btVagaAvancar2;

    @FXML
    private JFXButton btVagaVoltar2;

    @FXML
    private Tab tabVaga3;

    @FXML
    private AnchorPane pnVaga3;

    @FXML
    private JFXComboBox<Candidato.Escolaridade> cbVaga3Escolaridade;

    @FXML
    private JFXComboBox<Cidade> cbVaga3Municipio;

    @FXML
    private JFXComboBox<Curso> cbVaga3Formacao;

    @FXML
    private JFXComboBox<Integer> cbVaga3Tempo;

    @FXML
    private JFXComboBox<String> cbVaga3Tipo;

    @FXML
    private JFXComboBox<Estado> cbVaga3Estado;

    @FXML
    private JFXButton btVagaVoltar3;

    @FXML
    private JFXButton btVagaAvancar3;

    @FXML
    private JFXTextField txVaga3Idioma;

    @FXML
    private Tab tabVaga31;

    @FXML
    private AnchorPane pnVaga31;

    @FXML
    private JFXTextArea txVaga31Competencia;

    @FXML
    private JFXButton btVagaAvancar31;

    @FXML
    private JFXButton btVagaVoltar31;

    @FXML
    private Tab tabVaga32;

    @FXML
    private AnchorPane pnVaga32;

    @FXML
    private JFXTextArea txVaga32DescricaoAtividades;

    @FXML
    private JFXButton btVagaVoltar32;

    @FXML
    private JFXButton btVagaAvancar32;

    @FXML
    private Tab tabVaga4;

    @FXML
    private AnchorPane pnVaga4;

    @FXML
    private MaskTextField txVaga4Salario;

    @FXML
    private JFXCheckBox ckVaga4ACombinar;

    @FXML
    private JFXTextArea txVaga4Observacao;

    @FXML
    private MaskTextField txVaga4Comissao;

    @FXML
    private JFXButton btVagaAvancar4;

    @FXML
    private JFXButton btVagaVoltar4;

    @FXML
    private JFXRadioButton rbVaga4ComissaoSim;

    @FXML
    private JFXRadioButton rbVaga4ComissaoNao;

    @FXML
    private Tab tabVaga41;

    @FXML
    private AnchorPane pnVaga41;

    @FXML
    private MaskTextField txVaga41VRValor;

    @FXML
    private JFXButton btVagaVoltar41;

    @FXML
    private JFXRadioButton rbVaga41VRNao;

    @FXML
    private JFXRadioButton rbVaga41VRSim;

    @FXML
    private JFXRadioButton rbVaga41VTSim;

    @FXML
    private JFXRadioButton rbVaga41VTNao;

    @FXML
    private JFXRadioButton rbVaga41AMedicaNao;

    @FXML
    private JFXRadioButton rbVaga41AMedicaSim;

    @FXML
    private JFXTextField txVaga41AMedicaDetalhes;

    @FXML
    private JFXTextField txVaga41Outro;

    @FXML
    private JFXButton btAnexarFormulario;

    @FXML
    private JFXButton btAbrirFormulario;

    @FXML
    private JFXButton btRemoverFormulario;

    @FXML
    private JFXTextField txDocumento;

    @FXML
    private Tab tabCadastroContrato;

    @FXML
    private AnchorPane panContrato;

    @FXML
    private JFXDatePicker dtEnvioContrato;

    @FXML
    private JFXDatePicker dtRetornoContrato;

    @FXML
    private JFXComboBox<Anuncio.StatusContrato> cbStatusContrato;

    @FXML
    private Tab tabCurriculos;

    @FXML
    private AnchorPane panCurriculos;

    @FXML
    private TableView<Candidato> tbCurriculos;

    @FXML
    private JFXButton btAdicionarCurriculo;

    @FXML
    private Tab tabEntrevistas;

    @FXML
    private AnchorPane panEntrevistas;

    @FXML
    private JFXComboBox<Candidato> cbCandidatoEntrevista;

    @FXML
    private JFXButton btnEntrevista;

    @FXML
    private TableView<AnuncioEntrevista> tbEntrevista;

    @FXML
    private Tab tabPreSelecionado;

    @FXML
    private AnchorPane panPreSelecionado;

    @FXML
    private JFXButton btnAdicionar;

    @FXML
    private JFXComboBox<Candidato> cbPreSelecionado;

    @FXML
    private TableView<Candidato> tbPreSelecionado;

    @FXML
    private Tab tabConclusao;

    @FXML
    private JFXButton btnVerCandidatoConclusao;

    @FXML
    private JFXDatePicker dtAdmissao;

    @FXML
    private JFXTextField txResponsavel;

    @FXML
    private JFXDatePicker dtInicioDivulgacao;

    @FXML
    private JFXDatePicker dtFimDivulgacao;

    @FXML
    private JFXTextField txDivulgacaoObs;

    @FXML
    private JFXDatePicker dtInicioRecrutamento;

    @FXML
    private JFXDatePicker dtFimRecrutamento;

    @FXML
    private JFXTextField txRecrutamentoObs;

    @FXML
    private JFXDatePicker dtInicioAgendamento;

    @FXML
    private JFXDatePicker dtFimAgendamento;

    @FXML
    private JFXTextField txAgendamentoObs;

    @FXML
    private JFXDatePicker dtInicioEntrevista;

    @FXML
    private JFXDatePicker dtFimEntrevista;

    @FXML
    private JFXTextField txEntrevistaObs;

    @FXML
    private JFXDatePicker dtInicioPreSelecionado;

    @FXML
    private JFXDatePicker dtFimPreSelecionado;

    @FXML
    private JFXTextField txPreSelecionadoObs;

    @FXML
    private JFXDatePicker dtInicioRetorno;

    @FXML
    private JFXDatePicker dtFimRetorno;

    @FXML
    private JFXTextField txRetornoObs;

    @FXML
    private JFXButton btSalvarEConcluir;

    @FXML
    private JFXComboBox<CargoNivel> cbVaga1CargoNivel;

    @FXML
    private JFXTextField txCargoAdc;

    @FXML
    private JFXComboBox<Candidato> cbConclusaoCandidato;

    @FXML
    private JFXButton btnAprovarCandidato;

    @FXML
    private JFXButton btNovo;

    @FXML
    private JFXButton btAlterar;

    @FXML
    private JFXButton btExcluir;

    @FXML
    private JFXButton btSalvar;

    @FXML
    private TableView<AnuncioCandidatoConclusao> tbConclusaoCandidato;

    Anuncio anuncio;
    AnunciosImp anuncios;
    AnuncioEntrevistasImp anunciosEntrevistas;
    CargosImpl cargos;
    CargoNiveisImpl cargoNiveis;
    CandidatosImp candidatos;
    ClientesImp clientes;
    CursosImpl cursos;
    CidadesImpl cidades;
    TreinamentosImp treinamentos;
    Storage storage = StorageProducer.newConfig();

    CandidatoObserver observer = CandidatoObserver.getInstance();




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





}