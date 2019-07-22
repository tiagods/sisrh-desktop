package com.plkrhone.sisrh.config.init;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

public class JPAConfig {
	private static final String PERSISTENCE_UNIT_NAME = "sisrh";
	private static JPAConfig instance;

	private static EntityManagerFactory factory;

	private JPAConfig() {
	}
	static {
		Map<String,String> anotacao = DataBaseConfig.getInstance().getMap();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,anotacao);
	}
	public static JPAConfig getInstance() {
		if(instance==null)
			instance = new JPAConfig();
		return instance;
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

	public EntityManager createManager() {
		return factory.createEntityManager();
	}
}
