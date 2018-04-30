package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.plkrhone.sisrh.model.CursoSuperior;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.CursoSuperiorDAO;

public class CursosSuperioresImp extends AbstractRepository<CursoSuperior, Long> implements CursoSuperiorDAO{

	public CursosSuperioresImp(EntityManager manager) {
		super(manager);
	}

	@Override
	public CursoSuperior findByNome(String nome) {
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CursoSuperior> getAll() {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(CursoSuperior.class);
		criteria.addOrder(Order.asc("nome"));
		return (List<CursoSuperior>)criteria.list();
	}

}
