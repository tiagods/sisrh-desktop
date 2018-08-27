package com.plkrhone.sisrh.model;

import java.io.Serializable;

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
		PROFISSIONALIZANTE, TECNICO, TECNOLOGO, GRADUACAO, POSGRADUACAO, MESTRADO, DOUTORADO
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.id==-1L?"":this.nome.toUpperCase()+" ("+nivel+")";
	}
}
