package com.plkrhone.sisrh.model.anuncio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="anu_ent_formulario")
public class AnuncioEntrevistaFormulario implements Serializable{
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@OneToMany
	@JoinColumn(name="texto_id")
	private Set<AnuncioEntrevistaFormularioTexto> textos = new HashSet<>();
	private String descricao;
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
	 * @return the textos
	 */
	public Set<AnuncioEntrevistaFormularioTexto> getTextos() {
		return textos;
	}
	/**
	 * @param textos the textos to set
	 */
	public void setTextos(Set<AnuncioEntrevistaFormularioTexto> textos) {
		this.textos = textos;
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
	
	
}
