package com.plkrhone.sisrh.model.candidato;

import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Cargo;
import com.plkrhone.sisrh.model.CargoNivel;
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
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;
    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
    @Column(name = "cargo_ads")
    private String cargoAds;
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

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
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

    public String getCargoAds() {
        return cargoAds;
    }

    public void setCargoAds(String cargoAds) {
        this.cargoAds = cargoAds;
    }
}
