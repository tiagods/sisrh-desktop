package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAnalise;

public interface AnuncioEntrevistaAnaliseDAO {
	AnuncioEntrevistaAnalise save(AnuncioEntrevistaAnalise entrevista);
	void remove(AnuncioEntrevistaAnalise entrevista);
	List<AnuncioEntrevistaAnalise> getAll();
	AnuncioEntrevistaAnalise findById(Long id);
}
