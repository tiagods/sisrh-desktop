package com.plkrhone.sisrh.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/**
 *
 * @author Tiago
 */
public class RandomicoUtil {
	
	private static RandomicoUtil instance;
	public static RandomicoUtil getInstance() {
		if(instance==null)
			instance = new RandomicoUtil();
		return instance;
	}
    public String gerarSerial(String aux){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String data = sdf.format(new Date());
        int tamanhoDefinido = 64-aux.length()-data.length();//10 representa o tamanho da data
        String alfabeto = "abcdefghijklmnopqrstuvxiz";
        String maiuscula = alfabeto.toUpperCase();
        String numeros = "0123456789";
        String expressao = alfabeto+maiuscula+numeros;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i <tamanhoDefinido; i++){
            int valor = random.nextInt(expressao.length());
            builder.append(expressao.substring(valor, valor+1));
        }
        return aux+builder.toString()+data;
    }
}
