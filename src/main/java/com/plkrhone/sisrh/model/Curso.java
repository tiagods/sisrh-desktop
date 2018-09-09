package com.plkrhone.sisrh.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name="curso_superior")
public class Curso implements AbstractEntity,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Enumerated(value = EnumType.STRING)
	private Nivel nivel;

	public Curso (Long id,String nome){
		this.id=id;
		this.nome=nome;
	}
	public Curso(){}

	public enum Nivel{
		PROFISSIONALIZANTE, TECNICO, GRADUACAO, POSGRADUACAO, MESTRADO, DOUTORADO
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	/* (non-Javadoc)
             * @see java.lang.Object#hashCode()
             */

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Curso curso = (Curso) o;
		return Objects.equals(id, curso.id) &&
				Objects.equals(nome, curso.nome) &&
				nivel == curso.nivel;
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, nome, nivel);
	}

	@Override
	public String toString() {
		return this.id==-1L?"":this.nome.toUpperCase()+" ("+nivel+")";
	}
}
