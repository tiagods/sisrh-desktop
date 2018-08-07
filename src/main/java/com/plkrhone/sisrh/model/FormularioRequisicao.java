package com.plkrhone.sisrh.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by Tiago on 15/08/2017.
 */
@Entity
@Table(name="anu_formulario_requisicao")
public class FormularioRequisicao implements AbstractEntity,Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String formulario;
	
	@ManyToOne
	@JoinColumn(name="cargo_id")
	private Cargo cargo;

	@ManyToOne
	@JoinColumn(name="nivel_id")
	private CargoNivel nivel;

	@Column(name = "cargo_ads")
	private String cargoAds;

	private String tipo;
    
    @Temporal(TemporalType.DATE)
    @Column(name="inicio_periodo")
    private Calendar inicioPeriodo;
    
    @Temporal(TemporalType.DATE)
    @Column(name="fim_periodo")
    private Calendar fimPeriodo;
    
    @Column(name="carga_horaria")
    private String cargaHoraria;
    
    @Column(name="horario_trabalho")
    private String horarioTrabalho;
    
    private int sigiloso;
    
    private String motivo;
    
    @Column(name="motivo_obs")
    private String motivoObs;
    
    @Enumerated(EnumType.STRING)
	private Candidato.Escolaridade escolaridade;
    
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="curso_superior_id")
    private Curso curso;
    
    private String idioma;
    
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cidade_id")
    private Cidade cidade;
    
    @Column(name="tempo_experiencia")
    private int tempoExperiencia;
    
    @Column(name="tipo_experiencia")
    private String tipoExperiencia;
    
    private String competencia;
    
    private String atividade;
    
    private BigDecimal salario;
    
    @Column(name="salario_combinar")
    private int salarioCombinar;
    
    private String observacao;
    
    private int comissao;
    
    @Column(name="comissao_valor")
    private BigDecimal comissaoValor;
    
    @Column(name="vale_refeicao")
    private int valeRefeicao;
    
    @Column(name="vale_refeicao_valor")
    private BigDecimal vrValor;
    
    @Column(name="vale_transporte")
    private int valeTransporte;
    
    @Column(name="assistencia_medica")
    private int assistenciaMedica;
    
    @Column(name="assistencia_medica_valor")
    private String assistenciaMedicaValor;
    
    @Column(name="outro_beneficio")
    private String outroBeneficio;
    
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="anuncio_id")
    private Anuncio anuncio;

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
	public String getFormulario() {
		return formulario;
	}

	/**
	 * @param formulario the formulario to set
	 */
	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}

	/**
	 * @return the cargo
	 */
	public Cargo getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the inicioPeriodo
	 */
	public Calendar getInicioPeriodo() {
		return inicioPeriodo;
	}

	/**
	 * @param inicioPeriodo the inicioPeriodo to set
	 */
	public void setInicioPeriodo(Calendar inicioPeriodo) {
		this.inicioPeriodo = inicioPeriodo;
	}

	/**
	 * @return the fimPeriodo
	 */
	public Calendar getFimPeriodo() {
		return fimPeriodo;
	}

	/**
	 * @param fimPeriodo the fimPeriodo to set
	 */
	public void setFimPeriodo(Calendar fimPeriodo) {
		this.fimPeriodo = fimPeriodo;
	}

	/**
	 * @return the cargaHoraria
	 */
	public String getCargaHoraria() {
		return cargaHoraria;
	}

	/**
	 * @param cargaHoraria the cargaHoraria to set
	 */
	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	/**
	 * @return the horarioTrabalho
	 */
	public String getHorarioTrabalho() {
		return horarioTrabalho;
	}

	/**
	 * @param horarioTrabalho the horarioTrabalho to set
	 */
	public void setHorarioTrabalho(String horarioTrabalho) {
		this.horarioTrabalho = horarioTrabalho;
	}

	/**
	 * @return the sigiloso
	 */
	public int getSigiloso() {
		return sigiloso;
	}

	/**
	 * @param sigiloso the sigiloso to set
	 */
	public void setSigiloso(int sigiloso) {
		this.sigiloso = sigiloso;
	}

	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * @return the motivoObs
	 */
	public String getMotivoObs() {
		return motivoObs;
	}

	/**
	 * @param motivoObs the motivoObs to set
	 */
	public void setMotivoObs(String motivoObs) {
		this.motivoObs = motivoObs;
	}

	/**
	 * @return the escolaridade
	 */
	public Candidato.Escolaridade getEscolaridade() {
		return escolaridade;
	}

	/**
	 * @param escolaridade the escolaridade to set
	 */
	public void setEscolaridade(Candidato.Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	/**
	 * @return the curso
	 */
	public Curso getCurso() {
		return curso;
	}

	/**
	 * @param curso the curso to set
	 */
	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * @return the cidade
	 */
	public Cidade getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the tempoExperiencia
	 */
	public int getTempoExperiencia() {
		return tempoExperiencia;
	}

	/**
	 * @param tempoExperiencia the tempoExperiencia to set
	 */
	public void setTempoExperiencia(int tempoExperiencia) {
		this.tempoExperiencia = tempoExperiencia;
	}

	/**
	 * @return the tipoExperiencia
	 */
	public String getTipoExperiencia() {
		return tipoExperiencia;
	}

	/**
	 * @param tipoExperiencia the tipoExperiencia to set
	 */
	public void setTipoExperiencia(String tipoExperiencia) {
		this.tipoExperiencia = tipoExperiencia;
	}

	/**
	 * @return the competencia
	 */
	public String getCompetencia() {
		return competencia;
	}

	/**
	 * @param competencia the competencia to set
	 */
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	/**
	 * @return the atividade
	 */
	public String getAtividade() {
		return atividade;
	}

	/**
	 * @param atividade the atividade to set
	 */
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	/**
	 * @return the salario
	 */
	public BigDecimal getSalario() {
		return salario;
	}

	/**
	 * @param salario the salario to set
	 */
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	/**
	 * @return the salarioCombinar
	 */
	public int getSalarioCombinar() {
		return salarioCombinar;
	}

	/**
	 * @param salarioCombinar the salarioCombinar to set
	 */
	public void setSalarioCombinar(int salarioCombinar) {
		this.salarioCombinar = salarioCombinar;
	}

	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	/**
	 * @return the comissao
	 */
	public int getComissao() {
		return comissao;
	}

	/**
	 * @param comissao the comissao to set
	 */
	public void setComissao(int comissao) {
		this.comissao = comissao;
	}

	/**
	 * @return the comissaoValor
	 */
	public BigDecimal getComissaoValor() {
		return comissaoValor;
	}

	/**
	 * @param comissaoValor the comissaoValor to set
	 */
	public void setComissaoValor(BigDecimal comissaoValor) {
		this.comissaoValor = comissaoValor;
	}

	/**
	 * @return the valeRefeicao
	 */
	public int getValeRefeicao() {
		return valeRefeicao;
	}

	/**
	 * @param valeRefeicao the valeRefeicao to set
	 */
	public void setValeRefeicao(int valeRefeicao) {
		this.valeRefeicao = valeRefeicao;
	}

	/**
	 * @return the vrValor
	 */
	public BigDecimal getVrValor() {
		return vrValor;
	}

	/**
	 * @param vrValor the vrValor to set
	 */
	public void setVrValor(BigDecimal vrValor) {
		this.vrValor = vrValor;
	}

	/**
	 * @return the valeTransporte
	 */
	public int getValeTransporte() {
		return valeTransporte;
	}

	/**
	 * @param valeTransporte the valeTransporte to set
	 */
	public void setValeTransporte(int valeTransporte) {
		this.valeTransporte = valeTransporte;
	}

	/**
	 * @return the assistenciaMedica
	 */
	public int getAssistenciaMedica() {
		return assistenciaMedica;
	}

	/**
	 * @param assistenciaMedica the assistenciaMedica to set
	 */
	public void setAssistenciaMedica(int assistenciaMedica) {
		this.assistenciaMedica = assistenciaMedica;
	}

	/**
	 * @return the assistenciaMedicaValor
	 */
	public String getAssistenciaMedicaValor() {
		return assistenciaMedicaValor;
	}

	/**
	 * @param assistenciaMedicaValor the assistenciaMedicaValor to set
	 */
	public void setAssistenciaMedicaValor(String assistenciaMedicaValor) {
		this.assistenciaMedicaValor = assistenciaMedicaValor;
	}

	/**
	 * @return the outroBeneficio
	 */
	public String getOutroBeneficio() {
		return outroBeneficio;
	}

	/**
	 * @param outroBeneficio the outroBeneficio to set
	 */
	public void setOutroBeneficio(String outroBeneficio) {
		this.outroBeneficio = outroBeneficio;
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

	public CargoNivel getNivel() {
		return nivel;
	}

	public void setNivel(CargoNivel nivel) {
		this.nivel = nivel;
	}

	public String getCargoAds() {
		return cargoAds;
	}

	public void setCargoAds(String cargoAds) {
		this.cargoAds = cargoAds;
	}
}
