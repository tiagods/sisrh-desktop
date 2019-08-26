package com.plkrhone.sisrh.config.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import com.plkrhone.sisrh.controller.antigos.PersistenciaController;

public class PaisesConfig extends PersistenciaController{
	private static PaisesConfig instance;
	private static Set<String> paises = new TreeSet<>();
	public static PaisesConfig getInstance(){
		if(instance == null)
			instance = new PaisesConfig();
		return instance;
	}
	private PaisesConfig(){
		initializer();
	}
	private void initializer(){
		try {
			InputStream inStream = getClass().getResource("/config/paises.properties").openStream();
			Scanner scanner = new Scanner(inStream);
			scanner.useDelimiter("\n");
			while(scanner.hasNext()) paises.add(scanner.next().trim());
				scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Set<String> getAll(){
		return paises;
	}
}
