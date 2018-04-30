package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.ClienteSetor;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.ClienteSetoresDAO;

public class ClienteSetoresImp extends AbstractRepository<ClienteSetor, Long> implements ClienteSetoresDAO {

	public ClienteSetoresImp(EntityManager manager) {
		super(manager);
	}
	
	@Override
	public ClienteSetor save(ClienteSetor e) {
		getEntityManager().getTransaction().begin();
		ClienteSetor cs =  super.save(e);
		getEntityManager().getTransaction().commit();
		return cs;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteSetor> getAll() {
		Criteria c = getEntityManager().unwrap(Session.class).createCriteria(ClienteSetor.class);
		c.addOrder(Order.asc("nome"));
		return (List<ClienteSetor>) c.list();
	}
	@Override
	public ClienteSetor findByNome(String nome) {
		Criteria c = getEntityManager().unwrap(Session.class).createCriteria(ClienteSetor.class);
		c.add(Restrictions.eq("nome", nome));
		return (ClienteSetor)c.uniqueResult();
	}
}
