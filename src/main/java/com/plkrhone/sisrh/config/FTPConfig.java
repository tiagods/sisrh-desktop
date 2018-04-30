package com.plkrhone.sisrh.config;

import java.io.*;
import java.util.*;

public class FTPConfig {
	private static FTPConfig instance;
	private String file=getClass().getClassLoader().getResource("ftp.properties").getFile();
	
	public static FTPConfig getInstance(){
		if(instance==null)
			instance= new FTPConfig();
		return instance;
	}
	private FTPConfig(){
		initializer();
	}
	
	private void initializer(){
		File f = new File(file);
		Properties props = new Properties();
		InputStream inStream;
		try {
			inStream = new FileInputStream(f);
			props.load(inStream);
			
			Enumeration<Object> e = props.elements();
			while(e.hasMoreElements()){
				System.out.println(e.nextElement());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
