package com.plkrhone.sisrh.repository.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.plkrhone.sisrh.model.Cargo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.CandidatoDAO;

public class CandidatosImp extends AbstractRepository<Candidato, Long> implements CandidatoDAO {

	public CandidatosImp(EntityManager manager) {
		super(manager);
	}

	@Override
	public Candidato findById(Long id) {
		Query query = getEntityManager().createQuery("from Candidato as a "
				+ "LEFT JOIN FETCH a.historicos LEFT JOIN FETCH a.cursos "
				+ "where a.id=:id");
		query.setParameter("id",id);
		return (Candidato)query.getSingleResult();
	}

	@Override
	public Candidato save(Candidato e) {
		getEntityManager().getTransaction().begin();
		e = super.save(e);
		getEntityManager().getTransaction().commit();
		return e;
	}

	@Override
	public void remove(Candidato e) {
		// TODO Auto-generated method stub
		getEntityManager().getTransaction().begin();
		super.remove(e);
		getEntityManager().getTransaction().commit();
	}

	@Override
	public Candidato findByEmail(String value) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Candidato.class);
		criteria.add(Restrictions.eq("pessoaFisica.email", value));
		return (Candidato) criteria.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Candidato> filtrar(Cargo objetivo, Cargo experiencia, String sexo, int idadeMin,
                                   int idadeMax, String indicacao, Candidato.Escolaridade escolaridadeMin, Candidato.Escolaridade escolaridadeMax,
                                   LocalDate dataCurriculoMin, LocalDate dataCurriculoMax, String buscarPor, String valorPesquisa, boolean indisponivel) {
		// TODO Auto-generated method stub
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Candidato.class);
		
		if(experiencia!=null || objetivo!=null) {
		Disjunction disj = Restrictions.disjunction();
			if(objetivo!=null && objetivo.getId()!=-1) {
				disj.add(Restrictions.eq("objetivo1",objetivo)); 
				disj.add(Restrictions.eq("objetivo2",objetivo));
				disj.add(Restrictions.eq("objetivo3",objetivo));
			}
			if(experiencia!=null && experiencia.getId()!=-1) {
				disj.add(Restrictions.eq("cargo1",experiencia)); 
				disj.add(Restrictions.eq("cargo2",experiencia));
				disj.add(Restrictions.eq("cargo3",experiencia));
			}	
			criteria.add(disj);
		}
//		if(experiencia!=null) {
//			criteria.add(
//					Restrictions.or(
//							Restrictions.eq("cargo1",experiencia), 
//							Restrictions.eq("cargo2",experiencia),
//							Restrictions.eq("cargo3",experiencia)
//							)
//					);
//		}
		if(!sexo.equalsIgnoreCase("qualquer")) criteria.add(Restrictions.ilike("sexo", sexo));
		
		if(idadeMin>0 || idadeMax>0) {
			LocalDate localDate = LocalDate.now();
			LocalDate iMin = localDate.plusYears(-idadeMin);
			LocalDate iMax = localDate.plusYears((idadeMax==0?-99:-idadeMax));
			
			criteria.add(Restrictions.between("dataNascimento", 
							GregorianCalendar.from(iMax.atStartOfDay(ZoneId.systemDefault())),
							GregorianCalendar.from(iMin.atStartOfDay(ZoneId.systemDefault()))
						)
			);
		}
		if(!indicacao.toLowerCase().equals("qualquer")) {
			int ind = indicacao.toLowerCase().equals("sim")?1:0;
			criteria.add(Restrictions.eq("indicacao", ind));
		}
		if(escolaridadeMin!=null && escolaridadeMin!=Candidato.Escolaridade.FUNDAMENTAL_INCOMPLETO) {
			List<Candidato.Escolaridade> escolaridade=new ArrayList<>();
			for(Candidato.Escolaridade esc : Candidato.Escolaridade.values()) 
				if(esc.getValor()<escolaridadeMin.getValor()) escolaridade.add(esc);//limitar a formação minima
			criteria.add(Restrictions.not(Restrictions.in("escolaridade",escolaridade)));
		}
		if(escolaridadeMax!=null && escolaridadeMax!=Candidato.Escolaridade.DOUTORADO_COMPLETO) {
			List<Candidato.Escolaridade> escolaridade=new ArrayList<>();
			for(Candidato.Escolaridade esc : Candidato.Escolaridade.values()) 
				if(esc.getValor()>escolaridadeMax.getValor()) escolaridade.add(esc);//limitar a formação máxima
			criteria.add(Restrictions.not(Restrictions.in("escolaridade",escolaridade)));
		}
		if(dataCurriculoMin!=null) {
			LocalDateTime timeInicio = dataCurriculoMin.atTime(0, 0, 0);
			LocalDateTime timeFim = dataCurriculoMax==null?LocalDate.now().atTime(23, 59, 59):dataCurriculoMax.atTime(23, 59, 59);
			criteria.add(
					Restrictions.between("ultimaModificacao", 
							GregorianCalendar.from(timeInicio.atZone(ZoneId.systemDefault())),
							GregorianCalendar.from(timeFim.atZone(ZoneId.systemDefault()))
							)
					);
		}
		if(valorPesquisa.trim().length()>1) {
			if(buscarPor.equalsIgnoreCase("telefone") ){
				criteria.add(Restrictions.ilike("pessoaFisica.telefone", valorPesquisa, MatchMode.START));
			}
			else if(buscarPor.equalsIgnoreCase("email") ){
				criteria.add(Restrictions.ilike("pessoaFisica.email", valorPesquisa, MatchMode.ANYWHERE));
			}
			
			else {
				criteria.add(Restrictions.ilike(buscarPor.toLowerCase(), valorPesquisa, MatchMode.ANYWHERE));
			}
		}
		if(indisponivel) criteria.add(Restrictions.eq("ocupado", 0));
		criteria.addOrder(Order.desc("ultimaModificacao"));
		return criteria.list();
	}
	@Override
	public void save(Set<Candidato> candidatosList) {
		getEntityManager().getTransaction().begin();
		candidatosList.forEach(c->super.save(c));
		getEntityManager().getTransaction().commit();
	}

}
