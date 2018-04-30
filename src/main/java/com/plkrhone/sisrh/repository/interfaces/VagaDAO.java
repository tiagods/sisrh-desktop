package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Vaga;

public interface VagaDAO{
	Vaga save(Vaga vaga);
	void remove(Vaga vaga);
	List<Vaga> getAll();
	List<Vaga> getVagasByNome(String nome);
	Vaga findById(Long id);
	Vaga findByNome(String vaga);
}
