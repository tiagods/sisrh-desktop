package com.plkrhone.sisrh.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.plkrhone.sisrh.config.SecundaryDatabaseConfig;

public class ConnectionFactory {
	
	public static final String TABLENAME="versao_app";
	
	public Connection getConnection(){
		try{
			SecundaryDatabaseConfig s = SecundaryDatabaseConfig.getInstance();
			Class.forName(s.getValue("classForName"));
			return DriverManager.getConnection(s.getValue("url"), s.getValue("user"), s.getValue("password"));
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}
