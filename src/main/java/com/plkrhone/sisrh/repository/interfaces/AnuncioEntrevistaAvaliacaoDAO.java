package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevista;
import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaAvaliacao;

public interface AnuncioEntrevistaAvaliacaoDAO {
	AnuncioEntrevistaAvaliacao save(AnuncioEntrevistaAvaliacao aeavaliacao);
	void remove(AnuncioEntrevistaAvaliacao aeavaliacao);
	List<AnuncioEntrevistaAvaliacao> getAll();
	AnuncioEntrevistaAvaliacao findById(Long id);
	List<AnuncioEntrevistaAvaliacao> findByAnuncioEntrevista(AnuncioEntrevista ae);
}
