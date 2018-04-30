package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.anuncio.AnuncioEntrevistaFormularioTexto;

public interface AnuncioEntrevistaFormularioTextoDAO {
	AnuncioEntrevistaFormularioTexto save(AnuncioEntrevistaFormularioTexto texto);
	void remove(AnuncioEntrevistaFormularioTexto texto);
	List<AnuncioEntrevistaFormularioTexto> getAll();
	List<AnuncioEntrevistaFormularioTexto> findByInativo(boolean inativo);
	AnuncioEntrevistaFormularioTexto findById(Long id);
}
