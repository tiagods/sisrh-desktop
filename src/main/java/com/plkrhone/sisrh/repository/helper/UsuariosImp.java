package com.plkrhone.sisrh.repository.helper;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Usuario;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.Paginacao;
import com.plkrhone.sisrh.repository.interfaces.UsuarioDAO;

public class UsuariosImp extends AbstractRepository<Usuario, Long> implements UsuarioDAO {

	public UsuariosImp(EntityManager manager) {
		super(manager);
	}

	@Override
	public Usuario save(Usuario e) {
		getEntityManager().getTransaction().begin();
		Usuario v = super.save(e);
		getEntityManager().getTransaction().commit();
		return v;
	}

	@Override
	public void remove(Usuario e) {
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosByNome(String nome, Paginacao pagination) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		int totalRegistroPorPagina = pagination.getPageSize();
		int primeiroRegistro = pagination.getPageNumber() * totalRegistroPorPagina;
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistroPorPagina);
		return (List<Usuario>) criteria.list();

	}

	public Usuario findByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("nome", nome));
		return (Usuario) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getUsuariosByNome(String nome) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.addOrder(Order.asc("nome"));
		return (List<Usuario>) criteria.list();
	}

	@Override
	public Usuario findByLoginAndSenha(String login, String senha) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("login", login));
		criteria.add(Restrictions.ilike("senha", senha));
		return (Usuario) criteria.uniqueResult();
	}

	public Usuario findByLogin(String login) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		criteria.add(Restrictions.ilike("login", login));
		return (Usuario) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrar(String nome, int ativo, String ordem) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Usuario.class);
		if (nome!=null && !nome.trim().equals(""))
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.START));
		if (ativo == 1 || ativo == 0)
			criteria.add(Restrictions.eq("ativo", ativo));
		criteria.addOrder(Order.asc(ordem));
		return (List<Usuario>) criteria.list();
	}

}
