package com.plkrhone.sisrh.model.anuncio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.plkrhone.sisrh.model.AbstractEntity;
import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Cliente;

@Entity
@Table(name="anu_entrevista")
public class AnuncioEntrevista implements AbstractEntity,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="anuncio_id")
	private Anuncio anuncio;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="candidato_id")
	private Candidato candidato;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="anu_entrevista_id")
    private Set<AnuncioEntrevistaAvaliacao> avaliacao = new HashSet<>();
	
	@OneToOne(mappedBy="anuncioEntrevista",cascade=CascadeType.ALL,optional=true)
	//@JoinColumn(name="anu_entrevista_id")
	private AnuncioEntrevistaAnalise entrevista;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the anuncio
	 */
	public Anuncio getAnuncio() {
		return anuncio;
	}
	/**
	 * @return the candidato
	 */
	public Candidato getCandidato() {
		return candidato;
	}
	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}
	/**
	 * @return the avaliacao
	 */
	public Set<AnuncioEntrevistaAvaliacao> getAvaliacao() {
		return avaliacao;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param anuncio the anuncio to set
	 */
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	/**
	 * @param candidato the candidato to set
	 */
	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	/**
	 * @param avaliacao the avaliacao to set
	 */
	public void setAvaliacao(Set<AnuncioEntrevistaAvaliacao> avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	/**
	 * @return the entrevista
	 */
	public AnuncioEntrevistaAnalise getEntrevista() {
		return entrevista;
	}
	/**
	 * @param entrevista the entrevista to set
	 */
	public void setEntrevista(AnuncioEntrevistaAnalise entrevista) {
		this.entrevista = entrevista;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.candidato.getNome();
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
		AnuncioEntrevista other = (AnuncioEntrevista) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
