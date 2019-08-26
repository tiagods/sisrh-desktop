package com.plkrhone.sisrh.controller.antigos;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plkrhone.sisrh.config.init.JPAConfig;

public class PersistenciaController {
	private EntityManager entityManager;
    //private boolean ownsPersistenceContext;
    private static Logger log = LoggerFactory.getLogger(PersistenciaController.class);
    protected void loadFactory() {
    	loadFactory(null);
    }
    protected void loadFactory(EntityManager entityManager) {
    	if (entityManager == null) {
            debug("Criando um contexto de persistencia (EntityManager).");
            this.entityManager = JPAConfig.getInstance().createManager();
            //this.ownsPersistenceContext = true;
        } else {
            debug("Utilizando contexto de persistencia (EntityManager) existente.");
            this.entityManager = entityManager;
            //this.ownsPersistenceContext = false;
        }
    }
    public EntityManager getManager() {
		return this.entityManager;
	}
//    public void preparePersist() {
//    	this.entityManager.getTransaction().begin();;
//    }
//    public void commit() {
//    	this.entityManager.getTransaction().commit();
//    }
    protected void close() {
        //if (ownsPersistenceContext && getPersistenceContext().isOpen()) {
    	try{
	    	if (getManager().isOpen()) {
	        	debug("Fechando o contexto de persistencia (EntityManager).");
	        	getManager().close();
	        }
    	}catch (Exception e) {
    		if(log.isErrorEnabled())
    			debug(e.getMessage());
		}
        //super.cleanUp();
    }
    private void debug(String message) {
    	if(log.isDebugEnabled()) {
    		log.debug(message);            
    	}
    }
}
