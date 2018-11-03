package com.plkrhone.sisrh.observer;

import java.util.*;
import com.plkrhone.sisrh.model.*;
import com.plkrhone.sisrh.repository.helper.CandidatosImp;

public class CandidatoObserver implements InterfaceCandidato{
	
	private Set<Candidato> listCandidatos = new LinkedHashSet<>();
	
	private static CandidatoObserver instance;
	
	public static CandidatoObserver getInstance() {
		if(instance == null) instance = new CandidatoObserver();
		return instance;
	}
	private CandidatoObserver() {}
	@Override
	public void registerCandidato(Candidato c, EnumCandidato e, int change) {
		if(!listCandidatos.contains(c)) listCandidatos.add(c);
		Optional<Candidato> result = listCandidatos.stream().filter(o-> o == c).findAny();
		if(!result.isPresent())
			return;
		else {
			Candidato candidato = result.get();
			switch (e) {
				case APROVACAO:
					candidato.setTotalAprovacao(candidato.getTotalAprovacao() + change);
					if (change == 1) {
						candidato.setOcupado(1);
						candidato.setOcupadoDetalhes(c.getOcupadoDetalhes());
					} else if (change == -1) {
						candidato.setOcupado(0);
						candidato.setOcupadoDetalhes("");
					}
					break;
					default:
						break;

			}

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

			candidato.setTotalRecrutamento(candidato.getCurriculoSet().size());
			candidato.setTotalEntrevista(candidato.getEntrevistaSet().size());
			candidato.setTotalPreSelecao(candidato.getPreSelecaoSet().size());
			candidato.setTotalAprovacao(candidato.getConclusaoSet().size());
			novaList.add(candidato);
		});
		candidatos.save(novaList);
		listCandidatos.clear();
	}
	@Override
	public void clear() {
		listCandidatos.clear();
	}
	
}
