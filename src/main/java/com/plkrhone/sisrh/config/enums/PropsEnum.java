package com.plkrhone.sisrh.config.enums;

public enum PropsEnum {
    FTP("/credentials/ftp.properties"),
    MAIL("/credentials/mail.properties"),
    DB("/credentials/database.properties"),
    CONFIG("/config.properties");
    private String descricao;
    private PropsEnum(String descricao) {
        this.descricao=descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
