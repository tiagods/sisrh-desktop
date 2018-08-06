package com.plkrhone.sisrh.config.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UsuarioCache {
    private static UsuarioCache instance;
    private File userCache = new File(System.getProperty("user.dir")+"/usercache.properties");
    private Properties props;

    public static UsuarioCache getInstance() {
        if (instance == null) instance = new UsuarioCache();
        return instance;
    }
    private UsuarioCache(){
        try {
            if(!userCache.exists())
                save("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(String value) throws IOException{
        FileOutputStream fr = new FileOutputStream(userCache);
        props = new Properties();
        props.setProperty("lastLogin", value);
        props.store(fr, "Properties");
        fr.close();
    }
    public String load() throws IOException{
        FileInputStream in = new FileInputStream(userCache);
        props = new Properties();
        props.load(in);
        in.close();
        return props.getProperty("lastLogin");
    }
    public Properties getProps() {
        return props;
    }
}
