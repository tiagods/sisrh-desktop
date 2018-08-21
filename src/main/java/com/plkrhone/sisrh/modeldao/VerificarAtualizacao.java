package com.plkrhone.sisrh.modeldao;

import com.plkrhone.sisrh.config.init.VersaoSistema;
import com.plkrhone.sisrh.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class VerificarAtualizacao {
	private String versaoDisponivel = "";
	private String detalhesVersao="";
	private String dataVersao;
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	public String receberStatus(VersaoSistema versao){
		Connection con = new ConnectionFactory().getConnection();
		if(con==null){
			System.out.println("Sem link de comunicacao");
			return "Sem Link de comunicação";
		}
		else{
			try{
			PreparedStatement ps = con.prepareStatement("select * from ATUALIZACAO");
			ResultSet rs = ps.executeQuery();
			if(rs!=null){
				if(rs.last()){
					if(rs.getString(2).equals(versao.getVersao())){
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
				return "Comando invalido";
			}finally{
				if(con!=null){
					try{con.close();}catch(SQLException e){}
				}
			}
		}
		return "";
	}
	public String versaoDisponivel(){
		return versaoDisponivel;
	}
	public String detalhesVersao(){
		return detalhesVersao;
	}
	public String dataVersao(){
		return dataVersao;
	}
}
