package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Avaliacao;
import com.plkrhone.sisrh.model.avaliacao.AvaliacaoGrupo;

public interface AvaliacaoDAO {
	Avaliacao save(Avaliacao avaliacao);
	void remove(Avaliacao avaliacao);
	List<Avaliacao> getAll();
	Avaliacao findById(Long id);
	Avaliacao findByNome(String nome);
	List<Avaliacao> filtrar(String nome,Avaliacao.AvaliacaoTipo tipo, AvaliacaoGrupo grupo);
}
