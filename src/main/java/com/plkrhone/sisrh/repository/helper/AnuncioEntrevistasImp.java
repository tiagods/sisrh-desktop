package com.plkrhone.sisrh.repository.helper;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AnuncioEntrevistaDAO;

public class AnuncioEntrevistasImp extends AbstractRepository<AnuncioEntrevista, Long> implements AnuncioEntrevistaDAO{
	public AnuncioEntrevistasImp(EntityManager manager) {
		super(manager);
		// TODO Auto-generated constructor stub
	}
	@Override
	public AnuncioEntrevista findById(Long id) {
		Query query = getEntityManager().createQuery("from AnuncioEntrevista as a "
				+ "LEFT JOIN FETCH a.avaliacao LEFT JOIN FETCH a.entrevista"
				+ "where a.id=:id");
		query.setParameter("id", id);
		AnuncioEntrevista a = (AnuncioEntrevista)query.getSingleResult();
		return a;
	}
}
