package com.plkrhone.sisrh.util;

import java.io.Serializable;

/**
 * Created by Tiago on 27/07/2017.
 */
public class Keys implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Keys(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private String nome;
    private String valor;
   
}
