package com.plkrhone.sisrh.config.enums;

import java.net.URL;

public enum IconsEnum {
    BUTTON_EDIT("button_edit"),
    BUTTON_REMOVE("button_trash"),
    BUTTON_OK("button_ok"),
    BUTTON_SEARCH("button_search"),
    BUTTON_TAREFA_EMAIL("tarefas_email"),
    BUTTON_TAREFA_FONE("tarefas_fone");

    private String localizacao;
    private IconsEnum(String localizacao) {
        this.localizacao=localizacao;
    }
    public URL getLocalizacao() {
        return getClass().getResource("/fxml/imagens/"+localizacao+".png");
    }
}
