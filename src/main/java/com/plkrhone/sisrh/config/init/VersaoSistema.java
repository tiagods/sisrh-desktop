package com.plkrhone.sisrh.config.init;

public class VersaoSistema {
    private final String nome="SISRH";
    private final String versao="1.0.1";
    private final String data="27/08/2018";
    private final String versaoBanco="1.0.0";
    private final String detalhes="Versao 1.0";
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }
    /**
     * @return the versao
     */
    public String getVersao() {
        return versao;
    }

    public String getDate(){
        return data;
    }
    /**
     * @return the versaoBanco
     */
    public String getVersaoBanco() {
        return versaoBanco;
    }
    public String getDetalhes(){
        return this.detalhes;
    }
}
