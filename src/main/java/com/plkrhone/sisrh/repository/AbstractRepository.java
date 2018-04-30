package com.plkrhone.sisrh.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.plkrhone.sisrh.model.AbstractEntity;

public abstract class AbstractRepository<Entity extends AbstractEntity, PK extends Number> {
	private EntityManager em;
	private Class<Entity> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractRepository(EntityManager manager) {
		this.em = manager;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<Entity>) genericSuperclass.getActualTypeArguments()[0];
	}

	public Entity save(Entity e) {
		if (e.getId() != null)
			return em.merge(e);
		else {
			em.persist(e);
			return e;
		}
	}

	public void remove(Entity e) {
		em.remove(e);
	}

	public Entity findById(PK id) {
		return em.find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<Entity> getAll() {
		Query query = getEntityManager().createQuery("SELECT o FROM " + entityClass.getName() + " o");
		return (List<Entity>) query.getResultList();
	}

	protected EntityManager getEntityManager() {
		return this.em;
	}
}
