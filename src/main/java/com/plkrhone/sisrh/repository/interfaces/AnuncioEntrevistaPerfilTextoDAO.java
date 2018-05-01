
package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaPerfilTexto;

public interface AnuncioEntrevistaPerfilTextoDAO {
	AnuncioEntrevistaPerfilTexto save(AnuncioEntrevistaPerfilTexto texto);
	void remove(AnuncioEntrevistaPerfilTexto texto);
	List<AnuncioEntrevistaPerfilTexto> getAll();
	List<AnuncioEntrevistaPerfilTexto> findByInativo(boolean inativo);
	AnuncioEntrevistaPerfilTexto findById(Long id);
}