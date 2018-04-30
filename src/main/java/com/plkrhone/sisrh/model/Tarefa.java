package com.plkrhone.sisrh.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;

@Entity
public class Tarefa implements AbstractEntity,Serializable{
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
	@Enumerated(value=EnumType.STRING)
	private Anuncio.Cronograma cronograma;
	private String descricao;
	@Column(name="dia_todo")
	private int diaTodo;
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="data_inicio_evento")
	private Calendar dataInicioEvento;
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="data_fim_evento")
	private Calendar dataFimEvento;
	private int finalizado;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="atendente_id")
	private Usuario atendente;
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="criado_em")
	private Calendar criadoEm;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="criado_por_id")
	private Usuario criadoPor;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="anuncio_entrevista_id")
	private AnuncioEntrevista anuncioEntrevista;
	
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the anuncio
	 */
	public Anuncio getAnuncio() {
		return anuncio;
	}

	/**
	 * @param anuncio the anuncio to set
	 */
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	/**
	 * @return the cronograma
	 */
	public Anuncio.Cronograma getCronograma() {
		return cronograma;
	}

	/**
	 * @param cronograma the cronograma to set
	 */
	public void setCronograma(Anuncio.Cronograma cronograma) {
		this.cronograma = cronograma;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the diaTodo
	 */
	public int getDiaTodo() {
		return diaTodo;
	}

	/**
	 * @param diaTodo the diaTodo to set
	 */
	public void setDiaTodo(int diaTodo) {
		this.diaTodo = diaTodo;
	}

	/**
	 * @return the dataInicioEvento
	 */
	public Calendar getDataInicioEvento() {
		return dataInicioEvento;
	}

	/**
	 * @param dataInicioEvento the dataInicioEvento to set
	 */
	public void setDataInicioEvento(Calendar dataInicioEvento) {
		this.dataInicioEvento = dataInicioEvento;
	}

	/**
	 * @return the dataFimEvento
	 */
	public Calendar getDataFimEvento() {
		return dataFimEvento;
	}

	/**
	 * @param dataFimEvento the dataFimEvento to set
	 */
	public void setDataFimEvento(Calendar dataFimEvento) {
		this.dataFimEvento = dataFimEvento;
	}

	/**
	 * @return the finalizado
	 */
	public int getFinalizado() {
		return finalizado;
	}

	/**
	 * @param finalizado the finalizado to set
	 */
	public void setFinalizado(int finalizado) {
		this.finalizado = finalizado;
	}

	/**
	 * @return the atendente
	 */
	public Usuario getAtendente() {
		return atendente;
	}

	/**
	 * @param atendente the atendente to set
	 */
	public void setAtendente(Usuario atendente) {
		this.atendente = atendente;
	}

	/**
	 * @return the criadoEm
	 */
	public Calendar getCriadoEm() {
		return criadoEm;
	}

	/**
	 * @param criadoEm the criadoEm to set
	 */
	public void setCriadoEm(Calendar criadoEm) {
		this.criadoEm = criadoEm;
	}

	/**
	 * @return the criadoPor
	 */
	public Usuario getCriadoPor() {
		return criadoPor;
	}

	/**
	 * @param criadoPor the criadoPor to set
	 */
	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}

	/**
	 * @return the anuncioEntrevista
	 */
	public AnuncioEntrevista getAnuncioEntrevista() {
		return anuncioEntrevista;
	}

	/**
	 * @param anuncioEntrevista the anuncioEntrevista to set
	 */
	public void setAnuncioEntrevista(AnuncioEntrevista anuncioEntrevista) {
		this.anuncioEntrevista = anuncioEntrevista;
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
		Tarefa other = (Tarefa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	
}
