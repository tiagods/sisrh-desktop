package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaFormularioTexto;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AnuncioEntrevistaFormularioTextoDAO;

public class AnuncioEntrevistaFormulariosImpl extends AbstractRepository<AnuncioEntrevistaFormularioTexto,Long> implements AnuncioEntrevistaFormularioTextoDAO{

	public AnuncioEntrevistaFormulariosImpl(EntityManager manager) {
		super(manager);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AnuncioEntrevistaFormularioTexto> findByInativo(boolean inativo) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(AnuncioEntrevistaFormularioTexto.class);
		criteria.add(Restrictions.eq("inativo", inativo));
		criteria.addOrder(Order.asc("sequencia"));
		return criteria.list();
	}
	
}
