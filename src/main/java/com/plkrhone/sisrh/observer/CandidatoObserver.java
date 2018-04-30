package com.plkrhone.sisrh.observer;

import java.util.*;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.repository.helper.CandidatosImp;

public class CandidatoObserver implements InterfaceCandidato{
	
	private Set<Candidato> listCandidatos = new LinkedHashSet<>();
	
	private static CandidatoObserver instance;
	
	public static CandidatoObserver getInstance() {
		if(instance == null) instance=new CandidatoObserver();
		return instance;
	}
	private CandidatoObserver() {}
	@Override
	public void registerCandidato(Candidato c, EnumCandidato e, int change) {
		if(!listCandidatos.contains(c)) listCandidatos.add(c);
		switch(e) {
			case CURRICULO:
				listCandidatos.stream().filter(o-> o.getId() == c.getId()).forEach(candidato->{
					candidato.setTotalRecrutamento(candidato.getTotalRecrutamento()+change);
				});
				break;
			case ENTREVISTA:
				listCandidatos.stream().filter(o-> o.getId() == c.getId()).forEach(candidato->{
					candidato.setTotalEntrevista(candidato.getTotalEntrevista()+change);
				});
				break;
			case PRESELECAO:
				listCandidatos.stream().filter(o-> o.getId() == c.getId()).forEach(candidato->{
					candidato.setTotalPreSelecao(candidato.getTotalPreSelecao()+change);
				});
				break;
			case APROVACAO:
				listCandidatos.stream().filter(o->o.getId()== c.getId()).forEach(candidato->{
					candidato.setTotalAprovacao(candidato.getTotalAprovacao()+change);
					if(change==1) {
						candidato.setOcupado(1);
						candidato.setOcupadoDetalhes(c.getOcupadoDetalhes());
					}
					else if(change==-1){
						candidato.setOcupado(0);
						candidato.setOcupadoDetalhes("");
					}
				});
		}
	}
	
	@Override
	public void unregisterCandidato(Candidato c, EnumCandidato[] e, int change) {
		for(EnumCandidato ec : e) registerCandidato(c, ec, change);
	}
	@Override
	public void notifyUpdate(CandidatosImp candidatos) throws Exception{
		Set<Candidato> novaList = new LinkedHashSet<>();
		//recebendo candidatos direto do banco, caso algum seja atualizado
		listCandidatos.forEach(c->{
			Candidato candidato = candidatos.findById(c.getId());
			candidato.setOcupado(c.getOcupado());
			candidato.setOcupadoDetalhes(c.getOcupadoDetalhes());
			candidato.setTotalRecrutamento(c.getTotalRecrutamento());
			candidato.setTotalEntrevista(c.getTotalEntrevista());
			candidato.setTotalPreSelecao(c.getTotalPreSelecao());
			candidato.setTotalAprovacao(c.getTotalAprovacao());
			novaList.add(candidato);
			System.out.println("Candidato: "+c.getId()+"\tCurriculos: "+c.getTotalRecrutamento()+
					"\tEntrevistas: "+c.getTotalEntrevista()+
					"\tPreSelecao: "+c.getTotalPreSelecao()+"\tAprovações: "+c.getTotalAprovacao());
		});
		candidatos.save(novaList);
		listCandidatos.clear();
	}
	@Override
	public void clear() {
		listCandidatos.clear();
	}
	
}
