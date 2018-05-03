package com.plkrhone.sisrh.model.anuncio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.plkrhone.sisrh.model.AbstractEntity;
import com.plkrhone.sisrh.model.Usuario;


@Entity
@Table(name="anu_ent_analise")
public class AnuncioEntrevistaAnalise implements AbstractEntity, Serializable{

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(cascade= {CascadeType.MERGE,CascadeType.PERSIST},
			fetch=FetchType.LAZY,orphanRemoval=true)
    @JoinColumn(name="anu_entrevista_id")
    private Set<AnuncioEntrevistaFormulario> formularios = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="anu_ent_perfil", 
    	joinColumns={@JoinColumn(name="anu_ent_analise_id")}, 
    	inverseJoinColumns={@JoinColumn(name="perfil_id")})
    private Set<AnuncioEntrevistaPerfilTexto> perfis = new HashSet<>();

	@Column(name="formulario_entrevista")
	private String formularioEntrevista;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	private Calendar criadoEm;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="criado_por_id")
	private Usuario criadoPor;
	
	@OneToOne
	@JoinColumn(name="anu_entrevista_id")
	private AnuncioEntrevista anuncioEntrevista;
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
	 * @return the formularios
	 */
	public Set<AnuncioEntrevistaFormulario> getFormularios() {
		return formularios;
	}

	/**
	 * @param formularios the formularios to set
	 */
	public void setFormularios(Set<AnuncioEntrevistaFormulario> formularios) {
		this.formularios = formularios;
	}

	/**
	 * @return the perfis
	 */
	public Set<AnuncioEntrevistaPerfilTexto> getPerfis() {
		return perfis;
	}

	/**
	 * @param perfis the perfis to set
	 */
	public void setPerfis(Set<AnuncioEntrevistaPerfilTexto> perfis) {
		this.perfis = perfis;
	}

	/**
	 * @return the formularioEntrevista
	 */
	public String getFormularioEntrevista() {
		return formularioEntrevista;
	}

	/**
	 * @param formularioEntrevista the formularioEntrevista to set
	 */
	public void setFormularioEntrevista(String formularioEntrevista) {
		this.formularioEntrevista = formularioEntrevista;
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
		AnuncioEntrevistaAnalise other = (AnuncioEntrevistaAnalise) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
