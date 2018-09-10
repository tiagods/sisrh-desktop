package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Curso;

public interface CursoDAO {
	Curso save(Curso curso);
	void remove(Curso curso);
	Curso findByNome(String nome);
	List<Curso> listByNome(String nome);
	List<Curso> getAll();

}
