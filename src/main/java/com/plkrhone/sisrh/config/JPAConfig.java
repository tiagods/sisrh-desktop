package com.plkrhone.sisrh.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConfig {
	private static final String PERSISTENCE_UNIT_NAME = "sisrh";
	private static JPAConfig instance;

	private static EntityManagerFactory factory;

	private JPAConfig() {
	}
	static {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
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
