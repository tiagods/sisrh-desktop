package com.plkrhone.sisrh.controller.antigos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.plkrhone.sisrh.controller.UtilsController;
import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Treinamento;
import com.plkrhone.sisrh.model.anuncio.AnuncioCandidatoConclusao;
import com.plkrhone.sisrh.repository.helper.AnuncioCandidatoConclusaoImpl;
import com.plkrhone.sisrh.repository.helper.TreinamentosImp;
import com.plkrhone.sisrh.util.ComboBoxAutoCompleteUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class AnuncioCandidatoConclusaoController extends UtilsController implements Initializable{


    @FXML
    private JFXComboBox<Treinamento> cbNomeTreinamento;
    @FXML
    private DatePicker dtInicioConclusao;
    @FXML
    private DatePicker dtFimConclusao;
    private Stage stage;
    private TreinamentosImp treinamentos;
    private AnuncioCandidatoConclusaoImpl conclusaoImpl;
    private AnuncioCandidatoConclusao candidatoConclusao;

    public AnuncioCandidatoConclusaoController(Stage stage, AnuncioCandidatoConclusao candidatoConclusao){
        this.candidatoConclusao = candidatoConclusao;
        this.stage=stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            loadFactory();
            treinamentos = new TreinamentosImp(getManager());
            cbNomeTreinamento.getItems().add(new Treinamento(-1L, "Selecione um treinamento para o candidato aprovado"));
            cbNomeTreinamento.getItems().addAll(treinamentos.getAll());
            cbNomeTreinamento.getSelectionModel().selectFirst();

            if(candidatoConclusao.getTreinamento()!=null)
                cbNomeTreinamento.setValue(candidatoConclusao.getTreinamento());
            dtInicioConclusao.setValue(
                    candidatoConclusao.getDataInicoTreinamento()==null?null:
                            candidatoConclusao.getDataInicoTreinamento().getTime()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate());
            dtFimConclusao.setValue(
                    candidatoConclusao.getDataFimTreinamento()==null?null:
                            candidatoConclusao.getDataFimTreinamento().getTime()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate());
            new ComboBoxAutoCompleteUtil<>(cbNomeTreinamento);
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro", "","Erro ao salvar o registro",e,true);
        }
        finally {
            close();
        }
    }
    @FXML
    private void novoTreinamento(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cadastro de Treinamento");
        dialog.setHeaderText("Cadastre um novo treinamento");
        dialog.setContentText("Informe um nome:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                loadFactory();
                treinamentos = new TreinamentosImp(getManager());

                Treinamento t = treinamentos.findByNome(result.get().trim());
                if (t != null) {
                    alert(Alert.AlertType.ERROR,"Erro","Esse nome já existe","Por favor informe outro nome!");
                } else {
                    Treinamento t1 = new Treinamento(result.get());
                    t1 = treinamentos.save(t1);
                    // preencher combos
                    List<Treinamento> treinamentoList = treinamentos.getAll();
                    ObservableList<Treinamento> novaLista = FXCollections.observableArrayList();
                    novaLista.add(null);
                    novaLista.addAll(treinamentoList);
                    Treinamento tTemp = cbNomeTreinamento.getValue();
                    cbNomeTreinamento.setItems(novaLista);
                    if (tTemp != null)
                        cbNomeTreinamento.getSelectionModel().select(tTemp);
                    else cbNomeTreinamento.setValue(t1);
                    new ComboBoxAutoCompleteUtil<>(cbNomeTreinamento);
                }
            } catch (Exception e) {
                alert(Alert.AlertType.ERROR,"Erro", "","Erro ao salvar o registro",e,true);
            } finally {
                close();
            }
        }
    }
    @FXML
    private void salvar(ActionEvent event){
        if(dtInicioConclusao.getValue()!=null && cbNomeTreinamento.getValue().getId()==-1L
                ||dtFimConclusao.getValue()!=null && cbNomeTreinamento.getValue().getId()==-1L){
            alert(Alert.AlertType.ERROR,"Erro", "Campo obrigatorio","Para salvar uma data o treinamento é obrigatório!");
            return;
        }
        candidatoConclusao.setTreinamento(cbNomeTreinamento.getValue());
        candidatoConclusao.setDataInicoTreinamento(dtInicioConclusao.getValue()==null?null:
                GregorianCalendar.from(dtInicioConclusao.getValue().atStartOfDay(ZoneId.systemDefault())));
        candidatoConclusao.setDataFimTreinamento(dtFimConclusao.getValue()==null?null:
            GregorianCalendar.from(dtFimConclusao.getValue().atStartOfDay(ZoneId.systemDefault())));
        stage.close();
    }
}
