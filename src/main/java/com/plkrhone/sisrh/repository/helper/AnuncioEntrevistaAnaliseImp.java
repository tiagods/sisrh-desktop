package com.plkrhone.sisrh.repository.helper;

import javax.persistence.EntityManager;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAnalise;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AnuncioEntrevistaAnaliseDAO;

public class AnuncioEntrevistaAnaliseImp extends AbstractRepository<AnuncioEntrevistaAnalise, Long> implements AnuncioEntrevistaAnaliseDAO{

	public AnuncioEntrevistaAnaliseImp(EntityManager manager) {
		super(manager);
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
