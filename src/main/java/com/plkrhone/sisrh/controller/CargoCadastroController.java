package com.plkrhone.sisrh.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.plkrhone.sisrh.model.Cargo;
import com.plkrhone.sisrh.repository.helper.CargosImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CargoCadastroController extends UtilsController implements Initializable {

    @FXML
    private JFXTextField txCodigo;

    @FXML
    private JFXTextField txCriadoEm;

    @FXML
    private JFXTextField txNome;

    @FXML
    private JFXTextArea txDescricao;

    @FXML
    private JFXTextField txFonte;

    private Stage stage;
    private Cargo cargo;
    private CargosImpl cargos;

    public CargoCadastroController(Stage stage, Cargo cargo){
        this.stage=stage;
        this.cargo=cargo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(cargo!=null) preencherFormulario(cargo);
    }

    void preencherFormulario(Cargo cargo) {
        txCodigo.setText(String.valueOf(cargo.getId()));
        txNome.setText(cargo.getNome());
        txCriadoEm.setText(cargo.getCriadoEm() == null ? ""
                : new SimpleDateFormat("dd/MM/yyyy").format(cargo.getCriadoEm().getTime()));
        txDescricao.setText(cargo.getDescricao());
        txFonte.setText(cargo.getFonte());
        this.cargo = cargo;
    }

    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {

        try {
            loadFactory();
            if (txCodigo.getText().equals("")) {
                cargo = new Cargo();
                cargo.setCriadoEm(Calendar.getInstance());
                cargos = new CargosImpl(getManager());
                if (cargos.findByNome(txNome.getText().trim()) != null) {
                    alert(Alert.AlertType.ERROR,"Duplicidade","","Ja existe um cadastro com o nome informado");
                    return;
                }
            } else {
                Long id = Long.parseLong(txCodigo.getText());
                cargo.setId(id);
                Cargo v = cargos.findByNome(txNome.getText().trim());
                if (v != null && v.getId()!= cargo.getId()) {
                    alert(Alert.AlertType.ERROR,"Duplicidade","","Ja existe um cadastro com o nome informado");
                    return;
                }
            }
            cargo.setNome(txNome.getText());
            cargo.setDescricao(txDescricao.getText());
            cargo.setFonte(txFonte.getText());
            cargos= new CargosImpl(getManager());
            cargo = cargos.save(cargo);
            txCodigo.setText(String.valueOf(cargo.getId()));
            alert(Alert.AlertType.INFORMATION,"Sucesso","","Salvo com sucesso!");
            stage.close();
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","Erro ao salvar o registro",e,true);
        } finally {
            close();
        }
    }
    public Cargo getCargo() {
        return cargo;
    }
}
