package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.*;
import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.init.PaisesConfig;
import com.plkrhone.sisrh.config.init.UsuarioLogado;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.repository.helper.*;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.storage.PathStorageEnum;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fxutils.maskedtextfield.MaskTextField;
import org.fxutils.maskedtextfield.MaskedTextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class CandidatoCadastroController extends UtilsController implements Initializable{
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
    private MaskedTextField txTelefone;

    @FXML
    private MaskedTextField txCelular;

    @FXML
    private JFXTextField txEmail;

    @FXML
    private MaskedTextField txCep;

    @FXML
    private ComboBox<Cargo> cbObjetivo1;

    @FXML
    private ComboBox<Cargo> cbObjetivo2;

    @FXML
    private ComboBox<Cargo> cbObjetivo3;

    @FXML
    private JFXCheckBox ckNaoDisponivel;

    @FXML
    private JFXTextArea txNaoDisponivelDetalhes;

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
    private ComboBox<Cargo> cbCarreiraObjetivo1;

    @FXML
    private JFXTextField txCarreiraEmpresa2;

    @FXML
    private JFXTextArea txCarreiraDescricao2;

    @FXML
    private ComboBox<Cargo> cbCarreiraObjetivo2;

    @FXML
    private JFXTextField txCarreiraEmpresa3;

    @FXML
    private JFXTextArea txCarreiraDescricao3;

    @FXML
    private ComboBox<Cargo> cbCarreiraObjetivo3;

    @FXML
    private JFXTextField txFormulario;

    @FXML
    private JFXComboBox<String> cbNacionalidade;

    @FXML
    private JFXComboBox<Curso> cbCursoSuperior;

    @FXML
    private JFXTextField txLogradouro;

    @FXML
    private JFXTextField txComplemento;

    @FXML
    private JFXTextField txBairro;

    @FXML
    private JFXComboBox<Estado> cbEstado;

    @FXML
    private JFXComboBox<Cidade> cbCidade;

    @FXML
    private JFXTextField txNumero;

    @FXML
    private JFXButton btNovoCargo1,btNovoCargo2,btNovoCargo3,btNovoCargo4,btNovoCargo5,btNovoCargo6;

    private Anuncio anuncio;
    private AnunciosImp anuncios;
    private Candidato candidato;
    private CandidatosImp candidatos;
    private CargosImpl cargos;
    private CursosImpl cursos;
    private CidadesImpl cidades;
    Storage storage = StorageProducer.newConfig();
    private Stage stage;

    private boolean houveAtualizacaoCombo = false;

    public CandidatoCadastroController(Stage stage, Candidato candidato, Anuncio anuncio){
        this.candidato=candidato;
        this.stage=stage;
    }
    @FXML
    void alterarIdade(KeyEvent event) {
        Platform.runLater(() -> {
            if (!txIdade.getText().trim().equals("")) {
                try {
                    int idade = Integer.parseInt(txIdade.getText());
                    LocalDate date1 = LocalDate.now();
                    LocalDate dataNas = LocalDate.of(date1.getYear() - idade, 01, 01);
                    dtNascimento.setValue(dataNas);
                    txIdade.positionCaret(txIdade.getText().length());
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR,"Valor incorreto","Tente novamente","Informe somente numeros");
                }
            } else
                dtNascimento.setValue(null);
        });
    }

    @FXML
    void anexarFormulario(ActionEvent event) {
        Runnable run = () -> {
            // TODO Auto-generated method stub

            Set<FileChooser.ExtensionFilter> filters = new HashSet<>();
            //		alert = new Alert(Alert.AlertType.CONFIRMATION);
            //		alert.setTitle("Informação importante!");
            //		alert.setHeaderText("Deseja usar modelo do Word para imprimir seus formulários pré-prenchidos?");
            //		alert.setContentText(
            //				"Para usar essa técnica, salve seus formularios no padrão Microsoft Word 2007 (.doc),\n"
            //						+ "Caso contrario clique em cancelar");
            //		Optional<ButtonType> buttonType = alert.showAndWait();
            //		if (buttonType.get() == ButtonType.CANCEL) {
            //			filters.add(new FileChooser.ExtensionFilter("MS Word 2007", "*.doc"));
            //			filters.add(new FileChooser.ExtensionFilter("*.pdf", "*.doc|*.pdf|*.docx"));
            //			filters.add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            //		}

            filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc","*.pdf","*.docx"));
            File file = storage.carregarArquivo(new Stage(), filters);
            if (file != null) {
                String novoNome = storage.gerarNome(file, PathStorageEnum.CURRICULO.getDescricao());
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
        };
        Platform.runLater(run);
    }
    @FXML
    void buscarCep(ActionEvent event) {
        bucarCep(txCep, txLogradouro, txNumero, txComplemento, txBairro, cbCidade, cbEstado);
    }
    void combos() {
        combosCargos();

        EventHandler<ActionEvent> handler = event -> novoCargo();
        btNovoCargo1.setOnAction(handler);
        btNovoCargo2.setOnAction(handler);
        btNovoCargo3.setOnAction(handler);
        btNovoCargo4.setOnAction(handler);
        btNovoCargo5.setOnAction(handler);
        btNovoCargo6.setOnAction(handler);

        cursos = new CursosImpl(getManager());

        cbCursoSuperior.getItems().add(new Curso(-1L,""));
        cbCursoSuperior.getItems().addAll(cursos.getAll());
        new ComboBoxAutoCompleteUtil<>(cbCursoSuperior);

        pnCadastroIndicacao.setVisible(false);
        txQtdFilhos.setDisable(true);

        ToggleGroup group = new ToggleGroup();
        group.getToggles().addAll(rbSexoF, rbSexoM);

        ckNaoDisponivel.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue && !txCodigo.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Status do Candidato");
                alert.setContentText("Desmarcando essa opção fará com que o candidato\n"
                        + "fique disponivel para outros processos seletivos\n"
                        + "Tem certeza disso?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.CANCEL) ckNaoDisponivel.setSelected(true);
            }
        });
        ckFilhos.setOnAction(event -> {
            if (ckFilhos.isSelected()) {
                txQtdFilhos.setDisable(false);
            } else {
                txQtdFilhos.setDisable(true);
                txQtdFilhos.setText("");
            }
        });
        txQtdFilhos.setOnKeyReleased(event -> {
            try {
                Integer.parseInt(txQtdFilhos.getText());
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valor incorreto");
                alert.setHeaderText("Digite um numero válido!");
                alert.setContentText("O valor informado esta incorreto, por favor digite um numero valido!");
                alert.showAndWait();
                txQtdFilhos.setText("");
            }
        });
        dtNascimento.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDate date1 = LocalDate.now();
                LocalDate date2 = newValue;
                Period period = Period.between(date2, date1);
                txIdade.setText("" + period.getYears());
            }
        });
        ckPossuiIndicacao.setOnAction(event -> {
            if (ckPossuiIndicacao.isSelected()) {
                pnCadastroIndicacao.setVisible(true);
            } else
                pnCadastroIndicacao.setVisible(false);
        });
        cbEstadoCivil.getItems().addAll(Candidato.EstadoCivil.values());
        cbEscolaridade.getItems().addAll(Candidato.Escolaridade.values());


        cbNacionalidade.getItems().addAll(PaisesConfig.getInstance().getAll());
        cbNacionalidade.setValue("Brasil");

        new ComboBoxAutoCompleteUtil<>(cbNacionalidade);
        comboRegiao(cbCidade,cbEstado,getManager());
    }
    void combosCargos(){
        ComboBox[] comboBoxes =
                new ComboBox[]{cbObjetivo1,cbObjetivo2,cbObjetivo3,
                        cbCarreiraObjetivo1,cbCarreiraObjetivo2,cbCarreiraObjetivo3};
        cargos = new CargosImpl(getManager());
        List<Cargo> cargoList = new ArrayList<>();
        cargoList.add(new Cargo(-1L,""));
        cargoList.addAll(cargos.getAll());
        for(ComboBox box : comboBoxes){
            Cargo ca = (Cargo) box.getValue();
            box.getItems().setAll(cargoList);
            new ComboBoxAutoCompleteUtil<>(box);
            if(ca!=null){
                box.getSelectionModel().select(ca);
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        long tempoInicial = System.currentTimeMillis();
        txIdade.setPrefColumnCount(3);
        try {
            loadFactory();
            candidatos = new CandidatosImp(getManager());
            combos();
            //btAdicionarNoAnuncio.setDisable(true);
            if(candidato!=null) {
                preencherFormulario(candidato);
//                if(anuncio!=null){
//                    Long id = candidato.getId();
//                    Optional<Candidato> result = this.anuncio.getCurriculoSet()
//                            .stream()
//                            .filter(f->f.getId()==id)
//                            .findFirst();
//                    btAdicionarNoAnuncio.setDisable(result.isPresent());
//                }
            }


        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","Erro ao listar registros", e,true);

        } finally {
            close();
        }
        long tempoFinal = System.currentTimeMillis();
        System.out.println((tempoFinal - tempoInicial) + " ms");

    }
    public void novoCargo(){
        try {
            loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CARGO_CADASTRO);
            CargoCadastroController controller = new CargoCadastroController(stage, null);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(e -> {
                try {
                    loadFactory();
                    Cargo cargo = controller.getCargo();
                    //filtrar(this.paginacao);
                    combosCargos();
                } catch (Exception ex) {
                    alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao listar os registros", ex, true);
                } finally {
                    close();
                }
            });
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo" + FXMLEnum.CARGO_CADASTRO, e, true);
        } finally {
            close();
        }

    }
    public void preencherFormulario(Candidato candidato) {
        txCodigo.setText(candidato.getId() + "");
        txDataCriacao.setText(candidato.getCriadoEm()!=null?new SimpleDateFormat("dd/MM/yyyy").format(candidato.getCriadoEm().getTime()):"");
        txNome.setText(candidato.getNome());
        if (candidato.getSexo().equals("M"))
            rbSexoM.setSelected(true);
        else
            rbSexoF.setSelected(true);
        try {
            if (candidato.getDataNascimento() != null) {
                dtNascimento.setValue(candidato.getDataNascimento().getTime().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cbEstadoCivil.setValue(candidato.getEstadoCivil());
        if (candidato.getFumante() == 1)
            ckFumante.setSelected(true);
        if (candidato.getFilhos() == 1) {
            ckFilhos.setSelected(true);
            txQtdFilhos.setDisable(false);
        } else
            txQtdFilhos.setDisable(true);
        txQtdFilhos.setText(String.valueOf(candidato.getQtdeFilhos()));
        cbEscolaridade.setValue(candidato.getEscolaridade());
        cbCursoSuperior.setValue(candidato.getCursoSuperior());
        cbNacionalidade.setValue(candidato.getNacionalidade());
        PfPj pfpj = candidato.getPessoaFisica();
        if (pfpj != null) {
            txTelefone.setText(pfpj.getTelefone());
            txCelular.setText(pfpj.getCelular());
            txEmail.setText(pfpj.getEmail());
            txCep.setPlainText(pfpj.getCep());
            txLogradouro.setText(pfpj.getLogradouro());
            txNumero.setText(pfpj.getNumero());
            txComplemento.setText(pfpj.getComplemento());;
            txBairro.setText(pfpj.getBairro());
            cbEstado.setValue(pfpj.getEstado());
            cbCidade.setValue(pfpj.getCidade());
        }
        cbObjetivo1.setValue(candidato.getObjetivo1());
        cbObjetivo2.setValue(candidato.getObjetivo2());
        cbObjetivo3.setValue(candidato.getObjetivo3());

        txCarreiraEmpresa1.setText(candidato.getEmpresa1());
        txCarreiraEmpresa2.setText(candidato.getEmpresa2());
        txCarreiraEmpresa3.setText(candidato.getEmpresa3());

        cbCarreiraObjetivo1.setValue(candidato.getCargo1());
        cbCarreiraObjetivo2.setValue(candidato.getCargo2());
        cbCarreiraObjetivo3.setValue(candidato.getCargo3());

        txCarreiraDescricao1.setText(candidato.getDescricaoCargo1());
        txCarreiraDescricao2.setText(candidato.getDescricaoCargo2());
        txCarreiraDescricao3.setText(candidato.getDescricaoCargo3());

        txFormulario.setText(candidato.getFormulario());
        if (candidato.getIndicacao() == 1) {
            pnCadastroIndicacao.setVisible(true);
            ckPossuiIndicacao.setSelected(true);
            txEmpresaIndicacao.setText(candidato.getEmpresaIndicacao());
            txDetalhesIndicacao.setText(candidato.getDetalhesIndicacao());
        } else {
            pnCadastroIndicacao.setVisible(false);
            txEmpresaIndicacao.clear();
            txDetalhesIndicacao.clear();
        }
        ckNaoDisponivel.setSelected(candidato.getOcupado() == 1);
        txNaoDisponivelDetalhes.setText(candidato.getOcupadoDetalhes());
        this.candidato = candidato;
    }

    @FXML
    void removerFormulario(ActionEvent event) {
        try{
            storage.delete(txFormulario.getText());
            txFormulario.setText("");
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(candidato!=null) {
            try{
                loadFactory();
                candidatos = new CandidatosImp(getManager());
                candidato.setFormulario(txFormulario.getText());
                candidatos.save(candidato);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                close();
            }
        }
    }

    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }
    @FXML
    void salvar(ActionEvent event) {
        try {
            loadFactory();
            candidatos = new CandidatosImp(getManager());
            if (txCodigo.getText().equals("")) {
                candidato = new Candidato();
                candidato.setCriadoEm(Calendar.getInstance());
                candidato.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
                if (!txEmail.getText().trim().equals("")) {
                    Candidato can = candidatos.findByEmail(txEmail.getText().trim());
                    if (can != null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro Registro");
                        alert.setHeaderText("Valor duplicado!");
                        alert.setContentText("E-mail informado ja existe!"+can.getId()+"-"+can.getNome());
                        alert.showAndWait();
                        return;
                    }
                }
            }
            else if(!txEmail.getText().trim().equals("")){
                Candidato can = candidatos.findByEmail(txEmail.getText().trim());
                if (can!=null && can.getId() != candidato.getId()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro Registro");
                    alert.setHeaderText("Valor duplicado!");
                    alert.setContentText("E-mail informado ja existe!"+can.getId()+"-"+can.getNome());
                    alert.showAndWait();
                    return;
                }
            }
            candidato.setNome(txNome.getText());
            candidato.setSexo(rbSexoF.isSelected() ? "F" : "M");

			/*
			if(dtNascimento.getValue()!=null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Erro Registro");
				alert.setHeaderText("Campo obrigatorio!");
				alert.setContentText("é obrigatorio informar a data de nascimento ou a idade!");
				alert.showAndWait();
				return;
			}*/
            //Calendar calendar = Calendar.getInstance();
            //calendar.setTime(Date.from(dtNascimento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            candidato.setDataNascimento(dtNascimento.getValue()==null?null
                    : GregorianCalendar.from(dtNascimento.getValue().atStartOfDay(ZoneId.systemDefault())));
            candidato.setEstadoCivil(cbEstadoCivil.getValue());
            candidato.setFumante(ckFumante.isSelected() ? 1 : 0);
            candidato.setFilhos(ckFilhos.isSelected() ? 1 : 0);
            if (ckFilhos.isSelected()) {
                try {
                    int value = Integer.parseInt(txQtdFilhos.getText());
                    candidato.setQtdeFilhos(value);
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR,"Erro de conversão","Valor incorreto", "Quantidade de filhos, use apenas números");
                    return;
                }
            }
            candidato.setEscolaridade(cbEscolaridade.getValue());
            candidato.setCursoSuperior(cbCursoSuperior.getValue());
            candidato.setNacionalidade(cbNacionalidade.getValue());
            PfPj pfpj = new PfPj();
            pfpj.setEmail(txEmail.getText());
            pfpj.setTelefone(txTelefone.getPlainText());
            pfpj.setCelular(txCelular.getPlainText());
            pfpj.setCep(txCep.getPlainText());
            pfpj.setLogradouro(txLogradouro.getText());
            pfpj.setNumero(txNumero.getText());
            pfpj.setComplemento(txComplemento.getText());
            pfpj.setBairro(txBairro.getText());
            pfpj.setEstado(cbEstado.getValue());
            pfpj.setCidade(cbCidade.getValue());
            candidato.setPessoaFisica(pfpj);

            // curriculo
            candidato.setObjetivo1(cbObjetivo1.getValue().getId()==-1L?null:cbObjetivo1.getValue());
            candidato.setObjetivo2(cbObjetivo2.getValue().getId()==-1L?null:cbObjetivo2.getValue());
            candidato.setObjetivo3(cbObjetivo3.getValue().getId()==-1L?null:cbObjetivo3.getValue());

            candidato.setIndicacao(ckPossuiIndicacao.isSelected() ? 1 : 0);
            candidato.setEmpresaIndicacao(txEmpresaIndicacao.getText());
            candidato.setDetalhesIndicacao(txDetalhesIndicacao.getText());

            candidato.setEmpresa1(txCarreiraEmpresa1.getText());
            candidato.setEmpresa2(txCarreiraEmpresa2.getText());
            candidato.setEmpresa3(txCarreiraEmpresa3.getText());

            candidato.setCargo1(cbCarreiraObjetivo1.getValue().getId()==-1L?null:cbCarreiraObjetivo1.getValue());
            candidato.setCargo2(cbCarreiraObjetivo2.getValue().getId()==-1L?null:cbCarreiraObjetivo2.getValue());
            candidato.setCargo3(cbCarreiraObjetivo3.getValue().getId()==-1L?null:cbCarreiraObjetivo3.getValue());

            candidato.setDescricaoCargo1(txCarreiraDescricao1.getText());
            candidato.setDescricaoCargo2(txCarreiraDescricao2.getText());
            candidato.setDescricaoCargo3(txCarreiraDescricao3.getText());

            candidato.setUltimaModificacao(Calendar.getInstance());
            candidato.setFormulario(txFormulario.getText());

            candidato.setOcupado(ckNaoDisponivel.isSelected() ? 1 : 0);
            candidato.setOcupadoDetalhes(txNaoDisponivelDetalhes.getText());

            if (!txFormulario.getText().equals("") && !txFormulario.getText().startsWith(PathStorageEnum.CURRICULO.getDescricao()+"/")) {
                try {
                    storage.transferTo(txFormulario.getText(), PathStorageEnum.CURRICULO.getDescricao()+"/"+txFormulario.getText());
                    txFormulario.setText(PathStorageEnum.CURRICULO.getDescricao()+"/" + txFormulario.getText());
                    candidato.setFormulario(txFormulario.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                candidato.setFormulario(txFormulario.getText());
            candidato = candidatos.save(candidato);
            txCodigo.setText(String.valueOf(candidato.getId()));
            alert(Alert.AlertType.INFORMATION,"Sucesso","","Salvo com sucesso");
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","Falha ao salvar o registro", e, true);
        } finally {
            close();
        }
    }

    @FXML
    void visualizarFormulario(ActionEvent event) {
        visualizarFormulario(txFormulario.getText());
    }
    void visualizarFormulario(String formulario){
        Runnable run = () -> {
            if(!formulario.trim().equals("")){
                try {
                    File file  = storage.downloadFile(formulario);
                    if(file!=null)
                        Desktop.getDesktop().open(file);
                    System.out.println(file.getAbsolutePath());
                }catch (Exception e) {
                    alert(Alert.AlertType.ERROR,"Erro","","Erro ao baixar o formulario",e,true);
                }
            }
        };
        new Thread(run).start();
    }
    public boolean isHouveAtualizacaoCombo() {
        return this.houveAtualizacaoCombo;
    }
}
