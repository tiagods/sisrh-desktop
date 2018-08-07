package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Cargo;

public interface VagaDAO{
	Cargo save(Cargo cargo);
	void remove(Cargo cargo);
	List<Cargo> getAll();
	List<Cargo> getVagasByNome(String nome);
	Cargo findById(Long id);
	Cargo findByNome(String vaga);
}
