package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Treinamento;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.TreinamentoDAO;

public class TreinamentosImp extends AbstractRepository<Treinamento, Long> implements TreinamentoDAO{

	public TreinamentosImp(EntityManager manager) {
		super(manager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Treinamento> getTreinamentosByNome(String nome) {
		Criteria c = getEntityManager().unwrap(Session.class).createCriteria(Treinamento.class);
		c.addOrder(Order.asc("nome"));
		return (List<Treinamento>) c.list();
	}
	@Override
	public Treinamento save(Treinamento e) {
		getEntityManager().getTransaction().begin();
		Treinamento t  = super.save(e);
		getEntityManager().getTransaction().commit();
		return t;
	}

	@Override
	public Treinamento findByNome(String nome) {
		Criteria c = getEntityManager().unwrap(Session.class).createCriteria(Treinamento.class);
		c.add(Restrictions.eq("nome",nome));
		return (Treinamento)c.uniqueResult();
	}

}
