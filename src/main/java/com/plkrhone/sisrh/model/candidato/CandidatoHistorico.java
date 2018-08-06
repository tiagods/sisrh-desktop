package com.plkrhone.sisrh.model.candidato;

import com.plkrhone.sisrh.model.CargoNivel;
import com.plkrhone.sisrh.model.Vaga;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vaga getCargo() {
        return cargo;
    }

    public void setCargo(Vaga cargo) {
        this.cargo = cargo;
    }

    public CargoNivel getNivel() {
        return nivel;
    }

    public void setNivel(CargoNivel nivel) {
        this.nivel = nivel;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Calendar getInicio() {
        return inicio;
    }

    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    public Calendar getFim() {
        return fim;
    }

    public void setFim(Calendar fim) {
        this.fim = fim;
    }
}
