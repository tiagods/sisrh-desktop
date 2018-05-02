package com.plkrhone.sisrh.model.anuncio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.*;

import com.plkrhone.sisrh.model.AbstractEntity;
import com.plkrhone.sisrh.model.Avaliacao;
import com.plkrhone.sisrh.model.Usuario;

@Entity
@Table(name="anu_ent_avaliacao")
public class AnuncioEntrevistaAvaliacao implements AbstractEntity, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="avaliacao_id")
	private Avaliacao avaliacao;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="anu_entrevista_id")
	private AnuncioEntrevista anuncioEntrevista;
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	private Calendar criadoEm;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="criado_por_id")
	private Usuario criadoPor;
	private BigDecimal pontuacao;
	@Column(name="pontuacao_maxima")
	private BigDecimal pontuacaoMaxima;
    private String gabarito;
	@Enumerated(value=EnumType.STRING)
	@Column(name="avaliacao_tipo")
    private Avaliacao.AvaliacaoTipo avaliacaoTipo;
	private String formulario;
	/**
	 * @return the id
	 */
	@Override
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
	 * @return the avaliacao
	 */
	public Avaliacao getAvaliacao() {
		return avaliacao;
	}
	/**
	 * @param avaliacao the avaliacao to set
	 */
	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
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
	 * @return the pontuacao
	 */
	public BigDecimal getPontuacao() {
		return pontuacao;
	}
	/**
	 * @param pontuacao the pontuacao to set
	 */
	public void setPontuacao(BigDecimal pontuacao) {
		this.pontuacao = pontuacao;
	}
	
	/**
	 * @return the pontuacaoMaxima
	 */
	public BigDecimal getPontuacaoMaxima() {
		return pontuacaoMaxima;
	}
	/**
	 * @param pontuacaoMaxima the pontuacaoMaxima to set
	 */
	public void setPontuacaoMaxima(BigDecimal pontuacaoMaxima) {
		this.pontuacaoMaxima = pontuacaoMaxima;
	}
	/**
	 * @return the gabarito
	 */
	public String getGabarito() {
		return gabarito;
	}
	/**
	 * @param gabarito the gabarito to set
	 */
	public void setGabarito(String gabarito) {
		this.gabarito = gabarito;
	}
	/**
	 * @return the avaliacaoTipo
	 */
	public Avaliacao.AvaliacaoTipo getAvaliacaoTipo() {
		return avaliacaoTipo;
	}
	/**
	 * @param avaliacaoTipo the avaliacaoTipo to set
	 */
	public void setAvaliacaoTipo(Avaliacao.AvaliacaoTipo avaliacaoTipo) {
		this.avaliacaoTipo = avaliacaoTipo;
	}
	
	/**
	 * @return the formulario
	 */
	public String getFormulario() {
		return formulario;
	}
	/**
	 * @param formulario the formulario to set
	 */
	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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
		AnuncioEntrevistaAvaliacao other = (AnuncioEntrevistaAvaliacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
