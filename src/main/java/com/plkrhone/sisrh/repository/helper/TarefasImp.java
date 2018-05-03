package com.plkrhone.sisrh.repository.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Cliente;
import com.plkrhone.sisrh.model.Tarefa;
import com.plkrhone.sisrh.model.Usuario;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.TarefaDAO;

public class TarefasImp extends AbstractRepository<Tarefa, Long> implements TarefaDAO {

	public TarefasImp(EntityManager manager) {
		super(manager);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Tarefa findById(Long id) {
//		Query query = getEntityManager().createQuery("from Tarefa as a "
//				+ "LEFT JOIN FETCH a.anuncioEntrevista "
//				+ "where a.id=:id");
//		query.setParameter("id", id);
//		Tarefa a = (Tarefa)query.getSingleResult();
		return super.findById(id);
	}
	@Override
	public Tarefa save(Tarefa e) {
		getEntityManager().getTransaction().begin();
		Tarefa ex = super.save(e);
		getEntityManager().getTransaction().commit();
		return ex;
	}
	@Override
	public void remove(Tarefa e) {
		getEntityManager().getTransaction().begin();		
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tarefa> filtrar(Anuncio anuncio, Anuncio.Cronograma cronograma, Anuncio.AnuncioStatus anuncioStatus, 
			Cliente cliente, Usuario atendente, LocalDateTime dataInicio, LocalDateTime dataFim, int finalizado) {
		Criteria c = getEntityManager().unwrap(Session.class).createCriteria(Tarefa.class);
		c.createAlias("anuncio", "anu");		
		if(anuncio!=null)
			c.add(Restrictions.eq("anuncio",anuncio));
		if(cronograma!=null)
			c.add(Restrictions.eq("cronograma", cronograma));
		if(anuncioStatus!=null)
			c.add(Restrictions.eq("anu.anuncioStatus",anuncioStatus));
		if(cliente!=null)
			c.add(Restrictions.eq("anu.cliente",cliente));
		if(atendente!=null)
			c.add(Restrictions.eq("atendente",atendente));
		if(dataInicio!=null && dataFim!=null)
			c.add(Restrictions.between("dataInicioEvento",GregorianCalendar.from(dataInicio.atZone(ZoneId.systemDefault())),GregorianCalendar.from(dataFim.atZone(ZoneId.systemDefault()))));
		if(dataFim!=null)
			c.add(Restrictions.between("dataFimEvento",GregorianCalendar.from(dataInicio.atZone(ZoneId.systemDefault())),GregorianCalendar.from(dataFim.atZone(ZoneId.systemDefault()))));
		if(finalizado!=-1)
			c.add(Restrictions.eq("finalizado",finalizado));
		return c.list();
	};;
	@Override
	public long count(int finalizado) {
		Query query= getEntityManager().createQuery("select count(d.finalizado) from Tarefa as d where d.finalizado=:tarefaStatus");
		query.setParameter("finalizado", finalizado);
		return (long)query.getSingleResult();
	}

}
