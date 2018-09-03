package com.plkrhone.sisrh.repository.interfaces;

import com.plkrhone.sisrh.model.anuncio.AnuncioCandidatoConclusao;

public interface AnuncioCandidatoConclusaoDAO {
    AnuncioCandidatoConclusao save(AnuncioCandidatoConclusao candidatoConclusao);
    AnuncioCandidatoConclusao findById(Long id);
}
