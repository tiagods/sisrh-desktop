package com.plkrhone.sisrh.model.anuncio;

import com.plkrhone.sisrh.model.AbstractEntity;
import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Treinamento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "anu_can_conclusao")
public class AnuncioCandidatoConclusao implements AbstractEntity,Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    @ManyToOne
    @JoinColumn(name = "treinamento_id")
    private Treinamento treinamento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio_treinamento")
    private Calendar dataInicoTreinamento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim_treinamento")
    private Calendar dataFimTreinamento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    private Calendar dataInicio;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Treinamento getTreinamento() {
        return treinamento;
    }

    public void setTreinamento(Treinamento treinamento) {
        this.treinamento = treinamento;
    }

    public Calendar getDataInicoTreinamento() {
        return dataInicoTreinamento;
    }

    public void setDataInicoTreinamento(Calendar dataInicoTreinamento) {
        this.dataInicoTreinamento = dataInicoTreinamento;
    }

    public Calendar getDataFimTreinamento() {
        return dataFimTreinamento;
    }

    public void setDataFimTreinamento(Calendar dataFimTreinamento) {
        this.dataFimTreinamento = dataFimTreinamento;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnuncioCandidatoConclusao that = (AnuncioCandidatoConclusao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
