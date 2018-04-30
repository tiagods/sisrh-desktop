package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Treinamento;

public interface TreinamentoDAO {
	Treinamento save(Treinamento treinamento);

	void remove(Treinamento treinamento);

	List<Treinamento> getAll();

	List<Treinamento> getTreinamentosByNome(String nome);

	Treinamento findById(Long id);
	
	Treinamento findByNome(String nome);
}
