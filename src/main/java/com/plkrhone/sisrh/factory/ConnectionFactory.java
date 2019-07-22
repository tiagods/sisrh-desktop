package com.plkrhone.sisrh.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.plkrhone.sisrh.config.SecundaryDatabaseConfig;
import com.plkrhone.sisrh.config.init.DataBaseConfig;

public class ConnectionFactory {
	
	public static final String TABLENAME="versao_app";
	
	public Connection getConnection(){
		try{
			DataBaseConfig s = DataBaseConfig.getInstance();
			Class.forName(s.getValue("javax.persistence.jdbc.driver"));
			return DriverManager.getConnection(
					s.getValue("javax.persistence.jdbc.url"),
					s.getValue("javax.persistence.jdbc.user"),
					s.getValue("javax.persistence.jdbc.password"));
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}
