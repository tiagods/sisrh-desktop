package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import com.plkrhone.sisrh.model.Cargo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.Paginacao;
import com.plkrhone.sisrh.repository.interfaces.CargoDAO;

public class CargosImpl extends AbstractRepository<Cargo, Long> implements CargoDAO {

	public CargosImpl(EntityManager manager) {
		super(manager);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Cargo> getAll() {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Cargo.class);
		criteria.addOrder(Order.asc("nome"));
		return (List<Cargo>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cargo> getVagasByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Cargo.class);
		if(!nome.equals(""))
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		return (List<Cargo>)criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Cargo> getVagasByNome(String nome, Paginacao pagination) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Cargo.class);
		int totalRegistroPorPagina = pagination.getPageSize();
		int primeiroRegistro = pagination.getPageNumber() * totalRegistroPorPagina;
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistroPorPagina);
		return (List<Cargo>) criteria.list();

	}
	@Override
	public Cargo findByNome(String vaga) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Cargo.class);
		criteria.add(Restrictions.ilike("nome", vaga));
		return (Cargo) criteria.uniqueResult();
	}
	@Override
	public Cargo save(Cargo e) {
		getEntityManager().getTransaction().begin();
		Cargo v = super.save(e);
		getEntityManager().getTransaction().commit();
		return v;
	}
	@Override
	public void remove(Cargo e) {
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}
}
