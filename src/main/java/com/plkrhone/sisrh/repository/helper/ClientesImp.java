package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Cliente;
import com.plkrhone.sisrh.model.ClienteSetor;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.ClienteDAO;

public class ClientesImp extends AbstractRepository<Cliente, Long> implements ClienteDAO {

	public ClientesImp(EntityManager manager) {
		super(manager);
	}
	@Override
	public Cliente findById(Long id) {
		Query query = getEntityManager().createQuery("from Cliente as a "
				+ "LEFT JOIN FETCH a.anuncioSet where a.id=:id");
		query.setParameter("id", id);
		return (Cliente) query.getSingleResult();
	}
	@Override
	public Cliente save(Cliente e) {
		getEntityManager().getTransaction().begin();
		e = super.save(e);
		getEntityManager().getTransaction().commit();
		return e;
	}

	@Override
	public void remove(Cliente e) {
		// TODO Auto-generated method stub
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}

	@Override
	public Cliente findByNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long contar(int situacao) {
		Query query = getEntityManager()
				.createQuery("select count(d.situacao) from Cliente as d where d.situacao=:situacao");
		query.setParameter("situacao", situacao);
		return (long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> filtrar(int situacao, ClienteSetor setor, String texto, String filtrarPor, String ordenacao) {
		Criteria c = getEntityManager().unwrap(Session.class).createCriteria(Cliente.class);
		if (situacao != -1)
			c.add(Restrictions.eq("situacao", situacao));
		if (texto != null && !texto.equals("")) {
			String campo = "nome";
			if (filtrarPor.equals("ID")) {
				try {
					int id = Integer.parseInt(texto);
					c.add(Restrictions.eq("id", id));
				} catch (Exception e) {
				}
			} else {
				if (filtrarPor.equals("Nome"))
					campo = "nome";
				else if (filtrarPor.equals("Responsavel"))
					campo = "responsavel";
				c.add(Restrictions.ilike(campo, texto, MatchMode.ANYWHERE));
			}
		}
		if (setor != null && setor.getId()!=-1L)
			c.add(Restrictions.eq("setor", setor));
		c.addOrder(Order.asc(ordenacao));
		return c.list();
	}
	public Cliente findByCnpj(String cnpj) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Cliente.class);
		criteria.add(Restrictions.ilike("cnpj", cnpj));
		return (Cliente) criteria.uniqueResult();
	}
}
