package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.repository.helper.CidadesImp;
import com.plkrhone.sisrh.repository.helper.ClienteSetoresImp;
import com.plkrhone.sisrh.repository.helper.ClientesImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import com.plkrhone.sisrh.util.EnderecoUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.fxutils.maskedtextfield.MaskedTextField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClienteCadastroController extends UtilsController implements Initializable{
    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private JFXTextField txCodigo;

    @FXML
    private JFXTextField txCriacao;

    @FXML
    private JFXTextField txNome;

    @FXML
    private MaskedTextField txCnpj;

    @FXML
    private JFXTextField txResponsavel;

    @FXML
    private JFXTextField txEmail;

    @FXML
    private MaskedTextField txTelefone;

    @FXML
    private MaskedTextField txCelular;

    @FXML
    private JFXComboBox<?> cbSetor;

    @FXML
    private JFXCheckBox ckClienteContabil;

    @FXML
    private JFXCheckBox ckDesabilitar;

    @FXML
    private MaskedTextField txCep;

    @FXML
    private JFXTextField txLogradouro;

    @FXML
    private JFXTextField txNumero;

    @FXML
    private JFXTextField txComplemento;

    @FXML
    private JFXTextField txBairro;

    @FXML
    private JFXComboBox<?> cbEstado;

    @FXML
    private JFXComboBox<?> cbCidade;

    @FXML
    private TableView<?> tbPrincipal;

    private Stage stage;
    private Cliente cliente;
    private ClienteSetoresImp setores;
    private ClientesImp clientes;
    private CidadesImp cidades;

    public ClienteCadastroController(Stage stage,Cliente cliente){
        this.stage=stage;
        this.cliente=cliente;
    }

    @FXML
    void buscarCep(javafx.event.ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("CEP");
        try{
            loadFactory();
            EnderecoUtil util = EnderecoUtil.getInstance();
            if(txCep.getPlainText().trim().length()==8) {
                Endereco endereco = util.pegarCEP(txCep.getPlainText());
                if(endereco!=null){
                    txLogradouro.setText(endereco.getLogradouro());
                    txNumero.setText("");
                    txComplemento.setText(endereco.getComplemento());
                    txBairro.setText(endereco.getBairro());
                    cidades = new CidadesImp(getManager());
                    cbCidade.getItems().clear();
                    cbCidade.getItems().addAll(cidades.findByEstado(endereco.getUf()));
                    Cidade cidade = cidades.findByNome(endereco.getLocalidade());
                    cbCidade.setValue(cidade);
                    cbEstado.setValue(endereco.getUf());
                }
                else {
                    alert.setContentText("Verifique se o cep informado é valido ou se existe uma conexão com a internet");
                    alert.show();
                }
            }
            else{
                alert.setContentText("Verifique o cep informado");
                alert.show();
            }
        }catch(Exception e){
            alert.setTitle("Falha na conexão com o banco de dados");
            alert.setContentText("Houve uma falha na conexão com o banco de dados");
            alert.show();
            e.printStackTrace();
        }finally {
            close();
        }
    }

    private void combo(){
        cbEstado.getItems().setAll(Arrays.asList(Estado.values()));
        cbEstado.setValue(Estado.SP);
        cidades = new CidadesImp(getManager());
        Cidade cidade = cidades.findByNome("São Paulo");
        cbCidade.getItems().setAll(cidades.findByEstado(Estado.SP));
        cbCidade.setValue(cidade);
        cbEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    loadFactory();
                    cidades = new CidadesImp(getManager());
                    List<Cidade> listCidades = cidades.findByEstado(newValue);
                    cbCidade.getItems().setAll(listCidades);
                } catch (Exception e) {
                } finally {
                    close();
                }
            }
        });
        new ComboBoxAutoCompleteUtil<>(cbCidade);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(cliente!=null) preencherFormulario(cliente);
    }

    @FXML
    void novoSetor(ActionEvent event) {
        Optional<String> result = cadastroRapido();
        if (result.isPresent()) {
            if (result.get().trim().length() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Insira um nome para o departamento");
                alert.setContentText(null);
                alert.showAndWait();
            } else {
                try {
                    loadFactory();
                    setores = new ClienteSetoresImp(getManager());
                    ClienteSetor s = setores.findByNome(result.get().trim());
                    if (s == null) {
                        ClienteSetor setor = new ClienteSetor();
                        setor.setNome(result.get().trim());
                        setores.save(setor);

                        // preencher combos
                        List<ClienteSetor> avaliacaoGrupos = setores.getAll();
                        ObservableList<ClienteSetor> novaLista = FXCollections.observableArrayList();
                        novaLista.add(null);
                        novaLista.addAll(avaliacaoGrupos);
                        ClienteSetor setor1 = cbSetor.getValue();
                        cbSetor.getItems().clear();
                        cbSetor.getItems().addAll(novaLista);
                        cbSetorPesquisa.getItems().clear();
                        cbSetorPesquisa.getItems().addAll(novaLista);
                        if (setor1 != null)
                            cbSetor.getSelectionModel().select(setor1);
                    } else {
                        alert(Alert.AlertType.ERROR,"Valor duplicado","",
                                "Já existe um cadastro com o texto informado!");
                    }
                } catch (Exception e) {
                    alert(Alert.AlertType.ERROR,"Erro","",
                            "Falha ao salvar o registro!", e, true);
                } finally {
                    close();
                }
            }
        }
    }

    private void preencherFormulario(Cliente cliente) {
        try {
            txCodigo.setText(String.valueOf(cliente.getId()));
            if (cliente.getCriadoPor() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                txCriacao.setText("Criado em: " + cliente.getCriadoEm() != null ? sdf.format(cliente.getCriadoEm().getTime())
                        : "" + " por:" + cliente.getCriadoPor().getNome());
            }
            txNome.setText(cliente.getNome());
            // txCnpj.setText(cliente.getCnpj());
            txCnpj.setPlainText(cliente.getCnpj());
            txResponsavel.setText(cliente.getResponsavel());

            PfPj pfpj = cliente.getPessoaJuridica();
            if (pfpj != null) {
                txCep.setPlainText(pfpj.getCep());
                txLogradouro.setText(pfpj.getLogradouro());
                txNumero.setText(pfpj.getNumero());
                txComplemento.setText(pfpj.getComplemento());
                txBairro.setText(pfpj.getBairro());
                cbEstado.setValue(pfpj.getEstado());
                cbCidade.setValue(pfpj.getCidade());
            }
            if (cliente.getSetor() != null)
                cbSetor.setValue(cliente.getSetor());
            ckClienteContabil.setSelected(cliente.getClienteContabil() == 1 ? true : false);
            ckDesabilitar.setSelected(cliente.getSituacao() == 0);

            tbPrincipal.getItems().clear();
            tbPrincipal.getItems().addAll(cliente.getAnuncioSet());
            this.cliente = cliente;
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","",
                    "Falha ao preencher o formulario!", e, true);
        }
    }
    @FXML
    void sair(ActionEvent event){
        stage.close();
    }
    @FXML
    void salvar(ActionEvent event) {

    }
}
