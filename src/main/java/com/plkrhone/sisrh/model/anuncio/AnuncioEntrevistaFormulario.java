package com.plkrhone.sisrh.model.anuncio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@ManyToOne
	@JoinColumn(name="formulario_id")
	private AnuncioEntrevistaFormularioTexto formulario;
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
	 * @return the formulario
	 */
	public AnuncioEntrevistaFormularioTexto getFormulario() {
		return formulario;
	}
	/**
	 * @param formulario the formulario to set
	 */
	public void setFormulario(AnuncioEntrevistaFormularioTexto formulario) {
		this.formulario = formulario;
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
