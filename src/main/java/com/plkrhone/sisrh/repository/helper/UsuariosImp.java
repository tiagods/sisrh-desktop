package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.plkrhone.sisrh.repository.Usuarios;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Usuario;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.Paginacao;
import com.plkrhone.sisrh.repository.interfaces.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class UsuariosImp implements UsuarioDAO{

	@PersistenceContext
	EntityManager entityManager;

	public List<Usuario> getUsuariosByNome(String nome, Paginacao pagination) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		int totalRegistroPorPagina = pagination.getPageSize();
		int primeiroRegistro = pagination.getPageNumber() * totalRegistroPorPagina;
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistroPorPagina);
		return (List<Usuario>) criteria.list();

	}

	public Usuario findByNome(String nome) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("nome", nome));
		return (Usuario) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getUsuariosByNome(String nome) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.addOrder(Order.asc("nome"));
		return (List<Usuario>) criteria.list();
	}

	public List<Usuario> filtrar(String nome, int inativo, String ordem) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		if (nome!=null && !nome.trim().equals(""))
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.START));
		if (inativo == 1 || inativo == 0)
			criteria.add(Restrictions.eq("inativo", inativo==1));
		criteria.addOrder(Order.asc(ordem));
		return (List<Usuario>) criteria.list();
	}

}
