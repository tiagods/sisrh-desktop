package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Curso;

public interface CursoDAO {
	Curso save(Curso curso);
	void remove(Curso curso);
	List<Curso> getAll();
	Curso findByNome(String nome);
}
