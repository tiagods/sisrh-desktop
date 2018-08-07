package com.plkrhone.sisrh.repository.helper.filter;

import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Candidato.Escolaridade;
import com.plkrhone.sisrh.model.Cargo;
import com.plkrhone.sisrh.model.Curso;

public class CandidatoAnuncioFilter {
	private Cargo cargo;
	private Candidato.Escolaridade escolaridade;
	private Curso curso;
	
	public CandidatoAnuncioFilter(Cargo cargo, Candidato.Escolaridade escolaridade, Curso curso){
		this.cargo=cargo;
		this.escolaridade=escolaridade;
		this.curso = curso;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
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
	 * @return the curso
	 */
	public Curso getCurso() {
		return curso;
	}

	/**
	 * @param curso the curso to set
	 */
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
}
