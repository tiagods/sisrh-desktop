package com.plkrhone.sisrh.repository.helper.filter;

import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Candidato.Escolaridade;
import com.plkrhone.sisrh.model.CursoSuperior;
import com.plkrhone.sisrh.model.Vaga;

public class CandidatoAnuncioFilter {
	private Vaga vaga;
	private Candidato.Escolaridade escolaridade;
	private CursoSuperior cursoSuperior;
	
	public CandidatoAnuncioFilter(Vaga vaga, Candidato.Escolaridade escolaridade, CursoSuperior cursoSuperior){
		this.vaga=vaga;
		this.escolaridade=escolaridade;
		this.cursoSuperior=cursoSuperior;
	}

	/**
	 * @return the vaga
	 */
	public Vaga getVaga() {
		return vaga;
	}

	/**
	 * @param vaga the vaga to set
	 */
	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	/**
	 * @return the escolaridade
	 */
	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	/**
	 * @param escolaridade the escolaridade to set
	 */
	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	/**
	 * @return the cursoSuperior
	 */
	public CursoSuperior getCursoSuperior() {
		return cursoSuperior;
	}

	/**
	 * @param cursoSuperior the cursoSuperior to set
	 */
	public void setCursoSuperior(CursoSuperior cursoSuperior) {
		this.cursoSuperior = cursoSuperior;
	}
	
}
