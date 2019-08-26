package com.plkrhone.sisrh.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;


/**
 * Created by Tiago on 08/08/2017.
 */
@Component
public class CriptografiaUtil {
	
	public static Logger log = org.slf4j.LoggerFactory.getLogger(CriptografiaUtil.class);
	public String criptografar(String senha){
        String criptografia;
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
            for (byte b : messageDigest) {
                builder.append(String.format("%02X", 0xFF & b));
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        criptografia = builder.toString();
        return criptografia;
	}
}
