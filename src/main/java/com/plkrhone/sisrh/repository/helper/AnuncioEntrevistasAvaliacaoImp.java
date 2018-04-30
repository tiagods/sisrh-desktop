package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAvaliacao;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AnuncioEntrevistaAvaliacaoDAO;

public class AnuncioEntrevistasAvaliacaoImp extends AbstractRepository<AnuncioEntrevistaAvaliacao, Long> implements AnuncioEntrevistaAvaliacaoDAO{

	public AnuncioEntrevistasAvaliacaoImp(EntityManager manager) {
		super(manager);
	}
	@Override
	public AnuncioEntrevistaAvaliacao save(AnuncioEntrevistaAvaliacao e) {
		getEntityManager().getTransaction().begin();
		AnuncioEntrevistaAvaliacao v = super.save(e);
		getEntityManager().getTransaction().commit();
		return v;
	}
	
	@Override
	public void remove(AnuncioEntrevistaAvaliacao e) {
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
		
//		getEntityManager().getTransaction().begin();
//		Query query = getEntityManager().createQuery("delete from AnuncioEntrevistaAvaliacao where id=:id");
//		query.setParameter("id", e.getId());
//		query.executeUpdate();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AnuncioEntrevistaAvaliacao> findByAnuncioEntrevista(AnuncioEntrevista ae){
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(AnuncioEntrevistaAvaliacao.class);
		criteria.add(Restrictions.eq("anuncioEntrevista", ae));
		return criteria.list();
	}
}
