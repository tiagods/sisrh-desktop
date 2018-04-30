package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Cidade;
import com.plkrhone.sisrh.model.Estado;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.CidadeDAO;

public class CidadesImp extends AbstractRepository<Cidade, Long> implements CidadeDAO {

	public CidadesImp(EntityManager manager) {
		super(manager);
	}

	@Override
	public Cidade findByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Cidade.class);
		criteria.add(Restrictions.eq("nome", nome));
		return (Cidade) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cidade> findByEstado(Estado estado) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Cidade.class);
		criteria.add(Restrictions.eq("estado", estado));
		return (List<Cidade>)criteria.list();
	}

}
