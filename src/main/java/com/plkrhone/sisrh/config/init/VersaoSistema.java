package com.plkrhone.sisrh.config.init;

import com.plkrhone.sisrh.config.PropsConfig;
import com.plkrhone.sisrh.config.enums.PropsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.plkrhone.sisrh.config.PropsConfig.getValue;

public class VersaoSistema extends PropsConfig {
    private static String nome="";
    private static String versao="";
    private static String data="";
    private static String versaoBanco="";
    private static String detalhes="";

    Logger log = LoggerFactory.getLogger(VersaoSistema.class);
    private static VersaoSistema instance;

    public static VersaoSistema getInstance() {
        if(instance == null)
            instance = new VersaoSistema(PropsEnum.CONFIG);
        return instance;
    }

    public VersaoSistema(PropsEnum propsEnum) {
        super(propsEnum);
        this.nome=getValue("sis.nome");
        this.versao=getValue("sis.versao");
        this.data=getValue("sis.data");
        this.versaoBanco=getValue("sis.banco");
        this.detalhes=getValue("sis.detalhes");
    }/**
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
