package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.CursoSuperior;

public interface CursoSuperiorDAO {
	CursoSuperior save(CursoSuperior cursoSuperior);
	void remove(CursoSuperior cursoSuperior);
	List<CursoSuperior> getAll();
	CursoSuperior findByNome(String nome);
}
