package com.plkrhone.sisrh.config.init;

import com.plkrhone.sisrh.config.PropsConfig;
import com.plkrhone.sisrh.config.enums.PropsEnum;

public class DataBaseConfig extends PropsConfig {
    private static DataBaseConfig instance;

    public static DataBaseConfig getInstance() {
        if(instance==null) instance = new DataBaseConfig();
        return instance;
    }
    public DataBaseConfig() {
        super(PropsEnum.DB);
    }
}
