package com.plkrhone.sisrh.repository.helper;

import com.plkrhone.sisrh.model.CargoNivel;
import com.plkrhone.sisrh.model.ClienteSetor;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.CargoNivelDAO;
import com.plkrhone.sisrh.repository.interfaces.ClienteSetoresDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import java.util.List;

public class CargoNiveisImpl extends AbstractRepository<CargoNivel, Long> implements CargoNivelDAO {

	public CargoNiveisImpl(EntityManager manager) {
		super(manager);
	}
	
	@Override
	public CargoNivel save(CargoNivel e) {
		getEntityManager().getTransaction().begin();
		CargoNivel cs =  super.save(e);
		getEntityManager().getTransaction().commit();
		return cs;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CargoNivel> getAll() {
		Criteria c = getEntityManager().unwrap(Session.class).createCriteria(CargoNivel.class);
		c.addOrder(Order.asc("nome"));
		return (List<CargoNivel>) c.list();
	}
	@Override
	public CargoNivel findByNome(String nome) {
		Criteria c = getEntityManager().unwrap(Session.class).createCriteria(CargoNivel.class);
		c.add(Restrictions.eq("nome", nome));
		return (CargoNivel)c.uniqueResult();
	}
}
