package com.plkrhone.sisrh.model;

import com.plkrhone.sisrh.model.anuncio.AnuncioCandidatoConclusao;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * Created by Prolink on 30/07/2017.
 */
@Entity
public class Candidato implements AbstractEntity,Serializable {

	public enum Escolaridade {
		FUNDAMENTAL_INCOMPLETO("Fundamental Incompleto",1),
		FUNDAMENTAL_ANDAMENTO("Fundamental Em Andamento",2),
		FUNDAMENTAL_COMPLETO("Fundamental Completo",3),
	    MEDIO_INCOMPLETO("Médio Incompleto",4),
	    MEDIO_ANDAMENTO("Médio Em Andamento",5),
		MEDIO_COMPLETO("Médio Completo",6),
		TECNICO_INCOMPLETO("Tecnico Incompleto",7),
		TECNICO_ANDAMENTO("Tecnico Em Andamento",8),
		TECNICO_COMPLETO("Tecnico Completo",9),
	    SUPERIOR_INCOMPLETO("Superior Incompleto",10),
	    SUPERIOR_ANDAMENTO("Superior Em Andamento",11),
		SUPERIOR_COMPLETO("Superior Completo",12),
	    POS_GRADUACAO_INCOMPLETO("Pós-Graduação Incompleto",13),
		POS_GRADUACAO_ANDAMENOT("Pós-Graduação Em Andamento",14),
		POS_GRADUACAO_COMPLETO("Pós-Graduação Completo",15),
	    MESTRADO_INCOMPLETO("Mestrado Incompleto",16),
		MESTRADO_ANDAMENTO("Mestrado Em Andamento",17),
	    MESTRADO_COMPLETO("Mestrado Completo",18),
	    DOUTORADO_INCOMPLETO("Doutorado Incompleto",19),
		DOUTORADO_ANDAMENTO("Doutorado Em Andamento",20),
	    DOUTORADO_COMPLETO("Doutorado Completo",21);
		private String descricao;
		private int valor;
		Escolaridade(String descricao,int valor){
			this.descricao=descricao;
			this.valor=valor;
		}
		/**
		 * @return the descricao
		 */
		public String getDescricao() {
			return this.descricao;
		}
		/**
		 * @return the valor
		 */
		public int getValor() {
			return valor;
		}
		@Override
		public String toString() {
			return this.descricao;
		}
	}
	public enum EstadoCivil {
		SOLTEIRO("Solteiro"),CASADO("Casado"),SEPARADO("Separado"),DIVORCIADO("Divorciado"),VIUVO("Viúvo");
		private String descricao;
		EstadoCivil(String descricao){
			this.descricao=descricao;
		}
		public String getDescricao(){
			return this.descricao;
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.descricao;
		}
	}
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="criado_em")
	private Calendar criadoEm;
    @Column(name="data_modificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar ultimaModificacao;
    @ManyToOne
    @JoinColumn(name="criado_por_id")
    private Usuario criadoPor;
    private String nome;
    private String sexo;
    @Column(name="data_nascimento")
    @Temporal(TemporalType.DATE)
	private Calendar dataNascimento;

    @Transient
    private int idade;

    @Enumerated(value=EnumType.STRING)
    @Column(name="estado_civil")
    private EstadoCivil estadoCivil;
    private int fumante;
    private int filhos;
    private int qtdeFilhos;
    @Enumerated(value=EnumType.STRING)
    private Escolaridade escolaridade;
    private String nacionalidade;
    @Embedded
    private PfPj pessoaFisica;
    @ManyToOne
    @JoinColumn(name="objetivo1_id")
    private Cargo objetivo1;
    @ManyToOne
    @JoinColumn(name="objetivo2_id")
    private Cargo objetivo2;
    @ManyToOne
    @JoinColumn(name="objetivo3_id")
    private Cargo objetivo3;

    private int indicacao;
    @Column(name="empresa_indicacao")
    private String empresaIndicacao;
    @Column(name="detalhes_indicacao")
    private String detalhesIndicacao;

	private String empresa1;
	private String empresa2;
	private String empresa3;
	@ManyToOne
	@JoinColumn(name="cargo1_id")
	private Cargo cargo1;
	@ManyToOne
	@JoinColumn(name="cargo2_id")
	private Cargo cargo2;
	@ManyToOne
	@JoinColumn(name="cargo3_id")
	private Cargo cargo3;

	@ManyToOne
	@JoinColumn(name="cargo_nivel1_id")
	private CargoNivel cargoNivel1;

	@ManyToOne
	@JoinColumn(name="cargo_nivel2_id")
	private CargoNivel cargoNivel2;

	@ManyToOne
	@JoinColumn(name="cargo_nivel3_id")
	private CargoNivel cargoNivel3;

	@Column(name = "cargo_obs1")
	private String cargoObs1;

	@Column(name = "cargo_obs2")
	private String cargoObs2;

	@Column(name = "cargo_obs3")
	private String cargoObs3;
	/*
	@Column(name="descricao_cargo1")
	private String descricaoCargo1;
	@Column(name="descricao_cargo2")
	private String descricaoCargo2;
	@Column(name="descricao_cargo3")
	private String descricaoCargo3;
	*/

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="can_curso", joinColumns=
			{@JoinColumn(name="candidato_id")}, inverseJoinColumns=
			{@JoinColumn(name="curso_id")})
	private Set<Curso> cursos = new HashSet<>();
	/*
	@ManyToOne
	@JoinColumn(name="curso_superior_id")
	private Curso cursoSuperior;
*/
    private String formulario;

    @Column(name="total_recrutamento")
    private int totalRecrutamento = 0;//numero de vezes que o curriculo foi usado em recrutamento
    @Column(name="total_entrevista")
    private int totalEntrevista=0;//numero de entrevista agendadas
    @Column(name="total_pre_selecao")
    private int totalPreSelecao=0;//numero de pre-seleções realizadas
    @Column(name="total_aprovacao")
    private int totalAprovacao=0;//numero de vezes que teve o perfil aprovado
    @Column(name="ocupado")
    private int ocupado=0;
    @Column(name="ocupado_detalhes")
    private String ocupadoDetalhes;

    //recrutamento
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="anu_curriculo", joinColumns=
			{@JoinColumn(name="candidato_id")}, inverseJoinColumns=
			{@JoinColumn(name="anuncio_id")})
	private Set<Anuncio> curriculoSet = new HashSet<>();

	//entrevistas
	@OneToMany(mappedBy = "candidato", fetch= FetchType.LAZY,cascade= CascadeType.ALL,orphanRemoval=true)
	private Set<AnuncioEntrevista> entrevistaSet = new HashSet<>();

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="anu_pre_selecao", joinColumns=
			{@JoinColumn(name="candidato_id")}, inverseJoinColumns=
			{@JoinColumn(name="anuncio_id")})
	private Set<Candidato> preSelecaoSet = new HashSet<>();

	//area de conclusao
	@OneToMany(mappedBy = "candidato",fetch= FetchType.LAZY,cascade= CascadeType.ALL,orphanRemoval=true)
	private Set<AnuncioCandidatoConclusao> conclusaoSet = new HashSet<>();

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

    /**
	 * @return the ultimaModificacao
	 */
	public Calendar getUltimaModificacao() {
		return ultimaModificacao;
	}

	/**
	 * @param ultimaModificacao the ultimaModificacao to set
	 */
	public void setUltimaModificacao(Calendar ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Calendar getDataNascimento() {
    	return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public int getIdade() {
    	if(dataNascimento!=null) {
        	LocalDate now = LocalDate.now();
			Instant instant = this.dataNascimento.toInstant();
			ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
			Period period = Period.between(zonedDateTime.toLocalDate(), now);
			return this.idade=period.getYears();
        }
        else
        	return this.idade=0;
    }

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public int getFumante() {
		return fumante;
	}

	public void setFumante(int fumante) {
		this.fumante = fumante;
	}

	public int getFilhos() {
		return filhos;
	}

	public void setFilhos(int filhos) {
		this.filhos = filhos;
	}

	public int getQtdeFilhos() {
		return qtdeFilhos;
	}

	public void setQtdeFilhos(int qtdeFilhos) {
		this.qtdeFilhos = qtdeFilhos;
	}

	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public PfPj getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PfPj pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public Cargo getObjetivo1() {
		return objetivo1;
	}

	public void setObjetivo1(Cargo objetivo1) {
		this.objetivo1 = objetivo1;
	}

	public Cargo getObjetivo2() {
		return objetivo2;
	}

	public void setObjetivo2(Cargo objetivo2) {
		this.objetivo2 = objetivo2;
	}

	public Cargo getObjetivo3() {
		return objetivo3;
	}

	public void setObjetivo3(Cargo objetivo3) {
		this.objetivo3 = objetivo3;
	}

	public int getIndicacao() {
		return indicacao;
	}

	public void setIndicacao(int indicacao) {
		this.indicacao = indicacao;
	}

	public String getEmpresaIndicacao() {
		return empresaIndicacao;
	}

	public void setEmpresaIndicacao(String empresaIndicacao) {
		this.empresaIndicacao = empresaIndicacao;
	}

	public String getDetalhesIndicacao() {
		return detalhesIndicacao;
	}

	public void setDetalhesIndicacao(String detalhesIndicacao) {
		this.detalhesIndicacao = detalhesIndicacao;
	}

	public String getFormulario() {
		return formulario;
	}

	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}

	public int getTotalRecrutamento() {
		return totalRecrutamento;
	}

	public void setTotalRecrutamento(int totalRecrutamento) {
		this.totalRecrutamento = totalRecrutamento;
	}

	public int getTotalEntrevista() {
		return totalEntrevista;
	}

	public void setTotalEntrevista(int totalEntrevista) {
		this.totalEntrevista = totalEntrevista;
	}

	public int getTotalPreSelecao() {
		return totalPreSelecao;
	}

	public void setTotalPreSelecao(int totalPreSelecao) {
		this.totalPreSelecao = totalPreSelecao;
	}

	public int getTotalAprovacao() {
		return totalAprovacao;
	}

	public void setTotalAprovacao(int totalAprovacao) {
		this.totalAprovacao = totalAprovacao;
	}

	public int getOcupado() {
		return ocupado;
	}

	public void setOcupado(int ocupado) {
		this.ocupado = ocupado;
	}

	public String getOcupadoDetalhes() {
		return ocupadoDetalhes;
	}

	public void setOcupadoDetalhes(String ocupadoDetalhes) {
		this.ocupadoDetalhes = ocupadoDetalhes;
	}

	public String getEmpresa1() {
		return empresa1;
	}

	public void setEmpresa1(String empresa1) {
		this.empresa1 = empresa1;
	}

	public String getEmpresa2() {
		return empresa2;
	}

	public void setEmpresa2(String empresa2) {
		this.empresa2 = empresa2;
	}

	public String getEmpresa3() {
		return empresa3;
	}

	public void setEmpresa3(String empresa3) {
		this.empresa3 = empresa3;
	}

	public Cargo getCargo1() {
		return cargo1;
	}

	public void setCargo1(Cargo cargo1) {
		this.cargo1 = cargo1;
	}

	public Cargo getCargo2() {
		return cargo2;
	}

	public void setCargo2(Cargo cargo2) {
		this.cargo2 = cargo2;
	}

	public Cargo getCargo3() {
		return cargo3;
	}

	public void setCargo3(Cargo cargo3) {
		this.cargo3 = cargo3;
	}

	public CargoNivel getCargoNivel1() {
		return cargoNivel1;
	}

	public void setCargoNivel1(CargoNivel cargoNivel1) {
		this.cargoNivel1 = cargoNivel1;
	}

	public CargoNivel getCargoNivel2() {
		return cargoNivel2;
	}

	public void setCargoNivel2(CargoNivel cargoNivel2) {
		this.cargoNivel2 = cargoNivel2;
	}

	public CargoNivel getCargoNivel3() {
		return cargoNivel3;
	}

	public void setCargoNivel3(CargoNivel cargoNivel3) {
		this.cargoNivel3 = cargoNivel3;
	}

	public String getCargoObs1() {
		return cargoObs1;
	}

	public void setCargoObs1(String cargoObs1) {
		this.cargoObs1 = cargoObs1;
	}

	public String getCargoObs2() {
		return cargoObs2;
	}

	public void setCargoObs2(String cargoObs2) {
		this.cargoObs2 = cargoObs2;
	}

	public String getCargoObs3() {
		return cargoObs3;
	}

	public void setCargoObs3(String cargoObs3) {
		this.cargoObs3 = cargoObs3;
	}

	public Set<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Set<Curso> cursos) {
		this.cursos = cursos;
	}

	public Set<Anuncio> getCurriculoSet() {
		return curriculoSet;
	}

	public Set<AnuncioEntrevista> getEntrevistaSet() {
		return entrevistaSet;
	}

	public Set<Candidato> getPreSelecaoSet() {
		return preSelecaoSet;
	}

	public Set<AnuncioCandidatoConclusao> getConclusaoSet() {
		return conclusaoSet;
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
		Candidato other = (Candidato) obj;
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
