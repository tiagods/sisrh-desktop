package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.plkrhone.sisrh.model.Curso;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.CursoDAO;
import org.hibernate.criterion.Restrictions;

public class CursosImpl extends AbstractRepository<Curso, Long> implements CursoDAO {

	public CursosImpl(EntityManager manager) {
		super(manager);
	}

	@Override
	public Curso save(Curso e) {
		getEntityManager().getTransaction().begin();
		e = super.save(e);
		getEntityManager().getTransaction().commit();
		return e;
	}

	@Override
	public Curso findByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Curso.class);
		criteria.add(Restrictions.eq("nome",nome));
		return (Curso)criteria.uniqueResult();
	}
	@Override
	public List<Curso> listByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Curso.class);
		criteria.add(Restrictions.ilike("nome",nome, MatchMode.ANYWHERE));
		return (List<Curso>)criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> getAll() {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Curso.class);
		criteria.addOrder(Order.asc("nome"));
		return (List<Curso>)criteria.list();
	}

	public List<Curso> findByNivel(Curso.Nivel nivel){
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Curso.class);
		criteria.add(Restrictions.eq("nivel",nivel));
		return (List<Curso>)criteria.list();
	}

}
