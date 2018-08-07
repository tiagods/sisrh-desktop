package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.plkrhone.sisrh.model.Curso;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.CursoDAO;

public class CursosSuperioresImp extends AbstractRepository<Curso, Long> implements CursoDAO {

	public CursosSuperioresImp(EntityManager manager) {
		super(manager);
	}

	@Override
	public Curso findByNome(String nome) {
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> getAll() {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Curso.class);
		criteria.addOrder(Order.asc("nome"));
		return (List<Curso>)criteria.list();
	}

}
