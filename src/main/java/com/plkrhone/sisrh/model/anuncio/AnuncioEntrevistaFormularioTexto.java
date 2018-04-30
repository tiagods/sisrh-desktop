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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.plkrhone.sisrh.model.AbstractEntity;
import com.plkrhone.sisrh.model.Usuario;

@Entity
@Table(name="anu_ent_for_texto)")
public class AnuncioEntrevistaFormularioTexto implements AbstractEntity,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int sequencia;
	private String pergunta;
	private String descricao;
	private boolean inativo;
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	private Calendar criadoEm;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="criado_por_id")
	private Usuario criadoPor;
	@Override
	public Number getId() {
		// TODO Auto-generated method stub
		return id;
	}
	/**
	 * @return the sequencia
	 */
	public int getSequencia() {
		return sequencia;
	}
	/**
	 * @param sequencia the sequencia to set
	 */
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	
	/**
	 * @return the pergunta
	 */
	public String getPergunta() {
		return pergunta;
	}
	/**
	 * @param pergunta the pergunta to set
	 */
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
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
	 * @return the inativo
	 */
	public boolean isInativo() {
		return inativo;
	}
	/**
	 * @param inativo the inativo to set
	 */
	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
		AnuncioEntrevistaFormularioTexto other = (AnuncioEntrevistaFormularioTexto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EntrevistaFormularioTexto [pergunta=" + pergunta + "]";
	}
	
	
}
