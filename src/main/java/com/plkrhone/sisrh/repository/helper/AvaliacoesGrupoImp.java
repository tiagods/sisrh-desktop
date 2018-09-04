package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.avaliacao.AvaliacaoGrupo;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AvaliacaoGrupoDAO;

public class AvaliacoesGrupoImp extends AbstractRepository<AvaliacaoGrupo, Long> implements AvaliacaoGrupoDAO {

	public AvaliacoesGrupoImp(EntityManager manager) {
		super(manager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AvaliacaoGrupo> getAll() {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(AvaliacaoGrupo.class);
		criteria.addOrder(Order.asc("nome"));
		return (List<AvaliacaoGrupo>) criteria.list();
	}

	@Override
	public AvaliacaoGrupo findByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(AvaliacaoGrupo.class);
		criteria.add(Restrictions.ilike("nome", nome, MatchMode.START));
		return (AvaliacaoGrupo) criteria.uniqueResult();
	}

	@Override
	public AvaliacaoGrupo save(AvaliacaoGrupo e) {
		getEntityManager().getTransaction().begin();
		AvaliacaoGrupo v = super.save(e);
		getEntityManager().getTransaction().commit();
		return v;
	}

	@Override
	public void remove(AvaliacaoGrupo e) {
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}

}
