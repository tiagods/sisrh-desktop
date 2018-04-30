package com.plkrhone.sisrh.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plkrhone.sisrh.model.Usuario;

public class UserSession {
	
	Logger log = LoggerFactory.getLogger(UserSession.class);
	
	private static Usuario usuario;
	private static UserSession instance;
	public static UserSession getInstance(){
		if(instance==null){
			instance = new UserSession();
		}
		return instance;
	}
	private UserSession() {}
	public void setUsuario(Usuario usuario){
		UserSession.usuario=usuario;
		if(log.isDebugEnabled()) {
			log.debug("Usuario logado: "+usuario.getNome());
		}
	}
	public Usuario getUsuario(){
		return usuario;
	}
	public static void main(String[] args){
		String s = getInstance().getClass().getClassLoader().getResource("ftp.properties").getFile();
		File f = new File(s);
		if(f.exists())
			System.out.println("Existe");
		else{
			System.out.println(f.getAbsolutePath());
		}
	}
}
