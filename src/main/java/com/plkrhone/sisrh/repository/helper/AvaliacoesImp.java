package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Avaliacao;
import com.plkrhone.sisrh.model.AvaliacaoGrupo;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AvaliacaoDAO;

public class AvaliacoesImp extends AbstractRepository<Avaliacao, Long> implements AvaliacaoDAO {

	public AvaliacoesImp(EntityManager manager) {
		super(manager);
	}

	@Override
	public Avaliacao save(Avaliacao e) {
		getEntityManager().getTransaction().begin();
		Avaliacao v = super.save(e);
		getEntityManager().getTransaction().commit();
		return v;
	}

	@Override
	public void remove(Avaliacao e) {
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}

	@Override
	public Avaliacao findByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Avaliacao.class);
		criteria.add(Restrictions.ilike("nome", nome, MatchMode.START));
		return (Avaliacao) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Avaliacao> filtrar(String nome, Avaliacao.AvaliacaoTipo tipo, AvaliacaoGrupo grupo) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Avaliacao.class);
		if (!nome.equals(""))
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		if (tipo!=null)
			criteria.add(Restrictions.eq("tipo", tipo));
		if (grupo != null)
			criteria.add(Restrictions.eq("grupo", grupo));
		criteria.addOrder(Order.asc("nome"));
		return (List<Avaliacao>) criteria.list();
	}

}
