package com.plkrhone.sisrh.config;

import com.plkrhone.sisrh.config.enums.PropsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class FTPConfig extends PropsConfig{
	Logger log = LoggerFactory.getLogger(FTPConfig.class);
	private static FTPConfig instance;
	public static FTPConfig getInstance() {
		if(instance == null)
			instance = new FTPConfig();
		return instance;
	}
	private FTPConfig() {
		super(PropsEnum.FTP);
	}
}
