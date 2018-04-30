package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Vaga;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.Paginacao;
import com.plkrhone.sisrh.repository.interfaces.VagaDAO;

public class VagasImp extends AbstractRepository<Vaga, Long> implements VagaDAO {

	public VagasImp(EntityManager manager) {
		super(manager);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Vaga> getAll() {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Vaga.class);
		criteria.addOrder(Order.asc("nome"));
		return (List<Vaga>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Vaga> getVagasByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Vaga.class);
		if(!nome.equals(""))
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		return (List<Vaga>)criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Vaga> getVagasByNome(String nome, Paginacao pagination) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Vaga.class);
		int totalRegistroPorPagina = pagination.getPageSize();
		int primeiroRegistro = pagination.getPageNumber() * totalRegistroPorPagina;
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistroPorPagina);
		return (List<Vaga>) criteria.list();

	}
	@Override
	public Vaga findByNome(String vaga) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Vaga.class);
		criteria.add(Restrictions.ilike("nome", vaga));
		return (Vaga) criteria.uniqueResult();
	}
	@Override
	public Vaga save(Vaga e) {
		getEntityManager().getTransaction().begin();
		Vaga v = super.save(e);
		getEntityManager().getTransaction().commit();
		return v;
	}
	@Override
	public void remove(Vaga e) {
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}
}
