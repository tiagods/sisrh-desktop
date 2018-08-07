package com.plkrhone.sisrh.repository.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Cargo;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AnuncioDAO;

public class AnunciosImp extends AbstractRepository<Anuncio, Long> implements AnuncioDAO{

	public AnunciosImp(EntityManager manager) {
		super(manager);
	}
	@Override
	public Anuncio findById(Long id) {
		Query query = getEntityManager().createQuery("from Anuncio as a "
				+ "LEFT JOIN FETCH a.curriculoSet LEFT JOIN FETCH a.entrevistaSet "
				+ "LEFT JOIN FETCH a.preSelecaoSet LEFT JOIN FETCH a.cronogramaDetails "
				+ "where a.id=:id");
		query.setParameter("id", id);
		Anuncio a = (Anuncio)query.getSingleResult();
		return a;
	}
	@Override
	public Anuncio save(Anuncio e) {
		getEntityManager().getTransaction().begin();
		Anuncio ex = super.save(e);
		getEntityManager().getTransaction().commit();
		return ex;
	}
	@Override
	public void remove(Anuncio e) {
		getEntityManager().getTransaction().begin();		
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}
	@Override
	public long count(Anuncio.AnuncioStatus anuncioStatus) {
		Query query= getEntityManager().createQuery("select count(d.anuncioStatus) from Anuncio as d where d.anuncioStatus=:anuncioStatus");
		query.setParameter("anuncioStatus", anuncioStatus);
		return (long)query.getSingleResult();
	}
	@Override
	public long count(Anuncio.Cronograma cronograma) {
		Query query= getEntityManager().createQuery("select count(d.anuncioStatus) from Anuncio as d where d.cronograma=:cronograma");
		query.setParameter("cronograma", cronograma);
		return (long)query.getSingleResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Anuncio> filtrar(Anuncio.Cronograma cronograma, Anuncio.AnuncioStatus anuncioStatus, int clienteSituacao, Cargo cargo,
			String data, LocalDate inicial, LocalDate fim, String pesquisa, String pesquisarPor) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Anuncio.class);
		if(cronograma!=null)
			criteria.add(Restrictions.eq("cronograma",cronograma));
		if(anuncioStatus!=null)
			criteria.add(Restrictions.eq("anuncioStatus",anuncioStatus));
		if(clienteSituacao!=-1)
			criteria.createAlias("cliente", "cli").add(Restrictions.eq("cli.situacao",clienteSituacao));
		if(cargo !=null)
			criteria.add(Restrictions.eq("cargo", cargo));
		if(data!=null && !data.equals("") && inicial!=null && fim!=null) {
			if(inicial.isBefore(fim)) {
				Calendar d1 = GregorianCalendar.from(inicial.atStartOfDay(ZoneId.systemDefault()));
				Calendar d2 = GregorianCalendar.from(fim.atStartOfDay(ZoneId.systemDefault()));
				
				String pr=null;
				
				switch(data){
					case "Data de Abertura":
					pr="dataAbertura";
					break;
					case "Data de Admissão":
						pr="dataAdmissao";
						break;
					case "Data de Encerramento":
							pr="dataEncerramento";
							break;
					default:
						break;
				}
				
				criteria.add(Restrictions.between(pr, d1, d2));
			}
			else throw new IllegalArgumentException("Valor da data informada não é valida");
		}
		if(pesquisarPor!=null && !pesquisarPor.equals("")) {
			if(!pesquisa.trim().equals("")) {
				String filtro=null;
				MatchMode mM = MatchMode.ANYWHERE;
				switch(pesquisa) {
				case "Nome":
					filtro = "nome";
					break;
				case "Nome do Cargo":
					criteria.createAlias("formularioRequisicao", "vg");
					filtro = "vg.cargo.nome";
					//mM = MatchMode.START;
					break;
				case "Nome do Cliente":
					criteria.createAlias("cliente", "clie");
					filtro = "clie.nome";
					break;
				}
				if(filtro!=null)
					criteria.add(Restrictions.ilike(filtro, pesquisa, mM));
			}
		}
		criteria.addOrder(Order.desc("dataAbertura"));
		return criteria.list();
	}
	
	

}