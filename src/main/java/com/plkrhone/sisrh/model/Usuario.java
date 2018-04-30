package com.plkrhone.sisrh.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

@Entity
public class Usuario implements AbstractEntity, Serializable {
	public enum UsuarioNivel {
		ADMIN("Administrador"), GERENTE("Gerente"), OPERADOR("Operador");

		private String descricao;

		UsuarioNivel(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
		}
		@Override
		public String toString() {
			return this.descricao;
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private boolean inativo = false;
	private String login;
	private String nome;
	private String senha;
	@Embedded
	private PfPj pessoaFisica;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Calendar criadoEm;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "criado_por_id")
	private Usuario criadoPor;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ultimo_acesso")
	private Calendar ultimoAcesso;
	@Enumerated(EnumType.STRING)
	private UsuarioNivel nivel;

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

	public boolean getInativo() {
		return inativo;
	}

	public void setInativo(boolean ativo) {
		this.inativo = ativo;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PfPj getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PfPj pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	/**
	 * @return the criadoEm
	 */
	public Calendar getCriadoEm() {
		return criadoEm;
	}

	/**
	 * @param criadoEm
	 *            the criadoEm to set
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
	 * @param criadoPor
	 *            the criadoPor to set
	 */
	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}

	/**
	 * @return the ultimoAcesso
	 */
	public Calendar getUltimoAcesso() {
		return ultimoAcesso;
	}

	/**
	 * @param ultimoAcesso
	 *            the ultimoAcesso to set
	 */
	public void setUltimoAcesso(Calendar ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public UsuarioNivel getUsuarioNivel() {
		return nivel;
	}

	public void setUsuarioNivel(UsuarioNivel usuarioNivel) {
		this.nivel = usuarioNivel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public String toString() {
		return this.nome;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
