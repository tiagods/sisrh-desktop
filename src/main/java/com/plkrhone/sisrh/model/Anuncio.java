package com.plkrhone.sisrh.model;

import java.io.Serializable;

import java.util.*;

import javax.persistence.*;

import com.plkrhone.sisrh.model.anuncio.AnuncioCandidatoConclusao;
import com.plkrhone.sisrh.model.anuncio.AnuncioCronograma;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;

@Entity
public class Anuncio implements AbstractEntity,Serializable {
	public enum AnuncioStatus {
		EM_ANDAMENTO("Em Andamento"),
		DECLINADO("Declinado"),
		FECHADO("Fechado");
		
		private String descricao;
		
		AnuncioStatus(String descricao){
			this.descricao=descricao;
		}
		public String getDescricao() {
			return descricao;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.descricao;
		}
	}
	public enum StatusContrato {
		ACEITO("Aceito"), EM_ANDAMENTO("Em Andamento"), RECUSADO("Recusado");
		private String descricao;
		StatusContrato(String descricao){this.descricao=descricao;}
		public String getDescricao() {
			return descricao;
		}
		@Override
		public String toString() {
			return this.descricao;
		}
	}
	public enum Cronograma{
		FORMULARIO("Formulário de Requisição"),
		CONTRATO("Envio/Recebimento de Contrato"),
		DIVULGACAO_DA_VAGA("Divulgação da Cargo"),
		RECRUTAMENTO_DE_CURRICULOS("Recrutamento de currículos"),
		AGENDAMENTO_DE_ENTREVISTA("Agendamento de entrevista"),
		ENTREVISTAS_REALIZADAS("Entrevistas realizadas"),
		ENVIO_DE_CANDIDATO_PRE_SELECIONADO("Envio de candidado pré-selecionado"),
		RETORNO_DO_PROCESSO_SELETIVO("Retorno do processo seletivo");
		//TREINAMENTO("Treinamento");
		
		private String descricao;
		Cronograma(String descricao){
			this.descricao= descricao;
		}
		public String getDescricao() {
			return descricao;
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
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="criado_em")
    private Calendar criadoEm;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="criado_por_id")
    private Usuario criadoPor;
    
    private String nome;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    
    private String responsavel;
    
    @Enumerated(value=EnumType.STRING)
    @Column(name="status")
    private AnuncioStatus anuncioStatus;
    
    @Enumerated(value=EnumType.STRING)
    private Cronograma cronograma;
    
    @Temporal(TemporalType.DATE)
    @Column(name="data_abertura")
    private Calendar dataAbertura;
    
    @Temporal(TemporalType.DATE)
    @Column(name="data_encerramento")
    private Calendar dataEncerramento;
    
    @Temporal(TemporalType.DATE)
    @Column(name="data_admissao")
    private Calendar dataAdmissao;
    
    @OneToOne(cascade = CascadeType.ALL)
    //area do formulario
    @JoinColumn(name="formulario_requisicao_id")
    private FormularioRequisicao formularioRequisicao;
    
    //area do contrato
    @Temporal(TemporalType.DATE)
    @Column(name="data_envio_contrato")
    private Calendar dataEnvioContrato;
    
    @Temporal(TemporalType.DATE)
    @Column(name="data_retorno_contrato")
    private Calendar dataRetornoContrato;
    
    @Enumerated(value=EnumType.STRING)
    @Column(name="status_contrato")
    private StatusContrato statusContrato;
    
    //area de curriculos
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="anu_curriculo", joinColumns=
		{@JoinColumn(name="anuncio_id")}, inverseJoinColumns=
			{@JoinColumn(name="candidato_id")})
    private Set<Candidato> curriculoSet = new HashSet<>();
    //entrevistas
    @OneToMany(mappedBy = "anuncio", fetch= FetchType.LAZY,cascade= CascadeType.ALL,orphanRemoval=true)
    private Set<AnuncioEntrevista> entrevistaSet = new HashSet<>();
    //pre selecao
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="anu_pre_selecao", joinColumns=
		{@JoinColumn(name="anuncio_id")}, inverseJoinColumns=
			{@JoinColumn(name="candidato_id")})
    private Set<Candidato> preSelecaoSet = new HashSet<>();

    //area de conclusao
    @OneToMany(mappedBy = "anuncio",fetch= FetchType.LAZY,
            cascade= CascadeType.ALL,orphanRemoval=true)
    private Set<AnuncioCandidatoConclusao> conclusaoSet = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, 
 	       fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="cronograma_id")
    private AnuncioCronograma cronogramaDetails;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Calendar criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Usuario getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Usuario criadoPor) {
        this.criadoPor = criadoPor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public AnuncioStatus getAnuncioStatus() {
        return anuncioStatus;
    }

    public void setAnuncioStatus(AnuncioStatus anuncioStatus) {
        this.anuncioStatus = anuncioStatus;
    }

    public Cronograma getCronograma() {
        return cronograma;
    }

    public void setCronograma(Cronograma cronograma) {
        this.cronograma = cronograma;
    }

    public Calendar getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Calendar dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Calendar getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Calendar dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Calendar getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Calendar dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Calendar getDataEnvioContrato() {
        return dataEnvioContrato;
    }

    public void setDataEnvioContrato(Calendar dataEnvioContrato) {
        this.dataEnvioContrato = dataEnvioContrato;
    }

    public Calendar getDataRetornoContrato() {
        return dataRetornoContrato;
    }

    public void setDataRetornoContrato(Calendar dataRetornoContrato) {
        this.dataRetornoContrato = dataRetornoContrato;
    }

    public StatusContrato getStatusContrato() {
        return statusContrato;
    }

    public void setStatusContrato(StatusContrato statusContrato) {
        this.statusContrato = statusContrato;
    }

    public Set<Candidato> getCurriculoSet() {
        return curriculoSet;
    }

    public void setCurriculoSet(Set<Candidato> curriculoSet) {
        this.curriculoSet = curriculoSet;
    }

    public Set<AnuncioEntrevista> getEntrevistaSet() {
        return entrevistaSet;
    }

    public void setEntrevistaSet(Set<AnuncioEntrevista> entrevistaSet) {
        this.entrevistaSet = entrevistaSet;
    }

    public Set<Candidato> getPreSelecaoSet() {
        return preSelecaoSet;
    }

    public void setPreSelecaoSet(Set<Candidato> preSelecaoSet) {
        this.preSelecaoSet = preSelecaoSet;
    }



    public FormularioRequisicao getFormularioRequisicao() {
		return formularioRequisicao;
	}

	public void setFormularioRequisicao(FormularioRequisicao formularioRequisicao) {
		this.formularioRequisicao = formularioRequisicao;
	}

    public Set<AnuncioCandidatoConclusao> getConclusaoSet() {
        return conclusaoSet;
    }

    public void setConclusaoSet(Set<AnuncioCandidatoConclusao> conclusaoSet) {
        this.conclusaoSet = conclusaoSet;
    }

    /**
	 * @return the cronogramaDetails
	 */
	public AnuncioCronograma getCronogramaDetails() {
		return cronogramaDetails;
	}

	/**
	 * @param cronogramaDetails the cronogramaDetails to set
	 */
	public void setCronogramaDetails(AnuncioCronograma cronogramaDetails) {
		this.cronogramaDetails = cronogramaDetails;
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
		Anuncio other = (Anuncio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return this.nome +" - "+this.cliente.getNome()+" - "+this.cliente.getCnpj();
    }
}
