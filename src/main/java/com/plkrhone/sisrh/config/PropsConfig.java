package com.plkrhone.sisrh.config;

import com.plkrhone.sisrh.config.enums.PropsEnum;
import com.plkrhone.sisrh.config.enums.PropsInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class PropsConfig implements PropsInterface {
    private Logger log = LoggerFactory.getLogger(PropsConfig.class);
    private static Properties props = null;
    private static InputStream stream = null;

    public PropsConfig(PropsEnum propsEnum) {
        props = new Properties();
        fileLoad(propsEnum);
    }
    @Override
    public void fileLoad(PropsEnum propsEnum) {
        try {
            stream = getClass().getResource(propsEnum.getDescricao()).openStream();
            props.load(stream);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Falha ao carregar o arquivo de configurações do Banco de Dados - Atualizador");
        }
    }
    public static String getValue(String key) {
        return props.getProperty(key);
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        props.keySet().forEach(c->map.put(c.toString(),getValue(c.toString())));
        return map;
    }
}
