package com.plkrhone.sisrh.model.avaliacao;

import com.plkrhone.sisrh.model.AbstractEntity;
import com.plkrhone.sisrh.model.Avaliacao;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.GenericSignatureFormatError;
import java.util.Objects;

@Entity
@Table(name = "ava_condicao")
public class AvaliacaoCondicao implements AbstractEntity,Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double de;
    private double ate;
    @Column(columnDefinition = "text")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDe() {
        return de;
    }

    public void setDe(double de) {
        this.de = de;
    }

    public double getAte() {
        return ate;
    }

    public void setAte(double ate) {
        this.ate = ate;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvaliacaoCondicao that = (AvaliacaoCondicao) o;
        return Double.compare(that.de, de) == 0 &&
                Double.compare(that.ate, ate) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(descricao, that.descricao) &&
                Objects.equals(avaliacao, that.avaliacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, de, ate, descricao, avaliacao);
    }

    @Override
    public String toString() {
        String[] strings = descricao.split(" ");
        String texto = "DE: "+this.de+" ATE:"+ate +" -> ";
        int size = texto.length();
        int maxSize = 120;
        for(int i = 0 ; i<size ; i++){
            texto+=strings[i] + " ";
            if(i==maxSize) break;
        }
        texto+="...";
        return texto;
    }
}
