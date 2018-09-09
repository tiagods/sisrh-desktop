package com.plkrhone.sisrh.config.enums;

import java.net.URL;

public enum IconsEnum {
    BUTTON_CLIP("button_clip","Visualizar arquivo digitalizado"),
    BUTTON_EDIT("button_edit","Editar registro"),
    BUTTON_REMOVE("button_trash","Excluir registro"),
    BUTTON_OK("button_ok","Ok"),
    BUTTON_SEARCH("button_search","Buscar"),
    BUTTON_TAREFA_EMAIL("tarefas_email","E-Mail"),
    BUTTON_TAREFA_FONE("tarefas_fone","Telefone");

    private String localizacao;
    private String detalhes;
    private IconsEnum(String localizacao,String detalhes) {
        this.localizacao=localizacao;
        this.detalhes = detalhes;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public URL getLocalizacao() {
        return getClass().getResource("/fxml/imagens/"+localizacao+".png");
    }
}
