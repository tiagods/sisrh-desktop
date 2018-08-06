package com.plkrhone.sisrh.model;

import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "can_historico")
public class CandidatoHistorico extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Vaga cargo;
    @ManyToOne
    @JoinColumn(name = "nivel_id")
    private CargoNivel nivel;
    private String empresa;
    private String descricao;
    @Temporal( value = TemporalType.DATE)
    private Calendar inicio;
    @Temporal( value = TemporalType.DATE)
    private Calendar fim;


}
