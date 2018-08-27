package com.plkrhone.sisrh.config.enums;

import java.net.URL;

public enum FXMLEnum {
    ANUNCIO_CADASTRO("AnuncioCadastro.fxml"),
    ANUNCIO_PESQUISA("AnuncioPesquisa.fxml"),
    CANDIDATO_CADASTRO("CandidatoCadastro.fxml"),
    CANDIDATO_PESQUISA("CandidatoPesquisa.fxml"),
    CARGO_CADASTRO("CargoCadastro.fxml"),
    CARGO_PESQUISA("CargoPesquisa.fxml"),
    CLIENTE_CADASTRO("ClienteCadastro.fxml"),
    CLIENTE_PESQUISA("ClientePesquisa.fxml"),
    LOGIN("Login.fxml"),
    PROGRESS_SAMPLE("Progress.fxml");
    private String localizacao;
    private FXMLEnum(String localizacao) {
        this.localizacao=localizacao;
    }
    public URL getLocalizacao() {
        return getClass().getResource("/fxml/"+localizacao);
    }
}