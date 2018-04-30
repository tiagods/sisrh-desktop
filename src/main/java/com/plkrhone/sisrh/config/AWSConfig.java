package com.plkrhone.sisrh.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AWSConfig {
	private static AWSConfig instance;
	private Properties props;
	
	public static AWSConfig getInstance(){
		if(instance==null)
			instance= new AWSConfig();
		return instance;
	}
	private AWSConfig(){
		initializer();
	}
	private void initializer(){
		try {
			props = new Properties();
			InputStream inStream = getClass().getResource("/credentials/aws.properties").openStream();
			props.load(inStream);			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getValue(String key) {
		return props.getProperty(key);
	}	
}
