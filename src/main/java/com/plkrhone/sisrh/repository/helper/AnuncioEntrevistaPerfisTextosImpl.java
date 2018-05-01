package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaPerfilTexto;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AnuncioEntrevistaPerfilTextoDAO;
public class AnuncioEntrevistaPerfisTextosImpl extends AbstractRepository<AnuncioEntrevistaPerfilTexto,Long> implements AnuncioEntrevistaPerfilTextoDAO{

	public AnuncioEntrevistaPerfisTextosImpl(EntityManager manager) {
		super(manager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnuncioEntrevistaPerfilTexto> findByInativo(boolean inativo) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(AnuncioEntrevistaPerfilTexto.class);
		criteria.add(Restrictions.eq("inativo", inativo));
		criteria.addOrder(Order.asc("nome"));
		return criteria.list();
	}
	
}
