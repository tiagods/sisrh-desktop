package com.plkrhone.sisrh.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.plkrhone.sisrh.config.init.VersaoSistema;
import com.plkrhone.sisrh.factory.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class Atualizador {
	private String versaoDisponivel = "";
	private String detalhesVersao="";
	private String dataVersao;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private VersaoSistema versaoAtual = VersaoSistema.getInstance();
	
	private void atualizarAgora(){
    	
    }
	//comparação de versoes, retorna false se estiver atualizado
    public boolean atualizacaoPendente(){
    	return receberStatus().equals("Desatualizado");
    }
    
    public void iniciarAtualizacao() {
    	File updateNew = new File("update-1.jar");
		if(updateNew.exists()){
			Path pathI = Paths.get(updateNew.getAbsolutePath());
			File update = new File("update.jar");
			Path pathO = Paths.get(update.getAbsolutePath());
			try {
				Files.copy(pathI, pathO, StandardCopyOption.REPLACE_EXISTING);
				updateNew.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
    		Runtime.getRuntime().exec("java -jar update.jar ***");
    		System.exit(0);
    	}catch(IOException e){
    	}
	
    }
	private String receberStatus(){
		Connection con = new ConnectionFactory().getConnection();
		if(con==null){
			System.out.println("Sem link de comunicação");
			return "Sem Link de comunicação";
		}
		else{
			try{
			PreparedStatement ps = con.prepareStatement("select * from versao_app order by id desc limit 1");
			ResultSet rs = ps.executeQuery();
			if(rs!=null){
				if(rs.next()){
					if(rs.getString(2).equals(versaoAtual.getVersao())){
						return "Atualizado";
					}
					else{
						versaoDisponivel = rs.getString(2);
						detalhesVersao = rs.getString(3);
						dataVersao = sdf.format(rs.getDate(4));
						return "Desatualizado";
					}
				}
			}
			ps.close();
			}catch(SQLException e){
				e.printStackTrace();
				return "Comando invalido";
			}finally{
				if(con!=null){
					try{con.close();}catch(SQLException e){}
				}
			}
		}
		return "";
	}
	private String getVersaoDisponivel(){
		return versaoDisponivel;
	}
	private String getDetalhesVersao(){
		return detalhesVersao;
	}
	private String getDataVersao(){
		return dataVersao;
	}
}
