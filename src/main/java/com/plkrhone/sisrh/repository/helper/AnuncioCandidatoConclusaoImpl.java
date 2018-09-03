package com.plkrhone.sisrh.repository.helper;

import com.plkrhone.sisrh.model.anuncio.AnuncioCandidatoConclusao;
import com.plkrhone.sisrh.repository.AbstractRepository;
import com.plkrhone.sisrh.repository.interfaces.AnuncioCandidatoConclusaoDAO;

import javax.persistence.EntityManager;

public class AnuncioCandidatoConclusaoImpl extends AbstractRepository<AnuncioCandidatoConclusao,Long> implements AnuncioCandidatoConclusaoDAO{
    public AnuncioCandidatoConclusaoImpl(EntityManager manager) {
        super(manager);
    }
    @Override
    public AnuncioCandidatoConclusao save(AnuncioCandidatoConclusao e) {
        getEntityManager().getTransaction().begin();
        AnuncioCandidatoConclusao ex = super.save(e);
        getEntityManager().getTransaction().commit();
        return ex;
    }
    @Override
    public void remove(AnuncioCandidatoConclusao e) {
        getEntityManager().getTransaction().begin();
        super.remove(e);
        getEntityManager().getTransaction().commit();
    }
}
