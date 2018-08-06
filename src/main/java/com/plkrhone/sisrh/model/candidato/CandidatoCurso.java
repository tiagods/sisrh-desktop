package com.plkrhone.sisrh.model.candidato;

import com.plkrhone.sisrh.model.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "can_curso")
public abstract class CandidatoCurso implements Serializable, AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Override
    public Number getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
