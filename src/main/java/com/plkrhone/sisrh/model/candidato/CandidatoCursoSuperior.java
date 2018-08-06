package com.plkrhone.sisrh.model.candidato;

import com.plkrhone.sisrh.model.CursoSuperior;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CandidatoCursoSuperior extends CandidatoCurso {
    @ManyToOne
    @JoinColumn(name = "superior_id")
    private CursoSuperior superior;
}
