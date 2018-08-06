package com.plkrhone.sisrh.repository.helper.filter;

import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Candidato.Escolaridade;
import com.plkrhone.sisrh.model.CursoSuperior;
import com.plkrhone.sisrh.model.Vaga;

public class CandidatoAnuncioFilter {
	private Vaga cargo;
	private Candidato.Escolaridade escolaridade;
	private CursoSuperior cursoSuperior;
	
	public CandidatoAnuncioFilter(Vaga cargo, Candidato.Escolaridade escolaridade, CursoSuperior cursoSuperior){
		this.cargo=cargo;
		this.escolaridade=escolaridade;
		this.cursoSuperior=cursoSuperior;
	}

	public Vaga getCargo() {
		return cargo;
	}

	public void setCargo(Vaga cargo) {
		this.cargo = cargo;
	}

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
