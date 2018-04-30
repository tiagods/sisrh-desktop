package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Cidade;
import com.plkrhone.sisrh.model.Estado;

public interface CidadeDAO {
	Cidade save(Cidade cidade);
	void remove(Cidade cidade);
	List<Cidade> getAll();
	Cidade findById(Long id);
	Cidade findByNome(String nome);
	List<Cidade> findByEstado(Estado estado);
}
