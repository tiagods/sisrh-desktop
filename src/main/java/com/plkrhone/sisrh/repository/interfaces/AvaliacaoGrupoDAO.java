package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.AvaliacaoGrupo;

public interface AvaliacaoGrupoDAO {
	AvaliacaoGrupo save(AvaliacaoGrupo avaliacao);
	void remove(AvaliacaoGrupo avaliacao);
	List<AvaliacaoGrupo> getAll();
	AvaliacaoGrupo findById(Long id);
	AvaliacaoGrupo findByNome(String nome);
}
