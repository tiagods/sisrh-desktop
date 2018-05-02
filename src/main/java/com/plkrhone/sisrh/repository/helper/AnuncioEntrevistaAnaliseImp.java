package com.plkrhone.sisrh.repository.helper;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAnalise;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AnuncioEntrevistaAnaliseDAO;

public class AnuncioEntrevistaAnaliseImp extends AbstractRepository<AnuncioEntrevistaAnalise, Long> implements AnuncioEntrevistaAnaliseDAO{

	public AnuncioEntrevistaAnaliseImp(EntityManager manager) {
		super(manager);
	}
	@Override
	public AnuncioEntrevistaAnalise findById(Long id) {
		Query query = getEntityManager().createQuery("from AnuncioEntrevistaAnalise as a "
				+ "LEFT JOIN FETCH a.formularios LEFT JOIN FETCH a.perfis "
				+ "where a.id=:id");
		query.setParameter("id", id);
		AnuncioEntrevistaAnalise a = (AnuncioEntrevistaAnalise)query.getSingleResult();
		return a;
	}
	@Override
	public AnuncioEntrevistaAnalise save(AnuncioEntrevistaAnalise e) {
		getEntityManager().getTransaction().begin();
		AnuncioEntrevistaAnalise v = super.save(e);
		getEntityManager().getTransaction().commit();
		return v;
	}

	@Override
	public void remove(AnuncioEntrevistaAnalise e) {
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}

}
