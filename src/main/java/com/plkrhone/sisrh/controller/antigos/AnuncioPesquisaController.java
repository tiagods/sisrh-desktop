package com.plkrhone.sisrh.controller.antigos;

import com.jfoenix.controls.*;
import com.plkrhone.sisrh.config.enums.FXMLEnum;
import com.plkrhone.sisrh.config.enums.IconsEnum;
import com.plkrhone.sisrh.controller.UtilsController;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.repository.helper.*;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.storage.Storage;
import com.plkrhone.sisrh.util.storage.StorageProducer;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AnuncioPesquisaController extends UtilsController implements Initializable {
    @FXML
    private JFXComboBox<String> cbEmpresaPesquisa;

    @FXML
    private JFXComboBox<Cargo> cbVagaPesquisa;

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

    private Stage stage;
    private AnunciosImp anuncios;
    private AnuncioEntrevistasImp anunciosEntrevistas;
    private CargosImpl cargos;
    private CargoNiveisImpl cargoNiveis;
    private CandidatosImp candidatos;
    private ClientesImp clientes;
    private CursosImpl cursos;
    private CidadesImpl cidades;
    private TreinamentosImp treinamentos;
    private Storage storage = StorageProducer.newConfig();

    public AnuncioPesquisaController(Stage stage) {
        this.stage=stage;
    }

    private void abrirCadastro(Anuncio t) {
        try {
            loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.ANUNCIO_CADASTRO);
            if(t!=null) {
                anuncios = new AnunciosImp(getManager());
                t = anuncios.findById(t.getId());
            }
            AnuncioCadastroController controller = new AnuncioCadastroController(stage,t);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                try {
                    loadFactory();
                    /*
                    if(controller.isHouveAtualizacaoCombo()) {
                        AvaliacaoGrupo avaliacaoGrupo1 = cbGrupoPesquisa.getValue();
                        combos();
                        if (avaliacaoGrupo1 != null) {
                            cbGrupoPesquisa.getSelectionModel().select(avaliacaoGrupo1);
                        }
                    }
                    */
                    filtrar();
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR, "Erro", "","Falha ao listar os registros", e, true);
                } finally {
                    close();
                }
            });
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo" + FXMLEnum.AVALIACAO_CADASTRO, e, true);
        } finally {
            close();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void combos() {
        // combos da area de pesquisa
        cbCronogramaPesquisa.getItems().add(null);
        cbCronogramaPesquisa.getItems().addAll(FXCollections.observableList(Arrays.asList(Anuncio.Cronograma.values())));
        cbCronogramaPesquisa.setValue(null);
        cbEmpresaPesquisa.getItems().addAll("Qualquer", "Ativo", "Inativo");
        cbEmpresaPesquisa.setValue("Qualquer");
        cbStatusPesquisa.getItems().add(null);
        cbStatusPesquisa.getItems().addAll(FXCollections.observableList(Arrays.asList(Anuncio.AnuncioStatus.values())));
        cbStatusPesquisa.setValue(Anuncio.AnuncioStatus.EM_ANDAMENTO);

        cargos = new CargosImpl(getManager());
        List<Cargo> cargoList = cargos.getAll();
        cbVagaPesquisa.getItems().add(null);
        cbVagaPesquisa.getItems().addAll(FXCollections.observableList(cargoList));
        new ComboBoxAutoCompleteUtil<>(cbVagaPesquisa);
        cbDatasFiltro.getItems().addAll("Data de Abertura", "Data de Admissão", "Data de Encerramento");
        cbDatasFiltro.getSelectionModel().selectFirst();
        cbFiltro.getItems().addAll("Nome", "Nome da Cargo", "Nome do Cliente");
        cbFiltro.getSelectionModel().selectFirst();

        // grafico
        grafico.setLabelsVisible(true);
        grafico.setLegendVisible(true);
        grafico.setLegendSide(Side.BOTTOM);
        grafico.setLabelLineLength(10);
        graficosStatus();// graficos

        ToggleGroup tgGrafico = new ToggleGroup();
        tgGrafico.getToggles().addAll(rbCronogramaGrafico, rbStatusGrafico);
        EventHandler filterGrafico = event -> {
            filtrar();
            event.consume();
        };
        rbCronogramaGrafico.addEventFilter(ActionEvent.ACTION, filterGrafico);
        rbStatusGrafico.addEventFilter(ActionEvent.ACTION, filterGrafico);
        rbStatusGrafico.setSelected(true);

        ChangeListener pesquisas = (ChangeListener<Object>) (observable, oldValue, newValue) -> filtrar();
        cbCronogramaPesquisa.valueProperty().addListener(pesquisas);
        dtInicialPesquisa.valueProperty().addListener(pesquisas);
        dtFinalPesquisa.valueProperty().addListener(pesquisas);
        cbEmpresaPesquisa.valueProperty().addListener(pesquisas);
        cbStatusPesquisa.valueProperty().addListener(pesquisas);
    }
    private void filtrar() {
        try {
            loadFactory();
            anuncios = new AnunciosImp(getManager());
            if (rbCronogramaGrafico.isSelected())
                graficosCronograma();
            else
                graficosStatus();
            int valor = -1;
            if (cbEmpresaPesquisa.getValue().equals("Ativa"))
                valor = 1;
            else if (cbEmpresaPesquisa.getValue().equals("Inativa"))
                valor = 0;
            List<Anuncio> anunciosList = anuncios.filtrar(cbCronogramaPesquisa.getValue(), cbStatusPesquisa.getValue(),
                    valor, cbVagaPesquisa.getValue(), cbDatasFiltro.getValue(), dtInicialPesquisa.getValue(),
                    dtFinalPesquisa.getValue(), txPesquisa.getText().trim(), cbFiltro.getValue());
            tbAnuncio.getItems().setAll(FXCollections.observableArrayList(anunciosList));
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","Falha ao listar os registros","");
        } finally {
            close();
        }
    }
    private void graficosCronograma() {
        ObservableList<PieChart.Data> datas = FXCollections.observableArrayList();
        for (Anuncio.Cronograma c : Anuncio.Cronograma.values()) {
            long q = anuncios.count(c);
            if (q > 0)
                datas.add(new PieChart.Data(c.getDescricao() + "(" + q + ")", q));
        }
        grafico.setTitle("Visao por Cronograma");
        grafico.setData(datas);
    }

    private void graficosStatus() {
        ObservableList<PieChart.Data> datas = FXCollections.observableArrayList();
        for (Anuncio.AnuncioStatus c : Anuncio.AnuncioStatus.values()) {
            long q = anuncios.count(c);
            if (q > 0)
                datas.add(new PieChart.Data(c.getDescricao() + "(" + q + ")", q));
        }
        grafico.setTitle("Visão por Status");
        grafico.setData(datas);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabela();
        try {
            loadFactory();
            anuncios = new AnunciosImp(getManager());
            combos();
            List<Anuncio> anuncioList = anuncios.filtrar(null, Anuncio.AnuncioStatus.EM_ANDAMENTO, 1, null, "", null, null, "",
                    "");
            tbAnuncio.setItems(FXCollections.observableList(anuncioList));
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","Erro ao listar os registros:\t"+e,e,true);
        } finally {
            close();
        }
    }
    @FXML
    void novo(ActionEvent event) {
        abrirCadastro(null);
    }
    @FXML
    void pesquisar(KeyEvent event) {
        filtrar();
    }
    @FXML
    void sair(ActionEvent event){
        stage.close();
    }
    private void tabela() {
        TableColumn<Anuncio, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);

        TableColumn<Anuncio, String> colunaNome = new TableColumn<>("Nome do Anuncio");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setCellFactory((TableColumn<Anuncio, String> param) -> new TableCell<Anuncio, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                }
            }
        });
        TableColumn<Anuncio, Cliente> colunaCliente = new TableColumn<>("Cliente");
        colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaCliente.setCellFactory((TableColumn<Anuncio, Cliente> param) -> new TableCell<Anuncio, Cliente>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                }
            }
        });
        colunaCliente.setPrefWidth(150);

        TableColumn<Anuncio, Cliente> colunaClienteContabil = new TableColumn<>("Cliente Contabil");
        colunaClienteContabil.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaClienteContabil
                .setCellFactory((TableColumn<Anuncio, Cliente> param) -> new TableCell<Anuncio, Cliente>() {
                    @Override
                    protected void updateItem(Cliente item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getClienteContabil() == 1 ? "Sim" : "Não");
                        }
                    }
                });
        TableColumn<Anuncio, FormularioRequisicao> colunaVaga = new TableColumn<>("Cargo");
        colunaVaga.setCellValueFactory(new PropertyValueFactory<>("formularioRequisicao"));
        colunaVaga.setCellFactory(
                (TableColumn<Anuncio, FormularioRequisicao> param) -> new TableCell<Anuncio, FormularioRequisicao>() {
                    @Override
                    protected void updateItem(FormularioRequisicao item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getCargo() != null ? item.getCargo().getNome() : "");
                        }
                    }
                });
        TableColumn<Anuncio, Anuncio.Cronograma> colunaCronograma = new TableColumn<>("Cronograma");
        colunaCronograma.setCellValueFactory(new PropertyValueFactory<>("cronograma"));
        colunaCronograma
                .setCellFactory((TableColumn<Anuncio, Anuncio.Cronograma> param) -> new TableCell<Anuncio, Anuncio.Cronograma>() {
                    @Override
                    protected void updateItem(Anuncio.Cronograma item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getDescricao());
                        }
                    }
                });
        TableColumn<Anuncio, Anuncio.AnuncioStatus> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("anuncioStatus"));
        colunaStatus
                .setCellFactory((TableColumn<Anuncio, Anuncio.AnuncioStatus> param) -> new TableCell<Anuncio, Anuncio.AnuncioStatus>() {
                    @Override
                    protected void updateItem(Anuncio.AnuncioStatus item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getDescricao());
                        }
                    }
                });

        TableColumn<Anuncio, FormularioRequisicao> colunaFormulario = new TableColumn<>("");
        colunaFormulario.setCellValueFactory(new PropertyValueFactory<>("formularioRequisicao"));
        colunaFormulario.setCellFactory((TableColumn<Anuncio, FormularioRequisicao> param) -> new TableCell<Anuncio, FormularioRequisicao>() {
            JFXButton button = new JFXButton();//
            @Override
            protected void updateItem(FormularioRequisicao item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_CLIP);
                    } catch (IOException e) {
                    }
                    if(item.getFormulario()== null || item.getFormulario().equals(""))
                        button.setVisible(false);
                    button.setOnAction(event ->
                            visualizarFormulario(item.getFormulario(),storage)
                    );
                    setGraphic(button);
                }
            }
        });
        TableColumn<Anuncio, String> colunaEditar = new TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>(""));
        colunaEditar.setCellFactory((TableColumn<Anuncio, String> param) -> {
            final TableCell<Anuncio, String> cell = new TableCell<Anuncio, String>() {
                final JFXButton button = new JFXButton("Editar");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btJFXDefault");
                        button.setOnAction(event -> abrirCadastro(tbAnuncio.getItems().get(getIndex())));
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        });
        tbAnuncio.getColumns().addAll(colunaId, colunaNome, colunaVaga, colunaCliente, colunaClienteContabil,
                colunaCronograma, colunaStatus, colunaFormulario,colunaEditar);
    }
}
