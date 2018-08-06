package com.plkrhone.sisrh.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.*;

/**
 * Created by Tiago on 19/07/2017.
 */
@Entity
@Table(name="avaliacao")
public class Avaliacao implements AbstractEntity,Serializable {
	public enum AvaliacaoTipo {
		DISSERTATIVA("Dissertativa"), OBJETIVA("Objetiva"), MISTA("Mista");
		
		private String descricao;
		private AvaliacaoTipo(String descricao) {
			this.descricao=descricao;
		}
		public String getDescricao() {
			return descricao;
		}
		@Override
		public String toString() {
			return this.descricao;
		}
	}/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    private String nome;
    private String descricao;
    @Enumerated(value=EnumType.STRING)
    private AvaliacaoTipo tipo;
    private BigDecimal pontuacao;
    private String formulario;
    private String gabarito;
    @ManyToOne
    @JoinColumn(name="criado_por_id")
    private Usuario criadoPor;
    @Temporal(TemporalType.DATE)
    @Column(name="criado_em")
    private Calendar criadoEm;
    @ManyToOne
    @JoinColumn(name="grupo_id")
    private AvaliacaoGrupo grupo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public AvaliacaoTipo getTipo() {
        return tipo;
    }

    public void setTipo(AvaliacaoTipo tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(BigDecimal pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public String getGabarito() {
        return gabarito;
    }

    public void setGabarito(String gabarito) {
        this.gabarito = gabarito;
    }

    public Usuario getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Usuario criadoPor) {
        this.criadoPor = criadoPor;
    }

    public Calendar getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Calendar criadoEm) {
        this.criadoEm = criadoEm;
    }

    public AvaliacaoGrupo getDepartamento() {
        return grupo;
    }

    public void setDepartamento(AvaliacaoGrupo departamento) {
        this.grupo = departamento;
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
		Avaliacao other = (Avaliacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.nome;
	}
    
}
