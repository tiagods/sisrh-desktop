package com.plkrhone.sisrh.config;

public class VersaoConfig {
	private String versao="1.0.0";
	private String banco="1.0.0";
	private static VersaoConfig instance;
	
	public String getVersao() {
		return versao;
	}
	public String getBanco() {
		return banco;
	}
	public static VersaoConfig getInstance() {
		if(instance==null) instance=new VersaoConfig();
		return instance;
	}
}
