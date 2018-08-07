package com.plkrhone.sisrh.model.candidato;

import com.plkrhone.sisrh.model.AbstractEntity;
import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Curso;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "can_curso")
public abstract class CandidatoCurso implements Serializable, AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Override
    public Number getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Candidato getCandidato() {
        return candidato;
    }
    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
