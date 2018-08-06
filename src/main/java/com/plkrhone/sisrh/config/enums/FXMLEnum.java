package com.plkrhone.sisrh.config.enums;

import java.net.URL;

public enum FXMLEnum {
    ANUNCIO_PESQUISA("AnuncioPesquisa");
    private String localizacao;
    private FXMLEnum(String localizacao) {
        this.localizacao=localizacao;
    }
    public URL getLocalizacao() {
        return getClass().getResource("/fxml/"+localizacao+".fxml");
    }
}