package com.plkrhone.sisrh.model.anuncio;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.plkrhone.sisrh.model.AbstractEntity;
import com.plkrhone.sisrh.model.Anuncio;

@Entity
@Table(name="anu_cronograma")
public class AnuncioCronograma implements AbstractEntity, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="anuncio_id")
	private Anuncio anuncio;
	
	@Column(name = "data_inicio_divulgacao")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataInicioDivulgacao;
	
	@Column(name = "data_fim_divulgacao")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataFimDivulgacao;
	
	private String divulgacao;
	
	@Column(name = "data_inicio_recrutamento")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataInicioRecrutamento;
	
	@Column(name = "data_fim_recrutamento")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataFimRecrutamento;
	
	private String recrutamento;
	
	@Column(name = "data_inicio_agendamento")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataInicioAgendamento;
	
	@Column(name = "data_fim_agendamento")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataFimAgendamento;
	
	private String agendamento;
	
	@Column(name = "data_inicio_entrevista")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataInicioEntrevista;
	
	@Column(name = "data_fim_entrevista")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataFimEntrevista;
	
	private String entrevista;
	
	@Column(name = "data_inicio_pre_selecionado")
	@Temporal(value = TemporalType.DATE)
	
	private Calendar dataInicioPreSelecionado;
	@Column(name = "data_fim_pre_selecionado")
	
	@Temporal(value = TemporalType.DATE)
	private Calendar dataFimPreSelecionado;
	
	@Column(name="pre_selecionado")
	private String preSelecionado;

	@Column(name = "data_inicio_retorno")
	@Temporal(value = TemporalType.DATE)
	private Calendar dataInicioRetorno;
	
	@Column(name = "data_fim_retorno")
	private Calendar dataFimRetorno;
	private String retorno;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	 * @return the dataInicioDivulgacao
	 */
	public Calendar getDataInicioDivulgacao() {
		return dataInicioDivulgacao;
	}
	/**
	 * @param dataInicioDivulgacao the dataInicioDivulgacao to set
	 */
	public void setDataInicioDivulgacao(Calendar dataInicioDivulgacao) {
		this.dataInicioDivulgacao = dataInicioDivulgacao;
	}
	/**
	 * @return the dataFimDivulgacao
	 */
	public Calendar getDataFimDivulgacao() {
		return dataFimDivulgacao;
	}
	/**
	 * @param dataFimDivulgacao the dataFimDivulgacao to set
	 */
	public void setDataFimDivulgacao(Calendar dataFimDivulgacao) {
		this.dataFimDivulgacao = dataFimDivulgacao;
	}
	/**
	 * @return the divulgacao
	 */
	public String getDivulgacao() {
		return divulgacao;
	}
	/**
	 * @param divulgacao the divulgacao to set
	 */
	public void setDivulgacao(String divulgacao) {
		this.divulgacao = divulgacao;
	}
	/**
	 * @return the dataInicioRecrutamento
	 */
	public Calendar getDataInicioRecrutamento() {
		return dataInicioRecrutamento;
	}
	/**
	 * @param dataInicioRecrutamento the dataInicioRecrutamento to set
	 */
	public void setDataInicioRecrutamento(Calendar dataInicioRecrutamento) {
		this.dataInicioRecrutamento = dataInicioRecrutamento;
	}
	/**
	 * @return the dataFimRecrutamento
	 */
	public Calendar getDataFimRecrutamento() {
		return dataFimRecrutamento;
	}
	/**
	 * @param dataFimRecrutamento the dataFimRecrutamento to set
	 */
	public void setDataFimRecrutamento(Calendar dataFimRecrutamento) {
		this.dataFimRecrutamento = dataFimRecrutamento;
	}
	/**
	 * @return the recrutamento
	 */
	public String getRecrutamento() {
		return recrutamento;
	}
	/**
	 * @param recrutamento the recrutamento to set
	 */
	public void setRecrutamento(String recrutamento) {
		this.recrutamento = recrutamento;
	}
	/**
	 * @return the dataInicioAgendamento
	 */
	public Calendar getDataInicioAgendamento() {
		return dataInicioAgendamento;
	}
	/**
	 * @param dataInicioAgendamento the dataInicioAgendamento to set
	 */
	public void setDataInicioAgendamento(Calendar dataInicioAgendamento) {
		this.dataInicioAgendamento = dataInicioAgendamento;
	}
	/**
	 * @return the dataFimAgendamento
	 */
	public Calendar getDataFimAgendamento() {
		return dataFimAgendamento;
	}
	/**
	 * @param dataFimAgendamento the dataFimAgendamento to set
	 */
	public void setDataFimAgendamento(Calendar dataFimAgendamento) {
		this.dataFimAgendamento = dataFimAgendamento;
	}
	/**
	 * @return the agendamento
	 */
	public String getAgendamento() {
		return agendamento;
	}
	/**
	 * @param agendamento the agendamento to set
	 */
	public void setAgendamento(String agendamento) {
		this.agendamento = agendamento;
	}
	/**
	 * @return the dataInicioEntrevista
	 */
	public Calendar getDataInicioEntrevista() {
		return dataInicioEntrevista;
	}
	/**
	 * @param dataInicioEntrevista the dataInicioEntrevista to set
	 */
	public void setDataInicioEntrevista(Calendar dataInicioEntrevista) {
		this.dataInicioEntrevista = dataInicioEntrevista;
	}
	/**
	 * @return the dataFimEntrevista
	 */
	public Calendar getDataFimEntrevista() {
		return dataFimEntrevista;
	}
	/**
	 * @param dataFimEntrevista the dataFimEntrevista to set
	 */
	public void setDataFimEntrevista(Calendar dataFimEntrevista) {
		this.dataFimEntrevista = dataFimEntrevista;
	}
	/**
	 * @return the entrevista
	 */
	public String getEntrevista() {
		return entrevista;
	}
	/**
	 * @param entrevista the entrevista to set
	 */
	public void setEntrevista(String entrevista) {
		this.entrevista = entrevista;
	}
	/**
	 * @return the dataInicioPreSelecionado
	 */
	public Calendar getDataInicioPreSelecionado() {
		return dataInicioPreSelecionado;
	}
	/**
	 * @param dataInicioPreSelecionado the dataInicioPreSelecionado to set
	 */
	public void setDataInicioPreSelecionado(Calendar dataInicioPreSelecionado) {
		this.dataInicioPreSelecionado = dataInicioPreSelecionado;
	}
	/**
	 * @return the dataFimPreSelecionado
	 */
	public Calendar getDataFimPreSelecionado() {
		return dataFimPreSelecionado;
	}
	/**
	 * @param dataFimPreSelecionado the dataFimPreSelecionado to set
	 */
	public void setDataFimPreSelecionado(Calendar dataFimPreSelecionado) {
		this.dataFimPreSelecionado = dataFimPreSelecionado;
	}
	/**
	 * @return the preSelecionado
	 */
	public String getPreSelecionado() {
		return preSelecionado;
	}
	/**
	 * @param preSelecionado the preSelecionado to set
	 */
	public void setPreSelecionado(String preSelecionado) {
		this.preSelecionado = preSelecionado;
	}
	/**
	 * @return the dataInicioRetorno
	 */
	public Calendar getDataInicioRetorno() {
		return dataInicioRetorno;
	}
	/**
	 * @param dataInicioRetorno the dataInicioRetorno to set
	 */
	public void setDataInicioRetorno(Calendar dataInicioRetorno) {
		this.dataInicioRetorno = dataInicioRetorno;
	}
	/**
	 * @return the dataFimRetorno
	 */
	public Calendar getDataFimRetorno() {
		return dataFimRetorno;
	}
	/**
	 * @param dataFimRetorno the dataFimRetorno to set
	 */
	public void setDataFimRetorno(Calendar dataFimRetorno) {
		this.dataFimRetorno = dataFimRetorno;
	}
	/**
	 * @return the retorno
	 */
	public String getRetorno() {
		return retorno;
	}
	/**
	 * @param retorno the retorno to set
	 */
	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anuncio == null) ? 0 : anuncio.hashCode());
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
		AnuncioCronograma other = (AnuncioCronograma) obj;
		if (anuncio == null) {
			if (other.anuncio != null)
				return false;
		} else if (!anuncio.equals(other.anuncio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
