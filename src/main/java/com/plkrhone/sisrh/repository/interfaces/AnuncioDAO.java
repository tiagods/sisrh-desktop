package com.plkrhone.sisrh.repository.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Vaga;

public interface AnuncioDAO {
	Anuncio save(Anuncio anuncio);
	void remove(Anuncio anuncio);
	List<Anuncio> getAll();
	Anuncio findById(Long id);
	List<Anuncio> filtrar(Anuncio.Cronograma cronograma, Anuncio.AnuncioStatus anuncioStatus, int clienteSituacao, Vaga vaga, String data, LocalDate inicial, LocalDate fim, String pesquisa, String pesquisarPor);
	long count(Anuncio.AnuncioStatus anuncioStatus);
	long count(Anuncio.Cronograma cronograma);
}
